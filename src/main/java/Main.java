import game.Board;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import menuPackage.*;
import support.Settings;



import javafx.geometry.Pos;
import javafx.scene.ImageCursor;


import javafx.scene.layout.*;

import support.Settings;

import java.io.File;
//import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.image.Image;

//import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;

//import javafx.application.Platform;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.util.Objects;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        //soundtrack
        File file = new File(Settings.SOUNDTRACK_PATH);
        Media media = null;			//Suondtrack
        MediaPlayer mediaPlayer;//MediaPlayer
        try {

            media = new Media(file.toURI().toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //if(media!=null) {
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(mediaPlayer.getStartTime());   // reimposta la posizione del MediaPlayer sulla posizione iniziale
            mediaPlayer.play();                             // avvia la riproduzione
        });
        //}
        // end soundtrack

        //sfondo
        Image sfondo = new Image(Settings.BACKGROUND_IMAGE_PATH);
        BackgroundImage backgroundGif = new BackgroundImage(sfondo,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        // end sfondo
        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundGif));
        Scene scene = new Scene(root, Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        //Board game =null;
        //roba di game
        MainMenu  menuM = new MainMenu (root, mediaPlayer);
        PauseMenu menuP = new PauseMenu(root, mediaPlayer);
        //ConfigMenu menuC =new ConfigMenu(root);


        menuM.useNewGame    ( menuP, backgroundGif, scene);
        menuM.useLoad       (menuP, backgroundGif,scene);
        //menuP.useResume     (game.getPauseButton());
        //menuP.useSave(game);

        root.getChildren().add(menuM.getMenu());
        //root.getChildren().add(menuP.getMenu());
        //root.getChildren().add(menuW.getMenu());
        //SaveMenu menuS =new SaveMenu(root, backgroundGif);
        //root.getChildren().add(menuS.getMenu());
        //root.getChildren().add(menuC.getMenu());







        scene.getStylesheets().addAll(Settings.CSS_BUTTON_FILE, Settings.CSS_LABEL_FILE);   //roba di game
        //game.setScene(scene);                                                           //roba di game

        //stage
        stage.setTitle("KLOTSKI");
        stage.setScene(scene);

        //Image image = new Image(Settings.CURSOR_IMAGE);
        //stage.getScene().setCursor(new ImageCursor(image, image.getWidth() / 2.7, image.getHeight() / 2.7));
        //stage.getScene().setCursor(new ImageCursor(image, image.getWidth(), image.getHeight()));
        stage.getScene();
        stage.setResizable(false);
        stage.show();
    }
}
/*
    @Override
    public void start(Stage primaryStage) throws Exception {

            MainMenu menuprincipale = new MainMenu(primaryStage, root, mediaPlayer, backgroundGif);
            BorderPane menuf =menuprincipale.getMenu();
            root.getChildren().add(menuf);
            //WinMenu menuw=new WinMenu(primaryStage, root, mediaPlayer, backgroundGif);
            //root.getChildren().add(menuw);
        }

        //bisogna collegare lo slider del main menu con quello di pausa o si lascia stare

        Scene scene = new Scene(root, 550, 700);
        primaryStage.setTitle("KLOTSKI");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}*/