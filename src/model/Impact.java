package model;

import java.util.ArrayList;

// 10.(0,1)fromWhichTeamAssigned{player1 , player2} |11.isAssignedOnWhichTeam(0,1){player1,player2}
// 12.deactivatedForThisTurn(0,1) |13.theWayItIsGonnaBeAssigned(0-3){spellWay,attack,defend,don't care}
// 16.appliedToOnWhichState(state is for card that have isPositiveImpact)(0-3){none,defend,attack}
public class Impact {
    private int impactId = 1;//hghj
    private ArrayList<Cell> impactArea = new ArrayList<>();
    private Match match;
    private String targetTypeId = ""; //0.(0,1)"ValidOnAll"|1.(0,1)"SelectedCellImportance"|2.(0,1)"ValidOnAWholeTeam"|
    // 3.(0-2)"onWhichTeam"{friendly, hostile, both}|4.(0-2)"targetSoldierType"{hero,minion,both}|
    // 5.(0-1)"combo"|6.(2,3)"SquareLength"|7.column(1,0)|
    //8.nearHeroMinion(0-3){none, one , all,all+self}| 9. random(0,1)|10.previousAttacksMatters(0-2){none,constRise,difRise}|
    // 11.soldierAttackType(0-3){doesn't matter,melee, ranged, hybrid}|
    //12.row(0,1)//13.closestSoldiers(0,1)|14.ranged(0-n)
    private String impactTypeId = "";//0.(0,1)isPositive|1.(0-6)buffType{holy,power,poison,weakness,stun,disarm}|
    // 2.(0-3)QuantityChange{mana,health,damage}|3.(0,1)quantityChangeSign{negative/isPositiveImpact}|4,5.(0,n)"impactQuantity"|
    // 6.(0-3)PassivePermanent{none , passive , permanent , continuous}|7.(0,n)turnsToBeActivated |8,9.(0,n)turnsActive|
    // 10.dispel(0-2){none,buffDispel,allPositiveDispel}|11.setsOnCells(0,1) |
    // 12.cellImpact(0-4){none,poison,fire,holy}|
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

    private boolean validOnAll; //ok
    private boolean targetAttackTypeMatters;
    private boolean validOnAWholeTeam; //ok
    private boolean validOnFriendlyTeamOnly;
    private boolean validOnHostileTeamOnly;
    private boolean selectedCellImportant; //ok
    private boolean isImpactAreaSquare;
    private boolean oneColumn; //ok
    private boolean oneMinionBeside;
    private boolean allMinionsBeside;
    private boolean allSoldierType;
    private boolean minionSoldierTypeOnly;
    private boolean oneRandomClosest;
    private boolean isRandom;
    private boolean oneRow; //ok
    private boolean isRangedSetting;
    private boolean plusItSelf;

    private void setAllTargetTypeIdVariables() {
        validOnAll = targetTypeId.charAt(0) == '1';
        selectedCellImportant = targetTypeId.charAt(1) == '1';
        validOnAWholeTeam = targetTypeId.charAt(2) == '1';
        validOnFriendlyTeamOnly = targetTypeId.charAt(3) == '0';
        validOnHostileTeamOnly = targetTypeId.charAt(3) == '1';
        allSoldierType = targetTypeId.charAt(4) == '2';
        minionSoldierTypeOnly = targetTypeId.charAt(4) == '1';
        isImpactAreaSquare = targetTypeId.charAt(6) != '0';
        oneColumn = targetTypeId.charAt(7) == '1';
        oneMinionBeside = targetTypeId.charAt(8) == '1';
        allMinionsBeside = targetTypeId.charAt(8) == '2' || targetTypeId.charAt(8) == '3';
        plusItSelf = targetTypeId.charAt(8) == '3';
        isRandom = targetTypeId.charAt(9) == '1';
        targetAttackTypeMatters = targetTypeId.charAt(11) != '0';
        oneRow = targetTypeId.charAt(12) == '1';
        oneRandomClosest = targetTypeId.charAt(13) == '1';
        isRangedSetting = targetTypeId.charAt(13) != '0';
    }
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
        System.out.println(impactTypeId);
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
        doesHaveRisingDamage = targetTypeId.charAt(10) != '0';
    }

    //impactTypeComp variables

    public void setAllVariablesNeeded() {
        setAllTargetTypeIdVariables();
        setImpactTypeIdVariables();
        setAllImpactTypeCompVariables();
    }
    //needed id variables

    //set ImpactArea

    private void addToCardsImpact() {
        for (Cell cell : impactArea) {
            if (cell.getMovableCard() != null) {
                cell.getMovableCard().getImpactsAppliedToThisOne().add(this);
            }
        }
    }

    //setImpact main methods
    void setImpactArea(Player friendlyPlayer, Cell targetCell, Cell castingCell) {
        match = friendlyPlayer.match;
        Player opponentPlayer = match.getOtherPlayer(friendlyPlayer);
        teamOrHeroSets(friendlyPlayer, opponentPlayer);
        oneTargetSets(friendlyPlayer, targetCell, opponentPlayer);
        specialSets(friendlyPlayer, targetCell, castingCell, opponentPlayer);
        geometricSets(friendlyPlayer, targetCell, opponentPlayer, castingCell);
        addToCardsImpact();
        setImpactAreaComp(targetCell);
        System.out.println("begin of impact Area");
        for (Cell cell : impactArea) {
            System.out.println(cell.getCellCoordination().getX() + " " + cell.getCellCoordination().getY());
        }
        System.out.println("end of impact Area");
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

    private void teamOrHeroSets(Player friendlyPlayer, Player opponentPlayer) {
        if (validOnAll) { //isPositiveImpact on all soldiers on the table
            oneTeam(opponentPlayer, targetAttackTypeMatters);
            oneTeam(friendlyPlayer, targetAttackTypeMatters);
        } else if (validOnAWholeTeam) {
            if (validOnFriendlyTeamOnly)
                oneTeam(friendlyPlayer, targetAttackTypeMatters);
            else if (validOnHostileTeamOnly)
                oneTeam(opponentPlayer, targetAttackTypeMatters);
            else {
                oneTeam(opponentPlayer, targetAttackTypeMatters);
                oneTeam(friendlyPlayer, targetAttackTypeMatters);
            }
        } else if (!selectedCellImportant) { // ==> it must be set on Hero
            if (validOnFriendlyTeamOnly)
                oneHero(friendlyPlayer);
            else if (validOnHostileTeamOnly)
                oneHero(opponentPlayer);
            else {
                oneHero(opponentPlayer);
                oneHero(friendlyPlayer);
            }
        }
    }

    private void oneTargetSets(Player friendlyPlayer, Cell targetCell, Player opponentPlayer) {
        if (allSoldierType) {
            if (validOnFriendlyTeamOnly)
                oneSoldierFromOneTeam(targetCell, friendlyPlayer);
            else if (validOnHostileTeamOnly)
                oneSoldierFromOneTeam(targetCell, opponentPlayer);
            else
                oneSoldierFromOneTeam(targetCell, null); //means it can be applied to both teams
        } else if (minionSoldierTypeOnly) {
            if (validOnFriendlyTeamOnly)
                oneMinionFromOneTeam(targetCell, friendlyPlayer);
            else if (validOnHostileTeamOnly)
                oneMinionFromOneTeam(targetCell, opponentPlayer);
            else
                oneMinionFromOneTeam(targetCell, null);
        }
    }

    private void geometricSets(Player friendlyPlayer, Cell targetCell, Player opponentPlayer, Cell castingCell) {
        if (isImpactAreaSquare)
            oneSquare(match.table, targetCell, Integer.parseInt(targetTypeId.substring(6, 7)));
        else if (oneColumn) { //column
            if (validOnFriendlyTeamOnly)
                oneColumnFromOneTeam(targetCell, friendlyPlayer);
            else if (validOnHostileTeamOnly)
                oneColumnFromOneTeam(targetCell, opponentPlayer);
            else {
                oneColumnFromOneTeam(targetCell, friendlyPlayer);
                oneColumnFromOneTeam(targetCell, opponentPlayer);
            }
        } else if (isRangedSetting)
            setRanged(castingCell);
    }

    private void specialSets(Player friendlyPlayer, Cell targetCell, Cell castingCell, Player opponentPlayer) {
        if (oneMinionBeside) {
            if (validOnHostileTeamOnly) {
                if (killIt)
                    oneMinionBesideOneCell(targetCell, castingCell, friendlyPlayer);
            } else if (validOnFriendlyTeamOnly)
                oneMinionBesideOneCell(targetCell, castingCell, friendlyPlayer);
            else
                oneMinionBesideOneCell(targetCell, castingCell, opponentPlayer);
        } else if (allMinionsBeside)
            allMinionsBeside(castingCell);
        else if (oneRandomClosest)
            oneRandomClosest(castingCell.getMovableCard());
    }
    //setImpact  main methods

    private void oneHero(Player player) {
        if (oneRow) {
            oneRow(player.findPlayerHero().cardCell);
        } else if (!targetAttackTypeMatters) {
            impactArea.add(player.findPlayerHero().cardCell);
        } else if (targetTypeId.charAt(11) == '2' || targetTypeId.charAt(11) == '3') {
            Hero hero = player.findPlayerHero();
            if (hero.isHybrid() || hero.isRanged())
                impactArea.add(hero.cardCell);
        }
    }

    private void oneTeam(Player player, boolean targetAttackTypeMatters) {
        if (!targetAttackTypeMatters) {
            impactArea.addAll(match.table.findAllSoldiers(player));
        } else {
            char soldierType = targetTypeId.charAt(11);
            if (soldierType == '1')
                impactArea.addAll(match.table.findAllSpecificSoldiers(player, false, true, false));
        }
    }

    private void oneSquare(Table table, Cell cell, int squareLength) {
        for (int i = cell.getCellCoordination().getX(); i < cell.getCellCoordination().getX() + squareLength; i++)
            for (int j = cell.getCellCoordination().getY(); j < cell.getCellCoordination().getY() + squareLength; j++)
                impactArea.add(table.getCellByCoordination(i, j));
    }

    private void oneColumnFromOneTeam(Cell cell, Player player) {
        int y = cell.getCellCoordination().getY();
        for (int i = 1; i <= 5; i++)
            try {
                if (cell.getMovableCard().player.getUserName().compareTo(player.getUserName()) == 0)
                    impactArea.add(match.table.getCellByCoordination(i, y));
            } catch (NullPointerException ignored) {
            }
    }

    private void oneRow(Cell cell) {
        int x = cell.getCellCoordination().getX();
        for (int i = 1; i <= 9; i++) {
            if (match.table.getCellByCoordination(x, i) != null)
                impactArea.add(match.table.getCellByCoordination(x, i));
        }
    }

    private void oneSoldierFromOneTeam(Cell cell, Player player) {
        MovableCard movableCard = cell.getMovableCard();
        if (!targetAttackTypeMatters) {
            if (!isRandom) {
                if (movableCard == null)
                    return;
                if (player != null) {
                    if (cell.getMovableCard().player.getUserName().equals(player.getUserName()))
                        impactArea.add(cell);
                } else
                    impactArea.add(cell);
            } else {
                ArrayList<Cell> soldiersCells = match.table.findAllSoldiers(player);
                findAndAddRandomCellFromGivenCells(soldiersCells);
            }
        } else if ((targetTypeId.charAt(11) == '2' || targetTypeId.charAt(11) == '3') && isRandom) {
            ArrayList<Cell> soldierCells = match.table.findAllSpecificSoldiers(player, true, false, true);
            findAndAddRandomCellFromGivenCells(soldierCells);
        }
    }

    private void oneMinionFromOneTeam(Cell cell, Player player) {
        if (!isRandom) {
            if (player != null) {
                if (cell.getMovableCard() instanceof Minion)
                    if (cell.getMovableCard().player.getUserName().compareTo(player.getUserName()) == 0)
                        impactArea.add(cell);
            } else
                impactArea.add(cell);
        } else {
            ArrayList<Cell> minionsCells = match.table.findAllMinions(player);
            findAndAddRandomCellFromGivenCells(minionsCells);
        }
    }

    private void oneMinionBesideOneCell(Cell cell, Cell castingCell, Player player) {
        if (killIt) {
            findAndAddRandomCellFromGivenCells(player.findPlayerHero().cardCell.getFullAdjacentCells(player));
        } else if (castingCell.isTheseCellsAdjacent(cell) && cell.getMovableCard() != null && cell.getMovableCard().player.equals(player))
            impactArea.add(cell);
    }

    private void allMinionsBeside(Cell castingCell) {
        for (Cell cell : castingCell.getAdjacentCells()) {
            if (validOnHostileTeamOnly && !cell.getMovableCard().player.equals(castingCell.getMovableCard().player))
                impactArea.add(cell);
            else if (validOnFriendlyTeamOnly && cell.getMovableCard().player.equals(castingCell.getMovableCard().player))
                impactArea.add(cell);
            else
                impactArea.add(cell);
        }
        if (plusItSelf)
            impactArea.add(castingCell);
    }

    private void oneRandomClosest(MovableCard movableCard) {
        ArrayList<Cell> cellArrayList = match.table.findClosestSoldiers(movableCard, match.getOtherPlayer(movableCard.player));
        findAndAddRandomCellFromGivenCells(cellArrayList);
    }

    private void findAndAddRandomCellFromGivenCells(ArrayList<Cell> soldiersCells) {
        double i = Math.random() % soldiersCells.size();
        int j = (int) i;
        impactArea.add(soldiersCells.get(j));
    }

    private void setRanged(Cell castingCell) {
        int distance = Integer.parseInt(targetTypeId.substring(14, 15));
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 9; j++) {
                Cell cell = match.table.getCellByCoordination(i, j);
                if (cell.findDistanceBetweenCells(castingCell) <= distance)
                    impactArea.add(cell);
            }
        }
    }

    //set ImpactArea


    void doImpact(Player friendlyPlayer, MovableCard target, Cell targetCell, Cell castingCell) {
        setAllVariablesNeeded();
        setImpactArea(friendlyPlayer, targetCell, castingCell);
        if (turnsToBeActivated != 0)
            return;
        if (doesHaveAntiNegativeImpact)
            antiNegativeImpactOnDefend(target);
        else if (doesHaveAntiPoison)
            antiPoisonOnDefend(target);
        else if (doesHaveAntiDisarm)
            antiDisarmOnDefend(target);
        else if (doesHaveRisingDamage)
            attackOnPreviousTargets(target, castingCell.getMovableCard());
        else if (killIt)
            kill();
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
            System.out.println(cell.getCellCoordination().getX() + " " + cell.getCellCoordination().getY());
            MovableCard movableCard = cell.getMovableCard();
            if (movableCard != null)
                movableCard.getImpactsAppliedToThisOne().add(this);
        }
    }

    static void holyBuff(MovableCard movableCard, int damageTaken) {
        for (Impact impact : movableCard.getImpactsAppliedToThisOne()) {
            if (impact.holyBuff) {
                int maxHeal = Math.max(impact.getImpactQuantityWithSign(), damageTaken);
                movableCard.setHealth(movableCard.getHealth() + maxHeal);
            }
        }
    }

    void poisonBuff(MovableCard movableCard) {
        System.out.println(getImpactQuantityWithSign());
        for (Impact impact : movableCard.getImpactsAppliedToThisOne()) {
            if (impact.impactTypeId.charAt(1) == '3') //is poisonBuff
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
        movableCard.getImpactsAppliedToThisOne().removeIf(impact -> impact.impactWayOfAssigning.charAt(1) == '2' && impact.impactTypeId.charAt(indexOfThatThingInImpactId) == (char) 1);
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
            if (targetTypeId.charAt(10) == '2')
                targetTypeId = changeCharAtDesiredIndex(5, '6', impactTypeId);
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
        if (turnsToBeActivated == 0) {
            doImpact(movableCard.player, movableCard, movableCard.cardCell, movableCard.cardCell);
        }
        if (this.isPoisonBuff())
            poisonBuff(movableCard);
        if (this.passive)
            doImpact(movableCard.player, movableCard, movableCard.cardCell, movableCard.cardCell);

    }

    private String changeCharAtDesiredIndex(int index, char newChar, String string) {
        return string.substring(0, index) + newChar + string.substring(index + 1);
    }

    public ArrayList<Cell> getValidCells(Player friendlyPlayer) {
        ArrayList<Cell> cellArrayList = new ArrayList<>();
        match = friendlyPlayer.match;
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 9; j++) {
                Cell cell = match.table.getCell(i, j);
                if (!selectedCellImportant || oneColumn || oneRow || isImpactAreaSquare || validOnAll || validOnAWholeTeam) {
                    cellArrayList.add(cell);
                    continue;
                }
                if (cell.getMovableCard() == null)
                    continue;
                MovableCard movableCard = cell.getMovableCard();
                if (validOnHostileTeamOnly && movableCard.player.equals(friendlyPlayer))
                    continue;
                if (validOnFriendlyTeamOnly && !movableCard.player.equals(friendlyPlayer))
                    continue;
                if (minionSoldierTypeOnly &&!( movableCard instanceof Minion))
                    continue;
                cellArrayList.add(cell);
            }
        }
        return cellArrayList;
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
        return targetAttackTypeMatters;
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
        return targetTypeId;
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
        return selectedCellImportant;
    }



    Impact copy() {
        Impact impact = new Impact();
        impact.targetTypeId = targetTypeId;
        impact.impactTypeId = impactTypeId;
        impact.impactTypeIdComp = impactTypeIdComp;
        impact.impactWayOfAssigning = impactWayOfAssigning;
        impact.impactAdderTypes = impactAdderTypes;
        impact.match = match;
        return impact;
    }
    //getters
    //setters


    public void setTargetTypeId(String targetTypeId) {
        this.targetTypeId = targetTypeId;
    }

    public void setImpactTypeId(String impactTypeId) {
        this.impactTypeId = impactTypeId;
    }

    public void addToTargetTypeID(String targetTypeId) {
        this.targetTypeId += targetTypeId;
    }

    public void addToImpactTypeID(String impactTypeId) {
        this.impactTypeId += impactTypeId;
    }

    public void addToImpactTypeIdComp(String impactTypeIdComp) {
        this.impactTypeIdComp += impactTypeIdComp;
    }
    //setters
}
