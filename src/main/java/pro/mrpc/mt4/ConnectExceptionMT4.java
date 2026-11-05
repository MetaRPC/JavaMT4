package pro.mrpc.mt4;

/**
 * Thrown when an account method is called before {@link MT4Account#connectByServerName} /
 * {@link MT4Account#connectByHostPort} (mirrors the Python {@code ConnectExceptionMT4}). Unchecked.
 */
public class ConnectExceptionMT4 extends RuntimeException {
    public ConnectExceptionMT4(String message) {
        super(message);
    }
}
