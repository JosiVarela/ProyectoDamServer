package view;

import controller.DBConnection;
import controller.ServerConfigurations;
import controller.ServerProcessor;
import services.Resources;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Properties;

public class MainClass {
    private final static String DATA_PATH ="./src/connection.properties";
    public static void main(String[] args) {
        int port;
        ServerSocket serverSocket;
        Socket clientSocket;

        /*File file = new File(DATA_PATH);
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

                    DBConnection.readConnectionData();
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

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Can't start server on port " + port);
            return;
        }

        System.out.println("Server started at port " + port);*/

        try {
            ServerConfigurations.generateConfig();

            DBConnection.readConnectionData();

            DBConnection.connect();

            serverSocket = new ServerSocket(ServerConfigurations.getPort());
        } catch (NumberFormatException e) {
            System.out.println("Port introduced is not valid");
            return;
        } catch (SocketException e) {
            System.out.println("Introduced port is already in use");
            return;
        } catch (IOException e) {
            System.out.println("Error generating configurations. Contact with an Admin");
            return;
        } catch (SQLException e) {
            System.out.println("Error connecting to the database. Try it later or check the configuration.");
            return;
        }finally {
            if(DBConnection.getConnection() != null){
                try {
                    DBConnection.getConnection().close();
                } catch (SQLException e) {
                }
            }
        }

        System.out.println("Connected at port " + ServerConfigurations.getPort());

        while(true){
            try {
                clientSocket = serverSocket.accept();

                new ServerProcessor(clientSocket).start();

            } catch (IOException e) {
            }

        }

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
