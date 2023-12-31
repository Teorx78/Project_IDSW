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

/**
 * La classe `Board` rappresenta la scacchiera di gioco del puzzle. Gestisce la disposizione dei blocchi
 * sulla scacchiera, il conteggio delle mosse e fornisce pulsanti e funzionalità per il controllo del gioco.
 */
public class Board extends Game{
    private Pane group = null;

    /**
     * Costruttore che crea una nuova istanza di `Board` a partire da una configurazione specificata.
     *
     * @param configuration La configurazione da cui creare la scacchiera.
     */
    public Board(String configuration){         //Costruttore che prende in input la configurazione, serve per creare una board da zero
        super(configuration);
        if(blocks != null) blocks = new ArrayList<>();
        //lettura json
        JsonConfigurationReader jsonReader = new JsonConfigurationReader(configuration);
        jsonReader.readJson();
        //setup e salvataggio pezzi nel campo
        int k = 0;
        for(int i = 0; i < jsonReader.size(); i++){
            BlockType[] blockType = {BlockType.BLOCK_1X1, BlockType.BLOCK_1X2, BlockType.BLOCK_2X1, BlockType.BLOCK_2X2};
            ArrayList<Pair<Integer, Integer>> arrayList = jsonReader.getStartAnglePiece(blockType[i]);
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


    /**
     * Costruttore che crea una nuova istanza di `Board` a partire da un numero di salvataggio specificato.
     *
     * @param saveNumber Il numero di salvataggio da cui creare la scacchiera.
     */
    public Board(int saveNumber){           //Costruttore che prende in input il numero del salvataggio, serve per creare una board a partire da un salvataggio
        super(JsonSave.getConfig(saveNumber));
        if(blocks != null) blocks = new ArrayList<>();
        JsonSave.getSave(saveNumber);
        Map<Integer, Pair<Vector2, BlockType>> save = JsonSave.getSave(saveNumber);
        System.out.println(save);
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

    /**
     * Crea una nuova istanza di `Pane` contenente tutti i componenti della scacchiera.
     *
     * @param root           Il riquadro principale su cui posizionare il `Pane` della scacchiera.
     * @param backgroundGif L'immagine di sfondo da utilizzare per la scacchiera.
     * @return Il `Pane` contenente tutti i componenti della scacchiera.
     */
    public Pane createBoard(StackPane root,BackgroundImage backgroundGif) {     //metodo per la restituzione di un Pane contenente tutti i componenti della Board
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

    /**
     * Imposta l'evento del pulsante di pausa e lo aggiunge all'interfaccia utente.
     *
     * @param root     Il riquadro principale dell'interfaccia utente.
     * @param menuP    Il menu di pausa.
     * @param resume   Il pulsante di ripresa.
     */
    public void usePauseButton(StackPane root, BorderPane menuP, Button resume){        //metodo per settare l'event del bottone di pausa
        EventHandler<ActionEvent> event = e -> {
            pauseButton.setCancelButton(false);
            resume.setCancelButton(true);
            root.getChildren().add(menuP);
            System.out.println("PAUSE");
            if(Settings.activeBlock != null) {
                if (Settings.activeBlock.getSelected()) Settings.activeBlock.changeSelected();
                Settings.activeBlock.refresh();
                Settings.activeBlock = null;
                Settings.activeID = -1;
            }
        };
        pauseButton.setOnAction(event);
    }

    /**
     * Ottiene l'etichetta del contatore delle mosse.
     *
     * @return L'etichetta del contatore delle mosse.
     */
    public Label getCounterLabel(){     //metodo per avere la label del contatore delle mosse
        movesLabel.setText("MOSSE: " + moves);
        movesLabel.setTranslateX((double) (Settings.WINDOW_WIDTH * 70) / 100);
        movesLabel.setTranslateY(Settings.HIGHER_HEIGHT_LINE);
        movesLabel.getStyleClass().add("label");
        return movesLabel;
    }

    /**
     * Ottiene il pulsante per annullare l'ultima mossa.
     *
     * @return Il pulsante per annullare l'ultima mossa.
     */
    public Button getUndoButton() {     //metodo per avere il pulsante per tornare indietro nelle mosse

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

    /**
     * Ottiene il pulsante per la prossima migliore mossa (Next Best Move).
     *
     * @param root Il riquadro principale dell'interfaccia utente.
     * @return Il pulsante per la prossima migliore mossa.
     */
    public Button getNBMButton(StackPane root) {        //metodo per avere il pulsante per la next best move
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
            JsonSolutionReader jsr = new JsonSolutionReader(new JsonConfigurationReader(getConfiguration()).getConfiguration());
            jsr.readJson();
            NextBestMove nbm = new NextBestMove(jsr, blocks);
            Pair<Integer, MovementDirections> nextMove = nbm.nextMove();
            if(nextMove != null && nextMove.getValue() != null) {
                    blocks.get(nextMove.getKey()).move(nextMove.getValue());

                    moves++;
                    MOVES_COUNTER++;
                    movesLabel.setText("MOSSE: " + moves);

                    chronology.put(blocks.get(nextMove.getKey()), nbm.getSavedMove());
                    undoButton.setDisable(chronology.size() <= 0);
                    resetButton.setDisable(chronology.size() <= 0);
            }
            //check della vittoria
            if(Objects.requireNonNull(get2x2block()).getBottomLeft().isEqual(new Vector2(200,600))
                    && Objects.requireNonNull(get2x2block()).getBottomRight().isEqual(new Vector2(400,600))) {
                nbmButton.setDisable(true);
                System.out.println("********* VITTORIA! *********");
                WinMenu menuW = new WinMenu  (root);
                root.getChildren().add(menuW.getMenu());
            }
        };
        nbmButton.setOnAction(event);
        return nbmButton;
    }

    /**
     * Ottiene il pulsante per il ripristino della scacchiera.
     *
     * @return Il pulsante per il ripristino della scacchiera.
     */
    public Button getResetButton() {            //metodo per la restituzione del bottone di reset della board
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

    /**
     * Ottiene il pulsante per la pausa in gioco.
     *
     * @return Il pulsante per la pausa in gioco.
     */
    public Button getPauseButton(){         //metodo per la restituzione del pulsante di pausa in-game
        pauseButton.setId("pauseButton");
        pauseButton.getStyleClass().add("gameButton");
        //posizionamento del bottone
        pauseButton.setTranslateX((double) (Settings.WINDOW_WIDTH * 13) / 100);
        pauseButton.setTranslateY(Settings.HIGHER_HEIGHT_LINE);
        pauseButton.setCancelButton(true);
        //EVENTO
        return pauseButton;
    }

    /**
     * Ottiene il rettangolo rappresentante lo sfondo della scacchiera.
     *
     * @return Il rettangolo dello sfondo della scacchiera.
     */
    public Rectangle getBackground(){           //metodo per la restituzione dello sfondo della board
        Rectangle bg = new Rectangle(Settings.MIN_BOUNDS,
                Settings.MIN_BOUNDS,
                Settings.WINDOW_WIDTH - (Settings.MIN_BOUNDS*2),
                Settings.WINDOW_HEIGHT - (Settings.MIN_BOUNDS*2.5));
        bg.setFill(Paint.valueOf("black"));
        bg.opacityProperty().set(0.3);
        return bg;
    }

    /**
     * Reimposta la scacchiera rimuovendo tutti i componenti dal `Pane`.
     *
     * @param group Il `Pane` contenente i componenti della scacchiera.
     */
    public void resetBoard(Pane group){ group.getChildren().removeAll(); }      //metodo per il reset della board
}
