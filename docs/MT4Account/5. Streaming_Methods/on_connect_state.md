# Connection State Stream

> A live stream of terminal connection-state changes.

**API Information:**

* **Low-level API:** `MT4Account.onConnectState(List<String> terminalIds, Consumer<ConnectStateData> onData, Consumer<Throwable> onError)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.Connection`
* **RPC:** `OnConnectState(OnConnectStateRequest) -> stream of OnConnectStateReply`
* **Payload:** `ConnectStateData`

## 💬 Just the essentials

* **What it is.** A subscription that delivers the terminal's connection state — connected/alive flags, server and account, with a timestamp.
* **Why you need it.** To react to connectivity changes (drops, recoveries) for health monitoring and auto-recovery.
* **When to use.** Supervising a long-running bot. Close the `Subscription` to stop.

---

## 🎯 Purpose

* Connected / alive flags for the terminal session
* Server and account context
* Event timestamps and any error message

---

## Method Signature

```java
public Subscription onConnectState(
        List<String> terminalIds,
        Consumer<ConnectStateData> onData,
        Consumer<Throwable> onError)
```

Pass `null` (or an empty list) for `terminalIds` to watch the current session.

**Request message:**

```protobuf
message OnConnectStateRequest {
  repeated string terminal_ids = 1;   // omit for the current session
}
```

**Payload message (field numbers per our stub):**

```protobuf
message ConnectStateData {
  string id = 1;
  string state = 2;            // e.g. "Connected"
  bool is_connected = 3;
  bool is_alive = 4;
  bool api_is_alive = 5;
  string server = 6;
  uint64 account = 7;
  string error_message = 8;
  Timestamp timestamp = 9;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `terminalIds` | `List<String>`, optional | Restrict to specific terminal ids; pass `null` for the current session. |
| `onData` | `Consumer<ConnectStateData>` | Called on each state update. |
| `onError` | `Consumer<Throwable>` | Called on a terminal stream error (may be `null`). |

---

## ⬆️ Output — `onData` receives `ConnectStateData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `state` | `getState()` | State label (e.g. `"Connected"`) |
| `is_connected` | `getIsConnected()` | Session is connected to the server |
| `is_alive` | `getIsAlive()` | Terminal is alive |
| `api_is_alive` | `getApiIsAlive()` | API layer is alive |
| `server` | `getServer()` | Server name |
| `account` | `getAccount()` | Account login |
| `error_message` | `getErrorMessage()` | Error text, if any |
| `timestamp` | `getTimestamp()` | Event time |
| `id` | `getId()` | Terminal/session id |

---

## 🧩 Notes & Tips

* **`state` is a string**, not an enum — compare against labels like `"Connected"`, or rely on the `getIsConnected()` / `getIsAlive()` booleans.
* **Three liveness flags.** `isConnected` (to the broker), `isAlive` (terminal), `apiIsAlive` (gateway) — a healthy session has all three true.
* **Check `errorMessage`** when a flag flips false — it carries the reason.
* **Works regardless of market hours** — it reports session health, not prices.
* **Close to stop** the subscription — see [Streams](../../All_Guides/STREAMS.md).

---

## 🔗 Usage Examples

### 1) Watch connection state

```java
static Subscription watchState(MT4Account account) {
    return account.onConnectState(null, ev ->
            System.out.printf("%s connected=%s alive=%s server=%s%n",
                    ev.getState(), ev.getIsConnected(), ev.getIsAlive(), ev.getServer()), null);
}
```

### 2) Wait until connected

Block until the session reports a healthy state (with a timeout).

```java
static boolean waitConnected(MT4Account account, long timeoutMs) throws InterruptedException {
    var ok = new CountDownLatch(1);
    try (var s = account.onConnectState(null, ev -> {
            if (ev.getIsConnected() && ev.getIsAlive()) ok.countDown();
        }, null)) {
        return ok.await(timeoutMs, TimeUnit.MILLISECONDS);
    }
}
```

### 3) Alert on disconnect

Fire a callback the moment connectivity drops.

```java
static Subscription onDisconnect(MT4Account account, BiConsumer<String, String> callback) {
    return account.onConnectState(null, ev -> {
        if (!ev.getIsConnected() || !ev.getIsAlive())
            callback.accept(ev.getState(), ev.getErrorMessage());
    }, null);
}
```

---

## ⚠️ Pitfalls

* **`state` is free-form text.** Prefer the boolean flags over string matching where possible.
* **No close = leak.** Stop by closing the `Subscription`.
* **Events are sparse.** State is reported on change/heartbeat — don't expect a steady stream; use a timeout when waiting.

---

## 🧪 Testing Suggestions

* **Healthy session.** Shortly after connecting, an event has `state == "Connected"` with all three liveness flags true.
* **`waitConnected`.** Returns true on a live demo session within the timeout.
* **Clean stop.** Closing the `Subscription` ends delivery without leaking threads.

---

## 📚 See also

* [Trade Events Stream](on_trade.md)
* [Open-Order Profit Stream](on_opened_orders_profit.md)
* [Streams](../../All_Guides/STREAMS.md)

* **Back to:** [Streaming Methods — Overview](Streaming_Methods.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
