package game;

import javafx.util.Pair;
import json.JsonConfigurationReader;
import piece.BlockGFX;
import piece.BlockType;
import support.Settings;
import support.Vector2;

import java.util.ArrayList;
import java.util.Map;

public class Reset {
    public static void resetBoard(ArrayList<BlockGFX> blocks, String configuration){
        BlockType[] blockType = {BlockType.BLOCK_1X1, BlockType.BLOCK_1X2,
                BlockType.BLOCK_2X1, BlockType.BLOCK_2X2};

        //reset delle varie grafiche
        if(Settings.activeBlock != null) {
            if (Settings.activeBlock.getSelected()) Settings.activeBlock.changeSelected();
            Settings.activeBlock.refresh();
            Settings.activeBlock = null;
            Settings.activeID = -1;
        }
        //reset delle posizioni dei blocchi
        for (BlockType type : blockType) {
            JsonConfigurationReader jcr = new JsonConfigurationReader(configuration);
            jcr.readJson();
            ArrayList<Pair<Integer, Integer>> tempArr = jcr.getStartAnglePiece(type, configuration);
            int i = 0;
            for (BlockGFX block : blocks) {
                if (block.getPrototype().blockType.equals(type)) {
                    int x = tempArr.get(i).getKey() * Settings.MIN_BOUNDS;
                    int y = tempArr.get(i).getValue() * Settings.MIN_BOUNDS;
                    block.setTopLeft(new Vector2(x, y));
                    i++;
                }
            }
        }

    }

//    private static boolean checkCollections(ArrayList<BlockGFX> blocks){
//        BlockType[] blockType = {BlockType.BLOCK_1X1, BlockType.BLOCK_1X2,
//                                BlockType.BLOCK_2X1, BlockType.BLOCK_2X2};
//
//        int cont = 0;
//        for (BlockType type : blockType) {
//            ArrayList tempArr = (ArrayList) config.get(type);
//            cont += tempArr.size();
//        }
//        System.out.println("> Confronto: " + blocks.size()  + " | " + cont);
//        if(blocks.size() == cont) return true;
//        return false;
//    }

}
