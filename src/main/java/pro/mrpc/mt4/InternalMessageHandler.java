package pro.mrpc.mt4;

import pro.mrpc.mt4.exception.DecoderException;
import pro.mrpc.mt4.network.MetaTraderMessage;

public interface InternalMessageHandler {
    void handleMessage(MetaTraderMessage message, MT4Client client);
    void onDecoderException(DecoderException e);
}
