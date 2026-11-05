# MT4 Error / Return Codes Reference (Java)

## 🛡️ How MT4 reports errors

Unlike MT5 (which returns a numeric `returned_code` / `TRADE_RETCODE_*` on every trade result), **MT4 reports failures as exceptions**. A trading call either:

* **succeeds** — and returns its data (e.g. `orderSend` returns a ticket), or
* **fails** — and throws an exception carrying a structured error.

> There is **no** `returned_code` field and **no** `TRADE_RETCODE_*` constants in JavaMT4. The classic MQL4 error codes live in the `MqlErrorCode` enum (below).

### Quick example

```java
import pro.mrpc.mt4.ApiExceptionMT4;

try {
    long ticket = sugar.buyMarket("EURUSD", 0.01, null, null, null, null, null, null, null);
    System.out.println("✓ opened #" + ticket);
} catch (ApiExceptionMT4 e) {
    var err = e.getError();   // the structured protobuf Error (null for a transport failure)
    if (err != null)
        System.out.printf("✗ trade failed: %s (code %d)%n", err.getMqlErrorDescription(), err.getMqlErrorIntCode());
}
```

**Key point:** no exception = the trade went through; an exception tells you *why* it did not.

---

## Where the codes come from

The codes are the standard **MQL4 error codes**, defined in the protobuf enum `MqlErrorCode` inside `package/mt4_term_api/MrpcMt4Error.java`. They are returned by the trading server and mirror what `GetLastError()` returns inside an MQL4 EA.

---

## How errors surface in the API

### Low level — `MT4Account`

| Exception | When |
|-----------|------|
| `ConnectExceptionMT4` | a method was called before a connect, or a connection could not be established |
| `ApiExceptionMT4` | the server returned an error for the request (or a gRPC call failed) |

`ApiExceptionMT4.getError()` returns a protobuf `Error` with these getters:

| Field | Getter | Meaning |
|-------|--------|---------|
| `type` | `getType()` | high-level category (`ErrorType`): `MRPC`, `MRPC_VALIDATION`, `MRPC_TIMEOUT`, `MQL_EXECUTION`, … |
| `error_code` | `getErrorCode()` | gateway error code string (e.g. `MQL4_EXECUTION_FAILED`) |
| `error_message` | `getErrorMessage()` | human-readable gateway message |
| `mql_error_code` | `getMqlErrorCode()` | symbolic MQL4 error (e.g. `ERR_NOT_ENOUGH_MONEY`) |
| `mql_error_int_code` | `getMqlErrorIntCode()` | numeric MQL4 error (e.g. `134`) |
| `mql_error_description` | `getMqlErrorDescription()` | description of the MQL4 error |
| `command_type_name` | `getCommandTypeName()` | which RPC produced the error |

`ApiExceptionMT4.getErrorCode()` is a shortcut to the server error-code string (or `null`).

### High level — `MT4Sugar`

The Sugar layer wraps failures into domain exceptions (all extend `MT4Error`):

| Exception | Raised for |
|-----------|-----------|
| `OrderRejected` | `placeOrder` / `buyMarket` / … failures |
| `ModifyRejected` | `modifySlTp…` / `close` / `closeBy` failures |
| `ConnectivityError` | reconnect / session / handshake issues |
| `RateLimitExceeded` | the client-side rate-limit guard tripped |
| `MT4Error` | base class / plain validation (not connected, symbol not found, wait timeout) |

Each `MT4Error` carries the message (wrapping the low-level error), plus optional code/details and the cause.

---

## MQL4 Error Codes (`MqlErrorCode`)

### Trade server & common codes

| Code | Constant | Meaning |
|------|----------|---------|
| **0** | `ERR_NO_ERROR` | No error (success) |
| **1** | `ERR_NO_RESULT` | No error, but result unknown |
| **2** | `ERR_COMMON_ERROR` | Common error |
| **3** | `ERR_INVALID_TRADE_PARAMETERS` | Invalid trade parameters |
| **4** | `ERR_SERVER_BUSY` | Trade server is busy |
| **6** | `ERR_NO_CONNECTION` | No connection to trade server |
| **7** | `ERR_NOT_ENOUGH_RIGHTS` | Not enough rights |
| **8** | `ERR_TOO_FREQUENT_REQUESTS` | Too frequent requests |
| **64** | `ERR_ACCOUNT_DISABLED` | Account disabled |
| **65** | `ERR_INVALID_ACCOUNT` | Invalid account |
| **128** | `ERR_TRADE_TIMEOUT` | Trade timeout |
| **129** | `ERR_INVALID_PRICE` | Invalid price |
| **130** | `ERR_INVALID_STOPS` | Invalid stops (SL/TP too close to price) |
| **131** | `ERR_INVALID_TRADE_VOLUME` | Invalid trade volume |
| **132** | `ERR_MARKET_CLOSED` | Market is closed |
| **133** | `ERR_TRADE_DISABLED` | Trading is disabled |
| **134** | `ERR_NOT_ENOUGH_MONEY` | Not enough money |
| **135** | `ERR_PRICE_CHANGED` | Price changed |
| **136** | `ERR_OFF_QUOTES` | Off quotes (no prices) |
| **137** | `ERR_BROKER_BUSY` | Broker is busy |
| **138** | `ERR_REQUOTE` | Requote |
| **141** | `ERR_TOO_MANY_REQUESTS` | Too many requests |
| **145** | `ERR_TRADE_MODIFY_DENIED` | Modification denied (order too close to market) |
| **146** | `ERR_TRADE_CONTEXT_BUSY` | Trade context is busy |
| **148** | `ERR_TRADE_TOO_MANY_ORDERS` | Too many open/pending orders |
| **149** | `ERR_TRADE_HEDGE_PROHIBITED` | Hedging prohibited |
| **150** | `ERR_TRADE_PROHIBITED_BY_FIFO` | Prohibited by FIFO rule |

> Codes **4000+** are MQL4 *runtime* errors and rarely appear for API trading. Introspect `MqlErrorCode` in `MrpcMt4Error` for the complete list.

---

## Inspecting errors

### Low level (`MT4Account`)

```java
import pro.mrpc.mt4.ApiExceptionMT4;

try {
    var result = account.orderSend(request);
    System.out.printf("✓ order #%d @ %.5f%n", result.getTicket(), result.getPrice());
} catch (ApiExceptionMT4 e) {
    int code = e.getError() != null ? e.getError().getMqlErrorIntCode() : -1;
    switch (code) {
        case 134 -> System.out.println("Not enough money — reduce volume or add margin");
        case 132 -> System.out.println("Market is closed — try during trading hours");
        default  -> System.out.println("Trade failed: " + e.getMessage() + " (code " + code + ")");
    }
}
```

### High level (`MT4Sugar`)

```java
import pro.mrpc.mt4.error.OrderRejected;
import pro.mrpc.mt4.error.MT4Error;

try {
    long ticket = sugar.buyMarket("GBPUSD", 0.5, null, null, null, null, null, null, null);
    System.out.println("✓ order #" + ticket);
} catch (OrderRejected e) {
    System.out.println("Order rejected: " + e.getMessage());   // wraps the server error
} catch (MT4Error e) {
    System.out.println("SDK error: " + e.getMessage());
}
```

---

## Retrying transient errors

Some codes are **temporary** and safe to retry with a short backoff: `ERR_SERVER_BUSY` (4), `ERR_NO_CONNECTION` (6), `ERR_TOO_FREQUENT_REQUESTS` (8), `ERR_TRADE_TIMEOUT` (128), `ERR_PRICE_CHANGED` (135), `ERR_OFF_QUOTES` (136), `ERR_BROKER_BUSY` (137), `ERR_REQUOTE` (138), `ERR_TRADE_CONTEXT_BUSY` (146), `ERR_TOO_MANY_REQUESTS` (141).

```java
static final Set<Integer> RETRYABLE = Set.of(4, 6, 8, 128, 135, 136, 137, 138, 141, 146);

static long buyWithRetry(MT4Sugar sugar, String symbol, double lots, int maxRetries) throws InterruptedException {
    for (int attempt = 1; ; attempt++) {
        try {
            return sugar.buyMarket(symbol, lots, null, null, null, null, null, null, null);
        } catch (pro.mrpc.mt4.ApiExceptionMT4 e) {
            int code = e.getError() != null ? e.getError().getMqlErrorIntCode() : -1;
            if (RETRYABLE.contains(code) && attempt < maxRetries) {
                Thread.sleep(attempt * 1000L);   // 1s, 2s, …
                continue;
            }
            throw e;
        }
    }
}
```

**Permanent** errors (don't retry): `ERR_NOT_ENOUGH_MONEY` (134), `ERR_INVALID_STOPS` (130), `ERR_INVALID_TRADE_VOLUME` (131), `ERR_MARKET_CLOSED` (132), `ERR_TRADE_DISABLED` (133). Fix the request or conditions.

---

## Best practices

**✅ DO**

1. Wrap trading calls in `try/catch` — that's how MT4 reports failures.
2. Branch on `e.getError().getMqlErrorIntCode()` for the exact reason.
3. Retry only transient codes with a short backoff.

**❌ DON'T**

1. Don't look for a `returned_code` — MT4 has none; a returned ticket already means success.
2. Don't retry permanent errors (insufficient money, invalid stops, market closed).
3. Don't swallow exceptions silently — log `getMqlErrorDescription()`.

---

## Quick reference

| Category | Codes | Action |
|----------|-------|--------|
| Success | 0 + a returned ticket | continue |
| Transient | 4, 6, 8, 128, 135, 136, 137, 138, 141, 146 | retry with delay |
| Invalid request | 3, 129, 130, 131 | fix parameters |
| Restrictions | 132, 133, 140, 149, 150 | check trading conditions |
| Limits | 134, 148 | reduce volume / close orders |
| Account / rights | 7, 64, 65 | check account & permissions |

---

## See also

* [Errors](ERRORS.md) — the exception model in short
* [ENUMs Usage Reference](ENUMS_USAGE_REFERENCE.md)
* [MT4Account — Trading Operations](../MT4Account/4.%20Trading_Operations/Trading_Operations.Overview.md)
