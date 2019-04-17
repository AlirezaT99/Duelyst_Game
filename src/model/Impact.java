package model;

import java.util.ArrayList;

class Impact {
    private String name;
    private ArrayList<MovableCard> impactArea;
    private boolean isvalidOnHero;
    private boolean isvalidOnMinion;
    private boolean isvalidOnAllies;
    private boolean isvalidOnEnemies;
    private boolean isBuff;
    private boolean isMana;
    private boolean isPassive;
    private int turnsActive;
    private int impactQuantity;
    private boolean isHolyBuff;
    private boolean isPowerBuff;
    private boolean isPoisonBuff;
    private boolean isWeaknessBuff;
    private boolean isStunBuff;
    private boolean isDisarmBuff;

    public void setImpactArea(Card card, Match match, Cell targetCell){

    }

    public void doImpact(){

    }

    private void manaChange(){
        for (MovableCard movableCard : impactArea)
            movableCard.player.
    }

    private void healthChange(){
        for (MovableCard movableCard: impactArea)
            movableCard.health += impactQuantity;
    }

    //getters

    public boolean isStunBuff() {
        return isStunBuff;
    }

    public boolean isDisarmBuff() {
        return isDisarmBuff;
    }

    public boolean isIsvalidOnHero() {
        return isvalidOnHero;
    }

    public boolean isIsvalidOnMinion() {
        return isvalidOnMinion;
    }

    public boolean isIsvalidOnAllies() {
        return isvalidOnAllies;
    }

    public boolean isIsvalidOnEnemies() {
        return isvalidOnEnemies;
    }

    public boolean isPassive() {
        return isPassive;
    }

    public int getTurnsActive() {
        return turnsActive;
    }

    //getters

}
