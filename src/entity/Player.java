package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import main.Constants;
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
		
		ladybugStamp = gp.frameCount + Utility.generateRandom(Constants.LADYBUG_SPAWN_MIN, Constants.LADYBUG_SPAWN_MAX);
		mowerStamp = gp.frameCount + Utility.generateRandom(Constants.MOWER_SPAWN_MIN, Constants.MOWER_SPAWN_MAX);
		
		pickTileX = -Constants.TILE_SIZE;
		pickTileY = -Constants.TILE_SIZE;
		
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
		
		playerX = Constants.PLAYER_SPAWN_X;
		playerY = Constants.PLAYER_SPAWN_Y;
		speed = Constants.PLAYER_SPEED;
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
			pickInterval = Utility.generateRandom(Constants.PLAYER_PICK_TIME_MIN, Constants.PLAYER_PICK_TIME_MAX);
			pickTileX = playerX;
			pickTileY = playerY;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int imageOffset = setImage();
		
		if(state == "collision") {
			BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
			RescaleOp op = new RescaleOp(Constants.IMAGE_HIT_BRIGHTNESS, 0, null);
			op.filter(image, temp);
			g2.drawImage(temp, playerX + imageOffset, playerY, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
		} else {
			if(collRefStamp > gp.frameCount) {
				if(gp.frameCount % Constants.PLAYER_FLICKER_SPEED < Constants.PLAYER_FLICKER_SPEED / 2) {
					g2.drawImage(image, playerX + imageOffset, playerY, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
				}
			} else {
				g2.drawImage(image, playerX + imageOffset, playerY, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
			}
			
		}
	}
	
	// selects image to be drawn and returns imageOffset
	public int setImage() {
		
		int imageOffset = 0;
		
		switch(state) {
		case "idle":
			
			if(gp.frameCount % Constants.PLAYER_IDLE_STAND_LENGTH + Constants.PLAYER_IDLE_BLINK_LENGTH <= Constants.PLAYER_IDLE_STAND_LENGTH) {
				image = ImageManager.idle1;
			} else {
				image = ImageManager.idle2;
			}
			
			break;
		case "down":
			
			if(gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) <= Constants.PLAYER_WALK_FRAME_LENGTH || 
			  (gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) > Constants.PLAYER_WALK_FRAME_LENGTH*2 && 
			   gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) <= Constants.PLAYER_WALK_FRAME_LENGTH*3)) {
				image = ImageManager.front1;
			} else if(gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) > Constants.PLAYER_WALK_FRAME_LENGTH && 
					  gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) <= Constants.PLAYER_WALK_FRAME_LENGTH*2) {
				image = ImageManager.front2;
			} else {
				image = ImageManager.front3;
			}
			
			break;
		case "up":

			if(gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) <= Constants.PLAYER_WALK_FRAME_LENGTH || 
			  (gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) > Constants.PLAYER_WALK_FRAME_LENGTH*2 && 
			   gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) <= Constants.PLAYER_WALK_FRAME_LENGTH*3)) {
				image = ImageManager.back1;
			} else if(gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) > Constants.PLAYER_WALK_FRAME_LENGTH && 
					  gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) <= Constants.PLAYER_WALK_FRAME_LENGTH*2) {
				image = ImageManager.back2;
			} else {
				image = ImageManager.back3;
			}
			
			break;
		case "right":
			
			if(gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) <= Constants.PLAYER_WALK_FRAME_LENGTH || 
			  (gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) > Constants.PLAYER_WALK_FRAME_LENGTH*2 && 
			   gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) <= Constants.PLAYER_WALK_FRAME_LENGTH*3)) {
				image = ImageManager.right1;
			} else if(gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) > Constants.PLAYER_WALK_FRAME_LENGTH && 
					  gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) <= Constants.PLAYER_WALK_FRAME_LENGTH*2) {
				image = ImageManager.right2;
			} else {
				image = ImageManager.right3;
			}
			
			break;
		case "left":
			
			if(gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) <= Constants.PLAYER_WALK_FRAME_LENGTH || 
			  (gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) > Constants.PLAYER_WALK_FRAME_LENGTH*2 && 
			   gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) <= Constants.PLAYER_WALK_FRAME_LENGTH*3)) {
				image = ImageManager.left1;
			} else if(gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) > Constants.PLAYER_WALK_FRAME_LENGTH && 
					  gp.frameCount % (Constants.PLAYER_WALK_FRAME_LENGTH*4) <= Constants.PLAYER_WALK_FRAME_LENGTH*2) {
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
		selX = tileM.colSelTile*Constants.TILE_SIZE;
		selY = tileM.rowSelTile*Constants.TILE_SIZE;
	}
	
	public void checkPicking() {
		if(((pickTileX == playerX && pickTileY == playerY) && tileM.tile[tileM.rowSelTile][tileM.colSelTile].pickable) || ((selX == playerX && selY == playerY) && tileM.tile[tileM.rowSelTile][tileM.colSelTile].pickable)) {
			state = "picking";
		}
		if(pickStamp + pickInterval == gp.frameCount && (pickTileX == playerX && pickTileY == playerY)) {
			state = "idle";
			
			tileM.tileNums[pickTileY/Constants.TILE_SIZE][pickTileX/Constants.TILE_SIZE] = 8;
			tileM.tile[pickTileY/Constants.TILE_SIZE][pickTileX/Constants.TILE_SIZE].pickable = false;
			tileM.tile[pickTileY/Constants.TILE_SIZE][pickTileX/Constants.TILE_SIZE].isFlower = false;
			tileM.tile[pickTileY/Constants.TILE_SIZE][pickTileX/Constants.TILE_SIZE].changeStamp = Utility.generateRandom(Constants.TILE_CHANGE_FROM_PICKED_MIN, Constants.TILE_CHANGE_FROM_PICKED_MAX) + gp.frameCount;
			
			pickTileX = -Constants.TILE_SIZE;
			pickTileY = -Constants.TILE_SIZE;
		}
	}
	
	public void checkCollision() {
		
		if(collision == false && collRefStamp < gp.frameCount) {
			int collisionIndex = entityM.checkPlayerCollision();
			if(collisionIndex != -1) {
				collision = true;
				pickTileX = -Constants.TILE_SIZE;
				pickTileY = -Constants.TILE_SIZE;
				
				int entityX = entityM.entities.get(collisionIndex).entityX;
				int entityY = entityM.entities.get(collisionIndex).entityY;
				
				int[] temp = Utility.extrapolatePointByDistance(entityX, entityY, playerX, playerY, Constants.PLAYER_KNOCKBACK_DISTANCE);
				collisionX = temp[0];
				collisionY = temp[1];
				
				keepInBounds();
				
				imageBeforeCollision = image;
				collisionStamp = gp.frameCount + Constants.PLAYER_KNOCKBACK_SPEED;
				collRefStamp = collisionStamp + Constants.PLAYER_KNOCKBACK_RECOVERY;
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
			if(collXSignum == 0 || count >= 10) {
				collXSignum = altSignum;
			}
			if(collYSignum == 0 || count >= 10) {
				collYSignum = altSignum;
			}
			if(collisionX < 0) {
				collisionY = collisionY + (int)(((collYSignum * Math.abs(collisionX))) / Constants.PLAYER_KNOCKBACK_INERTIA);
				collisionX = 0;
				check = false;
			}
			if(collisionX > Constants.SCREEN_WIDTH - Constants.TILE_SIZE) {
				collisionY = collisionY + (int)((collYSignum * (collisionX - (Constants.SCREEN_WIDTH - Constants.TILE_SIZE))) / Constants.PLAYER_KNOCKBACK_INERTIA);
				collisionX = Constants.SCREEN_WIDTH - Constants.TILE_SIZE;
				check = false;
			}
			if(collisionY < Constants.SKY_LEVEL*Constants.TILE_SIZE) {
				collisionX = collisionX + (int)((collXSignum * (collisionY - Constants.SKY_LEVEL*Constants.TILE_SIZE)) / Constants.PLAYER_KNOCKBACK_INERTIA);
				collisionY = Constants.SKY_LEVEL*Constants.TILE_SIZE;
				check = false;
			}
			if(collisionY > Constants.SCREEN_HEIGHT - Constants.TILE_SIZE) {
				collisionX = collisionX + (int)((collXSignum * (collisionY - (Constants.SCREEN_HEIGHT - Constants.TILE_SIZE))) / Constants.PLAYER_KNOCKBACK_INERTIA);
				collisionY = Constants.SCREEN_HEIGHT - Constants.TILE_SIZE;
				check = false;
			}
			count++;
		}
	}
	
	public void checkSpawnLadybug() {
		if(gp.frameCount == ladybugStamp) {
			entityM.addEntity(playerX, playerY, "Ladybug");
			ladybugStamp = gp.frameCount + Utility.generateRandom(Constants.LADYBUG_SPAWN_MIN, Constants.LADYBUG_SPAWN_MAX);
		}
	}
	
	public void checkSpawnMower() {
		if(gp.frameCount == mowerStamp) {
			entityM.addEntity(playerX, playerY, "Mower");
			mowerStamp = gp.frameCount + Utility.generateRandom(Constants.MOWER_SPAWN_MIN, Constants.MOWER_SPAWN_MAX);
		}
	}
	
}








