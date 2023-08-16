package support;

import javafx.util.Pair;
import piece.BlockGFX;
import java.util.ArrayList;

public class DuplicateMap {
    private final ArrayList<BlockGFX> keys;
    private final ArrayList<Pair<Vector2, Vector2>> values;     //<before, after>
    private int size = 0;
    public DuplicateMap(){
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }
    public DuplicateMap(ArrayList<BlockGFX> keys, ArrayList<Pair<Vector2, Vector2>> values){
        this.keys = keys;
        this.values = values;
        //todo: verificare che i due array abbiano la stessa grandezza in caso contrario settare a null i rimanenti
    }
    /*SETTERS*/
    public void setKey(BlockGFX newKey){
        keys.add(newKey);
        values.add(null);
        size++;
    }
    public void setValue(Pair<Vector2, Vector2> newValue){
        keys.add(null);
        values.add(newValue);
        size++;
    }
    public void put(BlockGFX key, Pair<Vector2, Vector2> value){
        keys.add(key);
        values.add(value);
        size++;
    }
    public boolean remove(int index) {
        if (index > keys.size() - 1 || index > values.size() - 1) return false;
        keys.remove(index);
        values.remove(index);
        size--;
        return true;
    }
    /*GETTERS*/
    public BlockGFX getKey(int index){
        if(index > keys.size() - 1) return null;
        return keys.get(index);
    }
    public Pair<Vector2, Vector2> getValue(int index){
        if(index > values.size() - 1) return null;
        return values.get(index);
    }
    public Pair<BlockGFX, Pair<Vector2,Vector2>> get(int index){
        return new Pair<>(keys.get(index), values.get(index));
    }
    public int size(){ return size; }
    public MovementDirections getMovementDirection(int index){
        Pair<Vector2, Vector2> value = values.get(index);
        if(value.getKey().getX() < value.getValue().getX()) return MovementDirections.RIGHT;
        if(value.getKey().getX() > value.getValue().getX()) return MovementDirections.LEFT;
        if(value.getKey().getY() > value.getValue().getY()) return MovementDirections.DOWN;
        return MovementDirections.UP;
    }
}
