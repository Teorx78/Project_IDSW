package game;

import javafx.embed.swing.JFXPanel;
import javafx.util.Pair;
import json.JsonSolutionReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piece.BlockGFX;
import support.MovementDirections;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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
        ArrayList<BlockGFX> initialBlocks = board.getBlocks();
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
