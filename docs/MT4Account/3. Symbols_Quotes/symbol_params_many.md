# Symbol Parameters

> The per-symbol rulebook: digits, lot limits, stops level, tick value, trade mode and more — for one symbol or all of them.

**API Information:**

* **Low-level API:** `MT4Account.symbolParamsMany(String symbolName)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.AccountHelper`
* **RPC:** `SymbolParamsMany(SymbolParamsManyRequest) -> SymbolParamsManyReply`
* **Payload:** `SymbolParamsManyData`

## 💬 Just the essentials

* **What it is.** The trading specification for symbols — the numbers you need to size, round and validate orders.
* **Why you need it.** To normalize volume/price, respect the broker's minimum stop distance, and compute money-per-pip.
* **When to use.** Before sizing or sending orders; cache the result and refresh occasionally.

---

## 🎯 Purpose

* Volume min / max / step / limit
* Digits and point size (precision)
* Stops & freeze levels (minimum SL/TP distance)
* Tick value / tick size / contract size (P/L math)
* Trade mode, execution mode, margin calc mode
* Swaps, margins, base/profit/margin currencies

---

## Method Signature

```java
public SymbolParamsManyData symbolParamsMany(String symbolName)
```

Pass a single symbol name; pass `null` (sent as empty) to fetch **all** symbols.

**Request message:**

```protobuf
message SymbolParamsManyRequest {
  string symbol_name = 1;   // one symbol; omit/empty for all
}
```

**Payload message:**

```protobuf
message SymbolParamsManyData {
  repeated SymbolParamsManyInfo symbol_infos = 1;
}
```

`SymbolParamsManyInfo` has ~75 fields; the trading-relevant ones are below.

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `symbolName` | `String`, optional | Restrict to one symbol; pass `null`/empty for all symbols. |

---

## ⬆️ Output — `SymbolParamsManyData`

`getSymbolInfosList()` / `getSymbolInfos(i)` / `getSymbolInfosCount()` — a list of `SymbolParamsManyInfo`. Key getters:

**Identity & precision**

| Field | Java getter | Description |
|-------|-------------|-------------|
| `SymbolName` | `getSymbolName()` | Symbol name |
| `Digits` | `getDigits()` | Price decimal places |
| `Point` | `getPoint()` | Point size (e.g. 0.00001) |
| `SymDescription` | `getSymDescription()` | Human description |
| `Path` | `getPath()` | Market Watch path/category |

**Volume (lots)**

| Field | Java getter | Description |
|-------|-------------|-------------|
| `VolumeMin` / `VolumeMax` | `getVolumeMin()` / `getVolumeMax()` | Min / max lots per order |
| `VolumeStep` | `getVolumeStep()` | Lot increment |
| `VolumeLimit` | `getVolumeLimit()` | Max total volume per symbol (0 = none) |

**Trade rules**

| Field | Java getter | Description |
|-------|-------------|-------------|
| `TradeStopsLevel` | `getTradeStopsLevel()` | Min distance (points) for SL/TP from price |
| `TradeFreezeLevel` | `getTradeFreezeLevel()` | Freeze distance (points) near price |
| `TradeMode` | `getTradeMode()` | What's allowed (`SP_ENUM_SYMBOL_TRADE_MODE`) |
| `TradeExeMode` | `getTradeExeMode()` | Execution model (`SP_ENUM_SYMBOL_TRADE_EXECUTION`) |
| `TradeCalcMode` | `getTradeCalcMode()` | Margin calc mode (`SP_ENUM_TRADE_CALC_MODE`) |

**P/L & cost math**

| Field | Java getter | Description |
|-------|-------------|-------------|
| `TradeTickValue` | `getTradeTickValue()` | Money per tick for 1 lot |
| `TradeTickSize` | `getTradeTickSize()` | Price change of one tick |
| `TradeContractSize` | `getTradeContractSize()` | Units per lot |
| `SwapLong` / `SwapShort` | `getSwapLong()` / `getSwapShort()` | Swap rates |
| `MarginInitial` | `getMarginInitial()` | Initial margin per lot |
| `CurrencyBase` / `CurrencyProfit` / `CurrencyMargin` | `getCurrencyBase()` / `getCurrencyProfit()` / `getCurrencyMargin()` | Symbol currencies |

Prices (`getBid()`, `getBidHigh()`, `getBidLow()`, `getAskHigh()`, `getAskLow()`, `getSessionOpen()`, `getSessionClose()`, …) are also present for convenience.

---

## 🧱 Related enums (from proto)

### `SP_ENUM_SYMBOL_TRADE_MODE` — field `TradeMode`

| Constant | Value | Meaning |
|----------|-------|---------|
| `SYMBOL_TRADE_MODE_DISABLED` | 0 | Trading disabled |
| `SYMBOL_TRADE_MODE_LONGONLY` | 1 | Longs only |
| `SYMBOL_TRADE_MODE_SHORTONLY` | 2 | Shorts only |
| `SYMBOL_TRADE_MODE_CLOSEONLY` | 3 | Close only |
| `SYMBOL_TRADE_MODE_FULL` | 4 | Full access |

### `SP_ENUM_SYMBOL_TRADE_EXECUTION` — field `TradeExeMode`

| Constant | Value | Meaning |
|----------|-------|---------|
| `SYMBOL_TRADE_EXECUTION_REQUEST` | 0 | Request execution |
| `SYMBOL_TRADE_EXECUTION_INSTANT` | 1 | Instant execution |
| `SYMBOL_TRADE_EXECUTION_MARKET` | 2 | Market execution |
| `SYMBOL_TRADE_EXECUTION_EXCHANGE` | 3 | Exchange execution |

### `SP_ENUM_TRADE_CALC_MODE` — field `TradeCalcMode`

| Constant | Value | Meaning |
|----------|-------|---------|
| `SYMBOL_TRADE_MARGINE_CALC_MODE_FOREX` | 0 | Forex margin calc |
| `SYMBOL_TRADE_MARGINE_CALC_MODE_FUTURES` | 1 | Futures margin calc |
| `SYMBOL_TRADE_MARGINE_CALC_MODE_CFD` | 2 | CFD margin calc |

> Access via `Mt4TermApiAccountHelper.SP_ENUM_SYMBOL_TRADE_MODE.SYMBOL_TRADE_MODE_FULL`, etc.

---

## 🧩 Notes & Tips

* **Stops level is in points, not pips.** `TradeStopsLevel` is the minimum SL/TP distance in points — convert with `Point` (and remember 1 pip = 10 points on 5-digit symbols).
* **This is the source for normalization.** `VolumeMin/Max/Step` round lots; `Digits`/`Point` round prices. MT4Sugar's `normalizeLot` / `normalizePrice` are built on these.
* **Request one or all.** Pass `symbolName` for a single symbol; pass `null` to fetch the whole table (can be large).
* **Cache it.** These rarely change intraday — fetch once and reuse, refreshing occasionally.

---

## 🔗 Usage Examples

### 1) Normalize a lot to the broker's rules

Round any desired volume into the allowed `[min, max]` range on the correct step.

```java
static double normalizeLot(MT4Account account, String symbol, double lots) {
    var info = account.symbolParamsMany(symbol).getSymbolInfos(0);
    double step = info.getVolumeStep(), lo = info.getVolumeMin(), hi = info.getVolumeMax();
    lots = Math.max(lo, Math.min(hi, lots));
    double steps = Math.round(lots / step);
    return Math.round(steps * step * 1e8) / 1e8;
}
// normalizeLot(account, "EURUSD", 0.0234) -> 0.02
```

### 2) Is my SL far enough from price?

Validate a stop distance against `TradeStopsLevel` before sending an order.

```java
static boolean slDistanceOk(MT4Account account, String symbol, double slDistancePrice) {
    var info = account.symbolParamsMany(symbol).getSymbolInfos(0);
    double minDistance = info.getTradeStopsLevel() * info.getPoint();
    if (slDistancePrice < minDistance) {
        System.out.printf(Locale.ROOT, "SL too close: %.5f < min %.5f%n", slDistancePrice, minDistance);
        return false;
    }
    return true;
}
```

### 3) Money per pip for a lot

Compute the P/L value of one pip for sizing by risk.

```java
static double moneyPerPip(MT4Account account, String symbol, double lots) {
    var info = account.symbolParamsMany(symbol).getSymbolInfos(0);
    int pipPoints = (info.getDigits() == 3 || info.getDigits() == 5) ? 10 : 1;
    double value = info.getTradeTickValue() * (pipPoints * info.getPoint() / info.getTradeTickSize()) * lots;
    System.out.printf(Locale.ROOT, "%s: ~%.2f %s per pip for %s lot%n", symbol, value, info.getCurrencyProfit(), lots);
    return value;
}
```

---

## ⚠️ Pitfalls

* **Points vs pips.** `TradeStopsLevel`, `TradeFreezeLevel` and `Point` are in points; convert to pips before comparing to pip-based SL/TP.
* **`TradeStopsLevel` may be 0.** Some brokers report 0 (no minimum) — your SL/TP can then sit anywhere, but still normalize to `Digits`.
* **Large payload for "all".** Passing `null` returns every symbol — request one when you only need one.

---

## 🧪 Testing Suggestions

* **Single symbol.** `symbolParamsMany("EURUSD").getSymbolInfos(0)` has `getSymbolName() == "EURUSD"`.
* **Normalization.** `normalizeLot(..., 0.0234)` returns a multiple of `VolumeStep` within `[VolumeMin, VolumeMax]`.
* **All symbols.** Passing `null` returns many entries; each has positive `Digits` and `VolumeStep`.

---

## 📚 See also

* [Tick Value & Size](tick_value_with_size.md)
* [Symbols](symbols.md)
* [Quote](quote.md)

* **Back to:** [Symbols & Quotes — Overview](Symbols_Quotes.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
