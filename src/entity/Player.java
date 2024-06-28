package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.MouseHandler;
import tile.TileManager;

public class Player {

	GamePanel gp;
	MouseHandler mouseH;
	TileManager tileM;
	EntityManager entityM;
	
	public int playerX, playerY;
	public double speed;
	public int selX, selY;
	public int pickTileX, pickTileY;
	public int collisionX, collisionY; // knockback location when player collides with entity
	
	public BufferedImage imageBeforeCollision;
	public BufferedImage image;
	public BufferedImage idle1, idle2, front1, front2, front3, back1, back2, back3, right1, right2, right3, left1, left2, left3, bent1, bent2, bent3;
	public String state; // idle, up, down, right, left, picking
	public boolean collision;
	
	public int pickStamp;
	public int pickInterval;
	public int collisionStamp;
	public int collRefStamp; // this allows the player to have hit immunity for a while after getting hit

	
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
	
	// same /\
	public void setEntityManager(EntityManager entityM) {
		this.entityM = entityM;
	}
	
	public void setDefaultValues() {
		
		playerX = 0;
		playerY = gp.tileSize * gp.skyLevel;
		speed = 3;
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
		checkCollision();
		
		String state2 = state;
		if(state1 != state2 && state2 == "picking") {
			pickStamp = gp.frameCount;
			pickInterval = gp.generateRandom(300, 360);
			pickTileX = playerX;
			pickTileY = playerY;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int imageOffset = setImage();
		
		if(state == "collision") {
			BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
			RescaleOp op = new RescaleOp(20f, 0, null);
			op.filter(image, temp);
			g2.drawImage(temp, playerX + imageOffset, playerY, gp.tileSize, gp.tileSize, null);
		} else {
			if(collRefStamp > gp.frameCount) {
				if(gp.frameCount % 15 < 7) {
					g2.drawImage(image, playerX + imageOffset, playerY, gp.tileSize, gp.tileSize, null);
				}
			} else {
				g2.drawImage(image, playerX + imageOffset, playerY, gp.tileSize, gp.tileSize, null);
			}
			
		}
	}
	
	// selects image to be drawn and returns imageOffset
	public int setImage() {
		
		int imageOffset = 0;
		
		switch(state) {
		case "idle":
			
			if(gp.frameCount % 180 <= 160) { // occupies maj of 3 seconds
				image = idle1;
			} else { // occupies last 20 frames every 3 seconds
				image = idle2;
			}
			
			break;
		case "down":
			
			if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
				image = front1;
			} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
				image = front2;
			} else {
				image = front3;
			}
			
			break;
		case "up":

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
			double percentTimeLeft = (double)(pickStamp + pickInterval - gp.frameCount) / pickInterval * 100;
			
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
		return imageOffset;
	}

	public void movePlayer() {
		
		if(state == "collision") {
			
			double swatSpeed = gp.calculateKnockbackSpeed(playerX, playerY, collisionX, collisionY, gp.frameCount, collisionStamp);
			String[] temp = gp.homeTowardDest(playerX, playerY, collisionX, collisionY, swatSpeed);
			playerX = (int) Math.round(playerX + Double.parseDouble(temp[1]));
			playerY = (int) Math.round(playerY + Double.parseDouble(temp[2]));
			if(playerX == collisionX && playerY == collisionY) {
				state = "up";
			}
			
		} else {
			if(state != "picking") {
				String[] temp = gp.homeTowardDest(playerX, playerY, selX, selY, speed);
				
				state = temp[0];
				playerX = (int) Math.round(playerX + Double.parseDouble(temp[1]));
				playerY = (int) Math.round(playerY + Double.parseDouble(temp[2]));
			}
		}
		
	}
	
	public void checkSelectedTile() {
		selX = tileM.colSelTile*gp.tileSize;
		selY = tileM.rowSelTile*gp.tileSize;
	}
	
	public void checkPicking() {
		if(((pickTileX == playerX && pickTileY == playerY) && tileM.tile[tileM.rowSelTile][tileM.colSelTile].pickable) || ((selX == playerX && selY == playerY) && tileM.tile[tileM.rowSelTile][tileM.colSelTile].pickable)) {
			state = "picking";
			if(pickStamp + pickInterval == gp.frameCount) {
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
	
	public void checkCollision() {
		
		if(collision == false && collRefStamp < gp.frameCount) {
			int collisionIndex = entityM.checkPlayerCollision();
			if(collisionIndex != -1) {
				collision = true;
				int entityX = entityM.entities.get(collisionIndex).entityX;
				int entityY = entityM.entities.get(collisionIndex).entityY;
				
				int[] temp = gp.extrapolatePointByDistance(entityX, entityY, playerX, playerY, 96);
				collisionX = temp[0];
				collisionY = temp[1];
				
				keepInBounds();
				
				imageBeforeCollision = image;
				collisionStamp = gp.frameCount + 15;
				collRefStamp = collisionStamp + 120;
			}
		} 
		if(collision) {
			collision = false;
			state = "collision";
		}
	}
	
	// keeps the player within the bounds of the yard while being knocked back
	public void keepInBounds() {
		
		boolean check = false;
		int count = 0;
		while(check == false) {
			check = true;
			
			int collXSignum = Integer.signum(collisionX - playerX);
			int collYSignum = Integer.signum(collisionY - playerY);
			int altSignum = (gp.generateRandom(0, 1) * 2) - 1;
			double inertia = 1.2;
			if(collXSignum == 0 || count >= 10) {
				collXSignum = altSignum;
			}
			if(collYSignum == 0 || count >= 10) {
				collYSignum = altSignum;
			}
			if(collisionX < 0) {
				collisionY = collisionY + (int)(((collYSignum * Math.abs(collisionX))) / inertia);
				collisionX = 0;
				check = false;
			}
			if(collisionX > gp.screenWidth - gp.tileSize) {
				collisionY = collisionY + (int)((collYSignum * (collisionX - (gp.screenWidth - gp.tileSize))) / inertia);
				collisionX = gp.screenWidth - gp.tileSize;
				check = false;
			}
			if(collisionY < gp.skyLevel*gp.tileSize) {
				collisionX = collisionX + (int)((collXSignum * (collisionY + gp.skyLevel*gp.tileSize)) / inertia);
				collisionY = gp.skyLevel*gp.tileSize;
				check = false;
			}
			if(collisionY > gp.screenHeight - gp.tileSize) {
				collisionX = collisionX + (int)((collXSignum * (collisionY - (gp.screenHeight - gp.tileSize))) / inertia);
				collisionY = gp.screenHeight - gp.tileSize;
				check = false;
			}
			count++;
		}
	}
	
}








