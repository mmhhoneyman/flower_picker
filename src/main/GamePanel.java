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
		
		tileM.setPlayer(player);
		tileM.setEntityManager(entityM);
		
		player.setTileManager(tileM);
		player.setEntityManager(entityM);
		
		entityM.setPlayer(player);
		entityM.setTileManager(tileM);
		
		frameCount = 0;
		
		this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
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
		
		double refreshRate = 1000000000/Constants.FPS; // the length of a frame in nanoseconds
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
				
				// System.out.println(remainingTime);
				remainingTime = 5; // makes game faster for testing
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
	
}






