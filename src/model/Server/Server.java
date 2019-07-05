package model.Server;

import io.joshworks.restclient.http.HttpResponse;
import io.joshworks.restclient.http.Unirest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    private ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.serverSocket = new ServerSocket();


        final String baseAddress = "http://127.0.0.1:8080/";
        final String path1 = "get_all_keys";
        final String path = "init_DB";
        HttpResponse<String> response;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("name", "ali");
//        parameters.put("key", "key for adding to a DB or deleting from it");
//        parameters.put("value", "your content to save");
        try {
            response = Unirest.post(baseAddress + path).fields(parameters).asString();
            System.out.println(response.getStatus());
        } catch (Exception e) {
            e.printStackTrace(); //do something
        }


    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}

class ServerListener extends Thread {
    Server server;

    ServerListener(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        ServerRequestHandler serverRequestHandler = new ServerRequestHandler();
        serverRequestHandler.start();
        while (true) {

            try {
                Socket addedClient = server.getServerSocket().accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

class ServerRequestHandler extends Thread {
    private final Lock lock = new Lock();

    {
    }

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














