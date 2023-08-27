package game;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piece.BlockGFX;
import support.MovementDirections;
import support.Settings;
import support.Vector2;
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
        ArrayList<BlockGFX> blocks = new Board("conf2").getBlocks();
        Settings.activeBlock = blocks.get(5);
        Settings.activeID = 5;
        Vector2 beforeMove = Settings.activeBlock.getTopLeft();
        Vector2 afterMove = beforeMove;
        if(Settings.activeBlock.checkMovement(MovementDirections.UP, blocks)){
            afterMove = Settings.activeBlock.move(MovementDirections.UP).getValue();
            assertFalse(beforeMove.isEqual(afterMove));
        }
        else assertTrue(beforeMove.isEqual(afterMove));
    }

    @Test
    void getBlocks() {
        assertNotNull(board.getBlocks());
    }

    @Test
    void get2x2block() {
        BlockGFX block2x2 = board.get2x2block();
        assertNotNull(block2x2);
    }
}
