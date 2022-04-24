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
            System.out.println("Connection lost");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
