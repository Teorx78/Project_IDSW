package menuPackage;

import game.Board;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import static json.JsonSave.getNumberSave;

public class SaveMenu extends Menu {
    protected static Button[] saves = new Button[10];

    public SaveMenu(StackPane root,PauseMenu menuP, BackgroundImage backgroundGif, Scene scene) {
        super(root);
        VBox menuV = new VBox(10);
        for (int n = 0; n < getNumberSave(); n++) {
            saves[n] = new Button();
            menuV.getChildren().add(getSaveButton(n,menuP, backgroundGif, scene));
        }
        menu.getChildren().add(menuV);
        menu.setMaxSize(400, 500);
        menu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");

    }

    public Button getSaveButton(int saveNumber,PauseMenu menuP, BackgroundImage backgroundGif, Scene scene) {
        String buttonName = "Safe" + saveNumber;
        saves[saveNumber].setText(buttonName);
        create_button(saves[saveNumber], 90, 35);
        EventHandler<ActionEvent> event = e -> {
            Board savedGame = new Board(saveNumber + 1);
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

