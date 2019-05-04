package model;

import java.util.ArrayList;

public class Spell extends Card {
    private static ArrayList<Spell> spells = new ArrayList();
    private Impact impact;
    private Impact primaryImpact;
    private Impact secondaryImpact;


    @Override
    public String toString(boolean showCost) {
        String output = "Type : Spell - Name : " + name + " - MP : " + manaCost + " - Desc : " + description;
        if (showCost) output = output + " - Sell Cost : " + getCost();
        //output += "\n";
        return output;
    }

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
                return cell.getMovableCard() instanceof Minion && cell.getMovableCard().player.getUserName().equals(castingPlayer.getUserName());
            else if (targetsType.charAt(3) == '1')
                return cell.getMovableCard() instanceof Minion && !cell.getMovableCard().player.getUserName().equals(castingPlayer.getUserName());
        }
        if (targetsType.charAt(8) == '1')
            return castingPlayer.findPlayerHero().cardCell.isTheseCellsAdjacent(cell);
        return false;
    }

    public void castCard(Match match, Cell cell, Player castingPlayer) {
        if (isCastingValid(castingPlayer, cell, primaryImpact))
            primaryImpact.setImpactArea(castingPlayer, cell, cell);
        if (secondaryImpact != null && isCastingValid(castingPlayer, cell, secondaryImpact))
            secondaryImpact.setImpactArea(castingPlayer, cell, cell);
    }

    Spell copy(){
        Spell spell = new Spell();
        spell.cell = cell;
        setCardfieldsForCopy(spell);
        spell.primaryImpact = primaryImpact == null? null :primaryImpact.copy();
        spell.secondaryImpact =secondaryImpact  == null? null : secondaryImpact.copy();
        return spell;
    }



    //getters
    public Impact getPrimaryImpact() {
        return primaryImpact;
    }

    public Impact getSecondaryImpact() {
        return secondaryImpact;
    }

    public static Spell getSpellByName(String name) {
        for (int i = 0; i < spells.size(); i++) {
            if(spells.get(i).getName().equals(name))
                return spells.get(i);
        }
        return null;
    }
    //getters

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public void setPrimaryImpact(Impact primaryImpact) {
        this.primaryImpact = primaryImpact;
    }

    public void setSecondaryImpact(Impact secondaryImpact) {
        this.secondaryImpact = secondaryImpact;
    }

    public static void addToSpells(Spell spell) {
        spells.add(spell);
    }


    //setters
}
