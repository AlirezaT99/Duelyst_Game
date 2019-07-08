package model.Server;

import com.google.gson.Gson;
import model.Account;
import model.Deck;
import model.MyConstants;
import presenter.LoginMenuProcess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Server {
    private static ServerSocket serverSocket;
    private static HashSet<String> authcodes = new HashSet<>();
    private static HashMap<String, ClientManager> clients = new HashMap<>();
    private static HashMap<String, Account> onlineAccounts = new HashMap<>();
    private static ArrayList<String> chatMessages = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        presenter.MainProcess.readFiles();
        Server server = new Server();
        serverSocket = new ServerSocket(MyConstants.SERVER_PORT);
        new ServerListener(server).start();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }


    public boolean isAccountAvailabale(String userName) {
        for (Account account : Account.getAccounts()) {
            if (account.getUserName().equals(userName))
                return true;
        }
        return false;
    }

    public boolean isAccountOnline(String userName) {
        for (String s : onlineAccounts.keySet()) {
            if (onlineAccounts.get(s).getUserName().equals(userName))
                return true;
        }
        return false;
    }

    public ArrayList<String> getAccountNames() {
        ArrayList<Account> accounts = sortedAccounts();

        ArrayList<String> sortedAccountsUsernameArrayList = new ArrayList<>();
        for (Account account : accounts) {
            sortedAccountsUsernameArrayList.add(account.getUserName());
        }
        return sortedAccountsUsernameArrayList;
    }

    public ArrayList<Boolean> getUsersOnlineStatus() {
        ArrayList<Account> accounts = sortedAccounts();
        ArrayList<Boolean> onlineStatus = new ArrayList<>();
        ArrayList<Account> onlineClients = new ArrayList<>();

        for (String s : onlineAccounts.keySet()) {
            onlineClients.add(onlineAccounts.get(s));
        }

        for (Account account : accounts) {
            if (isAccountOnline(account.getUserName()))
                onlineStatus.add(new Boolean(true));
            else
                onlineStatus.add(new Boolean(false));
        }
        return onlineStatus;
    }

    public ArrayList<Integer> getNumberOfWins() {
        ArrayList<Account> accounts = sortedAccounts();
        ArrayList<Integer> numberOfWins = new ArrayList<>();
        for (Account account : accounts) {
            numberOfWins.add(account.getNumberOfWins());
        }
        return numberOfWins;
    }

    private ArrayList<Account> sortedAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            LoginMenuProcess.readUsers();
            LoginMenuProcess.sortUsers();
            accounts = LoginMenuProcess.getUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }



    //getters & setters

    public HashMap<String, ClientManager> getClients() {
        return clients;
    }

    public void setClients(HashMap<String, ClientManager> clients) {
        this.clients = clients;
    }

    public static HashMap<String, Account> getOnlineAccounts() {
        return onlineAccounts;
    }

    public HashSet<String> getAuthcodes() {
        return authcodes;
    }

    public void setOnlineAccounts(HashMap<String, Account> onlineAccounts) {
        this.onlineAccounts = onlineAccounts;
    }

    public int getLoginErrorNumber(String userName, String password) {
        try {
            return new LoginMenuProcess().login(userName, password);
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
        String tempAuthCode = (r.nextInt() % 10000) + "";
        while (authcodes.contains(tempAuthCode))
            tempAuthCode = (r.nextInt() % 10000) + "";
        authcodes.add(tempAuthCode);
        clients.put(tempAuthCode, clientManager);
        onlineAccounts.put(tempAuthCode, Account.getAccountByUserName(userName));
        showOnlineClients();
        return tempAuthCode;
    }

    public void showOnlineClients() {
        System.out.println("Online clients : ");
        for (String s : onlineAccounts.keySet()) {
            System.out.println("\n" + onlineAccounts.get(s).getUserName() + "\n");
        }
    }

    public ArrayList<String> getChatMessages() {
        synchronized (chatMessages) {
            return chatMessages;
        }
    }

    public void addToChatMessages(String message, String authCode){
        synchronized (chatMessages) {
            String finalMessage = onlineAccounts.get(authCode).getUserName() + " : " + message;
            chatMessages.add(finalMessage);
            System.out.println(finalMessage);
            if (chatMessages.size() > 10)
                chatMessages.remove(0);
        }
    }

    public void setDecks(ArrayList<Deck> decks, String authCode){
        HashMap<String, Deck> deckHashMap = new HashMap<>();
        for (Deck deck : decks) {
            deckHashMap.put(deck.getName(),deck);
        }
        onlineAccounts.get(authCode).getCollection().setDecks(decks);
        onlineAccounts.get(authCode).getCollection().setDeckHashMap(deckHashMap);
    }

    public void setSelectedDeck(Deck deck, String authCode){
        onlineAccounts.get(authCode).getCollection().setSelectedDeck(deck);
    }

    public void saveAccount(String authCode){
        try {
            LoginMenuProcess.save(onlineAccounts.get(authCode));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                ClientManager clientManager = new ClientManager(server, addedClient);
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

















