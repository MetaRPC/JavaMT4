# Order Send

> Send a market or pending order — the low-level entry point for opening trades.

**API Information:**

* **Low-level API:** `MT4Account.orderSend(OrderSendRequest request)` / `orderSend(String symbol, OrderSendOperationType type, double volume)` (defined in [`MT4Account.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/pro/mrpc/mt4/MT4Account.java))
* **gRPC service:** `mt4_term_api.TradingHelper`
* **RPC:** `OrderSend(OrderSendRequest) -> OrderSendReply`
* **Payload:** `OrderSendData`

## 💬 Just the essentials

* **What it is.** Opens a market order or places a pending order, with optional SL/TP, comment and magic.
* **Why you need it.** Full control over every order field at the lowest level.
* **When to use.** When you need raw control. For pips-based SL/TP, normalization and risk sizing, prefer [MT4Sugar](../../MT4Sugar/MT4Sugar.Master.Overview.md) (`buyMarket`, `buyLimit`, …) which wraps this.

---

## 🎯 Purpose

* Open market orders (BUY / SELL)
* Place pending orders (LIMIT / STOP)
* Attach SL / TP, comment and magic number
* Set slippage and pending-order expiration

---

## Method Signature

```java
// Full control — build the request yourself:
public OrderSendData orderSend(OrderSendRequest request)

// Convenience — a plain market order:
public OrderSendData orderSend(String symbol, OrderSendOperationType type, double volume)
```

For SL/TP, price, slippage, comment, magic or expiration, build an `OrderSendRequest` and use the first overload.

**Request message:**

```protobuf
message OrderSendRequest {
  string symbol = 1;
  OrderSendOperationType operation_type = 2;
  double volume = 3;
  double price = 4;
  int32 slippage = 5;
  double stoploss = 6;
  double takeprofit = 7;
  string comment = 8;
  int32 magic_number = 9;
  Timestamp expiration = 10;
  Timestamp expiration_utc = 11;
}
```

**Payload message:**

```protobuf
message OrderSendData {
  int32 ticket = 1;
  double volume = 2;
  double price = 3;
  Timestamp open_time = 4;
  Timestamp open_time_utc = 5;
}
```

---

## 🔽 Input (`OrderSendRequest` builder)

| Field | Builder setter | Type | Description |
|-------|----------------|------|-------------|
| `symbol` | `setSymbol` | `String` | Symbol name. |
| `operation_type` | `setOperationType` | `OrderSendOperationType` | Buy/sell, market or pending (see enum). |
| `volume` | `setVolume` | `double` | Lots. |
| `price` | `setPrice` | `double` | Entry price — **required for pending orders**; for market orders it can be omitted. |
| `slippage` | `setSlippage` | `int` | Max slippage in points (market orders). |
| `stoploss` | `setStoploss` | `double` | Stop-loss price (0 / omit for none). |
| `takeprofit` | `setTakeprofit` | `double` | Take-profit price (0 / omit for none). |
| `comment` | `setComment` | `String` | Order comment. |
| `magic_number` | `setMagicNumber` | `int` | Expert/magic id to tag the order. |
| `expiration` | `setExpiration` | `Timestamp` | Expiration for pending orders. |

---

## ⬆️ Output — `OrderSendData`

| Field | Java getter | Description |
|-------|-------------|-------------|
| `ticket` | `getTicket()` | Ticket of the new order |
| `volume` | `getVolume()` | Filled/placed volume |
| `price` | `getPrice()` | Fill price (market) or pending price |
| `open_time` / `open_time_utc` | `getOpenTime()` / `getOpenTimeUtc()` | Open/placement time (server / UTC) |

> On failure the call throws `ApiExceptionMT4` — inspect `e.getError().getMqlErrorIntCode()` / `getMqlErrorDescription()` (see [Errors](../../All_Guides/ERRORS.md)).

---

## 🧱 Related enums (from proto)

### `OrderSendOperationType` — field `operation_type`

| Constant | Value | Meaning |
|----------|-------|---------|
| `OC_OP_BUY` | 0 | Market buy |
| `OC_OP_SELL` | 1 | Market sell |
| `OC_OP_BUYLIMIT` | 2 | Buy limit (pending, below price) |
| `OC_OP_SELLLIMIT` | 3 | Sell limit (pending, above price) |
| `OC_OP_BUYSTOP` | 4 | Buy stop (pending, above price) |
| `OC_OP_SELLSTOP` | 5 | Sell stop (pending, below price) |

> Access via `Mt4TermApiTradingHelper.OrderSendOperationType.OC_OP_BUY`.

---

## 🧩 Notes & Tips

* **Market vs pending.** `OC_OP_BUY`/`OC_OP_SELL` fill immediately (price optional); the `*LIMIT`/`*STOP` variants are pending and **require** `price`.
* **Absolute SL/TP prices.** `stoploss`/`takeprofit` are prices, not pip distances. Respect the symbol's `TradeStopsLevel` ([symbol_params_many](../3.%20Symbols_Quotes/symbol_params_many.md)) or you'll get `ERR_INVALID_STOPS` (130).
* **Normalize first.** Round `volume` to `VolumeStep` and prices to `Digits`; MT4Sugar does this automatically.
* **Tag with `magic_number`.** It's how you later identify "my" orders in [opened_orders](../2.%20Orders_History/opened_orders.md).
* **Returns a ticket** on success — there is no numeric `returned_code` (that's MT5); errors are exceptions.

---

## 🔗 Usage Examples

> ⚠️ These place **real orders on the connected (demo) account**. Use 0.01 lots and clean up.

### 1) Open a market BUY

```java
static int buy(MT4Account account, String symbol, double lots) {
    var res = account.orderSend(symbol, OrderSendOperationType.OC_OP_BUY, lots);
    System.out.printf(Locale.ROOT, "opened #%d %s lot @ %.5f%n", res.getTicket(), res.getVolume(), res.getPrice());
    return res.getTicket();
}
```

### 2) Market SELL with SL/TP, comment and magic

```java
static int sellWithStops(MT4Account account, String symbol, double lots) {
    var q = account.quote(symbol);
    double sl = Math.round((q.getBid() + 0.0020) * 1e5) / 1e5;   // 20 pips above for a short
    double tp = Math.round((q.getBid() - 0.0030) * 1e5) / 1e5;   // 30 pips below
    var req = Mt4TermApiTradingHelper.OrderSendRequest.newBuilder()
            .setSymbol(symbol).setOperationType(OrderSendOperationType.OC_OP_SELL).setVolume(lots)
            .setStoploss(sl).setTakeprofit(tp).setComment("demo sell").setMagicNumber(777)
            .build();
    var res = account.orderSend(req);
    System.out.printf(Locale.ROOT, "opened #%d SL=%.5f TP=%.5f%n", res.getTicket(), sl, tp);
    return res.getTicket();
}
```

### 3) Place a BUY LIMIT below the market

```java
static int buyLimit(MT4Account account, String symbol, double lots) {
    var q = account.quote(symbol);
    double price = Math.round((q.getAsk() - 0.0030) * 1e5) / 1e5;   // 30 pips below current ask
    var req = Mt4TermApiTradingHelper.OrderSendRequest.newBuilder()
            .setSymbol(symbol).setOperationType(OrderSendOperationType.OC_OP_BUYLIMIT)
            .setVolume(lots).setPrice(price).build();
    var res = account.orderSend(req);
    System.out.printf(Locale.ROOT, "placed BUY LIMIT #%d @ %.5f%n", res.getTicket(), price);
    return res.getTicket();
}
```

### 4) Robust send with error handling

```java
static Integer safeBuy(MT4Account account, String symbol, double lots) {
    try {
        return account.orderSend(symbol, OrderSendOperationType.OC_OP_BUY, lots).getTicket();
    } catch (ApiExceptionMT4 e) {
        int code = e.getError() != null ? e.getError().getMqlErrorIntCode() : -1;
        switch (code) {
            case 134 -> System.out.println("not enough money");       // ERR_NOT_ENOUGH_MONEY
            case 130 -> System.out.println("SL/TP too close to price"); // ERR_INVALID_STOPS
            default  -> System.out.println("rejected: " + e.getMessage() + " (" + code + ")");
        }
        return null;
    }
}
```

---

## ⚠️ Pitfalls

* **Pending orders need `price`.** Omitting it for a `*LIMIT`/`*STOP` order fails.
* **SL/TP are prices.** And must respect `TradeStopsLevel` — too-close stops raise `ERR_INVALID_STOPS` (130).
* **Un-normalized volume/price.** Values off the broker's step/digits are rejected (`ERR_INVALID_TRADE_VOLUME` 131 / `ERR_INVALID_PRICE` 129).
* **Rejections are exceptions**, not a return code — always wrap trading calls in `try/catch (ApiExceptionMT4)`.
* **Clean up demos.** Close what you open with [order_close_delete](order_close_delete.md).

---

## 🧪 Testing Suggestions

* **Happy path (demo).** A 0.01 market BUY returns a positive `ticket`; the ticket appears in [opened_orders](../2.%20Orders_History/opened_orders.md).
* **Round-trip.** Open then [order_close_delete](order_close_delete.md) the ticket; it leaves `opened_orders`.
* **Failure path.** Send an oversized volume and assert `ApiExceptionMT4` with a sensible `mql_error_int_code`.

---

## 📚 See also

* [Order Modify](order_modify.md)
* [Order Close / Delete](order_close_delete.md)
* [Order Close By](order_close_by.md)
* [Opened Orders](../2.%20Orders_History/opened_orders.md)

* **Back to:** [Trading Operations — Overview](Trading_Operations.Overview.md) · [MT4Account — Master Overview](../MT4Account.Master.Overview.md)
