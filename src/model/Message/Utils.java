package model.Message;

import model.Account;

public class Utils extends Message {
    private String authCode;
    private boolean logout;
    private String account;

    public Utils(String authCode, boolean logout) {
        super("");
        this.authCode = authCode;
        this.logout = logout; // if false ---> get account
    }

    public String getAccount(){ return account;}

    public void setAccount(String account){ this.account = account;}

    public boolean isLogout() {
        return logout;
    }

    public String getAuthCode() {
        return authCode;
    }

}
