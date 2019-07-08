package model.Server;

import com.google.gson.Gson;
import model.*;
import model.Message.AddCardCommand.AddCardCommand;
import model.Message.BattleCommand.BattleRequest;
import model.Message.Message;
import model.Message.ScoreBoardCommand.ScoreBoardCommand;
import model.Message.ShopCommand.Trade.TradeRequest;
import model.Message.ShopCommand.UpdateShop.UpdateCards;
import model.client.Client;
import presenter.LoginMenuProcess;
import sun.java2d.pipe.AATextRenderer;

import java.io.File;
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
    private static ServerSocket serverSocket;
    private static HashSet<String> authcodes = new HashSet<>();
    private static HashMap<String, ClientManager> clients = new HashMap<>();
    private static HashMap<String, Account> onlineAccounts = new HashMap<>();
    private static ArrayList<String> chatMessages = new ArrayList<>();
    private static ArrayList<UpdateCards> shopUpdates = new ArrayList<>();
    private static ArrayList<Account> battleRequestPending = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        presenter.MainProcess.readFiles();
//        Shop.resetAllNumbers();
        Server server = new Server();
        server.saveCards();
            serverSocket = new ServerSocket(MyConstants.SERVER_PORT);
        new ServerListener(server).start();
    }

    static int getIndexOfUpdate(UpdateCards updateCards1) {
        if (shopUpdates.contains(updateCards1))
            return shopUpdates.indexOf(updateCards1);
        return shopUpdates.size() - 1;
    }

    static void addUpdateOfShop(TradeRequest tradeRequest) {
        synchronized (shopUpdates) {
            String name = tradeRequest.getObjectName();
            int cost = Shop.findCost(name);
            int[] powers = Shop.findPowers(name);
            int counter = Shop.findCount(name);
            if (tradeRequest.isBuy())
                shopUpdates.add(new UpdateCards(cost, powers, name, "", counter - 1, false, Shop.getType(name)));
            else
                shopUpdates.add(new UpdateCards(cost, powers, name, "", counter + 1, false, Shop.getType(name)));
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }


    public boolean isAccountAvailable(String userName) {
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

    static ArrayList<UpdateCards> getLatestUpdates(int lastIndexOfUpdates) {
        ArrayList<UpdateCards> updateCards = new ArrayList<>();
        synchronized (shopUpdates) {
            if (shopUpdates.size() > lastIndexOfUpdates + 1)
                updateCards = new ArrayList<>(shopUpdates.subList(lastIndexOfUpdates + 1, shopUpdates.size()));
        }
        return updateCards;
    }


    //getters & setters

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

    public void addToChatMessages(String message, String authCode) {
        synchronized (chatMessages) {
            String finalMessage = onlineAccounts.get(authCode).getUserName() + " : " + message;
            chatMessages.add(finalMessage);
            System.out.println(finalMessage);
            if (chatMessages.size() > 10)
                chatMessages.remove(0);
        }
    }

    public void setDecks(ArrayList<Deck> decks, String authCode) {
        HashMap<String, Deck> deckHashMap = new HashMap<>();
        for (Deck deck : decks) {
            deckHashMap.put(deck.getName(), deck);
        }
        onlineAccounts.get(authCode).getCollection().setDecks(decks);
        onlineAccounts.get(authCode).getCollection().setDeckHashMap(deckHashMap);
    }

    public void setSelectedDeck(Deck deck, String authCode) {
        onlineAccounts.get(authCode).getCollection().setSelectedDeck(deck);
    }

    public void saveAccount(String authCode) {
        try {
            System.out.println("fucking saving for : " + onlineAccounts.get(authCode).getUserName());
            LoginMenuProcess.save(onlineAccounts.get(authCode));
        } catch (IOException e) {
            e.printStackTrace(); //
        }
    }

    public static void saveCards() {
        try {
            for (Hero shopHero : Shop.getShopHeroes()) {
                Main.addCardToFiles(shopHero);
            }
            for (Spell shopSpell : Shop.getShopSpells()) {
                Main.addCardToFiles(shopSpell);
            }
            for (Minion shopMinion : Shop.getShopMinions()) {
                Main.addCardToFiles(shopMinion);
            }
        } catch (IOException e) {

        }
    }

    public static void addSpell(AddCardCommand addCardCommand) {
        String[] impact1 = addCardCommand.getImpact1().split("[ ]");
        String[] impact2 = addCardCommand.getImpact2().split("[ ]");

        Impact primaryImpact = new Impact(impact1[0], impact1[1], impact1[2], impact1[3]);
        Impact secondaryImpact = new Impact(impact2[0], impact2[1], impact2[2], impact2[3]);
        Spell spell = new Spell(primaryImpact, secondaryImpact);
        spell.setName(addCardCommand.getName());
        spell.setCost(addCardCommand.getCost());
        spell.isCostume(true);
        Shop.getShopSpells().add(spell);
        Spell.addToSpells(spell);
        try {
            Main.addCardToFiles(spell);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addHero(AddCardCommand addCardCommand) {

        Spell spell = Spell.getSpellByName(addCardCommand.getHeroSpecialPower());
        Hero hero = new Hero(addCardCommand.getName(), addCardCommand.getHealth(), addCardCommand.getDamage()
                , spell, addCardCommand.getCoolDown());
        hero.isCostume(true);

        switch (addCardCommand.getAttackType()) {
            case "Melee":
                hero.setMelee(true);
                break;
            case "Ranged":
                hero.setRanged(true);
                break;
            case "Hybrid":
                hero.setHybrid(true);
                break;
        }
        hero.setCost(addCardCommand.getCost());
        hero.setMaxAttackRange(addCardCommand.getRange());
        Shop.getShopHeroes().add(hero);
        Hero.addToHeroes(hero);
        try {
            Main.addCardToFiles(hero);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String setMatch(String authCode, int mode, int numberOfFlags){
        Match match = new Match(false, mode,numberOfFlags);
        match.setup(onlineAccounts.get(authCode),battleRequestPending.get(0),numberOfFlags);
        String matchString = new Gson().toJson(match,Match.class);
        BattleRequest battleRequest = new BattleRequest("",matchString);
        clients.get(authCode).sendToClient(battleRequest);
        getClientManager(battleRequestPending.get(0)).sendToClient(battleRequest);
        return matchString;
    }

    public ClientManager getClientManager(Account account){
        for (String s : clients.keySet()) {
            if(onlineAccounts.get(s).getUserName().equals(account.getUserName()))
                return clients.get(s);
        }
        return null;
    }

    public static ArrayList<Account> getBattleRequestPending() {
        return battleRequestPending;
    }

    public static void setBattleRequestPending(ArrayList<Account> battleRequestPending) {
        Server.battleRequestPending = battleRequestPending;
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

















