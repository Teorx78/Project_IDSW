package game;

import javafx.util.Pair;
import json.JsonSolutionReader;
import piece.BlockGFX;
import support.MovementDirections;
import support.Settings;
import support.Vector2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class NextBestMove {
    private char[][] currentMatrix, nextMatrix;
    private String alphabet = "ABCDEFGHILMNOPQRSTUVZ";
    private final JsonSolutionReader jsr;
    private Pair<Vector2, Vector2> from_to = null;      //before, after
    NextBestMove(JsonSolutionReader jsr, ArrayList<BlockGFX> blocks){   //costruttore della classe che traduce direttamente la lista di blocchi in matrice di char
        currentMatrix = new char[5][4];
        nextMatrix = new char[5][4];
        this.jsr = jsr;

        for (char[] chars : currentMatrix) {
            Arrays.fill(chars, '0');
        }
        for(BlockGFX block : blocks){
            int x = block.getTopLeft().getX() / Settings.MIN_BOUNDS - 1;
            int y = block.getTopLeft().getY() / Settings.MIN_BOUNDS - 1;
            switch (block.getPrototype().blockType){
                case BLOCK_1X1 -> currentMatrix[y][x] = alphabet.charAt(block.getId());
                case BLOCK_1X2 -> {
                    currentMatrix[y][x] = alphabet.charAt(block.getId());
                    currentMatrix[y+1][x] = alphabet.charAt(block.getId());
                }
                case BLOCK_2X1 -> {
                    currentMatrix[y][x] = alphabet.charAt(block.getId());
                    currentMatrix[y][x+1] = alphabet.charAt(block.getId());
                }
                case BLOCK_2X2 -> {
                    currentMatrix[y][x] = alphabet.charAt(block.getId());
                    currentMatrix[y+1][x] = alphabet.charAt(block.getId());
                    currentMatrix[y][x+1] = alphabet.charAt(block.getId());
                    currentMatrix[y+1][x+1] = alphabet.charAt(block.getId());
                }
            }
        }
    }
    /* METODI DI SUPPORTO  */
    private int getIdFromLetter(char c){ return alphabet.indexOf(c); } //traduce da lettera ad int
    public Pair<Vector2, Vector2> getSavedMove(){ return from_to; }     //ritorna un Pair delle coordinate pre e post movimento
    /* METODI PER LA MOSSA SEGUENTE */
    private Vector2 getChangedEmpty(ArrayList<Vector2> currentEmpty, ArrayList<Vector2> nextEmpty){     //metodo per trovare quale spazio vuoto è cambiato
        boolean isDifferent = true;
        for (Vector2 current: currentEmpty) {
            for (Vector2 next : nextEmpty){
                if(current.isEqual(next)) isDifferent = false;
            }
            if(isDifferent)
                return current;
            isDifferent = true;
        }
        return null;
    }
    private char[][] translateMatrix(String matrixString) {     //metodo che traduce la stringa letta nel json in matrice
        char[][] newMatrix = new char[5][4];
        try {
            String[] arrString = matrixString.split(",");
            int pos = 0;

            for (String str : arrString) {
                String[] _str = str.split("");
                for (int i = 0; i < _str.length; i++) {
                    String s = _str[i];
                    newMatrix[pos][i] = s.charAt(0);
                }
                pos++;
            }
        } catch (NumberFormatException nex) {
            nex.printStackTrace();
        }
        return newMatrix;
    }
    private ArrayList<Vector2> findEmptySpaces(char[][]matrix){         //metodo che trova lo spazio vuoto che è cambiato
        ArrayList<Vector2> emptyCells = new ArrayList<>();
        int x, y = 0;
        for (char[] row: matrix){
            x=0;
            for (char ch: row) {
                if (ch == '0') emptyCells.add(new Vector2(x,y));
                x++;
            }
            y++;
        }
        return emptyCells;
    }
    private Vector2 getBeforeCoords(MovementDirections movementDirection, Vector2 after){       //trova le coordiate prima del movimento
        switch (movementDirection){
            case UP -> {
                return new Vector2(after.getX(), after.getY()-1);
            }
            case DOWN -> {
                return new Vector2(after.getX(), after.getY()+1);
            }
            case RIGHT -> {
                return new Vector2(after.getX()+1, after.getY());
            }
            case LEFT -> {
                return new Vector2(after.getX()-1, after.getY());
            }
        }
        return null;
    }
    /* METODI PER LA RICERCA DELLA GRIGLIA CORRISPONDENTE  */
    private void findMatrix(){          //trova la matrice corrispondende alla posizione dei blocchi attuali e setta anche la matrice seguente
        Map<Object, Object> objSolutions = jsr.getConfigurationMap();
        boolean step = false;

        for (var entry : objSolutions.entrySet()) {
            String tempStr = (String) entry.getValue();
            char[][] tempMatrix = translateMatrix(tempStr);
            //scorro la matrice letta e quella attuale e cerco una modifica
            for(int i = 0; i < currentMatrix.length; i++){
                for (int j = 0; j < currentMatrix[i].length; j++){
                    if(currentMatrix[i][j] != tempMatrix[i][j]){
                        step = true;
                        break;
                    }
                }
                if(step) { //se ho trovato una differenza
                    break;
                }
            }
            if(step) step = false;
            else{   //se non ho trovato differenze
                Set<Object> keySet = objSolutions.keySet();
                for (Object s : keySet) {
                    if(objSolutions.get(s).equals(tempStr)) {
                        nextMatrix = translateMatrix((String) objSolutions.get((Integer.parseInt(((String)s))+1)+""));
                        break;
                    }
                }
                break;
            }
        }
    }
    /* METODO PER LA RESTITUZIONE DELLA PROSSIMA MOSSA */
    public Pair<Integer, MovementDirections> nextMove(){        //metodo che restituisce l'id e la direzione del movimento migliore
        findMatrix();       //trova la matrice attuale e la seguente
        Vector2 changedEmpty = getChangedEmpty(findEmptySpaces(currentMatrix), findEmptySpaces(nextMatrix));
        MovementDirections movementDirection = null;
        char movedChar;
        if(changedEmpty != null){
            movedChar = nextMatrix[changedEmpty.getY()][changedEmpty.getX()];
            try {
                if (currentMatrix[changedEmpty.getY() - 1][changedEmpty.getX()] == movedChar) {
                    movementDirection = MovementDirections.DOWN;
                    from_to = new Pair<>(changedEmpty, getBeforeCoords(MovementDirections.DOWN, changedEmpty));
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (currentMatrix[changedEmpty.getY() + 1][changedEmpty.getX()] == movedChar) {
                    movementDirection = MovementDirections.UP;
                    from_to = new Pair<>(changedEmpty, getBeforeCoords(MovementDirections.UP, changedEmpty));
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (currentMatrix[changedEmpty.getY()][changedEmpty.getX() - 1] == movedChar) {
                    movementDirection = MovementDirections.RIGHT;
                    from_to = new Pair<>(changedEmpty, getBeforeCoords(MovementDirections.RIGHT, changedEmpty));
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (currentMatrix[changedEmpty.getY()][changedEmpty.getX() + 1] == movedChar) {
                    movementDirection = MovementDirections.LEFT;
                    from_to = new Pair<>(changedEmpty, getBeforeCoords(MovementDirections.LEFT, changedEmpty));
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            return new Pair<>(getIdFromLetter(movedChar), movementDirection);
        }
        return null;
    }

}
