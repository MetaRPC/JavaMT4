package pro.mrpc.mt4;

import pro.mrpc.mt4.exception.DecoderException;
import pro.mrpc.mt4.network.MetaTraderMessage;

import java.io.IOException;

public interface MessageHandler {
    void onMessage(MetaTraderMessage message, MT4Client client);
    void onReceiveFailure(DecoderException e);
    void onSendFailure(Exception e);
    void onConnected(MT4Client client);
    void onDisconnected(IOException exception, MT4Client client);
}
