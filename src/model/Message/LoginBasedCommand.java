package model.Message;

import com.google.gson.Gson;
import model.Account;

public class LoginBasedCommand extends Message {
    private String userName;
    private String password;
    private boolean login; // if false then --> create account
    private boolean success;
    private int errorNumber = 0;
    String account = "";


    //request constructor
    public LoginBasedCommand(String userName, String password, boolean login) {
        super("");
        this.password = password;
        this.userName = userName;
        this.login = login;
    }

    //answer constructor
    public LoginBasedCommand(String userName, String password, boolean success, String authCode,boolean login,int errorNumber, Account account){
        super(authCode);
        this.login = login;
        this.userName = userName;
        this.password = password;
        this.success = success;
        this.authCode = authCode;
        this.errorNumber = errorNumber;
        Gson gson = new Gson();
        this.account = gson.toJson(account,Account.class);
    }

    public String getPassword() {
        return password;
    }

    public String getUserName(){
        return this.userName;
    }

    public boolean isLogin(){
        return login;
    }
    public boolean isCreateAccount(){
        return !isLogin();
    }

    public boolean wasRequestSuccessFull(){
        return success;
    }

    public String getAuthCode(){
        return authCode;
    }

    public Account getAccount() {
        return new Gson().fromJson(account,Account.class);
    }
}
