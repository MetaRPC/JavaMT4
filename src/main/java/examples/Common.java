package examples;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Loads MT4 connection settings from {@code appsettings.json} (classpath or working dir). */
public final class Common {
    public static final class Config {
        public long user;
        public String password;
        public String serverName = "MetaQuotes-Demo";
        public String baseSymbol = "EURUSD";
        public String apiKey = "TRIAL";
        public String grpcServer; // null → mt4.mrpc.pro:443
    }

    public static Config load() throws Exception {
        InputStream is = Common.class.getClassLoader().getResourceAsStream("appsettings.json");
        if (is == null) is = new java.io.FileInputStream("appsettings.json");
        String json = new String(is.readAllBytes());
        is.close();

        Config c = new Config();
        String user = num(json, "user");
        if (user != null) c.user = Long.parseLong(user);
        c.password = str(json, "password");
        c.serverName = orDefault(str(json, "serverName"), c.serverName);
        c.baseSymbol = orDefault(str(json, "baseSymbol"), c.baseSymbol);
        c.apiKey = orDefault(str(json, "apiKey"), c.apiKey);
        c.grpcServer = str(json, "grpcServer");
        // Allow the API key to come from the environment too (keeps real keys out of the file).
        String env = System.getenv("MRPC_API_KEY");
        if (env != null && !env.isBlank()) c.apiKey = env.trim();
        return c;
    }

    private static String str(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]*)\"").matcher(json);
        return m.find() ? m.group(1) : null;
    }

    private static String num(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*([0-9]+)").matcher(json);
        return m.find() ? m.group(1) : null;
    }

    private static String orDefault(String v, String def) {
        return (v != null && !v.isBlank()) ? v : def;
    }

    private Common() {
    }
}
