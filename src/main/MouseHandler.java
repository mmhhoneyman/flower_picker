package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

public class MouseHandler implements MouseListener, MouseMotionListener{

	public boolean leftClick, rightClick;
	public boolean leftClickAndRelease;
	public int mouseX, mouseY;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (SwingUtilities.isLeftMouseButton(e)) {
			leftClickAndRelease = true;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		mouseX = e.getX();
		mouseY = e.getY();
		
		if (SwingUtilities.isLeftMouseButton(e)) {
			leftClick = true;
		}
		else if (SwingUtilities.isRightMouseButton(e)) {
			rightClick = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		mouseX = e.getX();
		mouseY = e.getY();
		
		if (SwingUtilities.isLeftMouseButton(e)) {
			leftClick = false;
		}
		else if (SwingUtilities.isRightMouseButton(e)) {
			rightClick = false;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getX();
		mouseY = e.getY();
	} 

}
