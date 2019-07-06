package model.Message;

import java.io.Serializable;

public class Message implements Serializable {
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

//    @Override
//    public String toString(){
//
//    }

}
