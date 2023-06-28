package org.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.gamePackage.Game;
import org.example.support.Vector2;

public class GameTestMain extends Application{
    Game game;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {

        game = new Game(stage);

        Group root = game.getGroup();
        Scene scene = new Scene(root, 550, 700);

        //Vector2 v1 = new Vector2(100,200), v2 = new Vector2(90,210);
        //System.out.println(v1.isBetweenClosed(v2) || v2.isBetween(v1));
        //StackPane stackPane = new StackPane();
        //stackPane.getChildren().add(root);
        game.setScene(scene);
        game.startGame();

        //Scene scene = new Scene(stackPane, 550, 700);
        stage.setTitle("KLOTSKI");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}