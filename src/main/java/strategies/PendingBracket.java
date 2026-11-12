/*══════════════════════════════════════════════════════════════════════════════
 FILE: strategies/PendingBracket.java - STRATEGY: Pending order + bracket
 PURPOSE:
   "Set your price and wait for the market to come to you": place a BUY LIMIT with
   an automatic SL/TP bracket, wait for it to fill, and only then arm trailing /
   auto-breakeven. If it is not filled within the timeout, cancel the pending.

 🎯 WHO SHOULD USE THIS:
   • Limit-entry strategies (buy a dip to a chosen price)
   • A template for "place pending, wait-or-cancel, then manage"

 📚 WHAT THIS DEMO COVERS (phases):

   SETUP       - setDefaults / ensureConnected / ensureSymbol
   1. PLACE    - resolve entry price, size, buyLimit() with a pip bracket
   2. WAIT     - waitFilled(); on timeout, cancelPendings() and exit
   3. MANAGE   - optional setTrailingStop() / autoBreakeven(), then close()

   Inputs: StrategyPreset, RiskPreset, and timeoutSeconds (how long to wait
           for the limit to fill).

 ⚠️  WARNING: places a real pending order on your DEMO account; cleaned up at the
     end (cancelled if unfilled, closed if filled).

 USAGE:
   run.bat 15      (or: java -cp ... Program 15)
══════════════════════════════════════════════════════════════════════════════*/
package strategies;

import pro.mrpc.mt4.MT4Sugar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ORCHESTRATOR · Pending order + bracket
 * ──────────────────────────────────────────────────────────────────────────────
 * "Set your price and wait for the market to come to you": place a BUY LIMIT with an automatic SL/TP
 * bracket, wait for it to fill, and only then arm trailing / auto-breakeven. If it isn't filled within
 * the timeout, the pending order is cancelled.
 *
 * Flow:
 *   1. resolve the entry price ({@code StrategyPreset.entryPrice}, or a few pips below market for the demo)
 *   2. size &amp; place the BUY LIMIT with a pip bracket
 *   3. wait for fill (timeout → cancel pendings and exit)
 *   4. on fill: optional trailing + auto-breakeven, manage briefly, close
 *
 * ⚠️ Places a real pending order on your DEMO account; cleaned up at the end.
 */
public final class PendingBracket {
    public static long run(MT4Sugar sugar, StrategyPreset sp, RiskPreset rp, int timeoutSeconds) throws Exception {
        //region SETUP
        // ══════════════════════════════════════════════════════════════
        // SETUP - apply preset defaults, ensure connection + symbol
        // ══════════════════════════════════════════════════════════════
        System.out.println("── Pending Bracket · " + sp.symbol + " ──");
        sugar.setDefaults(sp.symbol, sp.magic, sp.deviationPips, null, rp.riskPercent);
        sugar.ensureConnected();
        sugar.ensureSymbol(sp.symbol);
        //endregion

        //region 1. PLACE PENDING
        // ──────────────────────────────────────────────────────────────
        // buyLimit(symbol, entry, lots, slPips, tpPips, ...)
        //   Resolve the entry price (explicit, or 5 pips below market for the
        //   demo), size by risk, and place a BUY LIMIT with a pip bracket.
        //   Returns: ticket (long) of the pending order.
        // ──────────────────────────────────────────────────────────────
        double entry = sp.entryPrice != null ? sp.entryPrice : sugar.midPrice(sp.symbol) - sugar.pipsToPrice(sp.symbol, 5);
        double lots = sp.lots != null ? sp.lots : sugar.calcLotByRisk(sp.symbol, rp.riskPercent, rp.slPips, null);

        long ticket = sugar.buyLimit(sp.symbol, entry, lots, rp.slPips, rp.tpPips, null, null, sp.comment, sp.magic);
        System.out.printf(Locale.ROOT, "placed     : BUY LIMIT #%d  %.2f lot @ %.5f%n", ticket, lots, entry);
        //endregion

        //region 2. WAIT FOR FILL
        // ──────────────────────────────────────────────────────────────
        // waitFilled(ticket, timeout)  /  cancelPendings(symbol) on timeout
        //   Block until the limit fills; if it does not within timeoutSeconds,
        //   cancel the pending order and return early.
        // ──────────────────────────────────────────────────────────────
        try {
            sugar.waitFilled((int) ticket, timeoutSeconds * 1000L);
            System.out.println("filled     : #" + ticket);
        } catch (Exception e) {
            System.out.println("timeout    : not filled in " + timeoutSeconds + "s — cancelling pendings");
            sugar.cancelPendings(sp.symbol, null);
            return ticket;
        }
        //endregion

        //region 3. MANAGE & CLOSE
        // ──────────────────────────────────────────────────────────────
        // setTrailingStop() / autoBreakeven() → manage → unwatch() → close()
        //   Once filled, arm the optional workers, manage briefly, then close.
        // ──────────────────────────────────────────────────────────────
        List<String> subs = new ArrayList<>();
        if (rp.trailingPips != null) subs.add(sugar.setTrailingStop((int) ticket, rp.trailingPips, 1.0));
        if (rp.beTriggerPips != null) subs.add(sugar.autoBreakeven((int) ticket, rp.beTriggerPips, rp.bePlusPips));

        System.out.println("managing   : 3s …");
        Thread.sleep(3000);
        for (String s : subs) sugar.unwatch(s);
        sugar.close((int) ticket);
        System.out.println("closed     : #" + ticket);
        return ticket;
        //endregion
    }

    private PendingBracket() {
    }
}
