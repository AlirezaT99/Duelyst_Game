package view;


import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Account;
import model.Match;
import model.MovableCard;
import model.Player;
import presenter.BattleMenuProcess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class BattleFX {
    private static double screenWidth;
    private static double screenHeight;
    private static Rectangle[][] rectangles = new Rectangle[7][11];
    private static Object draggedFromNode = new Object();
    private static int objectInHandIndex;
    private HBox bottomRow = new HBox();

    Pane start(Match match, boolean isStoryMode, Stage stage, Account account) throws FileNotFoundException {
        BattleMenu.battleSetup(match);
        Pane root = new Pane();
        setScreenVariables(stage);
        setBackGround(match, isStoryMode, root);
        setGeneralIcons(account, match, root, new Scene(new Group(), screenWidth, screenHeight));
        root.getChildren().addAll(setTable(new Pane(), stage.getScene(), match.getPlayer1(), root, match));
//        root.setOnMouseClicked(event -> {
//            try {
//                Main.setBattleMenuFX(account);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        });
        return root;
    }

    //create table graphics


    private Pane setTable(Pane group, Scene scene, Player player, Pane mainPane, Match match) {
        createTableRectangles(group, scene, player, mainPane, match);
        return group;
    }

    private String deleteWhiteSpaces(String string) {
        String result = string.replaceAll(" ", "");
        return result.trim();
    }

    private void setGeneralIcons(Account account, Match match, Pane root, Scene scene) throws FileNotFoundException {
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
        HBox secondPlayer = new HBox(secondPlayerManas, secondPlayerImageView);
        secondPlayerManas.setAlignment(Pos.CENTER);

        HBox iconsRow = new HBox(firstPlayer, secondPlayer);
        iconsRow.setSpacing(scene.getWidth() / 4.5);
        iconsRow.layoutXProperty().bind(root.widthProperty().subtract(iconsRow.widthProperty()).divide(2));
        iconsRow.setLayoutY(-1 * (scene.getHeight() / 20));
        HBox bottomRow = drawHand(match.getPlayer1(), root, scene);
        StackPane nextCard = getNextStackPane(scene, match.getPlayer1());
        root.getChildren().addAll(iconsRow, bottomRow, nextCard, getBootomRight(account, scene, match.getPlayer1(), match));
        bottomRow.setAlignment(Pos.BOTTOM_CENTER);
        bottomRow.layoutXProperty().bind(root.widthProperty().subtract(bottomRow.widthProperty()).divide(2));

        bottomRow.setPadding(new Insets(scene.getHeight() * 3 / 4, 0, 0, 0));

        firstPlayerImageView.setOnMouseEntered(event -> scaleIcon(firstPlayerImageView, 1, 1.1));
        firstPlayerImageView.setOnMouseExited(event -> scaleIcon(firstPlayerImageView, 1.1, 1));

        secondPlayerImageView.setOnMouseEntered(event -> scaleIcon(secondPlayerImageView, 1, 1.1));
        secondPlayerImageView.setOnMouseExited(event -> scaleIcon(secondPlayerImageView, 1.1, 1));
        // this.bottomRow = bottomRow;

    }

    private StackPane getNextStackPane(Scene scene, Player player) throws FileNotFoundException {
        StackPane nextCard = new StackPane();
        ImageView nextBackGround = new ImageView(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/next/next_background.png")));
        nextBackGround.setFitHeight(scene.getHeight() / 3);
        nextBackGround.setPreserveRatio(true);
        ImageView nextBorderShine = new ImageView(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/next/next_outer_ring_shine.png")));
        nextBorderShine.setFitHeight(scene.getHeight() / 3);
        nextBorderShine.setPreserveRatio(true);
        Text nextLabel = new Text("Next : " + player.getDeck().getNextCard().getName());
        nextLabel.setWrappingWidth(nextBackGround.getFitWidth() * 3 / 4);
        nextLabel.setFill(Color.WHITE);

        nextLabel.setFont(new Font(20));
        nextCard.setOnMouseEntered(event -> {
            try {
                nextLabel.setEffect(new Glow(1));
                nextBorderShine.setImage(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/next/next_outer_ring_smoke.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        nextCard.setOnMouseExited(event -> {
            try {
                nextLabel.setEffect(new Glow(0));
                nextBorderShine.setImage(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/next/next_outer_ring_shine.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        nextCard.getChildren().addAll(nextBackGround, nextBorderShine, nextLabel);
        nextCard.relocate(0, scene.getHeight() * 2 / 3);


        return nextCard;
    }

    private VBox getBootomRight(Account account, Scene scene, Player player, Match match) throws FileNotFoundException {
        VBox bottomRight = new VBox();
        Image endTurnInitialImage = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/bottomright/button_end_turn_mine.png"));
        Image endTurnGlowImage = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/bottomright/button_end_turn_mine_glow.png"));

        Image endTurnEnemyInitialImage = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/bottomright/button_end_turn_enemy.png"));
        Image endTurnEnemyGlowImage = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/bottomright/button_end_turn_enemy.png"));

        Image menuInitial = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/bottomright/button_primary_left.png"));
        Image menuGlow = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/bottomright/button_primary_left_glow.png"));

        Image graveYardInitial = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/bottomright/button_primary_right.png"));
        Image graveYardGlow = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/bottomright/button_primary_right_glow.png"));


        ImageView endTurnImage = new ImageView(endTurnInitialImage);
        endTurnImage.setFitWidth(scene.getWidth() / 5);
        endTurnImage.setPreserveRatio(true);

        Label endTurnLabel = new Label("END TURN");
        endTurnLabel.setTextFill(Color.WHITE);
        endTurnLabel.setFont(new Font(25));
        StackPane endTurn = new StackPane(endTurnImage, endTurnLabel);
        endTurn.setOnMouseEntered(event -> {
            if (match.currentTurnPlayer().getUserName().equals(player.getUserName())) {
                endTurnImage.setImage(endTurnGlowImage);
            }
            endTurnLabel.setEffect(new Glow(1));
        });
        endTurn.setOnMouseExited(event -> {
            if (match.currentTurnPlayer().getUserName().equals(player.getUserName())) {
                endTurnImage.setImage(endTurnInitialImage);
            }
            endTurnLabel.setEffect(new Glow(0));
            endTurnImage.setImage(endTurnInitialImage);

        });
        endTurn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (match.currentTurnPlayer().getUserName().equals(player.getUserName())) {
                        new BattleMenuProcess().endTurn();
                        ((ImageView) endTurn.getChildren().get(0)).setImage(endTurnEnemyInitialImage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        HBox lowerPart = new HBox();
        StackPane menu = new StackPane();
        StackPane graveYard = new StackPane();

        Label menuLabel = new Label("MENU");
        menuLabel.setTextFill(Color.WHITE);
        menuLabel.setFont(new Font(14));

        Label graveYardLabel = new Label("GRAVE YARD");
        graveYardLabel.setTextFill(Color.WHITE);
        graveYardLabel.setFont(new Font(14));

        ImageView menuView = new ImageView(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/bottomright/button_primary_left.png")));
        menuView.setFitWidth(scene.getWidth() / 10);
        menuView.setPreserveRatio(true);
        ImageView graveYardView = new ImageView(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/bottomright/button_primary_right.png")));
        graveYardView.setFitWidth(scene.getWidth() / 10);
        graveYardView.setPreserveRatio(true);

        menu.setOnMouseEntered(event -> menuView.setImage(menuGlow));
        menu.setOnMouseExited(event -> menuView.setImage(menuInitial));
        menu.setOnMouseClicked(event -> {
            try {
                Main.setMainMenuFX(account);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        graveYard.setOnMouseEntered(event -> graveYardView.setImage(graveYardGlow));
        graveYard.setOnMouseExited(event -> graveYardView.setImage(graveYardInitial));

        menu.getChildren().addAll(menuView, menuLabel);
        graveYard.getChildren().addAll(graveYardView, graveYardLabel);
        lowerPart.getChildren().addAll(menu, graveYard);
        bottomRight.getChildren().addAll(endTurn, lowerPart);
        bottomRight.relocate(scene.getWidth() * 4 / 5, scene.getHeight() * 3 / 4);

        return bottomRight;
    }

    private HBox drawHand(Player player, Pane root, Scene scene) throws FileNotFoundException {
        bottomRow.getChildren().clear();
        Image cardHolder = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/game-hand-card-container-hover.png"));
        bottomRow.setSpacing(scene.getWidth() / 60);
        for (int i = 0; i < 5; i++) {
            VBox cardContainer = new VBox();
            ImageView cardHolderView = new ImageView(cardHolder);
            cardHolderView.setFitHeight(scene.getWidth() / 10);
            cardHolderView.setPreserveRatio(true);
            Image mana = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/mana/mana_active.png"));
            ImageView manaView = new ImageView(mana);
            if ((player.getHand().getCards().get(i) != null && (player.getMana() < player.getHand().getCards().get(i).getManaCost())) || player.getHand().getCards().get(i) == null) {
                cardHolderView.setImage(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/game-hand-card-container.png")));
                ColorAdjust grayscale = new ColorAdjust();
                grayscale.setBrightness(-0.5);
                manaView.setEffect(grayscale);
            }
            StackPane imageStackPane = new StackPane();
            javafx.scene.control.Label cardName = new Label();
            if (player.getHand().getCards().get(i) != null)
                cardName = new Label(player.getHand().getCards().get(i).getName());
            cardName.setTextFill(Color.WHITE);
            imageStackPane.getChildren().addAll(cardHolderView, cardName);
            manaView.setFitHeight(scene.getHeight() / 20);
            manaView.setPreserveRatio(true);
            StackPane manaStackPane = new StackPane();
            manaStackPane.getChildren().addAll(manaView, new Text(player.getHand().getCards().get(i) != null ? player.getHand().getCards().get(i).getManaCost() + "" : ""));
            cardContainer.getChildren().addAll(imageStackPane, manaStackPane);
            cardContainer.setAlignment(Pos.BOTTOM_CENTER);
            cardContainer.setSpacing((-1) * (scene.getHeight()) / 30);
            if (!cardName.getText().equals("")) {
                final String descriptionString = player.getHand().getCards().get(i).getDescription();

                final String AP = player.getHand().getCards().get(i) instanceof MovableCard ? ((MovableCard) player.getHand().getCards().get(i)).getDamage() + "" : "";
                final String HP = player.getHand().getCards().get(i) instanceof MovableCard ? ((MovableCard) player.getHand().getCards().get(i)).getHealth() + "" : "";
                final int finalI = i;
                cardContainer.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (player.getHand().getCards().get(finalI) != null && player.getMana() >= player.getHand().getCards().get(finalI).getManaCost()) {
                            cardContainer.startFullDrag();
                            draggedFromNode = cardContainer;
                            objectInHandIndex = finalI;
                            //  System.out.println("fuck you");
                        }
                    }
                });
                cardContainer.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {

                            ImageView descriptionView = new ImageView(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/description/minion.png")));
                            descriptionView.setFitWidth(scene.getWidth() / 7);
                            descriptionView.setFitHeight(scene.getHeight() / 3);

                            Pane description = new Pane();
                            description.setPrefWidth(descriptionView.getFitWidth());
                            description.setPrefHeight(descriptionView.getFitHeight());

                            Text text = new Text(descriptionString);
                            System.out.println(descriptionString);
                            description.getChildren().addAll(descriptionView, text);

                            Bounds boundsInScene = cardContainer.localToScene(cardContainer.getBoundsInLocal());
                            descriptionView.relocate((boundsInScene.getMinX() + boundsInScene.getMaxX()) / 2 - (descriptionView.getFitWidth()) / 2, boundsInScene.getMinY() - descriptionView.getFitHeight());

                            Label APLabel = new Label(AP);
                            APLabel.relocate((boundsInScene.getMinX() + boundsInScene.getMaxX()) / 2 - (descriptionView.getFitWidth() / 3.4), boundsInScene.getMinY() - descriptionView.getFitHeight() / 2.35);
                            APLabel.setTextFill(Color.WHITE);

                            Label HPLabel = new Label(HP);
                            HPLabel.relocate((boundsInScene.getMinX() + boundsInScene.getMaxX()) / 2 + (descriptionView.getFitWidth() / 4), boundsInScene.getMinY() - descriptionView.getFitHeight() / 2.35);
                            HPLabel.setTextFill(Color.WHITE);

                            text.setWrappingWidth(descriptionView.getFitWidth() * 3 / 4);
                            text.relocate((boundsInScene.getMinX() + boundsInScene.getMaxX()) / 2 - (text.getWrappingWidth()) / 2, boundsInScene.getMinY() - descriptionView.getFitHeight() / 3.1);
                            text.setFill(Color.WHITE);
                            text.setFont(Font.font(10));

                            description.getChildren().addAll(APLabel, HPLabel);

                            fadeInPane(description);
                            root.getChildren().addAll(description);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

                cardContainer.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        root.getChildren().remove(root.getChildren().size() - 1);
                    }
                });
            }
            bottomRow.getChildren().add(cardContainer);
        }
        return bottomRow;
    }

    private void fadeInPane(Pane description) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(description);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setDuration(Duration.millis(100));
        fadeTransition.play();
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

    private void createTableRectangles(Pane group, Scene scene, Player player, Pane mainPane, Match match) {
        double ulx = scene.getWidth() / 5;
        double uly = scene.getHeight() / 4;
        double width = scene.getWidth() * 3 / 47;
        double margin = width / 20;
        double height = (scene.getHeight() / 2 - width / 5) / 5;
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 9; j++) {
                rectangles[i][j] = new Rectangle((width + margin) * (j - 1), (height + margin) * (i - 1), width, height);
                rectangles[i][j].setFill(Color.rgb(50, 50, 50));
                rectangles[i][j].setOpacity(0.3);
                // rectangles[i][j].setOpacity(0.6);
                final int finalJ = j;
                final int finalI = i;
                rectangles[i][j].setArcWidth(rectangles[i][j].getWidth() / 20);
                rectangles[i][j].setArcHeight(rectangles[i][j].getHeight() / 20);
                rectangles[i][j].setOnMouseEntered(event -> {
                    rectangles[finalI][finalJ].setFill(Color.WHITE);
                    rectangles[finalI][finalJ].setOpacity(0.2);
                });
                rectangles[i][j].setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangles[finalI][finalJ].startFullDrag();
                        draggedFromNode = rectangles[finalI][finalJ];
                    }
                });
                rectangles[i][j].setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
                    @Override
                    public void handle(MouseDragEvent event) {
                        System.out.println("actual map:" + finalI + " " + finalJ);
                        if (draggedFromNode != null && draggedFromNode instanceof VBox && BattleMenuProcess.isCoordinationValidToInsert(finalI, finalJ)) { //todo : ehtemalan shartaye bishatri mikhad
                            // group.getChildren().remove(draggedFromNode);
                            // ((StackPane) (((VBox) draggedFromNode).getChildren().get(1))).getChildren().remove(1);
                            String cardName = ((Label) ((StackPane) (((VBox) draggedFromNode).getChildren().get(0))).getChildren().get(1)).getText();
                            ((StackPane) (((VBox) draggedFromNode).getChildren().get(0))).getChildren().remove(1);
                            player.getHand().findCardByName(cardName)
                                    .castCard(match.getTable().getCellByCoordination(finalI, finalJ));
                            player.getHand().removeCardFromHand(player.getHand().selectCard(objectInHandIndex));
                            //   ((VBox) draggedFromNode).getChildren().remove(0);
                            // match.getPlayer2().getHand().removeCardFromHand();
                            Text text = new Text(cardName);
                            text.setFont(Font.font(10));
                            text.setFill(Color.WHITE);
                            draggedFromNode = null;
                            try {
                                bottomRow = drawHand(player, mainPane, scene);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            text.relocate(rectangles[finalI][finalJ].getX(), rectangles[finalI][finalJ].getY());
                            group.getChildren().addAll(text);
                        }
                    }
                });
                rectangles[i][j].setOnMouseExited(event -> {
                    rectangles[finalI][finalJ].setFill(Color.rgb(50, 50, 50));
                    rectangles[finalI][finalJ].setOpacity(0.3);
                });

                group.setPrefWidth(scene.getWidth() * 3 / 5);
                group.setPrefHeight(scene.getHeight() / 2);
                group.relocate(ulx, uly);
                group.getChildren().addAll(rectangles[i][j]);
            }
        }
//        backgroundRectangle.setOnMouseMoved(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//
//            }
//        });

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

