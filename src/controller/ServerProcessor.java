package controller;

import model.NumberManagement;

import java.io.*;
import java.net.Socket;

public class ServerProcessor extends Thread{
    private final Socket socket;

    public ServerProcessor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        boolean running = true;
        String option;
        DataInputStream dataInput;

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
                    //COMIC NUMBERS MANAGEMENT
                    case "existsComicNumber" -> NumberController.existsNumber(socket);
                    case "getComicNumber" -> NumberController.getComicNumber(socket);
                    case "insertComicNumber" -> NumberController.insertComicNumber(socket);
                    case "updateComicNumber" -> NumberController.updateComicNumber(socket);
                    case "deleteComicNumber" -> NumberController.deleteComicNumber(socket);
                    case "getComicNumbers" -> NumberController.getComicNumbers(socket);
                    case "getComicNumberList" -> NumberController.getNumberList(socket);
                    case "getNumbersByName" -> NumberController.getNumbersByName(socket);
                    case "getNumbersByColName" -> NumberController.getNumbersByColName(socket);
                    // COMIC COPIES MANAGEMENT
                    case "insertCopy" -> CopyController.insertCopy(socket);
                    case "updateCopy" -> CopyController.updateCopy(socket);
                    case "deleteCopy" -> CopyController.deleteCopy(socket);
                    case "existsCopy" -> CopyController.existsCopy(socket);
                    case "getComicCopy" -> CopyController.getComicCopy(socket);
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
}
