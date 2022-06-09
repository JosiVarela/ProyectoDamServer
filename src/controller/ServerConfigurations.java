package controller;
import services.Resources;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServerConfigurations {
    private static final Map<String,String> CONFIG = new HashMap<>();
    public static void generateConfig() throws IOException {
        File file = new File(Resources.SETTINGS_FILE_URL);

        CONFIG.put("port", null);
        CONFIG.put("dbHost", null);
        CONFIG.put("dbPort", null);
        CONFIG.put("dbName", null);
        CONFIG.put("dbUser", null);
        CONFIG.put("dbPassword", null);

        if(!file.exists()){
            file.createNewFile();
            storeDefaultConfig();
        }else{
            loadConfig();
        }
    }

    private static void storeDefaultConfig() throws IOException {
        CONFIG.replace("port", "8080");
        CONFIG.replace("dbHost", "localhost");
        CONFIG.replace("dbPort", "3306");
        CONFIG.replace("dbName", "dbname");
        CONFIG.replace("dbUser", "dbUser");
        CONFIG.replace("dbPassword", "password");
        storeConfiguration();
    }

    private static void storeConfiguration() throws IOException {
        try (
                OutputStream outputStream = new FileOutputStream(Resources.SETTINGS_FILE_URL);
        ){
            Properties properties = new Properties();
            properties.putAll(CONFIG);
            properties.store(outputStream, null);
        }
    }

    private static void loadConfig() throws IOException {
        try(
                InputStream inputStream = new FileInputStream(Resources.SETTINGS_FILE_URL);
                ){
            Properties properties = new Properties();
            properties.load(inputStream);

            if(!properties.containsKey("port") || !properties.containsKey("dbHost") || !properties.containsKey("dbPort")
                    || !properties.containsKey("dbName") || !properties.containsKey("dbUser")
                    || !properties.containsKey("dbPassword")){
                storeDefaultConfig();
                return;
            }

            CONFIG.replace("port", properties.getProperty("port"));
            CONFIG.replace("dbHost", properties.getProperty("dbHost"));
            CONFIG.replace("dbPort", properties.getProperty("dbPort"));
            CONFIG.replace("dbName", properties.getProperty("dbName"));
            CONFIG.replace("dbUser", properties.getProperty("dbUser"));
            CONFIG.replace("dbPassword", properties.getProperty("dbPassword"));
        }
    }

    public static String getDbHost(){
        return CONFIG.get("dbHost");
    }

    public static String getDbPort(){
        return CONFIG.get("dbPort");
    }

    public static String getDbName(){
        return CONFIG.get("dbName");
    }

    public static String getDbUser(){
        return CONFIG.get("dbUser");
    }

    public static String getDbPassword(){
        return CONFIG.get("dbPassword");
    }

    public static int getPort() throws NumberFormatException{
        return Integer.parseInt(CONFIG.get("port"));
    }

    /*public static void storeTranslations(String language) throws IOException {
        CONFIG.replace("lang", language);
        storeConfiguration();
    }

    public static String getTranslations() {
        return CONFIG.get("lang");
    }

    public static void storeServerConfig(ServerConfig serverConfig) throws IOException {
        CONFIG.replace("server_ip", serverConfig.getIp());
        CONFIG.replace("server_port", String.valueOf(serverConfig.getPort()));
        storeConfiguration();
    }

    public static ServerConfig getServerConfig() {
        ServerConfig serverConfig;

        serverConfig = new ServerConfig(CONFIG.get("server_ip"), Integer.parseInt(CONFIG.get("server_port")));

        return serverConfig;
    }

    public static void switchLanguage(String lang){
        switch (lang){
            case "es" -> Locale.setDefault(new Locale("es", "ES"));
            case "gl" -> Locale.setDefault(new Locale("gl", "ES"));
        }
    }*/
}
