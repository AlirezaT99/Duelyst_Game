package view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.stage.Stage;
import javafx.util.Callback;
import model.Account;
import model.Minion;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class AddCardFX {
    private static HashMap<String,String> buffs = new HashMap<>();
    public Pane start(Stage primaryStage, Account account) throws FileNotFoundException {
        Pane root = new Pane();
        buffs = new HashMap<>();
        Scene createCardScene = new Scene(new Pane(), primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(createCardScene.getWidth());
        root.setPrefHeight(createCardScene.getHeight());
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/averta-regular-webfont.ttf")), 40);
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/loginMenu/backgrounds/6.jpg", root, false);

        TextField nameTextField = getTextField(createCardScene, "name");
        nameTextField.relocate(createCardScene.getWidth() / 16, createCardScene.getHeight() / 5);
        String[] typesStringArray = {"Hero", "Minion", "Spell"};
        String[] firstTargetSetting = {"One Movable Card", " The Entire Team", " Hero"};
        String[] secondTargetSetting = {"Friendly Team", "Opponent Team", "Both Teams"};
        String[] attackType = {"Melee", "Ranged", "Hybrid"};
        String[] buffTypes = {"Holy", "Power", "Poison", "Weakness", "Stun", "Disarm"};
        String[] buffTarget = {"Friendly Team", "Enemy Team"};

        ComboBox<String> typeBox = new ComboBox(FXCollections.observableArrayList(typesStringArray));
        typeBox.relocate(createCardScene.getWidth() / 16, nameTextField.getLayoutY() + nameTextField.getPrefHeight() + createCardScene.getHeight() / 10);
        comboBoxSetting(createCardScene, typeBox);

        GraphicalCommonUsages.backSetting(root, createCardScene, account, "mainMenu");
        Image selectCard = new Image(new FileInputStream("src/view/sources/loginMenu/buttons/login.png"));
        Image selectCardGlow = new Image(new FileInputStream("src/view/sources/loginMenu/buttons/login_glow.png"));
        ImageView selectCardViewSpell = new ImageView(selectCard);
        ImageView selectCardViewMovable = new ImageView(selectCard);
        ImageView addBuffView = new ImageView(selectCard);

        Label addCardSpellLabel = new Label("Add Spell");
        addCardSpellLabel.setTextFill(Color.WHITE);
        Label addCardMOvableLabel = new Label("Add Card");
        addCardMOvableLabel.setTextFill(Color.WHITE);
        Label addBuffLAbel = new Label("Add Buff");
        addBuffLAbel.setTextFill(Color.WHITE);

        StackPane addCardSpell = new StackPane(selectCardViewSpell, addCardSpellLabel);
        StackPane addCardMovable = new StackPane(selectCardViewMovable, addCardMOvableLabel);
        StackPane addBuff = new StackPane(addBuffView, addBuffLAbel);

        addCardMovable.setOnMouseEntered(event -> selectCardViewMovable.setImage(selectCardGlow));
        addCardMovable.setOnMouseExited(event -> selectCardViewMovable.setImage(selectCard));

        addCardSpell.setOnMouseEntered(event -> selectCardViewSpell.setImage(selectCardGlow));
        addCardSpell.setOnMouseExited(event -> selectCardViewSpell.setImage(selectCard));

        addBuff.setOnMouseEntered(event -> addBuffView.setImage(selectCardGlow));
        addBuff.setOnMouseExited(event -> addBuffView.setImage(selectCard));
        //separating Spell and Movable card setting

        //spells
        VBox spellBox = new VBox();
        spellBox.setVisible(false);
        ComboBox<String> firstTarget = new ComboBox(FXCollections.observableArrayList(firstTargetSetting));
        ComboBox<String> secondTarget = new ComboBox(FXCollections.observableArrayList(secondTargetSetting));
        comboBoxSetting(createCardScene, firstTarget);
        comboBoxSetting(createCardScene, secondTarget);
        spellBox.getChildren().addAll(firstTarget, secondTarget, addCardSpell);
        spellBox.setSpacing(createCardScene.getHeight() / 10);
        spellBox.relocate(createCardScene.getWidth() / 16, typeBox.getLayoutY() + typeBox.getPrefHeight() + createCardScene.getHeight() / 10);
        selectCardViewSpell.setFitWidth(createCardScene.getWidth() / 10);
        selectCardViewSpell.setPreserveRatio(true);
        //spells

        //movable cards

        VBox movableCardMutualSetting = new VBox();
        movableCardMutualSetting.setVisible(false);
        TextField apTextField = new TextField();
        TextField hpTextField = new TextField();
        TextField rangeTextField = new TextField();
        TextField specialPower = new TextField();
        TextField specialPowerSetting = new TextField();

        apTextField.setPromptText("AP");
        apTextField.setStyle("-fx-background-color:rgba(175, 175, 175, 0.3);");
        hpTextField.setPromptText("HP");
        hpTextField.setStyle("-fx-background-color:rgba(175, 175, 175, 0.3);");
        rangeTextField.setPromptText("Range");
        rangeTextField.setStyle("-fx-background-color:rgba(175, 175, 175, 0.3);");
        specialPower.setStyle("-fx-background-color:rgba(175, 175, 175, 0.3);");
        specialPowerSetting.setStyle("-fx-background-color:rgba(175, 175, 175, 0.3);");
        if (!typeBox.getValue().equals("Spell"))
            movableCardMutualSetting.setVisible(true);

        ComboBox<String> attackTypeCombo = new ComboBox(FXCollections.observableArrayList(attackType));
        comboBoxSetting(createCardScene, attackTypeCombo);

        movableCardMutualSetting.getChildren().addAll(apTextField, hpTextField, rangeTextField, specialPower, specialPowerSetting, attackTypeCombo, addCardMovable);

        movableCardMutualSetting.relocate(createCardScene.getWidth() / 16, typeBox.getLayoutY() + typeBox.getPrefHeight() + createCardScene.getHeight() / 10);
        movableCardMutualSetting.setSpacing(createCardScene.getHeight() / 35);

        selectCardViewMovable.setFitWidth(createCardScene.getWidth() / 10);
        selectCardViewMovable.setPreserveRatio(true);
        //movable cards


        //doing buffs
        VBox buffVBox = new VBox();
        TextField buffnameField = getTextField(createCardScene, "buff name");
        TextField effectValueField = getTextField(createCardScene, "effect value");
        TextField delayField = getTextField(createCardScene, "delay");
        TextField lastField = getTextField(createCardScene, "last");
        ComboBox<String> buffTypeCombo = new ComboBox(FXCollections.observableArrayList(buffTypes));
        ComboBox<String> buffTargetCombo = new ComboBox(FXCollections.observableArrayList(buffTarget));
        comboBoxSetting(createCardScene, buffTypeCombo);
        comboBoxSetting(createCardScene, buffTargetCombo);
        buffVBox.relocate(createCardScene.getWidth() * 8 / 10, createCardScene.getHeight() / 5);
        buffVBox.setSpacing(createCardScene.getHeight() / 20);
        addBuffView.setPreserveRatio(true);
        addBuffView.setFitWidth(createCardScene.getWidth() / 10);
        buffVBox.getChildren().addAll(buffnameField, effectValueField, delayField, lastField, buffTypeCombo, buffTargetCombo, addBuff);


        root.getChildren().addAll(buffVBox);

        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        if (typeBox.getValue().equals("Spell")) {
                            spellBox.setVisible(true);
                            movableCardMutualSetting.setVisible(false);
                        } else {
                            spellBox.setVisible(false);
                            if (typeBox.getValue() != null) {
                                if (typeBox.getValue().equals("Hero"))
                                    specialPower.setPromptText("Special Power");
                                else
                                    specialPower.setPromptText("Buff name");
                                if (typeBox.getValue().equals("Hero"))
                                    specialPowerSetting.setPromptText("Cooldown");
                                else
                                    specialPowerSetting.setPromptText("Activation");
                            }
                            movableCardMutualSetting.setVisible(true);
                        }
                        // selected.setText(combo_box.getValue() + " selected");
                    }
                };
        typeBox.setOnAction(event);

        addBuff.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                      @Override
                                      public void handle(MouseEvent event) {
                                          String buff = "";
                                          switch (buffTypeCombo.getValue()){
                                              case "Holy":
                                                  buff+="0";
                                                  break;
                                              case "Power":
                                                  buff+="1";
                                                  break;
                                              case "Poison":
                                                  buff+="2";
                                                  break;
                                              case "Weakness":
                                                  buff+="3";
                                                  break;
                                              case "Stun":
                                                  buff+="4";
                                                  break;
                                              case "Disarm":
                                                  buff+="5";
                                                  break;
                                          }
                                          if(effectValueField.getText().length()<2)
                                              buff+=("0"+effectValueField.getText());
                                          buff+=delayField.getText();
                                          buff+=lastField.getText();
                                          if(buffTargetCombo.getValue().equals("Friendly Team"))
                                              buff+="0";
                                          else
                                              buff+="1";
                                          buffs.put(buffnameField.getText(),buff);
                                          try {
                                              GraphicalCommonUsages.okPopUp(buffnameField.getText()+" added",createCardScene,root);
                                          } catch (FileNotFoundException e) {
                                              e.printStackTrace();
                                          }
                                          // todo : create buff
                                      }
                                  }
        );
        addCardMovable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //todo : create movable card
                if(typeBox.getValue().equals("Minion")){
                    Minion minion = new Minion();
                    minion.setHealth(Integer.parseInt(hpTextField.getText()));
                    minion.setDamage(Integer.parseInt(apTextField.getText()));
                    minion.setMaxAttackRange(Integer.parseInt(rangeTextField.getText()));
                    String buff = buffs.get(specialPower.getText());
                    switch (attackTypeCombo.getValue()){
                        case "Melee":
                            minion.setMelee(true);
                            break;
                        case "Ranged":
                            minion.setRanged(true);
                            break;
                        case "Hybrid":
                            minion.setHybrid(true);
                            break;
                    }
                }
                if(typeBox.getValue().equals("Hero")){

                }
            }
        });

        addCardSpell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //todo : create spell
            }
        });


        comboBoxSetting(createCardScene, typeBox);

        root.getChildren().addAll(nameTextField, typeBox, spellBox, movableCardMutualSetting);


        return root;
    }

    private TextField getTextField(Scene createCardScene, String s) {
        TextField buffnameField = new TextField();
        buffnameField.setPromptText(s);
        buffnameField.setPrefWidth(createCardScene.getWidth() / 10);
        buffnameField.setPrefHeight(createCardScene.getHeight() / 20);
        buffnameField.setStyle("-fx-background-color:rgba(175, 175, 175, 0.3);");
        return buffnameField;
    }

    private void comboBoxSetting(Scene createCardScene, ComboBox<String> typeBox) {
        typeBox.getSelectionModel().selectFirst();
        typeBox.setPrefWidth(createCardScene.getWidth() / 10);
        typeBox.setPrefHeight(createCardScene.getHeight() / 20);
        typeBox.setStyle("-fx-background-color: rgba(250,250,250,0.4);");
        typeBox.getEditor().setStyle("-fx-text-fill: #00FF00;" + "-fx-background-color: #445566");
        typeBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> p) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            BackgroundFill background_fill = new BackgroundFill(Color.grayRgb(20, 0.4),
                                    new CornerRadii(0), new javafx.geometry.Insets(0, 0, 0, 0));
                            setBackground(new Background(background_fill));
                        }
                    }
                };
            }
        });
        typeBox.setButtonCell(new ListCell() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    // styled like -fx-prompt-text-fill:
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                } else {

                    setStyle("-fx-text-fill: #FFFFFF");
                    setText(item.toString());
                }
            }
        });
        typeBox.setPrefWidth(createCardScene.getWidth() / 6);

    }


}
