package pro.mrpc.mt4.error;

import io.grpc.Status;
import pro.mrpc.mt4.TradeOutcomeUnknownExceptionMT4;

/**
 * {@code MT4Sugar} form of {@link TradeOutcomeUnknownExceptionMT4}: the trade request may have reached the
 * terminal and the outcome is unknown (it was NOT rejected, and it was NOT re-sent). Check opened orders
 * before retrying. The cause is the {@link TradeOutcomeUnknownExceptionMT4}.
 */
public class TradeOutcomeUnknown extends ConnectivityError {
    private final TradeOutcomeUnknownExceptionMT4 outcome;

    public TradeOutcomeUnknown(String message, TradeOutcomeUnknownExceptionMT4 cause) {
        super(message, null, cause.getOperation(), cause);
        this.outcome = cause;
    }

    /** Always {@code TRADE_OUTCOME_UNKNOWN}. */
    public String getErrorCode() {
        return TradeOutcomeUnknownExceptionMT4.ERROR_CODE;
    }

    /** The trade operation that failed, e.g. "OrderSend". */
    public String getOperation() {
        return outcome.getOperation();
    }

    /** The gRPC status code of the original transport failure. */
    public Status.Code getStatusCode() {
        return outcome.getStatusCode();
    }
}
