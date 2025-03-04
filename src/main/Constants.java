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
	public static final int PLAYER_SPAWN_X = TILE_SIZE * MAX_SCREEN_COL / 2;
	public static final int PLAYER_SPAWN_Y = TILE_SIZE * ((MAX_SCREEN_ROW + SKY_LEVEL) / 2);
	
	public static final int PLAYER_SPEED = 3;
	
	public static final int PLAYER_PICK_TIME_MIN = 200;
	public static final int PLAYER_PICK_TIME_MAX = 260;
	
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
	
	public static final int PROJECTILE_LENGTH = 180;
	
	
	// BEE
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final int BEE_PERP_LINE_LENGTH_MIN = 500;
	public static final int BEE_PERP_LINE_LENGTH_MAX = 1000;
	
	public static final double BEE_MIN_DISTANCE_FROM_TARGET = 10;
	
	public static final int BEE_SPEED_MIN = 2;
	public static final int BEE_SPEED_MAX = 3;
	
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
	public static final int BUTTERFLY_SPEED_MAX = 4;
	
	public static final int BUTTERFLY_KNOCKBACK_DISTANCE = 96;
	public static final int BUTTERFLY_KNOCKBACK_SPEED = 15;
	
	public static final int BUTTERFLY_ANIMATION_FRAME_LENGTH = 12;
	
	public static final int BUTTERFLY_PICK_OFFSET = 12;
	
	public static final int BUTTERFLY_PICK_TIME = 180;
	
	
	// LADYBUG
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final int LADYBUG_SPAWN_MIN = 300;
	public static final int LADYBUG_SPAWN_MAX = 600;
	
	public static final int LADYBUG_SPEED_MIN = 2;
	public static final int LADYBUG_SPEED_MAX = 2;
	
	public static final int LADYBUG_KNOCKBACK_DISTANCE = 96;
	public static final int LADYBUG_KNOCKBACK_SPEED = 15;
	public static final double LADYBUG_KNOCKBACK_INERTIA = 1.2;
	
	public static final int LADYBUG_ANIMATION_FRAME_LENGTH = 8;
	
	
	// MOWER
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final int MOWER_SPAWN_MIN = 1200;
	public static final int MOWER_SPAWN_MAX = 3600;
	
	public static final int MOWER_SPEED_MIN = 1;
	public static final int MOWER_SPEED_MAX = 1;
	
	public static final int MOWER_TURNS_MIN = 1;
	public static final int MOWER_TURNS_MAX = 10;
	
	public static final int MOWER_ANIMATION_SHAKE_INTERVAL = 4;
	public static final int MOWER_ANIMATION_FRAME_LENGTH = 8;
	
	public static final int MOWER_FLEE_DISTANCE = 5;
	
	
	// BIRT
	//------------------------------------------------------------------------------------------------------------------------------------------------------
		public static final int BIRT_SPAWN_MIN = 240;
		
		public static final int BIRT_SPEED_MIN = 4;
		public static final int BIRT_SPEED_MAX = 6;
		
		public static final int BIRT_KNOCKBACK_DISTANCE = 150;
		public static final int BIRT_KNOCKBACK_SPEED = 30;
		public static final double BIRT_KNOCKBACK_INERTIA = 1.2;
		
		public static final int BIRT_ANIMATION_FRAME_LENGTH = 2;
	
		
	// TILEMANAGER
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final float SELTILE_TRANSPARENCY = 0.2f; // transparency set to 20%
	public static final int SELTILE_ANIMATION_FRAME_LENGTH = 20;
	
	public static final int TILE_PLANT_SPROUT_MIN = 20;
	public static final int TILE_PLANT_SPROUT_MAX = 100;
	
	public static final int TILE_CHANGE_FROM_SPROUT_1_MIN = 90;
	public static final int TILE_CHANGE_FROM_SPROUT_1_MAX = 180;
	public static final int TILE_CHANGE_FROM_SPROUT_2_MIN = 120;
	public static final int TILE_CHANGE_FROM_SPROUT_2_MAX = 210;
	
	public static final int TILE_CHANGE_FROM_FLOWER_1_MIN = 120;
	public static final int TILE_CHANGE_FROM_FLOWER_1_MAX = 240;
	public static final int TILE_CHANGE_FROM_FLOWER_2_MIN = 90;
	public static final int TILE_CHANGE_FROM_FLOWER_2_MAX = 240;
	
	public static final int TILE_CHANGE_FROM_ROSE_1_MIN = 90;
	public static final int TILE_CHANGE_FROM_ROSE_1_MAX = 240;
	public static final int TILE_CHANGE_FROM_ROSE_2_MIN = 90;
	public static final int TILE_CHANGE_FROM_ROSE_2_MAX = 240;
	
	public static final int TILE_CHANGE_FROM_PICKED_MIN = 90;
	public static final int TILE_CHANGE_FROM_PICKED_MAX = 240;
	
	public static final int TILE_CHANGE_FROM_MOWED_1_MIN = 90;
	public static final int TILE_CHANGE_FROM_MOWED_1_MAX = 240;
	public static final int TILE_CHANGE_FROM_MOWED_2_MIN = 90;
	public static final int TILE_CHANGE_FROM_MOWED_2_MAX = 240;
	public static final int TILE_CHANGE_FROM_MOWED_3_MIN = 90;
	public static final int TILE_CHANGE_FROM_MOWED_3_MAX = 240;
	
	public static final int TILE_ENTITY_STAMP_MIN = 180;
	public static final int TILE_ENTITY_STAMP_MAX = 2000;
	
	
	// IMAGES
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final float IMAGE_HIT_BRIGHTNESS = 20f;
	
	
	// SCENES
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final int CREDITS_INT = 420; // 7 seconds
	
	public static final int OPENING_INT = 1400;
	
	public static final int TITLE_INT_1 = 180; // 3 seconds
	public static final int TITLE_INT_2 = 300; // 5 seconds
	
	
	public static final int BEDROOM_INT_1_1 = 300;
	public static final int BEDROOM_INT_1_2 = BEDROOM_INT_1_1 + 400;
	public static final int BEDROOM_INT_1_3 = BEDROOM_INT_1_2 + 400;
	
	public static final int BEDROOM_INT_2_1 = BEDROOM_INT_1_3 + 300;
	public static final int BEDROOM_INT_2_2 = BEDROOM_INT_2_1 + 300;
	public static final int BEDROOM_INT_2_3 = BEDROOM_INT_2_2 + 300;
	public static final int BEDROOM_INT_2_4 = BEDROOM_INT_2_3 + 50;
	
	public static final int BEDROOM_INT_3_1 = BEDROOM_INT_2_4 + 200;
	public static final int BEDROOM_INT_3_2 = BEDROOM_INT_3_1 + 200;
	public static final int BEDROOM_INT_3_3 = BEDROOM_INT_3_2 + 300;
	public static final int BEDROOM_INT_3_4 = BEDROOM_INT_3_3 + 200;
	
	
	public static final int TUTORIAL_INT_1 = 290;
	public static final int TUTORIAL_INT_2 = TUTORIAL_INT_1 + 50;
	public static final int TUTORIAL_INT_3 = TUTORIAL_INT_2 + 150;
	
	public static final int COUNTDOWN_INT_1 = 40;
	public static final int COUNTDOWN_INT_2 = COUNTDOWN_INT_1 + 45;
	public static final int COUNTDOWN_INT_3 = COUNTDOWN_INT_2 + 45;
	
	public static final int PREGAME_TIMER = TUTORIAL_INT_3 + COUNTDOWN_INT_3;
	
	public static final int SUPPER_TIME_INT_1 = 60;
	public static final int SUPPER_TIME_INT_2 = SUPPER_TIME_INT_1 + 60;
	public static final int SUPPER_TIME_INT_3 = SUPPER_TIME_INT_2 + 120;
	public static final int SUPPER_TIME_INT_4 = SUPPER_TIME_INT_3 + 60;
	
	public static final int POSTGAME_TIMER = SUPPER_TIME_INT_4;
	
	public static final int GAME_TIME = 1800 * 5; // 30 seconds * 5 = 2:30 
	public static final int TOTAL_GAME_TIME = GAME_TIME + PREGAME_TIMER + POSTGAME_TIMER;
	
	public static final int EATING_INT_1 = 200;
	public static final int EATING_INT_2 = EATING_INT_1 + 400;
	public static final int EATING_INT_3 = EATING_INT_2 + 200;
	public static final int EATING_INT_4 = EATING_INT_3 + 200;
	public static final int EATING_INT_5 = EATING_INT_4 + 200;
	
	public static final int GIVING_INT = 300;
	
	// SKIP BUTTON
	//------------------------------------------------------------------------------------------------------------------------------------------------------
	public static final int SKIP_B_WIDTH = 28 * 2;
	public static final int SKIP_B_HEIGHT = 6 * 2;
	public static final int SKIP_B_X = Constants.SCREEN_WIDTH - 70;
	public static final int SKIP_B_Y = Constants.SCREEN_HEIGHT - 30;
	
}





