package piece;

import game.Board;
import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piece.*;
import support.MovementDirections;
import support.Vector2;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BlockGFXTest {
    private BlockGFX blockGFX;
    private JFXPanel jfxPanel = new JFXPanel();

    @BeforeEach
    void setUp() {
        // Crea un oggetto BlockPrototype per il test
        BlockPrototype blockPrototype = new BlockPrototype(BlockType.BLOCK_1X1);

        // Crea un oggetto BlockGFX per il test
        blockGFX = new BlockGFX(blockPrototype, 0, 0, 1);
    }

    @Test
    void setTopLeft() {
        // Verifica se il metodo setTopLeft imposta correttamente le coordinate dell'angolo in alto a sinistra
        Vector2 newCoords = new Vector2(10, 20);
        blockGFX.setTopLeft(newCoords);
        assertEquals(newCoords.getX(), blockGFX.getTopLeft().getX());
        assertEquals(newCoords.getY(), blockGFX.getTopLeft().getY());
    }

    @Test
    void getRectangle() {
        // Verifica se il metodo getRectangle restituisce un oggetto Rectangle
        assertNotNull(blockGFX.getRectangle());
    }

    @Test
    void move() {
        //crea una lista di blocchi
        ArrayList<BlockGFX> blocks = new Board("conf2").getBlocks();
        //salva le coordinate pre movimento di un blocco
        Vector2 beforeMove = blocks.get(5).getTopLeft();
        Pair<Vector2, Vector2> moveResult = blocks.get(5).move(MovementDirections.UP);
        //controlla che le coordinate prima del movimento siano le stesse
        assertTrue(beforeMove.isEqual(moveResult.getKey()));
        //controlla che le coordinate prima e post movimento siano diverse
        assertNotEquals(true, beforeMove.isEqual(moveResult.getValue()));
    }

    @Test
    void refresh() {
        // Verifica se il metodo refresh reimposta correttamente l'opacità
        blockGFX.refresh();
        Rectangle rectangle = blockGFX.getRectangle();
        assertEquals(1.0, rectangle.getOpacity()); // Opacità dovrebbe essere reimpostata a 1.0
    }
}
