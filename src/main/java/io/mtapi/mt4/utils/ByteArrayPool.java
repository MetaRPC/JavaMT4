package io.mtapi.mt4.utils;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ByteArrayPool {
    private final ConcurrentHashMap<Integer, ConcurrentLinkedQueue<byte[]>> pool = new ConcurrentHashMap<>();

    public byte[] get(int size) {
        var queue = pool.computeIfAbsent(size, k -> new ConcurrentLinkedQueue<>());
        byte[] array = queue.poll();
        if (array == null) {
            return new byte[size];
        }
        Arrays.fill(array, (byte) 0);
        return array;
    }

    public void release(byte[] array) {
        if (array == null) return;
        pool.computeIfAbsent(array.length, k -> new ConcurrentLinkedQueue<>()).offer(array);
    }
}
