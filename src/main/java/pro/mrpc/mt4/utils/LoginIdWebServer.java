package pro.mrpc.mt4.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class LoginIdWebServer {
    private static class DecoderTask implements Runnable {
        final String url;
        final byte[] data;
        long id;
        IOException exception;

        DecoderTask(String url, byte[] data) {
            this.url = url;
            this.data = data;
        }

        @Override
        public void run() {
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "FIREFOX");
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(Base64.getEncoder().encodeToString(data));
                wr.flush();
                wr.close();
                con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                if (response.toString().equals("OK"))
                    return;
                if (!Character.isDigit(response.charAt(0)))
                    throw new IOException("LoginIdWebServer: " + response);
                id = new BigInteger(response.toString(), 10).longValue();
            } catch (IOException e) {
                exception = new IOException("LoginIdWebServer: " + e.getMessage());
            }
        }
    }

    public long decode(String url, String guid, byte[] data, int timeout) throws IOException {
        DecoderTask task = new DecoderTask(url + "?guid=" + guid, data);
        Thread th = new Thread(task);
        th.start();
        try {
            th.join(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (th.isAlive())
            throw new RuntimeException("No reply from login id web server in " + timeout + "ms");
        if (task.exception != null)
            throw task.exception;
        return task.id;
    }
}
