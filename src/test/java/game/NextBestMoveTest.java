package game;

import game.NextBestMove;
import javafx.embed.swing.JFXPanel;
import json.JsonSolutionReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.util.Pair;
import piece.BlockGFX;
import support.MovementDirections;
import support.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NextBestMoveTest {

    private ArrayList<BlockGFX> blocks;
    private Board board;
    private JFXPanel jfxPanel = new JFXPanel();


    @BeforeEach
    void setUp() {
        //inizializza il campo
        board = new Board("conf2");
        // Inizializza la lista dei blocchi per il test
        blocks = board.getBlocks();
    }

    @Test
    void getSavedMove() {
        // json
        JsonSolutionReader jsr = new JsonSolutionReader("conf2");
        jsr.readJson();
        // Crea un oggetto NextBestMove usando i dati necessari per il test
        NextBestMove nextBestMove = new NextBestMove(jsr, blocks);
        // Azione che dovrebbe impostare il valore di 'from_to'
        nextBestMove.nextMove();
        // Ottiene il valore di 'from_to' usando il metodo getSavedMove
        Pair<Vector2, Vector2> savedMove = nextBestMove.getSavedMove();
        // Verifica che savedMove non sia null
        assertNotNull(savedMove);
    }

    @Test
    void nextMove() {
        //json
        JsonSolutionReader jsr = new JsonSolutionReader("conf2");
        jsr.readJson();
        // Crea un oggetto NextBestMove usando i dati necessari per il test
        NextBestMove nextBestMove = new NextBestMove(jsr, blocks);
        // Ottiene la mossa successiva usando il metodo nextMove
        Pair<Integer, MovementDirections> nextMove = nextBestMove.nextMove();
        // Verifica che nextMove non sia null
        assertNotNull(nextMove);
    }
}
