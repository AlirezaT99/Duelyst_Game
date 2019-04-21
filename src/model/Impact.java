package model;

import java.util.ArrayList;

class Impact {
    private String name;
    private ArrayList<Cell> impactArea;

    private String targetTypeId = ""; //(0,1)"ValidOnAll"|(0,1)"SelectedCellImportance"|(0,1)"ValidOnAWholeTeam"|
    // (0-2)"onWhichTeam"|(0-2)"targetSoldierType"|(0-n)"targetFactionType"|(2,3)"SquareLength"
    private String impactTypeId = "";//0.(0,1)isPositive|1.(0,1)isBuff|2.(0-6)buffType{holy,power,poison,weakness,stun,disarm}|
    // 3.(0-4)QuantityChange{mana,health,damage}|
    //4.(0,n)"impactQuantity"|5.(0,2)disImpactKDispel|6.(0,2)PassivePermanent|7.(0,n)turnsToBeActivated |8.(0,n)turnsActive


    public void setImpactArea(Card card, Match match, Cell targetCell) {
      String id = targetsId();
      if(id.charAt(1) == '0'){
          if(id.charAt(2) == '1'){

          }
          else
              impactArea.add(card.player.findPlayerHero().cardCell);
      }
      else {

      }
    }

    public void doImpact() {
        String id = impactTypeId;
        if (id.charAt(6) == '0') {
            if (id.charAt(3) == '1')
                manaChange();
            if (id.charAt(3) == '2')
                healthChange();
            if (id.charAt(3) == '3')
                damageChange();
        }
    }


    private void manaChange() {
        int impactQuantity = Integer.parseInt(impactTypeId.substring(4,5));
        for (Cell cell : impactArea) {
            Player player = cell.getMovableCard().player;
            player.setMana(player.getMana() + impactQuantity);
        }
    }

    private void healthChange() {
        int impactQuantity = Integer.parseInt(impactTypeId.substring(4,5));
        for (Cell cell : impactArea)
            cell.getMovableCard().nonPassiveHealthChange += impactQuantity;
    }

    private void damageChange() {
        int impactQuantity = Integer.parseInt(impactTypeId.substring(4,5));
        for (Cell cell : impactArea)
            cell.getMovableCard().nonPassiveDamageChange += impactQuantity;
    }

    void goThroughTime() {
        String s = impactTypeId.substring(0,7);
        char c1 = impactTypeId.charAt(7);
        char c2 = impactTypeId.charAt(8);
        c1++;
        c2++;
        impactTypeId = s + c1+ c2;
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

    //getters
    //setters


    public void setName(String name) {
        this.name = name;
    }
    //setters
}
