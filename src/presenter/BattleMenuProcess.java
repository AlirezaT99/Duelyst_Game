package presenter;

import model.*;
import view.BattleMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BattleMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private BattleMenu battleMenu;
    public String[] commandParts;
    private static Match match;

    public BattleMenuProcess(BattleMenu battleMenu) {
        this.battleMenu = battleMenu;
    }

    static { // card id format ??
        commandPatterns.add(Pattern.compile("Game Info"));
        commandPatterns.add(Pattern.compile("Show my minions"));
        commandPatterns.add(Pattern.compile("Show opponent minions"));
        commandPatterns.add(Pattern.compile("Show card info [a-zA-Z0-9._]+"));
//        commandPatterns.add(Pattern.compile("Select [a-zA-Z0-9._]+"));
//        commandPatterns.add(Pattern.compile("Move to (\\d, \\d)"));
//        commandPatterns.add(Pattern.compile("Attack [a-zA-Z0-9._]+"));
//        commandPatterns.add(Pattern.compile("Attack combo [a-zA-Z0-9._]+ [a-zA-Z0-9._]+ [[a-zA-Z0-9._]+]*"));
//        commandPatterns.add(Pattern.compile("Use special power (\\d, \\d)"));
        commandPatterns.add(Pattern.compile("Show hand"));
        commandPatterns.add(Pattern.compile("Insert \\w+ in (\\d, \\d)"));
        commandPatterns.add(Pattern.compile("End turn"));
        commandPatterns.add(Pattern.compile("Show collectibles"));
        commandPatterns.add(Pattern.compile("Select [a-zA-Z0-9._]+"));
//        commandPatterns.add(Pattern.compile("Show info [a-zA-Z0-9._]+"));
//        commandPatterns.add(Pattern.compile("Use [location x, y]")); // regex ?
        commandPatterns.add(Pattern.compile("Show next card"));
        commandPatterns.add(Pattern.compile("Enter graveyard"));
//        commandPatterns.add(Pattern.compile("Show info [a-zA-Z0-9._]+"));
//        commandPatterns.add(Pattern.compile("Show cards"));
        commandPatterns.add(Pattern.compile("Help"));
        commandPatterns.add(Pattern.compile("End Game"));
        commandPatterns.add(Pattern.compile("Exit"));
        commandPatterns.add(Pattern.compile("Show menu"));
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
            this::gameInfo,
            new DoCommand() {
                @Override
                public int doIt() {
                    return showSoldiers(match.currentTurnPlayer());
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return showSoldiers(match.notCurrentTurnPlayer());
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return showCardInfo(commandParts[3]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return selectCard(commandParts[1]);
                }
            },
//            new DoCommand() {
//                @Override
//                public int doIt() {
//                    return moveTo(commandParts[2], commandParts[3]);
//                }
//            },
//            new DoCommand() {
//                @Override
//                public int doIt() {
//                    return attack(commandParts[1]);
//                }
//            },
//            new DoCommand() {
//                @Override
//                public int doIt() {
//                    return attackCombo(commandParts);
//                }
//            },
//            new DoCommand() {
//                @Override
//                public int doIt() {
//                    return useSpecialPower(commandParts[3], commandParts[4]);
//                }
//            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return showHand(match.currentTurnPlayer());
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return insertCard(commandParts[1], commandParts[3], commandParts[4]);
                }
            },
            this::endTurn,
            this::showCollectibles,
            new DoCommand() {
                @Override
                public int doIt() {
                    return selectCollectible(commandParts[1]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return showNextCard(match.currentTurnPlayer());
                }
            },
            this::enterGraveyard,
            this::battleHelp,
            this::endGame,
            this::exit,
            BattleMenu::showMenu
    };

    private int enterGraveyard() {
        return 10;
    }

    private int selectCard(String cardID) {
        if (findCard(cardID) == null)
            return 2;
        return 5;
    }

    public static void showGraveYardCards() {

    }

    public static int showGraveYardCardInfo(String cardID) {
        for (Card card : match.player1_graveyard)
            if (cardID.equals(card.getCardID()))
                if (match.getPlayer1().equals(match.currentTurnPlayer()))
                    return showCardInfo(cardID);
        return -1;
    }

    private int endGame() {
        //todo: move to Main Menu

        // must be called when another functions has found the winner and given out the rewards
        return 0;
    }

    private int battleHelp() {
        return 0;
    }

    private int showNextCard(Player currentTurnPlayer) {
        return 0;
    }

    private int selectCollectible(String commandPart) {
        return 0;
    }

    private int showCollectibles() {
        return 0;
    }

    private int endTurn() {
        match.switchTurn();
        match.currentTurnPlayer().fillHand(); // ?
        return 0;
    }

    private int insertCard(String cardName, String x_str, String y_str) {
        int x = Integer.parseInt(x_str),
                y = Integer.parseInt(y_str);
        return 0;
    }

    public static int useSpecialPower(String x_str, String y_str) {
        int x = Integer.parseInt(x_str),
                y = Integer.parseInt(y_str);
        return 0;
    }

    public static int attack(String cardID) {
        if (findCard(cardID) == null)
            return 3;
        Card attackedCard = findCard(cardID);
        if (attackedCard instanceof MovableCard)
            ((MovableCard) match.currentTurnPlayer().getHand().getSelectedCard())
                    .attack((MovableCard) attackedCard);
        return 0;
    }

    public static int attackCombo(String[] commandParts) {
//        Attack combo [opponent card id] [my card id] [my card id] [...]
        for (int i = 2; i < commandParts.length; i++)
            if (findCard(commandParts[i]) == null || !(findCard(commandParts[i]) instanceof MovableCard))
                return 3;

        ArrayList<MovableCard> attackers = new ArrayList<>();
        for (int i = 3; i < commandParts.length; i++)
            attackers.add((MovableCard) findCard(commandParts[i]));
        ((MovableCard) match.currentTurnPlayer().getHand().getSelectedCard())
                .comboAttack(attackers, (MovableCard) findCard(commandParts[2]));
        return 0;
    }

    private int showHand(Player currentTurnPlayer) {
        BattleMenu.showMessage(currentTurnPlayer.getHand().showHand());
        return 0;
    }

    public static int moveTo(String card_x, String card_y) {
        int x = Integer.parseInt(card_x),
                y = Integer.parseInt(card_y);
        if (x > 5 || y > 9 || x < 1 || y < 1)
            return 1;
        //
        int moveValid = 0;
        if (match.currentTurnPlayer().getHand().getSelectedCard() instanceof MovableCard)
            moveValid = ((MovableCard) match.currentTurnPlayer().getHand().getSelectedCard())
                    .isMoveValid(match.getTable().getCell(x, y));
        if (moveValid != 0) return moveValid;
        //
        ((MovableCard) match.currentTurnPlayer().getHand().getSelectedCard()).move(match.getTable().getCell(x, y));
//        match.currentTurnPlayer().getHand().getSelectedCard().setCoordination(new Coordination(x, y));
        BattleMenu.showMessage(match.currentTurnPlayer().getHand().getSelectedCard().getCardID()
                + "moved to [" + x + "][" + y + "]");
        return 0;
    }

    private static int showCardInfo(String cardID) {
        if (match.getPlayer1().getCollection().getSelectedDeck().getHero().getCardID().equals(cardID))
            showInfo(match.getPlayer1().getCollection().getSelectedDeck().getHero());
        if (match.getPlayer2().getCollection().getSelectedDeck().getHero().getCardID().equals(cardID))
            showInfo(match.getPlayer2().getCollection().getSelectedDeck().getHero());
        for (Card minion : match.getPlayer1().getCollection().getSelectedDeck().getMinions())
            if (minion.getCardID().equals(cardID))
                showInfo(minion);
        for (Card minion : match.getPlayer2().getCollection().getSelectedDeck().getMinions())
            if (minion.getCardID().equals(cardID))
                showInfo(minion);
        for (Card spell : match.getPlayer1().getCollection().getSelectedDeck().getSpells())
            if (spell.getCardID().equals(cardID))
                showInfo(spell);
        for (Card spell : match.getPlayer2().getCollection().getSelectedDeck().getSpells())
            if (spell.getCardID().equals(cardID))
                showInfo(spell);
        return 0;
    }

    private static void showInfo(Card card) {
        if (card instanceof Hero)
            System.out.println("Hero:\nName: " + card.getName() + "\nHP: " + ((Hero) card).getHealth()
                    + "\nAP: " + ((Hero) card).getDamage() + "\nMP: " + card.getManaCost()
                    + "\nCost: " + card.getCost() + "\nDesc: " + card.getDescription());
        else if (card instanceof Minion)
            System.out.println("Minion:\nName: " + card.getName() + "\nHP: " + ((Minion) card).getHealth()
                    + "\nAP: " + ((Minion) card).getDamage() + "\nMP: " + card.getManaCost()
                    + "\nCost: " + card.getCost() + "\nDesc: " + card.getDescription());
        else if (card instanceof Spell)
            System.out.println("Spell:\nName: " + card.getName()
                    + "\nMP: " + card.getManaCost() + "\nCost: " + card.getCost()
                    + "\nDesc: " + card.getDescription());
    }

    private static Card findCard(String cardID) {
        if (match.getPlayer1().getCollection().getSelectedDeck().getHero().getCardID().equals(cardID))
            return match.getPlayer1().getCollection().getSelectedDeck().getHero();
        if (match.getPlayer2().getCollection().getSelectedDeck().getHero().getCardID().equals(cardID))
            return match.getPlayer2().getCollection().getSelectedDeck().getHero();
        for (Minion minion : match.getPlayer1().getCollection().getSelectedDeck().getMinions())
            if (minion.getCardID().equals(cardID))
                return minion;
        for (Minion minion : match.getPlayer2().getCollection().getSelectedDeck().getMinions())
            if (minion.getCardID().equals(cardID))
                return minion;
        for (Spell spell : match.getPlayer1().getCollection().getSelectedDeck().getSpells())
            if (spell.getCardID().equals(cardID))
                return spell;
        for (Spell spell : match.getPlayer2().getCollection().getSelectedDeck().getSpells())
            if (spell.getCardID().equals(cardID))
                return spell;
        return null;
    }

    private int showSoldiers(Player player) {
        showMinion(player.getCollection().getSelectedDeck().getHero());
        for (Minion minion : player.getCollection().getSelectedDeck().getMinions())
            showMinion(minion);
        return 0;
    }

    private void showMinion(MovableCard soldier) {
        BattleMenu.showMessage(soldier.getCardID() + " : " + soldier.getName() + ", health : " + soldier.getHealth()
                + ", location : (" + soldier.getCoordination().getX() + "," + soldier.getCoordination().getY()
                + "), power : " + soldier.getDamage());
    }

    private int gameInfo() {
        switch (match.getGameMode()) {
            case 1:
                System.out.println("Player1 Hero Health = "
                        + match.getPlayer1().getCollection().getSelectedDeck().getHero());
                System.out.println("Player2 Hero Health = "
                        + match.getPlayer2().getCollection().getSelectedDeck().getHero());
                break;
            case 2:
                // location & holder of flag
                break;
            case 3:
                // soldiers' names who hold flags + their team names
                break;
        }
        return 0;
    }

    public int exit() throws IOException { // ok ?
        battleMenu.setInBattleMenu(false);
        battleMenu.getBattleInit().setHasRun(false);
        battleMenu.getBattleInit().setInBattleInit(true);
        battleMenu.getBattleInit().run();
        return 0;
    }

    //setters
    public static void setMatch(Match match) {
        BattleMenuProcess.match = match;
    }
    //setters
}
