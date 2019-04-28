package presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;


import view.BattleInit;
import view.CustomGameMenu;
import view.SinglePlayerMenu;
import view.StoryMenu;

public class SinglePlayerMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private SinglePlayerMenu singlePlayerMenu;
    public String[] commandParts;

    public SinglePlayerMenuProcess(SinglePlayerMenu singlePlayerMenu) {
        this.singlePlayerMenu = singlePlayerMenu;
    }

    static {
        commandPatterns.add(Pattern.compile("enter story"));
        commandPatterns.add(Pattern.compile("story"));
        commandPatterns.add(Pattern.compile("1"));
        commandPatterns.add(Pattern.compile("enter custom game"));
        commandPatterns.add(Pattern.compile("custom game"));
        commandPatterns.add(Pattern.compile("2"));
        commandPatterns.add(Pattern.compile("exit"));
        commandPatterns.add(Pattern.compile("3"));
        commandPatterns.add(Pattern.compile("help"));
        commandPatterns.add(Pattern.compile("4"));
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
                    return enterStory();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterStory();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterStory();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterCustomGame();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterCustomGame();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterCustomGame();
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
                    return exit();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return singlePlayerMenu.help();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return singlePlayerMenu.help();
                }
            }
    };

    public int enterStory() throws IOException {
        StoryMenu storyMenu = new StoryMenu(singlePlayerMenu);
        singlePlayerMenu.setInSinglePlayerMenu(false);
        storyMenu.setHasRun(false);
        storyMenu.setInStoryMenu(true);
        storyMenu.run();
        return 0;
    }

    public int enterCustomGame() throws IOException {
        CustomGameMenu customGameMenu = new CustomGameMenu(singlePlayerMenu);
        singlePlayerMenu.setInSinglePlayerMenu(false);
        customGameMenu.setHasRun(false);
        customGameMenu.setInCustomGameMenu(true);
        customGameMenu.run();
        return 0;
    }

    public int exit() throws IOException {
        singlePlayerMenu.setInSinglePlayerMenu(false);
        singlePlayerMenu.getBattleInit().setHasRun(false);
        singlePlayerMenu.getBattleInit().setInBattleInit(true);
        singlePlayerMenu.getBattleInit().run();
        return 0;
    }
}
