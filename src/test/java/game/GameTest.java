package game;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piece.BlockGFX;
import piece.BlockPrototype;
import piece.BlockType;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Board board;
    private JFXPanel jfxPanel = new JFXPanel();

    @BeforeEach
    void setUp() {
        board = new Board("conf2");
    }

    @Test
    void setScene() {
        Scene scene = new Scene(new StackPane());
        board.setScene(scene);
        assertEquals(scene, new Game("conf2").getScene());
    }

    @Test
    void getConfiguration() {
        assertEquals("conf2", board.getConfiguration());
    }

    @Test
    void startGame() {
        // Test di esempio per il metodo startGame. Nota che questo metodo è difficile da testare direttamente.
        // Puoi considerare l'uso di librerie di testing specializzate per JavaFX.
    }

    @Test
    void getBlocks() {
        assertNotNull(board.getBlocks());
    }

    @Test
    void get2x2block() {
        //crea la lista di blocchi
        ArrayList<BlockGFX> blocks = board.getBlocks();
        BlockGFX block2x2 = board.get2x2block();
        assertNotNull(block2x2);
    }
}
