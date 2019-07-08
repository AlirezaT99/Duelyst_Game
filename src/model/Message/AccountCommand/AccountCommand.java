package model.Message.AccountCommand;

import model.Message.Message;

public class AccountCommand extends Message {
    private String authCode;
    public AccountCommand(String authCode){
        super(authCode);
        this.authCode = authCode;
    }
}
