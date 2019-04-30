package model;

interface toStr {
    String toString(boolean showCost);
}

public class Card implements toStr {
    protected int manaCost;
    protected String name;
    private Cell cell;
    private int cost;
    private Match match;
    protected Player player;
    private String cardID;
    protected String description;

    boolean isManaSufficient(int playerMana) {
        return playerMana >= manaCost;
    }

    public boolean isCoordinationValid() {
        //todo
        return true;
    }

    void castCard(Match match, Cell cell, Player castingPlayer) {

    }

    public String toString(boolean showCost) {
        return "don't care";
        /* we need to call "toString" method in Hero and Minion from the Cards ArrayList
         * and it's impossible to instantiate an abstract class so it had to implement the interface
         * but the function will be called in the subClasses, not this...
         */
    }

    //getters

    public String getName() {
        return name;
    }

    public int getCost() {
        return this.cost;
    }

    public Match getMatch() {
        return match;
    }

    public Coordination getCoordination() {
        return cell.getCellCoordination();
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getCardID() {
        return cardID;
    }

    public String getDescription() {
        return description;
    }
    //getters

    //setters

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public void setCoordination(Coordination coordination) {
        this.cell.setCellCoordination(coordination);
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //setters

}