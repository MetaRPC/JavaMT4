package io.mtapi.mt4.network.messages.res;

import io.mtapi.mt4.network.MetaTraderMessage;
import io.mtapi.mt4.network.enums.Timeframe;
import io.mtapi.mt4.network.parts.BarRecord;

import java.util.ArrayList;

/**
 * Response containing historical OHLCV bar data.
 */
public class HistoricalBarsRes implements MetaTraderMessage {
    public String symbol;
    public Timeframe timeframe;
    public final ArrayList<BarRecord> bars = new ArrayList<>();

    @Override
    public String toString() {
        return "HistoricalBarsRes{symbol='" + symbol + "', timeframe=" + timeframe +
                ", bars=" + bars.size() + '}';
    }
}
