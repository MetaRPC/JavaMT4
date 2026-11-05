package strategies;

/**
 * Strategy preset — the <b>WHAT to trade</b> half of an orchestrator setup (Java port of the Python
 * {@code StrategyPreset}). Pairs with a {@link RiskPreset} (the <b>HOW MUCH</b> half).
 *
 * <pre>
 *   symbol        trading symbol, e.g. "EURUSD"
 *   useMarket     true = market entry, false = pending (needs entryPrice)   [default true]
 *   entryPrice    price for a limit/stop entry                               [optional]
 *   lots          fixed lot size; null = auto-size from the RiskPreset       [optional]
 *   magic         order magic number                                        [default 0]
 *   deviationPips max slippage in pips                                       [default 2.0]
 *   comment       order comment
 * </pre>
 *
 * Fluent: {@code new StrategyPreset("EURUSD").magic(777).comment("demo")}.
 */
public final class StrategyPreset {
    public String symbol;
    public boolean useMarket = true;
    public Double entryPrice;
    public Double lots;
    public int magic = 0;
    public double deviationPips = 2.0;
    public String comment;

    public StrategyPreset(String symbol) {
        this.symbol = symbol;
    }

    public StrategyPreset market() { this.useMarket = true; this.entryPrice = null; return this; }
    public StrategyPreset limitAt(double price) { this.useMarket = false; this.entryPrice = price; return this; }
    public StrategyPreset lots(double lots) { this.lots = lots; return this; }
    public StrategyPreset magic(int magic) { this.magic = magic; return this; }
    public StrategyPreset deviation(double pips) { this.deviationPips = pips; return this; }
    public StrategyPreset comment(String comment) { this.comment = comment; return this; }
}
