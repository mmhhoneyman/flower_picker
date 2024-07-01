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

public class Butterfly extends Entity{
	
	public int pickStamp; // frame to stop picking
	public int endDestX, endDestY;
	public BufferedImage imageBeforeSwat;
	public BufferedImage image;
	
	public Butterfly(GamePanel gp, MouseHandler mouseH, Player player, TileManager tileM, int destX, int destY) {
		
		super(gp, mouseH, player, tileM, destX, destY);
		setSpawnLocation();
		state = "up";
		pickStamp = 0;
		
		setSpeed();
		
	}
	
	@Override
	void setSpawnLocation() {
		// TODO Auto-generated method stub
		
		int rand = Utility.generateRandom(1, 4);
		
		switch(rand) {
			case 1: // above flower
				spawnX = destX;
				spawnY = 0 - Constants.TILE_SIZE;
				endDestX = destX;
				endDestY = Constants.SCREEN_HEIGHT + Constants.TILE_SIZE;
				break;
			case 2: // below flower
				spawnX = destX;
				spawnY = Constants.SCREEN_HEIGHT + Constants.TILE_SIZE;
				endDestX = destX;
				endDestY = 0 - Constants.TILE_SIZE;
				break;
			case 3: // left of flower
				spawnX = 0 - Constants.TILE_SIZE;
				spawnY = destY;
				endDestX = Constants.SCREEN_WIDTH + Constants.TILE_SIZE;
				endDestY = destY;
				break;
			case 4: // right of flower
				spawnX = Constants.SCREEN_WIDTH + Constants.TILE_SIZE;
				spawnY = destY;
				endDestX = 0 - Constants.TILE_SIZE;
				endDestY = destY;
				break;
		}
		entityX = spawnX;
		entityY = spawnY;
		
	}
	
	public void setSpeed() {
		
		double rand = Utility.generateRandom(Constants.BUTTERFLY_SPEED_MIN, Constants.BUTTERFLY_SPEED_MAX) / 2;
		
		this.speed = rand * 3;
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
		checkFlower();
		checkPicking();
		move();
		
		if(swat) {
			imageBeforeSwat = image;
			state = "swat";
			int[] temp = Utility.extrapolatePointByDistance(player.playerX, player.playerY, entityX, entityY, Constants.BUTTERFLY_KNOCKBACK_DISTANCE);
			swatX = temp[0];
			swatY = temp[1];
			swatStamp = gp.frameCount + Constants.BUTTERFLY_KNOCKBACK_SPEED;
			swat = false;
		}
	}

	@Override
	void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
		int imageOffset = 0;
		if(state == "picking") {
			imageOffset = Constants.BUTTERFLY_PICK_OFFSET;
		}
		setImage();
		
		if(state == "swat") {
			BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
			RescaleOp op = new RescaleOp(Constants.IMAGE_HIT_BRIGHTNESS, 0, null);
			op.filter(image, temp);
			g2.drawImage(temp, entityX, entityY - imageOffset, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
		} else {
			g2.drawImage(image, entityX, entityY - imageOffset, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
		}
		
	}
	
	public void setImage() {
		BufferedImage image = null;
		int animationFrame;
		int interval = gp.frameCount % (Constants.BUTTERFLY_ANIMATION_FRAME_LENGTH * 4);
		
		if(interval <= Constants.BUTTERFLY_ANIMATION_FRAME_LENGTH || (interval > Constants.BUTTERFLY_ANIMATION_FRAME_LENGTH * 2 && interval <= Constants.BUTTERFLY_ANIMATION_FRAME_LENGTH * 3)) {
			animationFrame = 1;
		} else if(interval > Constants.BUTTERFLY_ANIMATION_FRAME_LENGTH && interval <= Constants.BUTTERFLY_ANIMATION_FRAME_LENGTH * 2) {
			animationFrame = 2;
		} else {
			animationFrame = 3;
		}
		
		switch(state) {
			case "left":
				
				switch(animationFrame) {
					case 1:
						image = ImageManager.butterfly_left_2;
						break;
					case 2:
						image = ImageManager.butterfly_left_1;
						break;
					case 3:
						image = ImageManager.butterfly_left_3;
						break;
				}
				
				break;
			case "right":

				switch(animationFrame) {
					case 1:
						image = ImageManager.butterfly_right_2;
						break;
					case 2:
						image = ImageManager.butterfly_right_1;
						break;
					case 3:
						image = ImageManager.butterfly_right_3;
						break;
				}
				
				break;
			case "up":

				switch(animationFrame) {
					case 1:
						image = ImageManager.butterfly_up_1;
						break;
					case 2:
						image = ImageManager.butterfly_up_2;
						break;
					case 3:
						image = ImageManager.butterfly_up_3;
						break;
				}
				
				break;
			case "down":

				switch(animationFrame) {
					case 1:
						image = ImageManager.butterfly_down_1;
						break;
					case 2:
						image = ImageManager.butterfly_down_2;
						break;
					case 3:
						image = ImageManager.butterfly_down_3;
						break;
				}
				
				break;
			case "picking":
				
				if(pickStamp - gp.frameCount > 175) {
					image = ImageManager.butterfly_up_1;
				} else if(pickStamp - gp.frameCount > 170 && pickStamp - gp.frameCount <= 175) {
					image = ImageManager.butterfly_up_2;
				} else if(pickStamp - gp.frameCount > 10 && pickStamp - gp.frameCount <= 170) {
					image = ImageManager.butterfly_up_3;
				} else if(pickStamp - gp.frameCount > 5 && pickStamp - gp.frameCount <= 10) {
					image = ImageManager.butterfly_up_2;
				} else {
					image = ImageManager.butterfly_up_1;
				}
				
				break;
			case "swat":
				image = imageBeforeSwat;
				
				break;
		}
		this.image = image;
	}

	@Override
	void move() {
		// TODO Auto-generated method stub
		if(state == "swat") {
			
			double swatSpeed = Utility.calculateKnockbackSpeed(entityX, entityY, swatX, swatY, gp.frameCount, swatStamp);
			String[] temp = Utility.homeTowardDest(entityX, entityY, swatX, swatY, swatSpeed);
			entityX = (int) Math.round(entityX + Double.parseDouble(temp[1]));
			entityY = (int) Math.round(entityY + Double.parseDouble(temp[2]));
			if(entityX == swatX && entityY == swatY) {
				state = "up";
			}
		} else {
			if(state != "picking" && state != "despawn") {
				if(gp.frameCount % 2 == 0) {
					String[] temp = Utility.homeTowardDest(entityX, entityY, destX, destY, speed);
					state = temp[0];
					entityX = (int) Math.round(entityX + Double.parseDouble(temp[1]));
					entityY = (int) Math.round(entityY + Double.parseDouble(temp[2]));
				}
		
			}
			
		}
		
		
	}
	
	public void checkPicking() {
		
		int entityTileX;
		int entityTileY;
		try {
			entityTileX = entityX / Constants.TILE_SIZE;
		} catch(ArithmeticException e) {
			entityTileX = 0;
		}
		try {
			entityTileY = entityY / Constants.TILE_SIZE;
		} catch(ArithmeticException e) {
			entityTileY = 0;
		}
		
		if(flee) {
			if(state == "picking") {
				state = "up";
			}
		} else {
			int flowerStatus = onFlower();
			
			if(flowerStatus == 1) {
				state = "picking";
				pickStamp = gp.frameCount + Constants.BUTTERFLY_PICK_TIME;
				tileM.tile[entityTileY][entityTileX].pickable = false;
				if(player.pickTileX == entityX && player.pickTileY == entityY) {
					player.pickTileX = -1;
					player.pickTileY = -1;
				}
				
				
			} else if(flowerStatus == 2) {
				tileM.tile[entityTileY][entityTileX].pickable = false;
				if(pickStamp == gp.frameCount) {
					state = "up";
					tileM.tile[entityTileY][entityTileX].isFlower = false;
					tileM.tileNums[entityTileY][entityTileX] = 8;
					tileM.tile[entityTileY][entityTileX].changeStamp = Utility.generateRandom(Constants.TILE_CHANGE_FROM_PICKED_MIN, Constants.TILE_CHANGE_FROM_PICKED_MAX) + gp.frameCount;
				}
			} else if(flowerStatus == 3) {
				state = "despawn";
			}
		}
		
		
		
		
	}
	
	public int onFlower() { // 0: not on flower, 1: just got on flower, 2: has been on flower, 3: despawn
		
		int destTileX;
		int destTileY;
		try {
			destTileX = destX / Constants.TILE_SIZE;
		} catch(ArithmeticException e) {
			destTileX = 0;
		}
		try {
			destTileY = destY / Constants.TILE_SIZE;
		} catch(ArithmeticException e) {
			destTileY = 0;
		}
		
		int entityTileX;
		int entityTileY;
		try {
			entityTileX = entityX / Constants.TILE_SIZE;
		} catch(ArithmeticException e) {
			entityTileX = 0;
		}
		try {
			entityTileY = entityY / Constants.TILE_SIZE;
		} catch(ArithmeticException e) {
			entityTileY = 0;
		}
		
		if((entityTileX < 0 || entityTileX > Constants.MAX_SCREEN_COL - 1) || (entityTileY < 0 || entityTileY > Constants.MAX_SCREEN_ROW - 1)) {
			if(destX == endDestX && destY == endDestY) {
				if((entityTileX < 0 - Constants.TILE_SIZE || entityTileX > Constants.MAX_SCREEN_COL - 1) || (entityTileY < 0 - Constants.TILE_SIZE || entityTileY > Constants.MAX_SCREEN_ROW - 1)) {
					return 3;
				}
			}
			return 0;
		}
		if(destX == entityX && destY == entityY) {
			if(tileM.tile[destTileY][destTileX].isFlower) {
				if(state == "picking") {
					return 2;
				} else {
					return 1;
				}
			} else {
				return 3;
			}
		}
		return 0;
	}
	
	public void checkFlower() {
		
		int destTileX = destX / Constants.TILE_SIZE;
		int destTileY = destY / Constants.TILE_SIZE;
		
		if(!((destTileX < 0 || destTileX > Constants.MAX_SCREEN_COL - 1) || (destTileY < 0 || destTileY > Constants.MAX_SCREEN_ROW - 1))) {
			if(tileM.tile[destTileY][destTileX].isFlower == false) {
				destX = endDestX;
				destY = endDestY;
			}
		}
		
	}
	
	
	
}






