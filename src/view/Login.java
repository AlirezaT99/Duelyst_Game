package view;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.ImageCursor;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import presenter.LoginMenuProcess;

import java.io.FileInputStream;
import java.util.Random;

class SceneSizeChangeListener implements ChangeListener<Number> {
    private final Scene scene;
    private final double ratio;
    private final double initHeight;
    private final double initWidth;
    private final Pane contentPane;

    public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
        this.scene = scene;
        this.ratio = ratio;
        this.initHeight = initHeight;
        this.initWidth = initWidth;
        this.contentPane = contentPane;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        final double newWidth = scene.getWidth();
        final double newHeight = scene.getHeight();

        double scaleFactor =
                newWidth / newHeight > ratio
                        ? newHeight / initHeight
                        : newWidth / initWidth;

        if (scaleFactor >= 1) {
            Scale scale = new Scale(scaleFactor, scaleFactor);
            scale.setPivotX(0);
            scale.setPivotY(0);
            scene.getRoot().getTransforms().setAll(scale);

            contentPane.setPrefWidth(newWidth / scaleFactor);
            contentPane.setPrefHeight(newHeight / scaleFactor);
        } else {
            contentPane.setPrefWidth(Math.max(initWidth, newWidth));
            contentPane.setPrefHeight(Math.max(initHeight, newHeight));
        }
    }
}

public class Login extends Application {
    boolean isInLogin = true;
    boolean isInSignUp = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        Rectangle2D primaryScreenBounds = initPrimaryStage(primaryStage);
        final Font font = Font.loadFont(new FileInputStream(new File("src/view/sources/loginMenu/fonts/TrumpGothicPro-Medium-webfont.ttf")), 22);
        Pane root = new Pane();
        Scene loginScene = new Scene(root, primaryScreenBounds.getMaxX(), primaryScreenBounds.getMaxY());
        primaryStage.setScene(loginScene);
        Random random = new Random();
        int backGroundNumber = random.nextInt(24) + 1;
        primaryStage.setTitle(backGroundNumber + "");
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/loginMenu/backgrounds/" + backGroundNumber + ".jpg",root,primaryStage);
        Image logo = new Image(new FileInputStream("src/view/sources/loginMenu/backgrounds/duelyst.png"));
        ImageView logoImageView = new ImageView(logo);
        logoImageView.relocate(0, 0);
        logoImageView.setFitWidth(loginScene.getWidth() / 2.5);
        logoImageView.setFitHeight(loginScene.getHeight() / 4);
        root.getChildren().addAll(logoImageView);
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> primaryStage.setFullScreen(true));
        Image cursor = new Image(new FileInputStream("src/view/sources/loginMenu/cursor/auto.png"));
        loginScene.setCursor(new ImageCursor(cursor));
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
        onLogin.getChildren().addAll(onLoginCircle, loginLabel);
        onSignUp.getChildren().addAll(onSignUpCircle, signUpLabel);
        //loginLabel.relocate();
        TextField textField = new TextField("Username");
        PasswordField passwordField = new PasswordField();
        textBoxesDesign(root, loginScene, width, textField, passwordField);
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
        GraphicalCommonUsages.addOnMouseEnterAndExitHandler(login,loginButton,onLoginButtonImage,loginButtonImage);
        handleLoginSubmission(textField, passwordField, login);
        GraphicalCommonUsages.addOnMouseEnterAndExitHandler(signUp,signUpButton,onSignUpButtonImage,signUpButtonImage);
        handleSignUpSubmission(onLoginCircle, onSignUpCircle, textField, passwordField, login, signUp);
        manageCreateAccountAndLoginBars(onLogin, onSignUp, onLoginCircle, onSignUpCircle, login, signUp);
        root.getChildren().addAll(login, signUp, onLogin, onSignUp);
        letterbox(loginScene, root);
        backgroundMusicPlay();
        primaryStage.show();
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
        root.getChildren().addAll(passwordField,textField);
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

    private void handleSignUpSubmission(Circle onLoginCircle, Circle onSignUpCircle, TextField textField, PasswordField passwordField, StackPane login, StackPane signUp) {
        signUp.setOnMouseClicked(event -> {
            if (signUp.isVisible()) {
                try {
                    //todo : check if the password is not empty and return the according alert otherwise.
                    if (LoginMenuProcess.createAccount(textField.getText(), passwordField.getText()) == 0) {
                        System.out.println("account created");
                        if (!isInLogin) {
                            loginAndCreateAccountBarManager(login, signUp, onSignUpCircle, onLoginCircle,true);
                        }
                    } else {
                        //todo : alerts
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleLoginSubmission(TextField textField, PasswordField passwordField, StackPane login) {
        login.setOnMouseClicked(event -> {
            if (login.isVisible()) {
                LoginMenuProcess loginMenuProcess = new LoginMenuProcess();
                try {
                    //todo : check if the password is not empty and return the according alert otherwise.
                    if (loginMenuProcess.login(textField.getText(), passwordField.getText()) == 0) {
                        System.out.println("logged in");
                        //todo : change the fucking scene to main menu
                    } else {
                        //todo : alerts
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
                clickSoundEffectPlay();
                loginAndCreateAccountBarManager(login,signUp,onSignUpCircle,onLoginCircle,false);
            }
        });
        onLogin.setOnMouseClicked(event -> {
            if (!isInLogin) {
                clickSoundEffectPlay();
                loginAndCreateAccountBarManager(login, signUp, onSignUpCircle, onLoginCircle,true);
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

    private Rectangle2D initPrimaryStage(Stage primaryStage) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreenDevice = ge.getDefaultScreenDevice();
        GraphicsConfiguration defaultConfiguration = defaultScreenDevice.getDefaultConfiguration();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(defaultConfiguration);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth() + screenInsets.right + screenInsets.left);
        primaryStage.setHeight(primaryScreenBounds.getHeight() + screenInsets.bottom);
        primaryStage.setFullScreen(true);
        return primaryScreenBounds;
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

    private void clickSoundEffectPlay() {
        javafx.scene.media.AudioClip audioClip = new javafx.scene.media.AudioClip(this.getClass().getResource("sources/loginMenu/music/pointdrop.m4a").toString());
        audioClip.setCycleCount(1);
        audioClip.play();
    }

    private void backgroundMusicPlay() {
        javafx.scene.media.AudioClip audioClip = new javafx.scene.media.AudioClip(this.getClass().getResource("sources/loginMenu/music/mainmenu_v2c_looping.m4a").toString());
        audioClip.setCycleCount(Integer.MAX_VALUE);
        audioClip.play();
    }

    private void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }
}
