package scene;

import javax.swing.JPanel;

import main.Constants;
import main.MouseHandler;
import main.Utility;
import main.ImageManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.AlphaComposite;
import java.awt.Color;


public class ScenePanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	
	public String state; // decides what scene the game is on
						 // options: credits, opening, title, bedroom, tutorial, game, eating, giving (hopefully)
	public String nextState;
	
	public int frameCount;
	public int postStamp;
	public int timeLeft;
	
	public Runnable active;
	
	MouseHandler mouseH;
	public Thread sceneThread;
	Random random;
	
	
public ScenePanel(String state) {
		
		random = new Random(System.currentTimeMillis());
		mouseH = new MouseHandler();
		
		this.state = state;
		this.nextState = state;
		
		frameCount = 0;
		postStamp = 1;
		
		this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addMouseListener(mouseH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
	
		sceneThread = new Thread(this);
		sceneThread.start();
	
	}
	
	public void isActive(Runnable active) {
		this.active =  active;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		double refreshRate = 1000000000/Constants.FPS; // the length of a frame in nanoseconds
		double nextDrawTime = System.nanoTime() + refreshRate;
		
		while(sceneThread != null) {
			
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
		
		timeLeft = postStamp - frameCount;;
		//System.out.println(timeLeft);
		
	}
		
	public void paintComponent(Graphics g) {
			
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		switch(state) {
			case "credits":
				creditsScene(g2);
				break;
			case "opening":
				openingScene(g2);
				break;
			case "title":
				titleScene(g2);
				break;
		}
		
		g2.dispose();
	}
	
	public void creditsScene(Graphics2D g2) {
		
		int interval = 420; // 7 seconds
		
		if(state == nextState) {
			nextState = "opening";
			postStamp = frameCount + interval;
		} else {
			if(interval - timeLeft < interval / 5) {
				g2.setColor(Color.black);
				g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
			} else if(interval - timeLeft < 2 * interval / 5) {
				g2.drawImage(ImageManager.credit_image_1, 20, 20, Constants.SCREEN_WIDTH - 40, Constants.SCREEN_HEIGHT - 40, null);
			} else if(interval - timeLeft < 3 * interval / 5) {
				g2.drawImage(ImageManager.credit_image_2, 20, 20, Constants.SCREEN_WIDTH - 40, Constants.SCREEN_HEIGHT - 40, null);
			} else if(timeLeft == 0) {
				state = "opening";
				//sceneThread = null;
				//System.out.println("foo");
			} else if(interval - timeLeft < 4 * interval / 5) {
				g2.drawImage(ImageManager.credit_image_2, 20, 20, Constants.SCREEN_WIDTH - 40, Constants.SCREEN_HEIGHT - 40, null);
				
				float transparency = ((float)(interval - timeLeft) - (3 * interval / 5)) / (float)(interval / 5);
				g2.setColor(Color.black);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
				g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
			} else {
				g2.setColor(Color.black);
				g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
			}
		}
		
	}
	
	public void openingScene(Graphics2D g2) {
		
		int interval = 1500;
		
		if(state == nextState) {
			nextState = "title";
			postStamp = frameCount + interval;
		} else {
			int offset = (int)(((double)(interval - timeLeft) / (interval * 5.0 / 7.0)) * Constants.SCREEN_HEIGHT);
			if(interval - timeLeft < 3 * interval / 7) {
				g2.drawImage(ImageManager.title_screen_sky, 0, 0 - offset, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
				g2.drawImage(ImageManager.title_screen_ground, 0, 0 + Constants.SCREEN_HEIGHT - offset, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
				
				float transparency = ((float)(timeLeft) - (4 * interval / 7)) / (float)(3 * interval / 7);
				g2.setColor(Color.black);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
				g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
			} else if(interval - timeLeft < 5 * interval / 7) {
				g2.drawImage(ImageManager.title_screen_sky, 0, 0 - offset, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
				g2.drawImage(ImageManager.title_screen_ground, 0, 0 + Constants.SCREEN_HEIGHT - offset, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			} else if(interval - timeLeft < 7 * interval / 7) {
				g2.drawImage(ImageManager.title_screen_ground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
				
				if(interval - timeLeft < 23 * interval / 28 && interval - timeLeft >= 22 * interval / 28) {
					if(frameCount % 2 == 0) {
						g2.drawImage(ImageManager.title_image, Constants.SCREEN_WIDTH / 2 - 72 * Constants.SCALE, Constants.SCREEN_HEIGHT / 3 - 38 * Constants.SCALE, 72 * Constants.SCALE * 2, 38 * Constants.SCALE * 2, null);
					}
				} else if(interval - timeLeft >= 23 * interval / 28){
					g2.drawImage(ImageManager.title_image, Constants.SCREEN_WIDTH / 2 - 72 * Constants.SCALE, Constants.SCREEN_HEIGHT / 3 - 38 * Constants.SCALE, 72 * Constants.SCALE * 2, 38 * Constants.SCALE * 2, null);
				}
			}
		}
		if(timeLeft == 0) {
			g2.drawImage(ImageManager.title_screen_ground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			g2.drawImage(ImageManager.title_image, Constants.SCREEN_WIDTH / 2 - 72 * Constants.SCALE, Constants.SCREEN_HEIGHT / 3 - 38 * Constants.SCALE, 72 * Constants.SCALE * 2, 38 * Constants.SCALE * 2, null);
			state = "title";
		}
	}
	
	public void titleScene(Graphics2D g2) {
		int interval = Utility.generateRandom(100, 300);
		
		g2.drawImage(ImageManager.title_screen_ground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
		g2.drawImage(ImageManager.title_image, Constants.SCREEN_WIDTH / 2 - 72 * Constants.SCALE, Constants.SCREEN_HEIGHT / 3 - 38 * Constants.SCALE, 72 * Constants.SCALE * 2, 38 * Constants.SCALE * 2, null);
		
		if(state == nextState) {
			nextState = "bedroom";
			postStamp = frameCount + interval;
		} else {
			
		}
		
	}
	
}
