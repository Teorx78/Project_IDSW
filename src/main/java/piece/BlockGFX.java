package piece;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import support.MovementDirections;
import support.Settings;
import support.Vector2;

public class BlockGFX extends Block{
    private final Rectangle rectangle;
    public BlockGFX(BlockPrototype blockPrototype, int xTopLeft, int yTopLeft, int id) {
        super(blockPrototype, xTopLeft, yTopLeft, id);
        Color STROKE_COLOR = Color.BLACK;
        double STROKE_WIDTH = 0.8;

        rectangle = new Rectangle(xTopLeft, yTopLeft, prototype.width, prototype.height);
        rectangle.setFill(new ImagePattern(new Image(blockPrototype.texture)));
        rectangle.setStroke(STROKE_COLOR);
        rectangle.setStrokeWidth(STROKE_WIDTH);
    }
    public Rectangle getRectangle(){
        rectangle.setOnMousePressed(e -> {
            //cambi di stato
            if(Settings.activeID > -1 && Settings.activeID != this.id){     //se attivo un pezzo e non ho cliccato sul pezzo attivo
//                System.out.println("> E' selezionato un altro pezzo!");
                Settings.activeBlock.changeSelected();  //deseleziona blocco selezionato
                Settings.activeBlock.refresh();
                Settings.activeID = -1;
                //Settings.activeBlock = null;
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

            if(Settings.activeID > -1) System.out.println("active block: " + Settings.activeBlock.toString());

        });
        rectangle.setOnMouseReleased(e -> {
            if(isSelected) {
                rectangle.opacityProperty().set(0.5);
            }
            else{
                rectangle.opacityProperty().set(1);
            }
        });
        /*rectangle.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(isSelected){
                    switch (event.getCode()){
                        case W, UP -> {
                            System.out.println("sopra");
                        }
                        case A, LEFT -> {
                            System.out.println("sinistra");

                        }
                        case S, DOWN -> {
                            System.out.println("sotto");

                        }
                        case D, RIGHT -> {
                            System.out.println("destra");

                        }
                    }
                }
            }
        });*/
        return rectangle;
    }

    public Pair<Vector2,Vector2> move(MovementDirections movementDirections){   //muove il pezzo
        int movementWidth = this.prototype.blockType.equals(BlockType.BLOCK_1X1)
                || this.prototype.blockType.equals(BlockType.BLOCK_1X2)
                ? this.prototype.width
                : this.prototype.width / 2;

        int movementHeight = this.prototype.blockType.equals(BlockType.BLOCK_1X1)
                || this.prototype.blockType.equals(BlockType.BLOCK_2X1)
                ? this.prototype.height
                : this.prototype.height / 2;

        Vector2 beforeMove = new Vector2(xTopLeft, yTopLeft);   //variabile prima del movimento

        System.out.println(this);

        //System.out.println("{ moveW: " + movementWidth + ", moveH: " + movementHeight + "}");

        switch (movementDirections){
            case UP -> {
                //rectangle.setTranslateY(-movementHeight);
                this.yTopLeft -= movementHeight;
                rectangle.setY(yTopLeft);
                //System.out.println("{ rx: " + rectangle.getX() + ", ry: " + rectangle.getY() + " }");
            }
            case DOWN -> {
                //rectangle.setTranslateY(movementHeight);
                this.yTopLeft += movementHeight;
                rectangle.setY(yTopLeft);
                //System.out.println("{ rx: " + rectangle.getX() + ", ry: " + rectangle.getY() + " }");
            }
            case LEFT -> {
                //rectangle.setTranslateX(-movementWidth);
                this.xTopLeft -= movementWidth;
                rectangle.setX(xTopLeft);
                //System.out.println("{ rx: " + rectangle.getX() + ", ry: " + rectangle.getY() + " }");
            }
            case RIGHT -> {
                //rectangle.setTranslateX(movementWidth);
                this.xTopLeft += movementWidth;
                rectangle.setX(this.xTopLeft);
                //System.out.println("{ rx: " + rectangle.getX() + ", ry: " + rectangle.getY() + " }");
            }
        }
        return new Pair<>(beforeMove, new Vector2(xTopLeft, yTopLeft));
    }

    public void refresh() {
        //refresh delle texture
        if(isSelected) {
            rectangle.opacityProperty().set(0.5);
        }
        else {
            rectangle.opacityProperty().set(1);
        }
    }


}
