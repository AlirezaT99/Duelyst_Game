package view;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Account;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {
    private static LoginMenu loginMenu = new LoginMenu();
    private static Login login;
    private static MainMenuFX mainMenuFX;
    private static BattleInitFX battleInitFX;
    private static ShopMenuFX shopMenuFX;
    private static Stage primaryStage;
    private static Scene currentScene;
    private static Rectangle2D primaryScreenBounds;

    public static void main(String[] args) throws IOException {
        presenter.MainProcess.readFiles();
        launch(args);
        loginMenu.run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        primaryScreenBounds = GraphicalCommonUsages.initPrimaryStage(primaryStage);
        primaryStage.setTitle("Duelyst");
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> primaryStage.setFullScreen(true));
        // primaryStage.setFullScreen(true);
        login = new Login();
        currentScene = new Scene(login.start(primaryStage), primaryStage.getWidth(), primaryStage.getHeight());
        javafx.scene.image.Image cursor = new Image(new FileInputStream("src/view/sources/common/cursors/auto.png"));
        currentScene.setCursor(new ImageCursor(cursor));
//        currentScene = login.start(primaryStage);
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

    public static void setLoginMenu() throws FileNotFoundException {
        if (Main.login == null)
            Main.login = new Login();
        currentScene.setRoot(login.start(primaryStage));
        primaryStage.setScene(currentScene);
        //   primaryStage.setFullScreen(true);
    }

    public static void setMainMenuFX(Account account) throws FileNotFoundException {
        Main.mainMenuFX = new MainMenuFX();
        currentScene.setRoot(mainMenuFX.start(primaryStage, account));
        primaryStage.setScene(currentScene);
    }

    public static void setShopMenuFX(Account account) throws FileNotFoundException {
        Main.shopMenuFX = new ShopMenuFX(account);
        currentScene.setRoot(shopMenuFX.start(primaryStage));
        primaryStage.setScene(currentScene);
    }

    public static void setBattleMenuFX(Account account) throws FileNotFoundException {
        Main.battleInitFX = new BattleInitFX();
        currentScene.setRoot(battleInitFX.start(primaryStage, account));
        primaryStage.setScene(currentScene);
        primaryStage.setFullScreen(true);
    }

    private void backgroundMusicPlay() {
        javafx.scene.media.AudioClip audioClip = new javafx.scene.media.AudioClip(this.getClass().getResource("sources/loginMenu/music/mainmenu_v2c_looping.m4a").toString());
        audioClip.setCycleCount(Integer.MAX_VALUE);
        audioClip.play();
    }
}
