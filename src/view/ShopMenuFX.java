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
import model.Message.ShopCommand.Trade.TradeCommand;
import model.Message.ShopCommand.Trade.TradeRequest;
import model.Message.ShopCommand.Trade.TradeResponse;
import model.client.Client;
import presenter.ShopMenuProcess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import static view.GraphicalCommonUsages.yesCancelPopUp;
import static view.ShopMenu.handleErrors;

public class ShopMenuFX {
    private static ArrayList<String> heroes = new ArrayList<>();
    private static ArrayList<String> minions = new ArrayList<>();
    private static ArrayList<String> spells = new ArrayList<>();
    private static ArrayList<String> items = new ArrayList<>();

    private static ArrayList<String> collectionHeroes = new ArrayList<>();
    private static ArrayList<String> collectionMinions = new ArrayList<>();
    private static ArrayList<String> collectionSpells = new ArrayList<>();
    private static ArrayList<String> collectionItems = new ArrayList<>();
    private static HashMap<String, int[]> movableCardsPowers = new HashMap<>();
    private static HashMap<String, Integer> costs = new HashMap<>();
    private static HashMap<String, Integer> cardNumbers = new HashMap<>();
    private static HashMap<String, Integer> cardCollectionNumbers = new HashMap<>();
    private static long acccountMoney;

    private static Text page = new Text();
    private static boolean isInShop = true;
    private static boolean isInCollection = false;
    static ArrayList<String> cardsToShow = new ArrayList<>();
    private static ArrayList<Pane> panesOfGifs = new ArrayList<>();
    private static HashMap<Integer, Label> cardLabels = new HashMap<>();
    private static HashMap<Integer, Label> cardPowers = new HashMap<>();
    private static HashMap<Integer, Label> cardPrices = new HashMap<>();
    private static HashMap<Integer, StackPane> cardPanes = new HashMap<>();
    private static HashMap<Integer, ImageView> cardImages = new HashMap<>();
    private static HashSet<String> costumeCards = new HashSet<>();
    private static TradeResponse tradeResponse;

    static {
        tradeResponseTaken();
    }


    static int pageNumber = 1;
    private static int selectedIndex = 0;
    private static Account account;
    private static Label money;
    private static Pane root;
    private static Scene scene;

    ShopMenuFX(Account account) {
        ShopMenuFX.account = account;
    }

    public static void setResponse(TradeResponse message) {
        tradeResponse = message;
    }

    public Pane start(Stage primaryStage) throws FileNotFoundException {
        synchronized (Client.getInstance().getShopLock()) {

        }
        root = new Pane();
        final Font trump_med = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Medium-webfont.ttf"), 36);
        final Font trump_reg = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Regular-webfont.ttf"), 36);
        final Font trump_reg_small = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Regular-webfont.ttf"), 28);
        final Font averta = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/averta-light-webfont.ttf"), 40);

        scene = new Scene(new Group(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(primaryStage.getWidth());
        root.setPrefHeight(primaryStage.getHeight());
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/mainMenu/backgrounds/" + (Math.abs(new Random().nextInt() % 2) + 1) + ".jpg", root, true);

        GridPane gridPane = new GridPane();
        drawCards(gridPane, scene, root, trump_reg, trump_reg_small, true);
        drawShopLabels(root, averta, scene);
        drawLeftBox(trump_med, root, scene);
        drawBackButton(root, scene, account);
        drawDrake(root, scene, trump_reg);

        return root;
    }

    static void drawCards(GridPane gridPane, Scene scene, Pane root, Font trump, Font trump_small, Boolean shopMenuOrCollection) throws FileNotFoundException {
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
            if (showPrice) // equals accessing the function from shopMenu
                stackPane.setOnMouseClicked(event -> {
                    try {
                        selectedIndex = finalI;
                        String str = isInShop ? "BUY" : "SELL";
                        if (str.equals("BUY")) {
                            if (!ShopMenuProcess.isDrakeEnough((costs.get(cardLabels.get(finalI).getText().trim())), acccountMoney)) {
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
            else // accessing the function from collectionMenu
                CollectionMenuFX.addEventForCollectionMenu(stackPane);
            stackPane.setOnMouseExited(event -> stackPane.setEffect(new Glow(0)));

            stackPane.getChildren().addAll(imageView, cardName, card_AP_HP, price);
            gridPane.add(stackPane, i % 5, i / 5 > 0 ? 1 : 0);
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
                    updatePowers();
                    showSearchBar(searchTextField, searchButton, false);
                    break;
                case "[1]":
                    moveToMinionTab();
                    updatePowers();
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

    private void moveToWantedTab(ArrayList<String> shopArray, ArrayList<String> collectionArray, int imageNumber, boolean removePowers) {
        cardsToShow.clear();
        if (isInShop)
            cardsToShow.addAll(shopArray);
        else if (isInCollection)
            cardsToShow.addAll(collectionArray);
        pageNumber = 1;
        if (removePowers)
            removePowers();
        else
            updatePowers();
        updatePrices();
        updateLabels();
        try {
            setImages(imageNumber);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void moveToSpellTab() {
        moveToWantedTab(spells, collectionSpells, 2, true);
    }

    private void moveToItemTab() {
        moveToWantedTab(items, collectionItems, 2, true);
    }

    private void moveToMinionTab() {
        moveToWantedTab(minions, collectionMinions, 1, false);
    }

    private void moveToHeroTab() {
        moveToWantedTab(heroes, collectionHeroes, 1, false);
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

    static Image getCardTheme(int number) throws FileNotFoundException {
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
        money = new Label(acccountMoney + "");
        money.relocate(scene.getWidth() * 0.88, scene.getHeight() / 64 + 20);
        money.setFont(font);
        money.setTextFill(Color.WHITE);
        root.getChildren().addAll(imageView, money);
    }

    static void pageSetText() {
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
                updatePowers();
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
                    updatePowers();
                } catch (ClassCastException ignored) {
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

    static void updateLabels() {
        Scene currentScene = scene == null ? CollectionMenuFX.scene : scene;
        removeLabels();
        for (int i = 0; i < 10; i++) {
            if (i + (10 * (pageNumber - 1)) < cardsToShow.size()) {
                int index = i + (10 * (pageNumber - 1));
                int cardType1 = getCardType(index);
                Animation animation = GraphicalCommonUsages.getGif(cardsToShow.get(index), "idle", cardType1, costumeCards.contains(cardsToShow.get(index))); //todo costumCard
                if (animation == null && !costumeCards.contains(cardsToShow.get(index))) {
                    System.out.println(cardsToShow.get(i + (10 * (pageNumber - 1))));
                }
                animation.getView().setFitWidth(currentScene.getWidth() / 20);
                animation.getView().setFitHeight(currentScene.getHeight() / 10);
                if (heroes.contains(cardsToShow.get(index)) || minions.contains(cardsToShow.get(index))) {
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

    private static int getCardType(int index) {
        int cardType = 5;
        if (items.contains(cardsToShow.get(index)))
            cardType = 3;
        if (spells.contains(cardsToShow.get(index)))
            cardType = 2;
        if (minions.contains(cardsToShow.get(index)))
            cardType = 1;
        if (heroes.contains(cardsToShow.get(index)))
            cardType = 0;
        return cardType;
    }

    static void updatePowers() {
        removePowers();
        if(cardsToShow.size() == 0)
            return;
        if (items.contains(cardsToShow.get(0)) || spells.contains(cardsToShow.get(0)))
            return;
        for (int i = 0; i < 10; i++) {
            if (i + (10 * (pageNumber - 1)) < cardsToShow.size()) {
                int index = i + (10 * (pageNumber - 1));
                if (!movableCardsPowers.containsKey(cardsToShow.get(index)))
                    System.out.println(cardsToShow.get(index));
                int damage = movableCardsPowers.get(cardsToShow.get(index))[0];
                int health = movableCardsPowers.get(cardsToShow.get(index))[1];
                cardPowers.get(i).setText("\n" + damage + "\t\t\t" + health+"\n");
                if(isInShop)
                    cardPowers.get(i).setText(cardPowers.get(i).getText()+cardNumbers.get(cardsToShow.get(index)));
                if(isInCollection)
                    cardPowers.get(i).setText(cardPowers.get(i).getText()+cardCollectionNumbers.get(cardsToShow.get(index)));
            }
        }
    }

    private void updatePrices() {
        removePrices();
        for (int i = 0; i < 10; i++) {
            if (i + (10 * (pageNumber - 1)) < cardsToShow.size()) {
                int index = i + (10 * (pageNumber - 1));
                cardPrices.get(i).setText(costs.get(cardsToShow.get(index)) + "");
            }
        }
    }

    private static void removeLabels() {
        for (int i = 0; i < 10; i++)
            if (i + (10 * (pageNumber - 1)) < cardsToShow.size())
                cardPowers.get(i).setText("");
    }

    private static void removePowers() {
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
            updatePowers();
            updatePrices();
        });
        shopPane.setOnMouseClicked(event -> {
            if (!isInShop)
                shopAndCollectionBarManager(true);
            shopAndCollectionGlowHandler(shopButton, shopBackgroundGlow, collectionButton, collectionBackground);
            updateLabels();
            updatePowers();
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

    private static void tradeProcess(boolean buy) throws FileNotFoundException {
        Client.getInstance().sendData(new TradeRequest(Client.getInstance().getAuthCode(), buy, cardLabels.get(selectedIndex).getText()));
        synchronized (Client.getInstance().getShopLock()) {
            if (tradeResponse.getAuthCode().equals("")) {
                try {
                    Client.getInstance().getShopLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        handleTrad();
        updateMoney();
    }


    static void buyProcess() throws FileNotFoundException {
        tradeProcess(true);
    }

    static void sellProcess() throws FileNotFoundException {
        tradeProcess(false);
    }

    private static void handleTrad() throws FileNotFoundException {
        if (!tradeResponse.isSuccessFullTrade()) {
            handleErrors(tradeResponse.getAlertMessage());
            tradeResponseTaken();
            return;
        }
        String name = tradeResponse.getObjectName();
        if (tradeResponse.isBuy()) {
            acccountMoney -= tradeResponse.getCost();
            cardNumbers.replace(name, cardNumbers.get(name) - 1);
            if (cardCollectionNumbers.containsKey(name)) {
                cardCollectionNumbers.replace(name, cardCollectionNumbers.get(name) + 1);
            }
            else {
                newCardActions(name);
            }
        } else {
            acccountMoney += tradeResponse.getCost();
            cardNumbers.replace(name, cardNumbers.get(name) + 1);
            cardCollectionNumbers.replace(name, cardCollectionNumbers.get(name) - 1);
            if(cardCollectionNumbers.get(name) == 0){
                collectionItems.remove(name);
                collectionHeroes.remove(name);
                collectionSpells.remove(name);
                collectionMinions.remove(name);
            }
        }
        tradeResponseTaken();
    }

    private static void newCardActions(String name) {
        cardCollectionNumbers.put(name, 1);
        if(heroes.contains(name))
            collectionHeroes.add(name);
        if(minions.contains(name))
            collectionMinions.add(name);
        if(spells.contains(name))
            collectionSpells.add(name);
        if(items.contains(name))
            collectionItems.add(name);
    }

    static void updateMoney() {
        //todo send a request to server
        money.setText(acccountMoney + "");
    }


    private static void tradeResponseTaken() {
        tradeResponse = new TradeResponse("", true, "", false, 0, 0);
    }

    //setters


    public static void setAcccountMoney(long acccountMoney) {
        ShopMenuFX.acccountMoney = acccountMoney;
    }

    public static void setHeroes(ArrayList<String> heroes) {
        ShopMenuFX.heroes = heroes;
    }

    public static void setMinions(ArrayList<String> minions) {
        ShopMenuFX.minions = minions;
    }

    public static void setSpells(ArrayList<String> spells) {
        ShopMenuFX.spells = spells;
    }

    public static void setItems(ArrayList<String> items) {
        ShopMenuFX.items = items;
    }

    public static void setCollectionHeroes(ArrayList<String> collectionHeroes) {
        ShopMenuFX.collectionHeroes = collectionHeroes;
    }

    public static void setCollectionMinions(ArrayList<String> collectionMinions) {
        ShopMenuFX.collectionMinions = collectionMinions;
    }

    public static void setCollectionSpells(ArrayList<String> collectionSpells) {
        ShopMenuFX.collectionSpells = collectionSpells;
    }

    public static void setCollectionItems(ArrayList<String> collectionItems) {
        ShopMenuFX.collectionItems = collectionItems;
    }

    public static void setMovableCardsPowers(HashMap<String, int[]> movableCardsPowers) {
        ShopMenuFX.movableCardsPowers = movableCardsPowers;
    }

    public static void setCosts(HashMap<String, Integer> costs) {
        ShopMenuFX.costs = costs;
    }

    public static void setCardNumbers(HashMap<String, Integer> cardNumbers) {
        ShopMenuFX.cardNumbers = cardNumbers;
    }

    public static void setCostumeCards(HashSet<String> costumeCards) {
        ShopMenuFX.costumeCards = costumeCards;
    }

    public static void setCardCollectionNumbers(HashMap<String, Integer> cardCollectionNumbers) {
        ShopMenuFX.cardCollectionNumbers = cardCollectionNumbers;
    }

    //setters


    static Pane getRoot() {
        return root;
    }
}