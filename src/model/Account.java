package model;

import java.util.ArrayList;

public class Account {
    private static ArrayList<Account> accounts;
    private String userName;
    private String password;
    private long money;
    private Collection collection;
    private ArrayList<model.Account> friends;
    private int numberOfWins;

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.money = 15000;
        this.friends = new ArrayList<>();
        this.numberOfWins = 0;
    }

    static {
        accounts = new ArrayList<>();
    }


    public void buy(int cost, UsableItem item, Card card) {
        money -= cost;
        if (item != null)
            collection.getItems().add(item);
        if (card != null)
            collection.getCards().add(card);
    }

    public void sell(int cost, UsableItem item, Card card) {
        money += cost;
        if (item != null)
            collection.getItems().remove(item);
        if (card != null)
            collection.getCards().remove(card);
    }

    public static void addAccount(Account account) {
        accounts.add(account);
    }

    //getters
    public long getMoney() {
        return money;
    }

    public Collection getCollection() {
        return collection;
    }

    public ArrayList<model.Account> getFriends() {
        return friends;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public static ArrayList<Account> getAccounts() { return accounts; }
    //getters

    //setters
    public void setMoney(long money) {
        this.money = money;
    }
    //setters
}
