package scene;

import javax.swing.JPanel;

import main.Constants;
import main.MouseHandler;
import main.ImageManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.Color;


public class ScenePanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	
	public String state; // decides what scene the game is on
						 // options: credits, opening, title, bedroom, tutorial, eating, giving (hopefully)
	
	public int frameCount;
	public int frameStamp;
	
	MouseHandler mouseH;
	Thread sceneThread;
	Random random;
	
	
public ScenePanel(String state) {
		
		random = new Random(System.currentTimeMillis());
		mouseH = new MouseHandler();
		
		this.state = state;
		
		frameCount = 0;
		
		this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
		this.setBackground(Color.gray);
		this.setDoubleBuffered(true);
		this.addMouseListener(mouseH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
	
		sceneThread = new Thread(this);
		sceneThread.start();
	
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
		
		
		
	}
		
	public void paintComponent(Graphics g) {
			
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		
		
		g2.dispose();
	}
	
}
