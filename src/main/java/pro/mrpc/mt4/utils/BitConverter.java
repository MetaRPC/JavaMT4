package pro.mrpc.mt4.utils;

import java.nio.charset.StandardCharsets;

public class BitConverter {
    public static void putBytes(short v, byte[] writeBuffer, int offset) {
        writeBuffer[offset] = (byte) ((v) & 0xFF);
        writeBuffer[offset + 1] = (byte) ((v >>> 8) & 0xFF);
    }

    public static void putBytes(long v, byte[] writeBuffer, int offset) {
        writeBuffer[offset + 7] = (byte) ((v >>> 56) & 0xFF);
        writeBuffer[offset + 6] = (byte) ((v >>> 48) & 0xFF);
        writeBuffer[offset + 5] = (byte) ((v >>> 40) & 0xFF);
        writeBuffer[offset + 4] = (byte) ((v >>> 32) & 0xFF);
        writeBuffer[offset + 3] = (byte) ((v >>> 24) & 0xFF);
        writeBuffer[offset + 2] = (byte) ((v >>> 16) & 0xFF);
        writeBuffer[offset + 1] = (byte) ((v >>> 8) & 0xFF);
        writeBuffer[offset] = (byte) ((v) & 0xFF);
    }

    public static void putBytes(int v, byte[] writeBuffer, int offset) {
        writeBuffer[offset + 3] = (byte) ((v >>> 24) & 0xFF);
        writeBuffer[offset + 2] = (byte) ((v >>> 16) & 0xFF);
        writeBuffer[offset + 1] = (byte) ((v >>> 8) & 0xFF);
        writeBuffer[offset] = (byte) ((v) & 0xFF);
    }

    public static void putBytes(double v, byte[] writeBuffer, int offset) {
        putBytes(Double.doubleToRawLongBits(v), writeBuffer, offset);
    }

    public static int toInt32(byte[] data, int offset) {
        return (data[offset] & 0xFF) | ((data[offset + 1] & 0xFF) << 8)
                | ((data[offset + 2] & 0xFF) << 16)
                | ((data[offset + 3] & 0xFF) << 24);
    }

    public static long toInt64(byte[] data, int offset) {
        return (((long) (data[offset + 7] & 0xff) << 56)
                | ((long) (data[offset + 6] & 0xff) << 48)
                | ((long) (data[offset + 5] & 0xff) << 40)
                | ((long) (data[offset + 4] & 0xff) << 32)
                | ((long) (data[offset + 3] & 0xff) << 24)
                | ((long) (data[offset + 2] & 0xff) << 16)
                | ((long) (data[offset + 1] & 0xff) << 8) | (data[offset] & 0xff));
    }

    public static short toInt16(byte[] data, int offset) {
        return (short) (data[offset] & 0xFF | (data[offset + 1] & 0xFF) << 8);
    }

    public static long toUInt32(byte[] data, int offset) {
        return ((long) data[offset] & 0xFF) | ((data[offset + 1] & 0xFF) << 8)
                | ((data[offset + 2] & 0xFF) << 16)
                | ((long) (data[offset + 3] & 0xFF) << 24);
    }

    public static float toFloat(byte[] data, int offset) {
        return Float.intBitsToFloat(toInt32(data, offset));
    }

    public static double toDouble(byte[] data, int offset) {
        return Double.longBitsToDouble(BitConverter.toInt64(data, offset));
    }

    public static String getString(byte[] buffer, int offset, int maxLength) {
        int end = offset;
        int limit = Math.min(buffer.length, offset + maxLength);
        while (end < limit && buffer[end] != 0) {
            end++;
        }
        return new String(buffer, offset, end - offset, StandardCharsets.US_ASCII);
    }

    public static byte[] getBytes(int v) {
        byte[] writeBuffer = new byte[4];
        writeBuffer[3] = (byte) ((v >>> 24) & 0xFF);
        writeBuffer[2] = (byte) ((v >>> 16) & 0xFF);
        writeBuffer[1] = (byte) ((v >>> 8) & 0xFF);
        writeBuffer[0] = (byte) ((v) & 0xFF);
        return writeBuffer;
    }

    public static byte[] getBytes(short v) {
        byte[] writeBuffer = new byte[2];
        writeBuffer[0] = (byte) ((v) & 0xFF);
        writeBuffer[1] = (byte) ((v >>> 8) & 0xFF);
        return writeBuffer;
    }

    public static byte[] getBytes(long v) {
        byte[] writeBuffer = new byte[8];
        writeBuffer[7] = (byte) ((v >>> 56) & 0xFF);
        writeBuffer[6] = (byte) ((v >>> 48) & 0xFF);
        writeBuffer[5] = (byte) ((v >>> 40) & 0xFF);
        writeBuffer[4] = (byte) ((v >>> 32) & 0xFF);
        writeBuffer[3] = (byte) ((v >>> 24) & 0xFF);
        writeBuffer[2] = (byte) ((v >>> 16) & 0xFF);
        writeBuffer[1] = (byte) ((v >>> 8) & 0xFF);
        writeBuffer[0] = (byte) ((v) & 0xFF);
        return writeBuffer;
    }
}
