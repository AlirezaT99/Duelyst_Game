package model.Message.ShopCommand.UpdateShop;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.text.Style;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class UpdateWholeShop {


    private String cards = "";
    private String collectionCards = "";
    private String movableCardsPowers = "";
    private String costs = "";
    private String cardNumbers= "";



    public UpdateWholeShop(ArrayList<ArrayList<Object>> cards, ArrayList<ArrayList<Object>> collectionCards, HashMap<String , int[]> movableCardsPowers, HashMap<String , Integer> costs, HashMap<String ,Integer> cardsNumbers) {
        Gson gson = new Gson();
        this.cards = gson.toJson(cards);
        this.collectionCards = gson.toJson(collectionCards);
        this.movableCardsPowers = gson.toJson(movableCardsPowers);
        this.costs = gson.toJson(costs);
        this.cardNumbers = gson.toJson(cardsNumbers);
    }

    private ArrayList<String > getSomeCard(boolean collection, int index){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ArrayList<String >>>(){}.getType();
        String wantedCards = collection? collectionCards:cards;
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

}
