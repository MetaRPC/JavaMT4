# Many Quotes

> Latest quotes for several symbols in a single round-trip.

**API Information:**

* **Low-level API:** `MT4Account.quoteMany(List<String> symbols)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.MarketInfo`
* **RPC:** `QuoteMany(QuoteManyRequest) -> QuoteManyReply`
* **Payload:** `QuoteManyData`

## 💬 Just the essentials

* **What it is.** A batch price read — one call returns a `QuoteData` for each requested symbol.
* **Why you need it.** One round-trip for a basket instead of N separate [quote](quote.md) calls.
* **When to use.** Dashboards, multi-symbol strategies, correlation/spread scans.

---

## 🎯 Purpose

* Quotes for a list of symbols at once
* Fewer round-trips than looping [quote](quote.md)
* Same per-symbol fields as a single quote

---

## Method Signature

```java
public QuoteManyData quoteMany(List<String> symbols)
```

**Request message:**

```protobuf
message QuoteManyRequest {
  repeated string symbols = 1;
}
```

**Payload message:**

```protobuf
message QuoteManyData {
  repeated QuoteData quotes = 1;   // one QuoteData per symbol
}

message QuoteData {
  string symbol = 1;
  double bid = 2;
  double ask = 3;
  Timestamp date_time = 4;
  double high = 5;
  double low = 6;
  Timestamp date_time_utc = 7;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `symbols` | `List<String>` | Symbol names, e.g. `List.of("EURUSD", "GBPUSD")`. |

---

## ⬆️ Output — `QuoteManyData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `quotes` | `getQuotesList()` / `getQuotes(i)` / `getQuotesCount()` | One quote per requested symbol |

Each `QuoteData` has `getSymbol()`, `getBid()`, `getAsk()`, `getHigh()`, `getLow()`, `getDateTime()` / `getDateTimeUtc()` — see [quote](quote.md) for the field details.

---

## 🧩 Notes & Tips

* **Prefer this over a loop.** For several symbols, one `quoteMany` beats N `quote` calls — fewer round-trips and a consistent snapshot.
* **Match by `symbol`, not position.** Index the result by `q.getSymbol()` rather than assuming the response order matches your request order.
* **Same shape as a single quote** — spread is `ask − bid`, times are protobuf `Timestamp`s.
* **Big baskets.** Requesting many symbols is fine, but keep the list to what you actually use.

---

## 🔗 Usage Examples

### 1) Quote a basket into a map

Index by symbol so lookups are order-independent.

```java
static Map<String, double[]> quoteBasket(MT4Account account, List<String> symbols) {
    var bySymbol = new HashMap<String, double[]>();
    for (var q : account.quoteMany(symbols).getQuotesList())
        bySymbol.put(q.getSymbol(), new double[]{q.getBid(), q.getAsk()});
    for (var sym : symbols) {
        var ba = bySymbol.get(sym);
        System.out.printf(Locale.ROOT, "%-8s bid=%s ask=%s%n", sym,
                ba == null ? "-" : ba[0], ba == null ? "-" : ba[1]);
    }
    return bySymbol;
}
```

### 2) Rank symbols by spread

Find which instruments are cheapest/most expensive to enter right now.

```java
static void spreads(MT4Account account, List<String> symbols) {
    account.quoteMany(symbols).getQuotesList().stream()
            .map(q -> Map.entry(q.getSymbol(), q.getAsk() - q.getBid()))
            .sorted(Map.Entry.comparingByValue())
            .forEach(e -> System.out.printf(Locale.ROOT, "%-8s spread=%.5f%n", e.getKey(), e.getValue()));
}
```

### 3) Refresh a watchlist snapshot

Pull the whole watchlist at once for a dashboard tick.

```java
static final List<String> WATCH = List.of("EURUSD", "GBPUSD", "USDJPY", "XAUUSD");

static void watchlist(MT4Account account) {
    for (var q : account.quoteMany(WATCH).getQuotesList()) {
        double mid = (q.getBid() + q.getAsk()) / 2;
        System.out.printf(Locale.ROOT, "%-8s %.5f  (bid %.5f / ask %.5f)%n", q.getSymbol(), mid, q.getBid(), q.getAsk());
    }
}
```

---

## ⚠️ Pitfalls

* **Don't assume order.** Map results by `q.getSymbol()`; the response list may not mirror the request order, and unknown symbols may be omitted.
* **Snapshot, not a stream.** For continuous updates use [on_symbol_tick](../5.%20Streaming_Methods/on_symbol_tick.md) with a list of symbols.
* **Spreads differ by symbol.** Compare in pips (via [symbol_params_many](symbol_params_many.md)) when ranking across instruments with different pip sizes.

---

## 🧪 Testing Suggestions

* **Coverage.** Requesting 3 valid symbols returns up to 3 quotes; each `symbol` is one of those requested.
* **Order-independence.** Build the map and assert lookups by name succeed regardless of response order.
* **Parity with single quote.** A symbol's `quoteMany` entry matches a separate [quote](quote.md) call taken at the same moment (within tick noise).

---

## 📚 See also

* [Quote](quote.md)
* [Quote History (OHLC)](quote_history.md)
* [On Symbol Tick (stream)](../5.%20Streaming_Methods/on_symbol_tick.md)

* **Back to:** [Symbols & Quotes — Overview](Symbols_Quotes.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
