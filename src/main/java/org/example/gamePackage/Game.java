package org.example.gamePackage;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.rectangle.Piece;
import org.example.support.Settings;
import org.example.support.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {
    private static ArrayList<Piece> mapPieces = new ArrayList<>();		//<Figure>
    private final Stage stage;
    private Scene scene;
    private int id_selected_piece = -1;
    private boolean selectedPiece = false;
    private Label counterLabel;

    private int moves_counter = 0;

    public Game(Stage stage) {
        this.stage = stage;
        this.counterLabel = new Label("Mosse: " + moves_counter);

        mapPieces.add(new Piece(3));
        //1x2
        mapPieces.add(new Piece( new Vector2(
                mapPieces.get(0).getTopLeft().getA() + mapPieces.get(0).getWidth(),
                mapPieces.get(0).getTopLeft().getB()),
                Color.BLUE,
                2));
        //2x1
        mapPieces.add(new Piece( new Vector2(
                mapPieces.get(1).getTopLeft().getA(),
                mapPieces.get(1).getTopLeft().getB() + mapPieces.get(1).getHeight()),
                Color.VIOLET,
                1));
        //1x1
        mapPieces.add(new Piece( new Vector2(
                mapPieces.get(0).getTopLeft().getA(),
                mapPieces.get(0).getTopLeft().getB() + mapPieces.get(0).getHeight()),
                Color.SILVER,
                0));

    }
    private int getRectangleId(Vector2 coords) {
        for (int i = 0; i < mapPieces.size(); i++) {
            Piece p = mapPieces.get(i);
            if(p.checkCoords(coords))
                return i;
        }
        return -1;
    }

    private boolean checkAngles(int i, Vector2 new_top_left, Vector2 new_bottom_right){
        //CHECK ANDOLI ALTO-SINISTRA E BASSO-DESTRA
        if(new_top_left.isEqual(mapPieces.get(i).getTopLeft())) return true;
        if(new_bottom_right.isEqual(mapPieces.get(i).getBottomRight())) return true;

        //CHECK ANGOLI ALTRO-DESTRA E BASSO-SINISTRA
        Vector2 new_top_right = new Vector2(new_top_left);      //pezzo selezionato
        new_top_right.addA(mapPieces.get(id_selected_piece).getWidth());
        if(new_top_right.isEqual(mapPieces.get(i).getTopRight())) return true;

        Vector2 new_bottom_left = new Vector2(new_bottom_right);    //pezzo selezionato
        new_bottom_left.subA(mapPieces.get(id_selected_piece).getWidth());
        if(new_bottom_left.isEqual(mapPieces.get(i).getBottomLeft())) return true;

        //CHECK CON IL CENTRO DEI PEZZI

        //intervalli pezzo check
        int x1 = mapPieces.get(i).getTopLeft().getA(), x2 = mapPieces.get(i).getBottomRight().getA();
        int y1 = mapPieces.get(i).getTopLeft().getB(), y2 = mapPieces.get(i).getBottomRight().getB();
        Vector2 vx = new Vector2(x1, x2), vy = new Vector2(y1, y2);
        //intervalli pezzo attivo
        Vector2 activeX = new Vector2(new_top_left.getA(), new_bottom_right.getA()), activeY = new Vector2(new_top_left.getB(), new_bottom_right.getB());

        if(activeX.isBetween(vx) && activeY.isBetween(vy)){
            return true;
        }

        return false;
    }

    private boolean checkNewCoords(boolean movement, boolean direction){

        System.out.println("> OX: [" + mapPieces.get(id_selected_piece).getTopLeft().getA() + ", " + mapPieces.get(id_selected_piece).getBottomRight().getA() + "]");
        System.out.println("> OY: [" + mapPieces.get(id_selected_piece).getTopLeft().getB() + ", " + mapPieces.get(id_selected_piece).getBottomRight().getB() + "]");

        Settings s = new Settings();

        //ANGOLI PRINCIPALI
        Vector2 new_top_left = mapPieces.get(id_selected_piece).getMovementNewCoords(movement, direction).getKey();
        Vector2 new_bottom_right = mapPieces.get(id_selected_piece).getMovementNewCoords(movement, direction).getValue();

        System.out.println("> Nuove Coordinate | TL: [" + new_top_left + "] | BR: [" + new_bottom_right + "]");

        //ALTRI ANGOLI DEL RETTANGOLO
//        Vector2 new_top_right = new Vector2(new_top_left);
//        Vector2 new_bottom_left = new Vector2(new_bottom_right);
//
//        new_top_right.addA(mapPieces.get(id_selected_piece).getWidth());
//        new_bottom_left.subA(mapPieces.get(id_selected_piece).getWidth());

//        Vector2 new_x_range = new Vector2(new_top_left.getA(), new_bottom_right.getA());
//        Vector2 new_y_range = new Vector2(new_top_left.getB(), new_bottom_right.getB());

        if(new_top_left.getA() < s.getMinHorizontalBounds() || new_top_left.getB() < s.getMinVerticalBounds()) return false;
        if(new_bottom_right.getA() > s.getMaxHorizontalBounds() || new_bottom_right.getB() > s.getMaxVerticalBounds()) return false;

//
//        System.out.println("> NX: [" + new_x_range + "]");
//        System.out.println("> NY: [" + new_y_range + "]");

//        for (int i = 0; i < mapPieces.size(); i++){
//            if(i != id_selected_piece){
//                Vector2 curr_x_range = new Vector2(mapPieces.get(i).getTopLeft().getA(), mapPieces.get(i).getBottomRight().getA());
//                Vector2 curr_y_range = new Vector2(mapPieces.get(i).getTopLeft().getB(), mapPieces.get(i).getBottomRight().getB());
//
//                System.out.println("> ID: " + i + " | X: [" + curr_x_range + "] | Y: [" + curr_y_range + "]");
//
//                if(new_top_left.isEqual(mapPieces.get(i).getTopLeft()) ||  new_bottom_right.isEqual(mapPieces.get(i).getBottomRight()) ){
//                    return false;
//                }
//                else{
//
//                    if(new_top_right.isEqual(mapPieces.get(i).getTopRight()) ||  new_bottom_left.isEqual(mapPieces.get(i).getBottomLeft()) ){
//                        return false;
//                    }
//                    else{
//                        System.out.println("> Nessun conflitto con il pezzo di ID: " + i);
//                    }
//                }
//            }
//            System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-");
//        }

        for (int i = 0; i < mapPieces.size(); i++){
            if(i != id_selected_piece){
                System.out.println("> Coordinate ID: " + i + " | TL: [" + mapPieces.get(i).getTopLeft() + "] | BR: [" + mapPieces.get(i).getBottomRight() + "]");
                //System.out.println(checkAngles(i, new_top_left, new_bottom_right));
                if(checkAngles(i, new_top_left, new_bottom_right)){
                    return false;
                }
            }
        }


        return true;
    }


    public void setScene(Scene scene){ this.scene = scene; }

    public void printCoordsPieces() {
        int cont = 0;
        System.out.println("************ COORDINATE DEI PEZZI ************ ");
        for (Iterator iterator = mapPieces.iterator(); iterator.hasNext();) {
            Piece piece = (Piece) iterator.next();
            System.out.println("> ID PEZZO: " + cont);
            System.out.println("> TOP LEFT: " + piece.getTopLeft().toString());
            System.out.println("> BOTTOM RIGHT: " + piece.getBottomRight().toString());
            System.out.println("> WIDTH: " + piece.getWidth() + " HEIGHT: " + piece.getHeight());
            if(cont < mapPieces.size() - 1) System.out.println(" --------------------------------------");
            cont++;
        }
        System.out.println("********************************************** ");
    }

    public Group getGroup() {
        Group group = new Group();

        for (Piece piece : mapPieces) {
            group.getChildren().add(piece.getRectangle());
        }

        return group;
    }

    //GIOCO
    public void startGame() {
        printCoordsPieces();
            scene.setOnKeyPressed(event -> {
                if(selectedPiece) {
                    KeyCode keyCode = event.getCode();
                    switch (keyCode) {
                        case W, UP -> {
                                // Muovi verso l'alto
                                if (checkNewCoords(false, false)) {
                                    mapPieces.get(id_selected_piece).setNewCoords(false, false);
                                } else {
                                    System.out.println("invalido");
                                }
                            }
                        case A, LEFT -> {
                                // Muovi verso sinistra
                                if (checkNewCoords(true, false)) {
                                    mapPieces.get(id_selected_piece).setNewCoords(true, false);
                                } else {
                                    System.out.println("invalido");
                                }
                            }
                        case S, DOWN -> {
                                // Muovi verso il basso
                                if (checkNewCoords(false, true)) {
                                    mapPieces.get(id_selected_piece).setNewCoords(false, true);
                                } else {
                                    System.out.println("invalido");
                                }
                            }
                        case D, RIGHT ->  {
                                // Muovi verso destra
                                if (checkNewCoords(true, true)) {
                                    mapPieces.get(id_selected_piece).setNewCoords(true, true);
                                } else {
                                    System.out.println("invalido");
                                }
                            }
                        default -> System.out.println("> ** Tasto non valido **");
                    }
                }
                System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
            });

        stage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Vector2 mouseCoordsClick = new Vector2((int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY());
                if(!selectedPiece) {
                    if (firstClick(mouseCoordsClick)) {
                        //System.out.println("> Pezzo selezionato");
                        mapPieces.get(getRectangleId(mouseCoordsClick)).changeSelected();        // ****** cambia la selezione ****** //
                        mapPieces.get(getRectangleId(mouseCoordsClick)).refresh();
                        selectedPiece = true;
                    }
                    else System.out.println("> Seleziona un pezzo!");
                }
                else{
                    if(getRectangleId(mouseCoordsClick) == id_selected_piece){
                        //RESET
                        mapPieces.get(id_selected_piece).changeSelected();
                        mapPieces.get(id_selected_piece).refresh();
                        id_selected_piece = -1;	//reset dell'id del pezzo mosso
                        selectedPiece = false;
                    }
                    else if(getRectangleId(mouseCoordsClick) >= 0){
                        //RESET
                        mapPieces.get(id_selected_piece).changeSelected();
                        mapPieces.get(id_selected_piece).refresh();
                        //ATTIVAZIONE NUOVO PEZZO
                        mapPieces.get(getRectangleId(mouseCoordsClick)).changeSelected();
                        mapPieces.get(getRectangleId(mouseCoordsClick)).refresh();
                        id_selected_piece = getRectangleId(mouseCoordsClick);	//reset dell'id del pezzo mosso
                    }
                    else{
                        if(selectedPiece) System.out.println("> Muovi il pezzo selezionato!");
                        else System.out.println("> Seleziona un pezzo!");
                    }
                }
            }
        });


    }

    public boolean firstClick(Vector2 mouseClick) {
        if(getRectangleId(mouseClick) >= 0) {
            System.out.println("> Selezione pezzo numero: " + getRectangleId(mouseClick));
            id_selected_piece = getRectangleId(mouseClick);		//id selezione del pezzo
            return true;
        }
        return false;
    }


}
