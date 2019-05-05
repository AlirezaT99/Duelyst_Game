package model;

import java.util.ArrayList;

public class CollectibleItem extends InfluentialItem {
    private static ArrayList<CollectibleItem> collectibleItems = new ArrayList<>();
    private Cell cell;
    private Match match;
    private Player player;

   public CollectibleItem copy(){
        CollectibleItem item = new CollectibleItem();
       item.name = name;
       item.description = description;
       item.itemID = itemID;
       if (dyingWishImpact != null)
           item.dyingWishImpact = dyingWishImpact.copy();
       if (summonImpact != null)
           item.summonImpact = summonImpact.copy();
        item.cell = cell;
       item.match = match;
        item.player = player;
        item.primaryImpact = primaryImpact.copy();
        item.secondaryImpact = secondaryImpact.copy();
        return item;
    }

    //getters

    public Cell getCell() {
        return cell;
    }

    public Match getMatch() {
        return match;
    }

    public Player getPlayer() {
        return player;
    }

    //getters

    //setters
    public void setMatch(Match match) {
        this.match = match;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static void addToCollectibleItems(CollectibleItem collectibleItem){ collectibleItems.add(collectibleItem);}

    //setters
}
