package model.Message.ShopCommand.UpdateShop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Card;
import model.Message.ShopCommand.ShopCommand;
import org.omg.CORBA.INTERNAL;

import javax.swing.text.Style;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UpdateWholeShop extends ShopCommand {

    private String cards;
    private String collectionCards;
    private String movableCardsPowers;
    private String costs;
    private String cardNumbers;
    private String cardCollectionNumbers;
    private String costumeCards;
    private long money;

    public UpdateWholeShop(HashMap<String , Integer> collctionCardNumbers,long money,HashSet<String > costumeCards,ArrayList<ArrayList<String >> cards, ArrayList<ArrayList<String >> collectionCards, HashMap<String , int[]> movableCardsPowers, HashMap<String , Integer> costs, HashMap<String ,Integer> cardsNumbers) {
        super("");
        this.money = money;
        Gson gson = new GsonBuilder().create();
        this.cards = gson.toJson(cards);
        this.collectionCards = gson.toJson(collectionCards);
        this.movableCardsPowers = gson.toJson(movableCardsPowers);
        this.costs = gson.toJson(costs);
        this.cardNumbers = gson.toJson(cardsNumbers);
        this.costumeCards = gson.toJson(costumeCards);
        this.cardCollectionNumbers = gson.toJson(collctionCardNumbers);
    }

    private ArrayList<String > getSomeCard(boolean collection, int index){
        Gson gson = new GsonBuilder().create();
        Type type = new TypeToken<ArrayList<ArrayList<String >>>(){}.getType();
        String wantedCards;
        if(collection)
            wantedCards = collectionCards;
        else
            wantedCards = cards;
        ArrayList< ArrayList<String>> cards2 =  gson.fromJson(wantedCards,type);
        return cards2.get(index);
    }
    public HashMap<String , Integer> getCosts(){
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String ,Integer>>(){}.getType();
        return gson.fromJson(costs,type);
    }

    public HashMap<String , Integer> getNumbers(){
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String ,Integer>>(){}.getType();
        return gson.fromJson(cardNumbers,type);
    }

    public HashMap<String , Integer> getCollectionNumbers(){
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String , Integer>>(){}.getType();
        return gson.fromJson(cardCollectionNumbers,type);
    }

    public HashMap<String , int[]> getPowers(){
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String , int[]>>(){}.getType();
        return gson.fromJson(movableCardsPowers,type);

    }

    public ArrayList<String > getHeroes(){
       return getSomeCard(false,0);
    }
    public ArrayList<String > getCollectionHeroes(){
        return getSomeCard(true, 0);
    }
    public ArrayList<String > getMinions(){
        return getSomeCard(false,1);
    }
    public ArrayList<String > getCollectionMinions(){
        return getSomeCard(true,1);
    }
    public ArrayList<String > getSpells(){
        return getSomeCard(false,2);
    }
    public ArrayList<String > getCollectionSpells(){
        return getSomeCard(true, 2);
    }
    public ArrayList<String > getItems(){
        return getSomeCard(false,3);
    }
    public ArrayList<String > getCollectionItems(){
        return getSomeCard(true,3);
    }

    public HashSet<String> getCostumes() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashSet<String >>(){}.getType();
        return gson.fromJson(costumeCards,type);
    }

    public long getMoney() {
        return money;
    }
}
