package model.Message.ShopCommand;

import model.Card;
import model.Item;

public class TradeResponse extends TradeCommand {
    private Card card = null;
    private Item item = null;
    private boolean successFullTrade;
    private String alertMessage  = "";

    public TradeResponse(String authCode, boolean buy, String objectName,Item item,boolean successFullTrade, String alertMessage) {
        super(authCode, buy, objectName);
        this.item = item;
        this.successFullTrade = successFullTrade;
        this.alertMessage = alertMessage;
    }

    public TradeResponse(String authCode, boolean buy, String objectName, Card card,boolean successFullTrade, String alertMessage) {
        super(authCode, buy, objectName);
        this.card =card;
        this.successFullTrade = successFullTrade;
        this.alertMessage = alertMessage;
    }

    public Card getCard() {
        return card;
    }

    public Item getItem() {
        return item;
    }

    public boolean isSuccessFullTrade() {
        return successFullTrade;
    }

    public String getAlertMessage() {
        return alertMessage;
    }
}
