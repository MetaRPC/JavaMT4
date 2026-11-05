# Errors

Every reply from the gateway carries either `data` or an `error`; a refused request is a normal answer, not a failed call. JavaMT4 turns that into exceptions so you don't have to inspect every reply by hand — the layers unwrap `data` for you and **throw** when the reply holds an `error`.

All JavaMT4 exceptions are **unchecked** (`extends RuntimeException`), so they add no `throws` noise — but you should still wrap trading calls in `try/catch`.

---

## The exception types

| Exception | Thrown by | When |
|-----------|-----------|------|
| `ApiExceptionMT4` | `MT4Account` / `MT4Service` | the server answered with an error, or a gRPC call failed |
| `ConnectExceptionMT4` | `MT4Account` connect | a connection/handshake could not be established |
| `MT4Error` (+ subclasses) | `MT4Sugar` | high-level validation / rejection (see below) |

### `ApiExceptionMT4`

```java
public MrpcMt4Error.Error getError();   // the server error, or null for a pure transport failure
public String getErrorCode();           // the server error code string, or null
```

* **Server error:** `getError()` is non-null. Read `getErrorCode()` (e.g. `TERMINAL_INSTANCE_NOT_FOUND`), and for a broker/terminal rejection the MQL fields on the error:
  `getError().getMqlErrorIntCode()` (e.g. `131`), `getError().getMqlErrorDescription()`, `getError().getMqlErrorCode()`.
* **Transport failure** (network, deadline, server down): `getError()` is `null`; `getMessage()` carries the gRPC status.

```java
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendOperationType;
import pro.mrpc.mt4.ApiExceptionMT4;

try {
    account.orderSend("EURUSD", OrderSendOperationType.OC_OP_BUY, 0.001);
} catch (ApiExceptionMT4 e) {
    if (e.getError() != null) {                                    // the server answered with an error
        System.out.println(e.getErrorCode());                      // e.g. MQL4_EXECUTION_FAILED
        System.out.println(e.getError().getMqlErrorIntCode());     // 131 (invalid trade volume)
        System.out.println(e.getError().getMqlErrorDescription()); // "invalid trade volume"
    } else {
        System.out.println("call failed: " + e.getMessage());      // gRPC status, e.g. UNAVAILABLE
    }
}
```

### `MT4Error` and its subclasses (Sugar layer)

[`MT4Sugar`](../MT4Sugar/MT4Sugar.Master.Overview.md) wraps failures into a small hierarchy (all extend `MT4Error`, carrying a message, optional code, details and the cause):

| Subclass | Raised when |
|----------|-------------|
| `OrderRejected` | an entry (`placeOrder`/`buyMarket`/…) was refused |
| `ModifyRejected` | a modify/close (`modifySlTp…`/`close`/`closeBy`) was refused |
| `RateLimitExceeded` | the internal order-send rate limiter tripped |
| `ConnectivityError` | a connectivity problem surfaced at the high level |

`MT4Error` itself is also thrown for plain validation (e.g. "not connected", "symbol not found", wait-timeouts).

```java
import pro.mrpc.mt4.error.OrderRejected;

try {
    sugar.buyMarket("EURUSD", 100.0, null, null, null, null, null, null, null);  // oversized
} catch (OrderRejected e) {
    System.out.println("entry rejected: " + e.getMessage());   // wraps the underlying ApiExceptionMT4
}
```

---

## Auto-reconnect

`MT4Account` reconnects **automatically** on a transient `UNAVAILABLE` or a *terminal-not-found* error code (`TERMINAL_INSTANCE_NOT_FOUND`, `TERMINAL_REGISTRY_TERMINAL_NOT_FOUND`): it sleeps briefly, re-establishes the session and retries the call. Other errors are thrown. So most transient drops never reach your `catch`.

---

## Common MQL rejection codes

When the terminal/broker refuses a trade, `getError().getMqlErrorIntCode()` carries the MQL code:

| Code | Meaning | What to do |
|------|---------|------------|
| `129` | invalid price | normalize the price to the symbol's digits |
| `130` | invalid stops | respect `TradeStopsLevel` ([symbol_params_many](../MT4Account/3.%20Symbols_Quotes/symbol_params_many.md)) |
| `131` | invalid trade volume | round to `VolumeStep`, within `[VolumeMin, VolumeMax]` |
| `132` | market closed | the session is closed — retry when open |
| `134` | not enough money | reduce lots / check free margin |
| `145` | modify denied | order too close to market, or nothing changed |

Server-level error codes you'll meet:

| `errorCode` | Meaning | What to do |
|-------------|---------|------------|
| `TERMINAL_INSTANCE_NOT_FOUND` | the terminal id is not a running terminal | reconnect (the client does this automatically) |
| `MQL4_EXECUTION_FAILED` | the terminal refused the request — see the MQL fields | fix the request |
| `NO_CHARTS_OPENED_FOR_SYMBOLS` | a tick stream for an unknown/unselected symbol | check the symbol / `symbolSelect` first |

---

## See also

* [MT4Account — Trading Operations](../MT4Account/4.%20Trading_Operations/Trading_Operations.Overview.md) — where rejections originate
* [MT4Sugar — Order Entry](../MT4Sugar/5.%20Order_Entry.md) · [Order Management](../MT4Sugar/6.%20Order_Management.md)
