package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.MouseHandler;
import tile.TileManager;

public class Player {

	GamePanel gp;
	MouseHandler mouseH;
	TileManager tileM;
	
	public int playerX, playerY;
	public double speed;
	public int selX, selY;
	public int pickTileX, pickTileY;
	
	public BufferedImage idle1, idle2, front1, front2, front3, back1, back2, back3, right1, right2, right3, left1, left2, left3, bent1, bent2, bent3;
	public String state; // idle, front, back, right, left, picking
	public int timeStamp;
	public int pickInterval;

	
	public Player(GamePanel gp, MouseHandler mouseH) {
		
		this.gp = gp;
		this.mouseH = mouseH;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	// exists to manage cyclic dependency with other classes (TileManager)
	public void setTileManager(TileManager tileM) {
		this.tileM = tileM;
	}
	
	public void setDefaultValues() {
		
		playerX = 0;
		playerY = gp.tileSize * gp.skyLevel;
		speed = 1 * gp.scale;
		state = "idle";
	}
	
	public void getPlayerImage() {
		
		try {
			
			idle1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_idle_1.png"));
			idle2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_idle_2.png"));
			
			front1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_front_1.png"));
			front2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_front_2.png"));
			front3 = ImageIO.read(getClass().getResourceAsStream("/player/boy_front_3.png"));
			
			back1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_back_1.png"));
			back2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_back_2.png"));
			back3 = ImageIO.read(getClass().getResourceAsStream("/player/boy_back_3.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_3.png"));
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_3.png"));
			
			bent1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_bent_1.png"));
			bent2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_bent_2.png"));
			bent3 = ImageIO.read(getClass().getResourceAsStream("/player/boy_bent_3.png"));
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		String state1 = state;
		
		movePlayer();
		checkSelectedTile();
		checkPicking();
		
		String state2 = state;
		if(state1 != state2 && state2 == "picking") {
			timeStamp = gp.frameCount;
			pickInterval = gp.generateRandom(300, 360);
			pickTileX = playerX;
			pickTileY = playerY;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		int imageOffset = 0;
		
		switch(state) {
		case "idle":
			
			if(gp.frameCount % 180 <= 160) { // occupies maj of 3 seconds
				image = idle1;
			} else { // occupies last 20 frames every 3 seconds
				image = idle2;
			}
			
			break;
		case "front":
			
			if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
				image = front1;
			} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
				image = front2;
			} else {
				image = front3;
			}
			
			break;
		case "back":

			if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
				image = back1;
			} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
				image = back2;
			} else {
				image = back3;
			}
			
			break;
		case "right":
			
			if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
				image = right1;
			} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
				image = right2;
			} else {
				image = right3;
			}
			
			break;
		case "left":
			
			if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
				image = left1;
			} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
				image = left2;
			} else {
				image = left3;
			}
			
			break;
		case "picking":
			double percentTimeLeft = (double)(timeStamp + pickInterval - gp.frameCount) / pickInterval * 100;
			
			if(percentTimeLeft > 97) {
				image = back1;
			} else if(percentTimeLeft > 95) {
				image = bent1;
			} else if(percentTimeLeft > 90) {
				image = bent2;
			} else if(percentTimeLeft > 77) {
				imageOffset = gp.frameCount % 5 - 2;
				image = bent3;
			} else if(percentTimeLeft > 60) {
				imageOffset = 0;
				image = bent2;
			} else if(percentTimeLeft > 45) {
				imageOffset = gp.frameCount % 5 - 2;
				image = bent3;
			} else if(percentTimeLeft > 30) {
				imageOffset = 0;
				image = bent2;
			} else if(percentTimeLeft > 4) {
				imageOffset = gp.frameCount % 5 - 2;
				image = bent3;
			} else {
				imageOffset = 0;
				image = back1;
			}
			
			break;
		}
		g2.drawImage(image, playerX + imageOffset, playerY, gp.tileSize, gp.tileSize, null);
		
	}

	public void movePlayer() {
		
		int distanceX = selX - playerX;
		int distanceY = selY - playerY;
		
		if(state != "picking") {
			if (distanceX != 0 || distanceY != 0) {
			// calculate slope and check for dividing by zero
			double slope;
			try {
				slope = (double)distanceY / (double)distanceX;
			} catch(ArithmeticException e) {
				slope = Double.POSITIVE_INFINITY;
			}
			
			double speedX;
			double speedY;
			// uses the X focused equation if the distance is more horizontal than vertical, and vice versa
			if(Math.abs(distanceY) > Math.abs(distanceX)) {
				speedX = (Math.round(speed / Math.sqrt(1 + slope * slope)) * Integer.signum(distanceX));
				speedY = speed * Integer.signum(distanceY);
			} else {
				speedY = (Math.round(Math.abs(slope) * speed / Math.sqrt(1 + slope * slope)) * Integer.signum(distanceY));
				speedX = speed * Integer.signum(distanceX);
			}
			// if both speeds are maxed, make both speeds one less
			if(Math.abs(speedX) == speed && Math.abs(speedY) == speed && speed != 1) {
				speedX = speedX - Integer.signum(distanceX);
				speedY = speedY - Integer.signum(distanceY);
			}
	        // if the slope is infinity, speedY is maxed (prevents player from freezing)
	        if(slope == Double.POSITIVE_INFINITY || slope == Double.NEGATIVE_INFINITY) {
	        	speedY = speed * Integer.signum(distanceY);
	        }
			// if the player is right next to the tile it clips to the tile
			if(Math.abs(speedX) > Math.abs(distanceX)) {
				speedX = distanceX;
			}
			if(Math.abs(speedY) > Math.abs(distanceY)) {
				speedY = distanceY;
			}
			
			// System.out.println("playerX: " + playerX + " playerY: " + playerY + " tileX: " + tileX + " tileY: " + tileY + " distanceX: " + distanceX + " distanceY: " + distanceY + " speedX: " + speedX + " speedY: " + speedY + " slope: " + slope);
			
			if(Math.abs(slope) < 1.5) {
				if(Integer.signum(distanceX) == 1) {
					state = "right";
				} else {
					state = "left";
				}
			} else {
				if(Integer.signum(distanceY) == 1) {
					state = "front";
				} else {
					state = "back";
				}
			}
			
			
			playerX = (int) Math.round(playerX + speedX);
			playerY = (int) Math.round(playerY + speedY);
			
			} else {
			state = "idle";
			}
		}
		
		
	}
	
	public void checkSelectedTile() {
		selX = tileM.colSelTile*gp.tileSize;
		selY = tileM.rowSelTile*gp.tileSize;
	}
	
	public void checkPicking() {
		if((pickTileX == playerX && pickTileY == playerY) || (selX == playerX && selY == playerY) && tileM.tile[tileM.rowSelTile][tileM.colSelTile].pickable) {
			state = "picking";
			if(timeStamp + pickInterval == gp.frameCount) {
				state = "idle";
				pickTileX = -1;
				pickTileY = -1;
				tileM.tileNums[playerY/gp.tileSize][playerX/gp.tileSize] = 8;
				tileM.tile[playerY/gp.tileSize][playerX/gp.tileSize].pickable = false;
				tileM.tile[playerY/gp.tileSize][playerX/gp.tileSize].isFlower = false;
				tileM.tile[playerY/gp.tileSize][playerX/gp.tileSize].changeStamp = gp.generateRandom(180, 240) + gp.frameCount;
			}
		}
	}
	
}








