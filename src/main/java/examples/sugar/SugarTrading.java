/*══════════════════════════════════════════════════════════════════════════════
 FILE: examples/sugar/SugarTrading.java - HIGH-LEVEL (MT4Sugar) TRADING DEMO
 PURPOSE:
   The full MT4Sugar order lifecycle with pip-based SL/TP: generic placeOrder,
   market/pending entries, modify, break-even, the close family, trailing /
   auto-break-even background workers and the wait-for-fill/closed awaiters.

 🎯 WHO SHOULD USE THIS:
   • Developers wiring up order execution with the high-level API
   • Learning the open -> manage -> close flow end to end
   • Prototyping trading logic quickly on a demo account

 📚 WHAT THIS DEMO COVERS (5 sections):

   1. MARKET ENTRY & LIFECYCLE
      • placeOrder() / sellMarket()       - generic & SELL market entries
      • waitFilled() / waitClosed()        - block until live / gone
      • modifySlTpByPips() / modifySlTpByPrice() - move SL/TP
      • closeByBreakeven() / close()       - risk-free, then flatten

   2. CLOSE BY OPPOSITE
      • buyMarket() + sellMarket()         - open an opposite pair
      • closeByTickets()                   - close one against the other
      • closeAll()                         - fallback bulk close

   3. PENDING ORDERS
      • buyLimit()                         - place a BUY LIMIT below market
      • cancelPendings()                   - remove pending orders

   4. AUTOMATION WORKERS (background)
      • setTrailingStop() / unsetTrailingStop()
      • autoBreakeven() / unwatch()

   5. PARTIAL & BULK CLOSE
      • closePartial()                     - close part of the volume
      • closeAll()                         - flatten everything

 🔄 API LEVELS:
   HIGH-LEVEL (MT4Sugar)   - THIS FILE: one-liner trading with smart defaults
   MID-LEVEL  (MT4Service) - typed wrappers
   LOW-LEVEL  (MT4Account) - raw order construction

 ⚠️  WARNING: sends REAL orders (0.01 lot) on your account - DEMO accounts only.
   Everything opened here is closed/cancelled before the demo returns.

 USAGE:
   run.bat 7      (or: java -cp ... Program 7)
══════════════════════════════════════════════════════════════════════════════*/
package examples.sugar;

import pro.mrpc.mt4.MT4Sugar;

import java.util.Locale;

/**
 * HIGH-LEVEL · Trading.
 *
 * <p>The full {@link MT4Sugar} order lifecycle with pip-based SL/TP on a DEMO account: generic {@code placeOrder},
 * market/pending entries, modify, breakeven, the close family, trailing/auto-breakeven workers and the
 * wait-for-fill/closed awaiters. Run: {@code run.bat 7}. ⚠️ Demo accounts only.</p>
 */
public final class SugarTrading {
    public static void run(MT4Sugar sugar, String symbol) throws Exception {
        // Default magic/deviation for all the orders below.
        sugar.setDefaults(symbol, 3001, 3.0, null, null);

        //region 1. MARKET ENTRY & LIFECYCLE
        // ══════════════════════════════════════════════════════════════
        // 1. MARKET ENTRY & LIFECYCLE
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // placeOrder() + waitFilled()
        //   placeOrder(): the generic entry (side, type, ...). Here a market
        //   BUY 0.01 with SL 50 pips / TP 100 pips. waitFilled() blocks until
        //   the order is live (market orders fill almost instantly).
        //   Returns: ticket (long).
        // ────────────────────────────────────────────────────────────
        long t1 = sugar.placeOrder("buy", "market", symbol, null, 0.01, 50.0, 100.0, null, null, "place", null, null);
        System.out.println("placeOrder buy   : #" + t1);
        sugar.waitFilled((int) t1, 10_000L);
        System.out.println("waitFilled       : #" + t1 + " live");

        // ────────────────────────────────────────────────────────────
        // modifySlTpByPips() / modifySlTpByPrice()
        //   Move SL/TP by a pip distance from entry, or set them at absolute
        //   prices instead.
        // ────────────────────────────────────────────────────────────
        sugar.modifySlTpByPips((int) t1, 40.0, 120.0);
        System.out.println("modifySlTpByPips : 40p / 120p");
        MT4Sugar.Quote q = sugar.lastQuote(symbol);
        sugar.modifySlTpByPrice((int) t1, q.bid - sugar.pipsToPrice(symbol, 60), q.ask + sugar.pipsToPrice(symbol, 140));
        System.out.println("modifySlTpByPrice: absolute SL/TP");

        // ────────────────────────────────────────────────────────────
        // closeByBreakeven() + close() + waitClosed()
        //   closeByBreakeven(): move SL to entry (+2 pips) to make the trade
        //   risk-free. Then flatten the position and confirm it's gone.
        // ────────────────────────────────────────────────────────────
        try {
            sugar.closeByBreakeven((int) t1, 2.0);
            System.out.println("closeByBreakeven : SL -> entry +2p");
        } catch (Exception e) {
            // Rejected when the position is not yet ~2 pips in profit: moving the SL
            // to entry+2p would put it on the wrong side / inside the broker's stop
            // level (ErrInvalidStops 130). Expected on a fresh entry — skip and go on.
            System.out.println("closeByBreakeven : skipped (not yet at break-even): " + e.getMessage());
        }
        Thread.sleep(500);
        sugar.close((int) t1);
        System.out.println("waitClosed       : " + sugar.waitClosed((int) t1, 10_000L).get("status"));

        // ────────────────────────────────────────────────────────────
        // sellMarket()
        //   The SELL counterpart of buyMarket; opened then closed right away.
        //   Returns: ticket (long).
        // ────────────────────────────────────────────────────────────
        long t2 = sugar.sellMarket(symbol, 0.01, 50.0, 100.0, null, null, "sell", null, null);
        System.out.println("sellMarket       : #" + t2);
        Thread.sleep(500);
        sugar.close((int) t2);
        //endregion

        //region 2. CLOSE BY OPPOSITE
        // ══════════════════════════════════════════════════════════════
        // 2. CLOSE BY OPPOSITE
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // closeByTickets() (with closeAll fallback)
        //   Open a BUY and a SELL, then close one against the other. Not all
        //   brokers allow close-by, so fall back to a bulk close on rejection.
        // ────────────────────────────────────────────────────────────
        long a = sugar.buyMarket(symbol, 0.01, null, null, null, null, "cb", null, null);
        long b = sugar.sellMarket(symbol, 0.01, null, null, null, null, "cb", null, null);
        Thread.sleep(500);
        try {
            sugar.closeByTickets((int) a, (int) b);
            System.out.println("closeByTickets   : #" + a + " by #" + b);
        } catch (Exception e) {
            // Not all brokers allow close-by — fall back to a bulk close.
            System.out.println("closeByTickets   : rejected — closeAll instead");
            sugar.closeAll(symbol, null, null);
        }
        //endregion

        //region 3. PENDING ORDERS
        // ══════════════════════════════════════════════════════════════
        // 3. PENDING ORDERS
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // buyLimit() + cancelPendings()
        //   Place a BUY LIMIT 100 pips below market, then cancel it
        //   (cancelPendings can be filtered by symbol/magic).
        //   Returns: ticket (long).
        // ────────────────────────────────────────────────────────────
        double entry = sugar.midPrice(symbol) - sugar.pipsToPrice(symbol, 100);
        long pending = sugar.buyLimit(symbol, entry, 0.01, 50.0, 100.0, null, null, "lim", null);
        System.out.printf(Locale.ROOT, "buyLimit @ %.5f : #%d%n", entry, pending);
        Thread.sleep(500);
        sugar.cancelPendings(symbol, null);
        System.out.println("cancelPendings   : cleared");
        //endregion

        //region 4. AUTOMATION WORKERS (background)
        // ══════════════════════════════════════════════════════════════
        // 4. AUTOMATION WORKERS (background)
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // setTrailingStop() / unsetTrailingStop() / autoBreakeven() / unwatch()
        //   Background workers run until stopped: trail the SL behind price,
        //   or move SL to break-even after a trigger. Each returns an id;
        //   unsetTrailingStop()/unwatch() stop it.
        // ────────────────────────────────────────────────────────────
        long t3 = sugar.buyMarket(symbol, 0.01, null, null, null, null, "auto", null, null);
        String trail = sugar.setTrailingStop((int) t3, 30, 10.0); // trail SL 30 pips behind, step 10 pips
        System.out.println("setTrailingStop  : " + trail);
        Thread.sleep(1500);
        sugar.unsetTrailingStop(trail); // stop the trailing worker
        String be = sugar.autoBreakeven((int) t3, 20, 2.0); // after +20 pips, move SL to entry +2
        System.out.println("autoBreakeven    : " + be);
        Thread.sleep(1000);
        sugar.unwatch(be); // unwatch() stops any worker/subscription by id
        //endregion

        //region 5. PARTIAL & BULK CLOSE
        // ══════════════════════════════════════════════════════════════
        // 5. PARTIAL & BULK CLOSE
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // closePartial() + closeAll()
        //   Close part of the volume, then flatten everything left.
        // ────────────────────────────────────────────────────────────
        try {
            sugar.closePartial((int) t3, 0.01);
        } catch (Exception ignored) {
        }
        sugar.closeAll(symbol, null, null);
        System.out.println("closeAll         : flat");
        //endregion
    }

    private SugarTrading() {
    }
}
