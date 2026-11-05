# MT4Sugar — Master Overview

> The **high-level API**: one-liners, pip-based SL/TP, risk sizing, trailing stops, watchers and waiters. The best starting point for most strategies.

`MT4Sugar` is **Layer 3**. It sits on top of [`MT4Service`](../MT4Service/MT4Service.Overview.md) and turns the raw operations into ergonomic one-liners — you pass symbols, lots, pips and prices, and it handles normalization, enum mapping and plumbing.

Defined in [`MT4Sugar.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Sugar.java).

```
MT4Sugar    (HIGH)   one-liners, risk sizing, trailing, watchers  ← you are here
MT4Service  (MID)    friendly facade over MT4Account
MT4Account  (LOW)    raw gRPC / protobuf
```

Construct it from a connected service:

```java
import pro.mrpc.mt4.MT4Account;
import pro.mrpc.mt4.MT4Service;
import pro.mrpc.mt4.MT4Sugar;

try (MT4Account account = new MT4Account(12345678L, "password", null, null, "TRIAL")) {
    MT4Service service = new MT4Service(account);
    service.connectByServerName("MetaQuotes-Demo", 30);
    MT4Sugar sugar = new MT4Sugar(service);      // ← the high-level API

    // one-liners:
    MT4Sugar.Quote q = sugar.lastQuote("EURUSD");
    double lots = sugar.calcLotByRisk("EURUSD", 1.0, 50, null);
    long ticket = sugar.buyMarket("EURUSD", lots, 50.0, 100.0, null, null, null, null, null);

    account.disconnect(false);
}
```

---

## 🚦 Method groups

* **[1. Connection & Defaults](./1.%20Connection_Defaults.md)** — `ensureConnected`, `ping`, `ensureSymbol`, `setDefaults`, `getDefault`, `disconnect`
* **[2. Prices & Quotes](./2.%20Prices_Quotes.md)** — `lastQuote`, `midPrice`, `quotes`, `spreadPips`, `bars`, `ticks`, `waitPrice`
* **[3. Symbol Math & Normalization](./3.%20Symbol_Math.md)** — `point`, `digits`, `pipSize`, `pipsToPrice`, `priceToPips`, `normalizePrice`, `normalizeLot`, `tickValue`
* **[4. Risk Sizing](./4.%20Risk_Sizing.md)** — `calcLotByRisk`, `calcCashRisk`, `breakevenPrice`
* **[5. Order Entry](./5.%20Order_Entry.md)** — `buyMarket`, `sellMarket`, `buyLimit`, `sellLimit`, `buyStop`, `sellStop`, `placeOrder`
* **[6. Order Management](./6.%20Order_Management.md)** — `modifySlTpByPips`, `modifySlTpByPrice`, `close`, `closePartial`, `closeBy`, `closeByTickets`, `closeAll`, `cancelPendings`
* **[7. Trailing & Breakeven](./7.%20Trailing_Breakeven.md)** — `setTrailingStop`, `unsetTrailingStop`, `autoBreakeven`, `closeByBreakeven`
* **[8. Watchers & Waiters](./8.%20Watchers_Waiters.md)** — `watchTicks`, `watchTrades`, `watchOpenedOrders`, `watchProfit`, `watchTickets`, `unwatch`, `waitFilled`, `waitClosed`
* **[9. Order Info & Exposure](./9.%20Order_Info.md)** — `findOrders`, `ordersForSymbol`, `positionsValue`, `exposureSummary`, `diagSnapshot`

---

## 🧩 Conventions

* **Pips, not points.** SL/TP and distances are in **pips** (`slPips`, `tpPips`, `distancePips`); Sugar converts to prices via the symbol's pip size (`pip = 10 × point` on 3/5-digit symbols).
* **Normalization is automatic.** Lots are snapped to the volume step/limits and prices to the symbol's digit grid.
* **Defaults.** `setDefaults(symbol, magic, deviationPips, slippagePips, riskPercent)` sets values reused across calls; order entry falls back to them when you pass `null`.
* **Background workers return an id.** `setTrailingStop` / `autoBreakeven` / `watch*` return a `String` subscription id; stop them with `unwatch(id)` (or `unsetTrailingStop(id)`).
* **Hooks.** The public `hooks` (`HookBus`) emits lifecycle events (`on_order_sent`, `on_close`, `on_modify`, …) — register with `sugar.hooks.on("on_order_sent", payload -> …)`.
* **Errors** raise `MT4Error` subclasses (`OrderRejected`, `ModifyRejected`, …) — see [Errors](../All_Guides/ERRORS.md).

---

## Nested value types

Small plain-data holders returned by the price helpers:

| Type | Fields |
|------|--------|
| `MT4Sugar.Quote` | `bid`, `ask`, `time` (`Instant`) |
| `MT4Sugar.Bar` | `time` (epoch s), `open`, `high`, `low`, `close`, `volume` |
| `MT4Sugar.Tick` | `time` (epoch s), `bid`, `ask` |
| `MT4Sugar.ExposureBucket` | `lotsNet`, `lotsLong`, `lotsShort`, `pnl`, `margin` |
| `MT4Sugar.ExposureSummary` | `balance`, `equity`, `margin`, `freeMargin`, `openPositions`, `bySymbol`, `total` |

---

## See also

* [MT4Service — Overview](../MT4Service/MT4Service.Overview.md)
* [MT4Account — Master Overview](../MT4Account/MT4Account.Master.Overview.md)
* [Errors](../All_Guides/ERRORS.md)
* [Streams](../All_Guides/STREAMS.md)
