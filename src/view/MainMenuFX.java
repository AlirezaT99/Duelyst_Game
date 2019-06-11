package view;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class MainMenuFX  {

    public Scene start(Stage primaryStage) throws FileNotFoundException {
        final javafx.scene.text.Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-light-webfont.ttf")), 30);
        final javafx.scene.text.Font fontSmall = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-light-webfont.ttf")), 15);
        Pane root = new Pane();
        Random random = new Random();
        int backGroundNumber = random.nextInt(2) + 1;
        Scene mainMenuScene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/mainMenu/backgrounds/" + backGroundNumber + ".jpg", root);
        Image cursor = new Image(new FileInputStream("src/view/sources/common/cursors/auto.png"));
        mainMenuScene.setCursor(new ImageCursor(cursor));
        Image arrow = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/dialogue.png"));
        Text playText = new Text("PLAY");
        setText(font, playText);
        HBox play = new HBox();
        play.getChildren().addAll(getArrowView(mainMenuScene, arrow),playText);
        Text collectionText = new Text("COLLECTION");
        setText(font, collectionText);
        HBox collection = new HBox();
        collection.getChildren().addAll(getArrowView(mainMenuScene, arrow),collectionText);
        Text shopText = new Text("SHOP");
        setText(font, shopText);
        HBox shop = new HBox();
        shop.getChildren().addAll(getArrowView(mainMenuScene, arrow),shopText);
        Text watchText = new Text("WATCH");
        setText(font, watchText);
        HBox watch = new HBox();
        watch.getChildren().addAll(getArrowView(mainMenuScene, arrow),watchText);
        Text saveText = new Text("SAVE");
        setText(font, saveText);
        HBox save = new HBox();
        save.getChildren().addAll(getArrowView(mainMenuScene, arrow),saveText);
        VBox textQueries = new VBox();
        textQueries.setSpacing(mainMenuScene.getHeight() / 100);
        textQueries.setPadding(new Insets(mainMenuScene.getHeight() / 3, mainMenuScene.getWidth()*2/3, mainMenuScene.getWidth() / 5, mainMenuScene.getWidth() / 6));
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
        textGlowEffect(play);
        textGlowEffect(collection);
        textGlowEffect(shop);
        textGlowEffect(watch);
        textGlowEffect(save);
        return mainMenuScene;
    }

    private void settingAddNewCard(Font fontSmall, Scene mainMenuScene, VBox addCard, Text addCardText, Image addCardImage) {
        ImageView addCardView = new ImageView(addCardImage);
        addCardText.setFont(fontSmall);
        addCardText.setFill(Color.WHITE);
        HBox firstLine = new HBox();
        firstLine.getChildren().addAll(addCardView);
        HBox secondLine = new HBox();
        secondLine.getChildren().addAll(addCardText);
        addCardView.setFitWidth(mainMenuScene.getWidth()/20);
        addCard.setPrefWidth(addCardView.getFitWidth());
        addCardView.setPreserveRatio(true);
        addCard.relocate(mainMenuScene.getWidth()*9/10,mainMenuScene.getHeight()*5/6);
        addCard.getChildren().addAll(firstLine,secondLine);
        firstLine.setAlignment(Pos.CENTER);
        secondLine.setAlignment(Pos.CENTER);
        addCardView.setOpacity(0.7);
        addCard.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addCardView.setOpacity(0.9);
                addCardView.setEffect(new Glow(0.5));
                addCardText.setEffect(new Glow(0.5));
            }
        });
        addCard.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addCardView.setOpacity(0.7);
                addCardView.setEffect(new Glow(0));
                addCardText.setEffect(new Glow(0));
            }
        });
    }

    private void friendsSetting(VBox friends, ImageView friendsView, Text friendsText, Scene scene) {
        friendsView.setFitWidth(scene.getWidth()/10);
        friendsView.setPreserveRatio(true);
        friendsView.setEffect(new Glow(0));
        friendsView.setOpacity(0.9);
        friendsView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                friendsText.setEffect(new Glow(1));
                friendsView.setEffect(new Glow(0.4));
            }
        });
        friendsView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                friendsText.setEffect(new Glow(0));
                friendsView.setEffect(new Glow(0));
            }
        });


    }

    private void backToLoginViewSetting(Pane root, Scene mainMenuScene, ImageView backToLoginView)  {
        backToLoginView.setFitWidth(mainMenuScene.getWidth()/15);
        backToLoginView.setPreserveRatio(true);
        root.getChildren().addAll(backToLoginView);
        backToLoginView.setOpacity(0.5);
        backToLoginView.setOnMouseEntered(event -> backToLoginView.setOpacity(0.9));
        backToLoginView.setOnMouseExited(event -> backToLoginView.setOpacity(0.5));
        backToLoginView.setOnMouseClicked(event -> {
            try {
                Main.setLoginMenu();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private ImageView getArrowView(Scene mainMenuScene, Image arrow) {
        ImageView shopArrowView = new ImageView(arrow);
        shopArrowView.setFitWidth(mainMenuScene.getWidth()/50);
        shopArrowView.setPreserveRatio(true);
        shopArrowView.setVisible(false);
        return shopArrowView;
    }

    private void textGlowEffect(HBox playText) {
        playText.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playText.getChildren().get(0).setVisible(true);
                playText.setEffect(new Glow(1));
            }
        });
        playText.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playText.getChildren().get(0).setVisible(false);
                playText.setEffect(new Glow(0));
            }
        });
        playText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playText.setEffect(new Glow(2));
            }
        });
    }

    private void setText(Font font, Text playText) {
        playText.setFont(font);
        playText.setFill(Color.WHITE);
    }

}
