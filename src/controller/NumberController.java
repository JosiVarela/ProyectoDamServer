package controller;

import model.CollectionManagement;
import model.NumberManagement;
import model.entities.ComicNumber;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class NumberController {
    public static void existsNumber(Socket socket){
        String isbn;
        boolean existsNumb;
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;

        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            isbn = dataInputStream.readUTF();

            DBConnection.connect();
            existsNumb = NumberManagement.existsNumber(DBConnection.getConnection(), isbn);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeBoolean(existsNumb);

        } catch (IOException e) {
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

    public static void getComicNumber(Socket socket){
        String isbn;
        ComicNumber comicNumber;
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;

        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            isbn = dataInputStream.readUTF();

            try{
                DBConnection.connect();
                comicNumber = NumberManagement.getComicNumber(DBConnection.getConnection(), isbn);
            } catch (SQLException e) {
                try {
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF("SQLE Error");
                } catch (IOException ex) {
                }
                return;
            } catch (IOException e){
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("IOE");
                return;
            }

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(comicNumber);
            objectOutputStream.flush();


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBConnection.getConnection().close();
            } catch (SQLException e) {
            }
        }
    }

    public static void insertComicNumber(Socket socket){
        ObjectInputStream objectInputStream;
        DataOutputStream dataOutputStream;
        ComicNumber comicNumber;
        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            comicNumber = (ComicNumber) objectInputStream.readObject();

            DBConnection.connect();
            NumberManagement.insertComicNumber(DBConnection.getConnection(), comicNumber);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

        } catch (IOException e) {
            try {
                DBConnection.getConnection().rollback();
            } catch (SQLException ex) {
            }
        } catch (ClassNotFoundException e) {
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
}
