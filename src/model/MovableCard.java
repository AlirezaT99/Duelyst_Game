package model;

import java.util.ArrayList;

class MovableCard extends Card {
    protected int health;
    protected boolean isAlive = false;
    protected Cell cardCell;
    protected int damage;
    private ArrayList<Impact> impactsAppliedToThisOne;
    private boolean didMoveInThisTurn;
    private boolean didAttackInThisTurn;
    private int moveRange;
    private int minAttackRange;
    private int maxAttackRange;
    private boolean isMelee;
    private boolean isHybrid;
    private boolean isComboAttacker;
    int buffHealthChange = 0;
    int buffDamageChange = 0;


    //card casting

    public void castCard(Match match,Cell cell,Player castingPlayer ){
        cell.setMovableCard(this);
        this.cardCell = cell;
        player.getHand().deleteCastedCard(this);
        player.setMana(player.getMana() - this.manaCost);
    }

    public boolean isCoordinationValid(Cell cell){
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
            //do attack
            cell.getMovableCard().counterAttack(this);
            manageCasualties();
        }
    }

    boolean isAttackValid(Cell cell) {
        counterAttackAndNormalAttackSameParameters(cell);
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
            impact.doImpact();
            impact.goThroughTime();
        }
    }

    protected void manageCasualties() {
        if (this.health + buffHealthChange <= 0)
            this.isAlive = false;
    }

    //move
    public void move(Cell destination) {
        if (isMoveValid(destination)) {
            didMoveInThisTurn = true;
            this.cardCell = destination;
        }
    }

    private boolean isMoveValid(Cell cell) {
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
        return Math.abs(cell1.getCellCoordination().getX() - cell2.getCellCoordination().getX()) + Math.abs(cell1.getCellCoordination().getY() - cell2.getCellCoordination().getY());
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private void takeDamage(int damage) {
        this.health -= damage;
    }

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

    public ArrayList<Impact> getImpactsAppliedToThisOne() {
        return impactsAppliedToThisOne;
    }

    //getters

    //setters


    public void setCardCell(Cell cardCell) {
        this.cardCell = cardCell;
    }

    //setters
    class Hero extends MovableCard {

        private Spell heroSpell;
        private int spellCost;
        private int spellCoolDown;

        public Hero(String name, int health, int damage, Spell heroSpell, int spellCost, int spellCoolDown) {
            this.heroSpell = heroSpell;
            this.spellCost = spellCost;
            this.spellCoolDown = spellCoolDown;
            this.health = health;
            this.name = name;
            this.damage = damage;
        }

        public void castSpell(Cell cell) {
//            heroSpell.castCard(this.getMatch(), cell);
            // check should be in spell class
            // if check
            // cast spell
            // put the impact of spell in all targets impacts applied to this one
        }
        // getters

        public int getSpellCost() {
            return spellCost;
        }

        public int getSpellCoolDown() {
            return spellCoolDown;
        }

        // getters
    }

    class Minion extends MovableCard {
        private Impact summonImpact;
        private Impact dyingWishImpact;
        private Impact onDefendImpact;
        private Impact onAttackImpact;
        private Impact onComboImpact;

        @Override
        protected void manageCasualties() {
            if (this.health <= 0) {
                this.isAlive = false;
                dyingWishImpact.doImpact();
                //do dyingWish
            }
        }

        public void castCard(Cell cell) {
            this.cardCell = cell;
            this.isAlive = true;
            summonImpact.doImpact();
            // do summonImpact
        }

        @Override
        public void attack(Cell cell) {
            if (MovableCard.this.isAttackValid(cell)) {
                super.attack(cell);
                onAttackImpact.doImpact();
            }
        }

        public void comboAttack(Cell cell, ArrayList<Minion> minions) {
            minions.get(0).onComboImpact.doImpact();
            super.attack(cell);
            for (int i = 1; i < minions.size(); i++) {
                MovableCard movableCard = minions.get(i);
                if (movableCard.isAttackValid(cell)) {
                    MovableCard opponent = cell.getMovableCard();
                    opponent.takeDamage(this.damage);
                    didAttackInThisTurn = true;
                }
            }
        }

        @Override
        protected void counterAttack(MovableCard opponent) {
            super.counterAttack(opponent);
            onDefendImpact.doImpact();
        }
    }
}
