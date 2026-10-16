# Your First Project in 10 Minutes (Java)

> **Hands-on Quick Start** - A working MetaTrader 4 project with JavaMT4 from scratch.

---

## Step 0: API key

1. Register free at [https://mrpc.pro/signup](https://mrpc.pro/signup).
2. Create your API key at [https://mrpc.pro/my](https://mrpc.pro/my).
3. Put it in `MRPC_API_KEY`.

---

## Step 1: Install the SDK

Gradle, from [JitPack](https://jitpack.io/#MetaRPC/JavaMT4) (built from this repository on request):

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
dependencies {
    implementation 'com.github.MetaRPC:JavaMT4:fa9acfb453'   // a commit of this repository; main-SNAPSHOT = the latest
}
```

Maven: add the repository `https://jitpack.io` and the dependency `com.github.MetaRPC` / `JavaMT4` / `fa9acfb453`.
The jar brings gRPC and the generated `mt4_term_api` stubs along; Java 17+. It is not on Maven Central.

Or build it yourself: `git clone https://github.com/MetaRPC/JavaMT4.git && cd JavaMT4 && ./gradlew publishToMavenLocal` installs
`pro.mrpc:mt4:1.0.0` into your local Maven repository (`mavenLocal()`).

Create a Gradle project (`gradle init --type java-application`) with the repositories and the dependency above.

---

## Step 2: Write the program

`src/main/java/App.java` (it connects, reads the account, buys 0.01 lot of EURUSD and disconnects):

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

## Step 3: Run it

```bash
export MRPC_API_KEY=your-api-key MT4_LOGIN=your-login MT4_PASSWORD=your-password
./gradlew run
```

No account yet? Open a demo account:
`curl -H "APIKey: your-api-key" "https://mt4.mrpc.pro/DemoAccount/Open?server=TeleTrade-Demo"` returns its login and password.

---

## Next steps

- **[Quotes](../quotes.md)** - stream ticks with `onSymbolTick`.
- **[API Reference](../api.md)** - everything `MT4Account` does, and the generated stubs for the rest of the API.
- **[Streams](STREAMS.md)** - the other streams.
