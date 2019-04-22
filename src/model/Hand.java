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

    void fillEmptyPlaces(Deck deck) {
        for (int i = 0; i < 5; i++)
            if (this.cards.get(i) == null)
                this.cards.add(i, deck.getLastCard());
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
