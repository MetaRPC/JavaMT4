package pro.mrpc.mt4.utils;

/**
 * A fast, allocation-free decompressor based on the LZO algorithm.
 * Used for decompressing MT4 server data (symbols, groups, orders, etc.).
 */
public class FastDecompressor {

    private static final int M2_MAX_OFFSET = 0x0800;

    private FastDecompressor() {
    }

    public static int decompress(byte[] src, int srcOffset, int srcLength, byte[] resultBuffer, int expectedSize) {
        if (src == null || resultBuffer == null || srcOffset < 0 || srcLength < 3 || expectedSize < 1 ||
                srcOffset + srcLength > src.length || resultBuffer.length < expectedSize) {
            return 0;
        }
        int decompressedSize = inflate(src, srcOffset, srcLength, resultBuffer, expectedSize);
        if (decompressedSize == expectedSize) {
            return decompressedSize;
        }
        return 0;
    }

    private static int inflate(byte[] src, int srcOffset, int srcLength, byte[] dst, int maxDstSize) {
        int ip = srcOffset;
        int op = 0;
        int mPos;

        final int ipEnd = srcOffset + srcLength;
        final int opEnd = maxDstSize;

        if ((src[ipEnd - 3] & 0xFF) != 17 || src[ipEnd - 2] != 0 || src[ipEnd - 1] != 0) {
            return 0;
        }

        int token = src[ip] & 0xFF;
        if (token > 17) {
            int len = token - 17;
            ip++;
            if (len >= 4) {
                if (op + len > opEnd || ip + len > ipEnd) return 0;
                System.arraycopy(src, ip, dst, op, len);
                ip += len;
                op += len;
            }
        }

        while (ip < ipEnd) {
            token = src[ip++] & 0xFF;

            if (token < 16) {
                int len = token;
                if (len == 0) {
                    while (true) {
                        if (ip >= ipEnd) return 0;
                        int nextByte = src[ip] & 0xFF;
                        if (nextByte != 0) {
                            len += 15 + nextByte;
                            ip++;
                            break;
                        }
                        len += 255;
                        ip++;
                    }
                }

                if (op + len + 3 > opEnd || ip + len + 3 > ipEnd) return 0;
                dst[op++] = src[ip++];
                dst[op++] = src[ip++];
                dst[op++] = src[ip++];
                System.arraycopy(src, ip, dst, op, len);
                ip += len;
                op += len;

                if (ip >= ipEnd) break;
                token = src[ip++] & 0xFF;

                if (token >= 16) {
                    // Fall through to match processing
                } else {
                    mPos = op - (1 + M2_MAX_OFFSET) - (token >> 2) - ((src[ip++] & 0xFF) << 2);
                    if (mPos < 0 || op + 3 > opEnd) return 0;
                    dst[op++] = dst[mPos++];
                    dst[op++] = dst[mPos++];
                    dst[op++] = dst[mPos];

                    int trailerLen = (src[ip - 2] & 0xFF) & 3;
                    if (trailerLen > 0) {
                        if (op + trailerLen > opEnd || ip + trailerLen > ipEnd) return 0;
                        System.arraycopy(src, ip, dst, op, trailerLen);
                        op += trailerLen;
                        ip += trailerLen;
                    }
                    continue;
                }
            }

            while (true) {
                int len;
                if (token >= 64) {
                    mPos = op - 1 - ((token >> 2) & 7) - ((src[ip++] & 0xFF) << 3);
                    len = (token >> 5) - 1;
                } else if (token >= 32) {
                    len = token & 31;
                    if (len == 0) {
                        while (true) {
                            if (ip >= ipEnd) return 0;
                            int nextByte = src[ip] & 0xFF;
                            if (nextByte != 0) {
                                len += 31 + nextByte;
                                ip++;
                                break;
                            }
                            len += 255;
                            ip++;
                        }
                    }
                    mPos = op - 1 - ((src[ip] & 0xFF) >> 2) - ((src[ip + 1] & 0xFF) << 6);
                    ip += 2;
                } else if (token >= 16) {
                    int offset = (token & 8) << 11;
                    len = token & 7;
                    if (len == 0) {
                        while (true) {
                            if (ip >= ipEnd) return 0;
                            int nextByte = src[ip] & 0xFF;
                            if (nextByte != 0) {
                                len += 7 + nextByte;
                                ip++;
                                break;
                            }
                            len += 255;
                            ip++;
                        }
                    }
                    offset += ((src[ip] & 0xFF) >> 2) + ((src[ip + 1] & 0xFF) << 6);
                    ip += 2;
                    if (offset == 0) {
                        return (ip == ipEnd) ? op : 0;
                    }
                    mPos = op - offset - 0x4000;
                } else {
                    mPos = op - 1 - (token >> 2) - ((src[ip++] & 0xFF) << 2);
                    if (mPos < 0 || op + 2 > opEnd) return 0;
                    dst[op++] = dst[mPos++];
                    dst[op++] = dst[mPos];

                    int trailerLen = (src[ip - 2] & 0xFF) & 3;
                    if (trailerLen > 0) {
                        if (op + trailerLen > opEnd || ip + trailerLen > ipEnd) return 0;
                        System.arraycopy(src, ip, dst, op, trailerLen);
                        op += trailerLen;
                        ip += trailerLen;
                    }

                    if (ip >= ipEnd) break;
                    token = src[ip++] & 0xFF;
                    continue;
                }

                if (mPos < 0 || op + len + 2 > opEnd) return 0;
                dst[op++] = dst[mPos++];
                dst[op++] = dst[mPos++];
                for (int i = 0; i < len; i++) {
                    dst[op++] = dst[mPos++];
                }

                int trailerLen = (src[ip - 2] & 0xFF) & 3;
                if (trailerLen == 0) {
                    break;
                }
                if (op + trailerLen > opEnd || ip + trailerLen > ipEnd) return 0;
                System.arraycopy(src, ip, dst, op, trailerLen);
                op += trailerLen;
                ip += trailerLen;

                if (ip >= ipEnd) break;
                token = src[ip++] & 0xFF;
            }
        }

        return (ip == ipEnd) ? op : 0;
    }
}
