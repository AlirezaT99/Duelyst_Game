package model;

import java.util.ArrayList;


public class Impact {
    private ArrayList<Cell> impactArea = new ArrayList<>();
    private Match match;
    private ImpactArea impactAreaClass;
    private String impactTypeId = "";
    // 0.(0,1)isPositive
    // 1.(0-6)buffType{holy,power,poison,weakness,stun,disarm}|
    // 2.(0-3)QuantityChange{mana,health,damage}
    // 3.(0,1)quantityChangeSign{negative/isPositiveImpact}
    // 4,5.(0,n)"impactQuantity"
    // 6.(0-3)PassivePermanent{none , passive , permanent , continuous}
    // 7.(0,n)turnsToBeActivated
    // 8,9.(0,n)turnsActive|
    // 10.dispel(0-2){none,buffDispel,allPositiveDispel}
    // 11.setsOnCells(0,1)
    // 12.cellImpact(0-4){none,poison,fire,holy}
    private String impactTypeIdComp = ""; //0.antiHolyBuff |1.antiNegativeImpactOnDefend(0,1) |
    // 2.antiPoisonOnDefend(0,1)|3.kill(0,1)|4.risingDamage(0-2){none,firstOneInDoc,secondOneInDoc}|
    // 5.immuneToMinDamage(0,1)| 6.antiDisarmOnDefend(0,1)
    private String impactWayOfAssigning = ""; //0.castingImpact(0-4){doesn't matter,spell,attack,defend,item}|
    // 1.wayCardGotIt(0-4){doesn't matter,spell,defend,attack}|
    //3.impactGiverTeam(0-3)|4.impactGetterTeam(0-3)|
    private String impactAdderTypes = "";//0.addToWhichState{none, defend, attack}|
    //needed id variables

    //targetTypeId variables

    private int turnsToBeActivated;
    private int turnsActive;


    //targetTypeIdVariables

    //impactTypeVariables

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
    private boolean continuos;
    private boolean permanent;
    private boolean cellImpact;
    private boolean fireCelll;
    private boolean holyCell;
    private boolean poisonCell;
    private boolean dispel;
    private boolean isPermanent;

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
        continuos = impactTypeId.charAt(6) == '3';
        turnsToBeActivated = Integer.parseInt(impactTypeId.substring(7, 8));
        turnsActive = Integer.parseInt(impactTypeId.substring(8, 10));
        dispel = impactTypeId.charAt(10) != '0';
        cellImpact = impactTypeId.charAt(11) == '1';
        poisonCell = impactTypeId.charAt(12) == '1';
        fireCelll = impactTypeId.charAt(12) == '2';
        holyCell = impactTypeId.charAt(12) == '3';
        isPermanent = impactTypeId.charAt(6) == '2' || !buff;
    }
    //impactTypeVariables

    //impactTypeComp variables

    private boolean doesHaveAntiHolyBuff;
    private boolean doesHaveAntiNegativeImpact;
    private boolean doesHaveAntiPoison;
    private boolean killIt;
    private boolean doesHaveAntiDisarm;
    private boolean doesHaveRisingDamage;

    private void setAllImpactTypeCompVariables() {
        doesHaveAntiHolyBuff = impactTypeIdComp.charAt(0) == '1';
        doesHaveAntiNegativeImpact = impactTypeIdComp.charAt(1) == '1';
        doesHaveAntiPoison = impactTypeIdComp.charAt(2) == '1';
        killIt = impactTypeIdComp.charAt(3) == '1';
        doesHaveRisingDamage = impactTypeIdComp.charAt(4) != '0';
        if (impactTypeIdComp.length() >= 7)
            doesHaveAntiDisarm = impactTypeIdComp.charAt(6) == '1';
        doesHaveRisingDamage = true;
    }


    private void setImpactAreaComp(Cell targetCell) {
        if (impactAdderTypes.length() < 2)
            return;
        boolean addToDefend = impactAdderTypes.charAt(0) == '1';
        boolean addToAttack = impactAdderTypes.charAt(1) == '2';
        if (addToDefend) {
            impactAdderTypes = changeCharAtDesiredIndex(0, '0', impactAdderTypes);
            targetCell.getMovableCard().onDefendImpact = this;
        }
        if (addToAttack) {
            impactAdderTypes = changeCharAtDesiredIndex(0, '0', impactAdderTypes);
            targetCell.getMovableCard().onAttackImpact = this;
        }
    }


    //impactTypeComp variables

    public void setAllVariablesNeeded() {
        setImpactTypeIdVariables();
        setAllImpactTypeCompVariables();
    }
    //needed id variables

    //set ImpactArea

    private void addToCardsImpact() {
        ArrayList<Cell> arrayList = new ArrayList<>(); // todo kasif kari
        for (Cell cell : impactArea) {
            if (arrayList.indexOf(cell) == -1)
                arrayList.add(cell);
            else if (cell.getMovableCard() != null) {
                cell.getMovableCard().getImpactsAppliedToThisOne().add(this);
            }
        }
    }


    void doImpact(Player friendlyPlayer, MovableCard target, Cell targetCell, Cell castingCell) {
        setAllVariablesNeeded();
        impactAreaClass.setImpactArea(friendlyPlayer, targetCell, castingCell);
        addToCardsImpact();
        setImpactAreaComp(targetCell);
        if (turnsToBeActivated != 0)
            return;
        if (killIt)
            kill();
        else if (doesHaveAntiNegativeImpact)
            antiNegativeImpactOnDefend(target);
        else if (doesHaveAntiPoison)
            antiPoisonOnDefend(target);
        else if (doesHaveAntiDisarm)
            antiDisarmOnDefend(target);
        else if (doesHaveRisingDamage)
            attackOnPreviousTargets(target, castingCell.getMovableCard());
        else if (powerBuff)
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
        player.setMana(player.getMana() + getImpactQuantityWithSign());
    }

    private void healthChange() {
        int impactQuantity = getImpactQuantityWithSign();
        for (Cell cell : impactArea) {
            MovableCard movableCard = cell.getMovableCard();
            if (movableCard == null)
                continue;
            if (isPermanent) {
                cell.getMovableCard().setHealth(cell.getMovableCard().getHealth() + impactQuantity);
            } else
                cell.getMovableCard().dispelableHealthChange += impactQuantity;
        }
    }

    private void damageChange() {
        int impactQuantity = getImpactQuantityWithSign();
        for (Cell cell : impactArea)
            if (isPermanent)
                cell.getMovableCard().setDamage(cell.getMovableCard().getDamage() + impactQuantity);
            else
                cell.getMovableCard().dispelableDamageChange += impactQuantity;
    }

    private void dispel(Player dispellingPlayer) {
        buffDispel(dispellingPlayer);
        allPositiveImpactDispel();
    }

    private void allPositiveImpactDispel() {
        if (impactTypeId.charAt(14) == '2') {
            for (Cell cell : impactArea) {
                cell.cellImpacts.removeIf(impact -> impact.isPositiveImpact);
                cell.getMovableCard().getImpactsAppliedToThisOne().removeIf(impact -> impact.isPositiveImpact);
            }
        }
    }

    private void buffDispel(Player dispellingPlayer) {
        if (impactTypeId.charAt(14) == '1') {
            for (Cell cell : impactArea) {
                if (cell.getMovableCard() != null) {
                    if (cell.getMovableCard().player.equals(dispellingPlayer)) {
                        cell.getMovableCard().getImpactsAppliedToThisOne().removeIf(impact -> !impact.isPositiveImpact && impact.buff);
                    } else
                        cell.getMovableCard().getImpactsAppliedToThisOne().removeIf(impact -> impact.isPositiveImpact && impact.buff);
                }
            }
        }
    }

    private void setImpactOnCells() {
        for (Cell cell : impactArea) {
            impactTypeId = changeCharAtDesiredIndex(11, '0', impactTypeId);
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
                int maxHeal = Math.min(impact.getImpactQuantityWithSign(), damageTaken);
                movableCard.setHealth(movableCard.getHealth() + maxHeal);
            }
        }
    }

    void poisonBuff(MovableCard movableCard) {
        for (Impact impact : movableCard.getImpactsAppliedToThisOne()) {
            if (impact.impactTypeId.charAt(1) == '3')
                movableCard.setHealth(movableCard.getHealth() + getImpactQuantityWithSign());
        }
    }


    private void powerBuffOrWeaknessBuff() {
        for (Cell cell : this.impactArea) {
            if (healthChange) //health change
                cell.getMovableCard().dispelableHealthChange += getImpactQuantityWithSign();
            if (damageChange) //damage change
                cell.getMovableCard().dispelableDamageChange += getImpactQuantityWithSign();
        }
    }

    private void powerBuff() {
        powerBuffOrWeaknessBuff();
    }

    private void weaknessBuff() {
        powerBuffOrWeaknessBuff();
    }
    //buff manager

    //special impacts manager

    static boolean doesHaveAntiHolyBuff(MovableCard attackingCard) {
        for (Impact impact : attackingCard.getImpactsAppliedToThisOne())
            if (impact.doesHaveAntiHolyBuff)
                return true;
        return false;
    }

    private void antiSomeThingOnDefend(int indexOfThatThingInImpactId, MovableCard movableCard) {
        movableCard.getImpactsAppliedToThisOne().removeIf
                (impact -> impact.impactWayOfAssigning != null & impact.impactWayOfAssigning.length() >= 2 && impact.impactWayOfAssigning.charAt(1) == '2' && impact.impactTypeId.charAt(indexOfThatThingInImpactId) == (char) 1);
    }

    private void antiNegativeImpactOnDefend(MovableCard movableCard) {
        antiSomeThingOnDefend(1, movableCard);
    }

    private void antiPoisonOnDefend(MovableCard movableCard) {
        antiSomeThingOnDefend(2, movableCard);
    }

    private void antiDisarmOnDefend(MovableCard movableCard) {
        antiSomeThingOnDefend(6, movableCard);
    }

    private void kill() {
        for (Cell cell : impactArea) {
            cell.getMovableCard().setHealth(0);
            cell.getMovableCard().dispelableHealthChange = 0;
        }
    }

    private void attackOnPreviousTargets(MovableCard target, MovableCard attacker) {
        if (attacker.haveAttackedOnThisBefore(target)) {
            target.setHealth(target.getHealth() - getImpactQuantityWithSign());
            if (impactAreaClass.getTargetTypeId().charAt(10) == '2')
                impactTypeId = changeCharAtDesiredIndex(5, '6', impactTypeId);
        } else
            attacker.addToTargetedOnes(target);
    }

    //special impacts manager


    public void goThroughTime(MovableCard movableCard) {
        if (turnsToBeActivated != 0)
            turnsToBeActivated--;
        char c1 = (char) (turnsToBeActivated + 48);
        impactTypeId = changeCharAtDesiredIndex(7, c1, impactTypeId);
        if (isPermanent || isPassive())
            return;
        if (turnsActive != 0)
            turnsActive--;
        char c2 = (char) (turnsActive / 10 + 48);
        char c3 = (char) (turnsActive % 10 + 48);
        impactTypeId = changeCharAtDesiredIndex(8, c2, impactTypeId);
        impactTypeId = changeCharAtDesiredIndex(9, c3, impactTypeId);
        System.out.println("turns " + turnsActive + " " + turnsToBeActivated);
//        if (turnsToBeActivated == 0)
//            doImpact(movableCard.player, movableCard, movableCard.cardCell, movableCard.cardCell);
//        if (turnsToBeActivated == 0) {
//            doImpact(movableCard.player, movableCard, movableCard.cardCell, movableCard.cardCell);
//        }
        if (this.isPoisonBuff())
            poisonBuff(movableCard);
//        if (this.passive)
//            doImpact(movableCard.player, movableCard, movableCard.cardCell, movableCard.cardCell);

    }

    private String changeCharAtDesiredIndex(int index, char newChar, String string) {
        return string.substring(0, index) + newChar + string.substring(index + 1);
    }


    public boolean isImpactOver() {
        if (passive || permanent)
            return false;
        return turnsActive <= 0;
    }

    public void doAntiImpact(MovableCard movableCard) {
        int x = getImpactQuantityWithSign();
        if (healthChange)
            movableCard.dispelableHealthChange -= x;
        if (damageChange)
            movableCard.dispelableDamageChange -= x;
    }
    //getters


    public boolean isTargetAttackTypeMatters() {
        return impactAreaClass.isAttackTypeMatters();
    }

    public boolean isPassive() {
        return passive;
    }

    public boolean isContinuos() {
        return continuos;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public boolean isCellImpact() {
        return cellImpact;
    }

    public boolean isFireCelll() {
        return fireCelll;
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
        return impactTypeIdComp.charAt(5) == '1';
    }

    boolean doesHaveAntiNegativeImpact() {
        return doesHaveAntiNegativeImpact;
    }

    public boolean isSelectedCellImportant() {
        return impactAreaClass.isSelectedCellImportant();
    }


    Impact copy() {
        Impact impact = new Impact();
//        impact.targetTypeId = targetTypeId;
        //todo complete
        impact.impactTypeId = impactTypeId;
        impact.impactTypeIdComp = impactTypeIdComp;
        impact.impactWayOfAssigning = impactWayOfAssigning;
        impact.impactAdderTypes = impactAdderTypes;
        impact.match = match;
        return impact;
    }

    public ImpactArea getImpactAreaClass() {
        return impactAreaClass;
    }

    //getters
    //setters


    public void setTargetTypeId(String targetTypeId) {
        impactAreaClass.setTargetTypeId(targetTypeId);
    }

    public void setImpactTypeId(String impactTypeId) {
        this.impactTypeId = impactTypeId;
    }

    public void addToTargetTypeID(String targetTypeId) {
        impactAreaClass.setTargetTypeId(impactAreaClass.getTargetTypeId() + targetTypeId);
    }

    public void addToImpactTypeID(String impactTypeId) {
        this.impactTypeId += impactTypeId;
    }

    public void addToImpactTypeIdComp(String impactTypeIdComp) {
        this.impactTypeIdComp += impactTypeIdComp;
    }
    //setters
}



