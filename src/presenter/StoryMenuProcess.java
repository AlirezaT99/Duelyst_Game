package presenter;

import model.Match;
import view.StoryMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class StoryMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private StoryMenu storyMenu;
    public String[] commandParts;

    public StoryMenuProcess(StoryMenu storyMenu) {
        this.storyMenu = storyMenu;
    }

    static {
        commandPatterns.add(Pattern.compile("enter 1"));
        commandPatterns.add(Pattern.compile("1"));
        commandPatterns.add(Pattern.compile("enter 2"));
        commandPatterns.add(Pattern.compile("2"));
        commandPatterns.add(Pattern.compile("enter 3"));
        commandPatterns.add(Pattern.compile("3"));
        commandPatterns.add(Pattern.compile("enter 4"));
        commandPatterns.add(Pattern.compile("enter exit"));
        commandPatterns.add(Pattern.compile("exit"));
        commandPatterns.add(Pattern.compile("4"));
        commandPatterns.add(Pattern.compile("enter 5"));
        commandPatterns.add(Pattern.compile("enter help"));
        commandPatterns.add(Pattern.compile("help"));
        commandPatterns.add(Pattern.compile("5"));
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
                    return enterFirstLevel();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterFirstLevel();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterSecondLevel();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterSecondLevel();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterThirdLevel();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return enterThirdLevel();
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
                public int doIt() throws IOException {
                    return storyMenu.help();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return storyMenu.help();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return storyMenu.help();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return storyMenu.help();
                }
            }
    };

    public int enterFirstLevel() {
        return 0;
    }

    public int enterSecondLevel() {
        return 0;
    }

    public int enterThirdLevel() {
        return 0;
    }

    public int exit() throws IOException {
        storyMenu.setInStoryMenu(false);
        storyMenu.getSinglePlayerMenu().setHasRun(false);
        storyMenu.getSinglePlayerMenu().setInSinglePlayerMenu(true);
        storyMenu.getSinglePlayerMenu().run();
        return 0;
    }
}
