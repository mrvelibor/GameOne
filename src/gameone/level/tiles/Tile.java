package gameone.level.tiles;

import gameone.graphics.*;

public abstract class Tile {

	private Sprite sprite;
		
	
	public static final int SIZE = 16;
	
	public static Tile empty = new VoidTile(),
					   grass = new GrassTile(),
					   rock = new RockTile(),
					   water = new WaterTile(),
					   plank = new PlankTile();
	
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
		
	
	public abstract boolean isCollidable();
	
	
	public void render(int x, int y) {
		Screen.renderTile(x, y, sprite);
	}
	
}
