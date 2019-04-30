package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private String name;
    private ArrayList<Card> cards;
    private ArrayList<UsableItem> items;
    private HashMap<String, Card> cardsHashMap;
    private HashMap<String, UsableItem> itemsHashMap;
    private Hero hero = null;
    public static final int MAX_CARD_NUMBER = 20;
    public static final int MAX_ITEM_NUMBER = 3;

    {
        cards = new ArrayList<>();
        cardsHashMap = new HashMap<>();
        itemsHashMap = new HashMap<>();
    }

    public String show(boolean showCost) {
        String output = "Heroes :\n" + "\t\t1 : " + hero.toString(false);
        output = output + "Items :\n";
        for (int i = 0; i < items.size(); i++)
            output = output + "\t\t" + (i + 1) + " : " + items.get(i).toString(false);
        output = output + "Cards :\n";
        for (int i = 0; i < cards.size(); i++)
            output = output + "\t\t" + (i + 1) + " : " + cards.get(i).toString(false);

        return output;
    }

    public Deck(String name) {
        this.name = name;
    }

    void putTheCardBackInTheQueue(Card card){
        cards.add(card);
        cardsHashMap.put(card.name,card);
    }

    Card getLastCard(){
        Card card = cards.get(0);
        cards.remove(card);
        cardsHashMap.remove(card);
        return card;
    }

    public static void createDeck(String deckName) {
        Deck deck = new Deck(deckName);
    } //shouldn't it be added to collection decks

    //getters

    public ArrayList<Card> getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<UsableItem> getItems() {
        return items;
    }

    public HashMap<String, Card> getCardsHashMap() {
        return cardsHashMap;
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
