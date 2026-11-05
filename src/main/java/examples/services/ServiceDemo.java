package examples.services;

import mt4_term_api.Mt4TermApiAccountHelper;
import mt4_term_api.Mt4TermApiMarketInfo;
import pro.mrpc.mt4.MT4Service;

import java.util.List;
import java.util.Locale;

/**
 * MID-LEVEL · MT4Service.
 *
 * <p>{@link MT4Service} wraps {@link pro.mrpc.mt4.MT4Account}: the same calls, but with friendly arguments
 * (sort/timeframe strings, {@code side}/{@code type}) and trade shortcuts ({@code buyMarket} … {@code close}).
 * Run: {@code run.bat 4}. ⚠️ Places and closes one demo order.</p>
 */
public final class ServiceDemo {
    public static void run(MT4Service s, String symbol) throws Exception {
        // accountSummary(): account money picture (same as the low-level call).
        Mt4TermApiAccountHelper.AccountSummaryData acc = s.accountSummary();
        System.out.printf(Locale.ROOT, "accountSummary   : balance %.2f %s, equity %.2f%n",
                acc.getAccountBalance(), acc.getAccountCurrency(), acc.getAccountEquity());

        // quote() / quoteMany(): current prices for one or many symbols.
        Mt4TermApiMarketInfo.QuoteData q = s.quote(symbol);
        System.out.printf(Locale.ROOT, "quote            : %s %.5f/%.5f%n", symbol, q.getBid(), q.getAsk());
        System.out.println("quoteMany        : " + s.quoteMany(List.of(symbol, "GBPUSD")).getQuotesCount() + " symbols");

        // quoteHistory(): OHLC bars — note the friendly timeframe string "H1" (the service converts it to the enum).
        System.out.println("quoteHistory H1  : " + s.quoteHistory(symbol, "H1", null, null, 50).getHistoricalQuotesCount() + " bars");
        System.out.println("symbols          : " + s.symbols().getSymbolNameInfosCount());

        // symbolParamsMany() / tickValueWithSize(): symbol rules and tick value.
        Mt4TermApiAccountHelper.SymbolParamsManyData sp = s.symbolParamsMany(symbol);
        if (sp.getSymbolInfosCount() > 0) {
            Mt4TermApiAccountHelper.SymbolParamsManyInfo i = sp.getSymbolInfos(0);
            System.out.printf(Locale.ROOT, "symbolParamsMany : %s point=%.5f digits=%d%n", i.getSymbolName(), i.getPoint(), i.getDigits());
        }
        if (s.tickValueWithSize(List.of(symbol)).getInfosCount() > 0) {
            System.out.printf(Locale.ROOT, "tickValueWithSize: %.5f%n", s.tickValueWithSize(List.of(symbol)).getInfos(0).getTradeTickValue());
        }

        // symbolSelect() / serverTimezone(): Market Watch toggle and the server UTC offset.
        System.out.println("symbolSelect     : success=" + s.symbolSelect(symbol, true).getSuccess());
        System.out.println("serverTimezone   : UTC offset " + s.serverTimezone().getUtcOffsetSeconds() + "s");

        // Orders: note the friendly sort aliases ("open_desc", "close_desc") the service maps to enum values.
        System.out.println("openedOrders     : " + s.openedOrders("open_desc").getOrderInfosCount());
        System.out.println("openedOrdersTickets: " + s.openedOrdersTickets().getTicketsCount());
        System.out.println("ordersHistory    : total=" + s.ordersHistory("close_desc", null, null, null, null).getTotalCount());
        System.out.println("tradeStats       : " + brief(s.tradeStats(null)));
        System.out.println("equityHistory    : " + brief(s.equityHistory(null, null)));

        // ── Trade shortcuts (round-trip) ─────────────────────────
        // setTradeDefaults(): magic/deviation/comment applied to every following buy*/sell* shortcut.
        s.setTradeDefaults(1001, 3, "svc-demo");
        // buyMarket(): instant BUY; magic/deviation/comment come from the defaults above. Returns the ticket.
        long ticket = s.buyMarket(symbol, 0.01, null, null, null, null, null);
        System.out.println("buyMarket        : #" + ticket + " (magic/deviation/comment from defaults)");
        Thread.sleep(800);

        // modifySlTp(): attach absolute SL/TP prices (200 points around the entry).
        double point = sp.getSymbolInfos(0).getPoint();
        s.modifySlTp((int) ticket, q.getBid() - 200 * point, q.getAsk() + 200 * point);
        System.out.println("modifySlTp       : SL/TP attached");
        Thread.sleep(800);

        // close(): flatten the position.
        s.close((int) ticket);
        System.out.println("close            : #" + ticket + " closed");
    }

    private static String brief(Object o) {
        String s = String.valueOf(o).replaceAll("\\s+", " ").trim();
        if (s.isEmpty()) return "(empty)";
        return s.length() > 100 ? s.substring(0, 100) + "…" : s;
    }

    private ServiceDemo() {
    }
}
