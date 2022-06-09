package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;
    private static String host;
    private static String port;
    private static String dbName;
    private static String dbUser;
    private static String dbPassword;

    public static void readConnectionData(){
        //TODO Read properties file for connection config

        host="192.168.56.100";
        port="3306";
        dbName="library";
        dbUser="interfaces";
        dbPassword="abc123.";
    }

    public static void connect() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String urlCon="jdbc:mysql://"+host+":"+port+"/"+dbName;
            connection = DriverManager.getConnection(urlCon, dbUser, dbPassword);
            connection.setAutoCommit(false);
        }catch (ClassNotFoundException ex){
            System.out.println("MYSQL DRIVER NOT FOUND");
        }

    }

    public static Connection getConnection(){
        return connection;
    }
}
