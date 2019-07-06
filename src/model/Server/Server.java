package model.Server;

import model.Account;
import model.MyConstants;
import model.client.Client;
import presenter.LoginMenuProcess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Server {
    private ServerSocket serverSocket;
    private HashSet<String > authcodes = new HashSet<>();
    private HashMap<String, ClientManager> clients = new HashMap<>();
    private HashMap<String , Account> onlineAccounts = new HashMap<>();

    public static void main(String[] args) throws IOException {
        presenter.MainProcess.readFiles();
        Server server = new Server();
        server.serverSocket = new ServerSocket(MyConstants.SERVER_PORT);
        new ServerListener(server).start();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }



    public boolean isAccountAvailabale(String userName){
        for (Account account : Account.getAccounts()) {
            if(account.getUserName().equals(userName))
                return true;
        }
        return false;
    }

    //getters & setters

    public HashMap<String, ClientManager> getClients() {
        return clients;
    }

    public void setClients(HashMap<String, ClientManager> clients) {
        this.clients = clients;
    }

    public HashMap<String, Account> getOnlineAccounts() {
        return onlineAccounts;
    }

    public void setOnlineAccounts(HashMap<String, Account> onlineAccounts) {
        this.onlineAccounts = onlineAccounts;
    }

    public int getLoginErrorNumber(String userName, String password) {
        try {
            return   new LoginMenuProcess().login(userName,password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean isLoginValid(String userName, String password) {
        return getLoginErrorNumber(userName, password) == 0;
    }

    public String generateAuthCode(String userName, ClientManager clientManager) {
        Random r = new Random();
        String tempAuthCode = (r.nextInt()%10000) + "";
        while (authcodes.contains(tempAuthCode))
            tempAuthCode = (r.nextInt()%10000) + "";
        authcodes.add(tempAuthCode);
        clients.put(tempAuthCode,clientManager);
        onlineAccounts.put(tempAuthCode,Account.getAccountByUserName(userName));
        return tempAuthCode;
    }

    //getters & setters
}

class ServerListener extends Thread {
    private Server server;

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
                ClientManager clientManager = new ClientManager(server,addedClient);
                clientManager.start();
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


    public Lock getLock() {
        return lock;
    }
}

















