/*══════════════════════════════════════════════════════════════════════════════
 FILE: examples/services/ServiceDemo.java - MID-LEVEL (MT4Service) DEMO
 PURPOSE:
   Show the MT4Service facade over MT4Account: the same calls, but with friendly
   arguments (sort/timeframe strings, side/type) and trade shortcuts
   (buyMarket ... close). Demonstrates read methods plus one self-cleaning
   round-trip order.

 🎯 WHO SHOULD USE THIS:
   • Developers who want typed/wrapped calls with less boilerplate
   • Anyone comparing the raw low-level API with the mid-level one
   • Learning the friendly sort/timeframe aliases and trade shortcuts

 📚 WHAT THIS DEMO COVERS (3 sections):

   1. ACCOUNT & MARKET INFO
      • accountSummary()        - balance / equity / currency
      • quote() / quoteMany()   - current prices for one or many symbols
      • quoteHistory()          - OHLC bars (friendly "H1" timeframe string)
      • symbols()               - tradable-instrument count
      • symbolParamsMany()      - point / digits
      • tickValueWithSize()     - tick value
      • symbolSelect()          - Market Watch toggle
      • serverTimezone()        - server UTC offset

   2. ORDERS & STATS
      • openedOrders()          - open orders (friendly "open_desc" sort)
      • openedOrdersTickets()   - ticket numbers only
      • ordersHistory()         - closed-order history ("close_desc" sort)
      • tradeStats()            - aggregated trade statistics
      • equityHistory()         - equity/balance time series

   3. TRADE SHORTCUTS (round-trip)
      • setTradeDefaults()      - magic / deviation / comment for every shortcut
      • buyMarket()             - instant BUY, returns the ticket
      • modifySlTp()            - attach absolute SL/TP prices
      • close()                 - flatten the position

 🔄 API LEVELS:
   LOW-LEVEL  (MT4Account) - raw gRPC/protobuf replies
   MID-LEVEL  (MT4Service) - THIS FILE: typed wrappers, friendly aliases
   HIGH-LEVEL (MT4Sugar)   - one-liner helpers with smart defaults

 ⚠️  WARNING: section 3 places ONE real market order (0.01 lot) and then closes
     it again — self-cleaning, but run it on a DEMO account.

 USAGE:
   run.bat 4      (or: java -cp ... Program 4)
══════════════════════════════════════════════════════════════════════════════*/
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
        //region 1. ACCOUNT & MARKET INFO
        // ══════════════════════════════════════════════════════════════
        // 1. ACCOUNT & MARKET INFO
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // accountSummary()
        //   Account money picture (same as the low-level call).
        //   Returns: balance, equity, currency, ...
        // ────────────────────────────────────────────────────────────
        Mt4TermApiAccountHelper.AccountSummaryData acc = s.accountSummary();
        System.out.printf(Locale.ROOT, "accountSummary   : balance %.2f %s, equity %.2f%n",
                acc.getAccountBalance(), acc.getAccountCurrency(), acc.getAccountEquity());

        // ────────────────────────────────────────────────────────────
        // quote() / quoteMany()
        //   Current prices for one or many symbols.
        //   Returns: QuoteData / QuoteManyData.
        // ────────────────────────────────────────────────────────────
        Mt4TermApiMarketInfo.QuoteData q = s.quote(symbol);
        System.out.printf(Locale.ROOT, "quote            : %s %.5f/%.5f%n", symbol, q.getBid(), q.getAsk());
        System.out.println("quoteMany        : " + s.quoteMany(List.of(symbol, "GBPUSD")).getQuotesCount() + " symbols");

        // ────────────────────────────────────────────────────────────
        // quoteHistory() / symbols()
        //   OHLC bars (friendly "H1" timeframe string the service maps to the
        //   enum) and the tradable-instrument catalog.
        //   Returns: historical quotes count / symbols count.
        // ────────────────────────────────────────────────────────────
        System.out.println("quoteHistory H1  : " + s.quoteHistory(symbol, "H1", null, null, 50).getHistoricalQuotesCount() + " bars");
        System.out.println("symbols          : " + s.symbols().getSymbolNameInfosCount());

        // ────────────────────────────────────────────────────────────
        // symbolParamsMany() / tickValueWithSize()
        //   Symbol trading rules and the money value of one tick.
        //   Returns: point/digits and tick value.
        // ────────────────────────────────────────────────────────────
        Mt4TermApiAccountHelper.SymbolParamsManyData sp = s.symbolParamsMany(symbol);
        if (sp.getSymbolInfosCount() > 0) {
            Mt4TermApiAccountHelper.SymbolParamsManyInfo i = sp.getSymbolInfos(0);
            System.out.printf(Locale.ROOT, "symbolParamsMany : %s point=%.5f digits=%d%n", i.getSymbolName(), i.getPoint(), i.getDigits());
        }
        if (s.tickValueWithSize(List.of(symbol)).getInfosCount() > 0) {
            System.out.printf(Locale.ROOT, "tickValueWithSize: %.5f%n", s.tickValueWithSize(List.of(symbol)).getInfos(0).getTradeTickValue());
        }

        // ────────────────────────────────────────────────────────────
        // symbolSelect() / serverTimezone()
        //   Market Watch toggle and the server's UTC offset.
        //   Returns: success flag / utcOffsetSeconds.
        // ────────────────────────────────────────────────────────────
        System.out.println("symbolSelect     : success=" + s.symbolSelect(symbol, true).getSuccess());
        System.out.println("serverTimezone   : UTC offset " + s.serverTimezone().getUtcOffsetSeconds() + "s");
        //endregion

        //region 2. ORDERS & STATS
        // ══════════════════════════════════════════════════════════════
        // 2. ORDERS & STATS
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // openedOrders() / openedOrdersTickets() / ordersHistory()
        //   Open orders and closed-order history. Note the friendly sort
        //   aliases ("open_desc", "close_desc") the service maps to enums.
        //   Returns: counts / totalCount.
        // ────────────────────────────────────────────────────────────
        System.out.println("openedOrders     : " + s.openedOrders("open_desc").getOrderInfosCount());
        System.out.println("openedOrdersTickets: " + s.openedOrdersTickets().getTicketsCount());
        System.out.println("ordersHistory    : total=" + s.ordersHistory("close_desc", null, null, null, null).getTotalCount());

        // ────────────────────────────────────────────────────────────
        // tradeStats() / equityHistory()
        //   Aggregated analytics; brief() prints a one-line preview.
        // ────────────────────────────────────────────────────────────
        System.out.println("tradeStats       : " + brief(s.tradeStats(null)));
        System.out.println("equityHistory    : " + brief(s.equityHistory(null, null)));
        //endregion

        //region 3. TRADE SHORTCUTS (round-trip)
        // ══════════════════════════════════════════════════════════════
        // 3. TRADE SHORTCUTS - open -> modify -> close (self-cleaning)
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // setTradeDefaults(magic, deviation, comment)
        //   Defaults applied to every following buy*/sell* shortcut.
        // ────────────────────────────────────────────────────────────
        s.setTradeDefaults(1001, 3, "svc-demo");

        // ────────────────────────────────────────────────────────────
        // buyMarket(symbol, lots, ...)
        //   Instant BUY; magic/deviation/comment come from the defaults above.
        //   Returns: the order ticket.    ⚠️ places a REAL order.
        // ────────────────────────────────────────────────────────────
        long ticket = s.buyMarket(symbol, 0.01, null, null, null, null, null);
        System.out.println("buyMarket        : #" + ticket + " (magic/deviation/comment from defaults)");
        Thread.sleep(800);

        // ────────────────────────────────────────────────────────────
        // modifySlTp(ticket, sl, tp)
        //   Attach absolute SL/TP prices (200 points around the entry).
        // ────────────────────────────────────────────────────────────
        double point = sp.getSymbolInfos(0).getPoint();
        s.modifySlTp((int) ticket, q.getBid() - 200 * point, q.getAsk() + 200 * point);
        System.out.println("modifySlTp       : SL/TP attached");
        Thread.sleep(800);

        // ────────────────────────────────────────────────────────────
        // close(ticket)
        //   Flatten the position opened above (leaves the account clean).
        // ────────────────────────────────────────────────────────────
        s.close((int) ticket);
        System.out.println("close            : #" + ticket + " closed");
        //endregion
    }

    private static String brief(Object o) {
        String s = String.valueOf(o).replaceAll("\\s+", " ").trim();
        if (s.isEmpty()) return "(empty)";
        return s.length() > 100 ? s.substring(0, 100) + "…" : s;
    }

    private ServiceDemo() {
    }
}
