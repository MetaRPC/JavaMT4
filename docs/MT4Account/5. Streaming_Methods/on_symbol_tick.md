# Symbol Tick Stream

> A live stream of tick (bid/ask) updates for one or more symbols.

**API Information:**

* **Low-level API:** `MT4Account.onSymbolTick(List<String> symbols, Consumer<OnSymbolTickData> onData, Consumer<Throwable> onError)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.SubscriptionService`
* **RPC:** `OnSymbolTick(OnSymbolTickRequest) -> stream of OnSymbolTickReply`
* **Payload:** `OnSymbolTickData`

## 💬 Just the essentials

* **What it is.** A subscription that calls your `onData` with a tick every time the price of a subscribed symbol changes.
* **Why you need it.** To react to price in real time instead of polling [quote](../3.%20Symbols_Quotes/quote.md).
* **When to use.** Live strategies, dashboards, spread/price monitors. Close the `Subscription` to stop.

---

## 🎯 Purpose

* Live bid/ask per symbol
* Subscribe to several symbols in one stream
* Clean stop via `Subscription.close()`

---

## Method Signature

```java
public Subscription onSymbolTick(
        List<String> symbols,
        Consumer<OnSymbolTickData> onData,
        Consumer<Throwable> onError)
```

Returns a `MT4Account.Subscription` (`AutoCloseable`). Close it to stop the stream.

**Request message:**

```protobuf
message OnSymbolTickRequest {
  repeated string symbol_names = 1;
}
```

**Payload message (field numbers per our stub):**

```protobuf
message OnSymbolTickData {
  OnSymbolMqlTickInfo symbol_tick = 1;
  string terminal_instance_guid_id = 2;
}

message OnSymbolMqlTickInfo {
  Timestamp time = 1;
  double bid = 2;
  double ask = 3;
  double last = 4;
  uint64 volume = 5;
  int64 time_msc = 6;
  string symbol = 7;
  Timestamp time_utc = 8;
  int64 time_msc_utc = 9;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `symbols` | `List<String>` | Symbols to subscribe to. |
| `onData` | `Consumer<OnSymbolTickData>` | Called for each tick. |
| `onError` | `Consumer<Throwable>` | Called on a terminal stream error (may be `null`). |

---

## ⬆️ Output — `onData` receives `OnSymbolTickData`

Each item has `getSymbolTick()` (`OnSymbolMqlTickInfo`):

| Field | Java getter | Description |
|-------|-------------|-------------|
| `symbol` | `getSymbol()` | Symbol |
| `bid` / `ask` | `getBid()` / `getAsk()` | Current bid / ask |
| `last` | `getLast()` | Last price (if provided) |
| `volume` | `getVolume()` | Tick volume |
| `time` / `time_utc` | `getTime()` / `getTimeUtc()` | Tick time (server / UTC) |

---

## 🧩 Notes & Tips

* **One callback per tick.** Each `onData` call is one tick for one of the subscribed symbols — check `tick.getSymbol()` to tell them apart.
* **Close to stop.** Use try-with-resources or call `subscription.close()`, otherwise the stream (and its reconnect loop) keeps running — see [Streams](../../All_Guides/STREAMS.md).
* **Auto-reconnect.** Transient gRPC drops are retried under the hood; your `onData` keeps firing across a reconnect.
* **No ticks on a closed market.** On weekends/holidays the stream stays open but delivers nothing.
* **For a one-shot price** use [quote](../3.%20Symbols_Quotes/quote.md); for several at once [quote_many](../3.%20Symbols_Quotes/quote_many.md).

---

## 🔗 Usage Examples

### 1) Stream N ticks then stop

```java
static void firstTicks(MT4Account account, String symbol, int n) throws InterruptedException {
    var latch = new CountDownLatch(n);
    try (var s = account.onSymbolTick(List.of(symbol), d -> {
            var t = d.getSymbolTick();
            System.out.printf(Locale.ROOT, "%s: bid=%.5f ask=%.5f%n", t.getSymbol(), t.getBid(), t.getAsk());
            latch.countDown();
        }, err -> { while (latch.getCount() > 0) latch.countDown(); })) {
        latch.await(20, TimeUnit.SECONDS);
    }   // close() cancels the stream
}
```

### 2) Keep the latest price per symbol

Maintain a live snapshot map you can read from elsewhere.

```java
static Map<String, double[]> livePrices(MT4Account account, List<String> symbols) {
    var latest = new ConcurrentHashMap<String, double[]>();
    account.onSymbolTick(symbols, d -> {
        var t = d.getSymbolTick();
        latest.put(t.getSymbol(), new double[]{t.getBid(), t.getAsk()});
    }, null);   // keep the Subscription if you want to close it later
    return latest;
}
```

### 3) Alert on a price level

Close the stream as soon as a target is hit.

```java
static boolean waitForPrice(MT4Account account, String symbol, double target, boolean above, long timeoutMs)
        throws InterruptedException {
    var hit = new CountDownLatch(1);
    try (var s = account.onSymbolTick(List.of(symbol), d -> {
            double bid = d.getSymbolTick().getBid();
            if ((above && bid >= target) || (!above && bid <= target)) {
                System.out.printf(Locale.ROOT, "%s hit %.5f (bid=%.5f)%n", symbol, target, bid);
                hit.countDown();
            }
        }, null)) {
        return hit.await(timeoutMs, TimeUnit.MILLISECONDS);
    }
}
```

---

## ⚠️ Pitfalls

* **No close = leak.** If you never close the `Subscription`, the stream and its reconnect loop run forever.
* **Blocking on a quiet market.** Closed sessions deliver no ticks — use a timeout when awaiting one.
* **Per-tick work must be fast.** Heavy processing inside `onData` backs up the stream — offload to a queue/executor.
* **Distinguish symbols.** With multiple symbols, always branch on `tick.getSymbol()`.

---

## 🧪 Testing Suggestions

* **Market open.** Subscribing to a liquid symbol delivers ticks within a second; `ask >= bid`.
* **Clean stop.** After `close()` the callback stops firing promptly without leaking threads.
* **Timeout path.** On a closed market, an awaited latch times out rather than hanging forever.

---

## 📚 See also

* [Quote](../3.%20Symbols_Quotes/quote.md)
* [Trade Events Stream](on_trade.md)
* [Streams](../../All_Guides/STREAMS.md)

* **Back to:** [Streaming Methods — Overview](Streaming_Methods.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
