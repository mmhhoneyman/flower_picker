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
	
	MouseHandler mouseH;
	public Thread sceneThread;
	Random random;
	
	boolean playButtonClicked, openingSkipped, bedroomSkipped;
	int playButtonClicks;
	int sunClicks;
	boolean eggActivate;
	
	
public ScenePanel(String state) {
		
		random = new Random(System.currentTimeMillis());
		mouseH = new MouseHandler();
		
		this.state = state;
		this.nextState = state;
		
		frameCount = 0;
		postStamp = 1;
		
		playButtonClicked = false;
		openingSkipped = false;
		bedroomSkipped = false;
		playButtonClicks = 0;
		sunClicks = 0;
		
		this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addMouseListener(mouseH);
		this.addMouseMotionListener(mouseH);
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
			case "bedroom":
				bedroomScene(g2);
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
		int interval = 180; // 3 seconds
		int interval2 = 300; // 5 seconds
		
		g2.drawImage(ImageManager.title_screen_ground, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
		g2.drawImage(ImageManager.title_image, Constants.SCREEN_WIDTH / 2 - 72 * Constants.SCALE, Constants.SCREEN_HEIGHT / 3 - 38 * Constants.SCALE, 72 * Constants.SCALE * 2, 38 * Constants.SCALE * 2, null);
		
		if(state == nextState) {
			nextState = "bedroom";
		} else {
			
		}
		
		int playButtonLocX = Constants.SCREEN_WIDTH / 2 - 43 * Constants.SCALE * 2/3;
		int playButtonLocY = 2 * Constants.SCREEN_HEIGHT / 3 - 20 * Constants.SCALE;
		int playButtonSizeX = 43 * Constants.SCALE * 4 / 3;
		int playButtonSizeY = 20 * Constants.SCALE * 4 / 3;
		int trimming = 10; // makes the clickbox for buttons a little smaller
		
		//System.out.println(mouseH.mouseX);
		//System.out.println(mouseH.mouseY);
		
		if(mouseH.mouseX >= playButtonLocX + trimming && mouseH.mouseX <= playButtonLocX + playButtonSizeX - trimming
			&& mouseH.mouseY >= playButtonLocY && mouseH.mouseY <= playButtonLocY + playButtonSizeY - trimming) {
			g2.drawImage(ImageManager.play_button_flicker, playButtonLocX, playButtonLocY, playButtonSizeX, playButtonSizeY, null);
			if(mouseH.leftClick) {
				playButtonClicked = true;
				playButtonClicks++;
				g2.drawImage(ImageManager.play_button_push, playButtonLocX, playButtonLocY, playButtonSizeX, playButtonSizeY, null);
			}
		} else {
			g2.drawImage(ImageManager.play_button_neutral, playButtonLocX, playButtonLocY, playButtonSizeX, playButtonSizeY, null);
		}
		
		if(mouseH.mouseX >= 90 && mouseH.mouseX <= 140
			&& mouseH.mouseY >= 45 && mouseH.mouseY <= 95) {
			if(mouseH.leftClickAndRelease) {
				sunClicks++;
				mouseH.leftClickAndRelease = false;
				}
			}
		
		if(sunClicks == 10) {
			sunClicks++;
			eggActivate = true;
			postStamp = frameCount + interval;
		}
		
		if(eggActivate == true) {
			float transparency = (float)(timeLeft) / (float)(interval) * 1.2f;
			if(transparency > 1.0f) {
				transparency = 1.0f;
			}
			if(transparency < 0f) {
				transparency = 0f;
			}
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
			g2.drawImage(ImageManager.not_easter_egg, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
			if(timeLeft == 0) {
				eggActivate = false;
			}
		}
		
		if(playButtonClicked && playButtonClicks == 1) {
			postStamp = frameCount + interval2;
			playButtonClicks++;
		}
		
		if(playButtonClicked) {
				float transparency = (float)(interval2 - timeLeft) / (float)(interval2);
				if(transparency > 1.0f || transparency < 0f) {
					transparency = 0f;
				}
			
			g2.setColor(Color.black);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
			g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
			
			if(timeLeft == 0) {
				state = "bedroom";
			}
		}
	}
	
	public void bedroomScene(Graphics2D g2) {
		// bedroom scene
		int interval1_1 = 300;
		int interval1_2 = interval1_1 + 400;
		int interval1_3 = interval1_2 + 400;
		
		// calendar mid-zoom
		int interval2_1 = interval1_3 + 300;
		int interval2_2 = interval2_1 + 300;
		int interval2_3 = interval2_2 + 300;
		int interval2_4 = interval2_3 + 50;
		
		// calendar, boy, fade-out
		int interval3_1 = interval2_4 + 200;
		int interval3_2 = interval3_1 + 200;
		int interval3_3 = interval3_2 + 300;
		int interval3_4 = interval3_3 + 200;
		
		if(state == nextState) {
			nextState = "tutorial";
			postStamp = frameCount + interval3_4;
		}
		
		if(timeLeft + interval1_1 >= interval3_4) {
			if(frameCount % 240 <= 40 * 1) {
				g2.drawImage(ImageManager.bedroom_1, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			} else if(frameCount % 240 <= 40 * 2) {
				g2.drawImage(ImageManager.bedroom_2, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			} else if(frameCount % 240 <= 40 * 3) {
				g2.drawImage(ImageManager.bedroom_3, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			} else if(frameCount % 240 <= 40 * 4) {
				g2.drawImage(ImageManager.bedroom_4, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			} else if(frameCount % 240 <= 40 * 5) {
				g2.drawImage(ImageManager.bedroom_5, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			} else if(frameCount % 240 <= 40 * 6) {
				g2.drawImage(ImageManager.bedroom_6, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			}
			
			float transparency = ((float)(timeLeft) - (interval3_4 - interval1_1)) / (float)(interval1_1);
			g2.setColor(Color.black);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
			g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
		} else if(timeLeft + interval1_3 >= interval3_4) {
			
			double zoom = (double)(interval3_4 - timeLeft - interval1_1) / (interval1_3 - interval1_1) + 1;
			
			int imgSizeX = (int)(Constants.SCREEN_WIDTH * zoom);
			int imgSizeY = (int)(Constants.SCREEN_HEIGHT * zoom);
			int imgLocX = (int)((double)(Constants.SCREEN_WIDTH - imgSizeX) * 1.34 / 2);
			int imgLocY = (int)((double)(Constants.SCREEN_HEIGHT - imgSizeY) / 2);
			
			if(frameCount % 240 <= 40 * 1) {
				g2.drawImage(ImageManager.bedroom_1, imgLocX, imgLocY, imgSizeX, imgSizeY, null);
			} else if(frameCount % 240 <= 40 * 2) {
				g2.drawImage(ImageManager.bedroom_2, imgLocX, imgLocY, imgSizeX, imgSizeY, null);
			} else if(frameCount % 240 <= 40 * 3) {
				g2.drawImage(ImageManager.bedroom_3, imgLocX, imgLocY, imgSizeX, imgSizeY, null);
			} else if(frameCount % 240 <= 40 * 4) {
				g2.drawImage(ImageManager.bedroom_4, imgLocX, imgLocY, imgSizeX, imgSizeY, null);
			} else if(frameCount % 240 <= 40 * 5) {
				g2.drawImage(ImageManager.bedroom_5, imgLocX, imgLocY, imgSizeX, imgSizeY, null);
			} else if(frameCount % 240 <= 40 * 6) {
				g2.drawImage(ImageManager.bedroom_6, imgLocX, imgLocY, imgSizeX, imgSizeY, null);
			}
			
			if(!(timeLeft + interval1_2 >= interval3_4)) {
				float transparency = (float)(interval3_4 - timeLeft - interval1_2) / (float)(interval1_3 - interval1_2);
				g2.setColor(Color.black);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
				g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
			}
		} else if(timeLeft + interval2_3 >= interval3_4) {
			
			double zoom = (double)(interval3_4 - timeLeft - interval1_3) / (interval2_3 - interval1_3) + 1;
			
			int imgSizeX = (int)(Constants.SCREEN_WIDTH * zoom);
			int imgSizeY = (int)(Constants.SCREEN_HEIGHT * zoom);
			int imgLocX = (int)((double)(Constants.SCREEN_WIDTH - imgSizeX) * 0.3 / 2);
			int imgLocY = (int)((double)(Constants.SCREEN_HEIGHT - imgSizeY) * 1.5 / 2);
			
			g2.drawImage(ImageManager.calendar_1, imgLocX, imgLocY, imgSizeX, imgSizeY, null);
			
			if(timeLeft + interval2_1 >= interval3_4) {
				float transparency = ((float)(timeLeft) - (interval3_4 - interval2_1)) / (float)(interval2_1 - interval1_3);
				g2.setColor(Color.black);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
				g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
			} else if(!(timeLeft + interval2_2 >= interval3_4)) {
				float transparency = ((float)(interval3_4 - timeLeft - interval2_2)) / (float)(interval2_3 - interval2_2);
				g2.setColor(Color.black);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
				g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
			}
		} else if(timeLeft + interval2_4 >= interval3_4) {
			g2.setColor(Color.black);
			g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		}
		else if(timeLeft + interval3_3 >= interval3_4) {
			if(timeLeft + interval3_1 >= interval3_4) {
				g2.drawImage(ImageManager.calendar_2, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			} else if(timeLeft + interval3_2 >= interval3_4) {
				g2.drawImage(ImageManager.shock, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
			} else if(timeLeft + interval3_3 >= interval3_4) {
				g2.drawImage(ImageManager.shock, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
				float transparency = (float)(interval3_4 - timeLeft - interval3_2) / (float)(interval3_3 - interval3_2);
				g2.setColor(Color.black);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
				g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // transparency set to 100%
			} else if(timeLeft + interval3_3 >= interval3_4) {
				g2.setColor(Color.black);
				g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
			}
		}
		if(timeLeft == 0) {
			state = "tutorial";
			sceneThread = null;
		}
	}
}
