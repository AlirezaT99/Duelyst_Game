package view;

import com.sun.scenario.effect.impl.prism.PrImage;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Account;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main  extends Application {
    private static LoginMenu loginMenu = new LoginMenu();
    private static Login login;
    private static MainMenuFX mainMenuFX;
    private static Stage primaryStage;
    private static Scene currentScene;
    private static Rectangle2D primaryScreenBounds;
    public static void main (String[] args) throws IOException {
            presenter.MainProcess.readFiles();
            launch(args);
            loginMenu.run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        primaryScreenBounds =  GraphicalCommonUsages.initPrimaryStage(primaryStage);
        primaryStage.setTitle("Duelyst");
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> primaryStage.setFullScreen(true));
       // primaryStage.setFullScreen(true);
        login = new Login();
        currentScene = login.start(primaryStage);
        primaryStage.setScene(currentScene);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        backgroundMusicPlay();
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void setMainMenuFX(Account account) throws FileNotFoundException {
        Main.mainMenuFX = new MainMenuFX();
        currentScene = mainMenuFX.start(primaryStage);
        primaryStage.setScene(currentScene);
    }
    public static void setLoginMenu() throws FileNotFoundException {
        if(Main.login == null)
        Main.login = new Login();
        currentScene = login.start(primaryStage);
        primaryStage.setScene(currentScene);
    }
    private void backgroundMusicPlay() {
        javafx.scene.media.AudioClip audioClip = new javafx.scene.media.AudioClip(this.getClass().getResource("sources/loginMenu/music/mainmenu_v2c_looping.m4a").toString());
        audioClip.setCycleCount(Integer.MAX_VALUE);
        audioClip.play();
    }
}
