package json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piece.BlockType;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonConfigurationReaderTest {

    private JsonConfigurationReader jsonConfigurationReader;

    @BeforeEach
    void setUp() {
        jsonConfigurationReader = new JsonConfigurationReader("conf2");
    }

    @Test
    void getStartAnglePiece() {
        jsonConfigurationReader.readJson();
        ArrayList<Pair<Integer, Integer>> angles = jsonConfigurationReader.getStartAnglePiece(BlockType.BLOCK_1X1);
        assertFalse(angles.isEmpty());
        assertEquals(4, angles.size());
        for (Pair<Integer, Integer> angle : angles) {
            assertNotNull(angle);
            assertNotNull(angle.getKey());
            assertNotNull(angle.getValue());
        }
    }

    @Test
    void readJson() {
        jsonConfigurationReader.readJson();
        Map<Object, Object> configurationMap = jsonConfigurationReader.getConfigurationMap();
        assertNotNull(configurationMap);
        assertFalse(configurationMap.isEmpty());
    }

    @Test
    void size() {
        jsonConfigurationReader.readJson();
        int size = jsonConfigurationReader.size();
        assertEquals(4, size);
    }

    @Test
    void getConfiguration() {
        String configuration = jsonConfigurationReader.getConfiguration();
        assertEquals("conf2", configuration);
    }

    @Test
    void getConfigurationMap() {
        jsonConfigurationReader.readJson();
        Map<Object, Object> configurationMap = jsonConfigurationReader.getConfigurationMap();
        assertNotNull(configurationMap);
    }
}
