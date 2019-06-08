package view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GraphicalCommonUsages {
    public static void addOnMouseEnterAndExitHandler(Node area, ImageView changeableArea, Image firstImage, Image seoncImage){
        area.setOnMouseEntered(event -> changeableArea.setImage(firstImage));
        area.setOnMouseExited(event -> changeableArea.setImage(seoncImage));
    }
    public static void setBackGroundImage(Image backGroundImage, Pane root, Stage stage){
        ImageView backGround = new ImageView(backGroundImage);
        backGround.setFitWidth(stage.getWidth());
        backGround.setFitHeight(stage.getHeight());
        root.getChildren().add(backGround);
    }

    public static void setBackGroundImage(String imageAddress,Pane root, Stage stage) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(imageAddress));
        setBackGroundImage(image,root,stage);
    }
}