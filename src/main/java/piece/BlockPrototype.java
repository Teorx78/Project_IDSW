package piece;

import support.Settings;

/**
 * La classe `BlockPrototype` rappresenta un prototipo di blocco nel gioco Klotski.
 * Ogni prototipo di blocco ha un tipo, dimensioni e una texture associata.
 * Le dimensioni e la texture del prototipo di blocco vengono impostate in base al suo tipo durante la creazione.
 */
public class BlockPrototype {
    /**
     * Larghezza del prototipo di blocco in pixel.
     */
    public int width;

    /**
     * Altezza del prototipo di blocco in pixel.
     */
    public int height;

    /**
     * Tipo del prototipo di blocco.
     */
    public BlockType blockType;

    /**
     * Percorso della texture associata al prototipo di blocco.
     */
    public String texture;

    /**
     * Costruttore della classe `BlockPrototype`. Crea un prototipo di blocco con le dimensioni e la texture associate al suo tipo.
     *
     * @param blockType Il tipo del prototipo di blocco da creare.
     */
    public BlockPrototype(BlockType blockType) {
        this.blockType = blockType;
        switch (blockType) {
            case BLOCK_1X1 -> {
                width = Settings.MIN_SIDE_DIMENSION;
                height = Settings.MIN_SIDE_DIMENSION;
                texture = new Settings().getTexturePath(0);
            }
            case BLOCK_1X2 -> {
                width = Settings.MIN_SIDE_DIMENSION;
                height = 2 * Settings.MIN_SIDE_DIMENSION;
                texture = new Settings().getTexturePath(1);
            }
            case BLOCK_2X1 -> {
                width = 2 * Settings.MIN_SIDE_DIMENSION;
                height = Settings.MIN_SIDE_DIMENSION;
                texture = new Settings().getTexturePath(2);
            }
            case BLOCK_2X2 -> {
                width = 2 * Settings.MIN_SIDE_DIMENSION;
                height = 2 * Settings.MIN_SIDE_DIMENSION;
                texture = new Settings().getTexturePath(3);
            }
        }
    }

    /**
     * Restituisce una rappresentazione in forma di stringa del prototipo di blocco.
     *
     * @return Una stringa che rappresenta il prototipo di blocco con le sue dimensioni, tipo e texture.
     */
    @Override
    public String toString() {
        return "BlockPrototype: [" +
                "width=" + width +
                ", height=" + height +
                ", blockType=" + blockType +
                ", texture='" + texture + '\'' +
                ']';
    }
}
