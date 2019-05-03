package model;

public class InfluentialItem extends Item {
    private Impact impact;

    //getters

    public Impact getImpact() {
        return impact;
    }

    public String getItemID() {
        return itemID;
    }

    //getters

    //setters

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
    }


    //setters
}
