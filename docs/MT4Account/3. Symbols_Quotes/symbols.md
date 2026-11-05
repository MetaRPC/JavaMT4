# Symbols

> The full list of symbols available on the account — names and their index.

**API Information:**

* **Low-level API:** `MT4Account.symbols()` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.MarketInfo`
* **RPC:** `Symbols(SymbolsRequest) -> SymbolsReply`
* **Payload:** `SymbolsData`

## 💬 Just the essentials

* **What it is.** Every instrument the broker exposes on this account, as `(name, index)` pairs.
* **Why you need it.** To discover tradable symbols at runtime and validate user input.
* **When to use.** On startup, when building symbol pickers, or before quoting/trading an unknown name.

---

## 🎯 Purpose

* Full symbol list from the broker
* Validate a symbol name before using it
* Build lookups (name → index) for other calls

---

## Method Signature

```java
public SymbolsData symbols()
```

No arguments.

**Request message:**

```protobuf
message SymbolsRequest {
}
```

**Payload message:**

```protobuf
message SymbolsData {
  repeated SymbolNameInfo SymbolNameInfos = 1;
}

message SymbolNameInfo {
  string symbol_name = 1;
  int32 symbol_index = 2;
}
```

---

## 🔽 Input

None.

---

## ⬆️ Output — `SymbolsData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `SymbolNameInfos` | `getSymbolNameInfosList()` / `getSymbolNameInfos(i)` / `getSymbolNameInfosCount()` | All available symbols |

Each `SymbolNameInfo`:

| Field | Java getter | Description |
|-------|-------------|-------------|
| `symbol_name` | `getSymbolName()` | Symbol name (e.g. `"EURUSD"`) |
| `symbol_index` | `getSymbolIndex()` | Broker-assigned index |

---

## 🧩 Notes & Tips

* **Field name.** The repeated proto field is `SymbolNameInfos` (PascalCase); the Java getters are the usual `getSymbolNameInfos…`.
* **Names are the broker's.** Symbols may carry suffixes (`EURUSD.m`, `EURUSD-ECN`) — never assume the bare `"EURUSD"` exists; check the list.
* **This is the catalog, not the rules.** For lot limits, digits and trade mode, use [symbol_params_many](symbol_params_many.md); for prices, use [quote](quote.md).
* **Market Watch.** Some brokers require a symbol be selected before quoting it — see [symbol_select](symbol_select.md).

---

## 🔗 Usage Examples

### 1) List and count

```java
static List<String> listSymbols(MT4Account account) {
    var names = account.symbols().getSymbolNameInfosList().stream()
            .map(s -> s.getSymbolName()).toList();
    System.out.printf("%d symbols, e.g. %s%n", names.size(), names.subList(0, Math.min(8, names.size())));
    return names;
}
```

### 2) Does a symbol exist (allowing suffixes)?

Validate a desired symbol, tolerating broker suffixes.

```java
static String resolveSymbol(MT4Account account, String wanted) {
    var names = account.symbols().getSymbolNameInfosList().stream().map(s -> s.getSymbolName()).toList();
    if (names.contains(wanted)) return wanted;
    var match = names.stream().filter(n -> n.startsWith(wanted)).findFirst();
    if (match.isPresent()) {
        System.out.printf("'%s' not found; using '%s'%n", wanted, match.get());
        return match.get();
    }
    throw new IllegalArgumentException("no symbol matching " + wanted);
}
```

### 3) Name → index map

Build a lookup you can reuse elsewhere.

```java
static Map<String, Integer> symbolIndexMap(MT4Account account) {
    var map = new HashMap<String, Integer>();
    for (var s : account.symbols().getSymbolNameInfosList()) map.put(s.getSymbolName(), s.getSymbolIndex());
    return map;
}
```

---

## ⚠️ Pitfalls

* **Suffixes.** Don't hard-code `"EURUSD"`; resolve against the real list to survive brokers that use suffixes.
* **Catalog only.** No prices or trading rules here — pair with [quote](quote.md) / [symbol_params_many](symbol_params_many.md).
* **Large list.** Brokers expose hundreds of symbols; filter to what you need rather than printing everything.

---

## 🧪 Testing Suggestions

* **Non-empty.** On a connected demo account, the list has many entries and names are unique.
* **Resolution.** `resolveSymbol("EURUSD")` returns a real name present in the list.
* **Map integrity.** The name→index map has the same length as the symbol list (no duplicate names).

---

## 📚 See also

* [Quote](quote.md)
* [Symbol Parameters](symbol_params_many.md)
* [Symbol Select (Market Watch)](symbol_select.md)

* **Back to:** [Symbols & Quotes — Overview](Symbols_Quotes.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
