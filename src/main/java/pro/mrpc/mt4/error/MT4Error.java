package pro.mrpc.mt4.error;

/**
 * Base MT4 domain error for the high-level {@code MT4Sugar} layer (Java port of the Python {@code MT4Error}).
 * Carries an optional numeric {@code code} and free-form {@code details}. Unchecked.
 */
public class MT4Error extends RuntimeException {
    private final Integer code;
    private final Object details;

    public MT4Error(String message) {
        this(message, null, null, null);
    }

    public MT4Error(String message, Integer code, Object details) {
        this(message, code, details, null);
    }

    public MT4Error(String message, Integer code, Object details, Throwable cause) {
        super(code != null ? "[" + code + "] " + message : message, cause);
        this.code = code;
        this.details = details;
    }

    public Integer getCode() {
        return code;
    }

    public Object getDetails() {
        return details;
    }
}
