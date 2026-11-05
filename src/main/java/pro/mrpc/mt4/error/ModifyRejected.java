package pro.mrpc.mt4.error;

/** An order modify/close/delete was rejected. */
public class ModifyRejected extends MT4Error {
    public ModifyRejected(String message, Integer code, Object details, Throwable cause) {
        super(message, code, details, cause);
    }
}
