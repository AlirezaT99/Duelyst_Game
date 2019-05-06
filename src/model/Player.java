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

    {
        hand = new Hand();
    }

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
        if (deck.getNextCard() == null)
            deck.refreshNextCard();
        for (int i = 0; i < 5; i++) {
            if (hand.getCards().size() < i + 1 || hand.getCards().get(i) == null) {
                Card card = deck.getNextCard();
                hand.getCards().add(i, card);
                deck.refreshNextCard();
                if (card instanceof Spell)
                    deck.getSpells().remove(card);
                if (card instanceof Minion)
                    deck.getMinions().remove(card);
            }
        }
    }

    public void playAI(Match match) {

    }

    public boolean equals(Player player) {
        if(player.isAI())
            return false;
        return player.getAccount().getUserName().equals(this.getAccount().getUserName());
    }

    Hero findPlayerHero() {
        return deck.getHero();
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

    public Deck getDeck() {
        return deck;
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

    public void setAI(boolean AI) {
        isAI = AI;
    }

    //setters

}