package game;


import piece.BlockGFX;
import support.Settings;

import java.util.ArrayList;

public class NextBestMove {
    private final int[][] initialBoard;          //0 (zero) equivale agli spazi vuoti
    private final ArrayList<BlockGFX> initialGrid;
    NextBestMove(ArrayList<BlockGFX> initialGrid){
        this.initialGrid = initialGrid;
        initialBoard = new int[5][4];
        for (BlockGFX block : initialGrid){
            int x = (block.getTopLeft().getX() / Settings.MIN_HORIZONTAL_BOUNDS) - 1;
            int y = (block.getTopLeft().getY() / Settings.MIN_VERTICAL_BOUNDS) - 1;
//            System.out.println("[" + x + ", " + y + "]");
            switch (block.getPrototype().blockType){
                case BLOCK_1X1 -> {
                    // #
                    initialBoard[y][x] = block.getId();
                }
                case BLOCK_1X2 -> {
                    // #
                    // #
                    initialBoard[y][x] = block.getId();
                    initialBoard[y + 1][x] = block.getId();
                }
                case BLOCK_2X1 -> {
                    // ##
                    initialBoard[y][x] = block.getId();
                    initialBoard[y][x + 1] = block.getId();
                }
                case BLOCK_2X2 -> {
                    // ##
                    // ##
                    initialBoard[y][x] = block.getId();
                    initialBoard[y + 1][x] = block.getId();
                    initialBoard[y][x + 1] = block.getId();
                    initialBoard[y + 1][x + 1] = block.getId();
                }
            }
        }
        //System.out.println("*** MATRIX ***");
        int cont = 0;
        for (int[] ints : initialBoard) {
            for (int anInt : ints) {
                System.out.print(anInt);
            }
            if(cont < initialBoard.length-1) System.out.print(",");
            cont++;
        }
        //System.out.println("***************");
    }


}
