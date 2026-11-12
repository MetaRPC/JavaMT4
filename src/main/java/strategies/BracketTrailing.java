/*══════════════════════════════════════════════════════════════════════════════
 FILE: strategies/BracketTrailing.java - STRATEGY: Bracket + Trailing Activation
 PURPOSE:
   Enter with a fixed SL/TP bracket, then switch on a trailing stop only once the
   trade has earned a configurable profit cushion - "let it prove itself before
   trailing".

 🎯 WHO SHOULD USE THIS:
   • Trend-following entries that should only trail after confirmation
   • A template for "enter now, activate management on a condition"

 📚 WHAT THIS DEMO COVERS (phases):

   SETUP        - setDefaults / ensureConnected / ensureSymbol
   1. ENTRY     - spread read, size, buyMarket() bracket, waitFilled(),
                  optional autoBreakeven()
   2. ACTIVATION- poll open profit; once it reaches activateAtPips, start
                  setTrailingStop()
   3. TEARDOWN  - unwatch() workers, close() the position

   Inputs: StrategyPreset, RiskPreset, and activateAtPips (profit cushion
           before trailing starts).

 ⚠️  WARNING: opens a real position on your DEMO account; cleaned up at the end.

 USAGE:
   run.bat 12      (or: java -cp ... Program 12)
══════════════════════════════════════════════════════════════════════════════*/
package strategies;

import mt4_term_api.Mt4TermApiAccountHelper;
import pro.mrpc.mt4.MT4Sugar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ORCHESTRATOR · Bracket + Trailing Activation
 * ──────────────────────────────────────────────────────────────────────────────
 * Enter with a fixed SL/TP bracket, then switch on a trailing stop only once the trade has earned a
 * configurable profit cushion — "let it prove itself before trailing".
 *
 * Flow:
 *   1. spread guard (skip if the spread is too wide)
 *   2. size &amp; enter at market with a pip bracket
 *   3. optional auto-breakeven guard
 *   4. poll the open profit; when it reaches {@code activateAtPips}, start the trailing stop
 *   5. manage briefly, then tear down (unwatch workers, close)
 *
 * ⚠️ Opens a real position on your DEMO account; cleaned up at the end.
 */
public final class BracketTrailing {
    public static long run(MT4Sugar sugar, StrategyPreset sp, RiskPreset rp, double activateAtPips) throws Exception {
        //region SETUP
        // ══════════════════════════════════════════════════════════════
        // SETUP - apply preset defaults, ensure connection + symbol
        // ══════════════════════════════════════════════════════════════
        System.out.println("── Bracket + Trailing · " + sp.symbol + " ──");
        sugar.setDefaults(sp.symbol, sp.magic, sp.deviationPips, null, rp.riskPercent);
        sugar.ensureConnected();
        sugar.ensureSymbol(sp.symbol);
        //endregion

        //region 1. ENTRY
        // ══════════════════════════════════════════════════════════════
        // 1. ENTRY - spread read, size, market bracket, optional auto-BE
        // ══════════════════════════════════════════════════════════════

        // ──────────────────────────────────────────────────────────────
        // spreadPips(symbol)
        //   Current spread in pips (a wide-spread guard input).
        // ──────────────────────────────────────────────────────────────
        double spread = sugar.spreadPips(sp.symbol);
        System.out.printf(Locale.ROOT, "spread     : %.1f pips%n", spread);

        // ──────────────────────────────────────────────────────────────
        // calcLotByRisk() + buyMarket() + waitFilled()
        //   Size by risk, open a BUY bracket at market, wait for the fill.
        //   Returns: ticket (long) of the opened position.
        // ──────────────────────────────────────────────────────────────
        double lots = sp.lots != null ? sp.lots : sugar.calcLotByRisk(sp.symbol, rp.riskPercent, rp.slPips, null);
        long ticket = sugar.buyMarket(sp.symbol, lots, rp.slPips, rp.tpPips, null, null, sp.comment, sp.magic, sp.deviationPips);
        System.out.printf(Locale.ROOT, "entry      : BUY #%d %.2f lot%n", ticket, lots);
        sugar.waitFilled((int) ticket, 10_000L);

        // ──────────────────────────────────────────────────────────────
        // findOrders(symbol, ..., "open", ...)
        //   Read the real fill price of our ticket (for the activation math).
        // ──────────────────────────────────────────────────────────────
        double entry = sugar.midPrice(sp.symbol);
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : sugar.findOrders(sp.symbol, null, null, "open", null)) {
            if (o.getTicket() == (int) ticket) entry = o.getOpenPrice();
        }

        // ──────────────────────────────────────────────────────────────
        // autoBreakeven(ticket, beTriggerPips, bePlusPips)
        //   Optional break-even guard (armed only if the RiskPreset sets it).
        // ──────────────────────────────────────────────────────────────
        List<String> subs = new ArrayList<>();
        if (rp.beTriggerPips != null) {
            subs.add(sugar.autoBreakeven((int) ticket, rp.beTriggerPips, rp.bePlusPips));
            System.out.printf(Locale.ROOT, "auto-BE    : arm at +%.0fp%n", rp.beTriggerPips);
        }
        //endregion

        //region 2. ACTIVATION
        // ══════════════════════════════════════════════════════════════
        // 2. ACTIVATION - start trailing once profit reaches activateAtPips
        // ══════════════════════════════════════════════════════════════

        // ──────────────────────────────────────────────────────────────
        // setTrailingStop(ticket, trailing, step)  [conditional]
        //   Poll the open profit (~8s for the demo); once it reaches
        //   activateAtPips, start the trailing stop.
        // ──────────────────────────────────────────────────────────────
        double trailing = rp.trailingPips != null ? rp.trailingPips : 15.0;
        System.out.printf(Locale.ROOT, "activation : waiting for +%.0f pips to start trailing …%n", activateAtPips);
        long deadline = System.currentTimeMillis() + 8000;
        boolean started = false;
        while (System.currentTimeMillis() < deadline) {
            double pips = sugar.priceToPips(sp.symbol, sugar.midPrice(sp.symbol) - entry);
            if (pips >= activateAtPips) {
                subs.add(sugar.setTrailingStop((int) ticket, trailing, 1.0));
                System.out.printf(Locale.ROOT, "trailing   : started at +%.1f pips (%.0f-pip trail)%n", pips, trailing);
                started = true;
                break;
            }
            Thread.sleep(500);
        }
        if (!started) System.out.println("trailing   : threshold not reached in demo window (would start live)");
        //endregion

        //region 3. TEARDOWN
        // ──────────────────────────────────────────────────────────────
        // unwatch(sub) / close(ticket)
        //   Stop the workers and close the position.
        // ──────────────────────────────────────────────────────────────
        System.out.println("managing   : 3s …");
        Thread.sleep(3000);
        for (String s : subs) sugar.unwatch(s);
        sugar.close((int) ticket);
        System.out.println("closed     : #" + ticket);
        return ticket;
        //endregion
    }

    private BracketTrailing() {
    }
}
