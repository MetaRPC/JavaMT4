package pro.mrpc.mt4;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import mt4_term_api.MrpcMt4Error;

/**
 * A trade call (OrderSend, OrderModify, OrderCloseDelete, OrderCloseBy) failed at the transport level
 * (gRPC UNAVAILABLE, DEADLINE_EXCEEDED, UNKNOWN or INTERNAL) after the request may already have reached
 * the terminal: the trade may or may not have been executed.
 *
 * <p>The SDK does NOT re-send such a call automatically. Check opened orders ({@code openedOrders()})
 * before retrying, or the trade may be executed twice.</p>
 *
 * <p>{@link #getErrorCode()} is {@value #ERROR_CODE}; {@link #getCause()} is the original
 * {@link StatusRuntimeException}.</p>
 */
public class TradeOutcomeUnknownExceptionMT4 extends ApiExceptionMT4 {

    /** Error code of this exception. */
    public static final String ERROR_CODE = "TRADE_OUTCOME_UNKNOWN";

    private final String operation;
    private final Status status;

    public TradeOutcomeUnknownExceptionMT4(String operation, StatusRuntimeException cause) {
        super(MrpcMt4Error.Error.newBuilder()
                .setErrorCode(ERROR_CODE)
                .setErrorMessage(describe(operation, cause.getStatus()))
                .build(), cause);
        this.operation = operation;
        this.status = cause.getStatus();
    }

    private static String describe(String operation, Status status) {
        return operation + " failed with gRPC " + status.getCode()
                + (status.getDescription() != null ? " (" + status.getDescription() + ")" : "")
                + " after the request may have reached the terminal; the trade may or may not have been executed."
                + " It was NOT re-sent automatically: check open positions/orders before retrying.";
    }

    /** The trade operation that failed, e.g. "OrderSend". */
    public String getOperation() {
        return operation;
    }

    /** The gRPC status of the original transport failure. */
    public Status getStatus() {
        return status;
    }

    /** The gRPC status code of the original transport failure (e.g. UNAVAILABLE). */
    public Status.Code getStatusCode() {
        return status.getCode();
    }

    @Override
    public StatusRuntimeException getCause() {
        return (StatusRuntimeException) super.getCause();
    }
}
