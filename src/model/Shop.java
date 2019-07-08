package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

    public static String search(String name) {
        int itemIndex = findItemIndex(name);
        if (itemIndex != -1)
            return itemIndex + "";
        int cardIndex = findCardIndex(name);
        if (cardIndex != -1)
            return cardIndex + "";
        return "-1";
    }

    public static String searchCollection(String name, Account buyingAccount) {
        if (buyingAccount.getCollection().findItemByName(name) == null &&
                buyingAccount.getCollection().findCardByName(name) == null) {
            return "Item/Card not found in collection";
        } else {
            String result = "";
            Collection collection = buyingAccount.getCollection();
            if (collection.findItemByName(name) != null) {
                for (int i = 0; i < collection.getItems().size(); i++)
                    if (collection.getItems().get(i).getName().equals(name))
                        result += (collection.getItems().get(i).getCollectionID() + "\n");
                return result;
            } else {
                for (int i = 0; i < collection.getMinions().size(); i++)
                    if (collection.getMinions().get(i).getName().equals(name))
                        result += (collection.getMinions().get(i).getCollectionID() + "\n");
                for (int i = 0; i < collection.getSpells().size(); i++)
                    if (collection.getSpells().get(i).getName().equals(name))
                        result += (collection.getSpells().get(i).getCollectionID() + "\n");
                for (int i = 0; i < collection.getHeroes().size(); i++)
                    if (collection.getHeroes().get(i).getName().equals(name))
                        result += (collection.getHeroes().get(i).getCollectionID() + "\n");
                return result;
            }
        }
    }


    // search

    // show Collection must come in show collection

    //buy

    public static int buy(Account account, String name) {
        UsableItem item = findItemByName(name.trim());
        Card card = findCardByName(name.trim());
        if (isBuyValid(account, item, card) != 0)
            return isBuyValid(account, item, card);
        int cost;
        if (item != null)
            cost = item.getCost();
        else
            cost = card.getCost();
        account.buy(cost, item == null ? null : item.copy(), card);
        return 0;
    }

    private static int isBuyValid(Account account, UsableItem item, Card card) {
        if (item == null && card == null) {
            return 3;
        }
        if ((item != null && item.getCost() > account.getMoney()) || (card != null && card.getCost() > account.getMoney())) {
            return 4;
        }
        if (item != null && account.getCollection().getItems().size() == 3) {
            return 5;
        }
        if((item != null && item.getNumber() == 0 ) || (card != null && card.getNumbers() == 0) )
            return 7;
        return 0;
    }
    //buy

    // sell
    public static int sell(Account account, String name) {
        UsableItem item = account.getCollection().findItemByName(name.trim());
        Card card = account.getCollection().findCardByName(name.trim());
        int cost;
        if (item == null && card == null) {
            return 6;
        }
        if (item != null) {
            if(item.collectionNumber == 0)
                return 7;
            cost = item.getCost();

        } else {
            if(card.collectionNumber == 0)
                return 7;
            cost = card.getCost();
        }

        account.sell(cost, item, card);
        return 0;
    }

    public static void changeNumbers(String name,int act) {
        long max = Math.max(Math.max(shopHeroes.size(),shopMinions.size()),Math.max(shopSpells.size(),shopItems.size()));
        for (int i = 0; i < max; i++) {
            try{
                if(shopHeroes.get(i).name.equalsIgnoreCase(name))
                    shopHeroes.get(i).setNumbers(shopHeroes.get(i).getNumbers()+act);
            }catch (Exception ignored){}
            try{
                if(shopMinions.get(i).name.equalsIgnoreCase(name))
                    shopMinions.get(i).setNumbers(shopMinions.get(i).getNumbers()+act);
            }catch (Exception ignored){}try{
                if(shopSpells.get(i).name.equalsIgnoreCase(name))
                    shopSpells.get(i).setNumbers(shopSpells.get(i).getNumbers()+act);
            }catch (Exception ignored){}try{
                if(shopItems.get(i).name.equalsIgnoreCase(name))
                    shopItems.get(i).setNumber(shopItems.get(i).getNumber()+act);
            }catch (Exception ignored){}
        }
    }
    // sell

    public static UsableItem findItemByName(String itemName) {
        for (UsableItem item : shopItems) {
            if (item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    private static int findItemIndex(String itemName) {
        UsableItem item = findItemByName(itemName);
        if (item != null)
            return shopItems.indexOf(item);
        return -1;
    }

    public static Card findCardByName(String cardName) {
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

    private static int findCardIndex(String cardName) {
        Spell spell = null;
        Hero hero = null;
        Minion minion = null;
        try {
            spell = (Spell) findCardByName(cardName);
        } catch (Exception e) {
            try {
                hero = (Hero) findCardByName(cardName);
            } catch (Exception e1) {
                try {
                    minion = (Minion) findCardByName(cardName);
                } catch (Exception ignored) {
                }
            }
        }
        if (spell != null)
            return shopSpells.indexOf(spell);
        if (hero != null)
            return shopHeroes.indexOf(hero);
        if (minion != null)
            return shopMinions.indexOf(minion);
        return -1;
    }

    public static ArrayList<ArrayList<String>> getCards() {
        ArrayList<ArrayList<String>> cards = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            cards.add(new ArrayList<>());
        for (Hero shopHero : shopHeroes)
            cards.get(0).add(shopHero.getName());
        for (Minion shopMinion : shopMinions)
            cards.get(1).add(shopMinion.name);
        for (Spell shopSpell : shopSpells)
            cards.get(2).add(shopSpell.name);
        for (UsableItem shopItem : shopItems)
            cards.get(3).add(shopItem.name);
        return cards;
    }

    public static HashMap<String, int[]> getMovableCardsPowers() {
        HashMap<String, int[]> cardPowers = new HashMap<>();
        for (Hero shopHero : shopHeroes) {
            int[] powers = {shopHero.getDamage(), shopHero.getHealth()};
            cardPowers.put(shopHero.name, powers);
        }
        for (Minion shopMinion : shopMinions) {
            int[] powers = {shopMinion.getDamage(), shopMinion.getHealth()};
            cardPowers.put(shopMinion.name, powers);
        }
        return cardPowers;
    }

    private static HashMap<String, Integer> getCardCost(ArrayList<Object> cards, HashMap<String, Integer> tempCosts) {
        for (Object object : cards) {
            if (object instanceof Card)
                tempCosts.put(((Card) object).name, ((Card) object).cost);
            if (object instanceof UsableItem)
                tempCosts.put(((UsableItem) object).name, ((UsableItem) object).getCost());
        }
        return tempCosts;
    }

    private static HashMap<String, Integer> getCardNumber(ArrayList<Object> objects, HashMap<String, Integer> tempCosts) {
        for (Object object : objects) {
            if (object instanceof Card)
                tempCosts.put(((Card) object).getName(), ((Card) object).getNumbers());
            if (object instanceof UsableItem)
                tempCosts.put(((UsableItem) object).name, ((UsableItem) object).getNumber());
        }
        return tempCosts;
    }


    public static HashMap<String, Integer> getCosts() {
        HashMap<String, Integer> cardCosts = new HashMap<>();
        cardCosts = new HashMap<>(getCardCost(new ArrayList<>(shopHeroes), cardCosts));
        cardCosts = new HashMap<>(getCardCost(new ArrayList<>(shopMinions), cardCosts));
        cardCosts = new HashMap<>(getCardCost(new ArrayList<>(shopSpells), cardCosts));
        cardCosts = new HashMap<>(getCardCost(new ArrayList<>(shopItems), cardCosts));
        return cardCosts;
    }

    public static HashMap<String, Integer> getNumbers() {
        HashMap<String, Integer> cardNum = new HashMap<>();
        HashMap<String, Integer> finalCardNum = cardNum;
        shopHeroes.forEach(hero ->{
            finalCardNum.put(hero.name,hero.getNumbers());
        });
        shopMinions.forEach(minion -> finalCardNum.put(minion.name,minion.getNumbers()));
        shopSpells.forEach(spell -> finalCardNum.put(spell.name,spell.getNumbers()));
        shopItems.forEach(usableItem -> finalCardNum.put(usableItem.name,usableItem.getNumber()));
        return finalCardNum;
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
    public static void addToHeroes(Hero hero) {
        shopHeroes.add(hero);
    }

    public static void addToMinions(Minion minion) {
        shopMinions.add(minion);
    }

    public static void addToSpells(Spell spell) {
        shopSpells.add(spell);
    }

    public static void addToItems(UsableItem usableItem) {
        shopItems.add(usableItem);
    }

    public static HashSet<String> getCostumeCards() {
        HashSet<String> costumeCards = new HashSet<>();
        for (Hero shopHero : shopHeroes) {
            if (shopHero.isCostume)
                costumeCards.add(shopHero.getName());
        }
        for (Minion shopMinion : shopMinions) {
            if (shopMinion.isCostume)
                costumeCards.add(shopMinion.name);
        }
        for (Spell shopSpell : shopSpells) {
            if (shopSpell.isCostume)
                costumeCards.add(shopSpell.getName());
        }
        return costumeCards;
    }

    public static int getCost(String objectName) {
        for (Hero shopHero : shopHeroes) {
            if (shopHero.getName().equals(objectName))
                return shopHero.cost;
        }
        for (Minion shopMinion : shopMinions) {
            if (shopMinion.getName().equals(objectName))
                return shopMinion.getCost();
        }
        for (Spell shopSpell : shopSpells) {
            if (shopSpell.getName().equals(objectName))
                return shopSpell.cost;
        }
        for (UsableItem shopItem : shopItems) {
            if (shopItem.name.equals(objectName))
                return shopItem.getCost();
        }
        return 0;
    }

    public static int getType(String name) {
        Card card = Shop.findCardByName(name);
        if (card instanceof Hero)
            return 0;
        if (card instanceof Minion)
            return 1;
        if (card instanceof Spell)
            return 2;
        Item item = Shop.findItemByName(name);
        if (item != null)
            return 3;
        return 5;


    }

    public static int findCost(String name) {
        Card card = findCardByName(name);
        if (card != null)
            return card.cost;
        try {
            UsableItem item = findItemByName(name);
            if (item != null)
                return item.getCost();
        } catch (Exception ignored) {
        }
        return -1;
    }

    public static int[] findPowers(String name) {
        Card card = findCardByName(name);
        int[] powers = new int[2];
        if (card instanceof MovableCard) {
            powers[0] = ((MovableCard) card).getDamage();
            powers[1] = ((MovableCard) card).getHealth();
        }
        return powers;
    }

    public static int findCount(String name) {
        Card card = findCardByName(name);
        if (card != null)
            return card.getNumbers();
        try {
            UsableItem item = findItemByName(name);
            if (item != null)
                return item.getNumber();
        } catch (Exception ignored) {
        }
        return 0;
    }
    //setters
}
