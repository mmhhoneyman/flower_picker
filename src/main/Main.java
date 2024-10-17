package main;

import javax.swing.JFrame;

import scene.ScenePanel;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Flower Picker");
		//window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		GamePanel gamePanel = new GamePanel();
		ScenePanel scenePanel = new ScenePanel("credits");
		
		//while(1==1) {
			window.add(scenePanel);
			
			window.pack();
			
			scenePanel.startGameThread();
			
			scenePanel.isActive(() -> {
	            window.remove(scenePanel);
	            window.add(gamePanel);
	            window.revalidate();
	            window.repaint();
	            gamePanel.startGameThread();
	        });
		//}
	}

}
