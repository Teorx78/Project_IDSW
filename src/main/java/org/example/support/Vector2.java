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
	public boolean isBetweenClosed(int i) {
		return (this.a <= i && i <= this.b);
	}
	public boolean isBetween(int i) {
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
	public boolean isEqual(Vector2 v2){
		return (this.a == v2.getA() && this.b == v2.getB());
	}
	public boolean isBetween(Vector2 v2){
		//contronto con l'intevallo della classe
		if(this.a < v2.a && v2.a < this.b) return true;		//a < v2.a < b
		if(this.a < v2.b && v2.b < this.b) return true;		//a < v2.b < b
		//confronto inverso
		if(v2.a < this.a && this.a < v2.b) return true;		//v2.a < a < v2.b
		if(v2.a < this.b && this.b < v2.b) return true;		//v2.a < b < v2.b

		return false;

	}

	@Override
	public String toString() {
		return a + ", " + b;
	}
}
