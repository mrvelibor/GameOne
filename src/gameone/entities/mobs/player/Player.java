package gameone.entities.mobs.player;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import gameone.entities.Entity;
import gameone.entities.mobs.Mob;
import gameone.input.Keyboard;
import gameone.input.Mouse;

public abstract class Player extends Mob {
		
	public static final int WIDTH = SIZE,
							HEIGHT = 2 * SIZE;
	
	
	public final Resource resource;
	
	
	public Player(int x, int y, DefensiveStats def, OffensiveStats off, Resource res, double spd) {
		super(x, y, WIDTH, HEIGHT, 1, def, off, spd);
		resource = res;
	}
	
	
	public static interface Resource {
		
		public int get();
		
		public int getMax();
		
		public double getPercent();
		
		public Color getColor();
		
	}
	
	
	private void keyboardMove() {
		int x = 0, y = 0;
		if(Keyboard.up) y--;
		if(Keyboard.down) y++;
		if(Keyboard.left) x--;
		if(Keyboard.right) x++;
		clearDestination();
		clearTarget();
		move(Direction.get(x, y));
	}
	
	private void rightClickAction(int x, int y) {
		Rectangle tar = new Rectangle(x, y, 1, 1);
		for(Entity e : level.elements)
			if(e.getSize().intersects(tar)) {
				setDestination(e);
				return;
			}
		clearDestination();
		setDestination(x, y);
	}
	
	
	@Override
	public void update() {
		Keyboard.update();
		
		if(Keyboard.move()) keyboardMove();
		else {
			if(Mouse.rightClick()) rightClickAction(Mouse.getMapX(), Mouse.getMapY());
			super.update();
		}

		if(Mouse.leftClick())
			attack(Mouse.getMapX(), Mouse.getMapY());
	}
	

	@Override
	public Rectangle2D.Double getBounds() {
		return new Rectangle2D.Double(posX + width/4, posY + height*3/4, width/2, height/4);
	}
	
	@Override
	public Rectangle2D.Double getHitBox() {
		return new Rectangle2D.Double(posX + width/4, posY + 3, width/2, height*2/3);
	}
	
}
