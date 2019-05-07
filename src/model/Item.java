package model;

import java.util.Iterator;

public class Item {
    protected String name;
    protected String description;
    protected String itemID;
    protected String collectionID;
    Impact dyingWishImpact;
    Impact summonImpact;
    Player player;

    public Item copy(){
        Item item = new Item();
        item.name = name;
        item.description = description;
        item.itemID = itemID;
        if (dyingWishImpact != null)
            item.dyingWishImpact = dyingWishImpact.copy();
        if (summonImpact != null)
            item.summonImpact = summonImpact.copy();
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

    public String getCollectionID() {return collectionID;}

    //getters

    //setters

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCollectionID(String collectionID) {
        this.collectionID = collectionID;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    //setters
}
