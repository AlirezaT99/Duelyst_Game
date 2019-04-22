package view;

import presenter.LoginMenuProcess;

import java.io.IOException;
import java.util.Scanner;


public class LoginMenu {
    private static boolean isInLoginMenu = true;
    private static String commandParts[];

    public static void main(String[] args) throws IOException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            commandParts = command.split("[ ]");
            LoginMenuProcess.commandParts = commandParts;
            if (!isInLoginMenu)
                break;
            int commandType = presenter.LoginMenuProcess.findPatternIndex(command);
            if (commandType == -1)
                System.out.println("invalid input");
            else
                presenter.LoginMenuProcess.DoCommands[commandType].doIt();
            // it should return an int in order to handle the messages
        }
    }

    public static String scan() {
        // there's supposed to be only one scanner defined in the code
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    //setters
    public static void setIsInLoginMenu(boolean isInLoginMenu) {
        LoginMenu.isInLoginMenu = isInLoginMenu;
    }
    //setters

    public static void help() {
        showMessage("create account [user name]");
        showMessage("login [user name]");
        showMessage("show leaderBoard");
        showMessage("save");
        showMessage("logout");
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }
}