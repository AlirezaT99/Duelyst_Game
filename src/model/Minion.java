package model;

import java.util.ArrayList;

    public class Minion extends MovableCard {
        private static ArrayList<Minion> minions = new ArrayList<>();
        private Impact onSpawnImpact;
        private Impact onDeathImpact;
        private Impact passiveImpact;
        private Impact onDefendImpact;
        private Impact onAttackImpact;
        private Impact onComboImpact;
        private Impact secondaryImpact;
        @Override
        protected void manageCasualties() {
            if (this.getHealth() <= 0) {
                this.isAlive = false;
                onDeathImpact.doImpact(this.player, this);
                //do dyingWish
            }
        }

        public void castCard(Cell cell) {
            this.cardCell = cell;
            this.isAlive = true;
            onSpawnImpact.doImpact(this.player, this);
            // do onSpawnImpact
        }

        @Override
        public void attack(Cell cell) {
            if (this.isAttackValid(cell)) {
                super.attack(cell);
                onAttackImpact.setImpactArea(this.player,cell,this.cardCell);
            }
        }

        public void comboAttack(Cell cell, ArrayList<Minion> minions) {
            minions.get(0).onComboImpact.setImpactArea(this.player, cell, this.cardCell);
            super.attack(cell);
            for (int i = 1; i < minions.size(); i++) {
                MovableCard movableCard = minions.get(i);
                if (movableCard.isAttackValid(cell)) {
                    MovableCard opponent = cell.getMovableCard();
                    opponent.takeDamage(this.getDamage());
                    super.didAttackInThisTurn = true;
                }
            }
        }

        @Override
        public String toString(boolean showCost) {
            String classType = getClassType(this);
            String output = "Type : Minion - Name : " + this.name + " - Class : " + classType + " - AP : " + this.getDamage() +
                    " HP : " + this.getHealth() + " - MP :" + this.manaCost + " Special power : " + this.description;
            if (showCost) output = output + " - Sell Cost : " + getCost();
            //output = output + "\n";
            return output;
        }

        @Override
        protected void counterAttack(MovableCard opponent) {
            super.counterAttack(opponent);
            onDefendImpact.setImpactArea(this.player, opponent.cardCell, this.cardCell);
        }

        //setters

        public void setOnSpawnImpact(Impact onSpawnImpact) {
            this.onSpawnImpact = onSpawnImpact;
        }

        public void setOnDeathImpact(Impact onDeathImpact) {
            this.onDeathImpact = onDeathImpact;
        }

        public void setOnDefendImpact(Impact onDefendImpact) {
            this.onDefendImpact = onDefendImpact;
        }

        public void setOnAttackImpact(Impact onAttackImpact) {
            this.onAttackImpact = onAttackImpact;
        }

        public void setSecondaryImpact(Impact secondaryImpact) {
            this.secondaryImpact = secondaryImpact;
        }

        public void setOnComboImpact(Impact onComboImpact) {
            this.onComboImpact = onComboImpact;
        }

        public void setPassiveImpact(Impact passiveImpact) {
            this.passiveImpact = passiveImpact;
        }

        public static void addToMinions(Minion minion){
            minions.add(minion);
        }
        //setters
    }



