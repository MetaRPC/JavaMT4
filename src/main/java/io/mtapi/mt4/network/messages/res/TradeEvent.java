package io.mtapi.mt4.network.messages.res;

import io.mtapi.mt4.network.MetaTraderMessage;
import io.mtapi.mt4.network.enums.TradeError;

/**
 * Response to an order transaction request.
 * Contains the server-assigned order ticket, request tracking ID, and trade result data.
 */
public class TradeEvent implements MetaTraderMessage {
    /** Server-assigned order ticket. 0 indicates an error. */
    public int ticket;
    /** Client-side request ID used for tracking (matches the requestId sent in the packet). */
    public int requestId;
    /** Error code from the server (0 = success). */
    public int errorCode;
    /** Open price from the order result data. */
    public double openPrice;

    public TradeError getTradeError() {
        return TradeError.forCode(errorCode);
    }

    public String getErrorDescription() {
        return TradeError.describe(errorCode);
    }

    public boolean isSuccess() {
        return errorCode == 0 && ticket > 0;
    }

    @Override
    public String toString() {
        return "TradeEvent{ticket=" + ticket + ", requestId=" + requestId
                + ", errorCode=" + errorCode + " (" + TradeError.describe(errorCode) + ")"
                + (openPrice != 0 ? ", openPrice=" + openPrice : "") + '}';
    }
}
