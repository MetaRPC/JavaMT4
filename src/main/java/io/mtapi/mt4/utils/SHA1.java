package io.mtapi.mt4.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-1 utility for MT4 login key derivation.
 */
public class SHA1 {
    private static final ThreadLocal<MessageDigest> SHA1_DIGEST = ThreadLocal.withInitial(() -> {
        try {
            return MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    });

    public static byte[] computeHash(byte[] data) {
        MessageDigest sha = SHA1_DIGEST.get();
        sha.reset();
        return sha.digest(data);
    }

    /**
     * Compute SHA-1 over multiple byte arrays concatenated.
     */
    public static byte[] computeHash(byte[]... arrays) {
        MessageDigest sha = SHA1_DIGEST.get();
        sha.reset();
        for (byte[] array : arrays) {
            sha.update(array);
        }
        return sha.digest();
    }

    /**
     * Compute key using MT4's custom non-standard SHA-1 variant (VSHA1).
     * Returns only the first 16 bytes (used as a 128-bit key in MT4).
     * <p>
     * This uses little-endian word loading, no padding, and little-endian output,
     * which differs from standard SHA-1.
     */
    public static byte[] computeKey(byte[] data) {
        byte[] shaKey = new VSHA1().computeHash(data);
        byte[] key = new byte[16];
        System.arraycopy(shaKey, 0, key, 0, 16);
        return key;
    }
}
