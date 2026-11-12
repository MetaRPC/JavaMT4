/*══════════════════════════════════════════════════════════════════════════════
 FILE: examples/sugar/SugarPositions.java - HIGH-LEVEL (MT4Sugar) POSITIONS DEMO
 PURPOSE:
   The MT4Sugar query surface (find / exposure / positions-value / diagnostics)
   plus all five live watchers (ticks, trades, opened orders, profit, tickets).
   Opens one small position so the queries and streams have data to show.

 🎯 WHO SHOULD USE THIS:
   • Developers building position dashboards / monitors
   • Learning how to query exposure and subscribe to live updates
   • Seeing the watch* / unwatch background-subscription pattern

 📚 WHAT THIS DEMO COVERS (3 sections):

   1. SETUP
      • buyMarket()            - open one test position for the demo

   2. QUERIES
      • findOrders()           - open orders, optionally filtered
      • ordersForSymbol()      - all orders for a symbol (shortcut)
      • exposureSummary()      - account totals + net/long/short/P&L
      • positionsValue()       - aggregated long/short/net for a symbol
      • diagSnapshot()         - compact account+exposure+spreads blob

   3. WATCHERS (background subscriptions)
      • watchTicks() / watchTrades() / watchOpenedOrders()
      • watchProfit() / watchTickets()
      • unwatch()              - stop a watcher by id

 🔄 API LEVELS:
   HIGH-LEVEL (MT4Sugar)   - THIS FILE: queries + one-call watchers
   MID-LEVEL  (MT4Service) - typed wrappers
   LOW-LEVEL  (MT4Account) - raw streams / order reads

 ⚠️  WARNING: opens and closes one REAL 0.01 position on your account - DEMO
   accounts only. The position is flattened (closeAll) before the demo returns.

 USAGE:
   run.bat 8      (or: java -cp ... Program 8)
══════════════════════════════════════════════════════════════════════════════*/
package examples.sugar;

import mt4_term_api.Mt4TermApiAccountHelper;
import pro.mrpc.mt4.MT4Sugar;

import java.util.List;
import java.util.Locale;

/**
 * HIGH-LEVEL · Positions.
 *
 * <p>The {@link MT4Sugar} query surface (find / exposure / positions-value / diagnostics) plus all five live
 * watchers (ticks, trades, opened orders, profit, tickets). Run: {@code run.bat 8}. ⚠️ Opens/closes a demo order.</p>
 */
public final class SugarPositions {
    public static void run(MT4Sugar sugar, String symbol) throws Exception {
        sugar.setDefaults(symbol, 4001, null, null, null);

        //region 1. SETUP
        // ══════════════════════════════════════════════════════════════
        // 1. SETUP
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // buyMarket()
        //   Open one position so the queries and watchers have something to
        //   show. Returns: ticket (long).
        // ────────────────────────────────────────────────────────────
        long t = sugar.buyMarket(symbol, 0.01, null, null, null, null, "pos", null, null);
        System.out.println("opened #" + t);
        Thread.sleep(800);
        //endregion

        //region 2. QUERIES
        // ══════════════════════════════════════════════════════════════
        // 2. QUERIES
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // findOrders()
        //   Open orders, optionally filtered by symbol/magic/side/state/
        //   min-profit. Returns: List<OpenedOrderInfo>.
        // ────────────────────────────────────────────────────────────
        List<Mt4TermApiAccountHelper.OpenedOrderInfo> all = sugar.findOrders(null, null, null, null, null);
        System.out.println("findOrders(all)  : " + all.size());
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : all) {
            System.out.printf(Locale.ROOT, "    #%d %s %s %.2f lot  P/L %.2f%n",
                    o.getTicket(), o.getSymbol(), o.getOrderType(), o.getLots(), o.getProfit());
        }
        // Same call with filters: only BUY, only market (state="open"), only this symbol.
        System.out.println("findOrders(buy/open, " + symbol + "): "
                + sugar.findOrders(symbol, null, "buy", "open", null).size());

        // ────────────────────────────────────────────────────────────
        // ordersForSymbol()
        //   A quick "all orders for this symbol" shortcut.
        //   Returns: List<OpenedOrderInfo>.
        // ────────────────────────────────────────────────────────────
        System.out.println("ordersForSymbol  : " + sugar.ordersForSymbol(symbol).size());

        // ────────────────────────────────────────────────────────────
        // exposureSummary()
        //   Account totals + net/long/short lots and P/L (per symbol & overall).
        //   Returns: ExposureSummary.
        // ────────────────────────────────────────────────────────────
        MT4Sugar.ExposureSummary e = sugar.exposureSummary();
        System.out.printf(Locale.ROOT, "exposureSummary  : balance %.2f equity %.2f positions %d net %.2f lot pnl %.2f%n",
                e.balance, e.equity, e.openPositions, e.total.lotsNet, e.total.pnl);

        // ────────────────────────────────────────────────────────────
        // positionsValue()
        //   Aggregated long/short/net lots and P/L for one symbol (market
        //   positions only). Returns: ExposureBucket.
        // ────────────────────────────────────────────────────────────
        MT4Sugar.ExposureBucket pv = sugar.positionsValue(symbol, null);
        System.out.printf(Locale.ROOT, "positionsValue   : %s long %.2f short %.2f net %.2f pnl %.2f%n",
                symbol, pv.lotsLong, pv.lotsShort, pv.lotsNet, pv.pnl);

        // ────────────────────────────────────────────────────────────
        // diagSnapshot()
        //   A compact account+exposure+spreads blob (good for dashboards/logs).
        //   Returns: Map of diagnostics.
        // ────────────────────────────────────────────────────────────
        System.out.println("diagSnapshot     : " + sugar.diagSnapshot().keySet());
        //endregion

        //region 3. WATCHERS (background subscriptions)
        // ══════════════════════════════════════════════════════════════
        // 3. WATCHERS (background subscriptions)
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // watchTicks/Trades/OpenedOrders/Profit/Tickets() + unwatch()
        //   Each watch* returns an id; the callback fires on every update;
        //   unwatch(id) stops it.
        // ────────────────────────────────────────────────────────────
        String w1 = sugar.watchTicks(symbol, d -> { }, null);         // price ticks
        String w2 = sugar.watchTrades(d -> { }, null);                // trade events
        String w3 = sugar.watchOpenedOrders(d -> { }, null);          // open-order changes
        String w4 = sugar.watchProfit(d -> { }, 1000, null);          // floating P/L, every 1s
        String w5 = sugar.watchTickets(d -> { }, 500, null);          // open tickets, every 0.5s
        System.out.println("watch started    : " + w1 + ", " + w2 + ", " + w3 + ", " + w4 + ", " + w5);
        Thread.sleep(3000);
        for (String s : List.of(w1, w2, w3, w4, w5)) sugar.unwatch(s);
        System.out.println("unwatch          : all stopped");

        // Flatten everything we opened.
        sugar.closeAll(symbol, null, null);
        System.out.println("closeAll         : flat");
        //endregion
    }

    private SugarPositions() {
    }
}
