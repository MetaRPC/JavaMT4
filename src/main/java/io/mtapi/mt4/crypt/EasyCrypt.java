package io.mtapi.mt4.crypt;

/**
 * Initial login packet obfuscation for MT4 protocol.
 * Uses a fixed key for the initial login request before encryption keys are established.
 */
public class EasyCrypt {
    private static final byte[] CRYPT_KEY = new byte[]{
            0x41, (byte) 0xB6, 0x7F, 0x58, 0x38, 0x0C, (byte) 0xF0, 0x2D,
            0x7B, 0x39, 0x08, (byte) 0xFE, 0x21, (byte) 0xBB, 0x41, 0x58
    };

    /**
     * Encrypts a login buffer in-place. Skips byte[0] (command byte).
     */
    public static void encrypt(byte[] buf) {
        byte value = 0;
        for (int i = 1; i < buf.length; i++) {
            value = (byte) ((value + (CRYPT_KEY[(i - 1) & 0xF] & 0xFF)) ^ buf[i]);
            buf[i] = value;
        }
    }

    /**
     * Decrypts a received easy-crypt buffer.
     */
    public static byte[] decrypt(byte[] buf) {
        byte prev = 0;
        byte[] res = new byte[buf.length];
        for (int i = 0; i < buf.length; i++) {
            byte value = (byte) (prev + (CRYPT_KEY[i & 0xF] & 0xFF));
            res[i] = (byte) (buf[i] ^ value);
            prev = buf[i];
        }
        return res;
    }
}
