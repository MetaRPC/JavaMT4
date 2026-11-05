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
        System.out.println("── Market One-Shot · " + sp.symbol + " ──");
        sugar.setDefaults(sp.symbol, sp.magic, sp.deviationPips, null, rp.riskPercent);
        sugar.ensureConnected();
        sugar.ensureSymbol(sp.symbol);

        // 1) position sizing
        double lots = sp.lots != null ? sp.lots : sugar.calcLotByRisk(sp.symbol, rp.riskPercent, rp.slPips, null);
        System.out.printf(Locale.ROOT, "sizing     : %.2f lot (risk %.1f%%, SL %.0f pips)%n", lots, rp.riskPercent, rp.slPips);

        // 2) market entry with bracket
        long ticket = sugar.buyMarket(sp.symbol, lots, rp.slPips, rp.tpPips, null, null, sp.comment, sp.magic, sp.deviationPips);
        System.out.printf(Locale.ROOT, "entry      : BUY #%d  SL %.0fp  TP %s%n", ticket, rp.slPips, rp.tpPips == null ? "-" : String.valueOf(rp.tpPips));
        sugar.waitFilled((int) ticket, 10_000L);

        // 3) optional management workers
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

        // 4) teardown
        for (String s : subs) sugar.unwatch(s);
        sugar.close((int) ticket);
        System.out.println("closed     : #" + ticket);
        return ticket;
    }

    private MarketOneShot() {
    }
}
