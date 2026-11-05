# Tick Value & Size

> Tick value, tick size and contract size per symbol — the inputs for P/L and risk-based position sizing.

**API Information:**

* **Low-level API:** `MT4Account.tickValueWithSize(List<String> symbolNames)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.AccountHelper`
* **RPC:** `TickValueWithSize(TickValueWithSizeRequest) -> TickValueWithSizeReply`
* **Payload:** `TickValueWithSizeData`

## 💬 Just the essentials

* **What it is.** For each requested symbol: the money value of one tick, the price size of one tick, and the contract size (units per lot).
* **Why you need it.** To turn pips into money — money-per-pip, P/L estimates, and risk-based lot sizing.
* **When to use.** When computing position size from a dollar/percent risk, or valuing a move.

---

## 🎯 Purpose

* Tick value (money per tick for 1 lot)
* Tick size (price change of one tick)
* Contract size (units per lot)
* Lightweight — just the numbers needed for sizing

---

## Method Signature

```java
public TickValueWithSizeData tickValueWithSize(List<String> symbolNames)
```

**Request message:**

```protobuf
message TickValueWithSizeRequest {
  repeated string symbol_names = 1;
}
```

**Payload message:**

```protobuf
message TickValueWithSizeData {
  repeated SymbolTickValueWithSizeInfo infos = 1;
}

message SymbolTickValueWithSizeInfo {
  int32 index = 1;
  string symbolName = 2;
  double TradeTickValue = 3;      // money per tick, 1 lot
  double TradeTickSize = 4;       // price change per tick
  double TradeContractSize = 5;   // units per lot
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `symbolNames` | `List<String>` | Symbols to query, e.g. `List.of("EURUSD", "XAUUSD")`. |

---

## ⬆️ Output — `TickValueWithSizeData`

`getInfosList()` / `getInfos(i)` / `getInfosCount()` — a list of `SymbolTickValueWithSizeInfo`:

| Field | Java getter | Description |
|-------|-------------|-------------|
| `symbolName` | `getSymbolName()` | Symbol (note camelCase) |
| `TradeTickValue` | `getTradeTickValue()` | Money value of one tick, for 1 lot (in deposit currency) |
| `TradeTickSize` | `getTradeTickSize()` | Price change of one tick |
| `TradeContractSize` | `getTradeContractSize()` | Units per lot |
| `index` | `getIndex()` | Position in the request |

---

## 🧩 Notes & Tips

* **This is the subset of [symbol_params_many](symbol_params_many.md)** needed for sizing — use this when you only need the tick/contract numbers for several symbols at once.
* **Money per pip** = `getTradeTickValue() × (pipSize / getTradeTickSize())`, where `pipSize` is 10 × point on 5/3-digit FX (get `Point`/`Digits` from [symbol_params_many](symbol_params_many.md)).
* **Values are per 1 lot** in the account's deposit currency — multiply by your lot size.
* **MT4Sugar builds on this** for `calcLotByRisk` / `calcCashRisk`.

---

## 🔗 Usage Examples

### 1) Tick value for a basket

```java
static Map<String, SymbolTickValueWithSizeInfo> tickValues(MT4Account account, List<String> symbols) {
    var out = new LinkedHashMap<String, SymbolTickValueWithSizeInfo>();
    for (var i : account.tickValueWithSize(symbols).getInfosList()) {
        out.put(i.getSymbolName(), i);
        System.out.printf(Locale.ROOT, "%-8s tick_value=%.5f tick_size=%.5f contract=%.0f%n",
                i.getSymbolName(), i.getTradeTickValue(), i.getTradeTickSize(), i.getTradeContractSize());
    }
    return out;
}
```

### 2) Money per pip

Convert tick value to per-pip value (needs the symbol's pip size).

```java
static double moneyPerPip(MT4Account account, String symbol, double lots) {
    var tv = account.tickValueWithSize(List.of(symbol)).getInfos(0);
    var p = account.symbolParamsMany(symbol).getSymbolInfos(0);
    double pipSize = p.getPoint() * ((p.getDigits() == 3 || p.getDigits() == 5) ? 10 : 1);
    double value = tv.getTradeTickValue() * (pipSize / tv.getTradeTickSize()) * lots;
    System.out.printf(Locale.ROOT, "%s: %.2f per pip for %s lot%n", symbol, value, lots);
    return value;
}
```

### 3) Risk-based lot size

Size a position so a stop-loss hit costs a fixed amount.

```java
static double lotForRisk(MT4Account account, String symbol, double riskAmount, double slPips) {
    double mpp = moneyPerPip(account, symbol, 1.0);   // per-pip for 1 lot
    if (mpp <= 0 || slPips <= 0) return 0.0;
    double raw = riskAmount / (slPips * mpp);
    var p = account.symbolParamsMany(symbol).getSymbolInfos(0);
    double lots = Math.max(p.getVolumeMin(), Math.min(p.getVolumeMax(),
            Math.round(raw / p.getVolumeStep()) * p.getVolumeStep()));
    System.out.printf(Locale.ROOT, "risk %.2f @ %s pip SL -> %.2f lot%n", riskAmount, slPips, lots);
    return Math.round(lots * 100) / 100.0;
}
```

---

## ⚠️ Pitfalls

* **Mixed-case fields.** Proto `symbolName` (camelCase) but `TradeTickValue` (PascalCase); the Java getters normalize both to `getSymbolName()` / `getTradeTickValue()`.
* **Per 1 lot.** Remember to scale by lot size; the raw values assume one lot.
* **Pip vs tick.** Tick ≠ pip. Convert via the symbol's pip size (`Point`/`Digits` from [symbol_params_many](symbol_params_many.md)).
* **Deposit currency.** `TradeTickValue` is already in your account currency — no FX conversion needed.

---

## 🧪 Testing Suggestions

* **Known symbol.** For EURUSD on a USD account, `TradeTickValue` ≈ 1.0 for 1 lot at 0.00001 tick size.
* **Batch.** Requesting 3 symbols returns up to 3 `infos`; match by `symbolName`.
* **Sizing sanity.** `lotForRisk(risk=50, slPips=50)` on EURUSD returns ~0.10 lot (≈ $1/pip) — adjust for the actual tick value.

---

## 📚 See also

* [Symbol Parameters](symbol_params_many.md)
* [Symbols](symbols.md)
* [Account Summary](../1.%20Account_Information/account_summary.md)

* **Back to:** [Symbols & Quotes — Overview](Symbols_Quotes.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
