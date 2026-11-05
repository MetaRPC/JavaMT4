# 4. Trading Operations — Overview

> Send market/pending orders, modify SL/TP/price, close or delete, and close-by.

## 📁 What lives here

* **[Order Send](order_send.md)** — Send a market or pending order.
* **[Order Modify](order_modify.md)** — Modify an existing order's price, SL, TP or expiration.
* **[Order Close / Delete](order_close_delete.md)** — Close a market order (fully or partially) or delete a pending order.
* **[Order Close By](order_close_by.md)** — Close an order by an opposite order on the same symbol.

All four live on the `mt4_term_api.TradingHelper` gRPC service.

---

## Quick choose

| Method | Returns | What it does |
|--------|---------|--------------|
| `orderSend(…)` | `OrderSendData` | Send a market or pending order. |
| `orderModify(ticket, price, sl, tp, expiration)` | `OrderModifyData` | Modify an existing order's price, SL, TP or expiration. |
| `orderCloseDelete(ticket, lots, closingPrice, slippage)` | `OrderCloseDeleteData` | Close a market order (fully or partially) or delete a pending order. |
| `orderCloseBy(ticketToClose, oppositeTicket)` | `OrderCloseByData` | Close an order by an opposite order on the same symbol. |

---

## ⚠️ Errors are exceptions

MT4 trading calls do not return a numeric result code (that's MT5). On rejection they **throw [`ApiExceptionMT4`](../../All_Guides/ERRORS.md)**; inspect `e.getError().getMqlErrorIntCode()` / `getMqlErrorDescription()`. Always wrap trading calls in `try/catch`.

> For pip-based SL/TP, lot/price normalization and risk sizing, prefer the [MT4Sugar](../../MT4Sugar/MT4Sugar.Master.Overview.md) helpers (`buyMarket`, `buyLimit`, `modifySlTpByPips`, `close`, …) which wrap these low-level calls.

---

* **Back to:** [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
