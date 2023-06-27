package support;

public class Vector2 {
	private int a, b;
	//Costruttore
	public Vector2(int a, int b){
		this.a = a;
		this.b = b;
	}
	//Setters
	public void setA(int a) {
		this.a = a;
	}
	public void setB(int b) {
		this.b = b;
	}
	//Getters
	public int getA() {
		return a;
	}
	public int getB() {
		return b;
	}
	//Operazioni
	//A
	public void addA(int add) {
		a += add;
	}
	public void subA(int sub) {
		a -= sub;
	}
	//B
	public void addB(int add) {
		b += add;
	}
	public void subB(int sub) {
		b -= sub;
	}
	public boolean isBetween(Vector2 coords) {
		//controllo se l'intervallo coords va ad intersecarsi con l'intervallo ]a, b[
		if(this.a < coords.a && this.b > coords.a ) return true; 	//se a < c.a < b
		if(this.a < coords.b && this.b > coords.b) return true;		//se a < c.b < b
		return false;
	}
	@Override
	public String toString() {
		return "A: " + a + "| B: " + b;
	}
}
