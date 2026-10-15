package examples;

import mt4_term_api.Mt4TermApiAccountHelper.AccountSummaryData;
import mt4_term_api.Mt4TermApiMarketInfo.QuoteData;
import mt4_term_api.Mt4TermApiSubscriptions.OnSymbolMqlTickInfo;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendData;
import mt4_term_api.Mt4TermApiTradingHelper.OrderSendOperationType;
import pro.mrpc.mt4.MT4Account;
import pro.mrpc.mt4.MrpcException;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * MetaRPC JavaMT4 Quickstart: connect through mt4.mrpc.pro:443, read the account and a price, buy 0.01 lot and close it,
 * stream three ticks, disconnect.
 *
 * <pre>
 *   MRPC_API_KEY   your API key (https://mrpc.pro/my)
 *   MT4_LOGIN, MT4_PASSWORD, MT4_SERVER   the MT4 account and its broker server, e.g. TeleTrade-Demo
 *   MRPC_GRPC_HOST optional, default mt4.mrpc.pro:443
 * </pre>
 */
public class Quickstart {
    public static void main(String[] args) throws Exception {
        String apiKey = args.length > 0 ? args[0] : System.getenv("MRPC_API_KEY");
        String host = System.getenv().getOrDefault("MRPC_GRPC_HOST", MT4Account.DEFAULT_SERVER);
        String login = System.getenv("MT4_LOGIN");
        String password = System.getenv("MT4_PASSWORD");
        String server = System.getenv().getOrDefault("MT4_SERVER", "TeleTrade-Demo");
        if (login == null || password == null) {
            System.err.println("Set MT4_LOGIN and MT4_PASSWORD (and MT4_SERVER). No account yet? Open a demo account with");
            System.err.println("  curl -H \"APIKey: <your key>\" \"https://mt4.mrpc.pro/DemoAccount/Open?server=TeleTrade-Demo\"");
            System.exit(2);
        }

        try (MT4Account account = new MT4Account(host, apiKey)) {
            System.out.println("Connecting to " + host + " ...");
            String terminal = account.connect(Long.parseLong(login), password, server, 120);
            System.out.println("Connected, terminal " + terminal);
            try {
                AccountSummaryData summary = account.accountSummary();
                System.out.printf(Locale.ROOT, "Account %d (%s): balance %.2f %s, equity %.2f, leverage 1:%d%n", summary.getAccountLogin(),
                        summary.getAccountUserName(), summary.getAccountBalance(), summary.getAccountCurrency(),
                        summary.getAccountEquity(), summary.getAccountLeverage());

                QuoteData quote = account.quote("EURUSD");
                System.out.printf(Locale.ROOT, "EURUSD bid %s ask %s%n", quote.getBid(), quote.getAsk());

                OrderSendData order = account.orderSend("EURUSD", OrderSendOperationType.OC_OP_BUY, 0.01);
                System.out.printf(Locale.ROOT, "Bought 0.01 lot: ticket #%d at %s%n", order.getTicket(), order.getPrice());
                account.orderClose(order.getTicket());
                System.out.println("Closed ticket #" + order.getTicket());

                List<OnSymbolMqlTickInfo> ticks = new CopyOnWriteArrayList<>();
                CompletableFuture<Void> done = new CompletableFuture<>();
                try (MT4Account.Subscription sub = account.onSymbolTick(List.of("EURUSD", "GBPUSD"), tick -> {
                    ticks.add(tick);
                    if (ticks.size() == 3) done.complete(null);
                }, done::completeExceptionally)) {
                    done.get(60, TimeUnit.SECONDS);
                }
                for (OnSymbolMqlTickInfo t : ticks.subList(0, 3)) System.out.printf(Locale.ROOT, "Tick %s %s/%s%n", t.getSymbol(), t.getBid(), t.getAsk());
            } finally {
                account.disconnect(false);
                System.out.println("Disconnected.");
            }
        } catch (MrpcException e) {
            System.err.println("Failed: " + e.getMessage());
            System.exit(1);
        }
    }
}
