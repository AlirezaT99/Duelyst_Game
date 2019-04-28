package view;

import presenter.BattleInitProcess;

import java.io.IOException;
import java.util.Scanner;

public class BattleInit {
    private boolean isInBattleInit;
    private MainMenu mainMenu;
    private BattleInitProcess battleInitProcess;
    private boolean hasRun = false;

    public BattleInit(MainMenu mainMenu) {
        isInBattleInit = true;
        this.mainMenu = mainMenu;
        battleInitProcess = new BattleInitProcess(this);
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!isInBattleInit)
                break;
            if (!hasRun) {
                help();
                hasRun = true;
            }
            // if(scanner.hasNextLine()) {
            String command = scanner.nextLine();
            battleInitProcess.commandParts = command.split("[ ]");
            int commandType = BattleInitProcess.findPatternIndex(command);
            if (commandType == -1)
                System.out.println("invalid input");
            else
                handleErrors(battleInitProcess.DoCommands[commandType].doIt());
        }
        scanner.close();
    }

    public void handleErrors(int message) {

    }

    public static int help() {
        showMessage("1. Single Player");
        showMessage("2. Multi Player");
        return 0;
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

    //getters
    public MainMenu getMainMenu() {
        return mainMenu;
    }
    //getters

    //setters
    public void setInBattleInit(boolean inBattleInit) {
        isInBattleInit = inBattleInit;
    }

    public void setHasRun(boolean hasRun) {
        this.hasRun = hasRun;
    }
    //setters
}

