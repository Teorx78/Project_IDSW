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

public class JsonConfigurationReader {
    protected static Map config;
    private static String configuration;
    public JsonConfigurationReader(String configuration) {
        JsonConfigurationReader.configuration = configuration;
        try{
            Object obj = new JSONParser().parse(new FileReader(Settings.JSON_PATH));
            JSONObject jo = (JSONObject) obj;
            config = ((Map)jo.get(configuration));
        }
        catch (IOException | ParseException ex){
            ex.printStackTrace();
        }
    }

    public JsonConfigurationReader(Map config){
        JsonConfigurationReader.config = config;
    }

    public static ArrayList<Pair<Integer, Integer>> getStartAnglePiece(BlockType blockType){
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
        //System.out.println(splitAngles);
        return splitAngles;
    }

    public int getConfigSize(){ return config.size(); }
    public static Map getConfiguration() { return config; }
    public static String getConfigurazionName(){ return configuration; }

}
