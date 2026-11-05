# Order Close / Delete

> Close a market order (fully or partially) or delete a pending order — one call for both.

**API Information:**

* **Low-level API:** `MT4Account.orderCloseDelete(int orderTicket, Double lots, Double closingPrice, Integer slippage)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.TradingHelper`
* **RPC:** `OrderCloseDelete(OrderCloseDeleteRequest) -> OrderCloseDeleteReply`
* **Payload:** `OrderCloseDeleteData`

## 💬 Just the essentials

* **What it is.** Closes a filled market order (whole or part) or removes a resting pending order — the server picks the right action by order type.
* **Why you need it.** To exit positions, take partial profit, or cancel pendings.
* **When to use.** Taking profit/loss, scaling out, or cleanup. MT4Sugar's `close` / `closeAll` / `cancelPendings` wrap this.

---

## 🎯 Purpose

* Close a market order in full
* Partially close a market order (pass `lots`)
* Delete a pending order
* Learn which action happened via `mode`

---

## Method Signature

```java
public OrderCloseDeleteData orderCloseDelete(
        int orderTicket, Double lots, Double closingPrice, Integer slippage)
```

Each boxed argument is optional — pass `null` to omit (`lots = null` closes in full).

**Request message:**

```protobuf
message OrderCloseDeleteRequest {
  int32 order_ticket = 1;
  double lots = 2;            // partial close volume; omit for full
  double closing_price = 3;   // market close price (optional)
  int32 slippage = 4;
}
```

**Payload message:**

```protobuf
message OrderCloseDeleteData {
  OrderCloseDeleteMode mode = 1;        // which action happened
  string history_order_comment = 2;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `orderTicket` | `int` | Ticket to close or delete. |
| `lots` | `Double`, optional | Lots to close (partial); pass `null` to close in full. |
| `closingPrice` | `Double`, optional | Closing price for market orders. |
| `slippage` | `Integer`, optional | Max slippage in points. |

---

## ⬆️ Output — `OrderCloseDeleteData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `mode` | `getMode()` | Whether a market order was closed or a pending deleted (`OrderCloseDeleteMode`) |
| `history_order_comment` | `getHistoryOrderComment()` | Resulting history/close comment |

---

## 🧱 Related enums (from proto)

### `OrderCloseDeleteMode` — field `mode`

| Constant | Value | Meaning |
|----------|-------|---------|
| `OCD_MARKET_ORDER` | 0 | A market order was closed |
| `OCD_PENDING_ORDER` | 1 | A pending order was deleted |

> Access via `Mt4TermApiTradingHelper.OrderCloseDeleteMode.OCD_MARKET_ORDER`.

---

## 🧩 Notes & Tips

* **One call, both actions.** You don't choose close-vs-delete — the server does it based on the order type and reports it in `mode`.
* **Partial close.** Pass `lots` smaller than the order's volume; the remainder stays open as a separate ticket.
* **Pending deletes ignore `lots`.** For a pending order the whole thing is removed.
* **Market must be open** to close a market order — a weekend/closed market throws `ERR_MARKET_CLOSED` (132).
* **Bulk exits.** For closing many orders use MT4Sugar's `closeAll` / `cancelPendings` rather than looping this by hand.

---

## 🔗 Usage Examples

### 1) Close an order in full

```java
static OrderCloseDeleteData close(MT4Account account, int ticket) {
    var res = account.orderCloseDelete(ticket, null, null, null);
    String kind = res.getMode() == OrderCloseDeleteMode.OCD_MARKET_ORDER ? "closed market" : "deleted pending";
    System.out.printf("#%d: %s%n", ticket, kind);
    return res;
}
```

### 2) Partial close (scale out)

Close half of a position, leaving the rest to run.

```java
static void closeHalf(MT4Account account, int ticket, double lots) {
    double half = Math.round(lots / 2 * 100) / 100.0;
    account.orderCloseDelete(ticket, half, null, null);
    System.out.printf(Locale.ROOT, "#%d: closed %.2f lot, rest left open%n", ticket, half);
}
```

### 3) Close everything on a symbol

Pull open orders, then close each matching ticket.

```java
static void closeSymbol(MT4Account account, String symbol) {
    for (var o : account.openedOrders().getOrderInfosList()) {
        if (o.getSymbol().equals(symbol)) {
            try {
                account.orderCloseDelete(o.getTicket(), null, null, null);
                System.out.println("closed #" + o.getTicket());
            } catch (Exception e) {
                System.out.println("#" + o.getTicket() + " failed: " + e.getMessage());
            }
        }
    }
}
```

---

## ⚠️ Pitfalls

* **Market closed.** Closing a market order when the session is closed throws `ERR_MARKET_CLOSED` (132) — pendings can still usually be deleted.
* **Partial close limits.** `lots` must respect the symbol's volume step and be ≤ the order's volume.
* **Exceptions, not codes.** Failures throw `ApiExceptionMT4`; wrap in `try/catch`.
* **Already closed.** Closing a ticket that's gone throws — tolerate it in cleanup loops.

---

## 🧪 Testing Suggestions

* **Round-trip (market open).** [order_send](order_send.md) then `orderCloseDelete` → `mode == OCD_MARKET_ORDER`; the ticket leaves [opened_orders](../2.%20Orders_History/opened_orders.md).
* **Pending delete.** Place a pending order and delete it → `mode == OCD_PENDING_ORDER`.
* **Partial.** Close part of a 0.02 position and assert a 0.01 remainder stays open.

---

## 📚 See also

* [Order Send](order_send.md)
* [Order Modify](order_modify.md)
* [Order Close By](order_close_by.md)

* **Back to:** [Trading Operations — Overview](Trading_Operations.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
