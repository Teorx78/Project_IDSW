package game;

import javafx.embed.swing.JFXPanel;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import piece.BlockGFX;
import piece.BlockPrototype;
import piece.BlockType;
import support.DuplicateMap;
import support.Vector2;

import static org.junit.jupiter.api.Assertions.*;

class UndoTest {
    private JFXPanel jfxPanel = new JFXPanel();

    @Test
    void undoMove() {
        // Crea un oggetto Undo
        Undo undo = new Undo();

        // Crea una DuplicateMap di esempio
        DuplicateMap chronology = new DuplicateMap();
        BlockGFX block1 = new BlockGFX(new BlockPrototype(BlockType.BLOCK_1X1), 0, 0, 1);
        BlockGFX block2 = new BlockGFX(new BlockPrototype(BlockType.BLOCK_1X1), 1, 0, 2);
        chronology.put(block1, new Pair<>(new Vector2(0, 0), new Vector2(1, 0)));
        chronology.put(block2, new Pair<>(new Vector2(1, 0), new Vector2(2, 0)));

        // Esegui l'undo
        chronology = undo.undoMove(chronology);

        // Verifica che l'undo sia stato eseguito correttamente
        assertEquals(1, chronology.size());
        assertTrue(chronology.containsKey(block1));
        assertFalse(chronology.containsKey(block2));
    }
}
