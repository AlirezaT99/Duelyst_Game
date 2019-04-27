package presenter;

import model.*;
import view.MainMenu;
import view.ShopMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ShopMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    public static String[] commandParts;
    private Account currentAccount;
    private ShopMenu shopMenu;
    private MainMenu mainMenu;

    static {
        commandPatterns.add(Pattern.compile("exit"));
        commandPatterns.add(Pattern.compile("show collection"));
        commandPatterns.add(Pattern.compile("search [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("search collection [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("buy [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("sell [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("show"));
        commandPatterns.add(Pattern.compile("help"));
    }

    public interface DoCommand {
        int doIt() throws IOException;
    }

    public DoCommand[] DoCommands = new DoCommand[]{
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return exit();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return showCollection();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return search(commandParts[1]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return searchCollection(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return buy(commandParts[1]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return sell(commandParts[1]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return show();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return ShopMenu.help();
                }
            }
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
        mainMenu.setHasRun(false);
        shopMenu.setIsInShopMenu(false);
        mainMenu.run();
        return 0;
    }

    private int showCollection() {
        //todo : hamoun showCollection e CollectionMenu + "Cost"
        return 0;
    }

    private int search(String name) {
        if (shopMenu.getShop().search(name).equals("-1"))
            return 1;
        ShopMenu.showMessage(shopMenu.getShop().search(name));
        return 0;
    }

    private int searchCollection(String name) {
        if (shopMenu.getShop().searchCollection(name, currentAccount).equals("-1"))
            return 2;
        ShopMenu.showMessage(shopMenu.getShop().searchCollection(name, currentAccount));
        return 0;
    }

    private int buy(String name) {
        if (shopMenu.getShop().buy(currentAccount, name) == 0)
            ShopMenu.showMessage("purchase was successful.");
        else
            return shopMenu.getShop().buy(currentAccount, name);
        return 0;
    }

    private int sell(String id) {
        if (shopMenu.getShop().sell(currentAccount, id) == 0)
            ShopMenu.showMessage("trade was successful.");
        else
            return shopMenu.getShop().sell(currentAccount, id);
        return 0;
    }

    private int show() {
        ShopMenu.showMessage("Heroes :");
        for (int i = 0; i < shopMenu.getShop().getShopHeroes().size(); i++) {
            MovableCard hero = shopMenu.getShop().getShopHeroes().get(i);
            ShopMenu.showMessage((i + 1) + " : " + shopMenu.getShop().getShopHeroes().get(i) + " Buy Cost : " +
                    hero.getCost());
        }
        ShopMenu.showMessage("Items :");
        for (int i = 0; i < shopMenu.getShop().getShopItems().size(); i++) {
            ShopMenu.showMessage((i + 1) + " : " + shopMenu.getShop().getShopItems().get(i) + " Buy Cost : " +
                    shopMenu.getShop().getShopItems().get(i).getCost());
        }
        ShopMenu.showMessage("Cards : ");
        ShopMenu.showMessage("  Spells : ");
        for (int i = 0; i < shopMenu.getShop().getShopSpells().size(); i++) {
            Spell spell = shopMenu.getShop().getShopSpells().get(i);
            ShopMenu.showMessage("  " + (i + 1) + " : " + spell + " Buy Cost : " + spell.getCost());
        }
        ShopMenu.showMessage("  Minions : ");
        for (int i = 0; i < shopMenu.getShop().getShopSpells().size(); i++) {
            MovableCard minion = shopMenu.getShop().getShopMinions().get(i);
            ShopMenu.showMessage("  " + (i + 1) + " : " + minion + " Buy Cost : " + minion.getCost());
        }
        return 0;
    }

    //setters
    public void setShopMenu(ShopMenu shopMenu) {
        this.shopMenu = shopMenu;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }
    //setters
}
