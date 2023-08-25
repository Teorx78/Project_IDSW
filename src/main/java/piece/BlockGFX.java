package piece;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import support.MovementDirections;
import support.Settings;
import support.Vector2;

public class BlockGFX extends Block{
    private final Rectangle rectangle;
    public BlockGFX(BlockPrototype blockPrototype, int xTopLeft, int yTopLeft, int id) {        //costruttore della classe: richiama il costruttore della superclasse e setta tutti i parametri del rettangolo di JavaFx
        super(blockPrototype, xTopLeft, yTopLeft, id);
        Color STROKE_COLOR = Color.BLACK;
        double STROKE_WIDTH = 0.8;

        rectangle = new Rectangle(xTopLeft, yTopLeft, prototype.width, prototype.height);
        rectangle.setFill(new ImagePattern(new Image(blockPrototype.texture)));
        rectangle.setStroke(STROKE_COLOR);
        rectangle.setStrokeWidth(STROKE_WIDTH);
    }
    @Override
    public void setTopLeft(Vector2 coords) {    //setta l'angolo in alto a sinistra nella superclasse e nel rettangolo di JavaFx
        super.setTopLeft(coords);
        rectangle.setX(coords.getX());
        rectangle.setY(coords.getY());
    }
    public Rectangle getRectangle(){        //ritorna il rettangolo creato dalle caratteristiche date dal costruttore ed inoltre implementa le gesture dei blocchi
        rectangle.setOnMousePressed(e -> {
            //cambi di stato
            if(Settings.activeID > -1 && Settings.activeID != this.id){     //se attivo un pezzo e non ho cliccato sul pezzo attivo
                Settings.activeBlock.changeSelected();  //deseleziona blocco selezionato
                Settings.activeBlock.refresh();
                Settings.activeID = -1;
            }

            if(Settings.activeID < 0) {     //se non è selezionato niente
                isSelected = !isSelected;
                Settings.activeID = this.id;
                Settings.activeBlock = this;
            }
            else {      //se ho cliccato sul pezzo già attivo
                isSelected = !isSelected;
                Settings.activeID = -1;
                Settings.activeBlock = null;
            }
            //cambi grafica
            if(isSelected) {
                rectangle.opacityProperty().set(0.5);
            }
            else {
                rectangle.opacityProperty().set(1);
            }
        });
        rectangle.setOnMouseReleased(e -> {
            if(isSelected) {
                rectangle.opacityProperty().set(0.5);
            }
            else{
                rectangle.opacityProperty().set(1);
            }
        });
        return rectangle;
    }
    public Pair<Vector2,Vector2> move(MovementDirections movementDirections){   //metodo per il movimento del blocco
        int movementWidth = this.prototype.blockType.equals(BlockType.BLOCK_1X1)
                || this.prototype.blockType.equals(BlockType.BLOCK_1X2)
                ? this.prototype.width
                : this.prototype.width / 2;

        int movementHeight = this.prototype.blockType.equals(BlockType.BLOCK_1X1)
                || this.prototype.blockType.equals(BlockType.BLOCK_2X1)
                ? this.prototype.height
                : this.prototype.height / 2;

        Vector2 beforeMove = new Vector2(xTopLeft, yTopLeft);   //variabile prima del movimento
        switch (movementDirections){
            case UP -> {
                this.yTopLeft -= movementHeight;        //cambio della variabile della classe
                rectangle.setY(yTopLeft);               //modifica della grafica
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
    public void refresh() {         //metodo per il refresh delle texture
        if(isSelected) {
            rectangle.opacityProperty().set(0.5);
        }
        else {
            rectangle.opacityProperty().set(1);
        }
    }
}
