package model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MovableCard extends Card {
    private int health;
    protected boolean isAlive = false;
    protected Cell cardCell;
    private int damage;
    private ArrayList<Impact> impactsAppliedToThisOne;
    protected boolean didMoveInThisTurn;
    protected boolean didAttackInThisTurn;
    private int moveRange;
    private int minAttackRange;
    private int maxAttackRange;
    private boolean isMelee;
    private boolean isRanged;
    private boolean isHybrid;
    protected Impact onDefendImpact;
    protected Impact onAttackImpact;
    private boolean isComboAttacker;
    int dispelableHealthChange = 0;
    int dispelableDamageChange = 0;
    private HashMap<String, MovableCard> previousTargets = new HashMap<>();


    String getClassType(MovableCard movableCard) {
        if (movableCard.isMelee)
            return "Melee";
        if (movableCard.isHybrid)
            return "Hybrid";
        if (movableCard.isRanged)
            return "Ranged";
        return null;
    }

    //card casting

    @Override
    public void castCard(Cell cell) {
        cell.setMovableCard(this);
        this.cardCell = cell;
        player.getHand().deleteCardBySettingNull(this);
        player.setMana(player.getMana() - this.manaCost);
    }

    @Override
    public boolean isCastingCoordinationValid(Cell cell) {
        return cell.getMovableCard() == null;
    }
    //card casting

    //attack & counterAttack
    public void attack(Cell cell) {
        if (isAttackValid(cell)) {
            // do attack
            MovableCard opponent = cell.getMovableCard();
            opponent.takeDamage(this.damage);
            didAttackInThisTurn = true;
            if (!Impact.doesHaveAntiHolyBuff(this))
                Impact.holyBuff(opponent, this.damage + this.dispelableDamageChange);
            //do attack
            opponent.counterAttack(this);
            manageCasualties();
        }
    }

    boolean isAttackValid(Cell cell) {
        if (!counterAttackAndNormalAttackSameParameters(cell))
            return false;
        if (isHybrid)
            return true;
        for (Impact impact : impactsAppliedToThisOne)
            if (impact.isStunBuff()) {
                printMessage("Stunned. Can't Attack");
                return false;
            }

        return true;
    }

    private boolean counterAttackAndNormalAttackSameParameters(Cell cell) {
        int distance = findDistanceBetweenTwoCells(this.cardCell, cell);
        if (distance > maxAttackRange || distance < minAttackRange) {
            printMessage("Out of attack range");
            return false;
        }
        if (this.player.equals(cell.getMovableCard().player)) {
            printMessage("Game doesn't have friendly fire");
            return false;
        }
        return true;
    }

    protected void counterAttack(MovableCard opponent) {
        if (isCounterAttackValid(opponent.cardCell)) {
            opponent.takeDamage(this.damage);
            if (!Impact.doesHaveAntiHolyBuff(this))
                Impact.holyBuff(opponent, this.damage + this.dispelableDamageChange);
            manageCasualties();
        }
    }

    private boolean isCounterAttackValid(Cell cell) {
        if (counterAttackAndNormalAttackSameParameters(cell)) {
            if (isHybrid)
                return true;
            for (Impact impact : impactsAppliedToThisOne)
                if (impact.isDisarmBuff()) {
                    printMessage("Disarmed. Can't CounterAttack");
                    return false;
                }

        } else
            return false;
        return true;
    }

    //attack & counterAttack

    public void goThroughTime() {
        for (Impact impact : impactsAppliedToThisOne) {
            impact.doImpact(this.player, this, this.cardCell, this.cardCell);
            impact.goThroughTime();

        }
    }

    protected void manageCasualties() {
        if (this.health + dispelableHealthChange <= 0)
            this.isAlive = false;
    }

    //move
    public void move(Cell destination) {
        if (isMoveValid(destination)) {
            didMoveInThisTurn = true;
            this.cardCell = destination;
            if (!cardCell.cellImpacts.isEmpty()) {
                this.impactsAppliedToThisOne.addAll(cardCell.cellImpacts);
            }
        }
    }

    public boolean isMoveValid(Cell cell) {
        if (didMoveInThisTurn) {
            printMessage("Already moved");
            return false;
        }
        if (findDistanceBetweenTwoCells(this.cardCell, cell) > this.moveRange) {
            printMessage("Out of range");
            return false;
        }
        if (isOpponentInTheWayOfDesiredDestination(this.cardCell, cell)) {
            printMessage("Enemy in the way");
            return false;
        }
        for (Impact impact : impactsAppliedToThisOne) {
            if (impact.isStunBuff()) {
                printMessage("Stunned. Can't Move");
                return false;
            }
        }
        return true;
    }

    private boolean isOpponentInTheWayOfDesiredDestination(Cell start, Cell destination) {
        int startX = start.getCellCoordination().getX();
        int startY = start.getCellCoordination().getY();
        int destinationX = destination.getCellCoordination().getX();
        int destinationY = destination.getCellCoordination().getY();
        Coordination coordination = new Coordination((startX + destinationX) / 2, (startY + destinationY) / 2);
        Cell cell = player.match.table.getCellByCoordination(coordination.getX(), coordination.getY());
        if (cell != null)
            return !cell.getMovableCard().player.equals(this.player);
        return false;
    }
    //move

    private int findDistanceBetweenTwoCells(Cell cell1, Cell cell2) {
        return Math.abs(cell1.getCellCoordination().getX() - cell2.getCellCoordination().getX())
                + Math.abs(cell1.getCellCoordination().getY() - cell2.getCellCoordination().getY());
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    protected void takeDamage(int damage) {
        this.health -= damage;
    }

    //previous targets manager

    void addToTargetedOnes(MovableCard movableCard) {
        previousTargets.put(movableCard.name, movableCard);
    }

    boolean haveAttackedOnThisBefore(MovableCard movableCard) {
        return previousTargets.containsKey(movableCard.name);
    }

    //previous targets manager

    //getters

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMoveRange() {
        return moveRange;
    }

    public int getMinAttackRange() {
        return minAttackRange;
    }

    public int getMaxAttackRange() {
        return maxAttackRange;
    }

    public boolean isMelee() {
        return isMelee;
    }

    public boolean isHybrid() {
        return isHybrid;
    }

    public boolean isRanged() {
        return isRanged;
    }

    public ArrayList<Impact> getImpactsAppliedToThisOne() {
        return impactsAppliedToThisOne;
    }

    public int getDamage() {
        return damage;
    }

    public void setOnDefendImpact(Impact onDefendImpact) {
        this.onDefendImpact = onDefendImpact;
    }

    public void setOnAttackImpact(Impact onAttackImpact) {
        this.onAttackImpact = onAttackImpact;
    }

    //getters

    //setters
    public void setCardCell(Cell cardCell) {
        this.cardCell = cardCell;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setMelee(boolean melee) {
        isMelee = melee;
    }

    public void setRanged(boolean ranged) {
        isRanged = ranged;
    }

    public void setHybrid(boolean hybrid) {
        isHybrid = hybrid;
    }

    public void setMaxAttackRange(int maxAttackRange) {
        this.maxAttackRange = maxAttackRange;
    }

    public void setComboAttacker(boolean comboAttacker) {
        isComboAttacker = comboAttacker;
    }

    //setters


}
