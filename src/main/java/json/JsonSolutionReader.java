package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import support.Settings;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class JsonSolutionReader extends JsonConfigurationReader{
    private static Map solution;
    public JsonSolutionReader(String configuration) {
        super(configuration);
        try{
            Object obj = new JSONParser().parse(new FileReader(Settings.JSON_SOLUTION_PATH));
            JSONObject jo = (JSONObject) obj;
            //System.out.println(jo);
            solution = ((Map) jo.get(configuration));
//            System.out.println(config.size());      //4
        }
        catch (IOException | ParseException ex){
            ex.printStackTrace();
        }
    }

    public String getElement(String index){
        return (String) solution.get(index);
    }
}
