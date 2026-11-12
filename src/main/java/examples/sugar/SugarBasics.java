/*══════════════════════════════════════════════════════════════════════════════
 FILE: examples/sugar/SugarBasics.java - HIGH-LEVEL (MT4Sugar) BASICS DEMO
 PURPOSE:
   Show the trader-friendly maths MT4Sugar adds on top of the service: defaults,
   pricing/pip conversions, normalization, quotes/history and risk sizing. This
   example READS ONLY - it never places an order.

 🎯 WHO SHOULD USE THIS:
   • Developers starting with the high-level MT4Sugar API
   • Anyone needing pip/price/lot maths and risk sizing
   • Learning the smart-defaults pattern (setDefaults/getDefault)

 📚 WHAT THIS DEMO COVERS (4 sections):

   1. CONNECTION & DEFAULTS
      • setDefaults() / getDefault() - remember & read shared defaults
      • ensureConnected() / ping()   - verify the session is alive
      • ensureSymbol()               - load & cache a symbol's params

   2. PRICING MATHS
      • point() / digits() / pipSize() / spreadPips() / midPrice()
      • pipsToPrice() / priceToPips() - convert pips <-> price
      • normalizePrice() / normalizeLot() - round to broker grid/steps

   3. QUOTES & HISTORY
      • lastQuote() / quotes()  - clean Bid/Ask snapshot(s)
      • bars() / ticks()        - recent candles and a price series
      • waitPrice()             - block until price crosses a target

   4. RISK & SIZING
      • calcLotByRisk() / calcCashRisk() - position sizing by % / cash
      • tickValue() / breakevenPrice()   - money per tick, break-even

 🔄 API LEVELS:
   HIGH-LEVEL (MT4Sugar)   - THIS FILE: one-liner helpers with smart defaults
   MID-LEVEL  (MT4Service) - typed wrappers
   LOW-LEVEL  (MT4Account) - raw gRPC/protobuf replies

 ℹ️  SAFE: read-only maths and quotes - this demo never sends an order.

 USAGE:
   run.bat 6      (or: java -cp ... Program 6)
══════════════════════════════════════════════════════════════════════════════*/
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
        //region 1. CONNECTION & DEFAULTS
        // ══════════════════════════════════════════════════════════════
        // 1. CONNECTION & DEFAULTS
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // setDefaults() / getDefault()
        //   Remember a symbol/magic/deviation/risk% so later calls can omit
        //   them, then read a stored default back by name.
        // ────────────────────────────────────────────────────────────
        sugar.setDefaults(symbol, 2001, 3.0, null, 1.0);
        System.out.printf(Locale.ROOT, "defaults         : symbol=%s magic=%s deviation=%s risk%%=%s%n",
                sugar.getDefault("symbol"), sugar.getDefault("magic"),
                sugar.getDefault("deviation_pips"), sugar.getDefault("risk_percent"));

        // ────────────────────────────────────────────────────────────
        // ensureConnected() / ping() / ensureSymbol()
        //   Verify the session is alive, then load & cache the symbol's params.
        //   Returns: ping() -> a health value; the others just ensure state.
        // ────────────────────────────────────────────────────────────
        sugar.ensureConnected();
        System.out.println("ping             : " + sugar.ping());
        sugar.ensureSymbol(symbol);
        //endregion

        //region 2. PRICING MATHS
        // ══════════════════════════════════════════════════════════════
        // 2. PRICING MATHS
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // point() / digits() / pipSize() / spreadPips() / midPrice()
        //   point = smallest price step; digits = decimals; pip = 1 pip in
        //   price (10×point on 3/5-digit symbols).
        // ────────────────────────────────────────────────────────────
        System.out.printf(Locale.ROOT, "point=%.5f digits=%d pip=%.5f spread=%.1f pips mid=%.5f%n",
                sugar.point(symbol), sugar.digits(symbol), sugar.pipSize(symbol), sugar.spreadPips(symbol), sugar.midPrice(symbol));

        // ────────────────────────────────────────────────────────────
        // pipsToPrice() / priceToPips()
        //   Convert between pips and absolute price moves.
        //   Returns: a price delta / a pip count.
        // ────────────────────────────────────────────────────────────
        System.out.printf(Locale.ROOT, "pipsToPrice(50)=%.5f  priceToPips(0.0050)=%.1f%n",
                sugar.pipsToPrice(symbol, 50), sugar.priceToPips(symbol, 0.0050));

        // ────────────────────────────────────────────────────────────
        // normalizePrice() / normalizeLot()
        //   Round a price to the symbol grid, and a lot size to the broker's
        //   volume step/limits.
        // ────────────────────────────────────────────────────────────
        System.out.printf(Locale.ROOT, "normalizePrice(1.234567)=%.5f  normalizeLot(0.037)=%.2f%n",
                sugar.normalizePrice(symbol, 1.234567), sugar.normalizeLot(symbol, 0.037));
        //endregion

        //region 3. QUOTES & HISTORY
        // ══════════════════════════════════════════════════════════════
        // 3. QUOTES & HISTORY
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // lastQuote() / quotes()
        //   lastQuote(): a clean {bid, ask, time} snapshot.
        //   quotes(): a map of {symbol -> quote} for several symbols.
        // ────────────────────────────────────────────────────────────
        MT4Sugar.Quote q = sugar.lastQuote(symbol);
        System.out.printf(Locale.ROOT, "lastQuote        : %.5f/%.5f%n", q.bid, q.ask);
        Map<String, MT4Sugar.Quote> qs = sugar.quotes(List.of(symbol, "GBPUSD"));
        qs.forEach((k, v) -> System.out.printf(Locale.ROOT, "quotes[%s]       : %.5f/%.5f%n", k, v.bid, v.ask));

        // ────────────────────────────────────────────────────────────
        // bars() / ticks()
        //   bars(): recent OHLC candles; ticks(): a quick price series
        //   derived from candle closes.
        // ────────────────────────────────────────────────────────────
        List<MT4Sugar.Bar> bars = sugar.bars(symbol, "H1", 5, null, null);
        System.out.println("bars(H1,5)       : " + bars.size() + (bars.isEmpty() ? "" :
                String.format(Locale.ROOT, "  last close %.5f", bars.get(bars.size() - 1).close)));
        System.out.println("ticks(M5,5)      : " + sugar.ticks(symbol, "M5", 5).size());

        // ────────────────────────────────────────────────────────────
        // waitPrice()
        //   Block until the price crosses a target (here just below mid, so it
        //   returns at once).
        //   Returns: the price at which the condition was met.
        // ────────────────────────────────────────────────────────────
        double target = sugar.midPrice(symbol) - sugar.pipsToPrice(symbol, 10);
        System.out.printf(Locale.ROOT, "waitPrice(>= mid-10p): %.5f%n", sugar.waitPrice(symbol, target, ">=", 5000L));
        //endregion

        //region 4. RISK & SIZING
        // ══════════════════════════════════════════════════════════════
        // 4. RISK & SIZING
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // calcLotByRisk() / calcCashRisk()
        //   calcLotByRisk: lot that risks N% of equity over a given stop.
        //   calcCashRisk: the $ risk of a given lot/stop.
        // ────────────────────────────────────────────────────────────
        System.out.printf(Locale.ROOT, "calcLotByRisk(1%%,50p)=%.2f  calcCashRisk(0.10,50p)=%.2f%n",
                sugar.calcLotByRisk(symbol, 1.0, 50, null), sugar.calcCashRisk(symbol, 0.10, 50));

        // ────────────────────────────────────────────────────────────
        // tickValue() / breakevenPrice()
        //   tickValue: money per tick for a lot size.
        //   breakevenPrice: entry adjusted for commission/swap.
        // ────────────────────────────────────────────────────────────
        System.out.printf(Locale.ROOT, "tickValue(0.10)=%.5f  breakevenPrice(1.1,+0.0001)=%.5f%n",
                sugar.tickValue(symbol, 0.10), sugar.breakevenPrice(1.10000, 0.0001, 0.0));
        //endregion
    }

    private SugarBasics() {
    }
}
