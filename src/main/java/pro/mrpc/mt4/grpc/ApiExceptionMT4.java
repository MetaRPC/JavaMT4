package pro.mrpc.mt4.grpc;

import mt4_term_api.MrpcMt4Error;

/**
 * Thrown when a MetaRPC MT4 gRPC reply carries an {@code error} payload instead of {@code data}.
 * Wraps {@link MrpcMt4Error.Error} so callers can branch on {@link #getErrorCode()} /
 * {@link #getMqlErrorIntCode()} (e.g. 4220 "symbol select error", 134 "not enough money").
 */
public class ApiExceptionMT4 extends RuntimeException {
    private final MrpcMt4Error.Error error;

    public ApiExceptionMT4(MrpcMt4Error.Error error) {
        super(format(error));
        this.error = error;
    }

    public MrpcMt4Error.Error getError() { return error; }
    public String getErrorCode() { return error.getErrorCode(); }
    public String getErrorMessage() { return error.getErrorMessage(); }
    public String getErrorType() { return error.getType().name(); }
    public int getMqlErrorIntCode() { return error.getMqlErrorIntCode(); }
    public String getMqlErrorCode() { return error.getMqlErrorCode().name(); }

    /** True for the non-retryable login failures (wrong login / password / server). Never retry these. */
    public boolean isInvalidLogin() {
        String c = error.getErrorCode();
        String m = error.getErrorMessage();
        return (c != null && c.toUpperCase().contains("INVALID_LOGIN"))
                || (m != null && (m.toLowerCase().contains("invalid account") || m.toLowerCase().contains("invalid login")));
    }

    private static String format(MrpcMt4Error.Error e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getType().name()).append('/').append(e.getErrorCode());
        if (e.getMqlErrorIntCode() != 0) sb.append("/mql").append(e.getMqlErrorIntCode());
        sb.append(": ").append(e.getErrorMessage());
        if (!e.getMqlErrorDescription().isEmpty()) sb.append(" (").append(e.getMqlErrorDescription()).append(')');
        return sb.toString();
    }
}
