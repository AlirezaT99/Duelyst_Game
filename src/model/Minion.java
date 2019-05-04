package model;


import java.util.ArrayList;
import java.util.HashMap;

public class Minion extends MovableCard {
    private static ArrayList<Minion> minions = new ArrayList<>();
    private Impact summonImpact;
    private Impact dyingWishImpact;
    private Impact onComboImpact;
    private Impact onTurnImpact;

    @Override
    protected void manageCasualties() {
        if (this.getHealth() + this.dispelableHealthChange <= 0) {
            this.isAlive = false;
            dyingWishImpact.doImpact(this.player,this ,this.cardCell, this.cardCell);
            //do dyingWish
        }
    }

    @Override
    public void castCard(Cell cell) {
        super.castCard(cell);
        summonImpact.doImpact(this.player,this ,this.cardCell, this.cardCell);
        // do summonImpact
    }

    @Override
    public void attack(Cell cell) {
        if (this.isAttackValid(cell)) {
            super.attack(cell);
            onAttackImpact.doImpact(this.player,this, cell, this.cardCell);
        }
    }

    public void comboAttack(Cell cell, ArrayList<Minion> minions) {
        minions.get(0).onComboImpact.doImpact(this.player, this,cell, this.cardCell);
        super.attack(cell);
        for (int i = 1; i < minions.size(); i++) {
            MovableCard movableCard = minions.get(i);
            if (movableCard.isAttackValid(cell)) {
                MovableCard opponent = cell.getMovableCard();
                opponent.takeDamage(this.getDamage());
                super.didAttackInThisTurn = true;
            }
        }
    }

    @Override
    public String toString(boolean showCost) {
        String classType = getClassType(this);
        String output = "Type : Minion - Name : " + this.name + " - Class : " + classType + " - AP : " + this.getDamage() +
                " HP : " + this.getHealth() + " - MP :" + this.manaCost + " Special power : " + this.description;
        if (showCost) output = output + " - Sell Cost : " + getCost();
       // output = output + "\n";
        return output;
    }

    @Override
    protected void counterAttack(MovableCard opponent) {
        super.counterAttack(opponent);
        if(onDefendImpact.doesHaveAntiNegativeImpact())
            this.setHealth(this.getHealth() + opponent.getDamage() + opponent.dispelableDamageChange);
        if(onDefendImpact.isImmuneToMinDamage()){
            if(this.player.match.table.doesHaveLowestDamage(opponent))
                this.setHealth(this.getHealth() + opponent.getDamage() + opponent.dispelableDamageChange);
        }
        onDefendImpact.doImpact(this.player,this, opponent.cardCell, this.cardCell);
    }

    //setters

    public void setSummonImpact(Impact summonImpact) {
        this.summonImpact = summonImpact;
    }

    public void setDyingWishImpact(Impact dyingWishImpact) {
        this.dyingWishImpact = dyingWishImpact;
    }



    public void setOnComboImpact(Impact onComboImpact) {
        this.onComboImpact = onComboImpact;
    }

    public void setOnTurnImpact(Impact onTurnImpact) {
        this.onTurnImpact = onTurnImpact;
    }

    public static void addToMinions(Minion minion){
     minions.add(minion);
    }
    //setters
}



