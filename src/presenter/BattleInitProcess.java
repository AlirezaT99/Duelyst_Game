package presenter;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import view.BattleInit;
import view.SinglePlayerMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BattleInitProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private BattleInit battleInit;
    public String[] commandParts;

    public BattleInitProcess(BattleInit battleInit) {
        this.battleInit = battleInit;
    }

    static {
        commandPatterns.add(Pattern.compile("enter single player"));
        commandPatterns.add(Pattern.compile("single player"));
        commandPatterns.add(Pattern.compile("1"));
        commandPatterns.add(Pattern.compile("enter multi player"));
        commandPatterns.add(Pattern.compile("multi player"));
        commandPatterns.add(Pattern.compile("2"));
        commandPatterns.add(Pattern.compile("exit"));
        commandPatterns.add(Pattern.compile("help"));
    }

    public interface DoCommand {
        int doIt() throws IOException;
    }

    public static int findPatternIndex(String command, String[] commandParts) {
        for (int i = 0; i < commandPatterns.size(); i++) {
            if (command.toLowerCase().matches(commandPatterns.get(i).pattern()))
                return i;
        }
        return -1;
    }

    public DoCommand[] DoCommands = new DoCommand[]{
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterSinglePlayer();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterSinglePlayer();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterSinglePlayer();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterMultiPlayer();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterMultiPlayer();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterMultiPlayer();
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
                public int doIt() throws IOException {
                    return battleInit.help();
                }
            }
    };

    public int enterSinglePlayer() throws IOException {
        SinglePlayerMenu singlePlayerMenu = new SinglePlayerMenu(battleInit);
        singlePlayerMenu.setHasRun(false);
        battleInit.setInBattleInit(false);
        singlePlayerMenu.setInSinglePlayerMenu(true);
        singlePlayerMenu.run();
        return 0;
    }

    public int enterMultiPlayer() {
        return 0;
    }

    public int exit() throws IOException {
        battleInit.setInBattleInit(false);
        battleInit.getMainMenu().setHasRun(false);
        battleInit.getMainMenu().setIsInMainMenu(true);
        battleInit.getMainMenu().run();
        return 0;
    }
}
