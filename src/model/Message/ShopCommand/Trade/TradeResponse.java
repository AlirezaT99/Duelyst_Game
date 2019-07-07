package model.Message.ShopCommand.Trade;

import model.Card;
import model.Item;

public class TradeResponse extends TradeCommand {
    private long cost;
    private boolean successFullTrade;
    private int alertMessage;



    public TradeResponse(String authCode, boolean buy, String objectName,boolean successFullTrade, int alertMessage,long cost) {
        super(authCode, buy, objectName);
        this.successFullTrade = successFullTrade;
        this.alertMessage = alertMessage;
        this.cost = cost;
    }

    public long getCost() {
        return cost;
    }

    public boolean isSuccessFullTrade() {
        return successFullTrade;
    }

    public int getAlertMessage() {
        return alertMessage;
    }
}
