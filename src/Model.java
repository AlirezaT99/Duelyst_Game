import java.util.ArrayList;


public class Model {
    class Card{

    }
    class MovableCard extends Card{
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


        //attack & counterAttack
        public void attack(Cell cell) {
            if (isAttackValid(cell)) {
                // do attack
                didAttackInThisTurn = true;
                cell.getMoveableCard().counterAttack(this);
                manageCasualties();
            }
        }

        private boolean isAttackValid(Cell cell) {
            counterAttackAndNormalAttackSameParameters(cell);
            if (isStunned) {
                printMessage("Stunned. Can't move");
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
            if(this.team.compareTo(cell.getMoveableCard().team) == 0){
                printMessage("Game doesn't have friendly fire");
                return false;
            }
            return true;
        }

        private void counterAttack(MoveableCard opponent) {
            if(isCounterAttackValid(opponent.cardCell)){
                //do attack
                manageCasualties();
            }
        }

        private boolean isCounterAttackValid(Cell cell) {
            if (counterAttackAndNormalAttackSameParameters(cell)) {
                if (isStunned && !isHybrid) {
                    printMessage("Stunned. Can't move");
                    return false;
                }
            }
            else
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
            return true;
        }

        private boolean isOpponentInTheWayOfDesiredDestination(Cell start, Cell destination) {
            int startX = start.getCellCoord().getX();
            int startY = start.getCellCoord().getY();
            int destinationX = destination.getCellCoord().getX();
            int destinationY = destination.getCellCoord().getY();
            Coordination coordination = new Coordination((startX + destinationX) / 2, (startY + destinationY) / 2);
            Cell cell = Cell.findCellByCoord(coordination);
            if (cell != null)
                return cell.getMoveableCard().team.compareTo(this.team) != 0;
            return false;
        }
        //move

        private int findDistanceBetweenTwoCells(Cell cell1, Cell cell2) {
            return Math.abs(cell1.getCellCoord().getX() - cell2.getCellCoord().getX()) + Math.abs(cell1.getCellCoord().getY() - cell2.getCellCoord().getY());
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

        //setters
    }
    class Hero extends MovableCard{}
    class Minion extends MovableCard{}
    class Spell extends Card{}
    class Impact{}
    class Player{}
    class Item{}
    class InfluentialItem extends Item{}
    class Flag extends Item{}
    class CollectibleItem extends InfluentialItem{}
    class UsableItem extends InfluentialItem{}
    class Collection{}
    class Shop{}
    class Deck{}
    class Hand{}
    class Match{}
    class Coordination{}
    class Cell{}
    class Table{}
}
