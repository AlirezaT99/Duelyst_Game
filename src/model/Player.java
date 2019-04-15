package model;

import java.util.ArrayList;

class Player {
    private String userName;
    private String password;
    private long money;
    private Collection collection;
    private ArrayList<Player> friends;
    private Hand hand;
    private int mana;
    private Match match;
    private boolean isAI;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public long getMoney() {
        return money;
    }

    public Collection getCollection() {
        return collection;
    }

    public ArrayList<Player> getFriends() {
        return friends;
    }

    public Hand getHand() {
        return hand;
    }

    public int getMana() {
        return mana;
    }

    public Match getMatch() {
        return match;
    }

    public boolean isAI() {
        return isAI;
    }

    public boolean isHasLoggedIn() {
        return hasLoggedIn;
    }

    private boolean hasLoggedIn;

    public void login() {
        hasLoggedIn = true;
    }

    private void logout() {
        hasLoggedIn = false;
        //todo go to LoginMenu
    }

    public void endTurn() {
        match.switchTurn();
    }

    public void addToHand(Card card) {
        this.hand.fillEmptyPlace(this.collection.getSelectedDeck().getLastCard());
        // todo : fillEmptyPlace ro bayad public konim
    }

    public void playAI(Match match) {
        this.hand.selectCard(0).castCard(
                this.hand.selectCard(0).getImpact().getImpactArea().get(0).coordination.getX(),
                this.hand.selectCard(0).getImpact().getImpactArea().get(0).coordination.getY());
    }
}