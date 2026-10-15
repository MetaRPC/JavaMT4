package pro.mrpc.mt4;

/**
 * An error the MRPC API answered with (the reply's {@code error}), or a failed gRPC call.
 */
public class MrpcException extends Exception {
    private final String code;

    public MrpcException(String code, String message) {
        super(code == null || code.isEmpty() ? message : code + ": " + message);
        this.code = code == null ? "" : code;
    }

    public MrpcException(String code, String message, Throwable cause) {
        this(code, message);
        initCause(cause);
    }

    /** The API's error code (e.g. {@code TERMINAL_INSTANCE_NOT_FOUND}), or the gRPC status of a failed call. */
    public String getCode() {
        return code;
    }
}
