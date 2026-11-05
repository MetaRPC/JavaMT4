# 3. Symbols & Quotes — Overview

> Symbol list, live quotes, OHLC bar history, per-symbol parameters, tick value, Market Watch select and server timezone.

## 📁 What lives here

* **[Symbols](symbols.md)** — List all symbols available on the account.
* **[Quote](quote.md)** — Get the latest quote (bid/ask) for a single symbol.
* **[Many Quotes](quote_many.md)** — Get latest quotes for several symbols in one call.
* **[Quote History (OHLC)](quote_history.md)** — Get OHLC bar history for a symbol and timeframe over a time range.
* **[Symbol Parameters](symbol_params_many.md)** — Get detailed trading parameters for one or many symbols.
* **[Tick Value & Size](tick_value_with_size.md)** — Get tick value, tick size and contract size for symbols (for P/L and risk math).
* **[Symbol Select (Market Watch)](symbol_select.md)** — Add a symbol to, or remove it from, Market Watch.
* **[Server Timezone](server_timezone.md)** — Get the trade-server timezone and UTC offset.

These live on the `mt4_term_api.MarketInfo` service (`symbols`, `quote`, `quoteMany`, `quoteHistory`, `symbolSelect`), `mt4_term_api.AccountHelper` (`symbolParamsMany`, `tickValueWithSize`) and `mt4_term_api.ServerTimezone` (`serverTimezone`).

---

## Quick choose

| Method | Returns | What it does |
|--------|---------|--------------|
| `symbols()` | `SymbolsData` | List all symbols available on the account. |
| `quote(symbol)` | `QuoteData` | Latest quote (bid/ask) for a single symbol. |
| `quoteMany(symbols)` | `QuoteManyData` | Latest quotes for several symbols in one call. |
| `quoteHistory(symbol, timeframe, from, to)` | `QuoteHistoryData` | OHLC bar history for a symbol/timeframe over a range. |
| `symbolParamsMany(symbolName)` | `SymbolParamsManyData` | Detailed trading parameters for one or many symbols. |
| `tickValueWithSize(symbolNames)` | `TickValueWithSizeData` | Tick value, tick size and contract size (P/L & risk math). |
| `symbolSelect(symbol, select)` | `SymbolSelectData` | Add/remove a symbol in Market Watch. |
| `serverTimezone()` | `ServerTimezoneData` | Trade-server timezone and UTC offset. |

---

* **Back to:** [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
