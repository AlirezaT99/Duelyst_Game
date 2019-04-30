package model;

import java.util.ArrayList;

enum GameMode {HeroesFight, CaptureTheFlag, CaptureMostFlags}

public class Match {
    private boolean singleMode;
    //    private model.GameMode gameMode;
    private int gameMode;
    Table table;
    private Player player1 = new Player();
    private Player player2 = new Player();
    private ArrayList<Card> graveYardPlayer1;
    private ArrayList<Card> graveYardPlayer2;
    private int turn = 0;
    private int numberOfFlags = -1;
    private Integer AILevel = 0;

    {
        graveYardPlayer1 = new ArrayList<>();
        graveYardPlayer2 = new ArrayList<>();
    }

    public Match(boolean singleMode, int gameMode) {
        this.singleMode = singleMode;
        this.gameMode = gameMode;
    }

    public void moveToGraveYard(Card card, Player player) {
        if (player.getUserName().equals(player1.getUserName())) {
            graveYardPlayer1.add(card);
            //
        } else {
            graveYardPlayer2.add(card);
            //
        }
    }

    public void play(Player player) {
        //
    }


    public void setup(Account account, String deckName, int numberOfFlags) {
        player1.setAccount(account);
        player1.setDeck(player1.getCollection().getDeckHashMap().get(deckName));
        player2.setAccount(null);
        this.numberOfFlags = numberOfFlags;
    }

    public void setup(Account account1, Account account2, String deckName, int numberOfFlags) {
        player1.setAccount(account1);
        player1.setAccount(account2);
        player1.setDeck(player1.getCollection().getDeckHashMap().get(deckName));
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

    void switchTurn() {
        turn ^= 1;
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

    Player getPlayer1() {
        return player1;
    }

    Player getPlayer2() {
        return player2;
    }

    Integer getAILevel() {
        return AILevel;
    }
    //getters

}
