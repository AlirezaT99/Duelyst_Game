package view;

import model.Account;
import model.Shop;
import presenter.ShopMenuProcess;

import java.io.IOException;
import java.util.Scanner;

public class ShopMenu {
    private boolean isInShopMenu = true;
    private ShopMenuProcess shopMenuProcess = new ShopMenuProcess();
    private static Shop shop = Shop.initShop();

    public ShopMenu(Account account) {
        shopMenuProcess.setShopMenu(this);
        shopMenuProcess.setCurrentAccount(account);
        // we'll be adding stuff here
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        help();
        while (true) {
            if (!isInShopMenu)
                break;
            // if(scanner.hasNextLine()){
            String command = scanner.nextLine();
            String[] commandParts = command.split("[ ]");
            ShopMenuProcess.commandParts = commandParts;
            int commandType = presenter.ShopMenuProcess.findPatternIndex(command, commandParts);
            if (commandType == -1)
                System.out.println("invalid input");
            else
                handleErrors(shopMenuProcess.DoCommands[commandType].doIt());
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
                showMessage("Card/Item not found in shop.");
                break;
            case 2:
                showMessage("Card/Item not found in collection.");
                break;
            case 3:
                showMessage("Card/Item is out of stock");
                break;
            case 4:
                showMessage("Not enough drake");
                break;
            case 5:
                showMessage("Maximum items are in the Collection");
                break;
            case 6:
                showMessage("You don't have this card/item.");
                break;
            case 10:
                CollectionMenu.showMessage("Card/Item not found");
        }
    }

    public static int help() {
        showMessage("exit");
        showMessage("show collection");
        showMessage("search [item name|card name]");
        showMessage("search collection [item name|card name]");
        showMessage("buy [card name|item name]");
        showMessage("sell [card id|item id]");
        showMessage("show");
        showMessage("help");
        return 0;
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

    public void setIsInShopMenu(boolean isInLoginMenu) {
        this.isInShopMenu = isInLoginMenu;
    }

    public ShopMenuProcess getShopMenuProcess() {
        return shopMenuProcess;
    }

    public Shop getShop() {
        return shop;
    }
}
