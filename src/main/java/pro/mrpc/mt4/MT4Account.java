package pro.mrpc.mt4;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Message;
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
import mt4_term_api.AccountHelperGrpc;
import mt4_term_api.ConnectionGrpc;
import mt4_term_api.MarketInfoGrpc;
import mt4_term_api.MrpcMt4Error;
import mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryData;
import mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest;
import mt4_term_api.Mt4TermApiConnection.ConnectData;
import mt4_term_api.Mt4TermApiConnection.ConnectExRequest;
import mt4_term_api.Mt4TermApiConnection.ConnectRequest;
import mt4_term_api.Mt4TermApiConnection.DisconnectRequest;
import mt4_term_api.Mt4TermApiConnection.GetIdData;
import mt4_term_api.Mt4TermApiConnection.GetIdRequest;
import mt4_term_api.Mt4TermApiMarketInfo.QuoteData;
import mt4_term_api.Mt4TermApiMarketInfo.QuoteRequest;
import mt4_term_api.Mt4TermApiSubscriptions.OnSymbolMqlTickInfo;
import mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply;
import mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest;
import mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteData;
import mt4_term_api.Mt4TermApiTradingHelper.OrderCloseDeleteRequest;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendData;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendOperationType;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendRequest;
import mt4_term_api.SubscriptionServiceGrpc;
import mt4_term_api.TradingHelperGrpc;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * An MT4 account through the MRPC API ({@code mt4.mrpc.pro:443}, gRPC over TLS).
 *
 * <pre>{@code
 * try (MT4Account account = new MT4Account(apiKey)) {
 *     account.connect(login, password, "TeleTrade-Demo", 120);
 *     AccountSummaryData summary = account.accountSummary();
 *     OrderSendData order = account.orderSend("EURUSD", OrderSendOperationType.OC_OP_BUY, 0.01);
 *     account.disconnect(false);
 * }
 * }</pre>
 *
 * Every call returns the reply's data or throws {@link MrpcException} with the error the server answered with (or the
 * gRPC status of a failed call). Any other method of the API is reachable through the generated stubs:
 * {@code account.authorized(TradeStatsGrpc.newBlockingStub(account.getChannel()))} sends the API key and terminal id.
 */
public class MT4Account implements AutoCloseable {
    public static final String DEFAULT_SERVER = "mt4.mrpc.pro:443";

    private static final Metadata.Key<String> API_KEY = Metadata.Key.of("apikey", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> ID = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
    private static final long CALL_TIMEOUT_MS = 30_000;

    private final ManagedChannel channel;
    private final String apiKey;
    private volatile String terminalId;

    /** The MRPC MT4 API at {@value #DEFAULT_SERVER}; {@code apiKey} null means {@code MRPC_API_KEY}, then {@code TRIAL}. */
    public MT4Account(String apiKey) {
        this(DEFAULT_SERVER, apiKey);
    }

    /** @param grpcServer host:port of the API, e.g. {@value #DEFAULT_SERVER} (TLS) */
    public MT4Account(String grpcServer, String apiKey) {
        this(Grpc.newChannelBuilder(grpcServer, TlsChannelCredentials.create()).maxInboundMessageSize(100 * 1024 * 1024).build(), apiKey);
    }

    /** On a channel you built (it is shut down by {@link #close()}). */
    public MT4Account(ManagedChannel channel, String apiKey) {
        this.channel = channel;
        String key = apiKey != null && !apiKey.isBlank() ? apiKey : System.getenv("MRPC_API_KEY");
        this.apiKey = key != null && !key.isBlank() ? key.trim() : "TRIAL";
    }

    public ManagedChannel getChannel() {
        return channel;
    }

    /** The terminal id: set by {@link #connect}, {@link #getId} or {@link #attach}; null when there is none. */
    public String getTerminalId() {
        return terminalId;
    }

    /** Uses a terminal that is already connected (its id from an earlier {@link #connect}). */
    public void attach(String terminalId) {
        this.terminalId = terminalId;
    }

    /** The stub with the API key and the terminal id (once there is one) in the metadata of every call. */
    public <S extends AbstractStub<S>> S authorized(S stub) {
        Metadata headers = new Metadata();
        headers.put(API_KEY, apiKey);
        String id = terminalId;
        if (id != null) headers.put(ID, id);
        return stub.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers));
    }

    /** Connects by broker server name (ConnectEx), e.g. "TeleTrade-Demo". Returns the terminal id. */
    public String connect(long user, String password, String serverName, int timeoutSeconds) throws MrpcException {
        ConnectExRequest request = ConnectExRequest.newBuilder()
                .setUser(user).setPassword(password).setMtClusterName(serverName).setTimeoutSeconds(timeoutSeconds).build();
        ConnectData data = data(call(() -> connection(timeoutSeconds * 1000L + 10_000).connectEx(request)));
        return connected(data);
    }

    /** Connects by the address of the broker's MT4 server (Connect). Returns the terminal id. */
    public String connect(long user, String password, String host, int port, int timeoutSeconds) throws MrpcException {
        ConnectRequest request = ConnectRequest.newBuilder()
                .setUser(user).setPassword(password).setHost(host).setPort(port).setTimeoutSeconds(timeoutSeconds).build();
        ConnectData data = data(call(() -> connection(timeoutSeconds * 1000L + 10_000).connect(request)));
        return connected(data);
    }

    private String connected(ConnectData data) throws MrpcException {
        if (data.getTerminalInstanceGuid().isEmpty()) throw new MrpcException("NO_TERMINAL_ID", "connect returned no terminal id");
        terminalId = data.getTerminalInstanceGuid();
        return terminalId;
    }

    /** The terminal id the server assigns to these credentials (GetId); a following {@link #connect} uses it. */
    public String getId(long user, String password) throws MrpcException {
        GetIdRequest request = GetIdRequest.newBuilder().setUser(Long.toString(user)).setPassword(password).build();
        GetIdData data = data(call(() -> connection(CALL_TIMEOUT_MS).getId(request)));
        if (data.getId().isEmpty()) throw new MrpcException("NO_TERMINAL_ID", "GetId returned no id");
        terminalId = data.getId();
        return terminalId;
    }

    public AccountSummaryData accountSummary() throws MrpcException {
        return data(call(() -> deadline(authorized(AccountHelperGrpc.newBlockingStub(channel)), CALL_TIMEOUT_MS)
                .accountSummary(AccountSummaryRequest.getDefaultInstance())));
    }

    /** The current bid/ask of a symbol (MarketInfo.Quote). */
    public QuoteData quote(String symbol) throws MrpcException {
        QuoteRequest request = QuoteRequest.newBuilder().setSymbol(symbol).build();
        return data(call(() -> deadline(authorized(MarketInfoGrpc.newBlockingStub(channel)), CALL_TIMEOUT_MS).quote(request)));
    }

    public OrderSendData orderSend(OrderSendRequest request) throws MrpcException {
        return data(call(() -> deadline(authorized(TradingHelperGrpc.newBlockingStub(channel)), CALL_TIMEOUT_MS).orderSend(request)));
    }

    /** A market order (or a pending one with {@link #orderSend(OrderSendRequest)} and a price). */
    public OrderSendData orderSend(String symbol, OrderSendOperationType type, double lots) throws MrpcException {
        return orderSend(OrderSendRequest.newBuilder().setSymbol(symbol).setOperationType(type).setVolume(lots).build());
    }

    /** Closes a market order or deletes a pending one (OrderCloseDelete). */
    public OrderCloseDeleteData orderClose(int ticket) throws MrpcException {
        OrderCloseDeleteRequest request = OrderCloseDeleteRequest.newBuilder().setOrderTicket(ticket).build();
        return data(call(() -> deadline(authorized(TradingHelperGrpc.newBlockingStub(channel)), CALL_TIMEOUT_MS).orderCloseDelete(request)));
    }

    /**
     * Streams the ticks of the symbols (OnSymbolTick) until the returned subscription is closed. {@code onError} gets an
     * error the stream answers with (an {@link MrpcException}) or the stream's failure; closing it is not an error.
     */
    public Subscription onSymbolTick(List<String> symbols, Consumer<OnSymbolMqlTickInfo> onTick, Consumer<Throwable> onError) {
        Subscription subscription = new Subscription(onTick, onError);
        authorized(SubscriptionServiceGrpc.newStub(channel))
                .onSymbolTick(OnSymbolTickRequest.newBuilder().addAllSymbolNames(symbols).build(), subscription);
        return subscription;
    }

    /** Disconnects the terminal; {@code delete} also deletes it. */
    public void disconnect(boolean delete) throws MrpcException {
        if (terminalId == null) return;
        DisconnectRequest request = DisconnectRequest.newBuilder().setDelete(delete).build();
        throwIfError(call(() -> connection(CALL_TIMEOUT_MS).disconnect(request)));
        terminalId = null;
    }

    /** Shuts the channel down (the terminal keeps running unless {@link #disconnect} was called). */
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

    /** A running tick stream; {@link #close()} cancels it. */
    public static final class Subscription implements ClientResponseObserver<OnSymbolTickRequest, OnSymbolTickReply>, AutoCloseable {
        private final Consumer<OnSymbolMqlTickInfo> onTick;
        private final Consumer<Throwable> onError;
        private volatile ClientCallStreamObserver<OnSymbolTickRequest> call;
        private volatile boolean closed;

        private Subscription(Consumer<OnSymbolMqlTickInfo> onTick, Consumer<Throwable> onError) {
            this.onTick = onTick;
            this.onError = onError;
        }

        @Override
        public void beforeStart(ClientCallStreamObserver<OnSymbolTickRequest> requestStream) {
            call = requestStream;
        }

        @Override
        public void onNext(OnSymbolTickReply reply) {
            if (closed) return;
            if (reply.hasError()) {
                onError.accept(new MrpcException(reply.getError().getErrorCode(), reply.getError().getErrorMessage()));
            } else if (reply.hasData() && reply.getData().hasSymbolTick()) {
                onTick.accept(reply.getData().getSymbolTick());
            }
        }

        @Override
        public void onError(Throwable t) {
            if (closed && Status.fromThrowable(t).getCode() == Status.Code.CANCELLED) return;
            onError.accept(t);
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void close() {
            closed = true;
            ClientCallStreamObserver<OnSymbolTickRequest> c = call;
            if (c != null) c.cancel("unsubscribed", null);
        }
    }

    private ConnectionGrpc.ConnectionBlockingStub connection(long timeoutMs) {
        return deadline(authorized(ConnectionGrpc.newBlockingStub(channel)), timeoutMs);
    }

    private static <S extends AbstractStub<S>> S deadline(S stub, long timeoutMs) {
        return stub.withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS);
    }

    @FunctionalInterface
    private interface Rpc<R> {
        R run();
    }

    private static <R extends Message> R call(Rpc<R> rpc) throws MrpcException {
        try {
            return rpc.run();
        } catch (StatusRuntimeException e) {
            throw new MrpcException(e.getStatus().getCode().name(), String.valueOf(e.getStatus().getDescription()), e);
        }
    }

    /** Every reply is {@code data} or {@code error}. */
    private static void throwIfError(Message reply) throws MrpcException {
        FieldDescriptor error = reply.getDescriptorForType().findFieldByName("error");
        if (error != null && reply.hasField(error)) {
            MrpcMt4Error.Error e = (MrpcMt4Error.Error) reply.getField(error);
            throw new MrpcException(e.getErrorCode(), e.getErrorMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static <D> D data(Message reply) throws MrpcException {
        throwIfError(reply);
        FieldDescriptor data = reply.getDescriptorForType().findFieldByName("data");
        if (data == null || !reply.hasField(data)) throw new MrpcException("NO_DATA", reply.getDescriptorForType().getName() + " has neither data nor error");
        return (D) reply.getField(data);
    }
}
