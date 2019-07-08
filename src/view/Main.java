package view;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import model.Account;
import model.Message.Utils;
import model.client.Client;
import model.Match;
import presenter.BattleMenuProcess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {
    private static LoginMenu loginMenu = new LoginMenu();
    private static Login login;
    private static MainMenuFX mainMenuFX;
    private static BattleInitFX battleInitFX;
    private static ShopMenuFX shopMenuFX;
    private static CollectionMenuFX collectionMenuFX;
    private static SinglePlayerMenuFX singlePlayerMenuFX;
    private static CustomGameMenuFX customGameMenuFX;
    private static StoryMenuFX storyMenuFX;
    private static BattleFX battleFx;
    private static ScoreBoardFX scoreBoardFX;
    private static Stage primaryStage;
    private static Scene currentScene;
    private static Rectangle2D primaryScreenBounds;
    private static AudioClip audioClip;

    public static void main(String[] args) throws IOException {
        presenter.MainProcess.readFiles();
        Client.getInstance().start();
        launch(args);
        loginMenu.run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        primaryScreenBounds = GraphicalCommonUsages.initPrimaryStage(primaryStage);
        primaryStage.setTitle("Duelyst");
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> primaryStage.setFullScreen(true));
        login = new Login();
        currentScene = new Scene(login.start(primaryStage), primaryStage.getWidth(), primaryStage.getHeight());
        javafx.scene.image.Image cursor = new Image(new FileInputStream("src/view/sources/common/cursors/auto.png"));
        currentScene.setCursor(new ImageCursor(cursor));
        primaryStage.setScene(currentScene);
        primaryStage.setOnCloseRequest(event -> {
            Utils util = new Utils(Client.getInstance().getAuthCode(),true);
            Client.getInstance().sendData(util);
            System.exit(0);
        });
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
        currentScene.setRoot(mainMenuFX.start(primaryStage, account));
        primaryStage.setScene(currentScene);
        backgroundMusicNotBattlePlay();
    }

    public static void setLoginMenu() throws FileNotFoundException {
        if (Main.login == null)
            Main.login = new Login();
        currentScene.setRoot(login.start(primaryStage));
        primaryStage.setScene(currentScene);
    }

    public static void setShopMenuFX(Account account) throws FileNotFoundException {
        Main.shopMenuFX = new ShopMenuFX(account);
        currentScene.setRoot(shopMenuFX.start(primaryStage));
    }

    public static void setCollectionMenuFX(Account account) throws FileNotFoundException {
        Main.collectionMenuFX = new CollectionMenuFX(account);
        currentScene.setRoot(collectionMenuFX.start(primaryStage));
    }

    public static void setBattleMenuFX(Account account) throws FileNotFoundException {
        Main.battleInitFX = new BattleInitFX();
        currentScene.setRoot(battleInitFX.start(primaryStage, account));
        primaryStage.setScene(currentScene);
    }

    public static void setSinglePlayerMenuFX(Account account) throws FileNotFoundException {
        if (singlePlayerMenuFX == null)
            Main.singlePlayerMenuFX = new SinglePlayerMenuFX();
        currentScene.setRoot(singlePlayerMenuFX.start(primaryStage, account));
        primaryStage.setScene(currentScene);
    }

    public static void setStoryMenuFX(Account account) throws FileNotFoundException {
        if (storyMenuFX == null)
            Main.storyMenuFX = new StoryMenuFX();
        currentScene.setRoot(storyMenuFX.start(primaryStage, account));
        primaryStage.setScene(currentScene);
    }

    public static void setCustomGameMenuFX(Account account) throws FileNotFoundException {
        if (customGameMenuFX == null)
            Main.customGameMenuFX = new CustomGameMenuFX();
        currentScene.setRoot(customGameMenuFX.start(primaryStage, account));
        primaryStage.setScene(currentScene);
    }

    public static void setScoreBoardFX(Account account) throws FileNotFoundException {
        scoreBoardFX = new ScoreBoardFX();
        currentScene.setRoot(scoreBoardFX.start(primaryStage, Client.getInstance().getAuthCode(), account));
        primaryStage.setScene(currentScene);
    }

    public static void setBattleFX(Account firstPlayer, Match match, boolean storyMode) throws FileNotFoundException {
        battleFx = new BattleFX();
        BattleMenuProcess.setMatch(match);
        backgroundMusicBattlePlay();
        currentScene.setRoot(battleFx.start(match, storyMode, getPrimaryStage(), firstPlayer));
    }

    public static void backToLastRoots(Account account, String whereTo) throws FileNotFoundException {
        switch (whereTo) {
            case "singlePlayer":
                setSinglePlayerMenuFX(account);
                break;
            case "battleInit":
                setBattleMenuFX(account);
                break;
            case "mainMenu":
                setMainMenuFX(account);
        }
    }

    public static Scene getScene() {
        return currentScene;
    }

    private static void backgroundMusicNotBattlePlay() {
        if (audioClip != null && audioClip.isPlaying())
            audioClip.stop();
        audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/loginMenu/music/mainmenu_v2c_looping.m4a").toString());
        audioClip.setCycleCount(Integer.MAX_VALUE);
        audioClip.play();
    }

    private static void backgroundMusicBattlePlay() {
        audioClip.stop();
        audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/Battle/music/music_battlemap_songhai.m4a").toString());
        audioClip.setCycleCount(Integer.MAX_VALUE);
        audioClip.setVolume(0.7);
        audioClip.play();
    }
}
