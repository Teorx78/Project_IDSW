package menuPackage;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.Test;
import support.Settings;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PauseMenuTest {

    private JFXPanel jfxPanel = new JFXPanel();
    @Test
    void useResume() {
        StackPane root = new StackPane();
        BorderPane game = new BorderPane();

        PauseMenu menuP = new PauseMenu(root, new MediaPlayer(new Media((new File(Settings.SOUNDTRACK_PATH).toURI().toString()))));
        Button resume =menuP.getResume();
        menuP.useResume(resume);

        root.getChildren().add(game);
        int m = root.getChildren().size();
        root.getChildren().add(menuP.getMenu());

        resume.fire();
        int n = root.getChildren().size();
        assertEquals(m, n);
    }
}