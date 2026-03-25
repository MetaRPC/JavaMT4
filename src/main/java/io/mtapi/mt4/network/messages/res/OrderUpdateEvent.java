package io.mtapi.mt4.network.messages.res;

import io.mtapi.mt4.network.MetaTraderMessage;
import io.mtapi.mt4.network.enums.Op;
import io.mtapi.mt4.network.enums.UpdateAction;

/**
 * Order update event from the MT4 server.
 * Carries the raw order data, action type, and balance/credit snapshot.
 */
public class OrderUpdateEvent implements MetaTraderMessage {
    public UpdateAction action;
    public int ticket;
    public Op type;
    public String symbol;
    public double lots;
    public double openPrice;
    public double closePrice;
    public double stopLoss;
    public double takeProfit;
    public double profit;
    public double commission;
    public double swap;
    public int openTime;
    public int closeTime;
    public int expiration;
    public int magicNumber;
    public String comment;
    public double balance;
    public double credit;

    @Override
    public String toString() {
        return "OrderUpdateEvent{action=" + action + ", ticket=" + ticket + ", type=" + type +
                ", symbol='" + symbol + "', lots=" + lots + '}';
    }
}
