package presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
        commandPatterns.add(Pattern.compile("enter story|story|1"));
        commandPatterns.add(Pattern.compile("enter custom game|custom game|2"));
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
            this::enterStory,
            this::enterCustomGame,
            this::exit,
            new DoCommand() {
                @Override
                public int doIt() {
                    return singlePlayerMenu.help();
                }
            }
    };

    private int enterStory() throws IOException {
        StoryMenu storyMenu = new StoryMenu(singlePlayerMenu);
        singlePlayerMenu.setInSinglePlayerMenu(false);
        storyMenu.setHasRun(false);
        storyMenu.setInStoryMenu(true);
        storyMenu.run();
        return 0;
    }

    private int enterCustomGame() {
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
