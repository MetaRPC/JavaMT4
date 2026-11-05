# Strategies — Master Overview

> The ready-made **orchestrators** and **presets** that ship with JavaMT4: how to pick one, and links to each spec.

---

## ⚠️ Educational & demonstration only

These orchestrators and presets are **demonstration templates** — starting points for your own strategies, not production trading systems.

* Simplified logic, no portfolio-level risk controls, no economic-calendar integration.
* **Demo accounts only.** Each one opens a real 0.01-lot position and **closes it again** at the end of the demo window.
* Trading involves substantial risk of loss; you are responsible for your decisions.

They live under `src/main/java/strategies/` and are driven by [`MT4Sugar`](../MT4Sugar/MT4Sugar.Master.Overview.md). The bundled examples run them from the menu (`run.bat 11/12/15/16`).

---

## 🚦 Orchestrators

Each orchestrator is a single `static long run(MT4Sugar sugar, StrategyPreset sp, RiskPreset rp, …)` that takes a [`StrategyPreset`](./Presets/Presets.md#strategypreset) (what/where to enter) and a [`RiskPreset`](./Presets/Presets.md#riskpreset) (how much to risk / protect), opens and manages a position, and returns the **ticket**.

| Orchestrator | `run(...)` extra arg | What it does | Demo |
|--------------|----------------------|--------------|------|
| **[Market One-Shot](./Orchestrators/MarketOneShot.md)** | — | Market entry with an automatic SL/TP bracket, optional trailing / auto-breakeven. | `run.bat 11` |
| **[Bracket + Trailing](./Orchestrators/BracketTrailing.md)** | `double activateAtPips` | Entry with a bracket; the trailing stop switches on only once price is far enough in profit. | `run.bat 12` |
| **[Pending Bracket](./Orchestrators/PendingBracket.md)** | `int timeoutSeconds` | Place a BUY LIMIT with a bracket, wait for fill (else cancel), then arm management. | `run.bat 15` |

> MT4-specific note: there is no Grid-DCA or OCO-straddle orchestrator in this SDK — MT4's single order list and broker hedging rules make those broker-dependent; build them yourself from the [MT4Sugar](../MT4Sugar/MT4Sugar.Master.Overview.md) primitives if you need them.

---

## 🎛️ Presets

Two small fluent classes configure every orchestrator (see **[Presets](./Presets/Presets.md)**):

* **`StrategyPreset`** — `symbol`, `useMarket`, `entryPrice`, `lots`, `magic`, `deviationPips`, `comment`.
* **`RiskPreset`** — `riskPercent`, `slPips`, `tpPips`, `trailingPips`, `beTriggerPips`, `bePlusPips`.

Ready-made factories ship in `Presets`: `marketEurusd()`, `limitEurusd(price)`, and the risk profiles `conservative()` / `balanced()` / `aggressive()`. `run.bat 16` prints them all and runs a demo.

---

## 🔌 Usage pattern (Java)

```java
import pro.mrpc.mt4.*;
import strategies.*;

try (MT4Account account = new MT4Account(12345678L, "password", null, null, "TRIAL")) {
    account.connectByServerName("MetaQuotes-Demo", 30);
    MT4Sugar sugar = new MT4Sugar(new MT4Service(account));

    StrategyPreset sp = new StrategyPreset("EURUSD").lots(0.01).magic(101).comment("market one-shot");
    RiskPreset     rp = new RiskPreset(1.0, 20).tp(40);

    long ticket = MarketOneShot.run(sugar, sp, rp);
    System.out.println("done #" + ticket);

    account.disconnect(false);
}
```

---

## 🧭 Which orchestrator?

* **Simple entry with protection** → [Market One-Shot](./Orchestrators/MarketOneShot.md)
* **Let winners run** → [Bracket + Trailing](./Orchestrators/BracketTrailing.md)
* **Wait at a price with a bracket** → [Pending Bracket](./Orchestrators/PendingBracket.md)

---

## 📖 Related

* [MT4Sugar — Master Overview](../MT4Sugar/MT4Sugar.Master.Overview.md)
* [MT4Service — Overview](../MT4Service/MT4Service.Overview.md)
* [MT4Account — Master Overview](../MT4Account/MT4Account.Master.Overview.md)

---

*Trade safely, code cleanly — and always close what you open.*
