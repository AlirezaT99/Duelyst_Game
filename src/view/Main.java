package view;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        presenter.MainProcess.readFiles();
        LoginMenu.run();
    }
    // todo : read files
}
