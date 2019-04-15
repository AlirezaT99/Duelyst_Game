public class Model {
    class Card{ private int manaCost;
        private Cell cell;
        private Impact impact;
        private int cost;
        private Match match;
        private Player player;
        protected String team;
        private String cardID;

        public String getCardID() {
            return cardID;
        }

        public void setCardID(String cardID) {
            this.cardID = cardID;
        }

        public int getManaCost() {
            return manaCost;
        }

        public void setManaCost(int manaCost)
        {
            this.manaCost = manaCost;
        }

        public Coordination getCoordination() {
            return cell.coordination;
        }

        public void setCoordination(Coordination coordination) {
            this.cell.coordination = coordination;
        }

        public Impact getImpact() {
            return impact;
        }

        public void setImpact(Impact impact) {
            this.impact = impact;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public Match getMatch() {
            return match;
        }

        public void setMatch(Match match) {
            this.match = match;
        }

        boolean isManaSufficient(int playerMana) {
            if (playerMana >= manaCost)
                return true;
            return false;
        }

        boolean isCoordinationValid() {
            //todo
            return true;
        }

        public void castCard(Cell cell) {
            cell.card = this;
            this.cell = cell;
            player.getHand().deleteCastedCard(this);
            // deleteCastedCard bayad public beshe
            // mana ro bayad oun player ei ke cast card mikone azash kam she, ke hamoun ja codesh ro mizanim
        }}
    class MovableCard extends Card{}
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
