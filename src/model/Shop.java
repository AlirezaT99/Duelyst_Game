package model;

import java.util.ArrayList;

public class Shop {
    private ArrayList<MovableCard.Hero> shopHeroes;
    private ArrayList<MovableCard.Minion> shopMinions;
    private ArrayList<Spell> shopSpells;
    private ArrayList<UsableItem> shopItems;

    {
        shopHeroes = new ArrayList<>();
        shopMinions = new ArrayList<>();
        shopSpells = new ArrayList<>();
        shopItems = new ArrayList<>();
    }

    public static Shop initShop() {
        Shop shop = new Shop();
        // create Items from file
        // add items to shop
        // create cards from file
        // add cards to shop
        return shop;
    }

    // search

    public String search(String name) {
        Item item = findItemByName(name);
        if (item != null)
            return item.getItemID();
        Card card = findCardByName(name);
        if (card != null)
            return card.getCardID();
        return "-1";
    }

    public String searchCollection(String name, Account buyingAccount) {
        return buyingAccount.getCollection().search(name);
    }

    // search

    // show Collection must come in show collection

    //buy

    public int buy(Account account, String name) {
        UsableItem item = findItemByName(name);
        Card card = findCardByName(name);
        if (isBuyValid(account, item, card) != 0)
            return isBuyValid(account, item, card);
        int cost;
        if (item != null)
            cost = item.getCost();
        else
            cost = card.getCost();
        account.buy(cost, item, card);
        return 0;
    }

    private int isBuyValid(Account account, UsableItem item, Card card) {
        if (item == null && card == null) {
            // printMessage("Card/Item is out of stock");
            return 3;
        }
        if ((item != null && item.getCost() > account.getMoney()) || (card != null && card.getCost() > account.getMoney())) {
            // printMessage("Not enough drake");
            return 4;
        }
        if (item != null && account.getCollection().getItems().size() == 3) {
            //printMessage("Maximum items are in the Collection");
            return 5;
        }
        return 0;
    }

    //buy

    // sell

    public int sell(Account account, String name) {
        UsableItem item = account.getCollection().findItemByID(name);
        Card card = account.getCollection().findCardByID(name);
        int cost;
        if (item == null && card == null) {
            //printMessage("Item/Card not found");
            return 6;
        }
        // printMessage("Sell was successful");
        if (item != null) {
            cost = item.getCost();
        } else
            cost = card.getCost();
        account.sell(cost, item, card);
        return 0;
    }

    // sell

    private UsableItem findItemByName(String itemName) {
        for (UsableItem item : shopItems) {
            if (item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    private Card findCardByName(String cardName) {
        for (Card card : shopHeroes) {
            if (card.getName().equals(cardName))
                return card;
        }
        for (Card card : shopMinions) {
            if (card.getName().equals(cardName))
                return card;
        }
        for (Card card : shopSpells) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    //getters
    public ArrayList<MovableCard.Hero> getShopHeroes() {
        return shopHeroes;
    }

    public ArrayList<MovableCard.Minion> getShopMinions() {
        return shopMinions;
    }

    public ArrayList<Spell> getShopSpells() {
        return shopSpells;
    }

    public ArrayList<UsableItem> getShopItems() {
        return shopItems;
    }

    //getters
}
