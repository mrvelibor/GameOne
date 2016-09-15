package gameone.entities.mobs.enemies;

import java.awt.geom.Rectangle2D;

import gameone.GameOne;
import gameone.entities.mobs.Mob;
import gameone.entities.projectiles.Magick;
import gameone.graphics.Sprite;
import gameone.graphics.SpriteSheet;

public class Cigan extends Mob {

	public static final int WIDTH = SIZE,
							HEIGHT = 2 * SIZE;

	private static final Sprite[][] SPRITE = new Sprite[4][4];
	static {
		for(int i = 0; i < SPRITE.length; i++)
			for(int j = 0; j < SPRITE[i].length; j++)
				SPRITE[i][j] = new Sprite(WIDTH, HEIGHT, i, j, SpriteSheet.enemy);
	}

	
	public Cigan(int x, int y) {
		super(x, y, WIDTH, HEIGHT, 2, new DefensiveStats(20, 0, 0), new OffensiveStats(1, 0, 0.5, 4), 0.5);
		setTarget(GameOne.player1);
		setDestination(GameOne.player1);
		//stopMoving();
	}
	

	/*private double nextX = 8*SIZE, nextY = 4*SIZE;
	@Override
	protected void stopMoving() {
		setDestination(nextX, nextY);
		nextX = getCenterX(); nextY = getCenterY();
	}*/
	
	@Override
	public void update() {
		super.update();
	}

	
	@Override
	public Rectangle2D.Double getBounds() {
		return new Rectangle2D.Double(posX + width/4, posY + height*3/4, width/2, height/4);
	}
	
	@Override
	public Rectangle2D.Double getHitBox() {
		return new Rectangle2D.Double(posX + width/4, posY + 3, width/2, height*2/3);
	}
	
	@Override
	public Sprite getSprite() {
		return SPRITE[direction.getInt()/2][anim/20 % 4];
	}


	@Override
	protected void fire(int x, int y) {
		new Magick(this, x, y, offense.getProjectSpeed());
	}
}
