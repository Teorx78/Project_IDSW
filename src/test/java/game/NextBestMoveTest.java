package game;

import javafx.embed.swing.JFXPanel;
import json.JsonSolutionReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.util.Pair;
import piece.BlockGFX;
import support.MovementDirections;
import support.Vector2;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NextBestMoveTest {

    private ArrayList<BlockGFX> blocks;
    private Board board;
    private JFXPanel jfxPanel = new JFXPanel();


    @BeforeEach
    void setUp() {
        board = new Board("conf2");
        blocks = board.getBlocks();
    }

    @Test
    void getSavedMove() {
        JsonSolutionReader jsr = new JsonSolutionReader("conf2");
        jsr.readJson();
        NextBestMove nextBestMove = new NextBestMove(jsr, blocks);
        nextBestMove.nextMove();
        Pair<Vector2, Vector2> savedMove = nextBestMove.getSavedMove();
        assertNotNull(savedMove);
    }

    @Test
    void nextMove() {
        JsonSolutionReader jsr = new JsonSolutionReader("conf2");
        jsr.readJson();
        NextBestMove nextBestMove = new NextBestMove(jsr, blocks);
        Pair<Integer, MovementDirections> nextMove = nextBestMove.nextMove();
        assertNotNull(nextMove);
    }
}
