package presenter;


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
        commandPatterns.add(Pattern.compile("enter 1|1"));
        commandPatterns.add(Pattern.compile("enter 2|2"));
        commandPatterns.add(Pattern.compile("enter 3|3"));
        commandPatterns.add(Pattern.compile("enter exit|exit|4"));
        commandPatterns.add(Pattern.compile("enter help|help|5"));
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
            this::enterFirstLevel,
            this::enterSecondLevel,
            this::enterThirdLevel,
            this::exit,
            new DoCommand() {
                @Override
                public int doIt() {
                    return storyMenu.help();
                }
            }
    };

    private int enterFirstLevel() {

        return 0;
    }

    private int enterSecondLevel() {
        return 0;
    }

    private int enterThirdLevel() {
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
