package pro.mrpc.mt4.crypt;

/**
 * MT4 stream encryption/decryption using a rolling XOR cipher.
 * This is the stateful encoder/decoder that maintains position in the key stream.
 *
 * Unlike MT5 which has a single encrypt/decrypt state, MT4 uses separate
 * encoder and decoder states with independent rolling positions.
 */
public class MT4Encryption {
    private byte encoderLast = 0;
    private int encoderIndex = 0;

    private byte decoderLast = 0;
    private int decoderIndex = 0;

    private byte[] key;

    public MT4Encryption(byte[] key) {
        this.key = key;
    }

    public void changeKey(byte[] newKey) {
        this.key = newKey;
    }

    public byte[] getKey() {
        return key;
    }

    public synchronized byte[] encode(byte[] buf) {
        byte[] res = new byte[buf.length];
        for (int i = 0; i < buf.length; i++) {
            encoderIndex &= 0xF;
            res[i] = (byte) (buf[i] ^ (encoderLast + key[encoderIndex]));
            encoderIndex++;
            encoderLast = buf[i];
        }
        return res;
    }

    public synchronized byte[] decode(byte[] buf) {
        byte[] res = new byte[buf.length];
        for (int i = 0; i < buf.length; i++) {
            decoderIndex &= 0xF;
            res[i] = (byte) (buf[i] ^ (decoderLast + key[decoderIndex]));
            decoderIndex++;
            decoderLast = res[i];
        }
        return res;
    }

    public void resetEncoder() {
        encoderLast = 0;
        encoderIndex = 0;
    }

    public void resetDecoder() {
        decoderLast = 0;
        decoderIndex = 0;
    }

    public void reset() {
        resetEncoder();
        resetDecoder();
    }
}
