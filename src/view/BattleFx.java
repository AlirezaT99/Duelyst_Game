package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BattleFx {
    Pane start(char storyMode) throws FileNotFoundException {
        Pane pane = new Pane();
        GraphicalCommonUsages.setBackGroundImage("src/view/sources/Battle/BattlePictures/Arena/a/a.png",pane);
        return pane;
    }

    private void setBattleBackGround(ImageView backGround, ImageView foreGround, ImageView midGround, ImageView[] images){
        Pane root = new Pane();
        root.getChildren().addAll(backGround,midGround,foreGround);
        for (ImageView imageView: images            ) {
            root.getChildren().addAll(imageView);
        }

    }
}

