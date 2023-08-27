package json;

import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import piece.BlockGFX;
import piece.BlockType;
import support.Settings;
import support.Vector2;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JsonSave {
    private static Map<String, Object> savesMap = new HashMap<>();
    private static int lastSave=-1;
    private static String config;
    private static void readAllSaves() {        //metodo che legge tutti i salvataggi dal json
        try {
            Object obj = new JSONParser().parse(new FileReader(Settings.JSON_SAVES_PATH));
            JSONObject jo = (JSONObject) obj;
            savesMap = (Map<String, Object>) jo;
            Set<String> keySet = savesMap.keySet();
            Object[] arrKeys = keySet.toArray();
            getLastSave(arrKeys);
        }catch (IOException | ParseException | ClassCastException ignored){}
    }
    private static void getLastSave(Object[] keys){     //metodo che trova il numero dell'ultimo salvataggio
        lastSave = 0;
        for(Object key : keys){
            String s = (String) key;
            int temp = Integer.parseInt(String.valueOf(s.charAt(s.length()-1)));
            if(temp > lastSave) lastSave = temp;
        }
    }
    public static void writeSave(ArrayList<BlockGFX> blocks) throws Exception{          //metodo per la scrittura su json dei blocchi passati (crea un nuovo salvataggio)
        readAllSaves();
        JSONObject jsonObject = new JSONObject(), allJsonObj = new JSONObject();
        if(savesMap != null) allJsonObj = new JSONObject(savesMap);
        jsonObject.put("configuration", new JsonConfigurationReader(config).getConfiguration());
        for (BlockGFX block : blocks) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(block.getSaveString());
            jsonArray.add(block.getPrototype().blockType.toString());
            jsonObject.put(block.getId(), jsonArray);
        }
        allJsonObj.put("save" + (lastSave + 1), jsonObject);
        Files.write(Paths.get(Settings.JSON_SAVES_PATH), allJsonObj.toJSONString().getBytes());
    }
    public static Map<Integer, Pair<Vector2, BlockType>> getSave(int saveNumber){       //metodo che ritorna un determinato salvataggio dato il suo numero
        readAllSaves();
        String save = "save" + saveNumber;
        Map<String, Object> allSaveMap = (Map<String, Object>) savesMap.get(save);
        Map<Integer, Pair<Vector2, BlockType>> finalMap = new HashMap<>();
        config = (String) allSaveMap.get("configuration");
        savesMap.remove("configuration");
        allSaveMap.remove("configuration");
        for (int i = 0; i < allSaveMap.size(); i++) {
            ArrayList<String> arr = (ArrayList<String>) allSaveMap.get(i+"");
            String[] split = arr.get(0).split(",", 2);
            finalMap.put(i, new Pair<>(new Vector2(Integer.parseInt(split[0]), Integer.parseInt(split[1])),
                                BlockType.valueOf(arr.get(1))));
        }
        return finalMap;
    }
    public static int getNumberSave() {     //restituisce il totale dei salvataggi
        readAllSaves();
        return savesMap.size();
    }
    public static String getConfig(int saveNumber){     //restituisce il nome della configurazione
        getSave(saveNumber);
        return config;
    }
    public static void setConfig(String config){ JsonSave.config = config; }        //setta la configurazione
}



