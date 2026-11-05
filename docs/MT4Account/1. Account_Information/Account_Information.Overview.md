# 1. Account Information — Overview

> The account's money picture and its performance history: a one-call snapshot, aggregate trade statistics, the equity curve, and unrealized-P/L extrema.

These five methods are **read-only** — they never touch orders. They are the first calls most bots make: a snapshot at startup, then statistics/equity for dashboards and risk checks.

All of them live on the `mt4_term_api.AccountHelper` and `mt4_term_api.TradeStats` gRPC services and return a typed `…Data` payload.

---

## Methods in this group

| Method | Returns | One-liner |
|--------|---------|-----------|
| [`accountSummary()`](./account_summary.md) | `AccountSummaryData` | Balance, equity, margin, leverage, currency, server time — one round-trip. |
| [`tradeStats(from)`](./trade_stats.md) | `StatsData` | Aggregate performance: win rate, profit factor, drawdowns, Sharpe, expectancy… |
| [`tradeUnrealizedPl(from)`](./trade_unrealized_pl.md) | `TradeUnrealizedPLData` | Per-order max/min floating-P/L extrema (MFE / MAE). |
| [`equityHistory(from, excludeSameBars)`](./equity_history.md) | `EquityHistoryData` | The equity/balance curve as time-stamped points. |
| [`tradeStatsEquityHistory(from, excludeSameBars)`](./trade_stats_equity_history.md) | `StatsWithChartsData` | Stats + equity chart + closed trades, all in one call. |

---

## When to use which

* **Just connected / banner / sanity check** → `accountSummary()`.
* **Pre-trade risk gate** (free margin, margin level, trading allowed) → `accountSummary()`.
* **Performance dashboard** (win rate, profit factor, drawdown) → `tradeStats()`, or `tradeStatsEquityHistory()` when you also want the curve and the trade list.
* **Equity/drawdown chart** → `equityHistory()`.
* **Trade-quality analysis** (how far each trade ran in your favour/against you) → `tradeUnrealizedPl()`.

---

## Shared argument — `from`

`tradeStats`, `tradeUnrealizedPl`, `equityHistory` and `tradeStatsEquityHistory` all take an optional `from` of type `java.time.Instant`:

* `null` → the server's default window (usually the whole account history).
* a value → only data at or after that instant.

In Java it is sent to the server as an ISO-8601 string (`from.toString()`), so pass a UTC `Instant` (e.g. `Instant.now().minus(30, ChronoUnit.DAYS)`).

---

**Back to:** [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
