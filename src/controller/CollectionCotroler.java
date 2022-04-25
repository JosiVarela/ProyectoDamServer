package controller;

import model.CollectionManagemet;
import model.entities.Collection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class CollectionCotroler {
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
            throw new RuntimeException(e);
        }
    }
}
