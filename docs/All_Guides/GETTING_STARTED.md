# Getting Started with JavaMT4

> **Quick Setup & Overview** - Connect to MetaTrader 4 from Java in a few minutes.

---

## What is JavaMT4?

**JavaMT4** is the Java client of the MRPC MT4 API (`mt4.mrpc.pro:443`, gRPC over TLS):

- **`MT4Account`**: connect, account, quotes, orders, tick stream, disconnect (see [API Reference](../api.md)).
- **The generated gRPC stubs** of the whole API (`mt4_term_api.*Grpc`), for everything else, with
  `account.authorized(stub)` adding the API key and terminal id.

The package also has `MT4Client`, a client of the MT4 server protocol that connects straight to a broker's MT4 server
(no MRPC). The other pages of these guides describe the API itself (methods, enums, return codes, streams); some of their
code samples are in Python, the calls and messages are the same from Java.

---

## Installation

```bash
git clone https://github.com/MetaRPC/JavaMT4.git
cd JavaMT4
./gradlew publishToMavenLocal      # installs pro.mrpc:mt4:1.0.0 into ~/.m2
```

Then, with `mavenLocal()` among your repositories: `implementation 'pro.mrpc:mt4:1.0.0'` (it brings gRPC and the generated
`mt4_term_api` stubs along). The SDK is not on Maven Central.

---

## API key

1. **Sign up** free at [https://mrpc.pro/signup](https://mrpc.pro/signup).
2. **Create a key** at [https://mrpc.pro/my](https://mrpc.pro/my) under **API Keys**.
3. Pass it to `new MT4Account(apiKey)` or set `MRPC_API_KEY`.

---

## API key and terminal id

- **API key**: from [https://mrpc.pro/my](https://mrpc.pro/my) (sign up free at [https://mrpc.pro/signup](https://mrpc.pro/signup)).
  Pass it to `new MT4Account(apiKey)` or set `MRPC_API_KEY`; without one the client sends `TRIAL`.
- **Terminal id**: `connect()` receives it from the server, keeps it (`getTerminalId()`) and sends it with every later call
  and stream. `attach(id)` uses a terminal that is already connected.

## Minimal example

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

---

## Reading order

1. **[Your First Project](Your_First_Project.md)** - a working project in 10 minutes.
2. **[API Reference](../api.md)** - what `MT4Account` does, and how to call the rest of the API.
3. **[gRPC Streaming](GRPC_STREAM_MANAGEMENT.md)** - ticks, trades and other streams.
4. **[Return Codes](RETURN_CODES_REFERENCE.md)** - broker and API return codes.
