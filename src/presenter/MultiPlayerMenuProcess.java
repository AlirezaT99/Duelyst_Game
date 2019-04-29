package presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import model.Account;
import view.MultiPlayerMenu;

public class MultiPlayerMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private MultiPlayerMenu multiPlayerMenu;
    public String[] commandParts;
    private Account account;
    private Account opponent;

    public MultiPlayerMenuProcess(MultiPlayerMenu multiPlayerMenu) {
        this.multiPlayerMenu = multiPlayerMenu;
    }

    static {
        commandPatterns.add(Pattern.compile("Select user [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("Start multiPlayer game \\d [\\d+]*")); // format of mode ?
        commandPatterns.add(Pattern.compile("Exit"));
        commandPatterns.add(Pattern.compile("Help"));
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
            new DoCommand() {
                @Override
                public int doIt() {
                    return selectUser(commandParts[2]);
                }
            },
            this::gameInit,
            this::exit,
            MultiPlayerMenu::help
    };

    public static void customGame(String command) {
        String[] commandparts = command.split("\\s+");

    }

    private int selectUser(String opponentUserName) {
        for (Account account : Account.getAccounts())
            if (account.getUserName().equals(opponentUserName)) {
                opponent = account;
                return 0;
            }
        return 0;
    }


    private int gameInit() {
        return 2;
    }

    public int exit() throws IOException {
        multiPlayerMenu.setInMultiPlayerMenu(false);
        multiPlayerMenu.getBattleInit().setHasRun(false);
        multiPlayerMenu.getBattleInit().setInBattleInit(true);
        multiPlayerMenu.getBattleInit().run();
        return 0;
    }

    //setters
    public void setAccount(Account account) {
        this.account = account;
    }
    //setters
}
