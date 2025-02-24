package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

import javax.imageio.ImageIO;

import entity.Bee;
import entity.Butterfly;
import entity.Ladybug;
import entity.Mower;
import entity.Player;
import tile.TileManager;

public final class ImageManager {
	
	public static BufferedImage idle1, idle2, front1, front2, front3, back1, back2, back3, right1, right2, right3, 
	left1, left2, left3, bent1, bent2, bent3;
	
	static {
		try {
			
			idle1 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_idle_1.png"));
			idle2 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_idle_2.png"));
			
			front1 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_front_1.png"));
			front2 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_front_2.png"));
			front3 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_front_3.png"));
			
			back1 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_back_1.png"));
			back2 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_back_2.png"));
			back3 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_back_3.png"));
			
			right1 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_right_2.png"));
			right3 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_right_3.png"));
			
			left1 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_left_2.png"));
			left3 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_left_3.png"));
			
			bent1 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_bent_1.png"));
			bent2 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_bent_2.png"));
			bent3 = ImageIO.read(Player.class.getResourceAsStream("/player/boy_bent_3.png"));
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static BufferedImage bee_down_left_1, bee_down_left_2, bee_down_left_3, bee_down_leftwing_1, bee_down_leftwing_2, 
	bee_down_leftwing_3, bee_down_right_1, bee_down_right_2, bee_down_right_3, bee_down_rightwing_1, bee_down_rightwing_2, 
	bee_down_rightwing_3, bee_left_1, bee_left_2, bee_left_3, bee_right_1, bee_right_2, bee_right_3, bee_up_left_1, 
	bee_up_left_2, bee_up_left_3, bee_up_leftwing_1, bee_up_leftwing_2, bee_up_leftwing_3, bee_up_right_1, bee_up_right_2, 
	bee_up_right_3, bee_up_rightwing_1, bee_up_rightwing_2, bee_up_rightwing_3;
	
	static { // loads all images
		try {
			bee_down_left_1 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_down_left_1.png"));
			bee_down_left_2 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_down_left_2.png"));
			bee_down_left_3 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_down_left_3.png"));
			bee_down_leftwing_1 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_down_leftwing_1.png"));
			bee_down_leftwing_2 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_down_leftwing_2.png"));
			bee_down_leftwing_3 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_down_leftwing_3.png"));
			bee_down_right_1 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_down_right_1.png"));
			bee_down_right_2 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_down_right_2.png"));
			bee_down_right_3 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_down_right_3.png"));
			bee_down_rightwing_1 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_down_rightwing_1.png"));
			bee_down_rightwing_2 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_down_rightwing_2.png"));
			bee_down_rightwing_3 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_down_rightwing_3.png"));
			bee_left_1 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_left_1.png"));
			bee_left_2 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_left_2.png"));
			bee_left_3 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_left_3.png"));
			bee_right_1 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_right_1.png"));
			bee_right_2 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_right_2.png"));
			bee_right_3 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_right_3.png"));
			bee_up_left_1 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_up_left_1.png"));
			bee_up_left_2 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_up_left_2.png"));
			bee_up_left_3 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_up_left_3.png"));
			bee_up_leftwing_1 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_up_leftwing_1.png"));
			bee_up_leftwing_2 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_up_leftwing_2.png"));
			bee_up_leftwing_3 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_up_leftwing_3.png"));
			bee_up_right_1 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_up_right_1.png"));
			bee_up_right_2 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_up_right_2.png"));
			bee_up_right_3 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_up_right_3.png"));
			bee_up_rightwing_1 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_up_rightwing_1.png"));
			bee_up_rightwing_2 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_up_rightwing_2.png"));
			bee_up_rightwing_3 = ImageIO.read(Bee.class.getResourceAsStream("/bee/bee_up_rightwing_3.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage butterfly_down_1, butterfly_down_2, butterfly_down_3, butterfly_left_1, butterfly_left_2, butterfly_left_3, 
	butterfly_right_1, butterfly_right_2, butterfly_right_3, butterfly_up_1, butterfly_up_2, butterfly_up_3;
	
	static { // loads all images
		try {
			butterfly_down_1 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_down_1.png"));
			butterfly_down_2 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_down_2.png"));
			butterfly_down_3 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_down_3.png"));
			butterfly_left_1 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_left_1.png"));
			butterfly_left_2 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_left_2.png"));
			butterfly_left_3 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_left_3.png"));
			butterfly_right_1 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_right_1.png"));
			butterfly_right_2 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_right_2.png"));
			butterfly_right_3 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_right_3.png"));
			butterfly_up_1 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_up_1.png"));
			butterfly_up_2 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_up_2.png"));
			butterfly_up_3 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_up_3.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage ladybug_down_1, ladybug_down_2, ladybug_down_3, ladybug_down_left_1, ladybug_down_left_2, 
	ladybug_down_left_3, ladybug_down_right_1, ladybug_down_right_2, ladybug_down_right_3, ladybug_left_1, ladybug_left_2, 
	ladybug_left_3, ladybug_right_1, ladybug_right_2, ladybug_right_3, ladybug_up_1, ladybug_up_2, ladybug_up_3, 
	ladybug_up_left_1, ladybug_up_left_2, ladybug_up_left_3, ladybug_up_right_1, ladybug_up_right_2, ladybug_up_right_3;
	
	static { // loads all images
		try {
			ladybug_down_1 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_down_1.png"));
			ladybug_down_2 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_down_2.png"));
			ladybug_down_3 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_down_3.png"));
			ladybug_down_left_1 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_down_left_1.png"));
			ladybug_down_left_2 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_down_left_2.png"));
			ladybug_down_left_3 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_down_left_3.png"));
			ladybug_down_right_1 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_down_right_1.png"));
			ladybug_down_right_2 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_down_right_2.png"));
			ladybug_down_right_3 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_down_right_3.png"));
			ladybug_left_1 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_left_1.png"));
			ladybug_left_2 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_left_2.png"));
			ladybug_left_3 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_left_3.png"));
			ladybug_right_1 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_right_1.png"));
			ladybug_right_2 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_right_2.png"));
			ladybug_right_3 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_right_3.png"));
			ladybug_up_1 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_up_1.png"));
			ladybug_up_2 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_up_2.png"));
			ladybug_up_3 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_up_3.png"));
			ladybug_up_left_1 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_up_left_1.png"));
			ladybug_up_left_2 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_up_left_2.png"));
			ladybug_up_left_3 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_up_left_3.png"));
			ladybug_up_right_1 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_up_right_1.png"));
			ladybug_up_right_2 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_up_right_2.png"));
			ladybug_up_right_3 = ImageIO.read(Ladybug.class.getResourceAsStream("/ladybug/ladybug_up_right_3.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage mower_down_1, mower_down_2, mower_down_3, mower_left_1, mower_left_2, 
	mower_left_3, mower_right_1, mower_right_2, mower_right_3, mower_up_1, mower_up_2, mower_up_3;
	
	static { // loads all images
		try {
			mower_down_1 = ImageIO.read(Mower.class.getResourceAsStream("/mower/mower_down_1.png"));
			mower_down_2 = ImageIO.read(Mower.class.getResourceAsStream("/mower/mower_down_2.png"));
			mower_down_3 = ImageIO.read(Mower.class.getResourceAsStream("/mower/mower_down_3.png"));
			mower_left_1 = ImageIO.read(Mower.class.getResourceAsStream("/mower/mower_left_1.png"));
			mower_left_2 = ImageIO.read(Mower.class.getResourceAsStream("/mower/mower_left_2.png"));
			mower_left_3 = ImageIO.read(Mower.class.getResourceAsStream("/mower/mower_left_3.png"));
			mower_right_1 = ImageIO.read(Mower.class.getResourceAsStream("/mower/mower_right_1.png"));
			mower_right_2 = ImageIO.read(Mower.class.getResourceAsStream("/mower/mower_right_2.png"));
			mower_right_3 = ImageIO.read(Mower.class.getResourceAsStream("/mower/mower_right_3.png"));
			mower_up_1 = ImageIO.read(Mower.class.getResourceAsStream("/mower/mower_up_1.png"));
			mower_up_2 = ImageIO.read(Mower.class.getResourceAsStream("/mower/mower_up_2.png"));
			mower_up_3 = ImageIO.read(Mower.class.getResourceAsStream("/mower/mower_up_3.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	public static BufferedImage fence, flower_blue_1, flower_blue_2, flower_blue_3, flower_orange_1, flower_orange_2, flower_orange_3, 
	flower_picked, flower_rose_1, flower_rose_2, flower_rose_3, flower_sprout_1, flower_sprout_2, flower_white_1, flower_white_2, 
	flower_white_3, flower_yellow_1, flower_yellow_2, flower_yellow_3, grass_hori, grass_mowed_1, grass_mowed_2, grass_mowed_3, 
	grass_plain, grass_vert, sky, weed, select_border_1, select_border_2;
	
	static {
		try {
			
			fence = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/fence.png"));
			flower_blue_1 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_blue_1.png"));
			flower_blue_2 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_blue_2.png"));
			flower_blue_3 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_blue_3.png"));
			flower_orange_1 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_orange_1.png"));
			flower_orange_2 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_orange_2.png"));
			flower_orange_3 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_orange_3.png"));
			flower_picked = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_picked.png"));
			flower_rose_1 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_rose_1.png"));
			flower_rose_2 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_rose_2.png"));
			flower_rose_3 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_rose_3.png"));
			flower_sprout_1 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_sprout_1.png"));
			flower_sprout_2 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_sprout_2.png"));
			flower_white_1 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_white_1.png"));
			flower_white_2 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_white_2.png"));
			flower_white_3 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_white_3.png"));
			flower_yellow_1 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_yellow_1.png"));
			flower_yellow_2 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_yellow_2.png"));
			flower_yellow_3 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/flower_yellow_3.png"));
			grass_hori = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/grass_hori.png"));
			grass_mowed_1 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/grass_mowed_1.png"));
			grass_mowed_2 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/grass_mowed_2.png"));
			grass_mowed_3 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/grass_mowed_3.png"));
			grass_plain = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/grass_plain.png"));
			grass_vert = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/grass_vert.png")); // don't use, it looks terrible lol
			sky = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/sky.png"));
			weed = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/weed.png"));
			select_border_1 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/select_border_1.png"));
			select_border_2 = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/select_border_2.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	public static BufferedImage birt_1, birt_2, birt_3, birt_4, birt_5, birt_6, birt_7;
	
	static {
		try {
			birt_1 = ImageIO.read(TileManager.class.getResourceAsStream("/birt/birt_1.png"));
			birt_2 = ImageIO.read(TileManager.class.getResourceAsStream("/birt/birt_2.png"));
			birt_3 = ImageIO.read(TileManager.class.getResourceAsStream("/birt/birt_3.png"));
			birt_4 = ImageIO.read(TileManager.class.getResourceAsStream("/birt/birt_4.png"));
			birt_5 = ImageIO.read(TileManager.class.getResourceAsStream("/birt/birt_5.png"));
			birt_6 = ImageIO.read(TileManager.class.getResourceAsStream("/birt/birt_6.png"));
			birt_7 = ImageIO.read(TileManager.class.getResourceAsStream("/birt/birt_7.png"));
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static BufferedImage callout_image, not_easter_egg, play_button_flicker, play_button_neutral, play_button_push;
	
	static {
		try {
			callout_image = ImageIO.read(TileManager.class.getResourceAsStream("/other/callout_image.png"));
			not_easter_egg = ImageIO.read(TileManager.class.getResourceAsStream("/other/not_easter_egg.png"));
			play_button_flicker = ImageIO.read(TileManager.class.getResourceAsStream("/other/play_button_flicker.png"));
			play_button_neutral = ImageIO.read(TileManager.class.getResourceAsStream("/other/play_button_neutral.png"));
			play_button_push = ImageIO.read(TileManager.class.getResourceAsStream("/other/play_button_push.png"));
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage flower_projectile_1, flower_projectile_2, flower_projectile_3, flower_projectile_4;
	
	static {
		try {
			flower_projectile_1 = ImageIO.read(TileManager.class.getResourceAsStream("/projectile/flower_projectile_1.png"));
			flower_projectile_2 = ImageIO.read(TileManager.class.getResourceAsStream("/projectile/flower_projectile_2.png"));
			flower_projectile_3 = ImageIO.read(TileManager.class.getResourceAsStream("/projectile/flower_projectile_3.png"));
			flower_projectile_4 = ImageIO.read(TileManager.class.getResourceAsStream("/projectile/flower_projectile_4.png"));
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage bedroom_1, bedroom_2, bedroom_3, bedroom_4, bedroom_5, bedroom_6, calendar_1, calendar_2, credit_image_1, 
	credit_image_2, eating_1, eating_2, eating_3, shock, title_image, title_screen_ground, title_screen_sky, tutorial_screen_1, 
	tutorial_screen_2;
	
	static {
		try {
			bedroom_1 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/bedroom_1.png"));
			bedroom_2 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/bedroom_2.png"));
			bedroom_3 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/bedroom_3.png"));
			bedroom_4 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/bedroom_4.png"));
			bedroom_5 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/bedroom_5.png"));
			bedroom_6 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/bedroom_6.png"));
			calendar_1 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/calendar_1.png"));
			calendar_2 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/calendar_2.png"));
			credit_image_1 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/credit_image_1.png"));
			credit_image_2 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/credit_image_2.png"));
			eating_1 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/eating_1.png"));
			eating_2 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/eating_2.png"));
			eating_3 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/eating_3.png"));
			shock = ImageIO.read(TileManager.class.getResourceAsStream("/screens/shock.png"));
			title_image = ImageIO.read(TileManager.class.getResourceAsStream("/screens/title_image.png"));
			title_screen_ground = ImageIO.read(TileManager.class.getResourceAsStream("/screens/title_screen_ground.png"));
			title_screen_sky = ImageIO.read(TileManager.class.getResourceAsStream("/screens/title_screen_sky.png"));
			tutorial_screen_1 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/tutorial_screen_1.png"));
			tutorial_screen_2 = ImageIO.read(TileManager.class.getResourceAsStream("/screens/tutorial_screen_2.png"));
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage points_largeF, points_largeR, points_mediumF, points_mediumR, points_score, points_smallF, points_smallR, 
	points_weeds, time_number_0, time_number_1, time_number_2, time_number_3, time_number_4, time_number_5, time_number_6, time_number_7, 
	time_number_8, time_number_9, time_number_colon;
		
	static {
		try {
			points_largeF = ImageIO.read(TileManager.class.getResourceAsStream("/text/points_largeF.png"));
			points_largeR = ImageIO.read(TileManager.class.getResourceAsStream("/text/points_largeR.png"));
			points_mediumF = ImageIO.read(TileManager.class.getResourceAsStream("/text/points_mediumF.png"));
			points_mediumR = ImageIO.read(TileManager.class.getResourceAsStream("/text/points_mediumR.png"));
			points_score = ImageIO.read(TileManager.class.getResourceAsStream("/text/points_score.png"));
			points_smallF = ImageIO.read(TileManager.class.getResourceAsStream("/text/points_smallF.png"));
			points_smallR = ImageIO.read(TileManager.class.getResourceAsStream("/text/points_smallR.png"));
			points_weeds = ImageIO.read(TileManager.class.getResourceAsStream("/text/points_weeds.png"));
			time_number_0 = ImageIO.read(TileManager.class.getResourceAsStream("/text/time_number_0.png"));
			time_number_1 = ImageIO.read(TileManager.class.getResourceAsStream("/text/time_number_1.png"));
			time_number_2 = ImageIO.read(TileManager.class.getResourceAsStream("/text/time_number_2.png"));
			time_number_3 = ImageIO.read(TileManager.class.getResourceAsStream("/text/time_number_3.png"));
			time_number_4 = ImageIO.read(TileManager.class.getResourceAsStream("/text/time_number_4.png"));
			time_number_5 = ImageIO.read(TileManager.class.getResourceAsStream("/text/time_number_5.png"));
			time_number_6 = ImageIO.read(TileManager.class.getResourceAsStream("/text/time_number_6.png"));
			time_number_7 = ImageIO.read(TileManager.class.getResourceAsStream("/text/time_number_7.png"));
			time_number_8 = ImageIO.read(TileManager.class.getResourceAsStream("/text/time_number_8.png"));
			time_number_9 = ImageIO.read(TileManager.class.getResourceAsStream("/text/time_number_9.png"));
			time_number_colon = ImageIO.read(TileManager.class.getResourceAsStream("/text/time_number_colon.png"));
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ImageIcon kitchen1, kitchen2, kitchen3;

    static {
        kitchen1 = new ImageIcon("res/screens/kitchen1.gif");
        kitchen2 = new ImageIcon("res/screens/kitchen2.gif");
        kitchen3 = new ImageIcon("res/screens/kitchen3.gif");
    }
    
    public static BufferedImage kitchen;
    
    static {
    	try {
			kitchen = ImageIO.read(TileManager.class.getResourceAsStream("/screens/kitchen.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}





