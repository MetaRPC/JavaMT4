# MetaTrader 4 SDK

This is the documentation of MetaTrader 4 SDK which connects your application directly to MetaTrader 4 Servers without the needs of terminal application installed in your environment.

This code doesn't depend on any external libraries, the connection is TCP based using Java NIO for best performances.

## Quick Start

`MT4Client` is the main object required to establish a connection, it requires an `host` a `port` and a callback handler.

```Java
MT4Client mt4Client = new MT4Client("HOST_IP/DNS", 443, new MessageHandler() {
    @Override
    public void onMessage(MetaTraderMessage message, MT4Client client) {
        //This function is called when a new message from the server is received by the client
    }

    @Override
    public void onReceiveFailure(DecoderException e) {
        //This function is called when an exception is raised during a message decoding,
        //It usually contains helpful logs for unknown messages from MT Server or malformed socket
    }

    @Override
    public void onSendFailure(Exception e) {
        //This function is called when an exception happens while preparing and sending a message to MT Server
    }

    @Override
    public void onDisconnected(IOException exception, MT4Client client) {
        //A disconnection may happen for many reasons. Network instability, server updates, etc.
        //To continue your application use this callback to reset the connection and establish a new one.
    }

    @Override
    public void onConnected(MT4Client client) {
        //This function is called when the account successfully connects to MT Server.
    }
});
```

### Open a connection

To open a connection use one of `MT4Client#connect` methods.
```Java
mt4Client.connect(username, password, waitConnection, downloadSymbols);
```

### Sending and receiving messages

There are two ways of sending and receiving messages from server. Each message represents an action. A message can be sent:
- *Asynchronously*: the thread which sends the messages doesn't wait for a possible answer, but continues immediately. An answer, if needed, will be available in `onMessage` callback.
- *Synchronously*: the thread that sends the messages waits for a possible answer, or null if nothing has received in time. To increase synchronous timeout change the value of `MT4Constants#MESSAGE_RESPONSE_TIMEOUT`.

Example of sending a message:
```Java
HistoricalBarsReq historicalBarsReq = new HistoricalBarsReq();
historicalBarsReq.symbol = "EURUSD";
historicalBarsReq.timeframe = Timeframe.H1;
historicalBarsReq.fromTime = (int) Instant.now().minus(Duration.ofDays(7)).getEpochSecond();
historicalBarsReq.count = (short) 500;

//Thread will wait until historical data are downloaded
HistoricalBarsRes res = (HistoricalBarsRes) mt4Client.sendRequest(historicalBarsReq);
System.out.println(res.bars.size());

//Request messages can be re-used in your application once used, you can pool when needed.
historicalBarsReq.timeframe = Timeframe.M1;
//Thread will not wait and returns immediately. HistoricalBarsRes will be received in onMessage callback
mt4Client.sendRequestAsync(historicalBarsReq);
```

Message objects can be reused in the application, there's no need to create a new request for each action. This will allow to pool your messages to reduce GC pressure.

### Supported messages

List of supported requests:

- `HistoricalBarsReq`: requests historical OHLCV bars for the specified symbol and timeframe. Expects `HistoricalBarsRes` answer.
- `OrderTransactionReq`: main message for sending, canceling, modifying, closing any kind of orders (market and pending). Expects `TradeEvent` answer.
- `SubscribeQuotesReq`: update server which symbols stream in quote streaming. There's no expected message back, but you'll receive `QuoteEvent` messages in `onMessage` callback.
- `ChangePasswordReq`: change the account password (main or investor). No expected response.

List of supported events/responses:

- `HistoricalBarsRes`: contains historical OHLCV bars requested.
- `TradeEvent`: received as a result of an order transaction request.
- `QuoteEvent`: received when a new quote tick is generated on the server.
- `OrderUpdateEvent`: received when an order is opened, closed or modified.
- `SymbolUpdateEvent`: received when symbol specifications change on the server.
- `ConGroupUpdateEvent`: received when connection group settings change.

### Order History

Order history uses a direct synchronous read from the socket:

```Java
OrderHistoryRes res = mt4Client.requestOrderHistory(fromTime, toTime);
for (TradeRecord trade : res.trades) {
    System.out.println(trade);
}
```

## Memory Management

There are several key points to manage and optimize memory depending on the use case.

- `MT4Constants#MAX_OUTPUT_SIZE` is used to allocate the output buffer while preparing the messages before sending to the socket.
- `MT4Constants#MAX_INPUT_SIZE` is used to allocate the input buffer while reading the messages from the socket.

## Building from source

Building from source can be made with a single Gradle command.
```bash
./gradlew build -x test
```

You'll find in `build/libs/` the following artifacts:
- `MT4SDK-1.0.jar` library compiled code
- `MT4SDK-1.0-javadoc.jar` generated javadoc
- `MT4SDK-1.0-sources.jar` full sources file
