package model;

public class InfluentialItem extends Item {
    protected Impact primaryImpact;
    protected Impact secondaryImpact;


    //getters


    public String getItemID() {
        return itemID;
    }

    //getters

    //setters

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setPrimaryImpact(Impact impact) {
        this.primaryImpact = impact;
    }
    public void setSecondaryImpact(Impact impact){this.secondaryImpact = impact;}

    //setters
}
