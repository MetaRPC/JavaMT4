package pro.mrpc.mt4.utils;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

/**
 * Searches the MetaQuotes broker network for MT4 server addresses by company name.
 * <p>
 * Usage:
 * <pre>{@code
 * SearchResult result = BrokerServerSearch.search("MyBroker");
 * for (BrokerResult broker : result.result) {
 *     for (ServerResult server : broker.results) {
 *         System.out.println(server.name + " -> " + Arrays.toString(server.access));
 *     }
 * }
 * }</pre>
 */
public class BrokerServerSearch {

    private static final String MT_VERSION = "mt4";
    private static final String BASE_URL = "https://updates.metaquotes.net/public/" + MT_VERSION + "/network";

    private static final byte[] STATIC_SIGN_KEY = {
            0x3D, 0x7B, 0x15, 0x16, (byte) 0xD6, (byte) 0xEA, (byte) 0xBB, 0x34,
            (byte) 0xD9, (byte) 0xD6, 0x63, (byte) 0xE3, 0x62, 0x3E, 0x1B, (byte) 0xD7,
            (byte) 0xFB, (byte) 0xDC, (byte) 0xAE, (byte) 0xF4, 0x57, 0x3B, (byte) 0xDF, 0x35,
            0x7F, (byte) 0xA8, (byte) 0xCF, 0x0B, (byte) 0xBE, (byte) 0xAD, (byte) 0x92, 0x7F
    };

    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    /**
     * Searches the MetaQuotes network for brokers matching the given company name.
     *
     * @param company the company/broker name to search for
     * @return parsed search result, or {@code null} if the broker was not found (HTTP 404)
     * @throws IOException          if the HTTP request fails
     * @throws InterruptedException if the request is interrupted
     */
    public static SearchResult search(String company) throws IOException, InterruptedException {
        StringBuilder sbPayload = new StringBuilder();
        sbPayload.append("company=").append(company).append("&code=").append(MT_VERSION);
        byte[] signatureBytes = computeSignature(sbPayload.toString().getBytes(StandardCharsets.US_ASCII));

        sbPayload.append("&signature=");
        for (byte b : signatureBytes) {
            sbPayload.append(String.format("%02x", b));
        }
        sbPayload.append("&ver=2");

        String requestBody = sbPayload.toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "en")
                .header("User-Agent", "MetaTrader 4 Terminal/4." + MT4Constants.CLIENT_BUILD + " (Windows NT 10.0.22621; x64)")
                .header("Cookie", generateCookies())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() == 404) {
            return null; // broker not found
        }
        if (response.statusCode() != 200) {
            throw new IOException("Bad response code: " + response.statusCode());
        }

        String jsonResponse;
        try (InputStream rawStream = response.body();
             InputStream decodedStream = handleDecompression(rawStream, response.headers().firstValue("Content-Encoding").orElse(""));
             Reader reader = new InputStreamReader(decodedStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            jsonResponse = sb.toString();
        }

        int jsonStartIndex = jsonResponse.indexOf("{");
        if (jsonStartIndex == -1) {
            throw new IOException("Invalid JSON response format");
        }
        String json = jsonResponse.substring(jsonStartIndex);

        try {
            return JsonReflectionParser.parse(json, SearchResult.class);
        } catch (Exception e) {
            throw new IOException("Error parsing JSON", e);
        }
    }

    private static InputStream handleDecompression(InputStream stream, String encoding) throws IOException {
        if ("gzip".equalsIgnoreCase(encoding)) {
            return new GZIPInputStream(stream);
        }
        return stream;
    }

    private static byte[] computeSignature(byte[] data) {
        byte[] step1 = MD5.computeMD5(data);
        byte[] buffer = new byte[48];
        System.arraycopy(step1, 0, buffer, 0, 16);
        System.arraycopy(STATIC_SIGN_KEY, 0, buffer, 16, 32);
        return MD5.computeMD5(buffer);
    }

    private static String generateCookies() {
        long uptimeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        long secondsSinceEpoch = Duration.between(Instant.EPOCH, Instant.now()).getSeconds();
        long timeVal = secondsSinceEpoch - (16436L * 24 * 3600);
        long softid = timeVal | ((uptimeMillis & 0x1FFFFFF) << 32) | 0x4200000000000000L;

        byte[] hardIdKey = createHardId();
        String commonKey = bytesToHex(hardIdKey).substring(0, 16);

        long age = secondsSinceEpoch - (24 * 3600);
        return "_fz_uniq=" + softid + ";uniq=" + softid + ";age=" + age + ";tid=" + commonKey + ";";
    }

    private static byte[] createHardId() {
        long seed = System.currentTimeMillis();
        byte[] data = new byte[256];
        for (int i = 0; i < 256; i++) {
            seed = seed * 214013 + 2531011;
            data[i] = (byte) ((seed >> 16) & 0xFF);
        }
        byte[] hardId = MD5.computeMD5(data);
        hardId[0] = 0;
        for (int i = 1; i < 16; i++) {
            hardId[0] += hardId[i];
        }
        return hardId;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // ======================== Result DTOs ========================

    public static class SearchResult {
        public BrokerResult[] result;
        @Override public String toString() { return "SearchResult{result=" + Arrays.toString(result) + '}'; }
    }

    public static class BrokerResult {
        public String company;
        public ServerResult[] results;
        @Override public String toString() { return "BrokerResult{company='" + company + "', results=" + Arrays.toString(results) + '}'; }
    }

    public static class ServerResult {
        public String name;
        public String logo_url;
        public String logo_hash;
        public String site;
        public String[] access;
        public int is_demo;
        @Override public String toString() { return "ServerResult{name='" + name + "', access=" + Arrays.toString(access) + ", is_demo=" + is_demo + '}'; }
    }
}
