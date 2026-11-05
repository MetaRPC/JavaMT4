package strategies;

import pro.mrpc.mt4.MT4Sugar;

/**
 * PRESETS · ready-made {@link StrategyPreset} / {@link RiskPreset} building blocks (Java port of the Python
 * {@code 16_presets}). "Separate WHAT from HOW MUCH": pick a strategy preset and a risk preset, hand them to
 * any orchestrator. The demo {@link #run} wires the {@code balanced} risk into {@link MarketOneShot}.
 */
public final class Presets {

    // ── Strategy presets (WHAT) ──────────────────────────────────
    public static StrategyPreset marketEurusd() {
        return new StrategyPreset("EURUSD").magic(777).deviation(2.0).comment("Market EURUSD");
    }

    public static StrategyPreset limitEurusd(double price) {
        return new StrategyPreset("EURUSD").limitAt(price).magic(778).comment("Limit EURUSD");
    }

    // ── Risk presets (HOW MUCH) ──────────────────────────────────
    public static RiskPreset conservative() {
        return new RiskPreset(0.5, 25).tp(50);
    }

    public static RiskPreset balanced() {
        return new RiskPreset(1.0, 20).tp(40).trailing(15).breakeven(10, 2);
    }

    public static RiskPreset aggressive() {
        return new RiskPreset(2.0, 15).tp(45).trailing(10).breakeven(8, 1);
    }

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

    private Presets() {
    }
}
