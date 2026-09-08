package pro.mrpc.mt4.network.enums;

/**
 * Swap calculation type.
 */
public enum SwapType {
    Points(0),
    Dollars(1),
    Interest(2),
    MarginCurrency(3);

    private final int value;

    SwapType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SwapType forValue(int value) {
        for (SwapType t : values()) {
            if (t.value == value) return t;
        }
        return null;
    }
}
