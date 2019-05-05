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
                    return addToDeck(commandParts[1], commandParts[4]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return removeFromDeck(commandParts[1], commandParts[4]);
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
                public int doIt() {
                    return validateDeck(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
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
                public int doIt() {
                    return showAllDecks();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return showDeck(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
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
                public int doIt() {
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

    private int addToDeck(String idStr, String deckName) { // is id an integer or a string after all???
        if (!account.getCollection().getDeckHashMap().containsKey(deckName))
            return 9;
        Deck deck = account.getCollection().getDeckHashMap().get(deckName);
        if (!account.getCollection().getItemsHashMap().containsKey(idStr)
                && account.getCollection().findCardByCollectionID(idStr) == null)
            return 3;
        if (deck.getItemsHashMap().containsKey(idStr) ||
                deck.findCardByID(idStr)!=null)
            return 4;
        if ((deck.getMinions().size()+deck.getSpells().size()) == Deck.MAX_CARD_NUMBER
                && !account.getCollection().getItemsHashMap().containsKey(idStr))
            if (!(account.getCollection().findCardByCollectionID(idStr) instanceof Hero))
                return 5;
//                && account.getCollection().getItemsHashMap().size() == Deck.MAX_ITEM_NUMBER
//                && !account.getCollection().getItemsHashMap().containsKey(idStr))
        // if (!(account.getCollection().getCardHashMap().get(idStr) instanceof Hero))

        if (account.getCollection().findCardByCollectionID(idStr) != null)
            if (account.getCollection().findCardByCollectionID(idStr) instanceof Hero
                    && deck.getHero() != null)
                return 6;
        //

        if (account.getCollection().getItemsHashMap().containsKey(idStr)
                && account.getCollection().getDeckHashMap().get(deckName).getItems().size() == 1) {
            return 12;
        }
        if (account.getCollection().findCardByCollectionID(idStr) != null)
            if (account.getCollection().findCardByCollectionID(idStr) instanceof Hero) {
                Hero hero = ((Hero) account.getCollection().findCardByCollectionID(idStr));
                Hero heroCopy = hero.copy();
                heroCopy.setCardID(createCardID(account.getUserName(), deck, heroCopy));
                deck.setHero(hero.copy());
                return 0;
            }
        if (account.getCollection().getItemsHashMap().containsKey(idStr)
                && account.getCollection().getItemsHashMap().size() < Deck.MAX_ITEM_NUMBER) {
            deck.getItemsHashMap()
                    .put(idStr, account.getCollection().getItemsHashMap().get(idStr).copy());
            deck.getItems().add(account.getCollection().getItemsHashMap().get(idStr).copy());
            return 0;
        }
        if (account.getCollection().findCardByCollectionID(idStr) != null) {
            if (account.getCollection().findCardByCollectionID(idStr) instanceof Spell) {
                Spell spellCopy = ((Spell) account.getCollection().findCardByCollectionID(idStr)).copy();
                spellCopy.setCardID(createCardID(account.getUserName(), deck, spellCopy));
                deck.getSpells().add(spellCopy);
            }
            if (account.getCollection().findCardByCollectionID(idStr) instanceof Minion) {
                Minion minionCopy = ((Minion) account.getCollection().findCardByCollectionID(idStr)).copy();
                minionCopy.setCardID(createCardID(account.getUserName(), deck, minionCopy));
                deck.getMinions().add(minionCopy);
            }
            return 0;
        }
        return 0;
    }

    private int removeFromDeck(String idStr, String deckName) {
        Deck deck = account.getCollection().getDeckHashMap().get(deckName);
        if (deck.findCardByID(idStr) == null
                && !deck.getItemsHashMap().containsKey(idStr))
            return 3;
        if (account.getCollection().findCardByID(idStr) instanceof Hero
                && deck.getHero() != null
                && deck.getHero().getCardID().equals(idStr)) {
            deck.setHero(null);
            return 0;
        }
        if (account.getCollection().findCardByID(idStr) instanceof Spell
                && deck.findCardByID(account.getCollection().findCardByID(idStr).getName())!=null) {
            deck.getSpells().remove(account.getCollection().findCardByID(idStr));
            return 0;
        }
        if (account.getCollection().findCardByID(idStr) instanceof Minion
                && deck.findCardByID(account.getCollection().findCardByID(idStr).getName())!=null) {
            deck.getMinions().remove(account.getCollection().findCardByID(idStr));
            return 0;
        }
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

    private int show() {
        CollectionMenu.showMessage(account.getCollection().show(true));
        return 0;
    }

    private int showDeck(String deckName) {
        if(account.getCollection().getDeckHashMap().get(deckName)!=null)
        CollectionMenu.showMessage(account.getCollection().getDeckHashMap().get(deckName).show(false));
        else
            return 8;
        return 0;
    }

    private int showAllDecks() {
        if (account.getCollection().getSelectedDeck() != null) {
            CollectionMenu.showMessage("1 : " + account.getCollection().getSelectedDeck().getName() + " :");
            showDeck(account.getCollection().getSelectedDeck().getName());
            int idx = 2;
            for (int i = 0; i < account.getCollection().getDecks().size(); i++) {
                if (!account.getCollection().getDecks().get(idx).getName()
                        .equals(account.getCollection().getSelectedDeck().getName())) {
                    CollectionMenu.showMessage(idx + " : " + account.getCollection().getDecks().get(idx).getName() + " :");
                    showDeck(account.getCollection().getDecks().get(idx).getName());
                    idx++;
                }
            }
        } else
            for (int i = 0; i < account.getCollection().getDecks().size(); i++) {
                CollectionMenu.showMessage((i + 1) + " : " + account.getCollection().getDecks().get(i).getName() + " :");
                showDeck(account.getCollection().getDecks().get(i).getName());
            }
        return 0;
    }

    public static int search(String name, Account account) {
        if (account.getCollection().findItemByName(name) == null && account.getCollection().findCardByName(name) == null)
            return 10;
        String result = "";
        if (account.getCollection().findItemByName(name) != null) {
            for (int i = 0; i < account.getCollection().getItems().size(); i++)
                if (account.getCollection().getItems().get(i).getName().equals(name))
                    result += (account.getCollection().getItems().get(i).getItemID() + "\n");
            CollectionMenu.showMessage(result);
        } else if (account.getCollection().findCardByName(name) != null) {
            for (int i = 0; i < account.getCollection().getMinions().size(); i++)
                if (account.getCollection().getMinions().get(i).getName().equals(name))
                    result += (account.getCollection().getMinions().get(i).getCardID() + "\n");
            for (int i = 0; i < account.getCollection().getSpells().size(); i++)
                if (account.getCollection().getSpells().get(i).getName().equals(name))
                    result += (account.getCollection().getSpells().get(i).getCardID() + "\n");
            for (int i = 0; i < account.getCollection().getHeroes().size(); i++)
                if (account.getCollection().getHeroes().get(i).getName().equals(name))
                    result += (account.getCollection().getHeroes().get(i).getCardID() + "\n");
            CollectionMenu.showMessage(result);
        }
        return 0;
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
