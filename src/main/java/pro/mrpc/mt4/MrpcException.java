package pro.mrpc.mt4;

import mt4_term_api.MrpcMt4Error;

/**
 * An error the MRPC API answered with (the reply's {@code error}), or a failed gRPC call.
 */
public class MrpcException extends Exception {
    private final String code;
    private final MrpcMt4Error.Error error;

    public MrpcException(String code, String message) {
        this(code, message, (MrpcMt4Error.Error) null);
    }

    public MrpcException(String code, String message, Throwable cause) {
        this(code, message);
        initCause(cause);
    }

    /** The error the API answered with. */
    public MrpcException(MrpcMt4Error.Error error) {
        this(error.getErrorCode(), error.getErrorMessage(), error);
    }

    private MrpcException(String code, String message, MrpcMt4Error.Error error) {
        super(code == null || code.isEmpty() ? message : code + ": " + message);
        this.code = code == null ? "" : code;
        this.error = error;
    }

    /** The API's error code (e.g. {@code TERMINAL_INSTANCE_NOT_FOUND}), or the gRPC status of a failed call. */
    public String getCode() {
        return code;
    }

    /**
     * The error the API answered with ({@code getMqlErrorCode()}, {@code getMqlErrorIntCode()} and the rest of its fields),
     * or null for a failed call.
     */
    public MrpcMt4Error.Error getError() {
        return error;
    }
}
