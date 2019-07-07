package model.Message.ScoreBoardCommand;

import model.Account;
import model.Message.Message;
import model.Server.Server;
import presenter.LoginMenuProcess;
import sun.font.CompositeGlyphMapper;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class ScoreBoardCommand extends Message {
    private String authCode;
    private boolean ranking;
    private boolean onlineStatus;
    private boolean numberOfWins;
    private ArrayList<String> sortedUsers = new ArrayList<>();
    private ArrayList<Boolean> onlineStusOfUsers = new ArrayList<>();
    private ArrayList<Integer> numberOfWinsOfUsers = new ArrayList<>();

    public ScoreBoardCommand(String authCode, boolean ranking, boolean online, boolean numberOfWins) {
        super("");
        this.authCode = authCode;
        this.ranking = ranking;
        this.onlineStatus = online;
        this.numberOfWins = numberOfWins;
        sortedUsers = new ArrayList<>();
        onlineStusOfUsers = new ArrayList<>();
        numberOfWinsOfUsers = new ArrayList<>();
    }

    public ScoreBoardCommand(String authCode, ArrayList<String> sortedUsers, ArrayList<Boolean> onlineStusOfUsers, ArrayList<Integer> numberOfWinsOfUsers){
        super(authCode);
        this.sortedUsers = sortedUsers;
        this.onlineStusOfUsers = onlineStusOfUsers;
        this.numberOfWinsOfUsers = numberOfWinsOfUsers;
    }


  //  public ScoreBoardCommand()


    public ArrayList<String> getSortedUsers() {
        return sortedUsers;
    }

    public ArrayList<Boolean> getOnlineStusOfUsers() {
        return onlineStusOfUsers;
    }

    public ArrayList<Integer> getNumberOfWinsOfUsers() {
        return numberOfWinsOfUsers;
    }

    public boolean getRanking() {
        return this.ranking;
    }

    public String getAuthCode() {
        return authCode;
    }
}
