package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import support.Settings;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * La classe `JsonSolutionReader` gestisce la lettura delle soluzioni JSON per una configurazione specifica.
 */
public class JsonSolutionReader implements JsonInterface {

    private static Map solution;
    private static String configuration;

    /**
     * Crea un nuovo oggetto `JsonSolutionReader` associato a una configurazione specifica.
     *
     * @param configuration Il nome della configurazione per cui leggere le soluzioni.
     */
    public JsonSolutionReader(String configuration) {
        JsonSolutionReader.configuration = configuration;
    }

    /**
     * Legge le soluzioni JSON per la configurazione specificata.
     */
    @Override
    public void readJson() {
        try {
            Object obj = new JSONParser().parse(new FileReader(Settings.JSON_SOLUTION_PATH));
            JSONObject jo = (JSONObject) obj;
            solution = ((Map) jo.get(configuration));
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Restituisce il numero di soluzioni disponibili per la configurazione corrente.
     *
     * @return Il numero di soluzioni disponibili.
     */
    @Override
    public int size() {
        return solution.size();
    }

    /**
     * Restituisce il nome della configurazione associata alle soluzioni.
     *
     * @return Il nome della configurazione.
     */
    @Override
    public String getConfiguration() {
        return configuration;
    }

    /**
     * Restituisce le soluzioni come una mappa di oggetti.
     *
     * @return Una mappa delle soluzioni per la configurazione corrente.
     */
    @Override
    public Map<Object, Object> getConfigurationMap() {
        return solution;
    }
}
