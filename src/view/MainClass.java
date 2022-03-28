package view;

import java.io.*;
import java.util.Properties;

public class MainClass {
    private final static String DATA_PATH ="./src/connection.properties";
    public static void main(String[] args) {
        int port;
        File file = new File(DATA_PATH);
        try{
            if(!file.exists()){
                try {
                    loadDefaultData(file);
                } catch (IOException e) {
                    System.out.println("Error creating connection data file");
                }

                port = 8080;

            }else{
                try{

                    port = Integer.parseInt(loadConnectionData(file));

                } catch (IOException e) {
                    System.out.println("Error loading connection data");
                    port = 8080;
                }
            }
        } catch (NumberFormatException e){
            System.out.println("Port introduced is not valid");
            return;
        }

        System.out.println("Server started at port " + port);

    }

    private static void loadDefaultData(File file) throws IOException {

        file.createNewFile();


        try(
                OutputStream outputStream = new FileOutputStream(file)
        ){
            Properties properties = new Properties();

            properties.setProperty("port", "8080");

            properties.store(outputStream, null);

        }
    }

    private static String loadConnectionData(File file) throws IOException {
        try(
                InputStream inputStream = new FileInputStream(file)
                ){
            Properties properties = new Properties();
            properties.load(inputStream);

            if(!properties.containsKey("port")){
                loadDefaultData(file);
                return "8080";
            }else{
                return properties.getProperty("port");
            }
        }
    }
}
