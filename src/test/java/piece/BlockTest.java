package piece;

import game.Board;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.MovementDirections;
import support.Vector2;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {
    private JFXPanel jfxPanel = new JFXPanel();
    private BlockPrototype blockPrototype;
    private Block block;

    @BeforeEach
    public void setUp() {
        blockPrototype = new BlockPrototype(BlockType.BLOCK_1X1);
        block = new Block(blockPrototype, 0, 0, 1);
    }

    @Test
    void setTopLeft() {
        block.setTopLeft(new Vector2(2, 2));
        assertEquals(2, block.getTopLeft().getX());
        assertEquals(2, block.getTopLeft().getY());
    }

    @Test
    void getTopLeft() {
        assertEquals(0, block.getTopLeft().getX());
        assertEquals(0, block.getTopLeft().getY());
    }

    @Test
    void getTopRight() {
        assertEquals(blockPrototype.width, block.getTopRight().getX());
        assertEquals(0, block.getTopRight().getY());
    }

    @Test
    void getBottomLeft() {
        assertEquals(0, block.getBottomLeft().getX());
        assertEquals(blockPrototype.height, block.getBottomLeft().getY());
    }

    @Test
    void getBottomRight() {
        assertEquals(blockPrototype.width, block.getBottomRight().getX());
        assertEquals(blockPrototype.height, block.getBottomRight().getY());
    }

    @Test
    void getSelected() {
        assertFalse(block.getSelected());
    }

    @Test
    void getId() {
        assertEquals(1, block.getId());
    }

    @Test
    void getPrototype() {
        assertEquals(blockPrototype, block.getPrototype());
    }

    @Test
    void changeSelected() {
        assertFalse(block.getSelected());
        block.changeSelected();
        assertTrue(block.getSelected());
        block.changeSelected();
        assertFalse(block.getSelected());
    }

    @Test
    void getSaveString() {
        assertEquals("0,0", block.getSaveString());
    }

    @Test
    void checkMovement() {
        // Crea un array di blocchi per simulare il campo ed aggiunge altri blocchi all'array 'blocks' come blocchi esistenti sul campo
        ArrayList<BlockGFX> blocks = new Board("conf2").getBlocks();

        // Verifica se il blocco può muoversi verso l'alto senza sovrapporsi ad altri blocchi
        assertFalse(block.checkMovement(MovementDirections.UP, blocks));

        // Verifica se il blocco può muoversi verso il basso senza sovrapporsi ad altri blocchi
        assertFalse(block.checkMovement(MovementDirections.DOWN, blocks));

        // Verifica se il blocco può muoversi a destra senza sovrapporsi ad altri blocchi
        assertFalse(block.checkMovement(MovementDirections.RIGHT, blocks));

        // Verifica se il blocco può muoversi a sinistra senza sovrapporsi ad altri blocchi
        assertFalse(block.checkMovement(MovementDirections.LEFT, blocks));
    }

    @Test
    void isOverlapping() {
        // Crea un blocco che si sovrappone a 'block'
        Block overlappingBlock = new Block(block, 0, 0);

        // Verifica se il blocco si sovrappone correttamente con 'overlappingBlock'
        assertTrue(block.isOverlapping(overlappingBlock));

        // Crea un blocco che non si sovrappone a 'block'
        Block nonOverlappingBlock = new Block(block, block.getPrototype().width, 0);

        // Verifica se il blocco non si sovrappone con 'nonOverlappingBlock'
        assertFalse(block.isOverlapping(nonOverlappingBlock));
    }

}
