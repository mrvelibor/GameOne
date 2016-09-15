package gameone.entities.projectiles;

import gameone.entities.Active;
import gameone.graphics.Sprite;
import gameone.graphics.SpriteSheet;

public class Magick extends Projectile {
	
	public static final int HEIGHT = SIZE,
							WIDTH = SIZE;

	private static final Sprite[][] SPRITE = new Sprite[8][4];
	static {
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 4; j++)
				SPRITE[i][j] = new Sprite(SIZE, i, j, SpriteSheet.projectiles);
	}


	public Magick(Active src, int x, int y, double spd) {
		super(src, x, y, WIDTH, HEIGHT, 20, spd);
	}
	
	public Magick(Active src, int x, int y) {
		this(src, x, y, 3);
	}
	
	
	@Override
	public Sprite getSprite() {
		return SPRITE[direction.getInt()][anim/10 % 4];
	}

}