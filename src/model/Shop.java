package model;

import presenter.CollectionMenuProcess;

import java.util.ArrayList;

public class Shop {
    private static ArrayList<Hero> shopHeroes = new ArrayList<>();
    private static ArrayList<Minion> shopMinions = new ArrayList<>();
    private static ArrayList<Spell> shopSpells = new ArrayList<>();
    private static ArrayList<UsableItem> shopItems = new ArrayList<>();

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
        if(buyingAccount.getCollection().findItemByName(name)==null &&
                buyingAccount.getCollection().findCardByName(name)==null){
            return "Item/Card not found in collection";}
        else
        {
            String result = "";
            Collection collection = buyingAccount.getCollection();
            if(collection.findItemByName(name)!=null){
                for(int i = 0; i< collection.getItems().size(); i++)
                    if( collection.getItems().get(i).getName().equals(name))
                        result+=(collection.getItems().get(i).getItemID() + "\n");
                return result;}
            else{
                for(int i = 0; i< collection.getMinions().size(); i++)
                    if( collection.getMinions().get(i).getName().equals(name))
                        result+=(collection.getMinions().get(i).getCardID() + "\n");
                for(int i = 0; i< collection.getSpells().size(); i++)
                    if( collection.getSpells().get(i).getName().equals(name))
                        result+=(collection.getSpells().get(i).getCardID() + "\n");
                for(int i = 0; i< collection.getHeroes().size(); i++)
                    if( collection.getHeroes().get(i).getName().equals(name))
                        result+=(collection.getHeroes().get(i).getCardID() + "\n");
                return result;}
        }
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
        account.buy(cost, item ==null?null:item.copy(), card);
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

    private static UsableItem findItemByName(String itemName) {
        for (UsableItem item : shopItems) {
            if (item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    private static Card findCardByName(String cardName) {
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
    public static ArrayList<Hero> getShopHeroes() {
        return shopHeroes;
    }

    public static ArrayList<Minion> getShopMinions() {
        return shopMinions;
    }

    public static ArrayList<Spell> getShopSpells() {
        return shopSpells;
    }

    public static ArrayList<UsableItem> getShopItems() {
        return shopItems;
    }

    //getters

    //setters
    public static void addToHeroes(Hero hero){
        shopHeroes.add(hero);
    }
    public static void addToMinions(Minion minion){
        shopMinions.add(minion);
    }
    public static void addToSpells(Spell spell){
        shopSpells.add(spell);
    }
    public static void addToItems(UsableItem usableItem){
        shopItems.add(usableItem);
    }

}
