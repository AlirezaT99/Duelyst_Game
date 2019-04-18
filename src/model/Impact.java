package model;

import org.json.*;
import com.google.*;

import java.util.ArrayList;

public class Impact {
    private String name;
    private ArrayList<Cell> impactArea;
    private boolean validEveryWhere;
    private boolean validOnEnemy;
    private boolean validOnTeammate;
    private boolean validOnHeroes;
    private boolean validOnMinions;
    private boolean isPassive;
    private int turnsActive;
    private boolean isHolyBuff;
    private boolean isPowerBuff;
    private boolean isPoisonBuff;
    private boolean isWeaknessBuff;
    private boolean isStunBuff;
    private boolean isDisarmBuff;
    //setters
    public Impact(boolean isValidOnEnemy){
        this.validOnEnemy = isValidOnEnemy;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setValidEveryWhere(boolean validEveryWhere) {
        this.validEveryWhere = validEveryWhere;
    }

    public void setValidOnEnemy(boolean validOnEnemy) {
        this.validOnEnemy = validOnEnemy;
    }

    public void setValidOnTeammate(boolean validOnTeammate) {
        this.validOnTeammate = validOnTeammate;
    }

    public void setValidOnHeroes(boolean validOnHeroes) {
        this.validOnHeroes = validOnHeroes;
    }

    public void setValidOnMinions(boolean validOnMinions) {
        this.validOnMinions = validOnMinions;
    }

    public void setPassive(boolean passive) {
        isPassive = passive;
    }

    public void setTurnsActive(int turnsActive) {
        this.turnsActive = turnsActive;
    }

    public void setHolyBuff(boolean holyBuff) {
        isHolyBuff = holyBuff;
    }

    public void setPowerBuff(boolean powerBuff) {
        isPowerBuff = powerBuff;
    }

    public void setPoisonBuff(boolean poisonBuff) {
        isPoisonBuff = poisonBuff;
    }

    public void setWeaknessBuff(boolean weaknessBuff) {
        isWeaknessBuff = weaknessBuff;
    }

    public void setStunBuff(boolean stunBuff) {
        isStunBuff = stunBuff;
    }

    public void setDisarmBuff(boolean disarmBuff) {
        isDisarmBuff = disarmBuff;
    }

    public void setImpactArea(Match match) {
        if (validEveryWhere) {
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 5; j++)
                    impactArea.add(match.getTable().getCellByCoordination(new Coordination(i, j)));
            return;
        }

    }

}
