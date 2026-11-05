# Order Modify

> Change an existing order's SL, TP, pending price or expiration.

**API Information:**

* **Low-level API:** `MT4Account.orderModify(int orderTicket, Double newPrice, Double newStopLoss, Double newTakeProfit, Instant newExpiration)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.TradingHelper`
* **RPC:** `OrderModify(OrderModifyRequest) -> OrderModifyReply`
* **Payload:** `OrderModifyData`

## 💬 Just the essentials

* **What it is.** Updates SL/TP (and, for pending orders, the entry price / expiration) of an order that already exists.
* **Why you need it.** To manage live orders without closing them — trailing stops, breakeven, re-pricing pendings.
* **When to use.** Any time you adjust protection or a pending price. MT4Sugar's `modifySlTpByPrice` / `modifySlTpByPips` wrap this.

---

## 🎯 Purpose

* Change SL / TP on a live order
* Re-price a pending order
* Update a pending order's expiration

---

## Method Signature

```java
public OrderModifyData orderModify(
        int orderTicket,
        Double newPrice, Double newStopLoss, Double newTakeProfit,
        Instant newExpiration)
```

Each `Double`/`Instant` argument is optional — pass `null` to leave that field unchanged.

**Request message:**

```protobuf
message OrderModifyRequest {
  int32 order_ticket = 1;
  double new_price = 2;              // pending-order price
  double new_stop_loss = 3;
  double new_take_profit = 4;
  Timestamp new_expiration = 5;
  Timestamp new_expiration_utc = 6;
}
```

**Payload message:**

```protobuf
message OrderModifyData {
  bool order_was_modified = 1;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `orderTicket` | `int` | Ticket to modify. |
| `newPrice` | `Double`, optional | New pending-order price (ignored for filled market orders). |
| `newStopLoss` | `Double`, optional | New SL price. |
| `newTakeProfit` | `Double`, optional | New TP price. |
| `newExpiration` | `Instant`, optional | New expiration (pending orders). |

---

## ⬆️ Output — `OrderModifyData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `order_was_modified` | `getOrderWasModified()` | True if the change was applied |

> On failure the call throws `ApiExceptionMT4` — e.g. `ERR_INVALID_STOPS` (130) when SL/TP is too close, or `ERR_TRADE_MODIFY_DENIED` (145) when nothing changed or the order is too near market.

---

## 🧩 Notes & Tips

* **Prices, not pips.** `newStopLoss` / `newTakeProfit` are absolute prices — convert from pips yourself (or use MT4Sugar's `modifySlTpByPips`).
* **Pass only what changes.** Supply just the fields you want to update; leave the rest `null`.
* **Respect the stops level.** New SL/TP must be at least `TradeStopsLevel` points from price ([symbol_params_many](../3.%20Symbols_Quotes/symbol_params_many.md)).
* **Pending vs market.** `newPrice`/`newExpiration` only make sense for pending orders; for filled market orders, modify SL/TP.
* **No-op is an error on some brokers.** Submitting unchanged values can throw rather than silently succeed — only call when something actually changes.

---

## 🔗 Usage Examples

### 1) Set SL and TP on an order

```java
static boolean setStops(MT4Account account, int ticket, double sl, double tp) {
    boolean ok = account.orderModify(ticket, null, sl, tp, null).getOrderWasModified();
    System.out.println(ok ? "modified" : "no change");
    return ok;
}
```

### 2) Trail the stop (move SL only)

Raise the stop on a long as price advances — one trailing step.

```java
static void trailStop(MT4Account account, int ticket, String symbol, double distancePips) {
    var q = account.quote(symbol);
    var info = account.symbolParamsMany(symbol).getSymbolInfos(0);
    double pip = info.getPoint() * ((info.getDigits() == 3 || info.getDigits() == 5) ? 10 : 1);
    double scale = Math.pow(10, info.getDigits());
    double newSl = Math.round((q.getBid() - distancePips * pip) * scale) / scale;   // for a BUY
    account.orderModify(ticket, null, newSl, null, null);
    System.out.printf(Locale.ROOT, "SL -> %.5f%n", newSl);
}
```

### 3) Re-price a pending order

Move a resting BUY LIMIT to a new level.

```java
static boolean repricePending(MT4Account account, int ticket, double newPrice) {
    double p = Math.round(newPrice * 1e5) / 1e5;
    return account.orderModify(ticket, p, null, null, null).getOrderWasModified();
}
```

---

## ⚠️ Pitfalls

* **Absolute prices.** SL/TP/price are prices, not distances — compute them before calling.
* **Too-close stops.** Violating `TradeStopsLevel` throws `ERR_INVALID_STOPS` (130).
* **No-change calls.** Re-submitting identical values may throw (`ERR_TRADE_MODIFY_DENIED` 145) — guard against it.
* **Exceptions, not codes.** Failures throw `ApiExceptionMT4`; wrap in `try/catch`.

---

## 🧪 Testing Suggestions

* **Happy path (market open).** Modifying SL/TP of an open order returns `getOrderWasModified() == true` and the new values show in [opened_orders](../2.%20Orders_History/opened_orders.md).
* **Too-close SL.** A stop inside `TradeStopsLevel` throws `ERR_INVALID_STOPS` (130).
* **No-op.** Re-applying the same SL/TP either returns false or throws — assert your code tolerates both.

---

## 📚 See also

* [Order Send](order_send.md)
* [Order Close / Delete](order_close_delete.md)
* [Opened Orders](../2.%20Orders_History/opened_orders.md)

* **Back to:** [Trading Operations — Overview](Trading_Operations.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
