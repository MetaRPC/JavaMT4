package pro.mrpc.mt4;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import mt4_term_api.AccountHelperGrpc;
import mt4_term_api.ConnectionGrpc;
import mt4_term_api.MrpcMt4Error;
import mt4_term_api.Mt4TermApiAccountHelper;
import mt4_term_api.Mt4TermApiConnection;
import mt4_term_api.Mt4TermApiTradingHelper;
import mt4_term_api.TradingHelperGrpc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pro.mrpc.mt4.error.MT4Error;
import pro.mrpc.mt4.error.TradeOutcomeUnknown;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Retry rules of {@link MT4Account} against an in-process fake gateway:
 * trades are never re-sent after a transport failure, reads are, refused API keys fail fast.
 */
@Timeout(60) // callWithReconnect retries reads without a bound: a regression must fail, not hang
public class MT4AccountRetryTest {

    /** One scripted answer per call; the last one repeats. */
    private static final class Script<T> {
        private final Deque<Supplier<T>> steps = new ArrayDeque<>();
        final AtomicInteger calls = new AtomicInteger();

        @SafeVarargs
        final void set(Supplier<T>... answers) {
            steps.clear();
            for (Supplier<T> a : answers) steps.add(a);
        }

        void answer(StreamObserver<T> observer) {
            calls.incrementAndGet();
            Supplier<T> step = steps.size() > 1 ? steps.poll() : steps.peek();
            try {
                observer.onNext(step.get());
                observer.onCompleted();
            } catch (StatusRuntimeException e) {
                observer.onError(e);
            }
        }
    }

    private final Script<Mt4TermApiConnection.ConnectExReply> connectEx = new Script<>();
    private final Script<Mt4TermApiTradingHelper.OrderSendReply> orderSend = new Script<>();
    private final Script<Mt4TermApiTradingHelper.OrderModifyReply> orderModify = new Script<>();
    private final Script<Mt4TermApiTradingHelper.OrderCloseDeleteReply> orderCloseDelete = new Script<>();
    private final Script<Mt4TermApiTradingHelper.OrderCloseByReply> orderCloseBy = new Script<>();
    private final Script<Mt4TermApiAccountHelper.AccountSummaryReply> accountSummary = new Script<>();

    private Server server;
    private ManagedChannel channel;
    private MT4Account account;

    private static MrpcMt4Error.Error error(String code) {
        return MrpcMt4Error.Error.newBuilder().setErrorCode(code).setErrorMessage(code + " (test)").build();
    }

    private static <T> Supplier<T> fail(Status status) {
        return () -> { throw status.asRuntimeException(); };
    }

    private static Mt4TermApiConnection.ConnectExReply connected() {
        return Mt4TermApiConnection.ConnectExReply.newBuilder()
                .setData(Mt4TermApiConnection.ConnectData.newBuilder()
                        .setTerminalInstanceGuid("mt4_live_f6a35703d3905dcfbb64a23164b31f7b"))
                .build();
    }

    @BeforeEach
    void setUp() throws Exception {
        String name = InProcessServerBuilder.generateName();
        server = InProcessServerBuilder.forName(name).directExecutor()
                .addService(new ConnectionGrpc.ConnectionImplBase() {
                    @Override
                    public void connectEx(Mt4TermApiConnection.ConnectExRequest request,
                                          StreamObserver<Mt4TermApiConnection.ConnectExReply> observer) {
                        connectEx.answer(observer);
                    }
                })
                .addService(new TradingHelperGrpc.TradingHelperImplBase() {
                    @Override
                    public void orderSend(Mt4TermApiTradingHelper.OrderSendRequest request,
                                          StreamObserver<Mt4TermApiTradingHelper.OrderSendReply> observer) {
                        orderSend.answer(observer);
                    }

                    @Override
                    public void orderModify(Mt4TermApiTradingHelper.OrderModifyRequest request,
                                            StreamObserver<Mt4TermApiTradingHelper.OrderModifyReply> observer) {
                        orderModify.answer(observer);
                    }

                    @Override
                    public void orderCloseDelete(Mt4TermApiTradingHelper.OrderCloseDeleteRequest request,
                                                 StreamObserver<Mt4TermApiTradingHelper.OrderCloseDeleteReply> observer) {
                        orderCloseDelete.answer(observer);
                    }

                    @Override
                    public void orderCloseBy(Mt4TermApiTradingHelper.OrderCloseByRequest request,
                                             StreamObserver<Mt4TermApiTradingHelper.OrderCloseByReply> observer) {
                        orderCloseBy.answer(observer);
                    }
                })
                .addService(new AccountHelperGrpc.AccountHelperImplBase() {
                    @Override
                    public void accountSummary(Mt4TermApiAccountHelper.AccountSummaryRequest request,
                                               StreamObserver<Mt4TermApiAccountHelper.AccountSummaryReply> observer) {
                        accountSummary.answer(observer);
                    }
                })
                .build().start();
        channel = InProcessChannelBuilder.forName(name).directExecutor().build();
        account = new MT4Account(channel, 1L, "pw", null, "test-key");

        connectEx.set(MT4AccountRetryTest::connected);
        account.connectByServerName("Test-Server", 5);
        connectEx.calls.set(0);
    }

    @AfterEach
    void tearDown() {
        channel.shutdownNow();
        server.shutdownNow();
    }

    @Test
    void orderSendUnavailableIsNotResent() {
        orderSend.set(fail(Status.UNAVAILABLE.withDescription("connection reset")));

        TradeOutcomeUnknownExceptionMT4 ex = assertThrows(TradeOutcomeUnknownExceptionMT4.class,
                () -> account.orderSend(Mt4TermApiTradingHelper.OrderSendRequest.getDefaultInstance()));

        assertEquals(1, orderSend.calls.get(), "OrderSend must be sent exactly once");
        assertEquals(0, connectEx.calls.get(), "no reconnect after an unknown trade outcome");
        assertEquals("TRADE_OUTCOME_UNKNOWN", ex.getErrorCode());
        assertEquals("OrderSend", ex.getOperation());
        assertEquals(Status.Code.UNAVAILABLE, ex.getStatusCode());
        assertInstanceOf(StatusRuntimeException.class, ex.getCause());
        assertTrue(ex.getMessage().startsWith("TRADE_OUTCOME_UNKNOWN: OrderSend failed with gRPC UNAVAILABLE"),
                ex.getMessage());
        assertInstanceOf(ApiExceptionMT4.class, ex, "existing catch (ApiExceptionMT4) handlers still catch it");
    }

    @Test
    void modifyCloseDeleteCloseByOnTransportFailuresAreNotResent() {
        orderModify.set(fail(Status.DEADLINE_EXCEEDED));
        TradeOutcomeUnknownExceptionMT4 m = assertThrows(TradeOutcomeUnknownExceptionMT4.class,
                () -> account.orderModify(7, null, 1.1, null, null));
        assertEquals("OrderModify", m.getOperation());
        assertEquals(Status.Code.DEADLINE_EXCEEDED, m.getStatusCode());
        assertEquals(1, orderModify.calls.get());

        orderCloseDelete.set(fail(Status.INTERNAL));
        TradeOutcomeUnknownExceptionMT4 c = assertThrows(TradeOutcomeUnknownExceptionMT4.class,
                () -> account.orderCloseDelete(7, null, null, null));
        assertEquals("OrderCloseDelete", c.getOperation());
        assertEquals(1, orderCloseDelete.calls.get());

        orderCloseBy.set(fail(Status.UNKNOWN));
        TradeOutcomeUnknownExceptionMT4 b = assertThrows(TradeOutcomeUnknownExceptionMT4.class,
                () -> account.orderCloseBy(7, 8));
        assertEquals("OrderCloseBy", b.getOperation());
        assertEquals(1, orderCloseBy.calls.get());
        assertEquals(0, connectEx.calls.get());
    }

    @Test
    void sugarCloseSurfacesTradeOutcomeUnknownNotModifyRejected() {
        orderCloseDelete.set(fail(Status.UNAVAILABLE));
        MT4Sugar sugar = new MT4Sugar(new MT4Service(account));

        MT4Error ex = assertThrows(MT4Error.class, () -> sugar.close(7));

        TradeOutcomeUnknown t = assertInstanceOf(TradeOutcomeUnknown.class, ex);
        assertEquals("TRADE_OUTCOME_UNKNOWN", t.getErrorCode());
        assertEquals("OrderCloseDelete", t.getOperation());
        assertEquals(Status.Code.UNAVAILABLE, t.getStatusCode());
        assertEquals(1, orderCloseDelete.calls.get());
        assertEquals(0, connectEx.calls.get());
    }

    @Test
    void orderSendTerminalInstanceNotFoundReconnectsAndResends() {
        orderSend.set(
                () -> Mt4TermApiTradingHelper.OrderSendReply.newBuilder()
                        .setError(error("TERMINAL_INSTANCE_NOT_FOUND")).build(),
                () -> Mt4TermApiTradingHelper.OrderSendReply.newBuilder()
                        .setData(Mt4TermApiTradingHelper.OrderSendData.newBuilder().setTicket(42)).build());

        Mt4TermApiTradingHelper.OrderSendData data =
                account.orderSend(Mt4TermApiTradingHelper.OrderSendRequest.getDefaultInstance());

        assertEquals(42, data.getTicket());
        assertEquals(2, orderSend.calls.get());
        assertEquals(1, connectEx.calls.get());
    }

    @Test
    void readUnavailableIsRetriedAfterReconnect() {
        accountSummary.set(
                fail(Status.UNAVAILABLE),
                () -> Mt4TermApiAccountHelper.AccountSummaryReply.newBuilder()
                        .setData(Mt4TermApiAccountHelper.AccountSummaryData.newBuilder().setAccountBalance(1000))
                        .build());

        Mt4TermApiAccountHelper.AccountSummaryData data = account.accountSummary();

        assertEquals(1000, data.getAccountBalance());
        assertEquals(2, accountSummary.calls.get());
        assertEquals(1, connectEx.calls.get());
    }

    @Test
    void apiKeyInvalidInReplyFailsFastWithCode() {
        orderSend.set(() -> Mt4TermApiTradingHelper.OrderSendReply.newBuilder()
                .setError(error("API_KEY_INVALID")).build());

        ApiExceptionMT4 ex = assertThrows(ApiExceptionMT4.class,
                () -> account.orderSend(Mt4TermApiTradingHelper.OrderSendRequest.getDefaultInstance()));

        assertEquals("API_KEY_INVALID", ex.getErrorCode());
        assertEquals(1, orderSend.calls.get());
        assertEquals(0, connectEx.calls.get());
    }

    @Test
    void apiKeyInvalidInStatusFailsFastWithCode() {
        accountSummary.set(fail(Status.UNAVAILABLE.withDescription("API_KEY_INVALID: unknown key")));

        ApiExceptionMT4 ex = assertThrows(ApiExceptionMT4.class, () -> account.accountSummary());

        assertEquals("API_KEY_INVALID", ex.getErrorCode());
        assertEquals(1, accountSummary.calls.get());
        assertEquals(0, connectEx.calls.get());
    }

    @Test
    void readReconnectRefusedKeyStopsAndKeepsCode() {
        accountSummary.set(fail(Status.UNAVAILABLE));
        connectEx.set(fail(Status.UNAUTHENTICATED.withDescription("API_KEY_REQUIRED")));

        ApiExceptionMT4 ex = assertThrows(ApiExceptionMT4.class, () -> account.accountSummary());

        assertEquals("API_KEY_REQUIRED", ex.getErrorCode());
        assertEquals(1, accountSummary.calls.get(), "no further calls after a refused key");
        assertEquals(1, connectEx.calls.get(), "no reconnect loop after a refused key");
    }

    @Test
    void readReconnectRefusedKeyInReplyStopsAndKeepsCode() {
        accountSummary.set(fail(Status.UNAVAILABLE));
        connectEx.set(() -> Mt4TermApiConnection.ConnectExReply.newBuilder()
                .setError(MrpcMt4Error.Error.newBuilder().setErrorCode("API_KEY_INVALID")).build());

        ApiExceptionMT4 ex = assertThrows(ApiExceptionMT4.class, () -> account.accountSummary());

        assertEquals("API_KEY_INVALID", ex.getErrorCode());
        assertEquals(1, accountSummary.calls.get());
        assertEquals(1, connectEx.calls.get());
    }
}
