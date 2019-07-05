package model.Message.ShopCommand;

import model.Message.Message;

public class UpdateShopObjectGifsRequest extends Message {
    private String objectName;
    public UpdateShopObjectGifsRequest(String authCode) {
        super(authCode);
    }
    public String getObjectName() {
        return objectName;
    }
}
