package main;

import javax.swing.JPanel;

import entity.EntityManager;
import entity.Player;
import tile.TileManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// SCREEN SETTINGS
	public final int originalTileSize = 16; // 16X16 tile... don't mess with this number
	public final int scale = 3;
	public final int tileSize = originalTileSize*scale; // 48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 14;
	public final int screenWidth = tileSize*maxScreenCol; // 1536 pixels
	public final int screenHeight = tileSize*maxScreenRow; // 960 pixels
	public final int skyLevel = 3; // decides how far down on the screen the sky level is, this is where the fence and sky tiles are placed
	public final int FPS = 60;
	
	public int frameCount;
	
	MouseHandler mouseH;
	TileManager tileM;
	Thread gameThread;
	Player player;
	EntityManager entityM;
	Random random;
	
	
	public GamePanel() {
		
		random = new Random(System.currentTimeMillis());
		mouseH = new MouseHandler();
		entityM = new EntityManager(this, mouseH);
		tileM = new TileManager(this, mouseH, entityM);
		player = new Player(this, mouseH);
		
		frameCount = 0;
		
		tileM.setPlayer(player);
		player.setTileManager(tileM);
		
		entityM.setPlayer(player);
		entityM.setTileManager(tileM);
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.gray);
		this.setDoubleBuffered(true);
		this.addMouseListener(mouseH);
		this.setFocusable(true);
	}

	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		double refreshRate = 1000000000/FPS; // the length of a frame in nanoseconds
		double nextDrawTime = System.nanoTime() + refreshRate;
		
		while(gameThread != null) {
			
			update();
			
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000; // converting from nanoseconds to milliseconds
				
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += refreshRate;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			frameCount++;
			
		}
		
	}
	
	public void update() {
		
		tileM.update();
		player.update();
		entityM.update();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		
		tileM.draw(g2);
		player.draw(g2);
		entityM.draw(g2);
		
		
		g2.dispose();
	}
	
	public int generateRandom(int rangeMin, int rangeMax) {
		return rangeMin + random.nextInt(rangeMax - rangeMin + 1);
	}
	
	
}






