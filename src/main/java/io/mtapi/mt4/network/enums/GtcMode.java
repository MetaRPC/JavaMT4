package io.mtapi.mt4.network.enums;

/**
 * GTC (Good Till Cancelled) mode for pending orders.
 */
public enum GtcMode {
    /** Pending orders are daily (expire at end of day). */
    Daily(0),
    /** Pending orders are GTC (good till cancelled). */
    GTC(1),
    /** Daily, but stops are not removed. */
    DailyNoStops(2);

    private final int value;

    GtcMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GtcMode forValue(int value) {
        for (GtcMode m : values()) {
            if (m.value == value) return m;
        }
        return null;
    }
}
