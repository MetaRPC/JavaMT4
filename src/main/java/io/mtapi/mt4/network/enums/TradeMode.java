package io.mtapi.mt4.network.enums;

/**
 * Symbol trade mode — whether trading is allowed.
 */
public enum TradeMode {
    /** Trading disabled. */
    No(0),
    /** Only closing trades allowed. */
    Close(1),
    /** Full trading access. */
    Full(2);

    private final int value;

    TradeMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TradeMode forValue(int value) {
        for (TradeMode m : values()) {
            if (m.value == value) return m;
        }
        return null;
    }
}
