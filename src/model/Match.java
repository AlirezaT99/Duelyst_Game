package model;

import presenter.StoryMenuProcess;

import java.util.ArrayList;

enum GameMode {HeroesFight, CaptureTheFlag, CaptureMostFlags}

public class Match {
    private boolean singleMode;
    //    private model.GameMode gameMode;
    private int gameMode;
    Table table;
    private Player player1 = new Player();
    private Player player2 = new Player();
    public ArrayList<Card> player1_graveyard;
    public ArrayList<Card> player2_graveyard;
    private int turn = 0;
    private int turn_number = 1;
    private int mana_basedOnTurn = 2;
    private int numberOfFlags = -1;
    private Integer AILevel = 0;
    private int player1_heroSpellCoolDownCounter = 100; // until the first time it's used
    private int player2_heroSpellCoolDownCounter = 100;

    {
        player1_graveyard = new ArrayList<>();
        player2_graveyard = new ArrayList<>();
    }

    public Match(boolean singleMode, int gameMode) {
        this.singleMode = singleMode;
        this.gameMode = gameMode;
        this.table = new Table();
    }

    public void moveToGraveYard(MovableCard card, Player player) {
        if (player.getUserName().equals(player1.getUserName()))
            player1_graveyard.add(card);
        else
            player2_graveyard.add(card);
        card.cardCell.setMovableCard(null);
    }

    public void play(Player player) {
        //
    }


    public void setup(Account account, String deckName, int numberOfFlags, Deck deck) {
        Deck playerDeck = account.getCollection().getDeckHashMap().get(deckName).copy();
        StoryMenuProcess.setDeckCardIDs(account.getUserName(),playerDeck);
        player1.setAccount(account);
        player1.match = this;
        player1.setDeck(playerDeck);
        player2.setAccount(new Account("computer", "computer"));
        player2.getAccount().setCollection(new Collection());
        player2.getAccount().getCollection().setSelectedDeck(deck);
        Deck deck1 = deck.copy();
        StoryMenuProcess.setDeckCardIDs("computer",deck1);
        player2.setDeck(deck1.copy());
        player2.setAI(true);
        player2.match = this;
        this.numberOfFlags = numberOfFlags;
    }

    public void setup(Account account1, Account account2, int numberOfFlags) {
        player1.setAccount(account1);
        player1.match = this;
        player2.setAccount(account2);
        player2.match = this;
        Deck deck = account1.getCollection().getSelectedDeck().copy();
        StoryMenuProcess.setDeckCardIDs(account1.getUserName(),deck);
        player1.setDeck(deck);
        Deck deck2 = account2.getCollection().getSelectedDeck().copy();
        StoryMenuProcess.setDeckCardIDs(account2.getUserName(),deck2);
        player2.setDeck(deck2);
        this.numberOfFlags = numberOfFlags;
    }

    public void setFlags(int numberOfFlags) {
        if (numberOfFlags == -1 || numberOfFlags == 0)
            return;
        //todo : set flags for third mode
    }

    //turn based manager
    public Player currentTurnPlayer() {
        if (turn == 0)
            return player1;
        return player2;
    }

    public Player notCurrentTurnPlayer() {
        if (turn == 0)
            return player2;
        return player1;
    }

    public void switchTurn() {
        turn ^= 1;
        turn_number++;
    }
    public void handleMana() {
        if (turn_number % 2 == 0)
            mana_basedOnTurn++;
        if (turn_number >= 14) mana_basedOnTurn = 9;
        currentTurnPlayer().setMana(mana_basedOnTurn);
    }
    //turn based manager

    //getters

    Player getOtherPlayer(Player player) {
        if (player.equals(player1))
            return player2;
        return player1;
    }

    public Table getTable() {
        return table;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    Integer getAILevel() {
        return AILevel;
    }

    public int getGameMode() {
        return gameMode;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public int getCurrentPlayerHeroCoolDownCounter() {
        if (currentTurnPlayer().equals(player1))
            return player1_heroSpellCoolDownCounter;
        return player2_heroSpellCoolDownCounter;
    }

    public void setCoolDownCounter() {
        if (currentTurnPlayer().equals(player1))
            player1_heroSpellCoolDownCounter = 0;
        else player2_heroSpellCoolDownCounter = 0;
    }

    public void coolDownIncrease() {
        player1_heroSpellCoolDownCounter++;
        player2_heroSpellCoolDownCounter++;
    }

    //getters

}
