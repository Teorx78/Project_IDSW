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

public class JsonConfigurationReader implements JsonInterface{
    private static Map config;
    private static String configuration;
    public JsonConfigurationReader(String configuration) { JsonConfigurationReader.configuration = configuration; }     //costruttore della classe
    public ArrayList<Pair<Integer, Integer>> getStartAnglePiece(BlockType blockType){       //metodo che calcola gli angoli iniziali dei blocchi di un certo tipo
        ArrayList angles = (ArrayList) config.get(new Settings().getBlockSizeString(blockType));
        ArrayList<Pair<Integer, Integer>> splitAngles = new ArrayList<>();
        for (Object o : angles) {
            String angle = (String) o;
            String[] split = angle.split(",", 2);
            try{
                splitAngles.add(new Pair<>(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            }catch (NumberFormatException nex){
                nex.printStackTrace();
            }
        }
        return splitAngles;
    }
    @Override
    public void readJson() {        //metodo che legge il json indicato nelle settings in JSON_PATH
        try{
            Object obj = new JSONParser().parse(new FileReader(Settings.JSON_PATH));
            JSONObject jo = (JSONObject) obj;
            JsonConfigurationReader.config = ((Map)jo.get(configuration));
        }
        catch (IOException | ParseException ex){
            ex.printStackTrace();
        }
    }
    @Override
    public int size() { return config.size(); }     //ritorna la dimensione di config
    @Override
    public String getConfiguration() { return configuration; }      //ritorna il nome della configurazione
    @Override
    public Map<Object, Object> getConfigurationMap() { return config; } //ritorna la mappa della configurazione
}
