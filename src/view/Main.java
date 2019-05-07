package view;

import java.io.IOException;

public class Main {
    private static LoginMenu loginMenu = new LoginMenu();

    public static void main(String[] args) throws IOException {
        try {
            presenter.MainProcess.readFiles();
            loginMenu.run();
        } catch (Exception e) {

        }
    }
}
