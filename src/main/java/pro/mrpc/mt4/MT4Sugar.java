package pro.mrpc.mt4;

import com.google.protobuf.Timestamp;
import mt4_term_api.Mt4TermApiAccountHelper;
import mt4_term_api.Mt4TermApiMarketInfo;
import mt4_term_api.Mt4TermApiTradingHelper;
import pro.mrpc.mt4.error.ModifyRejected;
import pro.mrpc.mt4.error.MT4Error;
import pro.mrpc.mt4.error.OrderRejected;
import pro.mrpc.mt4.util.HookBus;
import pro.mrpc.mt4.util.RateLimiter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import mt4_term_api.Mt4TermApiSubscriptions;

/**
 * High-level convenience layer over {@link MT4Service}, a Java port of the Python {@code app.MT4Sugar}:
 * pip math, price/lot normalization, quotes/bars/ticks, risk-based sizing, and pip-aware order entry/close.
 *
 * <p>This is batch A (foundation, market math, quotes, risk, trading, close). Background automation
 * (trailing stop, auto-breakeven, watches, waits) and the richer query helpers follow in a later batch.</p>
 */
public class MT4Sugar {
    private final MT4Service svc;
    public final HookBus hooks = new HookBus();
    private final RateLimiter orderSendRl = new RateLimiter(10.0);
    private final Map<String, Mt4TermApiAccountHelper.SymbolParamsManyInfo> symCache = new ConcurrentHashMap<>();
    private final Map<String, AutoCloseable> workers = new ConcurrentHashMap<>();

    // Defaults (set_defaults)
    private String defSymbol;
    private Integer defMagic;
    private Double defDeviationPips;
    private Double defSlippagePips;
    private Double defRiskPercent;

    public MT4Sugar(MT4Service service) {
        this.svc = service;
    }

    public MT4Service getService() {
        return svc;
    }

    // ===== Defaults / lifecycle =====

    public void setDefaults(String symbol, Integer magic, Double deviationPips, Double slippagePips, Double riskPercent) {
        if (symbol != null) defSymbol = symbol;
        if (magic != null) defMagic = magic;
        if (deviationPips != null) defDeviationPips = deviationPips;
        if (slippagePips != null) defSlippagePips = slippagePips;
        if (riskPercent != null) defRiskPercent = riskPercent;
    }

    /** A configured default by key: "symbol", "magic", "deviation_pips", "slippage_pips" or "risk_percent" (null if unset). */
    public Object getDefault(String key) {
        switch (key) {
            case "symbol": return defSymbol;
            case "magic": return defMagic;
            case "deviation_pips": return defDeviationPips;
            case "slippage_pips": return defSlippagePips;
            case "risk_percent": return defRiskPercent;
            default: return null;
        }
    }

    public void disconnect(boolean delete) {
        svc.disconnect(delete);
    }

    public void ensureConnected() {
        if (svc.getAccount().getId() == null && svc.getAccount().getServerName() == null) {
            throw new MT4Error("not connected: call a connect method first");
        }
    }

    public boolean ping() {
        try {
            svc.accountSummary();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void ensureSymbol(String symbol) {
        getSymbolParams(symbol, false);
    }

    // ===== Symbol params + market math =====

    private Mt4TermApiAccountHelper.SymbolParamsManyInfo getSymbolParams(String symbol, boolean forceRefresh) {
        String key = symbol.trim().toUpperCase();
        if (!forceRefresh) {
            Mt4TermApiAccountHelper.SymbolParamsManyInfo cached = symCache.get(key);
            if (cached != null) return cached;
        }
        Mt4TermApiAccountHelper.SymbolParamsManyData data = svc.symbolParamsMany(symbol);
        Mt4TermApiAccountHelper.SymbolParamsManyInfo found = null;
        for (Mt4TermApiAccountHelper.SymbolParamsManyInfo info : data.getSymbolInfosList()) {
            if (info.getSymbolName() != null && info.getSymbolName().trim().toUpperCase().equals(key)) {
                found = info;
                break;
            }
        }
        if (found == null && data.getSymbolInfosCount() > 0) found = data.getSymbolInfos(0);
        if (found == null) throw new MT4Error("symbol not found: " + symbol);
        symCache.put(key, found);
        return found;
    }

    public double point(String symbol) {
        double p = getSymbolParams(symbol, false).getPoint();
        return p > 0 ? p : 1e-5;
    }

    public int digits(String symbol) {
        Mt4TermApiAccountHelper.SymbolParamsManyInfo p = getSymbolParams(symbol, false);
        if (p.getDigits() > 0) return p.getDigits();
        double pt = p.getPoint() > 0 ? p.getPoint() : 1e-5;
        return Math.max(0, (int) Math.round(-Math.log10(pt)));
    }

    public double pipSize(String symbol) {
        double pt = point(symbol);
        int d = digits(symbol);
        return (d == 3 || d == 5) ? pt * 10.0 : pt;
    }

    public double spreadPips(String symbol) {
        Mt4TermApiMarketInfo.QuoteData q = svc.quote(symbol);
        double pip = pipSize(symbol);
        if (pip == 0) throw new MT4Error("pip_size=0 for " + symbol);
        return (q.getAsk() - q.getBid()) / pip;
    }

    public double midPrice(String symbol) {
        Mt4TermApiMarketInfo.QuoteData q = svc.quote(symbol);
        return (q.getBid() + q.getAsk()) / 2.0;
    }

    private double lotStep(String symbol) {
        double s = getSymbolParams(symbol, false).getVolumeStep();
        return s > 0 ? s : 0.01;
    }

    public double pipsToPrice(String symbol, double pips) {
        return pips * pipSize(symbol);
    }

    public double priceToPips(String symbol, double priceDelta) {
        double pip = pipSize(symbol);
        return pip == 0 ? 0.0 : priceDelta / pip;
    }

    private static double roundToGrid(double value, double step) {
        if (step <= 0) return value;
        return Math.round(value / step) * step;
    }

    public double normalizePrice(String symbol, double price) {
        return roundToGrid(price, point(symbol));
    }

    public double normalizeLot(String symbol, double lots) {
        Mt4TermApiAccountHelper.SymbolParamsManyInfo p = getSymbolParams(symbol, false);
        double v = roundToGrid(lots, p.getVolumeStep() > 0 ? p.getVolumeStep() : 0.01);
        if (p.getVolumeMin() > 0) v = Math.max(v, p.getVolumeMin());
        if (p.getVolumeMax() > 0) v = Math.min(v, p.getVolumeMax());
        return v;
    }

    // ===== Quotes / history =====

    public static final class Quote {
        public final double bid, ask;
        public final Instant time;
        public Quote(double bid, double ask, Instant time) { this.bid = bid; this.ask = ask; this.time = time; }
    }

    public static final class Bar {
        public final long time; // epoch seconds
        public final double open, high, low, close;
        public final long volume;
        public Bar(long time, double open, double high, double low, double close, long volume) {
            this.time = time; this.open = open; this.high = high; this.low = low; this.close = close; this.volume = volume;
        }
    }

    public static final class Tick {
        public final long time; // epoch seconds
        public final double bid, ask;
        public Tick(long time, double bid, double ask) { this.time = time; this.bid = bid; this.ask = ask; }
    }

    public Quote lastQuote(String symbol) {
        ensureSymbol(symbol);
        Mt4TermApiMarketInfo.QuoteData q = svc.quote(symbol);
        return new Quote(q.getBid(), q.getAsk(), toInstant(q.getDateTime()));
    }

    public Map<String, Quote> quotes(List<String> symbols) {
        Map<String, Quote> out = new LinkedHashMap<>();
        if (symbols == null || symbols.isEmpty()) return out;
        Mt4TermApiMarketInfo.QuoteManyData data = svc.quoteMany(symbols);
        for (Mt4TermApiMarketInfo.QuoteData q : data.getQuotesList()) {
            String sym = q.getSymbol();
            if (sym == null || sym.isEmpty()) continue;
            out.put(sym.toUpperCase(), new Quote(q.getBid(), q.getAsk(), toInstant(q.getDateTime())));
        }
        return out;
    }

    public double waitPrice(String symbol, double target, String direction, Long timeoutMs) {
        ensureSymbol(symbol);
        long start = System.currentTimeMillis();
        while (true) {
            Mt4TermApiMarketInfo.QuoteData q = svc.quote(symbol);
            double mid = (q.getBid() + q.getAsk()) / 2.0;
            boolean hit;
            switch (direction) {
                case ">=": hit = mid >= target; break;
                case "<=": hit = mid <= target; break;
                case ">": hit = mid > target; break;
                case "<": hit = mid < target; break;
                default: throw new IllegalArgumentException("direction must be >=, <=, > or <");
            }
            if (hit) return mid;
            if (timeoutMs != null && (System.currentTimeMillis() - start) > timeoutMs) {
                throw new MT4Error("wait_price timeout for " + symbol + ": target=" + target + " dir=" + direction);
            }
            sleep(250);
        }
    }

    public List<Bar> bars(String symbol, String timeframe, Integer count, Instant since, Instant until) {
        ensureSymbol(symbol);
        Mt4TermApiMarketInfo.QuoteHistoryData data = svc.quoteHistory(symbol, timeframe, since, until, count);
        List<Bar> out = new ArrayList<>();
        for (Mt4TermApiMarketInfo.HistoryQuote h : data.getHistoricalQuotesList()) {
            out.add(new Bar(h.getTime().getSeconds(), h.getOpen(), h.getHigh(), h.getLow(), h.getClose(), h.getTickVolume()));
        }
        if (count != null && count > 0 && out.size() > count) {
            out = new ArrayList<>(out.subList(out.size() - count, out.size()));
        }
        return out;
    }

    /** Recent "ticks" derived from the quote-history candles (bid=ask=close); for a quick price series. */
    public List<Tick> ticks(String symbol, String timeframe, Integer limit) {
        ensureSymbol(symbol);
        Mt4TermApiMarketInfo.QuoteHistoryData data = svc.quoteHistory(symbol, timeframe != null ? timeframe : "M1", null, null, limit);
        List<Tick> out = new ArrayList<>();
        for (Mt4TermApiMarketInfo.HistoryQuote h : data.getHistoricalQuotesList()) {
            out.add(new Tick(h.getTime().getSeconds(), h.getClose(), h.getClose()));
        }
        if (limit != null && limit > 0 && out.size() > limit) {
            out = new ArrayList<>(out.subList(out.size() - limit, out.size()));
        }
        return out;
    }

    // ===== Risk & sizing =====

    private double moneyPerPipForLots(String symbol, double lots) {
        double pip = pipSize(symbol);
        double pt = point(symbol);
        if (pip <= 0 || pt <= 0) return 0.0;
        Mt4TermApiAccountHelper.TickValueWithSizeData r = svc.tickValueWithSize(List.of(symbol));
        if (r.getInfosCount() == 0) throw new MT4Error("no tick value info for " + symbol);
        double tickValPerLot = r.getInfos(0).getTradeTickValue();
        return (tickValPerLot * lots) * (pip / pt);
    }

    public double calcLotByRisk(String symbol, double riskPercent, double stopPips, Double balance) {
        if (stopPips <= 0) throw new IllegalArgumentException("stop_pips must be > 0");
        double cash = balance != null ? balance : svc.accountSummary().getAccountBalance();
        double cashRisk = cash * (riskPercent / 100.0);
        double mpp1 = moneyPerPipForLots(symbol, 1.0);
        if (mpp1 <= 0) return 0.0;
        return normalizeLot(symbol, cashRisk / (mpp1 * stopPips));
    }

    public double calcCashRisk(String symbol, double lots, double stopPips) {
        if (lots <= 0 || stopPips <= 0) return 0.0;
        return moneyPerPipForLots(symbol, lots) * stopPips;
    }

    public double tickValue(String symbol, double lots) {
        ensureSymbol(symbol);
        Mt4TermApiAccountHelper.TickValueWithSizeData r = svc.tickValueWithSize(List.of(symbol));
        double v = r.getInfosCount() > 0 ? r.getInfos(0).getTradeTickValue() : 1.0;
        return v * lots;
    }

    public double breakevenPrice(double entryPrice, double commission, double swap) {
        return entryPrice + commission + swap;
    }

    // ===== Exposure =====

    public static final class ExposureBucket {
        public double lotsNet, lotsLong, lotsShort, pnl, margin;
    }

    public static final class ExposureSummary {
        public double balance, equity, margin, freeMargin;
        public int openPositions;
        public final Map<String, ExposureBucket> bySymbol = new LinkedHashMap<>();
        public final ExposureBucket total = new ExposureBucket();
    }

    public ExposureSummary exposureSummary() {
        ExposureSummary s = new ExposureSummary();
        Mt4TermApiAccountHelper.AccountSummaryData acc = svc.accountSummary();
        s.balance = acc.getAccountBalance();
        s.equity = acc.getAccountEquity();
        List<Mt4TermApiAccountHelper.OpenedOrderInfo> orders = svc.openedOrders().getOrderInfosList();
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : orders) {
            String sym = o.getSymbol() == null ? "UNKNOWN" : o.getSymbol().toUpperCase();
            double lots = o.getLots();
            double pnl = o.getProfit();
            double sgn = isBuy(o.getOrderType()) ? 1.0 : -1.0;
            ExposureBucket b = s.bySymbol.computeIfAbsent(sym, k -> new ExposureBucket());
            b.pnl += pnl;
            if (sgn > 0) { b.lotsLong += lots; b.lotsNet += lots; } else { b.lotsShort += lots; b.lotsNet -= lots; }
            s.total.pnl += pnl;
            if (sgn > 0) { s.total.lotsLong += lots; s.total.lotsNet += lots; } else { s.total.lotsShort += lots; s.total.lotsNet -= lots; }
        }
        s.openPositions = orders.size();
        return s;
    }

    // ===== Trading =====

    public long placeOrder(String side, String orderType, String symbol, Double price, Double lots,
                           Double slPips, Double tpPips, Double slPrice, Double tpPrice,
                           String comment, Integer magic, Double deviationPips) {
        ensureConnected();
        String sym = symbol != null ? symbol : defSymbol;
        if (sym == null) throw new IllegalArgumentException("symbol must be provided (no default configured)");
        ensureSymbol(sym);
        String s = side.toLowerCase();
        String t = orderType.toLowerCase();
        Integer mgc = magic != null ? magic : defMagic;
        Double dev = deviationPips != null ? deviationPips : defDeviationPips;
        double vol = normalizeLot(sym, lots != null ? lots : 0.01);

        double entry;
        if (t.equals("market")) {
            Mt4TermApiMarketInfo.QuoteData q = svc.quote(sym);
            entry = s.equals("buy") ? q.getAsk() : q.getBid();
        } else {
            if (price == null) throw new IllegalArgumentException("price is required for limit/stop orders");
            entry = normalizePrice(sym, price);
        }

        Double slAbs = slPrice;
        Double tpAbs = tpPrice;
        if (slAbs == null && slPips != null) {
            double d = pipsToPrice(sym, slPips);
            slAbs = s.equals("buy") ? entry - d : entry + d;
        }
        if (tpAbs == null && tpPips != null) {
            double d = pipsToPrice(sym, tpPips);
            tpAbs = s.equals("buy") ? entry + d : entry - d;
        }
        if (slAbs != null) slAbs = normalizePrice(sym, slAbs);
        if (tpAbs != null) tpAbs = normalizePrice(sym, tpAbs);

        Mt4TermApiTradingHelper.OrderSendRequest.Builder b = Mt4TermApiTradingHelper.OrderSendRequest.newBuilder()
                .setSymbol(sym).setOperationType(MT4Service.toOperationType(s, t)).setVolume(vol);
        if (!t.equals("market")) b.setPrice(entry);
        if (slAbs != null) b.setStoploss(slAbs);
        if (tpAbs != null) b.setTakeprofit(tpAbs);
        if (dev != null) b.setSlippage((int) Math.round(dev));
        if (mgc != null) b.setMagicNumber(mgc);
        if (comment != null) b.setComment(comment);

        orderSendRl.acquire();
        long ticket;
        try {
            ticket = svc.orderSend(b.build()).getTicket();
        } catch (Exception e) {
            throw new OrderRejected("order_send: " + e.getMessage(), null, null, e);
        }
        hooks.emit("on_order_sent", mapOf("ticket", ticket, "symbol", sym));
        return ticket;
    }

    public long buyMarket(String symbol, Double lots, Double slPips, Double tpPips, Double slPrice, Double tpPrice, String comment, Integer magic, Double deviationPips) {
        return placeOrder("buy", "market", symbol, null, lots, slPips, tpPips, slPrice, tpPrice, comment, magic, deviationPips);
    }

    public long sellMarket(String symbol, Double lots, Double slPips, Double tpPips, Double slPrice, Double tpPrice, String comment, Integer magic, Double deviationPips) {
        return placeOrder("sell", "market", symbol, null, lots, slPips, tpPips, slPrice, tpPrice, comment, magic, deviationPips);
    }

    public long buyLimit(String symbol, double price, Double lots, Double slPips, Double tpPips, Double slPrice, Double tpPrice, String comment, Integer magic) {
        return placeOrder("buy", "limit", symbol, price, lots, slPips, tpPips, slPrice, tpPrice, comment, magic, null);
    }

    public long sellLimit(String symbol, double price, Double lots, Double slPips, Double tpPips, Double slPrice, Double tpPrice, String comment, Integer magic) {
        return placeOrder("sell", "limit", symbol, price, lots, slPips, tpPips, slPrice, tpPrice, comment, magic, null);
    }

    public long buyStop(String symbol, double price, Double lots, Double slPips, Double tpPips, Double slPrice, Double tpPrice, String comment, Integer magic) {
        return placeOrder("buy", "stop", symbol, price, lots, slPips, tpPips, slPrice, tpPrice, comment, magic, null);
    }

    public long sellStop(String symbol, double price, Double lots, Double slPips, Double tpPips, Double slPrice, Double tpPrice, String comment, Integer magic) {
        return placeOrder("sell", "stop", symbol, price, lots, slPips, tpPips, slPrice, tpPrice, comment, magic, null);
    }

    // ===== Modify / close =====

    public void modifySlTpByPips(int ticket, Double slPips, Double tpPips) {
        Mt4TermApiAccountHelper.OpenedOrderInfo o = orderByTicket(ticket);
        if (o == null) throw new IllegalArgumentException("order not found by ticket " + ticket);
        String symbol = o.getSymbol().toUpperCase();
        boolean buy = isBuy(o.getOrderType());
        double entry = o.getOpenPrice();
        Double slAbs = null, tpAbs = null;
        if (slPips != null) {
            double d = pipsToPrice(symbol, slPips);
            slAbs = normalizePrice(symbol, buy ? entry - d : entry + d);
        }
        if (tpPips != null) {
            double d = pipsToPrice(symbol, tpPips);
            tpAbs = normalizePrice(symbol, buy ? entry + d : entry - d);
        }
        doModify(ticket, slAbs, tpAbs);
    }

    public void modifySlTpByPrice(int ticket, Double slPrice, Double tpPrice) {
        Mt4TermApiAccountHelper.OpenedOrderInfo o = orderByTicket(ticket);
        if (o == null) throw new IllegalArgumentException("order not found by ticket " + ticket);
        String symbol = o.getSymbol().toUpperCase();
        Double slAbs = slPrice != null ? normalizePrice(symbol, slPrice) : null;
        Double tpAbs = tpPrice != null ? normalizePrice(symbol, tpPrice) : null;
        doModify(ticket, slAbs, tpAbs);
    }

    public void close(int ticket) {
        try {
            svc.orderCloseDelete(ticket, null, null, null);
        } catch (Exception e) {
            throw new ModifyRejected("order_close_delete: " + e.getMessage(), null, null, e);
        }
        hooks.emit("on_close", mapOf("ticket", (long) ticket));
    }

    public void closePartial(int ticket, double lots) {
        if (lots <= 0) return;
        svc.orderCloseDelete(ticket, lots, null, null);
        hooks.emit("on_close", mapOf("ticket", (long) ticket, "lots", lots, "partial", true));
    }

    public void closeBy(int ticketA, int ticketB) {
        try {
            svc.orderCloseBy(ticketA, ticketB);
        } catch (Exception e) {
            throw new ModifyRejected("order_close_by: " + e.getMessage(), null, null, e);
        }
        hooks.emit("on_close_by", mapOf("ticket_a", (long) ticketA, "ticket_b", (long) ticketB));
    }

    /** Hedge close-by (alias of {@link #closeBy}), mirroring the Python {@code close_by_tickets}. */
    public void closeByTickets(int ticketA, int ticketB) {
        try {
            svc.orderCloseBy(ticketA, ticketB);
        } catch (Exception e) {
            throw new ModifyRejected("order_close_by: " + e.getMessage(), null, null, e);
        }
        hooks.emit("on_close", mapOf("ticket_a", (long) ticketA, "ticket_b", (long) ticketB, "close_by", true));
    }

    public void closeAll(String symbol, Integer magic, Boolean onlyProfit) {
        List<Mt4TermApiAccountHelper.OpenedOrderInfo> orders = svc.openedOrders().getOrderInfosList();
        String symUp = symbol != null ? symbol.toUpperCase() : null;
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : orders) {
            if (symUp != null && !symUp.equals(o.getSymbol().toUpperCase())) continue;
            if (magic != null && o.getMagicNumber() != magic) continue;
            if (Boolean.TRUE.equals(onlyProfit) && o.getProfit() <= 0) continue;
            if (Boolean.FALSE.equals(onlyProfit) && o.getProfit() >= 0) continue;
            int t = o.getTicket();
            svc.orderCloseDelete(t, null, null, null);
            hooks.emit("on_close", mapOf("ticket", (long) t));
        }
    }

    public void cancelPendings(String symbol, Integer magic) {
        List<Mt4TermApiAccountHelper.OpenedOrderInfo> orders = svc.openedOrders().getOrderInfosList();
        String symUp = symbol != null ? symbol.toUpperCase() : null;
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : orders) {
            if (!isPending(o.getOrderType())) continue;
            if (symUp != null && !symUp.equals(o.getSymbol().toUpperCase())) continue;
            if (magic != null && o.getMagicNumber() != magic) continue;
            int t = o.getTicket();
            svc.orderCloseDelete(t, null, null, null);
            hooks.emit("on_close", mapOf("ticket", (long) t, "pending", true));
        }
    }

    public void closeByBreakeven(int ticket, double plusPips) {
        Mt4TermApiAccountHelper.OpenedOrderInfo o = orderByTicket(ticket);
        if (o == null) throw new IllegalArgumentException("order not found by ticket " + ticket);
        String symbol = o.getSymbol().toUpperCase();
        boolean buy = isBuy(o.getOrderType());
        double delta = pipsToPrice(symbol, plusPips);
        double be = normalizePrice(symbol, buy ? o.getOpenPrice() + delta : o.getOpenPrice() - delta);
        doModify(ticket, be, null);
    }

    // ===== Automation (trailing / auto-breakeven) =====

    /** Start a trailing-stop worker for the ticket; returns a subscription id to pass to {@link #unsetTrailingStop}. */
    public String setTrailingStop(int ticket, double distancePips, Double stepPips) {
        if (distancePips <= 0) throw new IllegalArgumentException("distance_pips must be > 0");
        String subId = "trail-" + shortId();
        AtomicBoolean stop = new AtomicBoolean(false);
        Thread th = new Thread(() -> trailingLoop(ticket, distancePips, stepPips, stop), subId);
        th.setDaemon(true);
        workers.put(subId, () -> { stop.set(true); th.interrupt(); });
        th.start();
        return subId;
    }

    private void trailingLoop(int ticket, double distancePips, Double stepPips, AtomicBoolean stop) {
        while (!stop.get()) {
            Mt4TermApiAccountHelper.OpenedOrderInfo o = orderByTicket(ticket);
            if (o == null) return;
            String symbol = o.getSymbol().toUpperCase();
            boolean buy = isBuy(o.getOrderType());
            double mid;
            try {
                Mt4TermApiMarketInfo.QuoteData q = svc.quote(symbol);
                mid = (q.getBid() + q.getAsk()) / 2.0;
            } catch (Exception e) {
                return;
            }
            double delta = pipsToPrice(symbol, distancePips);
            double desiredSl = normalizePrice(symbol, buy ? mid - delta : mid + delta);
            double curSl = o.getStopLoss();
            boolean hasSl = curSl != 0.0;
            boolean need = !hasSl || (buy ? desiredSl > curSl : desiredSl < curSl);
            if (need && stepPips != null && hasSl && Math.abs(desiredSl - curSl) < pipsToPrice(symbol, stepPips)) need = false;
            if (need) {
                try {
                    svc.orderModify(ticket, null, desiredSl, null, null);
                    hooks.emit("on_modify", mapOf("ticket", (long) ticket, "sl", desiredSl, "trailing", true));
                } catch (Exception e) {
                    return;
                }
            }
            sleep(300);
        }
    }

    public void unsetTrailingStop(String subscriptionId) {
        unwatch(subscriptionId);
    }

    /** Start an auto-breakeven worker; moves SL to entry (+plusPips) once price moves triggerPips in profit. */
    public String autoBreakeven(int ticket, double triggerPips, double plusPips) {
        if (triggerPips <= 0) throw new IllegalArgumentException("trigger_pips must be > 0");
        String subId = "breakeven-" + shortId();
        AtomicBoolean stop = new AtomicBoolean(false);
        Thread th = new Thread(() -> autoBreakevenLoop(ticket, triggerPips, plusPips, stop), subId);
        th.setDaemon(true);
        workers.put(subId, () -> { stop.set(true); th.interrupt(); });
        th.start();
        return subId;
    }

    private void autoBreakevenLoop(int ticket, double triggerPips, double plusPips, AtomicBoolean stop) {
        Mt4TermApiAccountHelper.OpenedOrderInfo o = orderByTicket(ticket);
        if (o == null) return;
        String symbol = o.getSymbol().toUpperCase();
        boolean buy = isBuy(o.getOrderType());
        double entry = o.getOpenPrice();
        while (entry == 0.0 && !stop.get()) {
            o = orderByTicket(ticket);
            if (o == null) return;
            entry = o.getOpenPrice();
            if (entry == 0.0) sleep(200);
        }
        double triggerDelta = pipsToPrice(symbol, triggerPips);
        double targetSl = normalizePrice(symbol, buy ? entry + pipsToPrice(symbol, plusPips) : entry - pipsToPrice(symbol, plusPips));
        while (!stop.get()) {
            if (orderByTicket(ticket) == null) return;
            double mid;
            try {
                Mt4TermApiMarketInfo.QuoteData q = svc.quote(symbol);
                mid = (q.getBid() + q.getAsk()) / 2.0;
            } catch (Exception e) {
                return;
            }
            boolean moved = buy ? (mid - entry) >= triggerDelta : (entry - mid) >= triggerDelta;
            if (moved) {
                try {
                    svc.orderModify(ticket, null, targetSl, null, null);
                    hooks.emit("on_modify", mapOf("ticket", (long) ticket, "sl", targetSl, "auto_be", true));
                } catch (Exception ignored) {
                }
                return;
            }
            sleep(250);
        }
    }

    // ===== Watches (stream subscriptions) =====

    public String watchTicks(String symbol, Consumer<Mt4TermApiSubscriptions.OnSymbolTickData> onTick, Consumer<Throwable> onError) {
        ensureSymbol(symbol);
        String subId = "ticks-" + symbol.toUpperCase() + "-" + shortId();
        workers.put(subId, svc.onSymbolTick(List.of(symbol), onTick, onError != null ? onError : t -> { }));
        return subId;
    }

    public String watchTrades(Consumer<Mt4TermApiSubscriptions.OnTradeData> onEvent, Consumer<Throwable> onError) {
        String subId = "trades-" + shortId();
        workers.put(subId, svc.onTrade(onEvent, onError != null ? onError : t -> { }));
        return subId;
    }

    public String watchOpenedOrders(Consumer<Mt4TermApiSubscriptions.OnOpenedOrdersTicketsData> onEvent, Consumer<Throwable> onError) {
        String subId = "opened-" + shortId();
        workers.put(subId, svc.onOpenedOrdersTickets(500, onEvent, onError != null ? onError : t -> { }));
        return subId;
    }

    public String watchProfit(Consumer<Mt4TermApiSubscriptions.OnOpenedOrdersProfitData> onEvent, int timerPeriodMs, Consumer<Throwable> onError) {
        String subId = "profit-" + shortId();
        workers.put(subId, svc.onOpenedOrdersProfit(timerPeriodMs, onEvent, onError != null ? onError : t -> { }));
        return subId;
    }

    public String watchTickets(Consumer<Mt4TermApiSubscriptions.OnOpenedOrdersTicketsData> onEvent, int pullIntervalMs, Consumer<Throwable> onError) {
        String subId = "tickets-" + shortId();
        workers.put(subId, svc.onOpenedOrdersTickets(pullIntervalMs, onEvent, onError != null ? onError : t -> { }));
        return subId;
    }

    /** Cancel a subscription/worker created by any {@code watch*}/trailing/auto-breakeven method. */
    public void unwatch(String subscriptionId) {
        AutoCloseable c = workers.remove(subscriptionId);
        if (c != null) {
            try {
                c.close();
            } catch (Exception ignored) {
            }
        }
    }

    // ===== Awaiters =====

    /** Block until the order is no longer pending (filled); returns the order, or throws on timeout. */
    public Mt4TermApiAccountHelper.OpenedOrderInfo waitFilled(int ticket, Long timeoutMs) {
        long start = System.currentTimeMillis();
        while (true) {
            Mt4TermApiAccountHelper.OpenedOrderInfo o = orderByTicket(ticket);
            if (o != null && !isPending(o.getOrderType())) return o;
            if (timeoutMs != null && (System.currentTimeMillis() - start) > timeoutMs) throw new MT4Error("wait_filled timeout for ticket " + ticket);
            sleep(200);
        }
    }

    /** Block until the order disappears from opened orders (closed); returns a small {ticket,status,symbol,lots} map. */
    public Map<String, Object> waitClosed(int ticket, Long timeoutMs) {
        long start = System.currentTimeMillis();
        Mt4TermApiAccountHelper.OpenedOrderInfo last = orderByTicket(ticket);
        while (true) {
            Mt4TermApiAccountHelper.OpenedOrderInfo cur = orderByTicket(ticket);
            if (cur == null) {
                Map<String, Object> info = mapOf("ticket", (long) ticket, "status", "closed");
                if (last != null) {
                    info.put("symbol", last.getSymbol());
                    info.put("lots", last.getLots());
                }
                return info;
            }
            last = cur;
            if (timeoutMs != null && (System.currentTimeMillis() - start) > timeoutMs) throw new MT4Error("wait_closed timeout for ticket " + ticket);
            sleep(250);
        }
    }

    // ===== Queries =====

    /** Opened orders filtered; {@code state} = "open" (market only), "pending" (pending only) or null (all). */
    public List<Mt4TermApiAccountHelper.OpenedOrderInfo> findOrders(String symbol, Integer magic, String side, String state, Double minProfit) {
        String symUp = symbol != null ? symbol.toUpperCase() : null;
        String sideNorm = side != null ? side.toLowerCase() : null;
        List<Mt4TermApiAccountHelper.OpenedOrderInfo> out = new ArrayList<>();
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : svc.openedOrders().getOrderInfosList()) {
            if (symUp != null && !symUp.equals(o.getSymbol().toUpperCase())) continue;
            if (magic != null && o.getMagicNumber() != magic) continue;
            if (sideNorm != null && !sideNorm.equals(isBuy(o.getOrderType()) ? "buy" : "sell")) continue;
            if (minProfit != null && o.getProfit() < minProfit) continue;
            if ("open".equals(state) && isPending(o.getOrderType())) continue;
            if ("pending".equals(state) && !isPending(o.getOrderType())) continue;
            out.add(o);
        }
        return out;
    }

    public ExposureBucket positionsValue(String symbol, Integer magic) {
        String symUp = symbol != null ? symbol.toUpperCase() : null;
        ExposureBucket total = new ExposureBucket();
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : svc.openedOrders().getOrderInfosList()) {
            if (isPending(o.getOrderType())) continue;
            if (symUp != null && !symUp.equals(o.getSymbol().toUpperCase())) continue;
            if (magic != null && o.getMagicNumber() != magic) continue;
            double lots = o.getLots();
            if (isBuy(o.getOrderType())) { total.lotsLong += lots; total.lotsNet += lots; }
            else { total.lotsShort += lots; total.lotsNet -= lots; }
            total.pnl += o.getProfit();
        }
        return total;
    }

    public List<Mt4TermApiAccountHelper.OpenedOrderInfo> ordersForSymbol(String symbol) {
        String symUp = symbol.toUpperCase();
        List<Mt4TermApiAccountHelper.OpenedOrderInfo> out = new ArrayList<>();
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : svc.openedOrders().getOrderInfosList()) {
            if (symUp.equals(o.getSymbol().toUpperCase())) out.add(o);
        }
        return out;
    }

    public Map<String, Object> diagSnapshot() {
        ensureConnected();
        Mt4TermApiAccountHelper.AccountSummaryData acc = svc.accountSummary();
        Map<String, Object> account = mapOf("balance", acc.getAccountBalance(), "equity", acc.getAccountEquity());
        List<Mt4TermApiAccountHelper.OpenedOrderInfo> opened = svc.openedOrders().getOrderInfosList();
        ExposureSummary exposure = exposureSummary();
        TreeSet<String> symbols = new TreeSet<>();
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : opened) {
            if (o.getSymbol() != null && !o.getSymbol().isEmpty()) symbols.add(o.getSymbol().toUpperCase());
        }
        if (defSymbol != null) symbols.add(defSymbol.toUpperCase());
        Map<String, Object> spreads = new LinkedHashMap<>();
        for (String s : symbols) {
            try {
                Quote q = lastQuote(s);
                spreads.put(s, mapOf("bid", q.bid, "ask", q.ask, "spread_pips", spreadPips(s)));
            } catch (Exception ignored) {
            }
        }
        return mapOf("generated_at_ms", System.currentTimeMillis(), "account", account,
                "exposure", exposure, "open_orders_count", opened.size(), "spreads", spreads);
    }

    // ===== helpers =====

    private static String shortId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    private void doModify(int ticket, Double slAbs, Double tpAbs) {
        try {
            svc.orderModify(ticket, null, slAbs, tpAbs, null);
        } catch (Exception e) {
            throw new ModifyRejected("order_modify: " + e.getMessage(), null, null, e);
        }
        hooks.emit("on_modify", mapOf("ticket", (long) ticket, "sl", slAbs, "tp", tpAbs));
    }

    private Mt4TermApiAccountHelper.OpenedOrderInfo orderByTicket(int ticket) {
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : svc.openedOrders().getOrderInfosList()) {
            if (o.getTicket() == ticket) return o;
        }
        return null;
    }

    private static boolean isBuy(Mt4TermApiAccountHelper.OpenedOrderType type) {
        switch (type) {
            case OO_OP_BUY:
            case OO_OP_BUYLIMIT:
            case OO_OP_BUYSTOP:
                return true;
            default:
                return false;
        }
    }

    private static boolean isPending(Mt4TermApiAccountHelper.OpenedOrderType type) {
        switch (type) {
            case OO_OP_BUYLIMIT:
            case OO_OP_BUYSTOP:
            case OO_OP_SELLLIMIT:
            case OO_OP_SELLSTOP:
                return true;
            default:
                return false;
        }
    }

    private static Instant toInstant(Timestamp ts) {
        return ts == null ? null : Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos());
    }

    private static Map<String, Object> mapOf(Object... kv) {
        Map<String, Object> m = new LinkedHashMap<>();
        for (int i = 0; i + 1 < kv.length; i += 2) m.put(String.valueOf(kv[i]), kv[i + 1]);
        return m;
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
