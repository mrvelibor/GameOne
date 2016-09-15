package gameone.entities.mobs.player;

import gameone.entities.projectiles.Bullet;
import gameone.graphics.Sprite;
import gameone.graphics.SpriteSheet;

import java.awt.Color;

public class Mage extends Player {
	
	private static final Sprite[][] SPRITE = new Sprite[4][4];
	static {
		for(int i = 0; i < SPRITE.length; i++)
			for(int j = 0; j < SPRITE[i].length; j++)
				SPRITE[i][j] = new Sprite(WIDTH, HEIGHT, i, j, SpriteSheet.player);
	}
	

	public Mage(int x, int y) {
		super(x, y, new DefensiveStats(100, 2, 1), new OffensiveStats(5, 1, 9, 5), new Mana(100), 1.2); 
	}
	
	
	public static class Mana implements Resource {
		
		protected int max, current;
		
		public Mana(int m) {
			max = current = m;
		}

		@Override
		public int get() {
			return current;
		}

		@Override
		public int getMax() {
			return max;
		}

		@Override
		public double getPercent() {
			return (double) current/max;
		}

		@Override
		public Color getColor() {
			return Color.blue;
		}
		
	}


	@Override
	protected void fire(int x, int y) {
		new Bullet(this, x, y, offense.getProjectSpeed());
	}
	
	@Override
	public Sprite getSprite() {
		return SPRITE[direction.getInt()/2][anim/20 % 4];
	}

}