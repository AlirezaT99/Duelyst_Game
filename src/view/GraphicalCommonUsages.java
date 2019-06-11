package view;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GraphicalCommonUsages {
    public static void addOnMouseEnterAndExitHandler(Node area, ImageView changeableArea, Image firstImage, Image seoncImage) {
        area.setOnMouseEntered(event -> changeableArea.setImage(firstImage));
        area.setOnMouseExited(event -> changeableArea.setImage(seoncImage));
    }

    public static void addOnMouseEnterAndExitHandler(Node area, ImageView changeableArea, String firstImageAddress, String secondImageAddress) throws FileNotFoundException {
        Image image1 = new Image(new FileInputStream(firstImageAddress));
        Image image2 = new Image(new FileInputStream(secondImageAddress));
        addOnMouseEnterAndExitHandler(area, changeableArea, image1, image2);
    }

    public static void backGroundImageSetting(Image backGroundImage, Pane root) {
        ImageView backGround = new ImageView(backGroundImage);
        backGround.setFitWidth(root.getWidth());
        backGround.setFitHeight(root.getHeight());
        root.getChildren().add(backGround);
    }

    public static void setBackGroundImage(String imageAddress, Pane root) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(imageAddress));
        backGroundImageSetting(image, root);
    }

    public static Scene getOkayPopUp(Scene scene, String message) throws FileNotFoundException {
        Group root = new Group();
        Scene result = new Scene(root,scene.getWidth()/3,scene.getHeight()/4);
        VBox popUp = new VBox();
        Text messageText = new Text();
        messageText.relocate(0,0);
        HBox firstLine = new HBox(messageText);
        Image okButton = new Image(new FileInputStream("src/view/sources/mainMenu/utility_menu/button_confirm.png"));
        ImageView okButtonView = new ImageView(okButton);
        okButtonView.setFitWidth(result.getWidth()/4);
        HBox secondLine = new HBox(okButtonView);
        popUp.getChildren().addAll(firstLine,secondLine);
        root.getChildren().addAll(popUp);
        return result;
    }
    public static Rectangle2D initPrimaryStage(Stage primaryStage) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreenDevice = ge.getDefaultScreenDevice();
        GraphicsConfiguration defaultConfiguration = defaultScreenDevice.getDefaultConfiguration();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(defaultConfiguration);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth() + screenInsets.right + screenInsets.left);
        primaryStage.setHeight(primaryScreenBounds.getHeight() + screenInsets.bottom + screenInsets.top);
        primaryStage.setFullScreen(true);
        return primaryScreenBounds;
    }

}