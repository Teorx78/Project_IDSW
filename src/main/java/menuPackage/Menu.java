package menuPackage;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import support.Settings;
import javafx.scene.control.Slider;
import javafx.geometry.Orientation;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
/**
 * La classe Menu rappresenta un'interfaccia grafica di menu per il controllo delle impostazioni di gioco.
 * Fornisce opzioni per uscire dal gioco e regolare il livello del volume.
 */
public class Menu {
    protected  Button   quit_Game   = new Button(null),
                        volume      = new Button(null);
    protected static Slider slider;
    protected final BorderPane menu = new BorderPane();
    protected final  StackPane root;
    /**
     * Costruisce un oggetto Menu con il pannello radice specificato.
     *
     * @param root Lo StackPane radice utilizzato per la navigazione tra schermate diverse.
     */
    public Menu (StackPane root)
    { this.root = root; }
    /**
     * Recupera l'interfaccia grafica del menu.
     *
     * @return Il BorderPane che rappresenta il menu grafico.
     */
    public BorderPane getMenu()
    { return menu; }
    /**
     * Configura un pulsante con una dimensione e un aspetto specifici.
     *
     * @param button Il pulsante da configurare.
     * @param x      La larghezza del pulsante.
     * @param y      L'altezza del pulsante.
     */
    protected static void create_button(Button button, int x, int y)
    {
        button.setMinSize(x, y);
        button.setMaxSize(x, y);
        Font font = Font.font("Verdana", 17); // FontWeight.BOLD, per il grassetto
        button.setFont(font);
        button.setStyle("-fx-background-color: #0062ff; -fx-text-fill: #ffffff;");
    }
    /**
     * Crea e configura un pulsante "Esci dal gioco" con la funzionalità di uscire dall'applicazione.
     *
     * @return Il pulsante "Esci dal gioco" configurato.
     */
    public Button getQuit_Game()
    {
        quit_Game.setText("Quit Game");
        create_button(quit_Game, 180, 75);
        EventHandler<ActionEvent> event = e -> {
            System.out.println("quit");
            Platform.exit();
        };
        quit_Game.setOnAction(event);
        return quit_Game;
    }
    /**
     * Crea e configura un pulsante di controllo del volume con uno slider per regolare il livello del volume.
     *
     * @param gameRoot Il pannello radice del gioco.
     * @param mediaPlayer Il MediaPlayer utilizzato per la riproduzione audio.
     * @return Il pulsante di controllo del volume configurato.
     */
    public Button getVolume(StackPane gameRoot, MediaPlayer mediaPlayer)
    {
        volume.setId("volume");

        Image volumeIcon = new Image(Settings.SOUND_IMAGE_PATH, 55, 55, true, true);
        ImageView volumeImageView = new ImageView(volumeIcon);
        volume.setGraphic(volumeImageView);
        volume.setStyle("-fx-background-color: transparent;");


        slider = new Slider(0, 1, 0.5);
        EventHandler<ActionEvent> event = e -> {
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            slider.setMajorTickUnit(2);
            slider.setBlockIncrement(1);
            slider.setShowTickMarks(false);
            slider.setShowTickLabels(false);
            slider.setOrientation(Orientation.VERTICAL);
            //stile
            Text text1 = new Text();
            slider.skinProperty().addListener((obs, old, skin) -> {
                if (skin != null) {
                    StackPane thumb = (StackPane) slider.lookup(".thumb");
                    thumb.setPadding(new Insets(10));
                    thumb.getChildren().add(text1);
                    thumb.setStyle("-fx-background-color: #FFFFFF;");
                    thumb.setStyle("-fx-border-color: #000000; -fx-border-width: 4px;");
                }
            });

            Text text2 = new Text();
            slider.skinProperty().addListener((obs, old, skin) -> {
                if (skin != null) {
                    StackPane track = (StackPane) slider.lookup(".track");
                    track.setPadding(new Insets(5));
                    track.getChildren().add(text2);
                    track.setStyle("-fx-background-color: #ABCDEF;");
                }
            });
            //end stile
            BorderPane volumeSlider = new BorderPane();
            volumeSlider.setTop(slider);
            BorderPane.setMargin(slider, new Insets(560, 150, 150, 500));

            DoubleProperty volumeProperty = new SimpleDoubleProperty();
            // Aggiornamento dell'etichetta ad ogni spostamento dello slider
            slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                mediaPlayer.setVolume(newValue.doubleValue());
                volumeProperty.set(newValue.doubleValue());
            });

            volumeSlider.setOnMousePressed(event2 -> {
                int numChildren = gameRoot.getChildren().size();
                if (numChildren > 1) {
                    gameRoot.getChildren().remove(numChildren - 1);
                }
            });
            gameRoot.getChildren().add(volumeSlider);
        };
        volume.setOnAction(event);
        return volume;
    }
}

