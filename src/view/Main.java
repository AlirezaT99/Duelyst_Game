package view;

import com.sun.scenario.effect.impl.prism.PrImage;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main  extends Application {
    private static LoginMenu loginMenu = new LoginMenu();

    public static void main (String[] args) throws IOException {
            presenter.MainProcess.readFiles();
            launch(args);
            loginMenu.run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Login login = new Login();
        Scene currentScene = login.start(primaryStage);
        primaryStage.setScene(currentScene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        primaryStage.show();;

    }
}
