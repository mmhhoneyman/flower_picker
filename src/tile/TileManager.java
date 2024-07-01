package tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.EntityManager;
import entity.Player;
import main.GamePanel;
import main.ImageManager;
import main.MouseHandler;
import main.Utility;

public class TileManager {

	GamePanel gp;
	MouseHandler mouseH;
	Player player;
	EntityManager entityM;
	
	int timeStamp; // starting frame count
	int sproutInterval;
	
	public Tile[][] tile;
	
	public int rowSelTile; // NOTE: starts at 0
	public int colSelTile; // NOTE: starts at 0
	
	public int[][] initTileNums;
	public int[][] tileNums;
	
	
	// only works if the max screen columns are 16 and rows are 14 or smaller (not recommended)
	/* public int[][] tileNums = {
			{26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26},
			{26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26},
			{01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01},
			{24, 24, 24, 24, 24, 20, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24},
			{24, 20, 24, 24, 24, 24, 24, 24, 24, 20, 24, 24, 24, 24, 24, 24},
			{24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 20, 24, 24},
			{24, 24, 24, 24, 24, 20, 24, 24, 24, 24, 24, 20, 24, 24, 24, 24},
			{24, 24, 24, 24, 24, 24, 24, 24, 20, 24, 24, 24, 24, 24, 24, 24},
			{24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24},
			{20, 24, 24, 20, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24},
			{24, 24, 24, 24, 24, 24, 24, 24, 20, 24, 24, 24, 24, 20, 24, 24},
			{24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24},
			{24, 24, 24, 24, 24, 20, 24, 24, 24, 24, 24, 24, 20, 24, 24, 24},
			{24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 20, 24, 24}
	}; */
	
	
	public TileManager(GamePanel gp, MouseHandler mouseH, EntityManager entityM) {
		
		this.gp = gp;
		this.mouseH = mouseH;
		
		tile = new Tile[gp.maxScreenRow][gp.maxScreenCol];
		
		generateWorldNums();
		getInitLocations();
		setDefaultValues();
	}
	
	// exists to manage cyclic dependency with other classes (Player)
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	// same /\
	public void setEntityManager(EntityManager entityM) {
		this.entityM = entityM;
	}
	
	public void setDefaultValues() {
		sproutInterval = Utility.generateRandom(120, 210);
		timeStamp = 0;
		
		rowSelTile = gp.skyLevel;
		colSelTile = 0;
	}
	
	public void generateWorldNums() {
		
		int sky_tile_num = 26;
		int fence_tile_num = 1;
		int grass_plain_tile_num = 24;
		int grass_hori_tile_num = 20;
		
		int count = 0;
		int grass_hori_rand = Utility.generateRandom(1, (int) (2 * gp.maxScreenCol / 3));
		
		initTileNums = new int[gp.maxScreenRow][gp.maxScreenCol];
		
		for(int r = 0; r < gp.maxScreenRow; r++) {
			for(int c = 0; c < gp.maxScreenCol; c++) {
				if(r + 1 < gp.skyLevel) {
					initTileNums[r][c] = sky_tile_num;
				} else if(r + 1 == gp.skyLevel) {
					initTileNums[r][c] = fence_tile_num;
				} else {
					if(count == grass_hori_rand) {
						if(checkSurroundingTiles(r, c)) {
							initTileNums[r][c] = grass_hori_tile_num;
							grass_hori_rand = Utility.generateRandom((int) (gp.maxScreenCol / 2), (int) (4 * gp.maxScreenCol / 3)) + count;
						} else {
							initTileNums[r][c] = grass_plain_tile_num;
							grass_hori_rand = Utility.generateRandom((int) (gp.maxScreenCol / 3), (int) (gp.maxScreenCol / 2)) + count;
						}
					} else {
						initTileNums[r][c] = grass_plain_tile_num;
					}
					count++;
				}
			}
		}
		
		// assign the same values to tileNums
		tileNums = new int[gp.maxScreenRow][gp.maxScreenCol];
		for(int r = 0; r < gp.maxScreenRow; r++) {
			for(int c = 0; c < gp.maxScreenCol; c++) {
				tileNums[r][c] = initTileNums[r][c];
			}
		}
	}
	
	// returns true if all surrounding tiles don't contain grass_hori
	public boolean checkSurroundingTiles(int r, int c) {
		
		int grass_hori_tile_num = 20;
		
		int columnLeftBound;
		int columnRightBound;
		
		if(c == 0) {
			columnLeftBound = 0;
			columnRightBound = 1;
		} else if(c == gp.maxScreenCol - 1) {
			columnLeftBound = 1;
			columnRightBound = 0;
		} else {
			columnLeftBound = 1;
			columnRightBound = 1;
		}
		
		if(r > 0) {
			for(int c2 = c - columnLeftBound; c2 <= c + columnRightBound; c2++) {
				if(initTileNums[r - 1][c2] == grass_hori_tile_num) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void getInitLocations() {
			
		for(int r = 0; r < gp.maxScreenRow; r++) {
			for(int c = 0; c < gp.maxScreenCol; c++) {
				tile[r][c] = new Tile();
				tile[r][c].image = tileSwitch(initTileNums[r][c]);
				
				if(tile[r][c].image == ImageManager.grass_plain || tile[r][c].image == ImageManager.grass_hori || tile[r][c].image == ImageManager.grass_vert) {
					tile[r][c].walkable = true;
				} else {
					tile[r][c].walkable = false;
				}
				tile[r][c].pickable = false;
				tile[r][c].isFlower = false;
				tile[r][c].entityStamp = Integer.MAX_VALUE;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		for(int r = 0; r < gp.maxScreenRow; r++) {
			for(int c = 0; c < gp.maxScreenCol; c++) {
				g2.drawImage(tile[r][c].image, gp.tileSize*c, gp.tileSize*r, gp.tileSize, gp.tileSize, null);
			}
		}
		
		if(player.playerX != colSelTile*gp.tileSize || player.playerY != rowSelTile*gp.tileSize) {
			g2.setColor(Color.white);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f)); // transparency set to 20%
			g2.fillRect(colSelTile*gp.tileSize,  rowSelTile*gp.tileSize,  gp.tileSize,  gp.tileSize);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
			
			if(gp.frameCount % 40 < 20) {
				g2.drawImage(ImageManager.select_border_1, colSelTile*gp.tileSize, rowSelTile*gp.tileSize, gp.tileSize, gp.tileSize, null);
			} else {
				g2.drawImage(ImageManager.select_border_2, colSelTile*gp.tileSize, rowSelTile*gp.tileSize, gp.tileSize, gp.tileSize, null);
			}
			
		}
		
	}
	
	public void update() {
		
		checkSelectedTile();
		plantSprouts();
		updateTiles();
		
	}
	
	public void updateTiles() {
		for(int r = 0; r < gp.maxScreenRow; r++) {
			for(int c = 0; c < gp.maxScreenCol; c++) {
				// changes tile type
				if(tile[r][c].changeStamp <= gp.frameCount && (r*gp.tileSize != player.pickTileY && c*gp.tileSize != player.pickTileX)) {
					updateTile(r, c);
				}
				tile[r][c].image = tileSwitch(tileNums[r][c]);
				// spawns new enemy
				if(tile[r][c].entityStamp == gp.frameCount) {
					int rand = Utility.generateRandom(1, 2);
					if(rand == 1) {
						entityM.addEntity(c * gp.tileSize, r * gp.tileSize, "Butterfly");
					} else {
						entityM.addEntity(c * gp.tileSize, r * gp.tileSize, "Bee");
					}
					
				}
			}
		}
	}
	
	public void plantSprouts() {
		if(sproutInterval + timeStamp <= gp.frameCount) {
			
			boolean tileFound = false;
			int tryCount = 0;
			while(!tileFound && tryCount < 10) {
				int randCol = Utility.generateRandom(0, gp.maxScreenCol - 1);
				int randRow = Utility.generateRandom(gp.skyLevel, gp.maxScreenRow - 1);
				
				if(tileNums[randRow][randCol] == 24 || tileNums[randRow][randCol] == 20) {
					tileNums[randRow][randCol] = 12;
					//tile[randX][randY].nextState = "flower_sprout_2";
					//tile[randX][randY].timeStamp = gp.frameCount;
					tile[randRow][randCol].changeStamp = gp.frameCount + Utility.generateRandom(120, 210);
					timeStamp = gp.frameCount;
					sproutInterval = Utility.generateRandom(120, 210);
					tileFound = true;
				}
				tryCount++;
			}
		}
	}
	
	public void updateTile(int r, int c) {
		
		int minFlowerGrowth1 = 180;
		int maxFlowerGrowth1 = 240;
		
		int minSproutGrowth1 = 180;
		int maxSproutGrowth1 = 240;
		int minSproutGrowth2 = 180;
		int maxSproutGrowth2 = 240;
		
		int minGrassGrowth1 = 180;
		int maxGrassGrowth1 = 240;
		int minGrassGrowth2 = 180;
		int maxGrassGrowth2 = 240;
		
		int minEntityStamp = 180;
		int maxEntityStamp = 240;
		
		int max = Integer.MAX_VALUE; // used to ensure that the stamp doesn't get triggered, this is similar to infinity
		int tileNum = tileNums[r][c];
		
		switch(tileNum) {
			case 2: // flower_blue_1
				tileNums[r][c] = 3;
				tile[r][c].changeStamp = Utility.generateRandom(minFlowerGrowth1, maxFlowerGrowth1) + gp.frameCount;
				break;
			case 3: // flower_blue_2
				tileNums[r][c] = 4;
				tile[r][c].changeStamp = max;
				break;
			case 5: // flower_orange_1
				tileNums[r][c] = 6;
				tile[r][c].changeStamp = Utility.generateRandom(minFlowerGrowth1, maxFlowerGrowth1) + gp.frameCount;
				break;
			case 6: // flower_orange_2
				tileNums[r][c] = 7;
				tile[r][c].changeStamp = max;
				break;
			case 8: // flower_picked
				tileNums[r][c] = initTileNums[r][c];
				tile[r][c].changeStamp = max;
				break;
			case 9: // flower_rose_1
				tileNums[r][c] = 10;
				tile[r][c].changeStamp = Utility.generateRandom(minFlowerGrowth1, maxFlowerGrowth1) + gp.frameCount;
				break;
			case 10: // flower_rose_2
				tileNums[r][c] = 11;
				tile[r][c].changeStamp = max;
				break;
			case 12: // flower_sprout_1
				tileNums[r][c] = 13;
				tile[r][c].changeStamp = Utility.generateRandom(minSproutGrowth1, maxSproutGrowth1) + gp.frameCount;
				break;
			case 13: // flower_sprout_2
				
				int rand = Utility.generateRandom(1, 41);
				
				if(rand == 1) {
					tileNums[r][c] = 9;
					tile[r][c].isFlower = true;
				} else if(rand > 1 && rand <= 6) {
					tileNums[r][c] = 2;
					tile[r][c].isFlower = true;
				} else if(rand > 6 && rand <= 11) {
					tileNums[r][c] = 5;
					tile[r][c].isFlower = true;
				} else if(rand > 11 && rand <= 16) {
					tileNums[r][c] = 14;
					tile[r][c].isFlower = true;
				} else if(rand > 16 && rand <= 21) {
					tileNums[r][c] = 17;
					tile[r][c].isFlower = true;
				} else {
					tileNums[r][c] = 27;
				}
						
				tile[r][c].pickable = true;
				tile[r][c].changeStamp = Utility.generateRandom(minSproutGrowth2, maxSproutGrowth2) + gp.frameCount;
				if(tile[r][c].isFlower) {
					tile[r][c].entityStamp = Utility.generateRandom(minEntityStamp, maxEntityStamp) + gp.frameCount;
				}
				break;
			case 14: // flower_white_1
				tileNums[r][c] = 15;
				tile[r][c].changeStamp = Utility.generateRandom(minFlowerGrowth1, maxFlowerGrowth1) + gp.frameCount;
				break;
			case 15: // flower_white_2
				tileNums[r][c] = 16;
				tile[r][c].changeStamp = max;
				break;
			case 17: // flower_yellow_1
				tileNums[r][c] = 18;
				tile[r][c].changeStamp = Utility.generateRandom(minFlowerGrowth1, maxFlowerGrowth1) + gp.frameCount;
				break;
			case 18: // flower_yellow_2
				tileNums[r][c] = 19;
				tile[r][c].changeStamp = max;
				break;
			case 21: // grass_mowed_1
				tileNums[r][c] = 22;
				tile[r][c].changeStamp = Utility.generateRandom(minGrassGrowth1, maxGrassGrowth1) + gp.frameCount;
				break;
			case 22: // grass_mowed_2
				tileNums[r][c] = 23;
				tile[r][c].changeStamp = Utility.generateRandom(minGrassGrowth2, maxGrassGrowth2) + gp.frameCount;
				break;
			case 23: // grass_mowed_3
				tileNums[r][c] = initTileNums[r][c];
				tile[r][c].changeStamp = max;
				break;
			default:
				break;
		}
		
	}
	
	public BufferedImage tileSwitch(int num) {
		
		BufferedImage image = null;
		switch(num) {
			case 1:
				image = ImageManager.fence;
				break;
			case 2:
				image = ImageManager.flower_blue_1;
				break;
			case 3:
				image = ImageManager.flower_blue_2;
				break;
			case 4:
				image = ImageManager.flower_blue_3;
				break;
			case 5:
				image = ImageManager.flower_orange_1;
				break;
			case 6:
				image = ImageManager.flower_orange_2;
				break;
			case 7:
				image = ImageManager.flower_orange_3;
				break;
			case 8:
				image = ImageManager.flower_picked;
				break;
			case 9:
				image = ImageManager.flower_rose_1;
				break;
			case 10:
				image = ImageManager.flower_rose_2;
				break;
			case 11:
				image = ImageManager.flower_rose_3;
				break;
			case 12:
				image = ImageManager.flower_sprout_1;
				break;
			case 13:
				image = ImageManager.flower_sprout_2;
				break;
			case 14:
				image = ImageManager.flower_white_1;
				break;
			case 15:
				image = ImageManager.flower_white_2;
				break;
			case 16:
				image = ImageManager.flower_white_3;
				break;
			case 17:
				image = ImageManager.flower_yellow_1;
				break;
			case 18:
				image = ImageManager.flower_yellow_2;
				break;
			case 19:
				image = ImageManager.flower_yellow_3;
				break;
			case 20:
				image = ImageManager.grass_hori;
				break;
			case 21:
				image = ImageManager.grass_mowed_1;
				break;
			case 22:
				image = ImageManager.grass_mowed_2;
				break;
			case 23:
				image = ImageManager.grass_mowed_3;
				break;
			case 24:
				image = ImageManager.grass_plain;
				break;
			case 25:
				image = ImageManager.grass_vert;
				break;
			case 26:
				image = ImageManager.sky;
				break;
			case 27:
				image = ImageManager.weed;
				break;
			case 28:
				image = ImageManager.select_border_1;
				break;
			case 29:
				image = ImageManager.select_border_2;
				break;
		}
		return image;
	}
	public void checkSelectedTile() {
		
		if(mouseH.leftClick == true) {
			if(mouseH.mouseY > gp.skyLevel * gp.tileSize) { // player cannot interact with top three rows of tiles
				colSelTile = (mouseH.mouseX - mouseH.mouseX % gp.tileSize) / gp.tileSize;
				rowSelTile = (mouseH.mouseY - mouseH.mouseY % gp.tileSize) / gp.tileSize;
			}
		}
		
	}
	
}
