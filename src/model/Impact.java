package model;

import java.util.ArrayList;


public class Impact {
    private ImpactEffectComp impactEffectComp;

    private ArrayList<Cell> impactArea = new ArrayList<>();
    private Match match;
    private ImpactArea impactAreaClass;
    private String impactTypeComp = "";
    private String impactWayOfAssigning = "";
    private String targetTypeId = "";
    private String impactTypeId = "";
    // 0.(0,1)isPositive
    // 1.(0-6)buffType{holy,power,poison,weakness,stun,disarm}
    // 2.(0-3)QuantityChange{mana,health,damage}
    // 3.(0,1)quantityChangeSign{negative/isPositiveImpact}
    // 4,5.(0,n)"impactQuantity"
    // 6.(0-3)PassivePermanent{none , passive , permanent , continuous}
    // 7.(0,n)turnsToBeActivated
    // 8,9.(0,n)turnsActive
    // 10.dispel(0-2){none,buffDispel,allPositiveDispel}
    // 11.cellImpact(0-4){none,poison,fire,holy}
    //12.isOnCell(0,1)

    public Impact(String impactTypeComp, String impactWayOfAssigning, String targetTypeId, String impactTypeId) {
        this.impactTypeComp = impactTypeComp;
        this.impactWayOfAssigning = impactWayOfAssigning;
        this.targetTypeId = targetTypeId;
        this.impactTypeId = impactTypeId;
        impactAreaClass = new ImpactArea(targetTypeId, null);
        impactEffectComp = new ImpactEffectComp(impactArea, this, null, impactWayOfAssigning, impactTypeComp);
    }


    //impactTypeVariables

    private int turnsToBeActivated;
    private int turnsActive;
    private boolean isPositiveImpact;
    private boolean buff;
    private boolean holyBuff;
    private boolean powerBuff;
    private boolean poisonBuff;
    private boolean weaknessBuff;
    private boolean stunBuff;
    private boolean disarmBuff;
    private boolean manaChange;
    private boolean healthChange;
    private boolean damageChange;
    private boolean passive;
    private boolean continues;
    private boolean permanent;
    private boolean cellImpact;
    private boolean fireCell;
    private boolean holyCell;
    private boolean poisonCell;
    private boolean dispel;
    private boolean isBuffDispel;
    private boolean isAllPositiveDispel;
    int impactQuantity;

    private void setImpactTypeIdVariables() {
        isPositiveImpact = impactTypeId.charAt(0) == '1';
        buff = impactTypeId.charAt(1) != '0';
        holyBuff = impactTypeId.charAt(1) == '1';
        powerBuff = impactTypeId.charAt(1) == '2';
        poisonBuff = impactTypeId.charAt(1) == '3';
        weaknessBuff = impactTypeId.charAt(1) == '4';
        stunBuff = impactTypeId.charAt(1) == '5';
        disarmBuff = impactTypeId.charAt(1) == '6';
        manaChange = impactTypeId.charAt(2) == '1';
        healthChange = impactTypeId.charAt(2) == '2';
        damageChange = impactTypeId.charAt(2) == '3';
        passive = impactTypeId.charAt(6) == '1';
        permanent = impactTypeId.charAt(6) == '2';
        continues = impactTypeId.charAt(6) == '3';
        turnsToBeActivated = Integer.parseInt(impactTypeId.substring(7, 8));
        turnsActive = Integer.parseInt(impactTypeId.substring(8, 10));
        cellImpact = impactTypeId.charAt(11) != '0';
        poisonCell = impactTypeId.charAt(11) == '1';
        fireCell = impactTypeId.charAt(11) == '2';
        holyCell = impactTypeId.charAt(11) == '3';
        dispel = impactTypeId.charAt(10) != '0';
        isBuffDispel = impactTypeId.charAt(10) == '1';
        isAllPositiveDispel = impactTypeId.charAt(10) == '2';
        impactQuantity = getImpactQuantityWithSign();
    }
    //impactTypeVariables

    //impactTypeComp variables


    //impactTypeComp variables

    public void setAllVariablesNeeded() {
        setImpactTypeIdVariables();
    }
    //needed id variables

    //set ImpactArea

    void doImpact(Player friendlyPlayer, MovableCard target, Cell targetCell, Cell castingCell) {
        setAllVariablesNeeded();
        match = target.match;
        impactAreaClass = new ImpactArea(targetTypeId, match);
        impactAreaClass.setImpactArea(friendlyPlayer, targetCell, castingCell);
        impactArea = impactAreaClass.getImpactArea();
        impactEffectComp = new ImpactEffectComp(impactArea, this, match, impactWayOfAssigning, impactTypeComp);
        if (impactEffectComp.doesItHaveToBeSetOnSoldiersFourImpacts(targetCell))
            return;
        impactEffectComp.doImpactComp(target, castingCell);
        if (turnsToBeActivated <= 0)
            if (powerBuff)
                powerBuff();
            else if (poisonBuff)
                setPoisonBuff();
            else if (weaknessBuff)
                weaknessBuff();
            else if (cellImpact)
                setImpactOnCells();
            else if (!buff) {
                if (manaChange)
                    manaChange(friendlyPlayer);
                else if (healthChange)
                    healthChange();
                else if (damageChange)
                    damageChange();
            } else if (dispel)
                dispel(friendlyPlayer);

    }

    private void manaChange(Player player) {
        player.setMana(player.getMana() + impactQuantity);
    }

    private void healthChange() {
        for (Cell cell : impactArea) {
            MovableCard movableCard = cell.getMovableCard();
            if (movableCard == null)
                continue;
            if (permanent) {
                cell.getMovableCard().setHealth(cell.getMovableCard().getHealth() + impactQuantity);
            } else
                cell.getMovableCard().dispelableHealthChange += impactQuantity;
        }
    }

    private void damageChange() {
        for (Cell cell : impactArea)
            if (permanent)
                cell.getMovableCard().setDamage(cell.getMovableCard().getDamage() + impactQuantity);
            else
                cell.getMovableCard().dispelableDamageChange += impactQuantity;
    }

    private void dispel(Player dispellingPlayer) {
        if (isBuffDispel)
            buffDispel(dispellingPlayer);
        if (isAllPositiveDispel)
            allPositiveImpactDispel();
    }

    private void allPositiveImpactDispel() {
        for (Cell cell : impactArea) {
            MovableCard movableCard = cell.getMovableCard();
            cell.cellImpacts.removeIf(impact -> impact.isPositiveImpact);
            if (movableCard != null) {
                movableCard.getImpactsAppliedToThisOne().removeIf(impact -> impact.isPositiveImpact);
                movableCard.onDefendImpact = movableCard.onDefendImpact.isPositiveImpact?null:movableCard.onDefendImpact;
                movableCard.onAttackImpact = movableCard.onAttackImpact.isPositiveImpact?null:movableCard.onAttackImpact;
                if(movableCard instanceof Minion){
                    Minion minion = (Minion) movableCard;
                    minion.setSummonImpact(minion.getSummonImpact());
                }
            }
        }
    }

    private void buffDispel(Player dispellingPlayer) {
        for (Cell cell : impactArea) {
            if (cell.getMovableCard() != null) {
                if (cell.getMovableCard().player.equals(dispellingPlayer)) {
                    cell.getMovableCard().getImpactsAppliedToThisOne().removeIf(impact -> !impact.isPositiveImpact && impact.buff);
                } else
                    cell.getMovableCard().getImpactsAppliedToThisOne().removeIf(impact -> impact.isPositiveImpact && impact.buff);
            }
        }
    }

    private void setImpactOnCells() {
        for (Cell cell : impactArea) {
            impactTypeId = changeCharAtDesiredIndex(12, '0', impactTypeId);
            cell.addToImpacts(this);
        }
    }

    //buff manager

    private void setPoisonBuff() {
        for (Cell cell : impactArea) {
            MovableCard movableCard = cell.getMovableCard();
            if (movableCard != null)
                movableCard.getImpactsAppliedToThisOne().add(this);
        }
    }

    static void holyBuff(MovableCard movableCard, int damageTaken) {
        for (Impact impact : movableCard.getImpactsAppliedToThisOne()) {
            if (impact.holyBuff) {
                System.out.println("In Impact: Holy Buff is working (hope so)");
                int maxHeal = Math.min(impact.impactQuantity, damageTaken);
                movableCard.setHealth(movableCard.getHealth() + maxHeal);
            }
        }
    }

    private void doPoisonBuff(MovableCard movableCard) {
        if (poisonBuff)
            movableCard.setHealth(movableCard.getHealth() - impactQuantity);
    }


    private void powerBuffOrWeaknessBuff() {
        for (Cell cell : this.impactArea) {
            if (healthChange) //health change
                cell.getMovableCard().dispelableHealthChange += impactQuantity;
            if (damageChange) //damage change
                cell.getMovableCard().dispelableDamageChange += impactQuantity;
        }
    }

    private void powerBuff() {
        powerBuffOrWeaknessBuff();
    }

    private void weaknessBuff() {
        powerBuffOrWeaknessBuff();
    }
    //buff manager


    public void goThroughTime(MovableCard movableCard) {
        if (turnsToBeActivated >= 0) {
            turnsToBeActivated--;
            return;
        }
        if (passive || continues)
            return;
        if (turnsActive != 0)
            turnsActive--;
        System.out.println("turns " + turnsActive + " " + turnsToBeActivated);
        if (turnsToBeActivated == 0)
            doImpact(movableCard.player, movableCard, movableCard.cardCell, movableCard.cardCell);
        if (this.isPoisonBuff())
            doPoisonBuff(movableCard);
        if (this.passive)
            doImpact(movableCard.player, movableCard, movableCard.cardCell, movableCard.cardCell);

    }

    String changeCharAtDesiredIndex(int index, char newChar, String string) {
        return string.substring(0, index) + newChar + string.substring(index + 1);
    }


    public boolean isImpactOver() {
        if (passive || permanent)
            return false;
        return turnsActive <= 0;
    }

    public void doAntiImpact(MovableCard movableCard) {
        if (healthChange)
            movableCard.dispelableHealthChange -= impactQuantity;
        if (damageChange)
            movableCard.dispelableDamageChange -= impactQuantity;
    }
    //getters


    public boolean isTargetAttackTypeMatters() {
        return impactAreaClass.isAttackTypeMatters();
    }

    public boolean isPassive() {
        return passive;
    }

    public boolean isContinues() {
        return continues;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public boolean isCellImpact() {
        return cellImpact;
    }

    public boolean isFireCell() {
        return fireCell;
    }

    public boolean isHolyCell() {
        return holyCell;
    }

    public boolean isPoisonCell() {
        return poisonCell;
    }

    boolean isStunBuff() {
        return stunBuff;
    }

    boolean isDisarmBuff() {
        return disarmBuff;
    }

    boolean isPoisonBuff() {
        return poisonBuff;
    }

    public ArrayList<Cell> getImpactArea() {
        return impactArea;
    }

    public String getTargetTypeId() {
        return impactAreaClass.getTargetTypeId();
    }

    private int getImpactQuantityWithSign() {
        int sign;
        if (impactTypeId.charAt(3) == '0')
            sign = -1;
        else
            sign = 1;
        return sign * Integer.parseInt(impactTypeId.substring(4, 6));
    }

    public String getImpactTypeId() {
        return impactTypeId;
    }

    boolean isImmuneToMinDamage() {
        return impactEffectComp.isImmuneToMinDamage();
    }

    boolean doesHaveAntiNegativeImpact() {
        return impactEffectComp.doesHaveAntiNegativeImpact();
    }

    public boolean isSelectedCellImportant() {
        return impactAreaClass.isSelectedCellImportant();
    }


    Impact copy() {
        Impact impact = new Impact(impactTypeComp,impactWayOfAssigning,targetTypeId,impactTypeId);
        impact.match = match;
        return impact;
    }

    public ImpactArea getImpactAreaClass() {
        return impactAreaClass;
    }

    public ImpactEffectComp getImpactEffectComp() {
        return impactEffectComp;
    }


    //getters
    //setters


    public void setTargetTypeId(String targetTypeId) {
        impactAreaClass.setTargetTypeId(targetTypeId);
    }

    public void setImpactTypeId(String impactTypeId) {
        this.impactTypeId = impactTypeId;
    }


    //setters
}



