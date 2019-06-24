package view;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
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
import model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;

import static view.GraphicalCommonUsages.okPopUp;
import static view.GraphicalCommonUsages.yesCancelPopUp;
import static view.ShopMenuFX.*;

public class CollectionMenuFX {
    private static Account account;
    private static Pane root = new Pane();
    private static GridPane gridPane = new GridPane();
    private static HashMap<ImageView, String> deleteDeckHMap = new HashMap<>();
    private static HashMap<ImageView, String> selectDeckHMap = new HashMap<>();
    private static HashMap<String, ImageView> decksBackground = new HashMap<>();
    private static String visibleDeckName = "";
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

        gridPane.setVisible(false);
        drawCards(gridPane, scene, root, trump_reg, trump_reg_small, false);
        drawArrows();

        drawBackButton(root, scene, account);
        drawCollectionLabels(root, averta, scene);

        return root;
    }

    private void drawArrows() throws FileNotFoundException {
        ImageView leftArrow = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/arrow-invari.png")));
        ImageView rightArrow = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/arrow-unvari.png")));
        leftArrow.setScaleX(1.5);
        leftArrow.setScaleY(1.5);
        rightArrow.setScaleX(1.5);
        rightArrow.setScaleY(1.5);
        leftArrow.relocate((scene.getWidth() / 20), (scene.getHeight() * 0.8));
        rightArrow.relocate((scene.getWidth() / 20 + 50), (scene.getHeight() * 0.8));
        root.getChildren().addAll(leftArrow, rightArrow);
        addEventHandlerOnArrows(leftArrow, rightArrow);
    }

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

        decksVBox.setAlignment(Pos.TOP_CENTER);

        decksVBox.getChildren().addAll(new Text(""), label, titleUnderline, new Text("\n"));
        drawDecks(decksVBox, scene);

        Image gauntletControlBg = new Image(new FileInputStream("src/view/sources/collectionMenu/gauntlet_control_bar_bg.png"));
        VBox deckBarButtons = new VBox();
        deckBarButtons.setAlignment(Pos.BOTTOM_CENTER);
        deckBarButtons.setPadding(new Insets(0));
        deckBarButtons.setMaxHeight(scene.getHeight() / 4);

        StackPane createDeck = new StackPane();
        ImageView createDeckView = new ImageView(gauntletControlBg);
        Label createDeckLabel = new Label("CREATE DECK");
        createDeckLabel.setFont(averta);
        createDeckLabel.setTextFill(Color.WHITE);
        createDeck.getChildren().addAll(createDeckView, createDeckLabel);
        createDeck.setMaxHeight(82);
        createDeck.setOnMouseEntered(event -> createDeckView.setEffect(new Glow(0.5)));
        createDeck.setOnMouseExited(event -> createDeckView.setEffect(new Glow(0)));

        StackPane importDeck = new StackPane();
        ImageView importDeckView = new ImageView(gauntletControlBg);
        Label importDeckLabel = new Label("IMPORT DECK");
        importDeckLabel.setFont(averta);
        importDeckLabel.setTextFill(Color.WHITE);
        importDeck.getChildren().addAll(importDeckView, importDeckLabel);
        importDeck.setMaxHeight(82);
        importDeck.setOnMouseEntered(event -> importDeckView.setEffect(new Glow(0.5)));
        importDeck.setOnMouseExited(event -> importDeckView.setEffect(new Glow(0))); // SAVIOR

        StackPane exportDeck = new StackPane();
        ImageView exportDeckView = new ImageView(gauntletControlBg);
        Label exportDeckLabel = new Label("EXPORT DECK");
        exportDeckLabel.setFont(averta);
        exportDeckLabel.setTextFill(Color.WHITE);
        exportDeck.getChildren().addAll(exportDeckView, exportDeckLabel);
        exportDeck.setMaxHeight(82);
        exportDeck.setOnMouseEntered(event -> exportDeckView.setEffect(new Glow(0.5)));
        exportDeck.setOnMouseExited(event -> exportDeckView.setEffect(new Glow(0)));
        exportDeck.setOnMouseClicked(event -> {
            try {
                exportDeckProcess();
            } catch (FileNotFoundException e) {
            }
        });

        deckBarButtons.getChildren().addAll(createDeck, importDeck, exportDeck);
        manageDecksBar.getChildren().addAll(background, decksVBox, deckBarButtons);

        StackPane.setAlignment(decksVBox, Pos.TOP_CENTER);
        StackPane.setAlignment(deckBarButtons, Pos.BOTTOM_CENTER);
    }

    private void exportDeckProcess() throws FileNotFoundException {
        if (visibleDeckName.equals(""))
            okPopUp("no deck is selected", scene, root);
        else {
//            new ImportBasedDeck(account.getCollection().getDeckHashMap().get(visibleDeckName));
            okPopUp("deck \"" + visibleDeckName + "\" successfully exported.", scene, root);
            decksVBox.getChildren().remove(4, decksVBox.getChildren().size());
            drawDecks(decksVBox, scene);
        }
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
        root.getChildren().addAll(manageDeckPane);
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
            gridPane.setVisible(true);
            try {
                setDeckToShow();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            pageNumber = 1;
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

    private static void setDeckToShow() throws FileNotFoundException {
        Deck deck = account.getCollection().getDeckHashMap().get(visibleDeckName);
        cardsToShow.clear();
        cardsToShow.add(deck.getHero().getName());
        for (Minion minion : deck.getMinions())
            cardsToShow.add(minion.getName());
        for (Spell spell : deck.getSpells())
            cardsToShow.add(spell.getName());
        for (Item item : deck.getItems())
            cardsToShow.add(item.getName());
        updateLabels();
        updatePowers(account);
        for (int i = 0; i < 10; i++) {
            int number = 1;
            String label = ((Label) ((StackPane) gridPane.getChildren().get(i)).getChildren().get(1)).getText();
            if (account.getCollection().findItemByName(label) != null
                    || account.getCollection().findCardByName(label) instanceof Spell) number = 2;
            ((ImageView) ((StackPane) gridPane.getChildren().get(i)).getChildren().get(0)).setImage(getCardTheme(number));
        }
    }

    static void deleteDeckProcess() throws FileNotFoundException {
        account.getCollection().deleteDeck(aboutToDelete);
        if (visibleDeckName.equals(aboutToDelete)) gridPane.setVisible(false);
        aboutToDelete = "";
        decksVBox.getChildren().remove(4, decksVBox.getChildren().size());
        drawDecks(decksVBox, scene);
        decksBackground.get(visibleDeckName).setImage(new Image(new FileInputStream("src/view/sources/collectionMenu/button_primary_middle_glow.png")));
    }

    private void addEventHandlerOnArrows(ImageView leftArrow, ImageView rightArrow) {
        leftArrow.setOnMouseEntered(event -> leftArrow.setEffect(new Glow(0.4)));
        leftArrow.setOnMouseExited(event -> leftArrow.setEffect(new Glow(0)));

        rightArrow.setOnMouseEntered(event -> rightArrow.setEffect(new Glow(0.4)));
        rightArrow.setOnMouseExited(event -> rightArrow.setEffect(new Glow(0)));

        leftArrow.setOnMouseClicked(event -> {
            pageNumber--;
            if (pageNumber == 0)
                pageNumber++;
            else {
                updateLabels();
                updatePowers(account);
            }
            pageSetText();
        });

        rightArrow.setOnMouseClicked(event -> {
            pageNumber++;
            if ((pageNumber - 1) * 10 >= cardsToShow.size()) pageNumber--;
            else {
                updateLabels();
                try {
                    updatePowers(account);
                } catch (ClassCastException ex) {
//                    System.out.println("ClassCastException at shopMenuFx->addEventHandlerOnArrows->setOnMouseClicked ...");
                }
            }
            pageSetText();
        });
    }
}