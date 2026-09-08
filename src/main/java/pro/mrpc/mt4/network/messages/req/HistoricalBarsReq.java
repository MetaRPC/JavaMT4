package pro.mrpc.mt4.network.messages.req;

import pro.mrpc.mt4.network.MetaTraderMessage;
import pro.mrpc.mt4.network.MetaTraderRequest;
import pro.mrpc.mt4.network.OutputFactory;
import pro.mrpc.mt4.network.enums.Timeframe;
import pro.mrpc.mt4.network.messages.res.HistoricalBarsRes;
import pro.mrpc.mt4.utils.MT4Constants;

import java.nio.charset.StandardCharsets;

/**
 * Requests historical OHLCV bar data for a given symbol and timeframe.
 * <p>
 * The response is delivered as {@link HistoricalBarsRes}.
 */
public class HistoricalBarsReq implements MetaTraderRequest {
    /**
     * Symbol name (e.g., "EURUSD").
     */
    public String symbol;

    /**
     * Chart timeframe.
     */
    public Timeframe timeframe;

    /**
     * Start time as Unix timestamp (seconds since epoch, UTC).
     */
    public int fromTime;

    /**
     * Number of bars to request.
     */
    public short count;

    @Override
    public void build(OutputFactory outputFactory) {
        if (symbol == null || timeframe == null)
            throw new IllegalArgumentException("HistoricalBarsReq: symbol and timeframe required");

        outputFactory.single((byte) 0x01); // sub-command
        outputFactory.single((byte) 0x00); // padding

        byte[] symbolBytes = symbol.getBytes(StandardCharsets.US_ASCII);
        outputFactory.array(symbolBytes);
        outputFactory.pad(12 - symbolBytes.length);

        outputFactory.number(timeframe.getValue());
        outputFactory.number(fromTime);
        outputFactory.number(count);
    }

    @Override
    public byte getCommand() {
        return MT4Constants.SEND_QUOTE_HISTORY;
    }

    @Override
    public Class<? extends MetaTraderMessage> getExpectedResponseClass() {
        return HistoricalBarsRes.class;
    }

    @Override
    public void reset() {
        symbol = null;
        timeframe = null;
        fromTime = 0;
        count = 0;
    }
}
