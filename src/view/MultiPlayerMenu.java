package view;

import model.Account;
import presenter.MultiPlayerMenuProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MultiPlayerMenu {
    private boolean isInMultiPlayerMenu;
    private BattleInit battleInit;
    private MultiPlayerMenuProcess multiPlayerMenuProcess;
    private boolean hasRun = false;
    private ArrayList<Account> users = Account.getAccounts();

    public MultiPlayerMenu(BattleInit battleInit) {
        isInMultiPlayerMenu = true;
        this.battleInit = battleInit;
        multiPlayerMenuProcess = new MultiPlayerMenuProcess(this);
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        printUsers();
        while (true) {
            if (!isInMultiPlayerMenu)
                break;
            if (!hasRun) {
                help();
                hasRun = true;
            }
            String command = scanner.nextLine();
            multiPlayerMenuProcess.commandParts = command.split("[ ]");
            int commandType = MultiPlayerMenuProcess.findPatternIndex(command);
            if (commandType == -1)
                System.out.println("invalid input");
            else
                handleErrors(multiPlayerMenuProcess.DoCommands[commandType].doIt());
        }
        scanner.close();
    }

    private void printUsers() {
        showMessage("Choose a user to to start the match:");
        for (Account account : users) {
            showMessage(" - " + account.getUserName());
        }
    }

    private void handleErrors(int message) {
        switch (message) {
            case 1:
                showMessage("selected deck for second player is invalid");
        }
    }

    public int help() {
        showMessage("Select user [user name]");
        showMessage("Start multiPlayer game [mode] [number of flags]->(for the 3rd mode only)");
        showMessage("Exit");
        showMessage("Help");
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
    public void setInMultiPlayerMenu(boolean isInMultiPlayerMenu) {
        this.isInMultiPlayerMenu = isInMultiPlayerMenu;
    }

    public void setHasRun(boolean hasRun) {
        this.hasRun = hasRun;
    }
    //setters
}
