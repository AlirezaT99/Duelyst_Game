package model;

import javax.print.DocFlavor;
import java.util.ArrayList;

class Impact {
    private String name;
    private Impact next;
    private Impact previous;
    private ArrayList<Cell> impactArea;
    private ArrayList<Cell> temporaryImpactArea;
    private String targetTypeId = ""; //0.(0,1)"ValidOnAll"|1.(0,1)"SelectedCellImportance"|2.(0,1)"ValidOnAWholeTeam"|
    // 3.(0-2)"onWhichTeam"{friendly, hostile, both}|4.(0-2)"targetSoldierType"{hero,minion,both}|
    // 5.(0-n)"targetFactionType"|6.(2,3)"SquareLength"|7.column(1,0)
    //8.nearHeroHostileMinion(0-2){none, one , all}
    private String impactTypeId = "";//0.(0,1)isPositive|1.(0-6)buffType{holy,power,poison,weakness,stun,disarm}|
    // 2.(0-4)QuantityChange{mana,health,damage}|3.(0,1)quantityChangeSign{negative/positive}|4,5.(0,n)"impactQuantity"|
    // 6.(0,2)PassivePermanent{none , passive , permanent , continuous }|7.(0,n)turnsToBeActivated |8,9.(0,n)turnsActive|
    // 10.(0,1)fromWhichTeamAssigned{player1 , player2} |11.isAssignedOnWhichTeam(0,1){player1,player2}
    // 12.deactivatedForThisTurn(0,1) |13.theWayItIsGonnaBeAssigned(0-3){spellWay,attack,defend,don't care}
    // 14.dispel(0,1)
    private String impactTypeIdComp = ""; //0.

    //set ImpactArea

    public void setImpactArea(Player friendlyPlayer, Player opponentPlayer, Match match, Cell targetCell) {
        impactArea.clear();
        if (targetTypeId.charAt(0) == '1') {
            oneTeam(match.table, opponentPlayer);
            oneTeam(match.table, friendlyPlayer);
        } else if (targetTypeId.charAt(2) == '1') {
            if (targetTypeId.charAt(3) == '0')
                oneTeam(match.table, friendlyPlayer);
            else if (targetTypeId.charAt(3) == '1')
                oneTeam(match.table, opponentPlayer);
        } else if (targetTypeId.charAt(1) == '0') {
            if (targetTypeId.charAt(3) == '0')
                oneHero(match.table, friendlyPlayer);
            else if (targetTypeId.charAt(3) == '1')
                oneHero(match.table, opponentPlayer);
            else {
                oneHero(match.table, friendlyPlayer);
                oneHero(match.table, opponentPlayer);
            }
        } else if (targetTypeId.charAt(6) != '0')
            oneSquare(match.table, targetCell, Integer.parseInt(targetTypeId.substring(3, 4)));
        else if (targetTypeId.charAt(7) == '1') {
            if (targetTypeId.charAt(3) == '0')
                oneColumnFromOneTeam(targetCell, friendlyPlayer, match.table);
            else if (targetTypeId.charAt(3) == '1')
                oneColumnFromOneTeam(targetCell, opponentPlayer, match.table);
            else {
                oneColumnFromOneTeam(targetCell, friendlyPlayer, match.table);
                oneColumnFromOneTeam(targetCell, opponentPlayer, match.table);
            }
        } else if (targetTypeId.charAt(8) == '1')
            oneHostileMinionBesideHero(targetCell, friendlyPlayer.findPlayerHero(), match.table);
        else if (targetTypeId.charAt(4) == '2')
            oneSoldierFromOneTeam(targetCell, friendlyPlayer);
        else if (targetTypeId.charAt(4) == '1')
            oneMinionFromOneTeam(targetCell, friendlyPlayer);

    }

    private void oneHero(Table table, Player player) {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++) {
                MovableCard movableCard = table.getCellByCoordination(i, j).getMovableCard();
                if (movableCard instanceof MovableCard.Hero && movableCard.player.getUserName().compareTo(player.getUserName()) == 0) {
                    impactArea.add(table.getCellByCoordination(i, j));
                    return;
                }
            }
    }

    private void oneTeam(Table table, Player player) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                MovableCard movableCard = table.getCellByCoordination(i, j).getMovableCard();
                if (movableCard.player.getUserName().compareTo(player.getUserName()) == 0)
                    impactArea.add(table.getCellByCoordination(i, j));
            }
        }
    }

    private void oneSquare(Table table, Cell cell, int squareLength) {
        for (int i = cell.getCellCoordination().getX(); i < cell.getCellCoordination().getX() + squareLength; i++)
            for (int j = cell.getCellCoordination().getY(); j < cell.getCellCoordination().getY() + squareLength; j++)
                impactArea.add(table.getCellByCoordination(i, j));
    }

    private void oneSoldierFromOneTeam(Cell cell, Player player) {
        if (cell.getMovableCard().player.getUserName().compareTo(player.getUserName()) == 0)
            impactArea.add(cell);
    }

    private void oneColumnFromOneTeam(Cell cell, Player player, Table table) {
        int y = cell.getCellCoordination().getY();
        for (int i = 0; i < 5; i++)
            try {
                if (cell.getMovableCard().player.getUserName().compareTo(player.getUserName()) == 0)
                    impactArea.add(table.getCellByCoordination(i, y));
            } catch (NullPointerException ignored) {
            }
    }

    private void oneMinionFromOneTeam(Cell cell, Player player) {
        if (cell.getMovableCard() instanceof MovableCard.Minion)
            if (cell.getMovableCard().player.getUserName().compareTo(player.getUserName()) == 0)
                impactArea.add(cell);
    }

    private void oneHostileMinionBesideHero(Cell cell, MovableCard.Hero hero, Table table) {
        int heroX = hero.cardCell.getCellCoordination().getX();
        int herorY = hero.cardCell.getCellCoordination().getY();
        int chosenX = cell.getCellCoordination().getX();
        int chosenY = cell.getCellCoordination().getY();
        if (Math.abs(heroX - chosenX) == 1 && Math.abs(herorY - chosenY) == 1) {
            if (cell.getMovableCard().player.getUserName().compareTo(hero.player.getUserName()) != 0)
                impactArea.add(cell);
        }
    }
    //set ImpactArea

    //impact manager

    public void addImpact(Impact impactInSet) {
        while (impactInSet.next != null)
            impactInSet = impactInSet.next;
        impactInSet.next = this;
    }

    public void removeImpact() {
        this.next.previous = this.previous;
        this.previous.next = this.next;
    }

    //impact manager

    //buff manager

    void holyBuff(MovableCard movableCard){
        for (Impact impact: movableCard.getImpactsAppliedToThisOne()    ) {
            if(impact.impactTypeId.charAt(1) == '1') //is holyBuff
                    movableCard.health++;
        }
    }

    void poisonBuff(MovableCard movableCard){
        for (Impact impact: movableCard.getImpactsAppliedToThisOne()) {
            if(impact.impactTypeId.charAt(1) == '3') //is poisonBuff
                movableCard.health--;
        }
    }

    void powerBuffOrWeaknessBuff(int sign){
        for (Cell cell: this.impactArea    ) {
            if(impactTypeId.charAt(2)== '2') //heath change
                cell.getMovableCard().buffHealthChange += sign*Integer.parseInt(impactTypeId.substring(4,6));
            if(impactTypeId.charAt(2) == '3') //damage change
                cell.getMovableCard().buffDamageChange += sign*Integer.parseInt(impactTypeId.substring(4,6));
        }
    }

    void powerBuff(){
        powerBuffOrWeaknessBuff(1);
    }

    void weaknessBuff(){
        powerBuffOrWeaknessBuff(-1);
    }

    //buff manager


    public void doImpact() {

        String id = impactTypeId;
        if (id.charAt(11) == 1) //deactivated for this turn
            return;
        if(id.charAt(1) == '2')
            powerBuff();
        if(id.charAt(1) == '4')
            weaknessBuff();
        if (id.charAt(3) == '1')
            manaChange();
        if (id.charAt(3) == '2')
            healthChange();
        if (id.charAt(3) == '3')
            damageChange();
    }


    private void manaChange() {
        int impactQuantity = Integer.parseInt(impactTypeId.substring(4, 5));
        for (Cell cell : impactArea) {
            Player player = cell.getMovableCard().player;
            player.setMana(player.getMana() + impactQuantity);
        }
    }

    private void healthChange() {
        int impactQuantity = Integer.parseInt(impactTypeId.substring(4, 5));
        for (Cell cell : impactArea){
            cell.getMovableCard().buffHealthChange += impactQuantity;

        }
    }

    private void damageChange() {
        int impactQuantity = Integer.parseInt(impactTypeId.substring(4, 5));
        for (Cell cell : impactArea)
            cell.getMovableCard().buffDamageChange += impactQuantity;
    }

    private void dispell(String dispellingPlayerUserName) {
        for (Cell cell : impactArea) {
            for (Impact impact : cell.getMovableCard().getImpactsAppliedToThisOne()) {
                if (impact.targetTypeId.charAt(14) != '1') { //dispel
                    if(impact.targetTypeId.charAt(6) == '3')
                        impact.impactTypeId = changeString(12,13,"1",impact.impactTypeId);
                    else
                        removeImpact();
                }
            }
        }
    }

    private String changeString(int startIndex,int endIndex,String newThings, String string){
        return string.substring(0,startIndex) + newThings + string.substring(endIndex);
    }

    void goThroughTime() {
        String s = impactTypeId.substring(0, 7);
        char c1 = impactTypeId.charAt(7);
        char c2 = impactTypeId.charAt(8);
        c1++;
        c2++;
        impactTypeId = s + c1 + c2;
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

    public String getTargetTypeId() {
        return targetTypeId;
    }

    //getters
    //setters

    public void setName(String name) {
        this.name = name;
    }
    //setters
}
