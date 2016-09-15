package gameone.level.tiles;

import gameone.graphics.Sprite;
import gameone.graphics.SpriteSheet;

public class GrassTile extends Tile {
	
	public static final int MAP_COLOR = 0xff00ff21,
							LEVEL_CODE = 0;
	

	public GrassTile() {
		super(new Sprite(Tile.SIZE, 0, LEVEL_CODE, SpriteSheet.tiles));
	}

	@Override
	public boolean isCollidable() {
		return false;
	}
	
	@Override
	public String toString()
	{ return "Grass"; }

}
