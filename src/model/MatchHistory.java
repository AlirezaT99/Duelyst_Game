package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MatchHistory {
    private String opponetName;
    private boolean result;
    private LocalDateTime now;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public void setMatchHistory(Player player, Match match, boolean result){
        now = LocalDateTime.now();
        this.result = result;
        if(match.getAILevel()!=0)
            opponetName = match.getAILevel().toString();
        else
            if(match.getPlayer1().equals(player))
                opponetName = match.getPlayer2().getUserName();
            else
                opponetName = match.getPlayer1().getUserName();
        player.getAccount().getMatchHistory().add(0,this);
    }
}
