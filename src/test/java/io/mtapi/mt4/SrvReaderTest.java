package io.mtapi.mt4;

import io.mtapi.mt4.crypt.MT4Crypt;
import io.mtapi.mt4.network.parts.ServerInfo;
import io.mtapi.mt4.utils.BitConverter;
import io.mtapi.mt4.utils.SrvReader;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SrvReader} — parses .srv binary files into {@link ServerInfo}.
 * Uses synthetic srv data built in-memory (no real .srv file required).
 */
class SrvReaderTest {

    /**
     * Builds a synthetic .srv byte array with the given parameters.
     * Layout: PrimaryServer (352 bytes) + N encrypted SecondaryServer entries (160 bytes each).
     */
    private static byte[] buildSrvData(String name, String comment, boolean isDemo,
                                       String hostAddr, String[][] secondaryServers) {
        int count = secondaryServers != null ? secondaryServers.length : 0;
        int totalSize = 352 + count * 160;
        byte[] data = new byte[totalSize];

        // PrimaryServer header
        putString(data, 0, name, 64);
        putString(data, 64, comment, 128);
        BitConverter.putBytes(isDemo ? 1 : 0, data, 192);
        // ping=0 at 196, dummies at 200/204, ptr_dataserver at 208
        BitConverter.putBytes(count, data, 212);
        putString(data, 216, hostAddr, 64);
        // time at 280, dummy_11C[16] at 284, ptr_next at 348

        // Build secondary server entries (plaintext), then encrypt
        if (count > 0) {
            byte[] plain = new byte[count * 160];
            for (int i = 0; i < count; i++) {
                int off = i * 160;
                putString(plain, off, secondaryServers[i][0], 64);      // server
                // ip at off+64 (4 bytes, leave zero)
                putString(plain, off + 68, secondaryServers[i][1], 64); // description
                BitConverter.putBytes(0, plain, off + 132); // is_proxy = false
                BitConverter.putBytes(i, plain, off + 136); // priority = index
            }
            // Encrypt with the standard MT4 crypt key
            byte[] encrypted = MT4Crypt.encode(plain, MT4Crypt.getEasyCryptKey());
            System.arraycopy(encrypted, 0, data, 352, encrypted.length);
        }

        return data;
    }

    private static void putString(byte[] dest, int offset, String value, int fieldLen) {
        byte[] bytes = value.getBytes(StandardCharsets.US_ASCII);
        int len = Math.min(bytes.length, fieldLen);
        System.arraycopy(bytes, 0, dest, offset, len);
    }

    // ------------------------------------------------------------------ //

    @Test
    @DisplayName("Parse srv with primary + secondary servers")
    void testReadWithSecondaryServers() throws IOException {
        byte[] srvData = buildSrvData(
                "TestBroker-Demo",
                "Test Broker Demo Server",
                true,
                "primary.broker.com:443",
                new String[][]{
                        {"dc1.broker.com:443", "Data Center 1"},
                        {"dc2.broker.com:444", "Data Center 2"},
                }
        );

        ServerInfo info = SrvReader.read(srvData);

        assertNotNull(info);
        assertEquals("TestBroker-Demo", info.name);
        assertEquals("Test Broker Demo Server", info.comment);
        assertTrue(info.isDemo);
        assertEquals("primary.broker.com:443", info.hostAddress);
        assertEquals("primary.broker.com", info.getHost());
        assertEquals(443, info.getPort());

        assertNotNull(info.servers);
        assertEquals(2, info.servers.length);

        assertEquals("dc1.broker.com:443", info.servers[0].server);
        assertEquals("Data Center 1", info.servers[0].description);
        assertEquals("dc1.broker.com", info.servers[0].getHost());
        assertEquals(443, info.servers[0].getPort());
        assertEquals(0, info.servers[0].priority);

        assertEquals("dc2.broker.com:444", info.servers[1].server);
        assertEquals("Data Center 2", info.servers[1].description);
        assertEquals("dc2.broker.com", info.servers[1].getHost());
        assertEquals(444, info.servers[1].getPort());
        assertEquals(1, info.servers[1].priority);
    }

    @Test
    @DisplayName("Parse srv with no secondary servers")
    void testReadNoSecondaryServers() throws IOException {
        byte[] srvData = buildSrvData(
                "LiveServer",
                "Production",
                false,
                "live.broker.com:1950",
                new String[0][]
        );

        ServerInfo info = SrvReader.read(srvData);

        assertNotNull(info);
        assertEquals("LiveServer", info.name);
        assertEquals("Production", info.comment);
        assertFalse(info.isDemo);
        assertEquals("live.broker.com:1950", info.hostAddress);
        assertEquals("live.broker.com", info.getHost());
        assertEquals(1950, info.getPort());
        assertEquals(0, info.servers.length);
    }

    @Test
    @DisplayName("Reject data that is too short")
    void testReadTooShort() {
        assertThrows(IOException.class, () -> SrvReader.read(new byte[100]));
    }

    @Test
    @DisplayName("Host without port defaults to 443")
    void testHostWithoutPort() throws IOException {
        byte[] srvData = buildSrvData("Broker", "", false, "noport.broker.com", null);

        ServerInfo info = SrvReader.read(srvData);

        assertEquals("noport.broker.com", info.getHost());
        assertEquals(443, info.getPort());
    }
}
