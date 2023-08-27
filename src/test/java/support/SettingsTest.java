package support;

import org.junit.jupiter.api.Test;
import piece.BlockType;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class SettingsTest {

    @Test
    void testGetBlockSizeString() {
        Settings settings = new Settings();

        assertEquals("1x1", settings.getBlockSizeString(BlockType.BLOCK_1X1));
        assertEquals("1x2", settings.getBlockSizeString(BlockType.BLOCK_1X2));
        assertEquals("2x1", settings.getBlockSizeString(BlockType.BLOCK_2X1));
        assertEquals("2x2", settings.getBlockSizeString(BlockType.BLOCK_2X2));
    }

    @Test
    void testGetTexturePath() {
        Settings settings = new Settings();

        assertEquals("file:resources/texture/block_texture_1x1.png", settings.getTexturePath(0));
        assertEquals("file:resources/texture/block_texture_1x2.png", settings.getTexturePath(1));
        assertEquals("file:resources/texture/block_texture_2x1.png", settings.getTexturePath(2));
        assertEquals("file:resources/texture/block_texture_2x2.png", settings.getTexturePath(3));
    }

    @Test
    void testConstants() {
        assertEquals(-1, Settings.activeID);
        assertNull(Settings.activeBlock);
        assertEquals("file:resources/img/cursor.png", Settings.CURSOR_IMAGE);
        assertEquals("file:resources/img/background.gif", Settings.BACKGROUND_IMAGE_PATH);
        assertEquals("file:resources/style/button.css", Settings.CSS_BUTTON_FILE);
        assertEquals("file:resources/style/label.css", Settings.CSS_LABEL_FILE);
        assertEquals("resources/json/configuration.json", Settings.JSON_PATH);
        assertEquals("resources/json/solutions.json", Settings.JSON_SOLUTION_PATH);
        assertEquals("resources/json/saves.json", Settings.JSON_SAVES_PATH);
        assertEquals("file:resources/images/titleM.png", Settings.TITLE_IMAGE_PATH);
        assertEquals("file:resources/images/titleP.png", Settings.TITLE_PAUSE_IMAGE_PATH);
        assertEquals("file:resources/images/titleW.png", Settings.TITLE_WIN_IMAGE_PATH);
        assertEquals("file:resources/images/sound.png", Settings.SOUND_IMAGE_PATH);
        assertEquals("resources/sounds/soundtrack.mp3", Settings.SOUNDTRACK_PATH);
        assertEquals("file:resources/images/Conf", Settings.CONFIGURATIONS_PATH);
        assertEquals("file:resources/images/X.png", Settings.X_PATH);
        assertEquals(22, Settings.LABEL_SIZE);
        assertEquals(Color.BLACK, Settings.LABEL_COLOR);
        assertEquals((double)(Settings.WINDOW_HEIGHT * 85) / 100, Settings.LOWER_HEIGHT_LINE);
        assertEquals((double)(Settings.WINDOW_HEIGHT * 5) / 100, Settings.HIGHER_HEIGHT_LINE);
        assertEquals(600, Settings.WINDOW_WIDTH);
        assertEquals(750, Settings.WINDOW_HEIGHT);
        assertEquals(100, Settings.MIN_BOUNDS);
        assertEquals((600 - 100 * 2) / 4, Settings.MIN_SIDE_DIMENSION);
    }
}
