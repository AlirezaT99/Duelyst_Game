package model;

public class Player {
    private Account account;
    private Deck deck; // playing deck
    private Collection collection; // playing collection
    private long money;
    private Hand hand;
    private int mana;
    Match match;
    private boolean isAI;


    public boolean isAI() {
        return isAI;
    }

    public boolean hasLoggedIn() {
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

    public void fillHand() {
        // this.hand.fillEmptyPlace(deck);
        //
    }

    public void playAI(Match match) {

    }

    public boolean equals(Player player) {
        return player.getUserName().equals(this.getUserName());
    }

    Hero findPlayerHero() {
//        for (Card card : collection.getCards()) {
//            if (card instanceof Hero)
//                return (Hero) card;
//        }
//        return null;
        return null;
    }

    //getters
    public int getMana() {
        return mana;
    }

    public Hand getHand() {
        return hand;
    }

    public Match getMatch() {
        return match;
    }

    public long getMoney() {
        return money;
    }

    public Collection getCollection() {
        return this.collection;
    }

    public String getUserName() {
        return this.account.getUserName();
    }

    public Account getAccount() {
        return account;
    }
    //getters

    //setters
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    //setters

}