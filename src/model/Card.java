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

    void castCard(int x, int y ){

    }

    //getters
    public int getCost() {
        return cost;
    }

    public Match getMatch() {
        return match;
    }

    public Impact getImpact() {
        return impact;
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


    public void setImpact(Impact impact) {
        this.impact = impact;
    }


    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
    //setters

}