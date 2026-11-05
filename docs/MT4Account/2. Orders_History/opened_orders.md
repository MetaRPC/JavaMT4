# Opened Orders

> Every currently open order (market **and** pending) with full detail, in the sort order you choose.

**API Information:**

* **Low-level API:** `MT4Account.openedOrders(EnumOpenedOrderSortType sortType)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.AccountHelper`
* **RPC:** `OpenedOrders(OpenedOrdersRequest) -> OpenedOrdersReply`
* **Payload:** `OpenedOrdersData`

## 💬 Just the essentials

* **What it is.** The full list of live orders on the account — market positions and resting pending orders — each with price, SL/TP, profit, magic and times.
* **Why you need it.** To inspect, manage, or close current exposure.
* **When to use.** Whenever you need order details. If you only need ticket numbers, use [opened_orders_tickets](opened_orders_tickets.md) (cheaper).

---

## 🎯 Purpose

* All open market & pending orders in one call
* Full per-order detail (price, SL/TP, profit, swap, magic)
* Choose the sort order
* Filter in your code by symbol / magic / side

---

## Method Signature

```java
public OpenedOrdersData openedOrders()                                   // defaults to SORT_BY_OPEN_TIME_ASC
public OpenedOrdersData openedOrders(EnumOpenedOrderSortType sortType)
```

> The low-level call takes the `EnumOpenedOrderSortType` enum directly. For a friendly string sort (`"open_desc"` etc.) use the [MT4Service](../../MT4Service/MT4Service.Overview.md) wrapper.

**Request message:**

```protobuf
message OpenedOrdersRequest {
  EnumOpenedOrderSortType sort_type = 1;
}
```

**Payload message:**

```protobuf
message OpenedOrdersData {
  repeated OpenedOrderInfo order_infos = 1;
}

message OpenedOrderInfo {
  string comment = 1;
  double commision = 2;
  Timestamp expiration_time = 3;
  double lots = 4;
  int32 magic_number = 5;
  double open_price = 6;
  double profit = 7;
  double stop_loss = 8;
  double swap = 9;
  string symbol = 10;
  double take_profit = 11;
  int32 ticket = 12;
  int32 position_index = 13;
  OpenedOrderType order_type = 14;
  int32 sort_index = 15;
  Timestamp open_time = 16;
  int64 account_login = 17;
  Timestamp expiration_time_utc = 18;
  Timestamp open_time_utc = 19;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `sortType` | `EnumOpenedOrderSortType` | Sort order; the no-arg overload defaults to `SORT_BY_OPEN_TIME_ASC`. |

---

## ⬆️ Output — `OpenedOrdersData`

`getOrderInfosList()` / `getOrderInfos(i)` / `getOrderInfosCount()` — a list of `OpenedOrderInfo`:

| Field | Java getter | Description |
|-------|-------------|-------------|
| `ticket` | `getTicket()` | Order ticket |
| `symbol` | `getSymbol()` | Symbol |
| `order_type` | `getOrderType()` | Operation (`OpenedOrderType`, `OO_OP_*`) |
| `lots` | `getLots()` | Volume |
| `open_price` | `getOpenPrice()` | Open / pending price |
| `stop_loss` / `take_profit` | `getStopLoss()` / `getTakeProfit()` | SL / TP price (0 if unset) |
| `profit` | `getProfit()` | Current floating profit |
| `swap` / `commision` | `getSwap()` / `getCommision()` | Swap / commission (note proto spelling `commision`) |
| `magic_number` | `getMagicNumber()` | Expert/magic id |
| `comment` | `getComment()` | Order comment |
| `open_time` / `_utc` | `getOpenTime()` / `getOpenTimeUtc()` | Open time (server / UTC) |
| `expiration_time` / `_utc` | `getExpirationTime()` / `getExpirationTimeUtc()` | Pending-order expiration |

---

## 🧱 Related enums (from proto)

### `EnumOpenedOrderSortType` — input `sortType`

| Constant | Value | Meaning |
|----------|-------|---------|
| `SORT_BY_OPEN_TIME_ASC` | 0 | By open time, ascending (default) |
| `SORT_BY_OPEN_TIME_DESC` | 1 | By open time, descending |
| `SORT_BY_ORDER_TICKET_ID_ASC` | 2 | By ticket id, ascending |
| `SORT_BY_ORDER_TICKET_ID_DESC` | 3 | By ticket id, descending |

### `OpenedOrderType` — field `order_type`

| Constant | Value | Meaning |
|----------|-------|---------|
| `OO_OP_BUY` | 0 | Market buy |
| `OO_OP_SELL` | 1 | Market sell |
| `OO_OP_BUYLIMIT` | 2 | Buy limit (pending) |
| `OO_OP_SELLLIMIT` | 3 | Sell limit (pending) |
| `OO_OP_BUYSTOP` | 4 | Buy stop (pending) |
| `OO_OP_SELLSTOP` | 5 | Sell stop (pending) |
| `OO_OP_BALANCE` | 6 | Balance record (non-tradable) |
| `OO_OP_CREDIT` | 7 | Credit record (non-tradable) |

> In Java, access values via `Mt4TermApiAccountHelper.OpenedOrderType.OO_OP_BUY`.

---

## 🧩 Notes & Tips

* **Market and pending in one list.** `order_type` tells them apart: `OO_OP_BUY`/`OO_OP_SELL` are filled market orders; the `*LIMIT`/`*STOP` variants are resting pending orders.
* **Buy vs sell side.** Buy side = `OO_OP_BUY`, `OO_OP_BUYLIMIT`, `OO_OP_BUYSTOP`; the odd values (1,3,5) are the sell side.
* **`OO_OP_BALANCE` / `OO_OP_CREDIT`** are bookkeeping records, not tradable orders — skip them when summing exposure.
* **`profit` is floating** and excludes `swap`/`commision`; add them for the true carry cost.
* **Proto spelling:** the commission field is `commision` (single s) → getter `getCommision()` — use it as-is.
* **Lightweight alternative:** [opened_orders_tickets](opened_orders_tickets.md) returns just ticket numbers for fast polling.

---

## 🔗 Usage Examples

### 1) Open orders table

Print a readable snapshot of everything currently open.

```java
static String typeLabel(OpenedOrderType t) {
    return switch (t) {
        case OO_OP_BUY -> "BUY";            case OO_OP_SELL -> "SELL";
        case OO_OP_BUYLIMIT -> "BUY LIMIT"; case OO_OP_SELLLIMIT -> "SELL LIMIT";
        case OO_OP_BUYSTOP -> "BUY STOP";   case OO_OP_SELLSTOP -> "SELL STOP";
        default -> "?";
    };
}

static void printOpen(MT4Account account) {
    var data = account.openedOrders();
    System.out.printf(Locale.ROOT, "%10s %-8s %-10s %6s %10s %10s%n", "Ticket", "Symbol", "Type", "Lots", "Price", "Profit");
    double total = 0;
    for (var o : data.getOrderInfosList()) {
        total += o.getProfit();
        System.out.printf(Locale.ROOT, "%10d %-8s %-10s %6.2f %10.5f %10.2f%n",
                o.getTicket(), o.getSymbol(), typeLabel(o.getOrderType()), o.getLots(), o.getOpenPrice(), o.getProfit());
    }
    System.out.printf(Locale.ROOT, "total floating: %+.2f%n", total);
}
```

### 2) Only my bot's orders

Filter by `magic_number` to isolate orders a given strategy opened.

```java
static List<OpenedOrderInfo> myOrders(MT4Account account, int magic) {
    var mine = account.openedOrders().getOrderInfosList().stream()
            .filter(o -> o.getMagicNumber() == magic).toList();
    double floating = mine.stream().mapToDouble(o -> o.getProfit() + o.getSwap() + o.getCommision()).sum();
    System.out.printf(Locale.ROOT, "magic %d: %d order(s), net %+.2f%n", magic, mine.size(), floating);
    return mine;
}
```

### 3) Net exposure per symbol

Aggregate signed lots per symbol to see your real directional exposure (market orders only).

```java
static Map<String, Double> netExposure(MT4Account account) {
    var net = new TreeMap<String, Double>();
    for (var o : account.openedOrders().getOrderInfosList()) {
        if (o.getOrderType() == OpenedOrderType.OO_OP_BUY)
            net.merge(o.getSymbol(), o.getLots(), Double::sum);
        else if (o.getOrderType() == OpenedOrderType.OO_OP_SELL)
            net.merge(o.getSymbol(), -o.getLots(), Double::sum);
    }
    net.forEach((sym, lots) -> {
        String side = lots > 0 ? "long" : lots < 0 ? "short" : "flat";
        System.out.printf(Locale.ROOT, "%-8s %+.2f lots (%s)%n", sym, lots, side);
    });
    return net;
}
```

---

## ⚠️ Pitfalls

* **Pending orders have no profit yet.** A `*LIMIT`/`*STOP` order isn't filled — its `profit` is 0; don't count it as exposure.
* **Skip balance/credit rows.** `OO_OP_BALANCE` / `OO_OP_CREDIT` appear here but aren't trades — exclude them from lot/exposure math.
* **SL/TP of 0 means "unset"**, not a price of zero.
* **Snapshot.** The list reflects the moment of the call; use [on_opened_orders_tickets](../5.%20Streaming_Methods/on_opened_orders_tickets.md) to react to changes live.

---

## 🧪 Testing Suggestions

* **Flat account.** `order_infos` is empty; the table prints a zero total without error.
* **With a market order open.** The new ticket appears with `order_type == OO_OP_BUY`/`OO_OP_SELL` and a sensible `open_price`.
* **Magic filter.** Open two orders with different magics and assert `myOrders()` returns only the matching one.

---

## 📚 See also

* [Opened Order Tickets](opened_orders_tickets.md)
* [Orders History](orders_history.md)
* [Order Send](../4.%20Trading_Operations/order_send.md)

* **Back to:** [Orders & History — Overview](Orders_History.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
