package io.mtapi.mt4.network.enums;

/**
 * MT4 order type.
 */
public enum Op {
    Buy(0),
    Sell(1),
    BuyLimit(2),
    SellLimit(3),
    BuyStop(4),
    SellStop(5),
    Balance(6),
    Credit(7);

    private final int value;

    Op(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Op forValue(int value) {
        for (Op op : values()) {
            if (op.value == value) return op;
        }
        return Buy;
    }
}
