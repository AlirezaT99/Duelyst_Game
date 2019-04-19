package model;

import java.util.ArrayList;

class Spell extends Card {
    private int areaTargetSquare;
    private Impact primaryImpact = super.getImpact();
    private Impact secondaryImpact;

    public boolean isCastingValid(Cell cell, Impact impact) {
//        if(everyWhere)
//            return true;
//        if(singletarget)
//        {
//            if(primaryimpact.e)
//        }
        return true;
    }

    public void castCard(Cell cell) {
        if (isCastingValid(cell, primaryImpact))
            primaryImpact.doImpact();
        if (secondaryImpact != null && isCastingValid(cell, secondaryImpact))
            secondaryImpact.doImpact();
    }
    //getters
    public Impact getPrimaryimpact() {
        return primaryImpact;
    }
    public Impact getSecondaryImpact() { return secondaryImpact; }
    //getters

    //setters

    public void setName(String name) {
        this.name = name;
    }
    public void setAreaTargetSquare(int areaTargetSquare) {
        this.areaTargetSquare = areaTargetSquare;
    }
    public void setManaCost(int manaCost){
        this.manaCost = manaCost;
    }
    public void setCost(int cost){
        this.cost = cost;
    }
    public void setPrimaryimpact(Impact primaryimpact){
        this.primaryImpact = primaryimpact;
    }

    public void setSecondaryImpact(Impact secondaryImpact) {
        this.secondaryImpact = secondaryImpact;
    }
    //setters
}
