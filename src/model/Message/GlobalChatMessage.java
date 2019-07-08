package model.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GlobalChatMessage extends Message {
    private String message;
    private String chatMessagesJson;
    private boolean isUpdate;

    public GlobalChatMessage(String message, String authCode) {
        super(authCode);
        this.message = message;
        this.isUpdate = false;
    }

    public GlobalChatMessage(boolean isUpdate, String authCode) {
        super(authCode);
        this.isUpdate = isUpdate;
    }

    public GlobalChatMessage(ArrayList<String> chatMessages, String authCode) {
        super(authCode);
        Gson gson = new Gson();
        this.isUpdate = true;
        this.chatMessagesJson = gson.toJson(chatMessages);
    }

    public ArrayList<String> getChatMessages() {
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(chatMessagesJson, type);
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public String getMessage() {
        return this.message;
    }

    public String getAccountName() {
        return authCode;
        //todo decrypt the authCode
    }

}
