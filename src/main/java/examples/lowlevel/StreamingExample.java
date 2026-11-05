package examples.lowlevel;

import mt4_term_api.Mt4TermApiTradingHelper;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendOperationType;
import pro.mrpc.mt4.MT4Account;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * LOW-LEVEL · Streaming (subscriptions).
 *
 * <p>Every {@link MT4Account} server stream. Each {@code on…} call returns a {@link MT4Account.Subscription}
 * (an {@link AutoCloseable}); closing it stops the stream. We open a small position first so the order/profit/
 * trade streams have something to report. Run: {@code run.bat 3}. ⚠️ Demo accounts only.</p>
 */
public final class StreamingExample {
    public static void run(MT4Account account, String symbol) throws Exception {
        // Open a 0.01 position so onTrade / onOpenedOrders* / onOpenedOrdersProfit have live data to emit.
        Mt4TermApiTradingHelper.OrderSendData pos = account.orderSend(symbol, OrderSendOperationType.OC_OP_BUY, 0.01);
        System.out.println("opened #" + pos.getTicket() + " to feed the streams\n");
        try {
            // onSymbolTick(): live bid/ask updates. We wait for 3 ticks, then the try-with-resources closes the stream.
            System.out.println("── onSymbolTick (3 ticks / 20s) ──");
            CountDownLatch ticks = new CountDownLatch(3);
            try (MT4Account.Subscription s = account.onSymbolTick(List.of(symbol), d -> {
                var t = d.getSymbolTick(); // OnSymbolTickData wraps the actual tick info
                System.out.printf(Locale.ROOT, "  tick %s %.5f/%.5f%n", t.getSymbol(), t.getBid(), t.getAsk());
                ticks.countDown();
            }, e -> { while (ticks.getCount() > 0) ticks.countDown(); })) {
                ticks.await(20, TimeUnit.SECONDS);
            }

            // onOpenedOrdersTickets(): the set of open tickets, polled server-side every 500 ms.
            listen("onOpenedOrdersTickets (5s)", account.onOpenedOrdersTickets(500, d -> System.out.println("  " + brief(d)), e -> { }), 5);
            // onOpenedOrdersProfit(): live floating-P/L updates every 1000 ms.
            listen("onOpenedOrdersProfit (5s)", account.onOpenedOrdersProfit(1000, d -> System.out.println("  " + brief(d)), e -> { }), 5);
            // onTrade(): trade events (new order, modify, close, deal).
            listen("onTrade (5s)", account.onTrade(d -> System.out.println("  " + brief(d)), e -> { }), 5);
            // onConnectState(): terminal connection-state changes.
            listen("onConnectState (5s)", account.onConnectState(null, d -> System.out.println("  " + brief(d)), e -> { }), 5);
        } finally {
            // Always flatten what we opened for the demo.
            account.orderCloseDelete(pos.getTicket(), null, null, null);
            System.out.println("\nclosed #" + pos.getTicket());
        }
    }

    /** Runs a subscription for a few seconds, then closes it (try-with-resources cancels the stream). */
    private static void listen(String title, MT4Account.Subscription sub, int seconds) throws InterruptedException {
        System.out.println("── " + title + " ──");
        try (MT4Account.Subscription s = sub) {
            Thread.sleep(seconds * 1000L);
        }
    }

    private static String brief(Object o) {
        String s = String.valueOf(o).replaceAll("\\s+", " ").trim();
        if (s.isEmpty()) return "(empty update)";
        return s.length() > 120 ? s.substring(0, 120) + "…" : s;
    }

    private StreamingExample() {
    }
}
