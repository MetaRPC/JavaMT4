package io.mtapi.mt4.network.parts;

/**
 * Single trading or quote session (32 bytes in MT4 protocol).
 */
public class ConSession {
    public short openHour;
    public short openMin;
    public short closeHour;
    public short closeMin;
    public int open;
    public int close;
    public short[] align;

    @Override
    public String toString() {
        return String.format("%02d:%02d-%02d:%02d", openHour, openMin, closeHour, closeMin);
    }
}
