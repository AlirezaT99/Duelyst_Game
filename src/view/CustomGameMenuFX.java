package view;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
import model.*;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import presenter.SinglePlayerMenuProcess;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CustomGameMenuFX {
    Stage stage;

    public Pane start(Stage primaryStage, Account account) throws FileNotFoundException {
        Pane root = new Pane();
        stage = primaryStage;
        Scene customGameMenu = new Scene(new Pane(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(customGameMenu.getWidth());
        root.setPrefHeight(customGameMenu.getHeight());
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 25);
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/battleInit/pictures/obsidian_woods.jpg", root, false);
        VBox customGame = new VBox();

        VBox hero1 = opponentSetUp("src/view/sources/customGameMenu/pictures/div-e-sefid.jpg", "DIV-E-SEFID", customGameMenu, font, account, customGame, root);

        VBox hero2 = opponentSetUp("src/view/sources/customGameMenu/pictures/simorgh.jpg", "SIMORGH", customGameMenu, font, account, customGame, root);

        VBox hero3 = opponentSetUp("src/view/sources/customGameMenu/pictures/sevenheadeddragon.jpg", "SEVEN-HEADED DRAGON", customGameMenu, font, account, customGame, root);

        VBox hero4 = opponentSetUp("src/view/sources/customGameMenu/pictures/rakhsh.jpg", "RAKHSH", customGameMenu, font, account, customGame, root);

        VBox hero5 = opponentSetUp("src/view/sources/customGameMenu/pictures/zahhak.jpg", "ZAHHAK", customGameMenu, font, account, customGame, root);

        VBox hero6 = opponentSetUp("src/view/sources/customGameMenu/pictures/kaave.jpg", "KAAVE", customGameMenu, font, account, customGame, root);

        VBox hero7 = opponentSetUp("src/view/sources/customGameMenu/pictures/arash.jpg", "AARASH", customGameMenu, font, account, customGame, root);

        VBox hero8 = opponentSetUp("src/view/sources/customGameMenu/pictures/afsaane.jpg", "AFSAANE", customGameMenu, font, account, customGame, root);

        VBox hero9 = opponentSetUp("src/view/sources/customGameMenu/pictures/esfandiaar.png", "ESFANDIAR", customGameMenu, font, account, customGame, root);

        VBox hero10 = opponentSetUp("src/view/sources/customGameMenu/pictures/rostam.png", "ROSTAM", customGameMenu, font, account, customGame, root);
        HBox firstLine = new HBox(new Text(""), hero1, hero2, hero3, hero4, hero5, new Text(""));
        firstLine.setSpacing(customGameMenu.getWidth() / 20);

        HBox secondLine = new HBox(hero6, hero7, hero8, hero9, hero10);
        secondLine.setSpacing(customGameMenu.getWidth() / 20);

        GraphicalCommonUsages.backSetting(root, customGameMenu, account, "singlePlayer");

        customGame.getChildren().addAll(new Text(""), firstLine, secondLine);
        customGame.setSpacing(customGameMenu.getWidth() / 20);
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

    private VBox opponentSetUp(String address, String heroName, Scene battleInitScene, Font font, Account account, VBox motherset, Pane root) throws FileNotFoundException {
        Image levelImage = new Image(new FileInputStream(address));
        ImageView levelView = new ImageView(levelImage);
        levelView.setFitWidth(battleInitScene.getWidth() / 10);
        levelView.setFitHeight(battleInitScene.getHeight() / 10);
        StackPane view = new StackPane(levelView);
        view.setStyle("-fx-padding: 5;-fx-background-radius: 10;");
        view.setOpacity(0.6);
        Text description = new Text(heroName);
        description.setFill(Color.BISQUE);
        description.setFont(font);
        VBox opponentBox = new VBox(view, description, new Text());
        opponentBox.setSpacing(battleInitScene.getHeight() / 20);
        opponentBox.setAlignment(Pos.CENTER);
        mouseMovementHandling(view, description, opponentBox, account, motherset, battleInitScene, root);
        return opponentBox;
    }

    private void mouseMovementHandling(StackPane view, Text text, VBox vBox, Account account, VBox motherSet, Scene scene, Pane root) {
        vBox.setOnMouseEntered(event -> {
            view.setOpacity(1);
            ScaleTransition st = new ScaleTransition(Duration.millis(100), vBox);
            st.setFromX(1);
            st.setFromY(1);
            st.setToX(1.15);
            st.setToY(1.15);
            st.play();
            text.setEffect(new Glow(0.5));
        });

        vBox.setOnMouseExited(event -> {
            view.setOpacity(0.6);
            ScaleTransition st = new ScaleTransition(Duration.millis(100), vBox);
            st.setFromX(1.15);
            st.setFromY(1.15);
            st.setToX(1);
            st.setToY(1);
            st.play();
            text.setEffect(new Glow(0.5));
        });
        vBox.setOnMouseClicked(event -> {
            GraphicalCommonUsages.mouseClickAudioPlay();
            try {
                chooseDeck(account, text.getText(), motherSet, scene, root, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void chooseDeck(Account account, String heroName, VBox motherSet, Scene scene, Pane root, Boolean accessFromMulti) throws FileNotFoundException {
        for (Node child : motherSet.getChildren()) {
            if (!(child instanceof Text)) {
                FadeTransition ft = new FadeTransition(Duration.millis(300), child);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.play();
                ft.setOnFinished(event -> child.setVisible(false));
            }
        }
        VBox replacingVBox = new VBox();
        VBox.setMargin(replacingVBox, motherSet.getInsets());
        BackgroundFill background_fill = new BackgroundFill(Color.grayRgb(20, 0),
                new CornerRadii(0), new javafx.geometry.Insets(0, 0, 0, 0));
        replacingVBox.setBackground(new Background(background_fill));
        replacingVBox.layoutXProperty().bind(root.widthProperty().subtract(replacingVBox.widthProperty()).divide(2));
        replacingVBox.layoutYProperty().bind(root.heightProperty().subtract(replacingVBox.heightProperty()).divide(2));

        final Font deckFont = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 40);

        Label choose_your_deck = new Label("CHOOSE YOUR DECK");
        choose_your_deck.setFont(deckFont);
        choose_your_deck.setTextFill(Color.WHITE);

        VBox decks = new VBox();
        for (Deck deck : account.getCollection().getDecks()) {
            if (account.getCollection().validateDeck(deck)) {
                Text text = new Text(deck.getName());
                text.setFont(deckFont);
                text.setFill(Color.WHITE);
                decks.getChildren().add(text);
            }
        }
        decks.setSpacing(100 / 3);
        for (Node child : decks.getChildren()) {
            child.setOnMouseEntered(event -> child.setEffect(new Glow(0.5)));
            child.setOnMouseExited(event -> child.setEffect(new Glow(0)));
            child.setOnMouseClicked(event -> {
                try {
                    selectMode(account, heroName, ((Text) child).getText(), replacingVBox, scene, root, accessFromMulti);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            //todo : setOnMouseClicked -> make a match
        }
        decks.setAlignment(Pos.CENTER);
        replacingVBox.setSpacing(10);
        replacingVBox.getChildren().addAll(choose_your_deck, decks);
        root.getChildren().addAll(replacingVBox);
    }

    private void selectMode(Account account, String heroName, String deckName, VBox motherSet, Scene scene, Pane root, Boolean accessFromMultiPlayer) throws FileNotFoundException {
        for (Node child : motherSet.getChildren()) {
            FadeTransition ft = new FadeTransition(Duration.millis(250), child);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            ft.setOnFinished(event -> child.setVisible(false));
        }
        VBox replacingVBox = new VBox();
        VBox.setMargin(replacingVBox, motherSet.getInsets());
        BackgroundFill background_fill = new BackgroundFill(Color.grayRgb(30, 0),
                new CornerRadii(0), new javafx.geometry.Insets(0, 0, 0, 0));
        replacingVBox.setBackground(new Background(background_fill));
        replacingVBox.layoutXProperty().bind(root.widthProperty().subtract(replacingVBox.widthProperty()).divide(2));
        replacingVBox.layoutYProperty().bind(root.heightProperty().subtract(replacingVBox.heightProperty()).divide(2));

        final Font modeFont = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 40);

        Label choose_your_mode = new Label("CHOOSE YOUR MODE");
        choose_your_mode.setFont(Font.font(modeFont.getName(), 50));
        choose_your_mode.setTextFill(Color.WHITE);

        HBox modes = new HBox();
        Label mode_1 = new Label("MODE  1");
        mode_1.setFont(modeFont);
        mode_1.setTextFill(Color.WHITE);
        Label mode_2 = new Label("MODE  2");
        mode_2.setFont(modeFont);
        mode_2.setTextFill(Color.WHITE);
        VBox mode_3 = new VBox();
        Label mode_3_text = new Label("MODE  3");
        mode_3_text.setFont(modeFont);
        mode_3_text.setTextFill(Color.WHITE);
        TextField mode_3_flags = new TextField("number of flags");
        mode_3_flags.setStyle("-fx-background-color:rgba(175, 175, 175, 0.5) ; -fx-font-size:  20px; ");
        mode_3_flags.setPrefWidth(mode_3_text.getWidth());
        mode_3.getChildren().addAll(mode_3_text, mode_3_flags);
        modes.getChildren().addAll(mode_1, mode_2, mode_3);

        for (Node child : modes.getChildren()) {
            child.setOnMouseEntered(event -> {
                if (child instanceof Label)
                    child.setEffect(new Glow(0.8));
                else
                    ((VBox) child).getChildren().get(0).setEffect(new Glow(0.8));
            });

            child.setOnMouseExited(event -> {
                if (child instanceof Label)
                    child.setEffect(new Glow(0));
                else {
                    ((VBox) child).getChildren().get(0).setEffect(new Glow(0));
                }
            });
            if (!accessFromMultiPlayer)
                child.setOnMouseClicked(event -> {
                int mode;
                int numberOfFlags = 0;

                if (child instanceof Label) {
                    if (((Label) child).getText().equals("MODE  1")) {
                        mode = 1;
                        try {
                            System.out.println(heroName);
                            Match match = SinglePlayerMenuProcess.customGame(account, mode, deckName, Hero.getHeroByName(heroName), numberOfFlags);
                            Main.setBattleFX(account, match, false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    if (((Label) child).getText().equals("MODE  2")) {
                        mode = 2;
                        try {
                            Match match = SinglePlayerMenuProcess.customGame(account, mode, deckName, Hero.getHeroByName(heroName), 1);
                            Main.setBattleFX(account, match, false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (child instanceof VBox) {
                    mode = 3;
                    try {
                        if (validateNumberOfFlags(mode_3_flags, scene, root))
                            numberOfFlags = Integer.parseInt(mode_3_flags.getText());
                        Match match = SinglePlayerMenuProcess.customGame(account, mode, deckName, Hero.getHeroByName(heroName), numberOfFlags);
                        Main.setBattleFX(account, match, false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            else
                child.setOnMouseClicked(event -> {
                    try {
                        new BattleInitFX().waitForPlayer(root);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        }
        modes.setSpacing(10);
        replacingVBox.setSpacing(10);
        replacingVBox.getChildren().addAll(choose_your_mode, modes);
        root.getChildren().addAll(replacingVBox);
    }

    private boolean validateNumberOfFlags(TextField textField, Scene scene, Pane root) throws FileNotFoundException {
        try {
            if (Integer.parseInt(textField.getText()) < 1 || Integer.parseInt(textField.getText()) > 48) {
                GraphicalCommonUsages.okPopUp("invalid number of flags", scene, root);
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            GraphicalCommonUsages.okPopUp("invalid number of flags", scene, root);
        }
        return true;
    }


}
