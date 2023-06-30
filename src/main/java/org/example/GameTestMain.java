package org.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gamePackage.Game;
import org.example.support.Settings;

import java.util.Set;

public class GameTestMain extends Application{
    Game game;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {

        game = new Game(stage);

        Group root = game.getGroup();
//        StackPane sp = new StackPane();
//        sp.setAlignment(Pos.TOP_LEFT);

        Scene scene = new Scene(root, Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);

        game.setScene(scene);
        game.startGame();

        //Scene scene = new Scene(stackPane, 550, 700);
        stage.setTitle("KLOTSKI");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}