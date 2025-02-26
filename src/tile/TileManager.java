package tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import entity.EntityManager;
import entity.Player;
import main.Constants;
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
	
	
	// only works if the max screen columns are 16 and rows are 14 or smaller (use is not recommended)
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
		
		tile = new Tile[Constants.MAX_SCREEN_ROW][Constants.MAX_SCREEN_COL];
		
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
		sproutInterval = Utility.generateRandom(Constants.TILE_PLANT_SPROUT_MIN, Constants.TILE_PLANT_SPROUT_MAX);
		timeStamp = 0;
		
		rowSelTile = Constants.PLAYER_SPAWN_Y / Constants.TILE_SIZE;
		colSelTile = Constants.PLAYER_SPAWN_X / Constants.TILE_SIZE;
	}
	
	public void generateWorldNums() {
		
		int sky_tile_num = 26;
		int fence_tile_num = 1;
		int grass_plain_tile_num = 24;
		int grass_hori_tile_num = 20;
		
		int count = 0;
		int grass_hori_rand = Utility.generateRandom(1, (int) (2 * Constants.MAX_SCREEN_COL / 3));
		
		initTileNums = new int[Constants.MAX_SCREEN_ROW][Constants.MAX_SCREEN_COL];
		
		for(int r = 0; r < Constants.MAX_SCREEN_ROW; r++) {
			for(int c = 0; c < Constants.MAX_SCREEN_COL; c++) {
				if(r + 1 < Constants.SKY_LEVEL) {
					initTileNums[r][c] = sky_tile_num;
				} else if(r + 1 == Constants.SKY_LEVEL) {
					initTileNums[r][c] = fence_tile_num;
				} else {
					if(count == grass_hori_rand) {
						if(checkSurroundingTiles(r, c)) {
							initTileNums[r][c] = grass_hori_tile_num;
							grass_hori_rand = Utility.generateRandom((int) (Constants.MAX_SCREEN_COL / 2), (int) (4 * Constants.MAX_SCREEN_COL / 3)) + count;
						} else {
							initTileNums[r][c] = grass_plain_tile_num;
							grass_hori_rand = Utility.generateRandom((int) (Constants.MAX_SCREEN_COL / 3), (int) (Constants.MAX_SCREEN_COL / 2)) + count;
						}
					} else {
						initTileNums[r][c] = grass_plain_tile_num;
					}
					count++;
				}
			}
		}
		
		// assign the same values to tileNums
		tileNums = new int[Constants.MAX_SCREEN_ROW][Constants.MAX_SCREEN_COL];
		for(int r = 0; r < Constants.MAX_SCREEN_ROW; r++) {
			for(int c = 0; c < Constants.MAX_SCREEN_COL; c++) {
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
		} else if(c == Constants.MAX_SCREEN_COL - 1) {
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
			
		for(int r = 0; r < Constants.MAX_SCREEN_ROW; r++) {
			for(int c = 0; c < Constants.MAX_SCREEN_COL; c++) {
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
		for(int r = 0; r < Constants.MAX_SCREEN_ROW; r++) {
			for(int c = 0; c < Constants.MAX_SCREEN_COL; c++) {
				g2.drawImage(tile[r][c].image, Constants.TILE_SIZE*c, Constants.TILE_SIZE*r, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
			}
		}
		
		if(gp.state.equals("game")) {
			if(player.playerX != colSelTile*Constants.TILE_SIZE || player.playerY != rowSelTile*Constants.TILE_SIZE) {
				g2.setColor(Color.white);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Constants.SELTILE_TRANSPARENCY));
				g2.fillRect(colSelTile*Constants.TILE_SIZE,  rowSelTile*Constants.TILE_SIZE,  Constants.TILE_SIZE,  Constants.TILE_SIZE);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
				
				if(gp.frameCount % Constants.SELTILE_ANIMATION_FRAME_LENGTH*2 < Constants.SELTILE_ANIMATION_FRAME_LENGTH) {
					g2.drawImage(ImageManager.select_border_1, colSelTile*Constants.TILE_SIZE, rowSelTile*Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
				} else {
					g2.drawImage(ImageManager.select_border_2, colSelTile*Constants.TILE_SIZE, rowSelTile*Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
				}
			}
		}
		
	}
	
	public void update() {
		
		checkSelectedTile();
		plantSprouts();
		updateTiles();
		
	}
	
	public void updateTiles() {
		for(int r = 0; r < Constants.MAX_SCREEN_ROW; r++) {
			for(int c = 0; c < Constants.MAX_SCREEN_COL; c++) {
				// changes tile type
				if(tile[r][c].changeStamp <= gp.frameCount && (r*Constants.TILE_SIZE != player.pickTileY && c*Constants.TILE_SIZE != player.pickTileX)) {
					updateTile(r, c);
				}
				tile[r][c].image = tileSwitch(tileNums[r][c]);
				// spawns new enemy
				if(tile[r][c].entityStamp == gp.frameCount) {
					int rand = Utility.generateRandom(1, 2);
					if(rand == 1) {
						entityM.addEntity(c * Constants.TILE_SIZE, r * Constants.TILE_SIZE, "Butterfly");
					} else {
						entityM.addEntity(c * Constants.TILE_SIZE, r * Constants.TILE_SIZE, "Bee");
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
				int randCol = Utility.generateRandom(0, Constants.MAX_SCREEN_COL - 1);
				int randRow = Utility.generateRandom(Constants.SKY_LEVEL, Constants.MAX_SCREEN_ROW - 1);
				
				if(tileNums[randRow][randCol] == 24 || tileNums[randRow][randCol] == 20) {
					tileNums[randRow][randCol] = 12;
					tile[randRow][randCol].changeStamp = gp.frameCount + Utility.generateRandom(Constants.TILE_CHANGE_FROM_SPROUT_1_MIN, Constants.TILE_CHANGE_FROM_SPROUT_1_MAX);
					timeStamp = gp.frameCount;
					if(gp.frameCount - (Constants.TUTORIAL_INT_3 + Constants.COUNTDOWN_INT_3) <= Constants.GAME_TIME / 150) {
						sproutInterval = Utility.generateRandom(Constants.TILE_PLANT_SPROUT_MIN / 20, Constants.TILE_PLANT_SPROUT_MAX / 20);
					} else {
						sproutInterval = Utility.generateRandom(Constants.TILE_PLANT_SPROUT_MIN, Constants.TILE_PLANT_SPROUT_MAX);
					}
					tileFound = true;
				}
				tryCount++;
			}
		}
	}
	
	public void updateTile(int r, int c) {
		
		int minFlowerGrowth1 = Constants.TILE_CHANGE_FROM_FLOWER_2_MIN;
		int maxFlowerGrowth1 = Constants.TILE_CHANGE_FROM_FLOWER_2_MAX;
		
		int minSproutGrowth1 = Constants.TILE_CHANGE_FROM_SPROUT_2_MIN;
		int maxSproutGrowth1 = Constants.TILE_CHANGE_FROM_SPROUT_2_MAX;
		int minSproutGrowth2 = Constants.TILE_CHANGE_FROM_FLOWER_1_MIN;
		int maxSproutGrowth2 = Constants.TILE_CHANGE_FROM_FLOWER_1_MAX;
		
		int minRoseGrowth1 = Constants.TILE_CHANGE_FROM_ROSE_2_MIN;
		int maxRoseGrowth1 = Constants.TILE_CHANGE_FROM_ROSE_2_MAX;
		
		int minRoseSproutGrowth1 = Constants.TILE_CHANGE_FROM_ROSE_1_MIN;
		int maxRoseSproutGrowth1 = Constants.TILE_CHANGE_FROM_ROSE_1_MAX;
		
		int minGrassGrowth1 = Constants.TILE_CHANGE_FROM_MOWED_2_MIN;
		int maxGrassGrowth1 = Constants.TILE_CHANGE_FROM_MOWED_2_MAX;
		int minGrassGrowth2 = Constants.TILE_CHANGE_FROM_MOWED_3_MIN;
		int maxGrassGrowth2 = Constants.TILE_CHANGE_FROM_MOWED_3_MAX;
		
		int minEntityStamp = Constants.TILE_ENTITY_STAMP_MIN;
		int maxEntityStamp = Constants.TILE_ENTITY_STAMP_MAX;
		
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
				tile[r][c].changeStamp = Utility.generateRandom(minRoseGrowth1, maxRoseGrowth1) + gp.frameCount;
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
				
				int rand;
				if(gp.frameCount - (Constants.TUTORIAL_INT_3 + Constants.COUNTDOWN_INT_3) <= Constants.GAME_TIME / 20) {
					rand = Utility.generateRandom(2, 41);
				} else {
					rand = Utility.generateRandom(1, 41);
				}
				
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
				if(rand == 1) {
					tile[r][c].changeStamp = Utility.generateRandom(minRoseSproutGrowth1, maxRoseSproutGrowth1) + gp.frameCount;
				} else {
					tile[r][c].changeStamp = Utility.generateRandom(minSproutGrowth2, maxSproutGrowth2) + gp.frameCount;
				}
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
			if(mouseH.mouseY > Constants.SKY_LEVEL * Constants.TILE_SIZE) { // player cannot interact with tiles at or above the skyline
				colSelTile = (mouseH.mouseX - mouseH.mouseX % Constants.TILE_SIZE) / Constants.TILE_SIZE;
				rowSelTile = (mouseH.mouseY - mouseH.mouseY % Constants.TILE_SIZE) / Constants.TILE_SIZE;
			}
		}
		
	}
	
}
