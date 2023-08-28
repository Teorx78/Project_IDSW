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
/**
 * La classe SaveMenu rappresenta il menu per la visualizzazione e il caricamento dei salvataggi di gioco.
 */
public class SaveMenu extends Menu {
    private static final Button[] saves = new Button[100];
    private final Button back =new Button();
    /**
     * Costruisce il menu per la gestione dei salvataggi di gioco.
     *
     * @param root Il pannello radice dell'interfaccia di gioco.
     * @param menuP Il menu di pausa.
     * @param backgroundGif L'immagine di sfondo animata.
     * @param scene La scena corrente.
     */
    public SaveMenu(StackPane root,PauseMenu menuP, BackgroundImage backgroundGif, Scene scene)
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
    /**
     * Crea il pulsante per chiudere il menu dei salvataggi.
     *
     * @return Il pulsante per chiudere il menu dei salvataggi.
     */
    private Button getBack()
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
    /**
     * Crea il pulsante per selezionare e caricare un salvataggio di gioco.
     *
     * @param saveNumber Il numero del salvataggio.
     * @param menuP Il menu di pausa.
     * @param backgroundGif L'immagine di sfondo animata.
     * @param scene La scena corrente.
     * @return Il pulsante per caricare il salvataggio.
     */
    private Button getSaveButton(int saveNumber, PauseMenu menuP, BackgroundImage backgroundGif, Scene scene)
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

