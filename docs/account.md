# Account Information

The `MT4Client` provides continuous access to account balance, equity, margin requirements, open orders, and closed trades history.

## Reading Account Summary

```java
import io.mtapi.mt4.network.parts.AccountInfo;

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

## Shortcut Methods

```java
double balance = client.getBalance();
int leverage = client.getLeverage();
String currency = client.getAccountCurrency();
String company = client.getCompany();
```

## Viewing Open Positions & Orders

The client keeps an in-memory concurrent map of open positions and orders:

```java
import java.util.concurrent.ConcurrentHashMap;
import io.mtapi.mt4.network.messages.res.OrderUpdateEvent;

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

## Downloading Closed Order History

You can download closed trades over a specific time window:

```java
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import io.mtapi.mt4.network.messages.res.OrderHistoryRes;
import io.mtapi.mt4.network.parts.TradeRecord;

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
