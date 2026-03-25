package io.mtapi.mt4;

import io.mtapi.mt4.exception.DecoderException;
import io.mtapi.mt4.network.MetaTraderMessage;

public interface InternalMessageHandler {
    void handleMessage(MetaTraderMessage message, MT4Client client);
    void onDecoderException(DecoderException e);
}
