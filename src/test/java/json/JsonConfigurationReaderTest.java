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
        // Prima di ogni test, crea un nuovo oggetto JsonConfigurationReader
        jsonConfigurationReader = new JsonConfigurationReader("conf2");
    }

    @Test
    void getStartAnglePiece() {
        // Esegui il metodo readJson per caricare i dati dal file JSON
        jsonConfigurationReader.readJson();
        // Ottieni gli angoli di inizio per un certo tipo di blocco usando il metodo getStartAnglePiece
        ArrayList<Pair<Integer, Integer>> angles = jsonConfigurationReader.getStartAnglePiece(BlockType.BLOCK_1X1);
        // Verifica che l'elenco degli angoli non sia vuoto e abbia la dimensione corretta
        assertFalse(angles.isEmpty());
        assertEquals(4, angles.size());
        // Verifica che ogni elemento nell'elenco abbia il formato corretto
        for (Pair<Integer, Integer> angle : angles) {
            assertNotNull(angle);
            // Verifica che l'angolo abbia dei valori
            assertNotNull(angle.getKey());
            assertNotNull(angle.getValue());
        }
    }

    @Test
    void readJson() {
        // Esegui il metodo readJson per caricare i dati dal file JSON
        jsonConfigurationReader.readJson();
        // Verifica che il JSON sia stato letto correttamente e che la mappa non sia vuota
        Map<Object, Object> configurationMap = jsonConfigurationReader.getConfigurationMap();
        assertNotNull(configurationMap);
        assertFalse(configurationMap.isEmpty());
    }

    @Test
    void size() {
        // Esegui il metodo readJson per caricare i dati dal file JSON
        jsonConfigurationReader.readJson();
        // Ottieni la dimensione della mappa dei dati
        int size = jsonConfigurationReader.size();
        // Verifica che la dimensione sia quella che ti aspetti
        assertEquals(4, size);
    }

    @Test
    void getConfiguration() {
        // Ottieni il nome della configurazione impostato nel costruttore
        String configuration = jsonConfigurationReader.getConfiguration();
        // Verifica che il nome della configurazione sia quello che ti aspetti
        assertEquals("conf2", configuration);
    }

    @Test
    void getConfigurationMap() {
        // Esegui il metodo readJson per caricare i dati dal file JSON
        jsonConfigurationReader.readJson();
        // Ottieni la mappa dei dati dal JSON
        Map<Object, Object> configurationMap = jsonConfigurationReader.getConfigurationMap();
        // Verifica che la mappa non sia vuota
        assertNotNull(configurationMap);
    }
}
