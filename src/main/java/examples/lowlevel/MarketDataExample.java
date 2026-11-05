package examples.lowlevel;

import mt4_term_api.Mt4TermApiAccountHelper;
import mt4_term_api.Mt4TermApiMarketInfo;
import pro.mrpc.mt4.MT4Account;

import java.util.List;
import java.util.Locale;

/**
 * LOW-LEVEL · General (read-only) operations.
 *
 * <p>Walks through every informational {@link MT4Account} method — nothing here places a trade. Each call
 * returns a protobuf {@code …Data} message; the comments explain what each one gives you. Run: {@code run.bat 1}.</p>
 */
public final class MarketDataExample {
    public static void run(MT4Account account, String symbol) {
        // ── Account ──────────────────────────────────────────────
        // accountSummary(): one call with the account's money picture — balance, equity, leverage, currency, login.
        Mt4TermApiAccountHelper.AccountSummaryData acc = account.accountSummary();
        System.out.printf(Locale.ROOT, "accountSummary   : #%d (%s) balance %.2f %s, equity %.2f, leverage 1:%d%n",
                acc.getAccountLogin(), acc.getAccountUserName(), acc.getAccountBalance(),
                acc.getAccountCurrency(), acc.getAccountEquity(), acc.getAccountLeverage());

        // ── Market info ──────────────────────────────────────────
        // quote(): the latest bid/ask tick for one symbol.
        Mt4TermApiMarketInfo.QuoteData q = account.quote(symbol);
        System.out.printf(Locale.ROOT, "quote            : %s bid %.5f ask %.5f%n", symbol, q.getBid(), q.getAsk());

        // quoteMany(): the same, but for several symbols in a single request.
        Mt4TermApiMarketInfo.QuoteManyData qm = account.quoteMany(List.of(symbol, "GBPUSD"));
        System.out.print("quoteMany        : ");
        for (Mt4TermApiMarketInfo.QuoteData x : qm.getQuotesList()) {
            System.out.printf(Locale.ROOT, "%s %.5f/%.5f  ", x.getSymbol(), x.getBid(), x.getAsk());
        }
        System.out.println();

        // symbols(): the full list of instruments the terminal can trade (here we just count them).
        System.out.println("symbols          : " + account.symbols().getSymbolNameInfosCount() + " tradable");

        // symbolParamsMany(): per-symbol trading rules — point size, digits, min/max/step lot.
        Mt4TermApiAccountHelper.SymbolParamsManyData sp = account.symbolParamsMany(symbol);
        if (sp.getSymbolInfosCount() > 0) {
            Mt4TermApiAccountHelper.SymbolParamsManyInfo i = sp.getSymbolInfos(0);
            System.out.printf(Locale.ROOT, "symbolParamsMany : %s point=%.5f digits=%d lots[%.2f..%.2f step %.2f]%n",
                    i.getSymbolName(), i.getPoint(), i.getDigits(), i.getVolumeMin(), i.getVolumeMax(), i.getVolumeStep());
        }

        // symbolSelect(): add the symbol to Market Watch so the server streams its prices (true = add).
        Mt4TermApiMarketInfo.SymbolSelectData ss = account.symbolSelect(symbol, true);
        System.out.printf(Locale.ROOT, "symbolSelect     : %s success=%b%n", symbol, ss.getSuccess());

        // tickValueWithSize(): money value of one tick, the tick size and the contract size — used for risk math.
        Mt4TermApiAccountHelper.TickValueWithSizeData tv = account.tickValueWithSize(List.of(symbol));
        if (tv.getInfosCount() > 0) {
            Mt4TermApiAccountHelper.SymbolTickValueWithSizeInfo t = tv.getInfos(0);
            System.out.printf(Locale.ROOT, "tickValueWithSize: tickValue=%.5f tickSize=%.5f contract=%.1f%n",
                    t.getTradeTickValue(), t.getTradeTickSize(), t.getTradeContractSize());
        }

        // serverTimezone(): the trade server's UTC offset (handy when interpreting timestamps).
        System.out.println("serverTimezone   : UTC offset " + account.serverTimezone().getUtcOffsetSeconds() + "s");

        // ── Orders & history ─────────────────────────────────────
        // openedOrders(): all currently open orders/positions with their details.
        Mt4TermApiAccountHelper.OpenedOrdersData oo = account.openedOrders();
        System.out.println("openedOrders     : " + oo.getOrderInfosCount() + " open");
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : oo.getOrderInfosList()) {
            System.out.printf(Locale.ROOT, "    #%d %s %s %.2f lot @ %.5f  P/L %.2f%n",
                    o.getTicket(), o.getSymbol(), o.getOrderType(), o.getLots(), o.getOpenPrice(), o.getProfit());
        }

        // openedOrdersTickets(): just the ticket numbers of open orders (a lightweight version of the above).
        System.out.println("openedOrdersTickets: " + account.openedOrdersTickets().getTicketsCount() + " tickets");

        // ordersHistory(): closed orders/deals (paginated); here we read the default page.
        Mt4TermApiAccountHelper.OrdersHistoryData oh = account.ordersHistory();
        System.out.println("ordersHistory    : total=" + oh.getTotalCount() + " page=" + oh.getOrdersInfoCount());

        // ── Trade statistics (aggregated analytics over the account history) ──
        System.out.println("tradeStats             : " + brief(account.tradeStats(null)));              // profit factor, counts, drawdowns
        System.out.println("tradeStatsEquityHistory: " + brief(account.tradeStatsEquityHistory(null, null))); // stats + equity curve + trades
        System.out.println("equityHistory          : " + brief(account.equityHistory(null, null)));     // equity/balance time series
        System.out.println("tradeUnrealizedPl      : " + brief(account.tradeUnrealizedPl(null)));        // per-position MFE/MAE extrema
    }

    /** One-line preview of a protobuf message (its toString can be large/multiline). */
    private static String brief(Object o) {
        String s = String.valueOf(o).replaceAll("\\s+", " ").trim();
        if (s.isEmpty()) return "(empty)";
        return s.length() > 120 ? s.substring(0, 120) + "…" : s;
    }

    private MarketDataExample() {
    }
}
