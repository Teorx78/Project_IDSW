package support;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Objects;

public class Node {
    private final Node lastNode;
    private final Pair<Integer, MovementDirections> father;     //<id, movimento>
    private ArrayList<Pair<Integer, MovementDirections>> sons;
    //<id, movimento_del_blocco> -> per convensione è il movimento del top_left
    public Node(Pair<Integer, MovementDirections> father, Node lastNode){
        this.father = father;
        this.lastNode = lastNode;
    }
    public Node() {
        this.father = null;
        this.lastNode = null;
    }
    //getters
    public Pair<Integer, MovementDirections> getFather(){ return father; }
    public Pair<Integer, MovementDirections> getOppositeFather(){
        MovementDirections movementDirection = null;
        switch (father.getValue()){
            case UP -> movementDirection = MovementDirections.DOWN;
            case DOWN -> movementDirection = MovementDirections.UP;
            case LEFT -> movementDirection = MovementDirections.RIGHT;
            case RIGHT -> movementDirection = MovementDirections.LEFT;
        }
        return new Pair<>(father.getKey(), movementDirection);
    }
//    public Node getOppositeFather(){
//        MovementDirections oppositeDirection = null;
//        switch (Objects.requireNonNull(father).getFather().){
//            case LEFT -> oppositeDirection = MovementDirections.RIGHT;
//            case RIGHT -> oppositeDirection = MovementDirections.LEFT;
//            case DOWN -> oppositeDirection = MovementDirections.UP;
//            case UP -> oppositeDirection = MovementDirections.DOWN;
//        }
//        return new Pair<>(father.getFather().getKey(), oppositeDirection);
//    }
    public ArrayList<Pair<Integer, MovementDirections>> getSons() { return sons; }
    public int nodeSonsSize(){ return sons.size(); }
    public Node getLastNode() { return lastNode; }
    //setters
    public void setSons(ArrayList<Pair<Integer, MovementDirections>> sons) { this.sons = sons; }

    @Override
    public String toString() {
        return "Node{" +
                "lastNode= " + lastNode +
                ", father= " + father +
                ", sons= " + sons +
                '}';
    }
}
