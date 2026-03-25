package io.mtapi.mt4.network.messages.res;

import io.mtapi.mt4.network.MetaTraderMessage;

/**
 * Symbol specification update event from the MT4 server.
 */
public class SymbolUpdateEvent implements MetaTraderMessage {
    public byte[] rawData;

    public SymbolUpdateEvent(byte[] rawData) {
        this.rawData = rawData;
    }
}
