package io.mtapi.mt4.utils;

/**
 * Local LoginId decoder for MT4 login identification.
 * Implements two decode algorithms:
 * <ul>
 *   <li>{@link #decode(byte[])} - Complex algorithm for packet type 9 (server build &lt; 1435)</li>
 *   <li>{@link #decodeData(byte[])} - Simple table-driven algorithm for packet type 11 (LoginIdData)</li>
 * </ul>
 */
public class LoginId {

    private static final long[] BIT_MASKS = {
            0x0000000000000001L, 0x0000000000000002L, 0x0000000000000004L, 0x0000000000000008L,
            0x0000000000000010L, 0x0000000000000020L, 0x0000000000000040L, 0x0000000000000080L,
            0x0000000000000100L, 0x0000000000000200L, 0x0000000000000400L, 0x0000000000000800L,
            0x0000000000001000L, 0x0000000000002000L, 0x0000000000004000L, 0x0000000000008000L,
            0x0000000000010000L, 0x0000000000020000L, 0x0000000000040000L, 0x0000000000080000L,
            0x0000000000100000L, 0x0000000000200000L, 0x0000000000400000L, 0x0000000000800000L,
            0x0000000001000000L, 0x0000000002000000L, 0x0000000004000000L, 0x0000000008000000L,
            0x0000000010000000L, 0x0000000020000000L, 0x0000000040000000L, 0x0000000080000000L,
            0x0000000100000000L, 0x0000000200000000L, 0x0000000400000000L, 0x0000000800000000L,
            0x0000001000000000L, 0x0000002000000000L, 0x0000004000000000L, 0x0000008000000000L,
            0x0000010000000000L, 0x0000020000000000L, 0x0000040000000000L, 0x0000080000000000L,
            0x0000100000000000L, 0x0000200000000000L, 0x0000400000000000L, 0x0000800000000000L,
            0x0001000000000000L, 0x0002000000000000L, 0x0004000000000000L, 0x0008000000000000L,
            0x0010000000000000L, 0x0020000000000000L, 0x0040000000000000L, 0x0080000000000000L,
            0x0100000000000000L, 0x0200000000000000L, 0x0400000000000000L, 0x0800000000000000L,
            0x1000000000000000L, 0x2000000000000000L, 0x4000000000000000L, 0x8000000000000000L
    };

    private long s0;
    private long s8;

    /**
     * Decodes LoginId from raw byte data using the complex algorithm (packet type 9).
     */
    public long decode(byte[] data) {
        long[] d = new long[data.length / 8];
        for (int i = 0; i < d.length; i++) {
            d[i] = BitConverter.toInt64(data, i * 8);
        }
        return decodeComplex(d, d.length);
    }

    /**
     * Decodes LoginIdData from raw byte data using the simple table-driven algorithm (packet type 11).
     */
    public long decodeData(byte[] data) {
        long[] d = new long[data.length / 8];
        for (int i = 0; i < d.length; i++) {
            d[i] = BitConverter.toInt64(data, i * 8);
        }
        return decodeDataImpl(d, d.length);
    }

    private long decodeDataImpl(long[] pData, int szData) {
        long id = 0;
        for (int i = 0; i + 2 < szData; i += 3) {
            byte depth = (byte) (pData[i] >>> 14);
            long value = pData[i + 2];
            if (((pData[i + 1] & 0x3FC000) ^ 0x14C000) != 0) {
                id = pData[i + 1];
            }
            switch (depth) {
                case 0x12 -> id |= value;
                case 0x3A -> id -= value;
                case 0x40 -> id >>>= (int) Long.remainderUnsigned(value, 24);
                case 0x79 -> id ^= value;
                case (byte) 0x8B -> id += value;
                case (byte) 0xB5 -> id = (id & 0xffffffffL) | (((value >>> 32) & 0xffffffffL) << 32);
                case (byte) 0xC2 -> id <<= (int) Long.remainderUnsigned(value, 24);
                case (byte) 0xE1 -> id &= value;
                default -> { return 0; }
            }
        }
        return id;
    }

    @SuppressWarnings("all")
    private long decodeComplex(long[] pData, int szData) {
        s0 = 0;
        s8 = 0;

        long v158 = pData[0];
        long v140 = 0;

        while (true) {
            long v70 = v158;
            long vD0 = v158;
            long vC8 = pData[(int) s8];
            long v88 = 0;
            long v98 = 0;

            // Bit-level addition of vC8 + vD0
            for (int i = 0; i < 0x40; i++) {
                long vA0 = (vC8 & BIT_MASKS[i]) != 0 ? 1L : 0L;
                long v78 = (vD0 & BIT_MASKS[i]) != 0 ? 1L : 0L;
                long v48 = vA0 ^ v78 ^ v98;
                v98 = ((vA0 != 0 && v78 != 0) || (vA0 != 0 && v98 != 0)) ? 1L : (v78 & v98);
                long v68 = v48 != 0L ? ~0L : 0L;
                v88 |= BIT_MASKS[i] & v68;
            }
            vD0 = v88;

            // Bit-level addition of vD0 + (~v70 + 1)
            vC8 = ~v70 + 1;
            v88 = 0;
            v98 = 0;
            for (int i = 0; i < 0x40; i++) {
                long vA0 = (vD0 & BIT_MASKS[i]) != 0 ? 1L : 0L;
                long v78 = (vC8 & BIT_MASKS[i]) != 0 ? 1L : 0L;
                long vD8 = vA0 ^ v78 ^ v98;
                v98 = ((vA0 & v78) != 0 || (vA0 & v98) != 0) ? 1L : (v78 & v98);
                long v58 = vD8 != 0L ? ~0L : 0L;
                v88 |= BIT_MASKS[i] & v58;
            }

            // Swap hi/lo 32-bit words
            long v150 = swapWords(v88);

            // Compute 0x1C - 0x15 = 7 via bit-level subtraction
            long v100 = 0x1C;
            long v138 = 0x15;
            long v128 = ~v138 + 1;
            long v78 = 0;
            long vB0 = 0;
            for (int i = 0; i < 0x40; i++) {
                long vB8 = (v100 & BIT_MASKS[i]) != 0 ? 1L : 0L;
                v98 = (v128 & BIT_MASKS[i]) != 0 ? 1L : 0L;
                long v90 = vB8 ^ v98 ^ vB0;
                vB0 = ((vB8 & v98) != 0 || (vB0 & vB8) != 0) ? 1L : (v98 & vB0);
                long v68 = v90 != 0 ? ~0L : 0L;
                v78 |= BIT_MASKS[i] & v68;
            }

            // Rotate right by 0x15 within each 32-bit word
            v150 = rotateRight32Words(v150, 0x15);

            long v0 = v78 + 1;
            long v18 = ~0L << ((int) (v0 & 0xffffffffL));
            long v20 = swapWords(v150);
            v140 = v20 & ~v18;

            // Check v140 against 0xD8
            long vC0 = subtractBitwise(v140, 0xD8);
            if (vC0 == 0) {
                v18 = s0;
                s0 = 0;
                return v18;
            }

            long s10 = pData[(int) ((s8 + 1) & 0xffffffffL)];

            // XOR-add to compute index for s18
            long v60 = s8;
            long v68 = 2;
            do {
                v68 = v60 ^ v68;
                v60 = (v60 & ~v68) << 1;
            } while (v60 != 0);
            long s18 = pData[(int) (v68 & 0xffffffffL)];

            // Process s10 the same way
            v150 = swapWords(s10);
            v128 = ~v138 + 1;
            v78 = 0;
            vB0 = 0;
            for (int i = 0; i < 0x40; i++) {
                long vB8 = (v100 & BIT_MASKS[i]) != 0 ? 1L : 0L;
                v98 = (v128 & BIT_MASKS[i]) != 0 ? 1L : 0L;
                long v90 = vB8 ^ v98 ^ vB0;
                vB0 = ((vB8 & v98) != 0 || (vB0 & vB8) != 0) ? 1L : (v98 & vB0);
                v68 = v90 != 0 ? ~0L : 0L;
                v78 |= BIT_MASKS[i] & v68;
            }
            v150 = rotateRight32Words(v150, 0x15);
            v0 = v78 + 1;
            v18 = ~0L << ((int) (v0 & 0xffffffffL));
            v20 = swapWords(v150);
            v68 = v20 & ~v18;

            long v80 = ~(v68 ^ ~0xF5L);
            if (v80 == 0) {
                s10 = s0;
            }

            // Check v140 == 0x54 -> s0 = s10 & s18
            vC0 = subtractBitwise(v140, 0x54);
            if (vC0 == 0) {
                s0 = s10 & s18;
            }

            // Check v140 == 0x70 -> s0 = s10 | s18
            long vA0 = subtractBitwise(v140, 0x70);
            if (vA0 == 0) {
                s0 = s10 | s18;
            }

            // Check v140 == 0x91 -> s0 = s10 ^ s18
            v88 = subtractBitwise(v140, 0x91);
            if (v88 == 0) {
                s0 = s10 ^ s18;
            }

            // Check v140 == 0xAB -> s0 = s10 + s18 (bitwise add)
            v88 = subtractBitwise(v140, 0xAB);
            if (v88 == 0) {
                long va = s10;
                long vb = s18;
                do {
                    vb = va ^ vb;
                    va = (va & ~vb) << 1;
                } while (va != 0);
                s0 = vb;
            }

            // Check v140 == 0xA9 -> s0 = s10 - s18 (bitwise subtract)
            long vE0 = subtractBitwise(v140, 0xA9);
            if (vE0 == 0) {
                long v40 = s10;
                long v48 = ~s18 + 1;
                do {
                    v48 = v40 ^ v48;
                    v40 = (v40 & ~v48) << 1;
                } while (v40 != 0);
                s0 = v48;
            }

            // Check v140 == 0xB1 -> s0 = s10 << (s18 % 24) (via doubling)
            vE0 = subtractBitwise(v140, 0xB1);
            if (vE0 == 0) {
                long rem = Long.remainderUnsigned(s18, 24);
                v70 = s10;
                long v148 = 0;
                while (true) {
                    // Compare v148 < rem via bit-level comparison
                    if (compareLessThanUnsigned(v148, rem) == 0) {
                        s0 = v70;
                        break;
                    }
                    // v70 = v70 + v70 (bit-level double)
                    vD0 = v70;
                    vC8 = v70;
                    v88 = 0;
                    v98 = 0;
                    for (int i = 0; i < 0x40; i++) {
                        vA0 = (vC8 & BIT_MASKS[i]) != 0 ? 1L : 0L;
                        v78 = (vD0 & BIT_MASKS[i]) != 0 ? 1L : 0L;
                        long v48 = vA0 ^ v78 ^ v98;
                        v98 = ((vA0 & v78) != 0 || (vA0 & v98) != 0) ? 1L : (v78 & v98);
                        v68 = v48 != 0 ? ~0L : 0L;
                        v88 |= BIT_MASKS[i] & v68;
                    }
                    v70 = v88;
                    v148++;
                }
            }

            // Check v140 == 0xC8 -> s0 = s10 >>> (s18 % 24) (via bit extraction)
            v80 = v140 ^ 0xC8;
            if (v80 == 0) {
                long v1A0 = Long.remainderUnsigned(s18, 24);
                long v190 = s10;
                v100 = 0;
                long v160 = 0;
                while (true) {
                    // Compare v160 < 64
                    if (compareLessThanUnsigned(v160, 0x40) == 0) {
                        s0 = v100;
                        break;
                    }
                    v80 = v190 & BIT_MASKS[(int) v160];
                    if (v80 != 0 && v160 >= v1A0) {
                        vE0 = subtractBitwise(v160, v1A0);
                        v100 ^= BIT_MASKS[(int) vE0];
                    }
                    v160++;
                }
            }

            s8 += 3;
        }
    }

    private static long swapWords(long v) {
        return ((v >>> 32) & 0xffffffffL) | ((v & 0xffffffffL) << 32);
    }

    private static long rotateRight32Words(long v, int shift) {
        long lo = v & 0xffffffffL;
        long hi = (v >>> 32) & 0xffffffffL;
        lo = (lo >>> shift) | ((hi >>> shift) | (lo << (32 - shift))) << 32;
        // Replicating the exact bit manipulation from the original:
        long loResult = (v & 0xffffffffL) >>> shift;
        long hiShifted = ((v >>> 32) & 0xffffffffL) >>> shift;
        long loCarry = (v & 0xffffffffL) << (32 - shift);
        long hiResult = hiShifted | loCarry;
        return (loResult & 0xffffffffL) | ((hiResult & 0xffffffffL) << 32);
    }

    private static long subtractBitwise(long a, long b) {
        long neg = ~b + 1;
        long result = 0;
        long carry = 0;
        for (int i = 0; i < 0x40; i++) {
            long ba = (a & BIT_MASKS[i]) != 0 ? 1L : 0L;
            long bb = (neg & BIT_MASKS[i]) != 0 ? 1L : 0L;
            long sum = ba ^ bb ^ carry;
            carry = ((ba & bb) != 0 || (ba & carry) != 0) ? 1L : (bb & carry);
            if (sum != 0) result |= BIT_MASKS[i];
        }
        return result;
    }

    /**
     * Returns 1 if a < b (unsigned), 0 otherwise.
     * Simplified version of the bit-level comparison in the original code.
     */
    private static long compareLessThanUnsigned(long a, long b) {
        return Long.compareUnsigned(a, b) < 0 ? 1L : 0L;
    }
}
