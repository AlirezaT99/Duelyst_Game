package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MatchHistory {
    private String opponentName;
    private boolean result;
    private LocalDateTime now;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public void setMatchHistory(Player player, Match match, boolean result){
        now = LocalDateTime.now();
        this.result = result;
        if(match.getAILevel()!=0)
            opponentName = match.getAILevel().toString();
        else
            if(match.getPlayer1().equals(player))
                opponentName = match.getPlayer2().getUserName();
            else
                opponentName = match.getPlayer1().getUserName();
        player.getAccount().getMatchHistory().add(0,this);
    }
}
