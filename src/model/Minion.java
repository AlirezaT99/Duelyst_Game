package model;

import java.util.ArrayList;

public class Minion extends MovableCard {
    private static ArrayList<Minion> minions = new ArrayList<>();
    private Impact summonImpact;
    private Impact dyingWishImpact;
    private Impact onComboImpact;
    private Impact onTurnImpact;

    public Minion copy() {
        Minion minion = new Minion();
        minion.setHealth(this.getHealth());
        minion.isAlive = (this.isAlive);
        minion.cardCell = cardCell;
        minion.setDamage(this.getDamage());
        minion.didMoveInThisTurn = didMoveInThisTurn;
        minion.didAttackInThisTurn = didAttackInThisTurn;
        minion.moveRange = moveRange;
        minion.minAttackRange = minAttackRange;
        minion.maxAttackRange = maxAttackRange;
        minion.isMelee = this.isMelee;
        minion.isHybrid = isHybrid;
        minion.isRanged = isRanged;
        minion.isComboAttacker = isComboAttacker;
        minion.dispelableHealthChange = dispelableHealthChange;
        minion.dispelableDamageChange = dispelableDamageChange;
        minion.summonImpact = summonImpact == null ? null : summonImpact.copy();
        minion.dyingWishImpact = dyingWishImpact == null ? null : dyingWishImpact.copy();
        minion.onComboImpact = onComboImpact == null ? null : onComboImpact.copy();
        minion.onTurnImpact = onTurnImpact == null ? null : onTurnImpact.copy();
        minion.onDefendImpact = onDefendImpact == null ? null : onDefendImpact.copy();
        minion.onAttackImpact = onAttackImpact == null ? null : onAttackImpact.copy();
        minion.collectionID = this.collectionID;
        this.setCardfieldsForCopy(minion);
        return minion;
    }

    @Override
    protected void manageCasualties() {
        if (this.getHealth() + this.dispelableHealthChange <= 0) {
            this.isAlive = false;
            dyingWishImpact.doImpact(this.player, this, this.cardCell, this.cardCell);
            //do dyingWish
        }
    }

    @Override
    public void castCard(Cell cell) {
        super.castCard(cell);
        summonImpact.doImpact(this.player, this, this.cardCell, this.cardCell);
        // do summonImpact
    }

    @Override
    public void attack(MovableCard opponent) {
        if (this.isAttackValid(opponent)) {
            super.attack(opponent);
            onAttackImpact.doImpact(this.player, this, opponent.cardCell, this.cardCell);
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
        if (onDefendImpact.doesHaveAntiNegativeImpact())
            this.setHealth(this.getHealth() + opponent.getDamage() + opponent.dispelableDamageChange);
        if (onDefendImpact.isImmuneToMinDamage()) {
            if (this.player.match.table.doesHaveLowestDamage(opponent))
                this.setHealth(this.getHealth() + opponent.getDamage() + opponent.dispelableDamageChange);
        }
        onDefendImpact.doImpact(this.player, this, opponent.cardCell, this.cardCell);
    }

    //getters
    public static Minion getMinionByName(String name) {
        for (int i = 0; i < minions.size(); i++) {
            if (minions.get(i).getName().equals(name))
                return minions.get(i).copy();
        }
        return null;
    }
    //getters

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

    public static void addToMinions(Minion minion) {
        minions.add(minion);
    }
    //setters
}



