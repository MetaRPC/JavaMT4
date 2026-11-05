package pro.mrpc.mt4.error;

/** An order was rejected by the server/broker. */
public class OrderRejected extends MT4Error {
    public OrderRejected(String message, Integer code, Object details, Throwable cause) {
        super(message, code, details, cause);
    }
}
