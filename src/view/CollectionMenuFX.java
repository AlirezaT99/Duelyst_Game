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
import model.Deck;
import presenter.ShopMenuProcess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static view.GraphicalCommonUsages.yesCancelPopUp;
import static view.ShopMenuFX.drawBackButton;

public class CollectionMenuFX {
    private static Account account;
    private static Pane root = new Pane();
    private static HashMap<ImageView, String> deleteDeckHMap = new HashMap<>();
    private static HashMap<ImageView, String> selectDeckHMap = new HashMap<>();
    private static HashMap<String, ImageView> decksBackground = new HashMap<>();
    private static String visibleDeckName = null;
    private static Scene scene;
    private static VBox decksVBox;
    private static String aboutToDelete = "";

    CollectionMenuFX(Account account) {
        CollectionMenuFX.account = account;
    }

    public Pane start(Stage primaryStage) throws FileNotFoundException {
        final Font trump_med = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Medium-webfont.ttf"), 36);
        final Font trump_reg = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Regular-webfont.ttf"), 36);
        final Font trump_reg_small = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Regular-webfont.ttf"), 28);
        final Font averta = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/averta-light-webfont.ttf"), 40);

        scene = new Scene(new Group(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(primaryStage.getWidth());
        root.setPrefHeight(primaryStage.getHeight());
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/mainMenu/backgrounds/" + (Math.abs(new Random().nextInt() % 2) + 1) + ".jpg", root, true);
//
//        drawCards(scene, root, trump_reg, trump_reg_small);

        drawBackButton(root, scene, account);
        drawCollectionLabels(root, averta, scene);
//        new ShopMenuFX(account).drawLeftBox(trump_med, root, scene);

        return root;
    }

//    private void drawCards(Scene scene, Pane root, Font trump, Font trump_small) throws FileNotFoundException {
//        GridPane gridPane = new GridPane();
//        gridPane.relocate(scene.getWidth() * 7.3 / 24,scene.getHeight() * 7.5 / 24);
//        gridPane.setHgap(5);
//        gridPane.setVgap(5);
//
//        for (int i = 0; i < 10; i++) {
//            StackPane stackPane = new StackPane();
//            ImageView imageView = new ImageView(getCardTheme(3));
//            Label cardName = new Label();
//            Label card_AP_HP = new Label();
//            cardName.setFont(trump);
//            cardName.setTextFill(Color.WHITE);
//            card_AP_HP.setFont(trump_small);
//            card_AP_HP.setTextFill(Color.WHITE);
//            cardLabels.put(i, cardName);
//            cardPowers.put(i, card_AP_HP);
//            cardImages.put(i, imageView);
//            cardPanes.put(i, stackPane);
//            StackPane.setAlignment(cardName, Pos.TOP_CENTER);
//            StackPane.setAlignment(card_AP_HP, Pos.CENTER);
//            stackPane.setMaxSize(157, 279);
//
//            int finalI = i;
//            stackPane.setOnMouseEntered(event -> stackPane.setEffect(new Glow(0.2)));
//            stackPane.setOnMouseClicked(event -> {
//                try {
//                    selectedIndex = finalI;
//                    String str = isInShop ? "BUY" : "SELL";
//                    if (str.equals("BUY")) {
//                        if (!ShopMenuProcess.isDrakeEnough(Integer.parseInt(money.getText()), cardLabels.get(finalI).getText().trim()))
//                        {
//                            //todo : drake(no) popUp
//                            GraphicalCommonUsages.drakePopUp("not enough drake",scene,root,2);
//                        }
//                        else{
//                            yesCancelPopUp("Are you sure to " + str.toLowerCase() + " " + cardLabels.get(finalI).getText() + " ?", scene, root, str);
//                        }
//                    } else
//                        yesCancelPopUp("Are you sure to " + str.toLowerCase() + " " + cardLabels.get(finalI).getText() + " ?", scene, root, str);
//                } catch (FileNotFoundException e) {
//                }
//            });
//            stackPane.setOnMouseExited(event -> stackPane.setEffect(new Glow(0)));
//
//            stackPane.getChildren().addAll(imageView, cardName, card_AP_HP, price);
//            gridPane.add(stackPane, i % 5, i / 5 > 0 ? 1 : 0);
//        }
//        root.getChildren().addAll(gridPane);
//    }

    private void drawDeckBar(Pane root, Scene scene, Font font, StackPane manageDecksBar) throws FileNotFoundException {
        final Font averta = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/averta-light-webfont.ttf"), 28);

        Rectangle background = new Rectangle(scene.getWidth() * 0.25, scene.getHeight());
        background.setFill(Color.grayRgb(20, 0.8));
        decksVBox = new VBox();
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
        drawDecks(decksVBox, scene);

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
        closeBar.setOnMouseExited(event -> closeBar.setOpacity(0.5));
        closeBar.setOnMouseClicked(event -> {
            timeline3.play();

            manageDeckPane.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.millis(500), manageDeckPane);
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

            timeline2.play();
        });

        manageDeckPane.setLayoutX(scene.getWidth() * 30 / 40);
        manageDeckPane.setLayoutY(scene.getHeight() * 4 / 40);
        manageDeckPane.getChildren().addAll(collectionButton, collectionLabel);
        root.getChildren().add(manageDeckPane);
    }

    private static void drawDecks(VBox decksVBox, Scene scene) throws FileNotFoundException {
//        decksVBox.getChildren().clear();
        for (Deck deck : account.getCollection().getDeckHashMap().values())
            decksVBox.getChildren().add(addDeckView(scene, deck.getName(), decksVBox));
    }

    private static HBox addDeckView(Scene scene, String deckName, VBox decksVBox) throws FileNotFoundException {
        Image deckBackground = new Image(new FileInputStream("src/view/sources/collectionMenu/button_primary_middle.png"));
        Image deckBackgroundGlow = new Image(new FileInputStream("src/view/sources/collectionMenu/button_primary_middle_glow.png"));
        Image deleteDeck = new Image(new FileInputStream("src/view/sources/collectionMenu/recycle-bin-small.png"));
        Image selectDeck = new Image(new FileInputStream("src/view/sources/collectionMenu/checkCircle.png"));
        Image selectDeckGray = new Image(new FileInputStream("src/view/sources/collectionMenu/checkCircle_gray.png"));
        final Font deckNameFont = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Medium-webfont.ttf"), 24);

        StackPane deck = new StackPane();
        ImageView deckBg = new ImageView(deckBackground);
        decksBackground.put(deckName, deckBg);
        Label name = new Label("\t" + deckName);
        StackPane.setAlignment(name, Pos.CENTER_LEFT);
        name.setFont(deckNameFont);
        name.setTextFill(Color.WHITE);
        deck.setOnMouseClicked(event -> {
            for (ImageView imageView : decksBackground.values())
                    imageView.setImage(deckBackground);
            deckBg.setImage(deckBackgroundGlow);
            visibleDeckName = deckName;
            // TODO show deck
        });
        deck.getChildren().addAll(deckBg, name);

        ImageView deleteDeckView = new ImageView(deleteDeck);
        deleteDeckView.setOnMouseEntered(event -> deleteDeckView.setEffect(new Glow(0.5)));
        deleteDeckView.setOnMouseExited(event -> deleteDeckView.setEffect(new Glow(0)));
        deleteDeckView.setOnMouseClicked(event -> {
            aboutToDelete = deleteDeckHMap.get(deleteDeckView);
            try {
                yesCancelPopUp("Are you sure to delete " + aboutToDelete + " ?", scene, root, "DELETE");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        ImageView selectDeckView = new ImageView(selectDeckGray);
        if (account.getCollection().getSelectedDeck().getName().equals(deckName))
            selectDeckView.setImage(selectDeck);
        selectDeckView.setOnMouseClicked(event -> {
            if (account.getCollection().validateDeck(account.getCollection().getDeckHashMap().get(deckName))) {
                selectDeckView.setImage(selectDeck);
                account.getCollection().setSelectedDeck(deckName);
                for (ImageView imageView : selectDeckHMap.keySet())
                    if (!selectDeckHMap.get(imageView).equals(deckName))
                        imageView.setImage(selectDeckGray);
            } else {
                try {
                    GraphicalCommonUsages.okPopUp("this deck is invalid", scene, root);
                } catch (FileNotFoundException e) {
                }
            }
        });
        deleteDeckHMap.put(deleteDeckView, deckName);
        selectDeckHMap.put(selectDeckView, deckName);
        HBox deckHBox = new HBox();
        deckHBox.setSpacing(10);
        deckHBox.getChildren().addAll(new Text("   "), deck, deleteDeckView, selectDeckView);
        deckHBox.setAlignment(Pos.CENTER_LEFT);

        return deckHBox;
    }

    static void deleteDeckProcess() throws FileNotFoundException {
        account.getCollection().deleteDeck(aboutToDelete);
        decksVBox.getChildren().remove(4, decksVBox.getChildren().size());
        drawDecks(decksVBox, scene);
        decksBackground.get(visibleDeckName).setImage(new Image(new FileInputStream("src/view/sources/collectionMenu/button_primary_middle_glow.png")));
    }
}