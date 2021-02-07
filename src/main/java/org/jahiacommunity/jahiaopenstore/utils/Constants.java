package org.jahiacommunity.jahiaopenstore.utils;

public class Constants {

    public static String AUTH_HEADER = "Bearer 79420e49ad4e44aab7d8b479b178cb9ccf84598a";
    public static String JAHIA_SERVER_URL = "http://localhost:8080";
    public static String API_URL = "https://api.github.com/graphql";
    public static String PROXY_SERVER_URL = JAHIA_SERVER_URL+"/cms/githubproxy/jahiaopenstore/";
    // public static String JAHIA_APP_STORE_SERVER_URL = JAHIA_SERVER_URL+"/en/sites/jahiaopenstore"; //unused = url to configure in Jahia AppStore Admin setup;
    // TODO: (next step) auto-configure this with a import.xml (to make it work on JahiaCommunity server/SDK ;)
}
