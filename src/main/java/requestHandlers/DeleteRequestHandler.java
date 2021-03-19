package requestHandlers;

import entity.Response;
import settingsProviders.ConnectDBProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class DeleteRequestHandler {
    ConnectDBProvider connectDBProvider;

    public Response sendRequest(String cityName) throws IOException {
        String query = "?city=" + URLEncoder.encode(cityName, "UTF-8");
        final URL url = new URL(connectDBProvider.getUrl() + query);
        String messageResponse = null;
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Content-Type", "application/json");
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

    public DeleteRequestHandler(ConnectDBProvider connectDBProvider) {
        this.connectDBProvider = connectDBProvider;
    }
}
