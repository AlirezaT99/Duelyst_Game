package model.Message.BattleCommand;

import model.Account;
import model.Match;

public class BattleRequest extends BattleCommand {
    private String match;
    private int mode;
    private int numberOfFlags;
    private String firstPlayerAccount;
    private String secondPlayerAccount;
    private boolean isMatch = false;

    public BattleRequest(String authCode, int mode, int numberOfFlags)
    {
        super(authCode);
        this.mode = mode;
        this.numberOfFlags = numberOfFlags;
        isMatch =false;
    }

    public BattleRequest(String authCode, String match){
        super(authCode);
        this.match = match;
        isMatch = true;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }


    public boolean isMatch() {
        return isMatch;
    }

    public void setMatch(boolean match) {
        isMatch = match;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public void setNumberOfFlags(int numberOfFlags) {
        this.numberOfFlags = numberOfFlags;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }
}
