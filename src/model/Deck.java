package model;

import java.util.ArrayList;

class Deck {
    private String name;
    private ArrayList<Card> cards;
    private MovableCard.Hero hero;
    private int maxCardNumber;
    private Item item;

    {
        cards = new ArrayList<>();
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
