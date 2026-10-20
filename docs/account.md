# Account Information

## MT4Account (MRPC)

```java
AccountSummaryData summary = account.accountSummary();
System.out.printf("%d %s: balance %s %s, equity %s, credit %s, leverage 1:%d%n", summary.getAccountLogin(),
        summary.getAccountUserName(), summary.getAccountBalance(), summary.getAccountCurrency(),
        summary.getAccountEquity(), summary.getAccountCredit(), summary.getAccountLeverage());
```

MT4's `AccountSummary` has no margin values. They come with the `OnOpenedOrdersProfit` stream
(`SubscriptionServiceGrpc`): its `account_info` has `margin`, `free_margin` and `margin_level`, every
`timer_period_milliseconds`. Open orders and history: `OpenedOrders` and `OrdersHistory` of `AccountHelperGrpc`, through
`account.authorized(AccountHelperGrpc.newBlockingStub(account.getChannel()))`.

## MT4Client (MT4 server protocol, no MRPC)

> **`MT4Client` is a different client.** It speaks the MT4 server protocol straight to a broker's MT4 server (the
> broker's access point host and port), without MRPC and without an API key. It cannot connect to `mt4.mrpc.pro`; for the
> MRPC API use `MT4Account`.

`MT4Client` keeps the account, the open orders and the closed trades it receives from the MT4 server.

### Reading Account Summary

```java
import pro.mrpc.mt4.network.parts.AccountInfo;

AccountInfo info = client.getAccountInfo();

System.out.println("Account Holder: " + info.name);
System.out.println("Currency:       " + info.currency);
System.out.println("Balance:        " + info.balance);
System.out.println("Credit:         " + info.credit);
System.out.println("Leverage:       1:" + info.leverage);
System.out.println("Company:        " + info.company);
System.out.println("Account Group:  " + info.group);
System.out.println("Is Investor:    " + info.isInvestor());
```

### Shortcut Methods

```java
double balance = client.getBalance();
int leverage = client.getLeverage();
String currency = client.getAccountCurrency();
String company = client.getCompany();
```

### Viewing Open Positions & Orders

The client keeps an in-memory concurrent map of open positions and orders:

```java
import java.util.concurrent.ConcurrentHashMap;
import pro.mrpc.mt4.network.messages.res.OrderUpdateEvent;

ConcurrentHashMap<Integer, OrderUpdateEvent> openOrders = client.getOpenOrders();

for (OrderUpdateEvent order : openOrders.values()) {
    System.out.printf("Ticket #%d | Symbol: %s | Lots: %.2f | Type: %s | Open: %.5f | Profit: %.2f%n",
            order.ticket,
            order.symbol,
            order.lots / 100.0,
            order.orderType,
            order.openPrice,
            order.profit);
}
```

### Downloading Closed Order History

You can download closed trades over a specific time window:

```java
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import pro.mrpc.mt4.network.messages.res.OrderHistoryRes;
import pro.mrpc.mt4.network.parts.TradeRecord;

int oneYearAgo = (int) Instant.now().minus(365, ChronoUnit.DAYS).getEpochSecond();
int oneMonthAhead = (int) Instant.now().plus(30, ChronoUnit.DAYS).getEpochSecond();

OrderHistoryRes history = client.requestOrderHistory(oneYearAgo, oneMonthAhead);

for (TradeRecord trade : history.trades) {
    System.out.printf("History Ticket #%d: %s | Profit: %.2f | Closed at: %s%n",
            trade.ticket,
            trade.symbol,
            trade.profit,
            Instant.ofEpochSecond(trade.closeTime));
}
```
