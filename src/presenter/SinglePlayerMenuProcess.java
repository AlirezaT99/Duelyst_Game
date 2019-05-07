package presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import model.*;
import view.BattleMenu;
import view.SinglePlayerMenu;
import view.StoryMenu;

public class SinglePlayerMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private static SinglePlayerMenu singlePlayerMenu;
    public String[] commandParts;
    private Account account;

    public SinglePlayerMenuProcess(SinglePlayerMenu singlePlayerMenu) {
        SinglePlayerMenuProcess.singlePlayerMenu = singlePlayerMenu;
    }

    static {
        commandPatterns.add(Pattern.compile("enter story|story|1"));
        commandPatterns.add(Pattern.compile("enter custom game|custom game|2"));
        commandPatterns.add(Pattern.compile("exit|3"));
        commandPatterns.add(Pattern.compile("help|4"));
    }

    public int customGame(String command, Hero hero) throws IOException {
        String[] commandParts = command.split("\\s+");
        String deckName = commandParts[2]; // space ke nadare vasatesh?
        if (!account.getCollection().validateDeck(account.getCollection().getDeckHashMap().get(deckName)))
            return 4;
        int mode = Integer.parseInt(commandParts[3]);
        int numberOfFlags = -1;
        if (commandParts.length == 5) numberOfFlags = Integer.parseInt(commandParts[4]);
        Match match = new Match(true, mode, numberOfFlags);
        Deck deck = getSampleDecks().get(0).copy();
        deck.setHero(hero.copy());
        deck.getHero().setCardID("computer_" + deck.getHero().getName() + "_1");
        match.setup(account, deckName, numberOfFlags, deck);
        BattleMenu battleMenu = new BattleMenu(singlePlayerMenu.getBattleInit(), match);
        enterBattleMenu(battleMenu);
        return 0;
    }

    private static void enterBattleMenu(BattleMenu battleMenu) throws IOException {
        singlePlayerMenu.setHasRun(false);
        singlePlayerMenu.setInSinglePlayerMenu(false);
        battleMenu.setInBattleMenu(true);
        battleMenu.run();
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

    public ArrayList<Deck> getSampleDecks() {
        ArrayList<Deck> decks = new ArrayList<>();
        Deck deck1 = new Deck("deck1");
        deck1.addSpellToDeck(Spell.getSpellByName("Area Dispel"));
        deck1.addSpellToDeck(Spell.getSpellByName("Empower"));
        deck1.addSpellToDeck(Spell.getSpellByName("God Strength"));
        deck1.addSpellToDeck(Spell.getSpellByName("Madness"));
        deck1.addSpellToDeck(Spell.getSpellByName("Poison Lake"));
        deck1.addSpellToDeck(Spell.getSpellByName("Health with Profit"));
        deck1.addSpellToDeck(Spell.getSpellByName("Kings Guard"));
        deck1.addMinionToDeck(Minion.getMinionByName("Persian Swordsman"));
        deck1.addMinionToDeck(Minion.getMinionByName("Persian Lancer"));
        deck1.addMinionToDeck(Minion.getMinionByName("The Persian Pahlevoon"));
        deck1.addMinionToDeck(Minion.getMinionByName("Tourani GhollabSangDaar"));
        deck1.addMinionToDeck(Minion.getMinionByName("Tourani Prince"));
        deck1.addMinionToDeck(Minion.getMinionByName("The Eagle"));
        deck1.addMinionToDeck(Minion.getMinionByName("The Eagle"));
        deck1.addMinionToDeck(Minion.getMinionByName("FireBreathing Dragon"));
        deck1.addMinionToDeck(Minion.getMinionByName("The Leopard"));
        deck1.addMinionToDeck(Minion.getMinionByName("Goblin"));
        deck1.addMinionToDeck(Minion.getMinionByName("Giiv"));
        deck1.addMinionToDeck(Minion.getMinionByName("Iraj"));
        deck1.addMinionToDeck(Minion.getMinionByName("Shah Ghoul"));
        deck1.addItemToDeck(UsableItem.getUsableItemByName("Soul Eater"));
        deck1.getItems().get(0).setItemID("computer_SoulEater_1");
        StoryMenuProcess.setDeckCardIDs("computer", deck1);
        decks.add(deck1);
        ////
        Deck deck = new Deck("deck2");
        deck.addSpellToDeck(Spell.getSpellByName("HellFire"));
        deck.addSpellToDeck(Spell.getSpellByName("All Disarm"));
        deck.addSpellToDeck(Spell.getSpellByName("Dispel"));
        deck.addSpellToDeck(Spell.getSpellByName("Power Up"));
        deck.addSpellToDeck(Spell.getSpellByName("All power"));
        deck.addSpellToDeck(Spell.getSpellByName("All Attack"));
        deck.addSpellToDeck(Spell.getSpellByName("Weakening"));
        deck.addMinionToDeck(Minion.getMinionByName("Persian WarLord"));
        deck.addMinionToDeck(Minion.getMinionByName("Tourani Archer"));
        deck.addMinionToDeck(Minion.getMinionByName("Tourani Spy"));
        deck.addMinionToDeck(Minion.getMinionByName("StoneLauncher Ghoul"));
        deck.addMinionToDeck(Minion.getMinionByName("HogRider Div"));
        deck.addMinionToDeck(Minion.getMinionByName("HogRider Div"));
        deck.addMinionToDeck(Minion.getMinionByName("The Fierce Lion"));
        deck.addMinionToDeck(Minion.getMinionByName("The Wolf"));
        deck.addMinionToDeck(Minion.getMinionByName("The Witch"));
        deck.addMinionToDeck(Minion.getMinionByName("The Wild Swine"));
        deck.addMinionToDeck(Minion.getMinionByName("Piraan"));
        deck.addMinionToDeck(Minion.getMinionByName("Bahman"));
        deck.addMinionToDeck(Minion.getMinionByName("The Great Ghoul"));
        deck.addItemToDeck(UsableItem.getUsableItemByName("Terror Hood"));
        deck.setHero(Hero.getHeroByName("Aarash"));
        deck.getItems().get(0).setItemID("computer_TerrorHood_1");
        StoryMenuProcess.setDeckCardIDs("computer", deck);
        return decks;
    }

    //getters
    public Account getAccount() {
        return account;
    }
    //getters

    //setters
    public void setAccount(Account account) {
        this.account = account;
    }
    //setters
}
