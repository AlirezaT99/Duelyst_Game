package view;

import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Account;
import model.Message.ScoreBoardCommand.ScoreBoardCommand;
import model.client.Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ScoreBoardFX {
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Boolean> onlineStatus = new ArrayList<>();
    private ArrayList<Integer> numOfWins = new ArrayList<>();

    public Pane start(Stage primaryStage, String authCode, Account account) throws FileNotFoundException {
        Pane root = new Pane();
        Scene scoreBoardScene = new Scene(new Pane(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(scoreBoardScene.getWidth());
        root.setPrefHeight(scoreBoardScene.getHeight());
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 40);
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/scoreBoard/backGround/1.jpg", root, false);
        //GraphicalCommonUsages.backSetting(root, scoreBoardScene, account, "mainMenu");
        Image back = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_back_corner.png"));
        ImageView backView = new ImageView(back);
        backView.setFitWidth(scoreBoardScene.getWidth() / 15);
        backView.setPreserveRatio(true);
        root.getChildren().addAll(backView);
        backView.setOpacity(0.5);
        backView.setOnMouseEntered(event -> backView.setOpacity(0.9));
        backView.setOnMouseExited(event -> backView.setOpacity(0.5));

        try {
            setScoreBoard(scoreBoardScene, root, authCode, backView, account);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return root;
    }

    private void setScoreBoard(Scene scene, Pane root, String authCode, ImageView backView, Account account) throws InterruptedException, FileNotFoundException {

        Image online = new Image(new FileInputStream("src/view/sources/scoreBoard/icons/online.png"));
        Image offline = new Image(new FileInputStream("src/view/sources/scoreBoard/icons/offline.png"));
        final LongProperty lastUpdate = new SimpleLongProperty();
        VBox scoreBoard = new VBox();
        root.getChildren().addAll(scoreBoard);
        final long minUpdateInterval = 100;
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                scoreBoard.getChildren().clear();
                if (now - lastUpdate.get() > minUpdateInterval) {
                    ScoreBoardCommand scoreBoardCommand = new ScoreBoardCommand(authCode, true, false, false);
                    Client.getInstance().sendData(scoreBoardCommand);
                    synchronized (Client.getInstance().getLock()) {
                        if (Client.getInstance().getScoreBoardCommand().getAuthCode().equals("")) {
                            System.out.println("scoreboard waiting");
                            try {
                                Client.getInstance().getLock().wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        ScoreBoardCommand answer = Client.getInstance().getScoreBoardCommand();
                        Client.getInstance().setScoreBoardCommand(new ScoreBoardCommand("", false, false, false));
                        names = answer.getSortedUsers();
                        onlineStatus = answer.getOnlineStatusOfUsers();
                        numOfWins = answer.getNumberOfWinsOfUsers();
                    }

                    System.out.println(scene == null);
                    scoreBoard.setSpacing(scene.getHeight() / 20);
                    HBox[] row = new HBox[10];
                    for (int i = 0; i < Math.min(10, names.size()); i++) {
                        row[i] = new HBox();
                        ImageView stats = new ImageView(offline);
                        if (onlineStatus.get(i).booleanValue())
                            stats = new ImageView(online);
                        stats.setFitHeight(scene.getHeight() / 35);
                        stats.setPreserveRatio(true);
                        Label label = new Label((i + 1) + ". " + names.get(i));
                        label.setFont(Font.font(40));
                        Label numberOfWins = new Label("            victories :" + numOfWins.get(i));
                        numberOfWins.setFont(Font.font(20));
                        numberOfWins.setTextFill(Color.rgb(20, 20, 20));
                        label.setTextFill(Color.rgb(100, 100, 100));
                        row[i].setAlignment(Pos.CENTER);
                        row[i].getChildren().addAll(stats, label, numberOfWins);
                        scoreBoard.getChildren().addAll(row[i]);
                    }

                    scoreBoard.layoutXProperty().bind(root.widthProperty().subtract(scoreBoard.widthProperty()).divide(2));
                    scoreBoard.layoutYProperty().bind(root.heightProperty().subtract(scoreBoard.heightProperty()).divide(2));
                    lastUpdate.set(now);
                }
            }
        };
        timer.start();
        backView.setOnMouseClicked(event -> {
            try {
                timer.stop();
                Main.setMainMenuFX(account);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
