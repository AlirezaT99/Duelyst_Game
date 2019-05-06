package presenter;

import model.*;
import view.CollectionMenu;
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
                    if(commandParts.length == 5)
                        return search(commandParts[1]+" "+commandParts[2]+" "+commandParts[3]+" "+commandParts[4]);
                    if(commandParts.length == 4)
                        return search(commandParts[1]+" "+commandParts[2]+" "+commandParts[3]);
                    if(commandParts.length == 3)
                        return search(commandParts[1]+" "+commandParts[2]);
                    if(commandParts.length == 2)
                        return search(commandParts[1]);
                    return 0;
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    if(commandParts.length == 6)
                        return searchCollection(commandParts[2]+" "+commandParts[3]+" "+commandParts[4]+" "+commandParts[5]);
                    if(commandParts.length == 5)
                        return searchCollection(commandParts[2]+" "+commandParts[3]+" "+commandParts[4]);
                    if(commandParts.length == 4)
                        return searchCollection(commandParts[2]+" "+commandParts[3]);
                    if(commandParts.length == 3)
                        return searchCollection(commandParts[2]);
                    return 0;
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    if(commandParts.length == 5)
                        return buy(commandParts[1]+" "+commandParts[2]+" "+commandParts[3]+ " " + commandParts[4]);
                    if(commandParts.length == 4)
                        return buy(commandParts[1]+" "+commandParts[2]+" "+commandParts[3]);
                    if(commandParts.length == 3)
                        return buy(commandParts[1]+" "+commandParts[2]);
                    if(commandParts.length == 2)
                        return buy(commandParts[1]);
                    return 0;
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
        shopMenu.setIsInShopMenu(false);
        mainMenu.run();
        return 0;
    }

    private int showCollection() {
        CollectionMenu.showMessage(currentAccount.getCollection().show(true));
        return 0;
    }

    private int search(String name) {
        String s = shopMenu.getShop().search(name);
        if (s.equals("-1"))
            return 1;
        int i = Integer.parseInt(s)+1;
        ShopMenu.showMessage(i+"");
        return 0;
    }

    private int searchCollection(String name) {
        if (shopMenu.getShop().searchCollection(name, currentAccount).equals("Item/Card not found in collection"))
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
        for (int i = 0; i < Shop.getShopHeroes().size(); i++) {
            Hero hero = Shop.getShopHeroes().get(i);
            ShopMenu.showMessage("          "+(i + 1) + " : " + hero.toString(false) +" Buy Cost : "+
                    hero.getCost());
        }
        ShopMenu.showMessage("Items :");
        for (int i = 0; i < Shop.getShopItems().size(); i++) {
            ShopMenu.showMessage("          "+(i + 1) + " : " + Shop.getShopItems().get(i).toString(false) + " Buy Cost : " +
                    Shop.getShopItems().get(i).getCost());
        }
        ShopMenu.showMessage("Cards : ");
        ShopMenu.showMessage("  Spells : ");
        for (int i = 0; i < Shop.getShopSpells().size(); i++) {
            Spell spell = Shop.getShopSpells().get(i);
            ShopMenu.showMessage("            "+(i + 1) + " : " + spell.toString(false) + " Buy Cost : " + spell.getCost());
        }
        ShopMenu.showMessage("  Minions : ");
        for (int i = 0; i < Shop.getShopMinions().size(); i++) {
            Minion minion = Shop.getShopMinions().get(i);
            ShopMenu.showMessage("            "+ (i + 1) + " : " + minion.toString(false) + " Buy Cost : " + minion.getCost());
        }
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
