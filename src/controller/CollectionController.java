package controller;

import model.CollectionManagement;
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
            List<Collection> collectionList = CollectionManagement.getCollectionList(DBConnection.getConnection());
            DBConnection.getConnection().close();

            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objectOutputStream.writeObject(collectionList);

            objectOutputStream.flush();

        } catch (IOException e) {
        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
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

            collection = CollectionManagement.getCollectionInfoById(DBConnection.getConnection(), id);
            collection.setComicQuantity(CollectionManagement.getCollectionNumbersQuantity(DBConnection.getConnection(),
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
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
            }
        } catch (IOException e) {
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

            collectionList = CollectionManagement.getCollectionListByName(DBConnection.getConnection(), colName);

            DBConnection.getConnection().close();

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(collectionList);
            objectOutputStream.flush();

        } catch (IOException e) {
        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
            }
        }
    }

    public static void existsCollectionWithNameNotId(Socket socket){
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
            result = CollectionManagement.existsCollectionWithName(DBConnection.getConnection(), id, name);
            DBConnection.getConnection().close();

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");


            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeBoolean(result);

        } catch (IOException e) {
        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");

            } catch (IOException ex) {
            }
        }
    }

    public static void existsCollectionWithSameName(Socket socket){
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        String colName;
        boolean result;
        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            colName = dataInputStream.readUTF();

            DBConnection.connect();
            result = CollectionManagement.existsCollectionWithName(DBConnection.getConnection(), colName);
            DBConnection.getConnection().close();

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeBoolean(result);

        } catch (IOException e) {
        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
            }
        }
    }

    public static void existsCollectionWithId(Socket socket){
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        int colId;
        boolean result;

        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            colId = dataInputStream.readInt();

            DBConnection.connect();
            result = CollectionManagement.existsCollectionWithId(DBConnection.getConnection(), colId);
            DBConnection.getConnection().close();

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeBoolean(result);

        } catch (IOException e) {
            throw new RuntimeException(e);
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
            CollectionManagement.updateCollection(DBConnection.getConnection(), col);

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

    public static void insertCollection(Socket socket){
        DataOutputStream dataOutputStream;
        ObjectInputStream objectInputStream;
        Collection collection;

        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            collection = (Collection) objectInputStream.readObject();

            DBConnection.connect();
            CollectionManagement.insertCollection(DBConnection.getConnection(), collection);

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

    public static void deleteCollection(Socket socket){
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        int colId;
        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            colId = dataInputStream.readInt();

            DBConnection.connect();
            CollectionManagement.deleteCollection(DBConnection.getConnection(), colId);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");


        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                if (errorCode == 1451) {
                    dataOutputStream.writeUTF("SQLE Foreing");
                } else {
                    dataOutputStream.writeUTF("SQLE Error");
                }
            }catch (IOException ex){
            }
            try {
                DBConnection.getConnection().rollback();
            } catch (SQLException ex) {
            }
        } catch (IOException e) {
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

    public static void getCollectionName(Socket socket){
        int colId;
        String colName;
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;

        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            colId = dataInputStream.readInt();

            DBConnection.connect();
            colName = CollectionManagement.getCollectionName(DBConnection.getConnection(), colId);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(colName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
            }
        }finally {
            try {
                DBConnection.getConnection().close();
            } catch (SQLException e) {
            }
        }
    }
}
