package model;

import java.util.ArrayList;

class Player {
    private Account account;
    private Deck deck; // playing deck
    private Collection collection; // playing collection
    private long money;
    private Hand hand;
    private int mana;
    Match match;
    private boolean isAI;


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

    public void fillHand() {
       // this.hand.fillEmptyPlace(deck);
        //
    }

    public void playAI(Match match) {
        this.hand.selectCard(0).castCard(
                this.hand.selectCard(0).getImpact().getImpactArea().get(0).getCellCoordination().getX(),
                this.hand.selectCard(0).getImpact().getImpactArea().get(0).getCellCoordination().getY());
    }


    //getters
    public int getMana() {
        return mana;
    }

    public Hand getHand() {
        return hand;
    }

    public Match getMatch() {
        return match;
    }

   public long getMoney(){
        return money;
   }
   public Collection getCollection(){
        return this.collection;
   }
   public String getUserName(){
        return this.account.getUserName();
   }
    //getters

    //setters

    public void setMana(int mana) {
        this.mana = mana;
    }
    public void setMoney(long money){
        this.money = money;
    }
//setters

}