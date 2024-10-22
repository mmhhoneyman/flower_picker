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
		}
		
	}
	
	public void update() {
		
		if((state.equals("game") || frameCount == 0) && frameCount < Constants.TOTAL_GAME_TIME - Constants.POSTGAME_TIMER) {
			tileM.update();
			player.update();
			entityM.update();
		} else if(!(frameCount < Constants.TOTAL_GAME_TIME - Constants.POSTGAME_TIMER)) {
			state = "supper time!";
		}
		timeLeft = postStamp - frameCount;
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		tileM.draw(g2);
		player.draw(g2);
		entityM.draw(g2);
		skyDarken(g2);
		
		switch(state) {
			case "tutorial":
				tutorialScene(g2);
				break;
			case "countdown":
				countdownScene(g2);
				break;
			case "supper time!":
				supperTimeScene(g2);
				break;
		}
		
		if(frameCount == 0) {
			g2.setColor(Color.black);
			g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		}
		
		g2.dispose();
	}
	
	public void tutorialScene(Graphics2D g2) {
		int interval1 = Constants.TUTORIAL_INT_1;
		int interval2 = Constants.TUTORIAL_INT_2;
		int interval3 = Constants.TUTORIAL_INT_3;
		
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
			
			g2.drawImage(ImageManager.tutorial_screen_2, imgLocX + swipe + (offset - interval2), imgLocY, imgSizeX, imgSizeY, null);
		}
		if(timeLeft == 0) {
			state = "countdown";
		}
	}
	
	public void countdownScene(Graphics2D g2) {
		int interval1 = Constants.COUNTDOWN_INT_1;
		int interval2 = Constants.COUNTDOWN_INT_2;
		int interval3 = Constants.COUNTDOWN_INT_3;
		
		if(state == nextState) {
			nextState = "game";
			postStamp = frameCount + interval3;
		}
		if(timeLeft + interval1 >= interval3) {
			double zoom = (double)(timeLeft - (interval3 - interval1)) / (interval1) + 1;
			
			int imgSizeX = (int)(8 * 15 * zoom);
			int imgSizeY = (int)(10 * 15 * zoom);
			int imgLocX = (int)((double)(Constants.SCREEN_WIDTH - imgSizeX) / 2);
			int imgLocY = (int)((double)(Constants.SCREEN_HEIGHT - imgSizeY) / 2);
			
			g2.drawImage(ImageManager.time_number_3, imgLocX, imgLocY, imgSizeX, imgSizeY, null);
		} else if(timeLeft + interval2 >= interval3) {
			double zoom = (double)(timeLeft - (interval3 - interval2)) / (interval2 - interval1) + 1;
			
			int imgSizeX = (int)(8 * 15 * zoom);
			int imgSizeY = (int)(10 * 15 * zoom);
			int imgLocX = (int)((double)(Constants.SCREEN_WIDTH - imgSizeX) / 2);
			int imgLocY = (int)((double)(Constants.SCREEN_HEIGHT - imgSizeY) / 2);
			
			g2.drawImage(ImageManager.time_number_2, imgLocX, imgLocY, imgSizeX, imgSizeY, null);
		} else if(timeLeft + interval3 >= interval3) {
			double zoom = (double)(timeLeft) / (interval3 - interval2) + 1;
			
			int imgSizeX = (int)(8 * 15 * zoom);
			int imgSizeY = (int)(10 * 15 * zoom);
			int imgLocX = (int)((double)(Constants.SCREEN_WIDTH - imgSizeX) / 2);
			int imgLocY = (int)((double)(Constants.SCREEN_HEIGHT - imgSizeY) / 2);
			
			g2.drawImage(ImageManager.time_number_1, imgLocX, imgLocY, imgSizeX, imgSizeY, null);
		}
		if(timeLeft == 0) {
			state = "game";
			nextState = "supper time!";
		}
	}
	
	public void supperTimeScene(Graphics2D g2) {
		int interval1 = Constants.SUPPER_TIME_INT_1;
		int interval2 = Constants.SUPPER_TIME_INT_2;
		int interval3 = Constants.SUPPER_TIME_INT_3;
		int interval4 = Constants.SUPPER_TIME_INT_4;
		
		if(state == nextState) {
			nextState = null;
			postStamp = Constants.TOTAL_GAME_TIME;
		}
		if(timeLeft + interval1 >= interval4) {
			int offset = (frameCount / 4 % 2) * 10;
			g2.drawImage(ImageManager.callout_image, Constants.SCREEN_WIDTH / 4 + offset, Constants.SCREEN_HEIGHT - 48 * 4, 48 * 4, 48 * 4, null);
		} else if(timeLeft + interval2 >= interval4) {
			g2.drawImage(ImageManager.callout_image, Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT - 48 * 4, 48 * 4, 48 * 4, null);
		} else if(timeLeft + interval3 >= interval4) {
			g2.drawImage(ImageManager.callout_image, Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT - 48 * 4, 48 * 4, 48 * 4, null);
			
			float transparency = (float)(interval4 - interval2 - timeLeft) / (float)(interval3 - interval2);
			g2.setColor(Color.black);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
			g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
		} else if(timeLeft + interval4 >= interval4) {
			g2.setColor(Color.black);
			g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		}
		if(timeLeft == 0) {
			gameThread = null;
		}
	}
	
	public void skyDarken(Graphics2D g2) {
		if(frameCount > Constants.PREGAME_TIMER) {
			float transparency;
			if(frameCount < Constants.TOTAL_GAME_TIME - Constants.POSTGAME_TIMER) {
				transparency = (float)(frameCount - Constants.PREGAME_TIMER) / (Constants.TOTAL_GAME_TIME - Constants.PREGAME_TIMER - Constants.POSTGAME_TIMER) / 3.0f;
			} else {
				transparency = 0.33333334f;
			}
			
			g2.setColor(new Color(5, 15, 45));
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
			g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
		}
	}
	
}






