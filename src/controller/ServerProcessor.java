package controller;

import model.entities.Collection;

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
                    case "getCollectionList" -> CollectionCotroler.getCollectionList(socket);
                    case "getCollectionInfoById" -> sayGoodbye(dataOutput, socket);
                    case "disconnect" -> running = false;
                }

            }while(running);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void sayHello(DataOutputStream dataOutputStream, Socket socket){
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Hola mundo");
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
