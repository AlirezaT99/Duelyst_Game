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

public class StoryMenuFX {
    public Pane start(Stage primaryStage, Account account) throws FileNotFoundException {
        Pane root = new Pane();
        Scene storyMenuScene = new Scene(new Pane(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(storyMenuScene.getWidth());
        root.setPrefHeight(storyMenuScene.getHeight());
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 40);
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/battleInit/pictures/obsidian_woods.jpg", root);

        VBox level1 = levelSetUp("src/view/sources/storyMenu/pictures/div-e-sefid.png", "DIV-E-SEFID", 1, storyMenuScene, font);

        VBox level2 = levelSetUp("src/view/sources/storyMenu/pictures/zahhak.png", "ZAHHAK", 2, storyMenuScene, font);

        VBox level3 = levelSetUp("src/view/sources/storyMenu/pictures/arash.png", "ARASH", 3, storyMenuScene, font);

        HBox storyMenuHBox = new HBox(level1, level2, level3);
        storyMenuHBox.setSpacing(storyMenuScene.getWidth() / 10);

        GraphicalCommonUsages.backSetting(root,storyMenuScene,account,"singlePlayer");
        root.getChildren().addAll(storyMenuHBox);
        storyMenuHBox.layoutXProperty().bind(root.widthProperty().subtract(storyMenuHBox.widthProperty()).divide(2));
        storyMenuHBox.layoutYProperty().bind(root.heightProperty().subtract(storyMenuHBox.heightProperty()).divide(2));
        // battleInitPrimary.setPadding(new Insets(50,0,50,0));
        BackgroundFill background_fill = new BackgroundFill(javafx.scene.paint.Color.grayRgb(20, 0.8),
                new CornerRadii(0), new javafx.geometry.Insets(0, 0, 0, 0));
        storyMenuHBox.setBackground(new Background(background_fill));

        //  return battleInitScene;
        return root;
    }

    private void mouseMovementHandling(StackPane view, Text text, VBox vBox) {
        vBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.setOpacity(1);
                ScaleTransition st = new ScaleTransition(Duration.millis(100),vBox);
                st.setFromX(1);
                st.setFromY(1);
                st.setToX(1.1);
                st.setToY(1.1);
                st.play();
                text.setEffect(new Glow(0.5));
            }
        });

        vBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.setOpacity(0.6);
                ScaleTransition st = new ScaleTransition(Duration.millis(100),vBox);
                st.setFromX(1.1);
                st.setFromY(1.1);
                st.setToX(1);
                st.setToY(1);
                st.play();
                text.setEffect(new Glow(0.5));
            }
        });
        vBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new GraphicalCommonUsages().mouseClickAudioPlay();
                switch (text.getText()){
                    case "SINGLE PLAYER":

                }
            }
        });
    }

    private VBox levelSetUp(String address, String heroName, int mode, Scene battleInitScene, Font font) throws FileNotFoundException {
        Image levelImage = new Image(new FileInputStream(address));
        ImageView levelView = new ImageView(levelImage);
        levelView.setFitWidth(battleInitScene.getWidth()/6);
        levelView.setFitHeight(battleInitScene.getHeight()/6);
        StackPane view = new StackPane(levelView);
        view.setStyle("-fx-padding: 30;-fx-background-radius: 10;");
        view.setOpacity(0.6);
        Text description = new Text(heroName+"\n"+"MODE : "+mode);
        description.setFill(Color.WHITE);
        description.setFont(font);
        VBox levelVBox = new VBox(view,description,new Text());
        levelVBox.setSpacing(battleInitScene.getHeight()/20);
        levelVBox.setAlignment(Pos.CENTER);
        mouseMovementHandling(view,description,levelVBox);
        return levelVBox;
    }
}
