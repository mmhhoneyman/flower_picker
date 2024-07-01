package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

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
	
}





