package model.Message;

public class Utils extends Message {
    private String authCode;
    private boolean logout;

    public Utils(String authCode, boolean logout) {
        super("");
        this.authCode = authCode;
        this.logout = logout;
    }

    public boolean isLogout() {
        return logout;
    }
    public String getAuthCode(){
        return authCode;
    }

}
