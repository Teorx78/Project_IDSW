package game;

import javafx.util.Pair;
import piece.BlockGFX;
import support.DuplicateMap;
import support.MovementDirections;
import support.Settings;
import support.Vector2;

import java.time.chrono.Chronology;
import java.util.*;

public class Undo{
//    public LinkedHashMap<BlockGFX, Pair<Vector2, Vector2>> undoMove(<BlockGFX, Pair<Vector2, Vector2>> chronology){
//        BlockGFX lastKey = getLastElement(chronology.keySet());
//        MovementDirections movementDirection = getMovementDirection(chronology.get(lastKey).getKey(), chronology.get(lastKey).getValue());
//        Settings.activeBlock.move(movementDirection);
//        chronology.remove(lastKey);
//        return chronology;
//    }
//
//    private MovementDirections getMovementDirection(Vector2 v1, Vector2 v2){
//        //v1 = prima, v2 = dopo
//        //restituisco la mossa inversa rispetto a quella che è stata fatta in precedenza
//        if(v1.getX() < v2.getX()) return MovementDirections.LEFT;
//        if(v1.getX() > v2.getX()) return MovementDirections.RIGHT;
//        if(v1.getY() < v2.getY()) return MovementDirections.UP;
//        return MovementDirections.DOWN;
////        if(v1.getY() > v2.getY()) return MovementDirections.UP;
////        return null;
//    }
//
//    private BlockGFX getLastElement(Set<BlockGFX> keySet){
//        ArrayList<BlockGFX> keyList = new ArrayList<>(keySet);
//        System.out.println("keylist size:" + (keyList.size() - 1));
//        return keyList.get(keyList.size() - 1);
    public DuplicateMap undoMove(DuplicateMap chronology){
        if(chronology.size() > 0) {
            MovementDirections movementDirection = getMovementDirection(
                    chronology.getValue(chronology.size() - 1).getKey(),
                    chronology.getValue(chronology.size() - 1).getValue());

            Settings.activeBlock = chronology.getKey(chronology.size() - 1);
            Settings.activeID = chronology.getKey(chronology.size() - 1).getId();

            Settings.activeBlock.move(movementDirection);

            if(Settings.activeBlock.getSelected()) Settings.activeBlock.changeSelected();
            Settings.activeBlock.refresh();

            Settings.activeBlock = null;
            Settings.activeID = -1;

            chronology.remove(chronology.size() - 1);

            if(chronology.size() <= 0) Game.getUndoButtonComponent().setDisable(true);
        }
        else Game.getUndoButtonComponent().setDisable(true);

        return chronology;
    }

    private MovementDirections getMovementDirection(Vector2 v1, Vector2 v2){
        //v1 = prima, v2 = dopo
        //restituisco la mossa inversa rispetto a quella che è stata fatta in precedenza
        if(v1.getX() < v2.getX()) return MovementDirections.LEFT;
        if(v1.getX() > v2.getX()) return MovementDirections.RIGHT;
        if(v1.getY() < v2.getY()) return MovementDirections.UP;
        return MovementDirections.DOWN;
    }
}
