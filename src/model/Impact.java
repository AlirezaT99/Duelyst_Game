package model;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;

class Impact {
    private String name;
    private ArrayList<Cell> impactArea;
    //type finder booleans
    private boolean isPositive;
    private boolean isBuff;

    //type finder booleans

    //target finder booleans
        private boolean isvalidOnHero;
    private boolean isvalidOnMinion;
    private boolean isvalidOnAllies;
    private boolean isvalidOnEnemies;
    //target finder booleans

    //type specifier booleans
    private boolean isMana;
    private boolean isHealthChange;
    private boolean isDamageChange;
    private boolean isHolyBuff;
    private boolean isPowerBuff;
    private boolean isPoisonBuff;
    private boolean isWeaknessBuff;
    private boolean isStunBuff;
    private boolean isDisarmBuff;
    //type specifier booleans

    private boolean isPassive;
    private int turnsActive;
    private int turnsToBeActivated;
    private int impactQuantity;


    public void setImpactArea(Card card, Match match, Cell targetCell){

    }

    public void doImpact(){
        if(turnsToBeActivated == 0) {
            if (isMana)
                manaChange();
            if (isHealthChange)
                healthChange();
            if(isDamageChange)
                damagaeChange();
        }
    }


    private void manaChange(){
        for (Cell cell: impactArea) {
            Player player = cell.getMovableCard().player;
            player.setMana(player.getMana() + impactQuantity);
        }
    }

    private void healthChange(){
        for (Cell cell: impactArea)
            cell.getMovableCard().nonePassiveHealthChange += impactQuantity;
    }

    private void damagaeChange(){
        for(Cell cell : impactArea)
            cell.getMovableCard().nonePassiveDamageChange += impactQuantity;
    }


    void goThroughTime(){
        turnsActive -= 1;
        turnsToBeActivated -= 1;
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

    public boolean isPoisonBuff() {
        return isPoisonBuff;
    }
    //getters
    //setters


    public void setName(String name) {
        this.name = name;
    }

    public void setImpactArea(ArrayList<Cell> impactArea) {
        this.impactArea = impactArea;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }

    public void setBuff(boolean buff) {
        isBuff = buff;
    }

    public void setIsvalidOnHero(boolean isvalidOnHero) {
        this.isvalidOnHero = isvalidOnHero;
    }

    public void setIsvalidOnMinion(boolean isvalidOnMinion) {
        this.isvalidOnMinion = isvalidOnMinion;
    }

    public void setIsvalidOnAllies(boolean isvalidOnAllies) {
        this.isvalidOnAllies = isvalidOnAllies;
    }

    public void setIsvalidOnEnemies(boolean isvalidOnEnemies) {
        this.isvalidOnEnemies = isvalidOnEnemies;
    }

    public void setMana(boolean mana) {
        isMana = mana;
    }

    public void setHealthChange(boolean healthChange) {
        isHealthChange = healthChange;
    }

    public void setDamageChange(boolean damageChange) {
        isDamageChange = damageChange;
    }

    public void setHolyBuff(boolean holyBuff) {
        isHolyBuff = holyBuff;
    }

    public void setPowerBuff(boolean powerBuff) {
        isPowerBuff = powerBuff;
    }

    public void setPoisonBuff(boolean poisonBuff) {
        isPoisonBuff = poisonBuff;
    }

    public void setWeaknessBuff(boolean weaknessBuff) {
        isWeaknessBuff = weaknessBuff;
    }

    public void setStunBuff(boolean stunBuff) {
        isStunBuff = stunBuff;
    }

    public void setDisarmBuff(boolean disarmBuff) {
        isDisarmBuff = disarmBuff;
    }

    public void setPassive(boolean passive) {
        isPassive = passive;
    }

    public void setTurnsActive(int turnsActive) {
        this.turnsActive = turnsActive;
    }

    public void setTurnsToBeActivated(int turnsToBeActivated) {
        this.turnsToBeActivated = turnsToBeActivated;
    }

    public void setImpactQuantity(int impactQuantity) {
        this.impactQuantity = impactQuantity;
    }
}
