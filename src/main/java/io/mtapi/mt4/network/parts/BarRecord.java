package io.mtapi.mt4.network.parts;

/**
 * A single historical OHLCV bar record.
 * <p>
 * Prices are stored as integers (point values). To convert to real prices,
 * multiply by the symbol's point value (10^-digits).
 */
public class BarRecord {
    public int time;
    public int open;
    public int high;
    public int low;
    public int close;
    public double volume;

    @Override
    public String toString() {
        return "BarRecord{time=" + time + ", open=" + open + ", high=" + high +
                ", low=" + low + ", close=" + close + ", volume=" + volume + '}';
    }
}
