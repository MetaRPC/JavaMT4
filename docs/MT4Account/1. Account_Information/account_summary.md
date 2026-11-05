# Account Summary (RECOMMENDED)

> Complete account snapshot (balance, equity, margin, leverage, currency, server time) in a single call.

**API Information:**

* **Low-level API:** `MT4Account.accountSummary()` (defined in [`src/main/java/pro/mrpc/mt4/MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.AccountHelper`
* **RPC:** `AccountSummary(AccountSummaryRequest) -> AccountSummaryReply`
* **Payload:** `AccountSummaryData`

## 💬 Just the essentials

* **What it is.** One call that returns every essential account property.
* **Why you need it.** The most efficient way to read balance/equity/margin at once — one round-trip instead of many single-field calls.
* **When to use.** At session start (banner / sanity check), and right before any risk decision or order.

---

## 🎯 Purpose

* RECOMMENDED way to read account state
* Balance, equity, credit in one round-trip
* Login, name, server, company
* Leverage and trade mode (DEMO/REAL)
* Currency and server time

---

## Method Signature

```java
public AccountSummaryData accountSummary()
```

No arguments. Returns the typed payload directly, or throws `ApiExceptionMT4` on a server error.

**Request message:**

```protobuf
message AccountSummaryRequest {
}
```

**Payload message:**

```protobuf
message AccountSummaryData {
  int64 account_login = 1;
  double account_balance = 2;
  double account_equity = 3;
  string account_user_name = 4;
  int64 account_leverage = 5;
  EnumAccountTradeMode account_trade_mode = 6;
  string account_company_name = 7;
  string account_currency = 8;
  Timestamp server_time = 9;
  Timestamp server_time_utc = 15;
  double account_credit = 10;
  bool is_investor = 11;
  int32 utc_server_time_shift_minutes = 12;
  int32 server_utc_offset_seconds = 14;
  double account_margin = 13;
  double account_margin_free = 16;
  double account_margin_level = 17;
  EnumAccountMarginMode account_margin_mode = 18;
  bool account_trade_allowed = 19;
  bool account_trade_expert = 20;
}
```

---

## 🔽 Input

None. `accountSummary()` takes no parameters.

---

## ⬆️ Output — `AccountSummaryData`

| Field | Java getter | Proto Type | Description |
|-------|-------------|-----------|-------------|
| `account_login` | `getAccountLogin()` | int64 | Account number |
| `account_balance` | `getAccountBalance()` | double | Balance in deposit currency (excludes floating P/L) |
| `account_equity` | `getAccountEquity()` | double | Equity = balance + floating P/L of open orders |
| `account_user_name` | `getAccountUserName()` | string | Account owner |
| `account_leverage` | `getAccountLeverage()` | int64 | Leverage (e.g. 100 = 1:100) |
| `account_trade_mode` | `getAccountTradeMode()` | EnumAccountTradeMode | DEMO / CONTEST / REAL |
| `account_company_name` | `getAccountCompanyName()` | string | Broker name |
| `account_currency` | `getAccountCurrency()` | string | Deposit currency (e.g. USD) |
| `server_time` | `getServerTime()` | Timestamp | Current server time |
| `server_time_utc` | `getServerTimeUtc()` | Timestamp | Current server time in UTC |
| `account_credit` | `getAccountCredit()` | double | Credit in deposit currency |
| `is_investor` | `getIsInvestor()` | bool | True if connected with the investor (read-only) password |
| `utc_server_time_shift_minutes` | `getUtcServerTimeShiftMinutes()` | int32 | Server time shift from UTC, minutes |
| `server_utc_offset_seconds` | `getServerUtcOffsetSeconds()` | int32 | Server UTC offset, seconds |
| `account_margin` | `getAccountMargin()` | double | Margin currently used by open orders |
| `account_margin_free` | `getAccountMarginFree()` | double | Free margin available for new orders |
| `account_margin_level` | `getAccountMarginLevel()` | double | Margin level % = equity / margin × 100 |
| `account_margin_mode` | `getAccountMarginMode()` | EnumAccountMarginMode | Margin calculation mode |
| `account_trade_allowed` | `getAccountTradeAllowed()` | bool | Trading allowed on the account |
| `account_trade_expert` | `getAccountTradeExpert()` | bool | Expert/automated trading allowed |

---

## 🧱 Related enums (from proto)

### `EnumAccountTradeMode`

| Constant | Value | Meaning |
|----------|-------|---------|
| `ACCOUNT_TRADE_MODE_DEMO` | 0 | Demo / practice account |
| `ACCOUNT_TRADE_MODE_CONTEST` | 1 | Contest account |
| `ACCOUNT_TRADE_MODE_REAL` | 2 | Real (live) account |

### `EnumAccountMarginMode`

| Constant | Value | Meaning |
|----------|-------|---------|
| `ACCOUNT_MARGIN_MODE_RETAIL_NETTING` | 0 | Retail, netting |
| `ACCOUNT_MARGIN_MODE_EXCHANGE` | 1 | Exchange margin model |
| `ACCOUNT_MARGIN_MODE_RETAIL_HEDGING` | 2 | Retail, hedging (typical MT4) |

> In Java, access these through the generated enum, e.g. `Mt4TermApiAccountHelper.EnumAccountTradeMode.ACCOUNT_TRADE_MODE_REAL`.

---

## 🧩 Notes & Tips

* **One call beats many.** `accountSummary()` returns balance, equity, margin, free margin and margin level together — don't fan out into several calls for these.
* **`balance` vs `equity`.** Balance ignores open orders; equity includes their floating P/L. On a flat account they're equal; with open orders, `equity − balance` is your current floating P/L.
* **Money is in the deposit currency.** Every monetary field is in `account_currency`. Don't mix it with an instrument's quote currency without converting.
* **`margin_level` is 0 with no open orders** (division by zero margin is reported as 0), so guard before comparing it to a stop-out threshold.
* **Server time.** `server_time` / `server_time_utc` are protobuf `Timestamp`s — convert with `Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos())`; `server_utc_offset_seconds` tells you the broker's offset.
* **Read-only sessions.** If `is_investor` is true (investor password) or `account_trade_allowed` is false, trading calls will be rejected — check these before placing orders.

---

## 🔗 Usage Examples

### 1) Session-start banner

Print a clean account banner when your bot connects — handy for logs and sanity checks.

```java
import mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryData;
import mt4_term_api.Mt4TermApiAccountHelper.EnumAccountTradeMode;
import java.util.Locale;

static void printBanner(MT4Account account) {
    AccountSummaryData s = account.accountSummary();
    double floating = s.getAccountEquity() - s.getAccountBalance();
    String mode = switch (s.getAccountTradeMode()) {
        case ACCOUNT_TRADE_MODE_DEMO -> "DEMO";
        case ACCOUNT_TRADE_MODE_CONTEST -> "CONTEST";
        case ACCOUNT_TRADE_MODE_REAL -> "REAL";
        default -> "?";
    };
    System.out.println("=".repeat(52));
    System.out.printf(Locale.ROOT, " Account #%d  (%s)%n", s.getAccountLogin(), mode);
    System.out.printf(Locale.ROOT, " Broker : %s%n", s.getAccountCompanyName());
    System.out.printf(Locale.ROOT, " Balance: %,.2f %s%n", s.getAccountBalance(), s.getAccountCurrency());
    System.out.printf(Locale.ROOT, " Equity : %,.2f  (floating %+,.2f)%n", s.getAccountEquity(), floating);
    System.out.printf(Locale.ROOT, " Margin : used %,.2f / free %,.2f%n", s.getAccountMargin(), s.getAccountMarginFree());
    System.out.printf(Locale.ROOT, " Leverage 1:%d%n", s.getAccountLeverage());
    System.out.println("=".repeat(52));
}
```

### 2) Pre-trade risk gate

Refuse to open a new order unless the account can actually take it — a guard you call before every entry.

```java
static boolean canOpen(MT4Account account, double minFreeMargin, double minMarginLevel) {
    AccountSummaryData s = account.accountSummary();

    if (!s.getAccountTradeAllowed() || s.getIsInvestor()) {
        System.out.println("Trading not allowed on this account (read-only / disabled).");
        return false;
    }
    if (s.getAccountMarginFree() < minFreeMargin) {
        System.out.printf("Free margin too low: %.2f < %.2f%n", s.getAccountMarginFree(), minFreeMargin);
        return false;
    }
    // margin_level is 0 when nothing is open — only enforce it once there is margin in use
    if (s.getAccountMargin() > 0 && s.getAccountMarginLevel() < minMarginLevel) {
        System.out.printf("Margin level too low: %.1f%% < %.1f%%%n", s.getAccountMarginLevel(), minMarginLevel);
        return false;
    }
    return true;
}

// usage:
// if (canOpen(account, 500, 200.0)) sugar.buyMarket("EURUSD", 0.10, ...);
```

### 3) Equity / drawdown monitor

Poll equity on an interval and alert when drawdown from the session peak crosses a limit.

```java
static void watchDrawdown(MT4Account account, double maxDdPercent, long intervalMs) throws InterruptedException {
    Double peak = null;
    while (true) {
        double eq = account.accountSummary().getAccountEquity();
        peak = (peak == null) ? eq : Math.max(peak, eq);
        double dd = peak > 0 ? (peak - eq) / peak * 100 : 0.0;
        System.out.printf(Locale.ROOT, "equity=%,.2f peak=%,.2f drawdown=%.2f%%%n", eq, peak, dd);
        if (dd >= maxDdPercent) {
            System.out.printf("[ALERT] drawdown %.2f%% >= %.1f%% — consider de-risking%n", dd, maxDdPercent);
            break;
        }
        Thread.sleep(intervalMs);
    }
}
```

---

## ⚠️ Pitfalls

* **Snapshot, not a stream.** Equity/margin are a point-in-time read — re-query right before a risk check or order, don't cache them across time. (For live updates use [`onOpenedOrdersProfit`](../5.%20Streaming_Methods/on_opened_orders_profit.md).)
* **`margin_level` == 0 with nothing open.** Guard `account_margin > 0` before comparing the level to a stop-out/entry threshold.
* **Currency mismatch.** All money fields are in `account_currency`; converting instrument P/L into account currency is on you.
* **Read-only / disabled accounts.** `is_investor` or `account_trade_allowed == false` means orders will be rejected even though this call succeeds.

---

## 🧪 Testing Suggestions

* **Happy path (demo).** Fields are populated; `account_currency` non-empty; on a flat account `equity == balance` and `account_margin == 0`.
* **With open orders.** `equity != balance`, `account_margin > 0`, and `account_margin_level` is a sensible percentage.
* **Risk gate.** Unit-test `canOpen()` against synthetic summaries (low free margin, investor session, trading disabled) and assert it refuses.

---

## 📚 See also

* [Trade Statistics](trade_stats.md)
* [Unrealized P/L Extrema](trade_unrealized_pl.md)
* [Equity Curve](equity_history.md)
* [Stats + Equity + Trades](trade_stats_equity_history.md)

* **Back to:** [Account Information — Overview](Account_Information.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
