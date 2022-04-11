package controller;

import model.CollectionManagemet;
import model.entities.Collection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class CollectionCotroler {
    public static void getCollectionList(Socket socket){
        DataOutputStream dataOutputStream;
        ObjectOutputStream objectOutputStream;


        List<Collection> collectionList = CollectionManagemet.getCollectionList();

        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF("OK");

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objectOutputStream.writeObject(collectionList);

            objectOutputStream.flush();

        } catch (IOException e) {
            System.out.println("Connection lost");
        }



    }
}
