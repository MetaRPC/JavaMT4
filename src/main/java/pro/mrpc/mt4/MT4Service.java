package pro.mrpc.mt4;

import mt4_term_api.Mt4TermApiAccountHelper;
import mt4_term_api.Mt4TermApiConnection;
import mt4_term_api.Mt4TermApiMarketInfo;
import mt4_term_api.Mt4TermApiServerTimezone;
import mt4_term_api.Mt4TermApiSubscriptions;
import mt4_term_api.Mt4TermApiTradeStats;
import mt4_term_api.Mt4TermApiTradingHelper;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;

/**
 * Mid-level wrapper over {@link MT4Account}, a faithful Java port of the Python {@code app.MT4Service}: it keeps
 * the same method set, delegates to the account layer, and adds friendly conversions (timeframe strings → enum,
 * sort aliases, {@code side}/{@code type} → operation type) plus trade shortcuts ({@code buyMarket} … {@code closeBy}).
 *
 * <p>Unlike the dynamically-typed Python original it returns the typed protobuf {@code …Data} messages instead of
 * flattened lists; filtering/formatting of those lives in {@link MT4Sugar}.</p>
 */
public class MT4Service {
    private final MT4Account account;

    // Order defaults (set_trade_defaults)
    private Integer defaultMagic;
    private Integer defaultDeviationPips;
    private String defaultComment;

    public MT4Service(MT4Account account) {
        this.account = account;
    }

    public MT4Account getAccount() {
        return account;
    }

    // ===== Connection =====

    public String connectByServerName(String serverName) {
        return account.connectByServerName(serverName);
    }

    public String connectByServerName(String serverName, int timeoutSeconds) {
        return account.connectByServerName(serverName, timeoutSeconds);
    }

    public String connectByHostPort(String host, int port) {
        return account.connectByHostPort(host, port);
    }

    public String connectByHostPort(String host, int port, int timeoutSeconds) {
        return account.connectByHostPort(host, port, timeoutSeconds);
    }

    public void reconnect() {
        account.reconnect();
    }

    public void disconnect(boolean delete) {
        account.disconnect(delete);
    }

    // ===== Account / stats =====

    public Mt4TermApiAccountHelper.AccountSummaryData accountSummary() {
        return account.accountSummary();
    }

    public Mt4TermApiTradeStats.StatsData tradeStats(Instant since) {
        return account.tradeStats(since);
    }

    public Mt4TermApiTradeStats.TradeUnrealizedPLData tradeUnrealizedPl(Instant since) {
        return account.tradeUnrealizedPl(since);
    }

    public Mt4TermApiTradeStats.EquityHistoryData equityHistory(Instant since, Boolean excludeSameBars) {
        return account.equityHistory(since, excludeSameBars);
    }

    public Mt4TermApiTradeStats.StatsWithChartsData tradeStatsEquityHistory(Instant since, Boolean excludeSameBars) {
        return account.tradeStatsEquityHistory(since, excludeSameBars);
    }

    // ===== Symbols / quotes =====

    public Mt4TermApiMarketInfo.SymbolsData symbols() {
        return account.symbols();
    }

    /** Parameters for one symbol, or all symbols when {@code symbolName} is null. */
    public Mt4TermApiAccountHelper.SymbolParamsManyData symbolParamsMany(String symbolName) {
        return account.symbolParamsMany(symbolName);
    }

    public Mt4TermApiMarketInfo.QuoteData quote(String symbol) {
        return account.quote(symbol);
    }

    public Mt4TermApiMarketInfo.QuoteManyData quoteMany(List<String> symbols) {
        return account.quoteMany(symbols);
    }

    /** Historical bars; {@code timeframe} is an MT4 string ("M1".."MN1"); the window defaults to {@code limit} bars back from now. */
    public Mt4TermApiMarketInfo.QuoteHistoryData quoteHistory(String symbol, String timeframe, Instant since, Instant until, Integer limit) {
        Instant end = until != null ? until : Instant.now();
        Instant start = since;
        if (start == null) {
            int bars = (limit != null && limit > 0) ? limit : 100;
            start = end.minus(Duration.ofMinutes((long) timeframeMinutes(timeframe) * bars));
        }
        return account.quoteHistory(symbol, toTimeframe(timeframe), start, end);
    }

    public Mt4TermApiMarketInfo.QuoteHistoryData quoteHistory(String symbol, Mt4TermApiMarketInfo.ENUM_QUOTE_HISTORY_TIMEFRAME timeframe, Instant since, Instant until) {
        return account.quoteHistory(symbol, timeframe, since, until);
    }

    public Mt4TermApiAccountHelper.TickValueWithSizeData tickValueWithSize(List<String> symbols) {
        return account.tickValueWithSize(symbols);
    }

    public Mt4TermApiMarketInfo.SymbolSelectData symbolSelect(String symbol, boolean select) {
        return account.symbolSelect(symbol, select);
    }

    public Mt4TermApiServerTimezone.ServerTimezoneData serverTimezone() {
        return account.serverTimezone();
    }

    // ===== Orders / history =====

    public Mt4TermApiAccountHelper.OpenedOrdersData openedOrders() {
        return account.openedOrders();
    }

    /** Opened orders sorted by alias ("open_asc"/"open_desc"/"ticket_asc"/"ticket_desc") or raw enum name. */
    public Mt4TermApiAccountHelper.OpenedOrdersData openedOrders(String sort) {
        return sort == null ? account.openedOrders() : account.openedOrders(toOpenedSort(sort));
    }

    public Mt4TermApiAccountHelper.OpenedOrdersTicketsData openedOrdersTickets() {
        return account.openedOrdersTickets();
    }

    public Mt4TermApiAccountHelper.OrdersHistoryData ordersHistory() {
        return account.ordersHistory();
    }

    /** History sorted by alias ("close_desc" default; "open_asc", "ticket_desc", …) with optional window/pagination. */
    public Mt4TermApiAccountHelper.OrdersHistoryData ordersHistory(String sort, Instant since, Instant until, Integer page, Integer pageSize) {
        Mt4TermApiAccountHelper.EnumOrderHistorySortType sortMode =
                sort != null ? toHistorySort(sort) : Mt4TermApiAccountHelper.EnumOrderHistorySortType.HISTORY_SORT_BY_CLOSE_TIME_DESC;
        return account.ordersHistory(sortMode, since, until, page, pageSize);
    }

    // ===== Trading (typed passthrough + convenience) =====

    public Mt4TermApiTradingHelper.OrderSendData orderSend(Mt4TermApiTradingHelper.OrderSendRequest request) {
        return account.orderSend(request);
    }

    public Mt4TermApiTradingHelper.OrderSendData orderSend(String symbol, Mt4TermApiTradingHelper.OrderSendOperationType type, double lots) {
        return account.orderSend(symbol, type, lots);
    }

    public Mt4TermApiTradingHelper.OrderModifyData orderModify(int ticket, Double newPrice, Double newStopLoss, Double newTakeProfit, Instant newExpiration) {
        return account.orderModify(ticket, newPrice, newStopLoss, newTakeProfit, newExpiration);
    }

    public Mt4TermApiTradingHelper.OrderCloseDeleteData orderCloseDelete(int ticket, Double lots, Double closingPrice, Integer slippage) {
        return account.orderCloseDelete(ticket, lots, closingPrice, slippage);
    }

    public Mt4TermApiTradingHelper.OrderCloseByData orderCloseBy(int ticketToClose, int oppositeTicketClosingBy) {
        return account.orderCloseBy(ticketToClose, oppositeTicketClosingBy);
    }

    // ----- trade defaults + shortcuts (MT4ServiceTrade) -----

    /** Defaults mixed into every buy/sell shortcut (null leaves a default unchanged). */
    public void setTradeDefaults(Integer magic, Integer deviationPips, String comment) {
        if (magic != null) this.defaultMagic = magic;
        if (deviationPips != null) this.defaultDeviationPips = deviationPips;
        if (comment != null) this.defaultComment = comment;
    }

    public long buyMarket(String symbol, double lots, Double sl, Double tp, Integer magic, Integer deviationPips, String comment) {
        return sendMarketOrPending(symbol, "buy", "market", null, lots, sl, tp, magic, deviationPips, comment);
    }

    public long sellMarket(String symbol, double lots, Double sl, Double tp, Integer magic, Integer deviationPips, String comment) {
        return sendMarketOrPending(symbol, "sell", "market", null, lots, sl, tp, magic, deviationPips, comment);
    }

    public long buyLimit(String symbol, double price, double lots, Double sl, Double tp, Integer magic, String comment) {
        return sendMarketOrPending(symbol, "buy", "limit", price, lots, sl, tp, magic, null, comment);
    }

    public long sellLimit(String symbol, double price, double lots, Double sl, Double tp, Integer magic, String comment) {
        return sendMarketOrPending(symbol, "sell", "limit", price, lots, sl, tp, magic, null, comment);
    }

    public long buyStop(String symbol, double price, double lots, Double sl, Double tp, Integer magic, String comment) {
        return sendMarketOrPending(symbol, "buy", "stop", price, lots, sl, tp, magic, null, comment);
    }

    public long sellStop(String symbol, double price, double lots, Double sl, Double tp, Integer magic, String comment) {
        return sendMarketOrPending(symbol, "sell", "stop", price, lots, sl, tp, magic, null, comment);
    }

    public void modifySlTp(int ticket, Double slPrice, Double tpPrice) {
        account.orderModify(ticket, null, slPrice, tpPrice, null);
    }

    public void close(int ticket) {
        account.orderCloseDelete(ticket, null, null, null);
    }

    public void closePartial(int ticket, double lots) {
        account.orderCloseDelete(ticket, lots, null, null);
    }

    public void closeBy(int ticketA, int ticketB) {
        account.orderCloseBy(ticketA, ticketB);
    }

    private long sendMarketOrPending(String symbol, String side, String type, Double price, double lots,
                                     Double sl, Double tp, Integer magic, Integer deviationPips, String comment) {
        Mt4TermApiTradingHelper.OrderSendRequest.Builder b = Mt4TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(symbol).setOperationType(toOperationType(side, type)).setVolume(lots);
        if (price != null) b.setPrice(price);
        if (sl != null) b.setStoploss(sl);
        if (tp != null) b.setTakeprofit(tp);
        Integer dev = deviationPips != null ? deviationPips : defaultDeviationPips;
        if (dev != null) b.setSlippage(dev);
        Integer m = magic != null ? magic : defaultMagic;
        if (m != null) b.setMagicNumber(m);
        String c = comment != null ? comment : defaultComment;
        if (c != null) b.setComment(c);
        return account.orderSend(b.build()).getTicket();
    }

    // ===== Streams (relay to the account layer) =====

    public MT4Account.Subscription onSymbolTick(List<String> symbols, Consumer<Mt4TermApiSubscriptions.OnSymbolTickData> onData, Consumer<Throwable> onError) {
        return account.onSymbolTick(symbols, onData, onError);
    }

    public MT4Account.Subscription onTrade(Consumer<Mt4TermApiSubscriptions.OnTradeData> onData, Consumer<Throwable> onError) {
        return account.onTrade(onData, onError);
    }

    public MT4Account.Subscription onOpenedOrdersTickets(int pullIntervalMilliseconds, Consumer<Mt4TermApiSubscriptions.OnOpenedOrdersTicketsData> onData, Consumer<Throwable> onError) {
        return account.onOpenedOrdersTickets(pullIntervalMilliseconds, onData, onError);
    }

    public MT4Account.Subscription onOpenedOrdersProfit(int timerPeriodMilliseconds, Consumer<Mt4TermApiSubscriptions.OnOpenedOrdersProfitData> onData, Consumer<Throwable> onError) {
        return account.onOpenedOrdersProfit(timerPeriodMilliseconds, onData, onError);
    }

    public MT4Account.Subscription onConnectState(List<String> terminalIds, Consumer<Mt4TermApiConnection.ConnectStateData> onData, Consumer<Throwable> onError) {
        return account.onConnectState(terminalIds, onData, onError);
    }

    // ===== Converters (friendly strings → protobuf enums) =====

    /** side ("buy"/"sell") + type ("market"/"limit"/"stop") → operation type. */
    public static Mt4TermApiTradingHelper.OrderSendOperationType toOperationType(String side, String type) {
        boolean buy = side == null || !"sell".equalsIgnoreCase(side.trim());
        String t = type == null ? "market" : type.trim().toLowerCase();
        switch (t) {
            case "limit":
                return buy ? Mt4TermApiTradingHelper.OrderSendOperationType.OC_OP_BUYLIMIT
                           : Mt4TermApiTradingHelper.OrderSendOperationType.OC_OP_SELLLIMIT;
            case "stop":
                return buy ? Mt4TermApiTradingHelper.OrderSendOperationType.OC_OP_BUYSTOP
                           : Mt4TermApiTradingHelper.OrderSendOperationType.OC_OP_SELLSTOP;
            default:
                return buy ? Mt4TermApiTradingHelper.OrderSendOperationType.OC_OP_BUY
                           : Mt4TermApiTradingHelper.OrderSendOperationType.OC_OP_SELL;
        }
    }

    /** MT4 timeframe string ("M1","M5","M15","M30","H1","H4","D1","W1","MN1") or raw enum name → enum. */
    public static Mt4TermApiMarketInfo.ENUM_QUOTE_HISTORY_TIMEFRAME toTimeframe(String tf) {
        String key = tf == null ? "H1" : tf.trim().toUpperCase();
        String name;
        switch (key) {
            case "M1": case "1M": name = "QH_PERIOD_M1"; break;
            case "M5": case "5M": name = "QH_PERIOD_M5"; break;
            case "M15": case "15M": name = "QH_PERIOD_M15"; break;
            case "M30": case "30M": name = "QH_PERIOD_M30"; break;
            case "H1": case "1H": name = "QH_PERIOD_H1"; break;
            case "H4": case "4H": name = "QH_PERIOD_H4"; break;
            case "D1": case "1D": case "D": name = "QH_PERIOD_D1"; break;
            case "W1": case "1W": case "W": name = "QH_PERIOD_W1"; break;
            case "MN1": case "MN": case "MO": name = "QH_PERIOD_MN1"; break;
            default: name = key; // allow a raw enum name like "QH_PERIOD_H1"
        }
        return Mt4TermApiMarketInfo.ENUM_QUOTE_HISTORY_TIMEFRAME.valueOf(name);
    }

    private static int timeframeMinutes(String tf) {
        String key = tf == null ? "H1" : tf.trim().toUpperCase();
        switch (key) {
            case "M1": return 1;
            case "M5": return 5;
            case "M15": return 15;
            case "M30": return 30;
            case "H1": return 60;
            case "H4": return 240;
            case "D1": return 1440;
            case "W1": return 10080;
            case "MN1": return 43200;
            default: return 60;
        }
    }

    /** Opened-orders sort alias ("open_asc"/"open_desc"/"ticket_asc"/"ticket_desc") or raw enum name. */
    public static Mt4TermApiAccountHelper.EnumOpenedOrderSortType toOpenedSort(String sort) {
        String name;
        switch (sort.trim().toLowerCase()) {
            case "open_asc": name = "SORT_BY_OPEN_TIME_ASC"; break;
            case "open_desc": name = "SORT_BY_OPEN_TIME_DESC"; break;
            case "ticket_asc": name = "SORT_BY_ORDER_TICKET_ID_ASC"; break;
            case "ticket_desc": name = "SORT_BY_ORDER_TICKET_ID_DESC"; break;
            default: name = sort.trim().toUpperCase();
        }
        return Mt4TermApiAccountHelper.EnumOpenedOrderSortType.valueOf(name);
    }

    /** History sort alias ("open_asc/desc", "close_asc/desc", "ticket_asc/desc") or raw enum name. */
    public static Mt4TermApiAccountHelper.EnumOrderHistorySortType toHistorySort(String sort) {
        String name;
        switch (sort.trim().toLowerCase()) {
            case "open_asc": name = "HISTORY_SORT_BY_OPEN_TIME_ASC"; break;
            case "open_desc": name = "HISTORY_SORT_BY_OPEN_TIME_DESC"; break;
            case "close_asc": name = "HISTORY_SORT_BY_CLOSE_TIME_ASC"; break;
            case "close_desc": name = "HISTORY_SORT_BY_CLOSE_TIME_DESC"; break;
            case "ticket_asc": name = "HISTORY_SORT_BY_ORDER_TICKET_ID_ASC"; break;
            case "ticket_desc": name = "HISTORY_SORT_BY_ORDER_TICKET_ID_DESC"; break;
            default: name = sort.trim().toUpperCase();
        }
        return Mt4TermApiAccountHelper.EnumOrderHistorySortType.valueOf(name);
    }
}
