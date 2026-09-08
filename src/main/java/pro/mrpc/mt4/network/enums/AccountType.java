package pro.mrpc.mt4.network.enums;

/**
 * MT4 account type.
 */
public enum AccountType {
    Real(0),
    Contest(1),
    Demo(2);

    private final int value;

    AccountType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AccountType forValue(int value) {
        for (AccountType t : values()) {
            if (t.value == value) return t;
        }
        return null;
    }
}
