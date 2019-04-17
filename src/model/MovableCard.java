package model;

import java.util.ArrayList;

class MovableCard extends Card {
    protected int health;
    protected boolean isAlive = false;
    protected Cell cardCell;
    protected Impact damage;
    private ArrayList<Impact> impactsAppliedToThisOne;
    private int moveRange;
    private boolean didMoveInThisTurn;
    private boolean didAttackInThisTurn;
    private int minAttackRange;
    private int maxAttackRange;
    private boolean isMelee;
    private boolean isHybrid;
    private boolean isStunned = false;
    private Match match;

    public void castCard(Cell cell) {
        cell.setMovableCard(this);
        this.cardCell = cell;
        player.getHand().deleteCastedCard(this);
        // deleteCastedCard bayad public beshe
        // mana ro bayad oun player ei ke cast card mikone azash kam she, ke hamoun ja codesh ro mizanim
    }
    //attack & counterAttack
    public void attack(Cell cell) {
        if (isAttackValid(cell)) {
            // do attack
            didAttackInThisTurn = true;
            cell.getMovableCard().counterAttack(this);
            manageCasualties();
        }
    }

    private boolean isAttackValid(Cell cell) {
        counterAttackAndNormalAttackSameParameters(cell);
        if(isHybrid)
            return true;
        for (Impact impact: impactsAppliedToThisOne)
            if(impact.isStunBuff()){
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

    private void counterAttack(MovableCard opponent) {
        if (isCounterAttackValid(opponent.cardCell)) {
            //do attack
            manageCasualties();
        }
    }

    private boolean isCounterAttackValid(Cell cell) {
        if (counterAttackAndNormalAttackSameParameters(cell)) {
            if(isHybrid)
                return true;
            for (Impact impact: impactsAppliedToThisOne)
                if(impact.isDisarmBuff()){
                    printMessage("Disarmed. Can't CounterAttack");
                    return false;
                }

        } else
            return false;
        return true;
    }
    //attack & counterAttack

    protected void manageCasualties() {
        if (this.health <= 0)
            this.isAlive = false;
    }

    //move
    public void move(Cell cell) {
        if (isMoveValid(cell)) {
            didMoveInThisTurn = true;
            this.cardCell = cell;
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
        for (Impact impact:impactsAppliedToThisOne   ) {
            if(impact.isStunBuff())
            {
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
        Cell cell = player.match.table.findCellByCoordination(coordination);
        if (cell != null)
            return !cell.getMovableCard().player.equals(this.player);
        return false;
    }
    //move

    private int findDistanceBetweenTwoCells(Cell cell1, Cell cell2) {
        return Math.abs(cell1.getCellCoordination().getX() - cell2.getCellCoordination().getX()) + Math.abs(cell1.getCellCoordination().getY() - cell2.getCellCoordination().getY());
    }

    private void printMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    //getters
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public Impact getDamage() {
        return damage;
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
    //getters

    //setters

    public void setCardCell(Cell cardCell) {
        this.cardCell = cardCell;
    }

    //setters}
    class Hero extends MovableCard {

        private Spell heroSpell;
        private int spellCost;
        private int spellCoolDown;

        public Hero(String name, int health, Impact damage, Spell heroSpell, int spellCost, int spellCoolDown) {
            this.heroSpell = heroSpell;
            this.spellCost = spellCost;
            this.spellCoolDown = spellCoolDown;
            this.health = health;
            this.name = name;
            this.damage = damage;
        }

        public void castSpell(Cell cell) {
            // check should be in spell class
            // if check
            // cast spell
            // put the impact of spell in all targets impacts applied to this one
        }

        // getter

        public int getSpellcost() {
            return spellCost;
        }

        public int getSpellCoolDown() {
            return spellCoolDown;
        }

        // getter}
    }

    class Minion extends MovableCard {
        private Impact summonImpact;
        private Impact dyingWishImpact;

        @Override

        protected void manageCasualties() {
            if (this.health <= 0) {
                this.isAlive = false;
                //do dyingWish
            }
        }

        public void castCard(Cell cell) {
            this.cardCell = cell;
            this.isAlive = true;
            // do summonImpact
        }
    }
}
