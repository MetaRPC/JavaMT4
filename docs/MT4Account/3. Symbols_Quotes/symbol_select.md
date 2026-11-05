# Symbol Select (Market Watch)

> Add a symbol to, or remove it from, Market Watch.

**API Information:**

* **Low-level API:** `MT4Account.symbolSelect(String symbol, boolean select)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.MarketInfo`
* **RPC:** `SymbolSelect(SymbolSelectRequest) -> SymbolSelectReply`
* **Payload:** `SymbolSelectData`

## 💬 Just the essentials

* **What it is.** Toggle a symbol in the terminal's Market Watch (select = add, deselect = remove).
* **Why you need it.** Some brokers won't quote or trade a symbol until it's selected.
* **When to use.** Before quoting/trading a symbol that might not be in Market Watch yet.

---

## 🎯 Purpose

* Add a symbol to Market Watch
* Remove a symbol from Market Watch
* Confirm the operation via `success`

---

## Method Signature

```java
public SymbolSelectData symbolSelect(String symbol, boolean select)
```

**Request message:**

```protobuf
message SymbolSelectRequest {
  string symbol = 1;
  bool select = 2;     // true = add to Market Watch, false = remove
}
```

**Payload message:**

```protobuf
message SymbolSelectData {
  bool success = 1;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `symbol` | `String` | Symbol name. |
| `select` | `boolean` | `true` to add to Market Watch, `false` to remove. |

---

## ⬆️ Output — `SymbolSelectData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `success` | `getSuccess()` | True if the select/deselect was applied |

---

## 🧩 Notes & Tips

* **Select before you quote/trade** symbols that aren't in Market Watch — some brokers reject data/orders for unselected symbols.
* **Deselect to tidy up** (`select = false`), but don't remove a symbol that still has open orders or active subscriptions.
* **Idempotent-ish.** Selecting an already-selected symbol just returns `success = true`.
* **Validate the name first** with [symbols](symbols.md) — selecting a non-existent symbol fails.

---

## 🔗 Usage Examples

### 1) Ensure a symbol is ready to use

A helper to call before quoting/trading an unfamiliar symbol.

```java
static boolean ensureSelected(MT4Account account, String symbol) {
    boolean ok = account.symbolSelect(symbol, true).getSuccess();
    if (!ok) System.out.println("could not select " + symbol);
    return ok;
}

// usage:
// if (ensureSelected(account, "XAUUSD")) { var q = account.quote("XAUUSD"); }
```

### 2) Add then remove

```java
static void tempSelect(MT4Account account, String symbol) {
    account.symbolSelect(symbol, true);
    var q = account.quote(symbol);
    System.out.printf(Locale.ROOT, "%s: bid=%.5f ask=%.5f%n", symbol, q.getBid(), q.getAsk());
    account.symbolSelect(symbol, false);   // tidy up
}
```

### 3) Prepare a watchlist

Select everything a strategy needs up front.

```java
static Map<String, Boolean> prepareWatchlist(MT4Account account, List<String> symbols) {
    var ok = new LinkedHashMap<String, Boolean>();
    for (var s : symbols) ok.put(s, account.symbolSelect(s, true).getSuccess());
    var missing = ok.entrySet().stream().filter(e -> !e.getValue()).map(Map.Entry::getKey).toList();
    if (!missing.isEmpty()) System.out.println("failed to select: " + missing);
    return ok;
}
```

---

## ⚠️ Pitfalls

* **Invalid symbol.** Selecting a name that isn't on the account fails (or raises) — resolve it against [symbols](symbols.md) first.
* **Don't deselect active symbols.** Removing a symbol with open orders or live streams can disrupt them.
* **Selection ≠ data guarantee.** `success = true` means it's in Market Watch; the first quote may still lag a moment while it populates.

---

## 🧪 Testing Suggestions

* **Select a valid symbol.** Returns `success = true`; a following [quote](quote.md) works.
* **Deselect.** `select = false` returns `success = true` for a selected symbol.
* **Invalid name.** A bogus symbol returns `success = false` or raises `ApiExceptionMT4`.

---

## 📚 See also

* [Symbols](symbols.md)
* [Quote](quote.md)
* [Symbol Parameters](symbol_params_many.md)

* **Back to:** [Symbols & Quotes — Overview](Symbols_Quotes.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
