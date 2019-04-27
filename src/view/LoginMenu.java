package view;

import presenter.LoginMenuProcess;

import java.io.IOException;
import java.util.Scanner;

public class LoginMenu {
    private boolean isInLoginMenu = true;
    private LoginMenuProcess loginMenuProcess;

    public LoginMenu() {
        loginMenuProcess = new LoginMenuProcess();
        loginMenuProcess.setLoginMenu(this);
        // we'll be adding stuff here
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!isInLoginMenu)
                break;
            String command = scanner.nextLine();
            String[] commandParts = command.split("[ ]");
            loginMenuProcess.commandParts = commandParts;
            int commandType = presenter.LoginMenuProcess.findPatternIndex(command, commandParts);
            if (commandType == -1)
                System.out.println("invalid input");
            else
                handleErrors(loginMenuProcess.DoCommands[commandType].doIt());
        }
        scanner.close(); // ?
    }

    public static String scan() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static void handleErrors(int messageID) {
        switch (messageID) {
            case 1:
                LoginMenu.showMessage("an account with this username already exists");
                break;
            case 2:
                LoginMenu.showMessage("incorrect password");
                break;
            case 3:
                LoginMenu.showMessage("no account with this username found");
                break;
        }
    }

    //setters
    public void setIsInLoginMenu(boolean isInLoginMenu) {
        this.isInLoginMenu = isInLoginMenu;
    }
    //setters

    public static int help() {
        showMessage("create account [user name]");
        showMessage("login [user name]");
        showMessage("show leaderBoard");
        showMessage("save");
        showMessage("logout");
        return 0;
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }
}