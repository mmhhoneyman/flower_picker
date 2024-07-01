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

public class Ladybug extends Entity{
	
	public int spawnStamp;
	public int leaveStamp;
	
	public BufferedImage imageBeforeSwat;
	public BufferedImage image;
	
	public Ladybug(GamePanel gp, MouseHandler mouseH, Player player, TileManager tileM, int destX, int destY) {
		
		super(gp, mouseH, player, tileM, destX, destY);
		setSpawnLocation();
		state = "up";
		spawnStamp = gp.frameCount;
		leaveStamp = spawnStamp + 900;
		
		setSpeed();
		
	}
	
	@Override
	void setSpawnLocation() {
		// TODO Auto-generated method stub
		
		int rand = Utility.generateRandom(1, 3);
		int randX = Utility.generateRandom(0, Constants.MAX_SCREEN_COL - 1) * Constants.TILE_SIZE;
		int randY = Utility.generateRandom(Constants.SKY_LEVEL + 1, Constants.MAX_SCREEN_ROW - 1) * Constants.TILE_SIZE;
		
		switch(rand) {
			case 1: // below screen
				spawnX = randX;
				spawnY = Constants.SCREEN_HEIGHT + Constants.TILE_SIZE;
				break;
			case 2: // left of screen
				spawnX = 0 - Constants.TILE_SIZE;
				spawnY = randY;
				break;
			case 3: // right of screen
				spawnX = Constants.SCREEN_WIDTH + Constants.TILE_SIZE;
				spawnY = randY;
				break;
		}
		entityX = spawnX;
		entityY = spawnY;
		
	}
	
	public void setSpeed() {
		
		double rand = Utility.generateRandom(Constants.LADYBUG_SPEED_MIN, Constants.LADYBUG_SPEED_MAX) / 2;
		
		this.speed = rand * 2;
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
		refreshDest();
		move();
		
		if(swat) {
			imageBeforeSwat = image;
			state = "swat";
			int[] temp = Utility.extrapolatePointByDistance(player.playerX, player.playerY, entityX, entityY, Constants.LADYBUG_KNOCKBACK_DISTANCE);
			swatX = temp[0];
			swatY = temp[1];
			swatStamp = gp.frameCount + Constants.LADYBUG_KNOCKBACK_SPEED;
			swat = false;
		}
	}

	@Override
	void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
		int imageOffset = 0;

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
		int interval = gp.frameCount % (Constants.LADYBUG_ANIMATION_FRAME_LENGTH * 4);
		
		if(interval <= Constants.LADYBUG_ANIMATION_FRAME_LENGTH || (interval > Constants.LADYBUG_ANIMATION_FRAME_LENGTH * 2 && interval <= Constants.LADYBUG_ANIMATION_FRAME_LENGTH * 3)) {
			animationFrame = 1;
		} else if(interval > Constants.LADYBUG_ANIMATION_FRAME_LENGTH && interval <= Constants.LADYBUG_ANIMATION_FRAME_LENGTH * 2) {
			animationFrame = 2;
		} else {
			animationFrame = 3;
		}
		
		switch(state) {
			case "left":
				
				switch(animationFrame) {
					case 1:
						image = ImageManager.ladybug_left_1;
						break;
					case 2:
						image = ImageManager.ladybug_left_2;
						break;
					case 3:
						image = ImageManager.ladybug_left_3;
						break;
				}
				
				break;
			case "right":

				switch(animationFrame) {
					case 1:
						image = ImageManager.ladybug_right_1;
						break;
					case 2:
						image = ImageManager.ladybug_right_2;
						break;
					case 3:
						image = ImageManager.ladybug_right_3;
						break;
				}
				
				break;
			case "up":

				switch(animationFrame) {
					case 1:
						image = ImageManager.ladybug_up_1;
						break;
					case 2:
						image = ImageManager.ladybug_up_2;
						break;
					case 3:
						image = ImageManager.ladybug_up_3;
						break;
				}
				
				break;
			case "down":

				switch(animationFrame) {
					case 1:
						image = ImageManager.ladybug_down_1;
						break;
					case 2:
						image = ImageManager.ladybug_down_2;
						break;
					case 3:
						image = ImageManager.ladybug_down_3;
						break;
				}
				
				break;
			case "up left":

				switch(animationFrame) {
					case 1:
						image = ImageManager.ladybug_up_left_1;
						break;
					case 2:
						image = ImageManager.ladybug_up_left_2;
						break;
					case 3:
						image = ImageManager.ladybug_up_left_3;
						break;
				}
				
				break;
			case "down left":

				switch(animationFrame) {
					case 1:
						image = ImageManager.ladybug_down_left_1;
						break;
					case 2:
						image = ImageManager.ladybug_down_left_2;
						break;
					case 3:
						image = ImageManager.ladybug_down_left_3;
						break;
				}
				
				break;
			case "up right":

				switch(animationFrame) {
					case 1:
						image = ImageManager.ladybug_up_right_1;
						break;
					case 2:
						image = ImageManager.ladybug_up_right_2;
						break;
					case 3:
						image = ImageManager.ladybug_up_right_3;
						break;
				}
				
				break;
			case "down right":

				switch(animationFrame) {
					case 1:
						image = ImageManager.ladybug_down_right_1;
						break;
					case 2:
						image = ImageManager.ladybug_down_right_2;
						break;
					case 3:
						image = ImageManager.ladybug_down_right_3;
						break;
				}
				
				break;
			case "swat":
				image = imageBeforeSwat;
				break;
			case "idle":
				image = ImageManager.ladybug_up_1;
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
			
			keepInBounds();
			
			entityX = (int) Math.round(entityX + Double.parseDouble(temp[1]));
			entityY = (int) Math.round(entityY + Double.parseDouble(temp[2]));
			if(entityX == swatX && entityY == swatY) {
				state = "up";
			}
		} else {
			if(state != "despawn") {
				if(gp.frameCount % 2 == 0) {
					String[] temp = Utility.homeTowardDest(entityX, entityY, destX, destY, speed);
					
					double distanceX = destX - entityX;
					double distanceY = destY - entityY;
					double slope;
					try {
						slope = distanceY / distanceX;
					} catch(ArithmeticException e) {
						slope = Double.POSITIVE_INFINITY;
					}
					
					if(Math.abs(slope) < 0.5) {
						if(Integer.signum((int) distanceX) == 1) {
							state = "right";
						} else {
							state = "left";
						}
					} else if(Math.abs(slope) < 3) {
						if(Integer.signum((int) distanceX) == 1) {
							if(Integer.signum((int) distanceY) == 1) {
								state = "down right";
							} else {
								state = "up right";
							}
						} else {
							if(Integer.signum((int) distanceY) == 1) {
								state = "down left";
							} else {
								state = "up left";
							}
						}
					} else {
						if(Integer.signum((int) distanceY) == 1) {
							state = "down";
						} else {
							state = "up";
						}
					}
					
					entityX = (int) Math.round(entityX + Double.parseDouble(temp[1]));
					entityY = (int) Math.round(entityY + Double.parseDouble(temp[2]));
				}
		
			}
			
		}
		
		
	}
	
	public void refreshDest() {
		if(!flee) {
			if(gp.frameCount < leaveStamp) {
				destX = player.playerX;
				destY = player.playerY;
			} else {
				destX = spawnX;
				destY = spawnY;
			}
			if((destX == spawnX && entityX == destX) && (destY == spawnY && entityY == destY)){
				state = "despawn";
			}
		}
		
	}
	
public void keepInBounds() {
			
			int collXSignum = Integer.signum(entityX - swatX);
			int altSignum = (Utility.generateRandom(0, 1) * 2) - 1;
			double inertia = Constants.LADYBUG_KNOCKBACK_INERTIA;
			
			if(collXSignum == 0) {
				collXSignum = altSignum;
			}
			if(swatY < Constants.SKY_LEVEL*Constants.TILE_SIZE) {
				swatX = swatX + (int)((collXSignum * (swatY - Constants.SKY_LEVEL*Constants.TILE_SIZE)) / inertia);
				swatY = Constants.SKY_LEVEL*Constants.TILE_SIZE;
			}
	}
}






