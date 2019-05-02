package model;

import com.sun.xml.internal.org.jvnet.mimepull.MIMEConfig;

import java.util.ArrayList;

public class Minion extends MovableCard {
    private static ArrayList<Minion> minions = new ArrayList<>();
    private Impact summonImpact;
    private Impact dyingWishImpact;
    private Impact onDefendImpact;
    private Impact onAttackImpact;
    private Impact onComboImpact;
    private Impact onTurnImpact;

    @Override
    protected void manageCasualties() {
        if (this.getHealth() + this.dispelableHealthChange <= 0) {
            this.isAlive = false;
            dyingWishImpact.setImpactArea(this.player, this.cardCell, this.cardCell);
            //do dyingWish
        }
    }

    @Override
    public void castCard(Cell cell) {
        super.castCard(cell);
        summonImpact.setImpactArea(this.player, this.cardCell, this.cardCell);
        // do summonImpact
    }

    @Override
    public void attack(Cell cell) {
        if (this.isAttackValid(cell)) {
            super.attack(cell);
            onAttackImpact.setImpactArea(this.player, cell, this.cardCell);
        }
    }

    public void comboAttack(Cell cell, ArrayList<Minion> minions) {
        minions.get(0).onComboImpact.setImpactArea(this.player, cell, this.cardCell);
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
        onDefendImpact.setImpactArea(this.player, opponent.cardCell, this.cardCell);
    }

    //setters

    public void setSummonImpact(Impact summonImpact) {
        this.summonImpact = summonImpact;
    }

    public void setDyingWishImpact(Impact dyingWishImpact) {
        this.dyingWishImpact = dyingWishImpact;
    }

    public void setOnDefendImpact(Impact onDefendImpact) {
        this.onDefendImpact = onDefendImpact;
    }

    public void setOnAttackImpact(Impact onAttackImpact) {
        this.onAttackImpact = onAttackImpact;
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



