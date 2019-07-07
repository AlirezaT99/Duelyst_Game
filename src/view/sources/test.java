package view.sources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Hero;
import model.Shop;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) throws IOException {
        presenter.MainProcess.readFiles();
        ArrayList<Hero> Heros = new ArrayList<>(Shop.getShopHeroes());
        Gson gson = new GsonBuilder().create();
        String heroes = gson.toJson(Heros);
        Type type = new  TypeToken<ArrayList<Hero>>(){}.getType();
        ArrayList<Hero> wtf = gson.fromJson(heroes,type);
        for (Hero Hero : wtf) {
            System.out.println(Hero.getName());
        }
    }
}
