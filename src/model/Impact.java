package model;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;

class Impact {
    private String name;
    private ArrayList<Cell> impactArea;
    private boolean isPositive;
    private boolean isvalidOnHero;
    private boolean isvalidOnMinion;
    private boolean isvalidOnAllies;
    private boolean isvalidOnEnemies;
    private boolean isBuff;
    private boolean isMana;
    private boolean isHealthChange;
    private boolean isDamageChange;
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
        if(isMana)
            manaChange();
        if(isHealthChange)
            healthChange();
    }


    private void manaChange(){
        for (Cell cell: impactArea) {
            Player player = cell.getMovableCard().player;
            player.setMana(player.getMana() + impactQuantity);
        }
    }

    private void healthChange(){
        for (Cell cell: impactArea)
            cell.getMovableCard().health += impactQuantity;
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

    public int getImpactQuantity() {
        return impactQuantity;
    }

    public ArrayList<Cell> getImpactArea() {
        return impactArea;
    }

    //getters

}
