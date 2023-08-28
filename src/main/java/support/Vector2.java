package support;

/**
 * La classe `Vector2` rappresenta un vettore bidimensionale con coordinate intere (x, y).
 */
public class Vector2 {
	private int x, y;

	/**
	 * Costruttore della classe che inizializza un nuovo vettore con le coordinate specificate.
	 * @param x La coordinata x del vettore.
	 * @param y La coordinata y del vettore.
	 */
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Costruttore della classe che crea un nuovo vettore identico a un altro vettore esistente.
	 * @param newVect Il vettore da cui copiare le coordinate.
	 */
	public Vector2(Vector2 newVect) {
		this.x = newVect.getX();
		this.y = newVect.getY();
	}

	// Metodi Setters

	/**
	 * Imposta la coordinata x del vettore.
	 * @param x La nuova coordinata x da impostare.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Imposta la coordinata y del vettore.
	 * @param y La nuova coordinata y da impostare.
	 */
	public void setY(int y) {
		this.y = y;
	}

	// Metodi Getters

	/**
	 * Restituisce la coordinata x del vettore.
	 * @return La coordinata x del vettore.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Restituisce la coordinata y del vettore.
	 * @return La coordinata y del vettore.
	 */
	public int getY() {
		return y;
	}

	// Metodi per operazioni matematiche

	/**
	 * Aggiunge un valore specificato alla coordinata x del vettore.
	 * @param add Il valore da aggiungere alla coordinata x.
	 */
	public void addX(int add) {
		x += add;
	}

	/**
	 * Sottrae un valore specificato dalla coordinata x del vettore.
	 * @param sub Il valore da sottrarre dalla coordinata x.
	 */
	public void subX(int sub) {
		x -= sub;
	}

	/**
	 * Aggiunge un valore specificato alla coordinata y del vettore.
	 * @param add Il valore da aggiungere alla coordinata y.
	 */
	public void addY(int add) {
		y += add;
	}

	/**
	 * Sottrae un valore specificato dalla coordinata y del vettore.
	 * @param sub Il valore da sottrarre dalla coordinata y.
	 */
	public void subY(int sub) {
		y -= sub;
	}

	/**
	 * Verifica se due vettori hanno le stesse coordinate.
	 * @param v2 Il secondo vettore da confrontare con questo vettore.
	 * @return `true` se i due vettori hanno le stesse coordinate, altrimenti `false`.
	 */
	public boolean isEqual(Vector2 v2) {
		return (this.x == v2.getX() && this.y == v2.getY());
	}

	/**
	 * Verifica se questo vettore si trova tra due coordinate specificate.
	 * @param v2 Il secondo vettore che rappresenta l'intervallo.
	 * @return `true` se questo vettore si trova tra le coordinate specificate, altrimenti `false`.
	 */
	public boolean isBetween(Vector2 v2) {
		if (this.x < v2.x && v2.x < this.y) return true;  // a < v2.a < b
		if (this.x < v2.y && v2.y < this.y) return true;  // a < v2.b < b
		// Confronto inverso
		if (v2.x < this.x && this.x < v2.y) return true;  // v2.a < a < v2.b
		return v2.x < this.y && this.y < v2.y;  // v2.a < b < v2.b
	}

	/**
	 * Restituisce una rappresentazione testuale di questo vettore nel formato "[x, y]".
	 * @return Una stringa che rappresenta questo vettore.
	 */
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
