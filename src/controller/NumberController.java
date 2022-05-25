package controller;

import model.CollectionManagement;
import model.NumberCopiesManagement;
import model.NumberManagement;
import model.entities.Collection;
import model.entities.ComicNumber;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                comicNumber.setComicCopyList(NumberCopiesManagement.getComicCopiesList(DBConnection.getConnection(),
                        comicNumber.getIsbn()));
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

    public static void getComicNumbers(Socket socket){
        List<ComicNumber> comicNumbers;
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;

        try{
            DBConnection.connect();
            comicNumbers = NumberManagement.getComicNumbers(DBConnection.getConnection());

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(comicNumbers);
            objectOutputStream.flush();

        } catch (SQLException e) {
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("SQLE Error");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public static void updateComicNumber(Socket socket){
        ObjectInputStream objectInputStream;
        DataOutputStream dataOutputStream;
        ComicNumber comicNumber;
        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            comicNumber = (ComicNumber) objectInputStream.readObject();

            DBConnection.connect();
            NumberManagement.updateComicNumber(DBConnection.getConnection(), comicNumber);

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

    public static void deleteComicNumber(Socket socket){
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        String isbn;
        try{
            dataInputStream = new DataInputStream(socket.getInputStream());
            isbn = dataInputStream.readUTF();

            DBConnection.connect();
            NumberManagement.deleteComicNumber(DBConnection.getConnection(), isbn);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

        } catch (IOException e) {
            try {
                DBConnection.getConnection().rollback();
            } catch (SQLException ex) {
            }
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
        }finally {
            try {
                DBConnection.getConnection().commit();
                DBConnection.getConnection().close();
            } catch (SQLException e) {
            }
        }
    }

    public static void getNumberList(Socket socket){

        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;
        List<ComicNumber> numberList;

        try{

            DBConnection.connect();
            numberList = NumberManagement.getNumberList(DBConnection.getConnection());

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(numberList);
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
        }finally {
            try{
                DBConnection.getConnection().close();
            } catch (SQLException e) {
            }

        }
    }

    public static void getNumbersByName(Socket socket){
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;
        DataInputStream dataInputStream;
        List<ComicNumber> numberList;
        String name;

        try{

            dataInputStream = new DataInputStream(socket.getInputStream());
            name = dataInputStream.readUTF();

            DBConnection.connect();
            numberList = NumberManagement.getNumbersByName(DBConnection.getConnection(), name);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(numberList);
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
        }finally {
            try{
                DBConnection.getConnection().close();
            } catch (SQLException e) {
            }

        }
    }

    public static void getNumbersByColName(Socket socket){
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;
        DataInputStream dataInputStream;
        List<ComicNumber> numberList;
        String name;

        try{

            dataInputStream = new DataInputStream(socket.getInputStream());
            name = dataInputStream.readUTF();

            DBConnection.connect();
            numberList = NumberManagement.getNumbersByColName(DBConnection.getConnection(), name);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(numberList);
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
        }finally {
            try{
                DBConnection.getConnection().close();
            } catch (SQLException e) {
            }

        }
    }
}
