package model;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    private Card selectedCard;
    private boolean switchCardThisTurn;
    private Deck deck;
    {
        cards = new ArrayList<>();
    }


    void fillEmptyPlaces(Deck deck) {
//        for (int i = 0; i < 5; i++)
//            if (this.cards.get(i) == null)
//                this.cards.add(i, deck.getLastCard());
    }

    private boolean isThereEmptyPlace(Card card) {
        //todo
        return false;
    }

    void deleteCardBySettingNull(Card card) {
        int index = cards.indexOf(card);
        cards.set(index, null);
    }


    //switching

    private boolean didSwitchInCurrentTurn() {
        return switchCardThisTurn;
    }

    public void switchCard() {
        deck.putTheCardBackInTheQueue(selectedCard);
        Card card = deck.getLastCard();
        for (int i = 0; i < 5; i++) {
            if(cards.get(i).equals(selectedCard)){
                deleteCardBySettingNull(cards.get(i));
                cards.set(i,card);
            }
        }
        switchCardThisTurn = true;
    }

    //switching

    public String showHand() {
        String output = "";
        for (Card card : cards) {
            output = output.concat(card.toString(false));
        }
        return output;
    }

    public Card selectCard(int index) {
        selectedCard = cards.get(index);
        return selectedCard;
    }


    //getters

    ArrayList<Card> getCards() {
        return cards;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    //getters

    //setters

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    //setters

}
