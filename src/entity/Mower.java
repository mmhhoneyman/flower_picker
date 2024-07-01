package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.MouseHandler;
import tile.TileManager;

public class Mower extends Entity{
	
	public int endDestX, endDestY;
	
	public int turns;
	
	public int spawnStamp;
	public int leaveStamp;
	
	public BufferedImage image;

	static BufferedImage mower_down_1, mower_down_2, mower_down_3, mower_left_1, mower_left_2, 
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
	
	public Mower(GamePanel gp, MouseHandler mouseH, Player player, TileManager tileM, int destX, int destY) {
		
		super(gp, mouseH, player, tileM, destX, destY);
		setSpawnLocation();
		state = "up";
		spawnStamp = gp.frameCount;
		leaveStamp = spawnStamp + 900;
		
		endDestX = -1;
		endDestY = -1;
		
		turns = gp.generateRandom(1, 10);
		
		setSpeed();
		
	}
	
	@Override
	void setSpawnLocation() {
		// TODO Auto-generated method stub
		
		int rand = gp.generateRandom(1, 3);
		int randX = gp.generateRandom(0, gp.maxScreenCol - 2) * gp.tileSize;
		int randY = gp.generateRandom(gp.skyLevel + 1, gp.maxScreenRow - 2) * gp.tileSize;
		int randDestX = gp.generateRandom(0, gp.maxScreenCol - 2) * gp.tileSize;
		int randDestY = gp.generateRandom(gp.skyLevel, gp.maxScreenRow - 2) * gp.tileSize;
		
		switch(rand) {
			case 1: // below screen
				spawnX = randX;
				spawnY = gp.screenHeight + gp.tileSize;
				destX = spawnX;
				destY = randDestY;
				break;
			case 2: // left of screen
				spawnX = 0 - gp.tileSize * 2;
				spawnY = randY;
				destX = randDestX;
				destY = spawnY;
				break;
			case 3: // right of screen
				spawnX = gp.screenWidth + gp.tileSize;
				spawnY = randY;
				destX = randDestX;
				destY = spawnY;
				break;
		}
		entityX = spawnX;
		entityY = spawnY;
		
	}
	
	public void setSpeed() {
		
		double rand = gp.generateRandom(2, 2) / 2;
		
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
		
		int imageOffset = (gp.frameCount % 8 - 4) / 4;

		setImage();
		
		if(state == "up" || state == "down") {
			g2.drawImage(image, entityX + imageOffset, entityY, gp.tileSize * 2, gp.tileSize * 2, null);
		} else {
			g2.drawImage(image, entityX, entityY + imageOffset, gp.tileSize * 2, gp.tileSize * 2, null);
		}
		
	}
	
	public void setImage() {
		BufferedImage image = null;
		int animationFrame;
		int frameLength = 8; // how many frames each animationFrame lasts
		
		int interval = gp.frameCount % (frameLength * 3);
		
		if(interval <= frameLength) {
			animationFrame = 1;
		} else if(interval > frameLength && interval <= frameLength * 2) {
			animationFrame = 2;
		} else {
			animationFrame = 3;
		}
		
		switch(state) {
			case "left":
				
				switch(animationFrame) {
					case 1:
						image = mower_left_1;
						break;
					case 2:
						image = mower_left_2;
						break;
					case 3:
						image = mower_left_3;
						break;
				}
				
				break;
			case "right":

				switch(animationFrame) {
					case 1:
						image = mower_right_1;
						break;
					case 2:
						image = mower_right_2;
						break;
					case 3:
						image = mower_right_3;
						break;
				}
				
				break;
			case "up":

				switch(animationFrame) {
					case 1:
						image = mower_up_1;
						break;
					case 2:
						image = mower_up_2;
						break;
					case 3:
						image = mower_up_3;
						break;
				}
				
				break;
			case "down":

				switch(animationFrame) {
					case 1:
						image = mower_down_1;
						break;
					case 2:
						image = mower_down_2;
						break;
					case 3:
						image = mower_down_3;
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
						int rand = gp.generateRandom(1, 4);
						
						int offsetX;
						int offsetY;
						if(entityX / gp.tileSize - 1 < 0 || entityX / gp.tileSize + 1 > gp.maxScreenCol - 2) {
							offsetX = 0;
						} else {
							offsetX = 1;
						}
						if(entityY / gp.tileSize + 1 > gp.maxScreenRow - 2 || gp.skyLevel > entityY / gp.tileSize - 1) {
							offsetY = 0;
						} else {
							offsetY = 1;
						}
						
						switch(rand) {
							case 1: // down
								testState = "vertical";
								destX = entityX;
								destY = gp.generateRandom(entityY / gp.tileSize + offsetY, gp.maxScreenRow - 2) * gp.tileSize;
								break;
							case 2: // left
								testState = "horizontal";
								destX = gp.generateRandom(0, entityX / gp.tileSize - offsetX) * gp.tileSize;
								destY = entityY;
								break;
							case 3: // right
								testState = "horizontal";
								destX = gp.generateRandom(entityX / gp.tileSize + offsetX, gp.maxScreenCol - 2) * gp.tileSize;
								destY = entityY;
								break;
							case 4: // up
								testState = "vertical";
								destX = entityX;
								destY = gp.generateRandom(gp.skyLevel, entityY / gp.tileSize - offsetY) * gp.tileSize;;
								break;
						}
					} while(testState == testState2);
					turns --;
				} else {
					int rand = gp.generateRandom(1, 3);
					switch(rand) {
						case 1: // down
							destX = entityX;
							destY = (gp.maxScreenRow + 1) * gp.tileSize;
							break;
						case 2: // left
							destX = -2 * gp.tileSize;
							destY = entityY;
							break;
						case 3: // right
							destX = (gp.maxScreenCol + 1) * gp.tileSize;
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
		
		if(entityX >= 0 - gp.tileSize && entityX <= gp.screenWidth - gp.tileSize && entityY >= 0 && entityY <= gp.screenHeight - gp.tileSize) {
			if(state == "up" || state == "down") {
				if((entityY + offset + 1) % gp.tileSize >= 0 && (entityY + offset + 1) % gp.tileSize <= 2) {
					tileM.tile[(entityY + offset) / gp.tileSize][entityX / gp.tileSize].isFlower = false;
					tileM.tile[(entityY + offset) / gp.tileSize][entityX / gp.tileSize].pickable = false;
					tileM.tile[(entityY + offset) / gp.tileSize][entityX / gp.tileSize].changeStamp = gp.generateRandom(180, 240) + gp.frameCount;
					tileM.tileNums[(entityY + offset) / gp.tileSize][entityX / gp.tileSize] = 21;
					
					tileM.tile[(entityY + offset) / gp.tileSize][entityX / gp.tileSize + 1].isFlower = false;
					tileM.tile[(entityY + offset) / gp.tileSize][entityX / gp.tileSize + 1].pickable = false;
					tileM.tile[(entityY + offset) / gp.tileSize][entityX / gp.tileSize + 1].changeStamp = gp.generateRandom(180, 240) + gp.frameCount;
					tileM.tileNums[(entityY + offset) / gp.tileSize][entityX / gp.tileSize + 1] = 21;
				}
			} else {
				if((entityX + offset + 1) % gp.tileSize >= 0 && (entityX + offset + 1) % gp.tileSize <= 2) {
					tileM.tile[entityY / gp.tileSize][(entityX + offset) / gp.tileSize].isFlower = false;
					tileM.tile[entityY / gp.tileSize][(entityX + offset) / gp.tileSize].pickable = false;
					tileM.tile[entityY / gp.tileSize][(entityX + offset) / gp.tileSize].changeStamp = gp.generateRandom(180, 240) + gp.frameCount;
					tileM.tileNums[entityY / gp.tileSize][(entityX + offset) / gp.tileSize] = 21;
					
					tileM.tile[entityY / gp.tileSize + 1][(entityX + offset) / gp.tileSize].isFlower = false;
					tileM.tile[entityY / gp.tileSize + 1][(entityX + offset) / gp.tileSize].pickable = false;
					tileM.tile[entityY / gp.tileSize + 1][(entityX + offset) / gp.tileSize].changeStamp = gp.generateRandom(180, 240) + gp.frameCount;
					tileM.tileNums[entityY / gp.tileSize + 1][(entityX + offset) / gp.tileSize] = 21;
				}
			}
		}
		
	}
	
}





