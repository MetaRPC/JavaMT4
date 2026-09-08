package pro.mrpc.mt4.utils;

/**
 * MT4 SHA-1 implementations.
 * <p>
 * Two modes:
 * <ul>
 *   <li>{@link #computeHash(byte[])} — Non-standard variant (LE words, no padding, LE output).
 *       Used for key derivation during login.</li>
 *   <li>{@link #hashData(byte[])} + {@link #finalizeHash()} — Streaming SHA-1 with proper padding
 *       and big-endian output. Used for order transaction verification.</li>
 * </ul>
 */
public class VSHA1 {

    private int[] regs = {0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};

    // --- Streaming mode state (hashData + finalizeHash) ---
    private int nBitCount = 0;
    private int dwData = 0;
    private byte[] dwBlock = new byte[64];
    private int dwCount = 0;
    private int dbCount = 0;

    /**
     * Feeds data incrementally (streaming mode).
     * Call {@link #finalizeHash()} after all data has been fed.
     */
    public void hashData(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            dwData = (dwData << 8) + (data[i] & 0xFF);
            nBitCount += 8;
            if (++dbCount >= 4) {
                dbCount = 0;
                BitConverter.putBytes(dwData, dwBlock, dwCount * 4);
                if (++dwCount >= 16) {
                    dwCount = 0;
                    transform(dwBlock);
                }
                dwData = 0;
            }
        }
    }

    /**
     * Finalizes the streaming hash and returns 20 bytes (big-endian per register).
     */
    public byte[] finalizeHash() {
        int bitCnt = nBitCount;
        dwData = (dwData << 8) + 0x80;
        while (true) {
            nBitCount += 8;
            if (++dbCount >= 4) {
                dbCount = 0;
                BitConverter.putBytes(dwData, dwBlock, dwCount * 4);
                if (++dwCount >= 16) {
                    dwCount = 0;
                    transform(dwBlock);
                }
                dwData = 0;
            }
            if ((dbCount == 0) && (dwCount == 14)) {
                break;
            }
            dwData <<= 8;
        }
        BitConverter.putBytes(0, dwBlock, dwCount * 4);
        if (++dwCount >= 16) {
            dwCount = 0;
            transform(dwBlock);
        }
        BitConverter.putBytes(bitCnt, dwBlock, dwCount * 4);
        if (++dwCount >= 16) {
            dwCount = 0;
            transform(dwBlock);
        }
        return new byte[]{
                (byte) (regs[0] >>> 24), (byte) (regs[0] >>> 16), (byte) (regs[0] >>> 8), (byte) (regs[0]),
                (byte) (regs[1] >>> 24), (byte) (regs[1] >>> 16), (byte) (regs[1] >>> 8), (byte) (regs[1]),
                (byte) (regs[2] >>> 24), (byte) (regs[2] >>> 16), (byte) (regs[2] >>> 8), (byte) (regs[2]),
                (byte) (regs[3] >>> 24), (byte) (regs[3] >>> 16), (byte) (regs[3] >>> 8), (byte) (regs[3]),
                (byte) (regs[4] >>> 24), (byte) (regs[4] >>> 16), (byte) (regs[4] >>> 8), (byte) (regs[4])
        };
    }

    // --- One-shot mode (computeHash) — non-standard variant ---

    /**
     * Non-standard SHA-1 variant: LE word loading, no message padding, LE output.
     * Used for key derivation during login. NOT standard SHA-1.
     */
    byte[] computeHash(byte[] data) {
        int len = data.length;
        int left = 0;

        if (len >= 64) {
            byte[] block = new byte[64];
            for (int i = 0; i < len / 64; i++) {
                System.arraycopy(data, i * 64, block, 0, 64);
                transform(block);
                left += 64;
            }
        }

        int rem = len % 64;
        if (rem > 0) {
            byte[] block = new byte[64]; // zero-padded
            System.arraycopy(data, left, block, 0, rem);
            transform(block);
        }

        return new byte[]{
                (byte) (regs[0]), (byte) (regs[0] >>> 8), (byte) (regs[0] >>> 16), (byte) (regs[0] >>> 24),
                (byte) (regs[1]), (byte) (regs[1] >>> 8), (byte) (regs[1] >>> 16), (byte) (regs[1] >>> 24),
                (byte) (regs[2]), (byte) (regs[2] >>> 8), (byte) (regs[2] >>> 16), (byte) (regs[2] >>> 24),
                (byte) (regs[3]), (byte) (regs[3] >>> 8), (byte) (regs[3] >>> 16), (byte) (regs[3] >>> 24),
                (byte) (regs[4]), (byte) (regs[4] >>> 8), (byte) (regs[4] >>> 16), (byte) (regs[4] >>> 24)
        };
    }

    // --- Shared transform ---

    private static int sha1Shift(int bits, int word) {
        return (word << bits) | (word >>> (32 - bits));
    }

    private void transform(byte[] data) {
        int[] W = new int[80];
        for (int i = 0; i < 16; i++) {
            W[i] = BitConverter.toInt32(data, i * 4); // little-endian
        }
        for (int i = 16; i < 80; i++) {
            W[i] = sha1Shift(1, W[i - 3] ^ W[i - 8] ^ W[i - 14] ^ W[i - 16]);
        }

        int A = regs[0], B = regs[1], C = regs[2], D = regs[3], E = regs[4];

        for (int i = 0; i < 20; i++) {
            int temp = sha1Shift(5, A) + ((B & C) | (~B & D)) + E + W[i] + 0x5A827999;
            E = D; D = C; C = sha1Shift(30, B); B = A; A = temp;
        }
        for (int i = 20; i < 40; i++) {
            int temp = sha1Shift(5, A) + (B ^ C ^ D) + E + W[i] + 0x6ED9EBA1;
            E = D; D = C; C = sha1Shift(30, B); B = A; A = temp;
        }
        for (int i = 40; i < 60; i++) {
            int temp = sha1Shift(5, A) + ((B & C) | (B & D) | (C & D)) + E + W[i] + 0x8F1BBCDC;
            E = D; D = C; C = sha1Shift(30, B); B = A; A = temp;
        }
        for (int i = 60; i < 80; i++) {
            int temp = sha1Shift(5, A) + (B ^ C ^ D) + E + W[i] + 0xCA62C1D6;
            E = D; D = C; C = sha1Shift(30, B); B = A; A = temp;
        }

        regs[0] += A;
        regs[1] += B;
        regs[2] += C;
        regs[3] += D;
        regs[4] += E;
    }
}
