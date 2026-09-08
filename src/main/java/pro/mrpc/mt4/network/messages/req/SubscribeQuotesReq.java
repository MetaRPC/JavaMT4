package pro.mrpc.mt4.network.messages.req;

import pro.mrpc.mt4.network.MetaTraderMessage;
import pro.mrpc.mt4.network.MetaTraderRequest;
import pro.mrpc.mt4.network.OutputFactory;
import pro.mrpc.mt4.utils.MT4Constants;

/**
 * Subscribes to real-time quote streaming for the specified symbol codes.
 * <p>
 * Symbol codes are the internal short identifiers assigned during login.
 * After sending this request, {@code QuoteEvent} messages will be received
 * in the {@code onMessage} callback.
 */
public class SubscribeQuotesReq implements MetaTraderRequest {
    /**
     * Array of symbol codes to subscribe to.
     */
    public short[] symbolCodes;

    @Override
    public void build(OutputFactory outputFactory) {
        if (symbolCodes == null)
            throw new IllegalArgumentException("SubscribeQuotesReq: symbolCodes required");

        outputFactory.number((short) symbolCodes.length);
        for (short code : symbolCodes) {
            outputFactory.number(code);
        }
    }

    @Override
    public byte getCommand() {
        return MT4Constants.SEND_QUOTE_SUBSCRIBE;
    }

    @Override
    public Class<? extends MetaTraderMessage> getExpectedResponseClass() {
        return null;
    }

    @Override
    public void reset() {
        symbolCodes = null;
    }
}
