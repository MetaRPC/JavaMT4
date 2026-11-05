# Opened Order Tickets Stream

> A periodic stream of the live open ticket lists — positions and pending orders.

**API Information:**

* **Low-level API:** `MT4Account.onOpenedOrdersTickets(int pullIntervalMilliseconds, Consumer<OnOpenedOrdersTicketsData> onData, Consumer<Throwable> onError)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.SubscriptionService`
* **RPC:** `OnOpenedOrdersTickets(OnOpenedOrdersTicketsRequest) -> stream of OnOpenedOrdersTicketsReply`
* **Payload:** `OnOpenedOrdersTicketsData`

## 💬 Just the essentials

* **What it is.** A lightweight heartbeat: every `pullIntervalMilliseconds` it delivers the current lists of open position and pending-order tickets.
* **Why you need it.** Cheap "what's open now?" monitoring without pulling full order detail.
* **When to use.** Change detection and liveness. For full detail fetch [opened_orders](../2.%20Orders_History/opened_orders.md) when something changes.

---

## 🎯 Purpose

* Separate position and pending ticket lists
* Server-side sampling interval
* Minimal payload for monitoring loops

---

## Method Signature

```java
public Subscription onOpenedOrdersTickets(
        int pullIntervalMilliseconds,
        Consumer<OnOpenedOrdersTicketsData> onData,
        Consumer<Throwable> onError)
```

**Request message:**

```protobuf
message OnOpenedOrdersTicketsRequest {
  int32 pull_interval_milliseconds = 1;
}
```

**Payload message (field numbers per our stub):**

```protobuf
message OnOpenedOrdersTicketsData {
  repeated int32 position_tickets = 1;
  repeated int32 pending_order_tickets = 2;
  Timestamp server_time = 3;
  string terminal_instance_guid_id = 5;
  Timestamp server_time_utc = 6;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `pullIntervalMilliseconds` | `int` | How often the server samples the ticket lists (e.g. 500). |
| `onData` | `Consumer<OnOpenedOrdersTicketsData>` | Called each sample. |
| `onError` | `Consumer<Throwable>` | Called on a terminal stream error (may be `null`). |

---

## ⬆️ Output — `onData` receives `OnOpenedOrdersTicketsData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `position_tickets` | `getPositionTicketsList()` | Open position (market order) tickets |
| `pending_order_tickets` | `getPendingOrderTicketsList()` | Pending order tickets |
| `server_time` / `server_time_utc` | `getServerTime()` / `getServerTimeUtc()` | Sample time (server / UTC) |

---

## 🧩 Notes & Tips

* **Positions and pendings are split here** — unlike the one-shot [opened_orders_tickets](../2.%20Orders_History/opened_orders_tickets.md), which returns a single flat list.
* **Tune `pullIntervalMilliseconds`.** 500 ms is responsive; raise it (e.g. 2000) to reduce chatter for slow strategies.
* **Fetch detail on change.** When a ticket set changes, call [opened_orders](../2.%20Orders_History/opened_orders.md) once — don't stream full detail.
* **Close to stop** the subscription — see [Streams](../../All_Guides/STREAMS.md).
* **This heartbeat works even on a closed market** (it samples current state, not ticks).

---

## 🔗 Usage Examples

### 1) Heartbeat of open tickets

```java
static Subscription heartbeat(MT4Account account) {
    return account.onOpenedOrdersTickets(1000, ev ->
            System.out.printf("positions=%s pending=%s%n",
                    ev.getPositionTicketsList(), ev.getPendingOrderTicketsList()), null);
}
```

### 2) Detect opened / closed positions

Diff successive samples and act only on change.

```java
static Subscription watchPositions(MT4Account account) {
    var prev = new AtomicReference<Set<Integer>>(Set.of());
    return account.onOpenedOrdersTickets(1000, ev -> {
        var now = new HashSet<>(ev.getPositionTicketsList());
        if (!now.equals(prev.get())) {
            var opened = new TreeSet<>(now); opened.removeAll(prev.get());
            var closed = new TreeSet<>(prev.get()); closed.removeAll(now);
            System.out.println("opened: " + opened + " closed: " + closed);
            prev.set(now);
        }
    }, null);
}
```

### 3) React when a pending order appears

```java
static boolean waitForPending(MT4Account account, long timeoutMs) throws InterruptedException {
    var found = new CountDownLatch(1);
    try (var s = account.onOpenedOrdersTickets(500, ev -> {
            if (ev.getPendingOrderTicketsCount() > 0) found.countDown();
        }, null)) {
        return found.await(timeoutMs, TimeUnit.MILLISECONDS);
    }
}
```

---

## ⚠️ Pitfalls

* **Ids only.** No prices/profit here — fetch [opened_orders](../2.%20Orders_History/opened_orders.md) for detail.
* **No close = leak.** Stop by closing the `Subscription`.
* **Interval trade-off.** Too small a `pullIntervalMilliseconds` is chatty; too large is laggy — tune to your needs.

---

## 🧪 Testing Suggestions

* **Existing orders.** With open orders, `position_tickets` matches the one-shot [opened_orders_tickets](../2.%20Orders_History/opened_orders_tickets.md).
* **Change detection.** Open then close an order and assert the diff reports it once opened, once closed.
* **Clean stop.** Closing the `Subscription` ends delivery without leaking threads.

---

## 📚 See also

* [Opened Order Tickets](../2.%20Orders_History/opened_orders_tickets.md)
* [Open-Order Profit Stream](on_opened_orders_profit.md)
* [Streams](../../All_Guides/STREAMS.md)

* **Back to:** [Streaming Methods — Overview](Streaming_Methods.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
