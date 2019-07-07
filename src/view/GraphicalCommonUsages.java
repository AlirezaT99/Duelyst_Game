package view;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.*;

import java.awt.*;
import java.beans.EventHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GraphicalCommonUsages {
    public static void addOnMouseEnterAndExitHandler(Node area, ImageView changeableArea, Image firstImage, Image seoncImage) {
        area.setOnMouseEntered(event -> changeableArea.setImage(firstImage));
        area.setOnMouseExited(event -> changeableArea.setImage(seoncImage));
    }

    public static void addOnMouseEnterAndExitHandler(Node area, ImageView changeableArea, String firstImageAddress, String secondImageAddress) throws FileNotFoundException {
        Image image1 = new Image(new FileInputStream(firstImageAddress));
        Image image2 = new Image(new FileInputStream(secondImageAddress));
        addOnMouseEnterAndExitHandler(area, changeableArea, image1, image2);
    }

    public static void backGroundImageSetting(Image backGroundImage, Pane root, boolean blur) {
        ImageView backGround = new ImageView(backGroundImage);
        backGround.setFitWidth(root.getPrefWidth());
        backGround.setFitHeight(root.getPrefHeight());
        if (blur)
            backGround.setEffect(new GaussianBlur());
        root.getChildren().add(backGround);
    }

    public static void setBackGroundImage(String imageAddress, Pane root, boolean blur) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(imageAddress));
        backGroundImageSetting(image, root, blur);
    }

    public static void drakePopUp(String message, Scene scene, Pane root, int mode) throws FileNotFoundException {
        Rectangle bgRectangle = new Rectangle(scene.getWidth(), scene.getHeight());
        HBox popUp = new HBox();
        VBox popUpLeft = new VBox();
        Text messageText = new Text(message);
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 20);
        Image image = new Image(new FileInputStream("src/view/sources/shopMenu/popUpPictures/yes.jpg"));
        if (mode == 2)
            image = new Image(new FileInputStream("src/view/sources/shopMenu/popUpPictures/no.jpg"));
        Circle circle = new Circle();
        circle.setFill(new ImagePattern(image));
        circle.setRadius(scene.getWidth() / 15);

        bgRectangle.relocate(0.0, 0.0);
        root.getChildren().addAll(bgRectangle);
        bgRectangle.setOpacity(0.5);
        bgRectangle.setEffect(new GaussianBlur());

        popUp.setPrefHeight(scene.getHeight() / 4);
        popUp.setPrefWidth(scene.getWidth() / 3);
        BackgroundFill background_fill = new BackgroundFill(Color.grayRgb(20, 0.8),
                new CornerRadii(15), new javafx.geometry.Insets(0, 0, 0, 0));

        popUp.setBackground(new Background(background_fill));

        messageText.setTextAlignment(TextAlignment.CENTER);
        messageText.setFill(Color.WHITE);
        messageText.setFont(font);

        HBox firstLine = new HBox(messageText);
        firstLine.setAlignment(Pos.CENTER);
        messageText.setTextAlignment(TextAlignment.CENTER);
        popUpLeft.getChildren().addAll(new Text(""), firstLine);

        popUpLeft.setSpacing(popUpLeft.getPrefHeight() / 6);
        popUp.layoutXProperty().bind(root.widthProperty().subtract(popUp.widthProperty()).divide(2));
        popUp.layoutYProperty().bind(root.heightProperty().subtract(popUp.heightProperty()).divide(2));

        popUp.getChildren().addAll(new Text(""), circle, popUpLeft, new Text(""));
        popUp.setAlignment(Pos.CENTER);
        popUp.setSpacing(popUp.getPrefWidth() / 8);
        root.getChildren().addAll(popUp);
        soundEffectPlay("error");
        //

        Image okButton = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_confirm.png"));
        Image okButtonGlow = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_confirm_glow.png"));
        ImageView okButtonView = new ImageView(okButton);
        okButtonView.setFitWidth(popUp.getPrefWidth() / 4);
        okButtonView.setPreserveRatio(true);

        StackPane confirmStackPane = new StackPane();
        Text okText = new Text("OK");
        okText.setFont(Font.font(font.getName(), 14));
        okText.setFill(Color.WHITE);
        confirmStackPane.getChildren().addAll(okButtonView, okText);
        HBox secondLine = new HBox(confirmStackPane);
        secondLine.setAlignment(Pos.CENTER);
        popUpLeft.getChildren().addAll(secondLine, new Text(""));
        popUpLeft.setSpacing(popUp.getPrefHeight() / 4);

        confirmStackPane.setOnMouseEntered(event -> ((ImageView) confirmStackPane.getChildren().get(0)).setImage(okButtonGlow));
        confirmStackPane.setOnMouseExited(event -> ((ImageView) confirmStackPane.getChildren().get(0)).setImage(okButton));
        confirmStackPane.setOnMouseClicked(event -> root.getChildren().removeAll(bgRectangle, popUp));
    }

    public static void okPopUp(String message, Scene scene, Pane root) throws FileNotFoundException {
        Rectangle bgRectangle = new Rectangle(scene.getWidth(), scene.getHeight());
        VBox popUp = new VBox();
        Text messageText = new Text(message);
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 20);
        popUpInCommonConfigs(scene, root, bgRectangle, popUp, messageText, font);
        //

        Image okButton = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_confirm.png"));
        Image okButtonGlow = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_confirm_glow.png"));
        ImageView okButtonView = new ImageView(okButton);
        okButtonView.setFitWidth(popUp.getPrefWidth() / 4);
        okButtonView.setPreserveRatio(true);

        StackPane confirmStackPane = new StackPane();
        Text okText = new Text("OK");
        okText.setFont(Font.font(font.getName(), 14));
        okText.setFill(Color.WHITE);
        confirmStackPane.getChildren().addAll(okButtonView, okText);
        HBox secondLine = new HBox(confirmStackPane);
        secondLine.setAlignment(Pos.CENTER);
        popUp.getChildren().addAll(secondLine);

        confirmStackPane.setOnMouseEntered(event -> ((ImageView) confirmStackPane.getChildren().get(0)).setImage(okButtonGlow));
        confirmStackPane.setOnMouseExited(event -> ((ImageView) confirmStackPane.getChildren().get(0)).setImage(okButton));
        confirmStackPane.setOnMouseClicked(event -> root.getChildren().removeAll(bgRectangle, popUp));
    }

    public static void yesCancelPopUp(String message, Scene scene, Pane root, String command) throws FileNotFoundException {
        VBox popUp = new VBox();
        Text messageText = new Text(message);
        Rectangle bgRectangle = new Rectangle(scene.getWidth(), scene.getHeight());
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 20);
        popUpInCommonConfigs(scene, root, bgRectangle, popUp, messageText, font);


        Image confirmButton = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_confirm.png"));
        Image confirmButtonGlow = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_confirm_glow.png"));
        Image cancelButton = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_cancel.png"));
        Image cancelButtonGlow = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_cancel_glow.png"));

        ImageView confirmButtonView = new ImageView(confirmButton);
        confirmButtonView.setFitWidth(popUp.getPrefWidth() / 4);
        confirmButtonView.setPreserveRatio(true);
        //
        ImageView cancelButtonView = new ImageView(cancelButton);
        cancelButtonView.setFitWidth(popUp.getPrefWidth() / 4);
        cancelButtonView.setPreserveRatio(true);
        //
        StackPane confirmStackPane = new StackPane();
        Text confirmText = new Text(command);
        confirmText.setFont(Font.font(font.getName(), 14));
        confirmText.setFill(Color.WHITE);
        confirmStackPane.getChildren().addAll(confirmButtonView, confirmText);
        //
        StackPane cancelStackPane = new StackPane();
        Text cancelText = new Text("CANCEL");
        cancelText.setFont(Font.font(font.getName(), 14));
        cancelText.setFill(Color.WHITE);
        cancelStackPane.getChildren().addAll(cancelButtonView, cancelText);
        //
        HBox secondLine = new HBox();
        secondLine.getChildren().addAll(confirmStackPane, cancelStackPane);
        secondLine.setAlignment(Pos.CENTER);
        popUp.getChildren().addAll(secondLine);

        confirmStackPane.setOnMouseEntered(event -> ((ImageView) confirmStackPane.getChildren().get(0)).setImage(confirmButtonGlow));
        confirmStackPane.setOnMouseExited(event -> ((ImageView) confirmStackPane.getChildren().get(0)).setImage(confirmButton));
        confirmStackPane.setOnMouseClicked(event -> {
            root.getChildren().removeAll(bgRectangle, popUp);
            switch (confirmText.getText()) {
                case "BUY":
                    try {
                        ShopMenuFX.buyProcess();
                    } catch (FileNotFoundException e) {
                    }
                    break;
                case "SELL":
                    try {
                        ShopMenuFX.sellProcess();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "DELETE":
                    try {
                        CollectionMenuFX.deleteDeckProcess();
                    } catch (FileNotFoundException e) {
                    }
                    break;
                case "REMOVE":
                    try {
                        CollectionMenuFX.removeFromDeckProcess();
                    } catch (Exception e) {
                    }
                    break;
            }
        });

        cancelStackPane.setOnMouseEntered(event -> ((ImageView) cancelStackPane.getChildren().get(0)).setImage(cancelButtonGlow));
        cancelStackPane.setOnMouseExited(event -> ((ImageView) cancelStackPane.getChildren().get(0)).setImage(cancelButton));
        cancelStackPane.setOnMouseClicked(event -> root.getChildren().removeAll(bgRectangle, popUp));
    }

    public static Animation getGif(String cardName, String typeYouWant, int type , boolean isCostume) { //0.hero 1.minion 2.spell 3.item
        String address = "src/view/sources/gifs/";
        if (cardName.equals("Flag"))
            return new AnimatedGif(address + "items/" + cardName + "/" + typeYouWant + ".gif", 1000);
        if (type == 3)
            return new AnimatedGif(address + "items/" + cardName + "/" + typeYouWant + ".gif", 1000);
        Card card = Shop.findCardByName(cardName);
        if (type == 2 && !isCostume)
            return new AnimatedGif(address + "spells/" + cardName + "/" + typeYouWant + ".gif", 1000);
        if(type == 2)
            return new AnimatedGif(address + "spells/spell/" + typeYouWant + ".gif", 1000);
        if (type ==1  && !isCostume)
            return new AnimatedGif(address + "minions/" + cardName + "/" + typeYouWant + ".gif", 1000);
        if(type == 1)
            return new AnimatedGif(address + "minions/minion/" + typeYouWant + ".gif", 1000);
        if (type == 0 && !isCostume)
            return new AnimatedGif(address + "heroes/" + cardName + "/" + typeYouWant + ".gif", 1000);
        if(type == 0)
            return new AnimatedGif(address + "heroes/hero/" + typeYouWant + ".gif", 1000);
        return null;
    }

    static void popUpInCommonConfigs(Scene scene, Pane root, Rectangle bgRectangle, VBox popUp, Text messageText, Font font) {
        soundEffectPlay("error");
        bgRectangle.relocate(0.0, 0.0);
        root.getChildren().addAll(bgRectangle);
        bgRectangle.setOpacity(0.5);
        bgRectangle.setEffect(new GaussianBlur());

        popUp.setPrefHeight(scene.getHeight() / 4);
        popUp.setPrefWidth(scene.getWidth() / 3);
        BackgroundFill background_fill = new BackgroundFill(Color.grayRgb(20, 0.8),
                new CornerRadii(15), new javafx.geometry.Insets(0, 0, 0, 0));
        popUp.setBackground(new Background(background_fill));

        messageText.setTextAlignment(TextAlignment.CENTER);
        messageText.setFill(Color.WHITE);
        messageText.setFont(font);

        HBox firstLine = new HBox(messageText);
        firstLine.setAlignment(Pos.CENTER);
        messageText.setTextAlignment(TextAlignment.CENTER);
        popUp.getChildren().addAll(new Text(""), firstLine);

        popUp.setSpacing(popUp.getPrefHeight() / 6);
        popUp.layoutXProperty().bind(root.widthProperty().subtract(popUp.widthProperty()).divide(2));
        popUp.layoutYProperty().bind(root.heightProperty().subtract(popUp.heightProperty()).divide(2));
        root.getChildren().addAll(popUp);
    }

    public static void soundEffectPlay(String name) {
        AudioClip audioClip = new AudioClip(GraphicalCommonUsages.class.getResource("sources/common/music/" + name + ".m4a").toString());
        audioClip.setCycleCount(1);
        audioClip.play();
    }

    public static void mouseClickAudioPlay() {
        AudioClip audioClip = new AudioClip(GraphicalCommonUsages.class.getResource("sources/common/music/onclick.m4a").toString());
        audioClip.setCycleCount(1);
        audioClip.play(1);
        System.gc();
    }

    public static void backSetting(Pane root, Scene mainMenuScene, Account account, String whereTo) throws FileNotFoundException {
        Image back = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_back_corner.png"));
        ImageView backView = new ImageView(back);
        backView.setFitWidth(mainMenuScene.getWidth() / 15);
        backView.setPreserveRatio(true);
        root.getChildren().addAll(backView);
        backView.setOpacity(0.5);
        backView.setOnMouseEntered(event -> backView.setOpacity(0.9));
        backView.setOnMouseExited(event -> backView.setOpacity(0.5));
        backView.setOnMouseClicked(event -> {
            try {
                Main.backToLastRoots(account, whereTo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public static Rectangle2D initPrimaryStage(Stage primaryStage) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreenDevice = ge.getDefaultScreenDevice();
        GraphicsConfiguration defaultConfiguration = defaultScreenDevice.getDefaultConfiguration();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(defaultConfiguration);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth() + screenInsets.right + screenInsets.left);
        primaryStage.setHeight(primaryScreenBounds.getHeight() + screenInsets.bottom + screenInsets.top);
        primaryStage.setFullScreen(true);
        return primaryScreenBounds;
    }
}