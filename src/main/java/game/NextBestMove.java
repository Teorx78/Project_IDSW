package game;

import javafx.util.Pair;
import json.JsonSolutionReader;
import piece.BlockGFX;
import support.MovementDirections;
import support.Settings;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;

public class NextBestMove {
    private int POSITION = 0;
    private char[][] currentMatrix;
    public NextBestMove(JsonSolutionReader jsr, ArrayList<BlockGFX> blocks){
        String alphabet = "ABCDEFGHILMNOPQRSTUVZ";
        currentMatrix = new char[5][4];
        for (char[] chars : currentMatrix) {
            Arrays.fill(chars, '0');
        }
        for(BlockGFX block : blocks){
            int x = block.getTopLeft().getX() / Settings.MIN_HORIZONTAL_BOUNDS - 1;
            int y = block.getTopLeft().getY() / Settings.MIN_HORIZONTAL_BOUNDS - 1;
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
        for (char[] row: currentMatrix) {
            for (char id : row)
                System.out.print(id);
            System.out.println();
        }
        translateMatrix(jsr.getElement("0"));
    }
    private int getIdFromLetter(char c){
        String alphabet = "ABCDEFGHILMNOPQRSTUVZ";
        return alphabet.indexOf(c);
    }
    private char getLetterFromId(int id){
        String alphabet = "ABCDEFGHILMNOPQRSTUVZ";
        return alphabet.charAt(id);
    }
    private char[][] translateMatrix(String matrixString) {
        char[][] newMatrix = new char[5][4];
        try {
            String[] arrString = matrixString.split(",");
            int pos = 0;

            for (String str : arrString) {
                String[] _str = str.split("");
                System.out.println(Arrays.toString(_str));
                for (int i = 0; i < _str.length; i++) {
                    String s = _str[i];
                    newMatrix[pos][i] = getLetterFromId(s.charAt(0) - '0');
                }
                pos++;
            }
        } catch (NumberFormatException nex) {
            nex.printStackTrace();
        }
        for (char[] row : newMatrix) {
            for (char id : row)
                System.out.print(id);
            System.out.println();
        }
        return null;
    }
    private Pair<Character, MovementDirections> getNextMove(){
        return null;
    }

    //todo: rimuovere
    NextBestMove(ArrayList<BlockGFX> initialGrid){
        char[][] initialBoard = new char[5][4];          //0 (zero) equivale agli spazi vuoti
        for (char[] chars : initialBoard) {
            Arrays.fill(chars, '0');
        }
        for (BlockGFX block : initialGrid){
            int x = (block.getTopLeft().getX() / Settings.MIN_HORIZONTAL_BOUNDS) - 1;
            int y = (block.getTopLeft().getY() / Settings.MIN_VERTICAL_BOUNDS) - 1;
//            System.out.println("[" + x + ", " + y + "]");
            switch (block.getPrototype().blockType){
                case BLOCK_1X1 -> {
                    // #
                    initialBoard[y][x] = getLetterFromId(block.getId());
                }
                case BLOCK_1X2 -> {
                    // #
                    // #
                    initialBoard[y][x] = getLetterFromId(block.getId());
                    initialBoard[y + 1][x] = getLetterFromId(block.getId());
                }
                case BLOCK_2X1 -> {
                    // ##
                    initialBoard[y][x] = getLetterFromId(block.getId());
                    initialBoard[y][x + 1] = getLetterFromId(block.getId());
                }
                case BLOCK_2X2 -> {
                    // ##
                    // ##
                    initialBoard[y][x] = getLetterFromId(block.getId());
                    initialBoard[y + 1][x] = getLetterFromId(block.getId());
                    initialBoard[y][x + 1] = getLetterFromId(block.getId());
                    initialBoard[y + 1][x + 1] = getLetterFromId(block.getId());
                }
            }
        }
//        System.out.println("*** MATRIX ***");
        int cont = 0;
        for (char[] ints : initialBoard) {
            for (char anInt : ints) {
                System.out.print(anInt);
            }
            if(cont < initialBoard.length-1) System.out.print(",");
            cont++;
        }
//        System.out.println("***************");
    }



}

/*

* */