package controller;

import model.NumberManagement;

import java.io.*;
import java.net.Socket;

public class ServerProcessor extends Thread{
    private Socket socket;

    public ServerProcessor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        boolean running = true;
        String option;
        DataInputStream dataInput;
        DataOutputStream dataOutput = null;
        ObjectInputStream objectInput;
        ObjectOutputStream objectOutput;


        try {
            do{
                dataInput = new DataInputStream(socket.getInputStream());

                option = dataInput.readUTF();

                switch (option){
                    //COLLECTION MANAGEMENT
                    case "getCollectionList" -> CollectionController.getCollectionList(socket);
                    case "getCollectionInfoById" -> CollectionController.getCollectionInfoById(socket);
                    case "getCollectionsByName" -> CollectionController.getCollectionsByName(socket);
                    case "existCollectionWithNameNotId" -> CollectionController.existsCollectionWithNameNotId(socket);
                    case "existCollectionWithSameName" -> CollectionController.existsCollectionWithSameName(socket);
                    case "existCollectionWithId" -> CollectionController.existsCollectionWithId(socket);
                    case "updateCollection" -> CollectionController.updateCollection(socket);
                    case "insertCollection" -> CollectionController.insertCollection(socket);
                    case "deleteCollection" -> CollectionController.deleteCollection(socket);
                    case "getCollectionName" -> CollectionController.getCollectionName(socket);
                    //COMIC NUBERS MANAGEMENT
                    case "existsComicNumber" -> NumberController.existsNumber(socket);
                    case "getComicNumber" -> NumberController.getComicNumber(socket);
                    case "insertComicNumber" -> NumberController.insertComicNumber(socket);
                    //SERVER MANAGEMENT
                    case "ping" -> ping(socket);
                    case "disconnect" -> running = false;
                }

            }while(running);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void ping(Socket socket){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("pong");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sayGoodbye(DataOutputStream dataOutputStream, Socket socket){
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Adios mundo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
