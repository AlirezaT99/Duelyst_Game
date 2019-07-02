package model;

import org.omg.CORBA.IMP_LIMIT;

import java.util.ArrayList;

public class Spell extends Card {
    private static ArrayList<Spell> spells = new ArrayList<>();
    private Impact primaryImpact;
    private Impact secondaryImpact;

    public Spell(Impact primaryImpact, Impact secondaryImpact) {
        this.primaryImpact = primaryImpact;
        this.secondaryImpact = secondaryImpact;
    }

    @Override
    public String toString(boolean showCost) {
        String output = "Type : Spell - Name : " + name + " - MP : " + manaCost + " - Desc : " + description;
        if (showCost) output = output + " - Sell Cost : " + getCost();
        //output += "\n";
        return output;
    }

    public boolean isCastingValid(Player castingPlayer, Cell cell, Impact impact) {
        String targetsType = impact.getTargetTypeId();
        ArrayList<Cell> cells = primaryImpact.getImpactAreaClass().getValidCells(castingPlayer);
        for (Cell celll: cells
             ) {
        System.out.println("--------------------------");
            if(cell.getCellCoordination().getY() == celll.getCellCoordination().getY() && cell.getCellCoordination().getX() == celll.getCellCoordination().getX())
                return true;
        }
        System.out.println("***********************");
        return false;
//        if (targetsType.charAt(0) == '1')
//            return true;
//        if (targetsType.charAt(1) == '0')
//            return true;
//        if (targetsType.charAt(2) == '1')
//            return true;
//        if (targetsType.charAt(3) == '2')
//            return true;
//        if (targetsType.charAt(4) == '0')
//            return true;
//        if (targetsType.charAt(4) == '2') {
//            if (targetsType.charAt(3) == '0')
//                return cell.getMovableCard() != null && cell.getMovableCard().player.getUserName().equals(castingPlayer.getUserName());
//            else if (targetsType.charAt(3) == '1')
//                return cell.getMovableCard() != null && !cell.getMovableCard().player.getUserName().equals(castingPlayer.getUserName());
//        }
//        if (targetsType.charAt(4) == '1') {
//            if (targetsType.charAt(3) == '0')
//                return cell.getMovableCard() instanceof Minion && cell.getMovableCard().player.getUserName().equals(castingPlayer.getUserName());
//            else if (targetsType.charAt(3) == '1')
//                return cell.getMovableCard() instanceof Minion && !cell.getMovableCard().player.getUserName().equals(castingPlayer.getUserName());
//        }
//        if (targetsType.charAt(8) == '1')
//            return castingPlayer.findPlayerHero().cardCell.isTheseCellsAdjacent(cell);
//        return false;
    }

    public void castCard(Cell cell) {
        if (isCastingValid(this.player, cell, primaryImpact))
            primaryImpact.doImpact(this.player,cell.getMovableCard(),cell,cell);
        if (secondaryImpact != null )
            secondaryImpact.doImpact(this.player,cell.getMovableCard(),cell,cell);
        player.getHand().removeCardFromHand(this);
    }

    public Spell copy(){
        Impact primaryImpactCopy = primaryImpact == null?null:primaryImpact.copy();
        Impact secondaryImpactCopy = secondaryImpact == null? null:secondaryImpact.copy();
        Spell spell = new Spell(primaryImpactCopy,secondaryImpactCopy);
        spell.cell = cell;
        setCardFieldsForCopy(spell);
        spell.collectionID = this.collectionID;
        spell.manaCost = manaCost;
        spell.name = name;
        spell.cell = cell;
        spell.cost = cost;
        spell.match = match;
        spell.player = player;
        spell.cardID = cardID;
        spell.description = description;
        return spell;
    }

    public ArrayList<Cell> getValidCoordination(){

        return primaryImpact.getImpactAreaClass().getValidCells(player);
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
                return spells.get(i).copy();
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
