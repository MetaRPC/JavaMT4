# Opened Order Tickets

> Just the ticket numbers of currently open orders — the cheapest way to poll "what's open now".

**API Information:**

* **Low-level API:** `MT4Account.openedOrdersTickets()` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.AccountHelper`
* **RPC:** `OpenedOrdersTickets(OpenedOrdersTicketsRequest) -> OpenedOrdersTicketsReply`
* **Payload:** `OpenedOrdersTicketsData`

## 💬 Just the essentials

* **What it is.** A flat list of open ticket numbers — no prices, no profit, just ids.
* **Why you need it.** Minimal payload for high-frequency "is anything open / did it change?" checks.
* **When to use.** Polling loops. When you need the actual details, call [opened_orders](opened_orders.md).

---

## 🎯 Purpose

* Minimal payload — ticket ids only
* Cheap to call in a tight loop
* Pair with [opened_orders](opened_orders.md) to fetch detail only when something changed

---

## Method Signature

```java
public OpenedOrdersTicketsData openedOrdersTickets()
```

No arguments.

**Request message:**

```protobuf
message OpenedOrdersTicketsRequest {
}
```

**Payload message:**

```protobuf
message OpenedOrdersTicketsData {
  repeated int32 tickets = 1;   // open order tickets (market + pending)
}
```

---

## 🔽 Input

None.

---

## ⬆️ Output — `OpenedOrdersTicketsData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `tickets` | `getTicketsList()` / `getTickets(i)` / `getTicketsCount()` | Ticket numbers of all open orders (market + pending) |

---

## 🧩 Notes & Tips

* **One flat list.** Unlike the streaming [on_opened_orders_tickets](../5.%20Streaming_Methods/on_opened_orders_tickets.md) (which splits positions from pending), this returns a single `tickets` list covering both.
* **Set math is your friend.** Convert to a `Set<Integer>` to diff against a previous poll and spot what opened or closed.
* **Fetch detail on change only.** Poll this; when the set changes, call [opened_orders](opened_orders.md) once to get the new details — far cheaper than pulling full detail every tick.

---

## 🔗 Usage Examples

### 1) Is anything open?

A one-liner liveness/exposure check.

```java
static boolean hasOpenOrders(MT4Account account) {
    return account.openedOrdersTickets().getTicketsCount() > 0;
}
```

### 2) Detect opened / closed between polls

Diff two snapshots to react only when the set of open orders changes.

```java
static void watchChanges(MT4Account account, long intervalMs) throws InterruptedException {
    Set<Integer> prev = Set.of();
    while (true) {
        Set<Integer> now = new HashSet<>(account.openedOrdersTickets().getTicketsList());
        var opened = new TreeSet<>(now); opened.removeAll(prev);
        var closed = new TreeSet<>(prev); closed.removeAll(now);
        if (!opened.isEmpty()) System.out.println("opened: " + opened);
        if (!closed.isEmpty()) System.out.println("closed: " + closed);
        prev = now;
        Thread.sleep(intervalMs);
    }
}
```

### 3) Poll cheap, fetch detail on change

Only pull full order detail when the ticket set actually changes.

```java
static Set<Integer> refreshOnChange(MT4Account account, Set<Integer> prevTickets) {
    Set<Integer> now = new HashSet<>(account.openedOrdersTickets().getTicketsList());
    if (!now.equals(prevTickets)) {
        var details = account.openedOrders();     // one detailed call, only when needed
        System.out.printf("%d open order(s) now%n", details.getOrderInfosCount());
    }
    return now;
}
```

---

## ⚠️ Pitfalls

* **Ids only.** No prices, SL/TP or profit here — don't try to make risk decisions from this call alone.
* **Market + pending mixed.** The list doesn't tell you which tickets are filled vs resting; use [opened_orders](opened_orders.md) for that.
* **Empty when flat.** No open orders → empty `tickets` list.

---

## 🧪 Testing Suggestions

* **Flat account.** `tickets` is empty.
* **After opening an order.** The new ticket appears in `tickets` and matches the ticket returned by [order_send](../4.%20Trading_Operations/order_send.md).
* **Diff logic.** Open then close an order across two polls and assert the change-detector reports it once as opened, once as closed.

---

## 📚 See also

* [Opened Orders](opened_orders.md)
* [Orders History](orders_history.md)
* [On Opened Orders Tickets (stream)](../5.%20Streaming_Methods/on_opened_orders_tickets.md)

* **Back to:** [Orders & History — Overview](Orders_History.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
