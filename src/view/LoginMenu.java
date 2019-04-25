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
            int commandType = presenter.LoginMenuProcess.findPatternIndex(command, commandParts);
            if (commandType == -1)
                System.out.println("invalid input");
            else
                switch (presenter.LoginMenuProcess.DoCommands[commandType].doIt())
                {
                    case 1:
                        LoginMenu.showMessage("an account with this username already exists");
                        break;
                    case 2:
                        LoginMenu.showMessage("incorrect password");
                        break;
                    case 3:
                        LoginMenu.showMessage("no account with this username found");
                        break;
                    case 0:
                        break;
                }

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