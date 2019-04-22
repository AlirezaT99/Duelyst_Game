package view;

import model.Account;
import model.Player;

import java.nio.file.*;
import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LoginMenu {
    private static ArrayList<Pattern> commandPatterns;
    private static ArrayList<Account> users;
    private static boolean inLoginMenu;
    private static Account currentAccount;
    private static Player player;
    private static boolean isInLoginMenu;
    private static String commandParts[];

    static {
        commandPatterns.add(Pattern.compile("create account [^\\s\\\\]"));
        commandPatterns.add(Pattern.compile("login [^\\s\\\\]"));
        commandPatterns.add(Pattern.compile("show leaderboard"));
        commandPatterns.add(Pattern.compile("save"));
        commandPatterns.add(Pattern.compile("logout"));
        commandPatterns.add(Pattern.compile("help"));

    }

    interface DoCommand {
        void doIt() throws IOException;
    }

    public static void loginMenu() throws IOException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            commandParts = command.split("[ ]");
            if (!isInLoginMenu)
                break;
            int commandType = findPatternIndex(command);
            if (commandType == -1) {
                System.out.println("invalid input");
                continue;
            } else
                DoCommands[commandType].doIt();
        }
    }

    static DoCommand[] DoCommands = new DoCommand[]{
            new DoCommand() {
                @Override
                public void doIt() throws IOException {
                    createAccount(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public void doIt() throws IOException {
                    login(commandParts[1]);
                }
            },
            new DoCommand() {
                @Override
                public void doIt() throws IOException {
                    showLeaderBoard();
                }
            },
            new DoCommand() {
                @Override
                public void doIt() throws IOException {
                    save(player);
                }
            },
            new DoCommand() {
                @Override
                public void doIt() throws IOException {
                    logout(currentAccount);
                }
            },
            new DoCommand() {
                @Override
                public void doIt() throws IOException {
                    help();
                }
            }
    };

    private static int findPatternIndex(String command) {
        for (int i = 0; i < commandPatterns.size(); i++) {
            if (command.equals(commandPatterns.get(i).pattern()))
                return i;
        }
        return -1;
    }

    private static void readUsers() throws IOException {
        File folder = new File("src/model/accounts");
        File[] listOfFiles = folder.listFiles();
        Gson gson = new Gson();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                Path path = new File(file.getPath()).toPath();
                Reader reader = Files.newBufferedReader(path,
                        StandardCharsets.UTF_8);
                Account account = gson.fromJson(reader, Account.class);
                users.add(account);
            }
        }
    }

    private static void createAccount(String userName) throws IOException {
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getUserName().equals(userName)) {
                System.out.println("an account with this username already exists");
                return;
            }
        System.out.println("Enter password:");
        Scanner scanner = new Scanner(System.in);
        String passWord = scanner.nextLine();
        Account account = new Account(userName, passWord);
        users.add(account);
        String fileName = "src/model/accounts/" + userName + ".json";
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            gson.toJson(account, isr);
        }
    }

    private static void login(String userName) throws IOException {
        readUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(userName)) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter your password:");
                String passWord = scanner.nextLine();
                if (users.get(i).getPassword().equals(passWord)) {
                    currentAccount = users.get(i);
                    inLoginMenu = false;
                    Player currentPlayer = new Player();
                    currentPlayer.setAccount(currentAccount);
                    player = currentPlayer;
                    //todo : login, pass onto main menu
                } else
                    System.out.println("incorrect password");
                return;
            }
        }
        System.out.println("no account with this username found");
        return;
    }

    private static void showLeaderBoard() throws IOException {
        readUsers();
        sortUsers();
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + "-" + "UserName : " + users.get(i).getUserName() + "-" + "Wins : " +
                    users.get(i).getNumberOfWins());
        }
    }

    private static void sortUsers() {
        Collections.sort(users);
    }

    private static void save(Player player) throws IOException {
        currentAccount.setMoney(player.getMoney());
        // currentAccount.numberOfWins ro bad az har bazi avaz mikonim ounja.
        String fileName = "src/model/accounts/" + player.getUserName() + ".json";
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            gson.toJson(currentAccount, isr);
        }
    }

    private static void logout(Account account) {
        if (currentAccount.equals(account))
            currentAccount = null;
    }

    private static void help() {
        System.out.println("create account [user name]");
        System.out.println("login [user name]");
        System.out.println("show leaderboard");
        System.out.println("save");
        System.out.println("logout");
    }
}
