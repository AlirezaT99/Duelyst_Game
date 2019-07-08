package view;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.nashorn.internal.objects.Global;
import model.Account;
import model.Message.GlobalChatMessage;
import model.Message.ScoreBoardCommand.ScoreBoardCommand;
import model.Message.Utils;
import model.client.Client;
import presenter.LoginMenuProcess;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import static javafx.scene.text.TextAlignment.CENTER;

public class MainMenuFX {
    private Account currentAccount;
    private Pane chatRoom = new Pane();
    private boolean isInGlobalChat = false;
    private AnimationTimer globalChatTimer;

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

        Text scoreBoardText = new Text("SCORE BOARD");
        setText(font, scoreBoardText);
        HBox scoreBoard = new HBox();
        scoreBoard.getChildren().addAll(getArrowView(mainMenuScene, arrow), scoreBoardText);

        VBox textQueries = new VBox();
        textQueries.setSpacing(mainMenuScene.getHeight() / 100);
        textQueries.setPadding(new Insets(mainMenuScene.getHeight() / 3, mainMenuScene.getWidth() * 2 / 3, mainMenuScene.getWidth() / 5, mainMenuScene.getWidth() / 6));
        textQueries.getChildren().addAll(play, collection, shop, watch, scoreBoard, save);
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
        HBox bottom_right = new HBox();
        Image backToLogin = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_back_corner.png"));
        ImageView backToLoginView = new ImageView(backToLogin);
        backToLoginViewSetting(root, mainMenuScene, backToLoginView);
        VBox friends = new VBox();
        Text friendsText = new Text("FRIENDS");
        friendsText.setFont(fontSmall);
        Image friendsImage = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/friends.png"));
        ImageView friendsView = new ImageView(friendsImage);

        friendsSetting(friends, friendsView, friendsText, mainMenuScene, root);
        VBox addCard = new VBox();
        Text addCardText = new Text("ADD CARD");
        Image addCardImage = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/quests.png"));
        settingAddNewCard(fontSmall, mainMenuScene, addCard, addCardText, addCardImage);

        bottom_right.getChildren().addAll(addCard, friends);
        addCard.setAlignment(Pos.CENTER);
        bottom_right.relocate(mainMenuScene.getWidth() * 5 / 6, mainMenuScene.getHeight() * 3 / 4);
        bottom_right.setAlignment(Pos.BOTTOM_CENTER);
        root.getChildren().addAll(bottom_right, chatRoom);

        addCardHandle(addCard);
        root.getChildren().addAll(brandView);
        textGlowEffect(play, mainMenuScene, root);
        textGlowEffect(collection, mainMenuScene, root);
        textGlowEffect(shop, mainMenuScene, root);
        textGlowEffect(watch, mainMenuScene, root);
        textGlowEffect(scoreBoard, mainMenuScene, root);
        textGlowEffect(save, mainMenuScene, root);
        setChatRoom(mainMenuScene);
        return root;
    }

    private void setChatRoom(Scene scene) throws FileNotFoundException {
        BackgroundFill background_fill = new BackgroundFill(Color.grayRgb(150, 0.8),
                new CornerRadii(15), new javafx.geometry.Insets(0, 0, 0, 0));
        chatRoom.setBackground(new Background(background_fill));
        chatRoom.relocate(scene.getWidth(), 0);
        chatRoom.setPrefHeight(scene.getHeight());
        chatRoom.setPrefWidth(scene.getWidth() / 3);

        Pane chatRoomBox = new Pane();
        BackgroundFill background_fill_box = new BackgroundFill(Color.grayRgb(190, 0.95),
                new CornerRadii(15), new javafx.geometry.Insets(0, 0, 0, 0));
        chatRoomBox.setBackground(new Background(background_fill_box));
        chatRoom.getChildren().add(chatRoomBox);
        chatRoomBox.relocate(chatRoom.getPrefWidth() / 10, chatRoom.getPrefHeight() / 20);
        chatRoomBox.setPrefWidth(chatRoom.getPrefWidth() * 0.8);
        chatRoomBox.setPrefHeight(chatRoom.getPrefHeight() * 0.8);

        TextField chatField = new TextField();
        ImageView sendView = new ImageView(new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/send.png")));
        HBox bottomRow = new HBox(chatField, sendView);
        //chatField.setAlignment(Pos.CENTER);
        chatField.setStyle("-fx-background-color:rgba(100, 100, 200, 0.5);");
        chatField.setPrefWidth(chatRoomBox.getPrefWidth() * 3 / 4);
        chatField.setPrefHeight(chatRoom.getPrefHeight() * 0.05);
        bottomRow.setSpacing(chatRoomBox.getPrefWidth() / 10);
        sendView.setFitHeight(chatRoom.getPrefHeight() / 20);
        sendView.setPreserveRatio(true);
        ImageView close = new ImageView(new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_close@2x.png")));
        close.setFitHeight(scene.getHeight() / 20);
        close.setPreserveRatio(true);
        close.relocate(chatRoom.getPrefWidth() * 0.92, 0);
        close.setOnMouseEntered(event -> {
            close.setEffect(new Glow(0.6));
        });
        close.setOnMouseExited(event -> {
            close.setEffect(new Glow(0));
        });
        close.setOnMouseClicked(event -> {
            if (isInGlobalChat) {
                globalChatTimer.stop();
                isInGlobalChat = false;
                KeyValue keyValue = new KeyValue(chatRoom.layoutXProperty(), scene.getWidth());
                KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue);
                Timeline timeline = new Timeline(keyFrame);
                timeline.play();
            }
        });
        sendView.setOnMouseEntered(event -> {
            sendView.setEffect(new Glow(0.5));
        });
        sendView.setOnMouseExited(event -> {
            sendView.setEffect(new Glow(0));
        });
        sendView.setOnMouseClicked(event -> {
            if (!chatField.getText().equals("")) {
                GlobalChatMessage globalChatMessage = new GlobalChatMessage(chatField.getText(), Client.getInstance().getAuthCode());
                Client.getInstance().sendData(globalChatMessage);
            }
        });

        final LongProperty lastUpdate = new SimpleLongProperty();

        final long minUpdateInterval = 10000;

        globalChatTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate.get() > minUpdateInterval) {
                    updateChat(chatRoomBox);
                    lastUpdate.set(now);
                }
            }
        };

        sendView.relocate(0, -1 * (chatField.getPrefHeight() / 3));
        chatRoom.getChildren().addAll(bottomRow, close);
        bottomRow.relocate(chatRoomBox.getLayoutX(), chatRoom.getPrefHeight() * 0.9);
    }

    private void updateChat(Pane chatRoomBox) {
        GlobalChatMessage globalChatMessage = new GlobalChatMessage(true,Client.getInstance().getAuthCode());
        Client.getInstance().sendData(globalChatMessage);
        synchronized (Client.getInstance().getLock()) {
            if (Client.getInstance().getGlobalChatMessage().getAuthCode().equals("")) {
                try {
                    Client.getInstance().getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            GlobalChatMessage answer = Client.getInstance().getGlobalChatMessage();
            Client.getInstance().setGlobalChatMessage(new GlobalChatMessage("", ""));
            ArrayList<String> messages = answer.getChatMessages();
            chatRoomBox.getChildren().clear();
            VBox messageVBox = new VBox();
            chatRoomBox.getChildren().addAll(messageVBox);
            messageVBox.relocate(0,chatRoomBox.getPrefHeight()/10);
            for (String message : messages) {
                Text messagetext = new Text("   "+message);
                messagetext.setFill(Color.WHITE);
                Label label = new Label();

                messagetext.setFont(Font.font(15));
                messageVBox.getChildren().addAll(messagetext);
            }
            messageVBox.setSpacing(chatRoomBox.getPrefHeight()/20);
        }
    }

    private void addCardHandle(VBox addCard) {
        addCard.setOnMouseClicked(event -> {
            try {
                Main.setAddCardFX(currentAccount);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
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

    private void friendsSetting(VBox friends, ImageView friendsView, Text friendsText, Scene scene, Pane root) {
        HBox firstLine = new HBox(friendsView);
        HBox secondLine = new HBox(friendsText);
        secondLine.setAlignment(Pos.CENTER);
        friends.getChildren().addAll(firstLine, secondLine);
        friendsView.setFitWidth(scene.getWidth() / 10);
        friendsView.setPreserveRatio(true);
        friendsView.setEffect(new Glow(0));
        friendsView.setOpacity(0.9);
        friendsText.setFill(Color.WHITE);
        friendsText.setTextAlignment(CENTER);
        friendsView.setOnMouseEntered(event -> {
            friendsText.setEffect(new Glow(1));
            friendsView.setEffect(new Glow(0.4));
        });
        friendsView.setOnMouseExited(event -> {
            friendsText.setEffect(new Glow(0));
            friendsView.setEffect(new Glow(0));
        });
        friendsView.setOnMouseClicked(event -> {
            isInGlobalChat = true;
            KeyValue keyValue = new KeyValue(chatRoom.layoutXProperty(), scene.getWidth() * 2 / 3);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue);
            Timeline timeline = new Timeline(keyFrame);
            timeline.play();
            globalChatTimer.start();
        });

    }

    private void backToLoginViewSetting(Pane root, Scene mainMenuScene, ImageView backToLoginView) {
        backButtonAdjustment(root, mainMenuScene, backToLoginView);
        backToLoginView.setOnMouseClicked(event -> {
            try {
                if (!isInGlobalChat) {
                    Utils util = new Utils(Client.getInstance().getAuthCode(), true);
                    Client.getInstance().sendData(util);
                    Main.setLoginMenu();
                }
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
            if (!isInGlobalChat) {
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
                    case "SCORE BOARD":
                        try {
                            Main.setScoreBoardFX(currentAccount);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    case "SAVE":
                        try {
                            LoginMenuProcess.save(currentAccount);
                            GraphicalCommonUsages.okPopUp("all changes saved", scene, root);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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
