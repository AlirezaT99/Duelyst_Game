package view;

import model.Account;
import presenter.SinglePlayerMenuProcess;

import java.io.IOException;
import java.util.Scanner;

public class SinglePlayerMenu {
    private boolean isInSinglePlayerMenu;
    private BattleInit battleInit;
    private SinglePlayerMenuProcess singlePlayerMenuProcess;
    private boolean hasRun = false;

    public SinglePlayerMenu(BattleInit battleInit, Account account) {
        isInSinglePlayerMenu = true;
        this.battleInit = battleInit;
        singlePlayerMenuProcess = new SinglePlayerMenuProcess(this);
        singlePlayerMenuProcess.setAccount(account);
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!isInSinglePlayerMenu)
                break;
            if (!hasRun) {
                help();
                hasRun = true;
            }
            // if(scanner.hasNextLine()){
            String command = scanner.nextLine();
            singlePlayerMenuProcess.commandParts = command.split("[ ]");
            int commandType = SinglePlayerMenuProcess.findPatternIndex(command);
            if (commandType == -1)
                showMessage("invalid input");
            else if (singlePlayerMenuProcess.DoCommands[commandType].doIt() == 4) {
                inner_Loop:
                while (true) {
                    command = scanner.nextLine();
                    switch (customGameMenu(command)) {
                        case 1:
                            customGameHelp();
                            break;
                        case 2:
                            break inner_Loop;
                        case 3:
                            SinglePlayerMenuProcess.customGame(command);
                            break;
                        default:
                            showMessage("invalid input");
                    }
                }
            }

        }
        scanner.close();
    }

    private static int customGameMenu(String command) {
        if (command.equals("help")) return 1;
        else if (command.equals("exit")) return 2;
        else if (command.matches("Start game [a-zA-Z0-9._]+ \\d \\d+]")) return 3;
        else return -1;
    }

    public static int help() {
        showMessage("1. Story");
        showMessage("2. Custom game");
        showMessage("3. Exit");
        showMessage("4. Help");
        return 0;
    }

    private static void customGameHelp() {
        showMessage("Start game [deck name] [mode] [number of flags]->(only for the 3rd mode)");
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

    //getters
    public BattleInit getBattleInit() {
        return battleInit;
    }
    //getters

    //setters
    public void setInSinglePlayerMenu(boolean isInSinglePlayerMenu) {
        this.isInSinglePlayerMenu = isInSinglePlayerMenu;
    }

    public void setHasRun(boolean hasRun) {
        this.hasRun = hasRun;
    }
    //setters
}
