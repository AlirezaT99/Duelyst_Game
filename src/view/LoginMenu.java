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
            if (commandType == -1) {
                System.out.println("invalid input");
                continue;
            } else
                presenter.LoginMenuProcess.DoCommands[commandType].doIt();
        }
    }

    public static void showMessage(String message){
        System.out.println(message);
    }

    public static String scan(){
        Scanner scanner = new Scanner(System.in);
        String desiredInput = scanner.nextLine();
        return desiredInput;
    }



    //setters
    public static void setIsInLoginMenu(boolean isInLoginMenu) {
        LoginMenu.isInLoginMenu = isInLoginMenu;
    }
    //setters
}
