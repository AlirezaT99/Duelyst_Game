package presenter;


import model.*;
import view.BattleMenu;
import view.StoryMenu;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
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

    private int enterFirstLevel() throws IOException {
        Deck deck = new Deck("computerDeck");
        deck.addSpellToDeck(Spell.getSpellByName("Total Disarm"));
        deck.addSpellToDeck(Spell.getSpellByName("Lightning Bolt"));
        deck.addSpellToDeck(Spell.getSpellByName("All Disarm"));
        deck.addSpellToDeck(Spell.getSpellByName("All Poison"));
        deck.addSpellToDeck(Spell.getSpellByName("Dispel"));
        deck.addSpellToDeck(Spell.getSpellByName("Sacrifice"));
        deck.addSpellToDeck(Spell.getSpellByName("Kings Guard"));
        deck.addMinionToDeck(Minion.getMinionByName("Persian Archer"));
        deck.addMinionToDeck(Minion.getMinionByName("Tourani Lancer"));
        deck.addMinionToDeck(Minion.getMinionByName("Tourani MaceBearer"));
        deck.addMinionToDeck(Minion.getMinionByName("Tourani MaceBearer"));
        deck.addMinionToDeck(Minion.getMinionByName("Black Div"));
        deck.addMinionToDeck(Minion.getMinionByName("Cyclops"));
        deck.addMinionToDeck(Minion.getMinionByName("The Viper"));
        deck.addMinionToDeck(Minion.getMinionByName("The Giant Snake"));
        deck.addMinionToDeck(Minion.getMinionByName("The White Direwolf"));
        deck.addMinionToDeck(Minion.getMinionByName("The Witch Master"));
        deck.addMinionToDeck(Minion.getMinionByName("The Ice Queen"));
        deck.addMinionToDeck(Minion.getMinionByName("Siavash"));
        deck.addMinionToDeck(Minion.getMinionByName("Arzhang Div"));
        deck.addItemToDeck(UsableItem.getUsableItemByName("The Crown of Knowledge"));
        deck.setHero(Hero.getHeroByName("Div e Sefid"));
        deck.getItems().get(0).setItemID("computer_TheCrownOfKnowledge_1");
        setDeckCardIDs("computer",deck);
        Match match = new Match(true, 1);
        match.setup(storyMenu.getSinglePlayerMenu().getSinglePlayerMenuProcess().getAccount(),
                storyMenu.getSinglePlayerMenu().getSinglePlayerMenuProcess()
                        .getAccount().getCollection().getSelectedDeck().getName(), 0);
        match.getPlayer2().setDeck(deck);
        BattleMenu battleMenu = new BattleMenu(storyMenu.getSinglePlayerMenu().getBattleInit(), match);
        enterBattleMenu(battleMenu);
        return 0;
    }

    private int enterSecondLevel() throws IOException {
        Deck deck = new Deck("computerDeck");
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
        setDeckCardIDs("computer",deck);
        Match match = new Match(true, 3);
        match.setup(storyMenu.getSinglePlayerMenu().getSinglePlayerMenuProcess().getAccount(),
                storyMenu.getSinglePlayerMenu().getSinglePlayerMenuProcess()
                        .getAccount().getCollection().getSelectedDeck().getName(), 1);
        match.getPlayer2().setDeck(deck);
        BattleMenu battleMenu = new BattleMenu(storyMenu.getSinglePlayerMenu().getBattleInit(), match);
        enterBattleMenu(battleMenu);
        return 0;
    }

    private int enterThirdLevel() throws IOException{
        Deck deck = new Deck("computerDeck");
        deck.addSpellToDeck(Spell.getSpellByName("Area Dispel"));
        deck.addSpellToDeck(Spell.getSpellByName("Empower"));
        deck.addSpellToDeck(Spell.getSpellByName("God Strength"));
        deck.addSpellToDeck(Spell.getSpellByName("Madness"));
        deck.addSpellToDeck(Spell.getSpellByName("Poison Lake"));
        deck.addSpellToDeck(Spell.getSpellByName("Health with Profit"));
        deck.addSpellToDeck(Spell.getSpellByName("Kings Guard"));
        deck.addMinionToDeck(Minion.getMinionByName("Persian Swordsman"));
        deck.addMinionToDeck(Minion.getMinionByName("Persian Lancer"));
        deck.addMinionToDeck(Minion.getMinionByName("The Persian Pahlevoon"));
        deck.addMinionToDeck(Minion.getMinionByName("Tourani GhollabSangDaar"));
        deck.addMinionToDeck(Minion.getMinionByName("Tourani Prince"));
        deck.addMinionToDeck(Minion.getMinionByName("The Eagle"));
        deck.addMinionToDeck(Minion.getMinionByName("The Eagle"));
        deck.addMinionToDeck(Minion.getMinionByName("FireBreathing Dragon"));
        deck.addMinionToDeck(Minion.getMinionByName("The Leopard"));
        deck.addMinionToDeck(Minion.getMinionByName("Goblin"));
        deck.addMinionToDeck(Minion.getMinionByName("Giiv"));
        deck.addMinionToDeck(Minion.getMinionByName("Iraj"));
        deck.addMinionToDeck(Minion.getMinionByName("Shah Ghoul"));
        deck.addItemToDeck(UsableItem.getUsableItemByName("Soul Eater"));
        deck.setHero(Hero.getHeroByName("Zahhak"));
        deck.getItems().get(0).setItemID("computer_SoulEater_1");
        setDeckCardIDs("computer",deck);
        Match match = new Match(true, 2);
        match.setup(storyMenu.getSinglePlayerMenu().getSinglePlayerMenuProcess().getAccount(),
                storyMenu.getSinglePlayerMenu().getSinglePlayerMenuProcess()
                        .getAccount().getCollection().getSelectedDeck().getName(), 7);
        match.getPlayer2().setDeck(deck);
        BattleMenu battleMenu = new BattleMenu(storyMenu.getSinglePlayerMenu().getBattleInit(), match);
        enterBattleMenu(battleMenu);
        return 0;
    }
    private  void enterBattleMenu(BattleMenu battleMenu) throws IOException {
        storyMenu.setHasRun(false);
        storyMenu.setInStoryMenu(false);
        battleMenu.setInBattleMenu(true);
        battleMenu.run();
    }


    public int exit() throws IOException {
        storyMenu.setInStoryMenu(false);
        storyMenu.getSinglePlayerMenu().setHasRun(false);
        storyMenu.getSinglePlayerMenu().setInSinglePlayerMenu(true);
        storyMenu.getSinglePlayerMenu().run();
        return 0;
    }

    public static void setDeckCardIDs(String playerName, Deck deck){ //only use it for story mode.
        deck.getHero().setCardID(CollectionMenuProcess.createCardID(playerName,deck,deck.getHero()));
        for(int i = 0; i < deck.getMinions().size() ; i++){
            int num = 0;
            for( int j = 0; j < deck.getMinions().size() ; j++) {
                if (deck.getMinions().get(j).getName().equals(deck.getMinions().get(i).getName())
                    && (deck.getMinions().get(j).getCardID() != null && deck.getMinions().get(j).getCardID() != ""))
                    num++;
            }
            deck.getMinions().get(i).setCardID(CollectionMenuProcess.createCardID(playerName,deck,deck.getMinions().get(i)));
        }
        for(int i = 0; i < deck.getSpells().size() ; i++){
            int num = 0;
            for( int j = 0; j < deck.getSpells().size() ; j++){
                if (deck.getSpells().get(j).getName().equals(deck.getSpells().get(i).getName())
                        && (deck.getSpells().get(j).getCardID() != null && deck.getSpells().get(j).getCardID() != ""))
                    num++;
            }
            deck.getSpells().get(i).setCardID(CollectionMenuProcess.createCardID(playerName,deck,deck.getSpells().get(i)));
        }
    }
}
