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
    private final int width, height;
    private final BlockType[] blockType = {BlockType.BLOCK_1X1, BlockType.BLOCK_1X2, BlockType.BLOCK_2X1, BlockType.BLOCK_2X2};

    public Board(String configuration, Stage stage){
        super(configuration, stage);
        //setup del campo
        this.width = Settings.WINDOW_WIDTH - (Settings.MIN_HORIZONTAL_BOUNDS * 2);
        this.height = Settings.WINDOW_HEIGHT - (Settings.MIN_VERTICAL_BOUNDS * 2);
        //TODO: mettere la texture al campo di gioco -> sottoclasse per la grafica (?)
        //lettura json
        JsonConfigurationReader jsonReader = new JsonConfigurationReader(configuration);
        //setup e salvataggio pezzi nel campo
        int k = 0;
        for(int i = 0; i < jsonReader.getConfigSize(); i++){
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
        undoButton.getStyleClass().add("button");
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
        nbmButton.getStyleClass().add("button");
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
            if(nextMove != null) {
                blocks.get(nextMove.getKey()).move(nextMove.getValue());
                // next best move restituisce id e mossa fatta,
                // da li basta prendere l'id in blocks e fare la mossa rispettiva
                // ATTENZIONE! azzera active block e active id in Settings


                //System.out.println("NBM SELEZIONATO");
//            NextBestMove nbm = new NextBestMove(blocks);
//                System.out.println("PROSSIMA MOSSA: " + nbm.solve());
//            Pair<Integer, MovementDirections> nextMove = null;
//            if(chronology.size() > 0) {
//                nextMove = nbm.solve(new Pair<>(chronology.get(chronology.size() - 1).getKey().getId(), chronology.getMovementDirection(chronology.size() - 1)));
//            }
//            else{
//                nextMove = nbm.solve(new Pair<>(null, null));
//            }
//            BlockGFX blockMove = blocks.get(nextMove.getKey());
//            Vector2 before = new Vector2(blocks.get(nextMove.getKey()).getTopLeft().getX(), blocks.get(nextMove.getKey()).getTopLeft().getY());
//            int offsetX = 0, offsetY = 0;
//            switch (nextMove.getValue()){
//                case UP -> offsetY--;
//                case DOWN -> offsetY++;
//                case LEFT -> offsetX--;
//                case RIGHT -> offsetX++;
//            }
//            Vector2 after = new Vector2(blocks.get(nextMove.getKey()).getTopLeft().getX() + offsetX,
//                    blocks.get(nextMove.getKey()).getTopLeft().getY() + offsetY);
//            chronology.put(blockMove, new Pair<>(before, after));
//
//            blocks.get(nextMove.getKey()).move(nextMove.getValue());
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
        resetButton.getStyleClass().add("button");
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
            moves = 0;
            MOVES_COUNTER = 0;
            movesLabel.setText("MOSSE: " + moves);
        };
        resetButton.setOnAction(event);
        return resetButton;
    }

}
