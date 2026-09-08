# API Reference

Complete reference of the primary classes, interfaces, and data models in the `io.mtapi.mt4` package.

## Classes & Interfaces

### `MT4Client`
The primary client class for connecting to and interacting with MetaTrader 4 servers.

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
Callback interface for receiving non-blocking network events from the MetaTrader 4 server.

```java
public interface MessageHandler {
    void onConnected(MT4Client client);
    void onDisconnected(IOException exception, MT4Client client);
    void onMessage(MetaTraderMessage message, MT4Client client);
    void onReceiveFailure(DecoderException exception);
    void onSendFailure(Exception exception);
}
```

## Enumerations

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

## Request & Response Objects

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
