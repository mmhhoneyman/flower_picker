package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.MouseHandler;
import tile.TileManager;

public class Butterfly extends Entity{

	static BufferedImage butterfly_down_1, butterfly_down_2, butterfly_down_3, butterfly_left_1, butterfly_left_2, butterfly_left_3, 
	butterfly_right_1, butterfly_right_2, butterfly_right_3, butterfly_up_1, butterfly_up_2, butterfly_up_3;
	
	static { // loads all images
		try {
			butterfly_down_1 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_down_1.png"));
			butterfly_down_2 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_down_2.png"));
			butterfly_down_3 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_down_3.png"));
			butterfly_left_1 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_left_1.png"));
			butterfly_left_2 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_left_2.png"));
			butterfly_left_3 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_left_3.png"));
			butterfly_right_1 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_right_1.png"));
			butterfly_right_2 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_right_2.png"));
			butterfly_right_3 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_right_3.png"));
			butterfly_up_1 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_up_1.png"));
			butterfly_up_2 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_up_2.png"));
			butterfly_up_3 = ImageIO.read(Butterfly.class.getResourceAsStream("/butterfly/butterfly_up_3.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Butterfly(GamePanel gp, int destX, int destY) {
		
		super(gp, destX, destY);
		setSpawnLocation();
		spawnStamp = gp.frameCount;
		
		setSpeed();
		
	}
	
	@Override
	void setSpawnLocation() {
		// TODO Auto-generated method stub
		
		int rand = gp.generateRandom(1, 4);
		
		switch(rand) {
			case 1: // above flower
				spawnX = destX;
				spawnY = 0 - gp.tileSize;
				break;
			case 2: // below flower
				spawnX = destX;
				spawnY = gp.screenHeight + gp.tileSize;
				break;
			case 3: // left of flower
				spawnX = 0 - gp.tileSize;
				spawnY = destY;
				break;
			case 4: // right of flower
				spawnX = gp.screenWidth + gp.tileSize;
				spawnY = destY;
				break;
		}
		entityX = spawnX;
		entityY = spawnY;
		
	}
	
	public void setSpeed() {
		
		int rand = gp.generateRandom(1, 1);
		
		this.speed = rand * gp.scale;
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
		move();
		
	}

	@Override
	void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
		BufferedImage image = null;
		
		switch(state) {
			case "left":
				if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
					image = butterfly_left_2;
				} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
					image = butterfly_left_1;
				} else {
					image = butterfly_left_3;
				}
				break;
			case "right":
				if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
					image = butterfly_right_2;
				} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
					image = butterfly_right_1;
				} else {
					image = butterfly_right_3;
				}
				break;
			case "up":
				if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
					image = butterfly_up_1;
				} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
					image = butterfly_up_2;
				} else {
					image = butterfly_up_3;
				}
				break;
			case "down":
				if(gp.frameCount % 60 <= 15 || (gp.frameCount % 60 > 30 && gp.frameCount % 60 <= 45)) {
					image = butterfly_down_1;
				} else if(gp.frameCount % 60 > 15 && gp.frameCount % 60 <= 30) {
					image = butterfly_down_2;
				} else {
					image = butterfly_down_3;
				}
				break;
		}
		
		g2.drawImage(image, entityX, entityY, gp.tileSize, gp.tileSize, null);
		
	}

	@Override
	void move() {
		// TODO Auto-generated method stub
		
		if(spawnX > destX) {
			entityX -= speed;
			state = "left";
		}
		if(spawnX < destX) {
			entityX += speed;
			state = "right";
		}
		if(spawnY > destY) {
			entityY -= speed;
			state = "up";
		}
		if(spawnY < destY) {
			entityY += speed;
			state = "down";
		}
		
		if(Math.abs(destX - entityX) < speed) {
			entityX = destX;
		}
		if(Math.abs(destY - entityY) < speed) {
			entityY = destY;
		}
		
	}

}






