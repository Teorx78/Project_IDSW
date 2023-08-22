package support;

import piece.BlockGFX;
import piece.BlockType;
import java.awt.Color;

public class Settings {
	/* ************** VARIABILI DI STATO ************** */
	public static int activeID = -1;
	public static BlockGFX activeBlock = null;

	/* ************** FILE ************** */
	public static final String CURSOR_IMAGE = "file:resources/img/cursor.png";
	public static final String BACKGROUND_IMAGE_PATH = "file:resources/img/sfondo.gif";
	public static final String CSS_BUTTON_FILE = "file:resources/style/button.css";
	public static final String CSS_LABEL_FILE = "file:resources/style/label.css";
	public static final String JSON_PATH = "resources/json/configuration.json";				//"file:" non è necessario
	public static final String JSON_SOLUTION_PATH = "resources/json/solutions.json";		//"file:" non è necessario
	public static final String JSON_SAVES_PATH = "resources/json/saves.json";				//"file:" non è necessario

	/* ************** IMPOSTAZIONI GENERALI ************** */
	public static final int LABEL_SIZE = 22;
	public static final Color LABEL_COLOR = Color.BLACK;
	public static final double LOWER_HEIGHT_LINE = (double) (Settings.WINDOW_HEIGHT * 85) / 100;
	public static final double HIGHER_HEIGHT_LINE = (double) (Settings.WINDOW_HEIGHT * 5) / 100;

	/* ************** IMPOSTAZIONI DEL GIOCO ************** */
	//CAMPO -> dimensioni del campo sono 200x250 ci stanno 4 pedine 1x1 in orizzontale e 5 in verticale
	public static final int WINDOW_WIDTH = 600, WINDOW_HEIGHT = 750;
//	public static final int MIN_VERTICAL_BOUNDS = 100, MIN_HORIZONTAL_BOUNDS = 100;
	public static final int MIN_BOUNDS = 100;
	//PEZZI
//	public static final int MIN_SIDE_DIMENSION = (WINDOW_WIDTH - MIN_HORIZONTAL_BOUNDS * 2) / 4;
	public static final int MIN_SIDE_DIMENSION = (WINDOW_WIDTH - MIN_BOUNDS* 2) / 4;

	/* METODI DELLE IMPSTAZIONI DEL GIOCO */
	public String getBlockSizeString(BlockType blockType){
		switch (blockType){
			case BLOCK_1X1 -> {
				return "1x1";
			}
			case BLOCK_1X2 -> {
				return "1x2";
			}
			case BLOCK_2X1 -> {
				return "2x1";
			}
			case BLOCK_2X2 -> {
				return "2x2";
			}
		}
		return null;
	}

	public String getTexturePath(int id){
		String TEXTURE_PATH = "file:resources/texture/block_texture";
		switch (id) {
			case 0 -> {
				return TEXTURE_PATH + "_1x1.png";
			}
			case 1 -> {
				return TEXTURE_PATH + "_1x2.png";
			}
			case 2 -> {
				return TEXTURE_PATH + "_2x1.png";
			}
			case 3 -> {
				return TEXTURE_PATH + "_2x2.png";
			}
		}
		return null;
	}
}
