/*══════════════════════════════════════════════════════════════════════════════
 FILE: strategies/MarketOneShot.java - STRATEGY: Market One-Shot
 PURPOSE:
   The simplest orchestrator - a fast market entry with automatic management
   (pip SL/TP bracket, optional trailing stop and auto-breakeven), then a clean
   teardown. A good first strategy to read.

 🎯 WHO SHOULD USE THIS:
   • Anyone learning how the Sugar API composes into a full trade lifecycle
   • A template for "enter at market, manage, exit" bots

 📚 WHAT THIS DEMO COVERS (phases):

   SETUP      - setDefaults / ensureConnected / ensureSymbol
   1. SIZING  - calcLotByRisk() (or the fixed StrategyPreset.lots)
   2. ENTRY   - buyMarket() with a pip SL/TP bracket + waitFilled()
   3. MANAGE  - optional setTrailingStop() and/or autoBreakeven()
   4. TEARDOWN- unwatch() the workers, close() the position

   Inputs: StrategyPreset (symbol / magic / deviation / lots / comment),
           RiskPreset (riskPercent / slPips / tpPips / trailingPips /
           beTriggerPips / bePlusPips).

 ⚠️  WARNING: opens a real 0.01+ position on your DEMO account and closes it
     again at the end (self-cleaning).

 USAGE:
   run.bat 11      (or: java -cp ... Program 11)
══════════════════════════════════════════════════════════════════════════════*/
package strategies;

import pro.mrpc.mt4.MT4Sugar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ORCHESTRATOR · Market One-Shot
 * ──────────────────────────────────────────────────────────────────────────────
 * The simplest orchestrator: a fast market entry with automatic management.
 *
 * Flow:
 *   1. set defaults (symbol / magic / deviation / risk%) from the presets
 *   2. ensure the connection and the symbol
 *   3. size the lot — fixed {@code StrategyPreset.lots}, or auto from {@code RiskPreset.riskPercent} + {@code slPips}
 *   4. enter at market with a pip-based SL/TP bracket
 *   5. optionally arm a trailing stop and/or auto-breakeven (when configured in the RiskPreset)
 *   6. manage for a few seconds, then tear everything down (unwatch workers, close the position)
 *
 * ⚠️ Opens a real 0.01+ position on your DEMO account and closes it again at the end.
 */
public final class MarketOneShot {
    public static long run(MT4Sugar sugar, StrategyPreset sp, RiskPreset rp) throws Exception {
        //region SETUP
        // ══════════════════════════════════════════════════════════════
        // SETUP - apply preset defaults, ensure connection + symbol
        // ══════════════════════════════════════════════════════════════
        System.out.println("── Market One-Shot · " + sp.symbol + " ──");
        sugar.setDefaults(sp.symbol, sp.magic, sp.deviationPips, null, rp.riskPercent);
        sugar.ensureConnected();
        sugar.ensureSymbol(sp.symbol);
        //endregion

        //region 1. SIZING
        // ──────────────────────────────────────────────────────────────
        // calcLotByRisk(symbol, riskPercent, slPips)
        //   Lot size so hitting the SL loses riskPercent of equity.
        //   (Skipped when StrategyPreset.lots is set to a fixed size.)
        //   Returns: lot size (double).
        // ──────────────────────────────────────────────────────────────
        double lots = sp.lots != null ? sp.lots : sugar.calcLotByRisk(sp.symbol, rp.riskPercent, rp.slPips, null);
        System.out.printf(Locale.ROOT, "sizing     : %.2f lot (risk %.1f%%, SL %.0f pips)%n", lots, rp.riskPercent, rp.slPips);
        //endregion

        //region 2. ENTRY
        // ──────────────────────────────────────────────────────────────
        // buyMarket(symbol, lots, slPips, tpPips, ...)  +  waitFilled(ticket)
        //   Open a BUY at market with a pip SL/TP bracket, then block until
        //   the order is filled.
        //   Returns: ticket (long) of the opened position.
        // ──────────────────────────────────────────────────────────────
        long ticket = sugar.buyMarket(sp.symbol, lots, rp.slPips, rp.tpPips, null, null, sp.comment, sp.magic, sp.deviationPips);
        System.out.printf(Locale.ROOT, "entry      : BUY #%d  SL %.0fp  TP %s%n", ticket, rp.slPips, rp.tpPips == null ? "-" : String.valueOf(rp.tpPips));
        sugar.waitFilled((int) ticket, 10_000L);
        //endregion

        //region 3. MANAGE
        // ──────────────────────────────────────────────────────────────
        // setTrailingStop() / autoBreakeven()
        //   Optional background workers (armed only when the RiskPreset
        //   configures them). Each returns a subscription id to unwatch later.
        // ──────────────────────────────────────────────────────────────
        List<String> subs = new ArrayList<>();
        if (rp.trailingPips != null) {
            subs.add(sugar.setTrailingStop((int) ticket, rp.trailingPips, 1.0));
            System.out.printf(Locale.ROOT, "trailing   : on (%.0f pips)%n", rp.trailingPips);
        }
        if (rp.beTriggerPips != null) {
            subs.add(sugar.autoBreakeven((int) ticket, rp.beTriggerPips, rp.bePlusPips));
            System.out.printf(Locale.ROOT, "auto-BE    : arm at +%.0fp, SL to entry +%.0fp%n", rp.beTriggerPips, rp.bePlusPips);
        }

        System.out.println("diag       : " + sugar.diagSnapshot().keySet());
        System.out.println("managing   : 5s …");
        Thread.sleep(5000);
        //endregion

        //region 4. TEARDOWN
        // ──────────────────────────────────────────────────────────────
        // unwatch(sub) / close(ticket)
        //   Stop the management workers and close the position.
        // ──────────────────────────────────────────────────────────────
        for (String s : subs) sugar.unwatch(s);
        sugar.close((int) ticket);
        System.out.println("closed     : #" + ticket);
        return ticket;
        //endregion
    }

    private MarketOneShot() {
    }
}
