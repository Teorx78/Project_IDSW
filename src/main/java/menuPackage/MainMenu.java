package menuPackage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.FileNotFoundException;

import game.Board;
import javafx.scene.Group;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.image.Image;

import javafx.scene.layout.*;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;

import javafx.geometry.Insets;

import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import game.Game;
import support.Settings;

public class MainMenu extends Menu {
    protected static Button newGame = new Button(null),
                            credits = new Button(null),
                            load    = new Button(null);

    public MainMenu(StackPane root, MediaPlayer song) {
        super(root);
        Image newImage = new Image(Settings.TITLE_IMAGE_PATH, 360, 150, true, true);
        ImageView mainMenuTitle = new ImageView(newImage);

        VBox menuV = new VBox(30);
        menuV.getChildren().addAll(mainMenuTitle, getNewGame(),getLoad(),getQuit_Game());
        menuV.setAlignment(Pos.BASELINE_CENTER);
        BorderPane.setMargin(menuV, new Insets(15,0,0,0));

        HBox menuH = new HBox(300);
        menuH.getChildren().addAll(getCredits(), getVolume(root, song));
        BorderPane.setMargin(menuH, new Insets(0,0,30,45));

        menu.setTop(menuV);
        menu.setBottom(menuH);
        //menu.setMaxSize(550,700);
    }
    public void useNewGame(PauseMenu menuP, BackgroundImage backgroundGif, Scene scene){
        ConfigMenu menuC =new ConfigMenu(root);
        EventHandler<ActionEvent> event = e -> {

            root.getChildren().add(menuC.getMenu());
            for(int n=0;n<6;n++)
                menuC.useConfigButton(menuP, backgroundGif, scene, n);

        };
        newGame.setOnAction(event);
    }
    public void useLoad(PauseMenu menuP,BackgroundImage backgroundGif,Scene scene){
        EventHandler<ActionEvent> event = e -> {
            SaveMenu menuS =new SaveMenu(root, menuP, backgroundGif,scene);
            root.getChildren().add(menuS.getMenu());
        };
        load.setOnAction(event);
    }
    public Button getNewGame(){
        newGame.setText("New Game");
        create_button(newGame, 180, 75);
        return newGame;
    }
    public Button getCredits(){
        credits.setText("Credits");
        create_button(credits, 100, 55);
        EventHandler<ActionEvent> event = e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/Teorx78/Project_IDSW"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        };
        credits.setOnAction(event);
        return credits;
    }
    public Button getLoad(){
        load.setText("Load");
        create_button(load, 180, 75);
        return load;
    }
}