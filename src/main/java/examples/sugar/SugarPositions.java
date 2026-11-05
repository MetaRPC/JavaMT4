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

        // Open one position so the queries and watchers have something to show.
        long t = sugar.buyMarket(symbol, 0.01, null, null, null, null, "pos", null, null);
        System.out.println("opened #" + t);
        Thread.sleep(800);

        // ── Queries ──────────────────────────────────────────────
        // findOrders(): open orders, optionally filtered by symbol/magic/side/state/min-profit.
        List<Mt4TermApiAccountHelper.OpenedOrderInfo> all = sugar.findOrders(null, null, null, null, null);
        System.out.println("findOrders(all)  : " + all.size());
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : all) {
            System.out.printf(Locale.ROOT, "    #%d %s %s %.2f lot  P/L %.2f%n",
                    o.getTicket(), o.getSymbol(), o.getOrderType(), o.getLots(), o.getProfit());
        }
        // Same call with filters: only BUY, only market (state="open"), only this symbol.
        System.out.println("findOrders(buy/open, " + symbol + "): "
                + sugar.findOrders(symbol, null, "buy", "open", null).size());
        // ordersForSymbol(): a quick "all orders for this symbol" shortcut.
        System.out.println("ordersForSymbol  : " + sugar.ordersForSymbol(symbol).size());

        // exposureSummary(): account totals + net/long/short lots and P/L (per symbol and overall).
        MT4Sugar.ExposureSummary e = sugar.exposureSummary();
        System.out.printf(Locale.ROOT, "exposureSummary  : balance %.2f equity %.2f positions %d net %.2f lot pnl %.2f%n",
                e.balance, e.equity, e.openPositions, e.total.lotsNet, e.total.pnl);

        // positionsValue(): aggregated long/short/net lots and P/L for one symbol (market positions only).
        MT4Sugar.ExposureBucket pv = sugar.positionsValue(symbol, null);
        System.out.printf(Locale.ROOT, "positionsValue   : %s long %.2f short %.2f net %.2f pnl %.2f%n",
                symbol, pv.lotsLong, pv.lotsShort, pv.lotsNet, pv.pnl);
        // diagSnapshot(): a compact account+exposure+spreads blob (good for dashboards/logging).
        System.out.println("diagSnapshot     : " + sugar.diagSnapshot().keySet());

        // ── Watchers (background subscriptions) ──────────────────
        // Each watch* returns an id; the callback fires on every update; unwatch(id) stops it.
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
    }

    private SugarPositions() {
    }
}
