import examples.Common;
import pro.mrpc.mt4.MT4Account;
import pro.mrpc.mt4.MT4Service;
import pro.mrpc.mt4.MT4Sugar;

/**
 * Launcher for the JavaMT4 examples. Usage: {@code run.bat <n>}
 *
 * <pre>
 *   LOW-LEVEL (MT4Account)   1 Market data   2 Trading   3 Streaming
 *   MID-LEVEL (MT4Service)   4 Service demo
 *   HIGH-LEVEL (MT4Sugar)    6 Basics        7 Trading   8 Positions
 *   SANDBOX                  0 User code
 * </pre>
 */
public class Program {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            showUsage();
            return;
        }
        String cmd = args[0].toLowerCase();

        // Offline tools — no terminal connection needed. Handle before connecting.
        switch (cmd) {
            case "9": case "inspect": case "protobuf":
                examples.tools.ProtobufInspector.run();
                return;
            default:
                // fall through to the connected examples below
        }

        Common.Config cfg = Common.load();
        MT4Account account = new MT4Account(cfg.user, cfg.password, cfg.grpcServer, null, cfg.apiKey);
        System.out.println("Connecting user=" + cfg.user + " server=" + cfg.serverName + " ...");
        account.connectByServerName(cfg.serverName, 120);
        System.out.println("Connected. terminal=" + account.getId() + "\n");

        MT4Service service = new MT4Service(account);
        MT4Sugar sugar = new MT4Sugar(service);
        String sym = cfg.baseSymbol;

        try {
            switch (cmd) {
                case "1": examples.lowlevel.MarketDataExample.run(account, sym); break;
                case "2": examples.lowlevel.TradingExample.run(account, sym); break;
                case "3": examples.lowlevel.StreamingExample.run(account, sym); break;
                case "4": examples.services.ServiceDemo.run(service, sym); break;
                case "6": examples.sugar.SugarBasics.run(sugar, sym); break;
                case "7": examples.sugar.SugarTrading.run(sugar, sym); break;
                case "8": examples.sugar.SugarPositions.run(sugar, sym); break;
                case "11": strategies.MarketOneShot.run(sugar,
                        new strategies.StrategyPreset(sym).magic(777).comment("one-shot"),
                        new strategies.RiskPreset(1.0, 20).tp(40).trailing(15).breakeven(10, 2)); break;
                case "12": strategies.BracketTrailing.run(sugar,
                        new strategies.StrategyPreset(sym).magic(778).comment("bracket-trail"),
                        new strategies.RiskPreset(1.0, 20).tp(40).breakeven(10, 2), 5.0); break;
                case "15": strategies.PendingBracket.run(sugar,
                        new strategies.StrategyPreset(sym).magic(779).comment("pending"),
                        new strategies.RiskPreset(1.0, 20).tp(40).trailing(15), 10); break;
                case "16": strategies.Presets.run(sugar, sym); break;
                case "0": case "usercode": case "user": case "sandbox":
                    examples.usercode.UserCode.run(account, service, sugar, sym); break;
                default:
                    System.out.println("Unknown option: " + cmd);
                    showUsage();
            }
        } finally {
            account.disconnect(false);
            account.close();
        }
    }

    private static void showUsage() {
        System.out.println();
        System.out.println("JavaMT4 — examples launcher");
        System.out.println("  run.bat 1   LOW-LEVEL  MarketDataExample  (account, symbols, quote, history)");
        System.out.println("  run.bat 2   LOW-LEVEL  TradingExample     (orderSend / modify / close)");
        System.out.println("  run.bat 3   LOW-LEVEL  StreamingExample   (onSymbolTick)");
        System.out.println("  run.bat 4   MID-LEVEL  ServiceDemo        (wrappers + trade shortcuts)");
        System.out.println("  run.bat 6   HIGH-LEVEL SugarBasics        (pip math + normalization)");
        System.out.println("  run.bat 7   HIGH-LEVEL SugarTrading       (risk sizing + buy/close)");
        System.out.println("  run.bat 8   HIGH-LEVEL SugarPositions     (exposure + find/close)");
        System.out.println("  run.bat 11  ORCHESTRATOR MarketOneShot    (market entry + management)");
        System.out.println("  run.bat 12  ORCHESTRATOR BracketTrailing  (bracket + trailing activation)");
        System.out.println("  run.bat 15  ORCHESTRATOR PendingBracket   (limit order + bracket)");
        System.out.println("  run.bat 16  PRESETS      Presets          (strategy & risk presets)");
        System.out.println("  run.bat 9   TOOL       ProtobufInspector  (explore proto types/fields/enums — offline)");
        System.out.println("  run.bat 0   SANDBOX    UserCode           (your own code)");
        System.out.println();
    }
}
