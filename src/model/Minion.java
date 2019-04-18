package model;

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
