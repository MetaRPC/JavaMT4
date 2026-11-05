# Your First Project in 10 Minutes (Java)

> **Hands-on quick start** — connect, read the account, place and close a demo order.

---

## Step 0 — API key & demo account

1. Register free at [https://mrpc.pro/signup](https://mrpc.pro/signup) and create a key at [https://mrpc.pro/my](https://mrpc.pro/my).
2. No MT4 account yet? Open a demo:
   `curl -H "APIKey: your-api-key" "https://mt4.mrpc.pro/DemoAccount/Open?server=MetaQuotes-Demo"` returns a login and password.

---

## Step 1 — Get the project

```bash
git clone https://github.com/MetaRPC/JavaMT4.git
cd JavaMT4
```

You need **Java 17+** and **Maven** (`mvn`) or **mvnd**. The generated gRPC stubs build together with the source — nothing else to install.

---

## Step 2 — Configure `appsettings.json`

Edit the file at the project root with your credentials:

```json
{
  "user": 500123456,
  "password": "your_password",
  "serverName": "MetaQuotes-Demo",
  "baseSymbol": "EURUSD",
  "apiKey": "TRIAL"
}
```

(`apiKey` can stay `"TRIAL"` on demo; the `MRPC_API_KEY` env var overrides it.)

---

## Step 3 — Run a bundled example

```bat
run.bat 1
```

`run.bat` compiles the project and runs the launcher; `1` selects the low-level market-data example, which connects and prints the account summary, symbols, quotes and open orders. Run `run.bat` with no argument to see the whole menu (low-level / service / sugar / streaming examples).

---

## Step 4 — Write your own

Create a small `main` that connects, reads the account, buys 0.01 lot of EURUSD and closes it. This uses the high-level [`MT4Sugar`](../MT4Sugar/MT4Sugar.Master.Overview.md) layer so SL/TP are in pips and lots/prices are normalized for you:

```java
import pro.mrpc.mt4.MT4Account;
import pro.mrpc.mt4.MT4Service;
import pro.mrpc.mt4.MT4Sugar;

public class App {
    public static void main(String[] args) throws Exception {
        try (MT4Account account = new MT4Account(500123456L, "your_password", null, null, "TRIAL")) {
            account.connectByServerName("MetaQuotes-Demo", 30);

            MT4Sugar sugar = new MT4Sugar(new MT4Service(account));

            var acc = account.accountSummary();
            System.out.printf("Balance: %.2f %s%n", acc.getAccountBalance(), acc.getAccountCurrency());

            long ticket = sugar.buyMarket("EURUSD", 0.01, 50.0, 100.0, null, null, "first", null, null);
            System.out.println("opened #" + ticket);
            sugar.waitFilled((int) ticket, 10_000L);

            Thread.sleep(1000);
            sugar.close((int) ticket);
            System.out.println("closed #" + ticket);

            account.disconnect(false);
        }
    }
}
```

> ⚠️ This places a **real order on the connected (demo) account** — keep it to 0.01 lots and always close what you open.

---

## Next steps

* **[MT4Account — Master Overview](../MT4Account/MT4Account.Master.Overview.md)** — every low-level method, grouped.
* **[MT4Sugar — Master Overview](../MT4Sugar/MT4Sugar.Master.Overview.md)** — pip-based entry, risk sizing, trailing, watchers.
* **[Streams](STREAMS.md)** — react to ticks, trades and profit in real time.
* **[Errors](ERRORS.md)** — handle a refused order cleanly.
