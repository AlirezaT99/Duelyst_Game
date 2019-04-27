package model;

public class UsableItem extends InfluentialItem {
    private int cost;
    private Deck deck;
    private Match match;


    public String toString(boolean showCost) {
        String output = "Name : " + name + " - Desc :" + description;
        if (showCost) output = output + " - Sell Cost : " + getCost();
        output = output + "\n";
        return output;
    }

    // setter & getter
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getCost() {
        return cost;
    }

    public Deck getDeck() {
        return deck;
    }

    public Match getMatch() {
        return match;
    }
    // setter & getter
}
