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

import pro.mrpc.mt4.MT4Client;
import pro.mrpc.mt4.MessageHandler;
import pro.mrpc.mt4.exception.DecoderException;
import pro.mrpc.mt4.network.MetaTraderMessage;
import pro.mrpc.mt4.network.messages.req.SubscribeQuotesReq;
import pro.mrpc.mt4.network.messages.res.QuoteEvent;
import pro.mrpc.mt4.network.parts.AccountInfo;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        String host = "demo-mt4.mrpc.pro";
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


> **Authentication Note**: Connecting to `mt4.mrpc.pro:443` requires a valid MetaRPC API key. Register for free at [https://mrpc.pro/signup](https://mrpc.pro/signup) and generate your token in [https://mrpc.pro/my](https://mrpc.pro/my).



---

## 🆔 Account ID Generation (`GetId`)

> ⚠️ **Important**: You must generate your deterministic account ID with `GetId` **firstly** before connecting or streaming.

MetaRPC endpoints require two essential credentials for all operations:
1. **`APIKey`**: Your personal authentication token from [https://mrpc.pro/my](https://mrpc.pro/my) (obtained by registering at [https://mrpc.pro/signup](https://mrpc.pro/signup)). Sent in the `APIKey` header.
2. **`id`**: A deterministic account GUID generated from your MetaTrader `user` (login number) and `password`. The same credentials always produce the exact same GUID.

### Calling GetId

#### Via REST API:
```bash
curl -X GET "https://mt4.mrpc.pro/GetId?user=YOUR_LOGIN&password=YOUR_PASSWORD" \
     -H "APIKey: YOUR_API_KEY"
```

Response:
```json
{
  "data": {
    "id": "e8d91060-c3d3-4f4d-8d2a-9e1b2c3d4e5f"
  }
}
```

#### Via gRPC:
Send a `GetIdRequest` with `user` and `password` to the connection service before calling `ConnectEx`.

Use this returned `id` GUID in the `id` header or session parameter for `ConnectEx`, `AccountSummary`, and all other terminal operations.
