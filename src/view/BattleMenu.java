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
                showMenu();
                hasRun = true;
            }
            if (!isInBattleMenu)
                break;
            String command = scanner.nextLine();
            battleMenuProcess.commandParts = command.split("[) ,(]");
            int commandType = BattleMenuProcess.findPatternIndex(command);
            if (commandType == -1)
                System.out.println("invalid input");
            else {
                int returnedValue = battleMenuProcess.DoCommands[commandType].doIt();
                switch (returnedValue) {
                    case 10:
                        // graveyard
                        inner_loop:
                        while (true) {
                            command = scanner.nextLine();
                            switch (graveYardMenu(command)) {
                                case 1:
                                    break inner_loop;
                                case 2:
                                    graveYardHelp();
                                    break;
                                case 3:
                                    BattleMenuProcess.showGraveYardCards();
                                    break;
                                case 4:
                                    if (BattleMenuProcess.showGraveYardCardInfo(command.split("\\s+")[2]) == -1)
                                        showMessage("invalid cardID");
                                    break;
                                default:
                                    showMessage("invalid command");
                            }
                        }
                        break;

                    default:
                        handleEvents(returnedValue);
                        break;
                }
            }
        }
        scanner.close();
    }

    private void handleEvents(int messageID) {
        switch (messageID) {
            case 1:
                showMessage("invalid coordination");
                break;
        }

    }

    private void graveYardHelp() {
        showMessage("Show info [card id]");
        showMessage("Show cards");
        showMessage("Help");
        showMessage("Exit");
    }

    private int graveYardMenu(String command) {
        if (command.matches("Exit")) return 1;
        if (command.matches("Help")) return 2;
        if (command.matches("Show cards")) return 3;
        if (command.matches("Show info [a-zA-Z0-9._]+")) return 4;

        else return -1;
    }

    public static int showMenu() {
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
