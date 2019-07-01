package model.Message;

public class LoginBasedCommand extends Message {
    private String userName;
    private String password;
    private boolean login; //if false then --> create account
    private boolean success;

    public LoginBasedCommand(String userName, String password, boolean login) {
        super("");
        this.password = password;
        this.userName = userName;
        this.login = login;
    }

    public LoginBasedCommand(String userName, String password, boolean success, String authCode){
        super(authCode);
        this.userName = userName;
        this.password = password;
        this.success = success;
        this.authCode = authCode;
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
}
