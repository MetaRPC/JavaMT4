package io.mtapi.mt4.network.enums;

/**
 * Margin calculation mode.
 */
public enum MarginMode {
    Forex(0),
    CFD(1),
    Futures(2),
    CfdIndex(3),
    CfdLeverage(4);

    private final int value;

    MarginMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MarginMode forValue(int value) {
        for (MarginMode m : values()) {
            if (m.value == value) return m;
        }
        return null;
    }
}
