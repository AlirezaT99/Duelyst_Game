package view;

import javafx.scene.Group;
import javafx.scene.effect.Glow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Account;
import model.Match;
import model.Table;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class BattleFX {
    private static double screenWidth;
    private static double screenHeight;
    private static Rectangle[][] rectangles = new Rectangle[9][5];

    Pane start(Match match, boolean isStoryMode, Stage stage, Account account) throws FileNotFoundException {
        Pane pane = new Pane();
        setScreenVariables(stage);
        setBackGround(match, isStoryMode, pane);
        Pane table = setTable(new Pane());
        pane.getChildren().addAll(table);
//        table.relocate((screenWidth - table.getWidth()) / 2, (screenHeight - table.getHeight()) / 2);
        pane.setOnMouseClicked(event -> {
            try {
                Main.setBattleMenuFX(account);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        return pane;
    }

    //create table graphics

    private Pane setTable(Pane group) {
        PerspectiveTransform perspectiveTransform = getPerspectiveTransform();
        group.setEffect(perspectiveTransform);
        group.setCache(true);
        createTableRectangles(group);
        return group;
    }

    private void createTableRectangles(Pane group) {
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
        screenHeight = stage.getScene().getHeight();
        screenWidth = stage.getScene().getWidth();
        System.out.println("screenHeight = " + screenHeight);
    }

    private void setBackGround(Match match, boolean isStoryMode, Pane pane) throws FileNotFoundException {
        String arenaAddress = "src/view/sources/Battle/BattlePictures/Arena/";
        arenaAddress += !isStoryMode ? "Story/" : "nonStory/";
        arenaAddress += !isStoryMode ? match.getGameMode() : Math.abs(new Random().nextInt() % 7);
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

