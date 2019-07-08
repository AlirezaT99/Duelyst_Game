package model.Message.ShopCommand.UpdateShop;

import javafx.scene.image.Image;
import model.Card;
import model.Item;
import model.Message.ShopCommand.ShopCommand;

import java.util.ArrayList;

public class UpdateCards extends ShopCommand {
    private String objectName;
    private int counter;
    private boolean getImage;
    private int cost;
    private int[] powers;
    private ArrayList<Image> images = new ArrayList<>();
    private int cardType;
    public UpdateCards(int cost , int[] powers,String objectName, String authCode,int counter, boolean getImage,int cardType) {
        super(authCode);
        this.objectName = objectName;
        this.counter = counter;
        this.getImage = getImage;
        this.cardType = cardType;
        this.cost = cost;
        this.powers = powers;
    }

    public String getObjectName() {
        return objectName;
    }

    public int getCount() {
        return counter;
    }

    public boolean isGetImage() {
        return getImage;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public int getCardType() {
        return cardType;
    }

    public int getCost() {
        return cost;
    }

    public int[] getPowers() {
        return powers;
    }
}
