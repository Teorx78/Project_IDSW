package json;

import javafx.beans.binding.ObjectExpression;
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
    private static void readAllSaves() {
        try {
            Object obj = new JSONParser().parse(new FileReader(Settings.JSON_SAVES_PATH));
            JSONObject jo = (JSONObject) obj;
            savesMap = (Map<String, Object>) jo;
//            System.out.println(savesMap);
            Set<String> keySet = savesMap.keySet();
            Object[] arrKeys = keySet.toArray();
//            for(Object o: arrKeys) System.out.println(o.toString());
            getLastSave(arrKeys);
        }catch (IOException | ParseException | ClassCastException ignored){}
    }
    private static void getLastSave(Object[] keys){
        lastSave = 0;
        for(Object key : keys){
            String s = (String) key;
            int temp = Integer.parseInt(String.valueOf(s.charAt(s.length()-1)));
            if(temp > lastSave) lastSave = temp;
        }
    }
    public static void writeSave(ArrayList<BlockGFX> blocks) throws Exception{
        readAllSaves();
        JSONObject jsonObject = new JSONObject(), allJsonObj = new JSONObject();
        if(savesMap != null) allJsonObj = new JSONObject(savesMap);
        jsonObject.put("configuration", new JsonConfigurationReader(config).getConfiguration());
        //for (BlockGFX block : blocks) jsonObject.put(block.getId(), block.getSaveString());
        for (BlockGFX block : blocks) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(block.getSaveString());
            jsonArray.add(block.getPrototype().blockType.toString());
            jsonObject.put(block.getId(), jsonArray);
        }
//        System.out.println(">LS: " + lastSave);
        allJsonObj.put("save" + (lastSave + 1), jsonObject);

        Files.write(Paths.get(Settings.JSON_SAVES_PATH), allJsonObj.toJSONString().getBytes());
    }
    public static Map<Integer, Pair<Vector2, BlockType>> getSave(int saveNumber){
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

    public static int getNumberSave() {
        readAllSaves();
        return savesMap.size();
    }

//    public static Map<Vector2, BlockType> getSave(int saveNumber){
//        readAllSaves();
//        String save = "save" + saveNumber;
//        Map<String, String> map = (Map<String, String>) savesMap.get(save);
//        if(map != null){
//            Map<Vector2, BlockType> finalMap = new HashMap<>();
//            //ricavo la configurazione
//            config = map.get("configuration");
//            map.remove("configuration");
//            System.out.println(">conf: " + config);
//            System.out.println(">saves: " + map);
//            for (var entry : map.entrySet()) {
//                String[] split = entry.getKey().split(",", 2);
//                finalMap.put(new Vector2(Integer.parseInt(split[0]), Integer.parseInt(split[1])),
//                        BlockType.valueOf(entry.getValue())
//                );
//            }
//        return finalMap;
//        }
//        return null;
//    }
    public static String getConfig(int saveNumber){
        getSave(saveNumber);
        return config;
    }
}



