package io.mtapi.mt4.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-256 utility for MT4 login key derivation.
 */
public class SHA256 {
    private static final ThreadLocal<MessageDigest> SHA256_DIGEST = ThreadLocal.withInitial(() -> {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    });

    public static byte[] computeHash(byte[] data) {
        MessageDigest sha = SHA256_DIGEST.get();
        sha.reset();
        return sha.digest(data);
    }

    /**
     * Compute SHA-256 and return only the first 16 bytes (used as a 128-bit key in MT4).
     */
    public static byte[] computeKey(byte[] data) {
        byte[] shaKey = computeHash(data);
        byte[] key = new byte[16];
        System.arraycopy(shaKey, 0, key, 0, 16);
        return key;
    }
}
