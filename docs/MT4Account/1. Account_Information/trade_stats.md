# Trade Statistics

> Aggregated trade statistics — profit factor, win/loss, drawdowns, expectancy and a per-market breakdown, in one call.

**API Information:**

* **Low-level API:** `MT4Account.tradeStats(Instant from)` (defined in [`src/main/java/pro/mrpc/mt4/MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.TradeStats`
* **RPC:** `TradeStats(TradeStatsRequest) -> TradeStatsReply`
* **Payload:** `StatsData`

## 💬 Just the essentials

* **What it is.** A rollup of your trading performance: headline ratios, drawdowns, and a breakdown per market.
* **Why you need it.** The numbers a dashboard or weekly review needs — without computing them yourself from raw history.
* **When to use.** Reporting, periodic performance checks, "how am I doing?" screens.

---

## 🎯 Purpose

* Profit factor, expectancy, Sharpe ratio, GHPR, Z-score
* Win/loss counts and percentages
* Balance & equity drawdowns (absolute and relative)
* Per-market breakdown (trades, lots, profit, win rate)
* Account totals: deposits, withdrawals, growth %

---

## Method Signature

```java
public StatsData tradeStats(Instant from)
```

`from` is an optional UTC `java.time.Instant` (pass `null` for full history); it is sent as an ISO-8601 string.

**Request message:**

```protobuf
message TradeStatsRequest {
  string from = 1;       // ISO-8601 start; empty = full history
  string from_utc = 2;   // ISO-8601 start in UTC
}
```

**Payload message (top level, field numbers per our stub):**

```protobuf
message StatsData {
  TradeSummaryData summary = 1;              // open/day/week/month/total profit
  double max_balance_drawdown_raw = 2;
  double max_balance_drawdown_relative = 3;  // percent
  double max_equity_drawdown_raw = 4;
  double max_equity_drawdown_relative = 5;   // percent
  repeated MarketTradeCountData markets = 6; // per-symbol breakdown
  ProfitabilityData profitability = 7;       // won/lost counts & percentages
  double pips = 8;
  double lots = 9;
  double comissions = 10;
  AveragePipsUsdData average_win = 11;
  AveragePipsUsdData average_lost = 12;
  WonData longs_won = 13;
  WonData shorts_won = 14;
  ProfitData best_trade = 15;
  ProfitData worst_trade = 16;
  ProfitData best_trade_pips = 17;
  ProfitData worst_trade_pips = 18;
  string average_trade_length = 19;
  double profit_factor = 20;
  double standard_deviation = 21;
  double sharpe_ratio = 22;
  ZScoreData z_score = 23;
  ExpectancyData expectancy = 24;            // pips & dollar
  double ghpr = 25;                          // geometric holding period return
  int64 trades = 26;
  double growth_percent = 27;
  double deposits = 28;
  double withdrawals = 29;
}
```

---

## 🔽 Input

| Parameter | Type | Description |
|-----------|------|-------------|
| `from` | `Instant`, optional | Start of the window; serialized to an ISO-8601 string. Pass `null` for full history. |

---

## ⬆️ Output — `StatsData`

Headline **scalars**:

| Field | Java getter | Description |
|-------|-------------|-------------|
| `profit_factor` | `getProfitFactor()` | Gross profit ÷ gross loss (> 1 is profitable) |
| `sharpe_ratio` | `getSharpeRatio()` | Risk-adjusted return |
| `ghpr` | `getGhpr()` | Geometric holding-period return |
| `standard_deviation` | `getStandardDeviation()` | Std-dev of trade results |
| `pips` / `lots` / `comissions` | `getPips()` / `getLots()` / `getComissions()` | Total pips, lots traded, commissions |
| `trades` | `getTrades()` | Total number of trades (`long`) |
| `growth_percent` | `getGrowthPercent()` | Account growth, % |
| `deposits` / `withdrawals` | `getDeposits()` / `getWithdrawals()` | Totals in deposit currency |
| `max_balance_drawdown_raw` | `getMaxBalanceDrawdownRaw()` | Worst balance drawdown (money) |
| `max_balance_drawdown_relative` | `getMaxBalanceDrawdownRelative()` | Worst balance drawdown (%) |
| `max_equity_drawdown_raw` | `getMaxEquityDrawdownRaw()` | Worst equity drawdown (money) |
| `max_equity_drawdown_relative` | `getMaxEquityDrawdownRelative()` | Worst equity drawdown (%) |
| `average_trade_length` | `getAverageTradeLength()` | Average holding time (`String`) |

Nested **submessages**:

| Field | Java getter | Message | Key getters |
|-------|-------------|---------|-------------|
| `summary` | `getSummary()` | `TradeSummaryData` | `getOpenTrades()`, `getOpenProfit()`, `getDayProfit()`, `getWeekProfit()`, `getMonthProfit()`, `getTotalProfit()` |
| `profitability` | `getProfitability()` | `ProfitabilityData` | `getWonTrades()`, `getWonTradesPercent()`, `getLostTrades()`, `getLostTradesPercent()` |
| `markets` (repeated) | `getMarketsCount()` / `getMarkets(i)` | `MarketTradeCountData` | `getMarketName()`, `getCount()`, `getLots()`, `getProfit()`, `getWinRate()`, `getWonCount()`, `getLostCount()` |
| `average_win` / `average_lost` | `getAverageWin()` / `getAverageLost()` | `AveragePipsUsdData` | `getAveragePips()`, `getAverageUsd()` |
| `longs_won` / `shorts_won` | `getLongsWon()` / `getShortsWon()` | `WonData` | `getWonCount()`, `getAll()`, `getWonPersent()` |
| `best_trade` / `worst_trade` | `getBestTrade()` / `getWorstTrade()` | `ProfitData` | `getTiket()`, `getDate()`, `getProfit()` |
| `z_score` | `getZScore()` | `ZScoreData` | `getZScoreDecimal()`, `getZScoreProbability()` |
| `expectancy` | `getExpectancy()` | `ExpectancyData` | `getPips()`, `getDollar()` |

---

## 🧩 Notes & Tips

* **`summary` is the "today/this week/this month" block** — perfect for a compact P/L widget, separate from the all-time ratios.
* **Drawdowns come in two flavours:** balance vs equity, each as raw money and relative %. Equity drawdown is usually the one that matters for risk.
* **`markets` is your per-symbol attribution** — iterate `getMarketsCount()` / `getMarkets(i)` to see which instruments actually make or lose money.
* **Watch the proto spelling.** Field names carry the server's spelling: `comissions`, `tiket`, `won_persent` → getters are `getComissions()`, `getTiket()`, `getWonPersent()` — use them as-is.
* **`from` trims the window.** Pass `null` for the full account history; pass an `Instant` to scope stats to a period (e.g. this month).
* **Pairs with [equity_history](equity_history.md).** Use `tradeStats` for the numbers and `equityHistory` for the curve, or [tradeStatsEquityHistory](trade_stats_equity_history.md) to get both in one round-trip.

---

## 🔗 Usage Examples

### 1) Weekly performance report

Turn the stats into a readable report line — the kind you log or post to a channel.

```java
static void performanceReport(MT4Account account) {
    var s = account.tradeStats(null);
    var p = s.getProfitability();
    System.out.println("── Performance ──────────────────────────────");
    System.out.printf(Locale.ROOT, " Trades        : %d  (win %.1f%%)%n", s.getTrades(), p.getWonTradesPercent());
    System.out.printf(Locale.ROOT, " Profit factor : %.2f%n", s.getProfitFactor());
    System.out.printf(Locale.ROOT, " Expectancy    : %+.2f / trade%n", s.getExpectancy().getDollar());
    System.out.printf(Locale.ROOT, " Growth        : %+.2f%%%n", s.getGrowthPercent());
    System.out.printf(Locale.ROOT, " Max DD (eq)   : %.1f%%  (%,.2f)%n",
            s.getMaxEquityDrawdownRelative(), s.getMaxEquityDrawdownRaw());
    System.out.printf(Locale.ROOT, " This week     : %+,.2f%n", s.getSummary().getWeekProfit());
}
```

### 2) Which markets make money?

Rank the per-market breakdown so you can cut the losers and size up the winners.

```java
static void marketsByProfit(MT4Account account, int top) {
    var s = account.tradeStats(null);
    var rows = new ArrayList<>(s.getMarketsList());
    rows.sort(Comparator.comparingDouble(MarketTradeCountData::getProfit).reversed());
    System.out.printf(Locale.ROOT, "%-10s%7s%7s%12s%n", "Symbol", "Trades", "Win%", "Profit");
    for (var m : rows.subList(0, Math.min(top, rows.size()))) {
        System.out.printf(Locale.ROOT, "%-10s%7d%6.1f%%%12,.2f%n",
                m.getMarketName(), m.getCount(), m.getWinRate(), m.getProfit());
    }
}
```

### 3) Month-to-date health check

Scope the stats to the current month and flag a weak profit factor.

```java
static boolean monthHealth(MT4Account account, double minProfitFactor) {
    Instant start = YearMonth.now(ZoneOffset.UTC).atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant();
    var s = account.tradeStats(start);
    boolean ok = s.getProfitFactor() >= minProfitFactor;
    System.out.printf(Locale.ROOT, "MTD: trades=%d PF=%.2f result=%+,.2f -> %s%n",
            s.getTrades(), s.getProfitFactor(), s.getSummary().getMonthProfit(), ok ? "OK" : "WEAK");
    return ok;
}
```

---

## ⚠️ Pitfalls

* **Empty history.** A brand-new account with no closed trades returns zeroed stats — guard divisions (e.g. don't trust `profit_factor` when `trades == 0`).
* **Potentially heavy.** The `markets` list grows with the number of traded symbols; summarize rather than print everything.
* **Spelling quirks.** `getComissions()`, `getTiket()`, `getWonPersent()` are the real getter names — a typo-looking call is correct.
* **Scope awareness.** Numbers reflect the `from` window (or all history if `null`) — don't compare a month-scoped call with an all-time one.

---

## 🧪 Testing Suggestions

* **Happy path (demo with history).** `getTrades() > 0`, `getProfitFactor()` finite, `getMarketsCount() >= 1`.
* **Fresh account.** With no closed trades, assert the call still succeeds and your report handles zeros without dividing by zero.
* **Windowing.** A `from` of today vs a year ago returns different `trades` counts; assert the scoped count ≤ the full count.

---

## 📚 See also

* [Account Summary](account_summary.md)
* [Unrealized P/L Extrema](trade_unrealized_pl.md)
* [Equity Curve](equity_history.md)
* [Stats + Equity + Trades](trade_stats_equity_history.md)

* **Back to:** [Account Information — Overview](Account_Information.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
