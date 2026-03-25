package io.mtapi.mt4.crypt;

import io.mtapi.mt4.utils.MD5;

import java.time.LocalDateTime;

/**
 * MT4 XOR-based cipher for packet encoding/decoding.
 * Uses a 16-byte key with rolling XOR.
 */
public class MT4Crypt {
    private static final byte[] EASY_CRYPT_KEY = new byte[]{
            0x41, (byte) 0xB6, 0x7F, 0x58, 0x38, 0x0C, (byte) 0xF0, 0x2D,
            0x7B, 0x39, 0x08, (byte) 0xFE, 0x21, (byte) 0xBB, 0x41, 0x58
    };

    private static byte[] hardId;

    public static byte[] encode(byte[] buf, byte[] key) {
        int last = 0;
        byte[] res = new byte[buf.length];
        for (int i = 0; i < buf.length; i++) {
            res[i] = (byte) (buf[i] ^ (last + (key[i % key.length] & 0xFF)));
            last = res[i] & 0xFF;
        }
        return res;
    }

    public static byte[] decode(byte[] buf, byte[] key) {
        int last = 0;
        byte[] res = new byte[buf.length];
        for (int i = 0; i < buf.length; i++) {
            res[i] = (byte) (buf[i] ^ (last + (key[i % key.length] & 0xFF)));
            last = buf[i] & 0xFF;
        }
        return res;
    }

    public static byte[] getEasyCryptKey() {
        return EASY_CRYPT_KEY;
    }

    public static synchronized byte[] getHardId() {
        if (hardId == null || (hardId[0] == 0 && hardId[15] == 0)) {
            createHardId();
        }
        return hardId;
    }

    private static void createHardId() {
        int seed = (int) LocalDateTime.now().getNano();
        byte[] data = new byte[256];
        for (int i = 0; i < 256; i++) {
            seed = seed * 214013 + 2531011;
            data[i] = (byte) ((seed >>> 16) & 0xFF);
        }
        hardId = MD5.computeMD5(data);
        hardId[0] = 0;
        for (int i = 1; i < 16; i++) {
            hardId[0] += hardId[i];
        }
    }
}
