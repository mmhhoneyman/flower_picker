package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import main.GamePanel;
import main.MouseHandler;
import tile.Tile;
import tile.TileManager;

public class EntityManager {
	
	GamePanel gp;
	MouseHandler mouseH;
	Player player;
	TileManager tileM;
	
	public ArrayList<Entity> entities;
	
	public EntityManager (GamePanel gp, MouseHandler mouseH) {
		
		this.gp = gp;
		this.mouseH = mouseH;
		entities = new ArrayList<>();
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setTileManager(TileManager tileM) {
		this.tileM = tileM;
	}
	
	public void update() {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
	}
	
	public void draw(Graphics2D g2) {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g2);
		}
	}
	
	public void addEntity(GamePanel gp, int destX, int destY, String theClass) {
		
		switch(theClass) {
			case "Butterfly":
				entities.add(new Butterfly(gp, destX, destY));
				break;
		}
		
	}
	
	public void removeEntity() {
		
	}
	
}






