# Equity Curve

> Time series of balance/equity points with running drawdowns — your equity curve, ready to chart or export.

**API Information:**

* **Low-level API:** `MT4Account.equityHistory(Instant from, Boolean excludeSameBars)` (defined in [`src/main/java/pro/mrpc/mt4/MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.TradeStats`
* **RPC:** `EquityHistory(EquityHistoryRequest) -> EquityHistoryReply`
* **Payload:** `EquityHistoryData`

## 💬 Just the essentials

* **What it is.** A chronological list of points, each with balance, equity, running drawdowns and the cash flow at that moment.
* **Why you need it.** To plot the equity curve, measure maximum drawdown, or export performance to a spreadsheet.
* **When to use.** Reporting, charting, drawdown analysis.

---

## 🎯 Purpose

* Balance & equity over time (the curve)
* Running balance/equity drawdowns (money and %)
* Realized / unrealized P/L and cash flow per point
* Feed charts, CSV exports and risk reports

---

## Method Signature

```java
public EquityHistoryData equityHistory(Instant from, Boolean excludeSameBars)
```

`from` is an optional UTC `Instant` (pass `null` for full history); `excludeSameBars` is a `Boolean` (pass `null` to leave it unset).

**Request message:**

```protobuf
message EquityHistoryRequest {
  string from = 1;              // ISO-8601 start; empty = full history
  bool exclude_same_bars = 2;   // collapse consecutive equal points
  string from_utc = 3;          // ISO-8601 start in UTC
}
```

**Payload message (field numbers per our stub):**

```protobuf
message EquityHistoryData {
  repeated EquityPointData points = 1;
}

message EquityPointData {
  Timestamp time = 1;
  double balance = 2;
  double equity = 3;
  double balance_drawdown_raw = 4;
  double balance_drawdown_relative = 5;    // percent
  double equity_drawdown_raw = 6;
  double equity_drawdown_relative = 7;     // percent
  double realized_pl = 8;
  double unrealized_pl = 9;
  Timestamp time_utc = 10;
  double cash_flow = 11;                    // deposits/withdrawals at this point
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `from` | `Instant`, optional | Start of the window; serialized to an ISO-8601 string. Pass `null` for full history. |
| `excludeSameBars` | `Boolean`, optional | Collapse consecutive equal points to shrink the payload. Pass `null` to leave unset. |

---

## ⬆️ Output — `EquityHistoryData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `points` | `getPointsCount()` / `getPoints(i)` / `getPointsList()` | The equity-curve points, in time order |

Each `EquityPointData`:

| Field | Java getter | Description |
|-------|-------------|-------------|
| `time` / `time_utc` | `getTime()` / `getTimeUtc()` | Point timestamp (server / UTC) |
| `balance` | `getBalance()` | Balance at this point |
| `equity` | `getEquity()` | Equity at this point |
| `balance_drawdown_raw` / `_relative` | `getBalanceDrawdownRaw()` / `getBalanceDrawdownRelative()` | Balance drawdown (money / %) |
| `equity_drawdown_raw` / `_relative` | `getEquityDrawdownRaw()` / `getEquityDrawdownRelative()` | Equity drawdown (money / %) |
| `realized_pl` | `getRealizedPl()` | Realized P/L to this point |
| `unrealized_pl` | `getUnrealizedPl()` | Floating P/L at this point |
| `cash_flow` | `getCashFlow()` | Deposit/withdrawal applied at this point |

---

## 🧩 Notes & Tips

* **Drawdowns are precomputed.** You don't have to derive the running peak yourself — `getEquityDrawdownRelative()` is already the % below the high-water mark at each point.
* **`cash_flow` separates deposits from trading.** A jump in `balance` with a matching `cash_flow` is a deposit/withdrawal, not a trade — exclude it when measuring trading performance.
* **This payload can be large.** A long-lived account has many points; pass `from` to scope the window and/or `excludeSameBars = true` to drop flat stretches.
* **Timestamps are protobuf `Timestamp`s** — convert with `Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos())`.
* **Pairs with [trade_stats](trade_stats.md).** Use `tradeStats` for the headline ratios and `equityHistory` for the curve, or [tradeStatsEquityHistory](trade_stats_equity_history.md) to fetch both at once.

---

## 🔗 Usage Examples

### 1) Export the curve to CSV

Dump the equity curve so you can chart it in a spreadsheet or notebook.

```java
static void exportEquityCsv(MT4Account account, Path path) throws IOException {
    var data = account.equityHistory(null, true);
    try (var w = Files.newBufferedWriter(path)) {
        w.write("time_utc,balance,equity,equity_dd_%\n");
        for (var p : data.getPointsList()) {
            var t = Instant.ofEpochSecond(p.getTimeUtc().getSeconds());
            w.write(String.format(Locale.ROOT, "%s,%.2f,%.2f,%.2f%n",
                    t, p.getBalance(), p.getEquity(), p.getEquityDrawdownRelative()));
        }
    }
    System.out.printf("wrote %d points -> %s%n", data.getPointsCount(), path);
}
```

### 2) Maximum drawdown from the curve

Find the deepest equity drawdown and when it happened.

```java
static void maxDrawdown(MT4Account account) {
    var data = account.equityHistory(null, null);
    if (data.getPointsCount() == 0) { System.out.println("no equity history"); return; }
    var worst = data.getPointsList().stream()
            .max(Comparator.comparingDouble(p -> p.getEquityDrawdownRelative())).orElseThrow();
    var when = Instant.ofEpochSecond(worst.getTimeUtc().getSeconds());
    System.out.printf(Locale.ROOT, "Max equity drawdown: %.2f%% (%,.2f) at %s UTC%n",
            worst.getEquityDrawdownRelative(), worst.getEquityDrawdownRaw(), when);
}
```

### 3) Trading-only result (strip deposits)

Separate trading performance from deposits/withdrawals using `cash_flow`.

```java
static double tradingPnl(MT4Account account) {
    var pts = account.equityHistory(null, null).getPointsList();
    if (pts.isEmpty()) return 0.0;
    double netCash = pts.stream().mapToDouble(p -> p.getCashFlow()).sum();
    double start = pts.get(0).getBalance(), end = pts.get(pts.size() - 1).getBalance();
    double trading = (end - start) - netCash;
    System.out.printf(Locale.ROOT, "balance %,.2f -> %,.2f | deposits/withdrawals %+,.2f | trading P/L %+,.2f%n",
            start, end, netCash, trading);
    return trading;
}
```

---

## ⚠️ Pitfalls

* **Large payloads.** Full history can be big — scope with `from` and/or `excludeSameBars = true`; print counts rather than every point.
* **Deposits skew raw balance deltas.** Use `cash_flow` to isolate trading P/L from account funding.
* **Empty history.** A fresh account returns an empty `points` list — guard before indexing `getPoints(0)` / the last element.

---

## 🧪 Testing Suggestions

* **Happy path (demo with history).** `points` is non-empty and ordered by time; `equity_drawdown_relative >= 0` for every point.
* **`excludeSameBars`.** The collapsed call returns no more points than the full call.
* **Windowing.** A recent `from` returns fewer points than full history.

---

## 📚 See also

* [Account Summary](account_summary.md)
* [Trade Statistics](trade_stats.md)
* [Unrealized P/L Extrema](trade_unrealized_pl.md)
* [Stats + Equity + Trades](trade_stats_equity_history.md)

* **Back to:** [Account Information — Overview](Account_Information.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
