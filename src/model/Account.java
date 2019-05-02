package model;

import java.util.ArrayList;

public class Account {
    private ArrayList<MatchHistory> matchHistories = new ArrayList<>();
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
        this.collection = new Collection();
    }

    static {
        accounts = new ArrayList<>();
    }

    public void buy (int cost, UsableItem item, Card card) throws NullPointerException {
        money -= cost;
        if (item != null){
            collection.getItems().add(item);
            item.setItemID(createID());
        }
            if (card != null) {
                if(card instanceof Hero)
                    collection.getHeroes().add((Hero)card);
                if(card instanceof Minion)
                    collection.getMinions().add((Minion) card);
                if(card instanceof Spell)
                    collection.getSpells().add((Spell) card);
                card.setCardID(createID());
            }
        }
    private String createID(){
        int i = 0;
        while(collection.findCardByID(""+i) != null || collection.findItemByID(""+i) != null)
            i++;
        return ""+i;
    }

    public void sell(int cost, UsableItem item, Card card) {
        money += cost;
        if (item != null)
            collection.getItems().remove(item);
        if (card != null)
        {
            if(card instanceof Hero)
                collection.getHeroes().remove((Hero)card);
            if(card instanceof Minion)
                collection.getMinions().remove((Minion) card);
            if(card instanceof Spell)
                collection.getSpells().remove((Spell) card);
        }
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

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<MatchHistory> getMatchHistory() {
        return matchHistories;
    }
    //getters

    //setters
    public void setMoney(long money) {
        this.money = money;
    }
    //setters
}
