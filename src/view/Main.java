package view;

import java.io.IOException;

public class Main {
    private static LoginMenu loginMenu = new LoginMenu();

    public static void main(String[] args) throws IOException {
            presenter.MainProcess.readFiles();
            loginMenu.run();

    }
}
