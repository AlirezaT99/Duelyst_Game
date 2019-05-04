package model;

import java.util.ArrayList;

public class UsableItem extends InfluentialItem {
    private static ArrayList<UsableItem> usableItems = new ArrayList<>();
    private int cost;
    private Deck deck;
    private Match match;

    @Override
    UsableItem copy(){
        UsableItem item;
        item = (UsableItem) super.copy();
        item.primaryImpact = primaryImpact.copy();
        item.secondaryImpact = secondaryImpact.copy();
        item.cost = cost;
        item.deck = deck;
        item.match = match;
        return item;
    }

    public String toString(boolean showCost) {
        String output = "Name : " + name + " - Desc :" + description;
        if (showCost) output = output + " - Sell Cost : " + getCost();
        //output = output + "\n";
        return output;
    }

    // setter & getter
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public Deck getDeck() {
        return deck;
    }

    public Match getMatch() {
        return match;
    }

    public static UsableItem getUsableItemByName(String name){
        for(int i = 0; i < usableItems.size(); i++)
            if(usableItems.get(i).getName().equals(name))
                return usableItems.get(i);
        return null;
    }

    public static void addToUsableItems(UsableItem usableItem){ usableItems.add(usableItem);}
    // setter & getter
}
