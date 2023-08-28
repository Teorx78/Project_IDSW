package menuPackage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.scene.media.MediaPlayer;
import support.Settings;

/**
 * La classe MainMenu rappresenta il menu principale dell'applicazione.
 * Fornisce opzioni per iniziare una nuova partita, caricare partite salvate e visualizzare i crediti.
 */
public class MainMenu extends Menu {
    private static final Button newGame = new Button(null),
                                credits = new Button(null),
                                load    = new Button(null);
    /**
     * Costruisce il menu principale da visualizzare all'avvio dell'applicazione.
     *
     * @param root Il pannello radice dell'interfaccia di gioco.
     * @param song Il MediaPlayer per la musica di sottofondo.
     */
    public MainMenu(StackPane root, MediaPlayer song)
    {
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
    /**
     * Imposta la funzionalità per avviare la selezione della configurazione di gioco.
     *
     * @param menuP Il menu di pausa.
     * @param backgroundGif L'immagine di sfondo animata.
     * @param scene La scena corrente.
     */
    public void useNewGame(PauseMenu menuP, BackgroundImage backgroundGif, Scene scene)
    {
        ConfigMenu menuC =new ConfigMenu(root);
        EventHandler<ActionEvent> event = e -> {

            root.getChildren().add(menuC.getMenu());
            for(int n=0;n<6;n++)
                menuC.useConfigButton(menuP, backgroundGif, scene, n);

        };
        newGame.setOnAction(event);
    }
    /**
     * Imposta la funzionalità per aprire il menu di caricamento delle partite salvate.
     *
     * @param menuP Il menu di pausa.
     * @param backgroundGif L'immagine di sfondo animata.
     * @param scene La scena corrente.
     */
    public void useLoad(PauseMenu menuP,BackgroundImage backgroundGif,Scene scene)
    {
        EventHandler<ActionEvent> event = e -> {
            SaveMenu menuS =new SaveMenu(root, menuP, backgroundGif,scene);
            root.getChildren().add(menuS.getMenu());
        };
        load.setOnAction(event);
    }
    /**
     * Restituisce il pulsante per aprire la selezione delle configurazioni di gioco.
     *
     * @return Il pulsante per iniziare una nuova partita.
     */
    private Button getNewGame()
    {
        newGame.setText("New Game");
        create_button(newGame, 180, 75);
        return newGame;
    }
    /**
     * Restituisce il pulsante per visualizzare i crediti dell'applicazione su GitHub.
     *
     * @return Il pulsante per i crediti.
     */
    private Button getCredits()
    {
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
    /**
     * Restituisce il pulsante per aprire il menu di caricamento delle partite salvate.
     *
     * @return Il pulsante per il caricamento delle partite.
     */
    private Button getLoad()
    {
        load.setText("Load");
        create_button(load, 180, 75);
        return load;
    }
}