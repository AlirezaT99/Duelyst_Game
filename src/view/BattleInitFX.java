package view;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

public class BattleInitFX {

    public Pane start(Stage primaryStage, Account account) throws FileNotFoundException {
        Pane root = new Pane();

        Scene battleInitScene = new Scene(new Pane(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(battleInitScene.getWidth());
        root.setPrefHeight(battleInitScene.getHeight());
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 40);
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/battleInit/pictures/obsidian_woods.jpg", root, false);

        VBox singlePlayer = singlePlayerSetUp(battleInitScene, font, account);

        VBox multiPlayer = multiPlayerSetUp(battleInitScene, font, account);

        HBox battleInitPrimary = new HBox(singlePlayer, multiPlayer);
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

        //  return battleInitScene;
        return root;
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
            try {
                Main.setMainMenuFX(account);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
