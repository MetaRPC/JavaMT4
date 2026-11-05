# ENUMs Usage Reference — MT4Account (Java)

> Where each protobuf enum appears across the MT4Account low-level methods. Every enum name, value count and method below is taken from the generated `mt4_term_api` stubs.

---

## ✅ Summary

| Method Group | With ENUMs | Total |
|--------------|------------|-------|
| Account Information | 1 | 5 |
| Orders & History | 2 | 3 |
| Symbols & Quotes | 3 | 8 |
| Trading Operations | 2 | 4 |
| Streaming Methods | 2 | 5 |
| **TOTAL** | **10** | **25** |

> MT4 has a single order list (no positions/deals split as in MT5) and no Depth-of-Market, `order_check` or `order_calc_*` — those are MT5-only.

---

## 👤 1. Account Information

| Method | In | Out |
|--------|----|-----|
| `accountSummary` | — | `EnumAccountTradeMode` (3): DEMO/CONTEST/REAL · `EnumAccountMarginMode` (3) |
| `tradeStats`, `tradeUnrealizedPl`, `equityHistory`, `tradeStatsEquityHistory` | — | — (numeric/nested data) |

## 📋 2. Orders & History

| Method | In | Out |
|--------|----|-----|
| `openedOrders` | `EnumOpenedOrderSortType` (4) | `OpenedOrderType` (8): `OO_OP_*` incl. BALANCE/CREDIT |
| `ordersHistory` | `EnumOrderHistorySortType` (6) | `OpenedOrderType` (8) |
| `openedOrdersTickets` | — | — |

## 📊 3. Symbols & Quotes

| Method | In | Out |
|--------|----|-----|
| `quoteHistory` | `ENUM_QUOTE_HISTORY_TIMEFRAME` (9): `QH_PERIOD_M1…MN1` | — |
| `symbolParamsMany` | — | `SP_ENUM_TRADE_CALC_MODE` (3) · `SP_ENUM_SYMBOL_TRADE_MODE` (5) · `SP_ENUM_SYMBOL_TRADE_EXECUTION` (4) |
| `serverTimezone` | — | `SERVER_TIMEZONE_SOURCE` (3) |
| `symbols`, `quote`, `quoteMany`, `tickValueWithSize`, `symbolSelect` | — | — |

## 💰 4. Trading Operations

| Method | In | Out |
|--------|----|-----|
| `orderSend` | `OrderSendOperationType` (6): `OC_OP_*` | — |
| `orderCloseDelete` | — | `OrderCloseDeleteMode` (2): `OCD_MARKET_ORDER`/`OCD_PENDING_ORDER` |
| `orderModify`, `orderCloseBy` | — | — |

## 📡 5. Streaming Methods

| Method | In | Out |
|--------|----|-----|
| `onTrade` | — | `MT4_SUB_ENUM_EVENT_GROUP_TYPE` (2) · `SUB_ORDER_OPERATION_TYPE` (6) |
| `onOpenedOrdersProfit` | — | `MT4_SUB_ENUM_EVENT_GROUP_TYPE` (2) · `SUB_ORDER_OPERATION_TYPE` (6) |
| `onSymbolTick`, `onOpenedOrdersTickets`, `onConnectState` | — | — |

---

## 🗂 All ENUM types at a glance

| ENUM | Values | Outer class (Java) | Used by |
|------|--------|--------------------|---------|
| `EnumAccountTradeMode` | 3 | `Mt4TermApiAccountHelper` | accountSummary (out) |
| `EnumAccountMarginMode` | 3 | `Mt4TermApiAccountHelper` | accountSummary (out) |
| `EnumOpenedOrderSortType` | 4 | `Mt4TermApiAccountHelper` | openedOrders (in) |
| `EnumOrderHistorySortType` | 6 | `Mt4TermApiAccountHelper` | ordersHistory (in) |
| `OpenedOrderType` | 8 | `Mt4TermApiAccountHelper` | openedOrders, ordersHistory (out) |
| `SP_ENUM_TRADE_CALC_MODE` | 3 | `Mt4TermApiAccountHelper` | symbolParamsMany (out) |
| `SP_ENUM_SYMBOL_TRADE_MODE` | 5 | `Mt4TermApiAccountHelper` | symbolParamsMany (out) |
| `SP_ENUM_SYMBOL_TRADE_EXECUTION` | 4 | `Mt4TermApiAccountHelper` | symbolParamsMany (out) |
| `OrderSendOperationType` | 6 | `Mt4TermApiTradingHelper` | orderSend (in) |
| `OrderCloseDeleteMode` | 2 | `Mt4TermApiTradingHelper` | orderCloseDelete (out) |
| `ENUM_QUOTE_HISTORY_TIMEFRAME` | 9 | `Mt4TermApiMarketInfo` | quoteHistory (in) |
| `SERVER_TIMEZONE_SOURCE` | 3 | `Mt4TermApiServerTimezone` | serverTimezone (out) |
| `MT4_SUB_ENUM_EVENT_GROUP_TYPE` | 2 | `Mt4TermApiSubscriptions` | onTrade, onOpenedOrdersProfit (out) |
| `SUB_ORDER_OPERATION_TYPE` | 6 | `Mt4TermApiSubscriptions` | onTrade, onOpenedOrdersProfit (out) |

---

## Using enums in Java

The generated enums are nested in their outer `Mt4TermApi*` class — reference the constant through it:

```java
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendOperationType;
import mt4_term_api.Mt4TermApiAccountHelper.EnumOpenedOrderSortType;

var op   = OrderSendOperationType.OC_OP_BUY;
var sort = EnumOpenedOrderSortType.SORT_BY_OPEN_TIME_ASC;

// a value's integer: op.getNumber();  parse a name: OrderSendOperationType.valueOf("OC_OP_BUY")
```

[`MT4Service`](../MT4Service/MT4Service.Overview.md) lets you skip the enum entirely for common cases via string mappers (`toOperationType("buy","market")`, `toTimeframe("H1")`, `toOpenedSort("open_desc")`).

---

## Special cases

**1. Order-operation enums come in three flavours.** `OrderSendOperationType` (`OC_OP_*`, to *send* an order), `OpenedOrderType` (`OO_OP_*`, the type reported back on open/history orders) and `SUB_ORDER_OPERATION_TYPE` (`SUB_OP_*`, inside streaming events) describe the same six operations but are **distinct types** — don't mix their value names. `OpenedOrderType` has two extra non-tradable entries, `OO_OP_BALANCE` and `OO_OP_CREDIT`, for balance/credit history records.

**2. Return codes are not enums.** Trade rejections surface as exceptions carrying an integer MQL code, not a typed enum — see the [Return Codes Reference](RETURN_CODES_REFERENCE.md).

---

## See also

* [MT4Account — Master Overview](../MT4Account/MT4Account.Master.Overview.md)
* [Return Codes Reference](RETURN_CODES_REFERENCE.md)
* [Getting Started](GETTING_STARTED.md)
