/*══════════════════════════════════════════════════════════════════════════════
 FILE: examples/lowlevel/StreamingExample.java - LOW-LEVEL MT4 STREAMING DEMO
 PURPOSE:
   Demonstrates every MT4Account server stream. Each on...() call returns a
   MT4Account.Subscription (AutoCloseable); closing it stops the stream. A small
   position is opened first so the order/profit/trade streams have live data.

 🎯 WHO SHOULD USE THIS:
   • Developers learning the low-level MT4 streaming API
   • Building live tick / P&L / trade-event monitors
   • Seeing how subscriptions are opened and cleanly closed

 📚 WHAT THIS DEMO COVERS (3 sections):

   1. SETUP
      • orderSend()              - open one 0.01 position to feed the streams

   2. STREAMS
      • onSymbolTick()           - live Bid/Ask updates (waits for 3 ticks)
      • onOpenedOrdersTickets()  - set of open tickets, polled every 500 ms
      • onOpenedOrdersProfit()   - floating-P/L updates every 1000 ms
      • onTrade()                - trade events (new / modify / close / deal)
      • onConnectState()         - terminal connection-state changes

   3. CLEANUP
      • orderCloseDelete()       - always flatten the feed position at the end

 🔄 API LEVELS:
   LOW-LEVEL  (MT4Account) - THIS FILE: raw async subscriptions
   MID-LEVEL  (MT4Service) - typed streaming wrappers
   HIGH-LEVEL (MT4Sugar)   - callback-based watch helpers

 USAGE:
   run.bat 3      (or: java -cp ... Program 3)

 ⚠️  WARNING: opens one real 0.01 position to feed the streams, then closes it
     at the end. DEMO accounts only.
══════════════════════════════════════════════════════════════════════════════*/
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
        //region 1. SETUP
        // ══════════════════════════════════════════════════════════════
        // 1. SETUP - open a position so the streams have something to emit
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // orderSend(symbol, OC_OP_BUY, 0.01)
        //   Open a 0.01 market BUY so onTrade / onOpenedOrders* /
        //   onOpenedOrdersProfit have live data to emit.
        //   Returns: OrderSendData (ticket, price, ...).
        // ────────────────────────────────────────────────────────────
        Mt4TermApiTradingHelper.OrderSendData pos = account.orderSend(symbol, OrderSendOperationType.OC_OP_BUY, 0.01);
        System.out.println("opened #" + pos.getTicket() + " to feed the streams\n");
        //endregion
        try {
            //region 2. STREAMS
            // ══════════════════════════════════════════════════════════════
            // 2. STREAMS
            // ══════════════════════════════════════════════════════════════

            // ────────────────────────────────────────────────────────────
            // onSymbolTick(symbols, onData, onError)
            //   Live Bid/Ask updates. We wait for 3 ticks, then the
            //   try-with-resources closes (cancels) the stream.
            //   Returns: Subscription (AutoCloseable).
            // ────────────────────────────────────────────────────────────
            System.out.println("── onSymbolTick (3 ticks / 20s) ──");
            CountDownLatch ticks = new CountDownLatch(3);
            try (MT4Account.Subscription s = account.onSymbolTick(List.of(symbol), d -> {
                var t = d.getSymbolTick(); // OnSymbolTickData wraps the actual tick info
                System.out.printf(Locale.ROOT, "  tick %s %.5f/%.5f%n", t.getSymbol(), t.getBid(), t.getAsk());
                ticks.countDown();
            }, e -> { while (ticks.getCount() > 0) ticks.countDown(); })) {
                ticks.await(20, TimeUnit.SECONDS);
            }

            // ────────────────────────────────────────────────────────────
            // onOpenedOrdersTickets(intervalMs, onData, onError)
            //   The set of open tickets, polled server-side every 500 ms.
            //   Returns: Subscription (closed by listen() after a few seconds).
            // ────────────────────────────────────────────────────────────
            listen("onOpenedOrdersTickets (5s)", account.onOpenedOrdersTickets(500, d -> System.out.println("  " + brief(d)), e -> { }), 5);

            // ────────────────────────────────────────────────────────────
            // onOpenedOrdersProfit(intervalMs, onData, onError)
            //   Live floating-P/L updates every 1000 ms.
            //   Returns: Subscription.
            // ────────────────────────────────────────────────────────────
            listen("onOpenedOrdersProfit (5s)", account.onOpenedOrdersProfit(1000, d -> System.out.println("  " + brief(d)), e -> { }), 5);

            // ────────────────────────────────────────────────────────────
            // onTrade(onData, onError)
            //   Trade events (new order, modify, close, deal).
            //   Returns: Subscription.
            // ────────────────────────────────────────────────────────────
            listen("onTrade (5s)", account.onTrade(d -> System.out.println("  " + brief(d)), e -> { }), 5);

            // ────────────────────────────────────────────────────────────
            // onConnectState(..., onData, onError)
            //   Terminal connection-state changes.
            //   Returns: Subscription.
            // ────────────────────────────────────────────────────────────
            listen("onConnectState (5s)", account.onConnectState(null, d -> System.out.println("  " + brief(d)), e -> { }), 5);
            //endregion
        } finally {
            //region 3. CLEANUP
            // ══════════════════════════════════════════════════════════════
            // 3. CLEANUP
            // ══════════════════════════════════════════════════════════════

            // ────────────────────────────────────────────────────────────
            // orderCloseDelete(ticket, ...)
            //   Always flatten the position we opened for the demo.
            // ────────────────────────────────────────────────────────────
            account.orderCloseDelete(pos.getTicket(), null, null, null);
            System.out.println("\nclosed #" + pos.getTicket());
            //endregion
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
