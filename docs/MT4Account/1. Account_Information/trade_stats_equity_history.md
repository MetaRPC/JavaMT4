# Stats + Equity + Trades

> Statistics, the equity curve **and** the trade list — everything a performance report needs, in a single round-trip.

**API Information:**

* **Low-level API:** `MT4Account.tradeStatsEquityHistory(Instant from, Boolean excludeSameBars)` (defined in [`src/main/java/pro/mrpc/mt4/MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.TradeStats`
* **RPC:** `TradeStatsEquityHistory(TradeStatsEquityHistoryRequest) -> TradeStatsEquityHistoryReply`
* **Payload:** `StatsWithChartsData`

## 💬 Just the essentials

* **What it is.** A combined payload: the same `StatsData` as [trade_stats](trade_stats.md), the equity `charts` (same points as [equity_history](equity_history.md)), plus the closed and open trade lists.
* **Why you need it.** Build a full report (numbers + curve + trades) without three separate calls.
* **When to use.** Report/export screens where you want everything consistent and in one shot.

---

## 🎯 Purpose

* Headline statistics (`stats`)
* Equity-curve points (`charts`)
* Closed trades (`trades`) and currently open trades (`open_trades`)
* One round-trip for a complete report

---

## Method Signature

```java
public StatsWithChartsData tradeStatsEquityHistory(Instant from, Boolean excludeSameBars)
```

`from` is an optional UTC `Instant` (pass `null` for full history); `excludeSameBars` is a `Boolean` (pass `null` to leave it unset).

**Request message:**

```protobuf
message TradeStatsEquityHistoryRequest {
  string from = 1;              // ISO-8601 start; empty = full history
  bool exclude_same_bars = 2;   // collapse consecutive equal equity points
  string from_utc = 3;          // ISO-8601 start in UTC
}
```

**Payload message (field numbers per our stub):**

```protobuf
message StatsWithChartsData {
  StatsData stats = 1;                         // see trade_stats
  repeated EquityPointData charts = 2;         // see equity_history
  repeated TradeHistoryItemData trades = 3;    // closed trades
  repeated TradeHistoryItemData open_trades = 4;
}

message TradeHistoryItemData {
  int64 ticket = 1;
  string symbol = 2;
  bool is_buy = 3;
  double lots = 4;
  double open_price = 5;
  double close_price = 6;
  Timestamp open_time = 7;
  Timestamp close_time = 8;
  double profit = 9;
  double commission = 10;
  double swap = 11;
  string comment = 12;
  Timestamp open_time_utc = 13;
  Timestamp close_time_utc = 14;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `from` | `Instant`, optional | Start of the window; serialized to an ISO-8601 string. Pass `null` for full history. |
| `excludeSameBars` | `Boolean`, optional | Collapse consecutive equal equity points to shrink `charts`. Pass `null` to leave unset. |

---

## ⬆️ Output — `StatsWithChartsData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `stats` | `getStats()` | Headline statistics — see [trade_stats](trade_stats.md) |
| `charts` | `getChartsCount()` / `getCharts(i)` / `getChartsList()` | Equity-curve points — see [equity_history](equity_history.md) |
| `trades` | `getTradesCount()` / `getTrades(i)` / `getTradesList()` | Closed trades |
| `open_trades` | `getOpenTradesCount()` / `getOpenTrades(i)` / `getOpenTradesList()` | Currently open trades |

Each `TradeHistoryItemData`:

| Field | Java getter | Description |
|-------|-------------|-------------|
| `ticket` | `getTicket()` | Trade ticket |
| `symbol` | `getSymbol()` | Symbol |
| `is_buy` | `getIsBuy()` | True = buy, False = sell |
| `lots` | `getLots()` | Volume |
| `open_price` / `close_price` | `getOpenPrice()` / `getClosePrice()` | Entry / exit price |
| `open_time` / `close_time` (+ `_utc`) | `getOpenTime()` / `getCloseTime()` (+ `…Utc()`) | Entry / exit time |
| `profit` | `getProfit()` | Trade profit (excl. commission/swap) |
| `commission` / `swap` | `getCommission()` / `getSwap()` | Costs |
| `comment` | `getComment()` | Order comment |

---

## 🧩 Notes & Tips

* **One call, three views.** `stats` is the summary, `charts` is the curve, `trades`/`open_trades` are the raw rows — everything stays consistent because it's a single snapshot.
* **Net result per trade** is `getProfit() + getCommission() + getSwap()` (commission and swap are usually negative). Don't forget the costs when totalling.
* **`is_buy` is the side** — there's no separate order-type enum here; `true` = long, `false` = short.
* **Can be large.** `charts` and `trades` grow with account age — scope with `from` and `excludeSameBars = true` for the curve.
* **Prefer this over three calls** when you need all three together; use the individual methods when you only need one.

---

## 🔗 Usage Examples

### 1) Full report in one round-trip

Pull everything and print a compact report header.

```java
static void fullReport(MT4Account account) {
    var r = account.tradeStatsEquityHistory(null, true);
    var s = r.getStats();
    System.out.println("── Report ───────────────────────────────────");
    System.out.printf(Locale.ROOT, " Profit factor : %.2f%n", s.getProfitFactor());
    System.out.printf(Locale.ROOT, " Trades        : %d  (open now: %d)%n", s.getTrades(), r.getOpenTradesCount());
    System.out.printf(Locale.ROOT, " Growth        : %+.2f%%%n", s.getGrowthPercent());
    System.out.printf(Locale.ROOT, " Equity points : %d%n", r.getChartsCount());
    System.out.printf(Locale.ROOT, " Closed trades : %d%n", r.getTradesCount());
}
```

### 2) Recent closed trades table

Show the last few closed trades with side, size and net result.

```java
static void recentTrades(MT4Account account, int n) {
    var rows = new ArrayList<>(account.tradeStatsEquityHistory(null, null).getTradesList());
    rows.sort(Comparator.comparingLong((TradeHistoryItemData t) -> t.getCloseTimeUtc().getSeconds()).reversed());
    System.out.printf(Locale.ROOT, "%10s %-8s %-5s %6s %10s%n", "Ticket", "Sym", "Side", "Lots", "Net");
    for (var t : rows.subList(0, Math.min(n, rows.size()))) {
        double net = t.getProfit() + t.getCommission() + t.getSwap();
        System.out.printf(Locale.ROOT, "%10d %-8s %-5s %6.2f %10.2f%n",
                t.getTicket(), t.getSymbol(), t.getIsBuy() ? "BUY" : "SELL", t.getLots(), net);
    }
}
```

### 3) Open exposure right now

Summarize current open trades (count, lots, floating P/L) straight from `open_trades`.

```java
static void openExposure(MT4Account account) {
    var open = account.tradeStatsEquityHistory(null, null).getOpenTradesList();
    double lots = open.stream().mapToDouble(t -> t.getLots()).sum();
    double floating = open.stream().mapToDouble(t -> t.getProfit() + t.getCommission() + t.getSwap()).sum();
    System.out.printf(Locale.ROOT, "open trades: %d | lots: %.2f | floating: %+.2f%n", open.size(), lots, floating);
}
```

---

## ⚠️ Pitfalls

* **Heaviest call in the group.** It returns stats + curve + every trade — scope it (`from`, `excludeSameBars`) and summarize rather than dumping all rows.
* **Costs are separate.** `profit` excludes `commission` and `swap`; add them for the true net.
* **Empty account.** `trades`, `open_trades` and `charts` can all be empty — guard before indexing.

---

## 🧪 Testing Suggestions

* **Happy path (demo with history).** `stats.getTrades()` equals `getTradesCount()` for the same window; `charts` is non-empty.
* **Open vs closed.** `getOpenTradesCount()` matches what [opened_orders](../2.%20Orders_History/opened_orders.md) reports.
* **Consistency.** `stats` here equals a separate [trade_stats](trade_stats.md) call over the same `from`.

---

## 📚 See also

* [Account Summary](account_summary.md)
* [Trade Statistics](trade_stats.md)
* [Unrealized P/L Extrema](trade_unrealized_pl.md)
* [Equity Curve](equity_history.md)

* **Back to:** [Account Information — Overview](Account_Information.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
