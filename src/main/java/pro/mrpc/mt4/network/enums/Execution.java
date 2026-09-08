package pro.mrpc.mt4.network.enums;

/**
 * Symbol execution type.
 */
public enum Execution {
    /** Execution by request. */
    Request(0),
    /** Instant execution. */
    Instant(1),
    /** Market execution. */
    Market(2);

    private final int value;

    Execution(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Execution forValue(int value) {
        for (Execution e : values()) {
            if (e.value == value) return e;
        }
        return null;
    }
}
