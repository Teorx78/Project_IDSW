package menuPackage;

import game.Board;
import game.Reset;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import support.DuplicateMap;
import support.Settings;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

public class WinMenu extends Menu {
    public WinMenu(StackPane root)// throws FileNotFoundException
    {
        super(root);
        Image newImage = new Image(Settings.TITLE_WIN_IMAGE_PATH, 550, 250, true, true);
        ImageView winMenuTitle = new ImageView(newImage);

        VBox menuV = new VBox(30);
        menuV.getChildren().addAll(winMenuTitle);
        menuV.setAlignment(Pos.BASELINE_CENTER);
        BorderPane.setMargin(menuV, new Insets(150,0,0,0));

        menu.setStyle("-fx-background-color: rgba(0, 0, 255, 0.3);");
        menu.setTop(menuV);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            int n = root.getChildren().size();
            root.getChildren().remove(n-2, n);
            System.out.println("back to main menu");
            /*Reset.resetBoard(blocks, config);
            chronology = new DuplicateMap();
            resetButton.setDisable(true);
            undoButton.setDisable(true);
            nbmButton.setDisable(false);
            moves = 0;
            MOVES_COUNTER = 0;
            movesLabel.setText("MOSSE: " + moves);*/
        });
        pause.play();
    }


}
