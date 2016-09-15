package gameone.level.tiles;

import gameone.graphics.Sprite;
import gameone.graphics.SpriteSheet;

public class WaterTile extends Tile {
	
	public static final int MAP_COLOR = 0xff0026ff,
							LEVEL_CODE = 1;

	public WaterTile() {
		super(new Sprite(Tile.SIZE, 0, 1, SpriteSheet.tiles));
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public String toString()
	{ return "Water"; }

}
