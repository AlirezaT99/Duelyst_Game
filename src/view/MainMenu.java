package view;

import model.Account;
import presenter.MainMenuProcess;

import java.io.IOException;
import java.util.Scanner;

public class MainMenu {
    private boolean isInMainMenu = true;
    private boolean hasRun = false;
    private MainMenuProcess mainMenuProcess = new MainMenuProcess();

    public MainMenu(Account account) {
        mainMenuProcess.setCurrentAccount(account);
        mainMenuProcess.setMainMenu(this);
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!isInMainMenu)
                break;
            if (!hasRun) {
                help();
                hasRun = true;
            }
            String command = scanner.nextLine();
            MainMenuProcess.commandParts = command.split("[ ]");
            int commandType = presenter.MainMenuProcess.findPatternIndex(command);
            if (commandType == -1)
                System.out.println("invalid input");
            else
                handleErrors(mainMenuProcess.DoCommands[commandType].doIt());
        }
        scanner.close();
    }

    private static void handleErrors(int messageID) {
        if (messageID == 1)
            System.out.println("selected deck is invalid");
    }

    public static int help() {
        showMessage("1.Collection");
        showMessage("2.Shop");
        showMessage("3.Battle");
        showMessage("4.Exit");
        showMessage("5.Help");
        return 0;
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

    //setters
    public void setIsInMainMenu(boolean isInMainMenu) {
        this.isInMainMenu = isInMainMenu;
    }

    public void setHasRun(boolean hasRun) {
        this.hasRun = hasRun;
    }
    //setters

    //getters
    public MainMenuProcess getMainMenuProcess() {
        return mainMenuProcess;
    }

    //getters
}
