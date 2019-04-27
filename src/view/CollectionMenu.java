package view;

import model.Account;
import presenter.CollectionMenuProcess;

import java.io.IOException;
import java.util.Scanner;

public class CollectionMenu {
    private boolean isInCollectionMenu = true;
    private Account currentAccount;
    private CollectionMenuProcess collectionMenuProcess;

    public CollectionMenu(Account account) {
        currentAccount = account;
        collectionMenuProcess = new CollectionMenuProcess();
        collectionMenuProcess.setAccount(currentAccount);
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!isInCollectionMenu)
                break;
            String command = scanner.nextLine();
            CollectionMenuProcess.commandParts = command.split("[ ]");
            int commandType = presenter.CollectionMenuProcess.findPatternIndex(command);
            if (commandType == -1)
                System.out.println("invalid input");
            else
                handleErrors(collectionMenuProcess.DoCommands[commandType].doIt());
        }
        scanner.close();
    }

    private static void handleErrors(int messageID) {
        switch (messageID) {
            case 1:
                CollectionMenu.showMessage("A deck with this name already exists");
                break;
            case 2:
                CollectionMenu.showMessage("Card/Item doesn't exist");
                break;
            case 3:
                CollectionMenu.showMessage("No card/item with this id was found in the collection");
                break;
            case 4:
                CollectionMenu.showMessage("Card/Item is already in the deck");
                break;
            case 5:
                CollectionMenu.showMessage("Number of the cards in the deck must not exceed 20");
                break;
            case 6:
                CollectionMenu.showMessage("There's supposed to be only one hero in the deck");
                break;
            case 7:
                CollectionMenu.showMessage("Card doesn't exist in the deck");
                break;
            case 8:
                CollectionMenu.showMessage("Deck is invalid");
                break;
            case 9:
                CollectionMenu.showMessage("Deck not found");
                break;
            case 10:
                CollectionMenu.showMessage("Card/Item not found");
                break;
        }
    }

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

    public void setIsInCollectionMenu(boolean isInCollectionMenu) {
        this.isInCollectionMenu = isInCollectionMenu;
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }
}