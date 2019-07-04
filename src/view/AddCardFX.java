package view;

import com.google.gson.Gson;
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
import model.*;
import model.Main;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class AddCardFX {
    private static HashMap<String, String> buffs = new HashMap<>();
    private static HashMap<String, Spell> spells = new HashMap<>();

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
        String[] activationTypeCombo = {"on Spawn", "on Attack", "on Defend", "on Death"};


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
        TextField costMovableCard = new TextField();
        costMovableCard.setPromptText("Cost");
        costMovableCard.setStyle("-fx-background-color:rgba(175, 175, 175, 0.3);");

        TextField costSpell = new TextField();
        costSpell.setPromptText("Cost");
        costSpell.setStyle("-fx-background-color:rgba(175, 175, 175, 0.3);");

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
        spellBox.getChildren().addAll(firstTarget, secondTarget, costSpell, addCardSpell);
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
        ComboBox<String> activationCombo = new ComboBox(FXCollections.observableArrayList(activationTypeCombo));
        comboBoxSetting(createCardScene, activationCombo);

        ComboBox<String> attackTypeCombo = new ComboBox(FXCollections.observableArrayList(attackType));
        comboBoxSetting(createCardScene, attackTypeCombo);

        movableCardMutualSetting.getChildren().addAll(apTextField, hpTextField, rangeTextField, specialPower, specialPowerSetting, costMovableCard, attackTypeCombo, addCardMovable);

        movableCardMutualSetting.relocate(createCardScene.getWidth() / 16, typeBox.getLayoutY() + typeBox.getPrefHeight() + createCardScene.getHeight() / 10);
        movableCardMutualSetting.setSpacing(createCardScene.getHeight() / 40);

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
                                    specialPowerSetting.setPromptText("on Death/Spawn/Attack/Defend");
                            }
                            movableCardMutualSetting.setVisible(true);
                        }
                        // selected.setText(combo_box.getValue() + " selected");
                    }
                };
        typeBox.setOnAction(event);

        addBuff.setOnMouseClicked(event12 -> {
                    String buff = "";
                    switch (buffTypeCombo.getValue()) {
                        case "Holy":
                            buff += "1101";
                            break;
                        case "Power":
                            buff += "1231";
                            break;
                        case "Poison":
                            buff += "0320";
                            break;
                        case "Weakness":
                            buff += "0420";
                            break;
                        case "Stun":
                            buff += "0500";
                            break;
                        case "Disarm":
                            buff += "0600";
                            break;
                    }
                    if (effectValueField.getText().length() < 2 && effectValueField.getText().matches("\\d+"))
                        buff += ("0" + effectValueField.getText());
                    if (effectValueField.getText().length() == 2 && effectValueField.getText().matches("\\d+"))
                        buff += effectValueField.getText();
                    if (effectValueField.getText().length() > 2 || !effectValueField.getText().matches("\\d+")) {
                        try {
                            GraphicalCommonUsages.okPopUp("effect value invalid", createCardScene, root);
                            return;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    buff += "0"; //passive permanent
                    if (delayField.getText().length() > 1 || !delayField.getText().matches("\\d+")) {
                        try {
                            GraphicalCommonUsages.okPopUp("delay field invalid", createCardScene, root);
                            return;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    buff += delayField.getText(); //turns to be activated
                    if (lastField.getText().length() < 2 && lastField.getText().matches("\\d+"))
                        buff += "0" + lastField.getText();
                    if (lastField.getText().length() == 2 && lastField.getText().matches("\\d+"))
                        buff += lastField.getText(); // turns active
                    if (lastField.getText().length() > 2 && !lastField.getText().matches("\\d+")) {
                        try {
                            GraphicalCommonUsages.okPopUp("effect value invalid", createCardScene, root);
                            return;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    buff += "000g100"; //entering target type id
                    if (buffTargetCombo.getValue().equals("Friendly Team"))
                        buff += "0200000000";
                    else
                        buff += "1200000000";
                    buffs.put(buffnameField.getText(), buff);
                    try {
                        GraphicalCommonUsages.okPopUp(buffnameField.getText() + " added", createCardScene, root);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        );

        addCardMovable.setOnMouseClicked(event1 -> {
            if (typeBox.getValue().equals("Minion")) {
                try {
                    Minion minion = new Minion();
                    minion.setHealth(Integer.parseInt(hpTextField.getText()));
                    minion.setDamage(Integer.parseInt(apTextField.getText()));

                    minion.setMaxAttackRange(Integer.parseInt(rangeTextField.getText()));
                    if (!buffs.containsKey(specialPower.getText())) {
                        try {
                            GraphicalCommonUsages.okPopUp("no such buff found", createCardScene, root);
                            return;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    String buff = buffs.get(specialPower.getText());
                    String[] impactComponents = buff.split("g");
                    Impact impact = new Impact("000000000", "000", impactComponents[1], impactComponents[0]);
                    switch (attackTypeCombo.getValue()) {
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
                    switch (specialPowerSetting.getText().toLowerCase()) {
                        case "on death":
                            minion.setDyingWishImpact(impact);
                            break;
                        case "on defend":
                            minion.setOnDefendImpact(impact);
                            break;
                        case "on attack":
                            minion.setOnAttackImpact(impact);
                            break;
                        case "on spawn":
                            minion.setSummonImpact(impact);
                            break;
                    }
                    minion.setCost(Integer.parseInt(costMovableCard.getText()));
                    GraphicalCommonUsages.drakePopUp("minion created", createCardScene, root, 1);
                    buffs.clear();
                    minion.isCostume(true);
                    Shop.getShopMinions().add(minion);
                    Minion.addToMinions(minion);
                    Main.addCardToFiles(minion);
                    GraphicalCommonUsages.drakePopUp("minion added", createCardScene, root, 1);
                    //todo : add minion to everywhere :/
                } catch (Exception e) {
                    try {
                        GraphicalCommonUsages.drakePopUp("invalid setting for minion", createCardScene, root, 2);
                        return;
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if (typeBox.getValue().equals("Hero")) {
                try {
                    String name;
                    int health, damage, range, coolDown;
                    name = nameTextField.getText();
                    health = Integer.parseInt(hpTextField.getText());
                    damage = Integer.parseInt(apTextField.getText());
                    range = Integer.parseInt(rangeTextField.getText());
                    coolDown = Integer.parseInt(specialPowerSetting.getText());
                    Spell spell = Spell.getSpellByName(specialPower.getText());
                    Hero hero = new Hero(name, health, damage, spell, coolDown);
                    hero.isCostume(true);
                    Shop.getShopHeroes().add(hero);
                    Hero.addToHeroes(hero);
                    Main.addCardToFiles(hero);
                    GraphicalCommonUsages.drakePopUp("hero added", createCardScene, root, 1);
                } catch (Exception e) {
                    try {
                        GraphicalCommonUsages.drakePopUp("invalid setting for hero", createCardScene, root, 2);
                        return;
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }

                //todo : add hero to everywhere
            }
        });

        addCardSpell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    String targetTypeID = "";
                    Impact primaryImpact, secondaryImpact;
                    String primaryImpactTypeID = "", secondaryImpactTypeID = "";
                    if (firstTarget.getValue().trim().equals("One Movable Card"))
                        targetTypeID += "1";
                    else
                        targetTypeID += "0";

                    if (secondTarget.getValue().trim().equals("Both Teams"))
                        targetTypeID += "1";
                    else
                        targetTypeID += "0";

                    if (firstTarget.getValue().equals("The Entire Team"))
                        targetTypeID += "1";
                    else
                        targetTypeID += "0";

                    switch ((secondTarget.getValue().toLowerCase())) {
                        case "friendly team":
                            targetTypeID += "0";
                            break;
                        case "opponent team":
                            targetTypeID += "1";
                            break;
                        case "both teams":
                            targetTypeID += "2";
                            break;
                    }
                    if (firstTarget.getValue().equals("Hero"))
                        targetTypeID += "000000000";
                    else
                        targetTypeID += "200000000";
                    int count = 0;
                    if (buffs.size() >= 1) {
                        for (String s : buffs.keySet()) {
                            if (count == 0)
                                primaryImpactTypeID = buffs.get(s);
                            if (count == 1)
                                secondaryImpactTypeID = buffs.get(s);
                            count++;
                        }
                    }
                    primaryImpact = new Impact("000000000", "000", targetTypeID, primaryImpactTypeID);
                    secondaryImpact = new Impact("000000000", "000", targetTypeID, secondaryImpactTypeID);
                    Spell spell = new Spell(primaryImpact, secondaryImpact);
                    spell.setName(nameTextField.getText());
                    spell.setCost(Integer.parseInt(costSpell.getText()));
                    buffs.clear();
                    spell.isCostume(true);
                    Shop.getShopSpells().add(spell);
                    Spell.addToSpells(spell);
                    Main.addCardToFiles(spell);
                    addCardGif(spell);
                    GraphicalCommonUsages.drakePopUp("spell added", createCardScene, root, 1);
                    //todo : add spell to everywhere
                } catch (Exception e) {
                    try {
                        e.printStackTrace();
                        GraphicalCommonUsages.drakePopUp("invalid setting for spell", createCardScene, root, 2);
                        return;
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
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

    private boolean addCardGif(Card card){
        File stockDir = null;
        if (card instanceof Spell)
         stockDir = new File("src/view/sources/gifs/spells/bitch");
         return stockDir.mkdir();
    }

//    private String suitableFileName(String name){
//        String[] nameEditing = name.split("[ ]");
//        String suitableName = "";
//        for (String s : nameEditing) {
//            suitableName+=s.toLowerCase();
//        }
//        return suitableName;
//    }


}
