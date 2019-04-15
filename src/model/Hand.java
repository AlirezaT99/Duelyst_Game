package model;

import java.util.ArrayList;

class Hand {
    private ArrayList<Card> cards;
    private Card selectedCard;

    {
        cards = new ArrayList<>();
    }

    ArrayList<Card> getCards() {
        return cards;
    }

    public void fillEmptyPlace(Card card) {

    }

    private boolean isThereEmptyPlace(Card card) {
        return false; // <---
    }

    void deleteCastedCard(Card card) {
        cards.remove(card);
    }

    public void switchCard(Card card) {

    }

    private boolean didSwitchInCurrentTurn() {
        return false; // <---
    }

    public Card selectCard(int index) {
        return new Card();
    }

    public void castCard() {

    }
}
