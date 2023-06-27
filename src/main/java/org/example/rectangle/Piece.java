package org.example.rectangle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.rectangle.Figure;
import org.example.support.Vector2;


public class Piece extends Figure {

	private Color COLOR = Color.RED;
	private final double STROKE_WIDTH = 0.8;
	private final Color STROKE_COLOR = Color.BLACK;
	private Rectangle rectangle;
	
	//Costruttore
	public Piece(Vector2 tl, int id_rect_type){
		super(tl, id_rect_type);
		//creazione oggetto
		rectangle = new Rectangle(TOP_LEFT.getA(), TOP_LEFT.getB(), WIDTH, HEIGHT);
		rectangle.setFill(Color.RED);
		rectangle.setStroke(STROKE_COLOR);
		rectangle.setStrokeWidth(STROKE_WIDTH);
	}

	public Piece(Vector2 tl, Color color, int id_rect_type){
		super(tl, id_rect_type);
		//creazione oggetto
		this.COLOR = color;
		rectangle = new Rectangle(TOP_LEFT.getA(), TOP_LEFT.getB(), WIDTH, HEIGHT);
		rectangle.setFill(COLOR);
		rectangle.setStroke(STROKE_COLOR);
		rectangle.setStrokeWidth(STROKE_WIDTH);
	}
	
	public Piece(int id_rect_type) {
		super(id_rect_type);
		rectangle = new Rectangle(TOP_LEFT.getA(), TOP_LEFT.getB(), WIDTH, HEIGHT);
		rectangle.setFill(Color.RED);
		rectangle.setStroke(STROKE_COLOR);
		rectangle.setStrokeWidth(STROKE_WIDTH);
	}

	public Piece(int id_rect_type, Color color) {
		super(id_rect_type);
		this.COLOR = color;
		rectangle = new Rectangle(TOP_LEFT.getA(), TOP_LEFT.getB(), WIDTH, HEIGHT);
		rectangle.setFill(COLOR);
		rectangle.setStroke(STROKE_COLOR);
		rectangle.setStrokeWidth(STROKE_WIDTH);
	}
	
	public Rectangle getRectangle() {	//creazione del rettangolo
		//rectangle.setFill(Color.RED);
		//TODO: inserire e sistemare le opacità
		rectangle.setOnMousePressed(e -> {
			if(SELECTED == true) {
				rectangle.opacityProperty().set(0.5);
			}
			else {
				rectangle.opacityProperty().set(1);
			}
		});
		rectangle.setOnMouseReleased(e -> {
		  if(SELECTED == true) {			  
			  rectangle.opacityProperty().set(0.5);
		  }
		  else{
			  rectangle.opacityProperty().set(1);
		  }
		});
		
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle){ this.rectangle = rectangle; }

	public Rectangle setNewCoords(Vector2 newAngle) {
		if(SELECTED == true) {
			//if((newAngle.getA() >= TOP_LEFT.getA() && newAngle.getA() <= BOTTOM_RIGHT.getA())
			//	|| (newAngle.getB() >= TOP_LEFT.getB() && newAngle.getB() <= BOTTOM_RIGHT.getB())) {

				int offsetX = TOP_LEFT.getA(), offsetY = TOP_LEFT.getB();

				System.out.println(rectangle.getX() + " " + rectangle.getY());

				if(newAngle.getA() > BOTTOM_RIGHT.getA()) {
					TOP_LEFT.addA(WIDTH);
					BOTTOM_RIGHT.addA(WIDTH);
					offsetX = BOTTOM_RIGHT.getA();
				}
				if(newAngle.getA() < TOP_LEFT.getA()) {
					TOP_LEFT.subA(WIDTH);
					BOTTOM_RIGHT.subA(WIDTH);
					offsetX = TOP_LEFT.getA();
				}
				if(newAngle.getB() > BOTTOM_RIGHT.getB()) {
					TOP_LEFT.addB(HEIGHT);
					BOTTOM_RIGHT.addB(HEIGHT);
					offsetY = BOTTOM_RIGHT.getB();
				}
				if(newAngle.getB() < TOP_LEFT.getB()) {
					TOP_LEFT.subB(HEIGHT);
					BOTTOM_RIGHT.subB(HEIGHT);
					offsetY = TOP_LEFT.getB();
				}

				System.out.println(offsetX + " " + offsetY);

				rectangle.setX(offsetX);
				rectangle.setY(offsetY);
			}
			else {
				System.out.println("> ** Pezzo disattivato** ");
			}

		//}
		return rectangle;
	}

	public void refresh() {
		//refresh delle texture
		if(SELECTED == true) {
			rectangle.opacityProperty().set(0.5);
		}
		else {
			rectangle.opacityProperty().set(1);
		}
	}
	

}
