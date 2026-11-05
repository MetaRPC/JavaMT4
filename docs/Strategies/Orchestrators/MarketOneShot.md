# Market One-Shot

> Open a market order with an automatic SL/TP bracket, then optionally trail / auto-breakeven — the simplest orchestrator.

* **Source:** `src/main/java/strategies/MarketOneShot.java`
* **Entry:** `static long run(MT4Sugar sugar, StrategyPreset sp, RiskPreset rp)` → ticket
* **Demo:** `run.bat 11`

---

## What it does

1. Applies the presets as Sugar defaults (`setDefaults(symbol, magic, deviationPips, null, riskPercent)`), then `ensureConnected` / `ensureSymbol`.
2. **Sizes the lot** — fixed `StrategyPreset.lots`, or auto from `RiskPreset.riskPercent` + `slPips` via `calcLotByRisk`.
3. Enters at **market** with a pip-based SL/TP bracket (`buyMarket`), then `waitFilled`.
4. Optionally arms a **trailing stop** (if `RiskPreset.trailingPips` is set) and/or **auto-breakeven** (if `beTriggerPips` is set).
5. Manages for a few seconds, prints a `diagSnapshot`, then tears down — `unwatch` the workers and `close` the position.

Use it when you simply want a protected market entry in one call.

---

## Signature

```java
public static long run(MT4Sugar sugar, StrategyPreset sp, RiskPreset rp) throws Exception
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `sugar` | `MT4Sugar` | A connected high-level client. |
| `sp` | `StrategyPreset` | `symbol`, `lots` (null → size by risk), `magic`, `deviationPips`, `comment`. |
| `rp` | `RiskPreset` | `riskPercent`, `slPips`, `tpPips`; optional `trailingPips` / `beTriggerPips` enable management. |

**Returns:** the opened order's ticket (`long`).

---

## Example

```java
StrategyPreset sp = new StrategyPreset("EURUSD").lots(0.01).magic(101)
        .deviation(3.0).comment("market one-shot");
RiskPreset rp = new RiskPreset(1.0, 20).tp(40).trailing(15).breakeven(10, 2);

long ticket = MarketOneShot.run(sugar, sp, rp);
System.out.println("ticket #" + ticket);
```

> Omit `.lots(...)` on the `StrategyPreset` and sizing comes from `riskPercent` + `slPips` via `calcLotByRisk`. Set `.trailing(...)` / `.breakeven(...)` on the `RiskPreset` to arm the management workers.

---

## 📚 See also

* [Strategies — Master Overview](../Strategies.Master.Overview.md)
* [Presets](../Presets/Presets.md)
* [Bracket + Trailing](./BracketTrailing.md) · [Pending Bracket](./PendingBracket.md)
