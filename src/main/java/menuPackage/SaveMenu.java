package menuPackage;

import game.Board;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import support.Settings;

import static json.JsonSave.getNumberSave;

public class SaveMenu extends Menu {
    private static final Button[] saves = new Button[100];
    private final Button back =new Button();
    public SaveMenu(StackPane root,PauseMenu menuP, BackgroundImage backgroundGif, Scene scene)                 //crea l'elenco dei salvataggi possibili
    {
        super(root);
        VBox menuV = new VBox(10);
        for (int n = 0; n < getNumberSave(); n++) {
            saves[n] = new Button();
            menuV.getChildren().add(getSaveButton(n,menuP, backgroundGif, scene));
        }
        menu.setLeft(menuV);
        BorderPane.setMargin(menuV, new Insets(10, 10, 10, 10));

        menu.setRight(getBack());
        menu.setMaxSize(400, 500);
        menu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
    }
    private Button getBack()                                                                                    //crea il pulsante per chiudere l'elenco dei salvataggi
    {
        Image backIcon = new Image(Settings.X_PATH, 55, 55, true, true);
        ImageView backImageView = new ImageView(backIcon);
        back.setGraphic(backImageView);
        back.setStyle("-fx-background-color: transparent;");
        EventHandler<ActionEvent> event = e -> {
            root.getChildren().remove(menu);
        };
        back.setOnAction(event);
        return back;
    }
    private Button getSaveButton(int saveNumber, PauseMenu menuP, BackgroundImage backgroundGif, Scene scene)   //da un nome per differenziare i vari salvataggi e fa riprendere la partita partendo dal salvataggio
    {
        int n = saveNumber+1;
        String buttonName = "Save" + n;
        saves[saveNumber].setText(buttonName);
        create_button(saves[saveNumber], 90, 35);
        EventHandler<ActionEvent> event = e -> {
            Board savedGame = new Board(saveNumber);
            root.getChildren().remove(menu);
            root.getChildren().add(savedGame.createBoard(root, backgroundGif));
            savedGame.usePauseButton (root, menuP.getMenu(), menuP.getResume());
            menuP.useResume     (savedGame.getPauseButton());
            menuP.useSave(savedGame);
            savedGame.setScene(scene);
            savedGame.startGame(root);
            System.out.println(buttonName);
        };
        saves[saveNumber].setOnAction(event);
        return saves[saveNumber];
    }
}

