# 2. Orders & History — Overview

> Currently open orders (market + pending), their ticket lists, and closed-order history.

## 📁 What lives here

* **[Opened Orders](opened_orders.md)** — List all currently open orders (market + pending) with a chosen sort order.
* **[Opened Order Tickets](opened_orders_tickets.md)** — Get just the ticket numbers of currently open orders (lightweight).
* **[Orders History](orders_history.md)** — Get closed/historical orders over a time range, with paging and a sort mode.

All three live on the `mt4_term_api.AccountHelper` gRPC service and return a typed `…Data` payload.

---

## Quick choose

| Method | Returns | What it does |
|--------|---------|--------------|
| `openedOrders(sortType)` | `OpenedOrdersData` | List all currently open orders (market + pending) with a chosen sort order. |
| `openedOrdersTickets()` | `OpenedOrdersTicketsData` | Get just the ticket numbers of currently open orders (lightweight). |
| `ordersHistory(sortMode, from, to, pageNumber, itemsPerPage)` | `OrdersHistoryData` | Get closed/historical orders over a time range, with paging and a sort mode. |

---

## Note — one order list

MT4 keeps a **single order list**: market positions and resting pending orders live together, distinguished by their `OpenedOrderType` (`OO_OP_*`). There is no positions/deals split as in MT5. Closed orders move to history, read via `ordersHistory`.

---

* **Back to:** [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
