package game;

import support.DuplicateMap;
import support.MovementDirections;
import support.Settings;
import support.Vector2;

/**
 * La classe `Undo` fornisce un metodo per annullare l'ultima mossa effettuata nel gioco
 * e rimuoverla dalla cronologia delle mosse.
 */
public class Undo {
    /**
     * Metodo per annullare l'ultima mossa effettuata nel gioco e rimuoverla dalla cronologia delle mosse.
     *
     * @param chronology La cronologia delle mosse che contiene le informazioni sulle azioni precedenti.
     * @return La cronologia aggiornata dopo l'annullamento dell'ultima mossa.
     */
    public DuplicateMap undoMove(DuplicateMap chronology) {
        if (chronology.size() > 0) {
            MovementDirections movementDirection = getMovementDirection(
                    chronology.getValue(chronology.size() - 1).getKey(),
                    chronology.getValue(chronology.size() - 1).getValue());

            Settings.activeBlock = chronology.getKey(chronology.size() - 1);
            Settings.activeID = chronology.getKey(chronology.size() - 1).getId();

            if (Settings.activeBlock.getSelected()) Settings.activeBlock.changeSelected();
            Settings.activeBlock.move(movementDirection);

            Settings.activeBlock.refresh();

            Settings.activeBlock = null;
            Settings.activeID = -1;

            chronology.remove(chronology.size() - 1);
        }
        return chronology;
    }

    /**
     * Metodo privato per ottenere la direzione di movimento inversa.
     *
     * @param v1 Il vettore delle coordinate prima del movimento.
     * @param v2 Il vettore delle coordinate dopo il movimento.
     * @return La direzione di movimento inversa rispetto alla mossa effettuata.
     */
    private MovementDirections getMovementDirection(Vector2 v1, Vector2 v2) {
        // v1 = prima, v2 = dopo
        // Restituisce la direzione di movimento inversa rispetto a quella che è stata fatta in precedenza
        if (v1.getX() < v2.getX()) return MovementDirections.LEFT;
        if (v1.getX() > v2.getX()) return MovementDirections.RIGHT;
        if (v1.getY() < v2.getY()) return MovementDirections.UP;
        return MovementDirections.DOWN;
    }
}
