package json;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.Settings;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class JsonSolutionReaderTest {

    private JsonSolutionReader jsonSolutionReader;
    private JFXPanel jfxPanel = new JFXPanel();

    @BeforeEach
    void setUp() {
        jsonSolutionReader = new JsonSolutionReader("conf2");
    }
    @Test
    void readJson() {
        Platform.runLater(()->{
            jsonSolutionReader.readJson();
            Map<Object, Object> configurationMap = jsonSolutionReader.getConfigurationMap();
            assertNotNull(configurationMap);
            assertFalse(configurationMap.isEmpty());
        });
    }

    @Test
    void size() {
        Platform.runLater(()->{
            jsonSolutionReader.readJson();
            int size = jsonSolutionReader.size();
            boolean check = false;
            if(size > 0) check = true;
            assertTrue(check);
        });
    }

    @Test
    void getConfiguration() {
        Platform.runLater(()->{
            String configuration = jsonSolutionReader.getConfiguration();
            assertEquals("conf2", configuration);
        });
    }

    @Test
    void getConfigurationMap() {
        Platform.runLater(()->{
            jsonSolutionReader.readJson();
            Map<Object, Object> configurationMap = jsonSolutionReader.getConfigurationMap();
            assertNotNull(configurationMap);
        });
    }
}
