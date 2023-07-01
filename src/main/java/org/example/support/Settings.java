package org.example.support;

import java.awt.*;
import java.util.ArrayList;

public class Settings {
	/* ************** IMPOSTAZIONI GENERALI ************** */
	public static final int LABEL_SIZE = 22;
	public static final Color LABEL_COLOR = Color.BLACK;
	public static final String TEXTURE_PATH = "file:resources/texture/block_texture.png";


	/* ************** IMPOSTAZIONI DEL GIOCO ************** */
	//CAMPO -> dimensioni del campo sono 200x250 ci stanno 4 pedine 1x1 in orizzontale e 5 in verticale
	public static final int WINDOW_WIDTH = 600, WINDOW_HEIGHT = 750;
	public static final int MIN_VERTICAL_BOUNDS = 100, MIN_HORIZONTAL_BOUNDS = 100;
	private static Vector2 fieldBounds, START_COORDINATES;

	//PEZZI
	private static final int MIN_PIECE_WIDTH = (WINDOW_WIDTH - MIN_HORIZONTAL_BOUNDS * 2) / 4, MIN_PIECE_HEIGHT = MIN_PIECE_WIDTH;
	private static final Vector2 rect_1x1 = new Vector2(MIN_PIECE_WIDTH, MIN_PIECE_WIDTH);
	private static final Vector2 rect_1x2 = new Vector2(MIN_PIECE_WIDTH, MIN_PIECE_WIDTH * 2);
	private static final Vector2 rect_2x1 = new Vector2(MIN_PIECE_WIDTH * 2, MIN_PIECE_WIDTH);
	private static final Vector2 rect_2x2 = new Vector2(MIN_PIECE_WIDTH * 2, MIN_PIECE_WIDTH * 2);
	private static final ArrayList<Vector2> rectCollection = new ArrayList<>();

	public Settings(){
		/* GIOCO */
		//dimensioni campo
		fieldBounds = new Vector2(MIN_VERTICAL_BOUNDS, MIN_HORIZONTAL_BOUNDS);
		//START_COORDINATES = new Vector2(WINDOW_WIDTH - HORIZONTAL_BOUNDS, WINDOW_HEIGHT - VERTICAL_BOUNDS);
		START_COORDINATES = new Vector2(MIN_HORIZONTAL_BOUNDS, MIN_VERTICAL_BOUNDS);
		//Impostazioni dei Pezzi
		rectCollection.add(rect_1x1);
		rectCollection.add(rect_1x2);
		rectCollection.add(rect_2x1);
		rectCollection.add(rect_2x2);
	}

	/* METODI DELLE IMPSTAZIONI DEL GIOCO */
	public Vector2 getElement(int id) { return rectCollection.get(id); }
	public Vector2 getBounds() { return fieldBounds; }
	public Vector2 getStartCoordinates() { return START_COORDINATES; }
	public int getMaxPieceWidth(){ return MIN_PIECE_WIDTH * 2; }
	public int getMaxPieceHeight(){ return MIN_PIECE_HEIGHT * 2; }
	public int getMinPieceWidth(){ return MIN_PIECE_WIDTH; }
	public int getMinPieceHeight(){return MIN_PIECE_HEIGHT; }
	public int getMinHorizontalBounds(){ return MIN_HORIZONTAL_BOUNDS; }
	public int getMinVerticalBounds(){ return MIN_VERTICAL_BOUNDS; }
	public int getMaxVerticalBounds(){ return (WINDOW_HEIGHT - MIN_VERTICAL_BOUNDS); }
	public int getMaxHorizontalBounds(){ return (WINDOW_WIDTH - MIN_HORIZONTAL_BOUNDS);}
}
