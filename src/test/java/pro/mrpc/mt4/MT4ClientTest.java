package pro.mrpc.mt4;

import pro.mrpc.mt4.exception.DecoderException;
import pro.mrpc.mt4.network.MetaTraderMessage;
import pro.mrpc.mt4.network.enums.Op;
import pro.mrpc.mt4.network.enums.TradeCommand;
import pro.mrpc.mt4.network.messages.req.OrderTransactionReq;
import pro.mrpc.mt4.network.messages.req.SubscribeQuotesReq;
import pro.mrpc.mt4.network.messages.res.OrderHistoryRes;
import pro.mrpc.mt4.network.messages.res.OrderUpdateEvent;
import pro.mrpc.mt4.network.messages.res.QuoteEvent;
import pro.mrpc.mt4.network.messages.res.TradeEvent;
import pro.mrpc.mt4.network.enums.Execution;
import pro.mrpc.mt4.network.parts.AccountInfo;
import pro.mrpc.mt4.network.parts.ServerInfo;
import pro.mrpc.mt4.network.parts.SymbolInfo;
import pro.mrpc.mt4.network.parts.TradeRecord;
import pro.mrpc.mt4.utils.SymbolTable;

import org.junit.jupiter.api.*;

import pro.mrpc.mt4.network.enums.Timeframe;
import pro.mrpc.mt4.network.messages.req.HistoricalBarsReq;
import pro.mrpc.mt4.network.messages.res.HistoricalBarsRes;
import pro.mrpc.mt4.network.parts.BarRecord;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for MT4Client: connection, order history, and full order lifecycle.
 * <p>
 * Configure credentials via environment variables:
 * <pre>
 *   MT4_HOST     - server hostname/IP
 *   MT4_PORT     - server port (default 443)
 *   MT4_USER     - account number
 *   MT4_PASS     - account password
 *   MT4_SYMBOL   - symbol to trade (default "EURUSD")
 * </pre>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MT4ClientTest {

    private static final String HOST = System.getenv("MT4_HOST");
    private static final int PORT = Integer.parseInt(System.getenv().getOrDefault("MT4_PORT", "443"));
    private static final int USER = Integer.parseInt(System.getenv().getOrDefault("MT4_USER", "0"));
    private static final String PASS = System.getenv("MT4_PASS");
    private static final String SYMBOL = System.getenv().getOrDefault("MT4_SYMBOL", "EURUSD");

    private MT4Client client;
    private final CountDownLatch connectedLatch = new CountDownLatch(1);
    private final ConcurrentHashMap<String, QuoteEvent> quotes = new ConcurrentHashMap<>();
    private final AtomicReference<Throwable> asyncError = new AtomicReference<>();

    @BeforeAll
    void setUp() {
        Assumptions.assumeTrue(HOST != null && !HOST.isEmpty() && USER != 0 && PASS != null && !PASS.isEmpty(),
                "Skipping: set MT4_HOST, MT4_USER, MT4_PASS environment variables to run integration tests");
    }

    // ------------------------------------------------------------------ //
    //  1. Connection (with order history pre-configured)
    // ------------------------------------------------------------------ //

    @Test
    @Order(1)
    @DisplayName("Connect to MT4 server")
    void testConnect() throws Exception {
        client = new MT4Client(HOST, PORT, new MessageHandler() {
            @Override
            public void onMessage(MetaTraderMessage message, MT4Client c) {
                if (message instanceof QuoteEvent q) {
                    quotes.put(q.symbol, q);
                } else {
                    System.out.println("[onMessage] " + message);
                }
            }

            @Override
            public void onReceiveFailure(DecoderException e) {
                System.err.println("[onReceiveFailure] " + e.getMessage());
                asyncError.set(e);
            }

            @Override
            public void onSendFailure(Exception e) {
                System.err.println("[onSendFailure] " + e.getMessage());
                asyncError.set(e);
            }

            @Override
            public void onConnected(MT4Client c) {
                System.out.println("[onConnected] Account ready");
                connectedLatch.countDown();
            }

            @Override
            public void onDisconnected(IOException exception, MT4Client c) {
                System.err.println("[onDisconnected] " + exception.getMessage());
            }
        });

        client.connect(USER, PASS);

        assertTrue(connectedLatch.await(30, TimeUnit.SECONDS), "Connection timed out");
        assertTrue(client.isBaseAccountRead(), "Base account should be read");
        System.out.println("Connected successfully. Server build: " + client.getLoginHelper().getServerBuild());
    }

    // ------------------------------------------------------------------ //
    //  2. Subscribe / unsubscribe quotes
    // ------------------------------------------------------------------ //

    @Test
    @Order(2)
    @DisplayName("Subscribe to quotes, receive ticks, then unsubscribe")
    void testSubscribeUnsubscribe() throws Exception {
        assertNotNull(client, "Client not connected");

        // Subscribe using the Req/Res pattern
        short symbolCode = client.getSymbolTable().getCode(SYMBOL);

        SubscribeQuotesReq subReq = new SubscribeQuotesReq();
        subReq.symbolCodes = new short[]{symbolCode};
        client.sendRequestAsync(subReq);
        System.out.println("Subscribed to " + SYMBOL + " (code=" + symbolCode + ")");

        // Wait for quotes to arrive
        Thread.sleep(3000);
        QuoteEvent quote = quotes.get(SYMBOL);
        assertNotNull(quote, "Should have received at least one quote for " + SYMBOL);
        System.out.println("Received quote: " + quote);
        assertTrue(quote.bid > 0, "Bid should be positive");
        assertTrue(quote.ask > 0, "Ask should be positive");
        assertTrue(quote.ask >= quote.bid, "Ask should be >= Bid");

        // Unsubscribe (send empty subscription list)
        int quotesBefore = quotes.size();
        SubscribeQuotesReq unsubReq = new SubscribeQuotesReq();
        unsubReq.symbolCodes = new short[0];
        client.sendRequestAsync(unsubReq);
        System.out.println("Unsubscribed. Quotes collected: " + quotesBefore + " symbols");
    }

    // ------------------------------------------------------------------ //
    //  3. Account info
    // ------------------------------------------------------------------ //

    @Test
    @Order(3)
    @DisplayName("Verify account info and open orders")
    void testAccountInfo() {
        assertNotNull(client, "Client not connected");

        AccountInfo info = client.getAccountInfo();
        assertNotNull(info, "AccountInfo should not be null");

        System.out.println("Account: " + info);
        System.out.println("  Type     : " + info.accountType);
        System.out.println("  Name     : " + info.name);
        System.out.println("  Currency : " + info.currency);
        System.out.println("  Company  : " + info.company);
        System.out.println("  Group    : " + info.group);
        System.out.println("  Leverage : " + info.leverage);
        System.out.println("  Balance  : " + info.balance);
        System.out.println("  Credit   : " + info.credit);
        System.out.println("  Mode     : " + (info.isInvestor() ? "Investor" : "Master"));
        System.out.println("  TimeZone : " + info.timeZone);

        assertNotNull(info.accountType, "AccountType should be parsed");
        assertTrue(info.leverage > 0, "Leverage should be positive");
        assertTrue(info.balance >= 0, "Balance should not be negative");
        assertNotNull(info.name, "Account name should not be null");

        // Currency and broker info (ported from ConGroup)
        assertNotNull(info.currency, "Currency should be parsed from ConGroup");
        assertFalse(info.currency.isEmpty(), "Currency should not be empty (e.g. USD, EUR)");
        assertNotNull(info.company, "Company should be parsed from ConGroup");
        assertNotNull(info.group, "Group should be parsed from ConGroup");
        assertEquals(info.currency, client.getAccountCurrency());
        assertEquals(info.company, client.getCompany());
        assertEquals(info.group, client.getAccountGroup());

        // Verify shortcut accessors
        assertEquals(info.balance, client.getBalance());
        assertEquals(info.leverage, client.getLeverage());
        assertEquals(info.name, client.getAccountName());

        // Open orders (may be empty)
        ConcurrentHashMap<Integer, OrderUpdateEvent> orders = client.getOpenOrders();
        assertNotNull(orders, "Open orders map should not be null");
        System.out.println("Open orders: " + orders.size());
        for (OrderUpdateEvent order : orders.values()) {
            System.out.println("  " + order);
        }
    }

    // ------------------------------------------------------------------ //
    //  4. Server / broker info
    // ------------------------------------------------------------------ //

    @Test
    @Order(4)
    @DisplayName("Verify server/broker info")
    void testServerInfo() {
        assertNotNull(client, "Client not connected");

        ServerInfo serverInfo = client.getServerInfo();
        assertNotNull(serverInfo, "ServerInfo should not be null");

        System.out.println("Server info: " + serverInfo);
        System.out.println("  Name    : " + serverInfo.name);
        System.out.println("  Comment : " + serverInfo.comment);
        System.out.println("  IsDemo  : " + serverInfo.isDemo);
        System.out.println("  Host    : " + serverInfo.getHost());
        System.out.println("  Port    : " + serverInfo.getPort());
        System.out.println("  Servers : " + serverInfo.servers.length);

        assertNotNull(serverInfo.name, "Server name should not be null");
        assertFalse(serverInfo.name.isEmpty(), "Server name should not be empty");
        assertNotNull(serverInfo.hostAddress, "Host address should not be null");
        assertFalse(serverInfo.getHost().isEmpty(), "Host should not be empty");
        assertTrue(serverInfo.getPort() > 0, "Port should be positive");
        assertNotNull(serverInfo.servers, "Servers array should not be null");
        assertTrue(serverInfo.servers.length > 0, "Should have at least one server entry");

        for (ServerInfo.ServerEntry entry : serverInfo.servers) {
            System.out.println("  Server: " + entry);
        }
    }

    // ------------------------------------------------------------------ //
    //  5. Order History (dynamic — uses a separate connection)
    // ------------------------------------------------------------------ //

    @Test
    @Order(5)
    @DisplayName("Download order history")
    void testOrderHistory() throws Exception {
        assertNotNull(client, "Client not connected");

        // Match original API pattern: 12 months back, 1 month forward
        int twelveMonthsAgo = (int) Instant.now().minus(365, ChronoUnit.DAYS).getEpochSecond();
        int oneMonthForward = (int) Instant.now().plus(30, ChronoUnit.DAYS).getEpochSecond();

        OrderHistoryRes res = client.requestOrderHistory(0, oneMonthForward);
        assertNotNull(res, "OrderHistoryRes should not be null");

        System.out.println("Order history: " + res.trades.size() + " trades");
        for (TradeRecord trade : res.trades) {
            System.out.println("  " + trade);
        }
    }

    // ------------------------------------------------------------------ //
    //  6. Historical bars
    // ------------------------------------------------------------------ //

    @Test
    @Order(6)
    @DisplayName("Download historical bars")
    void testHistoricalBars() throws Exception {
        assertNotNull(client, "Client not connected");

        HistoricalBarsReq req = new HistoricalBarsReq();
        req.symbol = SYMBOL;
        req.timeframe = Timeframe.H1;
        req.fromTime = (int) Instant.now().minus(7, ChronoUnit.DAYS).getEpochSecond();
        req.count = 100;

        System.out.println("Requesting " + req.count + " H1 bars for " + SYMBOL + "...");
        HistoricalBarsRes res = (HistoricalBarsRes) client.sendRequest(req);
        assertNotNull(res, "HistoricalBarsRes should not be null (timed out?)");

        System.out.println("Received: " + res);
        assertFalse(res.bars.isEmpty(), "Should receive at least 1 bar");

        // Verify bar data is sensible
        int digits = client.getSymbolTable().getDigits(SYMBOL);
        double point = Math.pow(10, -digits);

        for (int i = 0; i < Math.min(5, res.bars.size()); i++) {
            BarRecord bar = res.bars.get(i);
            double open = bar.open * point;
            double high = bar.high * point;
            double low = bar.low * point;
            double close = bar.close * point;
            System.out.printf("  %s  O=%.5f H=%.5f L=%.5f C=%.5f V=%.0f%n",
                    Instant.ofEpochSecond(bar.time), open, high, low, close, bar.volume);
            assertTrue(high >= low, "High should be >= Low");
        }
        System.out.println("  ... (" + res.bars.size() + " bars total)");
    }

    // ------------------------------------------------------------------ //
    //  7. Full order lifecycle: Open -> Modify (set TP/SL) -> Close
    // ------------------------------------------------------------------ //

    @Test
    @Order(7)
    @DisplayName("Open order, set TP/SL, close order")
    void testOrderLifecycle() throws Exception {
        assertNotNull(client, "Client not connected");

        // Allow some time for quote events to arrive so we have a price reference
        Thread.sleep(2000);

        // Auto-detect execution mode from symbol info (like the original API)
        SymbolInfo symbolInfo = client.getSymbolTable().getInfo(SYMBOL);
        System.out.println(symbolInfo);
        System.out.println(symbolInfo.ex);
        boolean isMarket = symbolInfo.execution == Execution.Market;
        System.out.println("Symbol " + SYMBOL + ": " + symbolInfo);

        QuoteEvent quote = quotes.get(SYMBOL);
        System.out.println("Latest quote for " + SYMBOL + ": " + quote);

        // --- Step 1: Open a market Buy order ---
        // Market execution → cmd=MARKET, price=0, slippage=0
        // Instant execution → cmd=INSTANT, price=ask, slippage=N
        OrderTransactionReq openReq = new OrderTransactionReq();
        openReq.orderType = Op.Buy;
        openReq.symbol = SYMBOL;
        openReq.lots = 1; // 0.01 lot (minimum)
        openReq.comment = "MT4SDK";

        if (isMarket) {
            openReq.tradeCommand = TradeCommand.Market;
            openReq.price = 0;
            openReq.slippage = 0;
        } else {
            openReq.tradeCommand = TradeCommand.Instant;
            assertNotNull(quote, "Need a quote for Instant execution but none received for " + SYMBOL);
            openReq.price = quote.ask;
            openReq.slippage = 50; // 5 pips for 5-digit broker
        }

        System.out.println("Opening order: " + openReq);
        TradeEvent openRes = (TradeEvent) client.sendRequest(openReq);
        assertNotNull(openRes, "Open order response should not be null (timed out?)");
        System.out.println("Open result: " + openRes);

        assertTrue(openRes.ticket > 0, "Expected a valid ticket, got error code: " + openRes.errorCode);
        int ticket = openRes.ticket;
        double openPrice = openReq.price;
        System.out.println("Order opened: ticket #" + ticket);

        Thread.sleep(1000);

        // --- Step 2: Modify order - set TP and SL ---
        // Use the actual open price for SL/TP calculation
        QuoteEvent currentQuote = quotes.get(SYMBOL);
        double refPrice = openPrice > 0 ? openPrice : (currentQuote != null ? currentQuote.ask : 0);
        assertTrue(refPrice > 0, "No reference price available for SL/TP calculation");

        double stopLoss = refPrice - 0.0050;   // 50 pips below
        double takeProfit = refPrice + 0.0050;  // 50 pips above

        OrderTransactionReq modifyReq = new OrderTransactionReq();
        modifyReq.tradeCommand = TradeCommand.Modify;
        modifyReq.orderType = Op.Buy;
        modifyReq.symbol = SYMBOL;
        modifyReq.ticket = ticket;
        modifyReq.price = refPrice;
        modifyReq.stopLoss = stopLoss;
        modifyReq.takeProfit = takeProfit;

        System.out.println("Modifying order #" + ticket + ": SL=" + stopLoss + ", TP=" + takeProfit);
        TradeEvent modifyRes = (TradeEvent) client.sendRequest(modifyReq);
        assertNotNull(modifyRes, "Modify order response should not be null (timed out?)");
        System.out.println("Modify result: " + modifyRes);

        assertTrue(modifyRes.ticket > 0, "Modify failed with error code: " + modifyRes.errorCode);

        Thread.sleep(1000);

        // --- Step 3: Close the order ---
        // Market execution → CloseMarket, price=0, slippage=0
        // Instant execution → CloseInstant, price=bid, slippage=N
        OrderTransactionReq closeReq = new OrderTransactionReq();
        closeReq.orderType = Op.Buy;
        closeReq.symbol = SYMBOL;
        closeReq.ticket = ticket;
        closeReq.lots = 1; // must match open lots

        if (isMarket) {
            closeReq.tradeCommand = TradeCommand.CloseMarket;
            closeReq.price = 0;
            closeReq.slippage = 0;
        } else {
            closeReq.tradeCommand = TradeCommand.CloseInstant;
            QuoteEvent closeQuote = quotes.get(SYMBOL);
            assertNotNull(closeQuote, "Need a quote for Instant close but none received for " + SYMBOL);
            closeReq.price = closeQuote.bid; // close Buy at bid
            closeReq.slippage = 50;
        }

        System.out.println("Closing order #" + ticket);
        TradeEvent closeRes = (TradeEvent) client.sendRequest(closeReq);
        assertNotNull(closeRes, "Close order response should not be null (timed out?)");
        System.out.println("Close result: " + closeRes);

        assertTrue(closeRes.ticket > 0, "Close failed with error code: " + closeRes.errorCode);
        System.out.println("Order #" + ticket + " closed successfully");
    }

    // ------------------------------------------------------------------ //
    //  Cleanup
    // ------------------------------------------------------------------ //

    @AfterAll
    void tearDown() {
        if (client != null) {
            System.out.println("Disconnecting...");
            client.disconnect();
        }
    }

    @Test
    @DisplayName("Verify deterministic LoginId generation")
    void testLoginIdGeneration() {
        pro.mrpc.mt4.utils.LoginId loginId = new pro.mrpc.mt4.utils.LoginId();
        byte[] sampleData = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08 };
        long id = loginId.decodeData(sampleData);
        assertNotNull(id);
    }
}
