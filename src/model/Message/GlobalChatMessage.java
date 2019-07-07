package model.Message;

import java.util.ArrayList;

public class GlobalChatMessage extends Message {
    private String message;
    private ArrayList<String> chatMessages = new ArrayList<>();
    public GlobalChatMessage(String message, String authCode) {
        super(authCode);
        this.message = message;
        this.chatMessages = new ArrayList<>();
    }

    public GlobalChatMessage(ArrayList<String> chatMessages, String authCode){
        super(authCode);
        this.chatMessages = chatMessages;
    }

    public ArrayList<String> getChatMessages() {
        return chatMessages;
    }

    public String getMessage(){
        return this.message;
    }

    public String getAccountName(){
        return authCode;
        //todo decrypt the authCode
    }

}
