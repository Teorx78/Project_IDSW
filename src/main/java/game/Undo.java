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
    public DuplicateMap undoMove(DuplicateMap chronology){      //metodo che ritorna indietro di una mossa e toglie dalla cronologia quella determinata mossa
        if(chronology.size() > 0) {
            MovementDirections movementDirection = getMovementDirection(
                    chronology.getValue(chronology.size() - 1).getKey(),
                    chronology.getValue(chronology.size() - 1).getValue());

            Settings.activeBlock = chronology.getKey(chronology.size() - 1);
            Settings.activeID = chronology.getKey(chronology.size() - 1).getId();

            if(Settings.activeBlock.getSelected()) Settings.activeBlock.changeSelected();
            Settings.activeBlock.move(movementDirection);

            Settings.activeBlock.refresh();

            Settings.activeBlock = null;
            Settings.activeID = -1;

            chronology.remove(chronology.size() - 1);
        }
        return chronology;
    }

    private MovementDirections getMovementDirection(Vector2 v1, Vector2 v2){        //riturn della mossa inversa fatta dal blocco
        //v1 = prima, v2 = dopo
        //restituisco la mossa inversa rispetto a quella che è stata fatta in precedenza
        if(v1.getX() < v2.getX()) return MovementDirections.LEFT;
        if(v1.getX() > v2.getX()) return MovementDirections.RIGHT;
        if(v1.getY() < v2.getY()) return MovementDirections.UP;
        return MovementDirections.DOWN;
    }
}
