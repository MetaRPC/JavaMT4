package io.mtapi.mt4;

import io.mtapi.mt4.crypt.MT4Encryption;
import io.mtapi.mt4.exception.DecoderException;
import io.mtapi.mt4.network.messages.res.*;
import io.mtapi.mt4.utils.SymbolTable;
import io.mtapi.mt4.network.enums.Op;
import io.mtapi.mt4.network.enums.Timeframe;
import io.mtapi.mt4.network.enums.UpdateAction;
import io.mtapi.mt4.network.parts.BarRecord;
import io.mtapi.mt4.network.parts.TradeRecord;
import io.mtapi.mt4.utils.BitConverter;
import io.mtapi.mt4.utils.MT4Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * MT4 command dispatcher. Reads command bytes from the decoded stream
 * and dispatches to the appropriate handler/loader.
 *
 * Unlike MT5 which uses 9-byte packet headers with type/size/id/flags,
 * MT4 uses a single command byte followed by command-specific data.
 */
public class MessageDecoder {
    private static final Logger log = LoggerFactory.getLogger(MessageDecoder.class);

    private final InternalMessageHandler messageHandler;
    private final MT4Client client;

    public MessageDecoder(InternalMessageHandler messageHandler, MT4Client client) {
        this.messageHandler = messageHandler;
        this.client = client;
    }

    /**
     * Processes a single command from the server stream.
     * Called in the read loop after receiving the command byte.
     */
    public void onCommand(byte cmd, MT4Encryption encryption) throws DecoderException, IOException {
        switch (cmd) {
            case MT4Constants.CMD_QUOTE -> handleQuote(encryption);
            case MT4Constants.CMD_NEWS -> handleNews(encryption);
            case MT4Constants.CMD_MAIL -> handleMail(encryption);
            case MT4Constants.CMD_SYMBOL_UPDATE -> handleSymbolUpdate(encryption);
            case MT4Constants.CMD_ORDER_UPDATE -> handleOrderUpdate(encryption);
            case MT4Constants.CMD_QUOTE_HISTORY -> handleQuoteHistory(encryption);
            case MT4Constants.CMD_NEWS_UPDATE -> handleNewsUpdate(encryption);
            case MT4Constants.CMD_CON_GROUP_UPDATE -> handleConGroupUpdate(encryption);
            case MT4Constants.CMD_ORDER_NOTIFY_1, MT4Constants.CMD_ORDER_NOTIFY_2 -> handleOrderNotify(encryption);
            case MT4Constants.CMD_DISCONNECT -> handleDisconnect();
            case MT4Constants.CMD_PING -> log.debug("Ping received");
            default -> throw new DecoderException("Unknown command: 0x" + Integer.toHexString(cmd & 0xFF));
        }
    }

    private void handleQuote(MT4Encryption encryption) throws IOException {
        byte[] buf = client.receiveDecoded(1, encryption);
        int tickCount = buf[0] & 0xFF;
        int len = tickCount * 14;
        buf = client.receiveDecoded(len, encryption);

        log.debug("Received {} quote ticks", tickCount);

        SymbolTable symbolTable = client.getSymbolTable();

        for (int i = 0; i < tickCount; i++) {
            int offset = i * 14;
            short code = BitConverter.toInt16(buf, offset);
            String symbol = symbolTable.getSymbol(code);
            if (symbol == null) {
                log.trace("Unknown symbol code: {}", code);
                continue;
            }

            float ask = BitConverter.toFloat(buf, offset + 10);
            if (ask < 0.00000001f) continue;
            float bid = BitConverter.toFloat(buf, offset + 6);
            if (bid < 0.00000001f) continue;

            int digits = symbolTable.getDigits(symbol);
            double multiplier = Math.pow(10, digits);

            QuoteEvent event = new QuoteEvent();
            event.symbol = symbol;
            event.time = BitConverter.toInt32(buf, offset + 2);
            event.bid = Math.round(bid * multiplier) / multiplier;
            event.ask = Math.round(ask * multiplier) / multiplier;
            messageHandler.handleMessage(event, client);
        }
    }

    private void handleNews(MT4Encryption encryption) throws IOException {
        byte[] buf = client.receiveDecoded(6, encryption);
        int len = BitConverter.toInt32(buf, 0);
        if (len > 0 && len <= 0x800000) {
            client.receiveDecoded(len, encryption);
        }
        log.debug("News received");
    }

    private void handleMail(MT4Encryption encryption) throws IOException {
        client.readCompressed(encryption);
        log.debug("Mail received");
    }

    private void handleSymbolUpdate(MT4Encryption encryption) throws IOException {
        byte[] data = client.readCompressed(encryption);
        log.debug("Symbol update received ({} bytes)", data.length);
        client.getSymbolTable().update(data);
        messageHandler.handleMessage(new SymbolUpdateEvent(data), client);
    }

    private void handleOrderUpdate(MT4Encryption encryption) throws IOException {
        byte[] data = client.readCompressed(encryption);
        log.debug("Order update received ({} bytes)", data.length);

        // Each order record is 272 bytes
        for (int i = 0; i + 272 <= data.length; i += 272) {
            double balance = BitConverter.toDouble(data, i + 24);
            double credit = BitConverter.toDouble(data, i + 32);

            OrderUpdateEvent event = new OrderUpdateEvent();
            event.balance = balance;
            event.credit = credit;

            parseOrderRecord(event, data, i + 48);

            int actionByte = data[i + 4] & 0xFF;
            event.action = resolveUpdateAction(actionByte, event.type);

            // Keep the client's open orders map in sync
            client.updateOpenOrder(event);

            messageHandler.handleMessage(event, client);
        }
    }

    /**
     * Parses a 224-byte order record into an {@link OrderUpdateEvent}.
     * Shared by live order updates, account init, and order history.
     */
    public static void parseOrderRecord(OrderUpdateEvent event, byte[] data, int offset) {
        event.ticket = BitConverter.toInt32(data, offset);
        event.type = Op.forValue(BitConverter.toInt32(data, offset + 24));
        event.lots = BitConverter.toInt32(data, offset + 28) / 100.0;
        event.openTime = BitConverter.toInt32(data, offset + 32);
        event.closeTime = BitConverter.toInt32(data, offset + 64);
        event.expiration = BitConverter.toInt32(data, offset + 72);
        event.openPrice = BitConverter.toDouble(data, offset + 40);
        event.closePrice = BitConverter.toDouble(data, offset + 120);
        event.stopLoss = BitConverter.toDouble(data, offset + 48);
        event.takeProfit = BitConverter.toDouble(data, offset + 56);
        event.profit = BitConverter.toDouble(data, offset + 128);
        event.commission = BitConverter.toDouble(data, offset + 96);
        event.swap = BitConverter.toDouble(data, offset + 112);
        event.magicNumber = BitConverter.toInt32(data, offset + 144);
        event.symbol = BitConverter.getString(data, offset + 8, 12);
        event.comment = BitConverter.getString(data, offset + 148, 32);
    }

    private UpdateAction resolveUpdateAction(int actionByte, Op type) {
        return switch (actionByte) {
            case 0 -> (type == Op.Buy || type == Op.Sell) ? UpdateAction.PositionOpen : UpdateAction.PendingOpen;
            case 1 -> {
                if (type == Op.Balance) yield UpdateAction.Balance;
                if (type == Op.Credit) yield UpdateAction.Credit;
                yield (type == Op.Buy || type == Op.Sell) ? UpdateAction.PositionClose : UpdateAction.PendingClose;
            }
            case 2 -> (type == Op.Buy || type == Op.Sell) ? UpdateAction.PositionModify : UpdateAction.PendingModify;
            default -> UpdateAction.PositionOpen;
        };
    }

    private void handleQuoteHistory(MT4Encryption encryption) throws IOException {
        byte[] buf = client.receiveDecoded(24, encryption);
        String symbol = BitConverter.getString(buf, 0, 12);
        short tf = BitConverter.toInt16(buf, 12);
        int barCount = BitConverter.toInt32(buf, buf.length - 4);

        HistoricalBarsRes res = new HistoricalBarsRes();
        res.symbol = symbol;
        res.timeframe = Timeframe.forValue(tf);

        if (barCount > 0) {
            int len = barCount * 28;
            buf = client.receiveDecoded(len, encryption);

            for (int i = 0; i < barCount; i++) {
                int offset = i * 28;
                BarRecord bar = new BarRecord();
                bar.time = BitConverter.toInt32(buf, offset);
                bar.open = BitConverter.toInt32(buf, offset + 4);
                bar.high = bar.open + BitConverter.toInt32(buf, offset + 8);
                bar.low = bar.open + BitConverter.toInt32(buf, offset + 12);
                bar.close = bar.open + BitConverter.toInt32(buf, offset + 16);
                bar.volume = BitConverter.toDouble(buf, offset + 20);
                res.bars.add(bar);
            }
        }

        log.debug("Quote history received: {} {} bars={}", symbol, tf, barCount);
        messageHandler.handleMessage(res, client);
    }

    private void handleNewsUpdate(MT4Encryption encryption) throws IOException {
        byte[] buf = client.receiveDecoded(1, encryption);
        if (buf[0] == 0) {
            handleNews(encryption);
        }
    }

    private void handleConGroupUpdate(MT4Encryption encryption) throws IOException {
        byte[] data = client.readCompressed(encryption);
        log.debug("ConGroup update received ({} bytes)", data.length);
        messageHandler.handleMessage(new ConGroupUpdateEvent(data), client);
    }

    private void handleOrderNotify(MT4Encryption encryption) throws IOException {
        byte[] buf = client.receiveDecoded(6, encryption);
        int requestId = BitConverter.toInt32(buf, 0);
        byte tradeType = buf[4];
        byte notify = buf[5];

        if (requestId == 0) {
            TradeEvent event = new TradeEvent();
            event.errorCode = notify & 0xFF;
            log.debug("Order error: {}", event.getErrorDescription());
            messageHandler.handleMessage(event, client);
            return;
        }

        switch (notify & 0xFF) {
            case 0x8E -> log.trace("Order requestId={} accepted", requestId);
            case 0x8F -> log.trace("Order requestId={} in process", requestId);
            case 0x8A -> {
                client.receiveDecoded(16, encryption);
                TradeEvent event = new TradeEvent();
                event.requestId = requestId;
                event.errorCode = 0x8A;
                messageHandler.handleMessage(event, client);
            }
            case 0 -> processTradeResult(requestId, tradeType, encryption);
            case 1 -> log.trace("Order requestId={} success (notify=1)", requestId);
            default -> {
                TradeEvent event = new TradeEvent();
                event.requestId = requestId;
                event.errorCode = notify & 0xFF;
                log.debug("Order error: {} (requestId={})", event.getErrorDescription(), requestId);
                messageHandler.handleMessage(event, client);
            }
        }
    }

    private void processTradeResult(int requestId, byte tradeType, MT4Encryption encryption) throws IOException {
        TradeEvent event = new TradeEvent();
        event.requestId = requestId;
        event.errorCode = 0;

        switch (tradeType & 0xFF) {
            case 0x00 -> {
                client.receiveDecoded(16, encryption);
            }
            case 0x40, 0x41, 0x42, 0x43 -> {
                byte[] data = client.readCompressed(encryption);
                parseTradeResultData(event, data);
                messageHandler.handleMessage(event, client);
            }
            case 0x44, 0x45, 0x46 -> {
                client.receiveDecoded(8, encryption);
                client.receiveDecoded(8, encryption);
                byte[] data = client.readCompressed(encryption);
                parseTradeResultData(event, data);
                messageHandler.handleMessage(event, client);
            }
            case 0x47 -> {
                byte[] data = client.readCompressed(encryption);
                parseTradeResultData(event, data);
                messageHandler.handleMessage(event, client);
            }
            case 0x48 -> messageHandler.handleMessage(event, client);
            case 0x49 -> {
                client.receiveDecoded(8, encryption);
                client.receiveDecoded(8, encryption);
                client.readCompressed(encryption);
                messageHandler.handleMessage(event, client);
            }
            case 0x4A -> messageHandler.handleMessage(event, client);
            default -> {
                log.warn("Unknown trade type: 0x{}", Integer.toHexString(tradeType & 0xFF));
                event.errorCode = tradeType & 0xFF;
                messageHandler.handleMessage(event, client);
            }
        }
    }

    private static void parseTradeResultData(TradeEvent event, byte[] data) {
        if (data.length >= 48) {
            event.ticket = BitConverter.toInt32(data, 0);
            event.openPrice = BitConverter.toDouble(data, 40);
        }
    }

    private void handleDisconnect() {
        log.debug("Server disconnect command received");
        messageHandler.handleMessage(new DisconnectEvent(), client);
    }

    /**
     * Parses order history response data (called by MT4Client for synchronous reads).
     * Each trade record is 224 bytes.
     */
    public static OrderHistoryRes parseOrderHistory(byte[] data) {
        OrderHistoryRes res = new OrderHistoryRes();
        for (int i = 0; i + 224 <= data.length; i += 224) {
            TradeRecord trade = new TradeRecord();
            trade.ticket = BitConverter.toInt32(data, i);
            trade.login = BitConverter.toInt32(data, i + 4);
            trade.symbol = BitConverter.getString(data, i + 8, 12);
            trade.digits = BitConverter.toInt32(data, i + 20);
            trade.cmd = Op.forValue(BitConverter.toInt32(data, i + 24));
            trade.volume = BitConverter.toInt32(data, i + 28);
            trade.openTime = BitConverter.toInt32(data, i + 32);
            trade.state = BitConverter.toInt32(data, i + 36);
            trade.openPrice = BitConverter.toDouble(data, i + 40);
            trade.stopLoss = BitConverter.toDouble(data, i + 48);
            trade.takeProfit = BitConverter.toDouble(data, i + 56);
            trade.closeTime = BitConverter.toInt32(data, i + 64);
            trade.expiration = BitConverter.toInt32(data, i + 72);
            trade.commission = BitConverter.toDouble(data, i + 96);
            trade.swap = BitConverter.toDouble(data, i + 112);
            trade.closePrice = BitConverter.toDouble(data, i + 120);
            trade.profit = BitConverter.toDouble(data, i + 128);
            trade.taxes = BitConverter.toDouble(data, i + 136);
            trade.magicNumber = BitConverter.toInt32(data, i + 144);
            trade.comment = BitConverter.getString(data, i + 148, 32);
            res.trades.add(trade);
        }
        return res;
    }
}
