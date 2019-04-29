package presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import model.Account;
import model.Match;
import view.MultiPlayerMenu;

public class MultiPlayerMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private MultiPlayerMenu multiPlayerMenu;
    public String[] commandParts;
    private static Account account;
    private static Account opponent;

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
        String[] commandParts = command.split("\\s+");
        String deckName = commandParts[2]; // space ke nadare vasatesh?
        int mode = Integer.parseInt(commandParts[3]);
        int numberOfFlags = -1;
        if (commandParts.length == 5) numberOfFlags = Integer.parseInt(commandParts[4]);
        Match match = new Match(false, mode);
        match.setup(account, opponent, deckName, numberOfFlags);
    }

    private int selectUser(String opponentUserName) {
        for (Account account : Account.getAccounts())
            if (account.getUserName().equals(opponentUserName)) {
                opponent = account;
                return 0;
            }
        return 2;
    }


    private int gameInit() {
        return 3;
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
        MultiPlayerMenuProcess.account = account;
    }
    //setters
}
