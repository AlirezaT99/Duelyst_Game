package model;

import javax.management.MalformedObjectNameException;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import java.util.ArrayList;

public class GameLogic {
    private Match match;

    public GameLogic(Match match) {
        this.match = match;
    }

    public void moveProcess(MovableCard movableCard , Cell cell){
        
    }
    public void attackProcess(MovableCard movableCard, Cell cell){

    }
    public void counterAttackProcess(ArrayList<MovableCard> movableCards,Cell cell){

    }
    public void insertProcess(Card card, Cell cell){

    }
    public boolean gameHasEnded(){
        return false;
    }
    public ArrayList<Cell> showAvailableDestinations(MovableCard movableCard){
        return new ArrayList<>();
    }

}
