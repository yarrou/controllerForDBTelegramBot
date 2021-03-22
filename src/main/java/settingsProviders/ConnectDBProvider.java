package settingsProviders;

public class ConnectDBProvider {

    private static String url = System.getenv("URL_TLGRM_BT");

    public String getUrl() {
        return url;
    }
}
