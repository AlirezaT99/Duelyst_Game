package model;

import java.util.ArrayList;

class Shop {

    private ArrayList<Card> shopCards;
    private ArrayList<UsableItem> shopItems;

    {
        shopCards = new ArrayList<Card>();
        shopItems = new ArrayList<UsableItem>();
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
            return item.getId();
        Card card = findCardByName(name);
        if (card != null)
            return card.getCardID();
        printMessage("Card/Item not found");
        return "-1";
    }

    public int searchCollection(String name, Account buyingAccount) {
        return buyingAccount.getCollection().search(name);
    }

    // search

    // show Collection must come in show collection

    //buy

    public void buy(Account account, String name) {
        UsableItem item = findItemByName(name);
        Card card = findCardByName(name);
        if (!isBuyValid(account, item, card))
            return;
        int cost;
        if (item != null)
            cost = item.getCost();
        else
            cost = card.getCost();
        account.buy(cost, item, card);
        printMessage("Buying was successful");
    }

    private boolean isBuyValid(Account account, UsableItem item, Card card) {
        if (item == null && card == null) {
            printMessage("Card/Item is out of stock");
            return false;
        }
        if ((item != null && item.getCost() > account.getMoney()) || (card != null && card.getCost() > account.getMoney())) {
            printMessage("Not enough drake");
            return false;
        }
        if (item != null && account.getCollection().getItems().size() == 3) {
            printMessage("Maximum items are in the Collection");
            return false;
        }
        return true;
    }

    //buy

    // sell

    public void sell(Account account, String name) {
        UsableItem item = account.getCollection().findItemByName(name);
        Card card = account.getCollection().findCardByName(name);
        int cost = 0;
        if (item == null && card == null) {
            printMessage("Item/Card not found");
            return;
        }
        printMessage("Sell was successFull");
        if (item != null) {
            cost = item.getCost();
        } else
            cost = card.getCost();
        account.sell(cost, item, card);
    }

    // sell

    private UsableItem findItemByName(String itemName) {
        for (UsableItem item : shopItems) {
            if (item.getName().compareTo(itemName) == 0)
                return item;
        }
        return null;
    }

    private Card findCardByName(String cardName) {
        for (Card card : shopCards) {
            if (card.getName().compareTo(cardName) == 0)
                return card;
        }
        return null;
    }

    private void printMessage(String message) {
        System.out.println(message);
    }
}
