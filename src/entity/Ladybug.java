package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.MouseHandler;
import tile.TileManager;

public class Ladybug extends Entity{
	
	public int spawnStamp;
	public int leaveStamp;
	
	public BufferedImage imageBeforeSwat;
	public BufferedImage image;

	static BufferedImage ladybug_down_1, ladybug_down_2, ladybug_down_3, ladybug_down_left_1, ladybug_down_left_2, 
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
		
		int rand = gp.generateRandom(1, 3);
		int randX = gp.generateRandom(0, gp.maxScreenCol - 1) * gp.tileSize;
		int randY = gp.generateRandom(gp.skyLevel + 1, gp.maxScreenRow - 1) * gp.tileSize;
		
		switch(rand) {
			case 1: // below screen
				spawnX = randX;
				spawnY = gp.screenHeight + gp.tileSize;
				break;
			case 2: // left of screen
				spawnX = 0 - gp.tileSize;
				spawnY = randY;
				break;
			case 3: // right of screen
				spawnX = gp.screenWidth + gp.tileSize;
				spawnY = randY;
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
		move();
		
		if(swat) {
			imageBeforeSwat = image;
			state = "swat";
			int[] temp = gp.extrapolatePointByDistance(player.playerX, player.playerY, entityX, entityY, 96);
			swatX = temp[0];
			swatY = temp[1];
			swatStamp = gp.frameCount + 15;
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
			RescaleOp op = new RescaleOp(20f, 0, null);
			op.filter(image, temp);
			g2.drawImage(temp, entityX, entityY - imageOffset, gp.tileSize, gp.tileSize, null);
		} else {
			g2.drawImage(image, entityX, entityY - imageOffset, gp.tileSize, gp.tileSize, null);
		}
		
	}
	
	public void setImage() {
		BufferedImage image = null;
		int animationFrame;
		int frameLength = 8; // how many frames each animationFrame lasts
		int interval = gp.frameCount % (frameLength * 4);
		
		if(interval <= frameLength || (interval > frameLength * 2 && interval <= frameLength * 3)) {
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
						image = ladybug_left_1;
						break;
					case 2:
						image = ladybug_left_2;
						break;
					case 3:
						image = ladybug_left_3;
						break;
				}
				
				break;
			case "right":

				switch(animationFrame) {
					case 1:
						image = ladybug_right_1;
						break;
					case 2:
						image = ladybug_right_2;
						break;
					case 3:
						image = ladybug_right_3;
						break;
				}
				
				break;
			case "up":

				switch(animationFrame) {
					case 1:
						image = ladybug_up_1;
						break;
					case 2:
						image = ladybug_up_2;
						break;
					case 3:
						image = ladybug_up_3;
						break;
				}
				
				break;
			case "down":

				switch(animationFrame) {
					case 1:
						image = ladybug_down_1;
						break;
					case 2:
						image = ladybug_down_2;
						break;
					case 3:
						image = ladybug_down_3;
						break;
				}
				
				break;
			case "up left":

				switch(animationFrame) {
					case 1:
						image = ladybug_up_left_1;
						break;
					case 2:
						image = ladybug_up_left_2;
						break;
					case 3:
						image = ladybug_up_left_3;
						break;
				}
				
				break;
			case "down left":

				switch(animationFrame) {
					case 1:
						image = ladybug_down_left_1;
						break;
					case 2:
						image = ladybug_down_left_2;
						break;
					case 3:
						image = ladybug_down_left_3;
						break;
				}
				
				break;
			case "up right":

				switch(animationFrame) {
					case 1:
						image = ladybug_up_right_1;
						break;
					case 2:
						image = ladybug_up_right_2;
						break;
					case 3:
						image = ladybug_up_right_3;
						break;
				}
				
				break;
			case "down right":

				switch(animationFrame) {
					case 1:
						image = ladybug_down_right_1;
						break;
					case 2:
						image = ladybug_down_right_2;
						break;
					case 3:
						image = ladybug_down_right_3;
						break;
				}
				
				break;
			case "swat":
				image = imageBeforeSwat;
				break;
			case "idle":
				image = ladybug_up_1;
				break;
		}
		this.image = image;
	}

	@Override
	void move() {
		// TODO Auto-generated method stub
		if(state == "swat") {
			
			double swatSpeed = gp.calculateKnockbackSpeed(entityX, entityY, swatX, swatY, gp.frameCount, swatStamp);
			String[] temp = gp.homeTowardDest(entityX, entityY, swatX, swatY, swatSpeed);
			
			keepInBounds();
			
			entityX = (int) Math.round(entityX + Double.parseDouble(temp[1]));
			entityY = (int) Math.round(entityY + Double.parseDouble(temp[2]));
			if(entityX == swatX && entityY == swatY) {
				state = "up";
			}
		} else {
			if(state != "despawn") {
				if(gp.frameCount % 2 == 0) {
					String[] temp = gp.homeTowardDest(entityX, entityY, destX, destY, speed);
					
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
			int altSignum = (gp.generateRandom(0, 1) * 2) - 1;
			double inertia = 1.2;
			
			if(collXSignum == 0) {
				collXSignum = altSignum;
			}
			if(swatY < gp.skyLevel*gp.tileSize) {
				swatX = swatX + (int)((collXSignum * (swatY - gp.skyLevel*gp.tileSize)) / inertia);
				swatY = gp.skyLevel*gp.tileSize;
			}
	}
}






