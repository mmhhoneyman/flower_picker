package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.Constants;
import main.GamePanel;
import main.ImageManager;
import main.MouseHandler;
import main.Utility;
import tile.TileManager;

public class Mower extends Entity{
	
	public int endDestX, endDestY;
	
	public int turns;
	
	public BufferedImage image;
	
	public Mower(GamePanel gp, MouseHandler mouseH, Player player, TileManager tileM, int destX, int destY) {
		
		super(gp, mouseH, player, tileM, destX, destY);
		setSpawnLocation();
		state = "up";
		
		endDestX = -1;
		endDestY = -1;
		
		turns = Utility.generateRandom(Constants.MOWER_TURNS_MIN, Constants.MOWER_TURNS_MAX);
		
		setSpeed();
		
	}
	
	@Override
	void setSpawnLocation() {
		// TODO Auto-generated method stub
		
		int rand = Utility.generateRandom(1, 3);
		int randX = Utility.generateRandom(0, Constants.MAX_SCREEN_COL - 2) * Constants.TILE_SIZE;
		int randY = Utility.generateRandom(Constants.SKY_LEVEL + 1, Constants.MAX_SCREEN_ROW - 2) * Constants.TILE_SIZE;
		int randDestX = Utility.generateRandom(0, Constants.MAX_SCREEN_COL - 2) * Constants.TILE_SIZE;
		int randDestY = Utility.generateRandom(Constants.SKY_LEVEL, Constants.MAX_SCREEN_ROW - 2) * Constants.TILE_SIZE;
		
		switch(rand) {
			case 1: // below screen
				spawnX = randX;
				spawnY = Constants.SCREEN_HEIGHT + Constants.TILE_SIZE;
				destX = spawnX;
				destY = randDestY;
				break;
			case 2: // left of screen
				spawnX = 0 - Constants.TILE_SIZE * 2;
				spawnY = randY;
				destX = randDestX;
				destY = spawnY;
				break;
			case 3: // right of screen
				spawnX = Constants.SCREEN_WIDTH + Constants.TILE_SIZE;
				spawnY = randY;
				destX = randDestX;
				destY = spawnY;
				break;
		}
		entityX = spawnX;
		entityY = spawnY;
		
	}
	
	public void setSpeed() {
		
		double rand = Utility.generateRandom(2, 2) / 2;
		
		this.speed = rand * 2;
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
		refreshDest();
		cutGrass();
		move();
		
	}

	@Override
	void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
		int imageOffset = (gp.frameCount % Constants.MOWER_ANIMATION_SHAKE_INTERVAL*2 - Constants.MOWER_ANIMATION_SHAKE_INTERVAL) / Constants.MOWER_ANIMATION_SHAKE_INTERVAL;

		setImage();
		
		if(state == "up" || state == "down") {
			g2.drawImage(image, entityX + imageOffset, entityY, Constants.TILE_SIZE * 2, Constants.TILE_SIZE * 2, null);
		} else {
			g2.drawImage(image, entityX, entityY + imageOffset, Constants.TILE_SIZE * 2, Constants.TILE_SIZE * 2, null);
		}
		
	}
	
	public void setImage() {
		BufferedImage image = null;
		int animationFrame;
		int interval = gp.frameCount % (Constants.MOWER_ANIMATION_FRAME_LENGTH * 3);
		
		if(interval <= Constants.MOWER_ANIMATION_FRAME_LENGTH) {
			animationFrame = 1;
		} else if(interval > Constants.MOWER_ANIMATION_FRAME_LENGTH && interval <= Constants.MOWER_ANIMATION_FRAME_LENGTH * 2) {
			animationFrame = 2;
		} else {
			animationFrame = 3;
		}
		
		switch(state) {
			case "left":
				
				switch(animationFrame) {
					case 1:
						image = ImageManager.mower_left_1;
						break;
					case 2:
						image = ImageManager.mower_left_2;
						break;
					case 3:
						image = ImageManager.mower_left_3;
						break;
				}
				
				break;
			case "right":

				switch(animationFrame) {
					case 1:
						image = ImageManager.mower_right_1;
						break;
					case 2:
						image = ImageManager.mower_right_2;
						break;
					case 3:
						image = ImageManager.mower_right_3;
						break;
				}
				
				break;
			case "up":

				switch(animationFrame) {
					case 1:
						image = ImageManager.mower_up_1;
						break;
					case 2:
						image = ImageManager.mower_up_2;
						break;
					case 3:
						image = ImageManager.mower_up_3;
						break;
				}
				
				break;
			case "down":

				switch(animationFrame) {
					case 1:
						image = ImageManager.mower_down_1;
						break;
					case 2:
						image = ImageManager.mower_down_2;
						break;
					case 3:
						image = ImageManager.mower_down_3;
						break;
				}
				
				break;
		}
		this.image = image;
	}

	@Override
	void move() {
		// TODO Auto-generated method stub
		
		if(entityX != destX) { // this ensures that the mower will only move up, down, left, and right
			if(entityX > destX) {
				entityX -= speed;
				state = "left";
			} else if(entityX < destX) {
				entityX += speed;
				state = "right";
			}
		} else {
			if(entityY > destY) {
				entityY -= speed;
				state = "up";
			} else if(entityY < destY) {
				entityY += speed;
				state = "down";
			}
		}
		
		if(entityX > destX - speed && entityX < destX + speed && entityX != destX) {
			entityX = destX;
		}
		if(entityY > destY - speed && entityY < destY + speed && entityY != destY) {
			entityY = destY;
		}
		
	}
	
	public void refreshDest() {
		
		if(entityX == destX && entityY == destY) {
			if(destX == endDestX || destY == endDestY) {
				state = "despawn";
			} else {
				if(turns > 1) {
					String testState = "";
					String testState2 = "";
					
					if(state == "up" || state == "down") {
						testState2 = "vertical";
					} else {
						testState2 = "horizontal";
					}
					do {
						int rand = Utility.generateRandom(1, 4);
						
						int offsetX;
						int offsetY;
						if(entityX / Constants.TILE_SIZE - 1 < 0 || entityX / Constants.TILE_SIZE + 1 > Constants.MAX_SCREEN_COL - 2) {
							offsetX = 0;
						} else {
							offsetX = 1;
						}
						if(entityY / Constants.TILE_SIZE + 1 > Constants.MAX_SCREEN_ROW - 2 || Constants.SKY_LEVEL > entityY / Constants.TILE_SIZE - 1) {
							offsetY = 0;
						} else {
							offsetY = 1;
						}
						
						switch(rand) {
							case 1: // down
								testState = "vertical";
								destX = entityX;
								destY = Utility.generateRandom(entityY / Constants.TILE_SIZE + offsetY, Constants.MAX_SCREEN_ROW - 2) * Constants.TILE_SIZE;
								break;
							case 2: // left
								testState = "horizontal";
								destX = Utility.generateRandom(0, entityX / Constants.TILE_SIZE - offsetX) * Constants.TILE_SIZE;
								destY = entityY;
								break;
							case 3: // right
								testState = "horizontal";
								destX = Utility.generateRandom(entityX / Constants.TILE_SIZE + offsetX, Constants.MAX_SCREEN_COL - 2) * Constants.TILE_SIZE;
								destY = entityY;
								break;
							case 4: // up
								testState = "vertical";
								destX = entityX;
								destY = Utility.generateRandom(Constants.SKY_LEVEL, entityY / Constants.TILE_SIZE - offsetY) * Constants.TILE_SIZE;
								break;
						}
					} while(testState == testState2);
					turns --;
				} else {
					int rand = Utility.generateRandom(1, 3);
					switch(rand) {
						case 1: // down
							destX = entityX;
							destY = (Constants.MAX_SCREEN_ROW + 1) * Constants.TILE_SIZE;
							break;
						case 2: // left
							destX = -2 * Constants.TILE_SIZE;
							destY = entityY;
							break;
						case 3: // right
							destX = (Constants.MAX_SCREEN_COL + 1) * Constants.TILE_SIZE;
							destY = entityY;
							break;
					}
					endDestX = destX;
					endDestY = destY;
				}
			}
		}
		
	}
	
	public void cutGrass() {
		int offset = 20;
		
		if(entityX >= 0 - Constants.TILE_SIZE && entityX <= Constants.SCREEN_WIDTH - Constants.TILE_SIZE && entityY >= 0 && entityY <= Constants.SCREEN_HEIGHT - Constants.TILE_SIZE) {
			if(state == "up" || state == "down") {
				if((entityY + offset + 1) % Constants.TILE_SIZE >= 0 && (entityY + offset + 1) % Constants.TILE_SIZE <= 2) {
					tileM.tile[(entityY + offset) / Constants.TILE_SIZE][entityX / Constants.TILE_SIZE].isFlower = false;
					tileM.tile[(entityY + offset) / Constants.TILE_SIZE][entityX / Constants.TILE_SIZE].pickable = false;
					tileM.tile[(entityY + offset) / Constants.TILE_SIZE][entityX / Constants.TILE_SIZE].entityStamp = 0;
					tileM.tile[(entityY + offset) / Constants.TILE_SIZE][entityX / Constants.TILE_SIZE].changeStamp = Utility.generateRandom(Constants.TILE_CHANGE_FROM_MOWED_1_MIN, Constants.TILE_CHANGE_FROM_MOWED_1_MAX) + gp.frameCount;
					tileM.tileNums[(entityY + offset) / Constants.TILE_SIZE][entityX / Constants.TILE_SIZE] = 21;
					
					tileM.tile[(entityY + offset) / Constants.TILE_SIZE][entityX / Constants.TILE_SIZE + 1].isFlower = false;
					tileM.tile[(entityY + offset) / Constants.TILE_SIZE][entityX / Constants.TILE_SIZE + 1].pickable = false;
					tileM.tile[(entityY + offset) / Constants.TILE_SIZE][entityX / Constants.TILE_SIZE].entityStamp = 0;
					tileM.tile[(entityY + offset) / Constants.TILE_SIZE][entityX / Constants.TILE_SIZE + 1].changeStamp = Utility.generateRandom(Constants.TILE_CHANGE_FROM_MOWED_1_MIN, Constants.TILE_CHANGE_FROM_MOWED_1_MAX) + gp.frameCount;
					tileM.tileNums[(entityY + offset) / Constants.TILE_SIZE][entityX / Constants.TILE_SIZE + 1] = 21;
				}
			} else {
				if((entityX + offset + 1) % Constants.TILE_SIZE >= 0 && (entityX + offset + 1) % Constants.TILE_SIZE <= 2) {
					tileM.tile[entityY / Constants.TILE_SIZE][(entityX + offset) / Constants.TILE_SIZE].isFlower = false;
					tileM.tile[entityY / Constants.TILE_SIZE][(entityX + offset) / Constants.TILE_SIZE].pickable = false;
					tileM.tile[(entityY + offset) / Constants.TILE_SIZE][entityX / Constants.TILE_SIZE].entityStamp = 0;
					tileM.tile[entityY / Constants.TILE_SIZE][(entityX + offset) / Constants.TILE_SIZE].changeStamp = Utility.generateRandom(Constants.TILE_CHANGE_FROM_MOWED_1_MIN, Constants.TILE_CHANGE_FROM_MOWED_1_MAX) + gp.frameCount;
					tileM.tileNums[entityY / Constants.TILE_SIZE][(entityX + offset) / Constants.TILE_SIZE] = 21;
					
					tileM.tile[entityY / Constants.TILE_SIZE + 1][(entityX + offset) / Constants.TILE_SIZE].isFlower = false;
					tileM.tile[entityY / Constants.TILE_SIZE + 1][(entityX + offset) / Constants.TILE_SIZE].pickable = false;
					tileM.tile[(entityY + offset) / Constants.TILE_SIZE][entityX / Constants.TILE_SIZE].entityStamp = 0;
					tileM.tile[entityY / Constants.TILE_SIZE + 1][(entityX + offset) / Constants.TILE_SIZE].changeStamp = Utility.generateRandom(Constants.TILE_CHANGE_FROM_MOWED_1_MIN, Constants.TILE_CHANGE_FROM_MOWED_1_MAX) + gp.frameCount;
					tileM.tileNums[entityY / Constants.TILE_SIZE + 1][(entityX + offset) / Constants.TILE_SIZE] = 21;
				}
			}
		}
		
	}
	
}





