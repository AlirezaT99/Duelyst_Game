package model.Server;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class GameServer {
    private DatagramSocket serverSocket;
    private ArrayList<String> clients;

    {
        clients = new ArrayList<>();
    }

    public void main() throws IOException {
        GameServer server = new GameServer();
        server.serverSocket = new DatagramSocket();  //todo config must be set
        GameServerListener serverManager = new GameServerListener(server);
        serverManager.start();

    }


    //getters

    void addToSockets(String clientPort) {
        synchronized (clients) {
            clients.add(clientPort);
        }
    }

    DatagramSocket getServerSocket() {
        return serverSocket;
    }

    //getters

}

class GameServerListener extends Thread {
    GameServer server;
    final int unoMegabyte = 1024 * 1024 + 100;
    byte[] receive = new byte[unoMegabyte];

    GameServerListener(GameServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        ServerRequestHandler serverRequestHandler = new ServerRequestHandler();
        serverRequestHandler.start();
        while (true) {

            DatagramPacket packet = new DatagramPacket(receive, receive.length);
            try {
                server.getServerSocket().receive(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }
            receive = new byte[unoMegabyte];
        }
    }

}

class GameServerRequestHandler extends Thread {
    private ArrayList<DatagramPacket> datagramPackets;
    private final Lock lock = new Lock();

    {
        datagramPackets = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            if (datagramPackets.size() == 0) {
                try {
                    sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            } else
                handleRequest();
        }
    }

    private void handleRequest() {
        DatagramPacket requestPacket = datagramPackets.get(0);
        DatagramPacket answer = getAnswer(requestPacket); //todo
        synchronized (lock) {
            datagramPackets.remove(0);
        }
        addToAnswerQueue(answer); //todo
    }

    private void addToAnswerQueue(DatagramPacket answer) {
    }

    private DatagramPacket getAnswer(DatagramPacket requestPacket) {
        return new DatagramPacket(new byte[100],100);
    }


    void addToWaitingPacketsToBeHandled(DatagramPacket datagramPacket) {
        synchronized (lock) {
            datagramPackets.add(datagramPacket);
        }
    }

    public Lock getLock() {
        return lock;
    }
}


