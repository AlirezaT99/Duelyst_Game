package presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import view.MultiPlayerMenu;

public class MultiPlayerMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private MultiPlayerMenu multiPlayerMenu;
    public String[] commandParts;

    public MultiPlayerMenuProcess(MultiPlayerMenu multiPlayerMenu) {
        this.multiPlayerMenu = multiPlayerMenu;
    }

    static {
        commandPatterns.add(Pattern.compile("Select user [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("Start multiPlayer game \\d [\\d+]*"));
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
            this::selectUser,
            this::gameInit,
            this::exit,
            new DoCommand() {
                @Override
                public int doIt() {
                    return multiPlayerMenu.help();
                }
            }
    };

    private int selectUser() {

        return 0;
    }

    private int gameInit() {

        return 0;
    }

    public int exit() throws IOException {
        multiPlayerMenu.setInMultiPlayerMenu(false);
        multiPlayerMenu.getBattleInit().setHasRun(false);
        multiPlayerMenu.getBattleInit().setInBattleInit(true);
        multiPlayerMenu.getBattleInit().run();
        return 0;
    }
}
