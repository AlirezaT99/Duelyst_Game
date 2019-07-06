package model.Message.ShopCommand.UpdateShop;

import javafx.scene.image.Image;
import model.Card;
import model.Item;
import model.Message.ShopCommand.ShopCommand;

import java.util.ArrayList;

public class UpdateCards extends ShopCommand {
    private Card card = null;
    private Item item = null;
    private boolean inShop = true;
    private boolean inCollection = false;
    private int counter;
    ArrayList<Image> images = new ArrayList<>();
    public UpdateCards(String message, String authCode, Card card, int counter,boolean isInShop) {
        super(authCode);
        this.counter = counter;
        this.card = card;
        this.inShop = isInShop;
        this.inCollection = !isInShop;
    }

    public UpdateCards(String message, String authCode, Item item, int counter,boolean isInShop) {
        super(authCode);
        this.item = item;
        this.counter = counter;
        this.inShop = isInShop;
        this.inCollection = !isInShop;
    }

    public UpdateCards(String message, String authCode, ArrayList<Image> images) {
        super(authCode);
        this.images = images;
    }

    public Card getCard() {
        return card;
    }

    public Item getItem() {
        return item;
    }

    public int getCounter() {
        return counter;
    }

    public ArrayList<Image> getImages() {
        return images;
    }


}
