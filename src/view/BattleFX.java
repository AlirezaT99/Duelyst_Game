package view;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Account;
import model.Match;
import model.Player;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class BattleFX {
    private static double screenWidth;
    private static double screenHeight;
    private static Rectangle[][] rectangles = new Rectangle[9][5];

    Pane start(Match match, boolean isStoryMode, Stage stage, Account account) throws FileNotFoundException {
        BattleMenu.battleSetup(match);
        Pane root = new Pane();
        setScreenVariables(stage);
        setBackGround(match, isStoryMode, root);
       // root.getChildren().addAll(setTable(new Group()));
        setGeneralIcons(match, root, new Scene(new Group(), screenWidth, screenHeight));
        root.setOnMouseClicked(event -> {
            try {
                Main.setBattleMenuFX(account);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        return root;
    }

    //create table graphics

    private Group setTable(Group group) {
        PerspectiveTransform perspectiveTransform = getPerspectiveTransform();
        group.setEffect(perspectiveTransform);
        group.setCache(true);
        createTableRectangles(group);
        return group;
    }

    private String deleteWhiteSpaces(String string) {
        String result = string.replaceAll(" ", "");
        return result.trim();
    }

    private void setGeneralIcons(Match match, Pane root, Scene scene) throws FileNotFoundException {
        //  System.out.println(match.getPlayer1());

        Image firstPlayerImage = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/generals/" + deleteWhiteSpaces(match.getPlayer1().getDeck().getHero().getName()).toLowerCase() + ".png"));
        Image secondPlayerImage = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/generals/" + deleteWhiteSpaces(match.getPlayer2().getDeck().getHero().getName()).toLowerCase() + ".png"));

        ImageView firstPlayerImageView = new ImageView(firstPlayerImage);
        ImageView secondPlayerImageView = new ImageView(secondPlayerImage);


        firstPlayerImageView.setFitWidth(scene.getWidth() / 6);
        firstPlayerImageView.setPreserveRatio(true);

        secondPlayerImageView.setFitWidth(scene.getWidth() / 6);
        secondPlayerImageView.setPreserveRatio(true);


        HBox firstPlayerManas = drawMana(match.getPlayer1(), match, root, scene, 1);
        HBox firstPlayer = new HBox(firstPlayerImageView, firstPlayerManas);
        firstPlayerManas.setAlignment(Pos.CENTER);

        HBox secondPlayerManas = drawMana(match.getPlayer2(), match, root, scene, 2);
        HBox secondPlayer = new HBox(secondPlayerManas,secondPlayerImageView);
        secondPlayerManas.setAlignment(Pos.CENTER);

        HBox iconsRow = new HBox(firstPlayer, secondPlayer);
        iconsRow.setSpacing(scene.getWidth() / 4.5);
        iconsRow.layoutXProperty().bind(root.widthProperty().subtract(iconsRow.widthProperty()).divide(2));
        iconsRow.setLayoutY(-1 * (scene.getHeight() / 20));
        HBox bottomRow = drawHand(match.getPlayer1(),match,root,scene);

        root.getChildren().addAll(iconsRow,bottomRow);
        bottomRow.layoutXProperty().bind(root.widthProperty().subtract(bottomRow.widthProperty()).divide(2));

        bottomRow.setPadding(new Insets(scene.getHeight()*3/4,0,0,0));

        firstPlayerImageView.setOnMouseEntered(event -> scaleIcon(firstPlayerImageView, 1, 1.1));
        firstPlayerImageView.setOnMouseExited(event -> scaleIcon(firstPlayerImageView, 1.1, 1));

        secondPlayerImageView.setOnMouseEntered(event -> scaleIcon(secondPlayerImageView, 1, 1.1));
        secondPlayerImageView.setOnMouseExited(event -> scaleIcon(secondPlayerImageView, 1.1, 1));

    }
    private HBox drawHand(Player player, Match match, Pane root, Scene scene) throws FileNotFoundException {
        HBox bottomRow = new HBox();
        Image cardHolder = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/game-hand-card-container.png"));

        for(int i = 0; i < 5; i++) {
            VBox cardContainer = new VBox();
            ImageView cardHolderView = new ImageView(cardHolder);
            cardHolderView.setFitHeight(scene.getWidth()/8);
            cardHolderView.setPreserveRatio(true);
            Image mana = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/mana/mana_active.png"));
            ImageView manaView = new ImageView(mana);
            if (( player.getMana()<player.getHand().getCards().get(i).getManaCost())){
                ColorAdjust grayscale = new ColorAdjust();
                grayscale.setBrightness(-0.5);
                manaView.setEffect(grayscale);
            }
            StackPane imageStackPane = new StackPane();
            javafx.scene.control.Label cardName = new Label(player.getHand().getCards().get(i).getName());
            cardName.setTextFill(Color.WHITE);
            imageStackPane.getChildren().addAll(cardHolderView,cardName);
            manaView.setFitHeight(scene.getHeight() / 20);
            manaView.setPreserveRatio(true);
            StackPane manaStackPane = new StackPane();
            manaStackPane.getChildren().addAll(manaView,new Text(player.getHand().getCards().get(i).getManaCost()+""));
            cardContainer.getChildren().addAll(imageStackPane,manaStackPane);
            cardContainer.setAlignment(Pos.CENTER);
            cardContainer.setSpacing((-1)*(scene.getHeight())/20);
//            cardContainer.setOnMouseEntered(event -> scaleIcon(cardHolderView,1,1.1));
//            cardHolderView.setOnMouseExited(event -> scaleIcon(cardHolderView,1.1,1));
            bottomRow.getChildren().add(cardContainer);

        }

        return bottomRow;
    }


    private HBox drawMana(Player player, Match match, Pane root, Scene scene, int mode) throws FileNotFoundException {
        HBox manas = new HBox();
        for (int i = 0; i < 9; i++) {
            Image mana = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/mana/mana_inactive.png"));
            if ((i < player.getMana() && mode == 1) || (i > 8 - player.getMana() && mode == 2))
                mana = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/mana/mana_active.png"));
            System.out.println(player.getUserName() + " " + player.getMana());
            ImageView manaView = new ImageView(mana);
            manaView.setFitHeight(scene.getHeight() / 20);
            manaView.setPreserveRatio(true);
            manas.getChildren().addAll(manaView);
        }
        if (mode == 1)
            manas.setRotate(-4);
        else
            manas.setRotate(4);
        return manas;
    }

    private void scaleIcon(ImageView firstPlayerIcon, double from, double to) {
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(firstPlayerIcon);
        scaleTransition.setDuration(Duration.millis(100));
        scaleTransition.setFromX(from);
        scaleTransition.setFromY(from);
        scaleTransition.setToX(to);
        scaleTransition.setToY(to);
        scaleTransition.play();
    }

    private void createTableRectangles(Group group) {
        double width = 100;
        double margin = 5;
        double height = 100;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                rectangles[i][j] = new Rectangle((width + margin) * (i + 1), (height + margin) * (j + 1), width, height);
                rectangles[i][j].setFill(Color.BLUE);
                rectangles[i][j].setOpacity(0.4);
                group.getChildren().addAll(rectangles[i][j]);
                int finalJ = j;
                int finalI = i;
                rectangles[i][j].setOnMouseEntered(event -> rectangles[finalI][finalJ].setEffect(new Glow(1)));
            }
        }
    }

    private PerspectiveTransform getPerspectiveTransform() {
        double ulx = 520.0;
        double urx = 660 + screenWidth / 32 * 11;
        double llx = 480.0;
        double lrx = 760.0 + screenWidth / 32 * 11;
        double uy = 320.0;
        double ly = 420.0 + screenHeight / 3;
        PerspectiveTransform perspectiveTransform = new PerspectiveTransform();
        perspectiveTransform.setUrx(urx);
        perspectiveTransform.setUry(uy);
        perspectiveTransform.setUlx(ulx);
        perspectiveTransform.setUly(uy);
        perspectiveTransform.setLlx(llx);
        perspectiveTransform.setLly(ly);
        perspectiveTransform.setLrx(lrx);
        perspectiveTransform.setLry(ly);
        return perspectiveTransform;
    }

    //create Table graphics

    private void setScreenVariables(Stage stage) {
        screenHeight = stage.getHeight();
        screenWidth = stage.getWidth();
    }

    private void setBackGround(Match match, boolean isStoryMode, Pane pane) throws FileNotFoundException {
        String arenaAddress = "src/view/sources/Battle/BattlePictures/Arena/";
        arenaAddress += isStoryMode ? "Story/" : "nonStory/";
        arenaAddress += isStoryMode ? match.getGameMode() : Math.abs(new Random().nextInt() % 7);
        arenaAddress += ".jpg";
        ImageView backGround = new ImageView(new Image(new FileInputStream(arenaAddress)));
        setImageFItToScreen(backGround);
        pane.getChildren().addAll(backGround);
    }

    private void setImageFItToScreen(ImageView foreGroundImage) {
        foreGroundImage.setFitHeight(screenHeight);
        foreGroundImage.setFitWidth(screenWidth);
    }
}

