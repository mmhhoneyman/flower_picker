package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.ImageManager;
import main.MouseHandler;
import main.Utility;
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
	public String state; // idle, up, down, right, left, picking
	public boolean collision;
	
	public int pickStamp;
	public int pickInterval;
	public int collisionStamp;
	public int collRefStamp; // this allows the player to have hit immunity for a while after getting hit
	public int ladybugStamp;
	public int mowerStamp;

	
	public Player(GamePanel gp, MouseHandler mouseH) {
		
		this.gp = gp;
		this.mouseH = mouseH;
		
		ladybugStamp = gp.frameCount + Utility.generateRandom(240, 360);
		mowerStamp = gp.frameCount + Utility.generateRandom(240, 360);
		
		pickTileX = -1;
		pickTileY = -1;
		
		pickStamp = -1;
		
		setDefaultValues();
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
	
	public void update() {
		
		String state1 = state;
		
		movePlayer();
		checkSelectedTile();
		checkPicking();
		checkCollision();
		checkSpawnLadybug();
		checkSpawnMower();
		
		String state2 = state;
		if(state1 != state2 && state2 == "picking") {
			pickStamp = gp.frameCount;
			pickInterval = Utility.generateRandom(300, 360);
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
				image = ImageManager.idle1;
			} else { // occupies last 20 frames every 3 seconds
				image = ImageManager.idle2;
			}
			
			break;
		case "down":
			
			if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
				image = ImageManager.front1;
			} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
				image = ImageManager.front2;
			} else {
				image = ImageManager.front3;
			}
			
			break;
		case "up":

			if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
				image = ImageManager.back1;
			} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
				image = ImageManager.back2;
			} else {
				image = ImageManager.back3;
			}
			
			break;
		case "right":
			
			if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
				image = ImageManager.right1;
			} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
				image = ImageManager.right2;
			} else {
				image = ImageManager.right3;
			}
			
			break;
		case "left":
			
			if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
				image = ImageManager.left1;
			} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
				image = ImageManager.left2;
			} else {
				image = ImageManager.left3;
			}
			
			break;
		case "picking":
			double percentTimeLeft = (double)(pickStamp + pickInterval - gp.frameCount) / pickInterval * 100;
			
			if(percentTimeLeft > 97) {
				image = ImageManager.back1;
			} else if(percentTimeLeft > 95) {
				image = ImageManager.bent1;
			} else if(percentTimeLeft > 90) {
				image = ImageManager.bent2;
			} else if(percentTimeLeft > 77) {
				imageOffset = gp.frameCount % 5 - 2;
				image = ImageManager.bent3;
			} else if(percentTimeLeft > 60) {
				imageOffset = 0;
				image = ImageManager.bent2;
			} else if(percentTimeLeft > 45) {
				imageOffset = gp.frameCount % 5 - 2;
				image = ImageManager.bent3;
			} else if(percentTimeLeft > 30) {
				imageOffset = 0;
				image = ImageManager.bent2;
			} else if(percentTimeLeft > 4) {
				imageOffset = gp.frameCount % 5 - 2;
				image = ImageManager.bent3;
			} else {
				imageOffset = 0;
				image = ImageManager.back1;
			}
			
			break;
		}
		return imageOffset;
	}

	public void movePlayer() {
		
		if(state == "collision") {
			
			double swatSpeed = Utility.calculateKnockbackSpeed(playerX, playerY, collisionX, collisionY, gp.frameCount, collisionStamp);
			String[] temp = Utility.homeTowardDest(playerX, playerY, collisionX, collisionY, swatSpeed);
			playerX = (int) Math.round(playerX + Double.parseDouble(temp[1]));
			playerY = (int) Math.round(playerY + Double.parseDouble(temp[2]));
			if(playerX == collisionX && playerY == collisionY) {
				state = "up";
			}
			
		} else {
			if(state != "picking") {
				String[] temp = Utility.homeTowardDest(playerX, playerY, selX, selY, speed);
				
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
		}
		if(pickStamp + pickInterval == gp.frameCount) {
			state = "idle";
			
			tileM.tileNums[pickTileY/gp.tileSize][pickTileX/gp.tileSize] = 8;
			tileM.tile[pickTileY/gp.tileSize][pickTileX/gp.tileSize].pickable = false;
			tileM.tile[pickTileY/gp.tileSize][pickTileX/gp.tileSize].isFlower = false;
			tileM.tile[pickTileY/gp.tileSize][pickTileX/gp.tileSize].changeStamp = Utility.generateRandom(180, 240) + gp.frameCount;
			
			pickTileX = -1;
			pickTileY = -1;
		}
	}
	
	public void checkCollision() {
		
		if(collision == false && collRefStamp < gp.frameCount) {
			int collisionIndex = entityM.checkPlayerCollision();
			if(collisionIndex != -1) {
				collision = true;
				int entityX = entityM.entities.get(collisionIndex).entityX;
				int entityY = entityM.entities.get(collisionIndex).entityY;
				
				int[] temp = Utility.extrapolatePointByDistance(entityX, entityY, playerX, playerY, 96);
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
			int altSignum = (Utility.generateRandom(0, 1) * 2) - 1;
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
				collisionX = collisionX + (int)((collXSignum * (collisionY - gp.skyLevel*gp.tileSize)) / inertia);
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
	
	public void checkSpawnLadybug() {
		if(gp.frameCount == ladybugStamp) {
			entityM.addEntity(playerX, playerY, "Ladybug");
			ladybugStamp = gp.frameCount + Utility.generateRandom(700, 900);
		}
	}
	
	public void checkSpawnMower() {
		if(gp.frameCount == mowerStamp) {
			entityM.addEntity(playerX, playerY, "Mower");
			mowerStamp = gp.frameCount + Utility.generateRandom(700, 900);
		}
	}
	
}








