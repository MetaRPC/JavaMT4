package pro.mrpc.mt4.error;

/** Client-side guard: too many requests in a short time. */
public class RateLimitExceeded extends MT4Error {
    public RateLimitExceeded(String message, Integer code, Object details, Throwable cause) {
        super(message, code, details, cause);
    }
}
