package strategies;

/**
 * Risk preset — the <b>HOW MUCH</b> half of an orchestrator setup (Java port of the Python {@code RiskPreset}).
 * One object holds the complete risk configuration; reuse it across strategies and symbols.
 *
 * <pre>
 *   riskPercent    % of equity to risk per trade (1.0 = 1%)
 *   slPips         stop-loss distance in pips
 *   tpPips         take-profit distance in pips                 [optional]
 *   trailingPips   trailing-stop distance in pips               [optional → enables trailing]
 *   beTriggerPips  profit (pips) that arms auto-breakeven       [optional → enables auto-BE]
 *   bePlusPips     extra pips above the breakeven level         [default 0.0]
 * </pre>
 *
 * Fluent: {@code new RiskPreset(1.0, 20).tp(40).trailing(15).breakeven(10, 2)}.
 */
public final class RiskPreset {
    public double riskPercent;
    public double slPips;
    public Double tpPips;
    public Double trailingPips;
    public Double beTriggerPips;
    public double bePlusPips = 0.0;

    public RiskPreset(double riskPercent, double slPips) {
        this.riskPercent = riskPercent;
        this.slPips = slPips;
    }

    public RiskPreset tp(double pips) { this.tpPips = pips; return this; }
    public RiskPreset trailing(double pips) { this.trailingPips = pips; return this; }
    public RiskPreset breakeven(double triggerPips, double plusPips) { this.beTriggerPips = triggerPips; this.bePlusPips = plusPips; return this; }
}
