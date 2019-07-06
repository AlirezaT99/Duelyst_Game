package model.Server;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Account;
import model.Card;
import model.MyConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerGraphic extends Application {

    public static void main(String[] args) {
        try {
            Server.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }

    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Pane(), MyConstants.SERVER_WINDOW_WIDTH, MyConstants.SERVER_WINDOW_HEIGHT);
        setPane(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setPane(Scene scene) {
        Pane pane = (Pane) scene.getRoot();
        VBox settingMenu = new VBox();
        pane.getChildren().addAll(settingMenu);
        settingMenu.setAlignment(Pos.CENTER);
        settingMenu.relocate(0, scene.getHeight() / 10);
        settingMenu.layoutXProperty().bind(pane.widthProperty().subtract(settingMenu.widthProperty()).divide(2));
        Label onlineClients = new Label("Online Clients");
        onlineClients.setOnMouseEntered(event -> onlineClients.setUnderline(true));
        onlineClients.setOnMouseExited(event -> onlineClients.setUnderline(false));
        onlineClients.setOnMouseClicked(event -> showOnlineClients(scene, pane));
        settingMenu.getChildren().addAll(onlineClients);
    }

    public void showOnlineClients(Scene scene, Pane root) {
        HashMap<String, Account> onlineClients = Server.getOnlineAccounts();
        Pane onlineAccountPane = new Pane();
        onlineAccountPane.setPrefWidth(scene.getWidth());
        onlineAccountPane.setPrefHeight(scene.getHeight());
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(20, 160, 150),
                new CornerRadii(0), new javafx.geometry.Insets(0, 0, 0, 0));
        onlineAccountPane.setBackground(new Background(background_fill));

        root.getChildren().addAll(onlineAccountPane);
        VBox accountsVBox = new VBox();
        accountsVBox.setAlignment(Pos.CENTER);
        onlineAccountPane.getChildren().add(accountsVBox);
        accountsVBox.setSpacing(scene.getHeight() / 25);
        accountsVBox.layoutXProperty().bind(root.widthProperty().subtract(accountsVBox.widthProperty()).divide(2));
        accountsVBox.layoutYProperty().bind(root.heightProperty().subtract(accountsVBox.heightProperty()).divide(2));
//        Thread thread = new Thread(() -> {
//            while (true) {
//
//            }
//        });
//        thread.start();
        for (String s : onlineClients.keySet()) {
            Label label1 = new Label(onlineClients.get(s).getUserName());
            label1.setFont(Font.font(30));
            label1.setTextFill(Color.WHITE);
            accountsVBox.getChildren().add(label1);
            label1.setAlignment(Pos.CENTER);
        }
        onlineAccountPane.setOnMouseClicked(event1 -> {
                   // thread.stop();
                    root.getChildren().remove(onlineAccountPane);
                }
        );
    }


}
