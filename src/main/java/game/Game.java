package game;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import json.JsonSave;
import json.JsonSolutionReader;
import menuPackage.WinMenu;
import piece.BlockGFX;
import piece.BlockType;
import support.DuplicateMap;
import support.MovementDirections;
import support.Settings;
import support.Vector2;

import java.util.*;

/**
 * La classe `Game` rappresenta il gioco generale. Gestisce la logica di base, il conteggio delle mosse e fornisce
 * funzionalità comuni utilizzate dalla scacchiera di gioco.
 */
public class Game {
    private static Scene scene;
    private boolean win = false;
    protected static String config;
    protected static Button undoButton = new Button(null),
            nbmButton = new Button(null),
            resetButton = new Button(null),
            pauseButton = new Button(null);
    protected int moves = 0;
    protected Label movesLabel = new Label(null);
    protected static int MOVES_COUNTER = 0;
    protected static boolean loadFromSave = false;
    protected static ArrayList<BlockGFX> blocks = new ArrayList<>();   //se cambia, cambia ovunque
    protected DuplicateMap chronology = new DuplicateMap();

    /**
     * Costruttore della classe `Game` che accetta una configurazione iniziale.
     *
     * @param configuration La configurazione iniziale del gioco.
     */
    public Game(String configuration){
        config = configuration;
    }       //costruttore della classe
    /**
     * Imposta la scena principale del gioco.
     *
     * @param scene La scena principale del gioco.
     */
    public void setScene(Scene scene) { Game.scene = scene; }       //set della scena
    /**
     * Restituisce la configurazione attuale del gioco.
     *
     * @return La configurazione attuale del gioco.
     */
    public String getConfiguration(){ return config; }      //ritorna la configurazione scelta
    /**
     * Avvia il gioco e gestisce gli input da tastiera, le mosse e il controllo della vittoria.
     *
     * @param root Il pannello radice dell'interfaccia utente.
     */
    public void startGame(StackPane root){      //metodo per iniziare il gioco, questo metodo gestisce gli eventuali input da tastiera, casi particolari post movimento e il caso in cui si vinca
        scene.setOnKeyPressed(event -> {
            if(getSelectedBlock() > -1 && !win) {
                boolean _switch = false;
                Pair<Vector2, Vector2> savedMove = null;
                switch (event.getCode()) {
                    case W, UP -> {
                        //check posizione post movimento
                        //se possibile muovere pezzo
                        if(blocks.get(getSelectedBlock()).checkMovement(MovementDirections.UP, blocks)){
                            savedMove = Settings.activeBlock.move(MovementDirections.UP);
                            _switch = true;
                        }
                    }
                    case A, LEFT -> {
                        if(blocks.get(getSelectedBlock()).checkMovement(MovementDirections.LEFT, blocks)){
                            savedMove = Settings.activeBlock.move(MovementDirections.LEFT);
                            _switch = true;
                        }
                    }
                    case S, DOWN -> {
                        if(blocks.get(getSelectedBlock()).checkMovement(MovementDirections.DOWN, blocks)){
                            savedMove = Settings.activeBlock.move(MovementDirections.DOWN);
                            _switch = true;
                        }
                    }
                    case D, RIGHT -> {
                        if(blocks.get(getSelectedBlock()).checkMovement(MovementDirections.RIGHT, blocks)){
                            savedMove = Settings.activeBlock.move(MovementDirections.RIGHT);
                            _switch = true;
                        }
                    }
                    case T ->{
                        try {
                            JsonSave.writeSave(blocks);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    default -> System.out.println("***** UTILIZZA UN TASTO CORRETTO! (WASD | FRECCIE DIREZIONALI) *****");
                }

                //check vittoria
                if(Objects.requireNonNull(get2x2block()).getBottomLeft().isEqual(new Vector2(200,600))
                        && Objects.requireNonNull(get2x2block()).getBottomRight().isEqual(new Vector2(400,600))) {
                    System.out.println("********* VITTORIA! *********");
                    WinMenu menuW = new WinMenu  (root);
                    root.getChildren().add(menuW.getMenu());
                    nbmButton.setDisable(true);
                    win = true;
                    _switch = false;
                }

                if(_switch){
                    //dopo movimento aggiornamento delle varie collezioni tra cui quella della cronologia delle mosse
                    //inserimento della mossa fatta nel caso in cui avesse successo
                    chronology.put(Settings.activeBlock, savedMove);
                    undoButton.setDisable(chronology.size() <= 0);
                    resetButton.setDisable(chronology.size() <= 0);
                    moves++;
                    movesLabel.setText("MOSSE: " + moves);
                    //next best move
                    JsonSolutionReader jsr = new JsonSolutionReader(config);
                    jsr.readJson();
                    NextBestMove nbm = new NextBestMove(jsr, blocks);
                    Pair<Integer, MovementDirections> nextMove = nbm.nextMove();
                    if(nextMove != null && nextMove.getValue() != null) nbmButton.setDisable(false);
                    else nbmButton.setDisable(true);
                }


            } else System.out.println("> Seleziona un pezzo!");
        });
    }

    /**
     * Restituisce la lista dei blocchi presenti nel gioco.
     *
     * @return La lista dei blocchi presenti nel gioco.
     */
    public ArrayList<BlockGFX> getBlocks(){        //metodo per ricevere l'array dei blocchi
        return blocks;
    }

    /**
     * Metodo privato per determinare quale blocco è attualmente selezionato.
     *
     * @return L'indice del blocco selezionato, o -1 se nessun blocco è selezionato.
     */
    private int getSelectedBlock(){     //metodo che ricava quale blocco è selezionato attualmente
        for(int i = 0; i < blocks.size(); i++){
            if(blocks.get(i).getSelected()) return i;
        }
        return -1;
    }
    /**
     * Restituisce il blocco 2x2 presente nel gioco.
     *
     * @return Il blocco 2x2 presente nel gioco, o null se non è presente.
     */
    protected BlockGFX get2x2block(){       //metodo per ricavare il blocco 2x2
        for (BlockGFX block : blocks) {
            if(block.getPrototype().blockType.equals(BlockType.BLOCK_2X2)) return block;
        }
        return null;
    }
    /**
     * Restituisce la scena principale del gioco.
     *
     * @return La scena principale del gioco.
     */
    public Scene getScene(){ return scene; }
}
