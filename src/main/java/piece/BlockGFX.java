package piece;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import support.MovementDirections;
import support.Settings;
import support.Vector2;

/**
 * La classe `BlockGFX` rappresenta un blocco grafico nel gioco Klotski.
 * Estende la classe astratta `Block` e include una rappresentazione visiva del blocco come oggetto JavaFX Rectangle.
 * Questa classe gestisce anche gli eventi del mouse per la selezione e il movimento dei blocchi.
 */
public class BlockGFX extends Block {
    /**
     * Il rettangolo JavaFX che rappresenta il blocco grafico.
     */
    private final Rectangle rectangle;

    /**
     * Costruttore della classe `BlockGFX`. Crea un blocco grafico con le caratteristiche specificate.
     *
     * @param blockPrototype Il prototipo di blocco associato a questo blocco grafico.
     * @param xTopLeft       La coordinata x dell'angolo in alto a sinistra del blocco.
     * @param yTopLeft       La coordinata y dell'angolo in alto a sinistra del blocco.
     * @param id             L'ID univoco del blocco.
     */
    public BlockGFX(BlockPrototype blockPrototype, int xTopLeft, int yTopLeft, int id) {
        super(blockPrototype, xTopLeft, yTopLeft, id);
        Color STROKE_COLOR = Color.BLACK;
        double STROKE_WIDTH = 0.8;

        rectangle = new Rectangle(xTopLeft, yTopLeft, prototype.width, prototype.height);
        rectangle.setFill(new ImagePattern(new Image(blockPrototype.texture)));
        rectangle.setStroke(STROKE_COLOR);
        rectangle.setStrokeWidth(STROKE_WIDTH);
    }

    /**
     * Imposta la coordinata in alto a sinistra del blocco e del rettangolo JavaFX.
     *
     * @param coords Le nuove coordinate in alto a sinistra del blocco.
     */
    @Override
    public void setTopLeft(Vector2 coords) {
        super.setTopLeft(coords);
        rectangle.setX(coords.getX());
        rectangle.setY(coords.getY());
    }

    /**
     * Restituisce il rettangolo JavaFX che rappresenta il blocco grafico.
     * Gestisce gli eventi del mouse per la selezione e il movimento dei blocchi.
     *
     * @return Il rettangolo JavaFX del blocco grafico.
     */
    public Rectangle getRectangle() {
        // Gestione degli eventi del mouse per la selezione e il movimento dei blocchi
        rectangle.setOnMousePressed(e -> {
            // Cambi di stato
            if (Settings.activeID > -1 && Settings.activeID != this.id) {
                Settings.activeBlock.changeSelected();
                Settings.activeBlock.refresh();
                Settings.activeID = -1;
            }

            if (Settings.activeID < 0) {
                isSelected = !isSelected;
                Settings.activeID = this.id;
                Settings.activeBlock = this;
            } else {
                isSelected = !isSelected;
                Settings.activeID = -1;
                Settings.activeBlock = null;
            }

            // Cambi grafica
            if (isSelected) {
                rectangle.opacityProperty().set(0.5);
            } else {
                rectangle.opacityProperty().set(1);
            }
        });

        rectangle.setOnMouseReleased(e -> {
            if (isSelected) {
                rectangle.opacityProperty().set(0.5);
            } else {
                rectangle.opacityProperty().set(1);
            }
        });

        return rectangle;
    }

    /**
     * Esegue il movimento del blocco grafico nella direzione specificata e restituisce le coordinate prima e dopo il movimento.
     *
     * @param movementDirections La direzione del movimento.
     * @return Una coppia di coordinate, rappresentanti la posizione prima e dopo il movimento.
     */
    public Pair<Vector2, Vector2> move(MovementDirections movementDirections) {
        int movementWidth = this.prototype.blockType.equals(BlockType.BLOCK_1X1)
                || this.prototype.blockType.equals(BlockType.BLOCK_1X2)
                ? this.prototype.width
                : this.prototype.width / 2;

        int movementHeight = this.prototype.blockType.equals(BlockType.BLOCK_1X1)
                || this.prototype.blockType.equals(BlockType.BLOCK_2X1)
                ? this.prototype.height
                : this.prototype.height / 2;

        Vector2 beforeMove = new Vector2(xTopLeft, yTopLeft);

        switch (movementDirections) {
            case UP -> {
                this.yTopLeft -= movementHeight;
                rectangle.setY(yTopLeft);
            }
            case DOWN -> {
                this.yTopLeft += movementHeight;
                rectangle.setY(yTopLeft);
            }
            case LEFT -> {
                this.xTopLeft -= movementWidth;
                rectangle.setX(xTopLeft);
            }
            case RIGHT -> {
                this.xTopLeft += movementWidth;
                rectangle.setX(this.xTopLeft);
            }
        }

        return new Pair<>(beforeMove, new Vector2(xTopLeft, yTopLeft));
    }

    /**
     * Aggiorna la grafica del blocco grafico in base allo stato di selezione.
     */
    public void refresh() {
        if (isSelected) {
            rectangle.opacityProperty().set(0.5);
        } else {
            rectangle.opacityProperty().set(1);
        }
    }

    /**
     * Verifica se due blocchi grafici sono uguali.
     *
     * @param obj L'oggetto da confrontare.
     * @return True se i due blocchi grafici sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
