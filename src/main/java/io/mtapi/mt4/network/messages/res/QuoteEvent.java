package io.mtapi.mt4.network.messages.res;

import io.mtapi.mt4.network.MetaTraderMessage;

/**
 * Quote tick event received from the MT4 server.
 * Contains symbol, bid, ask and server time.
 */
public class QuoteEvent implements MetaTraderMessage {
    public String symbol;
    public double bid;
    public double ask;
    public int time;

    public QuoteEvent() {
    }

    public QuoteEvent(String symbol, double bid, double ask, int time) {
        this.symbol = symbol;
        this.bid = bid;
        this.ask = ask;
        this.time = time;
    }

    @Override
    public String toString() {
        return "QuoteEvent{symbol='" + symbol + "', bid=" + bid + ", ask=" + ask + ", time=" + time + '}';
    }
}
