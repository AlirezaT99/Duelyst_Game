package presenter;

import model.Account;
import view.BattleInit;
import view.MultiPlayerMenu;
import view.SinglePlayerMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BattleInitProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private BattleInit battleInit;
    private Account account;
    public String[] commandParts;

    public BattleInitProcess(BattleInit battleInit) {
        this.battleInit = battleInit;
    }

    static {
        commandPatterns.add(Pattern.compile("enter single player|single player|1"));
        commandPatterns.add(Pattern.compile("enter multi player|multi player|2"));
        commandPatterns.add(Pattern.compile("exit|3"));
        commandPatterns.add(Pattern.compile("help|4"));
    }

    public interface DoCommand {
        int doIt() throws IOException;
    }

    public static int findPatternIndex(String command) {
        for (int i = 0; i < commandPatterns.size(); i++)
            if (command.toLowerCase().matches(commandPatterns.get(i).pattern()))
                return i;
        return -1;
    }

    public DoCommand[] DoCommands = new DoCommand[]{
            this::enterSinglePlayer,
            this::enterMultiPlayer,
            this::exit,
            BattleInit::help
    };

    private int enterSinglePlayer() throws IOException {
        SinglePlayerMenu singlePlayerMenu = new SinglePlayerMenu(battleInit, account);
        singlePlayerMenu.setHasRun(false);
        battleInit.setInBattleInit(false);
        singlePlayerMenu.setInSinglePlayerMenu(true);
        singlePlayerMenu.run();
        return 0;
    }

    private int enterMultiPlayer() throws IOException {
        MultiPlayerMenu multiPlayerMenu = new MultiPlayerMenu(battleInit, account);
        multiPlayerMenu.setHasRun(false);
        battleInit.setInBattleInit(false);
        multiPlayerMenu.setInMultiPlayerMenu(true);
        multiPlayerMenu.run();
        return 0;
    }

    public int exit() throws IOException {
        battleInit.setInBattleInit(false);
        battleInit.getMainMenu().setHasRun(false);
        battleInit.getMainMenu().setIsInMainMenu(true);
        battleInit.getMainMenu().run();
        return 0;
    }

    //setters
    public void setAccount(Account account) {
        this.account = account;
    }
    //setters
}