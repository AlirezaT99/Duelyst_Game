package model;

import java.util.ArrayList;

enum GameMode {HeroesFight, CaptureTheFlag, CaptureMostFlags}

class Match {
    private boolean singleMode;
    private model.GameMode gameMode;
    Table table;
    private Player player1;
    private Player player2;
    private ArrayList<Card> graveYardPlayer1;
    private ArrayList<Card> graveYardPlayer2;
    private int turn = 0;

    {
        graveYardPlayer1 = new ArrayList<>();
        graveYardPlayer2 = new ArrayList<>();
    }

    public Player currentTurnPlayer() {
        if (turn == 0)
            return player1;
        return player2;
    }

    public Table getTable() {
        return table;
    }



    public void switchTurn() {
        turn ^= 1;
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
}
