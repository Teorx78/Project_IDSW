package json;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javafx.util.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import piece.BlockType;
import support.Settings;

/**
 * La classe `JsonConfigurationReader` legge una configurazione di gioco dal formato JSON
 * e fornisce metodi per estrarre informazioni specifiche da essa.
 */
public class JsonConfigurationReader implements JsonInterface {
    private static Map config;
    private static String configuration;

    /**
     * Costruttore della classe `JsonConfigurationReader`.
     *
     * @param configuration Il nome della configurazione da leggere dal file JSON.
     */
    public JsonConfigurationReader(String configuration) {
        JsonConfigurationReader.configuration = configuration;
    }

    /**
     * Ottiene gli angoli iniziali dei blocchi di un certo tipo dalla configurazione.
     *
     * @param blockType Il tipo di blocco di cui si vogliono ottenere gli angoli iniziali.
     * @return Una lista di coppie di coordinate (x, y) rappresentanti gli angoli iniziali dei blocchi.
     */
    public ArrayList<Pair<Integer, Integer>> getStartAnglePiece(BlockType blockType) {
        ArrayList angles = (ArrayList) config.get(new Settings().getBlockSizeString(blockType));
        ArrayList<Pair<Integer, Integer>> splitAngles = new ArrayList<>();
        for (Object o : angles) {
            String angle = (String) o;
            String[] split = angle.split(",", 2);
            try {
                splitAngles.add(new Pair<>(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            } catch (NumberFormatException nex) {
                nex.printStackTrace();
            }
        }
        return splitAngles;
    }

    /**
     * Legge la configurazione dal file JSON specificato nelle impostazioni.
     */
    @Override
    public void readJson() {
        try {
            Object obj = new JSONParser().parse(new FileReader(Settings.JSON_PATH));
            JSONObject jo = (JSONObject) obj;
            JsonConfigurationReader.config = ((Map) jo.get(configuration));
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Restituisce il numero di elementi nella configurazione.
     *
     * @return La dimensione della configurazione.
     */
    @Override
    public int size() {
        return config.size();
    }

    /**
     * Restituisce il nome della configurazione corrente.
     *
     * @return Il nome della configurazione.
     */
    @Override
    public String getConfiguration() {
        return configuration;
    }

    /**
     * Restituisce la mappa della configurazione corrente.
     *
     * @return La mappa della configurazione.
     */
    @Override
    public Map<Object, Object> getConfigurationMap() {
        return config;
    }
}
