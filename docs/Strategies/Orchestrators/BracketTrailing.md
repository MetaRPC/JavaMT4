# Bracket + Trailing Activation

> Enter with a fixed SL/TP bracket, then switch on a trailing stop **only once the trade has earned a profit cushion** — "let it prove itself before trailing".

* **Source:** `src/main/java/strategies/BracketTrailing.java`
* **Entry:** `static long run(MT4Sugar sugar, StrategyPreset sp, RiskPreset rp, double activateAtPips)` → ticket
* **Demo:** `run.bat 12`

---

## What it does

1. Applies presets as defaults; `ensureConnected` / `ensureSymbol`.
2. **Spread read** (`spreadPips`) for context.
3. Sizes the lot and enters at **market** with a pip bracket; `waitFilled`, then captures the fill price.
4. Optional **auto-breakeven** guard (if `beTriggerPips` is set).
5. **Activation loop:** polls the profit in pips; once it reaches `activateAtPips`, starts the **trailing stop** (`trailingPips`, default 15). In the short demo window it may not reach the threshold — it says so and would start live.
6. Manages briefly, then tears down — `unwatch` workers and `close`.

Use it to give a trade room to develop before protecting profit with a trail.

---

## Signature

```java
public static long run(MT4Sugar sugar, StrategyPreset sp, RiskPreset rp, double activateAtPips) throws Exception
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `sugar` | `MT4Sugar` | A connected high-level client. |
| `sp` | `StrategyPreset` | entry config (symbol, lots, magic, …). |
| `rp` | `RiskPreset` | `slPips`/`tpPips` bracket; `trailingPips` (trail distance, default 15); optional `beTriggerPips`. |
| `activateAtPips` | `double` | profit (pips) the trade must reach before trailing starts. |

**Returns:** the opened order's ticket (`long`).

---

## Example

```java
StrategyPreset sp = new StrategyPreset("EURUSD").lots(0.01).magic(102).comment("bracket+trailing");
RiskPreset rp = new RiskPreset(1.0, 20).tp(60).trailing(15).breakeven(10, 2);

long ticket = BracketTrailing.run(sugar, sp, rp, 12.0);   // start trailing after +12 pips
```

---

## 📚 See also

* [Strategies — Master Overview](../Strategies.Master.Overview.md)
* [Presets](../Presets/Presets.md)
* [Market One-Shot](./MarketOneShot.md) · [Pending Bracket](./PendingBracket.md)
* [MT4Sugar — Trailing & Breakeven](../../MT4Sugar/7.%20Trailing_Breakeven.md)
