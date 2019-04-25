package presenter;

import view.CollectionMenu;
import model.Account;
import model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CollectionMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private static Account currentAccount;
    private static Player player;
    public static String[] commandParts;

    static {
        commandPatterns.add(Pattern.compile("create deck [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("delete deck [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("add \\d+ to deck [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("remove \\d+ from deck [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("select deck [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("validate deck [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("search [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("show all decks"));
        commandPatterns.add(Pattern.compile("show deck [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("show"));
        commandPatterns.add(Pattern.compile("save"));
        commandPatterns.add(Pattern.compile("help"));
        commandPatterns.add(Pattern.compile("exit"));
    }

    public interface DoCommand {
        int doIt() throws IOException;
    }

    public static DoCommand[] DoCommands = new DoCommand[]{
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return createDeck(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return deleteDeck(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return addToDeck(commandParts[1], commandParts[4]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return removeFromDeck(commandParts[1], commandParts[4]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return selectDeck(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return validateDeck(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return showDeck(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return showAllDecks();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return show();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return search(commandParts[1]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return CollectionMenu.help();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return save();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return exit();
                }
            }
    };

    private static int show() {
        return 0;
    }

    private static int save() {
        return 0;
    }

    private static int exit() {
        return 0;
    }

    private static int showAllDecks() {
        return 0;
    }

    private static int createDeck(String commandPart) {
        return 0;
    }

    private static int deleteDeck(String commandPart) {
        return 0;
    }

    private static int addToDeck(String commandPart, String commandPart1) {
        return 0;
    }

    private static int removeFromDeck(String commandPart, String commandPart1) {
        return 0;
    }

    private static int validateDeck(String commandPart) {
        return 0;
    }

    private static int selectDeck(String commandPart) {
        return 0;
    }

    private static int showDeck(String commandPart) {
        return 0;
    }

    private static int search(String commandPart) {
        return 0;
    }

    public static int findPatternIndex(String command, String[] commandParts) {
        for (int i = 0; i < commandPatterns.size(); i++)
            if (command.toLowerCase().matches(commandPatterns.get(i).pattern()))
                return i;
        return -1;
    }
}
