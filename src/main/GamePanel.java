package main;

import javax.swing.JPanel;

import entity.EntityManager;
import entity.Player;
import tile.TileManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.AlphaComposite;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int frameCount;
	
	MouseHandler mouseH;
	TileManager tileM;
	public Thread gameThread;
	Player player;
	EntityManager entityM;
	Random random;
	
	public String state, nextState; //options: tutorial, countdown, game, supper time! 
	int postStamp;
	int timeLeft;
	
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
		postStamp = 1;
		timeLeft = 0;
		
		state = "tutorial";
		nextState = state;
		
		this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
		this.setBackground(Color.black);
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
				//remainingTime = 5; // makes game faster for testing
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += refreshRate;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			frameCount++;
			
			if(frameCount == 100) {
				//gameThread = null;
			}
			
		}
		
	}
	
	public void update() {
		
		if(state.equals("game") || frameCount == 0) {
			tileM.update();
			player.update();
			entityM.update();
		}
		timeLeft = postStamp - frameCount;
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		tileM.draw(g2);
		player.draw(g2);
		entityM.draw(g2);
		
		switch(state) {
			case "tutorial":
				tutorialScene(g2);
				break;
			case "countdown":
				countdownScene(g2);
				break;
			case "game":
				tileM.draw(g2);
				player.draw(g2);
				entityM.draw(g2);
				break;
		}
		
		g2.dispose();
	}
	
	public void tutorialScene(Graphics2D g2) {
		int interval1 = 200;
		int interval2 = interval1 + 500;
		int interval3 = interval2 + 200;
		
		if(state == nextState) {
			nextState = "countdown";
			postStamp = frameCount + interval3;
		}
		if(timeLeft + interval2 >= interval3) {
			int offset = interval3 - timeLeft;
			g2.drawImage(ImageManager.tutorial_screen_1, 0 + offset, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			g2.drawImage(ImageManager.tutorial_screen_1, 0 - Constants.SCREEN_WIDTH + offset, 0 + 147, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			g2.drawImage(ImageManager.tutorial_screen_1, 0 - Constants.SCREEN_WIDTH + offset, 0 - 10, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			
			double zoom = 0.05 * (1 + Math.sin(frameCount * 0.025)) + 1;
			
			int imgSizeX = (int)(Constants.SCREEN_WIDTH * zoom);
			int imgSizeY = (int)(Constants.SCREEN_HEIGHT * zoom);
			int imgLocX = (int)((double)(Constants.SCREEN_WIDTH - imgSizeX) / 2);
			int imgLocY = (int)((double)(Constants.SCREEN_HEIGHT - imgSizeY) / 2);
			
			g2.drawImage(ImageManager.tutorial_screen_2, imgLocX, imgLocY, imgSizeX, imgSizeY, null);
			
			if(timeLeft + interval1 >= interval3) {
				float transparency = ((float)(timeLeft) - (interval3 - interval1)) / (float)(interval1);
				if(transparency > 1.0f || transparency < 0f) {
					transparency = 1f;
				}
				g2.setColor(Color.black);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
				g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
			}
		} else if(timeLeft + interval3 >= interval3) {
			int swipe = (int)Math.pow(interval3 - timeLeft - interval2, 1.75) / 2;
			
			int offset = interval3 - timeLeft;
			g2.drawImage(ImageManager.tutorial_screen_1, 0 + offset + swipe, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			g2.drawImage(ImageManager.tutorial_screen_1, 0 - Constants.SCREEN_WIDTH + offset + swipe, 0 + 147, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			g2.drawImage(ImageManager.tutorial_screen_1, 0 - Constants.SCREEN_WIDTH + offset + swipe, 0 - 10, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);

			double zoom = 0.05 * (1 + Math.sin(frameCount * 0.025)) + 1;
			
			int imgSizeX = (int)(Constants.SCREEN_WIDTH * zoom);
			int imgSizeY = (int)(Constants.SCREEN_HEIGHT * zoom);
			int imgLocX = (int)((double)(Constants.SCREEN_WIDTH - imgSizeX) / 2);
			int imgLocY = (int)((double)(Constants.SCREEN_HEIGHT - imgSizeY) / 2);
			
			g2.drawImage(ImageManager.tutorial_screen_2, imgLocX + swipe, imgLocY, imgSizeX, imgSizeY, null);
		}
		if(timeLeft == 0) {
			state = "countdown";
		}
	}
	
	public void countdownScene(Graphics2D g2) {
		
	}
	
}






