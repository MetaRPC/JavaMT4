# Streams

The API pushes data over gRPC server streams. Ticks have their own method in the client: [`account.onSymbolTick(symbols, onTick, onError)`](../quotes.md). Every other stream is a
method of `SubscriptionServiceGrpc`:

| Stream | What it sends |
|---|---|
| [`OnSymbolTick`](../API_Reference/methods.md#subscriptionserviceonsymboltick) | ticks of the symbols |
| [`OnTrade`](../API_Reference/methods.md#subscriptionserviceontrade) | every change of orders, with the account values |
| [`OnOpenedOrdersProfit`](../API_Reference/methods.md#subscriptionserviceonopenedordersprofit) | the profit of the open orders and the account values (with margin), every `timer_period_milliseconds` |
| [`OnOpenedOrdersTickets`](../API_Reference/methods.md#subscriptionserviceonopenedorderstickets) | the tickets of the open orders, every `pull_interval_milliseconds` |
| [`OnConnectState`](../API_Reference/methods.md#connectiononconnectstate) | the state of your terminals (`Connected`, `Unreachable`, `Stopped`, ...) |
| [`OnJournal`](../API_Reference/methods.md#logsonjournal), [`OnExperts`](../API_Reference/methods.md#logsonexperts) | the terminal's journal and Experts log (a new stream starts with what is already written) |

Their requests and messages: [API methods](../API_Reference/methods.md#subscriptionservice).

## Using a stream

OnOpenedOrdersProfit, the account's equity, margin and profit every second:

```java
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitReply;
import mt4_term_api.Mt4TermApiSubscriptions.OnOpenedOrdersProfitRequest;
import mt4_term_api.SubscriptionServiceGrpc;

// account: a connected MT4Account
var subscriptions = account.authorized(SubscriptionServiceGrpc.newStub(account.getChannel()));
var request = OnOpenedOrdersProfitRequest.newBuilder().setTimerPeriodMilliseconds(1000).build();

Context.CancellableContext stream = Context.current().withCancellation();
stream.run(() -> subscriptions.onOpenedOrdersProfit(request, new StreamObserver<OnOpenedOrdersProfitReply>() {
    @Override
    public void onNext(OnOpenedOrdersProfitReply reply) {
        if (reply.hasError()) {                                   // the API answered with an error
            System.err.println("stream error: " + reply.getError().getErrorCode());
            return;
        }
        if (!reply.hasData()) return;                             // a heartbeat (after 30 s without data)
        var info = reply.getData().getAccountInfo();
        System.out.println("equity " + info.getEquity() + ", margin " + info.getMargin() + ", profit " + info.getProfit());
    }

    @Override
    public void onError(Throwable t) {
        if (Status.fromThrowable(t).getCode() == Status.Code.CANCELLED) return;   // stream.cancel()
        System.err.println("stream failed: " + t.getMessage());                    // open it again
    }

    @Override
    public void onCompleted() {
    }
}));

// ... when done
stream.cancel(null);
```

- Every message has `data` or `error`. A message with **neither** is a heartbeat: after 30 seconds without data the
  server sends one so that proxies keep the connection open. Skip it.
- An `error` message reports a problem with the stream's request (for example `NO_CHARTS_OPENED_FOR_SYMBOLS` for a
  symbol the terminal does not have); the stream may end after it.
- `stream.cancel(null)` ends it (`onError` then gets `CANCELLED`, which is not a failure).
- A stream does not resume by itself. When it fails or ends while you still need it, open it again; when calls answer
  `TERMINAL_INSTANCE_NOT_FOUND`, the terminal is gone: connect first.
- Opening a stream counts as a call toward the per-terminal limits, the messages it delivers do not; the number of tick
  streams per terminal is limited too: [Errors & Limits](https://mrpc.pro/docs-legacy/mt4-client-api#errors-limits).

## In a browser

Browsers cannot speak gRPC to the API (no gRPC-Web is served). Every stream is also a WebSocket on its REST path, with the
terminal id and key in the query: `wss://mt4.mrpc.pro/OnOpenedOrdersProfit?id=<terminal id>&apikey=<key>&timerPeriodMilliseconds=1000`.
See the [client guide](https://mrpc.pro/docs-legacy/mt4-client-api#step-7-real-time-streams-websocket).
