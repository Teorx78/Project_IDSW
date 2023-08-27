package support;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piece.BlockGFX;
import piece.BlockPrototype;
import piece.BlockType;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class DuplicateMapTest {

    private DuplicateMap duplicateMap;
    private JFXPanel jfxPanel = new JFXPanel();

    @BeforeEach
    void setUp() {
        duplicateMap = new DuplicateMap();
    }

    @Test
    void testSetKey() {
        BlockGFX blockGFX = new BlockGFX(new BlockPrototype(BlockType.BLOCK_1X1), 30,30,1);
        duplicateMap.setKey(blockGFX);

        assertEquals(1, duplicateMap.size());
        assertEquals(blockGFX, duplicateMap.getKey(0));
        assertNull(duplicateMap.getValue(0));
    }

    @Test
    void testSetValue() {
        Pair<Vector2, Vector2> value = new Pair<>(new Vector2(1, 2), new Vector2(3, 4));
        duplicateMap.setValue(value);

        assertEquals(1, duplicateMap.size());
        assertNull(duplicateMap.getKey(0));
        assertEquals(value, duplicateMap.getValue(0));
    }

    @Test
    void testPut() {
        BlockGFX blockGFX = new BlockGFX(new BlockPrototype(BlockType.BLOCK_1X1), 30,30,1);
        Pair<Vector2, Vector2> value = new Pair<>(new Vector2(1, 2), new Vector2(3, 4));
        duplicateMap.put(blockGFX, value);

        assertEquals(1, duplicateMap.size());
        assertEquals(blockGFX, duplicateMap.getKey(0));
        assertEquals(value, duplicateMap.getValue(0));
    }

    @Test
    void testRemove() {
        BlockGFX blockGFX = new BlockGFX(new BlockPrototype(BlockType.BLOCK_1X1), 30,30,1);
        Pair<Vector2, Vector2> value = new Pair<>(new Vector2(1, 2), new Vector2(3, 4));
        duplicateMap.put(blockGFX, value);

        assertTrue(duplicateMap.remove(0));
        assertEquals(0, duplicateMap.size());
        assertNull(duplicateMap.getKey(0));
        assertNull(duplicateMap.getValue(0));

        assertFalse(duplicateMap.remove(0));
    }

    @Test
    void testGetMovementDirection() {
        Pair<Vector2, Vector2> valueRight = new Pair<>(new Vector2(1, 0), new Vector2(3, 0));
        Pair<Vector2, Vector2> valueLeft = new Pair<>(new Vector2(3, 0), new Vector2(1, 0));
        Pair<Vector2, Vector2> valueDown = new Pair<>(new Vector2(0, 3), new Vector2(0, 1));
        Pair<Vector2, Vector2> valueUp = new Pair<>(new Vector2(0, 1), new Vector2(0, 3));

        duplicateMap.setValue(valueRight);
        duplicateMap.setValue(valueLeft);
        duplicateMap.setValue(valueDown);
        duplicateMap.setValue(valueUp);

        assertEquals(MovementDirections.RIGHT, duplicateMap.getMovementDirection(0));
        assertEquals(MovementDirections.LEFT, duplicateMap.getMovementDirection(1));
        assertEquals(MovementDirections.DOWN, duplicateMap.getMovementDirection(2));
        assertEquals(MovementDirections.UP, duplicateMap.getMovementDirection(3));
    }
}
