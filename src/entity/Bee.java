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

public class Bee extends Entity{
	
	public int endDestX, endDestY;
	public int tempDestX, tempDestY; // the point on the perpendisular line the bee is traveling towards
	public int perpLineX1, perpLineY1, perpLineX2, perpLineY2; // the points of the perpendicular line
	public int pickStamp; // frame to stop picking
	public BufferedImage imageBeforeSwat;
	public BufferedImage image;
	
	int randLength;
	
	double offset;
	
	public Bee(GamePanel gp, MouseHandler mouseH, Player player, TileManager tileM, int destX, int destY) {
		
		super(gp, mouseH, player, tileM, destX, destY);
		randLength = Utility.generateRandom(Constants.BEE_PERP_LINE_LENGTH_MIN, Constants.BEE_PERP_LINE_LENGTH_MAX);
		setSpawnLocation();
		state = "up";
		pickStamp = 0;
		offset = 2 * Math.PI * ((double)Utility.generateRandom(1, Constants.FPS)) / Constants.FPS;
		
		
		setSpeed();
		
	}
	
	@Override
	void setSpawnLocation() {
		// TODO Auto-generated method stub
		
		do {
			int rand = Utility.generateRandom(1, 4);
			int randX = Utility.generateRandom(0, Constants.MAX_SCREEN_COL - 1) * Constants.TILE_SIZE;
			int randY = Utility.generateRandom(0, Constants.MAX_SCREEN_ROW - 1) * Constants.TILE_SIZE;
			
			switch(rand) {
				case 1: // above flower
					spawnX = randX;
					spawnY = 0 - Constants.TILE_SIZE;
					endDestX = spawnX;
					endDestY = spawnY - Constants.TILE_SIZE;
					break;
				case 2: // below flower
					spawnX = randX;
					spawnY = Constants.SCREEN_HEIGHT + Constants.TILE_SIZE;
					endDestX = spawnX;
					endDestY = spawnY + Constants.TILE_SIZE;
					break;
				case 3: // left of flower
					spawnX = 0 - Constants.TILE_SIZE;
					spawnY = randY;
					endDestX = spawnX - Constants.TILE_SIZE;
					endDestY = spawnY;
					break;
				case 4: // right of flower
					spawnX = Constants.SCREEN_WIDTH + Constants.TILE_SIZE;
					spawnY = randY;
					endDestX = spawnX + Constants.TILE_SIZE;
					endDestY = spawnY;
					break;
			}
			entityX = spawnX;
			entityY = spawnY;
			
			//int[] temp = gp.calculatePerpendicularPoints(spawnX, spawnY, destX, destY, 1000);
			int[] temp = Utility.calculatePerpendicularPoints(endDestX, endDestY, spawnX, spawnY, randLength);
			perpLineX1 = temp[0];
			perpLineY1 = temp[1];
			perpLineX2 = temp[2];
			perpLineY2 = temp[3];
			//System.out.println("spawn: " + spawnX + " " + spawnY + " dest: " + destX + " " + destY + " line: " + perpLineX1 + " " + perpLineY1 + ", " + perpLineX2 + " " + perpLineY2);
		} while (Math.abs(Math.sqrt(Math.pow(destX - spawnX, 2) + Math.pow(destY - spawnY, 2))) <= Constants.BEE_MIN_DISTANCE_FROM_TARGET * Constants.TILE_SIZE); // bees cannot spawn 10 tiles from their destination flower
	}
	
	public void setSpeed() {
		
		double rand = Utility.generateRandom(Constants.BEE_SPEED_MIN, Constants.BEE_SPEED_MAX) / 2;
		
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
			int[] temp = Utility.extrapolatePointByDistance(player.playerX, player.playerY, entityX, entityY, Constants.BEE_KNOCKBACK_DISTANCE);
			swatX = temp[0];
			swatY = temp[1];
			swatStamp = gp.frameCount + Constants.BEE_KNOCKBACK_SPEED;
			swat = false;
		}
	}

	@Override
	void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
		int[] imageOffset = setImage();
		
		if(image != null) {
			if(state == "swat") {
				BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
				RescaleOp op = new RescaleOp(Constants.IMAGE_HIT_BRIGHTNESS, 0, null);
				op.filter(image, temp);
				g2.drawImage(temp, entityX + imageOffset[0], entityY - imageOffset[1], Constants.TILE_SIZE, Constants.TILE_SIZE, null);
			} else {
				g2.drawImage(image, entityX + imageOffset[0], entityY - imageOffset[1], Constants.TILE_SIZE, Constants.TILE_SIZE, null);
			}
		}
		
	}
	
	public int[] setImage() {
		
		BufferedImage image = null;
		
		int imageOffsetX = 0;
		int imageOffsetY = 0;
		int animationFrame;
		int interval = gp.frameCount % (Constants.BEE_ANIMATION_FRAME_LENGTH * 3);
		
		if(interval <= Constants.BEE_ANIMATION_FRAME_LENGTH) {
			animationFrame = 1;
		} else if(interval > Constants.BEE_ANIMATION_FRAME_LENGTH && interval <= Constants.BEE_ANIMATION_FRAME_LENGTH * 2) {
			animationFrame = 2;
		} else {
			animationFrame = 3;
		}
		
		switch(state) {
		case "left":
			
			switch(animationFrame) {
				case 1:
					image = ImageManager.bee_left_1;
					break;
				case 2:
					image = ImageManager.bee_left_2;
					break;
				case 3:
					image = ImageManager.bee_left_3;
					break;
			}
			
			break;
		case "right":

			switch(animationFrame) {
				case 1:
					image = ImageManager.bee_right_1;
					break;
				case 2:
					image = ImageManager.bee_right_2;
					break;
				case 3:
					image = ImageManager.bee_right_3;
					break;
			}
			
			break;
		case "up leftwing":

			switch(animationFrame) {
				case 1:
					image = ImageManager.bee_up_leftwing_1;
					break;
				case 2:
					image = ImageManager.bee_up_leftwing_2;
					break;
				case 3:
					image = ImageManager.bee_up_leftwing_3;
					break;
			}
			
			break;
		case "up rightwing":

			switch(animationFrame) {
				case 1:
					image = ImageManager.bee_up_rightwing_1;
					break;
				case 2:
					image = ImageManager.bee_up_rightwing_2;
					break;
				case 3:
					image = ImageManager.bee_up_rightwing_3;
					break;
			}
			
			break;
		case "down leftwing":

			switch(animationFrame) {
				case 1:
					image = ImageManager.bee_down_leftwing_1;
					break;
				case 2:
					image = ImageManager.bee_down_leftwing_2;
					break;
				case 3:
					image = ImageManager.bee_down_leftwing_3;
					break;
			}
			
			break;
		case "down rightwing":

			switch(animationFrame) {
				case 1:
					image = ImageManager.bee_down_rightwing_1;
					break;
				case 2:
					image = ImageManager.bee_down_rightwing_2;
					break;
				case 3:
					image = ImageManager.bee_down_rightwing_3;
					break;
			}
			
			break;
		case "up left":

			switch(animationFrame) {
				case 1:
					image = ImageManager.bee_up_left_1;
					break;
				case 2:
					image = ImageManager.bee_up_left_2;
					break;
				case 3:
					image = ImageManager.bee_up_left_3;
					break;
			}
			
			break;
		case "down left":

			switch(animationFrame) {
				case 1:
					image = ImageManager.bee_down_left_1;
					break;
				case 2:
					image = ImageManager.bee_down_left_2;
					break;
				case 3:
					image = ImageManager.bee_down_left_3;
					break;
			}
			
			break;
		case "up right":

			switch(animationFrame) {
				case 1:
					image = ImageManager.bee_up_right_1;
					break;
				case 2:
					image = ImageManager.bee_up_right_2;
					break;
				case 3:
					image = ImageManager.bee_up_right_3;
					break;
			}
			
			break;
		case "down right":

			switch(animationFrame) {
				case 1:
					image = ImageManager.bee_down_right_1;
					break;
				case 2:
					image = ImageManager.bee_down_right_2;
					break;
				case 3:
					image = ImageManager.bee_down_right_3;
					break;
			}
			
			break;
			case "picking":
				
				if(spawnX > entityX) {
					imageOffsetX = Constants.BEE_PICK_OFFSET_X;
					imageOffsetY = Constants.BEE_PICK_OFFSET_Y;
				} else {
					imageOffsetX = -Constants.BEE_PICK_OFFSET_X;
					imageOffsetY = Constants.BEE_PICK_OFFSET_Y;
				}
				
				if(pickStamp - gp.frameCount > 175) {
					if(spawnX > entityX) {
						image = ImageManager.bee_up_left_1;
					} else {
						image = ImageManager.bee_up_right_1;
					}
				} else if(pickStamp - gp.frameCount > 170 && pickStamp - gp.frameCount <= 175) {
					if(spawnX > entityX) {
						image = ImageManager.bee_up_left_2;
					} else {
						image = ImageManager.bee_up_right_2;
					}
				} else if(pickStamp - gp.frameCount > 10 && pickStamp - gp.frameCount <= 170) {
					if(spawnX > entityX) {
						image = ImageManager.bee_up_left_3;
					} else {
						image = ImageManager.bee_up_right_3;
					}
				} else if(pickStamp - gp.frameCount > 5 && pickStamp - gp.frameCount <= 10) {
					if(spawnX > entityX) {
						image = ImageManager.bee_up_left_2;
					} else {
						image = ImageManager.bee_up_right_2;
					}
				} else {
					if(spawnX > entityX) {
						image = ImageManager.bee_up_left_1;
					} else {
						image = ImageManager.bee_up_right_1;
					}
				}
				
				break;
			case "swat":
				image = imageBeforeSwat;
				break;
		}
		this.image = image;
		return new int[] {imageOffsetX, imageOffsetY};
	}

	@Override
	void move() {
		// TODO Auto-generated method stub
		
		String[] temp;
		if(state == "swat") {
			
			double swatSpeed = Utility.calculateKnockbackSpeed(entityX, entityY, swatX, swatY, gp.frameCount, swatStamp);
			temp = Utility.homeTowardDest(entityX, entityY, swatX, swatY, swatSpeed);
			entityX = (int) Math.round(entityX + Double.parseDouble(temp[1]));
			entityY = (int) Math.round(entityY + Double.parseDouble(temp[2]));
			if(entityX == swatX && entityY == swatY) {
				state = "up";
			}
		} else {
			if(state != "picking" && state != "despawn") {
				if(gp.frameCount % 2 == 0) {
					
					if(flee) {
						temp = Utility.homeTowardDest(entityX, entityY, destX, destY, speed);
					} else {
						int[] tempInt = Utility.findSineDest(perpLineX1, perpLineY1, perpLineX2, perpLineY2, entityX, entityY, destX, destY, spawnX, spawnY, offset, gp.frameCount);
						tempDestX = tempInt[0];
						tempDestY = tempInt[1];
						
						temp = Utility.homeTowardDest(entityX, entityY, tempDestX, tempDestY, speed);
					}
					
					
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
					} else if(Math.abs(slope) <= Double.POSITIVE_INFINITY) {
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
					} else { // these look really bad so im not gonna use them lol
						if(Integer.signum((int) distanceX) == 1) {
							if(Integer.signum((int) distanceY) == 1) {
								state = "down leftwing";
							} else {
								state = "up rightwing";
							}
						} else {
							if(Integer.signum((int) distanceY) == 1) {
								state = "down rightwing";
							} else {
								state = "up leftwing";
							}
						}
					}
					
					entityX = (int) Math.round(entityX + Double.parseDouble(temp[1]));
					entityY = (int) Math.round(entityY + Double.parseDouble(temp[2]));
				}
		
			}
			
		}
		
		
	}
	
	public void checkPicking() {
		
		int entityTileX = entityX / Constants.TILE_SIZE;
		int entityTileY = entityY / Constants.TILE_SIZE;
		
		if(flee) {
			if(state == "picking") {
				state = "up";
			}
		} else {
			int flowerStatus = onFlower();
			
			if(flowerStatus == 1) {
				state = "picking";
				pickStamp = gp.frameCount + Constants.BEE_PICK_TIME;
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
			}
			if(entityX >= destX - 1 && entityX <= destX + 1 && entityY >= destY - 1 && entityY <= destY + 1) {
				if(tileM.tile[destTileY][destTileX].isFlower == false) {
					return 3;
				}
			}
		}
		return 0;
	}
	
	public void checkFlower() {
		
		int destTileX = destX / Constants.TILE_SIZE;
		int destTileY = destY / Constants.TILE_SIZE;
		
		if(!((destTileX < 0 || destTileX > Constants.MAX_SCREEN_COL - 1) || (destTileY < 0 || destTileY > Constants.MAX_SCREEN_ROW - 1))) {
			if(tileM.tile[destTileY][destTileX].isFlower == false) {
				int[] temp = Utility.calculatePerpendicularPoints(spawnX, spawnY, destX, destY, randLength / 10);
				perpLineX1 = temp[0];
				perpLineY1 = temp[1];
				perpLineX2 = temp[2];
				perpLineY2 = temp[3];
				tempDestX = destX;
				tempDestY = destY;
				destX = endDestX;
				destY = endDestY;
			}
		}
		
	}
	
	
	
}






