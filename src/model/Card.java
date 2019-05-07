package model;

public abstract class Card {
    protected int manaCost;
    protected String name = "";
    protected Cell cell;
    protected int cost;
    protected Match match;
    protected Player player;
    protected String cardID = "";
    protected String collectionID = "";
    protected String description = "";

    void setCardFieldsForCopy(Card card) {
        card.cost = cost;
        card.match = match;
        card.player = player;
        card.cardID = cardID + "*";
        card.description = description;
        card.manaCost = manaCost;
        card.name = name;
    }

    public boolean isManaSufficient(int playerMana) {
        return playerMana >= manaCost;
    }

    public boolean isCastingCoordinationValid(Cell cell) {
        //todo
        return true;
    }

    public void castCard(Cell cell) {
    }

    public abstract String toString(boolean showCost);

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

    public String getCollectionID() {
        return collectionID;
    }

    public Player getPlayer() {
        return player;
    }

    //getters

    //setters

    public void setCardCollectionID(String cardID) {
        this.collectionID = cardID;
    }

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

    public void setPlayer(Player player) {
        this.player = player;
    }

    //setters

}