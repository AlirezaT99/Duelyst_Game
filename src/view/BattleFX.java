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
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import presenter.BattleMenuProcess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
    private static Pane rectanglesPane = new Pane();


    Pane start(Match match, boolean isStoryMode, Stage stage, Account account) throws FileNotFoundException {

        BattleMenu.battleSetup(match);
        Pane root = new Pane();
        setScreenVariables(stage);
        setBackGround(match, isStoryMode, root);
        setGeneralIcons(account, match, root, new Scene(new Group(), screenWidth, screenHeight));
        root.getChildren().addAll(setTable(rectanglesPane, stage.getScene(), match.getPlayer1(), root, match));
        updateSoldiers(match, new Scene(new Group(), screenWidth, screenHeight));
        return root;

    }

    //create table graphics
    public static void updateSoldiers(Match match, Scene scene) throws FileNotFoundException {
        for (int i = 1; i <= 5; i++)
            for (int j = 1; j <= 9; j++) {
                setGif(match.getTable().getCellByCoordination(i, j).getMovableCard(), i, j, scene, match, "idle");
                setItemGif(match.getTable().getCellByCoordination(i, j).getItem(), i, j, scene);
            }

    }

    private static void setItemGif(Item item, int x, int y, Scene scene) {
        Animation animation;
        if (item instanceof Flag) {
            animation = GraphicalCommonUsages.getGif("Flag", "idle");
            animation.getView().setFitWidth(scene.getWidth() / 18.8);
            // animation.getView().setFitHeight(scene.getHeight()/10);
            animation.getView().setPreserveRatio(true);
            animation.setCycleCount(Integer.MAX_VALUE);
            animation.play();
            if (gameMap[x][y].getChildren().size() > 1)
                gameMap[x][y].getChildren().subList(1, gameMap[x][y].getChildren().size()).clear();
            gameMap[x][y].getChildren().add(animation.getView());
            gameMap[x][y].setPrefHeight((rectangles[x][y].getHeight()));
            gameMap[x][y].setPrefWidth((rectangles[x][y].getWidth()));

            gameMap[x][y].getChildren().get(0).setLayoutX(0);
            gameMap[x][y].getChildren().get(0).setLayoutY(0);
        }

    }

    private static void setGif(Card card, int x, int y, Scene scene, Match match, String type) throws FileNotFoundException {
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

    private static String deleteWhiteSpaces(String string) {
        String result = string.replaceAll(" ", "");
        return result.trim();
    }

    private void setGeneralIcons(Account account, Match match, Pane root, Scene scene) throws FileNotFoundException {
        //  System.out.println(match.getPlayer1());
        rectanglesPane = new Pane();
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

        final TextField cheatField = new TextField();
        cheatField.setStyle("-fx-background-color:rgba(175, 175, 175, 0.8);");
        cheatField.setPrefWidth(scene.getWidth() / 20);
        cheatField.relocate(0, scene.getHeight() / 10);
        cheatField.layoutYProperty().bind(root.heightProperty().subtract(cheatField.heightProperty()).divide(2));
        cheatField.setVisible(false);
        root.getChildren().addAll(cheatField);

        ArrayList<String> onKeyPressed = new ArrayList<>();
        Scene scene1 = Main.getScene();
        if (match.getPlayer1().getAccount().equals(account))
            cheatField.layoutXProperty().bind(firstPlayerImageView.fitWidthProperty().subtract(cheatField.widthProperty()).divide(2));
        else
            cheatField.layoutXProperty().bind(secondPlayerImageView.fitWidthProperty().subtract(cheatField.widthProperty()).divide(2));
        scene1.setOnKeyPressed(event -> {
            if (!onKeyPressed.contains(event.getCode().toString()))
                onKeyPressed.add(event.getCode().toString());
            if (onKeyPressed.contains("ALT") && (onKeyPressed.contains("c") || onKeyPressed.contains("C")))
                cheatField.setVisible(true);
            if (cheatField.isVisible() && onKeyPressed.contains("ENTER"))
                processCheatCode(scene, root, match, match.getPlayer1().getAccount().equals(account) ? match.getPlayer1() : match.getPlayer2(), cheatField.getText());
        });
        scene1.setOnKeyReleased(event -> {
            onKeyPressed.remove(event.getCode().toString());
        });


    }

    private void processCheatCode(Scene scene, Pane root, Match match, Player player, String cheatCode) {
        switch (cheatCode.toLowerCase()) {
            case "mana":
                player.setMana(9);
                try {
                    updateMana(match, root, scene);
                    drawHand(player, root, scene);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
        System.out.println(player.getUserName() + " : " + cheatCode);
    }

    public static void endProcedure(Match match, Scene scene, Player player, boolean state) {
        System.out.println("end procedure");
        Pane resultPane = new Pane();
        resultPane.setPrefHeight(scene.getHeight());
        resultPane.setPrefWidth(scene.getWidth());

        BackgroundFill background_fill = new BackgroundFill(Color.grayRgb(20, 0.5),
                new CornerRadii(0), new javafx.geometry.Insets(0, 0, 0, 0));
        resultPane.setBackground(new Background(background_fill));

        ((Pane) (rectanglesPane.getParent())).getChildren().addAll(resultPane);
        try {
            ImageView resultView = new ImageView();
            Image winImage = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/end/" + deleteWhiteSpaces(player.getDeck().getHero().getName()).toLowerCase() + "/win.png"));
            Image loseImage = new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/end/" + deleteWhiteSpaces(player.getDeck().getHero().getName()).toLowerCase() + "/lose.png"));
            Label resultLabel = new Label();
            resultLabel.setTextFill(Color.WHITE);
            resultLabel.setFont(Font.font(50));
            if (state) {
                resultView.setImage(winImage);
                resultLabel.setText(player.getAccount().getUserName() + " Won");
            } else {
                resultView.setImage(loseImage);
                resultLabel.setText("You Lost");
            }
            VBox resultVBox = new VBox();
            resultVBox.setAlignment(Pos.CENTER);
            resultVBox.getChildren().addAll(resultView, resultLabel);
            resultPane.getChildren().add(resultVBox);
            resultVBox.setSpacing(scene.getHeight() / 10);
            resultVBox.layoutXProperty().bind(resultPane.widthProperty().subtract(resultVBox.widthProperty()).divide(2));
            resultVBox.layoutYProperty().bind(resultPane.heightProperty().subtract(resultVBox.heightProperty()).divide(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultPane.setOnMouseClicked(event1 -> {
                    try {
                        Main.setMainMenuFX(player.getAccount());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        );
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
        endTurn.setOnMouseClicked(event -> {
            try {
                if (match.currentTurnPlayer().getUserName().equals(player.getUserName())) {
                    new BattleMenuProcess().endTurn();
                    updateMana(match, root, scene);
                    bottomRow = drawHand(player, root, scene);
                    ((ImageView) endTurn.getChildren().get(0)).setImage(endTurnEnemyInitialImage);

                }
            } catch (IOException e) {
                e.printStackTrace();
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

        graveYard.setOnMouseClicked(event -> {
            Pane graveYard_sher = new Pane();
            // long time = System.currentTimeMillis();
            graveYard_sher.setPrefHeight(scene.getHeight());
            graveYard_sher.setPrefWidth(scene.getWidth());

            BackgroundFill background_fill = new BackgroundFill(Color.grayRgb(20, 0.5),
                    new CornerRadii(15), new javafx.geometry.Insets(0, 0, 0, 0));
            graveYard_sher.setBackground(new Background(background_fill));

            root.getChildren().addAll(graveYard_sher);
            ArrayList<Card> graveYard_list = new ArrayList<>();
            if (player.equals(match.getPlayer1()))
                graveYard_list = match.player1_graveyard;
            else
                graveYard_list = match.player2_graveyard;
            VBox graveVBox = new VBox();
            graveVBox.setAlignment(Pos.CENTER);
            graveYard_sher.getChildren().add(graveVBox);
            graveVBox.setSpacing(scene.getHeight() / 25);
            graveVBox.layoutXProperty().bind(root.widthProperty().subtract(graveVBox.widthProperty()).divide(2));
            graveVBox.layoutYProperty().bind(root.heightProperty().subtract(graveVBox.heightProperty()).divide(2));

            for (Card card : graveYard_list) {
                Label label1 = new Label(card.getName());
                label1.setFont(Font.font(30));
                label1.setTextFill(Color.WHITE);
                graveVBox.getChildren().add(label1);
                label1.setAlignment(Pos.CENTER);
            }
            graveYard_sher.setOnMouseClicked(event1 -> {
                        // root.getChildren().remove(graveYard_sher);
                        root.getChildren().remove(graveYard_sher);
                    }
            );
        });

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
        HBox manaFirstPlayer = drawMana(match.getPlayer1(), match, root, scene, 1);
        firstPlayer.getChildren().add(1, manaFirstPlayer);
        manaFirstPlayer.setAlignment(Pos.CENTER);
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
            String ff = player.getHand().getCards().get(i) == null ? "null" : player.getHand().getCards().get(i).getName();
            System.out.println(ff);
//            System.out.println(ff+" "+i+" ,id "+ player.getHand().getCards().get(i)!=null?player.getHand().getCards().get(i).getCardID():"null");
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
                    if (gameMap[finalI][finalJ].getChildren().size() > 1) {
                        HBox horizonalActiationHBox = new HBox();
                        ImageView onSpawnView = new ImageView();
                        ImageView onDeathView = new ImageView();
                        ImageView onAttackView = new ImageView();
                        ImageView onDefendView = new ImageView();

                        setActivationImageView(gameMap[finalI][finalJ], onSpawnView);
                        setActivationImageView(gameMap[finalI][finalJ], onDeathView);
                        setActivationImageView(gameMap[finalI][finalJ], onAttackView);
                        setActivationImageView(gameMap[finalI][finalJ], onDefendView);

                        String cardName = ((Label) (gameMap[finalI][finalJ].getChildren().get(gameMap[finalI][finalJ].getChildren().size() - 1))).getText();
                        Minion minion;
                        minion = Minion.getMinionByName(cardName);
                        if (minion != null) {
                            if (minion.getSummonImpact() != null) {
                                try {
                                    onSpawnView.setImage(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/activation/onSpawn.png")));
                                    horizonalActiationHBox.getChildren().addAll(onSpawnView);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (minion.getDyingWishImpact() != null) {
                                try {
                                    onDeathView.setImage(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/activation/onDeath.png")));
                                    horizonalActiationHBox.getChildren().addAll(onDeathView);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (minion.getOnAttackImpact() != null) {
                                try {
                                    onAttackView.setImage(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/activation/onAttack.png")));
                                    horizonalActiationHBox.getChildren().addAll(onAttackView);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (minion.getOnDefendImpact() != null) {
                                try {
                                    onDefendView.setImage(new Image(new FileInputStream("src/view/sources/Battle/BattlePictures/activation/onDefend.png")));
                                    horizonalActiationHBox.getChildren().addAll(onDefendView);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            rectanglesPane.getChildren().addAll(horizonalActiationHBox);
                            horizonalActiationHBox.relocate(gameMap[finalI][finalJ].getLayoutX(),gameMap[finalI][finalJ].getLayoutY());
//                            gameMap[finalI][finalJ].getChildren().addAll(horizonalActiationHBox);
                        }
                    }
                    rectangles[finalI][finalJ].setFill(Color.WHITE);
                    rectangles[finalI][finalJ].setOpacity(0.2);
                });
                gameMap[i][j].setOnDragDetected(event -> {
                    rectangles[finalI][finalJ].startFullDrag();
                    draggedFromNode = gameMap[finalI][finalJ];
                });
                gameMap[i][j].setOnMouseDragReleased(event -> {
                    // System.out.println("actual map:" + finalI + " " + finalJ);
                    if (draggedFromNode instanceof VBox && BattleMenuProcess.isCoordinationValidToInsert(finalI, finalJ)) { //todo : ehtemalan shartaye bishatri mikhad
                        // group.getChildren().remove(draggedFromNode);
                        // ((StackPane) (((VBox) draggedFromNode).getChildren().get(1))).getChildren().remove(1);
                        String cardName = ((Label) (((VBox) draggedFromNode).getChildren().get(2))).getText();
                        Card selectedCardFromHand = player.getHand().getCards().get(objectInHandIndex);
                        if (selectedCardFromHand instanceof Spell) {

                            Animation spellAnimation = GraphicalCommonUsages.getGif(selectedCardFromHand.getName(), "impact");
                            ImageView impactView = spellAnimation.getView();
                            gameMap[finalI][finalJ].getChildren().add(impactView);
                            impactView.setFitWidth((scene.getWidth() / 18.8) * 4);
                            impactView.setFitHeight(scene.getHeight() / 5);
                            impactView.layoutXProperty().bind(gameMap[finalI][finalJ].widthProperty().subtract(impactView.fitWidthProperty()).divide(2));
                            impactView.layoutYProperty().bind(gameMap[finalI][finalJ].heightProperty().subtract(impactView.fitHeightProperty()).divide(2));
                            spellAnimation.setCycleCount(1);
                            spellAnimation.play();
                            spellAnimation.setOnFinished(event1 -> gameMap[finalI][finalJ].getChildren().remove(gameMap[finalI][finalJ].getChildren().size() - 1));

                        }
                        //((StackPane) (((VBox) draggedFromNode).getChildren().get(0))).getChildren().remove(1);
                        if (player.getHand().getCards().get(objectInHandIndex) instanceof Minion)
                            ((Minion) (player.getHand().getCards().get(objectInHandIndex))).copy().castCard(match.getTable().getCellByCoordination(finalI, finalJ), objectInHandIndex);
                        else
                            ((Spell) player.getHand().getCards().get(objectInHandIndex)).copy().castCard(match.getTable().getCellByCoordination(finalI, finalJ));
//                            player.getHand().findCardByName(cardName)
//                                    .castCard(match.getTable().getCellByCoordination(finalI, finalJ));
                        player.getHand().removeCardFromHand(objectInHandIndex);
                        draggedFromNode = null;
                        try {
                            bottomRow = drawHand(player, mainPane, scene);
                            updateMana(match, mainPane, scene);
                            if (!(selectedCardFromHand instanceof Spell))
                                updateSoldiers(match, scene);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
//
                    }
                    if (draggedFromNode instanceof Pane && !(draggedFromNode instanceof VBox)
                            && ((Pane) draggedFromNode).getChildren().size() >= 3) {
                        Coordination coordination = getPaneFromMap((Pane) draggedFromNode);
                        if (gameMap[finalI][finalJ].getChildren().size() == 1 || gameMap[finalI][finalJ].getChildren().size() == 2) {

                            if (match.getTable().getCell(coordination.getX(), coordination.getY()).getMovableCard().isMoveValid(match.getTable().getCellByCoordination(finalI, finalJ)) == 0
                                    && match.getTable().getCell(coordination.getX(), coordination.getY()).getMovableCard().getPlayer().equals(match.currentTurnPlayer())) {
                                moveProcess(coordination, match, finalI, finalJ, scene, rectanglesPane);
                            }
                        } else
                            attackProcess(coordination, match, finalI, finalJ, scene, width, margin, height, group, rectanglesPane);
                    }
                });
                gameMap[i][j].setOnMouseExited(event -> {
                    if(rectanglesPane.getChildren().get(rectanglesPane.getChildren().size()-1) instanceof HBox)
                        rectanglesPane.getChildren().remove(rectanglesPane.getChildren().size()-1);
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

    private void setActivationImageView(Pane pane, ImageView onSpawnView) {
        onSpawnView.setFitWidth(pane.getWidth()/3);
        onSpawnView.setPreserveRatio(true);
    }


    public static void deathProcess(Coordination coordination, Match match, Scene scene, Pane rectanglesPane) {
        System.out.println(match.getTable().getCellByCoordination(coordination.getX(), coordination.getY()).getMovableCard().getName() + " in death process");
        Animation deathAnimation = GraphicalCommonUsages.getGif(match.getTable().getCellByCoordination(coordination.getX(), coordination.getY()).getMovableCard().getName(), "death");
        gameMap[coordination.getX()][coordination.getY()].getChildren().remove(1);
        ImageView deathView = deathAnimation.getView();
        deathView.setPreserveRatio(true);
        deathView.setFitWidth(scene.getWidth() / 18.8);
        gameMap[coordination.getX()][coordination.getY()].getChildren().add(1, deathView);
        deathAnimation.setCycleCount(1);
        deathAnimation.play();
        match.getTable().getCellByCoordination(coordination.getX(), coordination.getY()).setMovableCard(null);
        deathAnimation.setOnFinished(event -> {
            try {
                updateSoldiers(match, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private static void attackProcess(Coordination coordination, Match match, int finalI, int finalJ, Scene scene,
                                      double width, double margin, double height, Pane group, Pane rectanglesPane) {
        boolean isCounterAttackValid = match.getTable().getCell(coordination.getX(), coordination.getY()).getMovableCard().isCounterAttackValid(match.getTable().getCell(finalI, finalJ).getMovableCard());

        int result = match.getTable().getCell(coordination.getX(), coordination.getY()).getMovableCard().attack(match.getTable().getCell(finalI, finalJ).getMovableCard());
        if (result == 0) {
            Animation attackAnimation = GraphicalCommonUsages.getGif(((Label) ((Pane) draggedFromNode).getChildren().get(((Pane) draggedFromNode).getChildren().size() - 1)).getText(), "attack");
            ImageView imageView = attackAnimation.getView();
            ((Pane) draggedFromNode).getChildren().remove(1);
            ((Pane) draggedFromNode).getChildren().add(1, imageView);
            imageView.setFitWidth(scene.getWidth() / 18.8);
            imageView.setPreserveRatio(true);
            if (match.currentTurnPlayer().equals(match.getPlayer2()))
                rotateImageView(imageView);
            attackAnimation.setCycleCount(1);
            attackAnimation.play();
            movableCardAttackSFX(((Label) ((Pane) draggedFromNode).getChildren().get(((Pane) draggedFromNode).getChildren().size() - 1)).getText(), true);
            attackAnimation.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        if (isCounterAttackValid) {
                            Animation counterAttackAnimation = GraphicalCommonUsages.getGif(((Label) gameMap[finalI][finalJ].getChildren().get(gameMap[finalI][finalJ].getChildren().size() - 1)).getText(), "attack");
                            ImageView imageView = counterAttackAnimation.getView();
                            gameMap[finalI][finalJ].getChildren().remove(1);
                            gameMap[finalI][finalJ].getChildren().add(1, imageView);
                            imageView.setFitWidth(scene.getWidth() / 18.8);
                            imageView.setPreserveRatio(true);
                            if (match.currentTurnPlayer().equals(match.getPlayer1()))
                                rotateImageView(imageView);
                            counterAttackAnimation.setCycleCount(1);
                            counterAttackAnimation.play();
                            movableCardAttackSFX(((Label) gameMap[finalI][finalJ].getChildren().get(gameMap[finalI][finalJ].getChildren().size() - 1)).getText(), false);
                            counterAttackAnimation.setOnFinished(event1 -> {
                                try {
                                    BattleMenuProcess.buryTheDead();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        } else {
                            try {
                                BattleMenuProcess.buryTheDead();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private static void movableCardAttackSFX(String cardName, boolean attacker) {
        AudioClip audioClip;
        if (Hero.getHeroByName(cardName) != null) {
            if (cardName.toLowerCase().equals("afsaane") || cardName.toLowerCase().equals("simorgh")) {
                audioClip = new AudioClip(Main.class.getResource("sources/Battle/music/sfx_f4_general_hit.m4a").toString());
                audioClip.setCycleCount(1);
                audioClip.play();
                return;
            }
            if (cardName.toLowerCase().equals("aarash") || cardName.toLowerCase().equals("rostam") || cardName.toLowerCase().equals("esfandiar") || cardName.toLowerCase().equals("kaave")) {
                if (attacker)
                    audioClip = new AudioClip(Main.class.getResource("sources/Battle/music/sfx_f1_general_hit.m4a").toString());
                else
                    audioClip = new AudioClip(Main.class.getResource("sources/Battle/music/sfx_f1_general_attack_swing.m4a").toString());
                audioClip.setCycleCount(1);
                audioClip.play();
                return;
            } else {
                if (attacker)
                    audioClip = new AudioClip(Main.class.getResource("sources/Battle/music/sfx_f5_general_hit.m4a").toString());
                else
                    audioClip = new AudioClip(Main.class.getResource("sources/Battle/music/sfx_f5_general_attack_swing.m4a").toString());
                audioClip.setCycleCount(1);
                audioClip.play();
                return;
            }
        } else {
            Minion minion = Minion.getMinionByName(cardName);
            if (minion.isMelee()) {
                if (attacker)
                    audioClip = new AudioClip(Main.class.getResource("sources/Battle/music/sfx_f2melee_attack_impact_1.m4a").toString());
                else
                    audioClip = new AudioClip(Main.class.getResource("sources/Battle/music/sfx_f2melee_attack_swing_2.m4a").toString());
                audioClip.setCycleCount(1);
                audioClip.play();
                return;
            }
            if (minion.isHybrid()) {
                if (attacker)
                    audioClip = new AudioClip(Main.class.getResource("sources/Battle/music/sfx_f2_celestialphantom_attack_impact.m4a").toString());
                else
                    audioClip = new AudioClip(Main.class.getResource("sources/Battle/music/sfx_f2_celestialphantom_attack_swing.m4a").toString());
                audioClip.setCycleCount(1);
                audioClip.play();
                return;
            } else {
                if (attacker)
                    audioClip = new AudioClip(Main.class.getResource("sources/Battle/music/sfx_f4_engulfingshadow_attack_impact.m4a").toString());
                else
                    audioClip = new AudioClip(Main.class.getResource("sources/Battle/music/sfx_f4_engulfingshadow_attack_swing.m4a").toString());
                audioClip.setCycleCount(1);
                audioClip.play();
                return;
            }
        }
    }

    public static void moveProcess(Coordination coordination, Match match, int finalI, int finalJ, Scene scene, Pane
            rectanglesPane) {
        if (draggedFromNode != null) {

            double width = scene.getWidth() * 3 / 47;
            double margin = width / 20;
            double height = (scene.getHeight() / 2 - width / 5) / 5;
            if (match.getTable().getCell(coordination.getX(), coordination.getY()).getMovableCard().isMoveValid(match.getTable().getCell(finalI, finalJ)) == 0) {

                Animation runAnimation = GraphicalCommonUsages.getGif(((Label) ((Pane) draggedFromNode).getChildren().get(((Pane) draggedFromNode).getChildren().size() - 1)).getText(), "run");
                ImageView movableCard = runAnimation.getView();
                movableCard.setFitWidth(scene.getWidth() / 18.8);
                movableCard.setPreserveRatio(true);

                StackPane ap = (StackPane) (((Pane) draggedFromNode).getChildren().get(2));
                StackPane hp = (StackPane) (((Pane) draggedFromNode).getChildren().get(3));
                for (int k = ((Pane) draggedFromNode).getChildren().size() - 1; k > 0; k--)
                    ((Pane) draggedFromNode).getChildren().remove(k);

                match.getTable().getCell(coordination.getX(), coordination.getY()).getMovableCard().move(match.getTable().getCell(finalI, finalJ));
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
                runAnimation.setCycleCount(Integer.MAX_VALUE);
                runAnimation.play();
                timeline.play();
                timeLineAP.play();
                timeLineHP.play();
                timeline.setOnFinished(event1 -> {
                    try {
                        draggedFromNode = null;
                        updateSoldiers(match, scene);
                        rectanglesPane.getChildren().remove(movableCard);
                        rectanglesPane.getChildren().remove(ap);
                        rectanglesPane.getChildren().remove(hp);
                        System.out.println("done i guess");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
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

    //getters
    public static double getScreenWidth() {
        return screenWidth;
    }

    public static double getScreenHeight() {
        return screenHeight;
    }

    public static Pane getRectanglesPane() {
        return rectanglesPane;
    }

    public static Pane[][] getGameMap() {
        return gameMap;
    }
//getters//

    //setters

    public static void setDraggedFromNode(Object object) {
        draggedFromNode = object;
    }
    //setters
}
