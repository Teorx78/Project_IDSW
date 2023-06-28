package org.example.support;

import java.util.ArrayList;
import org.example.support.Vector2;
public class Settings {

	//CAMPO -> dimensioni del campo sono 200x250 ci stanno 4 pedine 1x1 in orizzontale e 5 in verticale
	private static final int WINDOW_WIDTH = 600, WINDOW_HEIGHT = 750;
	private static final int VERTICAL_BOUNDS = 75, HORIZONTAL_BOUNDS = 75;
	private static Vector2 fieldBounds, START_COORDINATES;
	//PEZZI
	private static int MAX_PIECE_WIDTH = WINDOW_WIDTH / 4, MAX_PIECE_HEIGHT = WINDOW_WIDTH / 4;
	private static Vector2 rect_1x1 = new Vector2(MAX_PIECE_WIDTH / 2, MAX_PIECE_HEIGHT / 2);
	private static Vector2 rect_1x2 = new Vector2(MAX_PIECE_WIDTH / 2, MAX_PIECE_HEIGHT);
	private static Vector2 rect_2x1 = new Vector2(MAX_PIECE_WIDTH, MAX_PIECE_HEIGHT / 2);
	private static Vector2 rect_2x2 = new Vector2(MAX_PIECE_WIDTH, MAX_PIECE_HEIGHT);
	private static ArrayList<Vector2> rectCollection = new ArrayList<>();


	//TODO: sistemare le dimensioni dei rettangoli devono essere 1/4 della larghezza della finestra a meno dei bounds

	public Settings(){
		//dimensioni campo
		fieldBounds = new Vector2(VERTICAL_BOUNDS, HORIZONTAL_BOUNDS);
		//START_COORDINATES = new Vector2(WINDOW_WIDTH - HORIZONTAL_BOUNDS, WINDOW_HEIGHT - VERTICAL_BOUNDS);
		START_COORDINATES = new Vector2(HORIZONTAL_BOUNDS, VERTICAL_BOUNDS);
		
		//dimensioni rettangoli -> Vecotor2(WIDTH, HEIGHT)
		/*rect_1x1 = new Vector2(MAX_PIECE_WIDTH / 2, MAX_PIECE_HEIGHT / 2);
		rect_1x2 = new Vector2(MAX_PIECE_WIDTH / 2, MAX_PIECE_HEIGHT);
		rect_2x1 = new Vector2(MAX_PIECE_WIDTH, MAX_PIECE_HEIGHT / 2);
		rect_2x2 = new Vector2(MAX_PIECE_WIDTH, MAX_PIECE_HEIGHT);*/
		
		//Impostazioni dei Pezzi
		rectCollection.add(rect_1x1);
		rectCollection.add(rect_1x2);
		rectCollection.add(rect_2x1);
		rectCollection.add(rect_2x2);
	}

	public Vector2 getElement(int id) { return rectCollection.get(id); }
	public Vector2 getBounds() {
		return fieldBounds;
	}
	public Vector2 getStartCoordinates() {
		return START_COORDINATES;
	}
	public int getMaxPieceWidth(){ return MAX_PIECE_WIDTH; }
	public int getMaxPieceHeight(){ return MAX_PIECE_HEIGHT; }
}
