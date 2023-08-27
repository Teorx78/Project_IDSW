package piece;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlockTypeTest {

    @Test
    public void testBlockTypes() {
        assertEquals(BlockType.BLOCK_1X1, BlockType.valueOf("BLOCK_1X1"));
        assertEquals(BlockType.BLOCK_1X2, BlockType.valueOf("BLOCK_1X2"));
        assertEquals(BlockType.BLOCK_2X1, BlockType.valueOf("BLOCK_2X1"));
        assertEquals(BlockType.BLOCK_2X2, BlockType.valueOf("BLOCK_2X2"));
    }
}
