# Quote History (OHLC)

> OHLC bar history for a symbol and timeframe over a time range — candles for charts, indicators and backtests.

**API Information:**

* **Low-level API:** `MT4Account.quoteHistory(String symbol, ENUM_QUOTE_HISTORY_TIMEFRAME timeframe, Instant from, Instant to)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.MarketInfo`
* **RPC:** `QuoteHistory(QuoteHistoryRequest) -> QuoteHistoryReply`
* **Payload:** `QuoteHistoryData`

## 💬 Just the essentials

* **What it is.** A list of OHLC bars for one symbol at a chosen timeframe, between `from` and `to`.
* **Why you need it.** Charting, indicators (SMA/ATR/…), and backtests.
* **When to use.** Any analysis that needs candles rather than a single live price.

---

## 🎯 Purpose

* OHLC bars for a timeframe (M1 … MN1)
* Bounded by a from/to time range
* Tick volume and spread per bar
* Feed indicators, charts and backtests

---

## Method Signature

```java
public QuoteHistoryData quoteHistory(
        String symbol,
        ENUM_QUOTE_HISTORY_TIMEFRAME timeframe,
        Instant from, Instant to)
```

> For a friendly string timeframe (`"H1"`) and a bar-count limit, use the [MT4Service](../../MT4Service/MT4Service.Overview.md) wrapper.

**Request message:**

```protobuf
message QuoteHistoryRequest {
  string symbol = 1;
  ENUM_QUOTE_HISTORY_TIMEFRAME timeframe = 2;
  Timestamp fromTime = 3;      // note camelCase
  Timestamp toTime = 4;
  Timestamp fromTimeUtc = 5;
  Timestamp toTimeUtc = 6;
}
```

**Payload message:**

```protobuf
message QuoteHistoryData {
  repeated HistoryQuote historical_quotes = 1;
}

message HistoryQuote {
  string symbol = 1;
  int32 index = 2;
  Timestamp time = 3;
  double open = 4;
  double close = 5;
  double high = 6;
  double low = 7;
  int64 real_volume = 8;
  int64 tick_volume = 9;
  int32 spread = 10;
  Timestamp time_utc = 11;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `symbol` | `String` | Symbol name. |
| `timeframe` | `ENUM_QUOTE_HISTORY_TIMEFRAME` | Bar timeframe (`QH_PERIOD_*`). |
| `from` | `Instant` | Start of the range (UTC). |
| `to` | `Instant` | End of the range (UTC). |

---

## ⬆️ Output — `QuoteHistoryData`

`getHistoricalQuotesList()` / `getHistoricalQuotes(i)` / `getHistoricalQuotesCount()` — a list of `HistoryQuote` bars:

| Field | Java getter | Description |
|-------|-------------|-------------|
| `time` / `time_utc` | `getTime()` / `getTimeUtc()` | Bar open time (server / UTC) |
| `open` / `high` / `low` / `close` | `getOpen()` / `getHigh()` / `getLow()` / `getClose()` | OHLC prices |
| `tick_volume` | `getTickVolume()` | Number of ticks in the bar |
| `real_volume` | `getRealVolume()` | Real volume (0 if the broker doesn't provide it) |
| `spread` | `getSpread()` | Spread at the bar, in points |
| `symbol` | `getSymbol()` | Symbol |
| `index` | `getIndex()` | Bar index in the series |

---

## 🧱 Related enums (from proto)

### `ENUM_QUOTE_HISTORY_TIMEFRAME` — input `timeframe`

| Constant | Value | Meaning |
|----------|-------|---------|
| `QH_PERIOD_M1` | 0 | 1 minute |
| `QH_PERIOD_M5` | 1 | 5 minutes |
| `QH_PERIOD_M15` | 2 | 15 minutes |
| `QH_PERIOD_M30` | 3 | 30 minutes |
| `QH_PERIOD_H1` | 4 | 1 hour |
| `QH_PERIOD_H4` | 5 | 4 hours |
| `QH_PERIOD_D1` | 6 | 1 day |
| `QH_PERIOD_W1` | 7 | 1 week |
| `QH_PERIOD_MN1` | 8 | 1 month |

> Access via `Mt4TermApiMarketInfo.ENUM_QUOTE_HISTORY_TIMEFRAME.QH_PERIOD_H1`.

---

## 🧩 Notes & Tips

* **`from` / `to` are required** `Instant`s (UTC) — the range bounds how many bars you get back.
* **`tick_volume` vs `real_volume`.** On FX most brokers report tick volume; `real_volume` is often 0.
* **Bars are time-ordered** by `time`; `index` is their position in the series.
* **For a single live price** use [quote](quote.md); for a live stream use [on_symbol_tick](../5.%20Streaming_Methods/on_symbol_tick.md).

---

## 🔗 Usage Examples

### 1) Fetch the last N hourly bars

```java
static List<HistoryQuote> recentBars(MT4Account account, String symbol, int hours) {
    Instant end = Instant.now();
    var bars = account.quoteHistory(symbol, ENUM_QUOTE_HISTORY_TIMEFRAME.QH_PERIOD_H1,
            end.minus(hours, ChronoUnit.HOURS), end).getHistoricalQuotesList();
    System.out.printf("%d H1 bars%n", bars.size());
    for (var b : bars.subList(Math.max(0, bars.size() - 3), bars.size())) {
        System.out.printf(Locale.ROOT, "  O=%.5f H=%.5f L=%.5f C=%.5f vol=%d%n",
                b.getOpen(), b.getHigh(), b.getLow(), b.getClose(), b.getTickVolume());
    }
    return bars;
}
```

### 2) Simple moving average of closes

```java
static Double sma(MT4Account account, String symbol, int period) {
    Instant end = Instant.now();
    var bars = account.quoteHistory(symbol, ENUM_QUOTE_HISTORY_TIMEFRAME.QH_PERIOD_H1,
            end.minus(period * 2L, ChronoUnit.HOURS), end).getHistoricalQuotesList();
    if (bars.size() < period) return null;
    double value = bars.subList(bars.size() - period, bars.size()).stream()
            .mapToDouble(b -> b.getClose()).average().orElse(0);
    System.out.printf(Locale.ROOT, "SMA(%d) %s = %.5f%n", period, symbol, value);
    return value;
}
```

### 3) Average daily range (last 14 days)

A quick ATR-style volatility read from daily bars.

```java
static double avgDailyRange(MT4Account account, String symbol, int days) {
    Instant end = Instant.now();
    var bars = account.quoteHistory(symbol, ENUM_QUOTE_HISTORY_TIMEFRAME.QH_PERIOD_D1,
            end.minus(days * 2L, ChronoUnit.DAYS), end).getHistoricalQuotesList();
    var ranges = bars.subList(Math.max(0, bars.size() - days), bars.size());
    double adr = ranges.isEmpty() ? 0.0 : ranges.stream().mapToDouble(b -> b.getHigh() - b.getLow()).average().orElse(0);
    System.out.printf(Locale.ROOT, "ADR(%d) %s = %.5f%n", days, symbol, adr);
    return adr;
}
```

---

## ⚠️ Pitfalls

* **Range too wide.** A tight timeframe over a long range returns a lot of bars — scope the window to what you need.
* **Partial last bar.** The most recent bar may still be forming; treat it as provisional.
* **Empty result.** Weekends/holidays or an out-of-session range can yield few or no bars — guard before indexing.

---

## 🧪 Testing Suggestions

* **Happy path.** H1 over the last 48h returns a sensible bar count with `high >= low` and `low <= close <= high`.
* **Ordering.** Bars are ascending in `time`; the last bar's time is close to now.
* **Timeframe scaling.** D1 over 14 days returns ~10–14 bars (trading days), far fewer than H1.

---

## 📚 See also

* [Quote](quote.md)
* [Many Quotes](quote_many.md)
* [On Symbol Tick (stream)](../5.%20Streaming_Methods/on_symbol_tick.md)

* **Back to:** [Symbols & Quotes — Overview](Symbols_Quotes.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
