package piece;

import support.MovementDirections;
import support.Settings;
import support.Vector2;

import java.util.ArrayList;
import java.util.Objects;

/**
 * La classe `Block` rappresenta un blocco nel gioco Klotski.
 * Questa classe gestisce la logica dei blocchi, inclusi il movimento, la selezione e il controllo delle collisioni.
 */
public class Block {
    /**
     * Il prototipo di blocco associato a questo blocco.
     */
    protected BlockPrototype prototype;

    /**
     * True se il blocco è selezionato, false altrimenti.
     */
    protected boolean isSelected = false;

    /**
     * La coordinata x dell'angolo in alto a sinistra del blocco.
     */
    protected int xTopLeft;

    /**
     * La coordinata y dell'angolo in alto a sinistra del blocco.
     */
    protected int yTopLeft;

    /**
     * L'ID univoco del blocco.
     */
    protected int id;

    /**
     * Costruttore della classe `Block`. Crea un blocco con le caratteristiche specificate.
     *
     * @param blockPrototype Il prototipo di blocco associato a questo blocco.
     * @param xTopLeft       La coordinata x dell'angolo in alto a sinistra del blocco.
     * @param yTopLeft       La coordinata y dell'angolo in alto a sinistra del blocco.
     * @param id             L'ID univoco del blocco.
     */
    Block(BlockPrototype blockPrototype, int xTopLeft, int yTopLeft, int id) {
        this.prototype = blockPrototype;
        this.xTopLeft = xTopLeft;
        this.yTopLeft = yTopLeft;
        this.id = id;
    }

    /**
     * Costruttore di copia della classe `Block`. Crea un nuovo blocco a partire da un blocco esistente e degli offset.
     *
     * @param block   Il blocco esistente da cui creare il nuovo blocco.
     * @param deltaX  L'offset orizzontale.
     * @param deltaY  L'offset verticale.
     */
    Block(Block block, int deltaX, int deltaY) {
        this.prototype = block.prototype;
        this.xTopLeft = block.xTopLeft + deltaX;
        this.yTopLeft = block.yTopLeft + deltaY;
    }

    /**
     * Imposta la coordinata in alto a sinistra del blocco.
     *
     * @param coords Le nuove coordinate in alto a sinistra del blocco.
     */
    public void setTopLeft(Vector2 coords) {
        this.xTopLeft = coords.getX();
        this.yTopLeft = coords.getY();
    }

    /**
     * Restituisce le coordinate in alto a sinistra del blocco.
     *
     * @return Le coordinate in alto a sinistra del blocco.
     */
    public Vector2 getTopLeft() {
        return new Vector2(xTopLeft, yTopLeft);
    }

    /**
     * Restituisce le coordinate in alto a destra del blocco.
     *
     * @return Le coordinate in alto a destra del blocco.
     */
    public Vector2 getTopRight() {
        return new Vector2(xTopLeft + prototype.width, yTopLeft);
    }

    /**
     * Restituisce le coordinate in basso a sinistra del blocco.
     *
     * @return Le coordinate in basso a sinistra del blocco.
     */
    public Vector2 getBottomLeft() {
        return new Vector2(xTopLeft, yTopLeft + prototype.height);
    }

    /**
     * Restituisce le coordinate in basso a destra del blocco.
     *
     * @return Le coordinate in basso a destra del blocco.
     */
    public Vector2 getBottomRight() {
        return new Vector2(xTopLeft + prototype.width, yTopLeft + prototype.height);
    }

    /**
     * Restituisce lo stato di selezione del blocco.
     *
     * @return True se il blocco è selezionato, false altrimenti.
     */
    public Boolean getSelected() {
        return isSelected;
    }

    /**
     * Restituisce l'ID del blocco.
     *
     * @return L'ID del blocco.
     */
    public int getId() {
        return id;
    }

    /**
     * Restituisce il prototipo di blocco associato a questo blocco.
     *
     * @return Il prototipo di blocco.
     */
    public BlockPrototype getPrototype() {
        return prototype;
    }

    /**
     * Cambia lo stato di selezione del blocco.
     */
    public void changeSelected() {
        isSelected = !isSelected;
    }

    /**
     * Restituisce una stringa formattata con le coordinate del blocco per la scrittura nel file JSON.
     *
     * @return Una stringa con le coordinate del blocco.
     */
    public String getSaveString() {
        return (xTopLeft / Settings.MIN_BOUNDS + "," + yTopLeft / Settings.MIN_BOUNDS);
    }

    /**
     * Controlla se il blocco può muoversi nella direzione specificata senza collisioni.
     *
     * @param movementDirection La direzione del movimento.
     * @param blocks            La lista dei blocchi presenti sul campo di gioco.
     * @return True se il blocco può muoversi senza collisioni, false altrimenti.
     */
    public boolean checkMovement(MovementDirections movementDirection, ArrayList<BlockGFX> blocks) {
        int prototypeWidth = this.prototype.blockType.equals(BlockType.BLOCK_1X1)
                || this.prototype.blockType.equals(BlockType.BLOCK_1X2)
                ? this.prototype.width
                : this.prototype.width / 2;

        int prototypeHeight = this.prototype.blockType.equals(BlockType.BLOCK_1X1)
                || this.prototype.blockType.equals(BlockType.BLOCK_2X1)
                ? this.prototype.height
                : this.prototype.height / 2;

        switch (movementDirection) {
            case UP -> {
                return (checkOverlap(blocks, new Block(this, 0, -prototypeHeight))
                        && checkBounds(new Block(this, 0, -prototypeHeight))
                        && checkRanges(blocks, new Block(this, 0, -prototypeHeight)));
            }
            case DOWN -> {
                return (checkOverlap(blocks, new Block(this, 0, prototypeHeight))
                        && checkBounds(new Block(this, 0, prototypeHeight))
                        && checkRanges(blocks, new Block(this, 0, prototypeHeight)));
            }
            case RIGHT -> {
                return (checkOverlap(blocks, new Block(this, prototypeWidth, 0))
                        && checkBounds(new Block(this, prototypeWidth, 0))
                        && checkRanges(blocks, new Block(this, prototypeWidth, 0)));
            }
            case LEFT -> {
                return (checkOverlap(blocks, new Block(this, -prototypeWidth, 0))
                        && checkBounds(new Block(this, -prototypeWidth, 0))
                        && checkRanges(blocks, new Block(this, -prototypeWidth, 0)));
            }
        }
        return false;
    }

    /**
     * Controlla se il blocco è sovrapposto a un altro blocco.
     *
     * @param deltaBlock Il blocco con cui verificare la sovrapposizione.
     * @return True se il blocco è sovrapposto a `deltaBlock`, false altrimenti.
     */
    public boolean isOverlapping(Block deltaBlock) {
        if (deltaBlock.getTopLeft().isEqual(this.getTopLeft())) return true;
        if (deltaBlock.getTopRight().isEqual(this.getTopRight())) return true;
        if (deltaBlock.getBottomLeft().isEqual(this.getBottomLeft())) return true;
        return deltaBlock.getBottomRight().isEqual(this.getBottomRight());
    }

    /**
     * Controlla se il blocco può muoversi all'interno dei limiti del campo.
     *
     * @param deltaBlock Il blocco con cui verificare i limiti.
     * @return True se il blocco è all'interno dei limiti del campo, false altrimenti.
     */
    private boolean checkBounds(Block deltaBlock) {
        if (deltaBlock.getTopLeft().getX() < Settings.MIN_BOUNDS) return false; // SINISTRA
        if (deltaBlock.getBottomRight().getX() > (Settings.WINDOW_WIDTH - Settings.MIN_BOUNDS))
            return false; // DESTRA
        if (deltaBlock.getTopLeft().getY() < Settings.MIN_BOUNDS) return false; // SOPRA
        return deltaBlock.getBottomRight().getY() < (Settings.WINDOW_HEIGHT - Settings.MIN_BOUNDS); // SOTTO
    }

    /**
     * Controlla se il blocco si sovrappone a un altro blocco dopo il movimento.
     *
     * @param blocks     La lista dei blocchi presenti sul campo di gioco.
     * @param deltaBlock Il blocco con cui verificare la sovrapposizione.
     * @return True se il blocco non si sovrappone a nessun altro blocco dopo il movimento, false altrimenti.
     */
    private boolean checkOverlap(ArrayList<BlockGFX> blocks, Block deltaBlock) {
        for (BlockGFX block : blocks) {
            if (block.getId() != this.id) {
                if (block.isOverlapping(deltaBlock)) return false;
            }
        }
        return true;
    }

    /**
     * Controlla se gli intervalli di movimento del blocco si sovrappongono con altri blocchi.
     *
     * @param blocks     La lista dei blocchi presenti sul campo di gioco.
     * @param deltaBlock Il blocco con cui verificare le sovrapposizioni.
     * @return True se gli intervalli di movimento del blocco non si sovrappongono con gli altri blocchi, false altrimenti.
     */
    private boolean checkRanges(ArrayList<BlockGFX> blocks, Block deltaBlock) {
        Vector2 xDeltaRange = new Vector2(deltaBlock.xTopLeft, deltaBlock.getBottomRight().getX());
        Vector2 yDeltaRange = new Vector2(deltaBlock.yTopLeft, deltaBlock.getBottomRight().getY());

        for (BlockGFX block : blocks) {
            if (block.getId() != this.id) {
                Vector2 xRange = new Vector2(block.getTopLeft().getX(), block.getBottomRight().getX());
                Vector2 yRange = new Vector2(block.getTopLeft().getY(), block.getBottomRight().getY());
                if ((xDeltaRange.isBetween(xRange) || xDeltaRange.isEqual(xRange)) && (yDeltaRange.isBetween(yRange) || yDeltaRange.isEqual(yRange)))
                    return false;
            }
        }
        return true;
    }

    /**
     * Restituisce una rappresentazione in formato stringa del blocco.
     *
     * @return Una stringa che rappresenta il blocco.
     */
    @Override
    public String toString() {
        return "Block{ " +
                "id = " + id +
                ", prototype = " + prototype +
                ", isSelected = " + isSelected +
                ", xTopLeft = " + xTopLeft +
                ", yTopLeft = " + yTopLeft +
                ", xBottomRight = " + this.getBottomRight().getX() +
                ", yBottomRight = " + this.getBottomRight().getY() +
                " }";
    }

    /**
     * Verifica se questo oggetto è uguale a un altro oggetto.
     *
     * @param o L'oggetto da confrontare con questo blocco.
     * @return True se i due oggetti sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return isSelected == block.isSelected &&
                xTopLeft == block.xTopLeft &&
                yTopLeft == block.yTopLeft &&
                id == block.id &&
                Objects.equals(prototype, block.prototype);
    }

    /**
     * Calcola un valore hash per questo blocco.
     *
     * @return Il valore hash del blocco.
     */
    @Override
    public int hashCode() {
        return Objects.hash(prototype, isSelected, xTopLeft, yTopLeft, id);
    }
}
