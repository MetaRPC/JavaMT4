# Getting Started

This guide walks you through setting up a basic Java application using the JavaMT4 SDK to connect to a MetaTrader 4 server, fetch account details, and receive real-time price quotes.

## Prerequisites

- **Java 17** or higher
- **Gradle** or **Maven** build tool
- Valid MT4 Account credentials (Login, Password, Host, Port)

## 1. Minimal Working Example

Create a new Java class `App.java`:

```java
package com.example;

import io.mtapi.mt4.MT4Client;
import io.mtapi.mt4.MessageHandler;
import io.mtapi.mt4.exception.DecoderException;
import io.mtapi.mt4.network.MetaTraderMessage;
import io.mtapi.mt4.network.messages.req.SubscribeQuotesReq;
import io.mtapi.mt4.network.messages.res.QuoteEvent;
import io.mtapi.mt4.network.parts.AccountInfo;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        String host = "demo-mt4.broker.com";
        int port = 443;
        int login = 12345678;
        String password = "your_password";

        MT4Client client = new MT4Client(host, port, new MessageHandler() {
            @Override
            public void onConnected(MT4Client c) {
                System.out.println("Connected to MT4 server successfully!");
                AccountInfo acc = c.getAccountInfo();
                System.out.printf("Account: %s, Currency: %s, Balance: %.2f%n",
                        acc.name, acc.currency, acc.balance);

                // Subscribe to EURUSD ticks
                try {
                    short symbolCode = c.getSymbolTable().getCode("EURUSD");
                    SubscribeQuotesReq req = new SubscribeQuotesReq();
                    req.symbolCodes = new short[]{symbolCode};
                    c.sendRequestAsync(req);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMessage(MetaTraderMessage message, MT4Client c) {
                if (message instanceof QuoteEvent quote) {
                    System.out.printf("[%s] Bid: %.5f | Ask: %.5f%n",
                            quote.symbol, quote.bid, quote.ask);
                }
            }

            @Override
            public void onDisconnected(IOException exception, MT4Client c) {
                System.out.println("Disconnected: " + exception.getMessage());
            }

            @Override
            public void onReceiveFailure(DecoderException e) {
                System.err.println("Receive error: " + e.getMessage());
            }

            @Override
            public void onSendFailure(Exception e) {
                System.err.println("Send error: " + e.getMessage());
            }
        });

        // Initiate connection
        client.connect(login, password);

        // Keep running
        Thread.sleep(60000);
        client.disconnect();
    }
}
```

## 2. Running Your Application

To execute your application using Gradle:

```bash
./gradlew run
```

Or with Maven:

```bash
mvn compile exec:java -Dexec.mainClass="com.example.App"
```

## 3. Next Steps

- Explore [Connection & Authentication](connection.md) for reconnect strategies and SSL options.
- Learn about [Quotes & Market Data](quotes.md) for historic candle and tick streaming.
- Learn about [Trading & Orders](trading.md) for placing and managing orders.
