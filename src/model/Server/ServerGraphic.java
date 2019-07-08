package model.Server;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Account;
import model.Card;
import model.MyConstants;
import model.Shop;
import view.AddCardFX;
import view.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        setPane(scene,primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setPane(Scene scene, Stage primaryStage) {
        Pane pane = (Pane) scene.getRoot();
        VBox settingMenu = new VBox();
        pane.getChildren().addAll(settingMenu);
        settingMenu.setAlignment(Pos.CENTER);
        settingMenu.relocate(0, scene.getHeight() / 10);
        settingMenu.setSpacing(scene.getHeight() / 20);
        settingMenu.layoutXProperty().bind(pane.widthProperty().subtract(settingMenu.widthProperty()).divide(2));
        Label onlineClients = new Label("Online Clients");
        onlineClients.setOnMouseEntered(event -> onlineClients.setUnderline(true));
        onlineClients.setOnMouseExited(event -> onlineClients.setUnderline(false));
        onlineClients.setOnMouseClicked(event -> showOnlineClients(scene, pane));

        Label shop = new Label("Shop");
        shop.setOnMouseEntered(event -> shop.setUnderline(true));
        shop.setOnMouseExited(event -> shop.setUnderline(false));
        shop.setOnMouseClicked(event -> showShop(scene, pane));

        Label addCard = new Label("Add Card");
        addCard.setOnMouseEntered(event -> addCard.setUnderline(true));
        addCard.setOnMouseExited(event -> addCard.setUnderline(false));
        addCard.setOnMouseClicked(event -> {
            try {
                showAddCard(scene, pane,primaryStage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        settingMenu.getChildren().addAll(onlineClients, shop, addCard);
    }

    private void showAddCard(Scene scene, Pane root, Stage primaryStage) throws FileNotFoundException {
        Pane addCardPane = new AddCardFX().start(primaryStage,new Account("server","1234"));

        backSetting(scene,addCardPane,root);
        scene.setRoot(addCardPane);
    }

    private void backSetting(Scene scene, Pane root, Pane mainRoot){
        Image back = null;
        try {
            back = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_back_corner.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView backView = new ImageView(back);
        backView.setFitWidth(scene.getWidth() / 15);
        backView.setPreserveRatio(true);
        root.getChildren().addAll(backView);
        backView.setOpacity(0.5);
        backView.setOnMouseEntered(event -> backView.setOpacity(0.9));
        backView.setOnMouseExited(event -> backView.setOpacity(0.5));
        backView.setOnMouseClicked(event -> {
            scene.setRoot(mainRoot);
        });
    }



    private void showShop(Scene scene, Pane root) {
        Pane shopPane = new Pane();
        shopPane.setPrefWidth(scene.getWidth());
        shopPane.setPrefHeight(scene.getHeight());
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(50, 100, 150),
                new CornerRadii(0), new javafx.geometry.Insets(0, 0, 0, 0));
        shopPane.setBackground(new Background(background_fill));
        root.getChildren().addAll(shopPane);


        final LongProperty lastUpdate = new SimpleLongProperty();

        final long minUpdateInterval = 200;
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (now - lastUpdate.get() > minUpdateInterval) {
                    shopPane.getChildren().clear();
                    HashMap<String, Integer> numberOfCards = Shop.getNumbers();
                    int count = 0;
                    for (String s : numberOfCards.keySet()) {
                        count++;
                        double x =scene.getWidth()/4;
                        double y = 0;
                        if(count <= 45)
                            y =( scene.getHeight()/50)*count;
                        else
                        {
                            x = scene.getWidth()*2.5/4;
                            y =( scene.getHeight()/50)*(count - 45);
                        }
                        Text cardStats = new Text(count+". "+s+" : "+numberOfCards.get(s).intValue());
                        cardStats.relocate(x,y);
                        shopPane.getChildren().addAll(cardStats);
                    }
                    lastUpdate.set(now);
                }
            }
        };
        timer.start();

        shopPane.setOnMouseClicked(event1 -> {
                    timer.stop();
                    root.getChildren().remove(shopPane);
                }
        );

    }

    public void showOnlineClients(Scene scene, Pane root) {
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

        final LongProperty lastUpdate = new SimpleLongProperty();

        final long minUpdateInterval = 100;
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (now - lastUpdate.get() > minUpdateInterval) {
                    HashMap<String, Account> onlineClients = Server.getOnlineAccounts();
                    accountsVBox.getChildren().clear();
                    for (String s : onlineClients.keySet()) {

                        Label label1 = new Label(onlineClients.get(s).getUserName());
                        label1.setFont(Font.font(30));
                        label1.setTextFill(Color.WHITE);
                        accountsVBox.getChildren().add(label1);
                        label1.setAlignment(Pos.CENTER);

                    }
                    lastUpdate.set(now);
                }
            }
        };

        timer.start();

        onlineAccountPane.setOnMouseClicked(event1 -> {
                    timer.stop();
                    root.getChildren().remove(onlineAccountPane);
                }
        );
    }
}
