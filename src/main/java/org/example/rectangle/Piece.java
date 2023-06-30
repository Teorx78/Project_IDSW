package org.example.rectangle;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import org.example.support.Settings;
import org.example.support.Vector2;

public class Piece extends Figure{
    private Color COLOR = Color.RED;
    private final double STROKE_WIDTH = 0.8;
    private final Color STROKE_COLOR = Color.BLACK;
    private Rectangle rectangle;

    //COSTRUTTORI

    public Piece(Vector2 tl, int id_rect_type){
        super(tl, id_rect_type);
        //creazione oggetto
        rectangle = new Rectangle(TOP_LEFT.getA(), TOP_LEFT.getB(), WIDTH, HEIGHT);
        //rectangle.setFill(Color.RED);
        rectangle.setFill(new ImagePattern(new Image(Settings.TEXTURE_PATH)));
        rectangle.setStroke(STROKE_COLOR);
        rectangle.setStrokeWidth(STROKE_WIDTH);
    }

    public Piece(Vector2 tl, Color color, int id_rect_type){
        super(tl, id_rect_type);
        //creazione oggetto
        this.COLOR = color;
        rectangle = new Rectangle(TOP_LEFT.getA(), TOP_LEFT.getB(), WIDTH, HEIGHT);
        //rectangle.setFill(COLOR);
        rectangle.setFill(new ImagePattern(new Image(Settings.TEXTURE_PATH)));
        rectangle.setStroke(STROKE_COLOR);
        rectangle.setStrokeWidth(STROKE_WIDTH);
    }

    public Piece(int id_rect_type) {
        super(id_rect_type);
        rectangle = new Rectangle(TOP_LEFT.getA(), TOP_LEFT.getB(), WIDTH, HEIGHT);
        //rectangle.setFill(Color.RED);
        rectangle.setFill(new ImagePattern(new Image(Settings.TEXTURE_PATH)));
        rectangle.setStroke(STROKE_COLOR);
        rectangle.setStrokeWidth(STROKE_WIDTH);
    }

    public Piece(int id_rect_type, Color color) {
        super(id_rect_type);
        this.COLOR = color;
        rectangle = new Rectangle(TOP_LEFT.getA(), TOP_LEFT.getB(), WIDTH, HEIGHT);
        //rectangle.setFill(COLOR);
        rectangle.setFill(new ImagePattern(new Image(Settings.TEXTURE_PATH)));
        rectangle.setStroke(STROKE_COLOR);
        rectangle.setStrokeWidth(STROKE_WIDTH);
    }

    //Setters
    public void setRectangle(Rectangle rectangle){ this.rectangle = rectangle; }

    //Metodi
    public Rectangle getRectangle() {	//creazione del rettangolo
        //rectangle.setFill(Color.RED);
        //TODO: inserire e sistemare le opacità
        rectangle.setOnMousePressed(e -> {
            if(SELECTED) {
                rectangle.opacityProperty().set(0.5);
            }
            else {
                rectangle.opacityProperty().set(1);
            }
        });
        rectangle.setOnMouseReleased(e -> {
            if(SELECTED) {
                rectangle.opacityProperty().set(0.5);
            }
            else{
                rectangle.opacityProperty().set(1);
            }
        });

        return rectangle;
    }

    public Rectangle setNewCoords(Boolean movement, boolean direction) {
        //movement = true -> orizzontale, movement = false -> verticale
        //direction = true -> alto / destra, direction = false -> basso / sinistra
        Settings s = new Settings();
        if(SELECTED) {
            if(movement){   //ORIZZONTALE -> y costante
                int nWidth = (s.getElement(ID_RECTANGLE).getA() == s.getMaxPieceWidth()) ? WIDTH / 2 : WIDTH;
                if(direction){
                    //destra
                    TOP_LEFT.addA(nWidth);
                    BOTTOM_RIGHT.addA(nWidth);
                }
                else {
                    //sinistra
                    TOP_LEFT.subA(nWidth);
                    BOTTOM_RIGHT.subA(nWidth);
                }
                rectangle.setX(TOP_LEFT.getA());
            }
            else{ //VERTICALE -> x costante
                int nHeight = (s.getElement(ID_RECTANGLE).getB() == s.getMaxPieceHeight()) ? HEIGHT / 2 : HEIGHT;
                if(direction){
                    //alto
                    TOP_LEFT.addB(nHeight);
                    BOTTOM_RIGHT.addB(nHeight);
                }
                else {
                    //basso
                    TOP_LEFT.subB(nHeight);
                    BOTTOM_RIGHT.subB(nHeight);
                }
                rectangle.setY(TOP_LEFT.getB());
            }
        }
        else {
            System.out.println("> ** Pezzo disattivato ** ");
        }

        return rectangle;
    }

    public void refresh() {
        //refresh delle texture
        if(SELECTED) {
            rectangle.opacityProperty().set(0.5);
        }
        else {
            rectangle.opacityProperty().set(1);
        }
    }

}
