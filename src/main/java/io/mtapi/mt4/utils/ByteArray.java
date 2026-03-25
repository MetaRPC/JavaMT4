package io.mtapi.mt4.utils;

import java.util.Arrays;

/**
 * A resizable, ordered byte array. Avoids the boxing that occurs with ArrayList&lt;Byte&gt;.
 * Zero-allocation on the hot path once pre-sized.
 */
public class ByteArray {
    public byte[] items;
    public int size;
    public boolean ordered;

    public ByteArray() {
        this(true, 16);
    }

    public ByteArray(int capacity) {
        this(true, capacity);
    }

    public ByteArray(boolean ordered, int capacity) {
        this.ordered = ordered;
        items = new byte[capacity];
    }

    public ByteArray(ByteArray array) {
        this.ordered = array.ordered;
        size = array.size;
        items = new byte[size];
        System.arraycopy(array.items, 0, items, 0, size);
    }

    public ByteArray(byte[] array) {
        this(true, array, 0, array.length);
    }

    public ByteArray(boolean ordered, byte[] array, int startIndex, int count) {
        this(ordered, count);
        size = count;
        System.arraycopy(array, startIndex, items, 0, count);
    }

    public void add(byte value) {
        byte[] items = this.items;
        if (size == items.length) items = resize(Math.max(8, (int) (size * 1.75f)));
        items[size++] = value;
    }

    public void addAll(ByteArray array) {
        addAll(array.items, 0, array.size);
    }

    public void addAll(byte[] array, int offset, int length) {
        byte[] items = this.items;
        int sizeNeeded = size + length;
        if (sizeNeeded > items.length) items = resize(Math.max(Math.max(8, sizeNeeded), (int) (size * 1.75f)));
        System.arraycopy(array, offset, items, size, length);
        size += length;
    }

    public byte get(int index) {
        if (index >= size) throw new IndexOutOfBoundsException("index can't be >= size: " + index + " >= " + size);
        return items[index];
    }

    public void set(int index, byte value) {
        if (index >= size) throw new IndexOutOfBoundsException("index can't be >= size: " + index + " >= " + size);
        items[index] = value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
    }

    public byte[] shrink() {
        if (items.length != size) resize(size);
        return items;
    }

    public byte[] ensureCapacity(int additionalCapacity) {
        if (additionalCapacity < 0) throw new IllegalArgumentException("additionalCapacity must be >= 0: " + additionalCapacity);
        int sizeNeeded = size + additionalCapacity;
        if (sizeNeeded > items.length) resize(Math.max(Math.max(8, sizeNeeded), (int) (size * 1.75f)));
        return items;
    }

    public byte[] setSize(int newSize) {
        if (newSize < 0) throw new IllegalArgumentException("newSize must be >= 0: " + newSize);
        if (newSize > items.length) resize(Math.max(8, newSize));
        size = newSize;
        return items;
    }

    protected byte[] resize(int newSize) {
        byte[] newItems = new byte[newSize];
        byte[] items = this.items;
        System.arraycopy(items, 0, newItems, 0, Math.min(size, newItems.length));
        this.items = newItems;
        return newItems;
    }

    public byte[] toArray() {
        byte[] array = new byte[size];
        System.arraycopy(items, 0, array, 0, size);
        return array;
    }

    public int hashCode() {
        if (!ordered) return super.hashCode();
        byte[] items = this.items;
        int h = 1;
        for (int i = 0, n = size; i < n; i++)
            h = h * 31 + items[i];
        return h;
    }

    public boolean equals(Object object) {
        if (object == this) return true;
        if (!ordered) return false;
        if (!(object instanceof ByteArray array)) return false;
        if (!array.ordered) return false;
        int n = size;
        if (n != array.size) return false;
        byte[] items1 = this.items, items2 = array.items;
        for (int i = 0; i < n; i++)
            if (items1[i] != items2[i]) return false;
        return true;
    }

    public String toString() {
        if (size == 0) return "[]";
        byte[] items = this.items;
        StringBuilder buffer = new StringBuilder(32);
        buffer.append('[');
        buffer.append(items[0]);
        for (int i = 1; i < size; i++) {
            buffer.append(", ");
            buffer.append(items[i]);
        }
        buffer.append(']');
        return buffer.toString();
    }

    public static ByteArray with(byte... array) {
        return new ByteArray(array);
    }
}
