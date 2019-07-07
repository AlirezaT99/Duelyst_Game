package model.Message.ScoreBoardCommand;

import model.Message.Message;

import java.util.ArrayList;

public class ScoreBoardCommand extends Message {
    private String authCode;
    private boolean ranking;
    private boolean onlineStatus;
    private boolean numberOfWins;
    private ArrayList<String> sortedUsers;
    private ArrayList<Boolean> onlineStatusOfUsers;
    private ArrayList<Integer> numberOfWinsOfUsers;

    public ScoreBoardCommand(String authCode, boolean ranking, boolean online, boolean numberOfWins) {
        super("");
        this.authCode = authCode;
        this.ranking = ranking;
        this.onlineStatus = online;
        this.numberOfWins = numberOfWins;
        sortedUsers = new ArrayList<>();
        onlineStatusOfUsers = new ArrayList<>();
        numberOfWinsOfUsers = new ArrayList<>();
    }

    public ScoreBoardCommand(String authCode, ArrayList<String> sortedUsers, ArrayList<Boolean> onlineStusOfUsers, ArrayList<Integer> numberOfWinsOfUsers){
        super(authCode);
        this.sortedUsers = sortedUsers;
        this.onlineStatusOfUsers = onlineStusOfUsers;
        this.numberOfWinsOfUsers = numberOfWinsOfUsers;
    }


  //  public ScoreBoardCommand()


    public ArrayList<String> getSortedUsers() {
        return sortedUsers;
    }

    public ArrayList<Boolean> getOnlineStatusOfUsers() {
        return onlineStatusOfUsers;
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
