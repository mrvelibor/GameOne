package gameone.level.tiles;

import gameone.graphics.Sprite;
import gameone.graphics.SpriteSheet;

public class PlankTile extends Tile {
	
	public static final int MAP_COLOR = 0xff3d1f11,
							LEVEL_CODE = 3;

	public PlankTile() {
		super(new Sprite(Tile.SIZE, 0, LEVEL_CODE, SpriteSheet.tiles));
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

	@Override
	public String toString()
	{ return "Plank"; }

}