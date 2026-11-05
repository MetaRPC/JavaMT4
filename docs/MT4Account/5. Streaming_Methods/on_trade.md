# Trade Events Stream

> A live stream of trade events — orders opened, updated and removed.

**API Information:**

* **Low-level API:** `MT4Account.onTrade(Consumer<OnTradeData> onData, Consumer<Throwable> onError)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.SubscriptionService`
* **RPC:** `OnTrade(OnTradeRequest) -> stream of OnTradeReply`
* **Payload:** `OnTradeData`

## 💬 Just the essentials

* **What it is.** A subscription that fires whenever an order's lifecycle changes — new, modified, closed/removed.
* **Why you need it.** To react instantly to order activity (monitors, copiers, dashboards) instead of polling.
* **When to use.** Any event-driven logic around orders. Close the `Subscription` to stop.

---

## 🎯 Purpose

* New / updated / removed order events
* Per-order operation type (`SUB_OP_*`)
* Event group type (profit vs update)
* Account snapshot alongside each event

---

## Method Signature

```java
public Subscription onTrade(Consumer<OnTradeData> onData, Consumer<Throwable> onError)
```

**Request message:**

```protobuf
message OnTradeRequest {}
```

**Payload message (field numbers per our stub):**

```protobuf
message OnTradeData {
  MT4_SUB_ENUM_EVENT_GROUP_TYPE type = 1;
  OnTadeEventData event_data = 2;        // proto spelling: "OnTade…"
  OnEventAccountInfo account_info = 3;
  string terminal_instance_guid_id = 4;
}

message OnTadeEventData {
  repeated OnTradeOrderInfo new_orders = 1;
  repeated OnTradeUpdatedOrderInfo updated_orders = 2;   // each has previous / current
  repeated OnTradeOrderInfo removed_orders = 3;
  repeated OnTradeOrderInfo new_history_orders = 4;
}

message OnTradeOrderInfo {
  int32 ticket = 2;
  double lots = 7;
  double open_price = 9;
  double order_profit = 11;
  double stop_loss = 12;
  string symbol = 14;
  double take_profit = 15;
  SUB_ORDER_OPERATION_TYPE type = 16;
  // ... index, is_history, comment, commission, magic_number, open_time, swap, close_price, close_time, account_login
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `onData` | `Consumer<OnTradeData>` | Called for each trade event. |
| `onError` | `Consumer<Throwable>` | Called on a terminal stream error (may be `null`). |

---

## ⬆️ Output — `onData` receives `OnTradeData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `type` | `getType()` | Event group (`MT4_SUB_ENUM_EVENT_GROUP_TYPE`) |
| `event_data.new_orders` | `getEventData().getNewOrdersList()` | Newly opened/placed orders |
| `event_data.updated_orders` | `getEventData().getUpdatedOrdersList()` | Changed orders (`getPrevious()` / `getCurrent()`) |
| `event_data.removed_orders` | `getEventData().getRemovedOrdersList()` | Closed / deleted orders |
| `event_data.new_history_orders` | `getEventData().getNewHistoryOrdersList()` | Orders that moved to history |

Each `OnTradeOrderInfo` carries `getTicket()`, `getSymbol()`, `getLots()`, `getOpenPrice()`, `getStopLoss()`, `getTakeProfit()`, `getOrderProfit()`, and `getType()` (`SUB_ORDER_OPERATION_TYPE`).

---

## 🧱 Related enums (from proto)

### `MT4_SUB_ENUM_EVENT_GROUP_TYPE` — field `type`

| Constant | Value | Meaning |
|----------|-------|---------|
| `OrderProfit` | 0 | Profit-update group |
| `OrderUpdate` | 1 | Order lifecycle change |

### `SUB_ORDER_OPERATION_TYPE` — order `type`

| Constant | Value | Meaning |
|----------|-------|---------|
| `SUB_OP_BUY` | 0 | Market buy |
| `SUB_OP_SELL` | 1 | Market sell |
| `SUB_OP_BUYLIMIT` | 2 | Buy limit |
| `SUB_OP_SELLLIMIT` | 3 | Sell limit |
| `SUB_OP_BUYSTOP` | 4 | Buy stop |
| `SUB_OP_SELLSTOP` | 5 | Sell stop |

> Access via `Mt4TermApiSubscriptions.SUB_ORDER_OPERATION_TYPE.SUB_OP_BUY` and `…MT4_SUB_ENUM_EVENT_GROUP_TYPE.OrderUpdate`.

---

## 🧩 Notes & Tips

* **Four buckets per event.** `event_data` splits changes into `newOrders`, `updatedOrders`, `removedOrders`, `newHistoryOrders` — iterate the ones you care about.
* **Updates carry before/after.** Each `updatedOrders` item has `getPrevious()` and `getCurrent()` (both `OnTradeOrderInfo`) — diff them to see exactly what changed (e.g. SL/TP).
* **Order operation enum differs from trading.** Streaming uses `SUB_OP_*` (`SUB_ORDER_OPERATION_TYPE`), not the `OC_OP_*` used by [order_send](../4.%20Trading_Operations/order_send.md) — same operations, different enum.
* **Close to stop** the subscription — see [Streams](../../All_Guides/STREAMS.md).
* **No events without activity.** If nothing trades, nothing fires.

---

## 🔗 Usage Examples

### 1) Log new orders

```java
static String label(SUB_ORDER_OPERATION_TYPE t) {
    return switch (t) {
        case SUB_OP_BUY -> "BUY";            case SUB_OP_SELL -> "SELL";
        case SUB_OP_BUYLIMIT -> "BUY LIMIT"; case SUB_OP_SELLLIMIT -> "SELL LIMIT";
        case SUB_OP_BUYSTOP -> "BUY STOP";   case SUB_OP_SELLSTOP -> "SELL STOP";
        default -> "?";
    };
}

static Subscription logNewOrders(MT4Account account) {
    return account.onTrade(ev -> {
        for (var o : ev.getEventData().getNewOrdersList())
            System.out.printf(Locale.ROOT, "[NEW] %s #%d %s @ %.5f%n",
                    label(o.getType()), o.getTicket(), o.getSymbol(), o.getOpenPrice());
    }, null);
}
```

### 2) Watch SL/TP changes

Use the previous/current pair to report exactly what moved.

```java
static Subscription watchModifications(MT4Account account) {
    return account.onTrade(ev -> {
        for (var upd : ev.getEventData().getUpdatedOrdersList()) {
            var cur = upd.getCurrent(); var prev = upd.getPrevious();
            if (cur.getStopLoss() != prev.getStopLoss() || cur.getTakeProfit() != prev.getTakeProfit())
                System.out.printf(Locale.ROOT, "[MOD] #%d %s SL %.5f->%.5f TP %.5f->%.5f%n",
                        cur.getTicket(), cur.getSymbol(), prev.getStopLoss(), cur.getStopLoss(),
                        prev.getTakeProfit(), cur.getTakeProfit());
        }
    }, null);
}
```

### 3) Fire a callback on any close

```java
static Subscription onClose(MT4Account account, Consumer<OnTradeOrderInfo> callback) {
    return account.onTrade(ev ->
            ev.getEventData().getRemovedOrdersList().forEach(callback), null);
}

// usage:
// var sub = onClose(account, o -> System.out.printf("[CLOSE] #%d %s%n", o.getTicket(), o.getSymbol()));
```

---

## ⚠️ Pitfalls

* **Wrong enum.** Order operations here are `SUB_OP_*`, not `OC_OP_*` — mixing them silently mismatches.
* **No close = leak.** Always stop the stream by closing the `Subscription`.
* **Quiet periods.** No trading activity → no events.
* **Updates need before/after.** Compare `getPrevious()` vs `getCurrent()` rather than assuming what changed.

---

## 🧪 Testing Suggestions

* **Market open.** Open then close a 0.01 order while subscribed — you see it in `newOrders` then `removedOrders`.
* **Modification.** Change an order's SL and assert it appears in `updatedOrders` with differing `previous`/`current`.
* **Clean stop.** Closing the `Subscription` ends delivery without leaking threads.

---

## 📚 See also

* [Symbol Tick Stream](on_symbol_tick.md)
* [Open-Order Profit Stream](on_opened_orders_profit.md)
* [Order Send](../4.%20Trading_Operations/order_send.md)
* [Streams](../../All_Guides/STREAMS.md)

* **Back to:** [Streaming Methods — Overview](Streaming_Methods.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
