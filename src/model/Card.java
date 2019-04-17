package model;

class Card {
    private int manaCost;
    protected String name;
    private Cell cell;
    private Impact impact;
    private int cost;
    private Match match;
    protected Player player;
    private String cardID;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public Coordination getCoordination() {
        return cell.getCellCoordination();
    }

    public void setCoordination(Coordination coordination) {
        this.cell.setCellCoordination(coordination);
    }

    public Impact getImpact() {
        return impact;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getName() {
        return name;
    }

    boolean isManaSufficient(int playerMana) {
        if (playerMana >= manaCost)
            return true;
        return false;
    }

    boolean isCoordinationValid() {
        //todo
        return true;
    }


}