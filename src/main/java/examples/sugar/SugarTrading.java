package examples.sugar;

import pro.mrpc.mt4.MT4Sugar;

import java.util.Locale;

/**
 * HIGH-LEVEL · Trading.
 *
 * <p>The full {@link MT4Sugar} order lifecycle with pip-based SL/TP on a DEMO account: generic {@code placeOrder},
 * market/pending entries, modify, breakeven, the close family, trailing/auto-breakeven workers and the
 * wait-for-fill/closed awaiters. Run: {@code run.bat 7}. ⚠️ Demo accounts only.</p>
 */
public final class SugarTrading {
    public static void run(MT4Sugar sugar, String symbol) throws Exception {
        // Default magic/deviation for all the orders below.
        sugar.setDefaults(symbol, 3001, 3.0, null, null);

        // placeOrder(): the generic entry (side, type, …). Here: market BUY 0.01, SL 50 pips, TP 100 pips.
        long t1 = sugar.placeOrder("buy", "market", symbol, null, 0.01, 50.0, 100.0, null, null, "place", null, null);
        System.out.println("placeOrder buy   : #" + t1);
        // waitFilled(): block until the order is live (market orders fill almost instantly).
        sugar.waitFilled((int) t1, 10_000L);
        System.out.println("waitFilled       : #" + t1 + " live");

        // modifySlTpByPips(): move SL/TP by a pip distance from the entry price.
        sugar.modifySlTpByPips((int) t1, 40.0, 120.0);
        System.out.println("modifySlTpByPips : 40p / 120p");
        // modifySlTpByPrice(): set SL/TP at absolute prices instead.
        MT4Sugar.Quote q = sugar.lastQuote(symbol);
        sugar.modifySlTpByPrice((int) t1, q.bid - sugar.pipsToPrice(symbol, 60), q.ask + sugar.pipsToPrice(symbol, 140));
        System.out.println("modifySlTpByPrice: absolute SL/TP");
        // closeByBreakeven(): move SL to the entry price (+2 pips) to make the trade risk-free.
        sugar.closeByBreakeven((int) t1, 2.0);
        System.out.println("closeByBreakeven : SL -> entry +2p");
        Thread.sleep(500);
        // close() + waitClosed(): flatten the position and confirm it's gone.
        sugar.close((int) t1);
        System.out.println("waitClosed       : " + sugar.waitClosed((int) t1, 10_000L).get("status"));

        // sellMarket(): the SELL counterpart of buyMarket.
        long t2 = sugar.sellMarket(symbol, 0.01, 50.0, 100.0, null, null, "sell", null, null);
        System.out.println("sellMarket       : #" + t2);
        Thread.sleep(500);
        sugar.close((int) t2);

        // close-by on an opposite pair: open a BUY and a SELL, then close one against the other.
        long a = sugar.buyMarket(symbol, 0.01, null, null, null, null, "cb", null, null);
        long b = sugar.sellMarket(symbol, 0.01, null, null, null, null, "cb", null, null);
        Thread.sleep(500);
        try {
            sugar.closeByTickets((int) a, (int) b);
            System.out.println("closeByTickets   : #" + a + " by #" + b);
        } catch (Exception e) {
            // Not all brokers allow close-by — fall back to a bulk close.
            System.out.println("closeByTickets   : rejected — closeAll instead");
            sugar.closeAll(symbol, null, null);
        }

        // Pending order round-trip: place a BUY LIMIT 100 pips below market, then cancel it.
        double entry = sugar.midPrice(symbol) - sugar.pipsToPrice(symbol, 100);
        long pending = sugar.buyLimit(symbol, entry, 0.01, 50.0, 100.0, null, null, "lim", null);
        System.out.printf(Locale.ROOT, "buyLimit @ %.5f : #%d%n", entry, pending);
        Thread.sleep(500);
        // cancelPendings(): remove pending orders (optionally filtered by symbol/magic).
        sugar.cancelPendings(symbol, null);
        System.out.println("cancelPendings   : cleared");

        // Automation workers: setTrailingStop / autoBreakeven run in the background until you stop them.
        long t3 = sugar.buyMarket(symbol, 0.01, null, null, null, null, "auto", null, null);
        String trail = sugar.setTrailingStop((int) t3, 30, 10.0); // trail SL 30 pips behind, step 10 pips
        System.out.println("setTrailingStop  : " + trail);
        Thread.sleep(1500);
        sugar.unsetTrailingStop(trail); // stop the trailing worker
        String be = sugar.autoBreakeven((int) t3, 20, 2.0); // after +20 pips, move SL to entry +2
        System.out.println("autoBreakeven    : " + be);
        Thread.sleep(1000);
        sugar.unwatch(be); // unwatch() stops any worker/subscription by id

        // closePartial(): close part of the volume; then closeAll() to flatten everything.
        try {
            sugar.closePartial((int) t3, 0.01);
        } catch (Exception ignored) {
        }
        sugar.closeAll(symbol, null, null);
        System.out.println("closeAll         : flat");
    }

    private SugarTrading() {
    }
}
