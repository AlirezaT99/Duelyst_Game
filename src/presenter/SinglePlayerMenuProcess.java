package presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import model.Account;
import model.Match;
import view.SinglePlayerMenu;
import view.StoryMenu;

public class SinglePlayerMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private SinglePlayerMenu singlePlayerMenu;
    public String[] commandParts;
    private static Account account;

    public SinglePlayerMenuProcess(SinglePlayerMenu singlePlayerMenu) {
        this.singlePlayerMenu = singlePlayerMenu;
    }

    static {
        commandPatterns.add(Pattern.compile("enter story|story|1"));
        commandPatterns.add(Pattern.compile("enter custom game|custom game|2"));
        commandPatterns.add(Pattern.compile("exit|3"));
        commandPatterns.add(Pattern.compile("help|4"));
    }

    public static void customGame(String command) {
        String[] commandParts = command.split("\\s+");
        String deckName = commandParts[2]; // space ke nadare vasatesh?
        int mode = Integer.parseInt(commandParts[3]);
        int numberOfFlags = -1;
        if (commandParts.length == 5) numberOfFlags = Integer.parseInt(commandParts[4]);
        Match match = new Match(true, mode);
        match.setup(account, deckName, numberOfFlags);
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
            SinglePlayerMenu::help
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
        return 4;
    }

    public int exit() throws IOException {
        singlePlayerMenu.setInSinglePlayerMenu(false);
        singlePlayerMenu.getBattleInit().setHasRun(false);
        singlePlayerMenu.getBattleInit().setInBattleInit(true);
        singlePlayerMenu.getBattleInit().run();
        return 0;
    }

    //setters
    public void setAccount(Account account) {
        SinglePlayerMenuProcess.account = account;
    }
    //setters
}
