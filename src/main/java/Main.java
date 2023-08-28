import javafx.scene.ImageCursor;
import javafx.scene.layout.*;
import support.Settings;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import menuPackage.MainMenu;
import menuPackage.PauseMenu;

/**
 * Questa è la classe principale dell'applicazione Klotski.
 */
public class Main extends Application {
    /**
     * Metodo principale per avviare l'applicazione Klotski.
     * @param args Argomenti della riga di comando (non utilizzati).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Metodo chiamato quando l'applicazione Klotski viene avviata.
     * @param stage Lo stage principale dell'applicazione.
     */
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
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(mediaPlayer.getStartTime());   // reimposta la posizione del MediaPlayer sulla posizione iniziale
            mediaPlayer.play();                             // avvia la riproduzione
        });
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

        MainMenu  menuM = new MainMenu (root, mediaPlayer);
        PauseMenu menuP = new PauseMenu(root, mediaPlayer);

        menuM.useNewGame    ( menuP, backgroundGif, scene);
        menuM.useLoad       (menuP, backgroundGif,scene);


        root.getChildren().add(menuM.getMenu());

        scene.getStylesheets().addAll(Settings.CSS_BUTTON_FILE, Settings.CSS_LABEL_FILE);   //roba di game

        //stage
        stage.setTitle("KLOTSKI");
        stage.setScene(scene);

        Image image = new Image(Settings.CURSOR_IMAGE);
        stage.getScene().setCursor(new ImageCursor(image, image.getWidth()/4, 0));
        stage.getScene();
        stage.setResizable(false);
        stage.show();
    }
}
