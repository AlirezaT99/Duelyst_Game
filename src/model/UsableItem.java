package model;

class UsableItem extends InfluentialItem {
    private int cost;
    private Deck deck;
    private Match match;

    @Override
    public String toString() {
        return "Name : " + name + " - Desc :" + description;
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
