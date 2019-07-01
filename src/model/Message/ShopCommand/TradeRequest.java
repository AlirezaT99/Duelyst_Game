package model.Message.ShopCommand;

public class TradeRequest extends TradeCommand {
    public TradeRequest(String authCode, boolean buy, String objectName) {
        super(authCode, buy, objectName);
    }
}
