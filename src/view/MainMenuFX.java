package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Account;
import presenter.LoginMenuProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class MainMenuFX {
    private Account currentAccount;

    public Pane start(Stage primaryStage, Account account) throws FileNotFoundException {
        currentAccount = account;
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-light-webfont.ttf")), 30);
        final Font fontSmall = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-light-webfont.ttf")), 15);
        Pane root = new Pane();
        root.setPrefWidth(primaryStage.getWidth());
        root.setPrefHeight(primaryStage.getHeight());
        Pane fakeRoot = new Pane();
        Random random = new Random();
        int backGroundNumber = random.nextInt(2) + 1;
        Scene mainMenuScene = new Scene(fakeRoot, primaryStage.getWidth(), primaryStage.getHeight());
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/mainMenu/backgrounds/" + backGroundNumber + ".jpg", root, false);
        Image cursor = new Image(new FileInputStream("src/view/sources/common/cursors/auto.png"));
        mainMenuScene.setCursor(new ImageCursor(cursor));
        Image arrow = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/dialogue.png"));
        Text playText = new Text("PLAY");
        setText(font, playText);
        HBox play = new HBox();
        play.getChildren().addAll(getArrowView(mainMenuScene, arrow), playText);
        Text collectionText = new Text("COLLECTION");
        setText(font, collectionText);
        HBox collection = new HBox();
        collection.getChildren().addAll(getArrowView(mainMenuScene, arrow), collectionText);
        Text shopText = new Text("SHOP");
        setText(font, shopText);
        HBox shop = new HBox();
        shop.getChildren().addAll(getArrowView(mainMenuScene, arrow), shopText);
        Text watchText = new Text("WATCH");
        setText(font, watchText);
        HBox watch = new HBox();
        watch.getChildren().addAll(getArrowView(mainMenuScene, arrow), watchText);
        Text saveText = new Text("SAVE");
        setText(font, saveText);
        HBox save = new HBox();
        save.getChildren().addAll(getArrowView(mainMenuScene, arrow), saveText);
        VBox textQueries = new VBox();
        textQueries.setSpacing(mainMenuScene.getHeight() / 100);
        textQueries.setPadding(new Insets(mainMenuScene.getHeight() / 3, mainMenuScene.getWidth() * 2 / 3, mainMenuScene.getWidth() / 5, mainMenuScene.getWidth() / 6));
        textQueries.getChildren().addAll(play, collection, shop, watch, save);
        root.getChildren().addAll(textQueries);
        Image brandImage = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/brand_duelyst.png"));
        ImageView brandView = new ImageView(brandImage);
        // setting brand imageView
        brandView.relocate(mainMenuScene.getWidth() / 8, mainMenuScene.getHeight() / 10);
        brandView.setFitHeight(mainMenuScene.getHeight() / 7);
        brandView.setFitWidth(mainMenuScene.getWidth() / 3);
        //setting done
//
        //
        Image backToLogin = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_back_corner.png"));
        ImageView backToLoginView = new ImageView(backToLogin);
        backToLoginViewSetting(root, mainMenuScene, backToLoginView);

        VBox addCard = new VBox();
        Text addCardText = new Text("ADD CARD");
        Image addCardImage = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/quests.png"));
        settingAddNewCard(fontSmall, mainMenuScene, addCard, addCardText, addCardImage);

        root.getChildren().add(addCard);
        root.getChildren().addAll(brandView);
        textGlowEffect(play, mainMenuScene, root);
        textGlowEffect(collection, mainMenuScene, root);
        textGlowEffect(shop, mainMenuScene, root);
        textGlowEffect(watch, mainMenuScene, root);
        textGlowEffect(save, mainMenuScene, root);
        return root;
    }

    private void settingAddNewCard(Font fontSmall, Scene mainMenuScene, VBox addCard, Text addCardText, Image addCardImage) {
        ImageView addCardView = new ImageView(addCardImage);
        addCardText.setFont(fontSmall);
        addCardText.setFill(Color.WHITE);
        HBox firstLine = new HBox();
        firstLine.getChildren().addAll(addCardView);
        HBox secondLine = new HBox();
        secondLine.getChildren().addAll(addCardText);
        addCardView.setFitWidth(mainMenuScene.getWidth() / 20);
        addCard.setPrefWidth(addCardView.getFitWidth());
        addCardView.setPreserveRatio(true);
        addCard.relocate(mainMenuScene.getWidth() * 9 / 10, mainMenuScene.getHeight() * 5 / 6);
        addCard.getChildren().addAll(firstLine, secondLine);
        firstLine.setAlignment(Pos.CENTER);
        secondLine.setAlignment(Pos.CENTER);
        addCardView.setOpacity(0.7);
        addCard.setOnMouseEntered(event -> {
            addCardView.setOpacity(0.9);
            addCardView.setEffect(new Glow(0.5));
            addCardText.setEffect(new Glow(0.5));
        });
        addCard.setOnMouseExited(event -> {
            addCardView.setOpacity(0.7);
            addCardView.setEffect(new Glow(0));
            addCardText.setEffect(new Glow(0));
        });
    }

    private void friendsSetting(VBox friends, ImageView friendsView, Text friendsText, Scene scene) {
        friendsView.setFitWidth(scene.getWidth() / 10);
        friendsView.setPreserveRatio(true);
        friendsView.setEffect(new Glow(0));
        friendsView.setOpacity(0.9);
        friendsView.setOnMouseEntered(event -> {
            friendsText.setEffect(new Glow(1));
            friendsView.setEffect(new Glow(0.4));
        });
        friendsView.setOnMouseExited(event -> {
            friendsText.setEffect(new Glow(0));
            friendsView.setEffect(new Glow(0));
        });
    }

    private void backToLoginViewSetting(Pane root, Scene mainMenuScene, ImageView backToLoginView) {
        backButtonAdjustment(root, mainMenuScene, backToLoginView);
        backToLoginView.setOnMouseClicked(event -> {
            try {
                Main.setLoginMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    static void backButtonAdjustment(Pane root, Scene mainMenuScene, ImageView backToLoginView) {
        backToLoginView.setFitWidth(mainMenuScene.getWidth() / 15);
        backToLoginView.setPreserveRatio(true);
        root.getChildren().addAll(backToLoginView);
        backToLoginView.setOpacity(0.5);
        backToLoginView.setOnMouseEntered(event -> backToLoginView.setOpacity(0.9));
        backToLoginView.setOnMouseExited(event -> backToLoginView.setOpacity(0.5));
    }

    private ImageView getArrowView(Scene mainMenuScene, Image arrow) {
        ImageView shopArrowView = new ImageView(arrow);
        shopArrowView.setFitWidth(mainMenuScene.getWidth() / 50);
        shopArrowView.setPreserveRatio(true);
        shopArrowView.setVisible(false);
        return shopArrowView;
    }

    private void textGlowEffect(HBox playText, Scene scene, Pane root) {
        playText.setOnMouseEntered(event -> {
            playText.getChildren().get(0).setVisible(true);
            playText.setEffect(new Glow(1));
            mouseHoverAudioPlay();
        });
        playText.setOnMouseExited(event -> {
            playText.getChildren().get(0).setVisible(false);
            playText.setEffect(new Glow(0));
        });
        playText.setOnMouseClicked(event -> {
            new GraphicalCommonUsages().mouseClickAudioPlay();
            switch (((Text) playText.getChildren().get(1)).getText()) {
                case "PLAY":
                    try {
                        if (currentAccount.getCollection().getSelectedDeck() == null) {
                            GraphicalCommonUsages.okPopUp("selected deck is invalid", scene, root);
                        } else {
                            Main.setBattleMenuFX(currentAccount);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "SHOP":
                    try {
                        Main.setShopMenuFX(currentAccount);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "COLLECTION":
                    try {
                        Main.setCollectionMenuFX(currentAccount);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "SAVE":
                    try{
                        LoginMenuProcess.save(currentAccount);
                        GraphicalCommonUsages.okPopUp("all changes saved",scene,root);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
            }
        });
    }

    private void setText(Font font, Text playText) {
        playText.setFont(font);
        playText.setFill(Color.WHITE);
    }

    private void mouseHoverAudioPlay() {
        javafx.scene.media.AudioClip audioClip = new javafx.scene.media.AudioClip(MainMenuFX.class.getResource("sources/mainMenu/music/sfx_ui_booster_huming_tail.m4a").toString());
        audioClip.setCycleCount(1);
        audioClip.play(1);
    }
}
