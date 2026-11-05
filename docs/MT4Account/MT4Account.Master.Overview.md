# MT4Account — Master Overview

> One page to orient fast: the low-level gRPC client for MetaTrader 4. Jump to any group overview or method spec from here.

`MT4Account` is **Layer 1** — direct gRPC/protobuf calls to the MT4 terminal gateway. [`MT4Service`](../MT4Service/MT4Service.Overview.md) (typed facade) and [`MT4Sugar`](../MT4Sugar/MT4Sugar.Master.Overview.md) (high-level helpers) build on top of it.

It exposes **25 feature methods** across 5 groups (plus connection & stream helpers). MT4 has a single order list — no positions/deals split, Depth-of-Market, `order_check` or `order_calc_*` (those are MT5-only).

Defined in [`src/main/java/pro/mrpc/mt4/MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java).

---

## 🚦 Start here — Section Overviews

* **[1. Account Information — Overview](./1.%20Account_Information/Account_Information.Overview.md)**
  Account snapshot plus performance: balance/equity/margin, trade statistics, equity curve and unrealized-P/L extrema.
* **[2. Orders & History — Overview](./2.%20Orders_History/Orders_History.Overview.md)**
  Currently open orders (market + pending), their ticket lists, and closed-order history.
* **[3. Symbols & Quotes — Overview](./3.%20Symbols_Quotes/Symbols_Quotes.Overview.md)**
  Symbol list, live quotes, OHLC bar history, per-symbol parameters, tick value, Market Watch select and server timezone.
* **[4. Trading Operations — Overview](./4.%20Trading_Operations/Trading_Operations.Overview.md)**
  Send market/pending orders, modify SL/TP/price, close or delete, and close-by.
* **[5. Streaming Methods — Overview](./5.%20Streaming_Methods/Streaming_Methods.Overview.md)**
  Real-time server streams: ticks, trade events, ticket lists, open-order profit and connection state.

---

## 🧭 Method groups

| Group | Methods |
|-------|---------|
| Account Information | `accountSummary`, `tradeStats`, `tradeUnrealizedPl`, `equityHistory`, `tradeStatsEquityHistory` |
| Orders & History | `openedOrders`, `openedOrdersTickets`, `ordersHistory` |
| Symbols & Quotes | `symbols`, `quote`, `quoteMany`, `quoteHistory`, `symbolParamsMany`, `tickValueWithSize`, `symbolSelect`, `serverTimezone` |
| Trading Operations | `orderSend`, `orderModify`, `orderCloseDelete`, `orderCloseBy` |
| Streaming Methods | `onSymbolTick`, `onTrade`, `onOpenedOrdersTickets`, `onOpenedOrdersProfit`, `onConnectState` |

---

## 🔌 Usage pattern (Java)

`MT4Account` is **synchronous** and implements `AutoCloseable`: each call blocks until the reply arrives, returns the reply's typed `…Data` payload, or throws [`ApiExceptionMT4`](../All_Guides/ERRORS.md) on a server-side error. Wrap it in try-with-resources so the gRPC channel is always closed.

```java
import pro.mrpc.mt4.MT4Account;
import mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryData;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendOperationType;

try (MT4Account account = new MT4Account(12345678L, "your_password", null, null, "TRIAL")) {
    // null grpcServer -> defaults to mt4.mrpc.pro:443 (TLS)
    account.connectByServerName("MetaQuotes-Demo", 30);

    AccountSummaryData summary = account.accountSummary();
    System.out.println(summary.getAccountBalance() + " " + summary.getAccountEquity());

    account.orderSend("EURUSD", OrderSendOperationType.OC_OP_BUY, 0.01);

    account.disconnect(false);
}   // close() shuts the channel down
```

---

## 🧱 How the call contract works

Every feature method follows the same shape:

1. `ensureConnected()` — fail fast if `connect…` has not run.
2. Build the protobuf `…Request`.
3. `callWithReconnect(...)` — invoke the gRPC stub; on `UNAVAILABLE` or a *terminal-not-found* error code it reconnects and retries automatically; any other error is thrown.
4. `dataOf(reply)` — unwrap the reply's `data` field, or throw [`ApiExceptionMT4`](../All_Guides/ERRORS.md) carrying the reply's `error`.

So you always get the typed `…Data` object directly — never a raw `…Reply` with a nested error to check by hand.

---

## 🔐 Constructor & connection

```java
MT4Account(long user, String password, String grpcServer, String id, String apiKey)
```

| Arg | Meaning |
|-----|---------|
| `user` | MT4 account login |
| `password` | account password |
| `grpcServer` | gateway `host:port`; `null` → `mt4.mrpc.pro:443` |
| `id` | terminal instance id; `null` on first connect (the gateway assigns one) |
| `apiKey` | API key; `"TRIAL"` works for demo |

Connect with either the broker's server **name** or an explicit **host/port**:

```java
account.connectByServerName("MetaQuotes-Demo", 30);            // by broker server name
account.connectByHostPort("mt4live.broker.com", 443, 30);      // by address
```

See **[Connection & lifecycle](../All_Guides/GETTING_STARTED.md)** for the full set (`disconnect`, `reconnect`, `close`).

---

## 🧩 Layer map

| Layer | Class | What it adds |
|-------|-------|--------------|
| 1 — low-level | **`MT4Account`** | raw gRPC calls, reconnect, typed `…Data` returns |
| 2 — facade | [`MT4Service`](../MT4Service/MT4Service.Overview.md) | friendly args (sort/timeframe strings, `side`/`type`), trade shortcuts |
| 3 — high-level | [`MT4Sugar`](../MT4Sugar/MT4Sugar.Master.Overview.md) | pip math, risk sizing, automation workers, watchers |

---

**See also:** [Getting Started](../All_Guides/GETTING_STARTED.md) · [Your First Project](../All_Guides/Your_First_Project.md) · [Streams](../All_Guides/STREAMS.md) · [Errors](../All_Guides/ERRORS.md)
