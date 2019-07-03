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
    private ArrayList<ImportBasedDeck> importedDecks = new ArrayList<>();

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.money = 15000;
        this.friends = new ArrayList<>();
        this.numberOfWins = 0;
        this.collection = new Collection();
        this.importedDecks = new ArrayList<>();
    }

    static {
        accounts = new ArrayList<>();
    }

    public void buy(int cost, UsableItem item, Card card) throws NullPointerException {
        money -= cost;
        if (item != null) {
            UsableItem item1 = item.copy();
            item1.setCollectionID(createCollectionID());
            collection.getItems().add(item1);
            collection.getItemsHashMap().put(item1.getCollectionID(), item1);
        }
        if (card != null) {
           // card.setCardCollectionID(createCollectionID());
            if (card instanceof Hero) {
                Hero hero = ((Hero) card).copy();
                hero.setCardCollectionID(createCollectionID());
                collection.getHeroes().add(hero);
                collection.getHeroHashMap().put(hero.getCollectionID(), hero);
            }
            if (card instanceof Minion) {
                Minion minion = ((Minion) card).copy();
                minion.setCardCollectionID(createCollectionID());
                collection.getMinions().add(minion);
                collection.getMinionHashMap().put(minion.getCollectionID(), minion);
            }
            if (card instanceof Spell) {
                Spell spell =  ((Spell) card).copy();
                spell.setCardCollectionID(createCollectionID());
                collection.getSpells().add(spell);
                collection.getSpellHashMap().put(spell.getCollectionID(), spell);
            }
        }
    }

    public String createCollectionID() {
        int i = 0;
        while (collection.findCardByCollectionID("" + i) != null || collection.findItemByCollectionID("" + i) != null)
            i++;
        return "" + i;
    }

    public void sell(int cost, UsableItem item, Card card) {
        money += cost;
        if (item != null)
            collection.getItems().remove(item);
        if (card != null) {
            if (card instanceof Hero) {
                for (int i = 0; i < collection.getHeroes().size(); i++)
                    if (collection.getHeroes().get(i).getName().equals(card.getName()) &&
                            collection.getHeroes().get(i).collectionID.equals(card.collectionID))
                        collection.getHeroes().remove(collection.getHeroes().get(i));
                collection.getHeroHashMap().remove(collection.getHeroHashMap().get(card.getCollectionID()));
            }
            if (card instanceof Minion) {
                for (int i = 0; i < collection.getMinions().size(); i++)
                    if (collection.getMinions().get(i).getName().equals(card.getName()) &&
                            collection.getMinions().get(i).collectionID.equals(card.collectionID))
                        collection.getMinions().remove(collection.getMinions().get(i));
                collection.getMinionHashMap().remove(collection.getMinionHashMap().get(card.getCollectionID()));
            }
            if (card instanceof Spell) {
                for (int i = 0; i < collection.getSpells().size(); i++)
                    if (collection.getSpells().get(i).getName().equals(card.getName()) &&
                            collection.getSpells().get(i).collectionID.equals(card.collectionID))
                        collection.getSpells().remove(collection.getSpells().get(i));
                collection.getSpellHashMap().remove(collection.getSpellHashMap().get(card.getCollectionID()));
            }
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

    public void setCollection(Collection collection){
        this.collection = collection;
    }

    public void increaseNumberOfWins(){
        numberOfWins++;
    }

    public ArrayList<ImportBasedDeck> getImportedDecks() {
        if(importedDecks == null)
            importedDecks = new ArrayList<>();
        return importedDecks;
    }

    //setters
}
