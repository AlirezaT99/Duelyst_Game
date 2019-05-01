package view;

import model.Match;
import presenter.BattleMenuProcess;

import java.io.IOException;
import java.util.Scanner;

public class BattleMenu {
    private boolean isInBattleMenu;
    private BattleInit battleInit;
    private BattleMenuProcess battleMenuProcess;
    private boolean hasRun = false;

    public BattleMenu(BattleInit battleInit, Match match) {
        isInBattleMenu = true;
        this.battleInit = battleInit;
        battleMenuProcess = new BattleMenuProcess(this);
        BattleMenuProcess.setMatch(match);
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!hasRun) {
                help();
                hasRun = true;
            }
            if (!isInBattleMenu)
                break;
            String command = scanner.nextLine();
            battleMenuProcess.commandParts = command.split("[ ]");
            int commandType = BattleMenuProcess.findPatternIndex(command);
            if (commandType == -1)
                System.out.println("invalid input");

        }
        scanner.close();
    }

    public static int help() {
        showMessage("Game Info");
        showMessage("Show my minions");
        showMessage("Show opponent minions");
        showMessage("Show card info [card id]");
        showMessage("Select [card id]");
        showMessage("Show card info [card id]");
        showMessage("Move to (x, y)");
        showMessage("Show card info [card id]");
        showMessage("Attack [opponent card id]");
        showMessage("Attack combo [opponent card id] [my card id] [my card id] [...]");
        showMessage("Use special power (x, y)");
        showMessage("Show hand");
        showMessage("Insert [card name] in (x, y)");
        showMessage("End turn");
        showMessage("Show collectibles");
        showMessage("Select [collectible id]");
        showMessage("Show info");
        showMessage("Use [location x, y]");
        showMessage("Show next card");
        showMessage("Enter graveyard");
        showMessage("Show info [card id]");
        showMessage("Show cards");
        showMessage("Help");
        showMessage("End Game");
        showMessage("Help");
        showMessage("Show menu");
        return 0;
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

    //setters
    public void setInBattleMenu(boolean isInBattleMenu) {
        this.isInBattleMenu = isInBattleMenu;
    }

    public void setHasRun(boolean hasRun) {
        this.hasRun = hasRun;
    }
    //setters

    //getters
    public BattleInit getBattleInit() {
        return battleInit;
    }
    //getters
}
