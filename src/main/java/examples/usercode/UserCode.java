/*══════════════════════════════════════════════════════════════════════════════
 FILE: examples/usercode/UserCode.java - USER CODE SANDBOX
 PURPOSE:
   Your scratch area. The three client tiers are already created and connected
   when run() is called, so you can experiment without any connection boilerplate:
      • account (MT4Account) - low-level, raw gRPC/protobuf
      • service (MT4Service) - mid-level typed wrappers
      • sugar   (MT4Sugar)   - high-level one-liner helpers

 🎯 WHO SHOULD USE THIS:
   • Anyone trying out a call or a small idea against a live demo terminal
   • Prototyping before moving code into a real example/strategy

 📚 WHAT THIS DEMO COVERS:
   • Prints the account balance, then hands over to your own code
   • A commented-out snippet shows how to read the current price via sugar

 ℹ️  SAFE by default: only reads the balance. Any order-sending code is yours to
     add - the sample lines below are commented out.

 USAGE:
   run.bat 0      (or: run.bat usercode)
══════════════════════════════════════════════════════════════════════════════*/
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
        //region SANDBOX
        // ══════════════════════════════════════════════════════════════
        // SANDBOX - write your own code here (account/service/sugar ready)
        // ══════════════════════════════════════════════════════════════

        // ────────────────────────────────────────────────────────────
        // service.accountSummary()
        //   Quick sanity check that the connection works: print the balance.
        // ────────────────────────────────────────────────────────────
        System.out.printf(Locale.ROOT, "Balance: %.2f%n", service.accountSummary().getAccountBalance());

        // --- your code here ---
        // Example: print the current price
        // var q = sugar.lastQuote(symbol);
        // System.out.printf("%s %.5f/%.5f%n", symbol, q.bid, q.ask);
        //endregion
    }

    private UserCode() {
    }
}
