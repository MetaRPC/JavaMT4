# Getting Started with JavaMT4

> **Quick setup & overview** — connect to MetaTrader 4 from Java and make your first calls.

---

## What is JavaMT4?

**JavaMT4** is the Java SDK for the MRPC MetaTrader 4 gateway (`mt4.mrpc.pro:443`, gRPC over TLS). It has three layers, each building on the one below:

| Layer | Class | What it gives you |
|-------|-------|-------------------|
| 1 — low-level | [`MT4Account`](../MT4Account/MT4Account.Master.Overview.md) | raw gRPC calls, auto-reconnect, typed `…Data` returns |
| 2 — facade | [`MT4Service`](../MT4Service/MT4Service.Overview.md) | friendly args (string timeframe/sort, `side`/`type`), trade shortcuts |
| 3 — high-level | [`MT4Sugar`](../MT4Sugar/MT4Sugar.Master.Overview.md) | pip math, risk sizing, trailing/breakeven, watchers |

The generated gRPC stubs live under `mt4_term_api.*` and are compiled with the project; the three layers above wrap them so you rarely touch the stubs directly.

---

## Prerequisites

* **Java 17+** (the project targets `maven.compiler.release = 17`).
* **Maven** (`mvn`) or the Maven Daemon (`mvnd`) — the bundled `run.bat` auto-detects either.

The project builds the gRPC stubs from `package/mt4_term_api/*.java` together with the source; there is no separate SDK artifact to install — you run from the cloned repository.

---

## Get the code

```bash
git clone https://github.com/MetaRPC/JavaMT4.git
cd JavaMT4
```

Maven coordinates (if you build/install it locally): `pro.mrpc:mt4:1.0.0`.

---

## API key

1. **Sign up** free at [https://mrpc.pro/signup](https://mrpc.pro/signup).
2. **Create a key** at [https://mrpc.pro/my](https://mrpc.pro/my) under **API Keys**.
3. Put it in `appsettings.json` (below). `"TRIAL"` works for demo accounts.

---

## Configure — `appsettings.json`

The examples read a flat config file at the project root:

```json
{
  "user": 500123456,
  "password": "your_password",
  "serverName": "MetaQuotes-Demo",
  "baseSymbol": "EURUSD",
  "apiKey": "TRIAL"
}
```

| Key | Meaning |
|-----|---------|
| `user` | MT4 account login |
| `password` | account password |
| `serverName` | broker server name (connect-by-name) |
| `baseSymbol` | default symbol the examples trade |
| `apiKey` | MRPC API key (`"TRIAL"` for demo); `MRPC_API_KEY` env var overrides it |

An optional `grpcServer` (`host:port`) overrides the default gateway `mt4.mrpc.pro:443`.

---

## Run an example

`run.bat` compiles and runs the example launcher (`Program`), passing the menu number:

```bat
run.bat 1          REM low-level market data
run.bat 4          REM MT4Service demo
run.bat 6          REM MT4Sugar basics
run.bat stop       REM stop the Maven daemon
```

Run `run.bat` with no argument to see the full menu.

---

## Minimal code

Connecting directly through the low-level layer:

```java
import mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryData;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendOperationType;
import pro.mrpc.mt4.MT4Account;

public class App {
    public static void main(String[] args) throws Exception {
        // user, password, grpcServer (null -> mt4.mrpc.pro:443), id (null on first connect), apiKey
        try (MT4Account account = new MT4Account(500123456L, "your_password", null, null, "TRIAL")) {
            account.connectByServerName("MetaQuotes-Demo", 30);

            AccountSummaryData s = account.accountSummary();
            System.out.println("Balance: " + s.getAccountBalance() + " " + s.getAccountCurrency());

            var order = account.orderSend("EURUSD", OrderSendOperationType.OC_OP_BUY, 0.01);
            System.out.println("Ticket #" + order.getTicket() + " @ " + order.getPrice());

            account.disconnect(false);   // true also deletes the terminal instance
        }   // close() shuts the gRPC channel down
    }
}
```

For friendlier arguments wrap it in [`MT4Service`](../MT4Service/MT4Service.Overview.md); for one-liners, risk sizing and automation use [`MT4Sugar`](../MT4Sugar/MT4Sugar.Master.Overview.md).

---

## Reading order

1. **[Your First Project](Your_First_Project.md)** — a working program, start to finish.
2. **[MT4Account — Master Overview](../MT4Account/MT4Account.Master.Overview.md)** — the full low-level surface.
3. **[MT4Service](../MT4Service/MT4Service.Overview.md)** · **[MT4Sugar](../MT4Sugar/MT4Sugar.Master.Overview.md)** — the higher layers.
4. **[Streams](STREAMS.md)** — ticks, trades, profit and connection-state streams.
5. **[Errors](ERRORS.md)** — what a refused call looks like, and its codes.
