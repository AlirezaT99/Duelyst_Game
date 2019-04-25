package presenter;

import view.LoginMenu;
import model.Account;
import model.Player;

import com.google.gson.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

public class LoginMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private static ArrayList<Account> users = new ArrayList<>();
    private static Account currentAccount;
    private static Player player;
    private static boolean isInLoginMenu = true;
    public static String[] commandParts;

    static {
        commandPatterns.add(Pattern.compile("create account [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("login [a-zA-Z0-9._]+"));
        commandPatterns.add(Pattern.compile("show leaderboard"));
        commandPatterns.add(Pattern.compile("save"));
        commandPatterns.add(Pattern.compile("logout"));
        commandPatterns.add(Pattern.compile("help"));
    }

    public interface DoCommand {
        int doIt() throws IOException;
    }

    public static DoCommand[] DoCommands = new DoCommand[]{
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return createAccount(commandParts[2]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return login(commandParts[1]);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return showLeaderBoard();
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return save(player);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return logout(currentAccount);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return LoginMenu.help();
                }
            }
    };

    public static int findPatternIndex(String command, String[] commandParts) {
        for (int i = 0; i < commandPatterns.size(); i++) {
            if (commandParts.length == 3 && commandParts[0].toLowerCase().equals("create")
                    && commandParts[1].toLowerCase().equals("account"))
                return 0;
            if (command.toLowerCase().matches(commandPatterns.get(i).pattern()))
                return i;
        }
        return -1;
    }

    private static void readUsers() throws IOException {
//        File folder = new File("src/model/accounts");
//        File[] listOfFiles = folder.listFiles();
//        Gson gson = new Gson();
//        for (File file : listOfFiles) {
//            if (file.isFile()) {
//                Path path = new File(file.getPath()).toPath();
//                Reader reader = Files.newBufferedReader(path,
//                        StandardCharsets.UTF_8);
//                Account account = gson.fromJson(reader, Account.class);
//                users.add(account);
//            }
//        }
        users.addAll(Account.getAccounts());
    }

    private static int createAccount(String userName) throws IOException {
        readUsers();
        for (Account user : users)
            if (user.getUserName().equals(userName))
                return 1; //message id : 1
        LoginMenu.showMessage("Enter password:");
        String passWord = LoginMenu.scan();
        Account account = new Account(userName, passWord);
        Account.addAccount(account);
        return 0;
//        users.add(account);
//        String fileName = "src/model/accounts/" + userName + ".json";
//        try (FileOutputStream fos = new FileOutputStream(fileName);
//             OutputStreamWriter isr = new OutputStreamWriter(fos,
//                     StandardCharsets.UTF_8)) {
//            Gson gson = new Gson();
//            gson.toJson(account, isr);
//        }
    }

    private static int login(String userName) throws IOException {
        readUsers();
        for (Account user : users) {
            if (user.getUserName().equals(userName)) {
                LoginMenu.showMessage("Enter your password:");
                String passWord = LoginMenu.scan();
                if (user.getPassword().equals(passWord)) {
                    currentAccount = user;
                    LoginMenu.setIsInLoginMenu(false);
                    Player currentPlayer = new Player();
                    currentPlayer.setAccount(currentAccount);
                    player = currentPlayer;
                    //todo : login, pass onto main menu
                    return 0;
                } else
                    return 2; // message id : 2
            }
        }
        return 3; //message id :3
    }

    private static int showLeaderBoard() throws IOException {
        readUsers();
        sortUsers();
        for (int i = 0; i < users.size(); i++)
            LoginMenu.showMessage((i + 1) + "-UserName : " + users.get(i).getUserName() + "-Wins : " +
                    users.get(i).getNumberOfWins());
        return 0;
    }

    private static void sortUsers() {
        users.sort(Comparator.comparing(Account::getNumberOfWins));
    }

    private static int save(Player player) throws IOException {
        currentAccount.setMoney(player.getMoney());
        // currentAccount.numberOfWins ro bad az har bazi avaz mikonim ounja.
        String fileName = "src/model/accounts/" + player.getUserName() + ".json";
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            gson.toJson(currentAccount, isr);
        }
        return 0;
    }

    private static int logout(Account account) {
        if (currentAccount.equals(account))
            currentAccount = null;
        return 0;
    }
}
