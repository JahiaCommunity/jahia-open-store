package org.jahiacommunity.jahiaopenstore.utils;

public class Constants {

    public static String TOKEN_PART1 = "03cdf898245b32";
    public static String TOKEN_PART2 = "e5f9ac62c84c85";
    public static String TOKEN_PART3 = "703d88554949";
    public static String AUTH_HEADER = "Bearer "+TOKEN_PART1+TOKEN_PART2+TOKEN_PART3;
    public static String JAHIA_SERVER_URL = "http://localhost:8080";
    public static String API_URL = "https://api.github.com/graphql";
    public static String GITHUB_REPO_URL = "https://github.com/JahiaCommunity/";
    public static String PROXY_SERVER_URL = JAHIA_SERVER_URL+"/cms/githubproxy/jahiaopenstore/";
    public static String PROXY_SERVER_DOC_URL = JAHIA_SERVER_URL+"/cms/githubproxydoc/jahiaopenstore/";
    // public static String JAHIA_APP_STORE_SERVER_URL = JAHIA_SERVER_URL+"/en/sites/jahiaopenstore"; //unused = url to configure in Jahia AppStore Admin setup;
    // TODO: (next step) auto-configure this with a import.xml (to make it work on JahiaCommunity server/SDK ;)
}
