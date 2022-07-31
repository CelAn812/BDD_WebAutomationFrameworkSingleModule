package configuration.utilities;

import java.util.Properties;

public class ReadSystemProperties {
    public static void getSystemInfo() {
        System.out.println(System.getProperty("os.name"));
    }

    //    String prodEnv = System.setProperty("prodEnv", "https://www.amazon.com");
//    String qaEnv = System.setProperty("qaEnv", "https://www.qa.amazon.com");
//    String devEnv = System.setProperty("devEnv", "https://www.dev.amazon.com");
    static Properties readProperties = ReadProperties.loadProperties("src/main/resources/config.properties");

    public static String getEnvUrl() {
        String url = null;
        if (envName.equalsIgnoreCase(("Dev"))) {
            url = readProperties.getProperty("DEV_URL");
        } else if (envName.equalsIgnoreCase(("Qa"))) {
            url = readProperties.getProperty("QA_URL");
        } else if (envName.equalsIgnoreCase(("Prod"))) {
            url = readProperties.getProperty("PROD_URL");
        }
        return url;
    }
    //Default env
    public static String envName = System.getProperty("env", "Prod");

//    public static synchronized void clearDataUsingEnvName() {
//    }
}
