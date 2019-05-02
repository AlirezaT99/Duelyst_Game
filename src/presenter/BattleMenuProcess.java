package presenter;

import model.Match;
import model.Player;
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
        commandPatterns.add(Pattern.compile("Move to (\\d, \\d)"));
        commandPatterns.add(Pattern.compile("Attack [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("Attack combo [a-zA-Z0-9._]+ [a-zA-Z0-9._]+ [[a-zA-Z0-9._]+]*"));
        commandPatterns.add(Pattern.compile("Use special power (\\d, \\d)"));
        commandPatterns.add(Pattern.compile("Show hand"));
        commandPatterns.add(Pattern.compile("Insert \\w+ in (\\d, \\d)"));
        commandPatterns.add(Pattern.compile("End turn"));
        commandPatterns.add(Pattern.compile("Show collectibles"));
        commandPatterns.add(Pattern.compile("Select [a-zA-Z0-9._]+"));
//        commandPatterns.add(Pattern.compile("Show info"));
//        commandPatterns.add(Pattern.compile("Use [location x, y]")); // regex ?
        commandPatterns.add(Pattern.compile("Show next card"));
        commandPatterns.add(Pattern.compile("Enter graveyard"));
//        commandPatterns.add(Pattern.compile("Show info [a-zA-Z0-9._]+"));
//        commandPatterns.add(Pattern.compile("Show cards"));
        commandPatterns.add(Pattern.compile("Help"));
        commandPatterns.add(Pattern.compile("End Game"));
        commandPatterns.add(Pattern.compile("Exit"));
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
            this::gameInfo,
            new DoCommand() {
                @Override
                public int doIt() {
                    return showMinions(match.currentTurnPlayer());
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return showMinions(match.notCurrentTurnPlayer());
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return showCardInfo(commandParts[3]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return moveTo(commandParts[2], commandParts[3]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return attack(commandParts[1]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return attackCombo(commandParts);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return useSpecialPower(commandParts[3], commandParts[4]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return showHand(match.currentTurnPlayer());
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return insertCard(commandParts[1], commandParts[3], commandParts[4]);
                }
            },
            this::endTurn,
            this::showCollectibles,
            new DoCommand() {
                @Override
                public int doIt() {
                    return selectCollectible(commandParts[1]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return showNextCard(match.currentTurnPlayer());
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return enterGraveyard(match.currentTurnPlayer());
                }
            },
            this::battleHelp,
            this::endGame,
            this::exit,
            BattleMenu::showMenu
    };

    private int endGame() {
        return 0;
    }

    private int battleHelp() {
        return 0;
    }

    private int enterGraveyard(Player currentTurnPlayer) {
        return 0;
    }

    private int showNextCard(Player currentTurnPlayer) {
        return 0;
    }

    private int selectCollectible(String commandPart) {
        return 0;
    }

    private int showCollectibles() {
        return 0;
    }

    private int endTurn() {
        match.switchTurn();
        //fill hand
        return 0;
    }

    private int insertCard(String commandPart, String commandPart1, String commandPart2) {
        return 0;
    }

    private int showHand(Player currentTurnPlayer) {
        return 0;
    }

    private int useSpecialPower(String commandPart, String commandPart1) {
        return 0;
    }

    private int attackCombo(String[] commandParts) {
        return 0;
    }

    private int attack(String commandPart) {
        return 0;
    }

    private int moveTo(String commandPart, String commandPart1) {
        return 0;
    }

    private int showCardInfo(String commandPart) {
        return 0;
    }

    private int showMinions(Player currentTurnPlayer) {
        return 0;
    }

    private int gameInfo() {
        return 0;
    }

    public int exit() throws IOException { // ok ?
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
