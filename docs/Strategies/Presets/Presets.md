# Strategy & Risk Presets

> The two small fluent classes that configure every orchestrator, plus ready-made factories.

* **Source:** `src/main/java/strategies/StrategyPreset.java`, `RiskPreset.java`, `Presets.java`
* **Demo:** `run.bat 16`

Orchestrators take one `StrategyPreset` (what/where to enter) and one `RiskPreset` (how much to risk / protect):

```java
long ticket = MarketOneShot.run(sugar, new StrategyPreset("EURUSD").lots(0.01), new RiskPreset(1.0, 20).tp(40));
```

---

## StrategyPreset

What and where to enter. Construct with a symbol, then chain fluent setters.

| Field | Type | Default | Meaning |
|-------|------|---------|---------|
| `symbol` | `String` | — | trading symbol, e.g. `"EURUSD"` |
| `useMarket` | `boolean` | `true` | `true` = market entry; `false` = pending (needs `entryPrice`) |
| `entryPrice` | `Double` | `null` | price for a limit/stop entry |
| `lots` | `Double` | `null` | fixed lot size; `null` → auto-size from the `RiskPreset` |
| `magic` | `int` | `0` | order magic number |
| `deviationPips` | `double` | `2.0` | max slippage in pips |
| `comment` | `String` | `null` | order comment |

**Fluent setters:** `market()`, `limitAt(double price)`, `lots(double)`, `magic(int)`, `deviation(double)`, `comment(String)`.

```java
StrategyPreset sp = new StrategyPreset("EURUSD").magic(777).deviation(2.0).comment("demo");
StrategyPreset pend = new StrategyPreset("EURUSD").limitAt(1.0900).lots(0.01);
```

### Ready-made factories (`Presets`)

| Factory | Returns |
|---------|---------|
| `Presets.marketEurusd()` | Market EURUSD preset (magic 777). |
| `Presets.limitEurusd(double price)` | BUY LIMIT EURUSD at `price` (magic 778). |

---

## RiskPreset

How much to risk and how to protect. Construct with `riskPercent` + `slPips`, then chain.

| Field | Type | Default | Meaning |
|-------|------|---------|---------|
| `riskPercent` | `double` | — | % of equity to risk per trade (1.0 = 1%) |
| `slPips` | `double` | — | stop-loss distance in pips |
| `tpPips` | `Double` | `null` | take-profit distance in pips |
| `trailingPips` | `Double` | `null` | trailing-stop distance — set to **enable** trailing |
| `beTriggerPips` | `Double` | `null` | profit (pips) that arms auto-breakeven — set to **enable** auto-BE |
| `bePlusPips` | `double` | `0.0` | extra pips above the breakeven level |

**Fluent setters:** `tp(double pips)`, `trailing(double pips)`, `breakeven(double triggerPips, double plusPips)`.

```java
RiskPreset rp = new RiskPreset(1.0, 20).tp(40).trailing(15).breakeven(10, 2);
```

### Ready-made risk factories (`Presets`)

| Factory | Profile |
|---------|---------|
| `Presets.conservative()` | `0.5%` risk, SL 25, TP 50 |
| `Presets.balanced()` | `1.0%` risk, SL 20, TP 40, trail 15, BE 10/+2 |
| `Presets.aggressive()` | `2.0%` risk, SL 15, TP 45, trail 10, BE 8/+1 |

---

## Putting it together

`Presets.run(sugar, symbol)` prints the catalog and runs a `MarketOneShot` with the `balanced` risk preset:

```java
Presets.run(sugar, "EURUSD");

// or hand-pick:
MarketOneShot.run(sugar, Presets.marketEurusd(), Presets.conservative());
```

> **Separate WHAT from HOW MUCH:** pick a strategy preset and a risk preset independently, then hand both to any orchestrator.

---

## 📚 See also

* [Strategies — Master Overview](../Strategies.Master.Overview.md)
* [Market One-Shot](../Orchestrators/MarketOneShot.md) · [Bracket + Trailing](../Orchestrators/BracketTrailing.md) · [Pending Bracket](../Orchestrators/PendingBracket.md)
* [MT4Sugar — Risk Sizing](../../MT4Sugar/4.%20Risk_Sizing.md)
