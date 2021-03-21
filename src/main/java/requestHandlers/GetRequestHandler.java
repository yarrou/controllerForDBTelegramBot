package requestHandlers;

import entity.Response;
import settingsProviders.ConnectDBProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GetRequestHandler {

    ConnectDBProvider connectDBProvider;

    public Response sendRequest(String cityName) throws IOException {
        String query = "?city=" + URLEncoder.encode(cityName, "UTF-8");
        final URL url = new URL(connectDBProvider.getUrl() + query);
        String messageResponse = null;
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        InputStream inputStream = null;
        if(connection.getResponseCode()==200){
            inputStream = connection.getInputStream();
        }else {
            inputStream = connection.getErrorStream();
        }
        try (final BufferedReader streamFromUrl = new BufferedReader(new InputStreamReader(inputStream))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = streamFromUrl.readLine()) != null) {
                content.append(inputLine);

                messageResponse = content.toString();}}

        return new Response(connection.getResponseCode(), messageResponse);
    }

    public GetRequestHandler(ConnectDBProvider connectDBProvider) {
        this.connectDBProvider = connectDBProvider;
    }
}
