package presenter;

import model.Deck;
import view.CollectionMenu;
import model.Account;
import model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CollectionMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private Account account;
    private Player player;
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

    public DoCommand[] DoCommands = new DoCommand[]{
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
                    return search(commandParts[1]);
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
                    return showDeck(commandParts[2]);
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
                    return save();
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
        // todo: go to mainMenu
        return 0;
    }

    private int createDeck(String deckName) {
        if (account.getCollection().getDeckHashMap().containsKey(deckName))
            return 1;
        Deck deck = new Deck(deckName);
        account.getCollection().getDeckHashMap().put(deckName, deck);
        account.getCollection().getDecks().add(deck); // gotta choose one to remove (HMap or Arrls)
        return 0;
    }

    private int deleteDeck(String deckName) {
        if (!account.getCollection().getDeckHashMap().containsKey(deckName))
            return 9;
        account.getCollection().getDecks().remove(account.getCollection().getDeckHashMap().get(deckName));
        account.getCollection().getDeckHashMap().remove(deckName);
        return 0;
    }

    private static int addToDeck(String idStr, String deckName) {
        int id = Integer.parseInt(idStr);

        return 0;
    }

    private static int removeFromDeck(String idStr, String deckName) {
        return 0;
    }

    private int validateDeck(String deckName) {
        boolean isValid = account.getCollection().validateDeck(account.getCollection().getDeckHashMap().get(deckName));
        if (isValid) {
            CollectionMenu.showMessage("Deck is Valid");
            return 0;
        }
        return 8;
    }

    private int selectDeck(String deckName) {
        boolean isValid = account.getCollection().validateDeck(account.getCollection().getDeckHashMap().get(deckName));
        if (isValid) {
            account.getCollection().setSelectedDeck(deckName);
            return 0;
        }
        return 8;
    }

    private static int showDeck(String deckName) {
        return 0;
    }

//    private static int search(String name) {
//        if (account.getCollection().search(name).equals("-1")) return 10;
//
//        return 0;
//    }

    public static int findPatternIndex(String command) {
        for (int i = 0; i < commandPatterns.size(); i++)
            if (command.toLowerCase().matches(commandPatterns.get(i).pattern()))
                return i;
        return -1;
    }

    //setters
    public void setAccount(Account account) {
        this.account = account;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    //setters
}
