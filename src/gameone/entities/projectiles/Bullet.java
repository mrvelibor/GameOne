package gameone.entities.projectiles;

import gameone.entities.Active;
import gameone.graphics.Sprite;

public class Bullet extends Projectile {
	
	public static final int SIZE = 3;

	private static final Sprite SPRITE = new Sprite(SIZE, 0xfffffa00);

	
	public Bullet(Active src, int x, int y, double spd) {
		super(src, x, y, SIZE, SIZE, 10, spd);
	}
	
	public Bullet(Active src, int x, int y) {
		this(src, x, y, 5);
	}
	
	
	@Override
	public Sprite getSprite() {
		return SPRITE;
	}

}
