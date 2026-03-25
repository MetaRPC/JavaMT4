package io.mtapi.mt4.network.parts;

import io.mtapi.mt4.network.enums.Op;

/**
 * A historical trade record (224 bytes in the MT4 protocol).
 */
public class TradeRecord {
    public int ticket;
    public int login;
    public String symbol;
    public int digits;
    public Op cmd;
    public int volume;
    public int openTime;
    public int state;
    public double openPrice;
    public double stopLoss;
    public double takeProfit;
    public int closeTime;
    public int expiration;
    public double commission;
    public double swap;
    public double closePrice;
    public double profit;
    public double taxes;
    public int magicNumber;
    public String comment;

    @Override
    public String toString() {
        return "TradeRecord{" +
                "ticket=" + ticket +
                ", login=" + login +
                ", symbol='" + symbol + '\'' +
                ", digits=" + digits +
                ", cmd=" + cmd +
                ", volume=" + volume +
                ", openTime=" + openTime +
                ", state=" + state +
                ", openPrice=" + openPrice +
                ", stopLoss=" + stopLoss +
                ", takeProfit=" + takeProfit +
                ", closeTime=" + closeTime +
                ", expiration=" + expiration +
                ", commission=" + commission +
                ", swap=" + swap +
                ", closePrice=" + closePrice +
                ", profit=" + profit +
                ", taxes=" + taxes +
                ", magicNumber=" + magicNumber +
                ", comment='" + comment + '\'' +
                '}';
    }
}
