package support;

import javafx.util.Pair;
import piece.BlockGFX;

import java.util.ArrayList;

/**
 * La classe `DuplicateMap` rappresenta una mappa specializzata che associa oggetti di tipo `BlockGFX` a coppie di vettori `Vector2`.
 * Ogni chiave (`BlockGFX`) è associata a un valore (`Pair<Vector2, Vector2>`) che rappresenta le coordinate prima e dopo un movimento.
 * Questa classe è utile per tenere traccia delle mosse duplicate in un gioco o in una situazione in cui è necessario associare blocchi a spostamenti.
 */
public class DuplicateMap {
    private final ArrayList<BlockGFX> keys;
    private final ArrayList<Pair<Vector2, Vector2>> values;     //<before, after>
    private int size = 0;

    /**
     * Crea una nuova istanza di `DuplicateMap` vuota.
     */
    public DuplicateMap() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }

    /**
     * Crea una nuova istanza di `DuplicateMap` con chiavi e valori specificati.
     *
     * @param keys   Una lista di chiavi (`BlockGFX`).
     * @param values Una lista di coppie di vettori (`Pair<Vector2, Vector2>`).
     */
    public DuplicateMap(ArrayList<BlockGFX> keys, ArrayList<Pair<Vector2, Vector2>> values) {
        this.keys = keys;
        this.values = values;
    }

    /*SETTERS*/

    /**
     * Aggiunge una nuova chiave (`BlockGFX`) alla mappa senza associare un valore.
     *
     * @param newKey La nuova chiave da aggiungere.
     */
    public void setKey(BlockGFX newKey) {
        keys.add(newKey);
        values.add(null);
        size++;
    }

    /**
     * Aggiunge un nuovo valore (`Pair<Vector2, Vector2>`) alla mappa senza associare una chiave.
     *
     * @param newValue Il nuovo valore da aggiungere.
     */
    public void setValue(Pair<Vector2, Vector2> newValue) {
        keys.add(null);
        values.add(newValue);
        size++;
    }

    /**
     * Associa una chiave (`BlockGFX`) a un valore (`Pair<Vector2, Vector2>`) nella mappa.
     *
     * @param key   La chiave da associare.
     * @param value Il valore da associare.
     */
    public void put(BlockGFX key, Pair<Vector2, Vector2> value) {
        keys.add(key);
        values.add(value);
        size++;
    }

    /**
     * Rimuove una chiave e il suo valore associato dalla mappa in base all'indice specificato.
     *
     * @param index L'indice dell'elemento da rimuovere.
     * @return `true` se l'elemento è stato rimosso con successo, altrimenti `false`.
     */
    public boolean remove(int index) {
        if (index > keys.size() - 1 || index > values.size() - 1) return false;
        keys.remove(index);
        values.remove(index);
        size--;
        return true;
    }

    /*GETTERS*/

    /**
     * Restituisce la chiave (`BlockGFX`) associata all'indice specificato.
     *
     * @param index L'indice della chiave da restituire.
     * @return La chiave associata all'indice specificato o `null` se l'indice è fuori dai limiti.
     */
    public BlockGFX getKey(int index) {
        if (index > keys.size() - 1) return null;
        return keys.get(index);
    }

    /**
     * Restituisce il valore (`Pair<Vector2, Vector2>`) associato all'indice specificato.
     *
     * @param index L'indice del valore da restituire.
     * @return Il valore associato all'indice specificato o `null` se l'indice è fuori dai limiti.
     */
    public Pair<Vector2, Vector2> getValue(int index) {
        if (index > values.size() - 1) return null;
        return values.get(index);
    }

    /**
     * Restituisce una coppia chiave-valore (`Pair<BlockGFX, Pair<Vector2, Vector2>>`) associata all'indice specificato.
     *
     * @param index L'indice della coppia da restituire.
     * @return La coppia chiave-valore associata all'indice specificato o una coppia con chiave e valore nulli se l'indice è fuori dai limiti.
     */
    public Pair<BlockGFX, Pair<Vector2, Vector2>> get(int index) {
        return new Pair<>(keys.get(index), values.get(index));
    }

    /**
     * Verifica se la mappa contiene una specifica chiave (`BlockGFX`).
     *
     * @param block La chiave da cercare nella mappa.
     * @return `true` se la chiave è presente nella mappa, altrimenti `false`.
     */
    public boolean containsKey(BlockGFX block) {
        for (BlockGFX blockGFX : keys) if (blockGFX.equals(block)) return true;
        return false;
    }

    /**
     * Restituisce il numero di elementi nella mappa.
     *
     * @return Il numero di elementi nella mappa.
     */
    public int size() {
        return size;
    }

    /**
     * Restituisce la direzione di movimento associata a un elemento nella mappa in base all'indice specificato.
     *
     * @param index L'indice dell'elemento nella mappa.
     * @return La direzione di movimento (`MovementDirections`) associata all'elemento.
     */
    public MovementDirections getMovementDirection(int index) {
        Pair<Vector2, Vector2> value = values.get(index);
        if (value.getKey().getX() < value.getValue().getX()) return MovementDirections.RIGHT;
        if (value.getKey().getX() > value.getValue().getX()) return MovementDirections.LEFT;
        if (value.getKey().getY() > value.getValue().getY()) return MovementDirections.DOWN;
        return MovementDirections.UP;
    }
}
