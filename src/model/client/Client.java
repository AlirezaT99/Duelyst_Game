package model.client;

import model.Message.Message;
import model.MyConstants;
import view.Main;

import java.io.*;
import java.net.Socket;

public class Client implements runnables.MessageListener {
    private String authCode;
    private static Client instance = new Client();
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public void start(){
        try {
            connect2Server();
            initIOStreams();
            startThreads();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect2Server() throws IOException {
        System.out.println("connecting to server...");
        clientSocket = new Socket(MyConstants.SERVER_ADDRESS, MyConstants.SERVER_PORT);
        System.out.println("client connected ...");
    }

    private void initIOStreams() throws IOException {
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    private void startThreads() {
        new Thread(new runnables.GetDataRunnable(inputStream, this)).start();
    }

    public void sendData(String text) {
        Message message = new Message(text);
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void receive(Message message) {
       // Main.getData(message);
    }

    public static Client getInstance() {
        return instance;
    }

}
