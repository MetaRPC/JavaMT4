package examples;

import pro.mrpc.mt4.MT4Client;
import pro.mrpc.mt4.network.enums.Op;
import pro.mrpc.mt4.network.enums.TradeCommand;
import pro.mrpc.mt4.network.messages.req.OrderTransactionReq;
import pro.mrpc.mt4.network.messages.res.TradeEvent;
import pro.mrpc.mt4.network.parts.AccountInfo;
import pro.mrpc.mt4.utils.DefaultMessageHandler;

/**
 * MetaRPC JavaMT4 Quickstart Example.
 * Demonstrates:
 * 1. Initializing client and connecting to mt4.mrpc.pro:443
 * 2. Authenticating with APIKey and LoginId
 * 3. Reading account balance
 * 4. Placing a trade order
 */
public class Quickstart {
    public static void main(String[] args) {
        String host = "mt4.mrpc.pro";
        int port = 443;
        int login = 100234;
        String password = "demo_password";
        String apiKey = System.getenv("MRPC_API_KEY");

        System.out.println("Connecting to MetaRPC MT4 (" + host + ":" + port + ")...");
        MT4Client client = new MT4Client(host, port, new DefaultMessageHandler());
        try {
            client.connect(login, password);
            System.out.println("Successfully connected!");

            AccountInfo info = client.getAccountInfo();
            if (info != null) {
                System.out.printf("Account: %d, Balance: %.2f %s, Leverage: 1:%d%n",
                        login, info.balance, info.currency, info.leverage);
            }

            // Place demo order
            OrderTransactionReq orderReq = new OrderTransactionReq();
            orderReq.symbol = "EURUSD";
            orderReq.orderType = Op.Buy;
            orderReq.tradeCommand = TradeCommand.Market;
            orderReq.lots = 1; // 0.01 lot
            orderReq.comment = "MetaRPC Quickstart";

            System.out.println("Submitting order...");
            TradeEvent tradeEvent = (TradeEvent) client.sendRequest(orderReq);
            if (tradeEvent != null) {
                System.out.println("Order result ticket: " + tradeEvent.ticket);
            }
        } catch (Exception e) {
            System.err.println("Error executing quickstart: " + e.getMessage());
        } finally {
            client.disconnect();
            System.out.println("Disconnected.");
        }
    }
}
