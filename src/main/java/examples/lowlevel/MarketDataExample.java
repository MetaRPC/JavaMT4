/*══════════════════════════════════════════════════════════════════════════════
 FILE: examples/lowlevel/MarketDataExample.java - LOW-LEVEL MT4 MARKET DATA DEMO
 PURPOSE:
   Walk through every read-only MT4Account information method - account, market
   info, orders/history and aggregated trade statistics. Nothing here places a
   trade; each call returns a raw protobuf ...Data message.

 🎯 WHO SHOULD USE THIS:
   • Developers learning the low-level MT4 gRPC API (read side)
   • Building monitoring / dashboard / reporting tools
   • Seeing exactly what each protobuf reply contains

 📚 WHAT THIS DEMO COVERS (4 sections):

   1. ACCOUNT
      • accountSummary()        - balance / equity / leverage / currency / login

   2. MARKET INFO
      • quote() / quoteMany()   - latest Bid/Ask for one or several symbols
      • symbols()               - full tradable-instrument catalog
      • symbolParamsMany()      - point, digits, min/max/step lot
      • symbolSelect()          - add a symbol to Market Watch
      • tickValueWithSize()     - tick value / size / contract size (risk math)
      • serverTimezone()        - trade-server UTC offset

   3. ORDERS & HISTORY
      • openedOrders()          - open orders/positions with details
      • openedOrdersTickets()   - just the ticket numbers (lightweight)
      • ordersHistory()         - closed orders/deals (paginated)

   4. TRADE STATISTICS
      • tradeStats()               - profit factor, counts, drawdowns
      • tradeStatsEquityHistory()  - stats + equity curve + trades
      • equityHistory()            - equity/balance time series
      • tradeUnrealizedPl()        - per-position MFE/MAE extrema

 🔄 API LEVELS:
   LOW-LEVEL  (MT4Account) - THIS FILE: raw gRPC/protobuf replies
   MID-LEVEL  (MT4Service) - typed wrappers, less boilerplate
   HIGH-LEVEL (MT4Sugar)   - one-liner helpers with smart defaults

 USAGE:
   run.bat 1      (or: java -cp ... Program 1)
══════════════════════════════════════════════════════════════════════════════*/
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
        //region 1. ACCOUNT
        // ══════════════════════════════════════════════════════════════
        // 1. ACCOUNT
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // accountSummary()
        //   One call with the account's money picture.
        //   Returns: balance, equity, leverage, currency, login.
        // ────────────────────────────────────────────────────────────
        Mt4TermApiAccountHelper.AccountSummaryData acc = account.accountSummary();
        System.out.printf(Locale.ROOT, "accountSummary   : #%d (%s) balance %.2f %s, equity %.2f, leverage 1:%d%n",
                acc.getAccountLogin(), acc.getAccountUserName(), acc.getAccountBalance(),
                acc.getAccountCurrency(), acc.getAccountEquity(), acc.getAccountLeverage());
        //endregion

        //region 2. MARKET INFO
        // ══════════════════════════════════════════════════════════════
        // 2. MARKET INFO
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // quote(symbol)
        //   The latest Bid/Ask tick for one symbol.
        //   Returns: QuoteData (bid, ask, time, ...).
        // ────────────────────────────────────────────────────────────
        Mt4TermApiMarketInfo.QuoteData q = account.quote(symbol);
        System.out.printf(Locale.ROOT, "quote            : %s bid %.5f ask %.5f%n", symbol, q.getBid(), q.getAsk());

        // ────────────────────────────────────────────────────────────
        // quoteMany(symbols)
        //   The same, but for several symbols in a single request.
        //   Returns: QuoteManyData (one QuoteData per symbol).
        // ────────────────────────────────────────────────────────────
        Mt4TermApiMarketInfo.QuoteManyData qm = account.quoteMany(List.of(symbol, "GBPUSD"));
        System.out.print("quoteMany        : ");
        for (Mt4TermApiMarketInfo.QuoteData x : qm.getQuotesList()) {
            System.out.printf(Locale.ROOT, "%s %.5f/%.5f  ", x.getSymbol(), x.getBid(), x.getAsk());
        }
        System.out.println();

        // ────────────────────────────────────────────────────────────
        // symbols()
        //   The full list of instruments the terminal can trade.
        //   Returns: SymbolsData (here we just count them).
        // ────────────────────────────────────────────────────────────
        System.out.println("symbols          : " + account.symbols().getSymbolNameInfosCount() + " tradable");

        // ────────────────────────────────────────────────────────────
        // symbolParamsMany(symbol)
        //   Per-symbol trading rules.
        //   Returns: point size, digits, min/max/step lot.
        // ────────────────────────────────────────────────────────────
        Mt4TermApiAccountHelper.SymbolParamsManyData sp = account.symbolParamsMany(symbol);
        if (sp.getSymbolInfosCount() > 0) {
            Mt4TermApiAccountHelper.SymbolParamsManyInfo i = sp.getSymbolInfos(0);
            System.out.printf(Locale.ROOT, "symbolParamsMany : %s point=%.5f digits=%d lots[%.2f..%.2f step %.2f]%n",
                    i.getSymbolName(), i.getPoint(), i.getDigits(), i.getVolumeMin(), i.getVolumeMax(), i.getVolumeStep());
        }

        // ────────────────────────────────────────────────────────────
        // symbolSelect(symbol, true)
        //   Add the symbol to Market Watch so the server streams its prices.
        //   Returns: SymbolSelectData (success flag).
        // ────────────────────────────────────────────────────────────
        Mt4TermApiMarketInfo.SymbolSelectData ss = account.symbolSelect(symbol, true);
        System.out.printf(Locale.ROOT, "symbolSelect     : %s success=%b%n", symbol, ss.getSuccess());

        // ────────────────────────────────────────────────────────────
        // tickValueWithSize(symbols)
        //   Money value of one tick, the tick size and the contract size.
        //   Returns: per-symbol info used for risk / position sizing.
        // ────────────────────────────────────────────────────────────
        Mt4TermApiAccountHelper.TickValueWithSizeData tv = account.tickValueWithSize(List.of(symbol));
        if (tv.getInfosCount() > 0) {
            Mt4TermApiAccountHelper.SymbolTickValueWithSizeInfo t = tv.getInfos(0);
            System.out.printf(Locale.ROOT, "tickValueWithSize: tickValue=%.5f tickSize=%.5f contract=%.1f%n",
                    t.getTradeTickValue(), t.getTradeTickSize(), t.getTradeContractSize());
        }

        // ────────────────────────────────────────────────────────────
        // serverTimezone()
        //   The trade server's UTC offset (handy when reading timestamps).
        //   Returns: ServerTimezoneData (utcOffsetSeconds).
        // ────────────────────────────────────────────────────────────
        System.out.println("serverTimezone   : UTC offset " + account.serverTimezone().getUtcOffsetSeconds() + "s");
        //endregion

        //region 3. ORDERS & HISTORY
        // ══════════════════════════════════════════════════════════════
        // 3. ORDERS & HISTORY
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // openedOrders()
        //   All currently open orders/positions with their details.
        //   Returns: OpenedOrdersData (ticket, symbol, type, lots, P/L, ...).
        // ────────────────────────────────────────────────────────────
        Mt4TermApiAccountHelper.OpenedOrdersData oo = account.openedOrders();
        System.out.println("openedOrders     : " + oo.getOrderInfosCount() + " open");
        for (Mt4TermApiAccountHelper.OpenedOrderInfo o : oo.getOrderInfosList()) {
            System.out.printf(Locale.ROOT, "    #%d %s %s %.2f lot @ %.5f  P/L %.2f%n",
                    o.getTicket(), o.getSymbol(), o.getOrderType(), o.getLots(), o.getOpenPrice(), o.getProfit());
        }

        // ────────────────────────────────────────────────────────────
        // openedOrdersTickets()
        //   Just the ticket numbers of open orders (lightweight).
        //   Returns: OpenedOrdersTicketsData (ticket list).
        // ────────────────────────────────────────────────────────────
        System.out.println("openedOrdersTickets: " + account.openedOrdersTickets().getTicketsCount() + " tickets");

        // ────────────────────────────────────────────────────────────
        // ordersHistory()
        //   Closed orders/deals (paginated); here we read the default page.
        //   Returns: OrdersHistoryData (totalCount + one page of orders).
        // ────────────────────────────────────────────────────────────
        Mt4TermApiAccountHelper.OrdersHistoryData oh = account.ordersHistory();
        System.out.println("ordersHistory    : total=" + oh.getTotalCount() + " page=" + oh.getOrdersInfoCount());
        //endregion

        //region 4. TRADE STATISTICS
        // ══════════════════════════════════════════════════════════════
        // 4. TRADE STATISTICS (aggregated analytics over the account history)
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // tradeStats() / tradeStatsEquityHistory() / equityHistory() / tradeUnrealizedPl()
        //   Aggregated analytics; replies can be large, so brief() prints a
        //   one-line preview of each.
        // ────────────────────────────────────────────────────────────
        System.out.println("tradeStats             : " + brief(account.tradeStats(null)));              // profit factor, counts, drawdowns
        System.out.println("tradeStatsEquityHistory: " + brief(account.tradeStatsEquityHistory(null, null))); // stats + equity curve + trades
        System.out.println("equityHistory          : " + brief(account.equityHistory(null, null)));     // equity/balance time series
        System.out.println("tradeUnrealizedPl      : " + brief(account.tradeUnrealizedPl(null)));        // per-position MFE/MAE extrema
        //endregion
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
