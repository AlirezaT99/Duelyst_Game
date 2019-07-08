package presenter;

import model.*;
import view.CollectionMenu;
import view.MainMenu;

import java.io.FileNotFoundException;
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
        commandPatterns.add(Pattern.compile("search [a-zA-Z0-9._]+[ ]*[a-zA-Z0-9._]*[ ]*[a-zA-Z0-9._]*"));
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
                public int doIt() {
                    return createDeck(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return deleteDeck(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return addToDeck(account, commandParts[1], commandParts[4]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return removeFromDeck(account, commandParts[1], commandParts[4]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return selectDeck(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws FileNotFoundException {
                    return validateDeck(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws FileNotFoundException {
                    if (commandParts.length == 5)
                        return search(commandParts[1] + " " + commandParts[2] + " " + commandParts[3] + " " + commandParts[4], account);
                    if (commandParts.length == 4)
                        return search(commandParts[1] + " " + commandParts[2] + " " + commandParts[3], account);
                    if (commandParts.length == 3)
                        return search(commandParts[1] + " " + commandParts[2], account);
                    if (commandParts.length == 2)
                        return search(commandParts[1], account);
                    return 0;
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws FileNotFoundException {
                    return showAllDecks(account);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws FileNotFoundException {
                    return showDeck(commandParts[2], account);
                }
            },
            this::show,
            this::save,
            CollectionMenu::help,

            this::exit
    };

    private int save() throws IOException {
        LoginMenuProcess.save(account);
        return 0;
    }

    private int exit() throws IOException {
        mainMenu.setIsInMainMenu(true);
        collectionMenu.setIsInCollectionMenu(false);
        mainMenu.run();
        return 0;
    }

    private int createDeck(String deckName) {
        if (account.getCollection().getDeckHashMap().containsKey(deckName))
            return 1;
        account.getCollection().addDeck(new Deck(deckName));
        return 0;
    }

    private int deleteDeck(String deckName) {
        return account.getCollection().deleteDeck(deckName);
    }

    public int addToDeck(Account account, String name, String deckName) {
        // changed idStr from an argument
        // to a local variable to work with card/item name

        if (!account.getCollection().getDeckHashMap().containsKey(deckName))
            return 9;
        Deck deck = account.getCollection().getDeckHashMap().get(deckName);

        try {
            Hero hero = ((Hero) account.getCollection().findCardByName(name));

            Hero heroCopy = hero.copy();
            heroCopy.setCardID(account.getUserName() + "_" + nameCreator(heroCopy.getName()) + "_1");
            deck.setHero(hero.copy());
            return 0;
        } catch (Exception ignored) {
        }
        try {
            UsableItem item = account.getCollection().findItemByName(name).copy();
            item.setItemID(account.getUserName() + "_" + nameCreator(item.getName()) + "_1");
            deck.getItemsHashMap()
                    .put(item.getName(), item);
            deck.getItems().add(item);
            return 0;
        } catch (Exception ignored) {
        }
        try {
            Spell spellCopy = ((Spell) account.getCollection().findCardByName(name)).copy();
            spellCopy.setCardID(createCardID(account.getUserName(), deck, spellCopy));
            if (deck.getSpells().size() + deck.getMinions().size() < 20)
                deck.getSpells().add(spellCopy);
        } catch (Exception ignored) {
        }
        try {
            Minion minionCopy = ((Minion) account.getCollection().findCardByName(name)).copy();
            minionCopy.setCardID(createCardID(account.getUserName(), deck, minionCopy));
            if (deck.getMinions().size() + deck.getSpells().size() < 20)
                deck.getMinions().add(minionCopy);

            return 0;
        } catch (Exception ignored) {
        }

        return 0;
    }

    public int removeFromDeck(Account account, String name, String deckName) { // changed idStr from an argument
        // to a local variable to work with card/item name
        Deck deck = account.getCollection().getDeckHashMap().get(deckName);
        String idStr;
        try {
            idStr = account.getCollection().findCardByName(name).getCollectionID();
        } catch (NullPointerException ex) {
            idStr = account.getCollection().findItemByName(name).getCollectionID();
        }
        if (deck == null)
            return 9;
        if (deck.findCardByID(idStr) == null && !deck.getItems().isEmpty()
                && !deck.getItems().get(0).getCollectionID().equals(idStr))
            return 3;
        if (account.getCollection().findCardByCollectionID(idStr) instanceof Hero
                && deck.getHero() != null
                && deck.getHero().getCollectionID().equals(idStr)) {
            deck.setHero(null);
            return 0;
        }
        if (account.getCollection().findCardByCollectionID(idStr) instanceof Spell
                && deck.findCardByID(account.getCollection().findCardByCollectionID(idStr).getCollectionID()) != null) {
            for (int i = 0; i < deck.getSpells().size(); i++) {
                if (deck.getSpells().get(i).getCollectionID().equals(idStr)) {
                    deck.getSpells().remove(deck.getSpells().get(i));
                    break;
                }
            }
            return 0;
        }
        if (account.getCollection().findCardByCollectionID(idStr) instanceof Minion
                && deck.findCardByID(account.getCollection().findCardByCollectionID(idStr).getCollectionID()) != null) {
            for (int i = 0; i < deck.getMinions().size(); i++) {
                if (deck.getMinions().get(i).getCollectionID().equals(idStr)) {
                    deck.getMinions().remove(deck.getMinions().get(i));
                    break;
                }
            }
            return 0;
        }

        if ((deck.getItems().size() > 0) && deck.getItems().get(0).getCollectionID().equals(idStr))
            account.getCollection().getDeckHashMap().get(deckName).getItems().remove(0);
        account.getCollection().getDeckHashMap().get(deckName).getItemsHashMap().remove(idStr);
        return 0;
    }

    private int validateDeck(String deckName) throws FileNotFoundException {
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

    private int show() throws FileNotFoundException {
        CollectionMenu.showMessage(account.getCollection().show(true));
        return 0;
    }

    private static int showDeck(String deckName, Account account) throws FileNotFoundException {
        if (account.getCollection().getDeckHashMap().get(deckName) != null)
            CollectionMenu.showMessage(account.getCollection().getDeckHashMap().get(deckName).show());
        else
            return 8;
        return 0;
    }

    public static int showAllDecks(Account account) throws FileNotFoundException {
        if (account.getCollection().getSelectedDeck() != null) {
            CollectionMenu.showMessage("1 : " + account.getCollection().getSelectedDeck().getName() + " :");
            showDeck(account.getCollection().getSelectedDeck().getName(), account);
            int idx = 2;
            for (int i = 0; i < account.getCollection().getDecks().size(); i++) {
                if (!account.getCollection().getDecks().get(i).getName()
                        .equals(account.getCollection().getSelectedDeck().getName())) {
                    CollectionMenu.showMessage(idx + " : " + account.getCollection().getDecks().get(i).getName() + " :");
                    showDeck(account.getCollection().getDecks().get(i).getName(), account);
                    idx++;
                }
            }
        } else
            for (int i = 0; i < account.getCollection().getDeckHashMap().values().size(); i++) {
                CollectionMenu.showMessage((i + 1) + " : " + account.getCollection().getDecks().get(i).getName() + " :");
                showDeck(account.getCollection().getDecks().get(i).getName(), account);
            }
        return 0;
    }

    public static String createCardID(String playerName, Deck deck, Card card) {
        if (card instanceof Hero) {
            System.out.println();
            return playerName + "_" + nameCreator(card.getName()) + "_1";
        }
        if (card instanceof Spell) {
            int idx = 1;
            for (int i = 0; i < deck.getSpells().size(); i++)
                if (deck.getSpells().get(i).getName().equals(card.getName()))
                    idx++;
            return playerName + "_" + nameCreator(card.getName()) + "_" + idx;
        }
        if (card instanceof Minion) {
            int idx = 1;
            for (int i = 0; i < deck.getMinions().size(); i++)
                if (deck.getMinions().get(i).getName().equals(card.getName()))
                    idx++;
            return playerName + "_" + nameCreator(card.getName()) + "_" + idx;
        }
        return "";
    }

    public static int search(String name, Account account) throws FileNotFoundException {
        if (account.getCollection().findItemByName(name) == null && account.getCollection().findCardByName(name) == null)
            return 10;
        String result = "";
        if (account.getCollection().findItemByName(name) != null) {
            for (int i = 0; i < account.getCollection().getItems().size(); i++)
                if (account.getCollection().getItems().get(i).getName().equals(name))
                    result += (account.getCollection().getItems().get(i).getCollectionID() + "\n");
            CollectionMenu.showMessage(result);
        } else if (account.getCollection().findCardByName(name) != null) {
            for (int i = 0; i < account.getCollection().getMinions().size(); i++)
                if (account.getCollection().getMinions().get(i).getName().equals(name))
                    result += (account.getCollection().getMinions().get(i).getCollectionID() + "\n");
            for (int i = 0; i < account.getCollection().getSpells().size(); i++)
                if (account.getCollection().getSpells().get(i).getName().equals(name))
                    result += (account.getCollection().getSpells().get(i).getCollectionID() + "\n");
            for (int i = 0; i < account.getCollection().getHeroes().size(); i++)
                if (account.getCollection().getHeroes().get(i).getName().equals(name))
                    result += (account.getCollection().getHeroes().get(i).getCollectionID() + "\n");
            CollectionMenu.showMessage(result);
        }
        return 0;
    }

    public static String nameCreator(String name) {
        String[] names = name.split("\\s+");
        String result = "";
        for (int i = 0; i < names.length; i++) {
            result += names[i];
        }
        return result;
    }

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

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setCollectionMenu(CollectionMenu collectionMenu) {
        this.collectionMenu = collectionMenu;
    }
    //setters
}
