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
	
	public int swatStamp;
	public boolean entityClicked;
	
	public ArrayList<Entity> entities;
	
	public EntityManager (GamePanel gp, MouseHandler mouseH) {
		
		this.gp = gp;
		this.mouseH = mouseH;
		entities = new ArrayList<>();
		swatStamp = 0;
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
		removeEntity();
		checkSwat();
	}
	
	public void draw(Graphics2D g2) {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g2);
		}
		if(entityClicked) {
			
		}
	}
	
	public void addEntity(int destX, int destY, String theClass) {
		
		switch(theClass) {
			case "Butterfly":
				entities.add(new Butterfly(this.gp, this.mouseH, this.player, this.tileM, destX, destY));
				break;
		}
		
	}
	
	public void removeEntity() {
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).state == "despawn") {
				entities.remove(i);
			}
		}
	}
	
	public void checkSwat() {
		
		if(mouseH.rightClick == true){
			if(swatStamp <= gp.frameCount) {
				entityClicked = false;
				for(int i = 0; i < entities.size(); i++) {
					if(mouseH.mouseX <= entities.get(i).entityX + gp.tileSize && mouseH.mouseX >= entities.get(i).entityX &&
					   mouseH.mouseY <= entities.get(i).entityY + gp.tileSize && mouseH.mouseY >= entities.get(i).entityY) {
						if(entities.get(i).state != "picking") {
							entityClicked = true;
							entities.get(i).swat = true;
						}
					}	
				}
				if(entityClicked) {
					swatStamp = gp.frameCount + 60;
				}
			}
		}
		
	}
	
}






