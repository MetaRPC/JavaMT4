package pro.mrpc.mt4.network.enums;

/**
 * Describes the type of order update received from the MT4 server.
 */
public enum UpdateAction {
    PositionOpen,
    PositionClose,
    PositionModify,
    PendingOpen,
    PendingClose,
    PendingModify,
    PendingFill,
    Balance,
    Credit
}
