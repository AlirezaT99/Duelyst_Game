package view;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SinglePlayerMenuFX {
    public Pane start(Stage primaryStage, Account account) throws FileNotFoundException {
        Pane root = new Pane();
        Scene singlePlayerMenuScene = new Scene(new Pane(),primaryStage.getWidth(),primaryStage.getHeight());
        root.setPrefWidth(singlePlayerMenuScene.getWidth());
        root.setPrefHeight(singlePlayerMenuScene.getHeight());
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 40);
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/battleInit/pictures/obsidian_woods.jpg", root);

        VBox storyMode = storyModeSetUP(singlePlayerMenuScene, font, account);

        VBox customGame = customGameSetUp(singlePlayerMenuScene, font, account);

        HBox battleInitSecondary = new HBox(storyMode,customGame);
        battleInitSecondary.setSpacing(singlePlayerMenuScene.getWidth()/10);

        Image back = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_back_corner.png"));
        ImageView backView = new ImageView(back);
        GraphicalCommonUsages.backSetting(root, singlePlayerMenuScene,account,"battleInit");
        root.getChildren().addAll(battleInitSecondary);
        battleInitSecondary.layoutXProperty().bind(root.widthProperty().subtract(battleInitSecondary.widthProperty()).divide(2));
        battleInitSecondary.layoutYProperty().bind(root.heightProperty().subtract(battleInitSecondary.heightProperty()).divide(2));
        // battleInitPrimary.setPadding(new Insets(50,0,50,0));
        BackgroundFill background_fill = new BackgroundFill(javafx.scene.paint.Color.grayRgb(20,0.8),
                new CornerRadii(0), new javafx.geometry.Insets(0,0,0,0));
        battleInitSecondary.setBackground(new Background(background_fill));

        //  return battleInitScene;
        return root;
    }

    private VBox customGameSetUp(Scene battleInitScene, Font font, Account account) throws FileNotFoundException {
        Image customGameImage = new Image(new FileInputStream("src/view/sources/singlePlayerMenu/pictures/custom.jpg"));
        ImageView cutomGameView = new ImageView(customGameImage);
        cutomGameView.setFitWidth(battleInitScene.getWidth()/4);
        cutomGameView.setFitHeight(battleInitScene.getHeight()*2/3);
        StackPane view = new StackPane(cutomGameView);
        view.setStyle("-fx-padding: 30;-fx-background-radius: 10;");

        view.setOpacity(0.6);
        Text multiPlayerText = new Text("CUSTOM GAME");
        multiPlayerText.setFill(Color.WHITE);
        multiPlayerText.setFont(font);
        VBox customGame = new VBox(view,multiPlayerText,new Text(""));
        customGame.setSpacing(battleInitScene.getHeight()/20);
        customGame.setAlignment(Pos.CENTER);

        mouseMovementHandling(view, multiPlayerText, customGame, account);

        return customGame;
    }

    private void mouseMovementHandling(StackPane view, Text text, VBox vBox, Account account) {
        vBox.setOnMouseEntered(event -> {
            double[] numbers = {1,1,1.1,0.5};
            handleMouseEvent(view,vBox,text,numbers);
        });

        vBox.setOnMouseExited(event -> {
            double[] numbers = {0.6,1.1,1,0.5};
            handleMouseEvent(view, vBox, text,numbers);
        });
        vBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new GraphicalCommonUsages().mouseClickAudioPlay();
                switch (text.getText()){
                    case "STORY MODE":
                        try {
                            Main.setStoryMenuFX(account);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "CUSTOM GAME":
                        try {
                            Main.setCustomGameMenuFX(account);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                }

            }
        });
    }

    private void handleMouseEvent(StackPane view, VBox vBox, Text text,double[]numbers) {
        view.setOpacity(numbers[0]);
        ScaleTransition st = new ScaleTransition(Duration.millis(100),vBox);
        st.setFromX(numbers[1]);
        st.setFromY(numbers[1]);
        st.setToX(numbers[2]);
        st.setToY(numbers[2]);
        st.play();
        text.setEffect(new Glow(numbers[3]));
    }

    private VBox storyModeSetUP(Scene battleInitScene, Font font, Account account) throws FileNotFoundException {
        Image storyModeImage = new Image(new FileInputStream("src/view/sources/singlePlayerMenu/pictures/story.jpg"));
        ImageView storyModeView = new ImageView(storyModeImage);
        storyModeView.setFitWidth(battleInitScene.getWidth()/4);
        storyModeView.setFitHeight(battleInitScene.getHeight()*2/3);
        StackPane view = new StackPane(storyModeView);
        view.setStyle("-fx-padding: 30;-fx-background-radius: 10;");
        view.setOpacity(0.6);
        Text singlePlayerText = new Text("STORY MODE");
        singlePlayerText.setFill(Color.WHITE);
        singlePlayerText.setFont(font);
        VBox storyMode = new VBox(view,singlePlayerText,new Text());
        storyMode.setSpacing(battleInitScene.getHeight()/20);
        storyMode.setAlignment(Pos.CENTER);
        mouseMovementHandling(view,singlePlayerText,storyMode, account);
        return storyMode;
    }

//    private void backToPlayViewSetting(Pane root, Scene mainMenuScene, ImageView backToLoginView, Account account)  {
//        backToLoginView.setFitWidth(mainMenuScene.getWidth()/15);
//        backToLoginView.setPreserveRatio(true);
//        root.getChildren().addAll(backToLoginView);
//        backToLoginView.setOpacity(0.5);
//        backToLoginView.setOnMouseEntered(event -> backToLoginView.setOpacity(0.9));
//        backToLoginView.setOnMouseExited(event -> backToLoginView.setOpacity(0.5));
//        backToLoginView.setOnMouseClicked(event -> {
//            try {
//                Main.setBattleMenuFX(account);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        });
//    }
}
