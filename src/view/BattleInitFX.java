package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BattleInitFX {

    public Pane start(Stage primaryStage, Account account) throws FileNotFoundException {
        Pane root = new Pane();
        Scene battleInitScene = new Scene(new Pane(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(battleInitScene.getWidth());
        root.setPrefHeight(battleInitScene.getHeight());
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 40);
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/battleInit/pictures/obsidian_woods.jpg", root);

        VBox singlePlayer = singlePlayerSetUp(battleInitScene, font);

        VBox multiPlayer = multiPlayerSetUp(battleInitScene, font);

        HBox battleInitPrimary = new HBox(singlePlayer, multiPlayer);
        battleInitPrimary.setSpacing(battleInitScene.getWidth() / 10);
        root.getChildren().addAll(battleInitPrimary);
        battleInitPrimary.layoutXProperty().bind(root.widthProperty().subtract(battleInitPrimary.widthProperty()).divide(2));
        battleInitPrimary.layoutYProperty().bind(root.heightProperty().subtract(battleInitPrimary.heightProperty()).divide(2));
        BackgroundFill background_fill = new BackgroundFill(javafx.scene.paint.Color.grayRgb(20, 0.8),
                new CornerRadii(0), new javafx.geometry.Insets(0, 0, 0, 0));
        BackgroundFill background_fill_root = new BackgroundFill(javafx.scene.paint.Color.grayRgb(150, 0.8),
                new CornerRadii(0), new javafx.geometry.Insets(0, 0, 0, 0));
        battleInitPrimary.setBackground(new Background(background_fill));
        root.setBackground(new Background(background_fill_root));
        //  return battleInitScene;
        return root;
    }

    private VBox multiPlayerSetUp(Scene battleInitScene, Font font) throws FileNotFoundException {
        Image multiPlayerImage = new Image(new FileInputStream("src/view/sources/battleInit/pictures/multi.jpg"));
        ImageView multiPlayerView = new ImageView(multiPlayerImage);
        multiPlayerView.setFitWidth(battleInitScene.getWidth() / 4);
        multiPlayerView.setFitHeight(battleInitScene.getHeight() * 2 / 3);
        StackPane view = new StackPane(multiPlayerView);
        view.setStyle("-fx-padding: 10;-fx-background-radius: 10;");

//        javafx.scene.shape.Rectangle image = new Rectangle(battleInitScene.getWidth()/4,battleInitScene.getHeight()*2/3);
//        image.setArcWidth(30);   // Corner radius
//        image.setArcHeight(30);
//        ImagePattern pattern = new ImagePattern(
//                new Image(new FileInputStream("src/view/sources/battleInit/pictures/multi.jpg"), battleInitScene.getWidth()/4, battleInitScene.getHeight()*2/3, false, false) // Resizing
//        );
//        image.setFill(pattern);
        Text multiPlayerText = new Text("Multi Player");
        multiPlayerText.setFill(Color.WHITE);
        multiPlayerText.setFont(font);
        VBox multiPlayer = new VBox(view, multiPlayerText);
        multiPlayer.setAlignment(Pos.CENTER);
        return multiPlayer;
    }

    private VBox singlePlayerSetUp(Scene battleInitScene, Font font) throws FileNotFoundException {
        Image singlePlayerImage = new Image(new FileInputStream("src/view/sources/battleInit/pictures/single.jpg"));
        ImageView singlePlayerView = new ImageView(singlePlayerImage);
        singlePlayerView.setFitWidth(battleInitScene.getWidth() / 4);
        singlePlayerView.setFitHeight(battleInitScene.getHeight() * 2 / 3);
        StackPane view = new StackPane(singlePlayerView);
        view.setStyle("-fx-padding: 10;-fx-background-radius: 10;");
        Text singlePlayerText = new Text("Single Player");
        singlePlayerText.setFill(Color.WHITE);
        singlePlayerText.setFont(font);

        VBox singlePlayer = new VBox(view, singlePlayerText);
        singlePlayer.setAlignment(Pos.CENTER);
        return singlePlayer;
    }
}
