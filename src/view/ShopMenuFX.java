package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
import presenter.ShopMenuProcess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import static view.GraphicalCommonUsages.soundEffectPlay;
import static view.GraphicalCommonUsages.yesCancelPopUp;
import static view.ShopMenu.handleErrors;
import static view.ShopMenu.scan;

public class ShopMenuFX {
    private Text page = new Text();
    private static boolean isInShop = true;
    private boolean isInCollection = false;
    private static ArrayList<String> cardsToShow = new ArrayList<>();
    private static ArrayList<Pane> panesOfGifs = new ArrayList<>();
    private static HashMap<Integer, Label> cardLabels = new HashMap<>();
    private static HashMap<Integer, Label> cardPowers = new HashMap<>();
    private static HashMap<Integer, Label> cardPrices = new HashMap<>();
    private static HashMap<Integer, StackPane> cardPanes = new HashMap<>();
    private static HashMap<Integer, ImageView> cardImages = new HashMap<>();
    private static int pageNumber = 1;
    private static int selectedIndex = 0;
    private static Account account;
    private static Label money;
    private static Pane root = new Pane();
    private static Stage stage;

    ShopMenuFX(Account account) {
        ShopMenuFX.account = account;
    }

    public Pane start(Stage primaryStage) throws FileNotFoundException {
        ShopMenuFX.stage = primaryStage;
        final Font trump_med = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Medium-webfont.ttf"), 36);
        final Font trump_reg = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Regular-webfont.ttf"), 36);
        final Font trump_reg_small = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Regular-webfont.ttf"), 28);
        final Font averta = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/averta-light-webfont.ttf"), 40);

        Scene scene = new Scene(new Group(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(primaryStage.getWidth());
        root.setPrefHeight(primaryStage.getHeight());
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/mainMenu/backgrounds/" + (Math.abs(new Random().nextInt() % 2) + 1) + ".jpg", root, true);

        drawCards(scene, root, trump_reg, trump_reg_small, true);
        drawShopLabels(root, averta, scene);
        drawLeftBox(trump_med, root, scene);
        drawBackButton(root, scene, account);
        drawDrake(root, scene, trump_reg);

        return root;
    }

    static void drawCards(Scene scene, Pane root, Font trump, Font trump_small, Boolean shopMenuOrCollection) throws FileNotFoundException {
        GridPane gridPane = new GridPane();
        if (shopMenuOrCollection)
            gridPane.relocate(scene.getWidth() * 7.3 / 24, scene.getHeight() * 7.5 / 24);
        else
            gridPane.relocate(scene.getWidth() / 6, scene.getHeight() * 7.5 / 24);
        drawGridPane(gridPane, scene, trump, trump_small, shopMenuOrCollection);
        root.getChildren().addAll(gridPane);
    }

    private static void drawGridPane(GridPane gridPane, Scene scene, Font trump, Font trump_small, Boolean showPrice) throws FileNotFoundException {
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        for (int i = 0; i < 10; i++) {
            StackPane stackPane = new StackPane();
            ImageView imageView = new ImageView(getCardTheme(3));
            Label cardName = new Label();
            Label card_AP_HP = new Label();
            cardName.setFont(trump);
            cardName.setTextFill(Color.WHITE);
            card_AP_HP.setFont(trump_small);
            card_AP_HP.setTextFill(Color.WHITE);

            cardLabels.put(i, cardName);
            cardPowers.put(i, card_AP_HP);
            cardImages.put(i, imageView);
            cardPanes.put(i, stackPane);
            StackPane.setAlignment(cardName, Pos.TOP_CENTER);
            StackPane.setAlignment(card_AP_HP, Pos.CENTER);
            stackPane.setMaxSize(157, 279);

            Image tinyDrake = new Image(new FileInputStream("src/view/sources/shopMenu/drake_veryVerySmall.png"));
            HBox price = new HBox();
            Label priceLabel = new Label();
            priceLabel.setFont(Font.font(trump_small.getName(), 16));
            priceLabel.setTextFill(Color.WHITE);
            cardPrices.put(i, priceLabel);
            price.setAlignment(Pos.BOTTOM_CENTER);
            price.getChildren().add(priceLabel);
            if (showPrice) price.getChildren().add(new ImageView(tinyDrake));

            int finalI = i;
            stackPane.setOnMouseEntered(event -> stackPane.setEffect(new Glow(0.2)));
            stackPane.setOnMouseClicked(event -> {
                try {
                    selectedIndex = finalI;
                    String str = isInShop ? "BUY" : "SELL";
                    if (str.equals("BUY")) {
                        if (!ShopMenuProcess.isDrakeEnough(Integer.parseInt(money.getText()), cardLabels.get(finalI).getText().trim())) {
                            //todo : drake(no) popUp
                            GraphicalCommonUsages.drakePopUp("not enough drake", scene, root, 2);
                        } else {
                            yesCancelPopUp("Are you sure to " + str.toLowerCase() + " " + cardLabels.get(finalI).getText() + " ?", scene, root, str);
                        }
                    } else {

                        yesCancelPopUp("Are you sure to " + str.toLowerCase() + " " + cardLabels.get(finalI).getText() + " ?", scene, root, str);
                    }
                } catch (FileNotFoundException e) {
                }
            });
            stackPane.setOnMouseExited(event -> stackPane.setEffect(new Glow(0)));

            stackPane.getChildren().addAll(imageView, cardName, card_AP_HP, price);
            gridPane.add(stackPane, i % 5, i / 5 > 0 ? 1 : 0);
//            stackPanes[i].relocate((i % 5 + 2.6) * (scene.getWidth() / 9) + (25 * (i % 5))
//                    , i / 5 > 0 ? (scene.getHeight() * 10 / 16) : (scene.getHeight() * 4.5 / 16));
        }
    }


    void drawLeftBox(Font font, Pane root, Scene scene) throws FileNotFoundException {
        ImageView heroesCircle = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/shopMenuCircle1.png")));
        ImageView minionsCircle = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/shopMenuCircle2.png")));
        ImageView itemsCircle = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/shopMenuCircle3.png")));
        ImageView searchCircle = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/shopMenuCircle4.png")));
        ImageView spellsCircle = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/shopMenuCircle5.png")));
        searchCircle.setFitWidth(90);
        searchCircle.setFitHeight(90);
        heroesCircle.setFitWidth(90);
        heroesCircle.setFitHeight(90);
        minionsCircle.setFitWidth(90);
        minionsCircle.setFitHeight(90);
        itemsCircle.setFitWidth(90);
        itemsCircle.setFitHeight(90);
        spellsCircle.setFitWidth(90);
        spellsCircle.setFitHeight(90);

        Label heroes = new Label("HEROES");
        Label minions = new Label("MINIONS");
        Label items = new Label("ITEMS");
        Label spells = new Label("SPELLS");
        Label search = new Label("SEARCH");
        Label[] labels = new Label[]{heroes, minions, items, spells, search};
        for (Label label : labels) {
            label.setFont(font);
            label.setTextFill(Color.WHITESMOKE);
        }

        HBox[] hBoxes = new HBox[5];
        for (int i = 0; i < 5; i++) {
            hBoxes[i] = new HBox();
            hBoxes[i].setSpacing(20);
            hBoxes[i].setStyle("-fx-background-color: rgba(0,0,0,0);");
            hBoxes[i].setBackground(new Background
                    (new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, new javafx.geometry.Insets(0))));
            hBoxes[i].setAlignment(Pos.CENTER_LEFT);
        }

        hBoxes[0].getChildren().addAll(heroesCircle, heroes);
        hBoxes[1].getChildren().addAll(minionsCircle, minions);
        hBoxes[2].getChildren().addAll(itemsCircle, items);
        hBoxes[3].getChildren().addAll(spellsCircle, spells);

        hBoxes[4].getChildren().addAll(searchCircle, search);
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Enter card/item ID");
        ImageView searchButton = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/searchButton.png")));
        searchButton.setOnMouseClicked(event -> {
            String command = searchTextField.getText();
            if (isInShop) {
                try {
                    handleErrors(ShopMenuProcess.search(command));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (isInCollection) {
                try {
                    handleErrors(ShopMenuProcess.searchCollection(command, account));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        searchButton.setFitHeight(40);
        searchButton.setFitWidth(40);
        searchTextField.relocate(scene.getWidth() / 25, scene.getHeight() * 0.8);
        searchButton.relocate(scene.getWidth() / 25 + 210, scene.getHeight() * 0.8);
        showSearchBar(searchTextField, searchButton, false);

        searchTextField.setOpacity(1);
        searchTextField.setStyle("-fx-background-color:rgba(184,245,243,0.8) ; -fx-font-size:  16px; ");
        searchButton.setOnMouseEntered(event -> searchButton.setEffect(new Glow(0.3)));
        searchButton.setOnMouseExited(event -> searchButton.setEffect(new Glow(0)));
        root.getChildren().addAll(searchButton, searchTextField);

        ListView<HBox> listView = new ListView<>();

        listView.relocate(scene.getWidth() / 30, scene.getHeight() / 5);
        listView.getItems().addAll(hBoxes);
//        listView.setMaxHeight(scene.getHeight() / 2);
        listView.setPrefHeight(450);
        listView.setStyle("-fx-control-inner-background: rgba(0,46,72,0.3);-fx-border-color: rgba(0,46,72,0.3);" +
                "-fx-control-outer-background: rgba(0,46,72,0.3);-fx-background-color: transparent;"); // -fx-border-radius: 10;-fx-background-radius: 10;
        for (HBox item : listView.getItems())
            item.setStyle("-fx-background-color: transparent;");

        listView.setBackground(new Background
                (new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, new javafx.geometry.Insets(0))));
        listView.setOnMouseClicked(event -> {
            switch (listView.getSelectionModel().getSelectedIndices().toString()) {
                case "[0]":
                    moveToHeroTab();
                    showSearchBar(searchTextField, searchButton, false);
                    break;
                case "[1]":
                    moveToMinionTab();
                    showSearchBar(searchTextField, searchButton, false);
                    break;
                case "[2]":
                    moveToItemTab();
                    showSearchBar(searchTextField, searchButton, false);
                    break;
                case "[3]":
                    moveToSpellTab();
                    showSearchBar(searchTextField, searchButton, false);
                    break;
                case "[4]":
                    showSearchBar(searchTextField, searchButton, true);
                    break;
            }
        });
        // arrows
        ImageView leftArrow = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/arrow-invari.png")));
        ImageView rightArrow = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/arrow-unvari.png")));
        leftArrow.setScaleX(1.5);
        leftArrow.setScaleY(1.5);
        rightArrow.setScaleX(1.5);
        rightArrow.setScaleY(1.5);
        leftArrow.relocate((scene.getWidth() * 2.2 / 9), (scene.getHeight() * 0.55));
        rightArrow.relocate((scene.getWidth() * 8.5 / 9), (scene.getHeight() * 0.55));
        root.getChildren().addAll(listView, leftArrow, rightArrow);
        addEventHandlerOnArrows(leftArrow, rightArrow);
    }

    private void doSomeThingsAfterFindingThingsInShop(String cardName) {
        ArrayList<String> result = new ArrayList<>();
        moveToHeroTab();
        try {
            goGetTheCardsFromWantedGroup(cardName, result);
        } catch (Exception ignored) {
        }
        try {
            moveToMinionTab();
            goGetTheCardsFromWantedGroup(cardName, result);
        } catch (Exception ignored) {
        }
        try {
            moveToItemTab();
            goGetTheCardsFromWantedGroup(cardName, result);
        } catch (Exception ignored) {
        }
        cardsToShow.clear();
        cardsToShow.addAll(result);
        updateLabels();
    }

    private void goGetTheCardsFromWantedGroup(String cardName, ArrayList<String> result) {
        for (String card : cardsToShow) {
            if (card.equalsIgnoreCase(cardName))
                result.add(card);
        }

    }

    private void moveToSpellTab() {
        cardsToShow.clear();
        if (isInShop)
            for (Spell spell : Shop.getShopSpells())
                cardsToShow.add(spell.getName());
        else if (isInCollection)
            for (Spell spell : account.getCollection().getSpells())
                cardsToShow.add(spell.getName());
        pageNumber = 1;
        updateLabels();
        removePowers();
        updatePrices();
        try {
            setImages(2);
        } catch (FileNotFoundException e) {
        }
    }

    private void moveToItemTab() {
        cardsToShow.clear();
        if (isInShop)
            for (Item item : Shop.getShopItems())
                cardsToShow.add(item.getName());
        else if (isInCollection)
            for (Item item : account.getCollection().getItems())
                cardsToShow.add(item.getName());
        pageNumber = 1;
        updateLabels();
        removePowers();
        updatePrices();
        try {
            setImages(2);
        } catch (FileNotFoundException e) {
        }
    }

    private void moveToMinionTab() {
        cardsToShow.clear();
        if (isInShop)
            for (Minion minion : Shop.getShopMinions())
                cardsToShow.add(minion.getName());
        else if (isInCollection)
            for (Minion minion : account.getCollection().getMinions())
                cardsToShow.add(minion.getName());
        pageNumber = 1;
        updateLabels();
        updatePrices();
        updatePowers(account);
        try {
            setImages(1);
        } catch (FileNotFoundException e) {
        }
    }

    private void moveToHeroTab() {
        cardsToShow.clear();
        if (isInShop)
            for (Hero hero : Shop.getShopHeroes())
                cardsToShow.add(hero.getName());
        else if (isInCollection)
            for (Hero hero : account.getCollection().getHeroes())
                cardsToShow.add(hero.getName());
        pageNumber = 1;
        updateLabels();
        updatePowers(account);
        updatePrices();
        try {
            setImages(1);
        } catch (FileNotFoundException e) {
        }
    }

    private void drawShopLabels(Pane root, Font font, Scene scene) throws FileNotFoundException {
        Label shop = new Label("SHOP");
        shop.setFont(font);
        shop.setTextFill(Color.WHITE);
        shop.relocate(scene.getWidth() / 16, scene.getHeight() / 16);

        Rectangle underLine = new Rectangle(10, 10);
        underLine.setFill(Color.WHITE);
        underLine.relocate(scene.getWidth() / 16, scene.getHeight() / 16 + shop.getLayoutY() + 10);

        Rectangle underLine2 = new Rectangle(300, 10);
        underLine2.setFill(Color.WHITE);
        underLine2.relocate(scene.getWidth(), scene.getHeight() / 16 + shop.getLayoutY() + 20);

        root.getChildren().addAll(shop, underLine, underLine2);

        //Timeline
        KeyValue xValue = new KeyValue(underLine.widthProperty(), scene.getWidth() * 4 / 8);
        KeyValue yValue = new KeyValue(underLine.heightProperty(), 3);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), xValue, yValue);

        KeyValue xValue2 = new KeyValue(underLine2.layoutXProperty(), scene.getWidth() * 6.2 / 8);
        KeyValue yValue2 = new KeyValue(underLine2.heightProperty(), 3);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1001), xValue2, yValue2); // to avoid duplicate annoying lines -_-
        Timeline timeline = new Timeline(keyFrame, keyFrame2);

        timeline.getKeyFrames().addAll(keyFrame, keyFrame2);
        timeline.play();
        //
        StackPane shopPane = new StackPane();
        StackPane collectionPane = new StackPane();
        Label shopLabel = new Label("SHOP");
        Label collectionLabel = new Label("COLLECTION");
        shopLabel.setTextFill(Color.WHITE);
        collectionLabel.setTextFill(Color.WHITE);

        Image shopBackground = new Image(new FileInputStream("src/view/sources/shopMenu/backShop.png"));
        Image collectionBackground = new Image(new FileInputStream("src/view/sources/shopMenu/backCollection.png"));
        Image shopBackgroundGlow = new Image(new FileInputStream("src/view/sources/shopMenu/backShopGlow.png"));
        Image collectionBackgroundGlow = new Image(new FileInputStream("src/view/sources/shopMenu/backCollectionGlow.png"));
        ImageView shopButton = new ImageView(shopBackgroundGlow);
        ImageView collectionButton = new ImageView(collectionBackground);
        shopPane.setLayoutX(scene.getWidth() * 23 / 40);
        shopPane.setLayoutY(scene.getHeight() / 16 + shop.getLayoutY() * 0.7);
        collectionPane.setLayoutX(scene.getWidth() * 25.5 / 40);
        collectionPane.setLayoutY(scene.getHeight() / 16 + shop.getLayoutY() * 0.7);
        shopPane.getChildren().addAll(shopButton, shopLabel);
        collectionPane.getChildren().addAll(collectionButton, collectionLabel);

//      <!-- PageDefinedHere -->
        page.relocate(50, scene.getHeight() * 0.95);
        page.setFont(font);
        page.setFill(Color.WHITE);

        manageShopAndCollectionBars(shopPane, collectionPane, shopButton, collectionButton, shopBackground, collectionBackground, shopBackgroundGlow, collectionBackgroundGlow);
        root.getChildren().addAll(page, shopPane, collectionPane);
    }

    private static Image getCardTheme(int number) throws FileNotFoundException {
        Image cardTheme1 = new Image(new FileInputStream("src/view/sources/shopMenu/cardTheme1.png"));
        Image cardTheme2 = new Image(new FileInputStream("src/view/sources/shopMenu/cardTheme2.png"));
        Image cardTheme3 = new Image(new FileInputStream("src/view/sources/shopMenu/cardTheme3.png"));
        if (number == 1) return cardTheme1;
        if (number == 2) return cardTheme2;
        else return cardTheme3;
    }

    private void setImages(int number) throws FileNotFoundException {
        for (int i = 0; i < 10; i++)
            cardImages.get(i).setImage(getCardTheme(number));
    }

    private void drawDrake(Pane root, Scene scene, Font font) throws FileNotFoundException {
        ImageView imageView = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/drake_verySmall.png"))); // for now
        imageView.relocate(scene.getWidth() * 0.92, scene.getHeight() / 64);
        money = new Label(account.getMoney() + "");
        money.relocate(scene.getWidth() * 0.88, scene.getHeight() / 64 + 20);
        money.setFont(font);
        money.setTextFill(Color.WHITE);
        root.getChildren().addAll(imageView, money);
    }

    private void pageSetText() {
        page.setText("Page = " + pageNumber + "/" + (cardsToShow.size() / 10 + (cardsToShow.size() % 10 != 0 ? 1 : 0)));
    }

    static void drawBackButton(Pane root, Scene scene, Account account) throws FileNotFoundException {
        ImageView backToMainView = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/button_back_corner.png")));
        backToMainViewSetting(root, scene, backToMainView, account);
    }

    private void addEventHandlerOnArrows(ImageView leftArrow, ImageView rightArrow) {
        leftArrow.setOnMouseEntered(event -> leftArrow.setEffect(new Glow(0.4)));
        rightArrow.setOnMouseEntered(event -> rightArrow.setEffect(new Glow(0.4)));

        leftArrow.setOnMouseExited(event -> leftArrow.setEffect(new Glow(0)));
        rightArrow.setOnMouseExited(event -> rightArrow.setEffect(new Glow(0)));

        if (pageNumber * 10 <= cardsToShow.size()) rightArrow.setEffect(new Glow(0));
        if (pageNumber == 1) leftArrow.setEffect(new Glow(0));

        leftArrow.setOnMouseClicked(event -> {
            pageNumber--;
            if (pageNumber == 0)
                pageNumber++;
            else {
                updateLabels();
                updatePowers(account);
                updatePrices();
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
                updatePrices();
            }
            pageSetText();
        });
    }

    private void showSearchBar(TextField searchTextField, ImageView searchButton, boolean show) {
        searchButton.setVisible(show);
        searchTextField.setVisible(show);
    }

    private void updateLabels() {
        removeLabels();
        for (int i = 0; i < 10; i++) {
            if (i + (10 * (pageNumber - 1)) < cardsToShow.size()) {
                Card card = Shop.findCardByName(cardsToShow.get(i + (10 * (pageNumber - 1))));
                Animation animation = GraphicalCommonUsages.getGif(cardsToShow.get(i + (10 * (pageNumber - 1))));
                animation.getView().setFitWidth(stage.getScene().getWidth() / 20);
                animation.getView().setFitHeight(stage.getScene().getHeight() / 10);
                if (card instanceof MovableCard) {
                    animation.getView().setFitHeight(animation.getView().getFitHeight() * 1.5);
                    animation.getView().setFitWidth(animation.getView().getFitWidth() * 1.5);
                }
                animation.setCycleCount(Integer.MAX_VALUE);
                animation.play();

                cardLabels.get(i).setText(/*"\n" + */cardsToShow.get(i + (10 * (pageNumber - 1))));
                cardPanes.get(i).setVisible(true);
                if (cardPanes.get(i).getChildren().get(cardPanes.get(i).getChildren().size() - 1) instanceof ImageView)
                    cardPanes.get(i).getChildren().remove(cardPanes.get(i).getChildren().size() - 1);
                cardPanes.get(i).getChildren().add(animation.getView());
            } else {
                cardPanes.get(i).setVisible(false);
                cardLabels.get(i).setText("");
            }
        }
        pageSetText();
    }

    private void updatePowers(Account account) {
        removePowers();
        for (int i = 0; i < 10; i++) {
            if (i + (10 * (pageNumber - 1)) < cardsToShow.size()) {
                MovableCard card = null;
                if (isInCollection)
                    card = (MovableCard) account.getCollection().findCardByName(cardsToShow.get(i + (10 * (pageNumber - 1))));
                else if (isInShop) {
                    if (Shop.findCardByName(cardsToShow.get(i + (10 * (pageNumber - 1)))) instanceof MovableCard)
                        card = (MovableCard) Shop.findCardByName(cardsToShow.get(i + (10 * (pageNumber - 1))));
                }
                if (card == null) continue; // TODO ??
                cardPowers.get(i).setText("\n" + card.getDamage() + "\t\t\t" + card.getHealth());
            }
            /*label->stackPane[i].setVisible(false)*/
        }
    }

    private void updatePrices() {
        removePrices();
        for (int i = 0; i < 10; i++) {
            if (i + (10 * (pageNumber - 1)) < cardsToShow.size()) {
                Card card = null;
                UsableItem item = null;
                if (isInCollection) {
                    card = account.getCollection().findCardByName(cardsToShow.get(i + (10 * (pageNumber - 1))));
                    item = account.getCollection().findItemByName(cardsToShow.get(i + (10 * (pageNumber - 1))));
                } else if (isInShop) {
                    card = Shop.findCardByName(cardsToShow.get(i + (10 * (pageNumber - 1))));
                    item = Shop.findItemByName(cardsToShow.get(i + (10 * (pageNumber - 1))));
                }
                if (card != null)
                    cardPrices.get(i).setText(card.getCost() + "");
                else if (item != null)
                    cardPrices.get(i).setText(item.getCost() + "");
            }
            /*label->stackPane[i].setVisible(false)*/
        }
    }

    private void removeLabels() {
        for (int i = 0; i < 10; i++)
            if (i + (10 * (pageNumber - 1)) < cardsToShow.size())
                cardPowers.get(i).setText("");
    }

    private void removePowers() {
        for (int i = 0; i < 10; i++)
            cardPowers.get(i).setText("");
    }

    private void removePrices() {
        for (int i = 0; i < 10; i++)
            cardPrices.get(i).setText("");
    }

    private void manageShopAndCollectionBars(StackPane shopPane, StackPane collectionPane, ImageView shopButton
            , ImageView collectionButton, Image shopBackground, Image collectionBackground, Image shopBackgroundGlow
            , Image collectionBackgroundGlow) {
        collectionPane.setOnMouseClicked(event -> {
            if (!isInCollection)
                shopAndCollectionBarManager(false);
            shopAndCollectionGlowHandler(collectionButton, collectionBackgroundGlow, shopButton, shopBackground);
            updateLabels();
            updatePowers(account);
            updatePrices();
        });
        shopPane.setOnMouseClicked(event -> {
            if (!isInShop)
                shopAndCollectionBarManager(true);
            shopAndCollectionGlowHandler(shopButton, shopBackgroundGlow, collectionButton, collectionBackground);
            updateLabels();
            updatePowers(account);
            updatePrices();
        });
    }

    private void shopAndCollectionBarManager(boolean setOnShop) {
        isInShop = setOnShop;
        isInCollection = !setOnShop;
        moveToHeroTab();
    }

    private static void backToMainViewSetting(Pane root, Scene mainMenuScene, ImageView backToMainView, Account account) {
        MainMenuFX.backButtonAdjustment(root, mainMenuScene, backToMainView);
        backToMainView.setOnMouseClicked(event -> {
            try {
                Main.setMainMenuFX(account);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private static void shopAndCollectionGlowHandler(ImageView changeableArea, Image secondImage, ImageView otherChangeableArea, Image otherFirstImage) {
        changeableArea.setImage(secondImage);
        otherChangeableArea.setImage(otherFirstImage);
    }

    static void buyProcess() throws FileNotFoundException {
        handleErrors(Shop.buy(account, cardLabels.get(selectedIndex).getText()));
//        GraphicalCommonUsages.drakePopUp("purchase was successful",);
        updateMoney();
    }

    static void updateMoney() {
        money.setText(account.getMoney() + "");
    }

    static void sellProcess() throws FileNotFoundException {
        handleErrors(Shop.sell(account, cardLabels.get(selectedIndex).getText()));
//        GraphicalCommonUsages.drakePopUp("purchase was successful",);
        updateMoney();
    }


    static Pane getRoot() {
        return root;
    }
}