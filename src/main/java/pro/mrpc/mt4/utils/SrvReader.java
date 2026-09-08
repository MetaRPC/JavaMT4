package pro.mrpc.mt4.utils;

import pro.mrpc.mt4.crypt.MT4Crypt;
import pro.mrpc.mt4.network.parts.ServerInfo;
import pro.mrpc.mt4.network.parts.ServerInfo.ServerEntry;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Reads MT4 {@code .srv} binary files into {@link ServerInfo}.
 * <p>
 * The {@code .srv} file format (as written by the MT4 terminal) is:
 * <pre>
 *   PrimaryServer struct (352 bytes):
 *     name         (64 bytes)  — broker/server name
 *     comment      (128 bytes) — description
 *     is_demo      (4 bytes)
 *     ping         (4 bytes)
 *     dummy_C8     (4 bytes)
 *     dummy_CC     (4 bytes)
 *     ptr_data     (4 bytes)
 *     count        (4 bytes)   — number of secondary server entries
 *     host_addr    (64 bytes)  — primary host address (host:port)
 *     time         (4 bytes)
 *     dummy_11C    (64 bytes)  — 16 ints reserved
 *     ptr_next     (4 bytes)
 *   Followed by count × 160 bytes of XOR-encrypted SecondaryServer entries.
 * </pre>
 *
 * <p>Usage:
 * <pre>{@code
 * ServerInfo info = SrvReader.read("/path/to/MyBroker-Demo.srv");
 * System.out.println(info.name + " -> " + info.getHost() + ":" + info.getPort());
 * }</pre>
 */
public class SrvReader {

    private static final int PRIMARY_SERVER_SIZE = 352;
    private static final int SERVER_ENTRY_SIZE = 160;

    private static final byte[] SRV_CRYPT_KEY = MT4Crypt.getEasyCryptKey();

    /**
     * Reads a {@code .srv} file from disk.
     *
     * @param srvFilePath path to the {@code .srv} file
     * @return parsed ServerInfo
     * @throws IOException if the file does not exist or cannot be read
     */
    public static ServerInfo read(String srvFilePath) throws IOException {
        return read(Path.of(srvFilePath));
    }

    /**
     * Reads a {@code .srv} file from disk.
     *
     * @param path path to the {@code .srv} file
     * @return parsed ServerInfo
     * @throws IOException if the file does not exist or cannot be read
     */
    public static ServerInfo read(Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            throw new FileNotFoundException(path + " not found");
        }
        return read(Files.readAllBytes(path));
    }

    /**
     * Parses a {@code .srv} file from a byte array.
     *
     * @param data the raw bytes of the {@code .srv} file
     * @return parsed ServerInfo
     * @throws IOException if the data is too short or malformed
     */
    public static ServerInfo read(byte[] data) throws IOException {
        if (data == null || data.length < PRIMARY_SERVER_SIZE) {
            throw new IOException("Invalid srv data: too short (expected at least "
                    + PRIMARY_SERVER_SIZE + " bytes, got " + (data == null ? 0 : data.length) + ")");
        }

        ServerInfo info = new ServerInfo();

        // Parse PrimaryServer header (352 bytes)
        info.name = BitConverter.getString(data, 0, 64);
        info.comment = BitConverter.getString(data, 64, 128);
        info.isDemo = BitConverter.toInt32(data, 192) != 0;
        // ping at 196, dummy at 200, 204, ptr_dataserver at 208
        int count = BitConverter.toInt32(data, 212);
        info.hostAddress = BitConverter.getString(data, 216, 64);
        // time at 280, dummy_11C[16] at 284, ptr_next at 348

        // Parse secondary server entries (each 160 bytes, XOR-encrypted)
        if (count > 0 && count <= 128) {
            int expectedSize = PRIMARY_SERVER_SIZE + count * SERVER_ENTRY_SIZE;
            if (data.length < expectedSize) {
                throw new IOException("Invalid srv data: expected " + expectedSize
                        + " bytes for " + count + " servers, got " + data.length);
            }

            byte[] encrypted = new byte[count * SERVER_ENTRY_SIZE];
            System.arraycopy(data, PRIMARY_SERVER_SIZE, encrypted, 0, encrypted.length);
            byte[] decrypted = MT4Crypt.decode(encrypted, SRV_CRYPT_KEY);

            info.servers = new ServerEntry[count];
            for (int i = 0; i < count; i++) {
                int off = i * SERVER_ENTRY_SIZE;
                ServerEntry entry = new ServerEntry();
                entry.server = BitConverter.getString(decrypted, off, 64);
                // ip at off+64 (4 bytes)
                entry.description = BitConverter.getString(decrypted, off + 68, 64);
                entry.isProxy = BitConverter.toInt32(decrypted, off + 132) != 0;
                entry.priority = BitConverter.toInt32(decrypted, off + 136);
                info.servers[i] = entry;
            }
        } else {
            info.servers = new ServerEntry[0];
        }

        return info;
    }
}
