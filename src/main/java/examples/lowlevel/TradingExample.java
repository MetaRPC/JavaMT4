package examples.lowlevel;

import mt4_term_api.Mt4TermApiMarketInfo;
import mt4_term_api.Mt4TermApiTradingHelper;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendOperationType;
import pro.mrpc.mt4.MT4Account;

import java.util.List;
import java.util.Locale;

/**
 * LOW-LEVEL · Trading operations.
 *
 * <p>Every {@link MT4Account} trading call, as a safe round-trip on a DEMO account: open a market order,
 * attach SL/TP, close it, then show {@code orderCloseBy} on an opposite pair. Everything is cleaned up at the
 * end. Run: {@code run.bat 2}. ⚠️ Demo accounts only.</p>
 */
public final class TradingExample {
    public static void run(MT4Account account, String symbol) throws Exception {
        // We need the symbol's point size to place SL/TP a sensible distance away.
        double point = account.symbolParamsMany(symbol).getSymbolInfos(0).getPoint();

        // orderSend(): open a market BUY of 0.01 lot. OC_OP_BUY = buy at the current ask.
        Mt4TermApiTradingHelper.OrderSendData o = account.orderSend(symbol, OrderSendOperationType.OC_OP_BUY, 0.01);
        System.out.printf(Locale.ROOT, "orderSend        : BUY #%d 0.01 lot @ %.5f%n", o.getTicket(), o.getPrice());
        Thread.sleep(800);

        // orderModify(): attach protection — SL 200 points below, TP 200 points above (null price = keep entry).
        Mt4TermApiMarketInfo.QuoteData q = account.quote(symbol);
        double sl = q.getBid() - 200 * point;
        double tp = q.getAsk() + 200 * point;
        account.orderModify(o.getTicket(), null, sl, tp, null);
        System.out.printf(Locale.ROOT, "orderModify      : #%d SL=%.5f TP=%.5f%n", o.getTicket(), sl, tp);
        Thread.sleep(800);

        // orderCloseDelete(): close the open position (same call also deletes a pending order).
        account.orderCloseDelete(o.getTicket(), null, null, null);
        System.out.println("orderCloseDelete : #" + o.getTicket() + " closed");

        // orderCloseBy(): close one position against an opposite one (hedge close).
        // First open a BUY and a SELL, then close the BUY *by* the SELL.
        Mt4TermApiTradingHelper.OrderSendData b = account.orderSend(symbol, OrderSendOperationType.OC_OP_BUY, 0.01);
        Mt4TermApiTradingHelper.OrderSendData s = account.orderSend(symbol, OrderSendOperationType.OC_OP_SELL, 0.01);
        System.out.printf(Locale.ROOT, "orderCloseBy     : opened pair BUY #%d / SELL #%d%n", b.getTicket(), s.getTicket());
        Thread.sleep(500);
        try {
            account.orderCloseBy(b.getTicket(), s.getTicket());
            System.out.println("orderCloseBy     : closed #" + b.getTicket() + " by #" + s.getTicket());
        } catch (Exception e) {
            // Some brokers don't allow close-by — fall back to closing each order individually.
            System.out.println("orderCloseBy     : rejected (" + e.getMessage() + ") — closing both individually");
            for (int t : List.of(b.getTicket(), s.getTicket())) {
                try {
                    account.orderCloseDelete(t, null, null, null);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private TradingExample() {
    }
}
