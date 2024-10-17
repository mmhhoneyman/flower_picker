package scene;

import javax.swing.JPanel;

import main.Constants;
import main.MouseHandler;
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
	public String prevState;
	
	public int frameCount;
	public int postStamp;
	public int timeLeft;
	
	public Runnable active;
	
	MouseHandler mouseH;
	Thread sceneThread;
	Random random;
	
	
public ScenePanel(String state) {
		
		random = new Random(System.currentTimeMillis());
		mouseH = new MouseHandler();
		
		this.state = state;
		this.prevState = state;
		
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
		System.out.println(timeLeft);
		
	}
		
	public void paintComponent(Graphics g) {
			
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		switch(state) {
			case "credits":
				creditsScene(g2);
				break;
			case "opening":
				
				break;
		}
		
		g2.dispose();
	}
	
	public void creditsScene(Graphics2D g2) {
		
		int interval = 420; // 7 seconds
		
		if(state == prevState) {
			prevState = null;
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
				active.run();
				try {
					sceneThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("foo");
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
	
}
