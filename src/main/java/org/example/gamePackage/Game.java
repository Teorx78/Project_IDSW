package org.example.gamePackage;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.example.rectangle.Piece;
import org.example.support.Vector2;


public class Game {
	
	private static ArrayList<Piece> mapPieces = new ArrayList<>();		//<Figure> 
	private Stage stage;
	private int id_selected_piece = -1;
	private boolean selectedPiece = false, clickBool = false;
	
	public Game(Stage stage) {
		this.stage = stage;
		
		mapPieces.add(new Piece(3));
		//1x2
		mapPieces.add(new Piece( new Vector2(
				mapPieces.get(0).getTopLeft().getA() + mapPieces.get(0).getWidth(),
				mapPieces.get(0).getTopLeft().getB()),
				Color.BLUE,
				1));	
		
	}
	
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
		
		for (int i = 0; i < mapPieces.size(); i++) {
			Piece piece = mapPieces.get(i);
			group.getChildren().add(piece.getRectangle());
		}
		
		return group;
	}
	
	public void startGame() {
		printCoordsPieces();
		stage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	Vector2 mouseCoordsClick = new Vector2((int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY());
    	    	if(selectedPiece == true) {
    	    		secondClick(mouseCoordsClick);
    	    		
    	    		//RESET
    	    		mapPieces.get(id_selected_piece).changeSelected();
    	    		mapPieces.get(id_selected_piece).refresh();
    	    		id_selected_piece = -1;	//reset dell'id del pezzo mosso
    	    		selectedPiece = false;

    	    	}
    	    	else {
    	    		if(firstClick(mouseCoordsClick)) {
    	    			//System.out.println("> Pezzo selezionato");	
    	    			mapPieces.get(getRectangleId(mouseCoordsClick)).changeSelected();		// ****** cambia la selezione ****** //
    	    			mapPieces.get(getRectangleId(mouseCoordsClick)).refresh();
    	    			selectedPiece = true;
    	    		}
    	    		else {
    	    			System.out.println("> Seleziona un pezzo! ");
    	    		}    	    		
    	    	}    	    	
    	    }
    	});
	}
	
	public boolean firstClick(Vector2 mouseClick) {
		System.out.println("** Primo click **");
		System.out.println("rect id: " + getRectangleId(mouseClick));
		if(getRectangleId(mouseClick) >= 0) {
			System.out.println("> Selezione pezzo numero: " + getRectangleId(mouseClick));
			id_selected_piece = getRectangleId(mouseClick);		//id selezione del pezzo
			return true;
		}
		return false;
	}
	
	public void secondClick(Vector2 mouseClick) {
		if(checkNewCoords(mouseClick)) {
			//movimento del pezzo selezionato
			mapPieces.get(id_selected_piece).setRectangle(mapPieces.get(id_selected_piece).setNewCoords(mouseClick));
		}
		else {
			//errore
			System.out.println("> Nuova posizione non valida");
		}
		
		//mapPieces.get(id_selected_piece).changeSelected();
	}
	
	private boolean checkNewCoords(Vector2 mouseClick) {
		int id_current_piece = 0;
		
		//prendere gli intervalli di x e y delle nuove coordinate post movimento
		Pair<Vector2, Vector2> newMovementCoords = mapPieces.get(id_selected_piece).getMovementNewCoords(mouseClick); //<TL, BR>
		Vector2 xRange = new Vector2(newMovementCoords.getKey().getA(), newMovementCoords.getValue().getA());
		Vector2 yRange = new Vector2(newMovementCoords.getKey().getB(), newMovementCoords.getValue().getB());

		//if(getRectangleId(mouseClick) == id_selected_piece) return false;

		for (Iterator iterator = mapPieces.iterator(); iterator.hasNext();) {
			if(id_current_piece != id_selected_piece) {
				Piece piece = (Piece) iterator.next();
				
				Vector2 current_x_range = new Vector2(piece.getTopLeft().getA(), piece.getBottomRight().getA());
				Vector2 current_y_range = new Vector2(piece.getTopLeft().getB(), piece.getBottomRight().getB());

				System.out.println("[" + xRange + "] || [" + current_x_range + " ]");

				//confronto nuove coordinate con i range di tutti i pezzi per vedere se ci sono conflitti
				if(yRange.isBetween(current_y_range) || xRange.isBetween(current_x_range) ){
					System.out.println("COORDINATE SOVRAPPOSTE!!");
					return false;
				}
				//else{
				//	System.out.println("OK!!");
				//}
			}
			//System.out.println(id_current_piece);
			id_current_piece++;
		}
		
		return true;
	}
	
	private int getRectangleId(Vector2 coords) {
		for (int i = 0; i < mapPieces.size(); i++) {
			Piece p = mapPieces.get(i);
			if(p.checkCoords(coords)) 
				return i;
		}
		return -1;
	}

}
