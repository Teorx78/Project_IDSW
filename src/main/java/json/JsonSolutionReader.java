package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import support.Settings;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class JsonSolutionReader implements JsonInterface{
    private static Map solution;
    private static String configuration;
    public JsonSolutionReader(String configuration) {
        JsonSolutionReader.configuration = configuration;
    }
    public String getElement(String index){
        return (String) solution.get(index);
    }
    @Override
    public void readJson() {
        try{
            Object obj = new JSONParser().parse(new FileReader(Settings.JSON_SOLUTION_PATH));
            JSONObject jo = (JSONObject) obj;
            solution = ((Map) jo.get(configuration));
        }
        catch (IOException | ParseException ex){
            ex.printStackTrace();
        }
    }
    @Override
    public int size() {
        return solution.size();
    }
    @Override
    public String getConfiguration() {
        return configuration;
    }
    @Override
    public Map<Object, Object> getConfigurationMap() {
        return solution;
    }
}
