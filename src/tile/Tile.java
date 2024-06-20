package tile;

import java.awt.image.BufferedImage;

public class Tile {
	
	
	public BufferedImage image;
	// public String nextState; // what the next state is able to be, "none" for no state available or can only be mowed or picked
	public boolean walkable; // is able to be turned into grass_mowed_1
	public boolean pickable; // is able to be turned into flower_picked
	//public int timeStamp; // stamp for when tile last changed
	public int changeStamp; // stamp for when tile will change

}
