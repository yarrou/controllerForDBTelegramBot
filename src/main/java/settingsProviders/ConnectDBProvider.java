package settingsProviders;

public class ConnectDBProvider {
    private final String urlDB = System.getenv("PG_CONN_STRING");
    private final String userDB = System.getenv("PG_USR");
    private final String passwordDB = System.getenv("PG_PSSWRD");
    private final String url = System.getenv("URL_TLGRM_BT");

    public String getUrlDB() {
        return urlDB;
    }

    public String getUserDB() {
        return userDB;
    }

    public String getPasswordDB() {
        return passwordDB;
    }

    public String getUrl() {
        return url;
    }
}
