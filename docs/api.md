# API Reference

The classes of the `pro.mrpc.mt4` package.

## Classes & Interfaces

### `MT4Account` (the MRPC API)

The client of the MRPC MT4 API (`mt4.mrpc.pro:443`, gRPC over TLS). Every method returns the reply's data or throws
`MrpcException` (`getCode()` is the API's error code, e.g. `TERMINAL_INSTANCE_NOT_FOUND`, or the gRPC status of a failed
call).

```java
public class MT4Account implements AutoCloseable {
    public MT4Account(String apiKey);                              // mt4.mrpc.pro:443
    public MT4Account(String grpcServer, String apiKey);           // host:port, TLS
    public MT4Account(ManagedChannel channel, String apiKey);      // your own channel

    public String connect(long user, String password, String serverName, int timeoutSeconds);  // ConnectEx, e.g. "TeleTrade-Demo"
    public String connect(long user, String password, String host, int port, int timeoutSeconds); // Connect by address
    public String getId(long user, String password);              // GetId; a following connect() uses this id
    public void attach(String terminalId);                        // an already connected terminal
    public String getTerminalId();

    public AccountSummaryData accountSummary();
    public QuoteData quote(String symbol);
    public OrderSendData orderSend(OrderSendRequest request);
    public OrderSendData orderSend(String symbol, OrderSendOperationType type, double lots);
    public OrderCloseDeleteData orderClose(int ticket);           // close a market order, delete a pending one
    public Subscription onSymbolTick(List<String> symbols, Consumer<OnSymbolMqlTickInfo> onTick, Consumer<Throwable> onError);

    public void disconnect(boolean delete);
    public void close();                                          // shuts the channel down

    public ManagedChannel getChannel();
    public <S extends AbstractStub<S>> S authorized(S stub);      // any generated stub, with the API key and terminal id
}
```

Every other method of the API is in the generated stubs (`mt4_term_api.*Grpc`), for example
`account.authorized(AccountHelperGrpc.newBlockingStub(account.getChannel())).openedOrders(...)`. The full API:
[https://mt4.mrpc.pro/apiui](https://mt4.mrpc.pro/apiui).

### `MT4Client` (MT4 server protocol, no MRPC)

> **`MT4Client` is a different client.** It speaks the MT4 server protocol straight to a broker's MT4 server (the
> broker's access point host and port), without MRPC and without an API key. It cannot connect to `mt4.mrpc.pro`; for the
> MRPC API use `MT4Account`.


```java
public class MT4Client {
    public MT4Client(String host, int port, MessageHandler handler);
    public void connect(int user, String password);
    public void disconnect();
    public boolean isConnected();
    public boolean isBaseAccountRead();

    public AccountInfo getAccountInfo();
    public double getBalance();
    public int getLeverage();
    public String getAccountCurrency();
    public String getCompany();
    public SymbolTable getSymbolTable();
    public ServerInfo getServerInfo();
    public ConcurrentHashMap<Integer, OrderUpdateEvent> getOpenOrders();

    public MetaTraderMessage sendRequest(MetaTraderRequest request) throws IOException;
    public void sendRequestAsync(MetaTraderRequest request) throws IOException;
    public OrderHistoryRes requestOrderHistory(int fromTime, int toTime) throws Exception;
}
```

### `MessageHandler`
Callback interface of `MT4Client` for the events of the MT4 server connection.

```java
public interface MessageHandler {
    void onConnected(MT4Client client);
    void onDisconnected(IOException exception, MT4Client client);
    void onMessage(MetaTraderMessage message, MT4Client client);
    void onReceiveFailure(DecoderException exception);
    void onSendFailure(Exception exception);
}
```

## `MT4Client` enumerations

### `Op` (Order Type)
- `Op.Buy`: Market buy order
- `Op.Sell`: Market sell order
- `Op.BuyLimit`: Pending buy limit order
- `Op.SellLimit`: Pending sell limit order
- `Op.BuyStop`: Pending buy stop order
- `Op.SellStop`: Pending sell stop order

### `TradeCommand`
- `TradeCommand.Market`: Execute at market price
- `TradeCommand.Instant`: Execute at specified instant quote with slippage
- `TradeCommand.Modify`: Change stop loss / take profit
- `TradeCommand.Pending`: Place pending order
- `TradeCommand.CloseMarket`: Close existing order at market price
- `TradeCommand.CloseInstant`: Close existing order at instant price
- `TradeCommand.Delete`: Delete pending order

### `Timeframe`
- `Timeframe.M1`: 1 Minute
- `Timeframe.M5`: 5 Minutes
- `Timeframe.M15`: 15 Minutes
- `Timeframe.M30`: 30 Minutes
- `Timeframe.H1`: 1 Hour
- `Timeframe.H4`: 4 Hours
- `Timeframe.D1`: 1 Day
- `Timeframe.W1`: 1 Week
- `Timeframe.MN1`: 1 Month

## `MT4Client` request & response objects

### `OrderTransactionReq`
Request payload for creating, modifying, and closing orders.

| Field | Type | Description |
|---|---|---|
| `tradeCommand` | `TradeCommand` | Command operation type |
| `orderType` | `Op` | Buy, Sell, Limit, Stop |
| `symbol` | `String` | Trading symbol (e.g. `EURUSD`) |
| `ticket` | `int` | Order ticket (for modify/close) |
| `lots` | `int` | Lots in hundredths (1 = 0.01 lot) |
| `price` | `double` | Requested price |
| `stopLoss` | `double` | Stop Loss price |
| `takeProfit` | `double` | Take Profit price |
| `slippage` | `int` | Allowed slippage points |
| `comment` | `String` | Order comment |

### `TradeEvent`
Response returned after submitting an `OrderTransactionReq`.

| Field | Type | Description |
|---|---|---|
| `ticket` | `int` | Created or modified order ticket ID |
| `errorCode` | `int` | 0 on success, or MetaTrader trade error code |
| `orderType` | `Op` | Order type |
| `lots` | `int` | Lot size executed |
| `price` | `double` | Price at execution |
