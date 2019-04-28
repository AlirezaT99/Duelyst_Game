package view;

import presenter.StoryMenuProcess;

import java.io.IOException;
import java.util.Scanner;

public class StoryMenu {
    private boolean isInStoryMenu;
    private SinglePlayerMenu singlePlayerMenu;
    private StoryMenuProcess storyMenuProcess;
    private boolean hasRun = false;

    public StoryMenu(SinglePlayerMenu singlePlayerMenu) {
        isInStoryMenu = true;
        this.singlePlayerMenu = singlePlayerMenu;
        storyMenuProcess = new StoryMenuProcess(this);
    }

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!isInStoryMenu)
                break;
            if (!hasRun) {
                help();
                hasRun = true;
            }
            // if(scanner.hasNextLine()){
            String command = scanner.nextLine();
            storyMenuProcess.commandParts = command.split("[ ]");
            int commandType = StoryMenuProcess.findPatternIndex(command);
            if (commandType == -1)
                System.out.println("invalid input");
            else
                handleErrors(storyMenuProcess.DoCommands[commandType].doIt());
        }
        scanner.close();
    }

    public void handleErrors(int message) {

    }

    public int help() {
        showMessage("1. Hero : Div -e- Sefid  mode : 1   reward : 0500");
        showMessage("2. Hero : Zahhak         mode : 2   reward : 1000");
        showMessage("3. Hero : Arash          mode : 3   reward : 1500");
        showMessage("4. exit");
        showMessage("5. help");
        return 0;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    //getters
    public SinglePlayerMenu getSinglePlayerMenu() {
        return singlePlayerMenu;
    }
    //getters

    //setters
    public void setInStoryMenu(boolean inStoryMenu) {
        isInStoryMenu = inStoryMenu;
    }

    public void setHasRun(boolean hasRun) {
        this.hasRun = hasRun;
    }
    //setters
}
