package pro.mrpc.mt4.nio;

import pro.mrpc.mt4.crypt.MT4Encryption;
import pro.mrpc.mt4.network.OutputFactory;
import pro.mrpc.mt4.utils.ByteArray;
import pro.mrpc.mt4.utils.ByteArrayPool;
import pro.mrpc.mt4.utils.MT4Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * MT4 socket layer with pooled byte arrays and output factories.
 * Unlike MT5 which encrypts packet payloads separately from headers,
 * MT4 uses a stateful stream encoder/decoder for the entire data stream.
 */
public class ServerSocket {
    public final ByteArrayPool byteArrayPool = new ByteArrayPool();
    public final OutputFactory.Pool outputFactoryPool;

    private final String host;
    private final int port;
    private Socket socket;
    private DataInputStream reader;
    private DataOutputStream writer;

    private volatile boolean connected = false;

    private final ByteArray rawInput = new ByteArray(MT4Constants.MAX_INPUT_SIZE);

    public ServerSocket(String host, int port) {
        this.host = host;
        this.port = port;
        outputFactoryPool = new OutputFactory.Pool(byteArrayPool);
    }

    protected void connect() throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), 30000);
        socket.setSoTimeout(30000);
        reader = new DataInputStream(socket.getInputStream());
        writer = new DataOutputStream(socket.getOutputStream());
        connected = true;
    }

    public boolean isConnected() {
        return connected;
    }

    /**
     * Sends raw data without any encoding.
     */
    public void sendRaw(byte[] data) throws IOException {
        writer.write(data);
        writer.flush();
    }

    /**
     * Sends data encoded with the stream encoder.
     */
    public synchronized void sendEncoded(byte[] data, MT4Encryption encryption) throws IOException {
        writer.write(encryption.encode(data));
        writer.flush();
    }

    /**
     * Reads exactly {@code count} bytes from the socket into the shared rawInput buffer.
     */
    public ByteArray receiveRaw(int count) throws IOException {
        rawInput.clear();
        rawInput.ensureCapacity(count);

        byte[] buf = rawInput.items;
        int rest = count;
        while (rest > 0) {
            int len = reader.read(buf, count - rest, rest);
            if (len == 0) {
                throw new IOException("Server disconnected");
            } else if (len == -1) {
                throw new IOException("Server closed the stream");
            } else {
                rest -= len;
            }
        }
        rawInput.size = count;
        return rawInput;
    }

    /**
     * Reads exactly {@code count} bytes and decodes them with the stream decoder.
     */
    public byte[] receiveDecoded(int count, MT4Encryption encryption) throws IOException {
        ByteArray raw = receiveRaw(count);
        byte[] data = new byte[count];
        System.arraycopy(raw.items, 0, data, 0, count);
        return encryption.decode(data);
    }

    /**
     * Reads compressed data: 4 bytes dst length, 4 bytes src length, then compressed bytes.
     * All decoded through the stream decoder.
     */
    public byte[] readCompressed(MT4Encryption encryption) throws IOException {
        byte[] buf = receiveDecoded(4, encryption);
        int dstLen = pro.mrpc.mt4.utils.BitConverter.toInt32(buf, 0);
        buf = receiveDecoded(4, encryption);
        int srcLen = pro.mrpc.mt4.utils.BitConverter.toInt32(buf, 0);
        buf = receiveDecoded(srcLen, encryption);

        byte[] result = new byte[dstLen];
        int decompressedSize = pro.mrpc.mt4.utils.FastDecompressor.decompress(buf, 0, srcLen, result, dstLen);
        if (decompressedSize != dstLen) {
            throw new IOException("Decompression failed: expected " + dstLen + " got " + decompressedSize);
        }
        return result;
    }

    protected void setSocketTimeout(int millis) {
        try {
            if (socket != null) socket.setSoTimeout(millis);
        } catch (Exception e) {
            // ignore
        }
    }

    protected void disconnect() throws IOException {
        if (connected) {
            connected = false;
            reader.close();
            writer.close();
            socket.close();
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void shrinkMemory() {
        // Do not shrink rawInput — it is actively used by the read thread
        // and shrinking from another thread causes a race condition.
    }
}
