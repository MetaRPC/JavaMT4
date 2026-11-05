# Order Close By

> Close an order by an opposite order on the same symbol — net a hedge in one operation.

**API Information:**

* **Low-level API:** `MT4Account.orderCloseBy(int ticketToClose, int oppositeTicketClosingBy)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.TradingHelper`
* **RPC:** `OrderCloseBy(OrderCloseByRequest) -> OrderCloseByReply`
* **Payload:** `OrderCloseByData`

## 💬 Just the essentials

* **What it is.** Closes one order using an opposite-direction order on the same symbol, netting them against each other.
* **Why you need it.** To unwind a hedge (a buy and a sell on the same symbol) in a single operation instead of closing each leg.
* **When to use.** When you hold opposite orders on one symbol and want to net them.

---

## 🎯 Purpose

* Close-by between two opposite tickets
* Net a hedge in one call
* Get the resulting close price / profit

---

## Method Signature

```java
public OrderCloseByData orderCloseBy(int ticketToClose, int oppositeTicketClosingBy)
```

**Request message:**

```protobuf
message OrderCloseByRequest {
  int32 ticket_to_close = 1;
  int32 opposite_ticket_closing_by = 2;
}
```

**Payload message:**

```protobuf
message OrderCloseByData {
  double close_price = 1;
  double profit = 2;
  Timestamp close_time = 3;
  Timestamp close_time_utc = 4;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `ticketToClose` | `int` | The ticket to be closed. |
| `oppositeTicketClosingBy` | `int` | The opposite-direction ticket used to close it. |

---

## ⬆️ Output — `OrderCloseByData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `close_price` | `getClosePrice()` | Price at which the close-by settled |
| `profit` | `getProfit()` | Realized profit from the operation |
| `close_time` / `close_time_utc` | `getCloseTime()` / `getCloseTimeUtc()` | Close time (server / UTC) |

---

## 🧩 Notes & Tips

* **Both legs must be opposite and same-symbol.** A BUY and a SELL on the same instrument; close-by nets them.
* **Order of tickets matters.** `ticketToClose` is the one being closed; `oppositeTicketClosingBy` is the counter order.
* **Broker support varies.** Not all brokers allow close-by (and hedging must be permitted); unsupported setups throw `ApiExceptionMT4`.
* **Alternative:** close each leg independently with [order_close_delete](order_close_delete.md) if close-by isn't available.
* **Market must be open** — a closed session throws `ERR_MARKET_CLOSED` (132).

---

## 🔗 Usage Examples

### 1) Close one order by another

```java
static OrderCloseByData closeBy(MT4Account account, int ticket, int opposite) {
    var res = account.orderCloseBy(ticket, opposite);
    System.out.printf(Locale.ROOT, "closed #%d by #%d: profit=%+.2f @ %.5f%n",
            ticket, opposite, res.getProfit(), res.getClosePrice());
    return res;
}
```

### 2) Net a hedge on a symbol

Find a buy and a sell on the same symbol and close them against each other.

```java
static OrderCloseByData netHedge(MT4Account account, String symbol) {
    var orders = account.openedOrders().getOrderInfosList();
    var buy = orders.stream().filter(o -> o.getSymbol().equals(symbol)
            && o.getOrderType() == OpenedOrderType.OO_OP_BUY).findFirst();
    var sell = orders.stream().filter(o -> o.getSymbol().equals(symbol)
            && o.getOrderType() == OpenedOrderType.OO_OP_SELL).findFirst();
    if (buy.isPresent() && sell.isPresent()) {
        var res = account.orderCloseBy(buy.get().getTicket(), sell.get().getTicket());
        System.out.printf(Locale.ROOT, "netted %s: profit=%+.2f%n", symbol, res.getProfit());
        return res;
    }
    System.out.println("no opposite pair on " + symbol);
    return null;
}
```

---

## ⚠️ Pitfalls

* **Must be a true opposite pair.** Same symbol, opposite directions — otherwise the call is rejected.
* **Hedging may be disabled.** Brokers that forbid hedging (or close-by) throw `ApiExceptionMT4`; fall back to [order_close_delete](order_close_delete.md).
* **Market closed.** Throws `ERR_MARKET_CLOSED` (132) on a closed session.
* **Exceptions, not codes.** Wrap in `try/catch`.

---

## 🧪 Testing Suggestions

* **Happy path (market open, hedging allowed).** Open a BUY and a SELL 0.01 on one symbol, then `orderCloseBy` → both leave [opened_orders](../2.%20Orders_History/opened_orders.md).
* **Unsupported.** On a non-hedging broker, assert the call throws rather than silently doing nothing.
* **Result fields.** `close_price` and `close_time` are populated on success.

---

## 📚 See also

* [Order Send](order_send.md)
* [Order Close / Delete](order_close_delete.md)
* [Order Modify](order_modify.md)

* **Back to:** [Trading Operations — Overview](Trading_Operations.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
