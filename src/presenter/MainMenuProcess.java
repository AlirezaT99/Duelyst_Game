package presenter;

import model.Account;
import model.Player;
import view.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private  Account currentAccount;
    public static String[] commandParts;
    private MainMenu mainMenu;
    private LoginMenu loginMenu;
    private BattleInit battleInit;
    static {
        commandPatterns.add(Pattern.compile("enter collection"));
        commandPatterns.add(Pattern.compile("collection"));
        commandPatterns.add(Pattern.compile("1"));
        commandPatterns.add(Pattern.compile("enter shop"));
        commandPatterns.add(Pattern.compile("shop"));
        commandPatterns.add(Pattern.compile("2"));
        commandPatterns.add(Pattern.compile("enter battle"));
        commandPatterns.add(Pattern.compile("battle"));
        commandPatterns.add(Pattern.compile("3"));
        commandPatterns.add(Pattern.compile("enter exit"));
        commandPatterns.add(Pattern.compile("exit"));
        commandPatterns.add(Pattern.compile("4"));
        commandPatterns.add(Pattern.compile("enter help"));
        commandPatterns.add(Pattern.compile("help"));
        commandPatterns.add(Pattern.compile("5"));
    }

    public interface DoCommand {
        int doIt() throws IOException;
    }

    public  DoCommand[] DoCommands = new DoCommand[]{
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterCollection(currentAccount);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterCollection(currentAccount);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterCollection(currentAccount);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterShop(currentAccount);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterShop(currentAccount);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterShop(currentAccount);
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
                    return enterBattle(); //todo: argument haye enterBattle ro moshakhas konim bade zadane battle.
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
                    return enterExit();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterExit();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterExit();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return mainMenu.help();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return mainMenu.help();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return mainMenu.help();
                }
            },
    };

    private  int enterCollection(Account account) throws IOException{
        mainMenu.setIsInMainMenu(false);
        CollectionMenu collectionMenu = new CollectionMenu(currentAccount);
        collectionMenu.setIsInCollectionMenu(true);
        collectionMenu.run();
        return 0;
    }

    private int enterShop(Account account) throws IOException{
        mainMenu.setIsInMainMenu(false);
        ShopMenu shopMenu = new ShopMenu(currentAccount);
        shopMenu.setIsInShopMenu(true);
        shopMenu.getShopMenuProcess().setMainMenu(mainMenu);
        shopMenu.run();
        return 0;
    }
    private int enterBattle() throws IOException{
        battleInit = new BattleInit(mainMenu);
        battleInit.run();
        return 0;
    }
    private int enterExit() throws IOException{
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
    public void setCurrentAccount(Account account){
        this.currentAccount = account;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setLoginMenu(LoginMenu loginMenu) {
        this.loginMenu = loginMenu;
    }
    //setters

}
