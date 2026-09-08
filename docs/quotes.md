# Quotes & Market Data

JavaMT4 provides real-time streaming market prices and historical candlestick data.

## Subscribing to Live Quotes

To receive live tick updates, request quotes using the symbol code from the client's `SymbolTable`:

```java
// Resolve symbol code
short eurusdCode = client.getSymbolTable().getCode("EURUSD");
short gbpusdCode = client.getSymbolTable().getCode("GBPUSD");

SubscribeQuotesReq req = new SubscribeQuotesReq();
req.symbolCodes = new short[]{ eurusdCode, gbpusdCode };
client.sendRequestAsync(req);
```

### Handling Incoming Quotes

In your `MessageHandler`:

```java
@Override
public void onMessage(MetaTraderMessage message, MT4Client c) {
    if (message instanceof QuoteEvent quote) {
        System.out.printf("[%s] Bid: %.5f | Ask: %.5f | Time: %s%n",
                quote.symbol,
                quote.bid,
                quote.ask,
                java.time.Instant.ofEpochSecond(quote.time));
    }
}
```

## Unsubscribing from Quotes

To cancel active subscriptions, send an empty symbol code array:

```java
SubscribeQuotesReq unsubReq = new SubscribeQuotesReq();
unsubReq.symbolCodes = new short[0];
client.sendRequestAsync(unsubReq);
```

## Requesting Historical Bars

You can query historical OHLCV candlestick data across any timeframe:

```java
import io.mtapi.mt4.network.enums.Timeframe;
import io.mtapi.mt4.network.messages.req.HistoricalBarsReq;
import io.mtapi.mt4.network.messages.res.HistoricalBarsRes;
import io.mtapi.mt4.network.parts.BarRecord;

HistoricalBarsReq req = new HistoricalBarsReq();
req.symbol = "EURUSD";
req.timeframe = Timeframe.H1; // M1, M5, M15, M30, H1, H4, D1, W1, MN1
req.fromTime = (int) (System.currentTimeMillis() / 1000 - (7 * 86400)); // 7 days ago
req.count = 100;

HistoricalBarsRes res = (HistoricalBarsRes) client.sendRequest(req);

int digits = client.getSymbolTable().getDigits("EURUSD");
double point = Math.pow(10, -digits);

for (BarRecord bar : res.bars) {
    double open = bar.open * point;
    double high = bar.high * point;
    double low = bar.low * point;
    double close = bar.close * point;
    System.out.printf("Time: %s | Open: %.5f | High: %.5f | Low: %.5f | Close: %.5f | Vol: %.0f%n",
            java.time.Instant.ofEpochSecond(bar.time), open, high, low, close, bar.volume);
}
```
