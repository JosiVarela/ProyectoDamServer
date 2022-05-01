package controller;

import model.CollectionManagemet;
import model.NumberManagement;
import model.entities.Collection;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class CollectionController {
    public static void getCollectionList(Socket socket){
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;


        try {
            DBConnection.connect();
            List<Collection> collectionList = CollectionManagemet.getCollectionList(DBConnection.getConnection());
            DBConnection.getConnection().close();

            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objectOutputStream.writeObject(collectionList);

            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
                e.printStackTrace();
            }
        }
    }

    public static void getCollectionInfoById(Socket socket){
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;
        Collection collection;
        int id;

        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            id = dataInputStream.readInt();


            DBConnection.connect();

            collection = CollectionManagemet.getCollectionInfoById(DBConnection.getConnection(), id);
            collection.setComicQuantity(CollectionManagemet.getCollectionNumbersQuantity(DBConnection.getConnection(),
                    id));

            collection.setNumberList(NumberManagement.getNumberListByColId(DBConnection.getConnection(),
                    collection.getId()));

            DBConnection.getConnection().close();

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(collection);
            objectOutputStream.flush();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getCollectionsByName(Socket socket){
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;
        String colName;
        List<Collection> collectionList;

        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            colName = dataInputStream.readUTF();

            DBConnection.connect();

            collectionList = CollectionManagemet.getCollectionListByName(DBConnection.getConnection(), colName);

            DBConnection.getConnection().close();

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(collectionList);
            objectOutputStream.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
            }
        }
    }

    public static void existsCollectionWithName(Socket socket){
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        String name;
        int id;
        boolean result;
        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            name = dataInputStream.readUTF();


            dataInputStream = new DataInputStream(socket.getInputStream());
            id = dataInputStream.readInt();


            DBConnection.connect();
            result = CollectionManagemet.existsCollectionWithName(DBConnection.getConnection(), id, name);
            DBConnection.getConnection().close();

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");


            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeBoolean(result);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");

            } catch (IOException ex) {
            }
        }
    }

    public static void updateCollection(Socket socket){
        ObjectInputStream objectInputStream;
        DataOutputStream dataOutputStream;
        Collection col;

        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            col = (Collection) objectInputStream.readObject();

            DBConnection.connect();
            CollectionManagemet.updateCollection(DBConnection.getConnection(), col);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

        } catch (IOException e) {
            try {
                DBConnection.getConnection().rollback();
            } catch (SQLException ex) {
            }
        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
            }
            try {
                DBConnection.getConnection().rollback();
            } catch (SQLException ex) {
            }
        }finally {
            try {
                DBConnection.getConnection().commit();
                DBConnection.getConnection().close();
            } catch (SQLException e) {
            }
        }
    }
}
