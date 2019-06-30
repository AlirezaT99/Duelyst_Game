package model;

import java.util.ArrayList;

public class ImpactEffectComp {

    private String impactTypeIdComp = "";
    // 0.antiHolyBuff
    // 1.holyBuffCanceler
    // 2.antiNegativeImpactOnDefend(0,1)
    // 3.antiPoisonOnDefend(0,1)
    // 4.kill(0,1)
    // 5.risingDamage(0-2){none,constRise,difRise}
    // 6.immuneToMinDamage(0,1)
    // 7.antiDisarmOnDefend(0,1)
    // 8.previousAttackMatters(0,1)
    private String impactWayOfAssigning = "";
    // 0.whereToPutIt(0-4){no where,defend,attack,dyingWish,summonImpact}
    // 1.impactGiverTeam(0-3)
    // 2.impactGetterTeam(0-3)

    private int impactQuantity;
    private ArrayList<Cell> impactArea;
    private Impact fatherImpact;
    Match match;

    public ImpactEffectComp(ArrayList<Cell> impactArea, Impact fatherImpact, Match match, String impactWayOfAssigning, String impactTypeIdComp) {
        this.impactArea = impactArea;
        this.fatherImpact = fatherImpact;
        this.match = match;
        this.impactWayOfAssigning = impactWayOfAssigning;
        this.impactTypeIdComp = impactTypeIdComp;
    }

    //needed id variables

    private boolean doesHaveAntiHolyBuff;
    private boolean doesHaveHolyBuffCanceler;
    private boolean doesHaveAntiNegativeImpact;
    private boolean doesHaveAntiPoison;
    private boolean killIt;
    private boolean doesHaveAntiDisarm;
    private boolean doesHaveRisingDamage;
    private boolean difRisingDamage;
    private boolean immuneToMinDamage;
    private boolean putItOnDefend;
    private boolean putItOnAttack;
    private boolean putItOnDyingWish;
    private boolean putItOnSummonImpact;
    private String getterTeam;

    public ImpactEffectComp() {
    }

    private void setAllImpactTypeCompVariables() {
        try {
            doesHaveAntiHolyBuff = impactTypeIdComp.charAt(0) == '1';
            doesHaveHolyBuffCanceler = impactTypeIdComp.charAt(1) == '1';
            doesHaveAntiNegativeImpact = impactTypeIdComp.charAt(2) == '1';
            doesHaveAntiPoison = impactTypeIdComp.charAt(3) == '1';
            killIt = impactTypeIdComp.charAt(4) == '1';
            doesHaveRisingDamage = impactTypeIdComp.charAt(5) != '0';
            difRisingDamage = impactTypeIdComp.charAt(5) == '2';
            doesHaveAntiDisarm = impactTypeIdComp.charAt(7) == '1';
            immuneToMinDamage = impactTypeIdComp.charAt(6) == '1';
        }catch (Exception e){}
    }

    private void setAllTheWayOfAssigningVariables() {
        try {
            putItOnDefend = impactWayOfAssigning.charAt(0) == '2';
            putItOnAttack = impactWayOfAssigning.charAt(0) == '1';
            putItOnDyingWish = impactWayOfAssigning.charAt(0) == '3';
            putItOnSummonImpact = impactWayOfAssigning.charAt(0) == '4';
            getterTeam = impactWayOfAssigning.charAt(1) == '1' ? match.getPlayer1().getUserName() : match.getPlayer2().getUserName();
        }catch (Exception e){}
    }


    void doImpactComp(MovableCard target, Cell castingCell) {

        setAllImpactTypeCompVariables();
        setAllTheWayOfAssigningVariables();
        if (killIt)
            kill();
        else if (doesHaveRisingDamage)
            attackOnPreviousTargets(target, castingCell.getMovableCard());
    }


    boolean doesItHaveToBeSetOnSoldiersFourImpacts(Cell targetCell) {
        MovableCard movableCard = targetCell.getMovableCard();
        if (putItOnAttack || putItOnDefend || putItOnDyingWish || putItOnSummonImpact)
            impactWayOfAssigning = fatherImpact.changeCharAtDesiredIndex(0, '0', impactWayOfAssigning);
        if (putItOnAttack) {
            if (movableCard != null)
                movableCard.onDefendImpact = fatherImpact;
            return true;
        }
        if (putItOnDefend) {
            if (movableCard != null)
                movableCard.onAttackImpact = fatherImpact;
            return true;
        }
        if (putItOnSummonImpact) {
            if (getterTeam.equals(match.getPlayer1().getUserName()))
                addToSummonImpact(match.getPlayer1().getDeck().getCards());
            else addToSummonImpact(match.getPlayer2().getDeck().getCards());
            return true;
        }
        if (putItOnDyingWish) {
            if (movableCard instanceof Minion) {
                Minion minion = (Minion) movableCard;
                minion.setDyingWishImpact(fatherImpact);
            }
            return true;
        }
        return false;
    }

    private void addToSummonImpact(ArrayList<Card> remainingDeck) {
        for (Card card : remainingDeck) {
            if (card instanceof MovableCard) {
                MovableCard movableCard = (MovableCard) card;
                movableCard.getImpactsAppliedToThisOne().add(fatherImpact);
            }
        }
    }

    //special impacts manager

    static boolean doesHaveHolyBuffCanceler(MovableCard attackingCard) {
        for (Impact impact : attackingCard.getImpactsAppliedToThisOne())
            if (impact.getImpactEffectComp().doesHaveHolyBuffCanceler)
                return true;
        return false;
    }

    private void kill() {
        for (Cell cell : impactArea) {
            cell.getMovableCard().setHealth(0);
            cell.getMovableCard().dispelableHealthChange = 0;
        }
    }

    private void attackOnPreviousTargets(MovableCard target, MovableCard attacker) {
        if (attacker.haveAttackedOnThisBefore(target)) {
            target.setHealth(target.getHealth() - impactQuantity);
            if (difRisingDamage) {
                impactQuantity += 2;
                fatherImpact.impactQuantity += 2;
            }
        } else
            attacker.addToTargetedOnes(target);
    }

    public boolean doesHaveAntiNegativeImpact() {
        return doesHaveAntiNegativeImpact;
    }


    boolean isImmuneToMinDamage() {
        return immuneToMinDamage;
    }

    public boolean isDoesHaveAntiPoison() {
        return doesHaveAntiPoison;
    }

    public boolean isDoesHaveAntiDisarm() {
        return doesHaveAntiDisarm;
    }

    public boolean isDoesHaveAntiHolyBuff() {
        return doesHaveAntiHolyBuff;
    }

    //special impacts manager


}
