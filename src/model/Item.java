package model;

import java.util.Iterator;

public class Item {
    protected String name;
    protected String description;
    protected String itemID;
    Impact dyingWishImpact;
    Impact summonImpact;

    public Item copy(){
        Item item = new Item();
        return item;
    }

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

    public void setName(String name){this.name = name;}
    //setters
}
