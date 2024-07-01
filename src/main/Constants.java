package main;

public final class Constants {
	
	// SCREEN SETTINGS
	public static final int ORIGINAL_TILE_SIZE = 16; // 16X16 tile... don't mess with this number
	public static final int SCALE = 3; // don't mess with this number
	public static final int TILE_SIZE = ORIGINAL_TILE_SIZE*SCALE; // 48x48 tile
	public static final int MAX_SCREEN_COL = 16;
	public static final int MAX_SCREEN_ROW = 14;
	public static final int SCREEN_WIDTH = TILE_SIZE*MAX_SCREEN_COL; // 768 pixels
	public static final int SCREEN_HEIGHT = TILE_SIZE*MAX_SCREEN_COL; // 672 pixels
	public static final int SKY_LEVEL = 3; // decides how far down on the screen the sky level is, this is where the fence and sky tiles are placed
								   		   // top of grassline sits on y = 144
	public static final int FPS = 60;
		
	
	
	
}





