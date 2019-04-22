package presenter;
import view.LoginMenu;
import model.Account;
import model.Player;
import com.google.gson.*;
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

public class LoginMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private static ArrayList<Account> users = new ArrayList<>();
    private static Account currentAccount;
    private static Player player;
    private static boolean isInLoginMenu = true;
    public static String commandParts[];

    static {
        commandPatterns.add(Pattern.compile("create account [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("login [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("show leaderboard"));
        commandPatterns.add(Pattern.compile("save"));
        commandPatterns.add(Pattern.compile("logout"));
        commandPatterns.add(Pattern.compile("help"));
    }

    public interface DoCommand {
        void doIt() throws IOException;
    }

    public static DoCommand[] DoCommands = new DoCommand[]{
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

    public static int findPatternIndex(String command) {
        for (int i = 0; i < commandPatterns.size(); i++) {
            if (command.matches(commandPatterns.get(i).pattern()))
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
        readUsers();
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getUserName().equals(userName)) {
               LoginMenu.showMessage("an account with this username already exists");
                return;
            }
       LoginMenu.showMessage("Enter password:");
        String passWord = LoginMenu.scan();
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
                LoginMenu.showMessage("Enter your password:");
                String passWord = LoginMenu.scan();
                if (users.get(i).getPassword().equals(passWord)) {
                    currentAccount = users.get(i);
                    view.LoginMenu.setIsInLoginMenu(false);
                    Player currentPlayer = new Player();
                    currentPlayer.setAccount(currentAccount);
                    player = currentPlayer;
                    //todo : login, pass onto main menu
                } else
                    LoginMenu.showMessage("incorrect password");
                return;
            }
        }
        LoginMenu.showMessage("no account with this username found");
        return;
    }

    private static void showLeaderBoard() throws IOException {
        readUsers();
        sortUsers();
        for (int i = 0; i < users.size(); i++) {
            LoginMenu.showMessage((i + 1) + "-" + "UserName : " + users.get(i).getUserName() + "-" + "Wins : " +
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
        LoginMenu.showMessage("create account [user name]");
        LoginMenu.showMessage("login [user name]");
        LoginMenu.showMessage("show leaderboard");
        LoginMenu.showMessage("save");
        LoginMenu.showMessage("logout");
    }
}

