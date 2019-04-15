import java.util.ArrayList;

public class Model {
    class Card{
        private int manaCost;
        protected String name;
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

        public String getName(){
            return name;
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

        private void counterAttack(MovableCard opponent) {
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
                return cell.getMovableCard().team.compareTo(this.team) != 0;
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

        //setters}
    class Hero extends MovableCard{

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
    class Minion extends MovableCard{
        private Impact summonImpact;
        private Impact dyingWishImpact;

        @Override

        protected void manageCasualties() {
            if(this.health <= 0){
                this.isAlive = false;
                //do dyingWish
            }
        }

        public void castCard(Cell cell) {
            this.cardCell = cell;
            this.isAlive = true;
            // do summonImpact
        }}
    }
    class Spell extends Card{
        private String name;
        //private int AreaTargetSquare;
        private Impact primaryimpact = super.getImpact();
        private Impact secondaryImpact;

        public Impact getPrimaryimpact() {
            return primaryimpact;
        }

        public boolean isCastingValid(Cell cell, Impact impact) {
//        if(everyWhere)
//            return true;
//        if(singletarget)
//        {
//            if(primaryimpact.e)
//        }
        }

        public void castCard(Cell cell) {
            if (isCastingValid(cell, primaryimpact))
                primaryimpact.doImpact();
            if (secondaryImpact != null && isCastingValid(cell, secondaryImpact))
                secondaryImpact.doImpact();
        }
    }
    class Impact{}
    class Player{
        private String userName;
        private String password;
        private long money;
        private Collection collection;
        private ArrayList<Player> friends;
        private Hand hand;
        private int mana;
        private Match match;
        private boolean isAI;

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }

        public long getMoney() {
            return money;
        }

        public Collection getCollection() {
            return collection;
        }

        public ArrayList<Player> getFriends() {
            return friends;
        }

        public Hand getHand() {
            return hand;
        }

        public int getMana() {
            return mana;
        }

        public Match getMatch() {
            return match;
        }

        public boolean isAI() {
            return isAI;
        }

        public boolean isHasLoggedIn() {
            return hasLoggedIn;
        }

        private boolean hasLoggedIn;

        public void login() {
            hasLoggedIn = true;
        }

        private void logout() {
            hasLoggedIn = false;
            //todo go to LoginMenu
        }

        public void endTurn() {
            match.switchTurn();
        }

        public void addToHand(Card card) {
            this.hand.fillEmptyPlace(this.collection.getSelectedDeck().getLastCard());
            // todo : fillEmptyPlace ro bayad public konim
        }

        public void playAI(Match match) {
            this.hand.selectCard(0).castCard(
                    this.hand.selectCard(0).getImpact().getImpactArea().get(0).coordination.getX(),
                    this.hand.selectCard(0).getImpact().getImpactArea().get(0).coordination.getY());
        }
    }
    class Item{
        private String name;
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }
    }
    class InfluentialItem extends Item{
        private Impact impact;
        private String itemID;

        public Impact getImpact() {
            return impact;
        }

        public void setImpact(Impact impact) {
            this.impact = impact;
        }

        public String getItemID() {
            return itemID;
        }

        public void setItemID(String itemID) {
            this.itemID = itemID;
        }
    }
    class Flag extends Item{
        Cell cell;
        Match match;

        public Cell getCell() {
            return cell;
        }

        public Match getMatch() {
            return match;
        }

        public Flag(Match match, Cell cell) {
            this.match = match;
            this.cell = cell;
        }
    }
    class CollectibleItem extends InfluentialItem{
        private Cell cell;
        private Match match;

        public Cell getCell() {
            return cell;
        }

        public Match getMatch() {
            return match;
        }

        public void setMatch(Match match) {
            this.match = match;
        }
    }
    class UsableItem extends InfluentialItem{
        private int cost;
        private Deck deck;
        private Match match;

        public void setDeck(Deck deck) {
            this.deck = deck;
        }

        public void setMatch(Match match) {
            this.match = match;
        }

        public int getCost() {
            return cost;
        }

        public Deck getDeck() {
            return deck;
        }

        public Match getMatch() {
            return match;
        }
    }
    class Collection{
        private ArrayList<Item> items;
        private ArrayList<Card> cards;
        private ArrayList<Deck> decks = new ArrayList<Deck>();
        private Deck selectedDeck;

        public void selectDeck(String name) {
            //if deck was in the decks
            //set select deck
        }

        public void addDeck(Deck deck) {
            decks.add(deck);
        }

        public void deleteDeck(String deckName){
            //if deck found
            //remove deck
            // return
            printMessage("Deck not found");
        }

        public void showDeck(Deck deck){
            //show deck probably in presenter
        }

        public boolean validateDeck(Deck deck){
            if(deck.getCards().size() != 20)
                return false;
            int heroCounter = 0;
            for (Card card: deck.getCards())
                if(card instanceof Hero)
                    heroCounter ++;
            return heroCounter == 1;
        }

        public void add(String id, Deck deck){
            //find card and item and hero by id
        }
        public void remove(String id){
            //remove and in dastana
        }

        //search
        public int search(String name){
            Item item = findItemByName(name);
            if(item != null)
                return items.indexOf(item);
            Card card = findCardByName(name);
            if(card != null)
                return cards.indexOf(card);
            printMessage("Card/Item not found");
            return -1;
        }

        public Item findItemByName(String itemName) {
            for (Item item : items) {
                if (item.getName().compareTo(itemName) == 0)
                    return item;
            }
            return null;
        }

        public Card findCardByName(String cardName) {
            for (Card card : cards) {
                if (card.getName().compareTo(cardName) == 0)
                    return card;
            }
            return null;
        }
        //search



        private void printMessage(String message){
            System.out.println(message);
        }

        //getters

        public ArrayList<Item> getItems() {
            return items;
        }

        public ArrayList<Card> getCards() {
            return cards;
        }

        public ArrayList<Deck> getDecks() {
            return decks;
        }

        //getters
    }
    class Shop{

        private ArrayList<Card> shopCards;
        private ArrayList<Item> shopItems;

        {
            shopCards = new ArrayList<Card>();
            shopItems = new ArrayList<Item>();
        }

        public static Shop initShop() {
            Shop shop = new Shop();
            // create Items from file
            // add items to shop
            // create cards from file
            // add cards to shop
            return shop;
        }

        // search

        public String  search(String name){
            Item item = findItemByName(name);
            if(item!= null)
                return item.getId();
            Card card = findCardByName(name);
            if(card != null)
                return card.getId();
            printMessage("Card/Item not found");
            return "-1";
        }

        public ArrayList<String > searchCollection(String name){
            //search collection
            return new ArrayList<>();
        }

        // search

        // show Collection must come in show collection

        //buy

        public void buy(Player player, String name) {
            Item item = findItemByName(name);
            Card card = findCardByName(name);
            if (!isBuyValid(player, item, card))
                return;
            int cost;
            if(item != null)
                cost = item.getCost();
            else
                cost = card.getCost();
            player.setMoney(player.getMoney() - cost);
            printMessage("Buying was successful");
            // add to player collection
        }

        private boolean isBuyValid(Player player, Item item, Card card) {
            if (item == null && card == null) {
                printMessage("Card/Item is out of stock");
                return false;
            }
            if ((item != null && item.getCost() > player.getMoney()) || (card != null && card.getCost() > player.getMoney())) {
                printMessage("Not enough drake");
                return false;
            }
            if (item != null && player.getPlayerCollection().getItems().size() == 3) {
                printMessage("Maximum items are in the Collection");
                return false;
            }
            return true;
        }

        //buy

        // sell

        public void sell(Player player,String name ){
            Item item = player.getPlayerCollection().findItemByName(name);
            Card card = player.getPlayerCollection().findCardByName(name);
            if(item == null && card == null){
                printMessage("Item/Card not found");
                return;
            }
            printMessage("Sell was successFull");
            if(item != null){
                player.setMoney(player.getMoney() + item.getCost());
                // remove from player collection
                return;
            }
            player.setMoney(player.getMoney() + card.getCost());
            // remove from player cards
        }

        // sell



        private Item findItemByName(String itemName) {
            for (Item item : shopItems) {
                if (item.getName().compareTo(itemName) == 0)
                    return item;
            }
            return null;
        }

        private Card findCardByName(String cardName) {
            for (Card card : shopCards) {
                if (card.getName().compareTo(cardName) == 0)
                    return card;
            }
            return null;
        }

        private void printMessage(String message) {
            System.out.println(message);
        }
    }
    class Deck{}
    class Hand{}
    class Match{}
    class Coordination{}
    class Cell{}
    class Table{}
}
