# Getting Started

Requires Java 17 or newer, an MRPC API key and an MT4 account (any broker).

## Install

```bash
git clone https://github.com/MetaRPC/JavaMT4.git
cd JavaMT4
./gradlew publishToMavenLocal      # installs pro.mrpc:mt4:1.0.0 into ~/.m2
```

Then, with `mavenLocal()` among your repositories: `implementation 'pro.mrpc:mt4:1.0.0'` (it brings gRPC and the generated
`mt4_term_api` stubs along). The SDK is not on Maven Central.

## A first program

```java
import mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryData;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendData;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendOperationType;
import pro.mrpc.mt4.MT4Account;

public class App {
    public static void main(String[] args) throws Exception {
        try (MT4Account account = new MT4Account(System.getenv("MRPC_API_KEY"))) {      // mt4.mrpc.pro:443
            account.connect(Long.parseLong(System.getenv("MT4_LOGIN")), System.getenv("MT4_PASSWORD"), "TeleTrade-Demo", 120);
            try {
                AccountSummaryData summary = account.accountSummary();
                System.out.println("Balance: " + summary.getAccountBalance() + " " + summary.getAccountCurrency());

                OrderSendData order = account.orderSend("EURUSD", OrderSendOperationType.OC_OP_BUY, 0.01);
                System.out.println("Ticket #" + order.getTicket() + " at " + order.getPrice());
            } finally {
                account.disconnect(false);   // true also deletes the terminal
            }
        }
    }
}
```

The same with a price, a closed order and ticks is
[`examples/Quickstart.java`](https://github.com/MetaRPC/JavaMT4/blob/main/src/main/java/examples/Quickstart.java)
(`./gradlew run` with `MRPC_API_KEY`, `MT4_LOGIN`, `MT4_PASSWORD`, `MT4_SERVER` set).

## Next

- [Connection](connection.md), [Account](account.md), [Quotes](quotes.md), [Trading](trading.md), [API Reference](api.md).

## API key and terminal id

- **API key**: from [https://mrpc.pro/my](https://mrpc.pro/my) (sign up free at [https://mrpc.pro/signup](https://mrpc.pro/signup)).
  Pass it to `new MT4Account(apiKey)` or set `MRPC_API_KEY`; without one the client sends `TRIAL`.
- **Terminal id**: `connect()` receives it from the server, keeps it (`getTerminalId()`) and sends it with every later call
  and stream. `attach(id)` uses a terminal that is already connected.
