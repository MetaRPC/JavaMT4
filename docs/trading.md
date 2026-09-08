# Trading & Orders

JavaMT4 supports execution of market orders, pending limit/stop orders, position modification (Stop Loss / Take Profit), and position closing.

## Opening an Order

MetaTrader 4 brokers use either **Market Execution** or **Instant Execution**. You can check the execution mode using `SymbolInfo`:

```java
import pro.mrpc.mt4.network.enums.Execution;
import pro.mrpc.mt4.network.enums.Op;
import pro.mrpc.mt4.network.enums.TradeCommand;
import pro.mrpc.mt4.network.messages.req.OrderTransactionReq;
import pro.mrpc.mt4.network.messages.res.TradeEvent;
import pro.mrpc.mt4.network.parts.SymbolInfo;

SymbolInfo symbolInfo = client.getSymbolTable().getInfo("EURUSD");
boolean isMarket = symbolInfo.execution == Execution.Market;

OrderTransactionReq req = new OrderTransactionReq();
req.orderType = Op.Buy;       // Op.Buy or Op.Sell
req.symbol = "EURUSD";
req.lots = 10;                // 10 = 0.10 lots (lots are in hundredths)
req.comment = "AlgoOrder";

if (isMarket) {
    req.tradeCommand = TradeCommand.Market;
    req.price = 0;
    req.slippage = 0;
} else {
    req.tradeCommand = TradeCommand.Instant;
    req.price = latestQuote.ask;
    req.slippage = 30; // 3 pips
}

TradeEvent result = (TradeEvent) client.sendRequest(req);
if (result.ticket > 0) {
    System.out.println("Order opened successfully! Ticket: #" + result.ticket);
} else {
    System.err.println("Order failed with error code: " + result.errorCode);
}
```

## Modifying Stop Loss and Take Profit

```java
OrderTransactionReq modifyReq = new OrderTransactionReq();
modifyReq.tradeCommand = TradeCommand.Modify;
modifyReq.orderType = Op.Buy;
modifyReq.symbol = "EURUSD";
modifyReq.ticket = ticket;
modifyReq.price = openPrice;
modifyReq.stopLoss = openPrice - 0.0050;   // 50 pips SL
modifyReq.takeProfit = openPrice + 0.0050; // 50 pips TP

TradeEvent modifyRes = (TradeEvent) client.sendRequest(modifyReq);
if (modifyRes.ticket > 0) {
    System.out.println("Order modified successfully!");
}
```

## Closing an Order

```java
OrderTransactionReq closeReq = new OrderTransactionReq();
closeReq.orderType = Op.Buy;
closeReq.symbol = "EURUSD";
closeReq.ticket = ticket;
closeReq.lots = 10; // Lots to close

if (isMarket) {
    closeReq.tradeCommand = TradeCommand.CloseMarket;
    closeReq.price = 0;
    closeReq.slippage = 0;
} else {
    closeReq.tradeCommand = TradeCommand.CloseInstant;
    closeReq.price = latestQuote.bid;
    closeReq.slippage = 30;
}

TradeEvent closeRes = (TradeEvent) client.sendRequest(closeReq);
System.out.println("Closed order ticket #" + closeRes.ticket);
```

## Pending Orders

To place limit or stop orders:

```java
OrderTransactionReq pendingReq = new OrderTransactionReq();
pendingReq.tradeCommand = TradeCommand.Pending;
pendingReq.orderType = Op.BuyLimit; // BuyLimit, SellLimit, BuyStop, SellStop
pendingReq.symbol = "EURUSD";
pendingReq.lots = 10;
pendingReq.price = targetLimitPrice;
pendingReq.stopLoss = stopLossPrice;
pendingReq.takeProfit = takeProfitPrice;

TradeEvent res = (TradeEvent) client.sendRequest(pendingReq);
```
