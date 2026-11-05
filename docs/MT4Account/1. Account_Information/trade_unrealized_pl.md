# Unrealized P/L Extrema

> For each open order, the **best and worst floating P/L** it has reached (with timestamps) — classic MFE / MAE excursion data.

**API Information:**

* **Low-level API:** `MT4Account.tradeUnrealizedPl(Instant from)` (defined in [`src/main/java/pro/mrpc/mt4/MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.TradeStats`
* **RPC:** `TradeUnrealizedPL(TradeUnrealizedPLRequest) -> TradeUnrealizedPLReply`
* **Payload:** `TradeUnrealizedPLData`

## 💬 Just the essentials

* **What it is.** Per open order, the maximum favorable (`max_unrealized_pl`) and maximum adverse (`min_unrealized_pl`) floating P/L reached since it opened, each with a timestamp.
* **Why you need it.** MFE/MAE analysis — how far trades run in your favor before you exit, and how deep they dip before recovering.
* **When to use.** Tuning stop-loss / take-profit placement and trailing logic from real behaviour.

---

## 🎯 Purpose

* See the peak profit each open order reached (MFE)
* See the deepest drawdown each open order survived (MAE)
* Timestamps of both extrema (server and UTC)
* Drive data-informed SL/TP and trailing decisions

---

## Method Signature

```java
public TradeUnrealizedPLData tradeUnrealizedPl(Instant from)
```

`from` is an optional UTC `java.time.Instant` (pass `null` for full history); it is sent as an ISO-8601 string.

**Request message:**

```protobuf
message TradeUnrealizedPLRequest {
  string from = 1;       // ISO-8601 start; empty = full history
  string from_utc = 2;   // ISO-8601 start in UTC
}
```

**Payload message (field numbers per our stub):**

```protobuf
message TradeUnrealizedPLData {
  repeated TradeUnrealizedExtremaData extrema = 1;
}

message TradeUnrealizedExtremaData {
  int64 ticket = 1;
  string symbol = 2;
  Timestamp max_unrealized_time = 3;      // when peak profit was reached
  double max_unrealized_pl = 4;           // MFE — best floating P/L
  Timestamp min_unrealized_time = 5;      // when deepest loss was reached
  double min_unrealized_pl = 6;           // MAE — worst floating P/L
  Timestamp max_unrealized_time_utc = 7;
  Timestamp min_unrealized_time_utc = 8;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `from` | `Instant`, optional | Start of the window; serialized to an ISO-8601 string. Pass `null` for full history. |

---

## ⬆️ Output — `TradeUnrealizedPLData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `extrema` | `getExtremaCount()` / `getExtrema(i)` / `getExtremaList()` | One entry per order with excursion data |

Each `TradeUnrealizedExtremaData`:

| Field | Java getter | Description |
|-------|-------------|-------------|
| `ticket` | `getTicket()` | Order ticket |
| `symbol` | `getSymbol()` | Symbol |
| `max_unrealized_pl` | `getMaxUnrealizedPl()` | **MFE** — highest floating P/L reached |
| `max_unrealized_time` / `_utc` | `getMaxUnrealizedTime()` / `getMaxUnrealizedTimeUtc()` | When the MFE occurred (server / UTC) |
| `min_unrealized_pl` | `getMinUnrealizedPl()` | **MAE** — lowest floating P/L reached |
| `min_unrealized_time` / `_utc` | `getMinUnrealizedTime()` / `getMinUnrealizedTimeUtc()` | When the MAE occurred (server / UTC) |

---

## 🧩 Notes & Tips

* **MFE vs MAE.** `getMaxUnrealizedPl()` is how good the trade got (Maximum Favorable Excursion); `getMinUnrealizedPl()` is how bad it got (Maximum Adverse Excursion). Both are *floating* values seen while the order was open, not the realized result.
* **Use it to size stops.** If winners routinely dip to −15 pips (MAE) before running, a −10 pip stop was too tight. If MFE far exceeds your actual exits, your TP is leaving money on the table.
* **Signs matter.** `max_unrealized_pl` is usually ≥ 0 and `min_unrealized_pl` usually ≤ 0, but a trade that was never in profit can have a negative MFE — don't assume positivity.
* **Timestamps are protobuf `Timestamp`s** — convert with `Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos())`.
* **`from` scopes the set**; pass `null` for everything available.

---

## 🔗 Usage Examples

### 1) MFE / MAE table

Show, per order, how far it ran in your favor and how deep it dipped.

```java
static void excursionTable(MT4Account account) {
    var data = account.tradeUnrealizedPl(null);
    System.out.printf(Locale.ROOT, "%10s %-8s %10s %10s%n", "Ticket", "Symbol", "MFE", "MAE");
    for (var e : data.getExtremaList()) {
        System.out.printf(Locale.ROOT, "%10d %-8s %10.2f %10.2f%n",
                e.getTicket(), e.getSymbol(), e.getMaxUnrealizedPl(), e.getMinUnrealizedPl());
    }
}
```

### 2) Were my stops too tight?

Find winners that first went underwater — their MAE tells you the stop distance they actually needed.

```java
static void stopPressure(MT4Account account, double threshold) {
    var deep = new ArrayList<>(account.tradeUnrealizedPl(null).getExtremaList());
    deep.removeIf(e -> e.getMinUnrealizedPl() > threshold);
    deep.sort(Comparator.comparingDouble(e -> e.getMinUnrealizedPl())); // deepest first
    System.out.printf("%d order(s) dipped below %.2f before now:%n", deep.size(), threshold);
    for (var e : deep.subList(0, Math.min(10, deep.size()))) {
        var when = Instant.ofEpochSecond(e.getMinUnrealizedTime().getSeconds());
        System.out.printf(Locale.ROOT, "  #%d %s: MAE %.2f at %s%n",
                e.getTicket(), e.getSymbol(), e.getMinUnrealizedPl(), when);
    }
}
```

### 3) Profit give-back detector

Highlight orders that reached a solid peak — candidates where a trailing stop or earlier TP would have locked in more.

```java
static void giveback(MT4Account account, double minPeak) {
    for (var e : account.tradeUnrealizedPl(null).getExtremaList()) {
        if (e.getMaxUnrealizedPl() >= minPeak) {
            System.out.printf(Locale.ROOT, "#%d %s: peaked at %.2f (worst %.2f) — review exit/trailing%n",
                    e.getTicket(), e.getSymbol(), e.getMaxUnrealizedPl(), e.getMinUnrealizedPl());
        }
    }
}
```

---

## ⚠️ Pitfalls

* **Open orders only.** These extrema describe currently-open orders' journey so far; for closed-trade results use [ordersHistory](../2.%20Orders_History/orders_history.md) or [tradeStats](trade_stats.md).
* **Floating, not realized.** A high MFE doesn't mean you captured it — that's exactly the point of the give-back check.
* **Empty when flat.** With no open orders, `extrema` is empty — handle the zero case.

---

## 🧪 Testing Suggestions

* **With open orders.** `getExtremaCount() > 0`; for each entry `max_unrealized_pl >= min_unrealized_pl`.
* **Flat account.** Returns an empty `extrema` list without error.
* **Timestamp sanity.** `min_unrealized_time` / `max_unrealized_time` convert to instants that fall after the order's open time.

---

## 📚 See also

* [Account Summary](account_summary.md)
* [Trade Statistics](trade_stats.md)
* [Equity Curve](equity_history.md)
* [Stats + Equity + Trades](trade_stats_equity_history.md)

* **Back to:** [Account Information — Overview](Account_Information.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
