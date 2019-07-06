package model.Server;

import io.joshworks.restclient.http.HttpResponse;
import io.joshworks.restclient.http.Unirest;
import model.client.Client;
import model.Message.Message;
import model.MyConstants;
import model.Reader;
import model.Writer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Server {
    private ServerSocket serverSocket;
    private HashMap<String, Socket> clients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.serverSocket = new ServerSocket(MyConstants.SERVER_PORT);
        new ServerListener(server).start();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void addToClients(String authCode, Socket clientSocket) {
        clients.put(authCode, clientSocket);
    }

    public void removeFromCleints(String authCode) {
        clients.remove(authCode);
    }
}

class ServerListener extends Thread {
    Server server;

    ServerListener(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
//        ServerRequestHandler serverRequestHandler = new ServerRequestHandler();
//        serverRequestHandler.start();
        while (true) {
            try {
                Socket addedClient = server.getServerSocket().accept();
                ClientManager clientManager = new ClientManager(addedClient);
                clientManager.run();
                System.out.println("client " + addedClient.getPort() + " added");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ServerRequestHandler extends Thread {
    private final Lock lock = new Lock();

    @Override
    public void run() {
        while (true) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleRequest() {
        synchronized (lock) {
        }
    }

    private void addToAnswerQueue(DatagramPacket answer) {
    }

    private DatagramPacket getAnswer(DatagramPacket requestPacket) {
        return new DatagramPacket(new byte[100], 100);
    }


    void addToWaitingPacketsToBeHandled(DatagramPacket datagramPacket) {
        synchronized (lock) {
        }
    }

    public Lock getLock() {
        return lock;
    }
}

class ClientManager {

    ClientManager(Socket socketOnServerSide) {
        this.socketOnServerSide = socketOnServerSide;
    }

    private static HashMap<String, ClientManager> clientManagers = new HashMap<>();

    private Socket socketOnServerSide;
    private Writer clientWriter;
    //setters
    private void setClientWriter(Writer writer) {
        clientWriter = writer;
        writer.start();
    }

    //command manager
    private boolean isACommand(String message) {
        return message.charAt(0) == '~';
    }

//    //command manager

    void run() {
        try {
            setClientWriter(new model.Writer(socketOnServerSide.getOutputStream()));
            Reader reader = new model.Reader(socketOnServerSide.getInputStream());
            reader.addListener(message -> {

                //todo : idk how it works here, sepehr should help
            });
            reader.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}















