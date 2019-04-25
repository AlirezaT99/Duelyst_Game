package presenter;

import com.google.gson.Gson;
import model.Account;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainProcess {

    public static void readFiles() throws IOException {
        // reading accounts
        File folder = new File("src/model/accounts");
        File[] listOfFiles = folder.listFiles();
        Gson gson = new Gson();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                Path path = new File(file.getPath()).toPath();
                Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                Account account = gson.fromJson(reader, Account.class);
                Account.getAccounts().add(account);
            }
        }
        // reading accounts

        //reading Spells
    }
}
