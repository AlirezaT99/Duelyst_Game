package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Collection {
    private ArrayList<UsableItem> items;
    private ArrayList<Card> cards;
    private ArrayList<Deck> decks;
    private HashMap<String, Deck> deckHashMap;
    private Deck selectedDeck;

    {
        items = new ArrayList<>();
        cards = new ArrayList<>();
        decks = new ArrayList<>();
        deckHashMap = new HashMap<>();
    }

    public void selectDeck(String name) {
        selectedDeck = deckHashMap.get(name);
    }

    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    public void deleteDeck(String deckName) {
        if (deckHashMap.containsKey(deckName)) {
            decks.remove(deckHashMap.get(deckName));
            deckHashMap.remove(deckName);
        }
        else
            printMessage("Deck not found"); // must be moved to view
    }

    public void showDeck(Deck deck) {
        //show deck probably in presenter
        // and int the view
        // it must not be here
    }

    public boolean validateDeck(Deck deck) {
        if (deck.getCards().size() != 20)
            return false;
        int heroCounter = 0;
        for (Card card : deck.getCards())
            if (card instanceof MovableCard.Hero)
                heroCounter++;
        return heroCounter == 1;
    }

    public void add(String id, Deck deck) {
        //find card and item and hero by id
        //it must be written in presenter and an instance should be held in presenter and the changes should be applied in the copy instance
    }

    public void remove(String id) {
        //same as add
    }

    //search
    public int search(String name) {
        Item item = findItemByName(name);
        if (item != null)
            return items.indexOf(item);
        Card card = findCardByName(name);
        if (card != null)
            return cards.indexOf(card);
        printMessage("Card/Item not found");
        return -1;
    }

    public UsableItem findItemByName(String itemName) {
        for (UsableItem item : items) {
            if (item.getName().equals(itemName))
                return item;

        }
        return null;
    }

    public Card findCardByName(String cardName) {
        for (Card card : cards) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }
    //search

    //getters
    public ArrayList<UsableItem> getItems() {
        return items;
    }

    public ArrayList<Card> getCards() {
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

    private void printMessage(String message) {
        System.out.println(message);
    }
}
