package model;

import java.util.ArrayList;

class Spell extends Card {
    private ArrayList spells = new ArrayList();
    private Impact impact;
    private int[] areaTargetSquare = new int[2];
    private Impact primaryImpact;
    private Impact secondaryImpact;

    public boolean isCastingValid(Player castingPlayer, Cell cell, Impact impact) {
        String targetsType = impact.getTargetTypeId();
        if (targetsType.charAt(0) == '1')
            return true;
        if (targetsType.charAt(1) == '0')
            return true;
        if (targetsType.charAt(2) == '1')
            return true;
        if (targetsType.charAt(3) == '2')
            return true;
        if (targetsType.charAt(4) == '0')
            return true;
        if (targetsType.charAt(4) == '2') {
            if (targetsType.charAt(3) == '0')
                return cell.getMovableCard() != null && cell.getMovableCard().player.getUserName().equals(castingPlayer.getUserName());
            else if (targetsType.charAt(3) == '1')
                return cell.getMovableCard() != null && !cell.getMovableCard().player.getUserName().equals(castingPlayer.getUserName());
        }
        if (targetsType.charAt(4) == '1') {
            if (targetsType.charAt(3) == '0')
                return cell.getMovableCard() instanceof MovableCard.Minion && cell.getMovableCard().player.getUserName().equals(castingPlayer.getUserName());
            else if (targetsType.charAt(3) == '1')
                return cell.getMovableCard() instanceof MovableCard.Minion && !cell.getMovableCard().player.getUserName().equals(castingPlayer.getUserName());
        }
        if (targetsType.charAt(8) == '1')
            return castingPlayer.findPlayerHero().cardCell.isTheseCellsAdjacent(cell);
        return false;
    }

    public void castCard(Match match, Cell cell, Player castingPlayer) {
        if (isCastingValid(castingPlayer, cell, primaryImpact))
            primaryImpact.doImpact();
        if (secondaryImpact != null && isCastingValid(castingPlayer, cell, secondaryImpact))
            secondaryImpact.doImpact();
    }

    //getters
    public Impact getPrimaryimpact() {
        return primaryImpact;
    }

    public Impact getSecondaryImpact() {
        return secondaryImpact;
    }



    //getters

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAreaTargetSquare(int[] areaTargetSquare) {
        this.areaTargetSquare = areaTargetSquare;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setPrimaryImpact(Impact primaryImpact) {
        this.primaryImpact = primaryImpact;
    }

    public void setSecondaryImpact(Impact secondaryImpact) {
        this.secondaryImpact = secondaryImpact;
    }

    //setters
}
