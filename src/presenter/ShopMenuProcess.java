package presenter;

import model.*;
import view.CollectionMenu;
import view.MainMenu;
import view.ShopMenu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static model.Shop.findCardByName;
import static model.Shop.findItemByName;

public class ShopMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    public static String[] commandParts;
    private static Account currentAccount;
    private static ShopMenu shopMenu;
    private MainMenu mainMenu;

    static {
        commandPatterns.add(Pattern.compile("exit"));
        commandPatterns.add(Pattern.compile("show collection"));
        commandPatterns.add(Pattern.compile("search [a-zA-Z0-9._]+[ ]*[a-zA-Z0-9._]*[ ]*[a-zA-Z0-9._]*[ ]* [a-zA-Z0-9._]*"));
        commandPatterns.add(Pattern.compile("search collection [a-zA-Z0-9._]+[ ]*[a-zA-Z0-9._]*[ ]*[a-zA-Z0-9._]*[ ]*[a-zA-Z0-9._]*"));
        commandPatterns.add(Pattern.compile("buy [a-zA-Z0-9._]+[ ]*[a-zA-Z0-9._]*[ ]*[a-zA-Z0-9._]*"));
        commandPatterns.add(Pattern.compile("sell [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("show"));
        commandPatterns.add(Pattern.compile("help"));
    }

    public interface DoCommand {
        int doIt() throws IOException;
    }

    public DoCommand[] DoCommands = new DoCommand[]{
            this::exit,
            ShopMenuProcess::showCollection,
            new DoCommand() {
                @Override
                public int doIt() {
                    if (commandParts.length == 5)
                        return search(commandParts[1] + " " + commandParts[2] + " " + commandParts[3] + " " + commandParts[4]);
                    if (commandParts.length == 4)
                        return search(commandParts[1] + " " + commandParts[2] + " " + commandParts[3]);
                    if (commandParts.length == 3)
                        return search(commandParts[1] + " " + commandParts[2]);
                    if (commandParts.length == 2)
                        return search(commandParts[1]);
                    return 0;
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws FileNotFoundException {
                    if (commandParts.length == 6)
                        return searchCollection(commandParts[2] + " " + commandParts[3] + " " + commandParts[4] + " " + commandParts[5], currentAccount);
                    if (commandParts.length == 5)
                        return searchCollection(commandParts[2] + " " + commandParts[3] + " " + commandParts[4], currentAccount);
                    if (commandParts.length == 4)
                        return searchCollection(commandParts[2] + " " + commandParts[3], currentAccount);
                    if (commandParts.length == 3)
                        return searchCollection(commandParts[2], currentAccount);
                    return 0;
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws FileNotFoundException {
                    if (commandParts.length == 5)
                        return buy(commandParts[1] + " " + commandParts[2] + " " + commandParts[3] + " " + commandParts[4]);
                    if (commandParts.length == 4)
                        return buy(commandParts[1] + " " + commandParts[2] + " " + commandParts[3]);
                    if (commandParts.length == 3)
                        return buy(commandParts[1] + " " + commandParts[2]);
                    if (commandParts.length == 2)
                        return buy(commandParts[1]);
                    return 0;
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws FileNotFoundException {
                    return sell(commandParts[1]);
                }
            },
            this::show,
            ShopMenu::help
    };

    public static int findPatternIndex(String command, String[] commandParts) {
        if (commandParts.length == 2 && commandParts[0].toLowerCase().equals("search"))
            return 2;
        if (commandParts.length == 3 && commandParts[0].toLowerCase().equals("search")
                && commandParts[1].toLowerCase().equals("collection"))
            return 3;
        if (commandParts.length == 2 && commandParts[0].toLowerCase().equals("buy"))
            return 4;
        if (commandParts.length == 2 && commandParts[0].toLowerCase().equals("sell"))
            return 5;
        for (int i = 0; i < commandPatterns.size(); i++) {
            if (command.toLowerCase().matches(commandPatterns.get(i).pattern()))
                return i;
        }
        return -1;
    }

    private int exit() throws IOException {
        mainMenu.setIsInMainMenu(true);
        shopMenu.setIsInShopMenu(false);
        mainMenu.run();
        return 0;
    }

    public static int showCollection() throws FileNotFoundException {
        CollectionMenu.showMessage(currentAccount.getCollection().show(true));
        return 0;
    }

    public static int search(String name) {
        String str = Shop.search(name);
        if (str.equals("-1"))
            return -1;
        int i = Integer.parseInt(str) + 1;
        System.out.println(i + ""); // TODO gotta change this stuff here and now -_-
        return i;
    }

    public static int searchCollection(String name, Account currentAccount) throws FileNotFoundException {
        if (Shop.searchCollection(name, currentAccount).equals("Item/Card not found in collection"))
            return 2;
        return CollectionMenuProcess.search(name, currentAccount);
//        ShopMenu.showMessage(shopMenu.getShop().searchCollection(name, currentAccount));
//        return 0;
    }

    private int buy(String name) throws FileNotFoundException {
        if (shopMenu.getShop().buy(currentAccount, name) == 0)
            ShopMenu.showMessage("purchase was successful.");
        else
            return shopMenu.getShop().buy(currentAccount, name);
        return 0;
    }

    public static boolean isDrakeEnough(int cardPrice,long accountMoney){
        return accountMoney >= cardPrice;
    }

    private int sell(String id) throws FileNotFoundException {
        if (shopMenu.getShop().sell(currentAccount, id) == 0)
            ShopMenu.showMessage("trade was successful.");
        else
            return shopMenu.getShop().sell(currentAccount, id);
        return 0;
    }

    private int show() throws FileNotFoundException {
//        ShopMenu.showMessage("Heroes :");
//        for (int i = 0; i < Shop.getShopHeroes().size(); i++) {
//            Hero hero = Shop.getShopHeroes().get(i);
//            ShopMenu.showMessage("          " + (i + 1) + " : " + hero.toString(false) + " Buy Cost : " +
//                    hero.getCost());
//        }
//        ShopMenu.showMessage("Items :");
//        for (int i = 0; i < Shop.getShopItems().size(); i++) {
//            ShopMenu.showMessage("          " + (i + 1) + " : " + Shop.getShopItems().get(i).toString(false) + " Buy Cost : " +
//                    Shop.getShopItems().get(i).getCost());
//        }
//        ShopMenu.showMessage("Cards : ");
//        ShopMenu.showMessage("  Spells : ");
//        for (int i = 0; i < Shop.getShopSpells().size(); i++) {
//            Spell spell = Shop.getShopSpells().get(i);
//            ShopMenu.showMessage("            " + (i + 1) + " : " + spell.toString(false) + " Buy Cost : " + spell.getCost());
//        }
//        ShopMenu.showMessage("  Minions : ");
//        for (int i = 0; i < Shop.getShopMinions().size(); i++) {
//            Minion minion = Shop.getShopMinions().get(i);
//            ShopMenu.showMessage("            " + (i + 1) + " : " + minion.toString(false) + " Buy Cost : " + minion.getCost());
//        }
        return 0;
    }

    //setters
    public void setShopMenu(ShopMenu shopMenu) {
        this.shopMenu = shopMenu;
    }

    void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }
    //setters
}
