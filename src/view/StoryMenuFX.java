package view;

import javafx.animation.ScaleTransition;
import javafx.event.Event;
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
import model.Match;
import model.Table;
import presenter.StoryMenuProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StoryMenuFX {
    public Pane start(Stage primaryStage, Account account) throws FileNotFoundException {
        Pane root = new Pane();
        Scene storyMenuScene = new Scene(new Pane(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(storyMenuScene.getWidth());
        root.setPrefHeight(storyMenuScene.getHeight());
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 40);
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/battleInit/pictures/obsidian_woods.jpg", root, false);

        VBox level1 = levelSetUp("src/view/sources/storyMenu/pictures/div-e-sefid.jpg", "DIV-E-SEFID", 1, storyMenuScene, font);

        VBox level2 = levelSetUp("src/view/sources/storyMenu/pictures/zahhak.jpg", "ZAHHAK", 2, storyMenuScene, font);

        VBox level3 = levelSetUp("src/view/sources/storyMenu/pictures/arash.jpg", "ARASH", 3, storyMenuScene, font);

        HBox storyMenuHBox = new HBox(level1, level2, level3);
        storyMenuHBox.setSpacing(storyMenuScene.getWidth() / 10);

        GraphicalCommonUsages.backSetting(root, storyMenuScene, account, "singlePlayer");
        root.getChildren().addAll(storyMenuHBox);
        storyMenuHBox.layoutXProperty().bind(root.widthProperty().subtract(storyMenuHBox.widthProperty()).divide(2));
        storyMenuHBox.layoutYProperty().bind(root.heightProperty().subtract(storyMenuHBox.heightProperty()).divide(2));
        // battleInitPrimary.setPadding(new Insets(50,0,50,0));
        storyMenuHBox.setBackground(new Background(new BackgroundFill(Color.grayRgb(20, 0.8),
                new CornerRadii(0), new javafx.geometry.Insets(0, 0, 0, 0))));
        handleSetOnMouseClicked(level1, level2, level3,account);
        //  return battleInitScene;
        return root;
    }

    private void handleSetOnMouseClicked(VBox level1, VBox level2, VBox level3, Account account) {
        level1.setOnMouseClicked(event -> {
            try {
                Main.setBattleFX(account, StoryMenuProcess.enterFirstLevel(account),true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        level2.setOnMouseClicked(event -> {
            try {
                Main.setBattleFX(account, StoryMenuProcess.enterSecondLevel(account),true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        level3.setOnMouseClicked(event -> {
            try {
                Main.setBattleFX(account, StoryMenuProcess.enterThirdLevel(account),true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void mouseMovementHandling(StackPane view, Text text, VBox vBox) {
        vBox.setOnMouseEntered(event -> {
            System.out.println(vBox.getLayoutX()+" "+vBox.getLayoutY() + text.getText().split("\n")[0]);
            switch (text.getText().split("\n")[0]) {
                case "DIV-E-SEFID":
                    try {
                        ((ImageView) view.getChildren().get(0)).setImage(new Image(new FileInputStream("src/view/sources/storyMenu/pictures/div-e-sefid2.jpg")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "ZAHHAK":
                    try {
                        ((ImageView) view.getChildren().get(0)).setImage(new Image(new FileInputStream("src/view/sources/storyMenu/pictures/zahhak2.jpg")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "ARASH":
                    try {
                        ((ImageView) view.getChildren().get(0)).setImage(new Image(new FileInputStream("src/view/sources/storyMenu/pictures/arash2.jpg")));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            view.setOpacity(1);
            ScaleTransition st = new ScaleTransition(Duration.millis(100), vBox);
            st.setFromX(1);
            st.setFromY(1);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
            text.setEffect(new Glow(0.5));
        });

        vBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch (text.getText().split("\n")[0]) {
                    case "DIV-E-SEFID":
                        try {
                            ((ImageView) view.getChildren().get(0)).setImage(new Image(new FileInputStream("src/view/sources/storyMenu/pictures/div-e-sefid.jpg")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ZAHHAK":
                        try {
                            ((ImageView) view.getChildren().get(0)).setImage(new Image(new FileInputStream("src/view/sources/storyMenu/pictures/zahhak.jpg")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ARASH":
                        try {
                            ((ImageView) view.getChildren().get(0)).setImage(new Image(new FileInputStream("src/view/sources/storyMenu/pictures/arash.jpg")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                view.setOpacity(0.6);
                ScaleTransition st = new ScaleTransition(Duration.millis(100), vBox);
                st.setFromX(1.1);
                st.setFromY(1.1);
                st.setToX(1);
                st.setToY(1);
                st.play();
                text.setEffect(new Glow(0));
            }
        });
//        vBox.setOnMouseClicked(event -> {
//            System.out.println("diiiiiv");
//            new GraphicalCommonUsages().mouseClickAudioPlay();
////                switch (text.getText()){
////                    case "SINGLE PLAYER":
////
////                }
//        });
    }

    private VBox levelSetUp(String address, String heroName, int mode, Scene battleInitScene, Font font) throws FileNotFoundException {
        Image levelImage = new Image(new FileInputStream(address));
        ImageView levelView = new ImageView(levelImage);
        levelView.setFitWidth(battleInitScene.getWidth() / 6);
        levelView.setFitHeight(battleInitScene.getHeight() / 6);
        StackPane view = new StackPane(levelView);
        view.setStyle("-fx-padding: 30;-fx-background-radius: 10;");
        view.setOpacity(0.6);
        Text description = new Text(heroName + "\n" + "MODE : " + mode);
        description.setFill(Color.WHITE);
        description.setFont(font);
        VBox levelVBox = new VBox(view, description, new Text());
        levelVBox.setSpacing(battleInitScene.getHeight() / 20);
        levelVBox.setAlignment(Pos.CENTER);
        mouseMovementHandling(view, description, levelVBox);
        return levelVBox;
    }
}
