package main;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import scene.ScenePanel;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Mother's Day");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);

        while (true) {
            resetGame(window);
        }
    }

    private static void resetGame(JFrame window) {
        GamePanel gamePanel = new GamePanel();
        ScenePanel scenePanel = new ScenePanel("credits", gamePanel.player);
        ScenePanel scenePanel2 = new ScenePanel("eating", gamePanel.player);
        

        resetContentPane(window, scenePanel);
        scenePanel.startGameThread();
        try { scenePanel.sceneThread.join(); } catch (InterruptedException e) { e.printStackTrace(); }

        resetContentPane(window, gamePanel);
        gamePanel.startGameThread();
        try { gamePanel.gameThread.join(); } catch (InterruptedException e) { e.printStackTrace(); }

        resetContentPane(window, scenePanel2);
        scenePanel2.startGameThread();
        try { scenePanel2.sceneThread.join(); } catch (InterruptedException e) { e.printStackTrace(); }

        SwingUtilities.invokeLater(() -> {
            window.setContentPane(new JPanel());
            window.getContentPane().setBackground(Color.BLACK);
            window.revalidate();
            window.repaint();
        });

        System.gc(); 
    }

    private static void resetContentPane(JFrame window, JPanel newPanel) {
        SwingUtilities.invokeLater(() -> {
            window.setContentPane(newPanel);
            window.pack();
            window.revalidate();
            window.repaint();
            newPanel.requestFocusInWindow();
        });
    }
}