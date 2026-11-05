package pro.mrpc.mt4;

import mt4_term_api.MrpcMt4Error;

/**
 * Thrown when the MT4 server answers a request with an error, or when a gRPC call fails. Unchecked so
 * it does not have to be declared on every method (mirrors the Python {@code ApiExceptionMT4}).
 */
public class ApiExceptionMT4 extends RuntimeException {
    private final MrpcMt4Error.Error error;

    public ApiExceptionMT4(MrpcMt4Error.Error error) {
        super(describe(error));
        this.error = error;
    }

    public ApiExceptionMT4(String message) {
        super(message);
        this.error = null;
    }

    public ApiExceptionMT4(String message, Throwable cause) {
        super(message, cause);
        this.error = null;
    }

    /** The server error, or {@code null} when this wraps a transport/other failure. */
    public MrpcMt4Error.Error getError() {
        return error;
    }

    /** The server error code (e.g. {@code TERMINAL_INSTANCE_NOT_FOUND}), or {@code null}. */
    public String getErrorCode() {
        return error != null ? error.getErrorCode() : null;
    }

    private static String describe(MrpcMt4Error.Error e) {
        if (e == null) return "MT4 API error";
        String code = e.getErrorCode();
        String message = e.getErrorMessage();
        if (message != null && !message.isEmpty()) {
            return (code != null && !code.isEmpty()) ? code + ": " + message : message;
        }
        return (code != null && !code.isEmpty()) ? code : "MT4 API error";
    }
}
