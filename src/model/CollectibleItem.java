package model;

import java.util.ArrayList;

public class CollectibleItem extends InfluentialItem {
    private static ArrayList<CollectibleItem> collectibleItems = new ArrayList<>();
    private Cell cell;
    private Match match;
    private Player player;

    CollectibleItem copy(){
        CollectibleItem collectibleItem;
        collectibleItem = (CollectibleItem) super.copy();
        collectibleItem.cell = cell;
        collectibleItem.match = match;
        collectibleItem.player = player;
        collectibleItem.primaryImpact = primaryImpact.copy();
        collectibleItem.secondaryImpact = secondaryImpact.copy();
        return collectibleItem;
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
