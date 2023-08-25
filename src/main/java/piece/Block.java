package piece;

import support.MovementDirections;
import support.Settings;
import support.Vector2;

import java.util.ArrayList;

public class Block {
    protected BlockPrototype prototype;
    protected boolean isSelected = false;
    protected int xTopLeft;
    protected int yTopLeft;
    protected int id;

    Block(BlockPrototype blockPrototype, int xTopLeft, int yTopLeft, int id){       //costruttore della classe Block: setta il blocco da zero
        this.prototype = blockPrototype;
        this.xTopLeft = xTopLeft;
        this.yTopLeft = yTopLeft;
        this.id = id;
    }
    Block(Block block, int deltaX, int deltaY){         ////costruttore della classe Block: setta il blocco partendo da un blocco e da degli offest
        this.prototype = block.prototype;
        this.xTopLeft = block.xTopLeft + deltaX;
        this.yTopLeft = block.yTopLeft + deltaY;
    }
    /* SETTERS */
    public void setTopLeft(Vector2 coords){     //setta l'angolo in alto a sinistra
        this.xTopLeft = coords.getX();
        this.yTopLeft = coords.getY();
    }
    /* GETTERS */
    public Vector2 getTopLeft(){ return new Vector2(xTopLeft, yTopLeft); }      //return dell'angolo in alto a sinistra
    public Vector2 getTopRight(){ return new Vector2(xTopLeft + prototype.width, yTopLeft); }       //return dell'angolo in alto a destra
    public Vector2 getBottomLeft(){ return new Vector2(xTopLeft, yTopLeft + prototype.height); }        //return dell'angolo in basso a sinistra
    public Vector2 getBottomRight(){ return new Vector2(xTopLeft + prototype.width, yTopLeft + prototype.height); }     //return dell'angolo in basso a destra
    public Boolean getSelected(){ return isSelected; }      //ritorn della variabile isSelected
    public int getId(){ return id; }        //return dell'id del blocco
    public BlockPrototype getPrototype() { return prototype; }  //return del prototipo del blocco
    /* METODI */
    public void changeSelected(){ isSelected = !isSelected; }       //cambio di isSelected
    public String getSaveString(){  return (xTopLeft / Settings.MIN_BOUNDS + "," + yTopLeft / Settings.MIN_BOUNDS); }       //ritorn della stringa che contiene le coodinate per la scrittura nel json
    public boolean checkMovement(MovementDirections movementDirection, ArrayList<BlockGFX> blocks){     //funzione per il controllo del movimento
        int prototypeWidth = this.prototype.blockType.equals(BlockType.BLOCK_1X1)
                || this.prototype.blockType.equals(BlockType.BLOCK_1X2)
                ? this.prototype.width
                : this.prototype.width / 2;

        int prototypeHeight = this.prototype.blockType.equals(BlockType.BLOCK_1X1)
                || this.prototype.blockType.equals(BlockType.BLOCK_2X1)
                ? this.prototype.height
                : this.prototype.height / 2;

        switch (movementDirection){
            case UP -> {
                //si muove lungo le y | negativo perche le y sono rivolte verso il basso
                return (checkOverlap(blocks, new Block(this, 0, -prototypeHeight))
                        && checkBounds(new Block(this, 0, -prototypeHeight))
                        && checkRanges(blocks, new Block(this, 0, -prototypeHeight)));
            }
            case DOWN -> {
                //si muove lungo le y | positivo perche le y sono rivolte verso il basso
                return (checkOverlap(blocks, new Block(this, 0, prototypeHeight))
                        && checkBounds(new Block(this, 0, prototypeHeight))
                        && checkRanges(blocks, new Block(this, 0, prototypeHeight)));
            }
            case RIGHT -> {
                //si muove lungo le x | positivo perche le x sono rivolte verso destra
                return (checkOverlap(blocks, new Block(this, prototypeWidth, 0))
                        && checkBounds(new Block(this, prototypeWidth, 0))
                        && checkRanges(blocks, new Block(this, prototypeWidth, 0)));
            }
            case LEFT -> {
                //si muove lungo le x | negativo perche le x sono rivolte verso destra
                return (checkOverlap(blocks, new Block(this, -prototypeWidth, 0))
                        && checkBounds(new Block(this, -prototypeWidth, 0))
                        && checkRanges(blocks, new Block(this, -prototypeWidth, 0)));
            }
        }
        return false;
    }
    private boolean checkOverlap(ArrayList<BlockGFX> blocks, Block deltaBlock){     //funzione di controllo di sovrapposizione dei blocchi post movimento
        for (BlockGFX block : blocks) {
            if (block.getId() != this.id) {
                if (block.isOverlapping(deltaBlock)) return false;
            }
        }
        return true;
    }
    public boolean isOverlapping(Block deltaBlock){             //funzione di supporto per il controllo della sovrapposizione dei blocchi
        if(deltaBlock.getTopLeft().isEqual(this.getTopLeft())) return true;
        if(deltaBlock.getTopRight().isEqual(this.getTopRight())) return true;
        if(deltaBlock.getBottomLeft().isEqual(this.getBottomLeft())) return true;
        return deltaBlock.getBottomRight().isEqual(this.getBottomRight());
    }
    private boolean checkBounds(Block deltaBlock){      //funzione di controllo dei bordi del campo
        if(deltaBlock.getTopLeft().getX() < Settings.MIN_BOUNDS) return false;               //SINISTRA
        if(deltaBlock.getBottomRight().getX() > (Settings.WINDOW_WIDTH - Settings.MIN_BOUNDS)) return false;     //DESTRA
        if(deltaBlock.getTopLeft().getY() < Settings.MIN_BOUNDS) return false;                 //SOPRA
        return deltaBlock.getBottomRight().getY() < (Settings.WINDOW_HEIGHT - Settings.MIN_BOUNDS);        //SOTTO
    }
    private boolean checkRanges(ArrayList<BlockGFX> blocks, Block deltaBlock){      //funzione di controllo per evitare che gli intervalli post movimento si intersechino con gli altri blocchi
        Vector2 xDeltaRange = new Vector2(deltaBlock.xTopLeft, deltaBlock.getBottomRight().getX());
        Vector2 yDeltaRange = new Vector2(deltaBlock.yTopLeft, deltaBlock.getBottomRight().getY());

        for (BlockGFX block : blocks) {
            if (block.getId() != this.id) {
               Vector2 xRange = new Vector2(block.getTopLeft().getX(), block.getBottomRight().getX());
               Vector2 yRange = new Vector2(block.getTopLeft().getY(), block.getBottomRight().getY());
               if((xDeltaRange.isBetween(xRange) || xDeltaRange.isEqual(xRange)) && (yDeltaRange.isBetween(yRange) || yDeltaRange.isEqual(yRange))) return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        return "Block{ " +
                "id = "+ id +
                ", prototype = " + prototype +
                ", isSelected = " + isSelected +
                ", xTopLeft = " + xTopLeft +
                ", yTopLeft = " + yTopLeft +
                ", xBottomRight = " + this.getBottomRight().getX() +
                ", yBottomRight = " + this.getBottomRight().getY() +
                " }";
    }
}
