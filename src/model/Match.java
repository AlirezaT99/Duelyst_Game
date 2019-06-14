package model;

import presenter.StoryMenuProcess;

import java.util.ArrayList;
import java.util.Random;

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
    private int player1_heroSpellCoolDownCounter = 1000; // until the first time it's used
    private int player2_heroSpellCoolDownCounter = 1000;

    {
        player1_graveyard = new ArrayList<>();
        player2_graveyard = new ArrayList<>();
    }

    public Match(boolean singleMode, int gameMode, int numberOfFlags) {
        this.singleMode = singleMode;
        this.gameMode = gameMode;
        this.table = new Table();

        spawnFlags(gameMode, numberOfFlags);
    }

    private void spawnFlags(int gameMode, int numberOfFlags) {
        if (gameMode == 2) {
            Flag flag = new Flag(this, table.getCellByCoordination(3, 5));
            this.getTable().getCellByCoordination(3, 5).setItem(flag);
        }
        if (gameMode == 3) {
            if (numberOfFlags < 1 || numberOfFlags > 30) numberOfFlags = 7;
            spawnRandomFlags(numberOfFlags);
        }
    }

    private void spawnRandomFlags(int numberOfFlags) {
        Boolean[][] cells = new Boolean[5][9];
        for (int i = 0; i < 5; i++)
            java.util.Arrays.fill(cells[i], false);
        cells[2][0] = cells[2][8] = true;
        for (int i = 0; i < numberOfFlags; i++) {
            Random random = new Random();
            int rnd = Math.abs(random.nextInt() % 45);
            if (!cells[rnd / 9][rnd % 9])
                cells[rnd / 9][rnd % 9] = true;
            else while (cells[rnd / 9][rnd % 9])
                rnd = Math.abs(random.nextInt() % 45);
            cells[rnd / 9][rnd % 9] = true;
        }
        cells[2][0] = cells[2][8] = false;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++) {
                if (cells[i][j]) {
                    Flag flag = new Flag(this, table.getCellByCoordination(i + 1, j + 9));
                    this.table.getCellByCoordination(i + 1, j + 1).setItem(flag);
                }
            }
    }

    public void moveToGraveYard(MovableCard card, Player player) {
        if (player.getUserName().equals(player1.getUserName()))
            player1_graveyard.add(card);
        else
            player2_graveyard.add(card);
        card.cardCell.setMovableCard(null);
    }
    


    public void setup(Account account, String deckName, int numberOfFlags, Deck deck) {
        Deck playerDeck = account.getCollection().getDeckHashMap().get(deckName).copy();
        StoryMenuProcess.setDeckCardIDs(account.getUserName(), playerDeck);
        player1.setAccount(account);
        player1.match = this;
        player1.setDeck(playerDeck);
        player1.setAI(false);
        player2.setAccount(new Account("computer", "computer"));
        player2.getAccount().setCollection(new Collection());
        player2.getAccount().getCollection().setSelectedDeck(deck);
        Deck deck1 = deck.copy();
        StoryMenuProcess.setDeckCardIDs("computer", deck1);
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
        StoryMenuProcess.setDeckCardIDs(account1.getUserName(), deck);
        player1.setDeck(deck);
        Deck deck2 = account2.getCollection().getSelectedDeck().copy();
        StoryMenuProcess.setDeckCardIDs(account2.getUserName(), deck2);
        player2.setDeck(deck2);
        this.numberOfFlags = numberOfFlags;
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

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    //getters

    //getters

}
