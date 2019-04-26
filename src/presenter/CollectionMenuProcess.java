package presenter;

import model.*;
import view.CollectionMenu;
import view.MainMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CollectionMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private Account account;
    private Player player;
    public static String[] commandParts;
    private MainMenu mainMenu;
    private CollectionMenu collectionMenu;

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

    private int exit() throws IOException {
        mainMenu.setIsInMainMenu(true);
        mainMenu.setHasRun(false);
        collectionMenu.setIsInCollectionMenu(false);
        mainMenu.run(); // ?
        return 0;
    }

    private int search(String name) {
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

    private int addToDeck(String idStr, String deckName) { // is id an integer or a string after all???
        if (!account.getCollection().getDeckHashMap().containsKey(deckName))
            return 9;
        if (!account.getCollection().getCardsHashMap().containsKey(idStr)
                && !account.getCollection().getItemsHashMap().containsKey(idStr))
            return 3;
        if (account.getCollection().getCardsHashMap().containsKey(idStr)
                || account.getCollection().getItemsHashMap().containsKey(idStr))
            return 4;
        if (account.getCollection().getCardsHashMap().size() == Deck.MAX_CARD_NUMBER
                && account.getCollection().getItemsHashMap().size() == Deck.MAX_ITEM_NUMBER
                && !account.getCollection().getItemsHashMap().containsKey(idStr))
            if (!(account.getCollection().getCardsHashMap().get(idStr) instanceof MovableCard.Hero))
                return 5;
        if (account.getCollection().getCardsHashMap().containsKey(idStr))
            if (account.getCollection().getCardsHashMap().get(idStr) instanceof MovableCard.Hero
                    && account.getCollection().getDeckHashMap().get(deckName).getHero() != null)
                return 6;
        //
        if (account.getCollection().getCardsHashMap().containsKey(idStr))
            if (account.getCollection().getCardsHashMap().get(idStr) instanceof MovableCard.Hero) {
                account.getCollection().getDeckHashMap().get(deckName)
                        .setHero((MovableCard.Hero) account.getCollection().getCardsHashMap().get(idStr));
                return 0;
            }
        if (account.getCollection().getItemsHashMap().containsKey(idStr)
                && account.getCollection().getItemsHashMap().size() < Deck.MAX_ITEM_NUMBER) {
            account.getCollection().getDeckHashMap().get(deckName).getItemsHashMap()
                    .put(idStr, account.getCollection().getItemsHashMap().get(idStr));
            return 0;
        }
        if (account.getCollection().getCardsHashMap().containsKey(idStr)) {
            account.getCollection().getDeckHashMap().get(deckName).getCardsHashMap()
                    .put(idStr, account.getCollection().getCardsHashMap().get(idStr));
            return 0;
        }
        return 0; // should be checked for bugs
    }

    private int removeFromDeck(String idStr, String deckName) {
        if (!account.getCollection().getDeckHashMap().get(deckName).getCardsHashMap().containsKey(idStr)
                && !account.getCollection().getDeckHashMap().get(deckName).getItemsHashMap().containsKey(idStr))
            return 3;
        if (account.getCollection().getCardsHashMap().get(idStr) instanceof MovableCard.Hero
                && account.getCollection().getDeckHashMap().get(deckName).getHero() != null
                && account.getCollection().getDeckHashMap().get(deckName).getHero().getCardID().equals(idStr)) {
            account.getCollection().getDeckHashMap().get(deckName).setHero(null);
            return 0;
        }
        account.getCollection().getDeckHashMap().get(deckName).getCardsHashMap().remove(idStr);
        account.getCollection().getDeckHashMap().get(deckName).getItemsHashMap().remove(idStr);
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

    private int showAllDecks() {
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
