package pro.mrpc.mt4.network.messages.res;

import pro.mrpc.mt4.network.MetaTraderMessage;
import pro.mrpc.mt4.network.parts.TradeRecord;

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
