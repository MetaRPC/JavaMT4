package pro.mrpc.mt4.network.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * MT4 server error/status codes returned in trade responses.
 */
public enum TradeError {
    OK_ANSWER(0, "OK"),
    OK_REQUEST(1, "OK request"),
    COMMON_ERROR(2, "Common error"),
    INVALID_PARAM(3, "Invalid parameters"),
    SERVER_BUSY(4, "Server busy"),
    OLD_VERSION(5, "Old version"),
    NO_CONNECT(6, "No connection"),
    NOT_ENOUGH_RIGHTS(7, "Not enough rights"),
    TOO_FREQUENT_REQUEST(8, "Too frequent request"),
    SECRET_KEY_REQUIRED(0xD, "Secret key required"),
    INVALID_ONETIME_PASSWORD(0xE, "Invalid one-time password"),
    ACCOUNT_DISABLED(0x40, "Account disabled"),
    INVALID_ACCOUNT(0x41, "Invalid account"),
    TRADE_TIMEOUT(0x80, "Trade timeout"),
    INVALID_PRICES(0x81, "Invalid prices"),
    INVALID_SL_TP(0x82, "Invalid stops (SL/TP)"),
    INVALID_VOLUME(0x83, "Invalid volume"),
    MARKET_CLOSED(0x84, "Market closed"),
    TRADE_DISABLED(0x85, "Trade disabled"),
    NOT_MONEY(0x86, "Not enough money"),
    PRICE_CHANGED(0x87, "Price changed"),
    OFF_QUOTES(0x88, "Off quotes"),
    BROKER_BUSY(0x89, "Broker busy"),
    REQUOTE(0x8A, "Requote"),
    ORDER_LOCKED(0x8B, "Order locked"),
    LONG_POS_ALLOWED(0x8C, "Only long positions allowed"),
    TOO_MANY_REQUESTS(0x8D, "Too many requests"),
    ORDER_ACCEPTED(0x8E, "Order accepted"),
    ORDER_IN_PROCESS(0x8F, "Order in process"),
    REQUEST_CANCELLED(0x90, "Request cancelled"),
    MODIFICATIONS_DENIED(0x91, "Modifications denied"),
    TRADE_CONTEXT_BUSY(0x92, "Trade context busy"),
    EXPIRATION_DISABLED(0x93, "Expiration disabled"),
    TOO_MANY_ORDERS(0x94, "Too many orders"),
    HEDGE_PROHIBITED(0x95, "Hedge prohibited"),
    FIFO_PROHIBITED(0x96, "FIFO close order prohibited");

    private final int code;
    private final String description;

    private static final Map<Integer, TradeError> BY_CODE = new HashMap<>();

    static {
        for (TradeError e : values()) {
            BY_CODE.put(e.code, e);
        }
    }

    TradeError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TradeError forCode(int code) {
        return BY_CODE.get(code);
    }

    /**
     * Returns description for a code, or "Unknown error (code)" if not mapped.
     */
    public static String describe(int code) {
        TradeError e = BY_CODE.get(code);
        return e != null ? e.description : "Unknown error (" + code + ")";
    }
}
