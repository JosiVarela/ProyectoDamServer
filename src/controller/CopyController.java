package controller;

import model.NumberCopiesManagement;
import model.entities.ComicCopy;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class CopyController {
    public static void insertCopy(Socket socket){
        DataOutputStream dataOutputStream;
        ObjectInputStream objectInputStream;
        ComicCopy comicCopy;

        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            comicCopy = (ComicCopy) objectInputStream.readObject();

            DBConnection.connect();
            NumberCopiesManagement.insertCopy(DBConnection.getConnection(), comicCopy);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void existsCopy(Socket socket){
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        int copyId;
        boolean existsCopy;

        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            copyId = dataInputStream.readInt();

            DBConnection.connect();
            existsCopy = NumberCopiesManagement.existsCopy(DBConnection.getConnection(), copyId);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeBoolean(existsCopy);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
            }
            try {
                DBConnection.getConnection().close();
            } catch (SQLException ex) {
            }
        }
    }

    public static void updateCopy(Socket socket){
        DataOutputStream dataOutputStream;
        ObjectInputStream objectInputStream;
        ComicCopy comicCopy;

        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            comicCopy = (ComicCopy) objectInputStream.readObject();

            DBConnection.connect();
            NumberCopiesManagement.updateCopy(DBConnection.getConnection(), comicCopy);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public static void deleteCopy(Socket socket){
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        int copyId;

        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            copyId = dataInputStream.readInt();

            DBConnection.connect();
            NumberCopiesManagement.deleteCopy(DBConnection.getConnection(), copyId);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public static void getComicCopy(Socket socket){
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;
        DataInputStream dataInputStream;
        int copyId;
        ComicCopy comicCopy;

        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            copyId = dataInputStream.readInt();

            DBConnection.connect();
            comicCopy = NumberCopiesManagement.getComicCopy(DBConnection.getConnection(), copyId);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(comicCopy);
            objectOutputStream.flush();

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
