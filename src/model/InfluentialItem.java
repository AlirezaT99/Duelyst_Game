package model;

import java.util.ArrayList;

public class InfluentialItem extends Item {
    private static ArrayList<UsableItem> usableItems = new ArrayList<>();
    private static ArrayList<CollectibleItem> collectibleItems = new ArrayList<>();
    private Impact primaryImpact;
    private Impact secondaryImpact;

    public Impact getPrimaryImpact() {
        return primaryImpact;
    }

    public void setPrimaryImpact(Impact impact) {
        this.primaryImpact = impact;
    }

    public void setSecondaryImpact(Impact impact) {
        this.secondaryImpact = impact;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public static void addToUsableItems(UsableItem usableItem){ usableItems.add(usableItem);}

    public static void addToCollectibleItems(CollectibleItem collectibleItem){ collectibleItems.add(collectibleItem);}

}
