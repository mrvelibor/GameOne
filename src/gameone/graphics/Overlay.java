package gameone.graphics;

import gameone.GameOne;
import gameone.entities.*;
import gameone.entities.mobs.Mob;
import gameone.entities.mobs.player.Player;
import gameone.input.Mouse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Overlay {
	
	public static final Font FONT = new Font("Courier New", Font.BOLD, GameOne.scaleUp(8));
	
	
	private static Graphics2D graphics;
	
	public Overlay(BufferedImage img) {
		graphics = (Graphics2D) img.getGraphics();
	}
	
	
	/**
	 * Renders the cursor.
	 */
	public static void renderCursor() {
		int x = Mouse.getScreenX(),
			y = Mouse.getScreenY();
		
		if(Mouse.leftClick()) graphics.setColor(Color.yellow);
		else graphics.setColor(Color.red);
		
		graphics.fillRect(x-1, y-1, 2, 2);
		graphics.setColor(Color.white);
		graphics.drawOval(x-5, y-5, 9, 9);
	}
	
	/**
	 * Renders a line from mob to its destination.
	 */
	public static void renderDestination(Mob m) {
		if(m.hasDestination()) {
			int mX = m.getCenterX()-Screen.getX(),
				mY = m.getCenterY()-Screen.getY(),
				tarX = m.getDestinationX()-Screen.getX(),
				tarY = m.getDestinationY()-Screen.getY();
			
			graphics.setColor(Color.magenta);
			graphics.drawLine(mX, mY, tarX, tarY);
		}
	}
	
	/**
	 * Renders health bars above or below all entities.
	 */
	public static void renderMobHealthBars(boolean top) {
		for(int i = 0; i < GameOne.getLevel().elements.size(); i++) {
			Entity e = GameOne.getLevel().elements.get(i);
			if(e instanceof Player) continue;
			
			int x = e.getCenterX()-8,
				y = (top ? e.getTopY()-6 : e.getBotY()+2);
			graphics.setColor(Color.white);
			graphics.drawRect(x-Screen.getX(), y-Screen.getY(), 16, 4);
			
			Color c;
			double per = e.defense.getHealthPercent();
			if(per > 0.4) c = Color.green;
			else if(per > 0.2) c = Color.yellow;
			else c = Color.red;
			graphics.setColor(c);
			graphics.fillRect(x-Screen.getX()+1, y-Screen.getY()+1, (int) (1 + 14*per), 3);
		}
	}

	
	/**
	 * Renders the HUD.
	 */
	public static void renderHUD(int x, int y) {
		Player p = GameOne.player1;
		Color c;
		double per;
		
		int xSize = 60, ySize = 10,
			y1 = y + ySize + 2;
		graphics.setColor(Color.white);
		graphics.drawRect(x, y, xSize, ySize);
		graphics.drawRect(x, y1, xSize, ySize);
		
		
		per = p.defense.getHealthPercent();
		if(per > 0.4) c = Color.green;
		else if(per > 0.2) c = Color.yellow;
		else c = Color.red;
		graphics.setColor(c);
		graphics.fillRect(x+1, y+1, (int) (xSize*per)-1, ySize-1);
		
		per = p.resource.getPercent();
		c = p.resource.getColor();
		graphics.setColor(c);
		graphics.fillRect(x+1, y1+1, xSize-1, ySize-1);
	}

	/**
	 * Renders a square over all mobs' hit boxes and bounds.
	 */
	public static void renderMobBounds() {
		for(int i = 0; i < GameOne.getLevel().elements.size(); i++) {
			Entity e = GameOne.getLevel().elements.get(i);
			Rectangle2D.Double r = e.getBounds();
			graphics.setColor(Color.white);
			graphics.drawRect((int) (r.getX()-Screen.getX()), (int) (r.getY()-Screen.getY()), (int) r.getWidth(), (int) r.getHeight());
			r = e.getHitBox();
			graphics.setColor(Color.yellow);
			graphics.drawRect((int) (r.getX()-Screen.getX()), (int) (r.getY()-Screen.getY()), (int) r.getWidth(), (int) r.getHeight());
		}
	}
	
	/**
	 * Renders a square over all projectiles' bounds.
	 */
	public static void renderProjectileBounds() {
		for(int i = 0; i < GameOne.getLevel().projectiles.size(); i++) {
			Rectangle2D r = GameOne.getLevel().projectiles.get(i).getBounds();
			graphics.drawRect((int) (r.getX()-Screen.getX()), (int) (r.getY()-Screen.getY()), (int) (r.getWidth()), (int) (r.getHeight()));
		}
	}
	
	
	/**
	 * Renders coordinates of the player on the given x and y loction on the graphics component.
	 */
	public static void drawPlayerCoords(Graphics g, int x, int y) {
		x = GameOne.scaleUp(x); y = GameOne.scaleUp(y);
		int p = (int) GameOne.SCALE;
		
		g.setColor(Color.black);
		g.drawString("Player: " + GameOne.player1.getCenterX() + "x, " + GameOne.player1.getCenterY() + "y", x+p, y+p);
		g.setColor(Color.white);
		g.drawString("Player: " + GameOne.player1.getCenterX() + "x, " + GameOne.player1.getCenterY() + "y", x, y);
	}
	
	/**
	 * Renders coordinates of the mouse on the given x and y loction on the g component.
	 */
	public static void drawMouseCoords(Graphics g, int x, int y) {
		x = GameOne.scaleUp(x); y = GameOne.scaleUp(y);
		int p = (int) GameOne.SCALE;
		
		StringBuilder sb = new StringBuilder("Mouse:  ");
		boolean[] b = Mouse.getPressed();
		for(int i = 0; i < b.length; i++)
			if(b[i]) sb.append(i+1);
		
		g.setColor(Color.black);
		g.drawString(sb.toString(), x+p, y+p);
		g.drawString("        " + Mouse.getMapX() + "x, " + Mouse.getMapY() + "y", x+p, y+p + g.getFont().getSize());
		
		g.setColor(Color.white);
		g.drawString(sb.toString(), x, y);
		g.drawString("        " + Mouse.getMapX() + "x, " + Mouse.getMapY() + "y", x, y + g.getFont().getSize());
	}
	
	/**
	 * Renders number of frames per second on the given x and y loction on the g component.
	 */
	public static void drawFpsCounter(Graphics g, int x, int y) {
		x = GameOne.scaleUp(x); y = GameOne.scaleUp(y);
		int p = (int) GameOne.SCALE;
		
		g.setColor(Color.black);
		g.drawString(GameOne.getUPS() + " UPS, " + GameOne.getFPS() + " FPS", x+p, y+p);
		
		g.setColor(Color.white);
		g.drawString(GameOne.getUPS() + " UPS, " + GameOne.getFPS() + " FPS ", x, y);
	}
	
}
