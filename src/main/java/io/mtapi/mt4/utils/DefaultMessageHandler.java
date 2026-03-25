package io.mtapi.mt4.utils;

import io.mtapi.mt4.MT4Client;
import io.mtapi.mt4.MessageHandler;
import io.mtapi.mt4.exception.DecoderException;
import io.mtapi.mt4.network.MetaTraderMessage;

import java.io.IOException;

public class DefaultMessageHandler implements MessageHandler {
    @Override
    public void onMessage(MetaTraderMessage message, MT4Client client) {
    }

    @Override
    public void onReceiveFailure(DecoderException e) {
    }

    @Override
    public void onSendFailure(Exception e) {
    }

    @Override
    public void onConnected(MT4Client client) {
    }

    @Override
    public void onDisconnected(IOException exception, MT4Client client) {
    }
}
