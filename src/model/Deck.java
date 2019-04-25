package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private String name;
    private ArrayList<Card> cards;
    private HashMap<String, Card> cardsHashMap;
    private MovableCard.Hero hero;
    public final int MAX_CARD_NUMBER = 20;
    private Item item;

    {
        cards = new ArrayList<>();
        cardsHashMap = new HashMap<>();
    }

    public Deck(String name) {
        this.name = name;
    }

    public static void createDeck(String deckName) {
        Deck deck = new Deck(deckName);
    }

    //getters
    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getLastCard() {
        //todo
        return new Card();
    }

    public String getName() {
        return name;
    }
    //getters
}
