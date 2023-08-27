package json;

import game.Board;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;
import piece.BlockGFX;
import support.Settings;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonSaveTest {
    private JFXPanel jfxPanel = new JFXPanel();
    @Test
    void writeSave() throws Exception {
        ArrayList<BlockGFX> blocks = new Board("conf2").getBlocks();
        JsonSave.writeSave(blocks);
        assertTrue(Files.exists(Path.of(Settings.JSON_SAVES_PATH)));
    }

    @Test
    void getSave() throws Exception {
        JsonSave.writeSave(new Board("conf2").getBlocks());
        assertNotNull(JsonSave.getSave(JsonSave.getNumberSave()-1));
    }

    @Test
    void getNumberSave() throws Exception {
        JsonSave.writeSave(new Board("conf1").getBlocks());
        JsonSave.writeSave(new Board("conf2").getBlocks());
        JsonSave.writeSave(new Board("conf3").getBlocks());
        assertNotEquals(-1, JsonSave.getNumberSave());
    }

    @Test
    void getConfig() throws Exception {
        JsonSave.setConfig("conf3");
        JsonSave.writeSave(new Board("conf3").getBlocks());
        String config = JsonSave.getConfig(JsonSave.getNumberSave()-1);
        assertEquals("conf3", config);
    }

    @Test
    void setConfig() throws Exception {
        JsonSave.setConfig("conf3");
        JsonSave.writeSave(new Board("conf3").getBlocks());
        assertNotNull(JsonSave.getConfig(JsonSave.getNumberSave()-1));
    }
}
