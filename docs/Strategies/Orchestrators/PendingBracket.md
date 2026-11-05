# Pending Bracket

> "Set your price and wait for the market to come to you": place a BUY LIMIT with an automatic SL/TP bracket, wait for fill, then arm management — or cancel on timeout.

* **Source:** `src/main/java/strategies/PendingBracket.java`
* **Entry:** `static long run(MT4Sugar sugar, StrategyPreset sp, RiskPreset rp, int timeoutSeconds)` → ticket
* **Demo:** `run.bat 15`

---

## What it does

1. Applies presets as defaults; `ensureConnected` / `ensureSymbol`.
2. **Resolves the entry price** — `StrategyPreset.entryPrice` if set, otherwise 5 pips below market (so the demo fills on a small dip).
3. Sizes the lot and places a **BUY LIMIT** with a pip bracket (`buyLimit`).
4. **Waits for fill** up to `timeoutSeconds`. On timeout it cancels pendings (`cancelPendings`) and returns.
5. On fill: optional **trailing** / **auto-breakeven** (if set on the `RiskPreset`), manages briefly, then `close`.

Use it for patient, price-specific entries that protect themselves once live.

---

## Signature

```java
public static long run(MT4Sugar sugar, StrategyPreset sp, RiskPreset rp, int timeoutSeconds) throws Exception
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `sugar` | `MT4Sugar` | A connected high-level client. |
| `sp` | `StrategyPreset` | `entryPrice` (optional — else 5 pips below market), `lots`, `magic`, `comment`. |
| `rp` | `RiskPreset` | `slPips`/`tpPips` bracket; optional `trailingPips` / `beTriggerPips`. |
| `timeoutSeconds` | `int` | how long to wait for the pending order to fill before cancelling. |

**Returns:** the pending order's ticket (`long`) — note that on a fill-timeout the order is cancelled and the ticket is returned for reference.

---

## Example

```java
StrategyPreset sp = new StrategyPreset("EURUSD").limitAt(1.0900).lots(0.01).magic(103).comment("pending bracket");
RiskPreset rp = new RiskPreset(1.0, 20).tp(40).trailing(15);

long ticket = PendingBracket.run(sugar, sp, rp, 30);   // wait up to 30s for the fill
```

> Use `.limitAt(price)` on the `StrategyPreset` to set the pending price explicitly; omit it and the demo places the limit 5 pips below the current mid.

---

## 📚 See also

* [Strategies — Master Overview](../Strategies.Master.Overview.md)
* [Presets](../Presets/Presets.md)
* [Market One-Shot](./MarketOneShot.md) · [Bracket + Trailing](./BracketTrailing.md)
* [MT4Sugar — Order Entry](../../MT4Sugar/5.%20Order_Entry.md)
