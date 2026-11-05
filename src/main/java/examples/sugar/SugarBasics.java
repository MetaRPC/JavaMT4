package examples.sugar;

import pro.mrpc.mt4.MT4Sugar;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * HIGH-LEVEL · Basics.
 *
 * <p>{@link MT4Sugar} adds trader-friendly maths on top of the service: pips, normalization, risk sizing.
 * This example reads only — no orders. Run: {@code run.bat 6}.</p>
 */
public final class SugarBasics {
    public static void run(MT4Sugar sugar, String symbol) {
        // setDefaults(): remember a symbol/magic/deviation/risk% so later calls can omit them.
        sugar.setDefaults(symbol, 2001, 3.0, null, 1.0);
        // getDefault(): read back a stored default by name.
        System.out.printf(Locale.ROOT, "defaults         : symbol=%s magic=%s deviation=%s risk%%=%s%n",
                sugar.getDefault("symbol"), sugar.getDefault("magic"),
                sugar.getDefault("deviation_pips"), sugar.getDefault("risk_percent"));
        // ensureConnected()/ping(): verify the session is alive; ensureSymbol(): load & cache the symbol's params.
        sugar.ensureConnected();
        System.out.println("ping             : " + sugar.ping());
        sugar.ensureSymbol(symbol);

        // ── Pricing maths ────────────────────────────────────────
        // point = smallest price step; digits = decimals; pip = 1 pip in price (10×point on 3/5-digit symbols).
        System.out.printf(Locale.ROOT, "point=%.5f digits=%d pip=%.5f spread=%.1f pips mid=%.5f%n",
                sugar.point(symbol), sugar.digits(symbol), sugar.pipSize(symbol), sugar.spreadPips(symbol), sugar.midPrice(symbol));
        // Convert between pips and absolute price moves.
        System.out.printf(Locale.ROOT, "pipsToPrice(50)=%.5f  priceToPips(0.0050)=%.1f%n",
                sugar.pipsToPrice(symbol, 50), sugar.priceToPips(symbol, 0.0050));
        // Round a price to the symbol grid, and a lot size to the broker's volume step/limits.
        System.out.printf(Locale.ROOT, "normalizePrice(1.234567)=%.5f  normalizeLot(0.037)=%.2f%n",
                sugar.normalizePrice(symbol, 1.234567), sugar.normalizeLot(symbol, 0.037));

        // ── Quotes & history ─────────────────────────────────────
        // lastQuote(): a clean {bid, ask, time} snapshot.
        MT4Sugar.Quote q = sugar.lastQuote(symbol);
        System.out.printf(Locale.ROOT, "lastQuote        : %.5f/%.5f%n", q.bid, q.ask);
        // quotes(): a map of {symbol -> quote} for several symbols.
        Map<String, MT4Sugar.Quote> qs = sugar.quotes(List.of(symbol, "GBPUSD"));
        qs.forEach((k, v) -> System.out.printf(Locale.ROOT, "quotes[%s]       : %.5f/%.5f%n", k, v.bid, v.ask));

        // bars(): recent OHLC candles; ticks(): a quick price series derived from candle closes.
        List<MT4Sugar.Bar> bars = sugar.bars(symbol, "H1", 5, null, null);
        System.out.println("bars(H1,5)       : " + bars.size() + (bars.isEmpty() ? "" :
                String.format(Locale.ROOT, "  last close %.5f", bars.get(bars.size() - 1).close)));
        System.out.println("ticks(M5,5)      : " + sugar.ticks(symbol, "M5", 5).size());

        // waitPrice(): block until the price crosses a target (here a target just below mid, so it returns at once).
        double target = sugar.midPrice(symbol) - sugar.pipsToPrice(symbol, 10);
        System.out.printf(Locale.ROOT, "waitPrice(>= mid-10p): %.5f%n", sugar.waitPrice(symbol, target, ">=", 5000L));

        // ── Risk & sizing ────────────────────────────────────────
        // calcLotByRisk: lot that risks N% of equity over a given stop; calcCashRisk: $ risk of a given lot/stop.
        System.out.printf(Locale.ROOT, "calcLotByRisk(1%%,50p)=%.2f  calcCashRisk(0.10,50p)=%.2f%n",
                sugar.calcLotByRisk(symbol, 1.0, 50, null), sugar.calcCashRisk(symbol, 0.10, 50));
        // tickValue: money per tick for a lot size; breakevenPrice: entry adjusted for commission/swap.
        System.out.printf(Locale.ROOT, "tickValue(0.10)=%.5f  breakevenPrice(1.1,+0.0001)=%.5f%n",
                sugar.tickValue(symbol, 0.10), sugar.breakevenPrice(1.10000, 0.0001, 0.0));
    }

    private SugarBasics() {
    }
}
