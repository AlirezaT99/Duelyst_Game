package model;

import java.util.ArrayList;

public class ImpactArea {
    private String targetTypeId = "";
    private Cell objectedCell;
    private Match match;
    private ArrayList<Cell> impactArea = new ArrayList<>();
    // 0.(0,1)"SelectedCellImportance"
    // 1.(0,1)"ValidOnAll"
    // 2.(0,1)"ValidOnAWholeTeam"
    // 3.(0-2)"onWhichTeam"{friendly, hostile, both}
    // 4.(0-2)"targetSoldierType"{hero,minion,both}
    // 5.(0-1)"combo"
    // 6.(2,3)"SquareLength"
    // 7.column(1,0)
    // 8.row(0,1)
    // 9.adjacentToObjectedCell(0-3){none, one , all,all+self}
    // 10.random(0,1)
    // 11.soldierAttackType(0-3){doesn't matter,melee, ranged, hybrid,ranged & hybrid}
    // 12.closestSoldiers(0,1)
    // 13.ranged(0-n)


    // 11.previousAttacksMatters(0-2){none,constRise,difRise}

    //targetTypeId variables

    private boolean isSelectedCellImportant;
    private boolean validOnAll; //ok
    private boolean validOnAWholeTeam; //ok
    private boolean validOnFriendlyTeamOnly;
    private boolean validOnHostileTeamOnly;
    private boolean isImpactAreaSquare;
    private boolean oneColumn; //ok
    private boolean oneSoldierBeside;
    private boolean allSoldiersBeside;
    private boolean allSoldierType;
    private boolean minionSoldierTypeOnly;
    private boolean oneRandomClosest;
    private boolean isRandom;
    private boolean oneRow; //ok
    private boolean isRangedSetting;
    private boolean plusItSelf;
    private boolean hybridTypeMatters;
    private boolean rangedTypeMatters;
    private boolean meleeTypeMatters;
    private boolean targetAttackTypeMatters;
    private int squareLength;
    private int distance;
    //targetTypeId variables

    private void setAllTargetTypeIdVariables() {
        isSelectedCellImportant = targetTypeId.charAt(0) == '1';
        validOnAll = targetTypeId.charAt(1) == '1';
        validOnAWholeTeam = targetTypeId.charAt(2) == '1';
        validOnFriendlyTeamOnly = targetTypeId.charAt(3) == '0';
        validOnHostileTeamOnly = targetTypeId.charAt(3) == '1';
        allSoldierType = targetTypeId.charAt(4) == '2';
        minionSoldierTypeOnly = targetTypeId.charAt(4) == '1';
        isImpactAreaSquare = targetTypeId.charAt(6) != '0';
        oneColumn = targetTypeId.charAt(7) == '1';
        oneRow = targetTypeId.charAt(8) == '1';
        oneSoldierBeside = targetTypeId.charAt(9) == '1';
        allSoldiersBeside = targetTypeId.charAt(9) == '2' || targetTypeId.charAt(8) == '3';
        plusItSelf = targetTypeId.charAt(9) == '3';
        isRandom = targetTypeId.charAt(10) == '1';
        hybridTypeMatters = targetTypeId.charAt(11) == '3' || targetTypeId.charAt(11) == '4';
        rangedTypeMatters = targetTypeId.charAt(11) == '2' || targetTypeId.charAt(11) == '4';
        meleeTypeMatters = targetTypeId.charAt(11) == '1';
        oneRandomClosest = targetTypeId.charAt(12) == '1';
        isRangedSetting = targetTypeId.charAt(13) != '0';
        targetAttackTypeMatters = meleeTypeMatters || rangedTypeMatters || hybridTypeMatters;
        squareLength = Integer.parseInt(targetTypeId.substring(6, 7));
        distance = Integer.parseInt(targetTypeId.substring(13));
    }

    //setImpact main methods
    void setImpactArea(Player friendlyPlayer, Cell targetCell, Cell castingCell) {
        Player opponentPlayer = initRequirements(friendlyPlayer,targetCell);
        teamOrHeroSets(friendlyPlayer, opponentPlayer);
        oneTargetSets(friendlyPlayer, targetCell, opponentPlayer);
        specialSets(friendlyPlayer, targetCell, castingCell, opponentPlayer);
        geometricSets(friendlyPlayer, targetCell, opponentPlayer, castingCell);

    }

    private Player initRequirements(Player friendlyPlayer,Cell cell) {
        objectedCell = cell;
        setAllTargetTypeIdVariables();
        match = friendlyPlayer.match;
        Player opponentPlayer = match.getOtherPlayer(friendlyPlayer);
        impactArea.clear();
        return opponentPlayer;
    }



    private void teamOrHeroSets(Player friendlyPlayer, Player opponentPlayer) {
        if (validOnAll)  //isPositiveImpact on all soldiers on the table
            affectEveryBody(friendlyPlayer, opponentPlayer);
        else if (validOnAWholeTeam)
            affectOnOneTeam(friendlyPlayer, opponentPlayer);
        else if (!isSelectedCellImportant)  // ==> it must be set on Hero
            affectOnHero(friendlyPlayer, opponentPlayer);

    }

    //affects

    private void affectOnOneTeam(Player friendlyPlayer, Player opponentPlayer) {
        if (validOnFriendlyTeamOnly)
            oneTeam(friendlyPlayer);
        else if (validOnHostileTeamOnly)
            oneTeam(opponentPlayer);
        else
            affectEveryBody(friendlyPlayer, opponentPlayer);
    }

    private void affectOnHero(Player friendlyPlayer, Player opponentPlayer) {
        if (validOnFriendlyTeamOnly)
            oneHero(friendlyPlayer);
        else if (validOnHostileTeamOnly)
            oneHero(opponentPlayer);
        else {
            oneHero(opponentPlayer);
            oneHero(friendlyPlayer);
        }
    }

    private void affectEveryBody(Player friendlyPlayer, Player opponentPlayer) {
        oneTeam(opponentPlayer);
        oneTeam(friendlyPlayer);
    }

    //affects

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


    private void specialSets(Player friendlyPlayer, Cell targetCell, Cell castingCell, Player opponentPlayer) {
        if (oneSoldierBeside && minionSoldierTypeOnly) {
            if (validOnHostileTeamOnly)
                oneMinionBesideOneCell(targetCell, castingCell, opponentPlayer);
            else if (validOnFriendlyTeamOnly)
                oneMinionBesideOneCell(targetCell, castingCell, friendlyPlayer);
            else {
                oneMinionBesideOneCell(targetCell, castingCell, opponentPlayer);
                oneMinionBesideOneCell(targetCell, castingCell, friendlyPlayer);
            }
        } else if (allSoldiersBeside) {
            allMinionsBeside(castingCell);
        } else if (oneRandomClosest)
            oneRandomClosest(castingCell.getMovableCard());
    }

    private void geometricSets(Player friendlyPlayer, Cell targetCell, Player opponentPlayer, Cell castingCell) {
        if (isImpactAreaSquare)
            oneSquare(match.table, targetCell, squareLength);
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
        else if (oneRow)
            oneRow(castingCell);
    }
    //setImpact  main methods


    //1st section

    private void oneHero(Player player) {
        if (!targetAttackTypeMatters)
            impactArea.add(player.findPlayerHero().cardCell);
        else
            affectHeroWithSpecificAttackType(player);
    }

    private void affectHeroWithSpecificAttackType(Player player) {
        if (meleeTypeMatters && player.findPlayerHero().isMelee)
            impactArea.add(player.findPlayerHero().cardCell);
        if (hybridTypeMatters && player.findPlayerHero().isHybrid)
            impactArea.add(player.findPlayerHero().cardCell);
        if (rangedTypeMatters && player.findPlayerHero().isRanged)
            impactArea.add(player.findPlayerHero().cardCell);
    }

    private void oneTeam(Player player) {
        if (!targetAttackTypeMatters)
            impactArea.addAll(match.table.findAllSoldiers(player));
        else
            impactArea.addAll(match.table.findAllSpecificSoldiers(player, hybridTypeMatters, meleeTypeMatters, rangedTypeMatters));

    }

    //1st section

    //2nd section

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
        } else if (isRandom) {
            ArrayList<Cell> soldierCells = match.table.findAllSpecificSoldiers(player, hybridTypeMatters, meleeTypeMatters, rangedTypeMatters);
            findAndAddRandomCellFromGivenCells(soldierCells);
        }
    }

    private void oneMinionFromOneTeam(Cell cell, Player player) {
        if (!isRandom) {
            if (player != null) {
                if (cell.getMovableCard() instanceof Minion)
                    if (cell.getMovableCard().player.getUserName().equals(player.getUserName()))
                        impactArea.add(cell);
            } else
                impactArea.add(cell);
        } else {
            ArrayList<Cell> minionsCells = match.table.findAllMinions(player);
            findAndAddRandomCellFromGivenCells(minionsCells);
        }
    }

    //2nd section

    //3rd section

    private void oneMinionBesideOneCell(Cell cell, Cell castingCell, Player player) {
        if (isRandom)
            findAndAddRandomCellFromGivenCells(player.findPlayerHero().cardCell.getFullAdjacentCells(player));
        else if (castingCell.isTheseCellsAdjacent(cell) && cell.getMovableCard() != null && cell.getMovableCard().player.equals(player))
            impactArea.add(cell);
    }

    private void allMinionsBeside(Cell castingCell) {
        for (Cell cell : castingCell.getAdjacentCells()) {
            if (cell.getMovableCard() == null)
                continue;
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

    //3rd section

    //4th section

    private void oneSquare(Table table, Cell cell, int squareLength) {
        for (int i = cell.getCellCoordination().getX(); i < cell.getCellCoordination().getX() + squareLength; i++)
            for (int j = cell.getCellCoordination().getY(); j < cell.getCellCoordination().getY() + squareLength; j++) {
                Cell temp = table.getCellByCoordination(i, j);
                if (temp != null)
                    impactArea.add(table.getCellByCoordination(i, j));
            }
    }

    private void oneColumnFromOneTeam(Cell cell, Player player) {
        int y = cell.getCellCoordination().getY();
        for (int i = 1; i <= 5; i++) {
            if (cell.getMovableCard() != null)
                if (cell.getMovableCard().player.getUserName().equals(player.getUserName()))
                    impactArea.add(match.table.getCellByCoordination(i, y));
        }
    }

    private void oneRow(Cell cell) {
        int x = cell.getCellCoordination().getX();
        for (int i = 1; i <= 9; i++) {
            if (match.table.getCellByCoordination(x, i) != null)
                impactArea.add(match.table.getCellByCoordination(x, i));
        }
    }

    private void setRanged(Cell castingCell) {
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 9; j++) {
                Cell cell = match.table.getCellByCoordination(i, j);
                if (cell.findDistanceBetweenCells(castingCell) <= distance)
                    impactArea.add(cell);
            }
        }
    }

    //4th section

    private void findAndAddRandomCellFromGivenCells(ArrayList<Cell> soldiersCells) {
        int j = (int)(Math.random() % soldiersCells.size());
        if (soldiersCells.size() >= 1)
            impactArea.add(soldiersCells.get(j));
    }

    public ArrayList<Cell> getValidCells(Player friendlyPlayer) {
        ArrayList<Cell> cellArrayList = new ArrayList<>();
        match = friendlyPlayer.match;
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 9; j++) {
                Cell cell = match.table.getCell(i, j);
                if (!isSelectedCellImportant || oneColumn || oneRow || isImpactAreaSquare || validOnAll || validOnAWholeTeam) {
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
                if (minionSoldierTypeOnly && !(movableCard instanceof Minion))
                    continue;
                cellArrayList.add(cell);
            }
        }
        return cellArrayList;
    }


    //set ImpactArea


    //getters
    public ArrayList<Cell> getImpactArea() {
        return impactArea;
    }
    //getters

    //setters

    public void setObjectedCell(Cell objectedCell) {
        this.objectedCell = objectedCell;
    }

    public void setTargetTypeId(String targetTypeId) {
        this.targetTypeId = targetTypeId;
    }

    public boolean isAttackTypeMatters() {
        return targetAttackTypeMatters;
    }

    public String getTargetTypeId() {
        return targetTypeId;
    }

    public boolean isSelectedCellImportant() {
        return isSelectedCellImportant;
    }

    //setters

}
