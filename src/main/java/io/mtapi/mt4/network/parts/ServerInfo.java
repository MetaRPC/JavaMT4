package io.mtapi.mt4.network.parts;

import io.mtapi.mt4.utils.BitConverter;

/**
 * Parsed broker/server information from the MT4 servers list.
 * <p>
 * The raw data from the server has this layout:
 * <pre>
 *   [0-1]  ServerVersion (short)
 *   [2-3]  ServerBuild   (short)
 *   [4 .. 4 + count*160 - 1]  SecondaryServer structs (each 160 bytes)
 *   [4 + count*160 .. end]    ServerInfo: name (64 bytes) + comment (128 bytes) = 192 bytes
 * </pre>
 * Where {@code count = (data.length - 0xC4) / 160} and {@code 0xC4 = 4 + 192}.
 * The primary server host address is taken from the first SecondaryServer's {@code server} field.
 */
public class ServerInfo {
    /** Broker/server name. */
    public String name;
    /** Broker/server description/comment. */
    public String comment;
    /** Whether this is a demo server. */
    public boolean isDemo;
    /** Main server host address (host:port), taken from the first server entry. */
    public String hostAddress;
    /** Server entries (all server addresses including the primary). */
    public ServerEntry[] servers;

    /**
     * Returns the host part of the main server address.
     */
    public String getHost() {
        return parseHost(hostAddress);
    }

    /**
     * Returns the port part of the main server address (defaults to 443).
     */
    public int getPort() {
        return parsePort(hostAddress);
    }

    @Override
    public String toString() {
        return "ServerInfo{name='" + name + "', comment='" + comment + "', isDemo=" + isDemo
                + ", host='" + hostAddress + "', servers=" +
                (servers != null ? servers.length : 0) + '}';
    }

    /**
     * A server entry from the servers list.
     */
    public static class ServerEntry {
        /** Server address (host:port). */
        public String server;
        /** Server description. */
        public String description;
        /** Whether this server can act as proxy. */
        public boolean isProxy;
        /** Priority (0-7 base, 255 = idle). */
        public int priority;

        public String getHost() {
            return parseHost(server);
        }

        public int getPort() {
            return parsePort(server);
        }

        @Override
        public String toString() {
            return "ServerEntry{server='" + server + "', desc='" + description
                    + "', priority=" + priority + '}';
        }
    }

    // ======================== Parsing ========================

    private static final int HEADER_SIZE = 4;       // ServerVersion(2) + ServerBuild(2)
    private static final int SERVER_ENTRY_SIZE = 160;
    private static final int SERVER_INFO_SIZE = 192; // name(64) + comment(128)
    private static final int OVERHEAD = HEADER_SIZE + SERVER_INFO_SIZE; // 0xC4 = 196

    /**
     * Parses the raw servers list buffer received during login.
     *
     * @param data   the raw servers list bytes (starts with 4-byte header)
     * @param isDemo whether this is a demo server (from the protocol is_demo byte)
     * @return parsed ServerInfo, or null if data is too short
     */
    public static ServerInfo parse(byte[] data, boolean isDemo) {
        if (data == null || data.length < OVERHEAD) return null;

        int count = (data.length - OVERHEAD) / SERVER_ENTRY_SIZE;
        if (count < 0) count = 0;

        ServerInfo info = new ServerInfo();
        info.isDemo = isDemo;

        // Parse server entries starting at offset 4 (each 160 bytes)
        // SecondaryServer struct: server(64) ip(4) desc(64) is_proxy(4) priority(4)
        //                         loading(4) ip_internal(4) ping(4) reserved(4) ptr_next(4) = 160
        info.servers = new ServerEntry[count];
        for (int i = 0; i < count; i++) {
            int off = HEADER_SIZE + i * SERVER_ENTRY_SIZE;
            ServerEntry entry = new ServerEntry();
            entry.server = BitConverter.getString(data, off, 64);
            // ip at off+64 (4 bytes, skip)
            entry.description = BitConverter.getString(data, off + 68, 64);
            entry.isProxy = BitConverter.toInt32(data, off + 132) != 0;
            entry.priority = BitConverter.toInt32(data, off + 136);
            info.servers[i] = entry;
        }

        // Primary host address comes from the first server entry
        if (count > 0) {
            info.hostAddress = info.servers[0].server;
        }

        // ServerInfo (name + comment) is at the end, after all server entries
        int srvInfoOffset = HEADER_SIZE + count * SERVER_ENTRY_SIZE;
        if (srvInfoOffset + SERVER_INFO_SIZE <= data.length) {
            info.name = BitConverter.getString(data, srvInfoOffset, 64);
            info.comment = BitConverter.getString(data, srvInfoOffset + 64, 128);
        }

        return info;
    }

    private static String parseHost(String address) {
        if (address == null || address.isEmpty()) return "";
        int idx = address.indexOf(':');
        return idx >= 0 ? address.substring(0, idx).trim() : address.trim();
    }

    private static int parsePort(String address) {
        if (address == null || address.isEmpty()) return 443;
        int idx = address.indexOf(':');
        if (idx >= 0 && idx < address.length() - 1) {
            try {
                return Integer.parseInt(address.substring(idx + 1).trim());
            } catch (NumberFormatException e) {
                return 443;
            }
        }
        return 443;
    }
}
