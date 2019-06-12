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
import javafx.scene.effect.BoxBlur;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ShopMenuFX {
    private Text page = new Text();
    private boolean isInShop = true;
    private boolean isInCollection = false;
    private static ArrayList<String> cardsToShow = new ArrayList<>();
    private static HashMap<Integer, Label> cardLabels = new HashMap<>();
    private static int pageNumber = 1;
    private Account account;

    public ShopMenuFX(Account account) {
        this.account = account;
    }

    public Pane start(Stage primaryStage) throws FileNotFoundException {
        final Font trump_med = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Medium-webfont.ttf"), 36);
        final Font trump_reg = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/TrumpGothicPro-Regular-webfont.ttf"), 36);
        final Font averta = Font.loadFont(new FileInputStream("src/view/sources/shopMenu/averta-light-webfont.ttf"), 40);

        Scene scene = new Scene(new Group(), primaryStage.getWidth(), primaryStage.getHeight());
        Pane root = new Pane();
        root.setPrefWidth(primaryStage.getWidth());
        root.setPrefHeight(primaryStage.getHeight());
        setBackGroundImage("src/view/sources/mainMenu/backgrounds/" + (Math.abs(new Random().nextInt() % 2) + 1) + ".jpg", root, primaryStage);

        drawCards(scene, root, trump_reg);
        drawShopLabels(root, averta, scene);
        drawLeftBox(trump_med, root, scene);
        drawBackButton(root, scene);

        return root;
    }

    private void drawCards(Scene scene, Pane root, Font trump_reg) throws FileNotFoundException {
        StackPane[] stackPanes = new StackPane[10];
        for (int i = 0; i < 10; i++) {
            stackPanes[i] = new StackPane();
            stackPanes[i].relocate((i % 5 + 2.6) * (scene.getWidth() / 9) + (25 * (i % 5))
                    , i / 5 > 0 ? (scene.getHeight() * 9 / 16) : (scene.getHeight() * 3 / 16));
            ImageView imageView = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/cardTheme1.png"))); // for now
            //, 157, 279, false, false
            Label label = new Label("Label " + i);
            label.setFont(trump_reg);
            label.setTextFill(Color.WHITE);
            cardLabels.put(i, label);
            stackPanes[i].getChildren().addAll(imageView, label);
            StackPane.setAlignment(label, Pos.CENTER);
            stackPanes[i].setMaxSize(157,279);
        }
        root.getChildren().addAll(stackPanes);
    }

    private void drawLeftBox(Font font, Pane root, Scene scene) throws FileNotFoundException {
        ImageView heroesCircle = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/shopMenuCircle1.png")));
        ImageView minionsCircle = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/shopMenuCircle2.png")));
        ImageView itemsCircle = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/shopMenuCircle3.png")));
        ImageView searchCircle = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/shopMenuCircle4.png")));
        searchCircle.setFitWidth(90);
        searchCircle.setFitHeight(90);
        heroesCircle.setFitWidth(90);
        heroesCircle.setFitHeight(90);
        minionsCircle.setFitWidth(90);
        minionsCircle.setFitHeight(90);
        itemsCircle.setFitWidth(90);
        itemsCircle.setFitHeight(90);

        Label heroes = new Label("HEROES");
        Label minions = new Label("MINIONS");
        Label items = new Label("ITEMS");
        Label search = new Label("SEARCH");
        Label[] labels = new Label[]{heroes, minions, items, search};
        for (Label label : labels) {
            label.setFont(font);
            label.setTextFill(Color.WHITESMOKE);
        }

        HBox[] hBoxes = new HBox[4];
        for (int i = 0; i < 4; i++) {
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

        hBoxes[3].getChildren().addAll(searchCircle, search);
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Enter card/item ID");
        ImageView searchButton = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/searchButton.png")));

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

        ListView<HBox> listView = new ListView<>(); // TODO somebody help :/ don't know how to remove its background -_-
        listView.relocate(scene.getWidth() / 30, scene.getHeight() / 5);
        listView.getItems().addAll(hBoxes);
        listView.setMaxHeight(388);
        listView.setStyle("-fx-control-inner-background: rgba(0,46,72,0.3);-fx-border-color: rgba(0,46,72,0.3);" +
                "-fx-control-outer-background: rgba(0,46,72,0.3);"); // -fx-border-radius: 10;-fx-background-radius: 10;
        listView.setBackground(new Background
                (new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, new javafx.geometry.Insets(0))));
        listView.setOnMouseClicked(event -> {
            switch (listView.getSelectionModel().getSelectedIndices().toString()) {
                case "[0]":
                    cardsToShow.clear();
                    if (isInShop)
                        for (Hero hero : Shop.getShopHeroes())
                            cardsToShow.add(hero.getName());
                    else if (isInCollection)
                        for (Hero hero : account.getCollection().getHeroes())
                            cardsToShow.add(hero.getName());
                    pageNumber = 1;
                    updateLabels();
                    showSearchBar(searchTextField, searchButton, false);
                    break;
                case "[1]":
                    cardsToShow.clear();
                    if (isInShop)
                        for (Minion minion : Shop.getShopMinions())
                            cardsToShow.add(minion.getName());
                    else if (isInCollection)
                        for (Minion minion : account.getCollection().getMinions())
                            cardsToShow.add(minion.getName());
                    pageNumber = 1;
                    updateLabels();
                    showSearchBar(searchTextField, searchButton, false);
                    break;
                case "[2]":
                    cardsToShow.clear();
                    if (isInShop)
                        for (Item item : Shop.getShopItems())
                            cardsToShow.add(item.getName());
                    else if (isInCollection)
                        for (Item item : account.getCollection().getItems())
                            cardsToShow.add(item.getName());
                    pageNumber = 1;
                    updateLabels();
                    showSearchBar(searchTextField, searchButton, false);
                    break;
                case "[3]":
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

    private void drawShopLabels(Pane root, Font font, Scene scene) throws FileNotFoundException {
        Label shop = new Label("SHOP");
        shop.setFont(font);
        shop.setTextFill(Color.WHITE);
        shop.relocate(scene.getWidth() / 16, scene.getHeight() / 16);

        Rectangle underLine = new Rectangle(10, 10);
        underLine.setFill(Color.WHITE);
        underLine.relocate(scene.getWidth() / 16, scene.getHeight() / 16 + shop.getLayoutY() + 10);

        Rectangle underLine2 = new Rectangle(200, 10);
        underLine2.setFill(Color.WHITE);
        underLine2.relocate(scene.getWidth(), scene.getHeight() / 16 + shop.getLayoutY() + 20);

        root.getChildren().addAll(shop, underLine, underLine2);

        //Timeline
        KeyValue xValue = new KeyValue(underLine.widthProperty(), scene.getWidth() * 5 / 8);
        KeyValue yValue = new KeyValue(underLine.heightProperty(), 3);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), xValue, yValue);
        Timeline timeline = new Timeline(keyFrame);

        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();

        KeyValue xValue2 = new KeyValue(underLine2.layoutXProperty(), scene.getWidth() * 7.2 / 8);
        KeyValue yValue2 = new KeyValue(underLine2.heightProperty(), 3);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1001), xValue2, yValue2); // to avoid duplicate annoying lines -_-
        Timeline timeline2 = new Timeline(keyFrame2);

        timeline2.getKeyFrames().addAll(keyFrame2);
        timeline2.play();
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
        shopPane.setLayoutX(scene.getWidth() * 28 / 40);
        shopPane.setLayoutY(scene.getHeight() / 16 + shop.getLayoutY() * 0.7);
        collectionPane.setLayoutX(scene.getWidth() * 30.5 / 40);
        collectionPane.setLayoutY(scene.getHeight() / 16 + shop.getLayoutY() * 0.7);
        shopPane.getChildren().addAll(shopButton, shopLabel);
        collectionPane.getChildren().addAll(collectionButton, collectionLabel);
        addOnClickHandler(shopLabel, shopButton, shopBackgroundGlow, collectionButton, collectionBackground);
        addOnClickHandler(collectionLabel, collectionButton, collectionBackgroundGlow, shopButton, shopBackground);

        // CHECKUP
//      <!-- PageDefinedHere -->
        page.relocate(50, 650);
        page.setFont(font);
        page.setFill(Color.WHITE);


        manageShopAndCollectionBars(shopPane, collectionPane);
        root.getChildren().addAll(shopPane, collectionPane);
    }

    private void pageSetText() {
        page.setText("Page = " + pageNumber + "/" + (cardsToShow.size() / 10 + (cardsToShow.size() % 10 != 0 ? 1 : 0)));
    }

    private void drawBackButton(Pane root, Scene scene) throws FileNotFoundException {
        ImageView backToMainView = new ImageView(new Image(new FileInputStream("src/view/sources/shopMenu/button_back_corner.png")));
        backToMainViewSetting(root, scene, backToMainView);
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
            else updateLabels();
            pageSetText();
        });
        rightArrow.setOnMouseClicked(event -> {
            pageNumber++;
            if ((pageNumber - 1) * 10 >= cardsToShow.size()) pageNumber--;
            else updateLabels();
            pageSetText();
        });
    }

    private void showSearchBar(TextField searchTextField, ImageView searchButton, boolean show) {
        searchButton.setVisible(show);
        searchTextField.setVisible(show);
    }

    private void updateLabels() {
        for (int i = 0; i < 10; i++) {
            if (i + (10 * (pageNumber - 1)) < cardsToShow.size())
                cardLabels.get(i).setText(cardsToShow.get(i + (10 * (pageNumber - 1))));
            else
                cardLabels.get(i).setText("NULL");
            /*label->stackPane[i].setVisible(false)*/
        }
        pageSetText();
    }

    private void manageShopAndCollectionBars(StackPane shopPane, StackPane collectionPane) {
        collectionPane.setOnMouseClicked(event -> {
            if (!isInCollection)
                shopAndCollectionBarManager(false);
        });
        shopPane.setOnMouseClicked(event -> {
            if (!isInShop)
                shopAndCollectionBarManager(true);
        });
    }

    private void shopAndCollectionBarManager(boolean setOnShop) {
        isInShop = setOnShop;
        isInCollection = !setOnShop;
    }

    private static void setBackGroundImage(Image backGroundImage, Pane root, Stage stage) {
        ImageView backGround = new ImageView(backGroundImage);
        backGround.setFitWidth(stage.getWidth());
        backGround.setFitHeight(stage.getHeight());
        backGround.setEffect(new BoxBlur());
        root.getChildren().add(backGround);
    }

    private static void setBackGroundImage(String imageAddress, Pane root, Stage stage) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(imageAddress));
        setBackGroundImage(image, root, stage);
    }

    private void backToMainViewSetting(Pane root, Scene mainMenuScene, ImageView backToMainView) {
        MainMenuFX.backButtonAdjustment(root, mainMenuScene, backToMainView);
        backToMainView.setOnMouseClicked(event -> {
            try {
                Main.setMainMenuFX(account);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private static void addOnClickHandler(Label firstLabel, ImageView changeableArea, Image secondImage, ImageView otherChangeableArea, Image otherFirstImage) {
        firstLabel.setOnMouseClicked(event -> changeableArea.setImage(secondImage));
        changeableArea.setOnMouseClicked(event -> changeableArea.setImage(secondImage));
        otherChangeableArea.setImage(otherFirstImage);
        // TODO clicking one, doesn't remove the glow from the other -_-
    }
}