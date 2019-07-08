package view;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class BattleInitFX {
    Stage stage;
    private Pane waiting;
    private HBox battleInitPrimary = new HBox();
    private Pane root = new Pane();

    public Pane start(Stage primaryStage, Account account) throws FileNotFoundException {
        stage = primaryStage;
        Scene battleInitScene = new Scene(new Pane(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(battleInitScene.getWidth());
        root.setPrefHeight(battleInitScene.getHeight());
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 40);
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/battleInit/pictures/obsidian_woods.jpg", root, false);

        VBox singlePlayer = singlePlayerSetUp(battleInitScene, font, account);

        VBox multiPlayer = multiPlayerSetUp(battleInitScene, font, account);

        battleInitPrimary = new HBox(singlePlayer, multiPlayer);
        battleInitPrimary.setSpacing(battleInitScene.getWidth() / 10);

        Image backToMain = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_back_corner.png"));
        ImageView backToMainView = new ImageView(backToMain);
        backToMainMenuViewSetting(root, battleInitScene, backToMainView, account);
        root.getChildren().addAll(battleInitPrimary);
        battleInitPrimary.layoutXProperty().bind(root.widthProperty().subtract(battleInitPrimary.widthProperty()).divide(2));
        battleInitPrimary.layoutYProperty().bind(root.heightProperty().subtract(battleInitPrimary.heightProperty()).divide(2));
        // battleInitPrimary.setPadding(new Insets(50,0,50,0));
        BackgroundFill background_fill = new BackgroundFill(javafx.scene.paint.Color.grayRgb(20, 0.8),
                new CornerRadii(0), new javafx.geometry.Insets(0, 0, 0, 0));
        battleInitPrimary.setBackground(new Background(background_fill));

        waiting = drawWaiting();
        waiting.relocate(battleInitScene.getWidth() * 0.25, battleInitScene.getHeight() * 0.15);
        waiting.setVisible(false);
        root.getChildren().add(waiting);

        //  return battleInitScene;
        return root;
    }

    private StackPane drawWaiting() throws FileNotFoundException {
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 40);
        StackPane pane = new StackPane();
        VBox vBox = new VBox();
        StackPane.setAlignment(vBox, Pos.CENTER);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        //
        ImageView waitingView = new ImageView(new Image(new FileInputStream("src/view/sources/battleInit/pictures/general_f1.png")));
        waitingView.setFitWidth(1920 / 2);
        waitingView.setPreserveRatio(true);

        AtomicReference<String> dots = new AtomicReference<>("");
        Label waiting = new Label("Waiting for player response" + dots);
        //
        AtomicInteger i = new AtomicInteger(1);
        Timeline waitingDot = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            i.getAndIncrement();
            i.updateAndGet(v -> v % 4);
            dots.set(".....".substring(0, i.get()));
            waiting.setText("Waiting for player response" + dots);
        }));
        waitingDot.setCycleCount(Integer.MAX_VALUE);
        waitingDot.play();
        //
        waiting.setFont(font);
        waiting.setTextFill(Color.WHITE);
        //
        vBox.getChildren().addAll(waitingView, new Text("\n"), waiting);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.grayRgb(20, 0.8),
                new CornerRadii(10), new javafx.geometry.Insets(0, 150, 0, 150))));
        pane.getChildren().add(vBox);
        return pane;
    }

    private VBox multiPlayerSetUp(Scene battleInitScene, Font font, Account account) throws FileNotFoundException {
        Image multiPlayerImage = new Image(new FileInputStream("src/view/sources/battleInit/pictures/multi.jpg"));
        ImageView multiPlayerView = new ImageView(multiPlayerImage);
        multiPlayerView.setFitWidth(battleInitScene.getWidth() / 4);
        multiPlayerView.setFitHeight(battleInitScene.getHeight() * 2 / 3);
        StackPane view = new StackPane(multiPlayerView);
        view.setStyle("-fx-padding: 30;-fx-background-radius: 10;");

        view.setOpacity(0.6);
        Text multiPlayerText = new Text("MULTI PLAYER");
        multiPlayerText.setFill(Color.WHITE);
        multiPlayerText.setFont(font);
        VBox multiPlayer = new VBox(view, multiPlayerText, new Text(""));
        multiPlayer.setSpacing(battleInitScene.getHeight() / 20);
        multiPlayer.setAlignment(Pos.CENTER);

        mouseMovementHandling(view, multiPlayerText, multiPlayer, account);

        return multiPlayer;
    }

    private void mouseMovementHandling(StackPane view, Text multiPlayerText, VBox multiPlayer, Account account) {
        multiPlayer.setOnMouseEntered(event -> {
            view.setOpacity(1);
            ScaleTransition st = new ScaleTransition(Duration.millis(100), multiPlayer);
            st.setFromX(1);
            st.setFromY(1);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
            multiPlayerText.setEffect(new Glow(0.5));
        });

        multiPlayer.setOnMouseExited(event -> {
            view.setOpacity(0.6);
            ScaleTransition st = new ScaleTransition(Duration.millis(100), multiPlayer);
            st.setFromX(1.1);
            st.setFromY(1.1);
            st.setToX(1);
            st.setToY(1);
            st.play();
            multiPlayerText.setEffect(new Glow(0.5));
        });
        multiPlayer.setOnMouseClicked(event -> {
            GraphicalCommonUsages.mouseClickAudioPlay();
            switch (multiPlayerText.getText()) {
                case "SINGLE PLAYER":
                    try {
                        Main.setSinglePlayerMenuFX(account);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "MULTI PLAYER":
                    try {
                        battleInitPrimary.getChildren().get(0).setVisible(false);
                        battleInitPrimary.getChildren().get(1).setVisible(false);
                        new CustomGameMenuFX().chooseDeck(account, account.getCollection().getSelectedDeck().getHero().getName(), new VBox(), new Scene(new Pane(), stage.getWidth(), stage.getHeight()), root, true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });
    }

    private VBox singlePlayerSetUp(Scene battleInitScene, Font font, Account account) throws FileNotFoundException {
        Image singlePlayerImage = new Image(new FileInputStream("src/view/sources/battleInit/pictures/single.jpg"));
        ImageView singlePlayerView = new ImageView(singlePlayerImage);
        singlePlayerView.setFitWidth(battleInitScene.getWidth() / 4);
        singlePlayerView.setFitHeight(battleInitScene.getHeight() * 2 / 3);
        StackPane view = new StackPane(singlePlayerView);
        view.setStyle("-fx-padding: 30;-fx-background-radius: 10;");
        view.setOpacity(0.6);
        Text singlePlayerText = new Text("SINGLE PLAYER");
        singlePlayerText.setFill(Color.WHITE);
        singlePlayerText.setFont(font);
        VBox singlePlayer = new VBox(view, singlePlayerText, new Text());
        singlePlayer.setSpacing(battleInitScene.getHeight() / 20);
        singlePlayer.setAlignment(Pos.CENTER);
        mouseMovementHandling(view, singlePlayerText, singlePlayer, account);
        return singlePlayer;
    }

    private void backToMainMenuViewSetting(Pane root, Scene mainMenuScene, ImageView backToLoginView, Account account) {
        backToLoginView.setFitWidth(mainMenuScene.getWidth() / 15);
        backToLoginView.setPreserveRatio(true);
        root.getChildren().addAll(backToLoginView);
        backToLoginView.setOpacity(0.5);
        backToLoginView.setOnMouseEntered(event -> backToLoginView.setOpacity(0.9));
        backToLoginView.setOnMouseExited(event -> backToLoginView.setOpacity(0.5));
        backToLoginView.setOnMouseClicked(event -> {
            if (!waiting.isVisible()) {
                try {
                    Main.setMainMenuFX(account);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                waiting.setVisible(false);
                battleInitPrimary.setVisible(true);
            }
        });
    }

    public void waitForPlayer(Pane root) throws FileNotFoundException {
        waiting = drawWaiting();
        waiting.relocate(1920 * 0.25, 1080 * 0.15);
        root.getChildren().add(waiting);
        battleInitPrimary.setVisible(false);

//        waiting.setVisible(true);
    }
}
