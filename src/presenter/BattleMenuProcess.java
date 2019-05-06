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

    static {
        commandPatterns.add(Pattern.compile("[gG]ame info"));
        commandPatterns.add(Pattern.compile("[sS]how my minions"));
        commandPatterns.add(Pattern.compile("[sS]how opponent minions"));
        commandPatterns.add(Pattern.compile("[sS]how card info [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("[sS]elect [a-zA-Z0-9._]+"));
//        commandPatterns.add(Pattern.compile("Move to (\\d, \\d)"));
//        commandPatterns.add(Pattern.compile("Attack [a-zA-Z0-9._]+"));
//        commandPatterns.add(Pattern.compile("Attack combo [a-zA-Z0-9._]+ [a-zA-Z0-9._]+ [[a-zA-Z0-9._]+]*"));
//        commandPatterns.add(Pattern.compile("Use special power (\\d, \\d)"));
        commandPatterns.add(Pattern.compile("[sS]how hand"));
        commandPatterns.add(Pattern.compile("[iI]nsert [a-zA-Z0-9._ ]+ in \\(\\d,[ ]*\\d\\)"));
        commandPatterns.add(Pattern.compile("[eE]nd turn"));
        commandPatterns.add(Pattern.compile("[sS]how collectibles"));
//        commandPatterns.add(Pattern.compile("Show info [a-zA-Z0-9._]+"));
//        commandPatterns.add(Pattern.compile("Use [location x, y]")); // regex ?
        commandPatterns.add(Pattern.compile("[sS]how next card"));
        commandPatterns.add(Pattern.compile("[eE]nter graveyard"));
//        commandPatterns.add(Pattern.compile("Show info [a-zA-Z0-9._]+"));
//        commandPatterns.add(Pattern.compile("Show cards"));
        commandPatterns.add(Pattern.compile("[hH]elp"));
        commandPatterns.add(Pattern.compile("[eE]nd Game"));
        commandPatterns.add(Pattern.compile("[eE]xit"));
        commandPatterns.add(Pattern.compile("[sS]how menu"));
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
            this::showHand,
            new DoCommand() {
                @Override
                public int doIt() {
                    return insertCard(commandParts);
                }
            },
            this::endTurn,
            this::showCollectibles,
            new DoCommand() {
                @Override
                public int doIt() {
                    return showNextCard();
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
        if (findCard(cardID) == null || !(findCard(cardID) instanceof MovableCard)) //todo: what about spell ??
            return 2;
        match.currentTurnPlayer().getHand().setSelectedCard(findCard(cardID));
        return 5;

        // should go in the collectible subMenu from here
        // todo : check the id to see if is a card or a collectible
    }

    public static void showGraveYardCards() {
        if (match.currentTurnPlayer().getUserName().equals(match.getPlayer1().getUserName())) {
            for (Card card : match.player1_graveyard)
                showCardInfo(card.getCardID());
        } else
            for (Card card : match.player2_graveyard)
                showCardInfo(card.getCardID());
    }

    public static int showGraveYardCardInfo(String cardID) {
        for (Card card : match.player1_graveyard)
            if (cardID.equals(card.getCardID()))
                if (match.getPlayer1().equals(match.currentTurnPlayer()))
                    return showCardInfo(cardID);
        for (Card card : match.player2_graveyard)
            if (cardID.equals(card.getCardID()))
                if (match.getPlayer2().equals(match.currentTurnPlayer()))
                    return showCardInfo(cardID);
        return -1;
    }

    private int endGame() {
        //todo: move to Main Menu

        // must be called when another functions has found the winner and given out the rewards
        return 0;
    }

    private int battleHelp() {
        //todo: show possible choices
        return 0;
    }

    private int showNextCard() {
        Card card = match.currentTurnPlayer().getDeck().getNextCard();
        if (card == null)
            return -1;
        System.out.println(card.toString(false) + "\n");
        return 0;
    }

    private int showCollectibles() {
        return 0;
    }

    public static int showCollectibleInfo() {
        return 0;
    }

    public static int useCollectible() {
        return 0;
    }

    private int endTurn() {
        match.currentTurnPlayer().fillHand();
        resetFlags();
        match.switchTurn();
        if (endGameReached()) {
            //todo : give reward to the winner / create matchHistory / goto mainMenu
        }
        return 0;
    }

    private boolean endGameReached() {
        switch (match.getGameMode()) {
            case 1:
                if (!match.currentTurnPlayer().getDeck().getHero().isAlive() ||
                        !match.notCurrentTurnPlayer().getDeck().getHero().isAlive())
                    return true;
            case 2:
                break;
            case 3:
                break;
        }
        return false;
    }

    private void resetFlags() {
        for (Cell cell : match.getTable().findAllSoldiers(match.currentTurnPlayer()))
            cell.getMovableCard().resetFlags();
    }

    private int insertCard(String[] command) {
        command = cleanupArray(command);
        int x = Integer.parseInt(command[command.length - 2]),
                y = Integer.parseInt(command[command.length - 1]);
        String cardName = "";
        for (int i = 1; !command[i].equals("in"); i++)
            cardName = cardName.concat(command[i] + " ");
        cardName = cardName.trim();
        if (coordinationInvalid(x, y))
            return 7;
        if (match.currentTurnPlayer().getHand().findCardByName(cardName) != null) {
            if (!match.currentTurnPlayer().getHand().findCardByName(cardName)
                    .isManaSufficient(match.currentTurnPlayer().getMana()))
                return 11;
            if (!(match.currentTurnPlayer().getHand().findCardByName(cardName) instanceof Spell)) {
                if (!isCoordinationValidToInsert(x, y))
                    return 12;
            } else {
                Spell spell = (Spell) match.currentTurnPlayer().getHand().findCardByName(cardName);
                if (spell.getPrimaryImpact().isSelectedCellImportant()) {
                    ArrayList<Cell> arrayList = spell.getValidCoordination();
                    if (arrayList.contains(match.getTable().getCell(x, y)))
                        return 12;
                }
            }
        }
        //
        String cardID = match.currentTurnPlayer().getHand().findCardByName(cardName).getCardID();
        if (match.currentTurnPlayer().getHand().findCardByName(cardName) instanceof MovableCard)
            match.currentTurnPlayer().getHand().findCardByName(cardName)
                    .castCard(match.getTable().getCellByCoordination(x, y));
        else if (match.currentTurnPlayer().getHand().findCardByName(cardName) instanceof Spell) // ok?
            ((Spell) match.currentTurnPlayer().getHand().findCardByName(cardName))
                    .castCard(match.getTable().getCellByCoordination(x, y), match.currentTurnPlayer());
        //
        match.currentTurnPlayer().fillHand();
        BattleMenu.showMessage(cardName + " with " + cardID + " inserted to (" + x + "," + y + ")");
        return 0;
    }

    private static String[] cleanupArray(String[] command) {
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0; i < command.length; i++)
            if (!command[i].equals(""))
                output.add(command[i]);
        String[] outputArr = new String[output.size()];
        for (int i = 0; i < output.size(); i++)
            outputArr[i] = output.get(i);
        return outputArr;
    }

    public static int useSpecialPower(String x_str, String y_str) {
        int x = Integer.parseInt(x_str),
                y = Integer.parseInt(y_str);
        if (coordinationInvalid(x, y))
            return 7;

        return 0;
    }

    public static int attack(String cardID) {
        if (findCard(cardID) == null)
            return 3;
        Card attackedCard = findCard(cardID);
        int returnValue = 0;
        if (attackedCard instanceof MovableCard)
            returnValue = ((MovableCard) match.currentTurnPlayer().getHand().getSelectedCard())
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

    private int showHand() {
        BattleMenu.showMessage(match.currentTurnPlayer().getHand().showHand());
        BattleMenu.showMessage("Next Card:");
        showNextCard();
        return 0;
    }

    public static int moveTo(String[] command) {
        command = cleanupArray(command);
        int x = Integer.parseInt(command[command.length - 2]),
                y = Integer.parseInt(command[command.length - 1]);
        if (coordinationInvalid(x, y))
            return 7;
        //
        int moveValid = 0;
        if (match.currentTurnPlayer().getHand().getSelectedCard() instanceof MovableCard)
            moveValid = ((MovableCard) match.currentTurnPlayer().getHand().getSelectedCard())
                    .isMoveValid(match.getTable().getCell(x, y));
        if (moveValid != 0) return moveValid;
        //
        ((MovableCard) match.currentTurnPlayer().getHand().getSelectedCard()).move(match.getTable().getCell(x, y));
        BattleMenu.showMessage(match.currentTurnPlayer().getHand().getSelectedCard().getCardID()
                + " moved to (" + x + "," + y + ")");
        return 0;
    }

    private boolean isCoordinationValidToInsert(int x, int y) {
        ArrayList<Cell> soldiersCells = match.getTable().findAllSoldiers(match.currentTurnPlayer());
        ArrayList<Cell> wantedCells = new ArrayList<>();
        for (Cell cell : soldiersCells)
            wantedCells.addAll(cell.getAdjacentCells());
        //
        ArrayList<Cell> toRemove = new ArrayList<>();
        for (Cell cell : wantedCells)
            if (!cell.isCellFree())
                toRemove.add(cell);
        wantedCells.removeAll(toRemove);
        //
        for (Cell cell : wantedCells)
            if (cell.getCellCoordination().equals(new Coordination(x, y)))
                return true;
        return false;
    }

    private static int showCardInfo(String cardID) {
        if (match.getPlayer1().getDeck().getHero().getCardID().equals(cardID))
            showInfo(match.getPlayer1().getDeck().getHero());
        if (match.getPlayer2().getDeck().getHero().getCardID().equals(cardID))
            showInfo(match.getPlayer2().getDeck().getHero());
        for (Card minion : match.getPlayer1().getDeck().getMinions())
            if (minion.getCardID().equals(cardID))
                showInfo(minion);
        for (Card minion : match.getPlayer2().getDeck().getMinions())
            if (minion.getCardID().equals(cardID))
                showInfo(minion);
        for (Card spell : match.getPlayer1().getDeck().getSpells())
            if (spell.getCardID().equals(cardID))
                showInfo(spell);
        for (Card spell : match.getPlayer2().getDeck().getSpells())
            if (spell.getCardID().equals(cardID))
                showInfo(spell);
        System.out.println("wtf");
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
        if (match.getPlayer1().getDeck().getHero().getCardID().equals(cardID))
            return match.getPlayer1().getDeck().getHero();
        if (match.getPlayer2().getDeck().getHero().getCardID().equals(cardID))
            return match.getPlayer2().getDeck().getHero();
        //
        for (Cell cell : match.getTable().findAllSoldiers(match.getPlayer1()))
            if (cell.getMovableCard().getCardID().equals(cardID))
                return cell.getMovableCard();
        for (Cell cell : match.getTable().findAllSoldiers(match.getPlayer2()))
            if (cell.getMovableCard().getCardID().equals(cardID))
                return cell.getMovableCard();
        //
        for (Minion minion : match.getPlayer1().getDeck().getMinions())
            if (minion.getCardID().equals(cardID))
                return minion;
        for (Minion minion : match.getPlayer2().getDeck().getMinions())
            if (minion.getCardID().equals(cardID))
                return minion;
        for (Spell spell : match.getPlayer1().getDeck().getSpells())
            if (spell.getCardID().equals(cardID))
                return spell;
        for (Spell spell : match.getPlayer2().getDeck().getSpells())
            if (spell.getCardID().equals(cardID))
                return spell;
        return null;
    }

    private int showSoldiers(Player player) {
        for (Cell cell : match.getTable().findAllSoldiers(player)) {
            showMinion(cell.getMovableCard());
        }
        return 0;
    }

    private void showMinion(MovableCard soldier) {
        BattleMenu.showMessage(soldier.getCardID() + " : " + soldier.getName() + ", health : " + soldier.getHealth()
                + ", location : (" + soldier.getCardCell().getCellCoordination().getX() + ","
                + soldier.getCardCell().getCellCoordination().getY()
                + "), power : " + soldier.getDamage());
    }

    private int gameInfo() {
        switch (match.getGameMode()) {
            case 1:
                System.out.println("Player1 Hero Health = "
                        + match.getPlayer1().getDeck().getHero().getHealth());
                System.out.println("Player1 MP = "
                        + match.getPlayer1().getMana());
                System.out.println("Player2 Hero Health = "
                        + match.getPlayer2().getDeck().getHero().getHealth());
                System.out.println("Player2 MP = "
                        + match.getPlayer2().getMana());
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

    private static boolean coordinationInvalid(int x, int y) {
        return x > 5 || y > 9 || x < 1 || y < 1;
    }

    //getters

    public static Match getMatch() {
        return match;
    }

    //getters

    //setters
    public static void setMatch(Match match) {
        BattleMenuProcess.match = match;
    }
    //setters
}
