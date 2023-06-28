package org.example.support;

public class Vector2 {
	private int a, b;
	//Costruttore
	public Vector2(int a, int b){
		this.a = a;
		this.b = b;
	}
	public Vector2(Vector2 newVect){
		this.a = newVect.getA();
		this.b = newVect.getB();
	}

	//metodi di suppporto
	private boolean isBetweenClosed(int i){
		return (this.a <= i && i <= this.b);
	}
	private boolean isBetween(int i){
		return (this.a < i && i < this.b);
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
		if(isBetween(coords.a) && isBetween(coords.b)) return true;
		return false;
	}
	public boolean isBetweenClosed(Vector2 coords) {
		//controllo se l'intervallo coords va ad intersecarsi con l'intervallo [a, b]
		if(isBetweenClosed(coords.a) && isBetweenClosed(coords.b)) return true;
		return false;
	}
//	public String toString() {
//		return "A: " + a + ", B: " + b;
//	}
	@Override
	public String toString() {
		return a + ", " + b;
	}
}
