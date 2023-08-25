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

public class Menu extends StackPane {
    protected  Button   quit_Game   = new Button(null),
                        volume      = new Button(null);
    protected static Slider slider;
    protected final BorderPane menu = new BorderPane();
    protected final  StackPane root;
    public Menu (StackPane root)                                            //salva il root essenziale per passare da una scheda all'altra
    { this.root = root; }
    public BorderPane getMenu()                                             //restituisce il menu
    { return menu; }
    protected static void create_button(Button button, int x, int y)        //permette di impostare tutti i pulsanti seguendo uno standard
    {
        button.setMinSize(x, y);
        button.setMaxSize(x, y);
        Font font = Font.font("Verdana", 17); // FontWeight.BOLD, per il grassetto
        button.setFont(font);
        button.setStyle("-fx-background-color: #0062ff; -fx-text-fill: #ffffff;");
    }
    public Button getQuit_Game()                                            //restituisce il pulsante per uscire dal gioco funzionante
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
    public Button getVolume(StackPane gameRoot, MediaPlayer mediaPlayer)    //restituisce il pulsante volume funzionante che permette di alzare o abbassare il volume
    {
        volume.setId("volume");

        Image volumeIcon = new Image(Settings.SOUND_IMAGE_PATH, 55, 55, true, true);
        ImageView volumeImageView = new ImageView(volumeIcon);
        volume.setGraphic(volumeImageView);
        volume.setStyle("-fx-background-color: transparent;");


        slider = new Slider(0, 1, 1);
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

