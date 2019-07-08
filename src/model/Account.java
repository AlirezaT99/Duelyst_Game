package model;

import presenter.LoginMenuProcess;

import java.io.Serializable;
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

    public static Account getAccountByUserName(String userName) {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName))
                return account;
        }
        return null;
    }

    public void buy(int cost, UsableItem item, Card card) throws NullPointerException {
        money -= cost;
        if (item != null) {
            UsableItem item1 = item.copy();
            item1.setCollectionID(createCollectionID());

            changeItem(item, +1);
        }
        if (card != null) {
            System.out.println(card.getName() + " " + card.collectionNumber);
            // card.setCardCollectionID(createCollectionID());
            if (card instanceof Hero) {
                Hero hero = ((Hero) card).copy();
                hero.setCardCollectionID(createCollectionID());
                changeHero((Hero) card, +1);
            }
            if (card instanceof Minion) {
                Minion minion = ((Minion) card).copy();
                minion.setCardCollectionID(createCollectionID());
                changeMinion((Minion) card, +1);
            }
            if (card instanceof Spell) {
                Spell spell = ((Spell) card).copy();
                spell.setCardCollectionID(createCollectionID());
                changeSpell((Spell) card, +1);
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
        if (item != null) {
            changeItem(item, -1);
        }
        if (card != null) {
            System.out.println(card.getName() + " " + card.collectionNumber);
            if (card instanceof Hero) {
                changeHero((Hero) card, -1);
            }
            if (card instanceof Minion) {
                for (int i = 0; i < collection.getMinions().size(); i++)
                    changeMinion((Minion) card, -1);
            }
            if (card instanceof Spell) {
                changeSpell((Spell) card, -1);
            }
        }
    }

    private void changeItem(UsableItem item, int act) {
        boolean found = false;
        for (int i = 0; i < collection.getItems().size(); i++) {
            if (collection.getItems().get(i).name.equalsIgnoreCase(item.name)) {
                found = true;
                collection.getItems().get(i).collectionNumber += (act);
                if (act == -1 && collection.getItems().get(i).collectionNumber <= 0) {
                    collection.getItems().remove(i);
                    return;
                }
            }
        }
        if (!found && act == 1) {
            collection.getItems().add(item);
            collection.getItemsHashMap().put(item.getCollectionID(), item);
        }
    }

    private void changeHero(Hero hero, int act) {
        boolean found = false;
        for (int i = 0; i < collection.getHeroes().size(); i++) {
            if (collection.getHeroes().get(i).name.equalsIgnoreCase(hero.name)) {
                found = true;
                collection.getHeroes().get(i).collectionNumber += act;
                if (act == -1 && collection.getHeroes().get(i).collectionNumber <= 0) {
                    collection.getHeroes().remove(i);
                    return;
                }
            }
        }
        if (!found && act == 1) {
            collection.getHeroes().add(hero);
            collection.getHeroHashMap().put(hero.collectionID, hero);
        }
    }

    private void changeMinion(Minion minion, int act) {
        boolean found = false;
        for (int i = 0; i < collection.getMinions().size(); i++) {
            if (collection.getMinions().get(i).name.equalsIgnoreCase(minion.name)) {
                found = true;
                collection.getMinions().get(i).collectionNumber += (act);
                if (act == -1 && collection.getMinions().get(i).collectionNumber <= 0) {
                    collection.getMinions().remove(i);
                    return;
                }
            }
        }
        if (act == 1 && !found) {
            collection.getMinions().add(minion);
            collection.getMinionHashMap().put(minion.collectionID, minion);
        }
    }

    private void changeSpell(Spell spell, int act) {
        boolean found = false;
        for (int i = 0; i < collection.getSpells().size(); i++) {
            if (collection.getSpells().get(i).name.equalsIgnoreCase(spell.name)) {
                found = true;
                collection.getSpells().get(i).collectionNumber += act;
                if (act == -1 && collection.getSpells().get(i).collectionNumber <= 0) {
                    collection.getSpells().remove(i);
                    return;
                }
            }
        }
        if (!found && act == 1) {
            collection.getSpells().add(spell);
            collection.getSpellHashMap().put(spell.collectionID, spell);
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

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public void increaseNumberOfWins() {
        numberOfWins++;
    }

    public ArrayList<ImportBasedDeck> getImportedDecks() {
        if (importedDecks == null)
            importedDecks = new ArrayList<>();
        return importedDecks;
    }

    public static void addToAccounts(Account account) {
        accounts.add(account);
        LoginMenuProcess loginMenuProcess = new LoginMenuProcess();
        loginMenuProcess.getUsers().add(account);
    }

    //setters
}
