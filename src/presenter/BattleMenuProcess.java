package presenter;

import javafx.scene.Group;
import javafx.scene.Scene;
import model.*;
import view.BattleFX;
import view.BattleMenu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Pattern;

public class BattleMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private static BattleMenu battleMenu;
    public String[] commandParts;
    private static Match match;

    static {
        commandPatterns.add(Pattern.compile("[sS]how my minions"));
        commandPatterns.add(Pattern.compile("[sS]how opponent minions"));
        commandPatterns.add(Pattern.compile("[sS]how card info [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("[sS]elect [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("[uU]se special power \\(\\d,[ ]*\\d\\)"));
        commandPatterns.add(Pattern.compile("[iI]nsert [a-zA-Z0-9._ ]+ in \\(\\d,[ ]*\\d\\)"));
        commandPatterns.add(Pattern.compile("[sS]how hand"));
        commandPatterns.add(Pattern.compile("[gG]ame info"));
        commandPatterns.add(Pattern.compile("[eE]nd turn"));
        commandPatterns.add(Pattern.compile("[sS]how collectibles"));
        commandPatterns.add(Pattern.compile("[sS]how next card"));
        commandPatterns.add(Pattern.compile("[eE]nter graveyard"));
        commandPatterns.add(Pattern.compile("[hH]elp"));
        commandPatterns.add(Pattern.compile("[eE]nd game"));
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
            new DoCommand() {
                @Override
                public int doIt() {
                    return useSpecialPower(commandParts);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return insertCard(commandParts);
                }
            },
            this::showHand,
            this::gameInfo,
            this::endTurn,
            this::showCollectibles,
            this::showNextCard,
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
        // todo : check the id to see if is a card or a collectible
    }

    public static void showGraveYardCards() {
        if (match.currentTurnPlayer().getUserName().equals(match.getPlayer1().getUserName())) {
            for (Card card : match.player1_graveyard)
                BattleMenu.showMessage(card.getCardID());
        } else
            for (Card card : match.player2_graveyard)
                BattleMenu.showMessage(card.getCardID());
    }

    public static int showGraveYardCardInfo(String cardID) {
        for (Card card : match.player1_graveyard)
            if (cardID.equals(card.getCardID())) {
                showInfo(card, false);
                return 0;
            }
        for (Card card : match.player2_graveyard)
            if (cardID.equals(card.getCardID())) {
                showInfo(card, false);
                return 0;
            }
        return -1;
    }

    private int endGame() throws IOException {
        //todo: give rewards
        exit();
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

    public int endTurn() throws IOException {
        match.currentTurnPlayer().fillHand();
        secondModeProcedure(match);
        resetFlags();
        buryTheDead();
        match.coolDownIncrease();
        if (endGameReached(match))
            endingProcedure();
        if (match.notCurrentTurnPlayer().isAI()) {
            match.switchTurn();
            impactGoThroughTime(match);
            match.handleMana();
            playAI(match.currentTurnPlayer());
            match.currentTurnPlayer().fillHand();
            secondModeProcedure(match);
            resetFlags();
            //buryTheDead();
            if (endGameReached(match))
                endingProcedure();
            match.switchTurn();
            match.handleMana();
            impactGoThroughTime(match);
//            impactGoThroughTime(match);
        } else {
            // match.handleMana();
            match.switchTurn();
            impactGoThroughTime(match);
        }
        return 0;
    }


    private static void endingProcedure() throws IOException {
        if (match.getGameMode() == 1) {
            if (match.getPlayer1().getDeck().getHero().isAlive()) {
                MatchHistory matchHistory = new MatchHistory();
                if (!match.getPlayer1().isAI()) {
                    BattleFX.endProcedure(match,new Scene(new Group(),BattleFX.getScreenWidth(),BattleFX.getScreenHeight()),match.getPlayer1(),true);
                    if (!match.getPlayer2().isAI()) {
                        BattleFX.endProcedure(match,new Scene(new Group(),BattleFX.getScreenWidth(),BattleFX.getScreenHeight()),match.getPlayer2(),false);
                        match.getPlayer1().getAccount().setMoney(match.getPlayer1().getAccount().getMoney() + 1500);
                        matchHistory.setMatchHistory(match.getPlayer2(), match, false);

                        //LoginMenuProcess.save(match.getPlayer2().getAccount());
                    }
                    if (match.getPlayer2().isAI()) {
                        match.getPlayer1().getAccount().setMoney(match.getPlayer1().getAccount().getMoney() + 500);
                    }
                    matchHistory.setMatchHistory(match.getPlayer1(), match, true);
                       // LoginMenuProcess.save(match.getPlayer1().getAccount());
                } else {
                    matchHistory.setMatchHistory(match.getPlayer2(), match, false);
                }
                BattleMenu.showMessage(match.getPlayer1().getUserName() + " has won.");
            } else {
                MatchHistory matchHistory = new MatchHistory();
                if (!match.getPlayer2().isAI()) { //sljkdfjl
                    BattleFX.endProcedure(match,new Scene(new Group(),BattleFX.getScreenWidth(),BattleFX.getScreenHeight()),match.getPlayer2(),true);
                    if (!match.getPlayer1().isAI()) {
                        BattleFX.endProcedure(match,new Scene(new Group(),BattleFX.getScreenWidth(),BattleFX.getScreenHeight()),match.getPlayer1(),false);
                        match.getPlayer2().getAccount().setMoney(match.getPlayer2().getAccount().getMoney() + 1500);
                        matchHistory.setMatchHistory(match.getPlayer1(), match, false);
                        //LoginMenuProcess.save(match.getPlayer1().getAccount());
                    }
                    if (match.getPlayer1().isAI()) {
                        match.getPlayer2().getAccount().setMoney(match.getPlayer2().getAccount().getMoney() + 500);
                    }
                    matchHistory.setMatchHistory(match.getPlayer2(), match, true);
                } else {
                    matchHistory.setMatchHistory(match.getPlayer1(), match, false);
                   // LoginMenuProcess.save(match.getPlayer1().getAccount());
                }
                BattleMenu.showMessage(match.getPlayer2().getUserName() + " has won.");
//                if (!match.getPlayer2().isAI())
//                    LoginMenuProcess.save(match.getPlayer2().getAccount());
            }
        } else if (match.getGameMode() != 1) {
            if (match.getPlayer1().getFlags() != null && match.getGameMode() == 2 || match.getPlayer1().getFlags().size() >= (match.getNumberOfFlags() / 2) && match.getGameMode() == 3) {
                MatchHistory matchHistory = new MatchHistory();
                if (match.getPlayer1().isAI()) {
                    matchHistory.setMatchHistory(match.getPlayer2(), match, false);
                    //LoginMenuProcess.save(match.getPlayer2().getAccount());
                } else {
                    if (match.getPlayer2().isAI()) {
                        matchHistory.setMatchHistory(match.getPlayer1(), match, true);
                      //  LoginMenuProcess.save(match.getPlayer1().getAccount());
                    } else {
                        matchHistory.setMatchHistory(match.getPlayer1(), match, true);
                        //LoginMenuProcess.save(match.getPlayer1().getAccount());
                        matchHistory.setMatchHistory(match.getPlayer2(), match, false);
                        //LoginMenuProcess.save(match.getPlayer2().getAccount());
                    }
                }
                BattleMenu.showMessage(match.getPlayer1().getUserName() + " has won.");
            } else {
                MatchHistory matchHistory = new MatchHistory();
                if (!match.getPlayer2().isAI()) {
                    if (!match.getPlayer1().isAI()) {
                        match.getPlayer1().getAccount().setMoney(match.getPlayer1().getAccount().getMoney() + 1500); // just changed
                        matchHistory.setMatchHistory(match.getPlayer1(), match, false);
                        //LoginMenuProcess.save(match.getPlayer1().getAccount());
                    }
                    if (match.getPlayer1().isAI()) {
                        match.getPlayer2().getAccount().setMoney(match.getPlayer2().getAccount().getMoney() + 500);
                    }
                    matchHistory.setMatchHistory(match.getPlayer2(), match, true);
                } else {
                    matchHistory.setMatchHistory(match.getPlayer1(), match, false);
                    //LoginMenuProcess.save(match.getPlayer1().getAccount());
                }
                BattleMenu.showMessage(match.getPlayer2().getUserName() + " has won.");
                //if (!match.getPlayer2().isAI())
                    //LoginMenuProcess.save(match.getPlayer2().getAccount());
            }
        }
//        battleMenu.getBattleInit().getMainMenu().setIsInMainMenu(true);
//        battleMenu.getBattleInit().setInBattleInit(false);
//        battleMenu.setInBattleMenu(false);
//        battleMenu.getBattleInit().getMainMenu().run();


    }

    public static void buryTheDead() {
        boolean isSomeOneDead = false;
        for (Cell cell : match.getTable().findAllSoldiers(match.currentTurnPlayer()))
            if (!cell.getMovableCard().isAlive() || cell.getMovableCard().getHealth() + cell.getMovableCard().dispelableHealthChange <= 0) {
                if (!cell.getMovableCard().getPlayer().getCollectibleItems().isEmpty())
                    cell.setItem(cell.getMovableCard().getPlayer().getCollectibleItems().get(0));
                match.moveToGraveYard(cell.getMovableCard(), match.currentTurnPlayer());
                isSomeOneDead = true;
            }
        for (Cell cell : match.getTable().findAllSoldiers(match.notCurrentTurnPlayer()))
            if (!cell.getMovableCard().isAlive() || cell.getMovableCard().getHealth() + cell.getMovableCard().dispelableHealthChange <= 0) {
                if (!cell.getMovableCard().getPlayer().getCollectibleItems().isEmpty())
                    cell.setItem(cell.getMovableCard().getPlayer().getCollectibleItems().get(0));
                match.moveToGraveYard(cell.getMovableCard(), match.notCurrentTurnPlayer());
                isSomeOneDead = true;
            }
        if(!isSomeOneDead) {
            try {
                BattleFX.updateSoldiers(match,new Scene(new Group(),BattleFX.getScreenWidth(),BattleFX.getScreenHeight()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void playAI(Player player) throws IOException {
        //
        outer:
        for (int i = 0; i < 5; i++)
            if (player.getHand().getCards().get(i) != null)
                if (player.getHand().getCards().get(i) instanceof Spell) {
//                ArrayList<Cell> arrayList = ((Spell) card).getValidCoordination();
//                if (arrayList != null && arrayList.size() >= 1)
//                    if (spellCastCheck((Spell) card, arrayList.get(0).getCellCoordination().getX(),
//                            arrayList.get(0).getCellCoordination().getY())) {
//                        ((Spell) card).castCard(arrayList.get(0), player);
//                        BattleMenu.showMessage(card.getCardID() + " inserted to ("
//                                + arrayList.get(0).getCellCoordination().getX() + "," + arrayList.get(0).getCellCoordination().getY() + ")");
//                        break outer;
//                    }
                } else {
                    Card card = player.getHand().getCards().get(i);
                    for (int j = 1; j <= 5; j++) {
                        for (int k = 1; k <= 9; k++) {
                            if (isCoordinationValidToInsert(j, k)) {
                                match.currentTurnPlayer().getHand().findCardByName(card.getName())
                                        .castCard(match.getTable().getCellByCoordination(j, k));
                                BattleMenu.showMessage(card.getCardID() + " inserted to ("
                                        + j + "," + k + ")");
                                BattleFX.updateSoldiers(match, new Scene(new Group(), BattleFX.getScreenWidth(), BattleFX.getScreenHeight()));
                                break outer;
                            }
                        }
                    }
                }
//            for (Cell allSoldier : match.getTable().findAllSoldiers(match.currentTurnPlayer())) {
//                for (Cell soldier : match.getTable().findAllSoldiers(match.notCurrentTurnPlayer())) {
//                    MovableCard movableCard = allSoldier.getMovableCard();
//                    if (soldier.getMovableCard() != null) {
//                        Cell destination = match.getTable().getCellByCoordination(soldier.getCellCoordination().getX(), soldier.getCellCoordination().getY() - 2);
//                        movableCard.move(destination);
//                        int result = movableCard.attack(soldier.getMovableCard());
//                        if (result == 0) {
//                            if (movableCard != null && soldier.getMovableCard() != null)
//                                BattleMenu.showMessage(movableCard.getCardID() + " has attacked " + soldier.getMovableCard().getCardID() + ".");
//                        }
//                    }
//                }
//            }
        for (Cell allSoldier : match.getTable().findAllSoldiers(match.currentTurnPlayer())) {
            if (Math.abs(new Random().nextInt() % 2) == 1) {
                MovableCard movableCard = allSoldier.getMovableCard();
                if (match.getTable().getCell(movableCard.getCardCell().getCellCoordination().getX(), movableCard.getCardCell().getCellCoordination().getY() - 1).getMovableCard() == null) {
                    Cell destination = match.getTable().getCell(movableCard.getCardCell().getCellCoordination().getX(), movableCard.getCardCell().getCellCoordination().getY() - 1);
                    BattleFX.setDraggedFromNode(BattleFX.getGameMap()[allSoldier.getCellCoordination().getX()][allSoldier.getCellCoordination().getY()]);
                    BattleFX.moveProcess(allSoldier.getCellCoordination(), match, destination.getCellCoordination().getX(), destination.getCellCoordination().getY(), new Scene(new Group(), BattleFX.getScreenWidth(), BattleFX.getScreenHeight()), BattleFX.getRectanglesPane());
                    System.out.println("in battlemenuprocess move process");

                }
            }
        }

        if (endGameReached(match))
            endingProcedure();
    }

    private static void impactGoThroughTime(Match match) {
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 9; j++) {
                Cell cell = match.getTable().getCellByCoordination(i, j);
                MovableCard movableCard = cell.getMovableCard();
                ArrayList<Impact> toRemove = new ArrayList<>();
                for (Impact impact : cell.cellImpacts) {
                    impact.goThroughTime(movableCard);
                    if (impact.isImpactOver()) {
                        impact.doAntiImpact(movableCard);
                        toRemove.add(impact);
                    }
                }
                cell.cellImpacts.removeAll(toRemove);
                if (movableCard != null) {
                    toRemove = new ArrayList<>();
                    int x = 1;
                    for (Impact impact : movableCard.getImpactsAppliedToThisOne()) {
                        impact.goThroughTime(movableCard);
                        if (impact.isImpactOver()) {
                            impact.doAntiImpact(movableCard);
                            toRemove.add(impact);
                        }
                        x++;
                    }
                    movableCard.getImpactsAppliedToThisOne().removeAll(toRemove);
                }

            }

        }

    }

    public static boolean endGameReached(Match match) {
        switch (match.getGameMode()) {
            case 1:
                if (!match.currentTurnPlayer().getDeck().getHero().isAlive() ||
                        !match.notCurrentTurnPlayer().getDeck().getHero().isAlive())
                    return true;
            case 2:
                if (match.getPlayer1().getHeldTheFlagNumberOfTurns() >= 8 ||
                        match.getPlayer2().getHeldTheFlagNumberOfTurns() >= 8)
                    return true;
            case 3:

                return false;
        }
        return false;
    }

    private void secondModeProcedure(Match match) {
        if (match.getGameMode() == 2) {
            for (Cell allFlag : match.getTable().findAllFlags()) {
                if (allFlag.getMovableCard() != null && allFlag.getMovableCard().getPlayer() != null)
                    allFlag.getMovableCard().getPlayer().increaseHeldFlag();
            }
        }
    }

    private static int useSpecialPower(String[] command) { // todo : test beshe
        command = cleanupArray(command);
        int x = Integer.parseInt(command[3]),
                y = Integer.parseInt(command[4]);
        if (coordinationInvalid(x, y))
            return 7;
        if (match.currentTurnPlayer().getDeck().getHero().getSpellCost() > match.currentTurnPlayer().getMana())
            return 11;
        if (match.currentTurnPlayer().getDeck().getHero().getSpellCoolDown() > match.getCurrentPlayerHeroCoolDownCounter())
            return 13;
        if (!spellCastCheck(match.currentTurnPlayer().getDeck().getHero().getHeroSpell(), x, y))
            return 12;
        match.currentTurnPlayer().getDeck().getHero().castSpell(match.getTable().getCellByCoordination(x, y));
        match.setCoolDownCounter();
        BattleMenuProcess.buryTheDead();
        return 0;
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
                if (!spellCastCheck(spell, x, y)) return 12;
            }
        }
        //
        if (match.currentTurnPlayer().getHand().findCardByName(cardName) == null)
            return 9;
        String cardID = match.currentTurnPlayer().getHand().findCardByName(cardName).getCardID();
        if (match.currentTurnPlayer().getHand().findCardByName(cardName) instanceof MovableCard)
            match.currentTurnPlayer().getHand().findCardByName(cardName)
                    .castCard(match.getTable().getCellByCoordination(x, y));
        else if (match.currentTurnPlayer().getHand().findCardByName(cardName) instanceof Spell) {// ok?
             match.currentTurnPlayer().getHand().findCardByName(cardName).setPlayer(match.currentTurnPlayer());
            ((Spell) match.currentTurnPlayer().getHand().findCardByName(cardName))
                    .castCard(match.getTable().getCellByCoordination(x, y));
        }
        //
//        match.currentTurnPlayer().fillHand();
        BattleMenu.showMessage(cardName + " with " + cardID + " inserted to (" + x + "," + y + ")");
        return 0;
    }

    private static boolean spellCastCheck(Spell spell, int x, int y) {
        spell.getPrimaryImpact().setAllVariablesNeeded();
        if (spell.getPrimaryImpact().isSelectedCellImportant()) {
            ArrayList<Cell> arrayList = spell.getValidCoordination();
            return arrayList.contains(match.getTable().getCell(x, y));
        }
        return true;
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

    public static int attack(String cardID) throws IOException {
        if (findCard(cardID) == null)
            return 3;
        Card attackedCard = findCard(cardID);
        int returnValue = 0;
        if (attackedCard instanceof MovableCard)
            returnValue = ((MovableCard) match.currentTurnPlayer().getHand().getSelectedCard())
                    .attack((MovableCard) attackedCard);
        if (endGameReached(match))
            endingProcedure();
        if (returnValue == 0) return 17;
        return returnValue;
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

    public static boolean isCoordinationValidToInsert(int x, int y) {
        if (match.getTable().getCellByCoordination(x, y).getItem() != null)
            return false;
        ArrayList<Cell> soldiersCells = match.getTable().findAllSoldiers(match.currentTurnPlayer());
        ArrayList<Cell> wantedCells = new ArrayList<>();
        for (Cell cell : soldiersCells)
            wantedCells.addAll(cell.getAdjacentCells());
        //
        ArrayList<Cell> toRemove = new ArrayList<>();
        for (Cell cell : wantedCells)
            if (!cell.isCellFree() || cell.getCellCoordination().getY() == 0 || cell.getCellCoordination().getX() == 0 ||
                    cell.getCellCoordination().getX() == 6 || cell.getCellCoordination().getY() == 10)
                toRemove.add(cell);
        wantedCells.removeAll(toRemove);
        //
        for (Cell cell : wantedCells) {
            System.out.println(cell.getCellCoordination().getX() + " " + cell.getCellCoordination().getY());
            if (cell.getCellCoordination().equals(new Coordination(x, y)))
                return true;
        }
        return false;
    }

    private static int showCardInfo(String cardID) {
        if (match.getPlayer1().getDeck().getHero().getCardID().equals(cardID)) {
            showInfo(match.getPlayer1().getDeck().getHero(), true);
            return 0;
        }
        if (match.getPlayer2().getDeck().getHero().getCardID().equals(cardID)) {
            showInfo(match.getPlayer2().getDeck().getHero(), true);
            return 0;
        }
        for (Card minion : match.getPlayer1().getDeck().getMinions())
            if (minion.getCardID().equals(cardID))
                showInfo(minion, true);
        for (Card minion : match.getPlayer2().getDeck().getMinions())
            if (minion.getCardID().equals(cardID))
                showInfo(minion, true);
        for (Card spell : match.getPlayer1().getDeck().getSpells())
            if (spell.getCardID().equals(cardID))
                showInfo(spell, true);
        for (Card spell : match.getPlayer2().getDeck().getSpells())
            if (spell.getCardID().equals(cardID))
                showInfo(spell, true);
        for (Cell cell : match.getTable().findAllSoldiers(match.currentTurnPlayer()))
            if (cell.getMovableCard().getCardID().equals(cardID)) {
                showInfo(cell.getMovableCard(), true);
                return 0;
            }
        for (Cell cell : match.getTable().findAllSoldiers(match.notCurrentTurnPlayer()))
            if (cell.getMovableCard().getCardID().equals(cardID)) {
                showInfo(cell.getMovableCard(), true);
                return 0;
            }
        return 0;
    }

    private static void showInfo(Card card, boolean showHealth) {
        String health = "";
        if (card instanceof Hero) {
            if (showHealth) health = "\nHP: " + ((Hero) card).getHealth();
            System.out.println("Hero:\nName: " + card.getName() + health
                    + "\nAP: " + ((Hero) card).getDamage() + "\nMP: " + card.getManaCost()
                    + "\nCost: " + card.getCost() + "\nDesc: " + card.getDescription());
        } else if (card instanceof Minion) {
            if (showHealth) health = "\nHP: " + ((Minion) card).getHealth();
            System.out.println("Minion:\nName: " + card.getName() + health
                    + "\nAP: " + ((Minion) card).getDamage() + "\nMP: " + card.getManaCost()
                    + "\nCost: " + card.getCost() + "\nDesc: " + card.getDescription());
        } else if (card instanceof Spell)
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
        BattleMenu.showMessage(soldier.getCardID() + " : " + soldier.getName() + ", health : " + (soldier.getHealth() + soldier.dispelableHealthChange)
                + ", location : (" + soldier.getCardCell().getCellCoordination().getX() + ","
                + soldier.getCardCell().getCellCoordination().getY()
                + "), power : " + (soldier.getDamage() + soldier.dispelableDamageChange));
    }

    private int gameInfo() {
        switch (match.getGameMode()) {
            case 1:
                System.out.println("Player1: Hero Health = "
                        + match.getPlayer1().getDeck().getHero().getHealth()
                        + ", Mana : " + match.getPlayer1().getMana());
                System.out.println("Player2: Hero Health = "
                        + match.getPlayer2().getDeck().getHero().getHealth()
                        + ", Mana : " + match.getPlayer2().getMana());
                break;
            case 2:
                for (int i = 1; i <= 5; i++)
                    for (int j = 1; j <= 9; j++)
                        if (match.getTable().getCellByCoordination(i, j).getItem() instanceof Flag) {
                            if (match.getTable().getCellByCoordination(i, j).getMovableCard() != null) {
                                System.out.print("holder : " + match.getTable().getCellByCoordination(i, j).getMovableCard().getPlayer().getAccount().getUserName() + "\n\t");
                            }
                            System.out.println("location : (" + i + ", " + j + ")");
                        }
                // location & holder of flag
                break;
            case 3:
                for (int i = 1; i <= 5; i++)
                    for (int j = 1; j <= 9; j++)
                        if (match.getTable().getCellByCoordination(i, j).getItem() != null && match.getTable().getCellByCoordination(i, j).getItem() instanceof Flag) {
                            if (match.getTable().getCellByCoordination(i, j).getMovableCard() != null) {
                                System.out.print("holder : " + match.getTable().getCellByCoordination(i, j).getMovableCard().getPlayer().getAccount().getUserName() + "\n\t");
                            }
                            System.out.println("location : (" + i + ", " + j + ")");
                        }
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
