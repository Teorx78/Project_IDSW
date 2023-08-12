package piece;

import support.Settings;

public class BlockPrototype {
    public int width;
    public int height;
    public BlockType blockType;
    public String texture;
    public BlockPrototype(BlockType blockType){
           this.blockType = blockType;
           switch (blockType){
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
