package io.mtapi.mt4.network.messages.res;

import io.mtapi.mt4.network.MetaTraderMessage;
import io.mtapi.mt4.network.parts.TradeRecord;

import java.util.ArrayList;

/**
 * Response containing historical closed trade records.
 */
public class OrderHistoryRes implements MetaTraderMessage {
    public final ArrayList<TradeRecord> trades = new ArrayList<>();

    @Override
    public String toString() {
        return "OrderHistoryRes{trades=" + trades.size() + '}';
    }
}
