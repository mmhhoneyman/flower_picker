package main;

import javax.swing.JFrame;

import scene.ScenePanel;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Mother's Day");
		//window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		while(1==1) { // im sure this won't cause problems
			
			GamePanel gamePanel = new GamePanel();
			ScenePanel scenePanel = new ScenePanel("eating", gamePanel.player);
			
			window.add(scenePanel);
			window.pack();
			scenePanel.startGameThread();
			
			try {
				scenePanel.sceneThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            window.remove(scenePanel);
            window.add(gamePanel);
            	window.pack();
            window.revalidate();
            window.repaint();
            gamePanel.startGameThread();
            
            try {
				gamePanel.gameThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ScenePanel scenePanel2 = new ScenePanel("eating", gamePanel.player);
            
            window.remove(gamePanel);
            window.add(scenePanel2);
            window.revalidate();
            window.repaint();
            scenePanel2.startGameThread();
            
            try {
				scenePanel2.sceneThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
