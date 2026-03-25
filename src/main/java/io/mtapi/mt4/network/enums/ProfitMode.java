package io.mtapi.mt4.network.enums;

/**
 * Profit calculation mode.
 */
public enum ProfitMode {
    Forex(0),
    CFD(1),
    Futures(2);

    private final int value;

    ProfitMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ProfitMode forValue(int value) {
        for (ProfitMode m : values()) {
            if (m.value == value) return m;
        }
        return null;
    }
}
