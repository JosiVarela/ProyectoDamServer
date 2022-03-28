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
        DataInputStream dataInput = null;
        DataOutputStream dataOutput = null;
        ObjectInputStream objectInput = null;
        ObjectOutputStream objectOutput = null;


        try {
            do{
                dataInput = new DataInputStream(socket.getInputStream());

                option = dataInput.readUTF();

                switch (option){
                    case "hello" -> System.out.println("Hola mundo");
                    case "disconnect" -> running = false;
                }

            }while(running);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
