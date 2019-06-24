package model;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ImportBasedDeck {
    private Gson gson = new Gson();
    private String gsonString = "";
    private Deck deck;
    private String deckName = "";

    public ImportBasedDeck(Deck deck){
        gsonString = gson.toJson(deck);
        deckName = deck.getName();
    }

    public Deck importDeck() {
        return gson.fromJson(gsonString, Deck.class);
    }

    public boolean isThisDeckValidToExport(Deck deck, Account account) {
        if (!doesItHaveTheHero(deck.getHero().getName(), account.getCollection().getHeroes()))
            return false;
        if (!doesItHaveTheItems(deck.getItems().get(0), account.getCollection().getItems()))
            return false;
        ArrayList<Card> cards = new ArrayList<>(account.getCollection().getSpells());
        cards.addAll(account.getCollection().getHeroes());
        cards.addAll(account.getCollection().getMinions());
        return doesItHaveTheCards(deck.getCards(), cards);
    }


    //validate Deck

    private boolean doesItHaveTheHero(String heroName, ArrayList<Hero> heroes) {
        for (Hero hero : heroes) {
            if (hero.getName().equalsIgnoreCase(heroName))
                return true;
        }
        return false;
    }

    private boolean doesItHaveTheItems(UsableItem usableItem, ArrayList<UsableItem> items) {
        if (usableItem == null)
            return true;
        for (UsableItem item : items) {
            if (item.name.equalsIgnoreCase(usableItem.name))
                return true;
        }
        return false;
    }

    private boolean doesItHaveTheCards(ArrayList<Card> deckCards, ArrayList<Card> collectionCards) {
        for (int i = 0; i < deckCards.size(); i++) {
            String card = deckCards.get(i).name;
            int counter = 0;
            for (int j = i; j < deckCards.size(); j++)
                if (deckCards.get(j).name.equalsIgnoreCase(card))
                    counter++;

            int counter2 = 0;
            for (Card collectionCard : collectionCards)
                if (collectionCard.name.equalsIgnoreCase(card))
                    counter2++;
            if (counter > counter2)
                return false;
        }
        return true;
    } //not so efficient and pretty

    //validate Deck
}
