package examples.usercode;

import pro.mrpc.mt4.MT4Account;
import pro.mrpc.mt4.MT4Service;
import pro.mrpc.mt4.MT4Sugar;

import java.util.Locale;

/**
 * SANDBOX: {@code account} (low-level), {@code service} (mid-level) and {@code sugar} (high-level) are already
 * connected. Write your own code below.
 */
public final class UserCode {
    public static void run(MT4Account account, MT4Service service, MT4Sugar sugar, String symbol) throws Exception {
        System.out.printf(Locale.ROOT, "Balance: %.2f%n", service.accountSummary().getAccountBalance());

        // --- your code here ---
        // Example: print the current price
        // var q = sugar.lastQuote(symbol);
        // System.out.printf("%s %.5f/%.5f%n", symbol, q.bid, q.ask);
    }

    private UserCode() {
    }
}
