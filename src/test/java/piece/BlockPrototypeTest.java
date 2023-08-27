package piece;

import org.junit.jupiter.api.Test;
import support.Settings;

import static org.junit.jupiter.api.Assertions.*;

public class BlockPrototypeTest {

    @Test
    public void testBlockPrototypeInitialization() {
        BlockPrototype block1x1 = new BlockPrototype(BlockType.BLOCK_1X1);
        assertEquals(BlockType.BLOCK_1X1, block1x1.blockType);
        assertEquals(Settings.MIN_SIDE_DIMENSION, block1x1.width);
        assertEquals(Settings.MIN_SIDE_DIMENSION, block1x1.height);
        assertEquals(new Settings().getTexturePath(0), block1x1.texture);

        BlockPrototype block1x2 = new BlockPrototype(BlockType.BLOCK_1X2);
        assertEquals(BlockType.BLOCK_1X2, block1x2.blockType);
        assertEquals(Settings.MIN_SIDE_DIMENSION, block1x2.width);
        assertEquals(2 * Settings.MIN_SIDE_DIMENSION, block1x2.height);
        assertEquals(new Settings().getTexturePath(1), block1x2.texture);
    }

}
