# Open-Order Profit Stream

> A live P/L ticker for open orders, emitted on a timer — plus an account snapshot.

**API Information:**

* **Low-level API:** `MT4Account.onOpenedOrdersProfit(int timerPeriodMilliseconds, Consumer<OnOpenedOrdersProfitData> onData, Consumer<Throwable> onError)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.SubscriptionService`
* **RPC:** `OnOpenedOrdersProfit(OnOpenedOrdersProfitRequest) -> stream of OnOpenedOrdersProfitReply`
* **Payload:** `OnOpenedOrdersProfitData`

## 💬 Just the essentials

* **What it is.** Every `timerPeriodMilliseconds` it delivers the current floating P/L of each open order.
* **Why you need it.** Real-time floating P/L without polling — risk dashboards, trailing/exit logic.
* **When to use.** Live P/L monitoring and profit-target automation. Close the `Subscription` to stop.

---

## 🎯 Purpose

* Per-order live profit
* Emission interval control
* Account snapshot alongside each update

---

## Method Signature

```java
public Subscription onOpenedOrdersProfit(
        int timerPeriodMilliseconds,
        Consumer<OnOpenedOrdersProfitData> onData,
        Consumer<Throwable> onError)
```

**Request message:**

```protobuf
message OnOpenedOrdersProfitRequest {
  int32 timer_period_milliseconds = 1;
}
```

**Payload message (field numbers per our stub):**

```protobuf
message OnOpenedOrdersProfitData {
  MT4_SUB_ENUM_EVENT_GROUP_TYPE type = 1;
  repeated OnOpenedOrdersProfitOrderInfo opened_orders_with_profit_updated = 2;
  OnEventAccountInfo account_info = 3;
  string terminal_instance_guid_id = 4;
}

message OnOpenedOrdersProfitOrderInfo {
  int32 ticket = 2;
  double lots = 6;
  double open_price = 8;
  double order_profit = 10;
  double stop_loss = 11;
  string symbol = 13;
  double take_profit = 14;
  SUB_ORDER_OPERATION_TYPE type = 15;
  // ... index, comment, commission, expiration, magic_number, open_time, swap
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `timerPeriodMilliseconds` | `int` | How often updates are emitted (e.g. 1000). |
| `onData` | `Consumer<OnOpenedOrdersProfitData>` | Called each tick of the timer. |
| `onError` | `Consumer<Throwable>` | Called on a terminal stream error (may be `null`). |

---

## ⬆️ Output — `onData` receives `OnOpenedOrdersProfitData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `type` | `getType()` | Event group (`MT4_SUB_ENUM_EVENT_GROUP_TYPE`) |
| `opened_orders_with_profit_updated` | `getOpenedOrdersWithProfitUpdatedList()` | Open orders with live P/L |
| `account_info` | `getAccountInfo()` | Account snapshot alongside the update |

Each `OnOpenedOrdersProfitOrderInfo` carries `getTicket()`, `getSymbol()`, `getOrderProfit()`, `getLots()`, `getOpenPrice()`, `getStopLoss()`, `getTakeProfit()`, and `getType()` (`SUB_ORDER_OPERATION_TYPE`).

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

> Access via `Mt4TermApiSubscriptions.SUB_ORDER_OPERATION_TYPE.SUB_OP_BUY` and `…MT4_SUB_ENUM_EVENT_GROUP_TYPE.OrderProfit`.

---

## 🧩 Notes & Tips

* **Profit field is `order_profit`** (`getOrderProfit()`), not `profit`, on these items.
* **Timer-driven.** Updates arrive on `timerPeriodMilliseconds`, independent of ticks — so you get P/L updates even between price changes.
* **Same operation enum as [on_trade](on_trade.md).** Order `type` is `SUB_ORDER_OPERATION_TYPE` (`SUB_OP_*`).
* **Works on a closed market** (it re-values current orders on a timer), though P/L won't move without price changes.
* **Close to stop** the subscription — see [Streams](../../All_Guides/STREAMS.md).

---

## 🔗 Usage Examples

### 1) Live P/L per order

```java
static Subscription livePnl(MT4Account account) {
    return account.onOpenedOrdersProfit(1000, ev -> {
        for (var o : ev.getOpenedOrdersWithProfitUpdatedList())
            System.out.printf(Locale.ROOT, "#%d %s %+.2f%n", o.getTicket(), o.getSymbol(), o.getOrderProfit());
    }, null);
}
```

### 2) Alert on total floating loss

Stop and alert when aggregate floating P/L breaches a limit.

```java
static boolean floatingGuard(MT4Account account, double maxLoss, long timeoutMs) throws InterruptedException {
    var breached = new CountDownLatch(1);
    try (var s = account.onOpenedOrdersProfit(2000, ev -> {
            double total = ev.getOpenedOrdersWithProfitUpdatedList().stream()
                    .mapToDouble(o -> o.getOrderProfit()).sum();
            System.out.printf(Locale.ROOT, "floating: %+.2f%n", total);
            if (total <= maxLoss) {
                System.out.printf(Locale.ROOT, "[ALERT] floating %.2f <= %.2f — de-risk!%n", total, maxLoss);
                breached.countDown();
            }
        }, null)) {
        return breached.await(timeoutMs, TimeUnit.MILLISECONDS);
    }
}
```

### 3) Close an order when it hits a profit target

Watch the stream and close the ticket once its profit reaches the target.

```java
static void takeProfitAt(MT4Account account, int ticket, double target, long timeoutMs) throws InterruptedException {
    var done = new CountDownLatch(1);
    try (var s = account.onOpenedOrdersProfit(1000, ev -> {
            for (var o : ev.getOpenedOrdersWithProfitUpdatedList()) {
                if (o.getTicket() == ticket && o.getOrderProfit() >= target) {
                    account.orderCloseDelete(ticket, null, null, null);
                    System.out.printf(Locale.ROOT, "#%d hit %.2f -> closed%n", ticket, target);
                    done.countDown();
                }
            }
        }, null)) {
        done.await(timeoutMs, TimeUnit.MILLISECONDS);
    }
}
```

---

## ⚠️ Pitfalls

* **Field is `order_profit`.** Not `profit` — use `getOrderProfit()`.
* **No close = leak.** Stop by closing the `Subscription`.
* **Acting inside the callback.** Closing/modifying from within the stream is fine, but keep per-update work light.
* **Flat account.** With nothing open, `opened_orders_with_profit_updated` is empty.

---

## 🧪 Testing Suggestions

* **With open orders.** Each update lists your open tickets; `order_profit` moves with price.
* **Total.** Summed `order_profit` roughly matches `equity − balance` from [account_summary](../1.%20Account_Information/account_summary.md).
* **Clean stop.** Closing the `Subscription` ends delivery without leaking threads.

---

## 📚 See also

* [Opened Order Tickets Stream](on_opened_orders_tickets.md)
* [Trade Events Stream](on_trade.md)
* [Account Summary](../1.%20Account_Information/account_summary.md)
* [Streams](../../All_Guides/STREAMS.md)

* **Back to:** [Streaming Methods — Overview](Streaming_Methods.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
