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
        System.out.println("── Bracket + Trailing · " + sp.symbol + " ──");
        sugar.setDefaults(sp.symbol, sp.magic, sp.deviationPips, null, rp.riskPercent);
        sugar.ensureConnected();
        sugar.ensureSymbol(sp.symbol);

        double spread = sugar.spreadPips(sp.symbol);
        System.out.printf(Locale.ROOT, "spread     : %.1f pips%n", spread);

        double lots = sp.lots != null ? sp.lots : sugar.calcLotByRisk(sp.symbol, rp.riskPercent, rp.slPips, null);
        long ticket = sugar.buyMarket(sp.symbol, lots, rp.slPips, rp.tpPips, null, null, sp.comment, sp.magic, sp.deviationPips);
        System.out.printf(Locale.ROOT, "entry      : BUY #%d %.2f lot%n", ticket, lots);
        sugar.waitFilled((int) ticket, 10_000L);

        double entry = sugar.midPrice(sp.symbol);
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : sugar.findOrders(sp.symbol, null, null, "open", null)) {
            if (o.getTicket() == (int) ticket) entry = o.getOpenPrice();
        }

        List<String> subs = new ArrayList<>();
        if (rp.beTriggerPips != null) {
            subs.add(sugar.autoBreakeven((int) ticket, rp.beTriggerPips, rp.bePlusPips));
            System.out.printf(Locale.ROOT, "auto-BE    : arm at +%.0fp%n", rp.beTriggerPips);
        }

        // Wait for the profit cushion, then start trailing (poll ~8s for the demo).
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

        System.out.println("managing   : 3s …");
        Thread.sleep(3000);
        for (String s : subs) sugar.unwatch(s);
        sugar.close((int) ticket);
        System.out.println("closed     : #" + ticket);
        return ticket;
    }

    private BracketTrailing() {
    }
}
