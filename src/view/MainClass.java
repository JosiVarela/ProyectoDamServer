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
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket clientSocket;

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
}
