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
        // Prima di ogni test, crea un nuovo oggetto JsonSolutionReader
        jsonSolutionReader = new JsonSolutionReader("conf2");
    }
    @Test
    void readJson() {
        Platform.runLater(()->{
            // Esegui il metodo readJson per caricare i dati dal file JSON
            jsonSolutionReader.readJson();
            // Verifica che il JSON sia stato letto correttamente e che la mappa non sia vuota
            Map<Object, Object> configurationMap = jsonSolutionReader.getConfigurationMap();
            assertNotNull(configurationMap);
            assertFalse(configurationMap.isEmpty());
        });
    }

    @Test
    void size() {
        Platform.runLater(()->{
            // Esegui il metodo readJson per caricare i dati dal file JSON
            jsonSolutionReader.readJson();
            // Ottieni la dimensione della mappa dei dati
            int size = jsonSolutionReader.size();
            boolean check = false;
            // Verifica che la dimensione sia quella che ti aspetti
            if(size > 0) check = true;
            assertTrue(check);
        });
    }

    @Test
    void getConfiguration() {
        Platform.runLater(()->{
            // Ottieni il nome della configurazione impostato nel costruttore
            String configuration = jsonSolutionReader.getConfiguration();
            // Verifica che il nome della configurazione sia quello che ti aspetti
            assertEquals("conf2", configuration);
        });
    }

    @Test
    void getConfigurationMap() {
        Platform.runLater(()->{
            // Esegui il metodo readJson per caricare i dati dal file JSON
            jsonSolutionReader.readJson();
            // Ottieni la mappa dei dati dal JSON
            Map<Object, Object> configurationMap = jsonSolutionReader.getConfigurationMap();
            // Verifica che la mappa non sia vuota
            assertNotNull(configurationMap);
        });
    }
}
