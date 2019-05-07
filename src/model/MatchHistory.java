package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MatchHistory {
    private String opponentName;
    private boolean result;
    private LocalDateTime now;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private String timeOfNow;

    public void setMatchHistory(Player player, Match match, boolean result){
        now = LocalDateTime.now();
        this.result = result;
        if (result)
            player.getAccount().increaseNumberOfWins();
        if(match.getAILevel()!=0)
            opponentName = match.getAILevel().toString();
        else
            if(match.getPlayer1().equals(player))
                opponentName = match.getPlayer2().getUserName();
            else
                opponentName = match.getPlayer1().getUserName();
        now = LocalDateTime.now();
        timeOfNow = dtf.format(now);
        player.getAccount().getMatchHistory().add(0,this);
    }
}
