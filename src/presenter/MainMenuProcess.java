package presenter;

import model.Account;
import view.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private Account currentAccount;
    public static String[] commandParts;
    private MainMenu mainMenu;
    private LoginMenu loginMenu;
    private BattleInit battleInit;

    static {
        commandPatterns.add(Pattern.compile("enter collection|collection|1"));
        commandPatterns.add(Pattern.compile("enter shop|shop|2"));
        commandPatterns.add(Pattern.compile("enter battle|battle|3"));
        commandPatterns.add(Pattern.compile("enter exit|exit|4"));
        commandPatterns.add(Pattern.compile("enter help|help|5"));
    }

    public interface DoCommand {
        int doIt() throws IOException;
    }

    public DoCommand[] DoCommands = new DoCommand[]{
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterCollection();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterShop();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterBattle(); //todo: argument haye enterBattle ro moshakhas konim bade zadane battle.
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return exit();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return MainMenu.help();
                }
            }
    };

    private int enterCollection() throws IOException {
        mainMenu.setIsInMainMenu(false);
        CollectionMenu collectionMenu = new CollectionMenu(currentAccount);
        collectionMenu.setIsInCollectionMenu(true);
        collectionMenu.run();
        return 0;
    }

    private int enterShop() throws IOException {
        mainMenu.setIsInMainMenu(false);
        ShopMenu shopMenu = new ShopMenu(currentAccount);
        shopMenu.setIsInShopMenu(true);
        shopMenu.getShopMenuProcess().setMainMenu(mainMenu);
        shopMenu.run();
        return 0;
    }

    private int enterBattle() throws IOException {
        if(!currentAccount.getCollection().validateDeck(currentAccount.getCollection().getSelectedDeck()))
            return 1; //message : "selected deck is invalid"
        battleInit = new BattleInit(mainMenu);
        battleInit.run();
        return 0;
    }

    private int exit() throws IOException {
        mainMenu.setIsInMainMenu(false);
        loginMenu.setIsInLoginMenu(true);
        loginMenu.run();
        return 0;
    }

    public static int findPatternIndex(String command) {
        for (int i = 0; i < commandPatterns.size(); i++)
            if (command.toLowerCase().matches(commandPatterns.get(i).pattern()))
                return i;
        return -1;
    }

    //setters
    public void setCurrentAccount(Account account) {
        this.currentAccount = account;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    void setLoginMenu(LoginMenu loginMenu) {
        this.loginMenu = loginMenu;
    }
    //setters
}
