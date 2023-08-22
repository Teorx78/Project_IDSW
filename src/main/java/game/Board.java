package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import json.JsonConfigurationReader;
import json.JsonSave;
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
import java.util.Map;
import java.util.Objects;

public class Board extends Game{
//    private final int width, height;
    public Board(String configuration){
        super(configuration);
        //setup del campo
//        this.width = Settings.WINDOW_WIDTH - (Settings.MIN_HORIZONTAL_BOUNDS * 2);
//        this.height = Settings.WINDOW_HEIGHT - (Settings.MIN_VERTICAL_BOUNDS * 2);
        //lettura json
        JsonConfigurationReader jsonReader = new JsonConfigurationReader(configuration);
        jsonReader.readJson();
        //setup e salvataggio pezzi nel campo
        int k = 0;
        for(int i = 0; i < jsonReader.getConfigSize(); i++){
            BlockType[] blockType = {BlockType.BLOCK_1X1, BlockType.BLOCK_1X2, BlockType.BLOCK_2X1, BlockType.BLOCK_2X2};
            ArrayList<Pair<Integer, Integer>> arrayList = jsonReader.getStartAnglePiece(blockType[i], configuration);
            for (Pair<Integer, Integer> integerIntegerPair : arrayList) {
                BlockPrototype blockPrototype = new BlockPrototype(blockType[i]);
                int x = integerIntegerPair.getKey() * Settings.MIN_BOUNDS;
                int y = integerIntegerPair.getValue() * Settings.MIN_BOUNDS;
                blocks.add(new BlockGFX(blockPrototype, x, y, k));
                //System.out.println(blocks.get(k).toString());
                k++;
            }
        }
        new JsonSolutionReader(configuration).readJson();
//        NextBestMove nbm = new NextBestMove(blocks);
    }
    public Board(int saveNumber){
        super(JsonSave.getConfig(saveNumber));
        JsonSave.getSave(saveNumber);
        Map<Integer, Pair<Vector2, BlockType>> save = JsonSave.getSave(saveNumber);
        config = JsonSave.getConfig(saveNumber);
        int k = 0;
        for (var entry : save.entrySet()) {
            BlockPrototype blockPrototype = new BlockPrototype(entry.getValue().getValue());
            int x = entry.getValue().getKey().getX() * Settings.MIN_BOUNDS;
            int y = entry.getValue().getKey().getY() * Settings.MIN_BOUNDS;
            blocks.add(new BlockGFX(blockPrototype, x, y, k));
            k++;
        }
        new JsonConfigurationReader(JsonSave.getConfig(saveNumber)).readJson();
        loadFromSave = true;
    }
    public Pane createBoard(){
        //Group group = new Group();
        Pane group = new Pane();

        group.getChildren().addAll(getUndoButton(),
                            getNBMButton(),
                            getResetButton(),
                            getPauseButton(),
                            getCounterLabel(),
                            getBackground());


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
        undoButton.setDisable(!loadFromSave);
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
        nbmButton.setDisable(loadFromSave);
        //posizionamento del bottone
        nbmButton.setTranslateX((double) (Settings.WINDOW_WIDTH * 75) / 100);
        nbmButton.setTranslateY(Settings.LOWER_HEIGHT_LINE);
        //EVENTO
        EventHandler<ActionEvent> event = e -> {
            if(Objects.requireNonNull(get2x2block()).getBottomLeft().isEqual(new Vector2(200,600))
                    && Objects.requireNonNull(get2x2block()).getBottomRight().isEqual(new Vector2(400,600)))
                nbmButton.setDisable(true);
            else{
                JsonSolutionReader jsr = new JsonSolutionReader(new JsonConfigurationReader(getConfiguration()).getConfiguration());
                jsr.readJson();
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
        resetButton.setDisable(!loadFromSave);
        //posizionamento del bottone
        resetButton.setTranslateX((double) (Settings.WINDOW_WIDTH * 45) / 100);
        resetButton.setTranslateY(Settings.LOWER_HEIGHT_LINE);
        //EVENTO
        EventHandler<ActionEvent> event = e -> {
//            System.out.println("RESET SELEZIONATO");
            Reset.resetBoard(blocks, config);
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
    public Rectangle getBackground(){
        Rectangle bg = new Rectangle(Settings.MIN_BOUNDS,
                Settings.MIN_BOUNDS,
                Settings.WINDOW_WIDTH - (Settings.MIN_BOUNDS*2),
                Settings.WINDOW_HEIGHT - (Settings.MIN_BOUNDS*2.5));
        bg.setFill(Paint.valueOf("black"));
        bg.opacityProperty().set(0.3);
        return bg;
    }
}
