package gameone.level.tiles;

import gameone.graphics.Sprite;
import gameone.graphics.SpriteSheet;

public class RockTile extends Tile {
	
	public static final int MAP_COLOR = 0xff404040,
							LEVEL_CODE = 2;
	
	public RockTile() {
		super(new Sprite(Tile.SIZE, 0, LEVEL_CODE, SpriteSheet.tiles));
	}
	
	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public String toString()
	{ return "Rock"; }

}
