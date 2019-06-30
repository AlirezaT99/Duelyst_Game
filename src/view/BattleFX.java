package view;


import javafx.animation.*;
import javafx.event.ActionEvent;
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
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import org.w3c.dom.css.Rect;
import presenter.BattleMenuProcess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;


public class BattleFX {
    private static double screenWidth;
    private static double screenHeight;
    private static Rectangle[][] rectangles = new Rectangle[7][11];
    private static Pane[][] gameMap = new Pane[7][11];
    private static Object draggedFromNode = new Object();

    private static int objectInHandIndex;
    private HBox bottomRow = new HBox();
    private HBox firstPlayer = new HBox();
    private HBox secondPlayer = new HBox();
    private Pane rectanglesPane = new Pane();

    Pane start(Match match, boolean isStoryMode, Stage stage, Account account) throws FileNotFoundException {
        BattleMenu.battleSetup(match);
        Pane root = new Pane();
        setScreenVariables(stage);
        setBackGround(match, isStoryMode, root);
        setGeneralIcons(account, match, root, new Scene(new Group(), screenWidth, screenHeight));
        root.getChildren().addAll(setTable(rectanglesPane, stage.getScene(), match.getPlayer1(), root, match));
        updateSoldiers(match, new Scene(new Group(), screenWidth, screenHeight), rectanglesPane);
        return root;
    }

    //create table graphics
    private static void updateSoldiers(Match match, Scene scene, Pane root) throws FileNotFoundException {
        for (int i = 1; i <= 5; i++)
            for (int j = 1; j <= 9; j++) {
                setGif(match.getTable().getCellByCoordination(i, j).getMovableCard(), i, j, scene, root, match, "idle");
            }

    }

    private static void setGif(Card card, int x, int y, Scene scene, Pane root, Match match, String type) throws FileNotFoundException {
        Animation animation = null;
        if (card != null) {
            animation = GraphicalCommonUsages.getGif(card.getName(), type);
            animation.getView().setFitWidth(scene.getWidth() / 18.8);
            animation.getView().setPreserveRatio(true);
            // animation.getView().setFitHeight(scene.getHeight()/10);
            animation.setCycleCount(Integer.MAX_VALUE);
            animation.play();
            //gameMap[x][y].getChildren().clear();
            for (int i = gameMap[x][y].getChildren().size() - 1; i > 0; i--) {
                gameMap[x][y].getChildren().remove(i);
            }
            // rectanglesPane.
            gameMap[x][y].getChildren().add(animation.getView());
            gameMap[x][y].setPrefHeight((rectangles[x][y].getHeight()));
            gameMap[x][y].setPrefWidth((rectangles[x][y].getWidth()));

            gameMap[x][y].getChildren().get(0).setLayoutX(0);
            gameMap[x][y].getChildren().get(0).setLayoutY(0);
            StackPane hp = new StackPane();
            ImageView hpView = new ImageView(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/hp.png")));
            Label hpLabel = new Label();
            hpLabel.setTextFill(Color.WHITE);
            hpView.setFitHeight(gameMap[x][y].getPrefHeight() / 3);
            hpView.setPreserveRatio(true);
            hpView.setOpacity(0.5);
            hp.getChildren().addAll(hpView, hpLabel);

            StackPane ap = new StackPane();
            ImageView apView = new ImageView(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/Arena/mutual/ap.png")));
            Label apLabel = new Label();
            apLabel.setTextFill(Color.WHITE);
            apView.setFitHeight(gameMap[x][y].getPrefHeight() / 3);
            apView.setPreserveRatio(true);
            apView.setOpacity(0.5);
            ap.getChildren().addAll(apView, apLabel);

            if (card instanceof MovableCard) {
                hpLabel.setText(((MovableCard) card).getHealth() + "");
                apLabel.setText(((MovableCard) card).getDamage() + "");
            }

            Label cardName = new Label(card.getName());
            cardName.setVisible(false);
            gameMap[x][y].getChildren().addAll(ap, hp, cardName);
            ap.relocate(0, gameMap[x][y].getPrefHeight() * 2 / 3);
            hp.relocate(gameMap[x][y].getPrefWidth() / 2, gameMap[x][y].getPrefHeight() * 2 / 3);

            if (card.getPlayer().equals(match.getPlayer2()))
                rotateImageView(animation.getView());
        } else {
            // Rectangle rectangle = (Rectangle) gameMap[x][y].getChildren().get(0);
            for (int i = gameMap[x][y].getChildren().size() - 1; i > 0; i--) {
                gameMap[x][y].getChildren().remove(i);
            }
        }
        // root.getChildren().add(animation.getView());

//
//        EventHandler<MouseEvent> handler = MouseEvent::consume;
//        animation.getView().addEventFilter(MouseEvent.ANY, handler);
    }

    private static void rotateImageView(ImageView imageView) {
        imageView.setTranslateZ(imageView.getBoundsInLocal().getWidth() / 2.0);
        imageView.setRotationAxis(Rotate.Y_AXIS);
        imageView.setRotate(180);
    }

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

        HBox manaFirstPlayer = drawMana(match.getPlayer1(), match, root, scene, 1);
        firstPlayer = new HBox(firstPlayerImageView, manaFirstPlayer);
        manaFirstPlayer.setAlignment(Pos.CENTER);

        HBox manaSecondPlayer = drawMana(match.getPlayer2(), match, root, scene, 2);
        secondPlayer = new HBox(manaSecondPlayer, secondPlayerImageView);
        manaSecondPlayer.setAlignment(Pos.CENTER);

        HBox iconsRow = new HBox(firstPlayer, secondPlayer);
        iconsRow.setSpacing(scene.getWidth() / 4.5);
        iconsRow.layoutXProperty().bind(root.widthProperty().subtract(iconsRow.widthProperty()).divide(2));
        iconsRow.setLayoutY(-1 * (scene.getHeight() / 20));
        HBox bottomRow = drawHand(match.getPlayer1(), root, scene);
        StackPane nextCard = getNextStackPane(scene, match.getPlayer1());
        root.getChildren().addAll(iconsRow, bottomRow, nextCard, getBootomRight(account, scene, match.getPlayer1(), match, root));
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

    private VBox getBootomRight(Account account, Scene scene, Player player, Match match, Pane root) throws FileNotFoundException {
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
                        System.out.println("fuck " + player.getMana());
                        updateMana(match, root, scene);
                        bottomRow = drawHand(player, root, scene);
                        ((ImageView) endTurn.getChildren().get(0)).setImage(endTurnEnemyInitialImage);
                        updateSoldiers(match, scene, rectanglesPane);
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

    private void updateMana(Match match, Pane root, Scene scene) throws FileNotFoundException {
        System.out.println("player 2 :" + match.getPlayer2().getMana());
        System.out.println("player 1: " + match.getPlayer1().getMana());
        firstPlayer.getChildren().remove(1);
        HBox manafirstPlayer = drawMana(match.getPlayer1(), match, root, scene, 1);
        firstPlayer.getChildren().add(1, manafirstPlayer);
        manafirstPlayer.setAlignment(Pos.CENTER);
        HBox manaSecondPlayer = drawMana(match.getPlayer2(), match, root, scene, 2);
        secondPlayer.getChildren().remove(0);
        secondPlayer.getChildren().add(0, manaSecondPlayer);
        manaSecondPlayer.setAlignment(Pos.CENTER);
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
            Label cardName = player.getHand().getCards().get(i) != null ? new Label(player.getHand().getCards().get(i).getName()) : new Label("");
            if (cardName != null)
                cardName.setVisible(false);
            Animation animation = null;
            if (player.getHand().getCards().get(i) != null) {
                animation = GraphicalCommonUsages.getGif(player.getHand().getCards().get(i).getName(), "idle");
                animation.getView().setFitWidth(scene.getWidth() / 20);
                animation.getView().setFitHeight(scene.getHeight() / 10);
                animation.setCycleCount(Integer.MAX_VALUE);
                animation.play();
            }

            imageStackPane.getChildren().addAll(cardHolderView, animation != null ? animation.getView() : new Label(""));
            manaView.setFitHeight(scene.getHeight() / 20);
            manaView.setPreserveRatio(true);
            StackPane manaStackPane = new StackPane();
            manaStackPane.getChildren().addAll(manaView, new Text(player.getHand().getCards().get(i) != null ? player.getHand().getCards().get(i).getManaCost() + "" : ""));
            cardContainer.getChildren().addAll(imageStackPane, manaStackPane, cardName);
            cardContainer.setAlignment(Pos.BOTTOM_CENTER);
            cardContainer.setSpacing((-1) * (scene.getHeight()) / 30);
            if (animation != null) {
                final String descriptionString = player.getHand().getCards().get(i).getDescription();

                final String AP = player.getHand().getCards().get(i) instanceof MovableCard ? ((MovableCard) player.getHand().getCards().get(i)).getDamage() + "" : "";
                final String HP = player.getHand().getCards().get(i) instanceof MovableCard ? ((MovableCard) player.getHand().getCards().get(i)).getHealth() + "" : "";
                final String name = player.getHand().getCards().get(i).getName();
                final int finalI = i;
                cardContainer.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (player.getHand().getCards().get(finalI) != null && player.getMana() >= player.getHand().getCards().get(finalI).getManaCost()) {
                            cardContainer.startFullDrag();
                            draggedFromNode = cardContainer;
                            objectInHandIndex = finalI;
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

                            Label nameLabel = new Label(name);
                            nameLabel.setAlignment(Pos.CENTER);

//                            nameLabel.layoutXProperty().bind(description.widthProperty().subtract(nameLabel.widthProperty()).divide(2));
//                            nameLabel.layoutYProperty().bind(description.heightProperty().subtract(nameLabel.heightProperty()).divide(2));

                            nameLabel.relocate(APLabel.getLayoutX(), boundsInScene.getMinY() - descriptionView.getFitHeight() / 1.5);
                            nameLabel.setTextFill(Color.WHITE);

                            text.setWrappingWidth(descriptionView.getFitWidth() * 3 / 4);
                            text.relocate((boundsInScene.getMinX() + boundsInScene.getMaxX()) / 2 - (text.getWrappingWidth()) / 2, boundsInScene.getMinY() - descriptionView.getFitHeight() / 3.1);
                            text.setFill(Color.WHITE);
                            text.setFont(Font.font(10));

                            description.getChildren().addAll(APLabel, HPLabel, nameLabel);

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
                Rectangle rectangle = new Rectangle((width + margin) * (j - 1), (height + margin) * (i - 1), width, height);
                rectangle.setVisible(false);
                // rectangles[i][j].setOpacity(0.6);
                final int finalJ = j;
                final int finalI = i;
                rectangles[i][j].setArcWidth(rectangles[i][j].getWidth() / 20);
                rectangles[i][j].setArcHeight(rectangles[i][j].getHeight() / 20);
                gameMap[i][j] = new Pane();
                gameMap[i][j].getChildren().add(rectangle);
                gameMap[i][j].setPrefWidth(rectangles[i][j].getWidth());
                gameMap[i][j].setPrefHeight(rectangles[i][j].getHeight());
                gameMap[i][j].relocate((width + margin) * (j - 1), (height + margin) * (i - 1));
                gameMap[i][j].setOnMouseEntered(event -> {
                    rectangles[finalI][finalJ].setFill(Color.WHITE);
                    rectangles[finalI][finalJ].setOpacity(0.2);
                });
                gameMap[i][j].setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangles[finalI][finalJ].startFullDrag();
                        draggedFromNode = gameMap[finalI][finalJ];
                    }
                });
                gameMap[i][j].setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
                    @Override
                    public void handle(MouseDragEvent event) {
                        // System.out.println("actual map:" + finalI + " " + finalJ);
                        if (draggedFromNode != null && draggedFromNode instanceof VBox && BattleMenuProcess.isCoordinationValidToInsert(finalI, finalJ)) { //todo : ehtemalan shartaye bishatri mikhad
                            // group.getChildren().remove(draggedFromNode);
                            // ((StackPane) (((VBox) draggedFromNode).getChildren().get(1))).getChildren().remove(1);
                            String cardName = ((Label) (((VBox) draggedFromNode).getChildren().get(2))).getText();
                            if (player.getHand().getCards().get(objectInHandIndex) instanceof Spell) {
                                System.out.println("fuck sepehr");

                                mainPane.getChildren().add(new Rectangle(scene.getWidth(), scene.getHeight(), Color.ORANGE));
                                long time = System.currentTimeMillis();
                                FadeTransition fadeTransition = new FadeTransition();
                                fadeTransition.setDuration(Duration.millis(1000));
                                fadeTransition.setFromValue(1);
                                fadeTransition.setToValue(0);
                                fadeTransition.setNode(mainPane.getChildren().get(mainPane.getChildren().size() - 1));
                                fadeTransition.play();
                                fadeTransition.setOnFinished(event1 -> mainPane.getChildren().remove(mainPane.getChildren().size() - 1));
                                ((Rectangle) gameMap[finalI][finalJ].getChildren().get(0)).setFill(Color.GOLD);
                                //while (System.currentTimeMillis() - time < 2000){}
                                //rectangles[finalI][finalJ].setFill(Color.WHITE);
                                // while (System.currentTimeMillis() - time < ){}
//                                Pane tempPane = new Pane();
//                                tempPane.getChildren()
//                                tempPane.setBackground(new Background(new BackgroundFill(Color.ORANGE,CornerRadii.EMPTY,new Insets(0,0,0,0))));
//                                Main.setSceneForAPeriodOfTime(tempPane,1000);

                            }
                            //((StackPane) (((VBox) draggedFromNode).getChildren().get(0))).getChildren().remove(1);
                            player.getHand().findCardByName(cardName)
                                    .castCard(match.getTable().getCellByCoordination(finalI, finalJ));
                            player.getHand().removeCardFromHand(player.getHand().selectCard(objectInHandIndex));
                            draggedFromNode = null;
                            try {
                                bottomRow = drawHand(player, mainPane, scene);
                                updateMana(match, mainPane, scene);
                                updateSoldiers(match, scene, group);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
//
                        }
                        if (draggedFromNode != null && draggedFromNode instanceof Pane &&
                                !(draggedFromNode instanceof VBox) && ((Pane) draggedFromNode).getChildren().size() >= 3) {
                            Coordination coordination = getPaneFromMap((Pane) draggedFromNode);
                            if (gameMap[finalI][finalJ].getChildren().size() == 1) {
                                if (match.getTable().getCell(coordination.getX(), coordination.getY()).getMovableCard().isMoveValid(match.getTable().getCellByCoordination(finalI, finalJ)) == 0 &&
                                        match.getTable().getCell(coordination.getX(), coordination.getY()).getMovableCard().getPlayer().equals(match.currentTurnPlayer())) {
                                    moveProcess(coordination, match, finalI, finalJ, scene, width, margin, height, group, rectanglesPane);
//                                    try {
//                                        setGeneralIcons(player.getAccount(),match,(Pane)scene.getRoot(),scene);
//                                    } catch (FileNotFoundException e) {
//                                        e.printStackTrace();
//                                    }
                                }
                            } else {
                                attackProcess(coordination, match, finalI, finalJ, scene, width, margin, height, group, rectanglesPane);

                            }
                        }
                    }
                });
                gameMap[i][j].setOnMouseExited(event -> {
                    rectangles[finalI][finalJ].setFill(Color.rgb(50, 50, 50));
                    rectangles[finalI][finalJ].setOpacity(0.3);
                });

                group.setPrefWidth(scene.getWidth() * 3 / 5);
                group.setPrefHeight(scene.getHeight() / 2);
                group.relocate(ulx, uly);
                group.getChildren().addAll(rectangles[i][j], gameMap[i][j]);
            }
        }

    }
    private static void attackProcess(Coordination coordination, Match match, int finalI, int finalJ, Scene scene, double width, double margin, double height, Pane group, Pane rectanglesPane){
        int result = match.getTable().getCell(coordination.getX(), coordination.getY()).getMovableCard().attack(match.getTable().getCell(finalI, finalJ).getMovableCard());
        if(result == 0){
            Animation attackAnimation = GraphicalCommonUsages.getGif(((Label) ((Pane) draggedFromNode).getChildren().get(((Pane) draggedFromNode).getChildren().size() - 1)).getText(), "attack");
            ImageView imageView = attackAnimation.getView();
            ((Pane) draggedFromNode).getChildren().remove(1);
            ((Pane) draggedFromNode).getChildren().add(1,imageView);
            imageView.setFitWidth(scene.getWidth()/18.8);
            imageView.setPreserveRatio(true);
            if(match.currentTurnPlayer().equals(match.getPlayer2()))
                rotateImageView(imageView);
            attackAnimation.setCycleCount(1);
            attackAnimation.play();
            movableCardAttackSFX(((Label) ((Pane) draggedFromNode).getChildren().get(((Pane) draggedFromNode).getChildren().size() - 1)).getText(),true);
            attackAnimation.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Animation counterAttackAnimation = GraphicalCommonUsages.getGif(((Label)gameMap[finalI][finalJ].getChildren().get(gameMap[finalI][finalJ].getChildren().size()-1)).getText(), "attack");
                        ImageView imageView = counterAttackAnimation.getView();
                        gameMap[finalI][finalJ].getChildren().remove(1);
                        gameMap[finalI][finalJ].getChildren().add(1,imageView);
                        imageView.setFitWidth(scene.getWidth()/18.8);
                        imageView.setPreserveRatio(true);
                        if(match.currentTurnPlayer().equals(match.getPlayer1()))
                            rotateImageView(imageView);
                        counterAttackAnimation.setCycleCount(1);
                        counterAttackAnimation.play();
                        movableCardAttackSFX(((Label)gameMap[finalI][finalJ].getChildren().get(gameMap[finalI][finalJ].getChildren().size()-1)).getText(),false);
                        counterAttackAnimation.setOnFinished(event1 -> {
                            try {
                                updateSoldiers(match, scene, group);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private static void movableCardAttackSFX(String cardName, boolean attacker){
        AudioClip audioClip;
        if(Hero.getHeroByName(cardName) != null){
            if(cardName.toLowerCase().equals("afsaane") || cardName.toLowerCase().equals("simorgh"))
            {
                audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/Battle/music/sfx_f4_general_hit.m4a").toString());
                audioClip.setCycleCount(1);
                audioClip.play();
                return;
            }
            if(cardName.toLowerCase().equals("aarash") || cardName.toLowerCase().equals("rostam") || cardName.toLowerCase().equals("esfandiar") || cardName.toLowerCase().equals("kaave")){
                if(attacker)
                    audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/Battle/music/sfx_f1_general_hit.m4a").toString());
                else
                    audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/Battle/music/sfx_f1_general_attack_swing.m4a").toString());
                audioClip.setCycleCount(1);
                audioClip.play();
                return;
            }
            else
            {
                if(attacker)
                    audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/Battle/music/sfx_f5_general_hit.m4a").toString());
                else
                    audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/Battle/music/sfx_f5_general_attack_swing.m4a").toString());
                audioClip.setCycleCount(1);
                audioClip.play();
                return;
            }
        }
        else
        {
            Minion minion = Minion.getMinionByName(cardName);
            if (minion.isMelee())
            {
                if(attacker)
                    audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/Battle/music/sfx_f2melee_attack_impact_1.m4a").toString());
                else
                    audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/Battle/music/sfx_f2melee_attack_swing_2.m4a").toString());
                audioClip.setCycleCount(1);
                audioClip.play();
                return;
            }
            if(minion.isHybrid())
            {
                if(attacker)
                    audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/Battle/music/sfx_f2_celestialphantom_attack_impact.m4a").toString());
                else
                    audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/Battle/music/sfx_f2_celestialphantom_attack_swing.m4a").toString());
                audioClip.setCycleCount(1);
                audioClip.play();
                return;
            }
            else
            {
                if(attacker)
                    audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/Battle/music/sfx_f4_engulfingshadow_attack_impact.m4a").toString());
                else
                    audioClip = new javafx.scene.media.AudioClip(Main.class.getResource("sources/Battle/music/sfx_f4_engulfingshadow_attack_swing.m4a").toString());
                audioClip.setCycleCount(1);
                audioClip.play();
                return;
            }
        }
    }

    private static void moveProcess(Coordination coordination, Match match, int finalI, int finalJ, Scene scene, double width, double margin, double height, Pane group, Pane rectanglesPane) {
        match.getTable().getCell(coordination.getX(), coordination.getY()).getMovableCard().move(match.getTable().getCell(finalI, finalJ));
        Animation runAnimation = GraphicalCommonUsages.getGif(((Label) ((Pane) draggedFromNode).getChildren().get(((Pane) draggedFromNode).getChildren().size() - 1)).getText(), "run");
        ImageView movableCard = runAnimation.getView();
        movableCard.setFitWidth(scene.getWidth() / 18.8);
        movableCard.setPreserveRatio(true);
        StackPane ap = (StackPane) (((Pane) draggedFromNode).getChildren().get(2));
        StackPane hp = (StackPane) (((Pane) draggedFromNode).getChildren().get(3));
        ((Pane) draggedFromNode).getChildren().remove(1);
        ((Pane) draggedFromNode).getChildren().remove(ap);
        ((Pane) draggedFromNode).getChildren().remove(hp);

        rectanglesPane.getChildren().addAll(movableCard, ap, hp);

        movableCard.relocate((coordination.getY() - 1) * (width + margin), (coordination.getX() - 1) * (height + margin));
        hp.relocate((coordination.getY() - 1) * (width + margin) + rectangles[finalI][finalJ].getWidth() / 2, (coordination.getX() - 1) * (height + margin) + rectangles[finalI][finalJ].getHeight() * 2 / 3);
        ap.relocate((coordination.getY() - 1) * (width + margin), (coordination.getX() - 1) * (height + margin) + rectangles[finalI][finalJ].getHeight() * 2 / 3);

        KeyValue xValueAP = new KeyValue(ap.layoutXProperty(), rectangles[finalI][finalJ].getX());
        KeyValue yValueAP = new KeyValue(ap.layoutYProperty(), rectangles[finalI][finalJ].getY() + rectangles[finalI][finalJ].getHeight() * 2 / 3);
        KeyFrame keyFrameAP = new KeyFrame(Duration.millis(500), xValueAP, yValueAP);

        KeyValue xValueHP = new KeyValue(hp.layoutXProperty(), rectangles[finalI][finalJ].getX() + rectangles[finalI][finalJ].getWidth() / 2);
        KeyValue yValueHP = new KeyValue(hp.layoutYProperty(), rectangles[finalI][finalJ].getY() + rectangles[finalI][finalJ].getHeight() * 2 / 3);
        KeyFrame keyFrameHP = new KeyFrame(Duration.millis(500), xValueHP, yValueHP);
        System.out.println("final I " + finalI + " , finalJ: " + finalJ);
        KeyValue xValue = new KeyValue(movableCard.layoutXProperty(), rectangles[finalI][finalJ].getX());
        KeyValue yValue = new KeyValue(movableCard.layoutYProperty(), rectangles[finalI][finalJ].getY());

        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), xValue, yValue);
        Timeline timeline = new Timeline(keyFrame);
        Timeline timeLineAP = new Timeline(keyFrameAP);
        Timeline timeLineHP = new Timeline(keyFrameHP);


        movableCard.setFitWidth(scene.getWidth() / 18.8);
        if (coordination.getY() > finalJ)
            rotateImageView(movableCard);
        movableCard.setPreserveRatio(true);
        runAnimation.setCycleCount(100);
        runAnimation.play();
        timeline.play();
        timeLineAP.play();
        timeLineHP.play();
        timeline.setOnFinished(event1 -> {
            try {
                updateSoldiers(match, scene, group);
                rectanglesPane.getChildren().remove(movableCard);
                rectanglesPane.getChildren().remove(ap);
                rectanglesPane.getChildren().remove(hp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createGameMapPane(Pane group, Scene scene, Player player, Pane mainPane, Match match, double width, double margin, double height, int i, int j, Rectangle rectangle, int finalJ, int finalI) {

    }

    //create Table graphics
    private Coordination getPaneFromMap(Pane pane) {
        for (int i = 1; i <= 5; i++)
            for (int j = 1; j <= 9; j++)
                if (pane.equals(gameMap[i][j]))
                    return new Coordination(i, j);
        return null;
    }

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

