package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private String name;
    private ArrayList<UsableItem> items;
    private ArrayList<Minion> minions;
    private ArrayList<Spell> spells;
    private HashMap<String, UsableItem> itemsHashMap;
    private Hero hero = null;
    public static final int MAX_CARD_NUMBER = 20;
    public static final int MAX_ITEM_NUMBER = 3;

    {
        itemsHashMap = new HashMap<>();
        minions = new ArrayList<>();
        spells = new ArrayList<>();
        items = new ArrayList<>();
    }

    public String show(boolean showCost) {
        String output = "";
        if (hero != null)
            output += "Heroes :\n" + "\t\t1 : " + hero.toString(false) + "\n";
        else
            output += "Heroes :\n";
        output += "Items :\n";
        for (int i = 0; i < items.size(); i++)
            output = output + "\t\t" + (i + 1) + " : " + items.get(i).toString(false) + "\n";
        output = output + "Cards :\n";
        for (int i = 0; i < spells.size(); i++)
            output = output + "\t\t" + (i + 1) + " : " + spells.get(i).toString(false) + "\n";
        for (int i = 0; i < minions.size(); i++)
            output = output + "\t\t" + (i + 1 + spells.size()) + " : " + minions.get(i).toString(false) + "\n";
        return output;
    }

    public Deck(String name) {
        this.name = name;
    }

    void putTheCardBackInTheQueue(Card card) {
        if (card instanceof Spell)
            spells.add((Spell) card);
        if (card instanceof Minion)
            minions.add((Minion) card);
        if (card instanceof Hero)
            setHero((Hero) card);
        //  cardsHashMap.put(card.name,card);
    }

    Card getLastCard() {
//        Card card = cards.get(0);
//        cards.remove(card);
//        cardsHashMap.remove(card);
        return new Minion(); // bullshit
    } //todo

    public static void createDeck(String deckName) {
        Deck deck = new Deck(deckName);
    } //shouldn't it be added to collection decks

    public Card findCardByID(String id) {
        if (hero != null && hero.getCardID().equals(id))
            return hero;
        for (int i = 0; i < minions.size(); i++)
            if (minions.get(i).getCardID().equals(id))
                return minions.get(i);
        for (int i = 0; i < spells.size(); i++)
            if (spells.get(i).getCardID().equals(id))
                return spells.get(i);
        return null;
    }
    //getters

    public String getName() {
        return name;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<UsableItem> getItems() {
        return items;
    }

    public ArrayList<Minion> getMinions() {
        return minions;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public HashMap<String, UsableItem> getItemsHashMap() {
        return itemsHashMap;
    }
    //getters

    //setters
    public void setHero(Hero hero) {
        this.hero = hero;
    }
    //setters
}
