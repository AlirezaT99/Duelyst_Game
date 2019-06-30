package model.Message.ShopCommand;

public class TradeCommand extends ShopCommand {
    private boolean buy;
    private boolean sell;
    private String objectName;

    public TradeCommand(String authCode,boolean buy,String objectName) {
        super(authCode);
        this.buy = buy;
        this.sell = !buy;
        this.objectName = objectName;
    }
}

