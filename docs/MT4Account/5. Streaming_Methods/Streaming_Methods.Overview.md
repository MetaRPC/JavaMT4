# 5. Streaming Methods — Overview

> Real-time server streams: ticks, trade events, ticket lists, open-order profit and connection state.

## 📁 What lives here

* **[Symbol Tick Stream](on_symbol_tick.md)** — Subscribe to real-time tick (bid/ask) updates for one or more symbols.
* **[Trade Events Stream](on_trade.md)** — Subscribe to trade events (orders opened, updated, removed).
* **[Opened Order Tickets Stream](on_opened_orders_tickets.md)** — Poll-stream the live lists of open position & pending-order tickets.
* **[Open-Order Profit Stream](on_opened_orders_profit.md)** — Stream open-order profit/loss updates (plus an account snapshot).
* **[Connection State Stream](on_connect_state.md)** — Subscribe to terminal connection-state changes.

The first four live on `mt4_term_api.SubscriptionService`; `onConnectState` lives on `mt4_term_api.Connection`.

---

## The Java streaming model

Unlike the blocking request methods, every `on…` call is **callback-based**:

```java
Subscription onSymbolTick(List<String> symbols,
                          Consumer<OnSymbolTickData> onData,
                          Consumer<Throwable> onError)
```

* It returns a **`MT4Account.Subscription`** (an `AutoCloseable`). The stream runs on a background thread and invokes `onData` for each message.
* **Stop it by closing the subscription** — ideally with try-with-resources:

```java
try (MT4Account.Subscription s = account.onSymbolTick(List.of("EURUSD"), d -> {
        var t = d.getSymbolTick();
        System.out.printf("%s %.5f/%.5f%n", t.getSymbol(), t.getBid(), t.getAsk());
    }, err -> System.out.println("stream error: " + err.getMessage()))) {
    Thread.sleep(10_000);   // consume for 10 s, then close() cancels the stream
}
```

* **Auto-reconnect is built in** — a transient gRPC drop reopens the stream and keeps delivering to your callback.
* `onError` is called for terminal errors; a `null` error consumer is allowed (errors are then swallowed after reconnect attempts).

> This is the Java analogue of PyMT4's async generators + `cancellation_event`: `onData` replaces `async for`, and `Subscription.close()` replaces setting the cancellation event. See [Streams](../../All_Guides/STREAMS.md).

---

## Quick choose

| Method | What it does |
|--------|--------------|
| `onSymbolTick(symbols, onData, onError)` | Real-time tick (bid/ask) updates for one or more symbols. |
| `onTrade(onData, onError)` | Trade events (orders opened, updated, removed). |
| `onOpenedOrdersTickets(pullIntervalMs, onData, onError)` | Live lists of open position & pending-order tickets. |
| `onOpenedOrdersProfit(timerPeriodMs, onData, onError)` | Open-order profit/loss updates (plus an account snapshot). |
| `onConnectState(terminalIds, onData, onError)` | Terminal connection-state changes. |

---

* **Back to:** [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
