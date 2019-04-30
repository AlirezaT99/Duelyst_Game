package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Collection {
    private ArrayList<UsableItem> items = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Deck> decks = new ArrayList<>();
    private HashMap<String, UsableItem> itemsHashMap;
    private HashMap<String, Card> cardsHashMap;
    private HashMap<String, Deck> deckHashMap;
    private Deck selectedDeck = null;

    {
        itemsHashMap = new HashMap<>();
        cardsHashMap = new HashMap<>();
        deckHashMap = new HashMap<>();
    }

    public void setSelectedDeck(String name) {
        selectedDeck = deckHashMap.get(name);
    }

    public void addDeck(Deck deck) {
        decks.add(deck);
        deckHashMap.put(deck.getName(), deck);
    }

    public int deleteDeck(String deckName) {
        if (!getDeckHashMap().containsKey(deckName))
            return 9;
        getDecks().remove(getDeckHashMap().get(deckName));
        getDeckHashMap().remove(deckName);
        return 0;
    }

    public boolean validateDeck(Deck deck) {
        if (deck.getCards().size() != 20)
            return false;
        return deck.getHero() != null;
    }

    public void add(String id, Deck deck) {
        //find card and item and hero by id
        //it must be written in presenter and an instance should be held in presenter and the changes
        // should be applied in the copy instance
    }

    public String show(boolean showCost) {
        String output = "Heroes :\n";
        int idx = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) instanceof Hero) {
                output = output + "\t\t" + (idx + 1) + " : " + items.get(i).toString(showCost);
                idx++;
            }
        }
        output = output + "Items :\n";
        for (int i = 0; i < items.size(); i++)
            output = output + "\t\t" + (i + 1) + " : " + items.get(i).toString(showCost);
        idx = 0;
        output = output + "Cards :\n";
        for (int i = 0; i < cards.size(); i++) {
            if (!(cards.get(i) instanceof Hero)) {
                output = output + "\t\t" + (idx + 1) + " : " + items.get(i).toString(showCost);
                idx++;
            }
        }
        return output;
    }

    //search
    String search(String name) {
        Item item = itemsHashMap.get(name);
        if (item != null)
            return item.getItemID();
        Card card = cardsHashMap.get(name);
        if (card != null)
            return card.getCardID();
        return "-1";
    }

    UsableItem findItemByID(String itemID) {
        for (UsableItem item : items) {
            if (item.getItemID().equals(itemID))
                return item;
        }
        return null;
    }

     Card findCardByID(String cardID) {
        for (Card card : cards) {
            if (card.getCardID().equals(cardID))
                return card;
        }
        return null;
    }
    //search

    //getters
    public HashMap<String, UsableItem> getItemsHashMap() {
        return itemsHashMap;
    }

    public HashMap<String, Card> getCardsHashMap() {
        return cardsHashMap;
    }

    ArrayList<UsableItem> getItems() {
        return items;
    }

    ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Deck getSelectedDeck() {
        return selectedDeck;
    }

    public HashMap<String, Deck> getDeckHashMap() {
        return deckHashMap;
    }
    //getters
}
