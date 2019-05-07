package view;

import model.*;
import presenter.BattleMenuProcess;
import presenter.CollectionMenuProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BattleMenu {
    private boolean isInBattleMenu;
    private BattleInit battleInit;
    private BattleMenuProcess battleMenuProcess;
    private boolean hasRun = false;

    public BattleMenu(BattleInit battleInit, Match match) {
        isInBattleMenu = true;
        this.battleInit = battleInit;
        battleMenuProcess = new BattleMenuProcess(this);
        BattleMenuProcess.setMatch(match);
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        battleSetup();
        while (true) {
            try {
                if (!hasRun) {
                    showMenu();
                    hasRun = true;
                }
                if (!isInBattleMenu)
                    break;
                String command = scanner.nextLine();
                battleMenuProcess.commandParts = command.split("[) ,(]");
                int commandType = BattleMenuProcess.findPatternIndex(command);
                if (commandType == -1)
                    System.out.println("invalid input");
                else {
                    int returnedValue = battleMenuProcess.DoCommands[commandType].doIt();
                    switch (returnedValue) {
                        case 5:
                            //select card
                            selectCard_loop:
                            while (true) {
                                command = scanner.nextLine();
                                switch (selectCardMenu(command)) {
                                    case 1:
                                        break selectCard_loop;
                                    case 2:
                                        selectCardHelp();
                                        break;
                                    case 4:
                                        cardHandleErrors(BattleMenuProcess.moveTo(command.split("[), (]")));
                                        break;
                                    case 5:
                                        cardHandleErrors(BattleMenuProcess.attack(command.split("\\s+")[1]));
                                        break;
                                    case 6:
                                        cardHandleErrors(BattleMenuProcess.attackCombo(command.split("\\s+")));
                                        break;
                                    default:
                                        showMessage("invalid command");
                                }
                            }
                            break;
                        case 10:
                            // graveyard
                            graveYard_loop:
                            while (true) {
                                command = scanner.nextLine();
                                switch (graveYardMenu(command)) {
                                    case 1:
                                        break graveYard_loop;
                                    case 2:
                                        graveYardHelp();
                                        break;
                                    case 3:
                                        BattleMenuProcess.showGraveYardCards();
                                        break;
                                    case 4:
                                        if (BattleMenuProcess.showGraveYardCardInfo(command.split("\\s+")[2]) == -1)
                                            showMessage("invalid cardID");
                                        break;
                                    default:
                                        showMessage("invalid command");
                                }
                            }
                            break;
                        case 15:
                            //collectible menu
                            collectible_loop:
                            while (true) {
                                command = scanner.nextLine();
                                switch (collectibleMenu(command)) {
                                    case 1:
                                        break collectible_loop;
                                    case 2:
                                        collectibleMenuHelp();
                                        break;
                                    case 3:
                                        BattleMenuProcess.showCollectibleInfo();
                                        break;
                                    case 4:
                                        BattleMenuProcess.useCollectible();
                                        break;
                                    default:
                                        showMessage("invalid command");
                                }
                            }
                            break;
                        default:
                            handleErrors(returnedValue);
                            break;
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    private void collectibleMenuHelp() {
        showMessage("Use (x, y)");
        showMessage("Show info");
        showMessage("Help");
        showMessage("Cancel");
    }

    private int collectibleMenu(String command) {
        if (command.matches("[cC]ancel")) return 1;
        if (command.matches("[hH]elp")) return 2;
        if (command.matches("[sS]how info")) return 3;
        if (command.matches("[uU]se \\(\\d,[ ]*\\d\\)")) return 4;
        return -1;
    }

    private void battleSetup() {
        setPlayerToCards(BattleMenuProcess.getMatch().getPlayer1());
        BattleMenuProcess.getMatch().getPlayer1().getDeck().getHero().setCardID(
                BattleMenuProcess.getMatch().getPlayer1().getAccount().getUserName() + "_" +
                        CollectionMenuProcess.nameCreator(BattleMenuProcess.getMatch().getPlayer1().getDeck().getHero().getName()) + "_1");
        setPlayerToCards(BattleMenuProcess.getMatch().getPlayer2());
        BattleMenuProcess.getMatch().getPlayer2().getDeck().getHero().setCardID(
                BattleMenuProcess.getMatch().getPlayer2().getAccount().getUserName() + "_" +
                        CollectionMenuProcess.nameCreator(BattleMenuProcess.getMatch().getPlayer2().getDeck().getHero().getName()) + "_1");

        BattleMenuProcess.getMatch().getPlayer1().fillHand();
        BattleMenuProcess.getMatch().getPlayer2().fillHand();
        //
        BattleMenuProcess.getMatch().getPlayer1().setMana(20);
//        BattleMenuProcess.getMatch().getPlayer1().setMana(2);
        BattleMenuProcess.getMatch().getPlayer2().setMana(20); // unused
        //
        BattleMenuProcess.getMatch().getPlayer1().getDeck().getHero()
                .castCard(BattleMenuProcess.getMatch().getTable().getCellByCoordination(3, 1));
        BattleMenuProcess.getMatch().getPlayer2().getDeck().getHero()
                .castCard(BattleMenuProcess.getMatch().getTable().getCellByCoordination(3, 9));
    }

    private void setPlayerToCards(Player player) {
        Deck deck = player.getDeck();
        for (int i = 0; i < deck.getItems().size(); i++)
            deck.getItems().get(i).setPlayer(player);
        for (int i = 0; i < deck.getMinions().size(); i++)
            deck.getMinions().get(i).setPlayer(player);
        for (int i = 0; i < deck.getSpells().size(); i++)
            deck.getSpells().get(i).setPlayer(player);
        deck.getHero().setPlayer(player);
        deck.getHero().getHeroSpell().setPlayer(player);
    }

    private void selectCardHelp() {
        showMessage("Move to (x, y)");
        showMessage("Attack [opponent card id]");
        showMessage("Attack combo [opponent card id] [my card id] [my card id] [...]");
        showMessage("Help");
        showMessage("Cancel");
    }

    private int selectCardMenu(String command) {
        if (command.matches("[cC]ancel")) return 1;
        if (command.matches("[hH]elp")) return 2;
        if (command.matches("[mM]ove to \\(\\d,[ ]*\\d\\)")) return 4;
        if (command.matches("[aA]ttack [a-zA-Z0-9._]+")) return 5;
        if (command.matches("[aA]ttack combo [a-zA-Z0-9._]+ [a-zA-Z0-9._]+[ [a-zA-Z0-9._]+]*")) return 6;

        return -1;
    }

    private void handleErrors(int messageID) {
        switch (messageID) {
            case 1:
                showMessage("invalid coordination");
                break;
            case 2:
                showMessage("invalid card id");
                break;
            case 7:
                showMessage("invalid coordination");
                break;
            case 9:
                showMessage("invalid card name");
                break;
            case 11:
                showMessage("You don't have enough mana");
                break;
            case 12:
                showMessage("invalid target");
                break;
            case 13:
                showMessage("hero spell hasn't cooled down yet");
                break;
        }
    }

    private void cardHandleErrors(int messageID) {
        switch (messageID) {
            case 1:
                showMessage("Invalid target");
                break;
            case 2:
                showMessage("Opponent minion is unavailable for attack");
                break;
            case 3:
                showMessage("Invalid card id");
                break;
            case 4:
                showMessage("Already moved");
                break;
            case 5:
                showMessage("Out of range");
                break;
            case 6:
                showMessage("Enemy in the way");
                break;
            case 8:
                showMessage("Stunned. Can't Move");
                break;
            case 9:
                showMessage("invalid card name");
                break;
            case 10:
                showMessage("the card is already there");
                break;
            case 11:
                showMessage("the cell is occupied by another card");
                break;
            case 13:
                showMessage("Stunned. Can't Attack");
                break;
            case 14:
                showMessage("Out of attack range");
                break;
            case 15:
                showMessage("Friendly fire not allowed");
                break;
            case 16:
                showMessage("Can't attack");
                break;
            case 17:
                showMessage("successfully attacked");
                break;
            case 18:
                showMessage("cell is invalid.");
        }
    }

    private void graveYardHelp() {
        showMessage("Show info [card id]");
        showMessage("Show cards");
        showMessage("Help");
        showMessage("Exit");
    }

    private int graveYardMenu(String command) {
        if (command.matches("[eE]xit")) return 1;
        if (command.matches("[hH]elp")) return 2;
        if (command.matches("[sS]how cards")) return 3;
        if (command.matches("[sS]how info [a-zA-Z0-9._]+")) return 4;

        else return -1;
    }

    public static int showMenu() {
        showMessage("Game Info");
        showMessage("Show my minions");
        showMessage("Show opponent minions");
        showMessage("Show card info [card id]");
        showMessage("Select [card id]");
        showMessage("Show hand");
        showMessage("Insert [card name] in (x, y)");
        showMessage("use special power (x,y)");
        showMessage("End turn");
        showMessage("Show collectibles");
        showMessage("Select [collectible id]");
        showMessage("Show next card");
        showMessage("Enter graveyard");
        showMessage("End Game");
        showMessage("Help");
        showMessage("Show menu");
        return 0;
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

    //setters
    public void setInBattleMenu(boolean isInBattleMenu) {
        this.isInBattleMenu = isInBattleMenu;
    }

    public void setHasRun(boolean hasRun) {
        this.hasRun = hasRun;
    }
    //setters

    //getters
    public BattleInit getBattleInit() {
        return battleInit;
    }
    //getters
}
