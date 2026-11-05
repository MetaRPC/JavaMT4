package pro.mrpc.mt4.error;

/** Connectivity / session issues (reconnect failed, handshake error, etc.). */
public class ConnectivityError extends MT4Error {
    public ConnectivityError(String message, Integer code, Object details, Throwable cause) {
        super(message, code, details, cause);
    }
}
