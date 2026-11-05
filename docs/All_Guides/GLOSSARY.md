# JavaMT4 Glossary

> Project-specific terms and concepts used throughout the JavaMT4 SDK and these docs.

---

## 🏗️ Architectural terms

### Three-layer architecture

The core design: three decoupled abstraction levels. Your code can enter at any level.

* **Layer 1 — [`MT4Account`](../MT4Account/MT4Account.Master.Overview.md):** low-level gRPC communication with the MT4 terminal.
* **Layer 2 — [`MT4Service`](../MT4Service/MT4Service.Overview.md):** typed facade over MT4Account with friendly args and trade shortcuts.
* **Layer 3 — [`MT4Sugar`](../MT4Sugar/MT4Sugar.Master.Overview.md):** high-level one-liners — pip SL/TP, risk sizing, trailing, watchers.

### MT4Account

Base layer: direct gRPC calls to the terminal. Builds the protobuf `…Request`, retries on transient drops (`callWithReconnect`), and returns the reply's typed `…Data` or throws `ApiExceptionMT4`. 25 feature methods across 5 groups, plus connect/session and streaming helpers. Source: `src/main/java/pro/mrpc/mt4/MT4Account.java`.

### MT4Service

Middle layer wrapping MT4Account one-to-one with friendlier signatures (string timeframe/sort, `side`/`type`) and trade shortcuts (`buyMarket`, `sellLimit`, `close`, …) plus `setTradeDefaults`. Returns the same typed `…Data`.

### MT4Sugar

High-level layer: pip-based SL/TP, risk-based lot sizing, price/lot normalization, trailing/auto-breakeven workers, batch close/cancel, watchers and waiters. Where most strategies live.

---

## ☕ Java-specific concepts

### Synchronous calls

Every request method on `MT4Account`/`MT4Service` **blocks** until the reply arrives and returns the typed payload directly (no `Future`/callback for ordinary calls). This is the Java counterpart of PyMT4's `async/await` — you just call the method.

### Nested value types

`MT4Sugar` returns small plain-data holders instead of protobuf messages: `Quote` (`bid`/`ask`/`time`), `Bar` (OHLC + volume), `Tick`, `ExposureBucket`, `ExposureSummary`. They are the Java analogue of PyMT4's dataclasses.

### Callback streams & `Subscription`

Streaming methods are **callback-based**: you pass a `Consumer<…Data> onData` and get back a `MT4Account.Subscription` (an `AutoCloseable`). Messages are delivered on a background thread; you stop by closing the subscription. This replaces PyMT4's async generators (`async for`).

### try-with-resources / `AutoCloseable`

`MT4Account` and `Subscription` implement `AutoCloseable`, so `try (…) { }` closes the gRPC channel / cancels the stream automatically — the Java counterpart of Python context managers (`async with`).

### Reading account state

A one-call snapshot via `accountSummary()` (balance, equity, margin, leverage, currency, server time). Re-query before a risk decision; it's a point-in-time read, not a stream.

---

## 📦 Technical concepts

### Normalization

Snapping values to broker constraints: **lots** to the symbol's `VolumeStep` within `[VolumeMin, VolumeMax]` (`normalizeLot`), and **prices** to the symbol's digit grid (`normalizePrice`). Sugar does this automatically on entry/modify, preventing `ERR_INVALID_TRADE_VOLUME` (131) / `ERR_INVALID_PRICE` (129).

### Risk-based lot calculation

`calcLotByRisk(symbol, riskPercent, stopPips, balance)` turns a risk budget (% of balance over a pip stop) into a normalized lot size, using the symbol's tick value and pip size.

### Points vs pips

A **point** is the smallest price increment (e.g. `0.00001` on a 5-digit symbol). A **pip** is the conventional unit — `10 × point` on 3/5-digit symbols, else `1 × point`. The SDK's SL/TP distances are in **pips**; the broker's `TradeStopsLevel` is in **points**.

### Pip-based order entry

Passing SL/TP as pip distances (`slPips`/`tpPips`) rather than absolute prices; Sugar converts them from the entry price via `pipSize`. Absolute prices (`slPrice`/`tpPrice`) are also accepted.

### Trailing stop

A background worker (`setTrailingStop`) that moves the SL to stay `distancePips` behind the mid price, only in the favorable direction, optionally throttled by `stepPips`.

### Breakeven

Moving the SL to the entry price (± a few pips) so the trade can no longer lose. One-shot via `closeByBreakeven`, or automatic once price runs `triggerPips` in profit via `autoBreakeven`.

### Hedging

Holding a BUY and a SELL on the same symbol at once. `orderCloseBy` / `closeBy` nets an opposite pair in one operation. Not all brokers permit it.

### Pending order

An order placed away from the market that fills when price reaches it: `*LIMIT` (toward a better price) or `*STOP` (on a breakout). Requires an entry `price`; lives in the same order list until filled or cancelled.

---

## 🔌 gRPC & protocol terms

### gRPC

The RPC framework the gateway speaks (HTTP/2 + protobuf, TLS). The SDK talks to `mt4.mrpc.pro:443`; generated stubs live in `package/mt4_term_api/`.

### Protobuf

The binary message format. Requests/replies are `…Request`/`…Reply` messages; each reply carries either `data` (a typed `…Data`) or an `error`. The SDK unwraps `data` for you.

### Return codes & errors

MT4 has **no** numeric `returned_code`; failures are exceptions (`ApiExceptionMT4`, or `MT4Error` subclasses at the Sugar layer). The MQL4 code lives in `getError().getMqlErrorIntCode()`. See the [Return Codes Reference](RETURN_CODES_REFERENCE.md).

### Streaming pattern

Server-push over gRPC streams, surfaced as callback `Subscription`s with built-in auto-reconnect. See [Streams](STREAMS.md).

---

## 🏛️ Trading terms

### Basic terms

* **Bid / Ask** — sell price / buy price; **spread** = ask − bid.
* **Lot** — trade volume unit (1.0 = standard; 0.01 = micro).
* **SL / TP** — stop-loss / take-profit exit prices.
* **Ticket** — the order's unique id.
* **Magic number** — a tag you attach to identify "your" orders.
* **Balance vs Equity** — balance excludes open P/L; equity = balance + floating P/L.
* **Margin / Free margin / Margin level** — collateral used / available / equity÷margin %.

### Risk amount

The cash you're willing to lose on a trade (e.g. 1% of balance). `calcCashRisk(symbol, lots, stopPips)` values a stop in cash; `calcLotByRisk` inverts it to a lot size.

### Grid trading

Placing layered pending orders at fixed intervals around price. Build it from `buyLimit`/`sellStop` entries and manage with `cancelPendings` / `closeAll`.

### Scalping

Many short-lived trades for small per-trade gains; sensitive to spread (`spreadPips`) and latency.

### News trading (OCO straddle)

Placing a buy-stop above and a sell-stop below price before a news release to catch the breakout; cancel the un-triggered side afterward (`cancelPendings`).

### Volume limits

Per-symbol `VolumeMin` / `VolumeMax` / `VolumeStep` (and sometimes `VolumeLimit`) from [`symbolParamsMany`](../MT4Account/3.%20Symbols_Quotes/symbol_params_many.md); `normalizeLot` enforces them.

---

## 🔑 Configuration terms

### Settings file — `appsettings.json`

The flat config the examples read: `user`, `password`, `serverName`, `baseSymbol`, `apiKey` (+ optional `grpcServer`). `MRPC_API_KEY` overrides the key. See [Getting Started](GETTING_STARTED.md).

---

## 🧩 Cross-component terms

### Batch operations

Acting on many orders at once: `closeAll(symbol, magic, onlyProfit)`, `cancelPendings(symbol, magic)` — filtered bulk exits that loop the open list for you.

### History queries

Reading closed orders over a window with sort/paging: `ordersHistory(sort, since, until, page, pageSize)`; aggregate stats via `tradeStats` / `tradeStatsEquityHistory`.

### Educational project

The bundled `examples/` and `strategies/` are learning material — demo-account oriented, 0.01-lot, self-cleaning. Not production trading advice.

---

## ☕ Java patterns

### Exception handling

All SDK exceptions are **unchecked** (`extends RuntimeException`): `ApiExceptionMT4`, `ConnectExceptionMT4`, and the `MT4Error` family (`OrderRejected`, `ModifyRejected`, `RateLimitExceeded`, `ConnectivityError`). Wrap trading calls in `try/catch`; see [Errors](ERRORS.md).

### Enums

Generated enums are nested in their outer `Mt4TermApi*` class — e.g. `Mt4TermApiTradingHelper.OrderSendOperationType.OC_OP_BUY`. The three operation-enum flavours (`OC_OP_*`, `OO_OP_*`, `SUB_OP_*`) are distinct types; see the [ENUMs Usage Reference](ENUMS_USAGE_REFERENCE.md).

---

## See also

* [Getting Started](GETTING_STARTED.md) · [Your First Project](Your_First_Project.md)
* [Project Map](PROJECT_MAP.md)
* [Return Codes Reference](RETURN_CODES_REFERENCE.md) · [ENUMs Usage Reference](ENUMS_USAGE_REFERENCE.md)
