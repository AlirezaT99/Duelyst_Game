package view;

import presenter.SinglePlayerMenuProcess;

import java.io.IOException;
import java.util.Scanner;

public class SinglePlayerMenu {
    private boolean isInSinglePlayerMenu;
    private BattleInit battleInit;
    private SinglePlayerMenuProcess singlePlayerMenuProcess;
    private boolean hasRun = false;

    public SinglePlayerMenu(BattleInit battleInit) {
        isInSinglePlayerMenu = true;
        this.battleInit = battleInit;
        singlePlayerMenuProcess = new SinglePlayerMenuProcess(this);
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!isInSinglePlayerMenu)
                break;
            if (!hasRun) {
                help();
                hasRun = true;
            }
            // if(scanner.hasNextLine()){
            String command = scanner.nextLine();
            singlePlayerMenuProcess.commandParts = command.split("[ ]");
            int commandType = SinglePlayerMenuProcess.findPatternIndex(command);
            if (commandType == -1)
                showMessage("invalid input");
            else
                singlePlayerMenuProcess.DoCommands[commandType].doIt();
        }
        scanner.close();
    }

    public int help() {
        showMessage("1. Story");
        showMessage("2. Custom game");
        showMessage("3. Exit");
        showMessage("4. Help");
        return 0;
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

    //getters
    public BattleInit getBattleInit() {
        return battleInit;
    }
    //getters

    //setters
    public void setInSinglePlayerMenu(boolean isInSinglePlayerMenu) {
        this.isInSinglePlayerMenu = isInSinglePlayerMenu;
    }

    public void setHasRun(boolean hasRun) {
        this.hasRun = hasRun;
    }
    //setters
}
