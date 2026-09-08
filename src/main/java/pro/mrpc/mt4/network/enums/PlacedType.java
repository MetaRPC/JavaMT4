package pro.mrpc.mt4.network.enums;

/**
 * Order placement source type.
 */
public enum PlacedType {
    Client(0),
    Expert(1),
    Dealer(2),
    Signal(3),
    Gateway(4),
    Mobile(5),
    Web(6),
    Api(7);

    private final int value;

    PlacedType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PlacedType forValue(int value) {
        for (PlacedType t : values()) {
            if (t.value == value) return t;
        }
        return null;
    }
}
