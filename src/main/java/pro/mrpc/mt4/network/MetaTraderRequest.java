package pro.mrpc.mt4.network;

/**
 * Interface for building outgoing MT4 requests.
 * MT4 uses a simpler stream-based protocol without the 9-byte headers of MT5.
 */
public interface MetaTraderRequest {
    /**
     * Builds the request payload into the output factory.
     */
    void build(OutputFactory outputFactory);

    /**
     * Returns the command byte for this request.
     */
    byte getCommand();

    /**
     * Returns the expected response class for synchronous requests, or null for async.
     */
    Class<? extends MetaTraderMessage> getExpectedResponseClass();

    /**
     * Resets internal state for object reuse.
     */
    void reset();
}
