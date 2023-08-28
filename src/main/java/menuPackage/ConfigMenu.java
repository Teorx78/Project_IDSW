package menuPackage;

import game.Board;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import support.Settings;
/**
 * La classe ConfigMenu rappresenta un menu per selezionare configurazioni di gioco.
 * Fornisce pulsanti con immagini per scegliere la configurazione desiderata.
 */
public class ConfigMenu extends Menu {
    private static final Button[] C = new Button[6];
    private static String configuration = "";
    /**
     * Costruisce un menu per selezionare la  configurazione del gioco desiderata.
     *
     * @param root Il pannello radice dell'interfaccia di gioco.
     */
    public ConfigMenu(StackPane root)
    {
        super(root);

        for (int n = 0; n < 6; n++) {
            C[n] = new Button();
            createConfigButton(n);
        }

        VBox menu1 = new VBox(15);
        menu1.getChildren().addAll(C[0], C[1], C[2]);
        BorderPane.setMargin(menu1, new Insets(10, 10, 10, 30));//up right down left

        VBox menu2 = new VBox(15);
        menu2.getChildren().addAll(C[3], C[4], C[5]);
        BorderPane.setMargin(menu2, new Insets(10, 30, 10, 10));//up right down left
        menu.setLeft(menu1);
        menu.setRight(menu2);
        menu.setMaxSize(400, 500);
        menu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
    }
    /**
     * Crea un pulsante con l'immagine della configurazione corrispondente.
     *
     * @param n L'indice del pulsante.
     */
    private void createConfigButton(int n)
    {
        int confNumber = n + 1;
        String ConfPath = Settings.CONFIGURATIONS_PATH + confNumber + ".png";
        Image ConfigIcon = new Image(ConfPath, 180, 150, true, true);
        ImageView confImageView = new ImageView(ConfigIcon);
        C[n].setGraphic(confImageView);
        C[n].setStyle("-fx-background-color: transparent;");
    }
    /**
     * Imposta la funzionalità di avvio di una partita con la configurazione selezionata.
     *
     * @param menuP Il menu di pausa.
     * @param backgroundGif L'immagine di sfondo animata.
     * @param scene La scena corrente.
     * @param n L'indice del pulsante di configurazione.
     */
    public void useConfigButton(PauseMenu menuP, BackgroundImage backgroundGif, Scene scene, int n)
    {
        int confNumbers = n+1;
        EventHandler<ActionEvent> event = e -> {
            configuration = "conf" + confNumbers;
            root.getChildren().remove(menu);
            Board game = new Board(configuration);
            root.getChildren().add(game.createBoard(root, backgroundGif));
            game.usePauseButton(root, menuP.getMenu(), menuP.getResume());
            menuP.useResume(game.getPauseButton());
            menuP.useSave(game);
            game.setScene(scene);
            game.startGame(root);
            System.out.println("New Game");


        };
        C[n].setOnAction(event);
    }
}
