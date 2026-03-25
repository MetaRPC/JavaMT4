package io.mtapi.mt4.network;

import io.mtapi.mt4.utils.BitConverter;
import io.mtapi.mt4.utils.ByteArrayPool;
import io.mtapi.mt4.utils.MT4Constants;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Zero-allocation packet builder for MT4 protocol.
 * Unlike MT5's OutputFactory which adds a 9-byte header, MT4 uses a simpler
 * stream-based format where the command byte is at position 0.
 */
public class OutputFactory {

    public static class Pool {
        protected final ByteArrayPool byteArrayPool;
        private final ConcurrentLinkedQueue<OutputFactory> pool = new ConcurrentLinkedQueue<>();

        public Pool(ByteArrayPool byteArrayPool) {
            this.byteArrayPool = byteArrayPool;
        }

        public OutputFactory obtain() {
            OutputFactory outputFactory = pool.poll();
            if (outputFactory == null) {
                return new OutputFactory(byteArrayPool);
            }
            outputFactory.init();
            return outputFactory;
        }

        public void release(OutputFactory outputFactory) {
            if (outputFactory == null) return;
            pool.offer(outputFactory);
        }
    }

    protected final ByteArrayPool byteArrayPool;

    private final byte[] OUTPUT_BUFFER = new byte[MT4Constants.MAX_OUTPUT_SIZE];
    private int outputSize = 0;

    OutputFactory(ByteArrayPool byteArrayPool) {
        this.byteArrayPool = byteArrayPool;
    }

    public void init() {
        outputSize = 0;
    }

    public void pad(int size) {
        Arrays.fill(OUTPUT_BUFFER, outputSize, outputSize + size, (byte) 0);
        outputSize += size;
    }

    public void single(byte singleByte) {
        OUTPUT_BUFFER[outputSize++] = singleByte;
    }

    public void array(byte[] array) {
        System.arraycopy(array, 0, OUTPUT_BUFFER, outputSize, array.length);
        outputSize += array.length;
    }

    public void array(byte[] array, int length) {
        System.arraycopy(array, 0, OUTPUT_BUFFER, outputSize, length);
        outputSize += length;
    }

    public void number(short integer) {
        BitConverter.putBytes(integer, OUTPUT_BUFFER, outputSize);
        outputSize += 2;
    }

    public void number(int integer) {
        BitConverter.putBytes(integer, OUTPUT_BUFFER, outputSize);
        outputSize += 4;
    }

    public void number(long integer) {
        BitConverter.putBytes(integer, OUTPUT_BUFFER, outputSize);
        outputSize += 8;
    }

    public void number(double integer) {
        BitConverter.putBytes(integer, OUTPUT_BUFFER, outputSize);
        outputSize += 8;
    }

    /**
     * Packs the output buffer into a byte[] with the command byte prepended.
     * MT4 format: [command_byte][payload...]
     */
    public byte[] pack(byte command) {
        byte[] packet = byteArrayPool.get(1 + outputSize);
        packet[0] = command;
        System.arraycopy(OUTPUT_BUFFER, 0, packet, 1, outputSize);
        return packet;
    }

    /**
     * Packs the raw output buffer without a command byte prefix.
     */
    public byte[] packRaw() {
        byte[] packet = byteArrayPool.get(outputSize);
        System.arraycopy(OUTPUT_BUFFER, 0, packet, 0, outputSize);
        return packet;
    }

    public byte[] getRawBuffer() {
        return OUTPUT_BUFFER;
    }

    public int getOutputSize() {
        return outputSize;
    }
}
