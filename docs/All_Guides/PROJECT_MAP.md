# 🗺️ JavaMT4 Project Map

> The whole project as one annotated tree, with links. **Links point to section *overviews*, never to individual method pages** — open an overview to drill down.

---

## 🧱 The three layers

JavaMT4 is built in three decoupled layers. Your code can enter at any level.

| Layer | Source | What it does | Overview |
|-------|--------|--------------|----------|
| **3 · MT4Sugar** | `src/main/java/pro/mrpc/mt4/MT4Sugar.java` | Convenience on top of Service: pip-based SL/TP, risk sizing, price/lot normalization, trailing/breakeven, batch close/cancel, watchers. | [MT4Sugar overview](../MT4Sugar/MT4Sugar.Master.Overview.md) |
| **2 · MT4Service** | `src/main/java/pro/mrpc/mt4/MT4Service.java` | Typed facade: friendly args (string timeframe/sort, `side`/`type`), trade shortcuts. Returns the typed `…Data`. | [MT4Service overview](../MT4Service/MT4Service.Overview.md) |
| **1 · MT4Account** | `src/main/java/pro/mrpc/mt4/MT4Account.java` | Low-level gRPC: proto request/reply, connect/session, auto-reconnect, streaming. Everything builds on it. | [MT4Account overview](../MT4Account/MT4Account.Master.Overview.md) |

```
Your code  →  MT4Sugar  →  MT4Service  →  MT4Account  →  gRPC  →  MRPC gateway → MT4 terminal
```

---

## 🌳 Repository tree

- **`JavaMT4/`** — repo root (SDK layers + runnable demo project)
  - **`src/main/java/pro/mrpc/mt4/`** — the SDK
    - `MT4Account.java` — **Layer 1** → [overview](../MT4Account/MT4Account.Master.Overview.md)
    - `MT4Service.java` — **Layer 2** → [overview](../MT4Service/MT4Service.Overview.md)
    - `MT4Sugar.java` — **Layer 3** → [overview](../MT4Sugar/MT4Sugar.Master.Overview.md)
    - `ApiExceptionMT4.java`, `ConnectExceptionMT4.java` — exceptions → [Errors](ERRORS.md)
    - **`error/`** — `MT4Error`, `OrderRejected`, `ModifyRejected`, `RateLimitExceeded`, `ConnectivityError` (Sugar-layer exceptions)
    - **`util/`** — `HookBus` (lifecycle events), `RateLimiter`
  - **`package/mt4_term_api/`** — generated gRPC / protobuf stubs (the raw contract), compiled with the project
  - **`src/main/java/examples/`** — runnable demos (the numbered menu)
    - `Program.java` — the launcher / menu runner → [Getting Started](GETTING_STARTED.md)
    - `Common.java` — reads `appsettings.json` (+ `MRPC_API_KEY`)
    - **`lowlevel/`** — `MarketDataExample`, `TradingExample`, `StreamingExample` → [MT4Account overview](../MT4Account/MT4Account.Master.Overview.md)
    - **`services/`** — `ServiceDemo` → [MT4Service overview](../MT4Service/MT4Service.Overview.md)
    - **`sugar/`** — `SugarBasics`, `SugarTrading`, `SugarPositions` → [MT4Sugar overview](../MT4Sugar/MT4Sugar.Master.Overview.md)
    - **`usercode/`** — `UserCode` (your sandbox entry)
  - **`src/main/java/strategies/`** — preset-driven strategy runners
    - `MarketOneShot`, `BracketTrailing`, `PendingBracket` — orchestrators
    - `StrategyPreset`, `RiskPreset`, `Presets` — fluent presets + catalog
  - **`docs/`** — this documentation site
  - `pom.xml` — Maven build (Java 17, mainClass `Program`) · `run.bat` — compile & run · `appsettings.json` — your credentials

---

## 📚 Documentation tree

- **`docs/`**
  - [Home](../index.md) — landing page
  - **`All_Guides/`** — these guides (Getting Started, Your First Project, Streams, Errors, Glossary, Return Codes, ENUMs, MT4 for Beginners, this map)
  - **`MT4Account/`** — low-level docs → [overview](../MT4Account/MT4Account.Master.Overview.md) *(Master + 5 group folders, per-method pages inside)*
  - **`MT4Service/`** — facade docs → [overview](../MT4Service/MT4Service.Overview.md) *(Overview + 5 group pages)*
  - **`MT4Sugar/`** — convenience docs → [overview](../MT4Sugar/MT4Sugar.Master.Overview.md) *(Master + 9 group pages)*

---

## 🎯 Which layer should you use?

- **Most trading apps → [MT4Sugar](../MT4Sugar/MT4Sugar.Master.Overview.md).** Pip-based SL/TP, risk sizing and auto-normalization protect you from off-quote and invalid-volume rejections.
- **Custom execution → [MT4Service](../MT4Service/MT4Service.Overview.md).** Typed calls when you manage your own sizing/normalization.
- **Protocol-level control → [MT4Account](../MT4Account/MT4Account.Master.Overview.md).** Raw proto structures and streams.
