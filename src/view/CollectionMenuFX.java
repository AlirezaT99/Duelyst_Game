package view;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Account;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import static view.GraphicalCommonUsages.yesCancelPopUp;
import static view.ShopMenuFX.drawBackButton;

public class CollectionMenuFX {
    private static Account account;
    private static Pane root = new Pane();

    CollectionMenuFX(Account account) {
        CollectionMenuFX.account = account;
    }

    public Pane start(Stage primaryStage) throws FileNotFoundException {
        final Font trump_med = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Medium-webfont.ttf"), 36);
        final Font trump_reg = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Regular-webfont.ttf"), 36);
        final Font averta = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/averta-light-webfont.ttf"), 40);

        Scene scene = new Scene(new Group(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(primaryStage.getWidth());
        root.setPrefHeight(primaryStage.getHeight());
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/mainMenu/backgrounds/" + (Math.abs(new Random().nextInt() % 2) + 1) + ".jpg", root, true);
//
        drawBackButton(root, scene);
        drawCollectionLabels(root, averta, scene);
//        new ShopMenuFX(account).drawLeftBox(trump_med, root, scene);

        return root;
    }

    private void drawDeckBar(Pane root, Scene scene, Font font, StackPane manageDecksBar) throws FileNotFoundException {
        final Font averta = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/averta-light-webfont.ttf"), 28);

        Rectangle background = new Rectangle(scene.getWidth() * 0.25, scene.getHeight());
        background.setFill(Color.grayRgb(20, 0.8));
        VBox decksVBox = new VBox();
        Label label = new Label("Manage Decks");
        label.setFont(font);
        label.setTextFill(Color.WHITE);
        Rectangle titleUnderline = new Rectangle(250, 2);
        titleUnderline.setFill(Color.WHITE);


        StackPane createDeck = new StackPane();
        decksVBox.setAlignment(Pos.TOP_CENTER);
        StackPane.setAlignment(decksVBox, Pos.TOP_CENTER);
        StackPane.setAlignment(createDeck, Pos.BOTTOM_CENTER);

        decksVBox.getChildren().addAll(new Text(""), label, titleUnderline, new Text("\n"));
        addDecks(decksVBox, scene);

        ImageView createDeckView = new ImageView(new Image(new FileInputStream("src/view/sources/collectionMenu/gauntlet_control_bar_bg.png")));
        Label createDeckLabel = new Label("CREATE DECK");
        createDeckLabel.setFont(averta);
        createDeckLabel.setTextFill(Color.WHITE);
        createDeck.getChildren().addAll(createDeckView, createDeckLabel);
        createDeck.setMaxHeight(82);
        createDeck.setOnMouseEntered(event -> createDeckView.setEffect(new Glow(0.5)));
        createDeck.setOnMouseExited(event -> createDeckView.setEffect(new Glow(0))); // savior

        manageDecksBar.getChildren().addAll(background, decksVBox, createDeck);
    }

    private void drawCollectionLabels(Pane root, Font font, Scene scene) throws FileNotFoundException {
        Label collection = new Label("COLLECTION");
        collection.setFont(font);
        collection.setTextFill(Color.WHITE);
        collection.relocate(scene.getWidth() / 16, scene.getHeight() / 16);

        Rectangle underLine = new Rectangle(10, 10);
        underLine.setFill(Color.WHITE);
        underLine.relocate(scene.getWidth() / 16, scene.getHeight() / 16 + collection.getLayoutY() + 10);

        root.getChildren().addAll(collection, underLine);

        //Timeline
        KeyValue xValue = new KeyValue(underLine.widthProperty(), scene.getWidth() * 5.5 / 8);
        KeyValue yValue = new KeyValue(underLine.heightProperty(), 3);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), xValue, yValue);
        Timeline timeline = new Timeline(keyFrame);

        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();

        StackPane manageDeckPane = new StackPane();
        Label collectionLabel = new Label("MANAGE DECKS");
        collectionLabel.setTextFill(Color.WHITE);

        Image collectionBackground = new Image(new FileInputStream("src/view/sources/collectionMenu/backDeck.png"));
        Image collectionBackgroundGlow = new Image(new FileInputStream("src/view/sources/collectionMenu/backDeckGlow.png"));
        ImageView collectionButton = new ImageView(collectionBackground);

        StackPane manageDecksBar = new StackPane();
        manageDecksBar.setVisible(false);
        manageDecksBar.relocate(scene.getWidth(), 0);
        drawDeckBar(root, scene, font, manageDecksBar);

        KeyValue moveX = new KeyValue(manageDecksBar.layoutXProperty(), scene.getWidth() * 6 / 8);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(500), moveX);
        Timeline timeline2 = new Timeline();
        timeline2.getKeyFrames().add(keyFrame2);

        KeyValue moveBackX = new KeyValue(manageDecksBar.layoutXProperty(), scene.getWidth());
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(500), moveBackX);
        Timeline timeline3 = new Timeline();
        timeline3.getKeyFrames().add(keyFrame3);

        ImageView closeBar = new ImageView(new Image(new FileInputStream("src/view/sources/collectionMenu/button_back_corner.png")));
        closeBar.setFitWidth(scene.getWidth() / 15);
        closeBar.setFitHeight(scene.getWidth() / 15);
//        closeBar.relocate(500,500);
        closeBar.relocate(scene.getWidth() * 0.75 - closeBar.getFitWidth(), scene.getHeight() - closeBar.getFitHeight());
        closeBar.setVisible(false);
        closeBar.setOpacity(0.5);
        closeBar.setOnMouseEntered(event -> closeBar.setOpacity(0.9));
        closeBar.setOnMouseExited (event -> closeBar.setOpacity(0.5));
        closeBar.setOnMouseClicked(event -> {
            timeline3.play();

            manageDeckPane.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.millis(501), manageDeckPane);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
            ft.setOnFinished(nextEvent -> manageDecksBar.setVisible(false));

            FadeTransition ft2 = new FadeTransition(Duration.millis(501), closeBar);
            ft2.setFromValue(1);
            ft2.setToValue(0);
            ft2.play();
            ft2.setOnFinished(nextEvent -> closeBar.setVisible(false));
        });

        root.getChildren().addAll(manageDecksBar, closeBar);

        manageDeckPane.setOnMouseEntered(event -> collectionButton.setImage(collectionBackgroundGlow));
        manageDeckPane.setOnMouseExited(event -> collectionButton.setImage(collectionBackground));
        manageDeckPane.setOnMouseClicked(event -> {
            FadeTransition ft = new FadeTransition(Duration.millis(500), manageDeckPane);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            ft.setOnFinished(nextEvent -> manageDeckPane.setVisible(false));
            manageDecksBar.setVisible(true);

            closeBar.setVisible(true);
            FadeTransition ft2 = new FadeTransition(Duration.millis(500), closeBar);
            ft2.setFromValue(0);
            ft2.setToValue(0.5);
            ft2.play();

            timeline2.play(); // laggy as fuck.. (?)
        });

        manageDeckPane.setLayoutX(scene.getWidth() * 30 / 40);
        manageDeckPane.setLayoutY(scene.getHeight() * 4 / 40);
        manageDeckPane.getChildren().addAll(collectionButton, collectionLabel);
        root.getChildren().add(manageDeckPane);
    }

    private void addDecks(VBox decksVBox, Scene scene) throws FileNotFoundException {
        Image deckBackground = new Image(new FileInputStream("src/view/sources/collectionMenu/button_primary_middle.png"));
        Image deckBackgroundGlow = new Image(new FileInputStream("src/view/sources/collectionMenu/button_primary_middle_glow.png"));
        Image deleteDeck = new Image(new FileInputStream("src/view/sources/collectionMenu/recycle-bin-small.png"));
        final Font deckNameFont = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Regular-webfont.ttf"), 24);

        StackPane deck = new StackPane();
        ImageView deckBg = new ImageView(deckBackground);
        deck.setOnMouseClicked(event -> {
            deckBg.setImage(deckBackgroundGlow);
            // selected deck = this
            //unGlow the rest
            //show deck
        });
        Label deckName = new Label();
        deckName.setTextFill(Color.WHITE);
        deckName.setFont(deckNameFont);
        deck.getChildren().addAll(deckBg, deckName);
        StackPane.setAlignment(deckName, Pos.CENTER_LEFT);
        ImageView deleteDeckView = new ImageView(deleteDeck);
        deleteDeckView.setOnMouseEntered(event -> deleteDeckView.setEffect(new Glow(0.5)));
        deleteDeckView.setOnMouseExited(event -> deleteDeckView.setEffect(new Glow(0)));
        deleteDeckView.setOnMouseClicked(event -> {
            try {
                yesCancelPopUp("Are you sure to delete "/* deckName */ + " ?", scene, root, "DELETE");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        HBox deckHBox = new HBox();
        deckHBox.setSpacing(10);
        deckHBox.getChildren().addAll(new Text("   "), deck, deleteDeckView);
        deckHBox.setAlignment(Pos.CENTER_LEFT);
        decksVBox.getChildren().add(deckHBox);
    }

    public static void deleteDeckProcess() {
    }
}