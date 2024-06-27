package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.MouseHandler;
import tile.TileManager;

abstract class Entity {
	
	public int spawnX, spawnY; // spawn location
	public int entityX, entityY; // actual location
	public int destX, destY; // destination location
	public int swatX, swatY; // swat end location
	public double speed;
	
	public BufferedImage image;
	public String state; // most entities will have: up, down, left, right, flee, swat
	public int swatStamp;
	public boolean swat;
	
	GamePanel gp;
	MouseHandler mouseH;
	Player player;
	TileManager tileM;
	
	public Entity(GamePanel gp, MouseHandler mouseH, Player player, TileManager tileM, int destX, int destY) {
		
		this.gp = gp;
		this.mouseH = mouseH;
		this.player = player;
		this.tileM = tileM;
		this.destX = destX;
		this.destY = destY;
		
		setSpawnLocation();
		
	}
	
	// one of the methods called by constructor. Formulates where the starting position should be
	abstract void setSpawnLocation();
	
	// calls on other methods to update variables by frame
	abstract void update();
	
	// decides what image to draw and where based on update() information
	abstract void draw(Graphics2D g2);
	
	// one of the methods called by update(). Updates movement information by frame
	abstract void move();
	
}