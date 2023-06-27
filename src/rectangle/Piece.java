package rectangle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import support.Vector2;


public class Piece extends Figure{

	private Color COLOR = Color.RED;
	private Rectangle rectangle;
	
	//Costruttore
	public Piece(Vector2 tl, int id_rect_type){
		super(tl, id_rect_type);
		//creazione oggetto
		rectangle = new Rectangle(TOP_LEFT.getA(), TOP_LEFT.getB(), WIDTH, HEIGHT);
		rectangle.setFill(Color.RED);
	}

	public Piece(Vector2 tl, Color color, int id_rect_type){
		super(tl, id_rect_type);
		//creazione oggetto
		this.COLOR = color;
		rectangle = new Rectangle(TOP_LEFT.getA(), TOP_LEFT.getB(), WIDTH, HEIGHT);
		rectangle.setFill(COLOR);
	}
	
	public Piece(int id_rect_type) {
		super(id_rect_type);
		rectangle = new Rectangle(TOP_LEFT.getA(), TOP_LEFT.getB(), WIDTH, HEIGHT);
		rectangle.setFill(Color.RED);
	}

	public Piece(int id_rect_type, Color color) {
		super(id_rect_type);
		this.COLOR = color;
		rectangle = new Rectangle(TOP_LEFT.getA(), TOP_LEFT.getB(), WIDTH, HEIGHT);
		rectangle.setFill(COLOR);
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
	
	public void setNewCoords(Vector2 newAngle) {
		if(SELECTED == true) {
			if((newAngle.getA() >= TOP_LEFT.getA() && newAngle.getA() <= BOTTOM_RIGHT.getA()) 
				|| (newAngle.getB() >= TOP_LEFT.getB() && newAngle.getB() <= BOTTOM_RIGHT.getB())) {
			
				if(newAngle.getA() >= BOTTOM_RIGHT.getA()) {
					rectangle.setX(rectangle.getX() + WIDTH);
					TOP_LEFT.addA(WIDTH);
					BOTTOM_RIGHT.addA(WIDTH);
					
				}
				else if(newAngle.getA() <= TOP_LEFT.getA()) {				
					rectangle.setX(rectangle.getX() - WIDTH);
					TOP_LEFT.subA(WIDTH);
					BOTTOM_RIGHT.subA(WIDTH);
				}
				else if(newAngle.getB() >= BOTTOM_RIGHT.getB()) {
					rectangle.setY(rectangle.getY() + HEIGHT);
					TOP_LEFT.addB(HEIGHT);
					BOTTOM_RIGHT.addB(HEIGHT);
				}
				else if(newAngle.getB() <= TOP_LEFT.getB()) {				
					rectangle.setY(rectangle.getY() - HEIGHT);			
					TOP_LEFT.subB(HEIGHT);
					BOTTOM_RIGHT.subB(HEIGHT);
				}
				
				//System.out.println(TOP_LEFT);
				//System.out.println(BOTTOM_RIGHT);
				//System.out.println("**************");
			}
			else {
				System.out.println("> ** Pezzo disattivato** ");
			}
			
		}
		//if(SELECTED == false) SELECTED = true;
		//else SELECTED = false;
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
