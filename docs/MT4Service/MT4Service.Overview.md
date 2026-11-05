# MT4Service — Overview

> The **mid-level facade**: the same operations as `MT4Account`, but with friendlier Java signatures.

`MT4Service` is **Layer 2**. It wraps a connected [`MT4Account`](../MT4Account/MT4Account.Master.Overview.md) and:

* accepts **string enums** (`timeframe = "H1"`, `sort = "open_desc"`) instead of protobuf constants,
* takes **friendly arguments** (`side`/`type`, `lots`, `sl`, `tp`, `magic`, `deviationPips`) and maps them to the raw request,
* adds **trade shortcuts** (`buyMarket`, `sellLimit`, `close`, …) that return the ticket directly,
* fills **sensible defaults** (e.g. a history window for `quoteHistory` from `timeframe × limit`).

Unlike the Python original, it returns the **typed protobuf `…Data` messages** (not flattened lists) — list filtering/formatting and risk math live one layer up in [`MT4Sugar`](../MT4Sugar/MT4Sugar.Master.Overview.md).

Defined in [`MT4Service.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Service.java).

```
MT4Sugar    (HIGH)   one-liners, risk sizing, watchers
MT4Service  (MID)    friendly facade  ← you are here
MT4Account  (LOW)    raw gRPC / protobuf
```

---

## 🚦 Method groups

Each group is documented on a single page.

* **[1. Account Information](./1.%20Account_Information.md)** — `accountSummary`, `tradeStats`, `tradeUnrealizedPl`, `equityHistory`, `tradeStatsEquityHistory`
* **[2. Orders & History](./2.%20Orders_History.md)** — `openedOrders`, `openedOrdersTickets`, `ordersHistory`
* **[3. Symbols & Quotes](./3.%20Symbols_Quotes.md)** — `symbols`, `quote`, `quoteMany`, `quoteHistory`, `symbolParamsMany`, `tickValueWithSize`, `symbolSelect`, `serverTimezone`
* **[4. Trading Operations](./4.%20Trading_Operations.md)** — `orderSend`, `orderModify`, `orderCloseDelete`, `orderCloseBy` **+** the trade shortcuts (`buyMarket`, `sellMarket`, `buyLimit`/`buyStop`, `sellLimit`/`sellStop`, `modifySlTp`, `close`, `closePartial`, `closeBy`, `setTradeDefaults`)
* **[5. Streaming Methods](./5.%20Streaming_Methods.md)** — `onSymbolTick`, `onTrade`, `onOpenedOrdersTickets`, `onOpenedOrdersProfit`, `onConnectState`

---

## 🔌 Construction, connection & lifecycle

`MT4Service` wraps an `MT4Account` you pass to its constructor.

| Method | Description |
|--------|-------------|
| `new MT4Service(MT4Account account)` | Wrap a (connected or not-yet-connected) account. |
| `connectByServerName(serverName)` / `(serverName, timeoutSeconds)` | Connect via broker server name. |
| `connectByHostPort(host, port)` / `(host, port, timeoutSeconds)` | Connect to a terminal host/port. |
| `reconnect()` | Re-establish the session. |
| `disconnect(boolean delete)` | Release the session (delete the terminal instance when `true`). |
| `getAccount()` | The wrapped `MT4Account`. |

```java
import pro.mrpc.mt4.MT4Account;
import pro.mrpc.mt4.MT4Service;

try (MT4Account account = new MT4Account(12345678L, "password", null, null, "TRIAL")) {
    MT4Service service = new MT4Service(account);
    service.connectByServerName("MetaQuotes-Demo", 30);

    var s = service.accountSummary();
    System.out.println(s.getAccountBalance() + " " + s.getAccountEquity());

    service.disconnect(false);
}
```

---

## 🔁 Friendly-string converters (public static)

The service exposes its mappers as static helpers you can reuse:

| Method | Maps |
|--------|------|
| `toOperationType(side, type)` | `"buy"/"sell"` + `"market"/"limit"/"stop"` → `OrderSendOperationType` |
| `toTimeframe(tf)` | `"M1".."MN1"` (or a raw enum name) → `ENUM_QUOTE_HISTORY_TIMEFRAME` |
| `toOpenedSort(sort)` | `"open_asc"/"open_desc"/"ticket_asc"/"ticket_desc"` → `EnumOpenedOrderSortType` |
| `toHistorySort(sort)` | `"open_*"/"close_*"/"ticket_*"` → `EnumOrderHistorySortType` |

---

## See also

* [MT4Account — Master Overview](../MT4Account/MT4Account.Master.Overview.md)
* [MT4Sugar — Overview](../MT4Sugar/MT4Sugar.Master.Overview.md)
* [Errors](../All_Guides/ERRORS.md)
