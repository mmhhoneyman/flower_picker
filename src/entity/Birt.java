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

public class Birt extends Entity{
	
	public int endDestX, endDestY;
	public BufferedImage imageBeforeSwat;
	public BufferedImage image;
	
	public Birt(GamePanel gp, MouseHandler mouseH, Player player, TileManager tileM, int destX, int destY) {
		
		super(gp, mouseH, player, tileM, destX, destY);
		setSpawnLocation();
		state = "moving";
		
		setSpeed();
		
	}
	
	@Override
	void setSpawnLocation() {
		// TODO Auto-generated method stub
		
		int rand = Utility.generateRandom(1, 4);
		
		switch(rand) {
			case 1: // left of screen, y1
				spawnX = 0 - Constants.TILE_SIZE;
				spawnY = 0;
				endDestX = Constants.SCREEN_WIDTH + Constants.TILE_SIZE;
				endDestY = 0;
				break;
			case 2: // right of screen, y1
				spawnX = Constants.SCREEN_WIDTH + Constants.TILE_SIZE;
				spawnY = 0;
				endDestX = 0 - Constants.TILE_SIZE;
				endDestY = 0;
				break;
			case 3: // left of screen, y2
				spawnX = 0 - Constants.TILE_SIZE;
				spawnY = Constants.TILE_SIZE;
				endDestX = Constants.SCREEN_WIDTH + Constants.TILE_SIZE;
				endDestY = Constants.TILE_SIZE;
				break;
			case 4: // right of screen, y2
				spawnX = Constants.SCREEN_WIDTH + Constants.TILE_SIZE;
				spawnY = Constants.TILE_SIZE;
				endDestX = 0 - Constants.TILE_SIZE;
				endDestY = Constants.TILE_SIZE;
				break;
		}
		entityX = spawnX;
		entityY = spawnY;
		destX = endDestX;
		destY = endDestY;
		
	}
	
	public void setSpeed() {
		
		double rand = Utility.generateRandom(Constants.BIRT_SPEED_MIN, Constants.BIRT_SPEED_MAX) / 2;
		
		this.speed = rand * 6;
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
		move();
		
		if(swat) {
			imageBeforeSwat = image;
			state = "swat";
			int[] temp = Utility.extrapolatePointByDistance(player.playerX, player.playerY, entityX, entityY, Constants.BIRT_KNOCKBACK_DISTANCE);
			swatX = temp[0];
			swatY = temp[1];
			endDestX = swatX;
			endDestY = swatY;
			destX = swatX;
			destY = swatY;
			swatStamp = gp.frameCount + Constants.BIRT_KNOCKBACK_SPEED;
			swat = false;
		}
	}

	@Override
	void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		setImage();
		
		if(state == "swat") {
			BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
			RescaleOp op = new RescaleOp(Constants.IMAGE_HIT_BRIGHTNESS, 0, null);
			op.filter(image, temp);
			g2.drawImage(temp, entityX, entityY, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
		} else {
			g2.drawImage(image, entityX, entityY, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
		}
		
	}
	
	public void setImage() {
		BufferedImage image = null;
		int animationFrame;
		int frameLength = Constants.BIRT_ANIMATION_FRAME_LENGTH;
		int interval = gp.frameCount % (frameLength * 7);
		
		if(interval <= frameLength) {
			animationFrame = 1;
		} else if(interval > frameLength && interval <= frameLength * 2) {
			animationFrame = 2;
		} else if((interval > frameLength * 2 && interval <= frameLength * 3)) {
			animationFrame = 3;
		} else if((interval > frameLength * 3 && interval <= frameLength * 4)) {
			animationFrame = 4;
		} else if((interval > frameLength * 4 && interval <= frameLength * 5)) {
			animationFrame = 5;
		} else if((interval > frameLength * 5 && interval <= frameLength * 6)) {
			animationFrame = 6;
		} else {
			animationFrame = 7;
		}
		
		switch(state) {
			default:
				
				switch(animationFrame) {
					case 1:
						image = ImageManager.birt_1;
						break;
					case 2:
						image = ImageManager.birt_2;
						break;
					case 3:
						image = ImageManager.birt_3;
						break;
					case 4:
						image = ImageManager.birt_4;
						break;
					case 5:
						image = ImageManager.birt_5;
						break;
					case 6:
						image = ImageManager.birt_6;
						break;
					case 7:
						image = ImageManager.birt_7;
						break;
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
		if(entityX == endDestX && entityY == endDestY) {
			state = "despawn";
		} else if(state == "swat") {
			
			double swatSpeed = Utility.calculateKnockbackSpeed(entityX, entityY, swatX, swatY, gp.frameCount, swatStamp);
			String[] temp = Utility.homeTowardDest(entityX, entityY, swatX, swatY, swatSpeed);
			entityX = (int) Math.round(entityX + Double.parseDouble(temp[1]));
			entityY = (int) Math.round(entityY + Double.parseDouble(temp[2]));
			if(entityX == swatX && entityY == swatY) {
				state = "moving";
			}
		} else {
			if(state != "despawn") {
				if(gp.frameCount % 2 == 0) {
					String[] temp = Utility.homeTowardDest(entityX, entityY, destX, destY, speed);
					entityX = (int) Math.round(entityX + Double.parseDouble(temp[1]));
					entityY = (int) Math.round(entityY + Double.parseDouble(temp[2]));
				}
		
			}
			
		}
		
		
	}
	
}