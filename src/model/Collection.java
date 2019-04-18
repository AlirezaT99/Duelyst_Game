package model;

import java.util.ArrayList;

class Collection {
    private ArrayList<UsableItem> items;
    private ArrayList<Card> cards;
    private ArrayList<Deck> decks = new ArrayList<Deck>();
    private Deck selectedDeck;

    public void selectDeck(String name) {
        //if deck was in the decks
        //set select deck
    }

    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    public void deleteDeck(String deckName) {
        //if deck found
        //remove deck
        // return
        printMessage("Deck not found");
    }

    public void showDeck(Deck deck) {
        //show deck probably in presenter
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
    }

    public void remove(String id) {
        //remove and in dastana
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
            if (item.getName().compareTo(itemName) == 0)
                return item;

        }
        return null;
    }

    public Card findCardByName(String cardName) {
        for (Card card : cards) {
            if (card.getName().compareTo(cardName) == 0)
                return card;
        }
        return null;
    }
    //search


    private void printMessage(String message) {
        System.out.println(message);
    }

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

    //getters
}
