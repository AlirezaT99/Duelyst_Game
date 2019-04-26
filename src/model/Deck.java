package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private String name;
    private ArrayList<Card> cards;
    private ArrayList<UsableItem> items;
    private HashMap<String, Card> cardsHashMap;
    private MovableCard.Hero hero;
    public final int MAX_CARD_NUMBER = 20;

    {
        cards = new ArrayList<>();
        cardsHashMap = new HashMap<>();
    }

    @Override
    public String toString() {
        String output = "Heroes :\n" + "\t\t1 : " + hero.toString() + " - Sell Cost : " + hero.getCost() + "\n";
        output = output + "Items :\n";
        for (int i = 0; i < items.size(); i++)
            output = output + "\t\t" + (i+1) + " : " + items.get(i).toString()
                    + " - Sell Cost : " + items.get(i).getCost() + "\n";
        output = output + "Cards :\n";
        for (int i = 0; i < cards.size(); i++)
            output = output + "\t\t" + (i+1) + " : " + cards.get(i).toString()
                    + " - Sell Cost : " + cards.get(i).getCost() + "\n";

        return output;
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
