package game;

import javafx.embed.swing.JFXPanel;
import javafx.util.Pair;
import json.JsonSolutionReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piece.BlockGFX;
import piece.BlockPrototype;
import piece.BlockType;
import support.MovementDirections;
import support.Settings;
import support.Vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResetTest {

    private Board board;
    private ArrayList<BlockGFX> blocks;
    private JFXPanel jfxPanel = new JFXPanel();


    @BeforeEach
    void setUp() {
        board = new Board("conf2");
        blocks = board.getBlocks();
    }

    @Test
    void resetBoard() {
        //salva la lista dei blocchi iniziali
        ArrayList<BlockGFX> initialBlocks = board.getBlocks();
        //utilizzo next best move per fare 15 mosse nel campo
        for (int i = 0; i < 15; i++){
            JsonSolutionReader jsr = new JsonSolutionReader("conf2");
            jsr.readJson();
            NextBestMove nbm = new NextBestMove(jsr, blocks);
            Pair<Integer, MovementDirections> nextMove = nbm.nextMove();
            blocks.get(nextMove.getKey()).move(nextMove.getValue());
        }
        Reset.resetBoard(blocks, "conf2");
        assertArrayEquals(initialBlocks.toArray(), blocks.toArray());
    }
}
