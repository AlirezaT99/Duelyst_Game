package model.Message;

public class Message {
    String authCode;

    public Message(String authCode) {
        this.authCode = authCode;
    }


    public String getAuthCode() {
        return authCode;
    }

    public String getUserName(){
        return authCode;
        //todo extract userName out of authCode
    }
}
