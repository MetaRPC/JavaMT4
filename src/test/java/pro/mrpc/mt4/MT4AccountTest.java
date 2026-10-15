package pro.mrpc.mt4;

import com.google.protobuf.Timestamp;
import io.grpc.Context;
import io.grpc.Metadata;
import io.grpc.Server;
import io.grpc.ServerInterceptors;
import io.grpc.ServerInterceptor;
import io.grpc.Contexts;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import mt4_term_api.AccountHelperGrpc;
import mt4_term_api.ConnectionGrpc;
import mt4_term_api.MrpcMt4Error;
import mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryData;
import mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryReply;
import mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryRequest;
import mt4_term_api.Mt4TermApiConnection.*;
import mt4_term_api.Mt4TermApiSubscriptions.OnSymbolMqlTickInfo;
import mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickData;
import mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickReply;
import mt4_term_api.Mt4TermApiSubscriptions.OnSymbolTickRequest;
import mt4_term_api.Mt4TermApiTradingHelper.*;
import mt4_term_api.SubscriptionServiceGrpc;
import mt4_term_api.TradingHelperGrpc;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/** MT4Account against an in-process gRPC server built from the generated service bases (no network, no MRPC account). */
class MT4AccountTest {
    private static final String TERMINAL = "mt4_live_0123456789abcdef0123456789abcdef";
    private static final Metadata.Key<String> API_KEY = Metadata.Key.of("apikey", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> ID = Metadata.Key.of("id", Metadata.ASCII_STRING_MARSHALLER);
    private static final Context.Key<Metadata> HEADERS = Context.key("headers");

    /** What the client sent: method, request, apikey, id. */
    record Seen(String method, Object request, String apikey, String id) {}

    private static final List<Seen> seen = new CopyOnWriteArrayList<>();
    private static volatile MrpcMt4Error.Error failNext;
    private static final ScheduledExecutorService ticks = Executors.newScheduledThreadPool(1);
    private static Server server;
    private static final String NAME = InProcessServerBuilder.generateName();

    private static void record(String method, Object request) {
        Metadata h = HEADERS.get();
        seen.add(new Seen(method, request, h.get(API_KEY), h.get(ID)));
    }

    private static MrpcMt4Error.Error takeFailure() {
        MrpcMt4Error.Error e = failNext;
        failNext = null;
        return e;
    }

    private static void fail(String code, String message) {
        failNext = MrpcMt4Error.Error.newBuilder().setErrorCode(code).setErrorMessage(message).build();
    }

    private static ConnectData connectData() {
        return ConnectData.newBuilder().setTerminalInstanceGuid(TERMINAL).build();
    }

    @BeforeAll
    static void start() throws Exception {
        ServerInterceptor captureHeaders = new ServerInterceptor() {
            @Override
            public <Q, R> io.grpc.ServerCall.Listener<Q> interceptCall(io.grpc.ServerCall<Q, R> call, Metadata headers, io.grpc.ServerCallHandler<Q, R> next) {
                return Contexts.interceptCall(Context.current().withValue(HEADERS, headers), call, headers, next);
            }
        };
        ConnectionGrpc.ConnectionImplBase connection = new ConnectionGrpc.ConnectionImplBase() {
            @Override
            public void connectEx(ConnectExRequest request, StreamObserver<ConnectExReply> out) {
                record("connectEx", request);
                MrpcMt4Error.Error e = takeFailure();
                out.onNext(e != null ? ConnectExReply.newBuilder().setError(e).build() : ConnectExReply.newBuilder().setData(connectData()).build());
                out.onCompleted();
            }

            @Override
            public void connect(ConnectRequest request, StreamObserver<ConnectReply> out) {
                record("connect", request);
                MrpcMt4Error.Error e = takeFailure();
                out.onNext(e != null ? ConnectReply.newBuilder().setError(e).build() : ConnectReply.newBuilder().setData(connectData()).build());
                out.onCompleted();
            }

            @Override
            public void getId(GetIdRequest request, StreamObserver<GetIdReply> out) {
                record("getId", request);
                MrpcMt4Error.Error e = takeFailure();
                out.onNext(e != null ? GetIdReply.newBuilder().setError(e).build() : GetIdReply.newBuilder().setData(GetIdData.newBuilder().setId(TERMINAL)).build());
                out.onCompleted();
            }

            @Override
            public void disconnect(DisconnectRequest request, StreamObserver<DisconnectReply> out) {
                record("disconnect", request);
                out.onNext(DisconnectReply.getDefaultInstance());
                out.onCompleted();
            }
        };
        AccountHelperGrpc.AccountHelperImplBase account = new AccountHelperGrpc.AccountHelperImplBase() {
            @Override
            public void accountSummary(AccountSummaryRequest request, StreamObserver<AccountSummaryReply> out) {
                record("accountSummary", request);
                MrpcMt4Error.Error e = takeFailure();
                out.onNext(e != null ? AccountSummaryReply.newBuilder().setError(e).build() : AccountSummaryReply.newBuilder().setData(AccountSummaryData.newBuilder()
                        .setAccountLogin(7689280).setAccountBalance(1000.5).setAccountEquity(990.25).setAccountUserName("Jane Trader")
                        .setAccountLeverage(100).setAccountCurrency("EUR")).build());
                out.onCompleted();
            }
        };
        TradingHelperGrpc.TradingHelperImplBase trading = new TradingHelperGrpc.TradingHelperImplBase() {
            @Override
            public void orderSend(OrderSendRequest request, StreamObserver<OrderSendReply> out) {
                record("orderSend", request);
                MrpcMt4Error.Error e = takeFailure();
                out.onNext(e != null ? OrderSendReply.newBuilder().setError(e).build()
                        : OrderSendReply.newBuilder().setData(OrderSendData.newBuilder().setTicket(88).setVolume(request.getVolume()).setPrice(1.1234)).build());
                out.onCompleted();
            }

            @Override
            public void orderCloseDelete(OrderCloseDeleteRequest request, StreamObserver<OrderCloseDeleteReply> out) {
                record("orderCloseDelete", request);
                out.onNext(OrderCloseDeleteReply.newBuilder().setData(OrderCloseDeleteData.newBuilder().setHistoryOrderComment("closed")).build());
                out.onCompleted();
            }
        };
        SubscriptionServiceGrpc.SubscriptionServiceImplBase subscriptions = new SubscriptionServiceGrpc.SubscriptionServiceImplBase() {
            @Override
            public void onSymbolTick(OnSymbolTickRequest request, StreamObserver<OnSymbolTickReply> out) {
                record("onSymbolTick", request);
                ServerCallStreamObserver<OnSymbolTickReply> stream = (ServerCallStreamObserver<OnSymbolTickReply>) out;
                int[] i = {0};
                var timer = ticks.scheduleAtFixedRate(() -> {
                    if (stream.isCancelled()) return;
                    if (request.getSymbolNames(0).equals("NOSUCH")) {
                        stream.onNext(OnSymbolTickReply.newBuilder().setError(MrpcMt4Error.Error.newBuilder()
                                .setErrorCode("NO_CHARTS_OPENED_FOR_SYMBOLS").setErrorMessage("NOSUCH")).build());
                        return;
                    }
                    int n = i[0]++;
                    stream.onNext(OnSymbolTickReply.newBuilder().setData(OnSymbolTickData.newBuilder().setSymbolTick(OnSymbolMqlTickInfo.newBuilder()
                            .setSymbol(request.getSymbolNames(n % 2)).setBid(1.1 + n / 1000.0).setAsk(1.1002 + n / 1000.0)
                            .setTimeUtc(Timestamp.newBuilder().setSeconds(1_792_065_600L + n)))).build());
                }, 0, 10, TimeUnit.MILLISECONDS);
                stream.setOnCancelHandler(() -> timer.cancel(false));
            }
        };
        server = InProcessServerBuilder.forName(NAME).directExecutor()
                .addService(ServerInterceptors.intercept(connection, captureHeaders))
                .addService(ServerInterceptors.intercept(account, captureHeaders))
                .addService(ServerInterceptors.intercept(trading, captureHeaders))
                .addService(ServerInterceptors.intercept(subscriptions, captureHeaders))
                .build().start();
    }

    @AfterAll
    static void stop() {
        server.shutdownNow();
        ticks.shutdownNow();
    }

    @BeforeEach
    void reset() {
        seen.clear();
        failNext = null;
    }

    private static MT4Account account(String apiKey) {
        return new MT4Account(InProcessChannelBuilder.forName(NAME).directExecutor().build(), apiKey);
    }

    private static Seen sent(String method) {
        return seen.stream().filter(s -> s.method().equals(method)).findFirst().orElseThrow();
    }

    @Test
    void connectSendsTheKeyKeepsTheTerminalIdAndSendsItWithEveryLaterCall() throws Exception {
        try (MT4Account a = account("key-1")) {
            assertEquals(TERMINAL, a.connect(123, "p", "TeleTrade-Demo", 60));
            assertEquals(TERMINAL, a.getTerminalId());
            Seen connect = sent("connectEx");
            assertEquals("key-1", connect.apikey());
            assertNull(connect.id());
            assertEquals("TeleTrade-Demo", ((ConnectExRequest) connect.request()).getMtClusterName());
            a.accountSummary();
            a.disconnect(true);
            assertTrue(seen.stream().filter(s -> !s.method().equals("connectEx")).allMatch(s -> TERMINAL.equals(s.id()) && "key-1".equals(s.apikey())));
            assertTrue(((DisconnectRequest) sent("disconnect").request()).getDelete());
            assertNull(a.getTerminalId());
        }
    }

    @Test
    void connectByAddressUsesConnect() throws Exception {
        try (MT4Account a = account("key-1")) {
            a.connect(123, "p", "10.0.0.1", 443, 60);
            ConnectRequest r = (ConnectRequest) sent("connect").request();
            assertEquals("10.0.0.1", r.getHost());
            assertEquals(443, r.getPort());
        }
    }

    @Test
    void accountSummaryIsWhatTheServerAnswered() throws Exception {
        try (MT4Account a = account("key-1")) {
            a.connect(123, "p", "TeleTrade-Demo", 60);
            AccountSummaryData s = a.accountSummary();
            assertEquals(7689280, s.getAccountLogin());
            assertEquals(1000.5, s.getAccountBalance());
            assertEquals("Jane Trader", s.getAccountUserName());
        }
    }

    @Test
    void anErrorReplyIsAnExceptionWithTheServersCode() throws Exception {
        try (MT4Account a = account("mrpc_test_key")) {
            fail("CONNECT_INVALID_LOGIN_ERROR", "Invalid account");
            MrpcException e = assertThrows(MrpcException.class, () -> a.connect(100234, "demo_password", "TeleTrade-Demo", 60));
            assertEquals("CONNECT_INVALID_LOGIN_ERROR", e.getCode());
            assertNull(a.getTerminalId());

            a.connect(100234, "p", "TeleTrade-Demo", 60);
            fail("TERMINAL_INSTANCE_NOT_FOUND", "no terminal");
            assertEquals("TERMINAL_INSTANCE_NOT_FOUND", assertThrows(MrpcException.class, a::accountSummary).getCode());
            fail("ERR_NOT_ENOUGH_MONEY", "Not enough money");
            assertEquals("ERR_NOT_ENOUGH_MONEY", assertThrows(MrpcException.class,
                    () -> a.orderSend("EURUSD", OrderSendOperationType.OC_OP_BUY, 0.1)).getCode());
        }
    }

    @Test
    void aFailedCallIsAnExceptionWithTheGrpcStatus() {
        MT4Account a = account("key-1");
        a.close();
        MrpcException e = assertThrows(MrpcException.class, () -> a.connect(123, "p", "TeleTrade-Demo", 60));
        assertEquals("UNAVAILABLE", e.getCode());
    }

    @Test
    void getIdAsksTheServerAndTheFollowingConnectSendsTheId() throws Exception {
        try (MT4Account a = account("key-1")) {
            assertEquals(TERMINAL, a.getId(123, "p"));
            assertEquals("123", ((GetIdRequest) sent("getId").request()).getUser());
            a.connect(123, "p", "TeleTrade-Demo", 60);
            assertEquals(TERMINAL, sent("connectEx").id());
        }
        try (MT4Account a = account("key-1")) {
            fail("INVALID_ACCOUNT", "Invalid account");
            assertEquals("INVALID_ACCOUNT", assertThrows(MrpcException.class, () -> a.getId(123, "wrong")).getCode());
            assertNull(a.getTerminalId());
        }
    }

    @Test
    void orderSendAndOrderCloseSendWhatWasAskedAndReturnTheServerResult() throws Exception {
        try (MT4Account a = account("key-1")) {
            a.connect(123, "p", "TeleTrade-Demo", 60);
            OrderSendData d = a.orderSend(OrderSendRequest.newBuilder().setSymbol("EURUSD").setOperationType(OrderSendOperationType.OC_OP_SELLLIMIT)
                    .setVolume(0.2).setPrice(1.2).setStoploss(1.3).build());
            OrderSendRequest r = (OrderSendRequest) sent("orderSend").request();
            assertEquals(OrderSendOperationType.OC_OP_SELLLIMIT, r.getOperationType());
            assertEquals(1.3, r.getStoploss());
            assertEquals(88, d.getTicket());
            assertEquals(0.2, d.getVolume());
            assertEquals("closed", a.orderClose(88).getHistoryOrderComment());
            assertEquals(88, ((OrderCloseDeleteRequest) sent("orderCloseDelete").request()).getOrderTicket());
        }
    }

    @Test
    void onSymbolTickDeliversTicksUntilClosed() throws Exception {
        try (MT4Account a = account("key-1")) {
            a.connect(123, "p", "TeleTrade-Demo", 60);
            List<OnSymbolMqlTickInfo> got = new CopyOnWriteArrayList<>();
            List<Throwable> errors = new CopyOnWriteArrayList<>();
            CompletableFuture<Void> four = new CompletableFuture<>();
            MT4Account.Subscription sub = a.onSymbolTick(List.of("EURUSD", "GBPUSD"), t -> {
                got.add(t);
                if (got.size() == 4) four.complete(null);
            }, errors::add);
            four.get(5, TimeUnit.SECONDS);
            sub.close();
            Thread.sleep(50);
            int after = got.size();
            Thread.sleep(50);
            assertEquals(after, got.size(), "no ticks after close()");
            assertEquals(List.of("EURUSD", "GBPUSD", "EURUSD", "GBPUSD"), got.subList(0, 4).stream().map(OnSymbolMqlTickInfo::getSymbol).toList());
            assertEquals(1_792_065_602L, got.get(2).getTimeUtc().getSeconds());
            assertEquals(List.of(), errors);
            assertEquals(TERMINAL, sent("onSymbolTick").id());
        }
    }

    @Test
    void onSymbolTickReportsAnErrorReplyOfTheStream() throws Exception {
        try (MT4Account a = account("key-1")) {
            a.connect(123, "p", "TeleTrade-Demo", 60);
            CompletableFuture<Throwable> error = new CompletableFuture<>();
            List<OnSymbolMqlTickInfo> got = new CopyOnWriteArrayList<>();
            MT4Account.Subscription sub = a.onSymbolTick(List.of("NOSUCH"), got::add, error::complete);
            Throwable e = error.get(5, TimeUnit.SECONDS);
            sub.close();
            assertInstanceOf(MrpcException.class, e);
            assertEquals("NO_CHARTS_OPENED_FOR_SYMBOLS", ((MrpcException) e).getCode());
            assertEquals(List.of(), got);
        }
    }
}
