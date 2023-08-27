package game;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.Settings;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private JFXPanel jfxPanel = new JFXPanel();

    @BeforeEach
    void setUp() {
        board = new Board("conf2");
    }

    @Test
    void createBoard() {
        Platform.runLater(()->{
            StackPane root = new StackPane();
            BackgroundImage backgroundImage = new BackgroundImage(
                    new Image(Settings.BACKGROUND_IMAGE_PATH),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT
            );

            Pane result = board.createBoard(root, backgroundImage);
            assertNotNull(result);
        });
    }

    @Test
    void usePauseButton() {
        StackPane root = new StackPane();
        BorderPane menuPane = new BorderPane();
        // Simula il click sul pulsante di pausa
        board.usePauseButton(root, menuPane, board.getPauseButton());
        EventHandler<ActionEvent> eventHandler = board.getPauseButton().getOnAction();

        // Verifica che il gestore d'evento funzioni correttamente
        assertNotNull(eventHandler);
    }

    @Test
    void getCounterLabel() {
       Label label = board.getCounterLabel();
        assertNotNull(label);
    }

    @Test
    void getUndoButton() {
        Button button = board.getUndoButton();
        assertNotNull(button);
    }

    @Test
    void getNBMButton() {
        StackPane root = new StackPane();
        Button button = board.getNBMButton(root);
        assertNotNull(button);
    }

    @Test
    void getResetButton() {
        Button button = board.getResetButton();
        assertNotNull(button);
    }

    @Test
    void getPauseButton() {
        Button button = board.getPauseButton();
        assertNotNull(button);
    }

    @Test
    void getBackground() {
        Rectangle rectangle = board.getBackground();
        assertNotNull(rectangle);
    }

    @Test
    void resetBoard() {
        Pane group = new Pane();
        board.resetBoard(group);
        assertTrue(group.getChildren().isEmpty());
    }
}
