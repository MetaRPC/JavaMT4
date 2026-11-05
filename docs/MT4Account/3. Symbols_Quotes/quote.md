# Quote

> The latest quote for a single symbol — bid, ask, day high/low and timestamp.

**API Information:**

* **Low-level API:** `MT4Account.quote(String symbol)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.MarketInfo`
* **RPC:** `Quote(QuoteRequest) -> QuoteReply`
* **Payload:** `QuoteData`

## 💬 Just the essentials

* **What it is.** A one-shot price read for one symbol: current `bid`/`ask`, session `high`/`low`, and the quote time.
* **Why you need it.** To read the market before placing or managing an order.
* **When to use.** Single reads. For a continuous feed, use [on_symbol_tick](../5.%20Streaming_Methods/on_symbol_tick.md).

---

## 🎯 Purpose

* Current bid / ask for a symbol
* Session high / low
* Quote timestamp (server / UTC)
* Compute spread and check tradability

---

## Method Signature

```java
public QuoteData quote(String symbol)
```

**Request message:**

```protobuf
message QuoteRequest {
  string symbol = 1;
}
```

**Payload message:**

```protobuf
message QuoteData {
  string symbol = 1;
  double bid = 2;
  double ask = 3;
  Timestamp date_time = 4;
  double high = 5;      // session high
  double low = 6;       // session low
  Timestamp date_time_utc = 7;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `symbol` | `String` | Symbol name, e.g. `"EURUSD"`. |

---

## ⬆️ Output — `QuoteData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `symbol` | `getSymbol()` | Symbol |
| `bid` | `getBid()` | Current bid (you sell at bid) |
| `ask` | `getAsk()` | Current ask (you buy at ask) |
| `high` / `low` | `getHigh()` / `getLow()` | Session high / low |
| `date_time` / `date_time_utc` | `getDateTime()` / `getDateTimeUtc()` | Quote time (server / UTC) |

---

## 🧩 Notes & Tips

* **Spread = `ask − bid`.** In price terms; divide by the symbol's pip size to express it in pips.
* **Buy at ask, sell at bid.** A market buy fills near `ask`, a market sell near `bid` — the spread is the immediate cost of entry.
* **Time field is `date_time`** (not `time`) → `getDateTime()`. It's a protobuf `Timestamp` — convert with `Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos())`.
* **One symbol per call.** For several symbols use [quote_many](quote_many.md); for a live stream use [on_symbol_tick](../5.%20Streaming_Methods/on_symbol_tick.md).
* **Market Watch.** If a broker returns no price, the symbol may need selecting first — see [symbol_select](symbol_select.md).

---

## 🔗 Usage Examples

### 1) Bid / ask / spread

```java
static QuoteData showQuote(MT4Account account, String symbol) {
    var q = account.quote(symbol);
    double spread = q.getAsk() - q.getBid();
    System.out.printf(Locale.ROOT, "%s: bid=%.5f ask=%.5f spread=%.5f%n", q.getSymbol(), q.getBid(), q.getAsk(), spread);
    return q;
}
```

### 2) Only trade when the spread is tight

A guard you call before entering — skip entries when the spread is abnormally wide (news, illiquid hours).

```java
static boolean spreadOk(MT4Account account, String symbol, double maxSpread) {
    var q = account.quote(symbol);
    double spread = q.getAsk() - q.getBid();
    if (spread > maxSpread) {
        System.out.printf(Locale.ROOT, "%s: spread %.5f > %.5f — skip entry%n", symbol, spread, maxSpread);
        return false;
    }
    return true;
}
```

### 3) Where is price in today's range?

Position the current mid inside the session high/low (0% = low, 100% = high).

```java
static double rangePosition(MT4Account account, String symbol) {
    var q = account.quote(symbol);
    double mid = (q.getBid() + q.getAsk()) / 2;
    double span = q.getHigh() - q.getLow();
    double pct = span > 0 ? (mid - q.getLow()) / span * 100 : 0.0;
    System.out.printf(Locale.ROOT, "%s: mid=%.5f in [%.5f, %.5f] -> %.0f%% of range%n",
            symbol, mid, q.getLow(), q.getHigh(), pct);
    return pct;
}
```

---

## ⚠️ Pitfalls

* **Snapshot, not a stream.** The quote is the price at call time; don't poll it in a tight loop — use [on_symbol_tick](../5.%20Streaming_Methods/on_symbol_tick.md) for live updates.
* **Spread is in price units.** Convert to pips with the symbol's pip size (see [symbol_params_many](symbol_params_many.md)) before comparing across symbols.
* **Unselected symbols.** Some brokers return an error/empty price until the symbol is in Market Watch.

---

## 🧪 Testing Suggestions

* **Happy path.** `ask >= bid`, both positive; `low <= bid <= high` (roughly) during the session.
* **Unknown symbol.** A bogus name surfaces an `ApiExceptionMT4`, not a silent zero.
* **Spread gate.** `spreadOk()` returns false for a tiny `maxSpread` and true for a generous one.

---

## 📚 See also

* [Many Quotes](quote_many.md)
* [Quote History (OHLC)](quote_history.md)
* [On Symbol Tick (stream)](../5.%20Streaming_Methods/on_symbol_tick.md)

* **Back to:** [Symbols & Quotes — Overview](Symbols_Quotes.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
