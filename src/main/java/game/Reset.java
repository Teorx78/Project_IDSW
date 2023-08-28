package game;

import javafx.util.Pair;
import json.JsonConfigurationReader;
import piece.BlockGFX;
import piece.BlockType;
import support.Settings;
import support.Vector2;
import java.util.ArrayList;

/**
 * La classe `Reset` fornisce un metodo per reimpostare la configurazione dei blocchi
 * all'interno del gioco, ripristinando le loro posizioni in base ai dati del file di configurazione JSON.
 */
public class Reset {
    /**
     * Metodo per il reset della configurazione dei blocchi nel gioco.
     *
     * @param blocks        Un elenco degli oggetti `BlockGFX` che rappresentano i blocchi.
     * @param configuration La stringa che rappresenta la configurazione di partenza letta da un file JSON.
     */
    public static void resetBoard(ArrayList<BlockGFX> blocks, String configuration) {
        // Tipi di blocchi disponibili
        BlockType[] blockType = {BlockType.BLOCK_1X1, BlockType.BLOCK_1X2, BlockType.BLOCK_2X1, BlockType.BLOCK_2X2};

        // Reset delle varie grafiche
        if (Settings.activeBlock != null) {
            if (Settings.activeBlock.getSelected()) Settings.activeBlock.changeSelected();
            Settings.activeBlock.refresh();
            Settings.activeBlock = null;
            Settings.activeID = -1;
        }

        // Reset delle posizioni dei blocchi in base alla configurazione JSON
        for (BlockType type : blockType) {
            JsonConfigurationReader jcr = new JsonConfigurationReader(configuration);
            jcr.readJson();
            ArrayList<Pair<Integer, Integer>> tempArr = jcr.getStartAnglePiece(type);
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
}
