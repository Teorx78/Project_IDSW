package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import json.JsonConfigurationReader;
import json.JsonSave;
import json.JsonSolutionReader;
import menuPackage.WinMenu;
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
    private Pane group =null;
    public Board(String configuration){
        super(configuration);
        if(blocks != null) blocks = new ArrayList<>();
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
                k++;
            }
        }
        new JsonSolutionReader(configuration).readJson();
    }
    public Board(int saveNumber){
        super(JsonSave.getConfig(saveNumber));
        if(blocks != null) blocks = new ArrayList<>();
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

    public Pane createBoard(StackPane root,BackgroundImage backgroundGif) {
        if(group!=null)
            resetBoard(group);
        group = new Pane();
        group.setBackground(new Background(backgroundGif));
        group.getChildren().addAll(getUndoButton(),
                getNBMButton(root),
                getResetButton(),
                getPauseButton(),
                getCounterLabel(),
                getBackground());
        for (BlockGFX block : blocks) {
            group.getChildren().add(block.getRectangle());
        }
        return group;
    }
    public void usePauseButton(StackPane root, BorderPane menuP, Button resume){
        EventHandler<ActionEvent> event = e -> {
            pauseButton.setCancelButton(false);
            resume.setCancelButton(true);
            root.getChildren().add(menuP);
            System.out.println("PAUSE");
        };
        pauseButton.setOnAction(event);
    }
    public Label getCounterLabel(){
        movesLabel.setText("MOSSE: " + moves);
        movesLabel.setTranslateX((double) (Settings.WINDOW_WIDTH * 70) / 100);
        movesLabel.setTranslateY(Settings.HIGHER_HEIGHT_LINE);
        movesLabel.getStyleClass().add("label");
        return movesLabel;
    }
    public Button getUndoButton() {
        //STILE
        undoButton.setId("undoButton");
        undoButton.getStyleClass().add("gameButton");
        undoButton.setDisable(!loadFromSave);
        //posizionamento del bottone
        undoButton.setTranslateX((double) (Settings.WINDOW_WIDTH * 13) / 100);
        undoButton.setTranslateY(Settings.LOWER_HEIGHT_LINE);
        //EVENTO
        EventHandler<ActionEvent> event = e -> {
            //reset delle varie grafiche
            if(Settings.activeBlock != null) {
                if (Settings.activeBlock.getSelected()) Settings.activeBlock.changeSelected();
                Settings.activeBlock.refresh();
                Settings.activeBlock = null;
                Settings.activeID = -1;
            }
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
    public Button getNBMButton(StackPane root) {
        //STILE
        nbmButton.setId("nbmButton");
        nbmButton.getStyleClass().add("gameButton");
        nbmButton.setDisable(loadFromSave);
        //posizionamento del bottone
        nbmButton.setTranslateX((double) (Settings.WINDOW_WIDTH * 75) / 100);
        nbmButton.setTranslateY(Settings.LOWER_HEIGHT_LINE);
        //EVENTO
        EventHandler<ActionEvent> event = e -> {
            //reset delle varie grafiche
            if(Settings.activeBlock != null) {
                if (Settings.activeBlock.getSelected()) Settings.activeBlock.changeSelected();
                Settings.activeBlock.refresh();
                Settings.activeBlock = null;
                Settings.activeID = -1;
            }
            if(Objects.requireNonNull(get2x2block()).getBottomLeft().isEqual(new Vector2(200,600))
                    && Objects.requireNonNull(get2x2block()).getBottomRight().isEqual(new Vector2(400,600))) {
                nbmButton.setDisable(true);
                System.out.println("********* VITTORIA! *********");
                WinMenu menuW = new WinMenu  (root);
                root.getChildren().add(menuW.getMenu());
            }

            else{
                JsonSolutionReader jsr = new JsonSolutionReader(new JsonConfigurationReader(getConfiguration()).getConfiguration());
                jsr.readJson();
                NextBestMove nbm = new NextBestMove(jsr, blocks);
                Pair<Integer, MovementDirections> nextMove = nbm.getNextMove(MOVES_COUNTER);
                if(nextMove != null && nextMove.getValue() != null) {
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
        //STILE
        resetButton.setId("resetButton");
        resetButton.getStyleClass().add("gameButton");
        resetButton.setDisable(!loadFromSave);
        //posizionamento del bottone
        resetButton.setTranslateX((double) (Settings.WINDOW_WIDTH * 45) / 100);
        resetButton.setTranslateY(Settings.LOWER_HEIGHT_LINE);
        //EVENTO
        EventHandler<ActionEvent> event = e -> {
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
        pauseButton.setCancelButton(true);
        //EVENTO
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
    public void resetBoard(Pane group){ group.getChildren().removeAll(); }
}
