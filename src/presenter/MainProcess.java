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

public class MainProcess {

    public static void readFiles() throws IOException {
        // reading accounts
        File folder = new File("src/model/accounts");
        File[] listOfFiles = folder.listFiles();
        Gson gson = new Gson();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                Path path = new File(file.getPath()).toPath();
                Reader reader = Files.newBufferedReader(path,
                        StandardCharsets.UTF_8);
                Account account = gson.fromJson(reader, Account.class);
                Account.getAccounts().add(account);
            }
        }
        // reading accounts

        //reading Spells
    }
}
