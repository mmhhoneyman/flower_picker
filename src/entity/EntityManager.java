package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import main.Audio;
import main.Constants;
import main.GamePanel;
import main.MouseHandler;
import main.Utility;
import tile.TileManager;

public class EntityManager {
	
	GamePanel gp;
	MouseHandler mouseH;
	Player player;
	TileManager tileM;
	
	public int swatStamp;
	public boolean entityClicked;
	
	public ArrayList<Entity> entities;
	
	Audio bee_se = new Audio("res/se/Bee_SE.wav", false);
	Audio butterfly_se = new Audio("res/se/Butterfly_SE.wav", false);
	Audio flower_se = new Audio("res/se/Flower_SE.wav", false);
	Audio hit_se = new Audio("res/se/Hit_SE.wav", false);
	Audio ladybug_se = new Audio("res/se/Ladybug_SE.wav", false);
	
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
		checkFlee();
	}
	
	public void draw(Graphics2D g2) {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g2);
		}
		if(entityClicked) {
			
		}
	}
	
	public void addEntity(int destX, int destY, String theClass) {
		
		//if(entities.size() == 0) {
			switch(theClass) {
				case "Butterfly":
					entities.add(new Butterfly(this.gp, this.mouseH, this.player, this.tileM, destX, destY));
					break;
				case "Ladybug":
					entities.add(0, new Ladybug(this.gp, this.mouseH, this.player, this.tileM, destX, destY));
					break;
				case "Bee":
					entities.add(new Bee(this.gp, this.mouseH, this.player, this.tileM, destX, destY));
					break;
				case "Mower":
					entities.add(0, new Mower(this.gp, this.mouseH, this.player, this.tileM, destX, destY));
			}
		//}
		
		
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
					if(mouseH.mouseX <= entities.get(i).entityX + Constants.TILE_SIZE && mouseH.mouseX >= entities.get(i).entityX &&
					   mouseH.mouseY <= entities.get(i).entityY + Constants.TILE_SIZE && mouseH.mouseY >= entities.get(i).entityY) {
						if(entities.get(i).state != "picking") {
							entityClicked = true;
							entities.get(i).swat = true;
							playSwatSound(entities.get(i));
						}
					}	
				}
				if(entityClicked) {
					swatStamp = gp.frameCount + Constants.PLAYER_SWAT_COOLDOWN;
				}
			}
		}
		
	}
	
	public void playSwatSound(Entity e) {
		String temp = e.getClass().getSimpleName();
		
		switch(temp) {
			case "Bee":
				bee_se.setVolume(0.75f);
				bee_se.play();
				break;
			case "Butterfly":
				butterfly_se.setVolume(0.75f);
				butterfly_se.play();
				break;
			case "Ladybug":
				ladybug_se.setVolume(0.75f);
				ladybug_se.play();
				break;
		}
	}
	
	// returns index of entity player is colliding with, if none then returns -1
	public int checkPlayerCollision() {
		int playerMinX = player.playerX;
		int playerMaxX = player.playerX + Constants.TILE_SIZE;
		int playerMinY = player.playerY;
		int playerMaxY = player.playerY + Constants.TILE_SIZE;
		
		for(int i = 0; i < entities.size(); i++) {
			int entityMinX = entities.get(i).entityX + Constants.PLAYER_COLLISION_TRIMMING;
			int entityMinY = entities.get(i).entityY + Constants.PLAYER_COLLISION_TRIMMING;
			
			int entityMaxX;
			int entityMaxY;
			if(entities.get(i).getClass().getSimpleName().equals("Mower")) {
				entityMaxX = entities.get(i).entityX + Constants.TILE_SIZE*2 - Constants.PLAYER_COLLISION_TRIMMING;
				entityMaxY = entities.get(i).entityY + Constants.TILE_SIZE*2 - Constants.PLAYER_COLLISION_TRIMMING;
			} else {
				entityMaxX = entities.get(i).entityX + Constants.TILE_SIZE - Constants.PLAYER_COLLISION_TRIMMING;
				entityMaxY = entities.get(i).entityY + Constants.TILE_SIZE - Constants.PLAYER_COLLISION_TRIMMING;
			}
			if((entityMinX >= playerMinX && entityMinX <= playerMaxX && entityMinY >= playerMinY && entityMinY <= playerMaxY) || // checks if top left corner of entity is inside player location
			   (entityMinX >= playerMinX && entityMinX <= playerMaxX && entityMaxY >= playerMinY && entityMaxY <= playerMaxY) || // checks if bottom left corner of entity is inside player location
			   (entityMaxX >= playerMinX && entityMaxX <= playerMaxX && entityMinY >= playerMinY && entityMinY <= playerMaxY) || // checks if top right corner of entity is inside player location
			   (entityMaxX >= playerMinX && entityMaxX <= playerMaxX && entityMaxY >= playerMinY && entityMaxY <= playerMaxY)) { // checks if bottom right corner of entity is inside player location
				hit_se.setVolume(0.75f);
				hit_se.play();
				return i;
			}
		}
		return -1;
	}
	
	public void checkFlee() {
		
		ArrayList<Integer> mowerLocations = new ArrayList<>();
		for(int i = 0; i < entities.size(); i++) { // find mowers, if any
			if(entities.get(i).getClass().getSimpleName().equals("Mower")) {
				mowerLocations.add(i);
			}
		}
		
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).getClass().getSimpleName().equals("Mower") == false) {
				for(int j = 0; j < mowerLocations.size(); j++) {
					double distanceX = entities.get(i).entityX - entities.get(mowerLocations.get(j)).entityX;
					double distanceY = entities.get(i).entityY - entities.get(mowerLocations.get(j)).entityY;
					double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
					
					if(distance <= Constants.MOWER_FLEE_DISTANCE * Constants.TILE_SIZE) {
						
						if(entities.get(i).getClass().getSimpleName().equals("Bee") || 
								entities.get(i).getClass().getSimpleName().equals("Butterfly")) {
							try {
								tileM.tile[entities.get(i).destY / Constants.TILE_SIZE][entities.get(i).destX / Constants.TILE_SIZE].pickable = true;
							} catch (ArrayIndexOutOfBoundsException e) {}
						}
						
						int[] temp = Utility.findFleeLocation(entities.get(i), entities.get(mowerLocations.get(j)));
						entities.get(i).destX = temp[0];
						entities.get(i).destY = temp[1];
						entities.get(i).endDestX = temp[0];
						entities.get(i).endDestY = temp[1];
						entities.get(i).flee = true;
					}
				}
			}
			
		}
		
	}
	
}






