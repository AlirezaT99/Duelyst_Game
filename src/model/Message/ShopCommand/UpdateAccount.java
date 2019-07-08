package model.Message.ShopCommand;

import com.google.gson.Gson;
import model.Account;
import model.Message.Message;

public class UpdateAccount extends Message {
    String account;
    boolean request;
    public UpdateAccount(String authCode , Account account, boolean request) {
        super(authCode);
        Gson gson = new Gson();
        this.request = request;
        this.account = gson.toJson(account);
    }

    public Account getAccount() {
        return new Gson().fromJson(account,Account.class);
    }
}
