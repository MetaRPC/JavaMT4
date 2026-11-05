package pro.mrpc.mt4;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Message;
import com.google.protobuf.Timestamp;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.TlsChannelCredentials;
import io.grpc.stub.AbstractStub;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import mt4_term_api.AccountHelperGrpc;
import mt4_term_api.ConnectionGrpc;
import mt4_term_api.MarketInfoGrpc;
import mt4_term_api.MrpcMt4Error;
import mt4_term_api.Mt4TermApiAccountHelper;
import mt4_term_api.Mt4TermApiConnection;
import mt4_term_api.Mt4TermApiMarketInfo;
import mt4_term_api.Mt4TermApiServerTimezone;
import mt4_term_api.Mt4TermApiSubscriptions;
import mt4_term_api.Mt4TermApiTradeStats;
import mt4_term_api.Mt4TermApiTradingHelper;
import mt4_term_api.ServerTimezoneGrpc;
import mt4_term_api.SubscriptionServiceGrpc;
import mt4_term_api.TradeStatsGrpc;
import mt4_term_api.TradingHelperGrpc;

import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.concurrent.TimeUnit;

/**
 * Low-level MT4 account over the MRPC gRPC API ({@code mt4.mrpc.pro:443}, TLS). A faithful Java port of the
 * Python {@code MetaRpcMT4.MT4Account}: the same method set, the same automatic-reconnect behaviour, and the
 * same "return the reply's data or throw {@link ApiExceptionMT4}" contract.
 *
 * <pre>{@code
 * try (MT4Account account = new MT4Account(500123456L, "password", null, null, "TRIAL")) {
 *     account.connectByServerName("AMarkets-Demo", 30);
 *     var summary = account.accountSummary();
 *     var order   = account.orderSend("EURUSD", OrderSendOperationType.OC_OP_BUY, 0.01);
 *     account.disconnect(false);
 * }
 * }</pre>
 */
public class MT4Account implements AutoCloseable {
    public static final String DEFAULT_SERVER = "mt4.mrpc.pro:443";

    private static final Metadata.Key<String> API_KEY = Metadata.Key.of("apikey", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> ID = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
    private static final long CALL_TIMEOUT_MS = 30_000;
    private static final long RECONNECT_BACKOFF_MS = 500;

    private final ManagedChannel channel;
    private final long user;
    private final String password;
    private final String apiKey;

    // Connection state (mirrors the Python fields)
    private volatile String terminalId;        // self.id
    private volatile String serverName;
    private volatile String host;
    private volatile int port;
    private volatile int connectTimeoutSeconds = 30;
    private volatile String name;

    /** The MRPC MT4 API; {@code grpcServer} null → {@value #DEFAULT_SERVER}, {@code apiKey} null → {@code MRPC_API_KEY} then {@code TRIAL}. */
    public MT4Account(long user, String password, String grpcServer, String id, String apiKey) {
        this(Grpc.newChannelBuilder(grpcServer != null && !grpcServer.isBlank() ? grpcServer : DEFAULT_SERVER,
                TlsChannelCredentials.create()).maxInboundMessageSize(100 * 1024 * 1024).build(), user, password, id, apiKey);
    }

    public MT4Account(long user, String password) {
        this(user, password, null, null, null);
    }

    /** On a channel you built (it is shut down by {@link #close()}). */
    public MT4Account(ManagedChannel channel, long user, String password, String id, String apiKey) {
        this.channel = channel;
        this.user = user;
        this.password = password;
        this.terminalId = (id != null && !id.isBlank()) ? id : null;
        String key = (apiKey != null && !apiKey.isBlank()) ? apiKey : System.getenv("MRPC_API_KEY");
        this.apiKey = (key != null && !key.isBlank()) ? key.trim() : "TRIAL";
    }

    public ManagedChannel getChannel() { return channel; }
    public long getUser() { return user; }
    /** Terminal instance id (set by connect); null when not connected. */
    public String getId() { return terminalId; }
    public String getServerName() { return serverName; }

    /** Attaches to a terminal that is already connected (its id from an earlier connect). */
    public void attach(String terminalId) { this.terminalId = terminalId; }

    /** A stub carrying the {@code apikey} and {@code id} headers on every call. */
    public <S extends AbstractStub<S>> S authorized(S stub) {
        Metadata headers = new Metadata();
        headers.put(API_KEY, apiKey);
        String id = terminalId;
        if (id != null) headers.put(ID, id);
        return stub.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers));
    }

    // ===== Connection =====

    /** Connects by broker server (cluster) name, e.g. "AMarkets-Demo" (ConnectEx). Returns the terminal id. */
    public String connectByServerName(String serverName) { return connectByServerName(serverName, 30, null, null); }

    public String connectByServerName(String serverName, int timeoutSeconds) { return connectByServerName(serverName, timeoutSeconds, null, null); }

    public String connectByServerName(String serverName, int timeoutSeconds, String name, Integer expiration) {
        Mt4TermApiConnection.ConnectExRequest.Builder b = Mt4TermApiConnection.ConnectExRequest.newBuilder()
                .setUser(user).setPassword(password).setMtClusterName(serverName).setTimeoutSeconds(timeoutSeconds);
        if (name != null) { b.setName(name); this.name = name; }
        if (expiration != null) b.setExpiration(expiration);
        Mt4TermApiConnection.ConnectExReply reply = deadline(authorized(ConnectionGrpc.newBlockingStub(channel)),
                timeoutSeconds * 1000L + 30_000).connectEx(b.build());
        throwIfError(reply);
        this.serverName = serverName;
        this.connectTimeoutSeconds = timeoutSeconds;
        Mt4TermApiConnection.ConnectData data = dataOf(reply);
        if (!data.getTerminalInstanceGuid().isEmpty()) this.terminalId = data.getTerminalInstanceGuid();
        return terminalId;
    }

    /** Connects by the broker's MT4 server address host:port (Connect). Returns the terminal id. */
    public String connectByHostPort(String host, int port) { return connectByHostPort(host, port, 30, null, null); }

    public String connectByHostPort(String host, int port, int timeoutSeconds) { return connectByHostPort(host, port, timeoutSeconds, null, null); }

    public String connectByHostPort(String host, int port, int timeoutSeconds, String name, Integer expiration) {
        Mt4TermApiConnection.ConnectRequest.Builder b = Mt4TermApiConnection.ConnectRequest.newBuilder()
                .setUser(user).setPassword(password).setHost(host).setPort(port).setTimeoutSeconds(timeoutSeconds);
        if (name != null) { b.setName(name); this.name = name; }
        if (expiration != null) b.setExpiration(expiration);
        Mt4TermApiConnection.ConnectReply reply = deadline(authorized(ConnectionGrpc.newBlockingStub(channel)),
                timeoutSeconds * 1000L + 30_000).connect(b.build());
        throwIfError(reply);
        this.host = host;
        this.port = port;
        this.connectTimeoutSeconds = timeoutSeconds;
        Mt4TermApiConnection.ConnectData data = dataOf(reply);
        if (!data.getTerminalInstanceGuid().isEmpty()) this.terminalId = data.getTerminalInstanceGuid();
        return terminalId;
    }

    /** Re-establishes the session using the last successful connect (used by the auto-reconnect logic). */
    public void reconnect() {
        if (serverName != null) {
            connectByServerName(serverName, connectTimeoutSeconds, name, null);
        } else if (host != null) {
            connectByHostPort(host, port, connectTimeoutSeconds, name, null);
        }
    }

    /** Disconnects the terminal ({@code delete} also deletes it), then nothing else; {@link #close()} frees the channel. */
    public void disconnect(boolean delete) {
        try {
            Mt4TermApiConnection.DisconnectRequest request = Mt4TermApiConnection.DisconnectRequest.newBuilder().setDelete(delete).build();
            deadline(authorized(ConnectionGrpc.newBlockingStub(channel)), 10_000).disconnect(request);
        } catch (Exception ignored) {
            // disconnect is best-effort
        }
        terminalId = null;
    }

    /** Shuts the channel down. */
    @Override
    public void close() {
        channel.shutdown();
        try {
            if (!channel.awaitTermination(5, TimeUnit.SECONDS)) channel.shutdownNow();
        } catch (InterruptedException e) {
            channel.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // ===== Account helper =====

    public Mt4TermApiAccountHelper.AccountSummaryData accountSummary() {
        ensureConnected();
        Mt4TermApiAccountHelper.AccountSummaryReply reply = callWithReconnect(() ->
                acct().accountSummary(Mt4TermApiAccountHelper.AccountSummaryRequest.getDefaultInstance()));
        return dataOf(reply);
    }

    public Mt4TermApiAccountHelper.OpenedOrdersData openedOrders() {
        return openedOrders(Mt4TermApiAccountHelper.EnumOpenedOrderSortType.SORT_BY_OPEN_TIME_ASC);
    }

    public Mt4TermApiAccountHelper.OpenedOrdersData openedOrders(Mt4TermApiAccountHelper.EnumOpenedOrderSortType sortType) {
        ensureConnected();
        Mt4TermApiAccountHelper.OpenedOrdersRequest request = Mt4TermApiAccountHelper.OpenedOrdersRequest.newBuilder().setSortType(sortType).build();
        Mt4TermApiAccountHelper.OpenedOrdersReply reply = callWithReconnect(() -> acct().openedOrders(request));
        return dataOf(reply);
    }

    public Mt4TermApiAccountHelper.OpenedOrdersTicketsData openedOrdersTickets() {
        ensureConnected();
        Mt4TermApiAccountHelper.OpenedOrdersTicketsReply reply = callWithReconnect(() ->
                acct().openedOrdersTickets(Mt4TermApiAccountHelper.OpenedOrdersTicketsRequest.getDefaultInstance()));
        return dataOf(reply);
    }

    public Mt4TermApiAccountHelper.OrdersHistoryData ordersHistory() {
        return ordersHistory(Mt4TermApiAccountHelper.EnumOrderHistorySortType.HISTORY_SORT_BY_CLOSE_TIME_DESC, null, null, null, null);
    }

    public Mt4TermApiAccountHelper.OrdersHistoryData ordersHistory(
            Mt4TermApiAccountHelper.EnumOrderHistorySortType sortMode, Instant from, Instant to, Integer pageNumber, Integer itemsPerPage) {
        ensureConnected();
        Mt4TermApiAccountHelper.OrdersHistoryRequest.Builder b = Mt4TermApiAccountHelper.OrdersHistoryRequest.newBuilder().setInputSortMode(sortMode);
        if (from != null) b.setInputFrom(ts(from));
        if (to != null) b.setInputTo(ts(to));
        if (pageNumber != null) b.setPageNumber(pageNumber);
        if (itemsPerPage != null) b.setItemsPerPage(itemsPerPage);
        Mt4TermApiAccountHelper.OrdersHistoryRequest request = b.build();
        Mt4TermApiAccountHelper.OrdersHistoryReply reply = callWithReconnect(() -> acct().ordersHistory(request));
        return dataOf(reply);
    }

    public Mt4TermApiAccountHelper.SymbolParamsManyData symbolParamsMany(String symbolName) {
        ensureConnected();
        Mt4TermApiAccountHelper.SymbolParamsManyRequest request = Mt4TermApiAccountHelper.SymbolParamsManyRequest.newBuilder()
                .setSymbolName(symbolName != null ? symbolName : "").build();
        Mt4TermApiAccountHelper.SymbolParamsManyReply reply = callWithReconnect(() -> acct().symbolParamsMany(request));
        return dataOf(reply);
    }

    public Mt4TermApiAccountHelper.TickValueWithSizeData tickValueWithSize(List<String> symbolNames) {
        ensureConnected();
        Mt4TermApiAccountHelper.TickValueWithSizeRequest request = Mt4TermApiAccountHelper.TickValueWithSizeRequest.newBuilder()
                .addAllSymbolNames(symbolNames).build();
        Mt4TermApiAccountHelper.TickValueWithSizeReply reply = callWithReconnect(() -> acct().tickValueWithSize(request));
        return dataOf(reply);
    }

    // ===== Trade statistics =====

    public Mt4TermApiTradeStats.StatsData tradeStats(Instant from) {
        ensureConnected();
        Mt4TermApiTradeStats.TradeStatsRequest.Builder b = Mt4TermApiTradeStats.TradeStatsRequest.newBuilder();
        if (from != null) b.setFrom(from.toString());
        Mt4TermApiTradeStats.TradeStatsRequest request = b.build();
        Mt4TermApiTradeStats.TradeStatsReply reply = callWithReconnect(() -> stats().tradeStats(request));
        return dataOf(reply);
    }

    public Mt4TermApiTradeStats.StatsWithChartsData tradeStatsEquityHistory(Instant from, Boolean excludeSameBars) {
        ensureConnected();
        Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest.Builder b = Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest.newBuilder();
        if (from != null) b.setFrom(from.toString());
        if (excludeSameBars != null) b.setExcludeSameBars(excludeSameBars);
        Mt4TermApiTradeStats.TradeStatsEquityHistoryRequest request = b.build();
        Mt4TermApiTradeStats.TradeStatsEquityHistoryReply reply = callWithReconnect(() -> stats().tradeStatsEquityHistory(request));
        return dataOf(reply);
    }

    public Mt4TermApiTradeStats.EquityHistoryData equityHistory(Instant from, Boolean excludeSameBars) {
        ensureConnected();
        Mt4TermApiTradeStats.EquityHistoryRequest.Builder b = Mt4TermApiTradeStats.EquityHistoryRequest.newBuilder();
        if (from != null) b.setFrom(from.toString());
        if (excludeSameBars != null) b.setExcludeSameBars(excludeSameBars);
        Mt4TermApiTradeStats.EquityHistoryRequest request = b.build();
        Mt4TermApiTradeStats.EquityHistoryReply reply = callWithReconnect(() -> stats().equityHistory(request));
        return dataOf(reply);
    }

    public Mt4TermApiTradeStats.TradeUnrealizedPLData tradeUnrealizedPl(Instant from) {
        ensureConnected();
        Mt4TermApiTradeStats.TradeUnrealizedPLRequest.Builder b = Mt4TermApiTradeStats.TradeUnrealizedPLRequest.newBuilder();
        if (from != null) b.setFrom(from.toString());
        Mt4TermApiTradeStats.TradeUnrealizedPLRequest request = b.build();
        Mt4TermApiTradeStats.TradeUnrealizedPLReply reply = callWithReconnect(() -> stats().tradeUnrealizedPL(request));
        return dataOf(reply);
    }

    // ===== Market info =====

    public Mt4TermApiMarketInfo.QuoteData quote(String symbol) {
        ensureConnected();
        Mt4TermApiMarketInfo.QuoteRequest request = Mt4TermApiMarketInfo.QuoteRequest.newBuilder().setSymbol(symbol).build();
        Mt4TermApiMarketInfo.QuoteReply reply = callWithReconnect(() -> market().quote(request));
        return dataOf(reply);
    }

    public Mt4TermApiMarketInfo.QuoteManyData quoteMany(List<String> symbols) {
        ensureConnected();
        Mt4TermApiMarketInfo.QuoteManyRequest request = Mt4TermApiMarketInfo.QuoteManyRequest.newBuilder().addAllSymbols(symbols).build();
        Mt4TermApiMarketInfo.QuoteManyReply reply = callWithReconnect(() -> market().quoteMany(request));
        return dataOf(reply);
    }

    public Mt4TermApiMarketInfo.SymbolsData symbols() {
        ensureConnected();
        Mt4TermApiMarketInfo.SymbolsReply reply = callWithReconnect(() ->
                market().symbols(Mt4TermApiMarketInfo.SymbolsRequest.getDefaultInstance()));
        return dataOf(reply);
    }

    public Mt4TermApiMarketInfo.QuoteHistoryData quoteHistory(
            String symbol, Mt4TermApiMarketInfo.ENUM_QUOTE_HISTORY_TIMEFRAME timeframe, Instant from, Instant to) {
        ensureConnected();
        Mt4TermApiMarketInfo.QuoteHistoryRequest request = Mt4TermApiMarketInfo.QuoteHistoryRequest.newBuilder()
                .setSymbol(symbol).setTimeframe(timeframe).setFromTime(ts(from)).setToTime(ts(to)).build();
        Mt4TermApiMarketInfo.QuoteHistoryReply reply = callWithReconnect(() -> market().quoteHistory(request));
        return dataOf(reply);
    }

    public Mt4TermApiMarketInfo.SymbolSelectData symbolSelect(String symbol, boolean select) {
        ensureConnected();
        Mt4TermApiMarketInfo.SymbolSelectRequest request = Mt4TermApiMarketInfo.SymbolSelectRequest.newBuilder()
                .setSymbol(symbol).setSelect(select).build();
        Mt4TermApiMarketInfo.SymbolSelectReply reply = callWithReconnect(() -> market().symbolSelect(request));
        return dataOf(reply);
    }

    public Mt4TermApiServerTimezone.ServerTimezoneData serverTimezone() {
        ensureConnected();
        Mt4TermApiServerTimezone.ServerTimezoneReply reply = callWithReconnect(() ->
                deadline(authorized(ServerTimezoneGrpc.newBlockingStub(channel)), CALL_TIMEOUT_MS)
                        .serverTimezone(Mt4TermApiServerTimezone.ServerTimezoneRequest.getDefaultInstance()));
        return dataOf(reply);
    }

    // ===== Trading =====

    public Mt4TermApiTradingHelper.OrderSendData orderSend(Mt4TermApiTradingHelper.OrderSendRequest request) {
        ensureConnected();
        Mt4TermApiTradingHelper.OrderSendReply reply = callWithReconnect(() -> trade().orderSend(request));
        return dataOf(reply);
    }

    /** A market order (or a pending one — pass a price/stops via the full {@link #orderSend(Mt4TermApiTradingHelper.OrderSendRequest)}). */
    public Mt4TermApiTradingHelper.OrderSendData orderSend(String symbol, Mt4TermApiTradingHelper.OrderSendOperationType type, double volume) {
        return orderSend(Mt4TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol).setOperationType(type).setVolume(volume).build());
    }

    public Mt4TermApiTradingHelper.OrderModifyData orderModify(
            int orderTicket, Double newPrice, Double newStopLoss, Double newTakeProfit, Instant newExpiration) {
        ensureConnected();
        Mt4TermApiTradingHelper.OrderModifyRequest.Builder b = Mt4TermApiTradingHelper.OrderModifyRequest.newBuilder().setOrderTicket(orderTicket);
        if (newPrice != null) b.setNewPrice(newPrice);
        if (newStopLoss != null) b.setNewStopLoss(newStopLoss);
        if (newTakeProfit != null) b.setNewTakeProfit(newTakeProfit);
        if (newExpiration != null) b.setNewExpiration(ts(newExpiration));
        Mt4TermApiTradingHelper.OrderModifyRequest request = b.build();
        Mt4TermApiTradingHelper.OrderModifyReply reply = callWithReconnect(() -> trade().orderModify(request));
        return dataOf(reply);
    }

    public Mt4TermApiTradingHelper.OrderCloseDeleteData orderCloseDelete(int orderTicket, Double lots, Double closingPrice, Integer slippage) {
        ensureConnected();
        Mt4TermApiTradingHelper.OrderCloseDeleteRequest.Builder b = Mt4TermApiTradingHelper.OrderCloseDeleteRequest.newBuilder().setOrderTicket(orderTicket);
        if (lots != null) b.setLots(lots);
        if (closingPrice != null) b.setClosingPrice(closingPrice);
        if (slippage != null) b.setSlippage(slippage);
        Mt4TermApiTradingHelper.OrderCloseDeleteRequest request = b.build();
        Mt4TermApiTradingHelper.OrderCloseDeleteReply reply = callWithReconnect(() -> trade().orderCloseDelete(request));
        return dataOf(reply);
    }

    public Mt4TermApiTradingHelper.OrderCloseByData orderCloseBy(int ticketToClose, int oppositeTicketClosingBy) {
        ensureConnected();
        Mt4TermApiTradingHelper.OrderCloseByRequest request = Mt4TermApiTradingHelper.OrderCloseByRequest.newBuilder()
                .setTicketToClose(ticketToClose).setOppositeTicketClosingBy(oppositeTicketClosingBy).build();
        Mt4TermApiTradingHelper.OrderCloseByReply reply = callWithReconnect(() -> trade().orderCloseBy(request));
        return dataOf(reply);
    }

    // ===== Streaming =====

    public Subscription onSymbolTick(List<String> symbols, Consumer<Mt4TermApiSubscriptions.OnSymbolTickData> onData, Consumer<Throwable> onError) {
        ensureTerminal();
        Mt4TermApiSubscriptions.OnSymbolTickRequest request = Mt4TermApiSubscriptions.OnSymbolTickRequest.newBuilder().addAllSymbolNames(symbols).build();
        return subscribe(obs -> authorized(SubscriptionServiceGrpc.newStub(channel)).onSymbolTick(request, obs), onData, onError);
    }

    public Subscription onTrade(Consumer<Mt4TermApiSubscriptions.OnTradeData> onData, Consumer<Throwable> onError) {
        ensureTerminal();
        Mt4TermApiSubscriptions.OnTradeRequest request = Mt4TermApiSubscriptions.OnTradeRequest.getDefaultInstance();
        return subscribe(obs -> authorized(SubscriptionServiceGrpc.newStub(channel)).onTrade(request, obs), onData, onError);
    }

    public Subscription onOpenedOrdersTickets(int pullIntervalMilliseconds,
            Consumer<Mt4TermApiSubscriptions.OnOpenedOrdersTicketsData> onData, Consumer<Throwable> onError) {
        ensureTerminal();
        Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest request = Mt4TermApiSubscriptions.OnOpenedOrdersTicketsRequest.newBuilder()
                .setPullIntervalMilliseconds(pullIntervalMilliseconds).build();
        return subscribe(obs -> authorized(SubscriptionServiceGrpc.newStub(channel)).onOpenedOrdersTickets(request, obs), onData, onError);
    }

    public Subscription onOpenedOrdersProfit(int timerPeriodMilliseconds,
            Consumer<Mt4TermApiSubscriptions.OnOpenedOrdersProfitData> onData, Consumer<Throwable> onError) {
        ensureTerminal();
        Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest request = Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest.newBuilder()
                .setTimerPeriodMilliseconds(timerPeriodMilliseconds).build();
        return subscribe(obs -> authorized(SubscriptionServiceGrpc.newStub(channel)).onOpenedOrdersProfit(request, obs), onData, onError);
    }

    public Subscription onConnectState(List<String> terminalIds,
            Consumer<Mt4TermApiConnection.ConnectStateData> onData, Consumer<Throwable> onError) {
        ensureTerminal();
        Mt4TermApiConnection.OnConnectStateRequest.Builder b = Mt4TermApiConnection.OnConnectStateRequest.newBuilder();
        if (terminalIds != null) b.addAllTerminalIds(terminalIds);
        Mt4TermApiConnection.OnConnectStateRequest request = b.build();
        return subscribe(obs -> authorized(ConnectionGrpc.newStub(channel)).onConnectState(request, obs), onData, onError);
    }

    // ===== Infrastructure =====

    @FunctionalInterface
    private interface StreamOpener {
        @SuppressWarnings("rawtypes")
        void open(StreamObserver observer);
    }

    @SuppressWarnings("unchecked")
    private <D> Subscription subscribe(StreamOpener opener, Consumer<D> onData, Consumer<Throwable> onError) {
        Subscription sub = new Subscription(opener, (Consumer<Object>) onData, onError);
        sub.open();
        return sub;
    }

    /** A running server stream; {@link #close()} cancels it. Re-opens itself on recoverable errors (like the Python port). */
    public final class Subscription implements ClientResponseObserver<Message, Message>, AutoCloseable {
        private final StreamOpener opener;
        private final Consumer<Object> onData;
        private final Consumer<Throwable> onError;
        private volatile ClientCallStreamObserver<Message> call;
        private volatile boolean closed;

        private Subscription(StreamOpener opener, Consumer<Object> onData, Consumer<Throwable> onError) {
            this.opener = opener;
            this.onData = onData;
            this.onError = onError;
        }

        @SuppressWarnings("unchecked")
        void open() {
            opener.open((StreamObserver) this);
        }

        @Override
        public void beforeStart(ClientCallStreamObserver<Message> requestStream) {
            this.call = requestStream;
        }

        @Override
        public void onNext(Message reply) {
            if (closed) return;
            MrpcMt4Error.Error error = errorOf(reply);
            if (error != null) {
                String code = error.getErrorCode();
                if (isReconnectTrigger(code)) {
                    reopenAfterReconnect();
                    return;
                }
                if ((error.getErrorMessage() != null && !error.getErrorMessage().isEmpty()) || (code != null && !code.isEmpty())) {
                    onError.accept(new ApiExceptionMT4(error));
                    return;
                }
            }
            FieldDescriptor data = reply.getDescriptorForType().findFieldByName("data");
            if (data != null && reply.hasField(data)) onData.accept(reply.getField(data));
        }

        @Override
        public void onError(Throwable t) {
            if (closed) {
                if (Status.fromThrowable(t).getCode() == Status.Code.CANCELLED) return;
            }
            if (Status.fromThrowable(t).getCode() == Status.Code.UNAVAILABLE) {
                reopenAfterReconnect();
                return;
            }
            onError.accept(t);
        }

        @Override
        public void onCompleted() {
        }

        private void reopenAfterReconnect() {
            if (closed) return;
            try {
                Thread.sleep(RECONNECT_BACKOFF_MS);
                reconnect();
                open();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                onError.accept(e);
            }
        }

        @Override
        public void close() {
            closed = true;
            ClientCallStreamObserver<Message> c = call;
            if (c != null) c.cancel("unsubscribed", null);
        }
    }

    private AccountHelperGrpc.AccountHelperBlockingStub acct() {
        return deadline(authorized(AccountHelperGrpc.newBlockingStub(channel)), CALL_TIMEOUT_MS);
    }

    private TradingHelperGrpc.TradingHelperBlockingStub trade() {
        return deadline(authorized(TradingHelperGrpc.newBlockingStub(channel)), CALL_TIMEOUT_MS);
    }

    private MarketInfoGrpc.MarketInfoBlockingStub market() {
        return deadline(authorized(MarketInfoGrpc.newBlockingStub(channel)), CALL_TIMEOUT_MS);
    }

    private TradeStatsGrpc.TradeStatsBlockingStub stats() {
        return deadline(authorized(TradeStatsGrpc.newBlockingStub(channel)), CALL_TIMEOUT_MS);
    }

    private static <S extends AbstractStub<S>> S deadline(S stub, long timeoutMs) {
        return stub.withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS);
    }

    private void ensureConnected() {
        if (host == null && serverName == null && terminalId == null) {
            throw new ConnectExceptionMT4("Please call a connect method first");
        }
    }

    private void ensureTerminal() {
        if (terminalId == null) throw new ConnectExceptionMT4("Please call a connect method first");
    }

    private static boolean isReconnectTrigger(String code) {
        return "TERMINAL_INSTANCE_NOT_FOUND".equals(code) || "TERMINAL_REGISTRY_TERMINAL_NOT_FOUND".equals(code);
    }

    /** Runs a unary call, reconnecting on UNAVAILABLE or a missing-terminal error; throws {@link ApiExceptionMT4} on any other server error. */
    private <R extends Message> R callWithReconnect(Supplier<R> rpc) {
        while (true) {
            R res;
            try {
                res = rpc.get();
            } catch (StatusRuntimeException e) {
                if (e.getStatus().getCode() == Status.Code.UNAVAILABLE) {
                    sleep(RECONNECT_BACKOFF_MS);
                    reconnect();
                    continue;
                }
                throw new ApiExceptionMT4(e.getStatus().getCode().name() + ": " + e.getStatus().getDescription(), e);
            }
            MrpcMt4Error.Error error = errorOf(res);
            if (error != null) {
                String code = error.getErrorCode();
                if (isReconnectTrigger(code)) {
                    sleep(RECONNECT_BACKOFF_MS);
                    reconnect();
                    continue;
                }
                if ((error.getErrorMessage() != null && !error.getErrorMessage().isEmpty()) || (code != null && !code.isEmpty())) {
                    throw new ApiExceptionMT4(error);
                }
            }
            return res;
        }
    }

    private static MrpcMt4Error.Error errorOf(Message reply) {
        FieldDescriptor f = reply.getDescriptorForType().findFieldByName("error");
        if (f != null && reply.hasField(f)) return (MrpcMt4Error.Error) reply.getField(f);
        return null;
    }

    private static void throwIfError(Message reply) {
        MrpcMt4Error.Error error = errorOf(reply);
        if (error != null && error.getErrorMessage() != null && !error.getErrorMessage().isEmpty()) {
            throw new ApiExceptionMT4(error);
        }
    }

    @SuppressWarnings("unchecked")
    private static <D> D dataOf(Message reply) {
        FieldDescriptor f = reply.getDescriptorForType().findFieldByName("data");
        if (f == null || !reply.hasField(f)) {
            throw new ApiExceptionMT4("NO_DATA: " + reply.getDescriptorForType().getName() + " has neither data nor error");
        }
        return (D) reply.getField(f);
    }

    private static Timestamp ts(Instant instant) {
        return Timestamp.newBuilder().setSeconds(instant.getEpochSecond()).setNanos(instant.getNano()).build();
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
