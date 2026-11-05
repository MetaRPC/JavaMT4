# Streams

The gateway pushes real-time data over gRPC server streams. In JavaMT4 every stream is a **callback subscription**: you pass a `Consumer<…Data> onData` (and an optional `Consumer<Throwable> onError`), and the call returns a `MT4Account.Subscription` — an `AutoCloseable` you close to stop.

| Method (`MT4Account` / `MT4Service`) | What it delivers |
|--------------------------------------|------------------|
| [`onSymbolTick`](../MT4Account/5.%20Streaming_Methods/on_symbol_tick.md) | bid/ask ticks for the given symbols |
| [`onTrade`](../MT4Account/5.%20Streaming_Methods/on_trade.md) | trade events — new / updated / removed orders |
| [`onOpenedOrdersProfit`](../MT4Account/5.%20Streaming_Methods/on_opened_orders_profit.md) | floating P/L of open orders, on a timer |
| [`onOpenedOrdersTickets`](../MT4Account/5.%20Streaming_Methods/on_opened_orders_tickets.md) | open position & pending ticket lists, sampled |
| [`onConnectState`](../MT4Account/5.%20Streaming_Methods/on_connect_state.md) | terminal connection-state changes |

---

## Using a stream

Close the subscription to stop it — try-with-resources is the clean way:

```java
import java.util.List;
import java.util.concurrent.TimeUnit;
import pro.mrpc.mt4.MT4Account;

// account: a connected MT4Account
try (MT4Account.Subscription s = account.onOpenedOrdersProfit(1000, ev -> {
        double total = ev.getOpenedOrdersWithProfitUpdatedList().stream()
                .mapToDouble(o -> o.getOrderProfit()).sum();
        System.out.printf("floating P/L: %+.2f%n", total);
    }, err -> System.err.println("stream error: " + err.getMessage()))) {

    TimeUnit.SECONDS.sleep(10);   // consume for 10 s
}   // close() cancels the stream
```

If you don't use try-with-resources, keep the returned `Subscription` and call `close()` when done.

---

## How subscriptions behave

* **One callback per message.** `onData` is invoked on a background thread for each update; keep per-message work light (offload heavy processing to a queue/executor).
* **Auto-reconnect is built in.** A transient gRPC drop reopens the stream under the hood and keeps delivering to your callback — you don't re-subscribe.
* **`onError`** is invoked for a terminal stream error; it may be `null` (errors are then swallowed after reconnect attempts).
* **Close to stop.** Nothing stops a stream except closing its `Subscription` (or closing the owning `MT4Account`). An unclosed subscription keeps its reconnect loop alive.
* **Quiet markets deliver nothing.** Tick/trade streams are silent on a closed market; the ticket/profit/connect-state streams are timer- or state-driven and still fire.

---

## Through the higher layers

* [`MT4Service`](../MT4Service/5.%20Streaming_Methods.md) exposes the same five methods with the same `Consumer`/`Subscription` shape.
* [`MT4Sugar`](../MT4Sugar/8.%20Watchers_Waiters.md) wraps them as **watchers** (`watchTicks`, `watchProfit`, …) that return a `String` id and are stopped with `unwatch(id)` — handy when you want the SDK to track the subscription for you.

---

## See also

* [MT4Account — Streaming Methods](../MT4Account/5.%20Streaming_Methods/Streaming_Methods.Overview.md)
* [MT4Sugar — Watchers & Waiters](../MT4Sugar/8.%20Watchers_Waiters.md)
* [Errors](ERRORS.md)
