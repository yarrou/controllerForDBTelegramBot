package requestHandlers;

import entity.City;
import entity.Response;
import settingsProviders.ConnectDBProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PostRequestHandler {
    ConnectDBProvider connectDBProvider;

    public Response sendRequest(City city) throws IOException {
        final URL url = new URL(connectDBProvider.getUrl());
        String messageResponse = null;
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = city.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (final BufferedReader streamFromUrl = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = streamFromUrl.readLine()) != null) {
                content.append(inputLine);
            }
            messageResponse = content.toString();
        } catch (final Exception ex) {
            messageResponse = ex.getClass().getName();
            ex.printStackTrace();
        }
        return new Response(connection.getResponseCode(), messageResponse);
    }

    public PostRequestHandler(ConnectDBProvider connectDBProvider) {
        this.connectDBProvider = connectDBProvider;
    }
}
