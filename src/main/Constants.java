package main;

public final class Constants {
	
	// SCREEN SETTINGS
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final int ORIGINAL_TILE_SIZE = 16; // 16X16 tile... don't mess with this number
	public static final int SCALE = 3; // don't mess with this number
	public static final int TILE_SIZE = ORIGINAL_TILE_SIZE*SCALE; // 48x48 tile
	public static final int MAX_SCREEN_COL = 16;
	public static final int MAX_SCREEN_ROW = 14;
	public static final int SCREEN_WIDTH = TILE_SIZE*MAX_SCREEN_COL; // 768 pixels
	public static final int SCREEN_HEIGHT = TILE_SIZE*MAX_SCREEN_ROW; // 672 pixels
	public static final int SKY_LEVEL = 3; // decides how far down on the screen the sky level is, this is where the fence and sky tiles are placed
								   		   // top of grassline sits on y = 144
	public static final int FPS = 60;
		
	
	// PLAYER
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final int PLAYER_SPAWN_X = 0;
	public static final int PLAYER_SPAWN_Y = TILE_SIZE * SKY_LEVEL;
	
	public static final int PLAYER_SPEED = 3;
	
	public static final int PLAYER_PICK_TIME_MIN = 300;
	public static final int PLAYER_PICK_TIME_MAX = 360;
	
	public static final int PLAYER_FLICKER_SPEED = 15;
	
	public static final int PLAYER_IDLE_STAND_LENGTH = 160;
	public static final int PLAYER_IDLE_BLINK_LENGTH = 20;
	
	public static final int PLAYER_WALK_FRAME_LENGTH = 15;
	
	public static final int PLAYER_KNOCKBACK_DISTANCE = 96;
	public static final int PLAYER_KNOCKBACK_SPEED = 15;
	public static final int PLAYER_KNOCKBACK_RECOVERY = 120;
	public static final double PLAYER_KNOCKBACK_INERTIA = 1.2;
	
	public static final int PLAYER_SWAT_COOLDOWN = 40;
	
	public static final int PLAYER_COLLISION_TRIMMING = 15; // allows the player and entities to touch a little bit before triggering collision
	
	
	// BEE
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final int BEE_PERP_LINE_LENGTH_MIN = 500;
	public static final int BEE_PERP_LINE_LENGTH_MAX = 1000;
	
	public static final double BEE_MIN_DISTANCE_FROM_TARGET = 10;
	
	public static final int BEE_SPEED_MIN = 2;
	public static final int BEE_SPEED_MAX = 2;
	
	public static final int BEE_KNOCKBACK_DISTANCE = 96;
	public static final int BEE_KNOCKBACK_SPEED = 15;
	
	public static final int BEE_ANIMATION_FRAME_LENGTH = 2;
	
	public static final int BEE_PICK_OFFSET_X = 18;
	public static final int BEE_PICK_OFFSET_Y = 5;
	
	public static final int BEE_PICK_TIME = 180;
	
	public static final double SINE_TAPER = 1;
	
	
	// BUTTERFLY
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final int BUTTERFLY_SPEED_MIN = 2;
	public static final int BUTTERFLY_SPEED_MAX = 2;
	
	public static final int BUTTERFLY_KNOCKBACK_DISTANCE = 96;
	public static final int BUTTERFLY_KNOCKBACK_SPEED = 15;
	
	public static final int BUTTERFLY_ANIMATION_FRAME_LENGTH = 12;
	
	public static final int BUTTERFLY_PICK_OFFSET = 12;
	
	public static final int BUTTERFLY_PICK_TIME = 180;
	
	
	// LADYBUG
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final int LADYBUG_SPAWN_MIN = 240;
	public static final int LADYBUG_SPAWN_MAX = 360;
	
	public static final int LADYBUG_SPEED_MIN = 2;
	public static final int LADYBUG_SPEED_MAX = 2;
	
	public static final int LADYBUG_KNOCKBACK_DISTANCE = 96;
	public static final int LADYBUG_KNOCKBACK_SPEED = 15;
	public static final double LADYBUG_KNOCKBACK_INERTIA = 1.2;
	
	public static final int LADYBUG_ANIMATION_FRAME_LENGTH = 8;
	
	
	// MOWER
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final int MOWER_SPAWN_MIN = 600;
	public static final int MOWER_SPAWN_MAX = 900;
	
	public static final int MOWER_SPEED_MIN = 2;
	public static final int MOWER_SPEED_MAX = 2;
	
	public static final int MOWER_TURNS_MIN = 1;
	public static final int MOWER_TURNS_MAX = 10;
	
	public static final int MOWER_ANIMATION_SHAKE_INTERVAL = 4;
	public static final int MOWER_ANIMATION_FRAME_LENGTH = 8;
	
	public static final int MOWER_FLEE_DISTANCE = 5;
	
	
	// TILEMANAGER
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final float SELTILE_TRANSPARENCY = 0.2f; // transparency set to 20%
	public static final int SELTILE_ANIMATION_FRAME_LENGTH = 20;
	
	public static final int TILE_PLANT_SPROUT_MIN = 120;
	public static final int TILE_PLANT_SPROUT_MAX = 210;
	
	public static final int TILE_CHANGE_FROM_SPROUT_1_MIN = 120;
	public static final int TILE_CHANGE_FROM_SPROUT_1_MAX = 210;
	public static final int TILE_CHANGE_FROM_SPROUT_2_MIN = 180;
	public static final int TILE_CHANGE_FROM_SPROUT_2_MAX = 240;
	
	public static final int TILE_CHANGE_FROM_FLOWER_1_MIN = 180;
	public static final int TILE_CHANGE_FROM_FLOWER_1_MAX = 240;
	public static final int TILE_CHANGE_FROM_FLOWER_2_MIN = 180;
	public static final int TILE_CHANGE_FROM_FLOWER_2_MAX = 240;
	
	public static final int TILE_CHANGE_FROM_ROSE_1_MIN = 180;
	public static final int TILE_CHANGE_FROM_ROSE_1_MAX = 240;
	public static final int TILE_CHANGE_FROM_ROSE_2_MIN = 180;
	public static final int TILE_CHANGE_FROM_ROSE_2_MAX = 240;
	
	public static final int TILE_CHANGE_FROM_PICKED_MIN = 180;
	public static final int TILE_CHANGE_FROM_PICKED_MAX = 240;
	
	public static final int TILE_CHANGE_FROM_MOWED_1_MIN = 180;
	public static final int TILE_CHANGE_FROM_MOWED_1_MAX = 240;
	public static final int TILE_CHANGE_FROM_MOWED_2_MIN = 180;
	public static final int TILE_CHANGE_FROM_MOWED_2_MAX = 240;
	public static final int TILE_CHANGE_FROM_MOWED_3_MIN = 180;
	public static final int TILE_CHANGE_FROM_MOWED_3_MAX = 240;
	
	public static final int TILE_ENTITY_STAMP_MIN = 180;
	public static final int TILE_ENTITY_STAMP_MAX = 240;
	
	
	// IMAGES
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final float IMAGE_HIT_BRIGHTNESS = 20f;
	
}





