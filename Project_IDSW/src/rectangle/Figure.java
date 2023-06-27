package rectangle;

import javafx.util.Pair;
import support.Settings;
import support.Vector2;

public class Figure {
	protected Vector2 TOP_LEFT, BOTTOM_RIGHT;
	protected int WIDTH, HEIGHT;
	protected boolean SELECTED;
		
	//Costruttore
	public Figure(Vector2 top_left, int id_rect_type) {
		Settings s = new Settings();
		//Angoli
		TOP_LEFT = top_left;
		BOTTOM_RIGHT = new Vector2(
				top_left.getA() + s.getElement(id_rect_type).getA(), 
				top_left.getB() + s.getElement(id_rect_type).getB()
			);
		//Lunghezza e Larghezza
		WIDTH = BOTTOM_RIGHT.getA() - TOP_LEFT.getA();
		HEIGHT = BOTTOM_RIGHT.getB() - TOP_LEFT.getB();		
		//Stato
		SELECTED = false;
	}
	
	public Figure(int id_rect_type) {
		Settings s = new Settings();
		//Angoli
		TOP_LEFT = new Vector2(s.getStartCoordinates().getA(), s.getStartCoordinates().getB());
		BOTTOM_RIGHT = new Vector2(TOP_LEFT.getA() + s.getElement(id_rect_type).getA(), 
									TOP_LEFT.getB() + s.getElement(id_rect_type).getB());
		//Lunghezza e Larghezza
		WIDTH = s.getElement(id_rect_type).getA();
		HEIGHT = s.getElement(id_rect_type).getB();
		//Stato
		SELECTED = false;
	}
	
	//Getters
	public Vector2 getTopLeft() { return TOP_LEFT; }
	public Vector2 getBottomRight() { return BOTTOM_RIGHT; }
	public Vector2 getTopRight() { return new Vector2(TOP_LEFT.getA() + WIDTH, TOP_LEFT.getB()); }
	public Vector2 getBottomLeft() { return new Vector2(BOTTOM_RIGHT.getA() - WIDTH, BOTTOM_RIGHT.getB()); }
	
	public int getTopX() { return TOP_LEFT.getA(); }
	public int getTopY() { return TOP_LEFT.getB(); }
	
	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }
	
	public boolean getSelected() { return SELECTED; }
	
	//Setters
	public boolean changeSelected() {
		SELECTED = !SELECTED;
		System.out.println(SELECTED);
		return SELECTED;
	}
	public void setTL(Vector2 v) {
		TOP_LEFT = v;
	}
	public void setBR(Vector2 v) {
		BOTTOM_RIGHT = v;
	}
	
	//Metodi
	public Pair<Vector2, Vector2> getMovementNewCoords(Vector2 mouseClick) {	//Pair<TOP_LEFT, BOTTOM_RIGHT>
		Vector2 TL = TOP_LEFT, BR = BOTTOM_RIGHT;
		System.out.println("> ** Coordinate Attuali: TL" + TL + " || BR: " + BR + "** ");
		if(mouseClick.getA() >= BOTTOM_RIGHT.getA()) {
			TL.addA(WIDTH);
			BR.addA(WIDTH);
		}
		if(mouseClick.getA() <= TOP_LEFT.getA()) {				
			TL.subA(WIDTH);
			BR.subA(WIDTH);
		}
		if(mouseClick.getB() >= BOTTOM_RIGHT.getB()) {
			TL.addB(HEIGHT);
			BR.addB(HEIGHT);
		}
		if(mouseClick.getB() <= TOP_LEFT.getB()) {						
			TL.subB(HEIGHT);
			BR.subB(HEIGHT);
		}
		System.out.println("> ** Nuove coordinate: TL: " + TL + " || Br: " + BR + "** ");
		
		return new Pair<Vector2, Vector2> (TL, BR);
	}
	
	public boolean checkCoords(Vector2 coords) {
		boolean x = false;
		boolean y = false;
		if(TOP_LEFT.getA() <= coords.getA() && BOTTOM_RIGHT.getA() >= coords.getA()) x = true;
		if(TOP_LEFT.getB() <= coords.getB() && BOTTOM_RIGHT.getB() >= coords.getB()) y = true;
		//System.out.println("> AND: "+ "x: " + x + " | y: " + y + " | xy: " + (x && y));
		return x && y;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TL: " + TOP_LEFT.toString() +  "|| " + "BR: " + BOTTOM_RIGHT.toString();
	}
}
