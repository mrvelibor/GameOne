package gameone.level.tiles;

import gameone.graphics.Sprite;

public class VoidTile extends Tile {
	
	public static final int LEVEL_CODE = -1;
	
	
	public VoidTile() {
		super(new Sprite(Tile.SIZE, 0xff000000));
	}

	@Override
	public boolean isCollidable() {
		return true;
	}
	

	@Override
	public String toString()
	{ return "Nothingness"; }
	
}
