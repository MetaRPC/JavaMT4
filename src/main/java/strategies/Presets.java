/*══════════════════════════════════════════════════════════════════════════════
 FILE: strategies/Presets.java - STRATEGY: ready-made presets catalog
 PURPOSE:
   Ready-made StrategyPreset / RiskPreset building blocks (Java port of the Python
   16_presets). "Separate WHAT from HOW MUCH": pick a strategy preset and a risk
   preset, hand them to any orchestrator.

 🎯 WHO SHOULD USE THIS:
   • Anyone who wants sensible defaults instead of hand-tuning every parameter
   • A reference for how StrategyPreset / RiskPreset are composed

 📚 WHAT THIS DEMO COVERS:

   1. STRATEGY PRESETS (WHAT) - marketEurusd(), limitEurusd(price)
   2. RISK PRESETS (HOW MUCH) - conservative(), balanced(), aggressive()
   3. DEMO                    - prints the catalog, then runs MarketOneShot
                                with the 'balanced' risk preset

 ⚠️  WARNING: the DEMO (run) calls MarketOneShot, which opens a real position on
     your DEMO account and closes it again (self-cleaning). The preset factories
     themselves only build objects - no orders.

 USAGE:
   run.bat 16      (or: java -cp ... Program 16)
══════════════════════════════════════════════════════════════════════════════*/
package strategies;

import pro.mrpc.mt4.MT4Sugar;

/**
 * PRESETS · ready-made {@link StrategyPreset} / {@link RiskPreset} building blocks (Java port of the Python
 * {@code 16_presets}). "Separate WHAT from HOW MUCH": pick a strategy preset and a risk preset, hand them to
 * any orchestrator. The demo {@link #run} wires the {@code balanced} risk into {@link MarketOneShot}.
 */
public final class Presets {

    //region 1. STRATEGY PRESETS (WHAT)
    // ══════════════════════════════════════════════════════════════
    // 1. STRATEGY PRESETS (WHAT) - symbol / entry / magic / comment
    // ══════════════════════════════════════════════════════════════

    // ──────────────────────────────────────────────────────────────
    // marketEurusd()
    //   A market-entry EURUSD preset (magic 777).
    //   Returns: StrategyPreset.
    // ──────────────────────────────────────────────────────────────
    public static StrategyPreset marketEurusd() {
        return new StrategyPreset("EURUSD").magic(777).deviation(2.0).comment("Market EURUSD");
    }

    // ──────────────────────────────────────────────────────────────
    // limitEurusd(price)
    //   A BUY-LIMIT EURUSD preset at the given price (magic 778).
    //   Returns: StrategyPreset.
    // ──────────────────────────────────────────────────────────────
    public static StrategyPreset limitEurusd(double price) {
        return new StrategyPreset("EURUSD").limitAt(price).magic(778).comment("Limit EURUSD");
    }
    //endregion

    //region 2. RISK PRESETS (HOW MUCH)
    // ══════════════════════════════════════════════════════════════
    // 2. RISK PRESETS (HOW MUCH) - risk% / SL / TP / trailing / break-even
    // ══════════════════════════════════════════════════════════════

    // ──────────────────────────────────────────────────────────────
    // conservative() / balanced() / aggressive()
    //   Three ready risk envelopes, from tight to wide.
    //   Returns: RiskPreset.
    // ──────────────────────────────────────────────────────────────
    public static RiskPreset conservative() {
        return new RiskPreset(0.5, 25).tp(50);
    }

    public static RiskPreset balanced() {
        return new RiskPreset(1.0, 20).tp(40).trailing(15).breakeven(10, 2);
    }

    public static RiskPreset aggressive() {
        return new RiskPreset(2.0, 15).tp(45).trailing(10).breakeven(8, 1);
    }
    //endregion

    //region 3. DEMO
    // ══════════════════════════════════════════════════════════════
    // 3. DEMO - print the catalog, then run MarketOneShot (balanced)
    // ══════════════════════════════════════════════════════════════
    public static void run(MT4Sugar sugar, String symbol) throws Exception {
        System.out.println("── Presets catalog ──");
        System.out.println("Strategy : marketEurusd (magic 777) · limitEurusd(price) (magic 778)");
        System.out.println("Risk     : conservative 0.5%/SL25/TP50");
        System.out.println("           balanced     1.0%/SL20/TP40 + trail 15 + BE 10/2");
        System.out.println("           aggressive   2.0%/SL15/TP45 + trail 10 + BE 8/1");
        System.out.println();
        System.out.println("Demo: MarketOneShot with the 'balanced' risk preset on " + symbol + "\n");

        StrategyPreset sp = new StrategyPreset(symbol).magic(777).comment("preset-demo");
        MarketOneShot.run(sugar, sp, balanced());
    }
    //endregion

    private Presets() {
    }
}
