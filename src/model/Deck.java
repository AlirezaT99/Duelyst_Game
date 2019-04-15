package model;

import java.util.ArrayList;

class Deck {
    private ArrayList<Card> cards;
    private Hero hero;
    private int maxCardNumber;
    private Item item;

    {
        cards = new ArrayList<>();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getLastCard() {
        return new Card();
    }
}
