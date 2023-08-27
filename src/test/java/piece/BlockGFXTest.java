package piece;

import game.Board;
import javafx.embed.swing.JFXPanel;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.MovementDirections;
import support.Vector2;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BlockGFXTest {
    private BlockGFX blockGFX;
    private JFXPanel jfxPanel = new JFXPanel();

    @BeforeEach
    void setUp() {
        BlockPrototype blockPrototype = new BlockPrototype(BlockType.BLOCK_1X1);
        blockGFX = new BlockGFX(blockPrototype, 0, 0, 1);
    }

    @Test
    void setTopLeft() {
        Vector2 newCoords = new Vector2(10, 20);
        blockGFX.setTopLeft(newCoords);
        assertEquals(newCoords.getX(), blockGFX.getTopLeft().getX());
        assertEquals(newCoords.getY(), blockGFX.getTopLeft().getY());
    }

    @Test
    void getRectangle() {
        assertNotNull(blockGFX.getRectangle());
    }

    @Test
    void move() {
        ArrayList<BlockGFX> blocks = new Board("conf2").getBlocks();
        /* TEST MOVIMENTO */
        Vector2 beforeMove = blocks.get(5).getTopLeft();
        Pair<Vector2, Vector2> moveResult = blocks.get(5).move(MovementDirections.UP);
        assertTrue(beforeMove.isEqual(moveResult.getKey()));
        assertFalse(beforeMove.isEqual(moveResult.getValue()));
    }

    @Test
    void refresh() {
        blockGFX.refresh();
        Rectangle rectangle = blockGFX.getRectangle();
        assertEquals(1.0, rectangle.getOpacity());
    }
}
