package pro.mrpc.mt4.network.enums;

/**
 * MT4 trade command types used in order transactions.
 */
public enum TradeCommand {
    Instant(0x40),
    Market(0x42),
    Pending(0x43),
    CloseInstant(0x44),
    CloseMarket(0x46),
    Modify(0x47),
    DeletePending(0x48),
    CloseBy(0x49),
    MultipleCloseBy(0x4A);

    private final int value;

    TradeCommand(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
