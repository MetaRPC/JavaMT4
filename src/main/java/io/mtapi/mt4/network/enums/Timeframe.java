package io.mtapi.mt4.network.enums;

/**
 * MT4 chart timeframes.
 */
public enum Timeframe {
    M1(1),
    M5(5),
    M15(15),
    M30(30),
    H1(60),
    H4(240),
    D1(1440),
    W1(10080),
    MN1(43200);

    private final int value;

    Timeframe(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Timeframe forValue(int value) {
        for (Timeframe tf : values()) {
            if (tf.value == value) return tf;
        }
        return M1;
    }
}
