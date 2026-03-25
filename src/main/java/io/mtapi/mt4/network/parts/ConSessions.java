package io.mtapi.mt4.network.parts;

/**
 * Quote and trade sessions for a single day (208 bytes in MT4 protocol).
 * Contains 3 quote sessions and 3 trade sessions.
 */
public class ConSessions {
    public ConSession[] quote;
    public ConSession[] trade;
    public int quoteOvernight;
    public int tradeOvernight;
    public int[] reserved;
}
