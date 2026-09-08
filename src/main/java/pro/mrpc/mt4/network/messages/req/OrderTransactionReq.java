package pro.mrpc.mt4.network.messages.req;

import pro.mrpc.mt4.network.MetaTraderMessage;
import pro.mrpc.mt4.network.MetaTraderRequest;
import pro.mrpc.mt4.network.OutputFactory;
import pro.mrpc.mt4.network.enums.Op;
import pro.mrpc.mt4.network.enums.PlacedType;
import pro.mrpc.mt4.network.enums.TradeCommand;
import pro.mrpc.mt4.network.messages.res.TradeEvent;
import pro.mrpc.mt4.utils.MT4Constants;


/**
 * Sends an order transaction (open, close, modify, delete) to the MT4 server.
 * <p>
 * The response is delivered as {@link TradeEvent}.
 *
 * <p>Example (market buy):
 * <pre>{@code
 * OrderTransactionReq req = new OrderTransactionReq();
 * req.tradeCommand = TradeCommand.Instant;
 * req.orderType = Op.Buy;
 * req.symbol = "EURUSD";
 * req.lots = 100; // 1.00 lot
 * req.price = 1.12345;
 * TradeEvent res = (TradeEvent) client.sendRequest(req);
 * }</pre>
 */
public class OrderTransactionReq implements MetaTraderRequest {
    /**
     * Trade command type (Instant, Market, Pending, Close, Modify, etc.).
     */
    public TradeCommand tradeCommand;

    /**
     * Placement source type (Client, Expert, Api, etc.). Defaults to Client.
     */
    public PlacedType placedType = PlacedType.Client;

    /**
     * Order type (Buy, Sell, BuyLimit, etc.).
     */
    public Op orderType;

    /**
     * Order ticket for modify/close operations.
     */
    public int ticket;

    /**
     * Magic number (EA identifier).
     */
    public int magicNumber;

    /**
     * Symbol name.
     */
    public String symbol;

    /**
     * Volume in hundredths of lot (100 = 1.00 lot).
     */
    public int lots;

    /**
     * Order price.
     */
    public double price;

    /**
     * Stop loss price.
     */
    public double stopLoss;

    /**
     * Take profit price.
     */
    public double takeProfit;

    /**
     * Maximum slippage in points.
     */
    public int slippage;

    /**
     * Order comment (max 31 characters).
     */
    public String comment;

    /**
     * Expiration time as Unix timestamp (0 = no expiration).
     */
    public int expiration;

    /**
     * Account number (set automatically by the client).
     */
    public int account;

    @Override
    public void build(OutputFactory outputFactory) {
        if (tradeCommand == null || orderType == null)
            throw new IllegalArgumentException("OrderTransactionReq: tradeCommand and orderType required");

        // Core 92-byte payload (CRC32 and verification added by MT4Client)
        // [0] tradeCommand, [1] placedType, [2] operation, [3] padding
        // [4-7] ticket, [8-11] magic, [12-23] symbol, [24-27] lots
        // [28-35] price, [36-43] stopLoss, [44-51] takeProfit
        // [52-55] slippage, [56-87] comment, [88-91] expiration

        outputFactory.single((byte) tradeCommand.getValue());
        outputFactory.single((byte) placedType.getValue());
        outputFactory.single((byte) orderType.getValue());
        outputFactory.single((byte) 0); // padding

        outputFactory.number(ticket);
        outputFactory.number(magicNumber);

        if (symbol != null) {
            byte[] symbolBytes = toBytes(symbol, 12);
            outputFactory.array(symbolBytes);
        } else {
            outputFactory.pad(12);
        }

        outputFactory.number(lots);
        outputFactory.number(price);
        outputFactory.number(stopLoss);
        outputFactory.number(takeProfit);
        outputFactory.number(slippage);

        if (comment != null) {
            byte[] commentBytes = toBytes(comment, 32);
            outputFactory.array(commentBytes);
        } else {
            outputFactory.pad(32);
        }

        outputFactory.number(expiration);
        // Total: 92 bytes (no checksum — added by MT4Client)
    }

    @Override
    public byte getCommand() {
        return MT4Constants.SEND_ORDER_TRANSACTION;
    }

    @Override
    public Class<? extends MetaTraderMessage> getExpectedResponseClass() {
        return TradeEvent.class;
    }

    @Override
    public void reset() {
        tradeCommand = null;
        orderType = null;
        ticket = 0;
        magicNumber = 0;
        symbol = null;
        lots = 0;
        price = 0;
        stopLoss = 0;
        takeProfit = 0;
        slippage = 0;
        comment = null;
        expiration = 0;
    }

    /**
     * Converts a string to a fixed-size byte array using single-byte encoding
     * (matching the original MT4 vUTF.toByte() — each char truncated to lower 8 bits).
     */
    private static byte[] toBytes(String str, int fixedLen) {
        byte[] buf = new byte[fixedLen];
        int len = Math.min(str.length(), fixedLen);
        for (int i = 0; i < len; i++) {
            buf[i] = (byte) (str.charAt(i) & 0xFF);
        }
        return buf;
    }

    @Override
    public String toString() {
        return "OrderTransactionReq{tradeCommand=" + tradeCommand + ", orderType=" + orderType +
                ", symbol='" + symbol + "', lots=" + lots + ", price=" + price + '}';
    }
}
