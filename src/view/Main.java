package view;
import presenter.MainProcess;
import java.io.IOException;

public class Main {
    static LoginMenu loginMenu;
    public static void main(String args[])throws IOException {
        String[] initializer= new String[2];
        presenter.MainProcess.readFiles();
        loginMenu.main(initializer);
    }
    // todo : read files
}
