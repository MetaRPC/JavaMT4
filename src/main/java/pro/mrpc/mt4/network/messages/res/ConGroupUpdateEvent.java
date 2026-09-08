package pro.mrpc.mt4.network.messages.res;

import pro.mrpc.mt4.network.MetaTraderMessage;

/**
 * Connection group update event from the MT4 server.
 */
public class ConGroupUpdateEvent implements MetaTraderMessage {
    public byte[] rawData;

    public ConGroupUpdateEvent(byte[] rawData) {
        this.rawData = rawData;
    }
}
