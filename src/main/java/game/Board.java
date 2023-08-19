package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import json.JsonConfigurationReader;
import json.JsonSolutionReader;
import piece.BlockGFX;
import piece.BlockPrototype;
import piece.BlockType;
import support.DuplicateMap;
import support.MovementDirections;
import support.Settings;
import javafx.scene.control.Button;
import support.Vector2;

import java.util.ArrayList;

public class Board extends Game{
//    private final int width, height;

    public Board(String configuration){
        super(configuration);
        //setup del campo
//        this.width = Settings.WINDOW_WIDTH - (Settings.MIN_HORIZONTAL_BOUNDS * 2);
//        this.height = Settings.WINDOW_HEIGHT - (Settings.MIN_VERTICAL_BOUNDS * 2);
        //lettura json
        JsonConfigurationReader jsonReader = new JsonConfigurationReader(configuration);
        //setup e salvataggio pezzi nel campo
        int k = 0;
        for(int i = 0; i < jsonReader.getConfigSize(); i++){
            BlockType[] blockType = {BlockType.BLOCK_1X1, BlockType.BLOCK_1X2, BlockType.BLOCK_2X1, BlockType.BLOCK_2X2};
            ArrayList<Pair<Integer, Integer>> arrayList = jsonReader.getStartAnglePiece(blockType[i]);
            for (Pair<Integer, Integer> integerIntegerPair : arrayList) {
                BlockPrototype blockPrototype = new BlockPrototype(blockType[i]);
                int x = integerIntegerPair.getKey() * Settings.MIN_HORIZONTAL_BOUNDS;
                int y = integerIntegerPair.getValue() * Settings.MIN_HORIZONTAL_BOUNDS;
                blocks.add(new BlockGFX(blockPrototype, x, y, k));
                //System.out.println(blocks.get(k).toString());
                k++;
            }
        }
        new JsonSolutionReader(configuration);
//        NextBestMove nbm = new NextBestMove(blocks);
    }
    public Pane createBoard(){
        //Group group = new Group();
        Pane group = new Pane();

        group.getChildren().addAll(getUndoButton(),
                            getNBMButton(),
                            getResetButton(),
                            getPauseButton(),
                            getCounterLabel());


        for (BlockGFX block : blocks) {
            group.getChildren().add(block.getRectangle());
        }
        return group;
    }
    public Label getCounterLabel(){
        movesLabel.setText("MOSSE: " + moves);
        movesLabel.setTranslateX((double) (Settings.WINDOW_WIDTH * 70) / 100);
        movesLabel.setTranslateY(Settings.HIGHER_HEIGHT_LINE);
        movesLabel.getStyleClass().add("label");
        return movesLabel;
    }
    public Button getUndoButton() {
//        undoButton = new Button(null);
        //STILE
        undoButton.setId("undoButton");
        undoButton.getStyleClass().add("gameButton");
        undoButton.setDisable(true);
        //posizionamento del bottone
        undoButton.setTranslateX((double) (Settings.WINDOW_WIDTH * 13) / 100);
        undoButton.setTranslateY(Settings.LOWER_HEIGHT_LINE);
        //EVENTO
        EventHandler<ActionEvent> event = e -> {
            //System.out.println("BOTTONE SELEZIONATO");
            chronology = new Undo().undoMove(chronology);
            resetButton.setDisable(chronology.size() <= 0);
            undoButton.setDisable(chronology.size() <= 0);
            moves++;
            MOVES_COUNTER--;
            movesLabel.setText("MOSSE: " + moves);
        };
        undoButton.setOnAction(event);

        return undoButton;
    }
    public Button getNBMButton() {
//        nbmButton = new Button(null);
        //STILE
        nbmButton.setId("nbmButton");
        nbmButton.getStyleClass().add("gameButton");
//        nbmButton.setDisable(true);
        //posizionamento del bottone
        nbmButton.setTranslateX((double) (Settings.WINDOW_WIDTH * 75) / 100);
        nbmButton.setTranslateY(Settings.LOWER_HEIGHT_LINE);
        //EVENTO
        EventHandler<ActionEvent> event = e -> {
            //todo: lettura delle soluzioni quando viene scelta una configurazione
            JsonSolutionReader jsr = new JsonSolutionReader(JsonConfigurationReader.getConfigurazionName());
            NextBestMove nbm = new NextBestMove(jsr, blocks);
            Pair<Integer, MovementDirections> nextMove = nbm.getNextMove(MOVES_COUNTER);
            if(nextMove.getValue() != null) {
                blocks.get(nextMove.getKey()).move(nextMove.getValue());

                moves++;
                MOVES_COUNTER++;
                movesLabel.setText("MOSSE: " + moves);

                chronology.put(blocks.get(nextMove.getKey()), nbm.getSavedMove());
                undoButton.setDisable(chronology.size() <= 0);
                resetButton.setDisable(chronology.size() <= 0);
            }
            else{
                nbmButton.setDisable(true);
            }
        };
        nbmButton.setOnAction(event);
        return nbmButton;
    }
    public Button getResetButton() {
//        resetButton = new Button(null);
        //STILE
        resetButton.setId("resetButton");
        resetButton.getStyleClass().add("gameButton");
        resetButton.setDisable(true);
        //posizionamento del bottone
        resetButton.setTranslateX((double) (Settings.WINDOW_WIDTH * 45) / 100);
        resetButton.setTranslateY(Settings.LOWER_HEIGHT_LINE);
        //EVENTO
        EventHandler<ActionEvent> event = e -> {
//            System.out.println("RESET SELEZIONATO");
            Reset.resetBoard(blocks);
            chronology = new DuplicateMap();
            resetButton.setDisable(true);
            undoButton.setDisable(true);
            nbmButton.setDisable(false);
            moves = 0;
            MOVES_COUNTER = 0;
            movesLabel.setText("MOSSE: " + moves);
        };
        resetButton.setOnAction(event);
        return resetButton;
    }
    public Button getPauseButton(){
        //STILE
        pauseButton.setId("pauseButton");
        pauseButton.getStyleClass().add("gameButton");
        //posizionamento del bottone
        pauseButton.setTranslateX((double) (Settings.WINDOW_WIDTH * 13) / 100);
        pauseButton.setTranslateY(Settings.HIGHER_HEIGHT_LINE);
        //EVENTO
        EventHandler<ActionEvent> event = e -> {
            System.out.println("PAUSE");
        };
        pauseButton.setOnAction(event);
        return pauseButton;
    }

}
