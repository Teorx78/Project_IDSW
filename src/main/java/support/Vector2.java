package support;

public class Vector2 {
	private int x, y;
	//Costruttore
	public Vector2(int x, int y){	//costruttore della classe di base
		this.x = x;
		this.y = y;
	}
	public Vector2(Vector2 newVect){	//costruttore della classe partendo da un altro vettore
		this.x = newVect.getX();
		this.y = newVect.getY();
	}

	//Setters
	public void setX(int x) {	//set della coordinata x
		this.x = x;
	}
	public void setY(int y) {	//set della coordinata y
		this.y = y;
	}
	//Getters
	public int getX() {		//return della coordinata x
		return x;
}
	public int getY() {		//return della coordinata y
		return y;
	}
	//Operazioni
	public void addX(int add) {		//somma add alla coordinata x
		x += add;
	}
	public void subX(int sub) {		//sottrae sub alla coordinata x
		x -= sub;
	}
	public void addY(int add) {			//somma add alla coordinata y
		y += add;
	}
	public void subY(int sub) {		//sottrae sub alla coordinata y
		y -= sub;
	}
	public boolean isEqual(Vector2 v2){		//controlla se due vettori hanno le coordinate uguali
		return (this.x == v2.getX() && this.y == v2.getY());
	}
	public boolean isBetween(Vector2 v2){			//controlla se il vettore passato e l'attuale si intersecano
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
