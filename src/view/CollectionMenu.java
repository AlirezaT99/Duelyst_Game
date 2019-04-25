package view;

import presenter.CollectionMenuProcess;

import java.io.IOException;
import java.util.Scanner;

public class CollectionMenu {
    private static boolean isInCollectionMenu = true;

    static void run() throws IOException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            String[] commandParts = command.split("[ ]");
            CollectionMenuProcess.commandParts = commandParts;
            if (!isInCollectionMenu)
                break;
            int commandType = presenter.CollectionMenuProcess.findPatternIndex(command, commandParts);
            if (commandType == -1)
                System.out.println("invalid input");
            else
                handleErrors(presenter.CollectionMenuProcess.DoCommands[commandType].doIt());
        }
    }

    public static String scan() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static void handleErrors(int messageID) {
        switch (messageID) {
            case 1:
                CollectionMenu.showMessage("a deck with this name already exists");
                break;
            case 2:
                CollectionMenu.showMessage("card doesn't exist");
                break;
            case 3:
                CollectionMenu.showMessage("no card with this id was found in the collection");
                break;
            case 4:
                CollectionMenu.showMessage("card is already in the deck");
                break;
            case 5:
                CollectionMenu.showMessage("number of the cards in the deck must not exceed 20");
                break;
            case 6:
                CollectionMenu.showMessage("there's a hero in the deck already");
                break;
            case 7:
                CollectionMenu.showMessage("card doesn't exist in the deck");
                break;
            case 8:
                CollectionMenu.showMessage("deck is invalid");
                break;
            case 9:
                CollectionMenu.showMessage("deck not found");
                break;
        }
    }

    //setters
    public static void setIsInCollectionMenu(boolean isInLoginMenu) {
        CollectionMenu.isInCollectionMenu = isInLoginMenu;
    }
    //setters

    public static int help() {
        showMessage("exit");
        showMessage("show");
        showMessage("search [card name | item name]");
        showMessage("save");
        showMessage("create deck [deck name]");
        showMessage("delete deck [deck name]");
        showMessage("add [card id | item id | hero id] to deck [deck name]");
        showMessage("remove [card id | item id | hero id] from deck [deck name]");
        showMessage("validate deck [deck name]");
        showMessage("select deck [deck name]");
        showMessage("show all decks");
        showMessage("show deck [deck name]");
        return 0;
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }
}