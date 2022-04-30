package controller;

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
                    case "getCollectionList" -> CollectionCotroller.getCollectionList(socket);
                    case "getCollectionInfoById" -> CollectionCotroller.getCollectionInfoById(socket);
                    case  "getCollectionByName" -> CollectionCotroller.getCollectionByName(socket);
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
