package game;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import piece.BlockGFX;
import piece.BlockType;
import support.DuplicateMap;
import support.MovementDirections;
import support.Settings;
import support.Vector2;

import java.util.*;

public class Game {
    private final String config;
    private final Stage stage;
    private static Scene scene;
    protected static Button undoButton = new Button(null), nbmButton = new Button(null), resetButton = new Button(null);

    protected static ArrayList<BlockGFX> blocks = new ArrayList<>();   //se cambia, cambia ovunque
    //protected LinkedHashMap<BlockGFX, Pair<Vector2, Vector2>> chronology = new LinkedHashMap<>();
    protected DuplicateMap chronology = new DuplicateMap();

    public Game(String configuration, Stage stage){
        this.config = configuration;
        this.stage = stage;
    }

    public void setScene(Scene scene) { Game.scene = scene; }
    public String getConfiguration(){ return config; }
    public static Button getUndoButtonComponent(){ return undoButton; }

    public void startGame(){
        scene.setOnKeyPressed(event -> {
            if(getSelectedBlock() > -1) {
                boolean _switch = false;
                Pair<Vector2, Vector2> savedMove = null;
                System.out.println(event.getCode());
                switch (event.getCode()) {
                    case W, UP -> {
                        //check posizione post movimento
                        //se possibile muovere pezzo
                        //System.out.println("UP: " + blocks.get(getSelectedBlock()).checkMovement(MovementDirections.UP, blocks));
                        if(blocks.get(getSelectedBlock()).checkMovement(MovementDirections.UP, blocks)){
                            savedMove = Settings.activeBlock.move(MovementDirections.UP);
                            _switch = true;
                        }
                    }
                    case A, LEFT -> {
                        //System.out.println("LEFT: " + blocks.get(getSelectedBlock()).checkMovement(MovementDirections.LEFT, blocks));
                        if(blocks.get(getSelectedBlock()).checkMovement(MovementDirections.LEFT, blocks)){
                            savedMove = Settings.activeBlock.move(MovementDirections.LEFT);
                            _switch = true;
                        }
                    }
                    case S, DOWN -> {
                        //System.out.println("DOWN: " + blocks.get(getSelectedBlock()).checkMovement(MovementDirections.DOWN, blocks));
                        if(blocks.get(getSelectedBlock()).checkMovement(MovementDirections.DOWN, blocks)){
                            savedMove = Settings.activeBlock.move(MovementDirections.DOWN);
                            _switch = true;
                        }
                    }
                    case D, RIGHT -> {
                        //System.out.println("RIGHT: " + blocks.get(getSelectedBlock()).checkMovement(MovementDirections.RIGHT, blocks));
                        if(blocks.get(getSelectedBlock()).checkMovement(MovementDirections.RIGHT, blocks)){
                            savedMove = Settings.activeBlock.move(MovementDirections.RIGHT);
                            _switch = true;
                        }
                    }
                    default -> //throw new IllegalStateException("Unexpected value: " + event.getCode());
                            System.out.println("***** UTILIZZA UN TASTO CORRETTO! (WASD | FRECCIE DIREZIONALI) *****");
                }

                if(_switch){
                    //dopo movimento aggiornamento delle varie collezioni tra cui quella della cronologia delle mosse
                    //inserimento della mossa fatta nel caso in cui avesse successo
                    chronology.put(Settings.activeBlock, savedMove);
                    System.out.println("***** \n" + chronology + "\n*****");
                    undoButton.setDisable(chronology.size() <= 0);
                    resetButton.setDisable(chronology.size() <= 0);
                }

                //check vittoria
                if(Objects.requireNonNull(get2x2block()).getBottomLeft().isEqual(new Vector2(200,600)) && Objects.requireNonNull(get2x2block()).getBottomRight().isEqual(new Vector2(400,600)))
                    //TODO: inserire un menu di vittoria
                    System.out.println("********* VITTORIA! *********");
            } else System.out.println("> Seleziona un pezzo!");
        });
    }
    private int getSelectedBlock(){
        for(int i = 0; i < blocks.size(); i++){
            if(blocks.get(i).getSelected()) return i;
        }
        return -1;
    }
    private BlockGFX get2x2block(){
        for (BlockGFX block : blocks) {
            if(block.getPrototype().blockType.equals(BlockType.BLOCK_2X2)) return block;
        }
        return null;
    }
}
