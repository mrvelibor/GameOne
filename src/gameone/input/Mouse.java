package gameone.input;

import gameone.GameOne;
import gameone.graphics.Screen;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Mouse implements MouseListener, MouseMotionListener {
	
	private static int posX = -1, posY = -1;
	private static boolean[] buttons = new boolean[5];

	
	public static boolean pressed() {
		for(int i = 0; i < buttons.length; i++)
			if(buttons[i]) return true;
		return false;
	}
	
	public static boolean leftClick() {
		return buttons[0];
	}

	public static boolean rightClick() {
		return buttons[2];
	}

	public static boolean middleClick() {
		return buttons[1];
	}
	
	public static boolean button4() {
		return buttons[3];
	}
	
	public static boolean button5() {
		return buttons[4];
	}
	
	public static boolean[] getPressed() {
		return buttons.clone();
	}

	
	public static int getMapX() {
		return getScreenX() + Screen.getX();
	}
	
	public static int getMapY() {
		return getScreenY() + Screen.getY();
	}
	
	public static int getScreenX() {
		return GameOne.scaleDown(posX);
	}
	
	public static int getScreenY() {
		return GameOne.scaleDown(posY);
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()-1] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()-1] = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		posX = e.getX(); posY = e.getY();
	}
}
