package support;

public class Vector2 {
	private int x, y;
	//Costruttore
	public Vector2(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Vector2(Vector2 newVect){
		this.x = newVect.getX();
		this.y = newVect.getY();
	}

	//metodi di suppporto
	public boolean isBetweenClosed(int i) {
		return (this.x <= i && i <= this.y);
	}
	public boolean isBetween(int i) {
		return (this.x < i && i < this.y);
	}

	//Setters
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	//Getters
	public int getX() {
		return x;
}
	public int getY() {
		return y;
	}
	//Operazioni
	//A
	public void addX(int add) {
		x += add;
	}
	public void subX(int sub) {
		x -= sub;
	}
	//B
	public void addY(int add) {
		y += add;
	}
	public void subY(int sub) {
		y -= sub;
	}
	public boolean isEqual(Vector2 v2){
		return (this.x == v2.getX() && this.y == v2.getY());
	}
	public boolean isBetween(Vector2 v2){
		//contronto con l'intevallo della classe
		if(this.x < v2.x && v2.x < this.y) return true;		//a < v2.a < b
		if(this.x < v2.y && v2.y < this.y) return true;		//a < v2.b < b
		//confronto inverso
		if(v2.x < this.x && this.x < v2.y) return true;		//v2.a < a < v2.b
		return v2.x < this.y && this.y < v2.y;		//v2.a < b < v2.b
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
