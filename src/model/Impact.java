package model;

import java.util.ArrayList;

// 10.(0,1)fromWhichTeamAssigned{player1 , player2} |11.isAssignedOnWhichTeam(0,1){player1,player2}
// 12.deactivatedForThisTurn(0,1) |13.theWayItIsGonnaBeAssigned(0-3){spellWay,attack,defend,don't care}
// 16.appliedToOnWhichState(state is for card that have impact)(0-3){none,defend,attack}
class Impact {
    private String name;
    private Impact next;
    private Impact previous;
    private ArrayList<Cell> impactArea;
    private Match match;
    private String targetTypeId = ""; //0.(0,1)"ValidOnAll"|1.(0,1)"SelectedCellImportance"|2.(0,1)"ValidOnAWholeTeam"|
    // 3.(0-2)"onWhichTeam"{friendly, hostile, both}|4.(0-2)"targetSoldierType"{hero,minion,both}|
    // 5.(0-n)"targetFactionType"|6.(2,3)"SquareLength"|7.column(1,0)|
    //8.nearHeroHostileMinion(0-2){none, one , all}| 9. random(0,1)|10.column(0,1)|11.soldierAttackType(0-3){doesn't matter,melee, ranged, hybrid}
    //12.row(0,1)//13.closestSoldiers(0,1)
    private String impactTypeId = "";//0.(0,1)isPositive|1.(0-6)buffType{holy,power,poison,weakness,stun,disarm}|
    // 2.(0-4)QuantityChange{mana,health,damage}|3.(0,1)quantityChangeSign{negative/positive}|4,5.(0,n)"impactQuantity"|
    // 6.(0,2)PassivePermanent{none , passive , permanent , continuous, momentary }|7.(0,n)turnsToBeActivated |8,9.(0,n)turnsActive|
    // 10.dispel(0-2){none,buffDispel,allPositiveDispel}|11.setsOnCells(0,1) |
    // |12.cellImpact(0-4){none,poison,fire,holy}
    private String impactTypeIdComp = ""; //0.antiHolyBuff |1.antiNegativeImpactOnDefend(0,1) |
    // 2.antiPoisonOnDefend(0,1)|3.kill(0,1)|4.risingDamage(0-2){none,firstOneInDoc,secondOneInDoc}|5.immuneToMinDamage(0,1)

    //targetTypeId variables

    private boolean validOnAll;
    private boolean targetAttackTypeMatters;
    private boolean validOnAWholeTeam;
    private boolean validOnFriendlyTeamOnly;
    private boolean validOnHostileTeamOnly;
    private boolean selectedCellImportant;
    private boolean isImpactAreaSquare;
    private boolean oneColumn;
    private boolean oneHostileMinionBeside;
    private boolean allHostileMinionsBeside;
    private boolean allSoldierType;
    private boolean minionSoldierTypeOnly;
    private boolean oneRandomClosest;
    private boolean isRandom;
    private boolean oneRow;

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
        oneHostileMinionBeside = targetTypeId.charAt(8) == '1';
        allHostileMinionsBeside = targetTypeId.charAt(8) == '2';
        isRandom = targetTypeId.charAt(9) == '1';
        targetAttackTypeMatters = targetTypeId.charAt(11) != '0';
        oneRow = targetTypeId.charAt(12) == '1';
        oneRandomClosest = targetTypeId.charAt(13) == '1';
    }

    //targetTypeIdVariables

    void setAllVariablesNeeded() {
        setAllTargetTypeIdVariables();
    }


    //set ImpactArea

    //set impact main methods
    private void setImpactArea(Player friendlyPlayer, Cell targetCell, Cell castingCell) {
        Player opponentPlayer = match.getOtherPlayer(friendlyPlayer);
        impactArea.clear();
        teamOrHeroSets(friendlyPlayer, opponentPlayer);
        oneTargetSets(friendlyPlayer, targetCell, opponentPlayer);
        geometricSets(friendlyPlayer, targetCell, opponentPlayer);
        specialSets(friendlyPlayer, targetCell, castingCell);
    }

    private void teamOrHeroSets(Player friendlyPlayer, Player opponentPlayer) {
        if (validOnAll) { //impact on all soldiers on the table
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

    private void geometricSets(Player friendlyPlayer, Cell targetCell, Player opponentPlayer) {
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
        }
    }

    private void specialSets(Player friendlyPlayer, Cell targetCell, Cell castingCell) {
        if (oneHostileMinionBeside)
            oneHostileMinionBesideHero(targetCell, friendlyPlayer.findPlayerHero());
        else if (allHostileMinionsBeside)
            allMinionsBeside(castingCell);
        else if (oneRandomClosest)
            oneRandomClosest(castingCell.getMovableCard());
    }

    //set impact main methods

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
        if (!targetAttackTypeMatters) {
            if (!isRandom) {
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

    private void oneHostileMinionBesideHero(Cell cell, Hero hero) {
        if (hero.cardCell.isTheseCellsAdjacent(cell))
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

    //set ImpactArea

    //impact manager

    public void addImpact(Impact impactInSet) {
        while (impactInSet.next != null)
            impactInSet = impactInSet.next;
        impactInSet.next = this;
    }

    private void removeImpact() {
        try {
            this.next.previous = this.previous;
            this.previous.next = this.next;
        } catch (NullPointerException ignored) {
        }
    }

    //impact manager

    //special impacts manager

    static boolean doesHaveAntiHolyBuff(MovableCard attackingCard) {
        for (Impact impact : attackingCard.getImpactsAppliedToThisOne())
            if (impact.impactTypeIdComp.charAt(0) == '1')
                return true;
        return false;
    }

    private void antiSomeThingOnDefend(int indexOfThatThingInImpactIdComp, char wantedState) {
        Impact impact = this;
        Impact movingImpact = this;
        while (movingImpact.next != null) {
            movingImpact = movingImpact.next;
            if (movingImpact.previous.impactTypeId.charAt(16) == '1' && movingImpact.previous.impactTypeId.charAt(indexOfThatThingInImpactIdComp) == wantedState)
                movingImpact.previous.removeImpact();
        }
        movingImpact = this;
        while (movingImpact.previous != null) {
            movingImpact = movingImpact.previous;
            if (movingImpact.next.impactTypeId.charAt(16) == '1' && movingImpact.previous.impactTypeId.charAt(indexOfThatThingInImpactIdComp) == wantedState)
                movingImpact.next.removeImpact();
        }
    }

    private void antiNegativeImpactOnDefend() {
        antiSomeThingOnDefend(0, '0');
    }

    private void antiPoisonOnDefend() {
        antiSomeThingOnDefend(1, '3');
    }

    private void antiDisarmOnDefend() {
        antiSomeThingOnDefend(1, '6');
    }

    private void kill() {
        for (Cell cell : impactArea) {
            cell.getMovableCard().setHealth(0);
        }
    }

    //special impacts manager

    public void doImpact(Player friendlyPlayer, Cell targetCell, Cell castingCell) {
        setImpactArea(friendlyPlayer, targetCell, castingCell);
        String id = impactTypeId;
        if (id.charAt(11) == 1) //deactivated for this turn
            return;

        if (id.charAt(1) == '2')
            powerBuff();
        if (id.charAt(1) == '4')
            weaknessBuff();
        if (id.charAt(15) == '1')
            setImpactONCells();
        if (id.charAt(1) == '0') {
            if (id.charAt(3) == '1')
                manaChange();
            if (id.charAt(3) == '2')
                healthChange();
            if (id.charAt(3) == '3')
                damageChange();
        }
        if (impactTypeIdComp.charAt(0) == '1')
            antiNegativeImpactOnDefend();
        if (impactTypeIdComp.charAt(1) == '1')
            antiPoisonOnDefend();
        if (impactTypeIdComp.charAt(2) == '1')
            antiDisarmOnDefend();
        if (impactTypeIdComp.charAt(3) == '1')
            kill();


    }

    private void manaChange() {
        int impactQuantity = getImpactQuantityWithSign();
        for (Cell cell : impactArea) {
            Player player = cell.getMovableCard().player;
            player.setMana(player.getMana() + impactQuantity);
        }
    }

    private void healthChange() {
        int impactQuantity = getImpactQuantityWithSign();
        for (Cell cell : impactArea)
            cell.getMovableCard().setHealth(cell.getMovableCard().getHealth() + impactQuantity);
    } //buff or nonBuff change (?)

    private void damageChange() {
        int impactQuantity = getImpactQuantityWithSign();
        for (Cell cell : impactArea)
            cell.getMovableCard().buffDamageChange += impactQuantity;
    } //buff or nonBuff change(?)

    private void dispell(Player dispellingPlayer) {
        if (impactTypeId.charAt(14) == '1') {
            for (Cell cell : impactArea) {
                if (cell.getMovableCard().player.equals(dispellingPlayer)) {
                    for (Impact impact : cell.getMovableCard().getImpactsAppliedToThisOne())
                        if (impact.impactTypeId.charAt(0) == '0' && impact.impactTypeId.charAt(1) != '0')
                            impact.removeImpact();
                } else
                    for (Impact impact : cell.getMovableCard().getImpactsAppliedToThisOne())
                        if (impact.impactTypeId.charAt(0) == '1' && impact.impactTypeId.charAt(1) != '0')
                            impact.removeImpact();
            }
        } else if (impactTypeId.charAt(14) == '2') {
            for (Cell cell : impactArea) {
                for (Impact impact : cell.getMovableCard().getImpactsAppliedToThisOne()) {
                    if (impact.impactTypeId.charAt(0) == '1')
                        impact.removeImpact();
                }
            }
        }
    }

    private void setImpactONCells() {
        for (Cell cell : impactArea) {
            changeCharAtDesiredIndex(15, '0', impactTypeId);
            cell.addToImpacts(this);
        }
    }

    //buff manager

    static void holyBuff(MovableCard movableCard, int damageTaken) {
        for (Impact impact : movableCard.getImpactsAppliedToThisOne()) {
            if (impact.impactTypeId.charAt(1) == '1') { //is holyBuff
                int maxHeal = Math.max(impact.getImpactQuantityWithSign(), damageTaken);
                movableCard.setHealth(movableCard.getHealth() + maxHeal);
            }
        }
    }

    void poisonBuff(MovableCard movableCard) {
        for (Impact impact : movableCard.getImpactsAppliedToThisOne()) {
            if (impact.impactTypeId.charAt(1) == '3') //is poisonBuff
                movableCard.setHealth(movableCard.getHealth() + getImpactQuantityWithSign());

        }
    }

    private void powerBuffOrWeaknessBuff() {
        for (Cell cell : this.impactArea) {
            if (impactTypeId.charAt(2) == '2') //health change
                cell.getMovableCard().buffHealthChange += getImpactQuantityWithSign();
            if (impactTypeId.charAt(2) == '3') //damage change
                cell.getMovableCard().buffDamageChange += getImpactQuantityWithSign();
        }
    }

    private void powerBuff() {
        powerBuffOrWeaknessBuff();
    }

    private void weaknessBuff() {
        powerBuffOrWeaknessBuff();
    }

    //buff manager

    void goThroughTime() {
        String s = impactTypeId.substring(0, 7);
        char c1 = impactTypeId.charAt(7);
        char c2 = impactTypeId.charAt(8);
        c1++;
        c2++;
        impactTypeId = s + c1 + c2;
    }

    private String changeCharAtDesiredIndex(int index, char newChar, String string) {
        return string.substring(0, index) + newChar + string.substring(index + 1);
    }

    //getters

    boolean isStunBuff() {
        return impactTypeId.charAt(2) == '5';
    }

    boolean isDisarmBuff() {
        return impactTypeId.charAt(2) == '6';
    }

    boolean isPoisonBuff() {
        return impactTypeId.charAt(2) == '3';
    }

    public ArrayList<Cell> getImpactArea() {
        return impactArea;
    }

    String getTargetTypeId() {
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

    //getters
    //setters

    public void setName(String name) {
        this.name = name;
    }

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
