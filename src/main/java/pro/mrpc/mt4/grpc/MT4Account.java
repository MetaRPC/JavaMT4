package pro.mrpc.mt4.grpc;

import com.google.protobuf.Timestamp;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import mt4_term_api.AccountHelperGrpc;
import mt4_term_api.ConnectionGrpc;
import mt4_term_api.MarketInfoGrpc;
import mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryData;
import mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest;
import mt4_term_api.Mt4TermApiAccountHelper.EnumOpenedOrderSortType;
import mt4_term_api.Mt4TermApiAccountHelper.EnumOrderHistorySortType;
import mt4_term_api.Mt4TermApiAccountHelper.OpenedOrderInfo;
import mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersData;
import mt4_term_api.Mt4TermApiAccountHelper.OpenedOrdersRequest;
import mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryData;
import mt4_term_api.Mt4TermApiAccountHelper.OrdersHistoryRequest;
import mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyData;
import mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyInfo;
import mt4_term_api.Mt4TermApiAccountHelper.SymbolParamsManyRequest;
import mt4_term_api.Mt4TermApiConnection.ConnectExReply;
import mt4_term_api.Mt4TermApiConnection.ConnectExRequest;
import mt4_term_api.Mt4TermApiConnection.DisconnectRequest;
import mt4_term_api.Mt4TermApiMarketInfo.ENUM_QUOTE_HISTORY_TIMEFRAME;
import mt4_term_api.Mt4TermApiMarketInfo.HistoryQuote;
import mt4_term_api.Mt4TermApiMarketInfo.QuoteData;
import mt4_term_api.Mt4TermApiMarketInfo.QuoteHistoryRequest;
import mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest;
import mt4_term_api.Mt4TermApiMarketInfo.SymbolSelectRequest;
import mt4_term_api.Mt4TermApiMarketInfo.SymbolsData;
import mt4_term_api.Mt4TermApiMarketInfo.SymbolsRequest;
import mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickData;
import mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply;
import mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest;
import mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteData;
import mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest;
import mt4_term_api.Mt4TermApiTradingHelper.OrderModifyData;
import mt4_term_api.Mt4TermApiTradingHelper.OrderModifyRequest;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendData;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendOperationType;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest;
import mt4_term_api.SubscriptionServiceGrpc;
import mt4_term_api.TradingHelperGrpc;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Thin blocking facade over the MetaRPC MT4 gRPC gateway ({@code mt4.mrpc.pro:443}).
 *
 * <p>This is the only transport that reaches MetaRPC: {@link pro.mrpc.mt4.MT4Client} in this
 * repository is a raw MT4 wire-protocol client that talks straight to a broker server and ignores
 * the API key. Everything here goes through the generated stubs in {@code package/}
 * ({@code mt4_term_api.*}), which mirror the REST surface documented at
 * {@code https://mt4.mrpc.pro/apiui}.
 *
 * <p>Every call attaches two headers: {@code id} (the terminal instance id) and {@code apikey}.
 * Pass a <b>stable</b> {@code id} per account (e.g. {@link #uuid5(UUID, String)}) so that re-runs
 * reuse the same terminal instead of orphaning a new one on the gateway.
 *
 * <pre>{@code
 * MT4Account acc = new MT4Account(420000935L, "pwd", System.getenv("MRPC_API_KEY"), "mt4.mrpc.pro:443",
 *                                 MT4Account.uuid5(NAMESPACE, "my-strategy"));
 * acc.connectByServerName("AETOS-Demo", "my-strategy", 120);
 * AccountSummaryData s = acc.accountSummary();
 * }</pre>
 */
public class MT4Account implements AutoCloseable {
    public static final String DEFAULT_GRPC_TARGET = "mt4.mrpc.pro:443";
    public static final long DEFAULT_DEADLINE_SECONDS = 90;

    private static final Metadata.Key<String> ID_KEY = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> APIKEY_KEY = Metadata.Key.of("apikey", Metadata.ASCII_STRING_MARSHALLER);

    private final long user;
    private final String password;
    private final String apiKey;
    private final String target;
    private final UUID id;
    private final ManagedChannel channel;

    private final ConnectionGrpc.ConnectionBlockingStub connection;
    private final AccountHelperGrpc.AccountHelperBlockingStub accountHelper;
    private final MarketInfoGrpc.MarketInfoBlockingStub marketInfo;
    private final TradingHelperGrpc.TradingHelperBlockingStub tradingHelper;
    private final SubscriptionServiceGrpc.SubscriptionServiceStub subscriptions;

    private volatile String terminalInstanceGuid;
    private volatile String serverName;
    private volatile String terminalName;
    private volatile long deadlineSeconds = DEFAULT_DEADLINE_SECONDS;
    private volatile AccountSummaryData lastSummary;
    private volatile int utcServerTimeShiftMinutes;

    /** Connects to {@link #DEFAULT_GRPC_TARGET} with a random terminal id. Prefer the 5-arg form with a stable id. */
    public MT4Account(long user, String password, String apiKey) {
        this(user, password, apiKey, DEFAULT_GRPC_TARGET, UUID.randomUUID());
    }

    /**
     * @param user     MT4 login
     * @param password MT4 password (master or investor)
     * @param apiKey   MetaRPC API key; null falls back to {@code MRPC_API_KEY} env, then "TRIAL"
     * @param target   gRPC target, e.g. {@code mt4.mrpc.pro:443} (TLS is used unless the host is localhost/127.0.0.1)
     * @param id       stable terminal instance id for this account (see {@link #uuid5(UUID, String)})
     */
    public MT4Account(long user, String password, String apiKey, String target, UUID id) {
        this.user = user;
        this.password = password;
        String k = apiKey;
        if (k == null || k.isBlank()) k = System.getenv("MRPC_API_KEY");
        if (k == null || k.isBlank()) k = "TRIAL";
        this.apiKey = k;
        this.target = target == null ? DEFAULT_GRPC_TARGET : target;
        this.id = id == null ? UUID.randomUUID() : id;

        ManagedChannelBuilder<?> b = ManagedChannelBuilder.forTarget(this.target)
                .keepAliveTime(30, TimeUnit.SECONDS)
                .keepAliveTimeout(20, TimeUnit.SECONDS)
                .keepAliveWithoutCalls(true)
                .maxInboundMessageSize(64 * 1024 * 1024);
        if (this.target.startsWith("localhost") || this.target.startsWith("127.0.0.1")) b.usePlaintext();
        this.channel = b.build();

        this.connection = ConnectionGrpc.newBlockingStub(channel);
        this.accountHelper = AccountHelperGrpc.newBlockingStub(channel);
        this.marketInfo = MarketInfoGrpc.newBlockingStub(channel);
        this.tradingHelper = TradingHelperGrpc.newBlockingStub(channel);
        this.subscriptions = SubscriptionServiceGrpc.newStub(channel);
    }

    // ------------------------------------------------------------------ identity / headers

    public long getUser() { return user; }
    public UUID getId() { return id; }
    public String getApiKey() { return apiKey; }
    public String getTarget() { return target; }
    public String getServerName() { return serverName; }
    public String getTerminalName() { return terminalName; }
    /** The id the gateway returned from ConnectEx (e.g. {@code mt4_live_<hex>}); null before connect. */
    public String getTerminalInstanceGuid() { return terminalInstanceGuid; }
    public boolean isConnected() { return terminalInstanceGuid != null; }
    public ManagedChannel getChannel() { return channel; }
    public void setDeadlineSeconds(long seconds) { this.deadlineSeconds = seconds; }

    /** Headers for every call: {@code id} + {@code apikey}. Both the uuid we send and the gateway's prefixed guid are accepted. */
    public Metadata headers() {
        Metadata md = new Metadata();
        md.put(ID_KEY, terminalInstanceGuid != null ? terminalInstanceGuid : id.toString());
        md.put(APIKEY_KEY, apiKey);
        return md;
    }

    private ClientInterceptor hdr() { return MetadataUtils.newAttachHeadersInterceptor(headers()); }

    private <T extends io.grpc.stub.AbstractStub<T>> T call(T stub) {
        return stub.withInterceptors(hdr()).withDeadlineAfter(deadlineSeconds, TimeUnit.SECONDS);
    }

    // ------------------------------------------------------------------ connection

    /**
     * ConnectEx: the gateway resolves {@code mtClusterName} (the broker server name as shown in the MT4
     * terminal, e.g. {@code AETOS-Demo}) and spins up / reuses a terminal for this {@code id}.
     * Typically 2-60 s on a cold terminal; use a generous timeout.
     *
     * @param mtClusterName broker server name
     * @param name          optional human-readable label shown by {@code GET /Accounts}
     * @param timeoutSeconds how long the gateway may take to bring the terminal up
     * @return the gateway's terminal instance guid
     * @throws ApiExceptionMT4 on a gateway error. If {@link ApiExceptionMT4#isInvalidLogin()} is true, never retry.
     */
    public String connectByServerName(String mtClusterName, String name, int timeoutSeconds) {
        ConnectExRequest.Builder rb = ConnectExRequest.newBuilder()
                .setUser(user)
                .setPassword(password)
                .setMtClusterName(mtClusterName)
                .setTimeoutSeconds(timeoutSeconds);
        if (name != null && !name.isEmpty()) rb.setName(name);
        ConnectExReply reply = connection.withInterceptors(hdr())
                .withDeadlineAfter(timeoutSeconds + 60L, TimeUnit.SECONDS)
                .connectEx(rb.build());
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        this.terminalInstanceGuid = reply.getData().getTerminalInstanceGuid();
        this.serverName = mtClusterName;
        this.terminalName = name;
        accountSummary(); // learn the broker clock offset (see toServerTime) and the trade mode
        return terminalInstanceGuid;
    }

    public String connectByServerName(String mtClusterName) { return connectByServerName(mtClusterName, null, 120); }

    /** Stops the terminal on the gateway. Fleet runners normally do NOT call this (prod keeps the terminal warm). */
    public void disconnect() {
        var reply = call(connection).disconnect(DisconnectRequest.newBuilder().build());
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        terminalInstanceGuid = null;
    }

    /** Closes the gRPC channel only; the gateway terminal stays alive. */
    @Override
    public void close() {
        channel.shutdown();
        try { channel.awaitTermination(5, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    // ------------------------------------------------------------------ account

    /** Also refreshes {@link #getUtcServerTimeShiftMinutes()} and {@link #isDemo()}. */
    public AccountSummaryData accountSummary() {
        var reply = call(accountHelper).accountSummary(AccountSummaryRequest.newBuilder().build());
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        lastSummary = reply.getData();
        utcServerTimeShiftMinutes = lastSummary.getUtcServerTimeShiftMinutes();
        return lastSummary;
    }

    /** Last AccountSummary reply (null before connect). */
    public AccountSummaryData getLastSummary() { return lastSummary; }
    public boolean isDemo() {
        return lastSummary != null && lastSummary.getAccountTradeMode() == mt4_term_api.Mt4TermApiAccountHelper.EnumAccountTradeMode.ACCOUNT_TRADE_MODE_DEMO;
    }

    // ------------------------------------------------------------------ broker clock
    //
    // EVERY google.protobuf.Timestamp the MT4 gateway returns (server_time, open_time, close_time,
    // bar time, tick time) and every Timestamp it accepts (OrdersHistory from/to, QuoteHistory
    // from/to) is BROKER-LOCAL time labelled as if it were UTC. AccountSummary reports the offset in
    // utc_server_time_shift_minutes (e.g. 180 for a UTC+3 broker). The helpers below convert.

    /** Broker clock offset from UTC in minutes (e.g. 180). Known after connect / accountSummary(). */
    public int getUtcServerTimeShiftMinutes() { return utcServerTimeShiftMinutes; }

    /** UTC instant to a broker-time Timestamp, as the gateway expects it in request windows. */
    public Timestamp toServerTime(Instant utc) { return ts(utc.plusSeconds(utcServerTimeShiftMinutes * 60L)); }

    /** Gateway Timestamp (broker time) to a real UTC instant. */
    public Instant toUtc(Timestamp brokerTime) { return instant(brokerTime).minusSeconds(utcServerTimeShiftMinutes * 60L); }

    /** Current broker time as an Instant (comparable to the gateway's timestamps). */
    public Instant serverNow() { return Instant.now().plusSeconds(utcServerTimeShiftMinutes * 60L); }

    public OpenedOrdersData openedOrders(EnumOpenedOrderSortType sort) {
        var reply = call(accountHelper).openedOrders(OpenedOrdersRequest.newBuilder().setSortType(sort).build());
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        return reply.getData();
    }

    public List<OpenedOrderInfo> openedOrders() {
        return openedOrders(EnumOpenedOrderSortType.forNumber(0)).getOrderInfosList();
    }

    /**
     * Closed orders (and balance operations, order_type 6) whose close time lies in [fromUtc, toUtc].
     * The window is converted to broker time for you (see {@link #toServerTime}); the returned
     * open_time/close_time are broker time, use {@link #toUtc(Timestamp)}.
     * {@code pageNumber} starts at 0; {@code itemsPerPage} 0 = server default.
     */
    public OrdersHistoryData ordersHistory(EnumOrderHistorySortType sort, Instant fromUtc, Instant toUtc, int pageNumber, int itemsPerPage) {
        return ordersHistoryServerTime(sort, toServerTime(fromUtc), toServerTime(toUtc), pageNumber, itemsPerPage);
    }

    /** Raw variant: {@code from}/{@code to} are already broker-time Timestamps. */
    public OrdersHistoryData ordersHistoryServerTime(EnumOrderHistorySortType sort, Timestamp from, Timestamp to, int pageNumber, int itemsPerPage) {
        OrdersHistoryRequest req = OrdersHistoryRequest.newBuilder()
                .setInputSortMode(sort)
                .setInputFrom(from)
                .setInputTo(to)
                .setPageNumber(pageNumber)
                .setItemsPerPage(itemsPerPage)
                .build();
        var reply = call(accountHelper).ordersHistory(req);
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        return reply.getData();
    }

    // ------------------------------------------------------------------ symbols / quotes

    /** All symbol names the account can see (not only Market Watch). */
    public SymbolsData symbols() {
        var reply = call(marketInfo).symbols(SymbolsRequest.newBuilder().build());
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        return reply.getData();
    }

    /** Adds (select=true) / removes a symbol in Market Watch. Fails with mql 4220 if the name does not exist. */
    public boolean symbolSelect(String symbol, boolean select) {
        var reply = call(marketInfo).symbolSelect(SymbolSelectRequest.newBuilder().setSymbol(symbol).setSelect(select).build());
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        return reply.getData().getSuccess();
    }

    /** Contract specs. {@code symbol == null} returns every symbol (slow: one broker round-trip per symbol). */
    public SymbolParamsManyData symbolParamsMany(String symbol) {
        SymbolParamsManyRequest.Builder b = SymbolParamsManyRequest.newBuilder();
        if (symbol != null && !symbol.isEmpty()) b.setSymbolName(symbol);
        var reply = call(accountHelper).symbolParamsMany(b.build());
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        return reply.getData();
    }

    /** Spec of one symbol, or null if the gateway returned nothing for it. */
    public SymbolParamsManyInfo symbolParams(String symbol) {
        List<SymbolParamsManyInfo> l = symbolParamsMany(symbol).getSymbolInfosList();
        for (SymbolParamsManyInfo i : l) if (symbol.equals(i.getSymbolName())) return i;
        return l.isEmpty() ? null : l.get(0);
    }

    /** Current bid/ask (plus day high/low). Prices for trading come from here, not from SymbolParamsMany. */
    public QuoteData quote(String symbol) {
        var reply = call(marketInfo).quote(QuoteRequest.newBuilder().setSymbol(symbol).build());
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        return reply.getData();
    }

    /**
     * Bars of {@code timeframe} whose open time lies in [fromUtc, toUtc] (converted to broker time for you).
     * The gateway returns them NEWEST FIRST; bar {@code time} is broker time ({@link #toUtc(Timestamp)}).
     */
    public List<HistoryQuote> quoteHistory(String symbol, ENUM_QUOTE_HISTORY_TIMEFRAME timeframe, Instant fromUtc, Instant toUtc) {
        return quoteHistoryServerTime(symbol, timeframe, toServerTime(fromUtc), toServerTime(toUtc));
    }

    /** Raw variant: {@code from}/{@code to} are already broker-time Timestamps. Newest bar first. */
    public List<HistoryQuote> quoteHistoryServerTime(String symbol, ENUM_QUOTE_HISTORY_TIMEFRAME timeframe, Timestamp from, Timestamp to) {
        QuoteHistoryRequest req = QuoteHistoryRequest.newBuilder()
                .setSymbol(symbol).setTimeframe(timeframe).setFromTime(from).setToTime(to).build();
        var reply = call(marketInfo).quoteHistory(req);
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        return reply.getData().getHistoricalQuotesList();
    }

    // ------------------------------------------------------------------ streaming

    /**
     * Raw server-streaming subscription: the gateway pushes a reply per tick for any of {@code symbols}.
     * The returned observer lets you {@code cancel()} the stream. No deadline is applied.
     */
    public ClientCallStreamObserver<OnSymbolTickRequest> onSymbolTick(Collection<String> symbols, StreamObserver<OnSymbolTickReply> observer) {
        OnSymbolTickRequest req = OnSymbolTickRequest.newBuilder().addAllSymbolNames(symbols).build();
        AtomicReference<ClientCallStreamObserver<OnSymbolTickRequest>> ref = new AtomicReference<>();
        subscriptions.withInterceptors(hdr()).onSymbolTick(req, new ClientResponseObserver<OnSymbolTickRequest, OnSymbolTickReply>() {
            @Override public void beforeStart(ClientCallStreamObserver<OnSymbolTickRequest> rs) { ref.set(rs); }
            @Override public void onNext(OnSymbolTickReply v) { observer.onNext(v); }
            @Override public void onError(Throwable t) { observer.onError(t); }
            @Override public void onCompleted() { observer.onCompleted(); }
        });
        return ref.get();
    }

    /** Pull-style tick subscription backed by a queue. Call {@link TickSubscription#cancel()} when done. */
    public TickSubscription subscribeTicks(Collection<String> symbols) {
        TickSubscription sub = new TickSubscription();
        sub.call = onSymbolTick(symbols, new StreamObserver<>() {
            @Override public void onNext(OnSymbolTickReply r) {
                if (r.hasError()) { sub.error = new ApiExceptionMT4(r.getError()); sub.done = true; return; }
                sub.queue.offer(r.getData());
            }
            @Override public void onError(Throwable t) {
                if (!(t instanceof StatusRuntimeException sre && sre.getStatus().getCode() == io.grpc.Status.Code.CANCELLED)) sub.error = t;
                sub.done = true;
            }
            @Override public void onCompleted() { sub.done = true; }
        });
        return sub;
    }

    public static final class TickSubscription {
        private final BlockingQueue<OnSymbolTickData> queue = new LinkedBlockingQueue<>();
        private volatile ClientCallStreamObserver<OnSymbolTickRequest> call;
        private volatile Throwable error;
        private volatile boolean done;

        /** Next tick or null on timeout. */
        public OnSymbolTickData poll(long timeout, TimeUnit unit) throws InterruptedException { return queue.poll(timeout, unit); }
        public boolean isDone() { return done; }
        public Throwable getError() { return error; }
        public void cancel() {
            ClientCallStreamObserver<OnSymbolTickRequest> c = call;
            if (c != null) c.cancel("client cancelled", null);
            done = true;
        }
    }

    // ------------------------------------------------------------------ trading

    public OrderSendData orderSend(OrderSendRequest request) {
        var reply = call(tradingHelper).orderSend(request);
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        return reply.getData();
    }

    /**
     * Market/pending order. For market orders pass the current ask (buy) / bid (sell) as {@code price};
     * {@code slippage} is in points. {@code sl}/{@code tp} 0 = none.
     */
    public OrderSendData orderSend(String symbol, OrderSendOperationType type, double volume, double price, int slippage,
                                   double sl, double tp, String comment, int magic) {
        OrderSendRequest.Builder b = OrderSendRequest.newBuilder()
                .setSymbol(symbol).setOperationType(type).setVolume(volume).setPrice(price).setSlippage(slippage)
                .setStoploss(sl).setTakeprofit(tp).setMagicNumber(magic);
        if (comment != null) b.setComment(comment);
        return orderSend(b.build());
    }

    public OrderModifyData orderModify(OrderModifyRequest request) {
        var reply = call(tradingHelper).orderModify(request);
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        return reply.getData();
    }

    /** Modify SL/TP (and price for pendings) of an order. For an open position pass its open price as {@code newPrice}. */
    public OrderModifyData orderModify(int ticket, double newPrice, double newSl, double newTp) {
        return orderModify(OrderModifyRequest.newBuilder()
                .setOrderTicket(ticket).setNewPrice(newPrice).setNewStopLoss(newSl).setNewTakeProfit(newTp).build());
    }

    public OrderCloseDeleteData orderCloseDelete(OrderCloseDeleteRequest request) {
        var reply = call(tradingHelper).orderCloseDelete(request);
        if (reply.hasError()) throw new ApiExceptionMT4(reply.getError());
        return reply.getData();
    }

    /** Close an open position (lots, closingPrice = bid for a buy / ask for a sell) or delete a pending (lots/price ignored). */
    public OrderCloseDeleteData orderCloseDelete(int ticket, double lots, double closingPrice, int slippage) {
        return orderCloseDelete(OrderCloseDeleteRequest.newBuilder()
                .setOrderTicket(ticket).setLots(lots).setClosingPrice(closingPrice).setSlippage(slippage).build());
    }

    // ------------------------------------------------------------------ helpers

    public static Timestamp ts(Instant i) {
        return Timestamp.newBuilder().setSeconds(i.getEpochSecond()).setNanos(i.getNano()).build();
    }

    public static Instant instant(Timestamp t) {
        return Instant.ofEpochSecond(t.getSeconds(), t.getNanos());
    }

    /** RFC 4122 name-based UUID v5 (SHA-1), same bytes as Python's uuid.uuid5 / Go's uuid.NewSHA1. */
    public static UUID uuid5(UUID namespace, String name) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(toBytes(namespace));
            md.update(name.getBytes(StandardCharsets.UTF_8));
            byte[] h = md.digest();
            h[6] = (byte) ((h[6] & 0x0f) | 0x50);
            h[8] = (byte) ((h[8] & 0x3f) | 0x80);
            long msb = 0, lsb = 0;
            for (int i = 0; i < 8; i++) msb = (msb << 8) | (h[i] & 0xff);
            for (int i = 8; i < 16; i++) lsb = (lsb << 8) | (h[i] & 0xff);
            return new UUID(msb, lsb);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static byte[] toBytes(UUID u) {
        byte[] b = new byte[16];
        long msb = u.getMostSignificantBits(), lsb = u.getLeastSignificantBits();
        for (int i = 0; i < 8; i++) b[i] = (byte) (msb >>> (8 * (7 - i)));
        for (int i = 8; i < 16; i++) b[i] = (byte) (lsb >>> (8 * (15 - i)));
        return b;
    }
}
