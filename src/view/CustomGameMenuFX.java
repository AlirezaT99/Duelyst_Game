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

public class CustomGameMenuFX {
    public Pane start(Stage primaryStage, Account account) throws FileNotFoundException {
        Pane root = new Pane();
        Scene customGameMenu = new Scene(new Pane(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(customGameMenu.getWidth());
        root.setPrefHeight(customGameMenu.getHeight());
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 25);
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/battleInit/pictures/obsidian_woods.jpg", root);

        VBox hero1 = opponentSetUp("src/view/sources/customGameMenu/pictures/div-e-sefid.jpg", "DIV-E-SEFID", customGameMenu, font,account);

        VBox hero2 = opponentSetUp("src/view/sources/customGameMenu/pictures/simorgh.jpg", "SIMORGH", customGameMenu, font,account);

        VBox hero3 = opponentSetUp("src/view/sources/customGameMenu/pictures/sevenheadeddragon.jpg", "SEVEN-HEADED DRAGON", customGameMenu, font,account);

        VBox hero4 = opponentSetUp("src/view/sources/customGameMenu/pictures/rakhsh.jpg", "RAKHSH", customGameMenu, font, account);

        VBox hero5 = opponentSetUp("src/view/sources/customGameMenu/pictures/zahhak.jpg", "ZAHHAK", customGameMenu, font, account);

        VBox hero6 = opponentSetUp("src/view/sources/customGameMenu/pictures/kaave.jpg", "KAAVE", customGameMenu, font, account);

        VBox hero7 = opponentSetUp("src/view/sources/customGameMenu/pictures/arash.jpg", "ARASH", customGameMenu, font, account);

        VBox hero8 = opponentSetUp("src/view/sources/customGameMenu/pictures/afsaane.jpg", "AFSAANE", customGameMenu, font, account);

        VBox hero9 = opponentSetUp("src/view/sources/customGameMenu/pictures/esfandiaar.png", "ESFANDIAAR", customGameMenu, font, account);

        VBox hero10 = opponentSetUp("src/view/sources/customGameMenu/pictures/rostam.png", "ROSTAM", customGameMenu, font, account);
        HBox firstLine = new HBox(hero1,hero2,hero3,hero4,hero5);
        firstLine.setSpacing(customGameMenu.getWidth() / 20);

        HBox secondLine = new HBox(hero6,hero7,hero8,hero9,hero10);
        secondLine.setSpacing(customGameMenu.getWidth() / 20);

        GraphicalCommonUsages.backSetting(root, customGameMenu, account, "singlePlayer");

        VBox customGame = new VBox(new Text("    "),firstLine,secondLine, new Text("    "));

        firstLine.setAlignment(Pos.CENTER);
        secondLine.setAlignment(Pos.CENTER);
        root.getChildren().addAll(customGame);
        customGame.layoutXProperty().bind(root.widthProperty().subtract(customGame.widthProperty()).divide(2));
        customGame.layoutYProperty().bind(root.heightProperty().subtract(customGame.heightProperty()).divide(2));

        BackgroundFill background_fill = new BackgroundFill(javafx.scene.paint.Color.grayRgb(20, 0.8),
                new CornerRadii(0), new javafx.geometry.Insets(0, 0, 0, 0));
        customGame.setBackground(new Background(background_fill));

        return root;
    }

    private VBox opponentSetUp(String address, String heroName, Scene battleInitScene, Font font, Account account) throws FileNotFoundException {
        Image levelImage = new Image(new FileInputStream(address));
        ImageView levelView = new ImageView(levelImage);
        levelView.setFitWidth(battleInitScene.getWidth() / 10);
        levelView.setFitHeight(battleInitScene.getHeight() / 10);
        StackPane view = new StackPane(levelView);
        view.setStyle("-fx-padding: 5;-fx-background-radius: 10;");
        view.setOpacity(0.6);
        Text description = new Text(heroName);
        description.setFill(Color.WHITE);
        description.setFont(font);
        VBox opponentBox = new VBox(view, description, new Text());
        opponentBox.setSpacing(battleInitScene.getHeight() / 20);
        opponentBox.setAlignment(Pos.CENTER);
        mouseMovementHandling(view, description, opponentBox,account);
        return opponentBox;
    }

    private void mouseMovementHandling(StackPane view, Text text, VBox vBox, Account account) {
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
                    case "STORY MODE":
                        try {
                            Main.setStoryMenuFX(account);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
    }
}
