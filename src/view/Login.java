package view;


import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import presenter.LoginMenuProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class Login {
    boolean isInLogin = true;
    boolean isInSignUp = false;


    public Pane start(Stage primaryStage) throws FileNotFoundException {
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/common/fonts/TrumpGothicPro-Medium-webfont.ttf")), 22);
        Pane root = new Pane();
        Pane fakeRoot = new Pane();
        Scene loginScene = new Scene(fakeRoot, primaryStage.getWidth(), primaryStage.getHeight());
        root.setPrefWidth(primaryStage.getWidth());
        root.setPrefHeight(primaryStage.getHeight());
        Random random = new Random();
        int backGroundNumber = random.nextInt(24) + 1;
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/loginMenu/backgrounds/" + backGroundNumber + ".jpg", root);
        Image logo = new Image(new FileInputStream("src/view/sources/loginMenu/backgrounds/duelyst.png"));
        ImageView logoImageView = new ImageView(logo);
        logoImageView.relocate(0, 0);
        logoImageView.setFitWidth(loginScene.getWidth() / 2.5);
        logoImageView.setFitHeight(loginScene.getHeight() / 4);
        root.getChildren().addAll(logoImageView);

        double width = loginScene.getWidth() / 6;
        createAccountAndLoginBarsContainerDesign(root, loginScene);
        //labels
        StackPane onLogin = new StackPane();
        StackPane onSignUp = new StackPane();
        Label loginLabel = new Label("LOG IN");
        Label signUpLabel = new Label("SIGN UP");
//        loginLabel.relocate(loginScene.getWidth() * 30.8 / 40, loginScene.getHeight() / 2.25);
//        signUpLabel.relocate(loginScene.getWidth() * 34.5 / 40, loginScene.getHeight() / 2.25);
        loginLabel.setTextFill(Color.WHITE);
        signUpLabel.setTextFill(Color.WHITE);
        Circle onLoginCircle = new Circle(20);
        Circle onSignUpCircle = new Circle(20);
        onLogin.setLayoutX(loginScene.getWidth() * 30.8 / 40);
        onLogin.setLayoutY(loginScene.getHeight() / 2.3);
        onSignUp.setLayoutX(loginScene.getWidth() * 34.5 / 40);
        onSignUp.setLayoutY(loginScene.getHeight() / 2.3);
        onLoginCircle.setOpacity(0.3);
        onSignUpCircle.setOpacity(0.3);
        Image exitButton = new Image(new FileInputStream("src/view/sources/loginMenu/buttons/button_close.png"));
        ImageView exitView = new ImageView(exitButton);
        exitViewSetting(loginScene, exitView);
        onLogin.getChildren().addAll(onLoginCircle, loginLabel);
        onSignUp.getChildren().addAll(onSignUpCircle, signUpLabel);
        //loginLabel.relocate();
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        textBoxesDesign(root, loginScene, width, usernameField, passwordField);
        // login and sign up so called buttons
        StackPane login = new StackPane();
        Image loginButtonImage = new Image(new FileInputStream("src/view/sources/loginMenu/buttons/login.png"));
        Image onLoginButtonImage = new Image(new FileInputStream("src/view/sources/loginMenu/buttons/login_glow.png"));
        ImageView loginButton = new ImageView(loginButtonImage);
        adjustButton(font, loginScene, width, login, loginButton, "Login");
        StackPane signUp = new StackPane();
        Image signUpButtonImage = new Image(new FileInputStream("src/view/sources/loginMenu/buttons/signUp.png"));
        Image onSignUpButtonImage = new Image(new FileInputStream("src/view/sources/loginMenu/buttons/signUp_glow.png"));
        ImageView signUpButton = new ImageView(signUpButtonImage);
        adjustButton(font, loginScene, width, signUp, signUpButton, "Sign Up");
        initCreateAccountAndLoginBarsDesign(onLoginCircle, onSignUpCircle, login, signUp);
        GraphicalCommonUsages.addOnMouseEnterAndExitHandler(login, loginButton, onLoginButtonImage, loginButtonImage);
        handleLoginSubmission(usernameField, passwordField, login, loginScene, root);
        GraphicalCommonUsages.addOnMouseEnterAndExitHandler(signUp, signUpButton, onSignUpButtonImage, signUpButtonImage);
        handleSignUpSubmission(onLoginCircle, onSignUpCircle, usernameField, passwordField, login, signUp,loginScene,root);
        manageCreateAccountAndLoginBars(onLogin, onSignUp, onLoginCircle, onSignUpCircle, login, signUp);
        root.getChildren().addAll(login, signUp, onLogin, onSignUp, exitView);
        //  letterbox(loginScene, root);
        return root;
    }

    private void exitViewSetting(Scene loginScene, ImageView exitView) {
        exitView.relocate(loginScene.getWidth() * 15 / 16, 0);
        exitView.setFitWidth(loginScene.getWidth() / 16);
        exitView.setPreserveRatio(true);
        exitView.setOpacity(0.5);
        exitView.setOnMouseEntered(event -> exitView.setOpacity(0.9));
        exitView.setOnMouseExited(event -> exitView.setOpacity(0.5));
        exitView.setOnMouseClicked(event -> System.exit(0));
    }

    private void createAccountAndLoginBarsContainerDesign(Pane root, Scene loginScene) {
        Rectangle container = new Rectangle(loginScene.getWidth() * 13 / 60, loginScene.getHeight() / 2);
        container.relocate(loginScene.getWidth() * 29 / 40, loginScene.getHeight() / 2.4);
        container.setFill(Color.rgb(20, 50, 100, 0.4));
        container.setArcHeight(10);
        container.setArcWidth(10);
        root.getChildren().add(container);
    }

    private void textBoxesDesign(Pane root, Scene loginScene, double width, TextField textField, PasswordField passwordField) {
        passwordField.setPromptText("Password");
        textField.setPrefWidth(width);
        passwordField.setPrefWidth(width);
        textField.relocate(loginScene.getWidth() * 3 / 4, loginScene.getHeight() / 2);
        fadingIn(textField, passwordField);
        textField.setOpacity(0.3);
        textField.setStyle("-fx-background-color:rgba(175, 175, 175, 0.5) ; -fx-font-size:  20px; ");
        passwordField.setStyle("-fx-background-color:rgba(175,175,175,0.5) ; -fx-font-size:  20px; ");
        passwordField.relocate(loginScene.getWidth() * 3 / 4, loginScene.getHeight() / 1.5);
        root.getChildren().addAll(passwordField, textField);
    }

    private void initCreateAccountAndLoginBarsDesign(Circle onLoginCircle, Circle onSignUpCircle, StackPane login, StackPane signUp) {
        if (isInLogin) {
            onLoginCircle.setVisible(true);
            login.setVisible(true);
        } else {
            onLoginCircle.setVisible(false);
            login.setVisible(false);
        }
        if (isInSignUp) {
            onSignUpCircle.setVisible(true);
            signUp.setVisible(true);
        } else {
            onSignUpCircle.setVisible(false);
            signUp.setVisible(false);
        }
    }

    private void handleSignUpSubmission(Circle onLoginCircle, Circle onSignUpCircle, TextField textField, PasswordField passwordField, StackPane login, StackPane signUp, Scene scene, Pane root) {
        signUp.setOnMouseClicked(event -> {
            if (signUp.isVisible()) {
                try {
                    if (textField.getText().length() == 0 || passwordField.getText().length() == 0)
                        GraphicalCommonUsages.okPopUp("username and password should not be empty", scene, root);
                    else {
                        int result = LoginMenuProcess.createAccount(textField.getText(), passwordField.getText());
                        if (result == 1)
                            GraphicalCommonUsages.okPopUp("a user with this username already exists", scene, root);
                        if (result == 0) {
                            System.out.println("account created");
                            if (!isInLogin) {
                                loginAndCreateAccountBarManager(login, signUp, onSignUpCircle, onLoginCircle, true);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleLoginSubmission(TextField textField, PasswordField passwordField, StackPane login, Scene scene, Pane root) {
        login.setOnMouseClicked(event -> {
            if (login.isVisible()) {
                LoginMenuProcess loginMenuProcess = new LoginMenuProcess();
                try {
                    int loginCheck = loginMenuProcess.login(textField.getText(), passwordField.getText());
                    if (loginCheck == 0) {
                        System.out.println("logged in");
                        Main.setMainMenuFX(loginMenuProcess.getCurrentAccount());
                    }
                    if (loginCheck == 2 || loginCheck == 3) {
                        GraphicalCommonUsages.okPopUp("incorrect username or password", scene, root);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void manageCreateAccountAndLoginBars(StackPane onLogin, StackPane onSignUp, Circle onLoginCircle, Circle onSignUpCircle, StackPane login, StackPane signUp) {
        onSignUp.setOnMouseClicked(event -> {
            if (!isInSignUp) {
                clickSoundEffectPlay("pointdrop");
                loginAndCreateAccountBarManager(login, signUp, onSignUpCircle, onLoginCircle, false);
            }
        });
        onLogin.setOnMouseClicked(event -> {
            if (!isInLogin) {
                clickSoundEffectPlay("pointdrop");
                loginAndCreateAccountBarManager(login, signUp, onSignUpCircle, onLoginCircle, true);
            }
        });
    }

    private void loginAndCreateAccountBarManager(StackPane login, StackPane signUp, Circle onSignUpCircle, Circle onLoginCircle, boolean setOnLogin) {
        login.setVisible(setOnLogin);
        signUp.setVisible(!setOnLogin);
        onSignUpCircle.setVisible(!setOnLogin);
        onLoginCircle.setVisible(setOnLogin);
        isInLogin = setOnLogin;
        isInSignUp = !setOnLogin;
    }


    private void adjustButton(Font font, Scene loginScene, double width, StackPane buttonPane, ImageView button, String buttonText) {
        button.relocate(loginScene.getWidth() * 3 / 4 + width / 4, loginScene.getHeight() / 1.25);
        button.setFitWidth(width / 2);
        button.setFitHeight(loginScene.getHeight() / 15);
        Text loginText = new Text(buttonText);
        loginText.setFont(font);
        loginText.setFill(Color.rgb(240, 240, 240));
        buttonPane.getChildren().addAll(button, loginText);
        buttonPane.setLayoutX(loginScene.getWidth() * 3 / 4 + width / 4);
        buttonPane.setLayoutY(loginScene.getHeight() / 1.25);
        loginText.relocate(loginScene.getWidth() * 3 / 4 + width / 4, loginScene.getHeight() / 1.25);
    }


    private void fadingIn(TextField textField, PasswordField passwordField) {
        FadeTransition textFieldFadeIn = new FadeTransition(Duration.millis(3000));
        FadeTransition passwordFadeIn = new FadeTransition(Duration.millis(3000));
        textFieldFadeIn.setNode(textField);
        textField.setVisible(true);
        passwordFadeIn.setNode(passwordField);
        passwordField.setVisible(true);
        textFieldFadeIn.setFromValue(0.0);
        textFieldFadeIn.setToValue(1);
        textFieldFadeIn.setCycleCount(1);
        textFieldFadeIn.setAutoReverse(false);
        textFieldFadeIn.playFromStart();

        passwordFadeIn.setFromValue(0.0);
        passwordFadeIn.setToValue(1);
        passwordFadeIn.setCycleCount(1);
        passwordFadeIn.setAutoReverse(false);
        passwordFadeIn.playFromStart();

    }

    private void clickSoundEffectPlay(String name) {
        javafx.scene.media.AudioClip audioClip = new javafx.scene.media.AudioClip(this.getClass().getResource("sources/loginMenu/music/" + name + ".m4a").toString());
        audioClip.setCycleCount(1);
        audioClip.play();
    }


}
