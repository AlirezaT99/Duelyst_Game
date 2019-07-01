package model.Message;

public class GlobalChatMessage extends Message {
    private String message;
    public GlobalChatMessage(String message, String authCode) {
        super(authCode);
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public String getAccountName(){
        return authCode;
        //todo decrypt the authCode
    }
}
