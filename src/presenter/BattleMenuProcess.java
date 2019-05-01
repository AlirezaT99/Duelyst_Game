package presenter;

import model.Match;
import view.BattleMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BattleMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private BattleMenu battleMenu;
    public String[] commandParts;
    private static Match match;

    public BattleMenuProcess(BattleMenu battleMenu) {
        this.battleMenu = battleMenu;
    }

    static { // card id format ??
            commandPatterns.add(Pattern.compile("Game Info"));
            commandPatterns.add(Pattern.compile("Show my minions"));
            commandPatterns.add(Pattern.compile("Show opponent minions"));
            commandPatterns.add(Pattern.compile("Show card info [a-zA-Z0-9._]+"));
            commandPatterns.add(Pattern.compile("Select [a-zA-Z0-9._]+"));
            commandPatterns.add(Pattern.compile("Show card info [a-zA-Z0-9._]+"));
            commandPatterns.add(Pattern.compile("Move to (\\d, \\d)"));
            commandPatterns.add(Pattern.compile("Show card info [a-zA-Z0-9._]+"));
            commandPatterns.add(Pattern.compile("Attack [a-zA-Z0-9._]+"));
            commandPatterns.add(Pattern.compile("Attack combo [a-zA-Z0-9._]+ [a-zA-Z0-9._]+ [[a-zA-Z0-9._]+]*"));
            commandPatterns.add(Pattern.compile("Use special power (\\d, \\d)"));
            commandPatterns.add(Pattern.compile("Show hand"));
            commandPatterns.add(Pattern.compile("Insert [card name] in (\\d, \\d)"));
            commandPatterns.add(Pattern.compile("End turn"));
            commandPatterns.add(Pattern.compile("Show collectibles"));
            commandPatterns.add(Pattern.compile("Select [collectible id]"));
            commandPatterns.add(Pattern.compile("Show info"));
            commandPatterns.add(Pattern.compile("Use [location x, y]")); // regex ?
            commandPatterns.add(Pattern.compile("Show next card"));
            commandPatterns.add(Pattern.compile("Enter graveyard"));
            commandPatterns.add(Pattern.compile("Show info [a-zA-Z0-9._]+"));
            commandPatterns.add(Pattern.compile("Show cards"));
            commandPatterns.add(Pattern.compile("Help"));
            commandPatterns.add(Pattern.compile("End Game"));
            commandPatterns.add(Pattern.compile("Help"));
            commandPatterns.add(Pattern.compile("Show menu"));
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
                    return 0;
                }
            },
            BattleMenu::help
    };

    public int exit() throws IOException { // ok?
        battleMenu.setInBattleMenu(false);
        battleMenu.getBattleInit().setHasRun(false);
        battleMenu.getBattleInit().setInBattleInit(true);
        battleMenu.getBattleInit().run();
        return 0;
    }

    //setters
    public static void setMatch(Match match) {
        BattleMenuProcess.match = match;
    }
    //setters
}
