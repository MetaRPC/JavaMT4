# Orders History

> Closed (historical) orders over a time range, with paging and a sort mode — your trade log.

**API Information:**

* **Low-level API:** `MT4Account.ordersHistory(EnumOrderHistorySortType sortMode, Instant from, Instant to, Integer pageNumber, Integer itemsPerPage)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.AccountHelper`
* **RPC:** `OrdersHistory(OrdersHistoryRequest) -> OrdersHistoryReply`
* **Payload:** `OrdersHistoryData`

## 💬 Just the essentials

* **What it is.** The list of closed orders in a time window, each with open/close price & time and realized profit.
* **Why you need it.** Reporting, P/L analysis, win-rate stats, strategy validation.
* **When to use.** After sessions, or any time you audit what actually happened.

---

## 🎯 Purpose

* Closed orders over a date range
* Open/close price, open/close time, realized profit per order
* Pagination for large histories (`total_count`, `next_page_token`)
* Choose the sort order

---

## Method Signature

```java
public OrdersHistoryData ordersHistory()   // HISTORY_SORT_BY_CLOSE_TIME_DESC, no window, no paging
public OrdersHistoryData ordersHistory(
        EnumOrderHistorySortType sortMode,
        Instant from, Instant to,
        Integer pageNumber, Integer itemsPerPage)
```

`from`/`to` are UTC `Instant`s (pass `null` to leave unbounded); `pageNumber`/`itemsPerPage` are `Integer`s (pass `null` to omit). For a friendly string sort (`"close_desc"` etc.) use the [MT4Service](../../MT4Service/MT4Service.Overview.md) wrapper.

**Request message (field numbers per our stub):**

```protobuf
message OrdersHistoryRequest {
  EnumOrderHistorySortType input_sort_mode = 1;
  Timestamp input_from = 2;        // start of window
  Timestamp input_to = 3;          // end of window
  int32 page_number = 4;
  int32 items_per_page = 5;
  string page_token = 6;
  int32 page_size = 7;
  Timestamp input_from_utc = 8;
  Timestamp input_to_utc = 9;
}
```

**Payload message:**

```protobuf
message OrdersHistoryData {
  repeated HistoryOrderInfo orders_info = 1;
  int32 total_count = 2;
  string next_page_token = 3;
}

message HistoryOrderInfo {
  string comment = 1;
  double commision = 2;            // proto spelling (single s)
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
  Timestamp close_time = 17;
  double close_price = 18;
  int64 account_login = 19;
  Timestamp expiration_time_utc = 20;
  Timestamp open_time_utc = 21;
  Timestamp close_time_utc = 22;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `sortMode` | `EnumOrderHistorySortType` | Sort order; the no-arg overload defaults to `HISTORY_SORT_BY_CLOSE_TIME_DESC`. |
| `from` | `Instant`, optional | Start of the window (UTC). Pass `null` to leave unbounded. |
| `to` | `Instant`, optional | End of the window (UTC). Pass `null` to leave unbounded. |
| `pageNumber` | `Integer`, optional | Zero-based page index. |
| `itemsPerPage` | `Integer`, optional | Page size. |

---

## ⬆️ Output — `OrdersHistoryData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `orders_info` | `getOrdersInfoList()` / `getOrdersInfo(i)` / `getOrdersInfoCount()` | The closed orders for this page |
| `total_count` | `getTotalCount()` | Total orders matching the query (across pages) |
| `next_page_token` | `getNextPageToken()` | Token/cursor for the next page (if paging by token) |

Each `HistoryOrderInfo` (adds close fields to the open-order shape):

| Field | Java getter | Description |
|-------|-------------|-------------|
| `ticket` | `getTicket()` | Order ticket |
| `symbol` | `getSymbol()` | Symbol |
| `order_type` | `getOrderType()` | Operation (`OpenedOrderType`, `OO_OP_*`) |
| `lots` | `getLots()` | Volume |
| `open_price` / `close_price` | `getOpenPrice()` / `getClosePrice()` | Entry / exit price |
| `stop_loss` / `take_profit` | `getStopLoss()` / `getTakeProfit()` | SL / TP price (0 if unset) |
| `profit` | `getProfit()` | Realized profit (excl. swap/commission) |
| `swap` / `commision` | `getSwap()` / `getCommision()` | Costs (note proto spelling `commision`) |
| `open_time` / `close_time` (+ `_utc`) | `getOpenTime()` / `getCloseTime()` (+ `…Utc()`) | Entry / exit time |
| `magic_number` | `getMagicNumber()` | Expert/magic id |
| `comment` | `getComment()` | Order comment |

---

## 🧱 Related enums (from proto)

### `EnumOrderHistorySortType` — input `sortMode`

| Constant | Value | Meaning |
|----------|-------|---------|
| `HISTORY_SORT_BY_OPEN_TIME_ASC` | 0 | By open time, ascending |
| `HISTORY_SORT_BY_OPEN_TIME_DESC` | 1 | By open time, descending |
| `HISTORY_SORT_BY_CLOSE_TIME_ASC` | 2 | By close time, ascending |
| `HISTORY_SORT_BY_CLOSE_TIME_DESC` | 3 | By close time, descending (default) |
| `HISTORY_SORT_BY_ORDER_TICKET_ID_ASC` | 4 | By ticket id, ascending |
| `HISTORY_SORT_BY_ORDER_TICKET_ID_DESC` | 5 | By ticket id, descending |

> `order_type` uses the same `OpenedOrderType` (`OO_OP_*`) enum as [opened_orders](opened_orders.md).

---

## 🧩 Notes & Tips

* **Bound the window.** Pass both `from` and `to`; omitting them (`null`) returns the default page of all history.
* **`total_count` drives paging.** It's the full match count — loop pages while `pageNumber * itemsPerPage < total_count`.
* **Net result per trade** is `getProfit() + getSwap() + getCommision()` (costs are usually negative).
* **Balance/credit rows.** As with open orders, `OO_OP_BALANCE`/`OO_OP_CREDIT` can appear — skip them for trade stats.
* **Prefer [trade_stats](../1.%20Account_Information/trade_stats.md) for ratios.** It computes profit factor, win rate, drawdowns for you; use `ordersHistory` when you need the raw rows.

---

## 🔗 Usage Examples

### 1) Last 30 days — closed trades table

```java
static void recentHistory(MT4Account account, int days) {
    Instant end = Instant.now();
    var hist = account.ordersHistory(EnumOrderHistorySortType.HISTORY_SORT_BY_CLOSE_TIME_DESC,
            end.minus(days, ChronoUnit.DAYS), end, null, null);
    System.out.printf("%d closed order(s) in %dd%n", hist.getTotalCount(), days);
    for (var o : hist.getOrdersInfoList().stream().limit(20).toList()) {
        String side = o.getOrderType() == OpenedOrderType.OO_OP_BUY ? "BUY" : "SELL";
        double net = o.getProfit() + o.getSwap() + o.getCommision();
        System.out.printf(Locale.ROOT, "  #%d %-8s %-4s %.2f %.5f->%.5f  net=%+.2f%n",
                o.getTicket(), o.getSymbol(), side, o.getLots(), o.getOpenPrice(), o.getClosePrice(), net);
    }
}
```

### 2) Realized P/L and win rate for a window

```java
static double windowPnl(MT4Account account, int days) {
    Instant end = Instant.now();
    var trades = account.ordersHistory(EnumOrderHistorySortType.HISTORY_SORT_BY_CLOSE_TIME_DESC,
            end.minus(days, ChronoUnit.DAYS), end, null, null).getOrdersInfoList();
    double realized = trades.stream().mapToDouble(o -> o.getProfit() + o.getSwap() + o.getCommision()).sum();
    long wins = trades.stream().filter(o -> o.getProfit() + o.getSwap() + o.getCommision() > 0).count();
    double wr = trades.isEmpty() ? 0.0 : wins * 100.0 / trades.size();
    System.out.printf(Locale.ROOT, "%d trades | realized %+.2f | win rate %.1f%%%n", trades.size(), realized, wr);
    return realized;
}
```

### 3) Page through a long history

Use `total_count` + `itemsPerPage` to walk every page.

```java
static List<HistoryOrderInfo> allHistory(MT4Account account, int days, int pageSize) {
    Instant end = Instant.now(), start = end.minus(days, ChronoUnit.DAYS);
    var out = new ArrayList<HistoryOrderInfo>();
    int page = 0, total;
    do {
        var res = account.ordersHistory(EnumOrderHistorySortType.HISTORY_SORT_BY_CLOSE_TIME_DESC,
                start, end, page, pageSize);
        out.addAll(res.getOrdersInfoList());
        total = res.getTotalCount();
        if (res.getOrdersInfoCount() == 0) break;
        page++;
    } while (page * pageSize < total);
    System.out.printf("collected %d / %d orders%n", out.size(), total);
    return out;
}
```

---

## ⚠️ Pitfalls

* **Costs are separate.** `profit` excludes `swap`/`commision` — add them for the true net.
* **Window required for meaningful results.** Without `from`/`to` you get the default page, not "everything".
* **Page bounds.** Stop when you've collected `total_count` (or a page comes back empty) to avoid an infinite loop.
* **Proto spelling:** `commision` (single s) → `getCommision()`.

---

## 🧪 Testing Suggestions

* **Known window.** On a demo account with history, `getOrdersInfoCount() <= getTotalCount()` and timestamps fall inside `[from, to]`.
* **Round-trip.** Open then close an order, then query a window covering now — the ticket appears with a `close_time` and realized `profit`.
* **Paging.** With `itemsPerPage` smaller than `total_count`, concatenated pages equal the full set with no duplicates.

---

## 📚 See also

* [Opened Orders](opened_orders.md)
* [Opened Order Tickets](opened_orders_tickets.md)
* [Trade Statistics](../1.%20Account_Information/trade_stats.md)

* **Back to:** [Orders & History — Overview](Orders_History.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
