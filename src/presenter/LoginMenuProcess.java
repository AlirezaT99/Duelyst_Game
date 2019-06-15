package presenter;

import view.LoginMenu;
import model.Account;
import view.MainMenu;
import com.google.gson.*;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.io.Reader;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LoginMenuProcess {
    private static ArrayList<Pattern> commandPatterns = new ArrayList<>();
    private static ArrayList<Account> users = new ArrayList<>();
    public String[] commandParts;
    private Account currentAccount;
    private LoginMenu loginMenu;

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

    public DoCommand[] DoCommands = new DoCommand[]{
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return createAccount(commandParts[2], "");
                }
            },
            new DoCommand() {
                @Override
                public int doIt() throws IOException {
                    return login(commandParts[1], "");
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
                    return save(currentAccount);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return logout(currentAccount);
                }
            },
            new DoCommand() {
                @Override
                public int doIt() {
                    return LoginMenu.help();
                }
            }
    };

    public static int findPatternIndex(String command, String[] commandParts) {
        if (commandParts.length == 3 && commandParts[0].toLowerCase().equals("create")
                && commandParts[1].toLowerCase().equals("account"))
            return 0;
        if (commandParts.length == 2 && commandParts[0].toLowerCase().equals("login"))
            return 1;
        for (int i = 0; i < commandPatterns.size(); i++) {
            if (command.toLowerCase().matches(commandPatterns.get(i).pattern()))
                return i;
        }
        return -1;
    }

    private static void readUsers() throws IOException {
        users.clear();
        MainProcess.readAccounts();
        users.addAll(Account.getAccounts());
    }

    public static int createAccount(String userName, String passWord) throws IOException {
        readUsers();
        for (Account user : users)
            if (user.getUserName().equals(userName))
                return 1; //message id : 1
        Account account = new Account(userName, passWord);
        Account.addAccount(account);
        users.add(account);
        String fileName = "src/model/accounts/" + userName + ".json";
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            gson.toJson(account, isr);
        }
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

    public int login(String userName, String password) throws IOException {
        readUsers();
        for (Account user : users) {
            if (user.getUserName().equals(userName)) {
                if (user.getPassword().equals(password)) {
                    currentAccount = user;
//                    loginMenu.setIsInLoginMenu(false);
//                    MainMenu mainMenu = new MainMenu(currentAccount); // correct ??
//                    mainMenu.getMainMenuProcess().setLoginMenu(loginMenu);
//                    mainMenu.run();
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
            LoginMenu.showMessage((i + 1) + "-UserName : " + users.get(i).getUserName() + " -Wins : " +
                    users.get(i).getNumberOfWins());
        return 0;
    }

    private static void sortUsers() {
        users.sort(Comparator.comparing(Account::getNumberOfWins).reversed()); // reversed ??
    }

    public static int save(Account account) throws IOException {
        String fileName = "src/model/accounts/" + account.getUserName() + ".json";
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            gson.toJson(account, isr);
        }
        return 0;
    }

    private int logout(Account account) {
        if (currentAccount.equals(account))
            currentAccount = null;
        return 0;
    }

    //setters
    public void setLoginMenu(LoginMenu loginMenu) {
        this.loginMenu = loginMenu;
    }
    //setters

    //getters

    public Account getCurrentAccount() {
        return currentAccount;
    }
    //getters
}
