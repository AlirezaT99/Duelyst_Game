package model;

class Card {
    protected int manaCost;
    protected String name;
    private Cell cell;
    protected int cost;
    private Match match;
    protected Player player;
    private String cardID;
    private String description;

    public String getName() {
        return name;
    }

    boolean isManaSufficient(int playerMana) {
        if (playerMana >= manaCost)
            return true;
        return false;
    }

    public boolean isCoordinationValid() {
        //todo
        return true;
    }

    void castCard(int x, int y) {

    }

    //getters
    public int getCost() {
        return cost;
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