package model;

public class Item {
    protected String name;
    protected String description;
    protected String itemID;
    Impact dyingWishImpact;
    Impact summonImpact;
    Impact impact;
    //getters

    public String getName() {
        return name;
    }

    public String getItemID() {
        return itemID;
    }

    public String getDescription() {
        return description;
    }

    //getters

    //setters

    public void setDescription(String description) {
        this.description = description;
    }


    //setters
}
